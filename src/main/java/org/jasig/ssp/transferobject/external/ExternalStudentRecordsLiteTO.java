package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.util.List;

import org.jasig.ssp.model.external.ExternalStudentRecordsLite;

public class ExternalStudentRecordsLiteTO implements ExternalDataTO<ExternalStudentRecordsLite>,
		Serializable {
	
	public ExternalStudentRecordsLiteTO(ExternalStudentRecordsLite model){
		super();
		from(model);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6292151242012755022L;
	
	private ExternalStudentTranscriptTO gpa;
	
	private List<ExternalStudentAcademicProgramTO> programs;
	


	@Override
	public void from(ExternalStudentRecordsLite model) {
		
		gpa = new ExternalStudentTranscriptTO(model.getGPA());
	}

	/**
	 * @return the gpa
	 */
	public ExternalStudentTranscriptTO getGpa() {
		return gpa;
	}

	/**
	 * @param gpa the gpa to set
	 */
	public void setGpa(ExternalStudentTranscriptTO gpa) {
		this.gpa = gpa;
	}

	/**
	 * @return the programs
	 */
	public List<ExternalStudentAcademicProgramTO> getPrograms() {
		return programs;
	}

	/**
	 * @param programs the programs to set
	 */
	public void setPrograms(List<ExternalStudentAcademicProgramTO> programs) {
		this.programs = programs;
	}

}
