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
	String intakeForm = conn.get("api/1/tool/studentIntake/252de4a0-7c06-4254-b7d8-4ffc02fe81ff")
	
	//lets manipulate it a bit
	def result = jsonParser.parseText(intakeForm)
	
	//remove the referenceData
	result.referenceData = null
	
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
	
	//subit the manipulated form
	conn.put("api/1/tool/studentIntake/252de4a0-7c06-4254-b7d8-4ffc02fe81ff", toJson(result))
	
	//Retrieve the form once more
	result = jsonParser.parseText( conn.get("api/1/tool/studentIntake/252de4a0-7c06-4254-b7d8-4ffc02fe81ff"))
	
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
	String form = '{"id":"","createdDate":null,"name":"Get a 2.0 GPA","personId":"58ba5ee3-734e-4ae9-b9c5-943774b4de41","description":"Get a 2.0 GPA","createdBy":{"id":"","firstName":"","lastName":""},"modifiedBy":{"id":"","firstName":"","lastName":""},"confidentialityLevel":{"id":"afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c","name":null}}'
	return conn.post("api/1/person/58ba5ee3-734e-4ae9-b9c5-943774b4de41/goal/", form)
}


String getAllJournalEntriesForPerson(ApiConnection conn){
	//"/1/person/{personId}/journalEntry"
	return conn.get("api/1/person/58ba5ee3-734e-4ae9-b9c5-943774b4de41/journalEntry/")
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

/**
 * You can exercise the ssp api from within STS (eclipse) using this script
 */

JsonSlurper jsonParser = new JsonSlurper()
ApiConnection conn = new ApiConnection("http://localhost:8080/ssp/", "advisor0", "advisor0", false)

//String output = getStudentIntakeForm(conn, jsonParser)
//String output = addChallengeToCategory(conn) 
//String output = addGoalToPerson(conn) 
//String output = getAllJournalEntriesForPerson(conn);
String output = getPerson(conn)

conn.formatAndPrintJson(output)
//println (output);