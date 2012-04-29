package org.studentsuccessplan.ssp.util;

import java.util.Set;

import org.studentsuccessplan.ssp.model.Auditable;
import org.studentsuccessplan.ssp.model.ObjectStatus;

public class SetOps {

	public static <T> Set<T> updateSet(Set<T> existing, Set<T> replacingWith) {
		existing.clear();
		existing.addAll(replacingWith);
		return existing;
	}

	public static <T extends Auditable> void softDeleteSetItems(Set<T> items) {
		for (T item : items) {
			item.setObjectStatus(ObjectStatus.DELETED);
		}
	}
}
