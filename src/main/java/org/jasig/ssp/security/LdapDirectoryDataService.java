package org.jasig.ssp.security;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.apache.commons.lang.NotImplementedException;
import org.jasig.ssp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

public class LdapDirectoryDataService implements DirectoryDataService {

	@Autowired
	private transient LdapTemplate ldapTemplate;

	@Override
	public String getProperty(final String propertyName, final Person user) {
		throw new NotImplementedException();
	}

	@Override
	public String getProperty(final String propertyName, final String userId) {
		if ("schoolId".equals(propertyName)) {
			return userId;
		} else if ("firstName".equals(propertyName)) {
			return "Gen";
		} else if ("lastName".equals(propertyName)) {
			return "Temporary";
		} else if ("primaryEmailAddress".equals(propertyName)) {
			return "unknown@test.com";
		} else {
			return "unknown property";
		}
	}

	@SuppressWarnings("unchecked")
	protected String propertyForDn(final String propertyName,
			final String baseDn) {
		final List<String> vals = ldapTemplate.search(
				baseDn, "(objectclass=person)",
				new AttributesMapper() {
					@Override
					public Object mapFromAttributes(final Attributes attrs)
							throws NamingException {
						final Attribute attrib = attrs.get(propertyName);
						if (null == attrib) {
							return null;
						} else {
							return attrib.get();
						}
					}
				});

		if (vals.isEmpty()) {
			return null;
		} else {
			return vals.get(0);
		}
	}

	private String buildDn(final String userId) {
		return "uid=" + userId + ",ou=users";
	}

	@Override
	public String getFirstNameForUserId(final String userId) {
		return propertyForDn("cn", buildDn(userId));
	}

	@Override
	public String getLastNameForUserId(final String userId) {
		return propertyForDn("sn", buildDn(userId));
	}

	@Override
	public String getPrimaryEmailAddressForUserId(final String userId) {
		return propertyForDn("mail", buildDn(userId));
	}

	@Override
	public String getPhoneForUserId(final String userId) {
		return propertyForDn("telephonenumber", buildDn(userId));
	}

}
