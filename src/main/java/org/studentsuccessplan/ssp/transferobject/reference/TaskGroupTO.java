package org.studentsuccessplan.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.studentsuccessplan.ssp.model.reference.TaskGroup;
import org.studentsuccessplan.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class TaskGroupTO extends AbstractReferenceTO<TaskGroup>
		implements TransferObject<TaskGroup> {

	public TaskGroupTO() {
		super();
	}

	public TaskGroupTO(final UUID id) {
		super(id);
	}

	public TaskGroupTO(final UUID id, final String name) {
		super(id, name);
	}

	public TaskGroupTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public TaskGroupTO(final TaskGroup model) {
		super();
		fromModel(model);
	}

	@Override
	public TaskGroup addToModel(final TaskGroup model) {
		super.addToModel(model);
		return model;
	}

	@Override
	public TaskGroup asModel() {
		return addToModel(new TaskGroup());
	}

	public static List<TaskGroupTO> listToTOList(
			final List<TaskGroup> models) {
		final List<TaskGroupTO> tos = Lists.newArrayList();
		for (TaskGroup model : models) {
			tos.add(new TaskGroupTO(model));
		}
		return tos;
	}

}
