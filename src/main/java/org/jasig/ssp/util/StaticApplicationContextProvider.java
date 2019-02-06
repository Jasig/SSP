package org.jasig.ssp.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * This class helps non-bean objects (e.g. instances of PortletFilter) gain access to resources in
 * the <code>ApplicationContext</code>.
 *
 * @since 2.9
 */
@Component
public class StaticApplicationContextProvider {

    private static ApplicationContext singleton;

    @Autowired
    private ApplicationContext applicationContext;

    @PostConstruct
    public void init() {
        StaticApplicationContextProvider.singleton = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return singleton;
    }

}
