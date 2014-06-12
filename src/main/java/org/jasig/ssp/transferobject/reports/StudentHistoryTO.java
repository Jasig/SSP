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
package org.jasig.ssp.transferobject.reports;

import java.util.*;

import com.google.common.collect.Sets;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.transferobject.EarlyAlertTO;
import org.jasig.ssp.transferobject.JournalEntryDetailTO;
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
        if ( ObjectStatus.ACTIVE.equals(journalEntryTO.getObjectStatus()) ) {
            HashSet<JournalEntryDetailTO> nonDeletedJournalEntryDetails = Sets.newHashSet();

            for ( JournalEntryDetailTO singleJournalEntryDetail : journalEntryTO.getJournalEntryDetails() ) {
                if ( ObjectStatus.ACTIVE.equals(singleJournalEntryDetail.getObjectStatus()) ) {
                    nonDeletedJournalEntryDetails.add(singleJournalEntryDetail);
                }
            }

            journalEntryTO.setJournalEntryDetails(nonDeletedJournalEntryDetails);

            journalEntries.add(journalEntryTO);
        }
	}

	public void addTask(String group, TaskTO task){		
		if(ObjectStatus.ACTIVE.equals(task.getObjectStatus()))
		{
			ArrayList<TaskTO> taskList = taskMap.get(group);
			if(null == taskList){
				taskList = new ArrayList<TaskTO>();
				taskMap.put(group, taskList);
			}	
			if(!taskList.contains(task)){
				taskList.add(task);	
			}		
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






