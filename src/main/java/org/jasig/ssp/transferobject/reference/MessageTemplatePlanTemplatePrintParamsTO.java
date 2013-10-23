package org.jasig.ssp.transferobject.reference;

import org.jasig.ssp.model.Template;
import org.jasig.ssp.transferobject.TemplateOutputTO;
import org.jasig.ssp.transferobject.TemplateTO;

public class MessageTemplatePlanTemplatePrintParamsTO extends
		AbstractMessageTemplateMapPrintParamsTO<TemplateOutputTO, Template, TemplateTO> {

	// Added from Message Templates
	private String programName;
	
	// Added from Message Templates
	private String divisionName;
	
	// Added from Message Templates
	private String departmentName;
	
	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
