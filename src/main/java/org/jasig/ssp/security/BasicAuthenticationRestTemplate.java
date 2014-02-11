package org.jasig.ssp.security;

import java.util.Map;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class BasicAuthenticationRestTemplate extends RestTemplate {
    public BasicAuthenticationRestTemplate(String username, String password) {
        DefaultHttpClient clientd = new DefaultHttpClient();
        clientd.getCredentialsProvider().setCredentials(new AuthScope(null, -1), 
        		new UsernamePasswordCredentials(username, password));
        setRequestFactory(new HttpComponentsClientHttpRequestFactory(clientd));
    }
    
	@SuppressWarnings("unchecked")
	public Object get(String url, Map<String,String> params, Class objectClass){
		if(params == null)
			return getForObject(url, objectClass, params);
		return getForObject(url, objectClass, params);
	}
	
}