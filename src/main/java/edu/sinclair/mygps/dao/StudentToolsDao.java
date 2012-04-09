package edu.sinclair.mygps.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import edu.sinclair.ssp.model.Person;

@Repository
public class StudentToolsDao {

	@Autowired
	private SessionFactory sessionFactory;

	protected static final UUID STUDENT_INTAKE_TOOL_LUID = UUID
			.fromString("B2D11856-5056-A51A-8026E838D61CB6AF");

	@SuppressWarnings("unchecked")
	public List<String> toolsForStudent(Person student, UUID onlyThisTool) {
		boolean allTools = (null == onlyThisTool);
		String query = "SELECT toolLUID FROM ToolsByStudent where studentID = ? ";

		List<String> results;

		if (allTools) {
			results = sessionFactory.getCurrentSession()
					.createSQLQuery(query)
					.addScalar("toolLUID")
					.setParameter(0, student.getId())
					.list();
		} else {
			query = query + " and toolLUID = ? ";
			results = sessionFactory.getCurrentSession()
					.createSQLQuery(query)
					.addScalar("toolLUID")
					.setParameter(0, student.getId())
					.setParameter(1, onlyThisTool)
					.list();
		}

		if (0 < results.size()) {
			return results;
		} else {
			return new ArrayList<String>();
		}
	}

	public void addIntakeToolToStudent(Person student) {
		addToolToStudent(student, STUDENT_INTAKE_TOOL_LUID);
	}

	protected void addToolToStudent(Person student, UUID toolLuid) {
		// What records exist for student and tool
		List<String> results = toolsForStudent(student, toolLuid);

		// Record does not exist for student and tool?
		if (results.size() == 0) {
			// add
			this.sessionFactory
					.getCurrentSession()
					.createSQLQuery("insert into ToolsByStudent("
							+ "toolLUID, studentID, toolID) values(?, ?, ?)")
					.setParameter(0, toolLuid)
					.setParameter(1, student.getId())
					.setParameter(2,
							coldfusionStringFromUUID(UUID.randomUUID()))
					.executeUpdate();
		}// ignore
	}

	protected void removeToolFromStudent(Person student, UUID toolLuid) {
		// What records exist for student and tool
		List<String> results = toolsForStudent(student, toolLuid);

		// Record does not exist for student and tool?
		if (results.size() != 0) {
			// delete
			this.sessionFactory.getCurrentSession()
					.createSQLQuery("delete from ToolsByStudent "
							+ "where toolLUID = ? and studentID = ?")
					.setParameter(0, toolLuid)
					.setParameter(1, student.getId())
					.executeUpdate();
		}// ignore
	}

	public static String coldfusionStringFromUUID(UUID uuid) {
		String uuidString = uuid.toString().toUpperCase();
		return uuidString.substring(0, 23) + uuidString.substring(24, 36);
	}
}
