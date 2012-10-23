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
import groovy.sql.Sql

class TableImporter {
	private Sql db

	TableImporter(){
		// Must have SQL Server Browser service running on port 1434 too
		db = Sql.newInstance('jdbc:sqlserver://127.0.0.1\\SQLEXPRESS;databaseName=SSP', 'SSPUser', '123456789', 'com.microsoft.sqlserver.jdbc.SQLServerDriver')
	}

	public void insert(String newTableName){
		File output = new File("R:/temp.xml")
		//File output = new File("../src/main/resources/org/jasig/ssp/database/changesets/a.xml")

		output << """\n    <changeSet id="create reference data - ${newTableName}" author="jon.adams">"""

		def records = db.rows('SELECT [journalEntrySessionTypeLUID] as id, [sessionType] as name, [sessionTypeDesc] as description, [treeOrder] as sortOrder FROM [JournalEntrySessionTypeLU]')

		records.each { output << """\n        <insert tableName="${newTableName}">
                <column name="id" value="${it.id}" />
                <column name="name" value="${cleanString("name", it.name, 80)}" />
                <column name="description" value="${cleanString("description", it.description, 64000)}" />
                <column name="created_date" valueDate="2012-05-18T14:00:00" />
                <column name="modified_date" valueDate="2012-05-18T14:00:00" />
                <column name="created_by" value="58ba5ee3-734e-4ae9-b9c5-943774b4de41" />
                <column name="modified_by" value="58ba5ee3-734e-4ae9-b9c5-943774b4de41" />
                <column name="object_status" value="1" />
                <column name="sort_order" value="${it.sortOrder}" />
            </insert>""" }

		output << "\n        <rollback>"
		records.each { output << """\n            <delete tableName="${newTableName}">
                        <where>id='${it.id}'</where>
                    </delete>""" }
		output << "\n        </rollback>"
		output << "\n    </changeSet>"
	}

	public String cleanString(String name, String val, int length){
		String newVal = val?.replaceAll("\"", "'")?.replaceAll("&", "&amp;")?.replaceAll("“", "'")?.replaceAll("”", "'")
		if(newVal && newVal.size()>length){
			newVal = newVal[0..length-1]
			println (name + ' ' + val.size().toString() + ', ' + length.toString() + ', ' + val)
		}
		return newVal
	}
}


TableImporter importer = new TableImporter();
importer.insert('journal_track')

