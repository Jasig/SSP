package org.jasig.ssp;

import static org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.jasig.ssp.model.Auditable;

public class TestUtils {

	public static <T extends Auditable> void assertList(
			final Collection<T> objects) {
		for (final T object : objects) {
			assertNotNull("Object in the list should not have been null.",
					object.getId());
		}
	}
}
