package org.jasig.ssp.service.security.oauth2.impl;


import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.SspUserDetailsService;
import org.jasig.ssp.service.security.oauth2.OAuth2ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Maps {@link OAuth2Client} instances to {@link SspUser} instances to satisfy
 * the {@link UserDetailsService} interface. That is, it makes it appear to
 * SpringSecurity as if an authenticated {@code ClientDetails} can be treated
 * just like any other {@link UserDetails}.
 */
@Service("oauth2ClientDetailsUserService")
@Transactional
public class SspUserOauth2ClientDetailsUserServiceImpl implements UserDetailsService {

	@Autowired
	private SspUserDetailsService sspUserDetailsService;

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final ClientDetails clientDetails = clientDetailsService.loadClientByClientId(username);
		if ( clientDetails == null ) {
			throw new UsernameNotFoundException("Found no OAuth2 client " +
					" with client ID [" + username + "]");
		}

		// Careful! this call will try to create a Person record if it
		// can't find one. That's one reason for first calling
		// ClientDetailsService.loadClientByClientid() first above.
		final SspUser origUserDetails =
				(SspUser)
				sspUserDetailsService.loadUserDetails(clientDetails.getClientId(),
						clientDetails.getAuthorities());

		// We need the "real" password in order for subsequent BasicAuth
		// processing to work, but that field can't be set after construction,
		// so just take a copy. Seemed to make more sense than getting into the
		// business of adding more use-case specific overloads of
		// loadUserDetails() or changing the SspUser interface to allow
		// password setting, which isn't really in the spirit of the
		// SpringSecurity classes from which it inherits. Another option
		// would have been just one overload with a Map of property overrides,
		// but that would be overkill for the one field use case we have here
		// for overriding password.
		SspUser overlaidUserDetails = new SspUser(origUserDetails.getUsername(),
				clientDetails.getClientSecret(),
				origUserDetails.getPerson().getObjectStatus() == ObjectStatus.ACTIVE,
				origUserDetails.isAccountNonExpired(),
				origUserDetails.isCredentialsNonExpired(),
				origUserDetails.isAccountNonLocked(),
				origUserDetails.getAuthorities());
		overlaidUserDetails.setEmailAddress(origUserDetails.getEmailAddress());
		overlaidUserDetails.setPerson(origUserDetails.getPerson());
		return overlaidUserDetails;
	}

}
