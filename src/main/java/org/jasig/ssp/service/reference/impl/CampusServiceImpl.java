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

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.reference.CampusDao;
import org.jasig.ssp.model.reference.Campus;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.CampusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

/**
 * Campus service implementation
 * 
 * @author jon.adams
 */
@Service
@Transactional
public class CampusServiceImpl extends AbstractReferenceService<Campus> implements CampusService {

	@Autowired
	transient private CampusDao dao;

	/**
	 * Set the DAO instance
	 * 
	 * @param dao
	 *            The DAO instance
	 */
	protected void setDao(final CampusDao dao) {
		this.dao = dao;
	}

	@Override
	protected CampusDao getDao() {
		return dao;
	}

	@Override
	public Campus getByCode(@NotNull final String code)
			throws ObjectNotFoundException {
		return this.dao.getByCode(code);
	}

    @Override
    public List<Campus> get(final List<UUID> campusIds) {
        return this.dao.getByIds(campusIds);
    }
}