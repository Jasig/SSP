package org.jasig.ssp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Table(name="map_term_note")
public class TermNote extends AbstractAuditable implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1396962314115710627L;

	@ManyToOne()
	@JoinColumn(name = "template_id", updatable = false, nullable = true)	
	private Template template;
	
	@ManyToOne()
	@JoinColumn(name = "plan_id", updatable = false, nullable = true)	
	private Plan plan;
	
	@Column(length = 2000)
	@Size(max = 2000)
	private String studentNotes;
	
	@Column(length = 2000)
	@Size(max = 2000)
	private String contactNotes;
	
	@NotNull
	@Column(length = 50)
	@Size(max = 50)
	private String termCode;
	
	@Column(nullable = false)
	private Boolean isImportant = false;

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Plan getPlan() {
		return plan;
	}

	public void setPlan(Plan plan) {
		this.plan = plan;
	}

	public String getStudentNotes() {
		return studentNotes;
	}

	public void setStudentNotes(String studentNotes) {
		this.studentNotes = studentNotes;
	}

	public String getContactNotes() {
		return contactNotes;
	}

	public void setContactNotes(String contactNotes) {
		this.contactNotes = contactNotes;
	}

	public Boolean getIsImportant() {
		return isImportant;
	}

	public void setIsImportant(Boolean isImportant) {
		this.isImportant = isImportant;
	}

	public String getTermCode() {
		return termCode;
	}

	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}

	@Override
	protected int hashPrime() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public TermNote clone()
	{
		TermNote clone = new TermNote();
		clone.setContactNotes(this.getContactNotes());
		clone.setIsImportant(this.getIsImportant());
		clone.setStudentNotes(this.getStudentNotes());
		clone.setTermCode(this.getTermCode());
		return clone;
	}
	
}
