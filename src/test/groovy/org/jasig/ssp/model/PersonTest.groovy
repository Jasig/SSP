package org.jasig.ssp.model

import static org.junit.Assert.*

import java.util.UUID

import org.junit.Test


class PersonTest {
	class AuditableSubClass extends Auditable{
		protected int hashPrime(){
			return 11
		};

		public int hashCode() {
			return hashPrime() * (id == null ? "id".hashCode() : id.hashCode());
		}
	}

	@Test
	void equalsWithSuperClassComparisons(){
		UUID id = UUID.randomUUID()
		String name = "A Name";
		Person a1 = new Person(id:id, firstName:name)
		Auditable a2 = new AuditableSubClass(id:id)
		assertFalse("with diff classes, obj should not be equal, even with same id", a1.equals(a2))
		assertFalse("with diff classes, obj should not be equal, even with same id - backwards", a2.equals(a1))
		assertTrue("hashcode should be different for different properties", a1.hashCode()!= a2.hashCode())
	}

	@Test
	void equals1(){
		UUID id = UUID.randomUUID()
		String name = "A Name";
		Person a1 = new Person(id:id, firstName:name)
		Auditable a2 = new Person(id:id)
		assertTrue("with same id, should be equal", a1.equals(a2))
		assertTrue("with same id, should be equal - backwards", a2.equals(a1))
		assertTrue("with same memory object, should be equal for obj1", a1.equals(a1))
		assertTrue("with same memory object, should be equal for obj2", a2.equals(a2))
		assertFalse("with same id but different props, hashcode should not be equal", a1.hashCode().equals(a2.hashCode()))
		assertEquals("with same memory object, hashcode should be equal", a1.hashCode(), a1.hashCode())
		assertEquals("with same memory object, hashcode should be equal for obj2", a2.hashCode(), a2.hashCode())

		a1 = new Person(firstName:name)
		a2 = new Person(firstName:name)
		assertTrue(a1.equals(a2))
		assertTrue(a2.equals(a1))
		assertTrue(a2.equals(a2))
		assertTrue(a1.equals(a1))
		assertEquals(a1.hashCode(), a2.hashCode())
		assertEquals(a1.hashCode(), a1.hashCode())
		assertEquals(a2.hashCode(), a2.hashCode())
	}

	@Test
	void equals2(){
		UUID id = UUID.randomUUID()
		String name = "A Name";
		Person a1 = new Person(id:id)
		Auditable a2 = new Person(id:id, firstName:name)
		assertTrue("with diff properties, obj should be equal, if the same id", a1.equals(a2))
		assertTrue("with diff properties, obj should be equal, if the same id - backwards", a2.equals(a1))
		assertTrue("obj1 should be equal to self", a2.equals(a2))
		assertTrue("obj2 should be equal to self",a1.equals(a1))
		assertTrue("hashcode should be different for different properties", a1.hashCode()!= a2.hashCode())
		assertEquals("hashcode should be same for same classes - obj1", a1.hashCode(), a1.hashCode())
		assertEquals("hashcode should be same for same classes - obj2", a2.hashCode(), a2.hashCode() )

		a1 = new Person(id:id, firstName: "")
		a2 = new Person(firstName:name)
		assertFalse("with diff properties, obj should not be equal, even with same id", a1.equals(a2))
		assertFalse("with diff properties, obj should not be equal, even with same id - backwards", a2.equals(a1))
		assertTrue("hashcode should be different for different properties", a1.hashCode()!= a2.hashCode())
	}
}