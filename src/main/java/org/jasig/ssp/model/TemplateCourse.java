package org.jasig.ssp.model;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="map_template_course")
public class TemplateCourse extends AbstractPlanCourse<Template> implements Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5473230782807660690L;
	
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "template_id", updatable = false, nullable = false)	
	private Template template;
	

	public Template getTemplate() {
		return template;
	}

	public void setTemplate(Template template) {
		this.template = template;
	}

	@Override
	public Template getParent() {
		return template;
	}
	
	@Override
	protected TemplateCourse clone() throws CloneNotSupportedException {
		TemplateCourse clone = new TemplateCourse();
		cloneCommonFields(clone);
		return clone;
	}

}
