package org.studentsuccessplan.mygps.model.transferobject;

import java.io.Serializable;
import java.util.Comparator;

public class TaskTOComparator implements Comparator<TaskTO>, Serializable {

	private static final long serialVersionUID = 1267207639241417281L;

	@Override
	public int compare(TaskTO taskTO0, TaskTO taskTO1) {
		return taskTO0.getName().toUpperCase()
				.compareTo(taskTO1.getName().toUpperCase());
	}
}
