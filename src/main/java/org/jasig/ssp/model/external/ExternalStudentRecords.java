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
	 * @param gpa the gpa to set
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
