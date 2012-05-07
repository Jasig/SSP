/**
 * Make sure the sqlserver-jdbc4-2.0.jar is installed in your Groovy user lib 
 * directory. For example: users/myusername/.groovy/lib/sqlserver-jdbc4-2.0.jar
 */

import groovy.sql.Sql

class TableImporter {
	private Sql db

	TableImporter(){
		db = Sql.newInstance('jdbc:sqlserver://127.0.0.1\\SQLEXPRESS;databaseName=SSP', 'SSPUser', '123456789', 'com.microsoft.sqlserver.jdbc.SQLServerDriver')
	}

	public void insert(String newTableName){
		File output = new File("../src/main/resources/org/jasig/ssp/database/changesets/000005.xml")

		output << """\n    <changeSet id="create reference data - ${newTableName}" author="jon.adams">"""

		def records = db.rows("""select 
[counselingOutcome] as crname,
[earlyAlertCounselingOutcomeLUID] as crid, 
[counselingOutcomeCode] as crdesc,
[treeOrder] as crorder 
from SSP.dbo.[EarlyAlertCounselingOutcomeLU] c
order by crid""")

		records.each { output << """\n        <insert tableName="${newTableName}">
                <column name="id" value="${it.crid}" />
                <column name="name" value="${it.crname}" />
                <column name="description" value="${it.crdesc}" />
                <column name="created_date" valueDate="2012-05-03T00:00:00" />
                <column name="modified_date" valueDate="2012-05-03T00:00:00" />
                <column name="created_by" value="58ba5ee3-734e-4ae9-b9c5-943774b4de41" />
                <column name="modified_by" value="58ba5ee3-734e-4ae9-b9c5-943774b4de41" />
                <column name="object_status" value="1" />
                <column name="sort_order" value="${it.crorder}" />
            </insert>""" }

		output << "\n        <rollback>"
		records.each { output << """\n            <delete tableName="${newTableName}">
                        <where>id='${it.crid}'</where>
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
importer.insert('early_alert_outcome')

