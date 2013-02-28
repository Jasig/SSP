package org.jasig.ssp.model.external;

import java.io.Serializable;
import java.util.List;

public class ExternalStudentRecordsLite extends AbstractExternalData implements
		ExternalData, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5994592133348666761L;
	

	private ExternalStudentTranscript gpa;
	private List<ExternalStudentAcademicProgram> programs;
	/**
	 * @return the gpq
	 */
	public ExternalStudentTranscript getGPA() {
		return gpa;
	}
	/**
	 * @param gpq the gpq to set
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

}
