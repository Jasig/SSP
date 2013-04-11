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

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Elective;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

/**
 * The <code>@JsonIgnoreProperties(ignoreUnknown = true)</code> is here b/c
 * front-end admin tools don't use a proper
 * <code>Ext.data.writer.Writer</code> (see
 * <code>AbstractReferenceAdminViewController.addRecord()</code> and
 * <code>editRecord()</code>. So it's hard for us to control what fields get
 * passed back to the server and which don't. E.g. the 'persist' config
 * on <code>Ext.data.Field</code> is useless. Of course, silently ignoring
 * inbound fields you don't know about isn't bad default practice anyway.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElectiveTO extends AbstractReferenceTO<Elective>
		implements TransferObject<Elective>, Serializable {

	private static final long serialVersionUID = 6475160974734656210L;

	private String code;	
	private Integer sortOrder;

	public ElectiveTO() {
		super();
	}

	public ElectiveTO(final Elective model) {
		super();
		from(model);
	}
	
	public ElectiveTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}	
	
	@Override
	public final void from(final Elective model) {
		super.from(model);
		code = model.getCode();
		sortOrder = model.getSortOrder();
		
	}

	public static List<ElectiveTO> toTOList(
			final Collection<Elective> models) {
		final List<ElectiveTO> tObjects = Lists.newArrayList();
		for (Elective model : models) {
			tObjects.add(new ElectiveTO(model));
		}
		return tObjects;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}	

}