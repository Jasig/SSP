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