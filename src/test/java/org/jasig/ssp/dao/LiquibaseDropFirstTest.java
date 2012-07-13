package org.jasig.ssp.dao;

import static org.junit.Assert.fail;

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

	private static final Logger LOGGER = LoggerFactory
			.getLogger(LiquibaseDropFirstTest.class);

	@Autowired
	private transient DataSource dataSource;

	@Autowired
	private transient ResourceLoader resourceLoader;

	@Test
	public void test() {
		// first run the prod master changelog
		SpringLiquibase sl = newSpringLiquibaseWithDropFirst();
		sl.setChangeLog("classpath:org/jasig/ssp/database/masterChangeLog.xml");

		try {
			sl.afterPropertiesSet(); // run scripts
		} catch (final LiquibaseException e) {
			LOGGER.error(
					"Failed to drop tables by liquibase scripts for production.",
					e);
			fail("Failed to run Prod changesets: " + e.getMessage());
		}

		// now run the test master changelog
		sl = newSpringLiquibaseWithDropFirst();
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