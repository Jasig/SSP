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
package org.jasig.ssp.util.sort;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Test the {@link SortDirection} utility class.
 * 
 * @author jon.adams
 * 
 */
public class SortDirectionTest {

	/**
	 * Test method for
	 * {@link org.jasig.ssp.util.sort.SortDirection#getSortDirection(java.lang.String)}
	 */
	@Test
	public void testGetSortDirection() {
		assertEquals("ASC Sort direction did not match.", SortDirection.ASC,
				SortDirection.getSortDirection("ASC"));
		assertEquals("DESC Sort direction did not match.", SortDirection.DESC,
				SortDirection.getSortDirection("DESC"));
		assertEquals("deSC (mixed-case) sort direction did not match.",
				SortDirection.DESC,
				SortDirection.getSortDirection("deSC"));
	}

	/**
	 * Test method for invalid calls to
	 * {@link org.jasig.ssp.util.sort.SortDirection#getSortDirection(java.lang.String)}
	 */
	@Test
	public void testGetSortDirectionInvalidParameters() {
		assertEquals(
				"Sort direction should have returned ASC for an invalid string.",
				SortDirection.ASC, SortDirection.getSortDirection("invalid"));
		assertEquals(
				"Sort direction should have returned ASC for null parameters.",
				SortDirection.ASC, SortDirection.getSortDirection(null));
		assertEquals(
				"Sort direction should have returned ASC for full \"DESCENDING\" string since it is not the expected \"DESC\" format.",
				SortDirection.ASC, SortDirection.getSortDirection("DESCENDING"));
	}
}