import groovy.sql.Sql

class TableImporter {
    private Sql db

    TableImporter(){
        db = Sql.newInstance('jdbc:sqlserver://192.168.56.101;databaseName=SSP', 'SSPUser', '123456789', 'com.microsoft.sqlserver.jdbc.SQLServerDriver')
    }

    public void insert(String newTableName){
        File output = new File("../src/main/resources/edu/sinclair/ssp/database/changesets/a.xml")

        output << """\n    <changeSet id="create reference data - ${newTableName}" author="daniel.bower">"""

        def records = db.rows('SELECT id,name,description,publicDescription,showInSelfHelpGuide FROM ChallengeReferral')
       
        records.each {
            output << """\n        <insert tableName="${newTableName}">
                <column name="id" value="${it.id}" />
                <column name="name" value="${cleanString("name", it.name, 80)}" />
                <column name="description" value="${cleanString("description", it.description, 64000)}" />
                <column name="public_description" value="${cleanString("public_description", it.publicDescription, 64000)?:''}" />
                <column name="show_in_self_help_guide" valueBoolean="${it.showInSelfHelpGuide}" />
                <column name="created_date" valueDate="2012-03-20T00:00:00" />
                <column name="modified_date" valueDate="2012-03-20T00:00:00" />
                <column name="created_by" value="58ba5ee3-734e-4ae9-b9c5-943774b4de41" />
                <column name="modified_by" value="58ba5ee3-734e-4ae9-b9c5-943774b4de41" />
                <column name="object_status" value="1" />
            </insert>"""
        }

        output << "\n        <rollback>"
        records.each {
          output << """\n            <delete tableName="${newTableName}">
                        <where>id='${it.id}'</where>
                    </delete>"""
        }
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
importer.insert('challenge_referral')

