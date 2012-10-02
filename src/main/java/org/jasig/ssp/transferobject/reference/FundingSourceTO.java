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

import org.jasig.ssp.model.reference.FundingSource;
import org.jasig.ssp.transferobject.TransferObject;

import com.google.common.collect.Lists;

public class FundingSourceTO extends AbstractReferenceTO<FundingSource>
		implements TransferObject<FundingSource> {

	public FundingSourceTO() {
		super();
	}

	public FundingSourceTO(final UUID id, final String name,
			final String description) {
		super(id, name, description);
	}

	public FundingSourceTO(final FundingSource model) {
		super();
		from(model);
	}

	public static List<FundingSourceTO> toTOList(
			final Collection<FundingSource> models) {
		final List<FundingSourceTO> tObjects = Lists.newArrayList();
		for (FundingSource model : models) {
			tObjects.add(new FundingSourceTO(model));
		}
		return tObjects;
	}
}
