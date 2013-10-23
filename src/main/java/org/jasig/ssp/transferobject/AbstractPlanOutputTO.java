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
package org.jasig.ssp.transferobject;

import java.util.UUID;

import org.jasig.ssp.model.AbstractPlan;
import org.jasig.ssp.model.external.ExternalStudentFinancialAid;
import org.jasig.ssp.model.external.ExternalStudentTranscript;
import org.jasig.ssp.model.reference.MessageTemplate;

public abstract class AbstractPlanOutputTO<P extends AbstractPlan,T extends AbstractPlanTO<P>> {

	// This is a minimal template that will work for both plan and template. Not optimized.
	private T plan;
	private String outputFormat;
    private Boolean includeCourseDescription;
    private Boolean includeHeaderFooter;
    private Boolean includeTotalTimeExpected;
    private Boolean includeFinancialAidInformation;             
    private String emailTo;
    private String emailCC;
    private String notes;
    private Boolean isPrivate = false;
    private ExternalStudentFinancialAid financialAid;
    private ExternalStudentTranscript gpa;
    
    public T getNonOutputTO() {
    	return plan;
    }
    
    public void setNonOuputTO(T plan) {
    	this.plan = plan;
    }
    
	public String getOutputFormat() {
		return outputFormat;
	}
	public void setOutputFormat(String outputFormat) {
		this.outputFormat = outputFormat;
	}
	public Boolean getIncludeCourseDescription() {
		return includeCourseDescription;
	}
	public void setIncludeCourseDescription(Boolean includeCourseDescription) {
		this.includeCourseDescription = includeCourseDescription;
	}
	public Boolean getIncludeHeaderFooter() {
		return includeHeaderFooter;
	}
	public void setIncludeHeaderFooter(Boolean includeHeaderFooter) {
		this.includeHeaderFooter = includeHeaderFooter;
	}
	public Boolean getIncludeTotalTimeExpected() {
		return includeTotalTimeExpected;
	}
	public void setIncludeTotalTimeExpected(Boolean includeTotalTimeExpected) {
		this.includeTotalTimeExpected = includeTotalTimeExpected;
	}
	public Boolean getIncludeFinancialAidInformation() {
		return includeFinancialAidInformation;
	}
	public void setIncludeFinancialAidInformation(
			Boolean includeFinancialAidInformation) {
		this.includeFinancialAidInformation = includeFinancialAidInformation;
	}
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getEmailCC() {
		return emailCC;
	}
	public void setEmailCC(String emailCC) {
		this.emailCC = emailCC;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/**
	 * @return the isPrivate
	 */
	public Boolean getIsPrivate() {
		return isPrivate;
	}

	/**
	 * @param isPrivate the isPrivate to set
	 */
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
	
	/**
	 * @return the financialAid
	 */
	public ExternalStudentFinancialAid getFinancialAid() {
		return financialAid;
	}

	/**
	 * @param financialAid the financialAid to set
	 */
	public void setFinancialAid(ExternalStudentFinancialAid financialAid) {
		this.financialAid = financialAid;
	}

	/**
	 * @return the gpa
	 */
	public ExternalStudentTranscript getGpa() {
		return gpa;
	}

	/**
	 * @param gpa the gpa to set
	 */
	public void setGpa(ExternalStudentTranscript gpa) {
		this.gpa = gpa;
	}
	
	public UUID getMessageTemplateMatrixId() {
		return null;
	}

	public void setMessageTemplateId(UUID messageTemplateMatrixId) {
	}
	
	public UUID getMessageTemplateFullId() {
		return null;
	}

	public void setMessageTemplateFullId(UUID messageTemplateFullId) {
	}
}
