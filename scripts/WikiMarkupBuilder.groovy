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

class Constants {
	static final COACH_ROLE = 'SSP_COACH'
	static final FACULTY_ROLE = 'SSP_FACULTY'
	static final STUDENT_ROLE = 'SSP_STUDENT'
	static final DEVELOPER_ROLE = 'SSP_DEVELOPER'
	static final MANAGER_ROLE = 'SSP_MANAGER'
	static final SUPPORT_STAFF_ROLE  = 'SSP_SUPPORT_STAFF' 
	
	static final readableRoles = [
		'SSP_COACH': 'coach',
		'SSP_FACULTY': 'faculty',
		'SSP_STUDENT': 'student',
		'SSP_DEVELOPER': 'developer',
		'SSP_MANAGER': 'manager',
		'SSP_SUPPORT_STAFF': 'support staff'
	]
	
	static final TAB = '  '
}

class Row {
	String name
	String username
	String role
}


filePath = null
out = System.out
tab = '  '

if(filePath == null && args.length < 1) {
	out.println "No filepath specified for demo data CSV file. If running this script in an IDE you can edit the filename variable directly, otherwise include it as the first argument."
	System.exit(0)
} else if(filePath == null && args[0] != null) {
	filePath = args[0]
} else if(filePath == null) {
	out.println "Error: No filepath given for Demo Data CSV Output"
	System.exit(0)
}

main()

def main() {
	def rows = new ArrayList<Row>()
	def writer = new StringWriter()	
	def file = new File(filePath).splitEachLine(",") { fields ->
		rows.add(name:fields[0], username:fields[1], role:fields[2])
	}
	addTableStart(writer)
	addHeader(writer)
	def activeCoach
	rows.each { row -> 
		if(row.role == COACH_ROLE) {
			activeCoach = row.username
			addTableRow(writer, row, '')
		} else {
			addTableRow(writer, row, activeCoach)
		}
	}
	addTableClose(writer)
	out << writer.toString()
}



def addTableStart(writer) {
	writer << "${tab}{table:class=confluenceTable}\n"
	indentTab()
}

def addTableClose(writer) {
	unidentTab()
	writer << "${tab}{table}\n"
}

def addTableRow(writer, row, coach) {
	
	writer << "${tab}{tr}"
	indentTab()
	
	writer << """
		|${tab}{td:class=confluenceTd${row.role == COACH_ROLE? ' highlight' : ''}}${row.name}{td}
		|${tab}{td:class=confluenceTd${row.role == COACH_ROLE? ' highlight' : ''}}${row.username}{td}
		|${tab}{td:class=confluenceTd${row.role == COACH_ROLE? ' highlight' : ''}}${readableRoles[row.role]}{td}
		|${tab}{td:class=confluenceTd${row.role == COACH_ROLE? ' highlight' : ''}}${coach?: ''}{td}\n""".stripMargin()

	unidentTab()
	writer << "${tab}{tr}\n"
	
}

def addHeader(writer) {
	writer << "${tab}{tr}"
	indentTab()
	
	writer << """
		|${tab}{th:class=confluenceTh}Name{th}
		|${tab}{th:class=confluenceTh}Username{th}
		|${tab}{th:class=confluenceTh}System Role{th}
		|${tab}{th:class=confluenceTh}Coach Assignment{th}\n""".stripMargin()

	unidentTab()
	writer << "${tab}{tr}\n"
	
}

def indentTab() {
	tab += TAB
}

def unidentTab() {
	if(tab.length() > 1) {
		tab = tab[0..-3]
	}
}
