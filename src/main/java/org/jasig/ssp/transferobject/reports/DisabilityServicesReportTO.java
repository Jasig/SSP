package org.jasig.ssp.transferobject.reports;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.PersonDisabilityAgency;
import org.jasig.ssp.model.PersonDisabilityType;
import org.jasig.ssp.model.PersonProgramStatus;
import org.jasig.ssp.model.reference.DisabilityType;
import org.jasig.ssp.transferobject.CoachPersonLiteTO;
import org.jasig.ssp.transferobject.PersonTO;

public class DisabilityServicesReportTO extends BaseStudentReportTO {
	private static String ILP = "ILP";
	
	final private static String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
	
	final private static SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat(DEFAULT_DATE_FORMAT,Locale.US);
	
	public DisabilityServicesReportTO(Person model, Boolean ilp,
			String disabilityCode, String agencyContact, String sspStatus,
			String odsReason, Date odsRegistrationDate, String interpreter,
			Integer registrationStatus, String assignmentDate, String major,
			String verteranSatus, String ethnicity) {
		super(model);
		this.ilp = ilp;
		this.disabilityCode = disabilityCode;
		this.agencyContacts = agencyContact;
		this.sspStatus = sspStatus;
		this.odsReason = odsReason;
		this.odsRegistrationDate = odsRegistrationDate;
		this.interpreter = interpreter;
		this.registrationStatus = registrationStatus;
		this.assignmentDates = assignmentDate;
		this.major = major;
		this.veteranStatus = verteranSatus;
		this.ethnicity = ethnicity;
	}
	
	
	
	public DisabilityServicesReportTO()
	{
		
	}
	
	private Boolean ilp;
	private String disabilityCode;
	private String agencyContacts;
	private String sspStatus;
	private String odsStatus;
	private String odsReason;
	private Date odsRegistrationDate;
	private String interpreter;
	private Integer registrationStatus;
	private String assignmentDates;
	private String major;
	private String veteranStatus;
	private String ethnicity;
	
	
	public Boolean getIlp() {
		return ilp;
	}

	public void setIlp(Boolean ilp) {
		this.ilp = ilp;
	}

	public String getDisabilityCode() {
		return disabilityCode;
	}

	public void setDisabilityCode(String disabilityCode) {
		this.disabilityCode = disabilityCode;
	}

	public String getAgencyContacts() {
		return agencyContacts;
	}

	public void setAgencyContacts(String agencyContacts) {
		this.agencyContacts = agencyContacts;
	}

	public String getSspStatus() {
		return sspStatus;
	}

	public void setSspStatus(String sspStatus) {
		this.sspStatus = sspStatus;
	}

	public String getOdsStatus() {
		return odsStatus;
	}



	public void setOdsStatus(String odsStatus) {
		this.odsStatus = odsStatus;
	}



	public String getOdsReason() {
		return odsReason;
	}

	public void setOdsReason(String odsReason) {
		this.odsReason = odsReason;
	}

	public Date getOdsRegistrationDate() {
		return odsRegistrationDate;
	}

	public void setOdsRegistrationDate(Date odsRegistrationDate) {
		this.odsRegistrationDate = odsRegistrationDate;
	}

	public String getInterpreter() {
		return interpreter;
	}

	public void setInterpreter(String interpreter) {
		this.interpreter = interpreter;
	}

	public Integer getRegistrationStatus() {
		return registrationStatus;
	}

	public void setRegistrationStatus(Integer registrationStatus) {
		this.registrationStatus = registrationStatus;
	}

	public String getAssignmentDates() {
		return assignmentDates;
	}

	public void setAssignmentDates(String assignmentDate) {
		this.assignmentDates = assignmentDate;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getVeteranStatus() {
		return veteranStatus;
	}

	public void setVeteranStatus(String verteranStatuss) {
		this.veteranStatus = verteranStatuss;
	}

	public String getEthnicity() {
		return ethnicity;
	}

	public void setEthnicity(String ethnicity) {
		this.ethnicity = ethnicity;
	}
	
	public void setPerson(Person person) {
		super.setPerson(person);
		if(person.getDemographics() != null){
			if(person.getDemographics().getEthnicity() != null)
				setEthnicity(person.getDemographics().getEthnicity().getName());
			if(person.getDemographics().getVeteranStatus() != null)
				setVeteranStatus(person.getDemographics().getVeteranStatus().getName());
		}
		
		StringBuffer disabilityAgents = new StringBuffer("");
		StringBuffer disabilityAssignmentDates = new StringBuffer("");

		for(PersonDisabilityAgency disabilityAgency:person.getDisabilityAgencies())
		{
			disabilityAgents.append(disabilityAgency.getDisabilityAgency().getName() + " ");
			disabilityAssignmentDates.append(DATE_FORMATTER.format(disabilityAgency.getCreatedDate()) + " ");
		}
		
		StringBuffer programStatuses = new StringBuffer("");
		for(PersonProgramStatus programStatus:person.getProgramStatuses())
		{
			programStatuses.append(programStatus.getProgramStatus().getName() + " ");
		}
		setSspStatus(programStatuses.toString());
		
		setAgencyContacts(disabilityAgents.toString());
		setAssignmentDates(disabilityAssignmentDates.toString());
		
		setCoach(new CoachPersonLiteTO(person.getCoach()));
		
		StringBuffer disabilityTypes = new StringBuffer("");
		for(PersonDisabilityType disabilityType:person.getDisabilityTypes())
		{
			disabilityTypes.append(disabilityType.getDisabilityType().getName() + " ");
		}
		setDisabilityCode(disabilityTypes.toString());
		if(getStudentType().equals(ILP))
			setIlp(true);
		else
			setIlp(false);

		if(person.getCurrentRegistrationStatus() != null)
			setRegistrationStatus(person.getCurrentRegistrationStatus().getRegisteredCourseCount());
		if(person.getEducationGoal() != null && person.getEducationGoal().getPlannedMajor() != null)
			setMajor(person.getEducationGoal().getPlannedMajor());
		
		if(person.getDisability() != null && person.getDisability().getDisabilityStatus() != null)
			setOdsStatus(person.getDisability().getDisabilityStatus().getName());
	}
}
