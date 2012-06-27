package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.reference.StudentType;

/**
 * SpecialServicesReportingTO transfer object
 */
public class SpecialServicesReportingTO
		implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5316251830392732984L;

	@SuppressWarnings("unused")
	private SpecialServicesReportingTO()
	{
		// must create through ReportablePerson
	}

	public SpecialServicesReportingTO(Person person) {
		super();
		this.person = person;
	}

	private Person person;

	public String getFirstName() {
		return person.getFirstName();
	}

	public String getLastName() {
		return person.getLastName();
	}

	public String getMiddleInitial() {
		return person.getMiddleInitial();
	}

	public String getUserId() {
		return person.getUserId();
	}

	public StudentType getStudentType() {
		return person.getStudentType();
	}

	public List<String> getSpecialServices() {
		List<String> retVal = new ArrayList<String>();

		Iterator<PersonSpecialServiceGroup> pssIter = person
				.getSpecialServiceGroups().iterator();
		while (pssIter.hasNext())
		{
			retVal.add(pssIter.next().getSpecialServiceGroup().getName());
		}
		return retVal;
	}

}
