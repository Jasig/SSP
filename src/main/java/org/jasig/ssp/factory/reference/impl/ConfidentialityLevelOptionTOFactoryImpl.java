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
package org.jasig.ssp.factory.reference.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.jasig.ssp.factory.reference.ConfidentialityLevelOptionTOFactory;
import org.jasig.ssp.security.permissions.DataPermissions;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.transferobject.reference.ConfidentialityLevelOptionTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ConfidentialityLevelOptionTOFactoryImpl
		implements ConfidentialityLevelOptionTOFactory {

	public ConfidentialityLevelOptionTOFactoryImpl() {
	}

	@Override
	public ConfidentialityLevelOptionTO from(DataPermissions model) {
		return new ConfidentialityLevelOptionTO(model);
	}

	@Override
	public DataPermissions from(ConfidentialityLevelOptionTO tObject)
			throws ObjectNotFoundException {
		return DataPermissions.valueOf(tObject.getName());
	}

	@Override
	public DataPermissions from(UUID id) throws ObjectNotFoundException {
		return null;
	}

	@Override
	public List<ConfidentialityLevelOptionTO> asTOList(
			Collection<DataPermissions> models) {
		List<ConfidentialityLevelOptionTO> TOs = new ArrayList<ConfidentialityLevelOptionTO>();
		for (DataPermissions dataPermissions : models) {
			TOs.add(from(dataPermissions));
		}
		return TOs;
	}

	@Override
	public Set<ConfidentialityLevelOptionTO> asTOSet(
			Collection<DataPermissions> models) {
		Set<ConfidentialityLevelOptionTO> TOs = new HashSet<ConfidentialityLevelOptionTO>();
		for (DataPermissions dataPermissions : models) {
			TOs.add(from(dataPermissions));
		}
		return TOs;
	}

	@Override
	public Set<DataPermissions> asSet(
			Collection<ConfidentialityLevelOptionTO> tObjects)
			throws ObjectNotFoundException {
		Set<DataPermissions> models = new HashSet<DataPermissions>();
		for (ConfidentialityLevelOptionTO confidentialityLevelOptionTO : tObjects) {
			models.add(from(confidentialityLevelOptionTO));
		}
		return models;
	}


}
