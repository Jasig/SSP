package org.jasig.ssp.service.security.oauth2.impl;


import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.security.SspUserDetailsService;
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

@Service
@Transactional
public class SspOauth2ClientServiceImpl extends ClientDetailsUserDetailsService {

	@Autowired
	private SspUserDetailsService sspUserDetailsService;

	@Autowired
	public SspOauth2ClientServiceImpl(ClientDetailsService clientDetailsService) {
		super(clientDetailsService);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final UserDetails userDetails = super.loadUserByUsername(username);
		final String personUsername = resolvePersonUsernameFromClient(userDetails);
		final UserDetails sspUserDetails =
				sspUserDetailsService.loadUserDetails(personUsername, userDetails.getAuthorities());
		// We need the "real" password, but can't be set after construction, so
		// just take a copy
		return new SspUser(sspUserDetails.getUsername(),
				userDetails.getPassword(),
				sspUserDetails.isEnabled(),
				sspUserDetails.isAccountNonExpired(),
				sspUserDetails.isCredentialsNonExpired(),
				sspUserDetails.isAccountNonLocked(),
				sspUserDetails.getAuthorities());
	}

	private String resolvePersonUsernameFromClient(UserDetails userDetails) {
		return "cgarciaadv1";
	}
}
