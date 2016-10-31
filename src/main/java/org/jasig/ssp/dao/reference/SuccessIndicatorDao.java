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

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.SuccessIndicator;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public class SuccessIndicatorDao extends AbstractReferenceAuditableCrudDao<SuccessIndicator>
        implements AuditableCrudDao<SuccessIndicator> {

    public SuccessIndicatorDao() {
        super(SuccessIndicator.class);
    }

    public List<SuccessIndicator> getByShowInCaseload(final boolean showInCaseload) {
        final Criteria query = createCriteria();
        query.add(Restrictions.eq("showInCaseload", showInCaseload));
        query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
        return query.list();
    }

    public List<SuccessIndicator> getByGenerateEarlyAlert(final boolean generateEarlyAlert) {
        final Criteria query = createCriteria();
        query.add(Restrictions.eq("generateEarlyAlert", generateEarlyAlert));
        query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
        return query.list();
    }

    public List<SuccessIndicator> getWithShowInCaseloadOrGenerateEarlyAlert
            (final boolean showInCaseload, final boolean generateEarlyAlert) {
        final Criteria query = createCriteria();
        query.add(Restrictions.or(Restrictions.eq("showInCaseload", showInCaseload),
                 Restrictions.eq("generateEarlyAlert", generateEarlyAlert)));
        query.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
        return query.list();
    }
}
