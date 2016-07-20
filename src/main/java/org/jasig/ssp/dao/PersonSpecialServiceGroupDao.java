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
package org.jasig.ssp.dao;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.PersonSpecialServiceGroup;
import org.jasig.ssp.model.ScheduledApplicationTaskStatus;
import org.jasig.ssp.model.ScheduledTaskStatus;
import org.jasig.ssp.model.reference.SpecialServiceGroup;
import org.jasig.ssp.service.ScheduledApplicationTaskStatusService;
import org.jasig.ssp.service.impl.ScheduledTaskWrapperServiceImpl;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Repository
public class PersonSpecialServiceGroupDao
		extends AbstractPersonAssocAuditableCrudDao<PersonSpecialServiceGroup>
		implements PersonAssocAuditableCrudDao<PersonSpecialServiceGroup> {

    @Autowired
    private transient ScheduledApplicationTaskStatusService scheduledApplicationTaskService;

	/**
	 * Constructor
	 */
	public PersonSpecialServiceGroupDao() {
		super(PersonSpecialServiceGroup.class);
	}

	public PagingWrapper<PersonSpecialServiceGroup> getAllForPersonIdAndSpecialServiceGroupId(
			final UUID personId, final UUID specialServiceGroupId,
			final SortingAndPaging sAndP) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
		criteria.add(Restrictions.eq("specialServiceGroup.id",
				specialServiceGroupId));
		return processCriteriaWithStatusSortingAndPaging(criteria, sAndP);
	}

	public List<String> getAllCodesForPersonId(final UUID personId) {
		final Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id", personId));
        criteria.setProjection(Projections.property("ssg.code"));
		criteria.createAlias("specialServiceGroup", "ssg");

        return criteria.list();
	}

    public Map<String, Pair<String, List<String>>> getAllSSGNamesWithCampusForInternalAndExternalStudentsWithSSGs(
                                                                            final List<SpecialServiceGroup> ssgParams) {

        if (CollectionUtils.isEmpty(ssgParams)) {
            return Maps.newHashMap();
        }
        
        final ScheduledApplicationTaskStatus status = scheduledApplicationTaskService.getByName(ScheduledTaskWrapperServiceImpl.REFRESH_DIRECTORY_PERSON_TASK_NAME);
        final ScheduledApplicationTaskStatus status_blue = scheduledApplicationTaskService.getByName(ScheduledTaskWrapperServiceImpl.REFRESH_DIRECTORY_PERSON_BLUE_TASK_NAME);

        final StringBuilder fromClause = new StringBuilder();
        if (status != null && status.getStatus() != null && status.getStatus().equals(ScheduledTaskStatus.COMPLETED)) {
            fromClause.append("from MaterializedDirectoryPerson as dp, ");
        } else if (status_blue != null && status_blue.getStatus() != null && status_blue.getStatus().equals(
                ScheduledTaskStatus.COMPLETED)) {
            fromClause.append("from MaterializedDirectoryPersonBlue as dp, ");
        }
        fromClause.append(" PersonSpecialServiceGroup as pssg, ExternalStudentSpecialServiceGroup as esssg, " +
                "SpecialServiceGroup secondssg left join pssg.specialServiceGroup as specialServiceGroup ");

        final String whereClause = "where " +
            "((pssg.person.id = dp.personId and dp.objectStatus = :personObjectStatus and specialServiceGroup in (:specialServiceGroup) and specialServiceGroup is not null) " +
            "or " +
            "(dp.personId is null and dp.schoolId = esssg.schoolId and esssg.code = secondssg.code and secondssg in (:specialServiceGroup) and secondssg is not null)) ";

        final String specialServiceCourseReportHQLQuery = "select "
                +   "dp.schoolId, "
                +   "specialServiceGroup.name, "
                +   "secondssg.name, "
                +   "dp.campusName "
                + fromClause + whereClause
                + "order by dp.schoolId";

        final Query query = createHqlQuery(specialServiceCourseReportHQLQuery);
        query.setParameter("personObjectStatus", ObjectStatus.ACTIVE);
        query.setParameterList("specialServiceGroup", ssgParams);

        final List<Object[]> rawResults = query.list();

        final Map<String, Pair<String, List<String>>> results = Maps.newHashMap();
        for (Object[] index : rawResults) {
            loadQueryResultMap(results, index);
        }

        return results;
    }

    public void deleteAllForPerson(final UUID personId) {
        final Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("person.id", personId));
        final List<PersonSpecialServiceGroup> personSpecialServiceGroups = criteria.list();

        if (CollectionUtils.isNotEmpty(personSpecialServiceGroups)) {
            for (PersonSpecialServiceGroup personSpecialServiceGroup : personSpecialServiceGroups) {
                this.delete(personSpecialServiceGroup);
            }
            sessionFactory.getCurrentSession().flush();
        } else {
            return;
        }
    }

    public void deleteForPersonByCode(final String code, final UUID id) {
        final Criteria criteria = createCriteria();
        criteria.add(Restrictions.eq("person.id", id));
        criteria.add(Restrictions.eq("ssg.code", code));
        criteria.createAlias("specialServiceGroup", "ssg");
        final PersonSpecialServiceGroup personSpecialServiceGroup = (PersonSpecialServiceGroup) criteria.uniqueResult();

        if (personSpecialServiceGroup != null) {
            this.delete(personSpecialServiceGroup);
            sessionFactory.getCurrentSession().flush();
        } else {
            return;
        }
    }

    private void loadQueryResultMap(final Map<String, Pair<String, List<String>>> results, Object[] record) {
        if (record != null && record[0] != null && (record[1] != null || record[2] != null)) {
            String schoolId = (String) record[0];
            String campusName = "";
            String ssgName;

            if (record[3] != null) {
                campusName = (String) record[3];
            }

            if (record[1] == null && record[2] != null) {
                ssgName = (String) record[2];
            } else {
                ssgName = (String) record[1];
            }

            if (results.containsKey(record[0])) {
                results.get(schoolId).getSecond().add(ssgName);
            } else {
                final List<String> ssgNames = Lists.newArrayList(ssgName);
                results.put(schoolId, new Pair<>(campusName, ssgNames));
            }
        }
    }
}