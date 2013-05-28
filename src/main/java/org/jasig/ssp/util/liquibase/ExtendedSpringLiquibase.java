/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.util.liquibase;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import liquibase.Liquibase;
import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;
import liquibase.logging.LogFactory;

/**
 * Restores support for the <code>executeEnabled</code> property which
 * is still referenced by the {@link SpringLiquibase} javadoc but which is
 * no longer implemented. Setting that value to <code>false</code> will
 * short-circuit execution on initialization. If set, the
 * @{link Liquibase#SHOULD_RUN_SYSTEM_PROPERTY} system property overrides this
 * property.
 */
public class ExtendedSpringLiquibase extends SpringLiquibase {

	private boolean executeEnabled = true;

	@Autowired
	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws LiquibaseException {
		if ( (applicationContext instanceof ConfigurableApplicationContext)
				&& !(((ConfigurableApplicationContext)applicationContext).isActive()) ) {
			// this is an attempt to work around accidential re-initialization
			// by background threads during shutdown. SSP-1273
			LogFactory.getLogger().info("Liquibase did not run because " +
					"the current ApplicationContext is shut down is or shutting down.");
			return;
		}
		String shouldRunProperty =
				System.getProperty(Liquibase.SHOULD_RUN_SYSTEM_PROPERTY);
		if (shouldRunProperty != null){
			super.afterPropertiesSet();
			return;
		}
		if ( !isExecuteEnabled() ) {
			LogFactory.getLogger().info("Liquibase did not run because " +
					"the 'executeEnabled' property was set to false");
			return;
		}
		super.afterPropertiesSet();
	}

	/**
	 * See class javadoc.
	 *
	 * @return
	 */
	public boolean isExecuteEnabled() {
		return executeEnabled;
	}

	/**
	 * See class javadoc.
	 *
	 * @param executeEnabled
	 */
	public void setExecuteEnabled(boolean executeEnabled) {
		this.executeEnabled = executeEnabled;
	}
}
