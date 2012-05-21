/**
 * 
 */
package org.jasig.ssp.util.sort;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.collections.Pair;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

/**
 * @author jon.adams
 * 
 */
public class SortingAndPagingTests {
	private transient SortingAndPaging testObjActive;

	private transient SortingAndPaging testObjAll;

	private transient SortingAndPaging testObjFull;

	private transient SortingAndPaging testObjStatusPagingOnly;

	@Before
	public void setUp() {
		testObjActive = new SortingAndPaging(ObjectStatus.ACTIVE);
		testObjAll = new SortingAndPaging(ObjectStatus.ALL);
		testObjStatusPagingOnly = new SortingAndPaging(ObjectStatus.ACTIVE, 0,
				10,
				new ArrayList<Pair<String, SortDirection>>(),
				"name", SortDirection.ASC);

		final List<Pair<String, SortDirection>> sortFields = Lists
				.newArrayList();
		sortFields.add(new Pair<String, SortDirection>("name",
				SortDirection.ASC));
		testObjFull = new SortingAndPaging(ObjectStatus.ACTIVE, 0, 10,
				sortFields, "name", SortDirection.ASC);
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
		assertEquals("GetStatus() did not match.", ObjectStatus.ACTIVE,
				testObjActive.getStatus());
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.util.sort.SortingAndPaging#isPaged()}.
	 */
	@Test
	public void testIsPaged() {
		assertTrue("Use of paging filters should have been flagged as Paged",
				testObjFull.isPaged());
		assertFalse(
				"Use of ObjectStatus only should not have been flagged as Paged",
				testObjAll.isPaged());
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.util.sort.SortingAndPaging#isSorted()}.
	 */
	@Test
	public void testIsSorted() {
		assertTrue("Use of sorting filters should have been flagged as Sorted",
				testObjFull.isSorted());
		assertFalse(
				"Use of empty sorting filters should not have been flagged as Sorted",
				testObjStatusPagingOnly.isSorted());
		assertFalse(
				"Use of ObjectStatus only should not have been flagged as Sorted",
				testObjAll.isSorted());
	}

	/**
	 * Test method for
	 * {@link org.jasig.ssp.util.sort.SortingAndPaging#isDefaultSorted()}.
	 */
	@Test
	public void testIsDefaultSorted() {
		assertTrue(
				"Use of default sorting filters should have been flagged as DefaultSorted",
				testObjFull.isDefaultSorted());
		assertFalse(
				"Use of ObjectStatus only should not have been flagged as DefaultSorted",
				testObjAll.isDefaultSorted());
	}
}