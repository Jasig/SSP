package edu.sinclair.ssp.config;

import org.springframework.beans.factory.annotation.Value;

/**
 * This class will pass through specific Configuration properties to the View Layer
 * @author daniel
 *
 * #suboptimal - This class could definitely use some additional research, but at least is works for now.
 */

public class WebProperties {
	
	/**
	 * Create singleton
	 */
	private WebProperties(){}
	
	private static WebProperties props;
	
	@Value("#{configProperties.app_title}") 
	private String appTitle;

	@Value("#{configProperties.inst_home_url}") 
	private String instHomeUrl;

	public static WebProperties getInstance(){
		if(null==props){
			props = new WebProperties();
		}
		
		return props;
	}
	
	public String getAppTitle() {
		return appTitle;
	}

	public void setAppTitle(String appTitle) {
		this.appTitle = appTitle;
	}

	public String getInstHomeUrl() {
		return instHomeUrl;
	}

	public void setInstHomeUrl(String instHomeUrl) {
		this.instHomeUrl = instHomeUrl;
	}

}
