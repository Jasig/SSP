package org.studentsuccessplan.ssp.util

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.studentsuccessplan.ssp.model.Auditable
import org.studentsuccessplan.ssp.model.ObjectStatus

import com.google.common.collect.Sets


class SetOpsTest {

	class AuditableSubClass extends Auditable{
		public AuditableSubClass(){
			setObjectStatus(ObjectStatus.ACTIVE)
		}

		private String other;

		public getOther() {
			return	this.other;
		}

		public setOther(String other) {
			this.other = other;
		}

		protected int hashPrime(){
			return 11
		}

		@Override
		public int hashCode() {
			int result = hashPrime();

			// Auditable properties
			result *= getId() == null ? "id".hashCode() : getId().hashCode();
			result *= getObjectStatus() == null ? hashPrime() : getObjectStatus()
					.hashCode();
			result *= other == null ? "other".hashCode() : other.hashCode();
			return result
		};
	}

	private Set<AuditableSubClass> set1
	private Set<AuditableSubClass> set2

	private AuditableSubClass a1 = new AuditableSubClass(id:UUID.randomUUID())
	private AuditableSubClass a2 = new AuditableSubClass(id:UUID.randomUUID())
	private AuditableSubClass a3 = new AuditableSubClass(id:UUID.randomUUID())
	private AuditableSubClass a4 = new AuditableSubClass(id:UUID.randomUUID())
	private AuditableSubClass a2_b

	@Before
	void setup(){
		set1 = Sets.newHashSet()
		set2 = Sets.newHashSet()
	}

	@Test
	void updateSet_addRemoveAndUpdate(){
		set1.addAll([a1, a2, a3, a4])

		a2_b = a2
		a2_b.other="testString"
		set2.addAll([a2_b])

		SetOps.updateSet(set1, set2);

		final List active = ObjectStatus.filterForStatus(set1, ObjectStatus.ACTIVE)
		final List softDeleted = ObjectStatus.filterForStatus(set1, ObjectStatus.DELETED)

		assertEquals("only one element should be active", 1, active.size())
		assertTrue("a2 should be the active element", active.contains(a2))

		active.each{
			assertEquals("Should overwrite a2 with a2_b", "testString", it.other)
		}

		assertEquals("3 elements should be softDeleted", 3, softDeleted.size())

		assertEquals("size of set1 should be 4", 4, set1.size())
		assertEquals("size of set2 should still be 1", 1, set2.size())
	}

	@Test
	void updateSet_addAndRemove(){
		set1.addAll([a1, a3, a4])
		set2.addAll([a2])

		SetOps.updateSet(set1, set2);

		final List active = ObjectStatus.filterForStatus(set1, ObjectStatus.ACTIVE)
		final List softDeleted = ObjectStatus.filterForStatus(set1, ObjectStatus.DELETED)

		assertEquals("only one element should be active", 1, active.size())
		assertTrue("a2 should be the active element", active.contains(a2))

		assertEquals("3 elements should be softDeleted", 3, softDeleted.size())

		assertEquals("size of set1 should be 4", 4, set1.size())
		assertEquals("size of set2 should still be 1", 1, set2.size())
	}
}