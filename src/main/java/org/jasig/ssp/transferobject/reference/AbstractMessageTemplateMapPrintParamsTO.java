package org.jasig.ssp.transferobject.reference;

import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.TermCourses;
import org.jasig.ssp.transferobject.AbstractPlanOutputTO;
import org.jasig.ssp.transferobject.AbstractPlanTO;

public class AbstractMessageTemplateMapPrintParamsTO<TOO extends AbstractPlanOutputTO<T,TO>, T extends AbstractPlan,TO extends AbstractPlanTO<T>> {

	Float totalPlanCreditHours;
	List<TermCourses<T, TO>> termCourses;
	String institutionName;
	UUID messageTemplateId;
	Person owner;
	Person student;
	
	AbstractPlanOutputTO<T,TO> outputPlan;
	
	public AbstractPlanOutputTO<T, TO> getOutputPlan() {
		return outputPlan;
	}
	public void setOutputPlan(AbstractPlanOutputTO<T, TO> outputPlan) {
		this.outputPlan = outputPlan;
	}
	
	public Float getTotalPlanCreditHours() {
		return totalPlanCreditHours;
	}
	public void setTotalPlanCreditHours(Float totalPlanCreditHours) {
		this.totalPlanCreditHours = totalPlanCreditHours;
	}
	public List<TermCourses<T, TO>> getTermCourses() {
		return termCourses;
	}
	public void setTermCourses(List<TermCourses<T, TO>> termCourses) {
		this.termCourses = termCourses;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public UUID getMessageTemplateId() {
		return messageTemplateId;
	}
	public void setMessageTemplateId(UUID messageTemplateId) {
		this.messageTemplateId = messageTemplateId;
	}
	public Person getOwner() {
		return owner;
	}
	public void setOwner(Person owner) {
		this.owner = owner;
	}
	public Person getStudent() {
		return student;
	}
	public void setStudent(Person student) {
		this.student = student;
	}
	
}
