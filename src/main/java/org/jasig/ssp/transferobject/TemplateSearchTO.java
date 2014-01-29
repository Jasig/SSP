package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.MapTemplateVisibility;
import org.jasig.ssp.model.ObjectStatus;

public class TemplateSearchTO {

	private MapTemplateVisibility visibility;
	private ObjectStatus objectStatus;
	private String divisionCode;
	private String programCode;
	private String departmentCode;
	
	public TemplateSearchTO() {

	}

	public TemplateSearchTO(MapTemplateVisibility visibility, ObjectStatus objectStatus,
			String divisionCode, String programCode, String departmentCode) {
		super();
		this.visibility = visibility;
		this.objectStatus = objectStatus;
		this.divisionCode = divisionCode;
		this.programCode = programCode;
		this.departmentCode = departmentCode;
	}

	public MapTemplateVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(MapTemplateVisibility visibility) {
		this.visibility = visibility;
	}

	public ObjectStatus getObjectStatus() {
		return objectStatus;
	}

	public void setObjectStatus(ObjectStatus objectStatus) {
		this.objectStatus = objectStatus;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getProgramCode() {
		return programCode;
	}

	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	public String getDepartmentCode() {
		return departmentCode;
	}

	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	
	public Boolean visibilityAll(){
		if(visibility == null)
			return true;
		return false;
	}
}
