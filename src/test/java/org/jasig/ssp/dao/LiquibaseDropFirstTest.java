package org.jasig.ssp.dao;

import static org.junit.Assert.fail;

import javax.sql.DataSource;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

import org.junit.Ignore;
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

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LiquibaseDropFirstTest.class);

	@Autowired
	private transient DataSource dataSource;

	@Autowired
	private transient ResourceLoader resourceLoader;

	@Ignore(value = "Useful for development. But until a better process is ready, since it is desctructive, disable this test so it isn't accidentally run on a production database.")
	@Test
	public void testProductionScripts() {
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

	@Ignore(value = "Useful for development. But until a better process is ready, since it is desctructive, disable this test so it isn't accidentally run on a production database.")
	@Test
	public void testProductionAndTestScripts() {
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