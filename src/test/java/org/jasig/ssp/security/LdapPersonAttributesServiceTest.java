package org.jasig.ssp.security;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.jasig.ssp.service.impl.LdapPersonAttributesService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("security-testConfig.xml")
public class LdapPersonAttributesServiceTest {

	@Autowired
	private transient LdapPersonAttributesService ldapPersonAttributesService;

	@Ignore
	@Test
	public void test() {
		assertEquals(
				"Unable to access telephonenumber property in ldap for testuser",
				"111 867-5309",
				ldapPersonAttributesService.getAttributes("testuser")
						.getPhone());
	}

	@Ignore
	@Test
	public void getCoaches() {
		final Collection<String> coaches = ldapPersonAttributesService
				.getCoaches();
		assertEquals(2, coaches.size());
	}
}
