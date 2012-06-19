package org.jasig.ssp.security;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("security-testConfig.xml")
public class LdapDirectoryDataServiceTest {

	@Autowired
	private transient LdapDirectoryDataService ldapDirectoryDataService;

	@Test
	public void test() {
		assertTrue(
				"Unable to access telephonenumber property in ldap for testuser",
				ldapDirectoryDataService.propertyForDn(
						"telephonenumber", "cn=testuser,ou=users").contains(
						"111 867-5309"));
	}

}
