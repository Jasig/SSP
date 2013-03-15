package org.jasig.ssp.security;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.ssp.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Makes sure every web request makes an attempt to lookup the current
 * user before anything else happens. This is a workaround for
 * the <a href="https://issues.jasig.org/browse/SSP-854">SSP-854</a> fix.
 *
 * <p>That fix made the current {@link org.jasig.ssp.model.Person} a thread-
 * local property of the current request. There was no longer any chance
 * of accidentally re-using a <code>Person</code> from another
 * Hibernate <code>Session</code>. This meant, though, that the code path
 * for lazy <code>Person</code> lookups changed. It used to be that
 * the web session already had a <code>Person</code>, you'd go through
 * an ID-based lookup, but now we *always* go through a username-based
 * lookup, which appears to have slightly different flush behaviors
 * (<a href="https://github.com/Jasig/SSP/commit/0b5a575551f17e60261457337be3c5868399a07b">0b5a575</a>)</p>
 *
 * <p>So the we ran into cases where the current user would not be looked up
 * until Hibernate was in the midst of a flush, e.g. when handling an API call
 * to create an <code>EarlyAlertResponse</code>. The flush would trigger
 * a call to {@link org.jasig.ssp.dao.AuditableEntityInterceptor}, which
 * would then try to look up the current user via
 * {@link org.jasig.ssp.service.SecurityService#currentUser()}. If that call
 * hadn't been made earlier in the web request, it would try to lazily populate
 * <code>SspUser#person</code> via
 * {@link org.jasig.ssp.service.PersonService#personFromUsername(String)}. This
 * would trigger another Hibernate flush (recall that accesses to the Hibernate
 * Session in a Hibernate Interceptor are strongly discouraged), which would
 * trigger another {@link org.jasig.ssp.service.SecurityService#currentUser()}
 * and eventually you'd get a <code>StackOverflowException</code>.</p>
 *
 * <p>So the idea now is to try as hard as possible to make sure the Hibernate
 * Interceptor isn't going to need to go back to the db to do its work (i.e.,
 * in this case, to lookup a <code>Person</code>)</p>.
 */
public class SspCurrentUserInterceptor extends HandlerInterceptorAdapter {
	@Autowired
	private SecurityService securityService;

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws Exception {
		securityService.currentUser();
		return true;
	}
}
