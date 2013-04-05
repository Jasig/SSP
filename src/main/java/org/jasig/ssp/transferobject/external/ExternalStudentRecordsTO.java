/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject.external;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.jasig.ssp.model.external.ExternalStudentRecords;
import org.jasig.ssp.model.external.ExternalStudentTermCourses;
import org.jasig.ssp.model.external.ExternalStudentTranscriptCourse;


public class ExternalStudentRecordsTO implements ExternalDataTO<ExternalStudentRecords>,
		Serializable {
	
	public ExternalStudentRecordsTO()
	{
		super();
	}
	
	public ExternalStudentRecordsTO(ExternalStudentRecords model)
	{
		super();
		from(model);
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 3130617569350185476L;

	@Override
	public void from(ExternalStudentRecords model) {
		if ( model.getGPA() != null ) {
			this.gpa = new ExternalStudentTranscriptTO(model.getGPA());
		}
		if ( model.getPrograms() != null && !model.getPrograms().isEmpty()) {
			this.programs = new ArrayList<ExternalStudentAcademicProgramTO>();
			for(ExternalStudentAcademicProgram program:model.getPrograms())
				this.programs.add(new ExternalStudentAcademicProgramTO(program));
		}
		if ( model.getTerms() != null && !model.getTerms().isEmpty()) {
			this.terms = new ArrayList<ExternalStudentTranscriptCourseTO>();
			for(ExternalStudentTranscriptCourse term:model.getTerms())
				this.terms.add(new ExternalStudentTranscriptCourseTO(term));
		}
		if(model.getFinancialAid() != null)
			financialAid = new ExternalStudentFinancialAidTO(model.getFinancialAid());
	}
	
	private ExternalStudentTranscriptTO gpa;
	private List<ExternalStudentAcademicProgramTO> programs;
	private List<ExternalStudentTranscriptCourseTO> terms;
	private ExternalStudentFinancialAidTO financialAid;
	/**
	 * @return the financial
	 */
	public ExternalStudentFinancialAidTO getFinancialAid() {
		return financialAid;
	}
	/**
	 * @param financial the financial to set
	 */
	public void setFinancialAid(ExternalStudentFinancialAidTO financial) {
		this.financialAid = financial;
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
	

	/**
	 * @return the terms
	 */
	public List<ExternalStudentTranscriptCourseTO> getTerms() {
		return terms;
	}
	/**
	 * @param terms the terms to set
	 */
	public void setTerms(List<ExternalStudentTranscriptCourseTO> terms) {
		this.terms = terms;
	}

}
