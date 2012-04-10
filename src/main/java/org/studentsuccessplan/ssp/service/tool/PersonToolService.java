package edu.sinclair.ssp.service.tool;

import java.util.List;

import edu.sinclair.ssp.model.Person;
import edu.sinclair.ssp.model.tool.PersonTool;
import edu.sinclair.ssp.model.tool.Tools;

public interface PersonToolService {

	public List<Tools> toolsForStudent(Person student, Tools onlyThisTool);

	public PersonTool studentHasTool(Person student, Tools onlyThisTool);

	public void addToolToStudent(Person student, Tools tool);

	public void removeToolFromStudent(Person student, Tools tool);

}