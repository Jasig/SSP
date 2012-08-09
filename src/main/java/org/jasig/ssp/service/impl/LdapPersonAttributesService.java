package org.jasig.ssp.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.security.PersonAttributesResult;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.PersonAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;

import com.google.common.collect.Lists;

public class LdapPersonAttributesService implements PersonAttributesService {

	@Autowired
	private transient LdapTemplate ldapTemplate;

	@Override
	public PersonAttributesResult getAttributes(
			final HttpServletRequest req,
			final HttpServletResponse res,
			final String username)
			throws ObjectNotFoundException {

		return getAttributes(username);

	}

	@Override
	public PersonAttributesResult getAttributes(final String username) {
		return propertiesForDn(username, buildDn(username));
	}

	@Override
	public PersonAttributesResult getAttributes(final String username,
			final PortletRequest portletRequest) {
		return getAttributes(username);
	}

	private String buildDn(final String userId) {
		return "uid=" + userId + ",ou=users";
	}

	@SuppressWarnings("unchecked")
	protected PersonAttributesResult propertiesForDn(final String username,
			final String baseDn) {
		final List<PersonAttributesResult> vals = ldapTemplate.search(
				baseDn, "(objectclass=person)",
				new AttributesMapper() {
					@Override
					public PersonAttributesResult mapFromAttributes(
							final Attributes attrs) throws NamingException {

						final PersonAttributesResult result = new PersonAttributesResult();
						result.setFirstName(extractProperty(attrs, "cn"));
						result.setLastName(extractProperty(attrs, "sn"));
						result.setPrimaryEmailAddress(extractProperty(attrs,
								"mail"));
						result.setSchoolId(username);
						result.setPhone(extractProperty(attrs,
								"telephonenumber"));
						return result;
					}
				});

		if (vals.isEmpty()) {
			return null;
		} else {
			return vals.get(0);
		}
	}

	private String extractProperty(final Attributes attrs, final String property) {
		final Attribute attrib = attrs.get(property);
		if (null == attrib) {
			return null;
		} else {
			Object val;
			try {
				val = attrib.get();
			} catch (NamingException e) {
				return null;
			}

			try {
				return (String) val;
			} catch (ClassCastException cce) {
				return val.toString();
			}
		}
	}

	@Override
	public Collection<String> getCoaches() {
		final String groupId = "COACH";

		final Collection<String> coaches = Lists.newArrayList();

		ldapTemplate.search(
				"cn=" + groupId + ",ou=groups", "uniqueMember=*",
				new ContextMapper() {
					@Override
					public Object mapFromContext(final Object context) {
						final DirContextAdapter adapter = (DirContextAdapter) context;

						for (String val : adapter
								.getStringAttributes("uniqueMember")) {
							coaches.add(extractUsername(val));
						}
						return null;
					}
				});

		return coaches;
	}

	private String extractUsername(final String val) {
		// cn=testuser1,ou=users,dc=springframework,dc=org
		return val.substring(3, (val.length() - 35));
	}

	@Override
	public List<Map<String, Object>> searchForUsers(
			final HttpServletRequest req, final HttpServletResponse res,
			final Map<String, String> query) {
		throw new NotImplementedException();
	}
}
