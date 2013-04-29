

def xml = new groovy.xml.MarkupBuilder(new PrintWriter(System.out))
xml.mkp.xmlDeclaration(version:'1.0', encoding: 'UTF-8')
xml.databaseChangeLog( xmlns : "http://www.liquibase.org/xml/ns/dbchangelog"
        , "xmlns:xsi" : "http://www.w3.org/2001/XMLSchema-instance"
        , "xsi:schemaLocation" : "http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd")
        {
//            changeSet(id: "refactor 1.1 timestamps (mssql)", author: "dan.mccallum", dbms:"mssql") {
            changeSet(id: "refactor 1.2 timestamps (mssql)", author: "dan.mccallum", dbms:"mssql") {
//                new File("timestamp-fields-1-1").eachLine { line ->
//                new File("timestamp-fields-1-1-cont").eachLine { line ->
                new File("timestamp-fields-1-2").eachLine { line ->

                    def fields = []
                    if ( line.contains(".") ) {
                        fields[0] = line
                    } else {
                        fields[0] = "${line}.created_date"
                        fields[1] = "${line}.modified_date"
                    }

                    fields.each { field ->
                        def parts = field.split("\\.")
                        customChange(class: "org.jasig.ssp.util.liquibase.MSSQLDateTimeTimezoneRefactor") {
                            param(name: "tableName", value: "${parts[0]}")
                            param(name: "columnName", value: "${parts[1]}")
                            param(name: "origTimeZoneId", value: "\${database.timezone.legacy}")
                            param(name: "newTimeZoneId", value: "\${database.timezone}")
                        }
                    }
                }
            }
        }