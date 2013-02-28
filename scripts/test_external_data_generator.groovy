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
 * Generates an xml liquibase change set for testing many terms.
 * Usage:
 * BASE_LOCATION_CHANGESET set this to either TEST_LOCATION_CHANGESET or FULL_DATA_BASE_LOCATION_CHANGESET depending on which datachange set you wish to generate
 * fileName is the name of the changeset file to be generated
 * WRITE_UPORTAL_USERS set to true if you want to generate the user.xml files to register the users with uportal (coaches only)
 * BASE_LOCATION_UPORTAL_USERS set the appropriate location for you machine
 * author YOU
 * filed description:  this is the description of the changeset that is stored in the databasechangelog table
 * TOTAL_NUMBER_OF_COACHES
 * STUDENT_MULTIPLIER set to 1 if you want all coaches to have the same number of students.
 * BASE_NUMBER_OF_STUDENTS if STUDENT_MULTIPLIER is one all coaches will have this number of students.
 *
 */

import java.util.Calendar;
import java.util.Date;
import java.util.UUID
import org.jasig.ssp.model.Person
import static Constants.*
class Constants{

	/* Change Sets, change as needed */
	static final fileName = "i000007-test.xml";
	static final TEST_LOCATION_CHANGESET = './src/test/resources/org/jasig/ssp/database/integrationchangesets/'
	static final FULL_DATA_BASE_LOCATION_CHANGESET = './src/main/resources/org/jasig/ssp/database/testingintegrationchangesets/'
	static final BASE_LOCATION_CHANGESET = TEST_LOCATION_CHANGESET
	static final author = "james.stanley"
	static final fileDescription = "Adding Set of External Data"
	
	static final def eol = System.properties.'line.separator'
	
	static final ADVISOR_NAME = "coach"
	static final STUDENT_NAME = "student"
	static final TOTAL_NUMBER_OF_COACHES = 2 as Integer
	static final STUDENT_MULTIPLIER = 4 as Integer //use this to set the multiplier that determines the number of students a coach has. (coachIndex * STUDENT_MULTIPLIER)
	static final BASE_NUMBER_OF_STUDENTS = 5 as Integer
	
	static final String[] SUBJECT_ABBREVIATIONS = ["MATH101", "BIO101", "CHEM101"]
	static final String[] COURSE_NUMBER = ["1001M", "1001B","1001C"]
	static final String[] FORMATTED_COURSE = ["Freshman Math", "Freshman Biology", "Freshman Chemistry"]
	static final String[] SECTION_NUMBER =["A1", "B2", "C3"]
	static final String TERM_CODE = "FA12"
	static final Integer NUMBER_OF_SUBJECTS = 3
	static final Integer CREDIT_VALUE = "4"
	static final String FIRST_NAME = "test"
	static final String MIDDLE_NAME = "Mumford"
	static final String STATUS_CODE = "WK"
	static final String DEGREE_CODE = "LA_SCI"
	static final String DEGREE_NAME = "Liberal Arts Science Degree"
	static final Double CREDIT_HOURS_FOR_GPA = 30.2
	static final Double CREDIT_HOURS_EARNED = 22
	static final Double CREDIT_HOURS_ATTEMPTED = 18.8
	static final Double TOTAL_QUALITY_POINTS = 221
	static final Double GRADE_POINT_AVERAGE = 3.9

}


def writer = new FileWriter(BASE_LOCATION_CHANGESET + fileName)
def xml = new groovy.xml.MarkupBuilder(writer) as groovy.xml.MarkupBuilder
writer << '<?xml version="1.0" encoding="UTF-8" standalone="no"?>' << eol
writer << getXmlLicense() << eol
xml.databaseChangeLog( xmlns : "http://www.liquibase.org/xml/ns/dbchangelog"
					 , "xmlns:xsi" : "http://www.w3.org/2001/XMLSchema-instance"
					 , "xsi:schemaLocation" : "http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd"
					 ) {
					 changeSet(author:author, id:fileDescription) {
						 
						 for(Integer i = 1; i <= TOTAL_NUMBER_OF_COACHES; i++){
							 coachName = ADVISOR_NAME + i
							 facultySchoolId = coachName
							 for(Integer l = 0; l < NUMBER_OF_SUBJECTS; l++) {
								 subjectAbbreviation = SUBJECT_ABBREVIATIONS[l]
								 number = COURSE_NUMBER[l]
								 formattedCourse = FORMATTED_COURSE[l]
								 sectionCode = SECTION_NUMBER[l] + l
								 sectionNumber = SECTION_NUMBER[l] + l
								 title = "title" + FORMATTED_COURSE[l]
								 description = "description" + FORMATTED_COURSE[l]
								 
								 
								 creditValue = CREDIT_VALUE
								 addExternalCourseSection(xml,
									 formattedCourse,
									 subjectAbbreviation,
									 number,
									 sectionNumber,
									 creditValue,
									 TERM_CODE,
									 description)
								 
								 addExternalFacultyCourse(xml,
									 facultySchoolId,
									 TERM_CODE,
									 formattedCourse,
									 sectionCode,
									 sectionNumber,
									 title)
								 }
							for(Integer k = 0; k < BASE_NUMBER_OF_STUDENTS; k++){
								schoolId = coachName + STUDENT_NAME + k
								firstName = FIRST_NAME
								middleName = MIDDLE_NAME
								primaryEmailAddress = schoolId + "@unicon.net"
								lastName = schoolId
								degreeCode = DEGREE_CODE
								degreeName = DEGREE_NAME
								programCode = degreeCode + k
								programName = DEGREE_NAME + k
								
								addExternalStudentAcademicProgress(xml,
									schoolId,
									degreeCode,
									degreeName,
									programCode,
									programName)
								addExternalStudentTranscript(xml,
									schoolId,
									CREDIT_HOURS_FOR_GPA,
									CREDIT_HOURS_EARNED,
									CREDIT_HOURS_ATTEMPTED,
									TOTAL_QUALITY_POINTS,
									GRADE_POINT_AVERAGE)
								for(Integer l = 0; l < NUMBER_OF_SUBJECTS; l++) {
								subjectAbbreviation = SUBJECT_ABBREVIATIONS[l]
								number = COURSE_NUMBER[l]
								formattedCourse = FORMATTED_COURSE[l] + l
								sectionNumber = SECTION_NUMBER[l] + l
								sectionCode = sectionNumber
								title = "title" + FORMATTED_COURSE[l]
								description = "description" + FORMATTED_COURSE[l]
								statusCode = STATUS_CODE
								addExternalFacultyCourseRoster(xml,
									facultySchoolId,
									schoolId,
									firstName,
									middleName,
									lastName,
									primaryEmailAddress,
									TERM_CODE,
									formattedCourse,
									sectionCode,
									sectionNumber,
									statusCode)
								addExternalStudentsByCourse(xml,
									schoolId,
									formattedCourse,
									TERM_CODE,
									firstName,
									middleName,
									lastName,
									"Y",
									2.0)
								addExternalStudentTranscriptCourse(xml, 
									schoolId, 
									subjectAbbreviation, 
									number, 
									formattedCourse, 
									sectionNumber, 
									title, 
									description,
									"A",
									3,
									"FA12",
									"Best")
								
								
								
								addExternalStudentTest(xml,
									schoolId,
									"Hard Test" + k + "_" + l,
									"HT" + k + "_" + l,
									null,
									null,
									new Date(),
									95,
									"good")
								}
							}
						}
					 }
}
					 
					 
void addExternalStudentTranscriptCourse(xml, 
	schoolId, 
	subjectAbbreviation, 
	number, 
	formattedCourse, 
	sectionNumber, 
	title, 
	description,
	grade,
	creditEarned,
	termCode,
	creditType){
	xml.insert(tableName:'external_student_transcript_course'){
		xml.column(name:"school_id", schoolId)
		xml.column(name:"subject_abbreviation", subjectAbbreviation)
		xml.column(name:"number", number)
		xml.column(name:"formatted_course",formattedCourse)
		xml.column(name:"section_number", sectionNumber)
		xml.column(name:"title", title)
		xml.column(name:"description", description)
		xml.column(name:"grade", grade)
		xml.column(name:"credit_earned", creditEarned)
		xml.column(name:"term_code", termCode)
		xml.column(name:"credit_type", creditType)
	}

	xml.rollback{
		xml.delete(tableName:'external_student_transcript_course'){
				xml.where("school_id='" + schoolId + "'")
			}
	}
}
	
void addExternalStudentTranscript(xml,
		schoolId,
		creditHoursForGPA,
		creditHoursEarned,
		creditHoursAttempted,
		totalQualityPoints,
		gradePointAverage){
		xml.insert(tableName:'external_student_transcript'){
			xml.column(name:"school_id", schoolId)
			xml.column(name:"credit_hours_for_gpa", creditHoursForGPA)
			xml.column(name:"credit_hours_earned", creditHoursEarned)
			xml.column(name:"credit_hours_attempted",creditHoursAttempted)
			xml.column(name:"total_quality_points", totalQualityPoints)
			xml.column(name:"grade_point_average", gradePointAverage)
		}
	
		xml.rollback{
			xml.delete(tableName:'external_student_transcript'){
					xml.where("school_id='" + schoolId + "'")
			}
		}
}

		
void addExternalCourseSection(xml,
		formattedCourse,
		subjectAbbreviation,
		number,
		sectionNumber,
		creditValue,
		termCode,
		description){
		xml.insert(tableName:'external_course_section'){
			xml.column(name:"formatted_course", formattedCourse)
			xml.column(name:"subject_abbreviation", subjectAbbreviation)
			xml.column(name:"number", number)
			xml.column(name:"section_number",sectionNumber)
			xml.column(name:"credit_value", creditValue)
			xml.column(name:"term_code", termCode)
			xml.column(name:"description", description)
		}
	
		xml.rollback{
			xml.delete(tableName:'external_course_section'){
					xml.where("formatted_course='" + formattedCourse + "'")
			}
		}
}
		
void addExternalStudentTest(xml,
	schoolId,
	testName,
	testCode,
	subTestCode,
	subTestName,
	testDate,
	score,
	status){
	xml.insert(tableName:'external_student_test'){
		xml.column(name:"school_id", schoolId)
		xml.column(name:"test_name", testName)
		xml.column(name:"test_code", testCode)
		xml.column(name:"sub_test_code", subTestCode)
		xml.column(name:"sub_test_name", subTestName)
		xml.column(name:"test_date", testDate)
		xml.column(name:"score", score)
		xml.column(name:"status", status)
	}

	xml.rollback{
		xml.delete(tableName:'external_student_test'){
				xml.where("school_id='" + schoolId + "'")
		}
	}
}
	
void addExternalStudentAcademicProgress(xml,
	schoolId,
	degreeCode,
	degreeName,
	programCode,
	programName){
	xml.insert(tableName:'external_student_academic_program'){
		xml.column(name:"school_id", schoolId)
		xml.column(name:"degree_code", degreeCode)
		xml.column(name:"degree_name", degreeName)
		xml.column(name:"program_code", programCode)
		xml.column(name:"program_name", programName)
	}

	xml.rollback{
		xml.delete(tableName:'external_student_academic_program'){
				xml.where("school_id='" + schoolId + "'")
		}
	}
}

	
	void addExternalStudentsByCourse(xml,
		schoolId,
		formattedCourse,
		termCode,
		firstName,
		middleName,
		lastName,
		audited,
		academicGrade){
		xml.insert(tableName:'external_students_by_course'){
			xml.column(name:"school_id", schoolId)
			xml.column(name:"formatted_course", formattedCourse)
			xml.column(name:"term_code", termCode)
			xml.column(name:"first_name", firstName)
			xml.column(name:"middle_name", middleName)
			xml.column(name:"last_name", lastName)
			xml.column(name:"audited", audited)
			xml.column(name:"academic_grade", academicGrade)
		}
	
		xml.rollback{
			xml.delete(tableName:'external_students_by_course'){
					xml.where("school_id='" + schoolId + "'")
			}
		}
	}

		void addExternalFacultyCourse(xml,
			facultySchoolId,
			termCode,
			formattedCourse,
			sectionCode,
			sectionNumber,
			title){
			xml.insert(tableName:'external_faculty_course'){
				xml.column(name:"faculty_school_id", facultySchoolId)
				xml.column(name:"term_code", termCode)
				xml.column(name:"formatted_course", formattedCourse)
				xml.column(name:"section_code", sectionCode)
				xml.column(name:"section_number", sectionNumber)
				xml.column(name:"title", title)
			}
		
			xml.rollback{
				xml.delete(tableName:'external_faculty_course'){
						xml.where("faculty_school_id='" + facultySchoolId + "'")
				}
			}
		}
			
			void addExternalFacultyCourseRoster(xml,
				facultySchoolId,
				schoolId,
				firstName,
				middleName,
				lastName,
				primaryEmailAddress,
				termCode,
				formattedCourse,
				sectionCode,
				sectionNumber,
				statusCode){
				xml.insert(tableName:'external_faculty_course_roster'){
					xml.column(name:"faculty_school_id", facultySchoolId)
					xml.column(name:"school_id", schoolId)
					xml.column(name:"first_name", firstName)
					xml.column(name:"middle_name", middleName)
					xml.column(name:"last_name", lastName)
					xml.column(name:"primary_email_address", primaryEmailAddress)
					xml.column(name:"term_code", termCode)
					xml.column(name:"formatted_course", formattedCourse)
					xml.column(name:"section_code", sectionCode)
					xml.column(name:"section_number", sectionNumber)
					xml.column(name:"status_code", statusCode)
				}
			
				xml.rollback{
					xml.delete(tableName:'external_faculty_course_roster'){
							xml.where("school_id='" + schoolId + "'")
					}
				}
			}


String getXmlLicense(){
	return " <!-- 													\n\
			Licensed to Jasig under one or more contributor license		\n\
			agreements. See the NOTICE file distributed with this work	\n\
			for additional information regarding copyright ownership.	\n\
			Jasig licenses this file to you under the Apache License,	\n\
			Version 2.0 (the \"License\"); you may not use this file 	\n\
			except in compliance with the License. You may obtain a 	\n\
			copy of the License at: 									\n\
																		\n\
			http://www.apache.org/licenses/LICENSE-2.0					 \n\
																		\n\
			Unless required by applicable law or agreed to in writing,	\n\
			software distributed under the License is distributed on	\n\
			an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY	\n\
			KIND, either express or implied. See the License for the	\n\
			specific language governing permissions and limitations		\n\
			under the License.											-->\n "
}

