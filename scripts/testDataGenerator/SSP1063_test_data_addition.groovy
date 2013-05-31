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
import static Constants.*

class Constants{
	 
	/* Configuration */
	static final def eol = System.properties.'line.separator'
	static final fileName = "demo_data_3.xml";
	static final FULL_DATA_BASE_LOCATION_CHANGESET = './src/main/resources/org/jasig/ssp/database/demoDataChangeset/'
	static final BASE_LOCATION_CHANGESET = FULL_DATA_BASE_LOCATION_CHANGESET
	static final author = "demo.data.script.generator"
	static final fileDescription = "Augmenting Demo Data Set"
	static final demoDataFile = './src/main/resources/org/jasig/ssp/database/demoDataChangeset/demo_data.xml'
	static final r200b2DataFile = './src/main/resources/org/jasig/ssp/database/demoDataChangeset/r200b2_demo_data.xml'
	static final CHANGESET = new XmlParser().parse(demoDataFile).changeSet
	static final R200B2_DATA = new XmlParser().parse(r200b2DataFile).changeSet
	
	/* Constant Data */
	static final FAFSA_DATE = "2012-08-20"
	
	static final ethnicities = [
		"African American/Black",
		"Asian Pacific Islander",
		"Caucasian/White",
		"Hispanic/Latino",
		"Other",
		"Prefer Not To Answer",
		"American Indian/Alaskan Native"		
	]
	
	static final marital_status = [
		"Single",
		"Married",
		"Separated",
		"Widowed",
		"Divorced"
	]
	
	static final early_alert_reasons = [
		"1f5729af-0337-4e58-a001-8a9f80dbf8aa",
		"300d68ef-38c2-4b7d-ad46-7874aa5d34ac",
		"b2d112a9-5056-a51a-8010-b510525ea3a8",
		"b2d112b8-5056-a51a-8067-1fda2849c3e5",
		"b2d112c8-5056-a51a-80d5-beec7d48cb5d",
		"b2d112d7-5056-a51a-80aa-795e56155af5",
		"b2d112e7-5056-a51a-80e8-a30645c463e4",
		"b2d11316-5056-a51a-80f9-79421bdf08bf",
		"b2d11326-5056-a51a-806c-79f352d0c2b2",
		"b2d11335-5056-a51a-80ea-074f8fef94ea"		
	]
	
	static final early_alert_suggestions = [
		"b2d11160-5056-a51a-807d-9897c14bdd44",
		"b2d111fd-5056-a51a-80fe-6f3344174dbe",
		"b2d111dd-5056-a51a-8034-909bd6af80d5",
		"b2d1120c-5056-a51a-80ea-c779a3109f8f",
		"b2d11151-5056-a51a-8051-3acdf99fef84",
		"b2d11141-5056-a51a-80c1-c1250ba820f8",
		"b2d11170-5056-a51a-8002-b5ce9f25e2bc",
		"b2d111ce-5056-a51a-8017-676c4d8c4f1d",
		"b2d111ed-5056-a51a-8046-5291453e8720",
		"b2d111be-5056-a51a-8075-c5a65da17079"
	]
	
	static final term_codes = ['SP', 'SU', 'FA', 'WN']
	
	static final XML_LICENSE = """ <!--
			|Licensed to Jasig under one or more contributor license
		    |agreements. See the NOTICE file distributed with this work
		    |for additional information regarding copyright ownership.
		    |Jasig licenses this file to you under the Apache License,
		    |Version 2.0 (the \"License\"); you may not use this file
		    |except in compliance with the License. You may obtain a
		    |copy of the License at:
			|
		    |http://www.apache.org/licenses/LICENSE-2.0
			|
		    |Unless required by applicable law or agreed to in writing,
		    |software distributed under the License is distributed on
		    |an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
		    |KIND, either express or implied. See the License for the
		    |specific language governing permissions and limitations
		    |under the License.											
			|-->
			|
			|""".stripMargin()
}

def writer = new FileWriter(BASE_LOCATION_CHANGESET + fileName)
def xml = new groovy.xml.MarkupBuilder(writer) as groovy.xml.MarkupBuilder
writer << '<?xml version="1.0" encoding="UTF-8" standalone="no"?>' << eol
writer << XML_LICENSE << eol
xml.databaseChangeLog( xmlns : "http://www.liquibase.org/xml/ns/dbchangelog"
	, "xmlns:xsi" : "http://www.w3.org/2001/XMLSchema-instance"
	, "xsi:schemaLocation" : "http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd"
	) {
	changeSet(author:author, id:fileDescription) {
		def externalPersons = getAllExternalPersons()
		def externalCourses = parseCourses()
		def currentFacultyMember = ""
		
		externalPersons.each { externalPerson ->				
			addToExternalPerson(xml, externalPerson)
			if(isStudent(externalPerson)) {				
				addExternalRegistrationStatusByTerm(xml, externalPerson)
				addToExternalStudentAcademicProgram(xml, externalPerson)
				addExternalStudentFinancialAid(xml, externalPerson)
				addToExternalStudentTranscript(xml, externalPerson)
				rand(8).times {
					addExternalStudentTranscriptTerm(xml, externalPerson)
				}
				replaceExternalStudentTranscriptCourse(xml, externalPerson, externalCourses, currentFacultyMember)
				addToEarlyAlert(xml, externalPerson, externalCourses)
			} else if(isFaculty(externalPerson)) {
				currentFacultyMember = getColumnValueByName(externalPerson, 'username')
			}
		}
	}
}

////////////////////////////////////
// Data Generation Methods
////////////////////////////////////
	
def addToExternalPerson(xml, person) {
	xml.update(tableName:'external_person') {
		xml.column(name:'ethnicity', value: randFromCollection(ethnicities) )
		xml.column(name:'gender', value:"${percentChance(50)? 'M' : 'F'}")
		xml.column(name:'residency_county', value:'Hamilton')
		xml.column(name:'f1_status', value:'N' )
		xml.column(name:'cell_phone', value:'480-555-7894')
		xml.column(name:'marital_status', value: randFromCollection(marital_status))
		xml.where("school_id = '${getColumnValueByName(person, 'username')}'")
	}
	xml.rollback() {
		xml.update(tableName:'external_person') {
			xml.column(name:'ethnicity', value: 'x' )
			xml.column(name:'gender', value: 'x')
			xml.column(name:'residency_county', value:'')
			xml.column(name:'f1_status', value:'' )
			xml.column(name:'cell_phone', value:'')
			xml.column(name:'marital_status', value: '')
			xml.where("school_id = '${getColumnValueByName(person, 'username')}'")
		}
	}
}

def addExternalRegistrationStatusByTerm(xml, person) {
	def username =  getColumnValueByName(person, 'username')
	for(year in 2012..(randFromCollection([2012, 2013, 2014, 2015, 2016]))) {
		for(termCode in (percentChance(25)? term_codes: ['SP', 'FA'])) {
			xml.insert(tableName:'external_registration_status_by_term') {
				xml.column(name:'school_id', value: username)
				xml.column(name:'term_code', value: "$termCode$year" )
				xml.column(name:'registered_course_count', value: randFromCollection([3,4,5,6]) )
				xml.column(name:'tuition_paid', value: percentChance(5)? 'N' : 'Y' )
			}
			
			xml.rollback() {
				xml.delete(tableName:'external_registration_status_by_term') {
					xml.where("school_id = '$username'")
				}
			}
		}
	}
}

def addToExternalStudentAcademicProgram(xml, person) {
	def schoolId = getColumnValueByName(person, 'school_id')
	//originally the data confused the scool_id with a UUID, 
	//where the school_id is just supposed to be the username.
	// Need both these values to properly populate after the
	// update to change school_id to be username
	def username =  getColumnValueByName(person, 'username')
	
	def esap = getExternalStudentAcademicProgramBySchoolId(schoolId)

	if(esap != null) {
		xml.update(tableName:'external_student_academic_program') {
			xml.column(name:'intended_program_at_admit', value:getColumnValueByName(esap, 'program_name') )
			xml.where("school_id = '${username}'")
		}
		xml.rollback() {
			xml.update(tableName:'external_student_academic_program') {
				xml.column(name:'intended_program_at_admit', value:'' )
				xml.where("school_id = '${username}'")
			}
		}
	}	
}

def addExternalStudentFinancialAid(xml, person) {
	def username = getColumnValueByName(person, 'username')
	def esfa = makeExternalStudentFinancialAid(person)
	
	xml.insert(tableName:'external_student_financial_aid') {		
		xml.column(name:'school_id', value: username )		
		xml.column(name:'financial_aid_gpa', value: esfa.financialAidGpa)
		xml.column(name:'gpa_20_b_hrs_needed', value: esfa.gpaBHrsNeeded )
		xml.column(name:'gpa_20_a_hrs_needed', value: esfa.gpaAHrsNeeded )
		xml.column(name:'needed_for_67ptc_completion', value: esfa.neededFor67PtcCompletion )
		xml.column(name:'sap_status', value: esfa.sapStatus )
		xml.column(name:'fafsa_date', value: esfa.FAFSADate )		
		xml.column(name:'financial_aid_remaining', value: esfa.financialAidRemaining )
		xml.column(name:'original_loan_amount', value: esfa.originalLoanAmount )
		xml.column(name:'remaining_loan_amount', value: esfa.remainingLoanAmount )
	}
	
	xml.rollback() {
		xml.delete(tableName:'external_student_academic_program') {
			xml.where("school_id = '${username}'")
		}
	}	
}

def addToExternalStudentTranscript(xml, person) {
	def schoolId = getColumnValueByName(person, 'school_id')
	def username = getColumnValueByName(person, 'username')
	def est = getExternalStudentTranscriptBySchoolId(schoolId)

	if(est != null) {
		def hoursAttempted = Float.parseFloat(getColumnValueByName(est, 'credit_hours_attempted'))		
		def hoursEarned = Float.parseFloat(getColumnValueByName(est, 'credit_hours_earned'))
		def percentHours = (hoursEarned/hoursAttempted)*100.00
		
		xml.update(tableName:'external_student_transcript') {
			xml.column(name:'academic_standing', value: 'Good')
			xml.column(name:'credit_hours_not_completed', value: (hoursAttempted - hoursEarned))
			xml.column(name:'credit_completion_rate', value: percentHours.trunc(2))
			if(percentChance(10)) {
				xml.column(name:'current_restrictions', value: randFromCollection(['Dismissed', 'Fine Hold', 'Conduct']) )
			}
			xml.column(name:'gpa_trend_indicator', value: randFromCollection(['Up', 'Down', 'New', 'Same']))
			xml.where("school_id = '${username}'")
		}
		
		xml.rollback() {
			xml.update(tableName:'external_student_transcript') {			
				xml.column(name:'academic_standing', value: '')
				xml.column(name:'credit_hours_not_completed', value: 0)
				xml.column(name:'credit_completion_rate', value: 0)
				xml.column(name:'gpa_trend_indicator', value: '')
				xml.column(name:'current_restrictions', value: '')
				xml.where("school_id = '${username}'")
			}
		}
	}
}

def addExternalStudentTranscriptTerm(xml, person) {
	def username = getColumnValueByName(person, 'username')
	def estt = makeExternalStudentTranscriptTerm(person)
	
	xml.insert(tableName:'external_student_transcript_term') {
		xml.column(name:'school_id', value: username )		
		xml.column(name:'credit_hours_for_gpa', value: estt.creditHoursForGPA  )
		xml.column(name:'credit_hours_earned', value: estt.creditHoursEarned )
		xml.column(name:'credit_hours_attempted', value: estt.creditHoursAttempted )
		xml.column(name:'credit_hours_not_completed', value: estt.creditHoursNotCompleted )
		xml.column(name:'credit_completion_rate', value: estt.creditCompletionRate )
		xml.column(name:'total_quality_points', value: estt.totalQualityPoints )
		xml.column(name:'grade_point_average', value: estt.gradePointAverage )
		xml.column(name:'term_code', value: estt.termCode )
	}
	
	xml.rollback() {
		xml.delete(tableName:'external_student_academic_program') {
			xml.where("school_id = '${username}'")
		}
	}
}

def addToEarlyAlert(xml, person, courses) {
	def schoolId = getColumnValueByName(person, 'school_id')
	def username =  getColumnValueByName(person, 'username')
	def firstName =  getColumnValueByName(person, 'first_name')
	def sspPerson = getPersonFromSchoolId(username) //this might need to be changed to schoolId later
	def earlyAlerts = getEarlyAlertsByPersonId(getColumnValueByName(sspPerson, 'id'))
	
	earlyAlerts.each { earlyAlert ->
		ExternalCourse course = randFromCollection(courses)
		def courseTermCode = "${randFromCollection(term_codes)}${randFromCollection([2012, 2013, 2014, 2015, 2016])}"
		
		xml.update(tableName:"early_alert") {
			xml.column(name:"course_name",  value: course.formattedCourse)
			xml.column(name:"course_title",  value: course.title)
			xml.column(name:"early_alert_reason_other_description", value: randFromCollection(early_alert_reasons) )
			xml.column(name:"comment", value: "Please looking into this issue I noticed with ${firstName}." )
			xml.column(name:"early_alert_suggestion_other_description", value: randFromCollection(early_alert_suggestions) )
			xml.column(name:"course_term_code", value: courseTermCode )

			xml.where("id = '${getColumnValueByName(earlyAlert, 'id')}'")
		}
	}
}

def replaceExternalStudentTranscriptCourse(xml, person, courses, faultyId) {
	
	def schoolId = getColumnValueByName(person, 'school_id')
	def username =  getColumnValueByName(person, 'username')
	xml.delete(tableName: 'external_student_transcript_course') {
		xml.where("school_id = '${username}'")	
	}
	
	rand(18).times {
		ExternalCourse course = randFromCollection(courses)
		def sectionNumber = String.format('%03d', rand(999))
		
		xml.insert(tableName:'external_student_transcript_course') {		
			xml.column(name:'school_id', value: username )	
			xml.column(name:'faculty_school_id', value: faultyId )	
			xml.column(name:'first_name', value: getColumnValueByName(person, 'first_name') )	
			xml.column(name:'middle_name', value: getColumnValueByName(person, 'middle_name') )	
			xml.column(name:'last_name', value: getColumnValueByName(person, 'last_name') )	
			xml.column(name:'term_code', value: course.termCode )	
			xml.column(name:'section_code', value: "${course.formattedCourse}-${sectionNumber}" )	
			xml.column(name:'section_number', value: sectionNumber )	
			xml.column(name:'formatted_course', value: course.formattedCourse )	
			xml.column(name:'subject_abbreviation', value: course.subjectAbbreviation )	
			xml.column(name:'number', value: course.number )	
			xml.column(name:'title', value: course.title )	
			xml.column(name:'description', value: course.description )	
			xml.column(name:'grade', value: randFromCollection(['A', 'B', 'C', 'D', 'F']) )	
			xml.column(name:'credit_earned', value: course.maxCreditHours )	
			xml.column(name:'credit_type', value: randFromCollection(['Institutional', 'Institutional', 'Institutional', 'Institutional', 'Transfer', 'Proficiency']) )	
			xml.column(name:'audited', value: percentChance(15)? 'Y': 'N' )	
			xml.column(name:'status_code', value: 'E' )	
		}	
	}
	
	xml.rollback() {
		xml.delete(tableName:'external_student_transcript_course') {
			xml.where("school_id = '${username}'")
		}
	}
	
}

////////////////////////////////////
// Make Object Methods
////////////////////////////////////

def makeExternalStudentFinancialAid(person) {
	
	def esfa = new ExternalStudentFinancialAid()
	
	def schoolId = getColumnValueByName(person, 'school_id')
	def username =  getColumnValueByName(person, 'username')
	
	def thousand = (rand(9) + 1) * 1000 as Integer
	def hundred = (rand(9) + 1) * 100 as Integer
	def fifty =  (rand(2) == 0? 0 : 50) as Integer
	
	def totalAid = thousand+hundred+fifty
	def usedAid = rand(totalAid)
	def finAidRemaining = totalAid - usedAid
	def gpa = (rand(3) + 1) + (new Random().nextDouble()) as Double
	
	esfa.schoolId = username
	esfa.financialAidGpa = gpa.trunc(2)
	esfa.gpaBHrsNeeded = 3 * (rand(3)+1)
	esfa.gpaAHrsNeeded = 3 * (rand(esfa.gpaBHrsNeeded/3)+1)
	esfa.neededFor67PtcCompletion = 3 * (rand(4)+1)
	esfa.sapStatus = 'Y'
	esfa.FAFSADate = FAFSA_DATE
	esfa.financialAidRemaining = (usedAid as Float).trunc(2)
	esfa.originalLoanAmount = (totalAid as Float).trunc(2)
	esfa.remainingLoanAmount = (finAidRemaining as Float).trunc(2)
	
	return esfa
}

def makeExternalStudentTranscriptTerm(person) {
	def estt = new ExternalStudentTranscriptTerm()
	
	def username =  getColumnValueByName(person, 'username')
	
	def creditHours = randFromCollection([9, 12, 15, 18, 21])
	def creditHoursIncluded = (percentChance(15)? creditHours-3 : creditHours)
	def qualityPoints = 0 	
	/*
	 Quality points = 
	 a=4, b=3, c=2, d=1, f=0 for each credit completed.
	 for each class taken. Each class is 3 credit hours according
	 to the above implementation 
	*/
	for(credit in 1..creditHours) {	
		def chance = rand(100) + 1
		def add = 0
		if(chance < 5) {
			add = 0
		} else if (chance < 10) {
			add = 1
		} else if (chance < 45) {
			add = 2
		} else if (chance < 70) {
			add = 3
		} else if (chance <= 100) {
			add = 4
		}

		qualityPoints += add
	}
		
	estt.schoolId = username
	estt.creditHoursForGPA = creditHoursIncluded
	estt.creditHoursEarned = creditHoursIncluded
	estt.creditHoursAttempted = creditHours
	estt.creditHoursNotCompleted = creditHours - creditHoursIncluded
	estt.creditCompletionRate = creditHoursIncluded/creditHours
	estt.totalQualityPoints = qualityPoints
	estt.gradePointAverage = (qualityPoints/creditHours as Float).trunc(2)
	estt.termCode = randFromCollection(term_codes) + (rand(5) + 2012) 
	
	return estt
}

////////////////////////////////////
// Classes
////////////////////////////////////

class ExternalStudentFinancialAid {
	def schoolId
	def financialAidGpa
	def gpaBHrsNeeded
	def gpaAHrsNeeded
	def neededFor67PtcCompletion
	def sapStatus
	def FAFSADate
	def financialAidRemaining
	def originalLoanAmount
	def remainingLoanAmount
}

class ExternalStudentTranscriptTerm {
	def schoolId
	def creditHoursForGPA
	def creditHoursEarned
	def creditHoursAttempted
	def creditHoursNotCompleted
	def creditCompletionRate
	def totalQualityPoints
	def gradePointAverage
	def termCode
}
	

class ExternalCourse {
	String code
	String formattedCourse
	String subjectAbbreviation
	String number
	String title
	String description
	Integer maxCreditHours
	Integer minCreditHours
	String isDev
	String termCode
}
////////////////////////////////////
// Data Access Methods
////////////////////////////////////
		
def getAllExternalPersons() {
	getAllByTableName('external_person', CHANGESET)
}

def getAllExternalStudentAcademicProgram() {
	getAllByTableName('external_student_academic_program', CHANGESET)
}

def getAllExternalStudentTranscript() {
	getAllByTableName('external_student_transcript', CHANGESET)
}

def getAllEarlyAlerts() {
	getAllByTableName('early_alert', CHANGESET)
}

def getAllPersons() {
	getAllByTableName('person', CHANGESET)
}

def getAllExternalCourses() {
	getAllByTableName('external_course', R200B2_DATA)
}

def getAllExternalTerms() {
	getAllByTableName('external_term', R200B2_DATA)
}

def getAllExternalCourseTerms() {
	getAllByTableName('external_course_term', R200B2_DATA)
}

def getEarlyAlertsByPersonId(personId) {
	getEntitiesByColumnValue( getAllEarlyAlerts(), 'person_id', personId)
}

def getExternalStudentAcademicProgramBySchoolId(schoolId) {
	getEntityBySchoolId( getAllExternalStudentAcademicProgram(), schoolId)
}

def getExternalStudentTranscriptBySchoolId(schoolId) {
	getEntityBySchoolId( getAllExternalStudentTranscript(), schoolId)
}

def getPersonFromSchoolId(schoolId) {
	getEntityBySchoolId(getAllPersons(), schoolId)
}

def getAllByTableName(tableName, data) {
	data.insert.findAll{ it.@tableName == tableName }
}

def getEntityById(entities, id) {
	getEntityByColumnValue(entities, 'id', id)
}

def getEntityBySchoolId(entities, schoolId) {
	getEntityByColumnValue(entities, 'school_id', schoolId)
}

def getEntityByColumnValue(entities, column, value) {
	entities.find { 
		it.column.find{ it.@name == column }.@value == value
	}
}

def getEntitiesByColumnValue(entities, column, value) {
	def entitiesByValue = entities.findAll { 
		it.column.find{ it.@name == column }.@value == value
	}
}

def getColumnValueByName(entity, name) {
	def value = null
	
	if(entity != null) {
		value = entity.column.find{ it.@name == name}?.@value
	}
	
	value
}

////////////////////////////////////
// Utility Methods
////////////////////////////////////

def isStudent(person) {

	if(isNotFacultyOrStudent(person) || getColumnValueByName(person, 'office_location') != null) {
		return false
	}
	
	return true
}

def isFaculty(person) {

	if(isNotFacultyOrStudent(person) || getColumnValueByName(person, 'office_location') == null) {
		return false
	}
	
	return true
}


def isNotFacultyOrStudent(person) {
	def username = getColumnValueByName(person, 'username')
	for(role in ["staff", "manager", "dev", "adv"]) {
		if(username.contains(role)) {
			return true
		}
	}
	return false;
}

def parseCourses() {
	def externalCourses = []	
	
	getAllExternalCourses().each { xmlCourse ->
		ExternalCourse course = new ExternalCourse()
		def courseCode = getColumnValueByName(xmlCourse, 'code')
		def courseTerms = getAllExternalCourseTerms();
		def term = getEntityByColumnValue(courseTerms, 'course_code', courseCode)
		
		course.code = courseCode
		course.formattedCourse = getColumnValueByName(xmlCourse, 'formatted_course')
		course.subjectAbbreviation = getColumnValueByName(xmlCourse, 'subject_abbreviation')
		course.number = getColumnValueByName(xmlCourse, 'number')
		course.title = getColumnValueByName(xmlCourse, 'title')
		course.description = getColumnValueByName(xmlCourse, 'description')
		course.maxCreditHours = getColumnValueByName(xmlCourse, 'max_credit_hours')
		course.minCreditHours = getColumnValueByName(xmlCourse, 'min_credit_hours')
		course.isDev = getColumnValueByName(xmlCourse, 'is_dev')
		course.termCode = getColumnValueByName(term, 'term_code')
		
		externalCourses.add(course)
	}
	
	externalCourses
}

/** Returns a random value from a List, Map, or Array
 * 
 */
def randFromCollection(collection) {
	if(collection instanceof List) {
		return collection[rand(collection.size)]
	} else if(collection instanceof Map) {
		def keySet = (collection.keySet() as List)
		def randKey = keySet[rand(keySet.size())]
		return collection[randKey]
	}
	return collection[rand(collection.length)]
}

/**returns true of false whether a random chance falls within the percentage.
 * For instance, pass in percentChance(15) for a 15% chance of returning true. 
 */
def percentChance(chance) {
	return (rand(100) + 1) < chance
}

/**Helper method to make getting a random number less verbose.
 * 
 */
def rand(ceiling) {
	new Random().nextInt(ceiling as Integer)
}





