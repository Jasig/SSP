package org.jasig.ssp.security;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.portlet.PortletException;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.filter.FilterChain;
import javax.portlet.filter.FilterConfig;
import javax.portlet.filter.RenderFilter;
import javax.servlet.Filter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/**
 *This Security Filter, for use with Spring Security, implements both 
 *{@link RenderFilter} (from the Portlet world) and {@link Filter} (from the 
 *Servlet world).  One instance each will be defined in web.xml and portlet.xml.
 */
public final class SspSecurityFilter implements RenderFilter, Filter {

	private static final String AUTHENTICATION_TOKEN_KEY = 
			SspSecurityFilter.class.getName() + ".GRANTED_AUTHORITIES_KEY";
	private static final Logger LOGGER = LoggerFactory
			.getLogger(SspSecurityFilter.class);

	@Override
	public void init(final FilterConfig arg0) throws PortletException {
		// nothing to do
	}

	@Override
	public void init(javax.servlet.FilterConfig arg0) throws ServletException {
		// nothing to do
	}

	@Override
	public void destroy() {
		// nothing to do
	}

	@Override
	public void doFilter(final RenderRequest req, final RenderResponse res,
			final FilterChain chain) throws IOException, PortletException {

		final String principal = req.getRemoteUser();
		if (principal != null) {  

			// User is authenticated
			final PortletSession session = req.getPortletSession();
			@SuppressWarnings("unchecked")
			Set<GrantedAuthority> authorities = (Set<GrantedAuthority>) 
					session.getAttribute(AUTHENTICATION_TOKEN_KEY);
			
			if (authorities == null) {  
				
				// But the user's access has not yet been established...
				authorities = new HashSet<GrantedAuthority>();
				for (AccessType y : AccessType.values()) {
					if (req.isUserInRole(y.getRoleName())) {
						authorities.add(new GrantedAuthorityImpl(y.getRoleName()));
					}
				}
				for (Role r : Role.values()) {
					if (req.isUserInRole(r.getRoleName())) {
						authorities.add(new GrantedAuthorityImpl(r.getRoleName()));
					}
				}
				
				/*
				 *  TODO:  Remove this code after the real security setup is 
				 *  implemented;  ROLE_USER is a placeholder. 
				 */
				if (authorities.size() != 0) {
					authorities.add(new GrantedAuthorityImpl("ROLE_USER"));
				}

				LOGGER.debug("Setting up GrantedAutorities for user '{}' -- {}",
						principal, authorities.toString());
				
				final PreAuthenticatedAuthenticationToken token = 
						new PreAuthenticatedAuthenticationToken(
								principal, 
								"credentials", 
								Collections.unmodifiableSet(authorities));

				session.setAttribute(AUTHENTICATION_TOKEN_KEY, token, 
								PortletSession.APPLICATION_SCOPE);	

			}

		}

		chain.doFilter(req, res);

	}

	@Override
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final javax.servlet.FilterChain chain) throws IOException, 
			ServletException {
		
		// Cast to httpr
		final HttpServletRequest httpr = (HttpServletRequest) req;
		final HttpSession session = httpr.getSession();

		if (session != null) {

			final AbstractAuthenticationToken token = 
					(AbstractAuthenticationToken) session.getAttribute(AUTHENTICATION_TOKEN_KEY);

			if (token != null) {

				LOGGER.trace("Found authentication token for user '{}'",
						token.getPrincipal());
				
				final SecurityContext ctx = SecurityContextHolder.getContext();
				ctx.setAuthentication(token);
				
			}
			
		}
		
		chain.doFilter(req, res);
		
	}

}
