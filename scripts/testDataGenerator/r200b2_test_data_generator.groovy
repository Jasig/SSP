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
/*
 * 
 * Generates an xml liquibase change set for testing large database sets.
 * Usage:  
 * BASE_LOCATION_CHANGESET set this to either TEST_LOCATION_CHANGESET or FULL_DATA_BASE_LOCATION_CHANGESET depending on which datachange set you wish to generate
 * fileName is the name of the changeset file to be generated
 * BASE_LOCATION_UPORTAL_USERS set the appropriate location for you machine
 * author YOU
 * filed description:  this is the description of the changeset that is stored in the databasechangelog table
 */

import java.util.Calendar;
import java.util.Date;
import java.util.UUID

import org.jasig.ssp.service.reference.ElectiveService;

import static Constants.*

class Constants{

	static final def eol = System.properties.'line.separator'
	/* Change Sets, change as needed */
	static final fileName = 'r200b2_demo_data.xml';
	static final TEST_LOCATION_CHANGESET = null // file path to save location, eg: './src/test/resources/org/jasig/ssp/database/testDataChangesets/'
	static final FULL_DATA_BASE_LOCATION_CHANGESET = './src/main/resources/org/jasig/ssp/database/demoDataChangeset/'
	static final BASE_LOCATION_CHANGESET = FULL_DATA_BASE_LOCATION_CHANGESET
	static final author = 'demo.data.script.generator'
	static final fileDescription = 'Adding R2.0.0b2 Demo Data Set'
	
	static final FIRST_TERM_YEAR = 2012
	static final LAST_TERM_YEAR = 2016
	
	static final COURSES_PER_MAIN_TERM = 40 //SP and FA
	static final COURSES_PER_OFF_TERM = 10 //SU and WN
	
	static final HONORS_PERCENTAGE = 15 // out of 100 (e.g. 15 = 15% chance)
	static final DISTANCE_LEARNING_PERCENTAGE = 15 // out of 100 (e.g. 15 = 15% chance)
	
	static final DATE_FORMATTER = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	
	static final base_created_date = "2012-08-20T00:00:00"
	
	static final term_codes = ['SP', 'SU', 'FA', 'WN']

	static final term_names = [
		'Spring',
		'Summer',
		'Fall',
		'Winter'		
	]
	
	static final term_name_codes = [
		'Spring':'SP',
		'Summer':'SU',
		'Fall':'FA',		
		'Winter':'WN',
			
		'SP':'Spring',
		'SU':'Summer',
		'FA':'Fall',
		'WN':'Winter',		
	]
	
	static final term_dates = [
		'FA_START':"%s-08-22T00:00:00",
		'FA_END':"%s-12-06T00:00:00",
		
		'WN_START':"%s-10-16T00:00:00",
		'WN_END':"%s-12-06T00:00:00",
		
		'SP_START':"%s-01-07T00:00:00",
		'SP_END':"%s-04-30T00:00:00",
		
		'SU_START': "%s-05-20T00:00:00",
		'SU_END': "%s-07-12T00:00:00"
	]
			
	static final colorIds = [
	//	"0ada03c8-6ec0-49c8-987c-e33a4d439ee3", //"ORANGE" Default for Important Courses, do not include since there is actually meaning to this
	//	"f7a4b243-a1bd-4c28-b557-fc1f6b8439eb", //"YELLOW" Default for Transcripted courses, do not include since there is actually meaning to this
		"d0770680-f0ef-4dab-ad0b-7f175ede779d", //"RED"
		"ff5733f2-ba81-4daa-8c78-50915c935506", //"PURPLE"
		"64502a6d-c4f6-4850-87b9-856aab5955de", //"GREEN"
		"5985772a-cda6-4a08-b76d-27b0e4e601f2", //"BROWN"
		"27d1dfde-2f16-4365-9315-32c32998d84e", //"FUCHSIA"
		"2615ab88-cc09-48d4-a404-0774d71bf8a4", //"GRAY"
		"8dad87be-936b-450d-8f4a-f733b679a7dd", //"LT_BLUE"
		"43cbb2d1-4f5b-4dc7-ad71-f59df121d8fb", //"LT_GREEN"
		"2f70bc51-7173-4d83-9c7a-17cca2e8d4db", //"PINK"
		"9a6b915b-91c8-49d5-8e1a-47c67c5aed59", //"SALMON"
		"93e117d8-bd62-4bff-a812-94cae3a65a12", //"TAN"
		"e0432f26-ba18-43bd-9475-d3cd899e2f8c", //"VIOLET"
		"7f000101-3e62-19f8-813e-626ce6040000", //"MAROON"
		"8442bfcc-b44c-4b70-a99c-2b67691568ae" //"AQUA"

	]
	
	static final electiveIds = [
		'GEN':'9a07ced6-7b3a-4926-8a88-ba23f998fc46',
		'PRG':'3bdda584-f7a2-4402-8863-4b5bd8273009',
		'LNG':'db60bb67-a7e3-4610-a9af-3a0e4d48b58b',
		'ART':'bb0abe0b-d6f8-4987-8fff-27020dc9fe35', 
		'HUM':'3122e73b-dd86-4f23-af05-22c2abd93414',
		'ENG':'989a9679-d925-4475-acbb-8bd9d81fcb04'		
	]
	
	static final tagIds = [
		'DL':'deac8cef-f87a-441e-ae64-495f1a78806b',
		'TAG':'2f87ea59-70b5-43f2-8387-664173806298',
		'HON':'4c38893f-cdd6-4b18-a685-fd3cf8e64cbc',
	]
	
	static final courseSuffixes = [
		'Introduction to',
		'Foundational',
		'College level',
		'Basics of',
		'General',
		'Laboratory Section:',
		'Principles of',
		'Fundamentals of',
		'Survey',
		'Elementary',
		'Special Topic:',
		'Exploratory',		
		'Inquiry into',
		'International',
		'Topic:',
		'Enterprise',
		'Applied',
		'Analytical',
		'Honors',
		'Higher',
		'Advanced',		
		'Individualized',
		'Experimental',
		'Quantitative',
		'Creative',
		'Investigation',
		'Entrepreneurial',
		'Integrative'
	]	
	
	static final courseAbbreviations = [
		'MAT', 'PHY', 'CHM', 'CST', 'PSY', 'AES', 'HST', 'ECN',
		'BIO', 'COM', 'AST', 'IEE', 'SCL', 'PHL', 'EEE', 'MIC',
		'LIT', 'ENG', 'FLM', 'GEO', 'POL', 'FIN', 'EDU'
	]
	
	static final courseDescriptions = [	
		'MAT':'Mathematics',
		'PHY':'Physics',
		'CHM':'Chemistry',
		'CST':'Computing',
		'PSY':'Psychology',
		'AES':'Aerospace',
		'HST':'History',
		'ECN':'Economics',
		'BIO':'Biology',
		'COM':'Communications',
		'AST':'Astronomy',
		'IEE':'Industrial Engineering',
		'SCL':'Sociology',
		'PHL':'Philosophy',
		'EEE':'Electrical Engineering',
		'MIC':'Microbiology',
		'LIT':'Literature',
		'ENG':'English',
		'FLM':'Film',
		'GEO':'Geography',
		'POL':'Political Science',
		'FIN':'Finance',
		'EDU':'Education'	
	]
	
	static final arts_programs = [
		'PSY', 'HST', 'ECN', 'COM', 'SCL', 'PHL', 'LIT', 'ENG', 
		'FLM', 'GEO', 'POL', 'FIN', 'EDU' 
	]
	
	static final programs = [
		'MAT-AS':'Associates of Science in Mathematics',
		'PHY-AS':'Associates of Science in Physics',
		'CHM-AS':'Associates of Science in Chemistry',
		'CST-AS':'Associates of Science in Computing',
		'PSY-AA':'Associates of Arts in Psychology',
		'AES-AS':'Associates of Science in Aerospace',
		'HST-AA':'Associates of Arts in History',
		'ECN-AA':'Associates of Arts in Economics',
		'BIO-AS':'Associates of Science in Biology',
		'COM-AA':'Associates of Arts in Communications',
		'AST-AS':'Associates of Science in Astronomy',
		'IEE-AS':'Associates of Science in Industrial Engineering',
		'SCL-AA':'Associates of Arts in Sociology',
		'PHL-AA':'Associates of Arts in Philosophy',
		'EEE-AS':'Associates of Science in Electrical Engineering',
		'MIC-AS':'Associates of Science in Microbiology',
		'LIT-AA':'Literature',
		'ENG-AA':'Associates of Arts in English',
		'FLM-AA':'Associates of Arts in Film',
		'GEO-AA':'Associates of Arts in Geography',
		'POL-AA':'Associates of Arts in Political Science',
		'FIN-AA':'Associates of Arts in Finance',
		'EDU-AA':'Associates of Arts in Education',
		
		'MAT-BS':'Bachelors of Science in Mathematics',
		'PHY-BS':'Bachelors of Science in Physics',
		'CHM-BS':'Bachelors of Science in Chemistry',
		'CST-BS':'Bachelors of Science in Computing',
		'PSY-BA':'Bachelors of Arts in Psychology',
		'AES-BS':'Bachelors of Science in Aerospace',
		'HST-BA':'Bachelors of Arts in History',
		'ECN-BA':'Bachelors of Arts in Economics',
		'BIO-BS':'Bachelors of Science in Biology',
		'COM-BA':'Bachelors of Arts in Communications',
		'AST-BS':'Bachelors of Science in Astronomy',
		'IEE-BS':'Bachelors of Science in Industrial Engineering',
		'SCL-BA':'Bachelors of Arts in Sociology',
		'PHL-BA':'Bachelors of Arts in Philosophy',
		'EEE-BS':'Bachelors of Science in Electrical Engineering',
		'MIC-BS':'Bachelors of Science in Microbiology',
		'LIT-BA':'Bachelors of Arts in Literature',
		'ENG-BA':'Bachelors of Arts in English',
		'FLM-BA':'Bachelors of Arts in Film',
		'GEO-BA':'Bachelors of Arts in Geography',
		'POL-BA':'Bachelors of Arts in Political Science',
		'FIN-BA':'Bachelors of Arts in Finance',
		'EDU-BA':'Bachelors of Arts in Education'
	]
	
	def static final divisions = [
		"DIV_HA":"Division of Arts & Humanities",
		"DIV_BNS":"Division of Behavior & Natural Science",
		"DIV_B":"Division of Business",
		"DIV_E":"Division of Education",
		"DIV_HS":"Division of Health Sciences",
		"DIV_EM":"Division of Engineering & Mathematics"		
	]
	
	def static final departments = [
		"DEP_HS":"Department of Health Services",
		"DEP_AS":"Department of Academic Services",
		"DEP_AC":"Division of Academic Counseling",
		"DEP_DS":"Division of Disability Support",
		"DEP_IS":"Division of International Studies",
		"DEP_LR":"Division of Learning Resources", 
		"DEP_T":"Department of Technology"		
	]
	
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
						generateDivisions(xml)
						generateDepartments(xml)
						generatePrograms(xml)
						def tagIds = generateTags(xml)	
						def electives = generateElectives(xml) 						
						def terms = generateExternalTerms(xml)
						def courses = generateExternalCoursesAndTermAssoc(xml, terms, tagIds, electives)
					 }
}
					 
def generateDivisions(xml) {
	addCodeNamePair(xml, 'external_division', divisions)
}
	
def generateDepartments(xml) {
	addCodeNamePair(xml, 'external_department', departments)
}

def generatePrograms(xml) {
	addCodeNamePair(xml, 'external_program', programs)
}

def generateTags(xml) {

	xml.insert(tableName:'tag') {
		xml.column(name:"id", value:tagIds['DL'])
		xml.column(name:"name", value:"Distance Learning")
		xml.column(name:"code", value:"DL")
		xml.column(name:"description", value:"Distance Learning Course")
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, null)
	}
	
	xml.insert(tableName:'tag') {
		xml.column(name:"id", value:tagIds['TAG'])
		xml.column(name:"name", value:"Transfer Guarantee")
		xml.column(name:"code", value:"TAG")
		xml.column(name:"description", value:"Transfer Assurance Guarantee for the state")
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, null)
	}

	xml.insert(tableName:'tag') {
		xml.column(name:"id", value:tagIds['HON'])
		xml.column(name:"name", value:"Honors")
		xml.column(name:"code", value:"HON")
		xml.column(name:"description", value:"Honors Course")
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, null)
	}
	
	xml.rollback{
		tagIds.each { tag ->
			xml.delete(tableName:'tag'){
				xml.where("id='${tag}'")
			}
		}
	}	
}

def generateElectives(xml) {
	
	def electives = new ArrayList<Elective>()
	def sortOrder = 0
	def electiveMap = [
		'GEN':'General Education Elective',
		'PRG':'Program Elective',
		'LNG':'Modern Language Elective',
		'ART':'Arts Elective',
		'HUM':'Humanities Elective',
		'ENG':'Engineering Elective'
	]
	
	electiveMap.each{ code, name ->
		def elective = makeElective(name, code, sortOrder)
		
		xml.insert(tableName:'elective') {
			xml.column(name:"id", value:elective.id)
			xml.column(name:"name", value:elective.name)
			xml.column(name:"description", value:elective.description)
			xml.column(name:"code", value:elective.code)
			xml.column(name:"color_id", value:elective.colorId)
			xml.column(name:"sort_order", value:elective.sortOrder)
			addCreatedModified(xml, null, null)
			addObjectStatus(xml, null)
		}
		electives.add(elective)
		sortOrder++
	}
	
	xml.rollback {
		electives.each{ elective ->
			xml.delete(tableName:'elective'){
				xml.where("id='${elective.id}'")
			}			
		}
	}
	return electives
}
					 
def generateExternalTerms(xml) {	
	def terms = new ArrayList<GroovyExternalTerm>()
		
	for(year in FIRST_TERM_YEAR..LAST_TERM_YEAR) {
		for(code in term_codes) {
			def term = makeTerm(year, code)	
			terms.add(term)
			xml.insert(tableName:'external_term') {
				xml.column(name:"name", value:term.name)
				xml.column(name:"code", value:term.code)
				xml.column(name:"start_date", value:DATE_FORMATTER.format(term.startDate))
				xml.column(name:"end_date", value:DATE_FORMATTER.format(term.endDate))
				xml.column(name:"report_year", value:term.reportYear)
			}			
		}
	}
	
	xml.rollback{
		terms.each { term ->
			xml.delete(tableName:'external_term'){
				xml.where("code='${term.code}'")
			}
		}
	}
	
	return terms
}


def generateExternalCoursesAndTermAssoc(xml, terms, tagIds, electiveIds) {
	def courses = new ArrayList<GroovyExternalCourse>()
	
	terms.each { term ->	
		for(i in 0..(isMainTerm(term)? COURSES_PER_MAIN_TERM : COURSES_PER_OFF_TERM) ) {

			def course = makeExternalCourse(courses) 			
			addExternalCourse(xml, course)
			addExternalCourseTerm(xml, course, term)
			
			def programCode = "${course.subjectAbbreviation}-${isArts(course)? 'BA' : 'BS'}"
			addExternalCourseProgram(xml, course, programCode)
			
			if(Integer.parseInt(course.number) < 300) {				
				def programCodeAssoc = "${course.subjectAbbreviation}-${isArts(course)? 'AA' : 'AS'}"
				addExternalCourseProgram(xml, course, programCodeAssoc)				
			}
			
			if(course.isHonors) {
				xml.insert(tableName:'external_course_tag') {
					xml.column(name:"course_code", value:course.code)
					xml.column(name:"tag", value:'HON')
				}
			}
			
			if(course.isDistanceLearning) {
				xml.insert(tableName:'external_course_tag') {
					xml.column(name:"course_code", value:course.code)
					xml.column(name:"tag", value:'DL')
				}
			}
			
			courses.add(course)
		}
	}

	xml.rollback{
		courses.each { course ->
			xml.delete(tableName:'external_course'){
				xml.where("code='${course.code}'")
			}
			
			xml.delete(tableName:'external_course_term'){
				xml.where("course_code='${course.code}'")
			}
			
			xml.delete(tableName:'external_course_program'){
				xml.where("course_code='${course.code}'")
			}
			xml.delete(tableName:'external_course_tag'){
				xml.where("course_code='${course.code}'")
			}
		}
	}
	
	return courses
}

def makeTerm(year, code) {	
	GroovyExternalTerm term = new GroovyExternalTerm()
	
	term.name = "${term_name_codes[code]} ${year}"
	term.code = "${code}${year}"
	term.startDate = parseTermDate("${code}_START", year)
	term.endDate = parseTermDate("${code}_END", year)
	term.reportYear = year
	
	return term
}

def makeExternalCourse(courses) {
	def course = new GroovyExternalCourse()	
	
	def abbreviation = getRandomValueFromCollection(courseAbbreviations)
	def number = new Random().nextInt(399)+101
	def code = "${abbreviation}-${number}"
	def numMatchingCourses = countMatchingCourses(courses, "${abbreviation}${number}")
	def courseBuzzWord = getRandomValueFromCollection(courseSuffixes)
	
	if(numMatchingCourses > 0) {
		code = code + "-${numMatchingCourses}"
	}
	
	course.code = code
	course.formattedCourse = "${abbreviation}${number}"
	course.subjectAbbreviation = abbreviation
	course.number = number
	course.title = "${courseBuzzWord} ${courseDescriptions[abbreviation]}"
	course.description = "${courseBuzzWord} ${courseDescriptions[abbreviation]}"
	course.maxCreditHours = getRandomIntWithinRange(3, 4)
	course.minCreditHours = getRandomIntWithinRange(2, 3)
	course.isDev = 'N'
	
	if((new Random().nextInt(100)) < HONORS_PERCENTAGE 
	   || courseBuzzWord == 'Honors' ) {
		course.isHonors = true
	} else {
		course.isHonors = false
	}
	
	if((new Random().nextInt(100)) < DISTANCE_LEARNING_PERCENTAGE) {
		course.isDistanceLearning = true
	} else {
		course.isDistanceLearning = false
	}

	 return course	
}

def makeElective(name, code, sortOrder) {
	def elective = new Elective()
	def aOrAn = startsWithVowel(name)? 'An': 'A'
	
	elective.id = UUID.fromString(electiveIds[code])
	elective.name = name	
	elective.description = "${aOrAn} ${name}"
	elective.code = code
	elective.colorId = UUID.fromString(getRandomValueFromCollection(colorIds))
	elective.sortOrder = sortOrder
	
	return elective
}

void addCodeNamePair(xml, tableName, collection) {
	collection.each {code, name ->
		xml.insert(tableName:tableName) {
			xml.column(name:"code", value:code)
			xml.column(name:"name", value:name)
		}
	}
	
	xml.rollback{
		collection.each {code, name ->
			xml.delete(tableName:tableName){
				xml.where("code='${code}' AND name='${name}'")
			}
		}
	}
}

void addExternalCourse(xml, course) {
	xml.insert(tableName:'external_course') {
		xml.column(name:"code", value:course.code)
		xml.column(name:"formatted_course", value:course.formattedCourse)
		xml.column(name:"subject_abbreviation", value:course.subjectAbbreviation)
		xml.column(name:"number", value:course.number)
		xml.column(name:"title", value:course.title)
		xml.column(name:"description", value:course.description)
		xml.column(name:"max_credit_hours", value:course.maxCreditHours)
		xml.column(name:"min_credit_hours", value:course.minCreditHours)
		xml.column(name:"is_dev", value:course.isDev)
	}
}

void addExternalCourseTerm(xml, course, term) {
	xml.insert(tableName:'external_course_term') {
		xml.column(name:"course_code", value:course.code)
		xml.column(name:"term_code", value:term.code)
	}
}

void addExternalCourseProgram(xml, course, programCode) {
	xml.insert(tableName:'external_course_program') {
		xml.column(name:"course_code", value:course.code)
		xml.column(name:"program_code", value:programCode)
		xml.column(name:"program_name", value:programs[programCode])
	}
}

void addCreatedModified(xml, created_by, created_date){
	xml.column(name:"created_date",  value:created_date == null ? base_created_date.toString() : created_date)
	xml.column(name:"modified_date",  value:created_date == null ? base_created_date.toString() : created_date)
	xml.column(name:"created_by",  value:created_by == null ? "58ba5ee3-734e-4ae9-b9c5-943774b4de41": created_by)
	xml.column(name:"modified_by",  value:created_by == null ? "58ba5ee3-734e-4ae9-b9c5-943774b4de41": created_by)
}

void addObjectStatus(xml, objectStatus)
{
	xml.column(name:"object_status",  value:"${objectStatus?:1}")
}


def getRandomValueFromCollection(collection) {
	if(collection instanceof List) {
		return collection[new Random().nextInt(collection.size)]
	} else if(collection instanceof Map) {
		def keySet = (collection.keySet() as List)
		def randKey = keySet[new Random().nextInt(keySet.size())]
		return collection[randKey]
	}
	return collection[new Random().nextInt(array.length)]
}

def getRandomIntWithinRange(min, max) {
	new Random().nextInt(max - min + 1) + min
}

def parseTermDate(termKey, year) {
	def termDate = String.format(term_dates[termKey], year)
	return DATE_FORMATTER.parse(termDate)
}

def isMainTerm(term) {
	return term.code.startsWith("SP") || term.code.startsWith("FA")
}

def countMatchingCourses(courses, formattedCourse) {
	def count = 0
	courses.each { course ->
		if(course.formattedCourse == formattedCourse) {
			count++
		}
	}
	return count
}

def startsWithVowel(String string) {
	def vowels = ['a', 'e', 'i', 'o', 'u']
	return vowels.contains(string.toLowerCase().charAt(0))
}

def isArts(course) {
	return arts_programs.contains(course.subjectAbbreviation)
}

class Elective {
	UUID id
	String name
	String description
	String code
	UUID colorId
	Integer sortOrder	
}

class GroovyExternalTerm {
	String name
	String code
	Date startDate
	Date endDate
	Integer reportYear
}

class GroovyExternalCourse {
	String code
	String formattedCourse
	String subjectAbbreviation
	String number
	String title
	String description
	Integer maxCreditHours
	Integer minCreditHours
	String isDev
	
	//script specific variables, not to persist
	Boolean isHonors
	Boolean isDistanceLearning	
}

