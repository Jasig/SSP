package edu.sinclair.ssp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

/**
 * <tt>AuthenticationSuccessHandler</tt> determines which home page the user should be routed
 * to based upon the {@link GrantedAuthority}'s (roles) they are assigned.
 * 
 * Logs the timestamp of login.
 *
 * @author Alexander A. leader
 */
public class SspAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	
	private Logger logger = LoggerFactory.getLogger(SspAuthenticationSuccessHandler.class);
	
	@Override
	@Transactional(readOnly = false)
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
		super.onAuthenticationSuccess(request, response, authentication);
		
	}

}
