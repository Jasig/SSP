package org.jasig.ssp.util;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

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
	public static <T extends Auditable> Set<T> updateSet(
			@NotNull final Set<T> existing, @NotNull final Set<T> replacingWith) {
		if (existing == null) {
			throw new IllegalArgumentException("Missing existing set.");
		}

		if (replacingWith == null) {
			throw new IllegalArgumentException("Missing replacement set.");
		}

		// Pull ids and verify existingIds are indeed persisted (have ids)
		final Map<UUID, T> existingIds = Maps.newHashMap();
		for (final T t : existing) {
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
		for (final T t : replacingWith) {
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
		for (final UUID id : idsToSoftDelete) {
			toRemove.add(existingIds.get(id));
		}

		softDeleteSetItems(toRemove);

		return existing;
	}

	/**
	 * Mark each item {@link ObjectStatus#INACTIVE}.
	 * 
	 * @param items
	 *            Items to be marked as {@link ObjectStatus#INACTIVE}
	 */
	public static <T extends Auditable> void softDeleteSetItems(
			final Set<T> items) {

		if (items == null) {
			return;
		}

		for (final T item : items) {
			if (!item.getObjectStatus().equals(ObjectStatus.INACTIVE)) {
				item.setObjectStatus(ObjectStatus.INACTIVE);
			}
		}
	}
}