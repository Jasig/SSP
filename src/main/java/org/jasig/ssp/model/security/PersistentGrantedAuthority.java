/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.model.security;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.util.Assert;

/**
 * Derived with only minor changes from {@code org.springframework.security.core.authority.SimpleGrantedAuthority}
 */
@Embeddable
public class PersistentGrantedAuthority implements GrantedAuthority {

	@NotNull
	@NotEmpty
	@Column(nullable = false, length = 255) // matches up_permission.activity
	private String authority;

	// for Hibernate
	public PersistentGrantedAuthority() {
		// nothing to do
	}

	// for app
	public PersistentGrantedAuthority(String authority) {
		Assert.hasText(authority, "A granted authority textual representation is required");
		this.authority = authority;
	}

	public String getAuthority() {
		return authority;
	}

	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (obj instanceof GrantedAuthority) {
			return getAuthority().equals(((GrantedAuthority) obj).getAuthority());
		}

		return false;
	}

	public int hashCode() {
		return this.authority.hashCode();
	}

	public String toString() {
		return this.authority;
	}
}
