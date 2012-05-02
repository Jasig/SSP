import groovy.sql.Sql

class TableImporter {
    private Sql db

    TableImporter(){
        db = Sql.newInstance('jdbc:sqlserver://192.168.56.101;databaseName=SSP', 'SSPUser', '123456789', 'com.microsoft.sqlserver.jdbc.SQLServerDriver')
    }

    public void insert(String newTableName){
        File output = new File("../src/main/resources/org/jasig/ssp/database/changesets/a.xml")

        output << """\n    <changeSet id="create reference data - ${newTableName}" author="daniel.bower">"""

        def records = db.rows("""select c.name as cname, cr.id as crid, cr.name as crname, ccr.id as ccrid 
from SSP.dbo.ChallengeChallengeReferral ccr
inner join SSP.dbo.Challenge c on ccr.challengeId = c.id
inner join SSP.dbo.ChallengeReferral cr on ccr.challengeReferralId = cr.id
order by c.name""")
       
        records.each {
            output << """\n        <insert tableName="${newTableName}">
                <column name="id" value="${it.ccrid}" />
                <column name="challenge_id" value="" />${it.cname}
                <column name="challenge_referral_id" value="${it.crid}" />${it.crname}
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
                        <where>id='${it.ccrid}'</where>
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
importer.insert('challenge_challenge_referral')

