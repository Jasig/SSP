/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
