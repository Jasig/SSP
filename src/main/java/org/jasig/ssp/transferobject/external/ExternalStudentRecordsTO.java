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
		if ( model.getGPA() != null ) {
			this.gpa = new ExternalStudentTranscriptTO(model.getGPA());
		}
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
