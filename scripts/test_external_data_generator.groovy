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
	static final fileName = "i000008-test.xml";
	static final TEST_LOCATION_CHANGESET = './src/test/resources/org/jasig/ssp/database/integrationchangesets/'
	static final FULL_DATA_BASE_LOCATION_CHANGESET = './src/main/resources/org/jasig/ssp/database/testingintegrationchangesets/'
	static final BASE_LOCATION_CHANGESET = FULL_DATA_BASE_LOCATION_CHANGESET
	static final author = "james.stanley"
	static final fileDescription = "Adding Large Set of External Data"
	
	static final def eol = System.properties.'line.separator'
	
	static final ADVISOR_NAME = "Adv"
	static final STUDENT_NAME = "Stu"
	static final TOTAL_NUMBER_OF_COACHES = 3 as Integer
	static final STUDENT_MULTIPLIER = 4 as Integer //use this to set the multiplier that determines the number of students a coach has. (coachIndex * STUDENT_MULTIPLIER)
	static final BASE_NUMBER_OF_STUDENTS = 5 as Integer
	
	static final String[] SUBJECT_ABBREVIATIONS = ["MATH101", "BIO101", "CHEM101"]
	static final String[] COURSE_NUMBER = ["1001M", "1001B","1001C"]
	static final String[] FORMATTED_COURSE = ["Freshman Math", "Freshman Biology", "Freshman Chemistry"]
	static final String[] SECTION_NUMBER =["A1", "B2", "C3"]
	static final String[] TERM_CODES = ["FA12","SP13"]
	static final Integer NUMBER_OF_SUBJECTS = 3
	static final Integer NUMBER_OF_TERMS = 2
	static final Integer CREDIT_VALUE = "4"
	static final String FIRST_NAME = "test"
	static final String MIDDLE_NAME = "Mumford"
	static final String[] STATUS_CODE = ["E","RW","W"]
	static final String DEGREE_CODE = "LA_SCI"
	static final String DEGREE_NAME = "Liberal Arts Science Degree"
	static final Double CREDIT_HOURS_FOR_GPA = 30.2
	static final Double CREDIT_HOURS_EARNED = 22
	static final Double CREDIT_HOURS_ATTEMPTED = 18.8
	static final Double TOTAL_QUALITY_POINTS = 221
	static final Double GRADE_POINT_AVERAGE = 3.9
	static final Double CREDIT_HOURS_NOT_COMPLETED = 4;
	static final Double CREDIT_COMPLETION_RATE = 80;
	static final String ACADEMIC_STANDING = "good"
	static final String CURRENT_RESTRICTIONS = "None"
	static final String GPA_TREND_INDICATOR = "UP"
	static final Double FINANCIAL_AID_GPA = 4.3
	static final Double GPA_20_B_HRS_NEEDED = 10
	static final Double GPA_20_A_HRS_NEEDED = 5
	static final Double NEEDED_FOR_67_PTC_COMPLETION = 14
	static final String CURRENT_YEAR_FINANCIAL_AWARD = "Y"
	static final String SAP_STATUS = "Y"
	static final FAFSA_DATE = new Date();
	static final FINANCIAL_AID_REMIANING = 10000
	static final ORIGINAL_LOAN_AMOUNT = 100000
	static final REMAINING_LOAN_AMOUNT = 10000

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
								 for(Integer m = 0; m < NUMBER_OF_TERMS; m++) {
								 subjectAbbreviation = SUBJECT_ABBREVIATIONS[l] = TERM_CODES[m]
								 number = COURSE_NUMBER[l]
								 formattedCourse = FORMATTED_COURSE[l]
								 sectionCode = SECTION_NUMBER[l] + l + TERM_CODES[m]
								 sectionNumber = SECTION_NUMBER[l] + l + m
								 title = "title" + FORMATTED_COURSE[l] + TERM_CODES[m]
								 description = "description" + FORMATTED_COURSE[l] + TERM_CODES[m]
								 
								 
								 creditValue = CREDIT_VALUE
								 
								 
								 addExternalFacultyCourse(xml,
									 facultySchoolId,
									  TERM_CODES[m],
									 formattedCourse,
									 sectionCode,
									 sectionNumber,
									 title)
								 }
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
								addExternalStudentFinancialAid(xml,
									schoolId,
									FINANCIAL_AID_GPA,
									GPA_20_B_HRS_NEEDED,
									GPA_20_A_HRS_NEEDED,
									NEEDED_FOR_67_PTC_COMPLETION,
									CURRENT_YEAR_FINANCIAL_AWARD,
									SAP_STATUS,
									FAFSA_DATE,
									FINANCIAL_AID_REMIANING,
									ORIGINAL_LOAN_AMOUNT,
									REMAINING_LOAN_AMOUNT)
								
								addExternalStudentTranscript(xml,
									schoolId,
									CREDIT_HOURS_FOR_GPA,
									CREDIT_HOURS_EARNED,
									CREDIT_HOURS_NOT_COMPLETED,
									CREDIT_HOURS_ATTEMPTED,
									CREDIT_COMPLETION_RATE,
									TOTAL_QUALITY_POINTS,
									GRADE_POINT_AVERAGE,
									ACADEMIC_STANDING,
									CURRENT_RESTRICTIONS,
									GPA_TREND_INDICATOR)
								for(Integer m = 0; m < NUMBER_OF_TERMS; m++) {
									addExternalRegistrationStatusbyTerm(xml,
										schoolId,
										 TERM_CODES[m],
										NUMBER_OF_SUBJECTS,
										k%3 == 0 ? 'Y':'N')
									addExternalStudentTranscriptTerm(xml,
										schoolId,
										CREDIT_HOURS_FOR_GPA - 5,
										CREDIT_HOURS_EARNED - 5,
										CREDIT_HOURS_ATTEMPTED - 5,
										CREDIT_HOURS_NOT_COMPLETED + 3,
										CREDIT_COMPLETION_RATE - 5,
										TOTAL_QUALITY_POINTS - 5,
										GRADE_POINT_AVERAGE + 0.5,
										 TERM_CODES[m])
								}
								for(Integer l = 0; l < NUMBER_OF_SUBJECTS; l++) {
									for(Integer m = 0; m < NUMBER_OF_TERMS; m++) {
									subjectAbbreviation = SUBJECT_ABBREVIATIONS[l] + TERM_CODES[m]
									number = COURSE_NUMBER[l] + m
									formattedCourse = FORMATTED_COURSE[l] + l + TERM_CODES[m]
									sectionNumber = SECTION_NUMBER[l] + l + m
									sectionCode = sectionNumber + formattedCourse + TERM_CODES[m]
									title = "title" + FORMATTED_COURSE[l] + TERM_CODES[m]
									description = "description" + FORMATTED_COURSE[l] + TERM_CODES[m]
									statusCode = STATUS_CODE[(i+k+l+m)%3]
									
									addExternalFacultyCourseRoster(xml,
										facultySchoolId,
										schoolId,
										firstName,
										middleName,
										lastName,
										primaryEmailAddress,
										 TERM_CODES[m],
										formattedCourse,
										sectionCode,
										sectionNumber,
										statusCode)

									
									addExternalStudentTranscriptCourse(xml, 
										schoolId, 
										subjectAbbreviation, 
										number, 
										formattedCourse, 
										sectionNumber, 
										sectionCode,
										title, 
										description,
										"A",
										3,
										TERM_CODES[m],
										"Best",
										firstName,
										middleName,
										lastName,
										statusCode,
										facultySchoolId,
										i+k+l%4 == 0?"Y":"N")
									}
									
									
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

					 
 void addExternalRegistrationStatusbyTerm(xml,
	 schoolId,
	 termCode,
	 registeredCourseCount,
	 tuitionPaid){
	 xml.insert(tableName:'external_registration_status_by_term'){
		 xml.column(name:"school_id", schoolId)
		 xml.column(name:"term_code", termCode)
		 xml.column(name:"registered_course_count", registeredCourseCount)
		 xml.column(name:"tuition_paid",tuitionPaid)
	 }
 
	 xml.rollback{
		 xml.delete(tableName:'external_registration_status_by_term'){
				 xml.where("school_id='" + schoolId + "'")
			 }
		 }
 }
					 
void addExternalStudentTranscriptCourse(xml, 
	schoolId, 
	subjectAbbreviation, 
	number, 
	formattedCourse, 
	sectionNumber, 
	sectionCode,
	title, 
	description,
	grade,
	creditEarned,
	termCode,
	creditType,
	firstName,
	middleName,
	lastName,
	statusCode,
	facultySchoolId,
	audited){
	xml.insert(tableName:'external_student_transcript_course'){
		xml.column(name:"school_id", schoolId)
		xml.column(name:"subject_abbreviation", subjectAbbreviation)
		xml.column(name:"number", number)
		xml.column(name:"formatted_course",formattedCourse)
		xml.column(name:"section_number", sectionNumber)
		xml.column(name:"section_code", sectionCode)
		xml.column(name:"title", title)
		xml.column(name:"description", description)
		xml.column(name:"grade", grade)
		xml.column(name:"credit_earned", creditEarned)
		xml.column(name:"term_code", termCode)
		xml.column(name:"credit_type", creditType)
		xml.column(name:"first_name", firstName)
		xml.column(name:"middle_name", middleName)
		xml.column(name:"last_name", lastName)
		xml.column(name:"status_code", statusCode)
		xml.column(name:"faculty_school_id", facultySchoolId)
		xml.column(name:"audited", audited)
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
		creditHoursNotCompleted,
		creditHoursAttempted,
		creditCompletionRate,
		totalQualityPoints,
		gradePointAverage,
		academicStanding,
		currentRestrictions,
		gpaTrendIndicator){
		xml.insert(tableName:'external_student_transcript'){
			xml.column(name:"school_id", schoolId)
			xml.column(name:"credit_hours_for_gpa", creditHoursForGPA)
			xml.column(name:"credit_hours_earned", creditHoursEarned)
			xml.column(name:"credit_hours_not_completed", creditHoursNotCompleted)
			xml.column(name:"credit_completion_rate", creditCompletionRate)
			xml.column(name:"credit_hours_attempted",creditHoursAttempted)
			xml.column(name:"total_quality_points", totalQualityPoints)
			xml.column(name:"grade_point_average", gradePointAverage)
			xml.column(name:"academic_standing", academicStanding)
			xml.column(name:"current_restrictions", currentRestrictions)
			xml.column(name:"gpa_trend_indicator", gpaTrendIndicator)
		}
	
		xml.rollback{
			xml.delete(tableName:'external_student_transcript'){
					xml.where("school_id='" + schoolId + "'")
			}
		}
}
		
void addExternalStudentFinancialAid(xml,
		schoolId,
		financialAidGpa,
		gpa20BHrsNeeded,
		gpa20AHrsNeeded,
		neededFor67ptcCompletion,
		currentYearFinancialAidAward,
		sapStatus,
		fafsaDate,
		financialAidRemaing,
		originalLoanAmount,
		remainingLoanAmount){
		xml.insert(tableName:'external_student_financial_aid'){
			xml.column(name:"school_id", schoolId)
			xml.column(name:"financial_aid_gpa", financialAidGpa)
			xml.column(name:"gpa_20_b_hrs_needed", gpa20BHrsNeeded)
			xml.column(name:"gpa_20_a_hrs_needed",gpa20AHrsNeeded)
			xml.column(name:"needed_for_67ptc_completion", neededFor67ptcCompletion)
			xml.column(name:"current_year_financial_aid_award", currentYearFinancialAidAward)
			xml.column(name:"sap_status", sapStatus)
			xml.column(name:"fafsa_date", fafsaDate)
			xml.column(name:"financial_aid_remaining", financialAidRemaing)
			xml.column(name:"original_loan_amount", originalLoanAmount)
			xml.column(name:"remaining_loan_amount", remainingLoanAmount)
		}
	
		xml.rollback{
			xml.delete(tableName:'external_student_financial_aid'){
					xml.where("school_id='" + schoolId + "'")
			}
		}
}

void addExternalStudentTranscriptTerm(xml,
		schoolId,
		creditHoursForGPA,
		creditHoursEarned,
		creditHoursAttempted,
		creditHoursNotCompleted,
		creditCompletionRate,
		totalQualityPoints,
		gradePointAverage,
		termCode){
		xml.insert(tableName:'external_student_transcript_term'){
			xml.column(name:"school_id", schoolId)
			xml.column(name:"credit_hours_for_gpa", creditHoursForGPA)
			xml.column(name:"credit_hours_earned", creditHoursEarned)
			xml.column(name:"credit_hours_attempted",creditHoursAttempted)
			xml.column(name:"credit_hours_not_completed",creditHoursNotCompleted)
			xml.column(name:"credit_completion_rate", creditCompletionRate)
			xml.column(name:"total_quality_points", totalQualityPoints)
			xml.column(name:"grade_point_average", gradePointAverage)
			xml.column(name:"term_code", termCode)
		}
	
		xml.rollback{
			xml.delete(tableName:'external_student_transcript_term'){
					xml.where("school_id='" + schoolId + "'")
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
		xml.column(name:"intended_program_at_admit", programName)
	}

	xml.rollback{
		xml.delete(tableName:'external_student_academic_program'){
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

