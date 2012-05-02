package org.jasig.ssp.util.sort

import static org.junit.Assert.*

import org.hibernate.Criteria
import org.junit.Before
import org.junit.Test
import org.jasig.ssp.model.ObjectStatus
import org.jasig.ssp.util.sort.SortingAndPaging;

class SortingAndPagingTest {
	private SortingAndPaging sAndP
	private Criteria criteria

	@Before
	public void setup(){
		criteria = [add:{ restrictions -> return},
			setFirstResult:{int i -> return},
			setMaxResults:{int i -> return},
			addOrder:{ order -> return}
		] as Criteria
	}

	@Test
	public void testStatusOnlyConstructor(){
		sAndP = new SortingAndPaging(ObjectStatus.ACTIVE)
		assertNull(sAndP.getFirstResult())
		assertNull(sAndP.getMaxResults())
		assertNull(sAndP.getDefaultSortProperty())
		assertNull(sAndP.getDefaultSortDirection())
		assertNull(sAndP.getSortFields())

		assertTrue(sAndP.isFilteredByStatus())
		assertFalse(sAndP.isPaged())
		assertFalse(sAndP.isSorted())
		assertFalse(sAndP.isDefaultSorted())

		sAndP.addStatusFilterToCriteria(criteria)
		sAndP.addPagingToCriteria(criteria)
		sAndP.addSortingToCriteria(criteria)
		sAndP.addAll(criteria)
	}

	@Test
	public void testSingleSortConstructor(){
		sAndP = SortingAndPaging.createForSingleSort(ObjectStatus.ACTIVE, 0, 10, "name", "ASC", null)
		assertEquals(0, sAndP.getFirstResult())
		assertEquals(10, sAndP.getMaxResults())
		assertNull(sAndP.getDefaultSortProperty())
		assertNull(sAndP.getDefaultSortDirection())
		assertNotNull(sAndP.getSortFields())
		assertEquals(1, sAndP.getSortFields().size())

		assertTrue(sAndP.isFilteredByStatus())
		assertTrue(sAndP.isPaged())
		assertTrue(sAndP.isSorted())
		assertFalse(sAndP.isDefaultSorted())

		sAndP.addStatusFilterToCriteria(criteria)
		sAndP.addPagingToCriteria(criteria)
		sAndP.addSortingToCriteria(criteria)
		sAndP.addAll(criteria)
	}

	@Test
	public void testSingleSortConstructor_withDefault(){
		sAndP = SortingAndPaging.createForSingleSort(ObjectStatus.ACTIVE, 0, 10, null, "ASC", "id")
		assertEquals(0, sAndP.getFirstResult())
		assertEquals(10, sAndP.getMaxResults())
		assertEquals("id", sAndP.getDefaultSortProperty())
		assertEquals(SortDirection.ASC, sAndP.getDefaultSortDirection())
		assertNull(sAndP.getSortFields())

		assertTrue(sAndP.isFilteredByStatus())
		assertTrue(sAndP.isPaged())
		assertFalse(sAndP.isSorted())
		assertTrue(sAndP.isDefaultSorted())

		sAndP.addStatusFilterToCriteria(criteria)
		sAndP.addPagingToCriteria(criteria)
		sAndP.addSortingToCriteria(criteria)
		sAndP.addAll(criteria)
	}
}