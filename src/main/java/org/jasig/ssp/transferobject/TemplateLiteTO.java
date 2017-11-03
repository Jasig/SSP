/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.transferobject;

import org.jasig.ssp.model.AbstractPlanCourse;
import org.jasig.ssp.model.MapTemplateVisibility;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.reference.MapTemplateTag;
import org.jasig.ssp.transferobject.reference.MapTemplateTagTO;

import java.util.ArrayList;
import java.util.List;

/**
 * TO for /summary API call.  In early phases of development this will not hold a lot of meaningful data.
 * 
 * 
 */
public class TemplateLiteTO extends AbstractPlanTO<Template> {

	private String programCode;
	
	private String divisionCode;
	
	private String departmentCode;
	
	private Boolean isPrivate;
	
	private MapTemplateVisibility visibility;

	private List<MapTemplateTagTO> mapTemplateTags = new ArrayList<>();
	
	/**
	 * Empty constructor.
	 */
	public TemplateLiteTO() {
		super();
	}
	/**
	 * Empty constructor.
	 */
	public TemplateLiteTO(Template model) {
		super();
		from(model);
	}	
	
	@Override
	public void from(Template model) {
		super.from(model);
		this.setIsPrivate(model.getIsPrivate());
		this.setVisibility(model.getVisibility());
		this.setDepartmentCode(model.getDepartmentCode());
		this.setDivisionCode(model.getDivisionCode());
		this.setProgramCode(model.getProgramCode());
		for (MapTemplateTag mapTemplateTag : model.getMapTemplateTags()) {
			this.getMapTemplateTags().add(new MapTemplateTagTO(mapTemplateTag));
		}
	}
	
	@Override
	public List<? extends AbstractPlanCourseTO<Template, ? extends AbstractPlanCourse<Template>>> getCourses() {
		return null;
	}
	public String getProgramCode() {
		return programCode;
	}
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public MapTemplateVisibility getVisibility() {
		return visibility;
	}
	public void setVisibility(MapTemplateVisibility visibility) {
		this.visibility = visibility;
	}

	public List<MapTemplateTagTO> getMapTemplateTags() {
		return mapTemplateTags;
	}

	public void setMapTemplateTags(List<MapTemplateTagTO> mapTemplateTags) {
		this.mapTemplateTags = mapTemplateTags;
	}
}