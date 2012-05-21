package org.jasig.ssp.util.collections;

import static org.jasig.ssp.util.assertions.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PairTest
{
	private static final String TESTSTRING1 = "test string 1";

	private static final String TESTSTRING2 = "test string 2";

	@Test
	public void testHashCode() {
		final Pair<String, String> p1 = new Pair<String, String>(TESTSTRING1,
				TESTSTRING2);
		final Pair<String, String> p2 = new Pair<String, String>(TESTSTRING1,
				TESTSTRING2);
		final Pair<String, String> p3 = new Pair<String, String>(TESTSTRING1,
				"");
		final Pair<String, String> p4 = new Pair<String, String>(TESTSTRING2,
				"");

		assertEquals("HashCodes should have matched.", p1.hashCode(),
				p2.hashCode());
		assertNotEquals("HashCodes should not have matched.", p1.hashCode(),
				p3.hashCode());
		assertNotEquals("HashCodes should not have matched.", p1.hashCode(),
				p4.hashCode());
	}

	@Test
	public void testEquals() {
		final Pair<String, String> p1 = new Pair<String, String>(TESTSTRING1,
				TESTSTRING2);
		final Pair<String, String> p2 = new Pair<String, String>(TESTSTRING1,
				TESTSTRING2);
		final Pair<String, String> p3 = new Pair<String, String>(TESTSTRING1,
				"");
		final Pair<String, String> p4 = new Pair<String, String>(TESTSTRING1,
				null);
		final Pair<String, String> p5 = new Pair<String, String>(null,
				TESTSTRING1);
		final Pair<String, String> p6 = new Pair<String, String>(TESTSTRING2,
				TESTSTRING1);

		assertEquals("Equals should have been true.", p1, p2);
		assertNotEquals("Pairs should not have matched.", p1, p3);
		assertEquals("Pairs should have matched.", p3, p3);
		assertNotEquals("Pairs should not have matched.", p3, p4);
		assertNotEquals("Non-matching class should not have matched.", p1,
				new Object());
		assertNotEquals("null should not have matched.", p1,
				null);
		assertNotEquals(
				"Pairs with reversed arguments should not have matched.", p4,
				p5);
		assertNotEquals(
				"Pairs with reversed arguments should not have matched.", p1,
				p6);
	}

	@Test
	public void testGets() {
		final Pair<String, String> p1 = new Pair<String, String>(TESTSTRING1,
				TESTSTRING2);

		assertEquals("GetFirst should have matched.", p1.getFirst(),
				p1.getFirst());
		assertEquals("GetSecond should have matched.", p1.getSecond(),
				p1.getSecond());
	}
}