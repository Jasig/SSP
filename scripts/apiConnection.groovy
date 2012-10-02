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
import java.util.List;

import org.jasig.ssp.*
import org.jasig.ssp.model.reference.*
import org.jasig.ssp.transferobject.*
import org.jasig.ssp.transferobject.reference.*;

import com.google.common.collect.Lists

import groovy.json.*



String submitChildCareArrangement(ApiConnection conn, JsonSlurper jsonParser){
	
	String form = """{"name":"Sunny Day","description":""}"""
	String output = conn.post("api/1/reference/childCareArrangement/", form)
	
	def result = jsonParser.parseText(output)
	
	String id = result.id
	form = """{"id":"${id}","name":"Sunny Day","description":""}"""
	return  conn.put("api/1/reference/childCareArrangement/"+id.toString(), form)
}

String getStudentIntakeForm(ApiConnection conn, JsonSlurper jsonParser){
	
	//retrieve the intake form
	String intakeForm = conn.get("api/1/tool/studentIntake/7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194")
	
	//lets manipulate it a bit
	def result = jsonParser.parseText(intakeForm)
	
	//remove the referenceData
	result.referenceData = null
	
	result.person.primaryEmailAddress = 'test@sinclair.edu'
	
	result.person.studentType = new ReferenceLiteTO(id:UUID.fromString("b2d05919-5056-a51a-80bd-03e5288de771"))
	
	/*List<ReferenceLiteTO<ProgramStatus>> programStatuses = Lists.newArrayList();
	programStatuses << new ReferenceLiteTO<ProgramStatus>(id:UUID.fromString("b2d12527-5056-a51a-8054-113116baab88"))
	result.person.programStatuses = programStatuses*/
	
	//add a challenge
	List<PersonChallengeTO> challenges = Lists.newArrayList();
	challenges << new PersonChallengeTO(challengeId: UUID.fromString("07b5c3ac-3bdf-4d12-b65d-94cb55167998"), personId: UUID.fromString(result.person.id), description:"Childcare")
	result.personChallenges = challenges
	
	//add a funding source
	List<PersonFundingSourceTO> fundings = Lists.newArrayList();
	fundings << new PersonFundingSourceTO(fundingSourceId:UUID.fromString("365e8c95-f356-4f1f-8d79-4771ae8b0291"), personId: UUID.fromString(result.person.id), description:"Other")
	result.personFundingSources = fundings
	
	//add a specialServiceGroup
	List<ReferenceLiteTO<SpecialServiceGroup>> specialServiceGroups = Lists.newArrayList();
	specialServiceGroups << new ReferenceLiteTO(id: UUID.fromString("f6201a04-bb31-4ca5-b606-609f3ad09f87"))
	//def specialServiceGroups = null;
	result.person.specialServiceGroups = specialServiceGroups;
	
	result.personEducationGoal = new PersonEducationGoalTO(description:"ed goal description", plannedOccupation:"occupied", howSureAboutMajor:2)
	
	result.personEducationPlan = new PersonEducationPlanTO(newOrientationComplete:false, registeredForClasses:false, collegeDegreeForParents:false, specialNeeds:false, gradeTypicallyEarned:"B")
	
	//submit the manipulated form
	conn.put("api/1/tool/studentIntake/7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194", toJson(result))
	
	//Retrieve the form once more
	result = jsonParser.parseText( conn.get("api/1/tool/studentIntake/7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194"))
	
	//we're not interested in the reference data
	result.referenceData = null
	
	//retrun the resulting json
	return toJson(result)	
}

String addChallengeToCategory(ApiConnection conn){
	String form = "\"7c0e5b76-9933-484a-b265-58cb280305a5\""
	return conn.post("api/1/reference/category/5d24743a-a11e-11e1-a9a6-0026b9e7ff4c/challenge", form)
}

String addGoalToPerson(ApiConnection conn){
	String form = '{"id":"","createdDate":null,"name":"Get a 2.0 GPA","personId":"7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194","description":"Get a 2.0 GPA","createdBy":{"id":"","firstName":"","lastName":""},"modifiedBy":{"id":"","firstName":"","lastName":""},"confidentialityLevel":{"id":"afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c","name":null}}'
	return conn.post("api/1/person/7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194/goal/", form)
}


String getAllJournalEntriesForPerson(ApiConnection conn){
	//"/1/person/{personId}/journalEntry"
	return conn.get("api/1/person/7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194/journalEntry/")
}

String getPerson(ApiConnection conn){
	//"/1/session/getAuthenticatedPerson"
	return conn.get("api/1/session/getAuthenticatedPerson")
}

String toJson(def form){
	def builder = new JsonBuilder()
	builder.call(form)
	return builder.toString()
}

String search(ApiConnection conn){
	//"/1/person/search"
	return conn.get("api/1/person/search?searchTerm=dennis")
}

String getCaseload(ApiConnection conn){
	//"/1/person/caseload"
	return conn.get("api/1/person/caseload")
	//return conn.get("api/1/person/caseload?programStatusId=b2d12527-5056-a51a-8054-113116baab88")
}

String getAppointments(ApiConnection conn){
	return conn.get("api/1/person/1010e4a0-1001-0110-1011-4ffc02fe81ff/appointment/")
}

String getCurrentAppointment(ApiConnection conn){
	return conn.get("api/1/person/1010e4a0-1001-0110-1011-4ffc02fe81ff/appointment/current/")
}

String getCoaches(ApiConnection conn){
	return conn.get("api/1/person/coach/")
}

String getAllTerms(ApiConnection conn){
	return conn.get("api/1/reference/term/")
}

/**
 * You can exercise the ssp api from within STS (eclipse) using this script
 */

JsonSlurper jsonParser = new JsonSlurper()
ApiConnection conn = new ApiConnection("http://localhost:8080/ssp/", "advisor0", "advisor0", false)

String output = getStudentIntakeForm(conn, jsonParser)
//String output = addChallengeToCategory(conn) 
//String output = addGoalToPerson(conn) 
//String output = getAllJournalEntriesForPerson(conn);
//String output = getPerson(conn)
//String output = search(conn)
//String output = getCaseload(conn)
//String output = getAppointments(conn)
//String output = getCoaches(conn)
//String output = getCurrentAppointment(conn)
//String output = getAllTerms(conn)


conn.formatAndPrintJson(output)
//println (output);