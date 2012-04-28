package org.studentsuccessplan.ssp.util;

import java.util.Set;

public class SetOps {

	public static <T> Set<T> updateSet(Set<T> existing, Set<T> replacingWith) {
		existing.clear();
		existing.addAll(replacingWith);
		return existing;
	}
}
