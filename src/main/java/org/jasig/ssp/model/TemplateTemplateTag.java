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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.jasig.ssp.model.reference.MapTemplateTag;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Templates may have zero or multiple map template tags.
 * 
 * The TemplateTemplateTag entity is an associative mapping between a templates
 * and any map template tags.
 *
 * @author mike.sultzaberger
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="map_template_template_tag")
public class TemplateTemplateTag
		extends AbstractAuditable
		implements Auditable, Cloneable {

	private static final long serialVersionUID = 4989840121708000902L;

	/**
	 * Associated template.
	 *
	 * <p>
	 * This association should never be changed after creation.
	 */
	@NotNull
	@ManyToOne()
	@JoinColumn(name = "template_id", updatable = false, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Template template;

	/**
	 * Associated map template tag.
	 *
	 * <p>
	 * This association should never be changed after creation.
	 */
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "map_template_tag_id", updatable = false, nullable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private MapTemplateTag mapTemplateTag;

	public TemplateTemplateTag() {
		super();
	}

	public TemplateTemplateTag(@NotNull final Template template,
                               @NotNull final MapTemplateTag mapTemplateTag) {
		super();
		this.template = template;
		this.mapTemplateTag = mapTemplateTag;
	}

	@Override
	protected int hashPrime() {
		return 5;
	}

	@Override
	final public int hashCode() {
		int result = hashPrime();

		// AbstractAuditable properties
		result *= hashField("id", getId());
		result *= hashField("objectStatus", getObjectStatus());

		result *= hashField("template", template);
		result *= hashField("mapTemplateTag", mapTemplateTag);

		return result;
	}
}