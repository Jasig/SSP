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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jasig.ssp.model.MapTemplateVisibility;
import org.jasig.ssp.model.Template;
import org.jasig.ssp.model.TemplateCourse;
import org.jasig.ssp.model.TermNote;

/**
 */
public class TemplateTO extends AbstractPlanTO<Template> {
	
	private String divisionCode;
	
	private String departmentCode;
	
	private Boolean isPrivate;
	
	private MapTemplateVisibility visibility;
	
	private List<TemplateCourseTO> templateCourses = new ArrayList<TemplateCourseTO>(); 
	
	
	public TemplateTO(Template model) {
		super();
		from(model);
	}

	public TemplateTO() {
		super();
	}

	@Override
	public void from(Template model) {
		super.from(model);
		this.setIsPrivate(model.getIsPrivate());
		if(model.getVisibility().equals(MapTemplateVisibility.PRIVATE))
			this.setIsPrivate(true);
		else
			this.setIsPrivate(false);
		this.setVisibility(model.getVisibility());
		this.setDepartmentCode(model.getDepartmentCode());
		this.setDivisionCode(model.getDivisionCode());
		List<TemplateCourse> templateCourses = model.getTemplateCourses();
		for (TemplateCourse templateCourse : templateCourses) {
			this.getTemplateCourses().add(new TemplateCourseTO(templateCourse));
		}
		List<TermNote> termNotes = model.getTermNotes();
		for (TermNote termNote : termNotes) {
			TermNoteTO termNoteTO = new TermNoteTO(termNote);
			this.getTermNotes().add(termNoteTO);
		}
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


	public List<TemplateCourseTO> getTemplateCourses() {
		return templateCourses;
	}


	public void setTemplateCourses(List<TemplateCourseTO> templateCourses) {
		this.templateCourses = templateCourses;
	}

	public Collection<TemplateTO> asTOList(Collection<Template> rows) {
		List<TemplateTO> result = new ArrayList<TemplateTO>();
		for (Template template : rows) {
			result.add(new TemplateTO(template));
		}
		return result;
	}

	@Override
	@JsonIgnore
	public List<TemplateCourseTO> getCourses() {
		return templateCourses;
	}

	public MapTemplateVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(MapTemplateVisibility visibility) {
		this.visibility = visibility;
	}

}