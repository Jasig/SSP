package org.jasig.ssp.security;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.impl.client.DecompressingHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class BasicAuthenticationRestTemplate extends RestTemplate {
	
    public BasicAuthenticationRestTemplate(String username, String password, String clientIpAddress, Integer clientPort, Boolean acceptEncodingGzip) {
    	    	
        DefaultHttpClient clientd = new DefaultHttpClient();
        addClientAddress( clientd,  clientIpAddress,  clientPort);
        addAuthentication( clientd,  username,  password);
       
        setRequestFactory(new HttpComponentsClientHttpRequestFactory(addGzip(clientd, acceptEncodingGzip)));
    }
    
   
    
    public BasicAuthenticationRestTemplate(String username, String password) {
    	DefaultHttpClient clientd = new DefaultHttpClient();
        addAuthentication( clientd,  username,  password);
       
        setRequestFactory(new HttpComponentsClientHttpRequestFactory(clientd));
    }
    
    public BasicAuthenticationRestTemplate(String username, String password, Boolean acceptEncodingGzip) {
    	DefaultHttpClient clientd = new DefaultHttpClient();
        addAuthentication( clientd,  username,  password);
       
        setRequestFactory(new HttpComponentsClientHttpRequestFactory(addGzip(clientd, acceptEncodingGzip)));
    }
    
	@SuppressWarnings("unchecked")
	public Object get(String url, Map<String,String> params, Class objectClass){
		if(params == null)
			return getForObject(url, objectClass, params);
		return getForObject(url, objectClass, params);
	}
	
	 private HttpClient addClientAddress(HttpClient clientd, String clientIpAddress, Integer clientPort){
	    	if(StringUtils.isNotBlank(clientIpAddress) && clientPort != null){
	    		HttpHost proxy = new HttpHost(clientIpAddress, clientPort);
	    		clientd.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
	    	}
	         return clientd;
	    }
	    
	    private HttpClient addAuthentication(DefaultHttpClient clientd, String username, String password){
	    	clientd.getCredentialsProvider().setCredentials(new AuthScope(null, -1), 
	        		new UsernamePasswordCredentials(username, password));
	    	return clientd;
	   }
	    
	    private HttpClient addGzip(HttpClient clientd, Boolean acceptEncodingGzip){
	    	 
	        if(acceptEncodingGzip){
	        	return new DecompressingHttpClient(clientd);
	        }
	        return clientd;
	    }
}