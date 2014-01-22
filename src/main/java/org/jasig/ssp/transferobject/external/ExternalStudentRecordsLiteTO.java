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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.jasig.ssp.model.external.ExternalStudentAcademicProgram;
import org.jasig.ssp.model.external.ExternalStudentFinancialAid;
import org.jasig.ssp.model.external.ExternalStudentFinancialAidAwardTerm;
import org.jasig.ssp.model.external.ExternalStudentFinancialAidFile;
import org.jasig.ssp.model.external.ExternalStudentRecordsLite;
import org.jasig.ssp.model.external.ExternalStudentTermCourses;

public class ExternalStudentRecordsLiteTO implements ExternalDataTO<ExternalStudentRecordsLite>,
		Serializable {
	
	public ExternalStudentRecordsLiteTO(ExternalStudentRecordsLite model, BigDecimal balanceOwed){
		super();
		from(model);
		if(financialAid != null)
			financialAid.setBalanceOwed(balanceOwed);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6292151242012755022L;
	
	private ExternalStudentTranscriptTO gpa;
	
	private List<ExternalStudentAcademicProgramTO> programs;
	
	private ExternalStudentFinancialAidTO financialAid;
	
	private List<ExternalStudentFinancialAidAwardTermTO> financialAidAcceptedTerms;
	
	private List<ExternalStudentFinancialAidFileTO> financialAidFiles;
	
	

	/**
	 * @return the financialAid
	 */
	public ExternalStudentFinancialAidTO getFinancialAid() {
		return financialAid;
	}

	/**
	 * @param financialAid the financialAid to set
	 */
	public void setFinancialAid(ExternalStudentFinancialAidTO financialAid) {
		this.financialAid = financialAid;
	}

	@Override
	public void from(ExternalStudentRecordsLite model) {
		if ( model.getGPA() != null ) {
			this.gpa = new ExternalStudentTranscriptTO(model.getGPA());
		}
		if ( model.getPrograms() != null && !model.getPrograms().isEmpty()) {
			this.programs = new ArrayList<ExternalStudentAcademicProgramTO>();
			for(ExternalStudentAcademicProgram program:model.getPrograms())
				this.programs.add(new ExternalStudentAcademicProgramTO(program));
		}
		
		if( model.getFinancialAid() != null)
			financialAid = new ExternalStudentFinancialAidTO(model.getFinancialAid(), null);
		
		if(model.getFinancialAidAcceptedTerms() != null  && !model.getFinancialAidAcceptedTerms().isEmpty()){
			this.financialAidAcceptedTerms = new ArrayList<ExternalStudentFinancialAidAwardTermTO>();
			for(ExternalStudentFinancialAidAwardTerm term:model.getFinancialAidAcceptedTerms())
				this.financialAidAcceptedTerms.add(new ExternalStudentFinancialAidAwardTermTO(term));
		}
		
		if(model.getFinancialAidFiles() != null  && !model.getFinancialAidFiles().isEmpty()){
			this.financialAidFiles = new ArrayList<ExternalStudentFinancialAidFileTO>();
			for(ExternalStudentFinancialAidFile term:model.getFinancialAidFiles())
				this.financialAidFiles.add(new ExternalStudentFinancialAidFileTO(term));
		}
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

	public List<ExternalStudentFinancialAidAwardTermTO> getFinancialAidAcceptedTerms() {
		return financialAidAcceptedTerms;
	}

	public void setFinancialAidAcceptedTerms(
			List<ExternalStudentFinancialAidAwardTermTO> financialAidAcceptedTerms) {
		this.financialAidAcceptedTerms = financialAidAcceptedTerms;
	}

	public List<ExternalStudentFinancialAidFileTO> getFinancialAidFiles() {
		return financialAidFiles;
	}

	public void setFinancialAidFiles(
			List<ExternalStudentFinancialAidFileTO> financialAidFiles) {
		this.financialAidFiles = financialAidFiles;
	}
	
	public String getAllFinancialAidTermsOutput(){
		StringBuilder output = new StringBuilder("");
		if(getFinancialAidAcceptedTerms() != null && getFinancialAidAcceptedTerms().size() > 0){
			for(ExternalStudentFinancialAidAwardTermTO to:getFinancialAidAcceptedTerms()){
				output.append(to.getTermCode())
				.append(":").
				append(to.getAccepted()).
				append(", ");
			}
		}
		if(output.length() > 2){
			return output.substring(0, output.length() - 2);
		}
		return "";
	}

	
	public String getFinancialAidAcceptedTermsOutput(){
		StringBuilder output = new StringBuilder("");
		String accepted = "Y";
		if(getFinancialAidAcceptedTerms() != null && getFinancialAidAcceptedTerms().size() > 0){
			for(ExternalStudentFinancialAidAwardTermTO to:getFinancialAidAcceptedTerms()){
				if(to.getAccepted().equalsIgnoreCase(accepted)){
					output.append(to.getTermCode())
							.append(":").
							append(to.getAccepted()).
							append(", ");
				}
			}
		}
		if(output.length() > 2){
			return output.substring(0, output.length() - 2);
		}
		return "";
	}
}
