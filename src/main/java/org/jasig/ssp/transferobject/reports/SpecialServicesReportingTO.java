package org.jasig.ssp.transferobject.reports;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.web.api.reports.SpecialServicesReportController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * SpecialServicesReportingTO transfer object
 */
public class SpecialServicesReportingTO implements Serializable {

	private static final long serialVersionUID = -5316251830392732984L;

	@SuppressWarnings("unused")
	private SpecialServicesReportingTO() {
		// must create through ReportablePerson
	}

	public SpecialServicesReportingTO(final Person person) {
		super();
		this.person = person;
	}

	// TODO: Should be a PersonTO, but SpecialServices not populated by service
	// in the TO
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

	public String getStudentType() {
		if (person.getStudentType() == null) {
			return null;
		}
		else {
			return person.getStudentType().getName();
		}
	}

	protected Logger getLogger() {
		return LOGGER;
	}

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SpecialServicesReportController.class);

	public List<String> getSpecialServices() {
		final List<String> retVal = new ArrayList<String>();
		final Iterator<PersonSpecialServiceGroup> pssIter = person
				.getSpecialServiceGroups().iterator();
		while (pssIter.hasNext()) {
			retVal.add(pssIter.next().getSpecialServiceGroup().getName());
		}
		return retVal;
	}

}
