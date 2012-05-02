import org.studentsuccessplan.ssp.*

/**
 * You can exercise the ssp api from within STS (eclipse) using this script
 */

ApiConnection conn = new ApiConnection("http://localhost:8080/ssp/", "student0", "student0")

conn.formatAndPrintJson(conn.get("api/1/tool/studentIntake/252de4a0-7c06-4254-b7d8-4ffc02fe81ff"))
