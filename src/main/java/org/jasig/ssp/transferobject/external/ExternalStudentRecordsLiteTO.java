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
		// nothing to do... need access to TO factories to actually the
		// programs association, so for consistency we require the client
		// to fully hydrate any TO associations.
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
