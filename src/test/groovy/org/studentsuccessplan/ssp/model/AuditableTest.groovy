package org.studentsuccessplan.ssp.model

import static org.junit.Assert.*
import org.junit.Test

import com.google.common.collect.Sets

class AuditableTest {

	class AuditableSubClass extends Auditable{
		protected int hashPrime(){
			return 11
		};
	}

	class AuditableSubClass2 extends Auditable{
		protected int hashPrime(){
			return 7
		};
	}

	@Test
	void equals1(){
		UUID id = UUID.randomUUID()
		Auditable a1 = new AuditableSubClass(id:id)
		Auditable a2 = new AuditableSubClass(id:id)
		assertTrue("with same id, should be equal", a1.equals(a2))
		assertTrue("with same id, should be equal - backwards", a2.equals(a1))
		assertTrue("with same memory object, should be equal for obj1", a1.equals(a1))
		assertTrue("with same memory object, should be equal for obj2", a2.equals(a2))
		assertEquals("with same id, hashcode should be equal", a1.hashCode(), a2.hashCode())
		assertEquals("with same memory object, hashcode should be equal", a1.hashCode(), a1.hashCode())
		assertEquals("with same memory object, hashcode should be equal for obj2", a2.hashCode(), a2.hashCode())


		a1 = new AuditableSubClass(id:id)
		a2 = new AuditableSubClass()
		assertFalse(a1.equals(a2))
		assertFalse(a2.equals(a1))
		assertTrue(a2.equals(a2))
		assertTrue(a1.equals(a1))
		assertTrue(a1.hashCode() != a2.hashCode())
		assertEquals(a1.hashCode(), a1.hashCode())
		assertEquals(a2.hashCode(), a2.hashCode())
	}

	@Test
	void equals2(){
		UUID id = UUID.randomUUID()
		Auditable a1 = new AuditableSubClass(id:id)
		Auditable a2 = new AuditableSubClass2(id:id)
		assertFalse("with diff classes, obj should not be equal, even with same id", a1.equals(a2))
		assertFalse("with diff classes, obj should not be equal, even with same id - backwards", a2.equals(a1))
		assertTrue("obj1 should be equal to self", a2.equals(a2))
		assertTrue("obj2 should be equal to self",a1.equals(a1))
		assertTrue("hashcode should be different for different classes", a1.hashCode()!= a2.hashCode())
		assertEquals("hashcode should be same for same classes - obj1", a1.hashCode(), a1.hashCode())
		assertEquals("hashcode should be same for same classes - obj2", a2.hashCode(), a2.hashCode() )
	}

	@Test
	public void testSetOperations() {
		Auditable pel1 = new AuditableSubClass()
		Auditable pel2 = new AuditableSubClass()
		Set<Auditable> container = Sets.newHashSet();

		container.add(pel1)
		container.add(pel2);

		assertEquals("hashcode with null id calculated incorrectly", 2, container.size())

		container = Sets.newHashSet()
		pel1.setId(UUID.randomUUID())
		container.add(pel1)
		pel2.setId(UUID.randomUUID())
		container.add(pel2)

		assertEquals("hashcode with different id should not be equal", 2, container.size())

		container = Sets.newHashSet()
		container.add(pel1)
		pel2.setId(pel1.getId())
		container.add(pel2)

		assertEquals("hashcode with same id should be equal", 1, container.size())
	}
}
