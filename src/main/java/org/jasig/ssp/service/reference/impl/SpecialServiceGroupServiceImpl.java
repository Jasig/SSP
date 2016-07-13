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
package org.jasig.ssp.service.reference.impl;

import org.jasig.ssp.dao.reference.SpecialServiceGroupDao;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.SpecialServiceGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Service
@Transactional
public class SpecialServiceGroupServiceImpl extends AbstractReferenceService<SpecialServiceGroup>
		implements SpecialServiceGroupService {

	@Autowired
	transient private SpecialServiceGroupDao dao;

	protected void setDao(final SpecialServiceGroupDao dao) {
		this.dao = dao;
	}

	@Override
	protected SpecialServiceGroupDao getDao() {
		return dao;
	}

	@Override
	public SpecialServiceGroup getByCode(@NotNull final String code) throws ObjectNotFoundException {
		return this.dao.getByCode(code);
	}

    @Override
    public List<SpecialServiceGroup> getByNotifyOnWithdraw(final boolean notifyOnWithdraw) throws ObjectNotFoundException {
        return this.dao.getByNotifyOnWithdraw(notifyOnWithdraw);
    }
}
