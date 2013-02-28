package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.util.List;

import org.jasig.ssp.model.external.ExternalStudentRecords;


public class ExternalStudentRecordsTO implements ExternalDataTO<ExternalStudentRecords>,
		Serializable {
	
	public ExternalStudentRecordsTO()
	{
		super();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3130617569350185476L;

	@Override
	public void from(ExternalStudentRecords model) {
		// TODO Auto-generated method stub
		this.gpa = new ExternalStudentTranscriptTO(model.getGPA());
	}
	
	private ExternalStudentTranscriptTO gpa;
	private List<ExternalStudentAcademicProgramTO> programs;
	private List<ExternalStudentTermCoursesTO> terms;
	/**
	 * @return the gpq
	 */
	public ExternalStudentTranscriptTO getGpq() {
		return gpa;
	}
	/**
	 * @param gpq the gpq to set
	 */
	public void setGpq(ExternalStudentTranscriptTO gpq) {
		this.gpa = gpq;
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
	

	/**
	 * @return the terms
	 */
	public List<ExternalStudentTermCoursesTO> getTerms() {
		return terms;
	}
	/**
	 * @param terms the terms to set
	 */
	public void setTerms(List<ExternalStudentTermCoursesTO> terms) {
		this.terms = terms;
	}

}
