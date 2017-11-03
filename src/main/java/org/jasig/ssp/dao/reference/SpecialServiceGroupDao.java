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
package org.jasig.ssp.dao.reference;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Data access class for the SpecialServiceGroup reference entity.
 */
@Repository
public class SpecialServiceGroupDao extends AbstractReferenceAuditableCrudDao<SpecialServiceGroup>
		implements AuditableCrudDao<SpecialServiceGroup> {

	public SpecialServiceGroupDao() {
		super(SpecialServiceGroup.class);
	}

	public SpecialServiceGroup getByCode(final String code) {
		// TODO: (performance) Perfect example of data that should be cached
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("code", code));
		return (SpecialServiceGroup) query.uniqueResult();
	}

    public List<SpecialServiceGroup> getByNotifyOnWithdraw(final boolean notifyOnWithdraw) {
        final Criteria query = createCriteria();
        query.add(Restrictions.eq("notifyOnWithdraw", notifyOnWithdraw));
		query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
        return query.list();
    }

	@Override
	public PagingWrapper<SpecialServiceGroup> getAll(
			final SortingAndPaging sAndP) {
		SortingAndPaging sp = sAndP;
		if (sp == null) {
			sp = new SortingAndPaging(ObjectStatus.ACTIVE, null, null, null);
		}

		if (!sp.isSorted()) {
			sp.appendSortField("name", SortDirection.ASC);
		}

		return super.getAll(sp);
	}

    public List<SpecialServiceGroup> getByIds(List<UUID> specialServiceGroupIds) {
    	if (CollectionUtils.isEmpty(specialServiceGroupIds)) {
    		return Lists.newArrayList();
		}

        final Criteria query = createCriteria();
        query.add(Restrictions.in("id", specialServiceGroupIds));
        return query.list();
    }
}