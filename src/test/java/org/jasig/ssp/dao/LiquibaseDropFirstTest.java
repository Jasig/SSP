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
package org.jasig.ssp.dao;

import static org.junit.Assert.fail;

import java.util.Properties;

import javax.sql.DataSource;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * This test will make sure that the changelogs work from scratch in both test
 * and prod environments
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("dao-dropFirst-testConfig.xml")
public class LiquibaseDropFirstTest {

	/**
	 * Add this key to ssp-config.properties with a value of false to drop the
	 * database and run the production changelog. Otherwise the test will be
	 * skipped.
	 */
	public static final String RUN_PRODUCTION_CHANGELOG = "test.liquibase_drop_first.skip_production_changelog";

	/**
	 * Add this key to ssp-config.properties with a value of false to drop the
	 * database and run the test changelog. Otherwise the test will be skipped.
	 */
	public static final String RUN_TEST_CHANGELOG = "test.liquibase_drop_first.skip_test_changelog";

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LiquibaseDropFirstTest.class);

	@Autowired
	private transient Properties configProperties;

	@Autowired
	private transient DataSource dataSource;

	@Autowired
	private transient ResourceLoader resourceLoader;

	private boolean skipIt(final String changeLogName) {
		final String config = configProperties.getProperty(changeLogName);

		final boolean skip = (config == null)
				|| (config.equalsIgnoreCase("true"));

		if (skip) {
			LOGGER.info("Skipping {}", changeLogName);
		}

		return skip;
	}

	@Test
	public void testProductionScripts() {
		if (skipIt(RUN_PRODUCTION_CHANGELOG)) {
			return;
		}

		// run the prod master changelog
		final SpringLiquibase sl = newSpringLiquibaseWithDropFirst();
		sl.setChangeLog("classpath:org/jasig/ssp/database/masterChangeLog.xml");

		try {
			sl.afterPropertiesSet(); // run scripts
		} catch (final LiquibaseException e) {
			LOGGER.error(
					"Failed to drop tables by liquibase scripts for production.",
					e);
			fail("Failed to run Prod changesets: " + e.getMessage());
		}
	}

	@Test
	public void testProductionAndTestScripts() {
		if (skipIt(RUN_TEST_CHANGELOG)) {
			return;
		}

		// run the test master changelog
		final SpringLiquibase sl = newSpringLiquibaseWithDropFirst();
		sl.setChangeLog("classpath:org/jasig/ssp/database/masterChangeLog-test.xml");

		try {
			sl.afterPropertiesSet(); // run scripts
		} catch (final LiquibaseException e) {
			LOGGER.error(
					"Failed to drop tables by liquibase scripts for test data.",
					e);
			fail("Failed to run Test changesets: " + e.getMessage());
		}
	}

	/**
	 * Create Spring liquibase instance with drop first setting on.
	 * 
	 * @return
	 */
	private SpringLiquibase newSpringLiquibaseWithDropFirst() {
		final SpringLiquibase sl = new SpringLiquibase();
		sl.setDataSource(dataSource);
		sl.setResourceLoader(resourceLoader);
		sl.setDropFirst(true);
		return sl;
	}
}