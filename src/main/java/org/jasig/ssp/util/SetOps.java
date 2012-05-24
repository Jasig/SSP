package org.jasig.ssp.util;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.model.Auditable;
import org.jasig.ssp.model.ObjectStatus;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public final class SetOps {

	/**
	 * This class is only called statically
	 */
	private SetOps() {
		super();
	}

	/**
	 * Soft delete ones in existing that are not in replacingWith <br />
	 * Ignore ones in existing that are in replacingWith <br />
	 * Add from replacingWith where not in existing.
	 * 
	 * @param existing
	 *            Existing set
	 * @param replacingWith
	 *            Replace existing set with this set.
	 * @return Updated set.
	 */
	public static <T extends Auditable> Set<T> updateSet(final Set<T> existing,
			final Set<T> replacingWith) {

		// Pull ids and verify existingIds are indeed persisted (have ids)
		final Map<UUID, T> existingIds = Maps.newHashMap();
		for (T t : existing) {
			if (t.getId() == null) {
				throw new IllegalArgumentException(
						"Every object in the existing list must have a non-null id");
			}
			existingIds.put(t.getId(), t);
		}

		// Determine which of the replacingWith items are new vs updates
		// Also, pull ids
		final Map<UUID, T> replacingWithIds = Maps.newHashMap();
		final Set<T> newItems = Sets.newHashSet();
		for (T t : replacingWith) {
			if (t.getId() == null) {
				newItems.add(t);
			} else {
				if (existingIds.keySet().contains(t.getId())) {
					replacingWithIds.put(t.getId(), t);
				} else {
					newItems.add(t);
				}
			}
		}

		// add all newItems to existing
		existing.addAll(newItems);

		// soft delete in existing where not in replacingWith
		final Set<UUID> idsToSoftDelete = existingIds.keySet();
		idsToSoftDelete.removeAll(replacingWithIds.keySet());

		final Set<T> toRemove = Sets.newHashSet();
		for (UUID id : idsToSoftDelete) {
			toRemove.add(existingIds.get(id));
		}

		softDeleteSetItems(toRemove);

		return existing;
	}

	/**
	 * Mark each item as deleted by setting its ObjectStatus to deleted
	 * 
	 * @param items
	 *            Items to be marked as deleted
	 */
	public static <T extends Auditable> void softDeleteSetItems(
			final Set<T> items) {
		for (T item : items) {
			if (item.getObjectStatus() != ObjectStatus.DELETED) {
				item.setObjectStatus(ObjectStatus.DELETED);
			}
		}
	}
}
