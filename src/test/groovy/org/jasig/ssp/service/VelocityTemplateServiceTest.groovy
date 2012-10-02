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
package org.jasig.ssp.service
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("service-testConfig.xml")
@TransactionConfiguration
class VelocityTemplateServiceTest {

	@Autowired
	private VelocityTemplateService service;

	@Test
	public void testGenerateContentFromTemplate(){
		assert("Hello Velocity" == service.generateContentFromTemplate('Hello $val', "test-template", ["val":"Velocity"]))

		//template already loaded with this id, so, you could get something a little different from what you expected...
		assert("Hello Velocity" == service.generateContentFromTemplate('Hello $val in the World', "test-template", ["val":"Velocity"]))
	}
}