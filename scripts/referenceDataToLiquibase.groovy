import java.util.UUID

File output = new File("../src/main/resources/org/jasig/ssp/database/changesets/000003.xml")

String recordsSql = """INSERT INTO "student_status" VALUES ('7cdaf7ae-ab45-4cc1-9768-a3fdbb889a7f', 'Pre-College/ESP', 'Pre-College/ESP', '2012-03-08 17:37:28.231', '2012-03-08 17:37:33.338', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1');
INSERT INTO "student_status" VALUES ('6d23ab89-66bf-4278-9655-1e68a3b08ab4', 'New', 'New', '2012-03-08 17:37:34.704', '2012-03-08 17:37:44.902', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1');
INSERT INTO "student_status" VALUES ('1bfa82ba-0c53-41d6-97e4-c0722788a5fb', 'Current', 'Current', '2012-03-08 17:37:45.978', '2012-03-08 17:37:51.451', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1');
INSERT INTO "student_status" VALUES ('35464cd2-3541-4877-ac4f-3a484a2fe64e', 'Former', 'Former', '2012-03-08 17:37:52.367', '2012-03-08 17:37:57.672', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1');
INSERT INTO "student_status" VALUES ('0fc31ace-4554-4f6a-9546-2a5d45f8fa9a', 'Transfer', 'Transfer', '2012-03-08 17:37:58.601', '2012-03-08 17:38:06.593', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1010e4a0-1001-0110-1011-4ffc02fe81ff', '1');"""

String tableName = null
List records = []
recordsSql.eachLine{ String line ->
    if(!tableName){
        tableName = line.find(~/"\w+"/).find(~/\w+/)
    }
    String chopped1 = line.find(~/', '.*', '/)
    String chopped2 = chopped1[4..chopped1.size()-1]
    String description = chopped2[0..(chopped2.indexOf('\'')-1)]
    records << idNameAndDesc(description)
}


Map idNameAndDesc(String desc){
    return [id:UUID.randomUUID().toString(), name:desc, description:desc];
}


output << """\n    <changeSet id="create reference data - ${tableName}" author="daniel.bower">"""

records.each {
  output << """\n        <insert tableName="${tableName}">
            <column name="id" value="${it.id}" />
            <column name="name" value="${it.name}" />
            <column name="description" value="${it.description}" />
            <column name="created_date" valueDate="2012-03-20T00:00:00" />
            <column name="modified_date" valueDate="2012-03-20T00:00:00" />
            <column name="created_by" value="58ba5ee3-734e-4ae9-b9c5-943774b4de41" />
            <column name="modified_by" value="58ba5ee3-734e-4ae9-b9c5-943774b4de41" />
            <column name="object_status" value="1" />
        </insert>"""
}

output << "\n        <rollback>"
records.each {
  output << """\n            <delete tableName="${tableName}">
                <where>id='${it.id}'</where>
            </delete>"""
}
output << "\n        </rollback>"
output << "\n    </changeSet>"