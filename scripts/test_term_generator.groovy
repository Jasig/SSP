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
	static final base_terms_start_date = "2014-08-20T00:00:00"
	static final term_codes = ["FA","WN","SP","SU"]
	static final term_names = ["Fall","Winter","Spring","Summer"]

	/* Change Sets, change as needed */
	static final fileName = "000014-test.xml";
	static final TEST_LOCATION_CHANGESET = './src/test/resources/org/jasig/ssp/database/changesets/'
	static final FULL_DATA_BASE_LOCATION_CHANGESET = './src/main/resources/org/jasig/ssp/database/testingchangesets/'
	static final BASE_LOCATION_CHANGESET = FULL_DATA_BASE_LOCATION_CHANGESET
	static final author = "james.stanley"
	static final fileDescription = "Adding Set of Future Terms Data Set"
	
	static final def eol = System.properties.'line.separator'
	
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
							generateTerms(xml)
					 }
}
void generateTerms(xml){
	String dateString = offSetDate(base_terms_start_date, 0)
	ArrayList<String> fullCodes = new ArrayList<String>()
	for(Integer k = 0; k <= 4; k++)
		for(Integer i = 0; i <= 3; i++){
			String code = term_codes[i]
			String name = term_names[i]
			String yearString = getYear(dateString).toString()
			String fullCode = code + yearString.substring(2)
			fullCodes.add(fullCode)
		xml.insert(tableName:'external_term'){
			xml.column(name:"name", value:name + " " + getYear(dateString))
			xml.column(name:"code", value:fullCode)
			xml.column(name:"start_date", value:dateString)
			dateString = offSetDate(dateString, 92)
			xml.column(name:"end_date", value:dateString)
			xml.column(name:"report_year", getYear(dateString))
		}
		
	}
	
	xml.rollback{
		for(String code:fullCodes){
			xml.delete(tableName:'external_term'){
				xml.where("code='" + code+"'")
			}
		}
	}
}

Integer getYear(dateString){
	java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	Date date = dateFormat.parse(dateString)
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	return c.get(Calendar.YEAR);
}


String offSetDate(dateString, dayOffsetParams){
	java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	Date date = dateFormat.parse(dateString)
	Calendar cal = Calendar.getInstance()
	cal.setTime(date)
	cal.add(Calendar.DAY_OF_MONTH, dayOffsetParams);
	return dateFormat.format(new Date(cal.getTimeInMillis())).toString()
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

