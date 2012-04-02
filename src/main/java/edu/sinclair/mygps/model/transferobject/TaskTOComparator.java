package edu.sinclair.mygps.model.transferobject;

import java.util.Comparator;

public class TaskTOComparator implements Comparator<TaskTO> {

	public int compare(TaskTO taskTO0, TaskTO taskTO1) {

		return taskTO0.getName().toUpperCase().compareTo(taskTO1.getName().toUpperCase());

	}
	
}
