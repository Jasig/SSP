import org.jasig.ssp.*
import groovy.json.JsonSlurper



def submitChildCareArrangement(ApiConnection conn, JsonSlurper jsonParser){
	
	String form = """{"name":"Sunny Day","description":""}"""
	String output = conn.post("api/1/reference/childCareArrangement/", form)
	
	def result = jsonParser.parseText(output)
	
	String id = result.id
	form = """{"id":"${id}","name":"Sunny Day","description":""}"""
	return  conn.put("api/1/reference/childCareArrangement/"+id.toString(), form)
}

def getStudentIntakeForm(ApiConnection conn){
	String intakeForm = conn.get("api/1/tool/studentIntake/252de4a0-7c06-4254-b7d8-4ffc02fe81ff")
	return conn.put("api/1/tool/studentIntake/252de4a0-7c06-4254-b7d8-4ffc02fe81ff", intakeForm)
}


/**
 * You can exercise the ssp api from within STS (eclipse) using this script
 */

JsonSlurper jsonParser = new JsonSlurper()
ApiConnection conn = new ApiConnection("http://localhost:8080/ssp/", "student0", "student0", false)

String output = submitChildCareArrangement(conn, jsonParser)

conn.formatAndPrintJson(output)




