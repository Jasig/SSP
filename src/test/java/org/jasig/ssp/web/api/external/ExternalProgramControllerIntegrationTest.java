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
package org.jasig.ssp.web.api.external; // NOPMD

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEmpty;
import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.Iterator;

import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.external.ExternalCourseTO;
import org.jasig.ssp.transferobject.external.ExternalCourseTermResponseTO;
import org.jasig.ssp.transferobject.external.ExternalProgramTO;
import org.jasig.ssp.transferobject.external.TermTO;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link TermController} tests
 * 
 * @author daniel.bower
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../ControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public class ExternalProgramControllerIntegrationTest {

	@Autowired
	private transient ExternalProgramController controller;

	public void testControllerGet() throws ObjectNotFoundException,
			ValidationException {
		assertNotNull(
				"Controller under test was not initialized by the container correctly.",
				controller);

		final ExternalProgramTO obj = controller.get("MATH");

		assertNotNull(
				"Returned TermTO from the controller should not have been null.",
				obj);

		assertEquals("Returned Course.title did not match.", "MATH",
				obj.getName());
	}
	
	public void testControllerAll() {
		final Collection<ExternalProgramTO> list = controller.getAllPrograms();

		assertNotNull("List should not have been null.", list);
		assertNotEmpty("List action should have returned some objects.", list);
	}
	
}