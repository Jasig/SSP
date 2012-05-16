package org.jasig.ssp

import org.apache.http.*
import org.apache.http.entity.*
import org.apache.http.client.*
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.protocol.ClientContext
import org.apache.http.conn.params.ConnRoutePNames
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.DefaultHttpClient
import org.apache.http.message.BasicNameValuePair
import org.apache.http.protocol.BasicHttpContext
import org.apache.http.protocol.HTTP
import org.apache.http.protocol.HttpContext
import org.apache.http.util.EntityUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * This class simply supports testing against a running version of the api.
 * It is a client for consuming services of the application
 */
class ApiConnection {
	//The base url of the application
	private String baseUrl

	//have we successfully authenticated with the application?
	private boolean authenticated = false

	//some necessary bits and pieces for Apache HttpClient
	private HttpClient httpClient
	private HttpContext httpContext
	private HttpHost httpHost
	private CookieStore cookieStore

	//Should we connect using an http proxy (like CharlesProxy?)
	private boolean useProxy
	//proxy settings for the above proxy
	private Map proxySettings = [host:"localhost", port: 8888]

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiConnection.class)

	/**
	 * Create a connection to the API
	 */
	public ApiConnection(String baseUrl, String username, String password, boolean useProxy = false){
		this.baseUrl = baseUrl
		this.useProxy = useProxy
		createConnection()
		authenticate(username, password)
	}

	/**
	 * Make a get request to the api
	 * @param url the remaining url and parameters after the baseUrl
	 */
	public String get(String url){
		//no authentication means no point in continuing
		if(!authenticated) return null

		LOGGER.debug("Creating Get Request")
		HttpGet httpGet = new HttpGet(baseUrl + url)

		HttpResponse response = httpClient.execute(httpHost, httpGet, httpContext)
		LOGGER.debug("Get response status: " + response.getStatusLine().toString())

		return extractResult(response)
	}

	/**
	 * Make a put request to the api.  Requires Authentication.
	 * @param url the remaining url and parameters after the baseUrl
	 */
	public String put(String url, payload){
		if(!authenticated) return null

		String fullUrl = baseUrl + url

		LOGGER.info("Put to $fullUrl")

		HttpPut httpPut = new HttpPut(fullUrl)
		attachPayload(httpPut, payload)

		httpPut.addHeader("Content-Type", "application/json")

		HttpResponse response = httpClient.execute(httpHost, httpPut, httpContext)
		LOGGER.debug("Put response status: " + response.getStatusLine())

		return extractResult(response)
	}

	/**
	 * Make a post request to the api.  Requires Authentication.
	 * @param url the remaining url and parameters after the baseUrl
	 */
	public String post(String url, payload){
		if(!authenticated) return null

		HttpResponse response = basePost(baseUrl + url, payload, null)

		return extractResult(response)
	}

	/**
	 * Makes a post request to the url with the given payload.
	 * Does not require authentication
	 * @param url the full url
	 */
	public HttpResponse basePost(String fullUrl, payload, String contentType){
		LOGGER.info("Post to $fullUrl")

		HttpPost httpPost = new HttpPost(fullUrl)
		attachPayload(httpPost, payload)

		if(!contentType){
			httpPost.addHeader("Content-Type", "application/json")
		}else{
			httpPost.addHeader("Content-Type", contentType)
		}

		HttpResponse response = httpClient.execute(httpHost, httpPost, httpContext)
		LOGGER.debug("Post response Status: " + response.getStatusLine())

		return response
	}

	/**
	 * Attach the payload to the request
	 */
	private void attachPayload(HttpEntityEnclosingRequest request, Map payload){
		List <NameValuePair> nvps = new ArrayList <NameValuePair>()
		payload.each{key, value ->
			nvps << new BasicNameValuePair(key, value)
		}
		request.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8))
	}

	/**
	 * Attach the payload to the request
	 */
	private void attachPayload(HttpEntityEnclosingRequest request, String payload){
		request.setEntity(new StringEntity(payload, HTTP.UTF_8))
	}

	private String extractResult(HttpResponse response){
		HttpEntity entity = response.getEntity()
		String result = entity.getContent().getText()
		LOGGER.debug(result)
		EntityUtils.consume(response.getEntity())
		return result
	}

	/**
	 * Authenticate with a spring security application
	 */
	public void authenticate(String username, String password){
		//construct the url to authenticate against
		String authUrl = baseUrl + "j_spring_security_check"

		HttpResponse response = basePost(authUrl, ["j_username":username, "j_password":password], "application/x-www-form-urlencoded")

		//if we are told to redirect afterwards, the authentication was probably successful
		if(response.toString().contains("login_error=1")){
			throw new Exception("Failed to Authenticate")
		}else{
			authenticated = true
			LOGGER.debug("Successfully Authenticated")
		}
		EntityUtils.consume(response.getEntity())
	}

	/**
	 * Create a connection to the application
	 */
	private void createConnection(){
		LOGGER.debug("Creating Connection")

		//instantiate the the context / provides storage for the session cookie
		httpContext = new BasicHttpContext()
		cookieStore = new BasicCookieStore()
		httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore)

		//The host to connect
		httpHost = new HttpHost("localhost", 8080)

		httpClient = new DefaultHttpClient()

		//setup an http proxy if enabled
		if(useProxy) httpClient.getParams().setParameter(
		ConnRoutePNames.DEFAULT_PROXY,
		new HttpHost(proxySettings.host, proxySettings.port))
	}

	/**
	 * Print the cookies to the debug logger
	 */
	private void printCookies(){
		LOGGER.debug(cookieStore.getCookies().toString())
	}

	/**
	 * pretty print json to the info logger
	 * @param json
	 */
	public void formatAndPrintJson(String json){
		if(json){
			LOGGER.info(groovy.json.JsonOutput.prettyPrint(json))
		}else{
			LOGGER.info("null result")
		}
	}
}
