package org.jasig.ssp.transferobject.reports;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.JournalEntryTO;
import org.jasig.ssp.transferobject.TaskTO;

public class StudentHistoryTO { 
		
	String dateDayOnly;
	
	List<EarlyAlertTO> earlyAlerts = new ArrayList<EarlyAlertTO>();
	List<HashMap<String, Object>> taskList = new ArrayList<HashMap<String,Object>>();	
	HashMap<String,ArrayList<TaskTO>> taskMap = new HashMap<String,ArrayList<TaskTO>>();

	List<JournalEntryTO> journalEntries = new ArrayList<JournalEntryTO>();

	public StudentHistoryTO(String dateDayOnly) {
		this.dateDayOnly = dateDayOnly;
	}

	public void addEarlyAlertTO(EarlyAlertTO earlyAlertTO){
		earlyAlerts.add(earlyAlertTO);
	}

	public void addJournalEntryTO(JournalEntryTO journalEntryTO){
		journalEntries.add(journalEntryTO);
	}

	public void addTask(String group, TaskTO task){		
		ArrayList<TaskTO> taskList = taskMap.get(group);
		if(null == taskList){
			taskList = new ArrayList<TaskTO>();
			taskMap.put(group, taskList);
		}	
		if(!taskList.contains(task)){
			taskList.add(task);	
		}		
	}

	public void createTaskList() {
		for (Map.Entry<String, ArrayList<TaskTO>> entry : taskMap.entrySet())
		{
		    String groupName = entry.getKey();
		    List<TaskTO> tasks = entry.getValue();
		    
		    HashMap<String, Object> tempMap = new HashMap<String, Object>();
		    tempMap.put("groupName", groupName);
		    tempMap.put("taskList", tasks);
		    taskList.add(tempMap);
		}		
	}
	
	public String getDateDayOnly() {
		return dateDayOnly;
	}
	public List<EarlyAlertTO> getEarlyAlerts() {
		return earlyAlerts;
	}	
	public List<JournalEntryTO> getJournalEntries() {
		return journalEntries;
	}
		
	public List<HashMap<String, Object>> getTaskList() {
		return taskList;
	}

	public HashMap<String, ArrayList<TaskTO>> getTaskMap() {
		return taskMap;
	}
	
	public HashMap<String,ArrayList<TaskTO>> getTasks() {
		return taskMap;
	}
	
	public void setDateDayOnly(String dateDayOnly) {
		this.dateDayOnly = dateDayOnly;
	}

	public void setTaskList(List<HashMap<String, Object>> taskList) {
		this.taskList = taskList;
	}

	public void setTaskMap(HashMap<String, ArrayList<TaskTO>> taskMap) {
		this.taskMap = taskMap;
	}
}






