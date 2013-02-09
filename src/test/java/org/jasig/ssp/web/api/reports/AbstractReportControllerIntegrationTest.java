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
package org.jasig.ssp.web.api.reports;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.Nullable;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.input.CharSequenceReader;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.hibernate.SessionFactory;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.service.EarlyAlertService;
import org.jasig.ssp.service.PersonService;
import org.jasig.ssp.service.impl.SecurityServiceInTestEnvironment;
import org.jasig.ssp.service.reference.CampusService;
import org.jasig.ssp.util.service.stub.StubPersonAttributesService;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import static org.hamcrest.Matchers.array;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("ReportControllerIntegrationTests-context.xml")
@TransactionConfiguration
@Transactional
public abstract class AbstractReportControllerIntegrationTest {

	@Autowired
	protected transient SecurityServiceInTestEnvironment securityService;

	@Autowired
	protected transient SessionFactory sessionFactory;

	@Autowired
	protected transient StubPersonAttributesService personAttributesService;

	@Autowired
	protected transient PersonService personService;

	@Autowired
	protected transient CampusService campusService;

	@Autowired
	protected transient EarlyAlertService earlyAlertService;

	protected transient List<String> origCoachUsernames;

	/**
	 * Setup the security service with the administrator.
	 */
	@Before
	public void setUp() {
		securityService.setCurrent(new Person(Person.SYSTEM_ADMINISTRATOR_ID));
		Collection<String> rawCoachUsernames = personAttributesService.getCoachUsernames();
		if ( rawCoachUsernames != null ) {
			origCoachUsernames = new ArrayList<String>(rawCoachUsernames);
		}
	}

	@After
	public void tearDown() {
		personAttributesService.setCoachUsernames(origCoachUsernames);
		securityService.setCurrent(new Person());
	}

	protected void expectReportBodyLines(List<String> expectedReportBodyLines,
									   MockHttpServletResponse response,
									   Predicate<String> firstBodyRowRule)
			throws UnsupportedEncodingException {
		final List<String> actualReportBodyLines = new ArrayList<String>();
		final String csvReport = response.getContentAsString();
		final LineIterator lineIterator =
				IOUtils.lineIterator(new CharSequenceReader(csvReport));
		boolean accumulatingActualBodyLines = false;
		if(firstBodyRowRule == null)
			accumulatingActualBodyLines = true;
		while ( lineIterator.hasNext() ) {
			String line = lineIterator.next();
			if ( accumulatingActualBodyLines || firstBodyRowRule.apply(line) ) {
				accumulatingActualBodyLines = true;
				actualReportBodyLines.add(line);
			}
		}

		assertStringCollectionsEqual(expectedReportBodyLines, actualReportBodyLines);
	}

	// we do this rather than a simple assertEquals() b/c the diff display
	// in an IDE is so much nicer
	protected void assertStringCollectionsEqual(Collection<String> expected,
											  Collection<String> actual) {
		String[] actualArray = actual.toArray(new String[actual.size()]);
		assertThat(actualArray, array(matchersFor(expected)));
	}

	protected Matcher<String>[] matchersFor(Collection<String> strings) {
		Matcher<String>[] matchers = new Matcher[strings.size()];
		int i = 0;
		for ( String string : strings ) {
			matchers[i++] = IsEqual.equalTo(string);
		}
		return matchers;
	}

	protected abstract Predicate<String> afterHeader();

	protected Predicate<String> afterLineContaining(final String pattern) {
		return new Predicate<String>() {

			private boolean matched;
			private final Predicate<CharSequence> patternMatcher =
					Predicates.containsPattern(pattern);

			@Override
			public boolean apply(@Nullable String input) {
				if ( matched ) {
					return true;
				}
				matched = patternMatcher.apply(input);
				return false; // this predicate matches lines occuring *after* the given pattern
			}
		};
	}
}
