package org.jasig.ssp.service.uportal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

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
    @Value("${uportal_api_permissions_uri_format:/uPortal/api/v5-5/permissions/assignments/users/%s?includeInherited=true}")
    private String assignmentsUriFormat;

    @Value("${uportal_api_people_uri_format:/uPortal/api/v5-0/people/%s}")
    private String peopleUriFormat;

    @Value("${uportal_api_search_uri_format:/uPortal/api/people.json?searchTerms[]=}")
    private String searchUriFormat;

    private static final String PARAM_SEARCH_TERMS = "searchTerms";
    private static final String REST_URI_SEARCH_PREFIX = "/uPortal/api/people.json?searchTerms%5B%5D={" + PARAM_SEARCH_TERMS + "}";
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
            throw new RuntimeException("Unable to obtain permissions for principal: " + principal, e);
        }

        return rslt;

    }

    public Map<String, List<String>> getAttributesForPrincipal(String principal) {
        logger.debug("Obtaining attributes from uPortal for principal='{}'", principal);

        try {
            final String apiUrl =
                    uPortalServer + String.format(peopleUriFormat, URLEncoder.encode(principal, "UTF-8"));
            final JsonNode node = oAuth2RestTemplate.getForObject(apiUrl, JsonNode.class);

            ObjectMapper mapper = new ObjectMapper();
            Map<String, List<String>> result = mapper.convertValue(node, Map.class);
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Unable to obtain attributes for principal: " + principal, e);
        }
    }

    public List<Map<String, Object>> searchForUsers(final Map<String, String> query) {

        logger.debug("Searching for users with query terms '{}'", query);

        // Assemble searchTerms[] in expected way
        final List<String> searchTerms = new ArrayList<>();
        final Map<String, String[]> params = new HashMap<>();
        for (final Map.Entry<String, String> y : query.entrySet()) {
            searchTerms.add(y.getKey());
            params.put(y.getKey(), new String[] { y.getValue() });
        }

        try {
            String uri = searchUriFormat + URLEncoder.encode(String.join(",", searchTerms), "UTF-8");
            // Build the URL
            final StringBuilder bld = new StringBuilder(uPortalServer + uri);
            for (final String key : params.keySet()) {
                bld.append("&").append(key).append("={").append(key).append("}");
            }
            final String url = bld.toString();

            logger.debug("Invoking REST enpoint with URL '{}'", url);

            JsonNode node = oAuth2RestTemplate.getForObject(url, JsonNode.class, params);

            final JsonNode people = node.get("people");

            final ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> rslt = null;
            try {
                rslt = mapper.convertValue(people, List.class);
            } catch (final Exception e) {
                final String msg = "Failed to search for users with the specified query:  "
                        + query;
                throw new RuntimeException(msg, e);
            }

            logger.debug("Retrieved the following people for query {}:  {}",
                    query, rslt);

            return rslt;
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException("Unable to obtain users for query: " + query, ex);
        }
    }

}
