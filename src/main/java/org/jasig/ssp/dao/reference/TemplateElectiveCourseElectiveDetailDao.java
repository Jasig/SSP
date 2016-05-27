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
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AbstractAuditableCrudDao;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.TemplateElectiveCourse;
import org.jasig.ssp.model.TemplateElectiveCourseElective;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jboss.logging.Logger;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Data access class for the TemplateElectiveCourse reference entity.
 */
@Repository
public class TemplateElectiveCourseElectiveDetailDao extends
		AbstractAuditableCrudDao<TemplateElectiveCourseElective>
		implements AuditableCrudDao<TemplateElectiveCourseElective> {

//	@Autowired
//	private JournalStepDao journalStepDao;

	private Logger logger = Logger.getLogger(TemplateElectiveCourseElectiveDetailDao.class);

	public TemplateElectiveCourseElectiveDetailDao() {
		super(TemplateElectiveCourseElective.class);
	}

	public void softDeleteReferencingAssociations(UUID id) throws ObjectNotFoundException {
		TemplateElectiveCourseElective obj = get(id);
//		String softDeleteAssociations = "update JournalStepJournalStepDetail set objectStatus = :objectStatus where journalStepDetail = :journalStepDetail";
//		createHqlQuery(softDeleteAssociations).setInteger("objectStatus", ObjectStatus.INACTIVE.ordinal()).setEntity("journalStepDetail", obj).executeUpdate();
	}

	public List<TemplateElectiveCourseElective> getAllElectivesForElectiveCourse(TemplateElectiveCourse templateElectiveCourse) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("templateElectiveCourse", templateElectiveCourse));
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		criteria.addOrder(Order.asc("formattedCourse"));
		return criteria.list();

//		SortingAndPaging sp = sAndP;
//		if (sp == null) {
//			sp = new SortingAndPaging(ObjectStatus.ACTIVE);
//		}
//
//		if (!sp.isSorted()) {
//			sp.appendSortField("objectStatus", SortDirection.ASC);
//			sp.appendSortField("formattedCourse", SortDirection.ASC);
//		}
//
//		return super.getAll(sp);
	}


//	public JournalStepDao getJournalStepDao() {
//		return journalStepDao;
//	}


//	public void setJournalStepDao(JournalStepDao journalStepDao) {
//		this.journalStepDao = journalStepDao;
//	}
}