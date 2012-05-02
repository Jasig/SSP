package org.jasig.ssp.service.tool.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import org.jasig.ssp.dao.PersonDao;
import org.jasig.ssp.dao.PersonToolsDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.tool.PersonTool;
import org.jasig.ssp.model.tool.Tools;
import org.jasig.ssp.service.tool.PersonToolService;

@Service
public class PersonToolServiceImpl implements PersonToolService {

	@Autowired
	private PersonToolsDao personToolsDao;

	@Autowired
	private PersonDao personDao;

	@Override
	public List<Tools> toolsForStudent(Person student, Tools onlyThisTool) {
		boolean allTools = (null == onlyThisTool);

		List<Tools> tools = Lists.newArrayList();

		for (PersonTool personTool : student.getTools()) {
			if (personTool.getObjectStatus().equals(
					ObjectStatus.ACTIVE)) {
				if (allTools) {
					tools.add(personTool.getTool());
				} else {
					if (personTool.getTool().equals(onlyThisTool)) {
						tools.add(onlyThisTool);
					}
				}
			}
		}

		return tools;
	}

	@Override
	public PersonTool studentHasTool(Person student, Tools onlyThisTool) {
		for (PersonTool personTool : student.getTools()) {
			if (personTool.getTool().equals(onlyThisTool)) {
				return personTool;
			}
		}
		return null;
	}

	@Override
	public void addToolToStudent(Person student, Tools tool) {
		if ((studentHasTool(student, tool)) == null) {
			PersonTool personTool = new PersonTool(student, tool);
			personToolsDao.save(personTool);
			student.getTools().add(personTool);
			personDao.save(student);
		}
	}

	@Override
	public void removeToolFromStudent(Person student, Tools tool) {
		PersonTool personTool = studentHasTool(student, tool);
		if (personTool != null) {
			personTool.setObjectStatus(ObjectStatus.DELETED);
			personToolsDao.save(personTool);
			student.getTools().remove(personTool);
			personDao.save(student);
		}
	}

}
