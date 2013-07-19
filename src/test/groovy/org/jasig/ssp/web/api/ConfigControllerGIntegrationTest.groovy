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
package org.jasig.ssp.web.api

import org.hibernate.SessionFactory
import org.jasig.ssp.model.ObjectStatus
import org.jasig.ssp.model.Person
import org.jasig.ssp.security.permissions.Permission
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment
import org.jasig.ssp.service.reference.ConfigService
import org.jasig.ssp.web.api.reference.ConfigController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.transaction.TransactionConfiguration
import org.springframework.transaction.annotation.Transactional
import spock.lang.Specification

/**
 * Depends on org/jasig/ssp/database/changesets/000061.xml having been run.
 */
@ContextConfiguration("../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
class ConfigControllerGIntegrationTest extends Specification {

	@Autowired
	ConfigController configController

	@Autowired
	ConfigService configService

	@Autowired
	SecurityServiceInTestEnvironment securityService;

	@Autowired
	SessionFactory sessionFactory;

	def cleanup() {
		securityService.setCurrent(new Person())
	}

	def "trusted IP list obfuscated when retrieved by name without write permission"() {
		given: "a current user with read permissions only"
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				Permission.REFERENCE_READ);

		and: "a configured trusted IP list"
		// service doesn't implement permission checks, so this works
		def config = configService.getByName("highly_trusted_ips")
		config.setValue("127.0.0.1");
		config = configService.save(config)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "that user tries to read the trusted IP list by name"
		def configTO = configController.getByName("highly_trusted_ips");

		then: "the trusted IP list is obfuscated"
		configTO.value == "RESTRICTED"
	}

	def "trusted IP list obfuscated when retrieved by ID without write permission"() {
		given: "a current user with read permissions only"
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				Permission.REFERENCE_READ);

		and: "a configured trusted IP list"
		// service doesn't implement permission checks, so this works
		def config = configService.getByName("highly_trusted_ips")
		config.setValue("127.0.0.1");
		config = configService.save(config)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "that user tries to read the trusted IP list by ID"
		def configTO = configController.get(UUID.fromString("68181d51-ee71-11e2-a87d-406c8f22c3ce"))

		then: "the trusted IP list is obfuscated"
		configTO.value == "RESTRICTED"
	}

	def "trusted IP list obfuscated when retrieved by list without write permission"() {
		given: "a current user with read permissions only"
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				Permission.REFERENCE_READ);

		and: "a configured trusted IP list"
		// service doesn't implement permission checks, so this works
		def config = configService.getByName("highly_trusted_ips")
		config.setValue("127.0.0.1");
		config = configService.save(config)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "that user tries to read the trusted IP list by ID"
		def allConfig = configController.getAll(ObjectStatus.ALL, 0, -1, null, null);
		def foundConfigRecord = false;

		then: "the trusted IP list is obfuscated"
		allConfig.rows.each { it ->
			if ( it.name == "highly_trusted_ips" ) {
				foundConfigRecord = true;
				assert it.value == "RESTRICTED"
			}
		}
		foundConfigRecord
	}

	def "trusted IP list returned verbatim when retrieved by name with write permission"() {
		given: "a current user with read permissions only"
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				Permission.REFERENCE_READ, Permission.REFERENCE_WRITE);

		and: "a configured trusted IP list"
		// service doesn't implement permission checks, so this works
		def config = configService.getByName("highly_trusted_ips")
		config.setValue("127.0.0.1");
		config = configService.save(config)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "that user tries to read the trusted IP list by name"
		def configTO = configController.getByName("highly_trusted_ips");

		then: "the trusted IP list is obfuscated"
		configTO.value == "127.0.0.1"
	}

	def "trusted IP list returned verbatim when retrieved by ID with write permission"() {
		given: "a current user with read permissions only"
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				Permission.REFERENCE_READ, Permission.REFERENCE_WRITE);

		and: "a configured trusted IP list"
		// service doesn't implement permission checks, so this works
		def config = configService.getByName("highly_trusted_ips")
		config.setValue("127.0.0.1");
		config = configService.save(config)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "that user tries to read the trusted IP list by ID"
		def configTO = configController.get(UUID.fromString("68181d51-ee71-11e2-a87d-406c8f22c3ce"))

		then: "the trusted IP list is obfuscated"
		configTO.value == "127.0.0.1"
	}

	def "trusted IP list returned verbatim when retrieved by list with write permission"() {
		given: "a current user with read permissions only"
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID),
				Permission.REFERENCE_READ, Permission.REFERENCE_WRITE);

		and: "a configured trusted IP list"
		// service doesn't implement permission checks, so this works
		def config = configService.getByName("highly_trusted_ips")
		config.setValue("127.0.0.1");
		config = configService.save(config)

		and: "the hib session is flushed for good measure"
		sessionFactory.currentSession.flush()

		when: "that user tries to read the trusted IP list by ID"
		def allConfig = configController.getAll(ObjectStatus.ALL, 0, -1, null, null);
		def foundConfigRecord = false;

		then: "the trusted IP list is obfuscated"
		allConfig.rows.each { it ->
			if ( it.name == "highly_trusted_ips" ) {
				foundConfigRecord = true;
				assert it.value == "127.0.0.1"
			}
		}
		foundConfigRecord
	}
}
