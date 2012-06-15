package org.jasig.ssp.util.sort; // NOPMD tests may have lots of imports

import static org.jasig.ssp.util.assertions.SspAssert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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
		assertTrue(
				"Use of ObjectStatus only should not been flagged as Paged because of defaults.",
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

	@Test
	public void testMaxResultDefaults() {
		final SortingAndPaging defaults = new SortingAndPaging(null);
		assertNotNull("Default max should not have been null.",
				defaults.getMaxResults());
		assertEquals("Default max not set.",
				SortingAndPaging.DEFAULT_MAXIMUM_RESULTS,
				defaults.getMaxResults());
	}

	@Test
	public void testMaxAllowableValues() {
		final SortingAndPaging maxLimit = new SortingAndPaging(null, -1,
				SortingAndPaging.MAXIMUM_ALLOWABLE_RESULTS + 5, null, null,
				null);
		assertNotEquals("Default max should have been limited.",
				SortingAndPaging.MAXIMUM_ALLOWABLE_RESULTS,
				maxLimit.getMaxResults());
		assertEquals("First result should have been sanitized to 0.",
				Integer.valueOf(0),
				maxLimit.getFirstResult());
	}
}