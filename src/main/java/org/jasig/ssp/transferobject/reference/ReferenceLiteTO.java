/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.AbstractReference;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.jasig.ssp.util.collections.Pair;

/**
 * "Lite" version for a reference model, only including the ID, name, and
 * objectStatus properties.
 * 
 * @param <T>
 *            Any reference model.
 */
public class ReferenceLiteTO<T extends AbstractReference> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private String name;

	private ObjectStatus objectStatus;

	public ReferenceLiteTO() {
		super();
	}

	public ReferenceLiteTO(
			final UUID id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public ReferenceLiteTO(
			final UUID id, final String name, ObjectStatus objectStatus) {
		super();
		this.id = id;
		this.name = name;
		this.objectStatus = objectStatus;
	}

	public ReferenceLiteTO(final AbstractReference reference) {
		super();
		this.id = reference.getId();
		this.name = reference.getName();
		this.objectStatus = reference.getObjectStatus();
	}

	public static <T extends AbstractReference> ReferenceLiteTO<T> fromModel(
			final T model) {
		if (model == null) {
			return null;
		} else {
			return new ReferenceLiteTO<T>(model.getId(),
					model.getName(), model.getObjectStatus());
		}
	}

	/**
	 * Same as {@link #fromModel(org.jasig.ssp.model.reference.AbstractReference)}
	 * but for representing an <em>association</em> to that reference, which
	 * might have its own {@link ObjectStatus}, independent of the underlying
	 * reference's {@link ObjectStatus}. The <code>ObjectStatus</code> set on
	 * the resulting TO represents the status for the entire association as
	 * defined by {@link ObjectStatus#and(org.jasig.ssp.model.ObjectStatus)}.
	 *
	 * @param model
	 * @param associationStatus
	 * @param <T>
	 * @return
	 */
	public static <T extends AbstractReference> ReferenceLiteTO<T> fromModelAssociation(
			final T model, final ObjectStatus associationStatus) {
		ReferenceLiteTO<T> to = fromModel(model);
		if ( to == null ) {
			return null;
		}
		to.setObjectStatus(combinedObjectStatus(to, associationStatus));
		return to;
	}

	private static <T extends AbstractReference> ObjectStatus
	combinedObjectStatus(ReferenceLiteTO<T> to, ObjectStatus associationStatus) {
		ObjectStatus toObjStatus = to.objectStatus;
		if ( to.getObjectStatus() == null ) {
			return associationStatus;
		}
		return to.objectStatus.and(associationStatus);
	}

	public static <T extends AbstractReference> List<ReferenceLiteTO<T>> toTOList(
			@NotNull final Collection<T> models) {
		final List<ReferenceLiteTO<T>> tos = Lists.newArrayList();
		if (models != null) {
			for (final T model : models) {
				tos.add(fromModel(model));
			}
		}
		return tos;
	}

	public static <T extends AbstractReference> List<ReferenceLiteTO<T>> toTOAssociationList(
			@NotNull final Collection<Pair<T,ObjectStatus>> modelsAndAssociationStatuses) {
		final List<ReferenceLiteTO<T>> tos = Lists.newArrayList();
		if (modelsAndAssociationStatuses != null) {
			for (final Pair<T,ObjectStatus> modelAndStatus : modelsAndAssociationStatuses) {
				final ReferenceLiteTO<T> to =
						fromModelAssociation(modelAndStatus.getFirst(), modelAndStatus.getSecond());
				tos.add(to);
			}
		}
		return tos;
	}

	public static <T extends AbstractReference> Set<ReferenceLiteTO<T>> toTOSet(
			@NotNull final Collection<T> models) {
		final Set<ReferenceLiteTO<T>> tos = Sets.newHashSet();
		if (models != null) {
			for (final T model : models) {
				tos.add(fromModel(model));
			}
		}
		return tos;
	}

	public static <T extends AbstractReference> Set<ReferenceLiteTO<T>> toTOAssociationSet(
			@NotNull final Collection<Pair<T,ObjectStatus>> modelsAndAssociationStatuses) {
		final Set<ReferenceLiteTO<T>> tos = Sets.newHashSet();
		if (modelsAndAssociationStatuses != null) {
			for (final Pair<T,ObjectStatus> modelAndStatus : modelsAndAssociationStatuses) {
				final ReferenceLiteTO<T> to =
						fromModelAssociation(modelAndStatus.getFirst(), modelAndStatus.getSecond());
				tos.add(to);
			}
		}
		return tos;
	}

	public UUID getId() {
		return id;
	}

	public void setId(final UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(@NotNull final ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}
}