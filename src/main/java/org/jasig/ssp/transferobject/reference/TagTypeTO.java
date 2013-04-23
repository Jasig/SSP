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
package org.jasig.ssp.transferobject.reference;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.jasig.ssp.model.reference.TagType;
import org.jasig.ssp.transferobject.TransferObject;
import org.jasig.ssp.model.ObjectStatus;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.google.common.collect.Lists;

/**
 * TagType transfer object
 * 
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class TagTypeTO
		extends AbstractReferenceTO<TagType>
		implements TransferObject<TagType> { 

	private String code;

	/**
	 * Empty constructor
	 */
	public TagTypeTO() {
		super();
	}
	
	public TagTypeTO(final TagType model) {
		super();
		from(model);
	}

	public TagTypeTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	

	@Override
	public final void from(final TagType model) {
		if (model == null) {
			throw new IllegalArgumentException("Model can not be null.");
		}

		super.from(model);
		code = model.getCode();
		
	}

	

	public static List<TagTypeTO> toTOList(
			final Collection<TagType> models) {
		final List<TagTypeTO> tObjects = Lists.newArrayList();
		for (final TagType model : models) {
			tObjects.add(new TagTypeTO(model)); 
		}

		return tObjects;
	}
	
	public String getCode(){
		return code;
	}
	
	public void setCode(String code){
		this.code = code;
	}
	
	/*public Boolean getActive() {
		return this.getObjectStatus().equals(ObjectStatus.ACTIVE);
		}
		
	public void setActive(Boolean active) {
		if(active) {
		this.setObjectStatus(ObjectStatus.ACTIVE);
		} else {
		this.setObjectStatus(ObjectStatus.INACTIVE);
		}	
	}*/
}