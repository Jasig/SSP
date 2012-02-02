package edu.sinclair.ssp.config;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Simple utility listener to load certain properties before Spring Starts up.
 * 
 * Add this entry to your web.xml:
 * <pre><listener>
    <listener-class>edu.sinclair.ssp.config.ExternalConfigLoaderContextListener</listener-class>
  </listener></pre>
 * 
 * @author daniel
 *
 */
public class ExternalConfigLoaderContextListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try{
			new LogBackConfigLoader("/usr/local/etc/ssp/logback.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {		
	}
}
