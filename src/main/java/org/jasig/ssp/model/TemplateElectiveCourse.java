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
package org.jasig.ssp.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="map_template_elective_course")
public class TemplateElectiveCourse extends AbstractMapElectiveCourse implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5473230782807660690L;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "template_id", updatable = false, nullable = false)	
	private Template template;

	@OneToMany(fetch = FetchType.LAZY)
	@Cascade({ CascadeType.PERSIST, CascadeType.MERGE, CascadeType.SAVE_UPDATE })
	@JoinColumn(name = "map_template_elective_course_id")
	@OrderBy("formattedCourse")
	private List<TemplateElectiveCourseElective> templateElectiveCourseElectives = new ArrayList<TemplateElectiveCourseElective>(0);

	public List<TemplateElectiveCourseElective> getElectiveCourseElectives() {
		return templateElectiveCourseElectives;
	}

	public void setElectiveCourseElectives(List<TemplateElectiveCourseElective> templateElectiveCourseElectives) {
		this.templateElectiveCourseElectives = templateElectiveCourseElectives;
	}


	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	public Template getParent() {
		return template;
	}
	
	@Override
	protected TemplateElectiveCourse clone() throws CloneNotSupportedException {
		TemplateElectiveCourse clone = new TemplateElectiveCourse();
		cloneCommonFields(clone);
		for (TemplateElectiveCourseElective templateElectiveCourseElective : this.getElectiveCourseElectives()) {
			TemplateElectiveCourseElective templateElectiveCourseElectiveClone = templateElectiveCourseElective.clone();
			templateElectiveCourseElectiveClone.setTemplateElectiveCourse(clone);
			clone.getElectiveCourseElectives().add(templateElectiveCourseElectiveClone);
		}
		return clone;
	}

}
