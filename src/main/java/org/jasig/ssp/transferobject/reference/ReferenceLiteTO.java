package org.jasig.ssp.transferobject.reference;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.jasig.ssp.model.reference.AbstractReference;

import com.google.common.collect.Lists;

public class ReferenceLiteTO<T extends AbstractReference> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private UUID id;

	private String name;

	public ReferenceLiteTO() {
		super();
	}

	public ReferenceLiteTO(
			final UUID id, final String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public static <T extends AbstractReference> ReferenceLiteTO<T> fromModel(
			final T model) {
		if (model == null) {
			return null;
		} else {
			return new ReferenceLiteTO<T>(model.getId(),
					model.getName());
		}
	}

	public static <T extends AbstractReference> List<ReferenceLiteTO<T>> toTOList(
			@NotNull final Collection<T> models) {
		final List<ReferenceLiteTO<T>> tos = Lists.newArrayList();
		for (final T model : models) {
			tos.add(fromModel(model));
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

}
