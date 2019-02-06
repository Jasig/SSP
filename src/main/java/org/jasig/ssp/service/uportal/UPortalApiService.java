package org.jasig.ssp.service.uportal;

import com.fasterxml.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
public class UPortalApiService {

    private static final String DEFAULT_CLIENT_SECRET = "CHANGEME";

    @Value("${uportal_api_client_id:ssp}")
    private String clientId;

    @Value("${uportal_api_client_secret:" + DEFAULT_CLIENT_SECRET + "}")
    private String clientSecret;

    @Value("${uportal_api_server:http://localhost:8080}")
    private String uPortalServer;

    @Value("${uportal_api_access_token_uri:/uPortal/api/v5-5/oauth/token}")
    private String accessTokenUri;

    // @Value("${uportal_api_permissions_uri_format:/uPortal/api/assignments/principal/%s.json}")
    @Value("${uportal_api_permissions_uri_format:/uPortal/api/v5-5/assignments/users/%s?includeInherited=true}")
    private String assignmentsUriFormat;

    private OAuth2RestTemplate oAuth2RestTemplate;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @PostConstruct
    public void init() {

        // Server-based deployments really need to set a custom SspOAuthClient.clientSecret property...
        if (DEFAULT_CLIENT_SECRET.equals(clientSecret)) {
            final StringBuilder message = new StringBuilder();
            message
                    .append("\n****************************************************************")
                    .append("\n**                     SECURITY WARNING!                      **")
                    .append("\n**                                                            **")
                    .append("\n** The SspOAuthClient.clientSecret property currently has the **")
                    .append("\n** default value.  This configuration is insecure.  Please    **")
                    .append("\n** define a value for SspOAuthClient.clientSecret in either   **")
                    .append("\n** uPortal.properties or global.properties.                   **")
                    .append("\n****************************************************************");
            logger.warn(message.toString());
        }

        final ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
        details.setId("uPortal");
        details.setClientId(clientId);
        details.setClientSecret(clientSecret);
        details.setClientAuthenticationScheme(AuthenticationScheme.form); // Important!
        details.setAccessTokenUri(uPortalServer + accessTokenUri);

        oAuth2RestTemplate = new OAuth2RestTemplate(details);
        oAuth2RestTemplate.setAccessTokenProvider(new ClientCredentialsAccessTokenProvider());

    }

    public Set<Permission> getPermissionsForPrincipal(String principal) {

        final Set<Permission> rslt = new HashSet<>();

        logger.debug("Obtaining permissions from uPortal for principal='{}'", principal);

        try {
            final String apiUrl =
                    uPortalServer + String.format(assignmentsUriFormat, URLEncoder.encode(principal, "UTF-8"));
            final JsonNode node = oAuth2RestTemplate.getForObject(apiUrl, JsonNode.class);

            final JsonNode assignments = node.get("assignments");
            if (assignments.isArray()) {
                for (JsonNode assignment : assignments) {
                    final Permission p = new Permission(
                            assignment.get("ownerKey").textValue(),
                            assignment.get("activityKey").textValue(),
                            assignment.get("principalKey").textValue(),
                            assignment.get("targetKey").textValue(),
                            assignment.get("inherited").asBoolean());
                    rslt.add(p);
                }
            } else {
                throw new RuntimeException("JSON format not supported;  node type " + assignments.getNodeType());
            }

        } catch (Exception e) {
            throw new RuntimeException("Unable to optain permissions for principal: " + principal, e);
        }

        return rslt;

    }

}
