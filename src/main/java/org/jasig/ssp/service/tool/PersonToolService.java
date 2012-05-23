package org.jasig.ssp.service.tool;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.model.tool.Tools;
import org.jasig.ssp.service.PersonAssocService;

public interface PersonToolService extends PersonAssocService<PersonTool> {

	PersonTool personHasTool(Person student, Tools onlyThisTool);

	PersonTool addToolToPerson(Person student, Tools tool);

	PersonTool removeToolFromPerson(Person student, Tools tool);
}