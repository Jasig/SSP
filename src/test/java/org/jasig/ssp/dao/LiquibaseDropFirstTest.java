package org.jasig.ssp.dao;

import static org.junit.Assert.fail;

import javax.sql.DataSource;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

import org.junit.Test;
import org.junit.runner.RunWith;
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

	@Autowired
	private transient DataSource dataSource;

	@Autowired
	private transient ResourceLoader resourceLoader;

	@Test
	public void test() {
		// first run the prod master changelog
		SpringLiquibase sl = newSpringLiquibase();
		sl.setChangeLog("classpath:org/jasig/ssp/database/masterChangeLog.xml");

		try {
			sl.afterPropertiesSet();
		} catch (LiquibaseException e) {
			fail("Failed to run Test changesets");
		}

		// now run the test master changelog
		sl = newSpringLiquibase();
		sl.setChangeLog("classpath:org/jasig/ssp/database/masterChangeLog-test.xml");

		try {
			sl.afterPropertiesSet();
		} catch (LiquibaseException e) {
			fail("Failed to run Test changesets");
		}
	}

	private SpringLiquibase newSpringLiquibase() {
		final SpringLiquibase sl = new SpringLiquibase();
		sl.setDataSource(dataSource);
		sl.setResourceLoader(resourceLoader);
		sl.setDropFirst(true);
		return sl;
	}

}
