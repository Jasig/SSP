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
	static final fileName = "i000010-test.xml";
	static final TEST_LOCATION_CHANGESET = './src/test/resources/org/jasig/ssp/database/integrationchangesets/'
	static final FULL_DATA_BASE_LOCATION_CHANGESET = './src/main/resources/org/jasig/ssp/database/testingintegrationchangesets/'
	static final BASE_LOCATION_CHANGESET = FULL_DATA_BASE_LOCATION_CHANGESET
	static final author = "james.stanley"
	static final fileDescription = "Adding Regisration By Term Data"
	
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
	static final String TERM_CODE = "SP13"
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
							 for(Integer k = 0; k < BASE_NUMBER_OF_STUDENTS; k++){
								schoolId = coachName + STUDENT_NAME + k
								addExternalRegistrationStatusbyTerm(xml,
									schoolId,
									TERM_CODE,
									NUMBER_OF_SUBJECTS,
									k%3 == 0 ? 'Y':'N')
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

