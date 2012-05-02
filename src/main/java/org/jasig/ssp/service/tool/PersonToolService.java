package org.jasig.ssp.service.tool;

import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.model.tool.Tools;

public interface PersonToolService {

	public List<Tools> toolsForStudent(Person student, Tools onlyThisTool);

	public PersonTool studentHasTool(Person student, Tools onlyThisTool);

	public void addToolToStudent(Person student, Tools tool);

	public void removeToolFromStudent(Person student, Tools tool);

}