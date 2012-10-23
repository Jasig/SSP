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
package org.jasig.ssp.security;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.jasig.ssp.service.impl.LdapPersonAttributesService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("security-testConfig.xml")
public class LdapPersonAttributesServiceTest {

	@Autowired
	private transient LdapPersonAttributesService ldapPersonAttributesService;

	@Ignore
	@Test
	public void test() {
		assertEquals(
				"Unable to access telephonenumber property in ldap for testuser",
				"111 867-5309",
				ldapPersonAttributesService.getAttributes("testuser")
						.getPhone());
	}

	@Ignore
	@Test
	public void getCoaches() {
		final Collection<String> coaches = ldapPersonAttributesService
				.getCoaches();
		assertEquals(2, coaches.size());
	}
}
