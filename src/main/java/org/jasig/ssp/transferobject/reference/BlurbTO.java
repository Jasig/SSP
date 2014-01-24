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

import org.jasig.ssp.model.reference.Blurb;
import org.jasig.ssp.model.reference.EnrollmentStatus;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class BlurbTO extends AbstractReferenceTO<Blurb>
		implements TransferObject<Blurb> {

	private String code;
	
	private String value;
	
	public BlurbTO() {
		super();
	}

	public BlurbTO(final Blurb model) {
		super();
		from(model);
	}

	@Override
	public void from(Blurb model) {
		super.from(model);
		setCode(model.getCode());
		setValue(model.getValue());
	};
	
	public static List<BlurbTO> toTOList(
			final Collection<Blurb> models) {
		final List<BlurbTO> tObjects = Lists.newArrayList();
		for (Blurb model : models) {
			tObjects.add(new BlurbTO(model));
		}
		return tObjects;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
