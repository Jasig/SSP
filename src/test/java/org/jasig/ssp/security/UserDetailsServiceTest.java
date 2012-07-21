package org.jasig.ssp.security;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.SecurityService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("security-testConfig.xml")
@TransactionConfiguration()
@Transactional
public class UserDetailsServiceTest {

	@Autowired
	private transient SecurityService securityService;

	@Autowired
	private transient SspUserDetailsService userDetailsService;

	@Autowired
	private transient PersonDao personDao;

	@Autowired
	private transient PersonService personService;

	@Before
	public void setUp() {
		SecurityContextHolder.clearContext();
	}

	@Test
	public void loadUserDetailsForKen() {
		final Authentication token = new UsernamePasswordAuthenticationToken(
				"ken", "password");
		final SspUser user = (SspUser) userDetailsService
				.loadUserDetails(token);
		assertNotNull(user);
		assertEquals("ken", user.getUsername());
	}

	@Test
	public void loadUserDetailsForNewUser() throws ObjectNotFoundException {
		final String username = "adaLovelace";
		final PersonAttributesResult adaAttribs = new PersonAttributesResult(
				"adaLovelace", "Ada", "Lovelace", "ada@lovelace.com",
				"112-358-1321");

		final PersonAttributesService personAttributesService =
				createMock(PersonAttributesService.class);
		personService.setPersonAttributesService(personAttributesService);
		expect(personAttributesService.getAttributes(username)).andReturn(
				adaAttribs);
		replay(personAttributesService);

		final Authentication token = new UsernamePasswordAuthenticationToken(
				username, "password");
		final SspUser user = (SspUser) userDetailsService
				.loadUserDetails(token);
		assertNotNull(user);
		assertEquals(username, user.getUsername());

		personDao.delete(user.getPerson());
	}

}
