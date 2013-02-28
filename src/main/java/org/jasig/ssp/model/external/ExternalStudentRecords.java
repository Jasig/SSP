package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.util.List;

public class ExternalStudentRecords extends AbstractExternalData implements
		ExternalData, Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -4432569410223412913L;

	private ExternalStudentTranscript gpa;
	private List<ExternalStudentAcademicProgram> programs;
	private List<ExternalStudentTermCourses> terms;
	/**
	 * @return the gpa
	 */
	public ExternalStudentTranscript getGPA() {
		return gpa;
	}
	/**
	 * @param gpq the gpa to set
	 */
	public void setGPA(ExternalStudentTranscript gpa) {
		this.gpa = gpa;
	}
	/**
	 * @return the programs
	 */
	public List<ExternalStudentAcademicProgram> getPrograms() {
		return programs;
	}
	/**
	 * @param programs the programs to set
	 */
	public void setPrograms(List<ExternalStudentAcademicProgram> programs) {
		this.programs = programs;
	}
	

	/**
	 * @return the terms
	 */
	public List<ExternalStudentTermCourses> getTerms() {
		return terms;
	}
	/**
	 * @param terms the terms to set
	 */
	public void setTerms(List<ExternalStudentTermCourses> terms) {
		this.terms = terms;
	}

}
