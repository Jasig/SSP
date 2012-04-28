package org.studentsuccessplan.ssp.util

import static org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.studentsuccessplan.ssp.model.Auditable;

import com.google.common.collect.Sets

class SetOpsTest {

	class AuditableSubClass extends Auditable{
		private String other;
		protected int hashPrime(){
			return 11
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
		a2_b = new AuditableSubClass(id:a2.getId(), other:"testString")
	}

	@Test
	void updateSet_addRemoveAndUpdate(){
		set1.addAll([a1, a2, a3, a4])
		set2.addAll([a2_b])
		SetOps.updateSet(set1, set2);
		assertEquals("should trim all elements but the one in a2", 1, set1.size())
		assertTrue("should only contain a2", set1.contains(a2))
		set1.each{
			assertEquals("Should overwrite a2 with a2_b", "testString", it.other)
		}
	}

	@Test
	void updateSet_addAndRemove(){
		set1.addAll([a1, a3, a4])
		set2.addAll([a2])
		SetOps.updateSet(set1, set2);
		assertEquals("should trim all elements but the one in a2", 1, set1.size())
		assertTrue("should only contain a2", set1.contains(a2))
	}
}