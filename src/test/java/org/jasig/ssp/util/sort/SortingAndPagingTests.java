/**
 * 
 */
package org.jasig.ssp.util.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.jasig.ssp.model.ObjectStatus;
import org.junit.Before;
import org.junit.Test;

/**
 * @author jon.adams
 * 
 */
public class SortingAndPagingTests {
	private SortingAndPaging testObjActive;

	private SortingAndPaging testObjAll;

	@Before
	public void setUp() {
		testObjActive = new SortingAndPaging(ObjectStatus.ACTIVE);
		testObjAll = new SortingAndPaging(ObjectStatus.ALL);
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.util.sort.SortingAndPaging#SortingAndPaging(org.jasig.ssp.model.ObjectStatus)}
	 * .
	 */
	@Test
	public void testSortingAndPagingObjectStatus() {
		final SortingAndPaging obj = new SortingAndPaging(ObjectStatus.ACTIVE);
		assertEquals("ObjectStatus did not match.", ObjectStatus.ACTIVE,
				obj.getStatus());
		assertNull("Sort should have been null.", obj.getDefaultSortProperty());
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.util.sort.SortingAndPaging#isFilteredByStatus()}.
	 */
	@Test
	public void testIsFilteredByStatus() {
		assertTrue("Use of Active should have been filteredByStatus",
				testObjActive.isFilteredByStatus());
		assertFalse("Use of All should not have been filteredByStatus",
				testObjAll.isFilteredByStatus());
	}
}