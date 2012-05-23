import groovy.sql.Sql

class TableImporter {
	private Sql db

	TableImporter(){
		// Must have SQL Server Browser service running on port 1434 too
		db = Sql.newInstance('jdbc:sqlserver://127.0.0.1\\SQLEXPRESS;databaseName=SSP', 'SSPUser', '123456789', 'com.microsoft.sqlserver.jdbc.SQLServerDriver')
	}

	public void insert(String newTableName){
		File output = new File("R:/temp.xml")

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

