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
package org.jasig.ssp.dao;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Plan;
import org.jasig.ssp.transferobject.external.SearchExternalCourseTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.transferobject.reports.PlanAdvisorCountTO;
import org.jasig.ssp.transferobject.reports.PlanCourseCountTO;
import org.jasig.ssp.transferobject.reports.PlanStudentStatusTO;
import org.jasig.ssp.transferobject.reports.SearchPlanTO;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

@Repository
public class PlanDao extends AbstractPlanDao<Plan> implements AuditableCrudDao<Plan> {


	public PlanDao() {
		super(Plan.class);
	}

//	@Override
//	protected Criteria createCriteria() {
//		Criteria c = super.createCriteria();
//		c.setFetchMode("planCourses.elective.color", FetchMode.SELECT);
//		return c;
//	}
	
	@SuppressWarnings("unchecked")
	public List<Plan> getAllForStudent(UUID id)
	{
		List<Plan> plans = createCriteria()
		.add(Restrictions.eq("person.id", id)).list();
		return plans;
	}
	
	public Plan getActivePlanForStudent(UUID id)
	{
		Plan activePlan = (Plan) createCriteria()
		.add(Restrictions.eq("person.id", id))
		.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE))
		.uniqueResult();
		return activePlan;
	}

	
	public PagingWrapper<Plan> getAllForStudent(final SortingAndPaging sAndP,UUID personId) {
		Criteria criteria = createCriteria();
		criteria.add(Restrictions.eq("person.id",personId));
		return processCriteriaWithStatusSortingAndPaging(criteria,
				sAndP);
	}

	public int markOldPlansAsInActive(Plan plan) {
		int updatedEntities = 0;
		String markOldPlansAsInActiveBaseQuery = "update Plan p set p.objectStatus = :objectStatus where p.person = :person and p != :plan";
		updatedEntities += createHqlQuery( markOldPlansAsInActiveBaseQuery )
				.setInteger("objectStatus", ObjectStatus.INACTIVE.ordinal() )
				.setEntity("person", plan.getPerson())
				.setEntity("plan", plan)
				.executeUpdate();
		return updatedEntities;
	}
	
	@SuppressWarnings("unchecked")
	public List<PlanAdvisorCountTO> getAdvisorsPlanCount(SearchPlanTO form){
		Criteria criteria = createCriteria();
		
		if(form.getDateFrom() != null)
			criteria.add(Restrictions.ge("modifiedDate", form.getDateFrom()));
		if(form.getDateTo() != null)
			criteria.add(Restrictions.lt("modifiedDate", form.getDateTo()));
		
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.ACTIVE));
		
		criteria.setProjection(Projections.projectionList().
        		add(Projections.countDistinct("id").as("plan_entityCount")).
        		add(Projections.groupProperty("createdBy").as("plan_coach")));
		
		List<EntityStudentCountByCoachTO> activePlansByCoaches = criteria.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EntityStudentCountByCoachTO.class, "plan_")).list();
		
		criteria = createCriteria();
		
		if(form.getDateFrom() != null)
			criteria.add(Restrictions.ge("modifiedDate", form.getDateFrom()));
		if(form.getDateTo() != null)
			criteria.add(Restrictions.lt("modifiedDate", form.getDateTo()));
		
		criteria.add(Restrictions.eq("objectStatus", ObjectStatus.INACTIVE));
		
		criteria.setProjection(Projections.projectionList().
        		add(Projections.countDistinct("id").as("plan_entityCount")).
        		add(Projections.groupProperty("createdBy").as("plan_coach")));
		
		List<EntityStudentCountByCoachTO> inactivePlansByCoaches = criteria.setResultTransformer(
						new NamespacedAliasToBeanResultTransformer(
								EntityStudentCountByCoachTO.class, "plan_")).list();
		
		Map<UUID,PlanAdvisorCountTO> results = new HashMap<UUID,PlanAdvisorCountTO>();
		for(EntityStudentCountByCoachTO inactivePlan:inactivePlansByCoaches){
			if(results.containsKey(inactivePlan.getCoach().getId())){
				PlanAdvisorCountTO result = results.get(inactivePlan.getCoach().getId());
				result.setInactivePlanCount(inactivePlan.getEntityCount());
			}else{
				PlanAdvisorCountTO result = new PlanAdvisorCountTO();
				result.setCoachName(inactivePlan.getCoach().getFirstName() + " " + inactivePlan.getCoach().getLastName());
				result.setInactivePlanCount(inactivePlan.getEntityCount());
				results.put(inactivePlan.getCoach().getId(), result);
			}
		}
		
		for(EntityStudentCountByCoachTO activePlan:activePlansByCoaches){
			if(results.containsKey(activePlan.getCoach().getId())){
				PlanAdvisorCountTO result = results.get(activePlan.getCoach().getId());
				result.setActivePlanCount(activePlan.getEntityCount());
			}else{
				PlanAdvisorCountTO result = new PlanAdvisorCountTO();
				result.setCoachName(activePlan.getCoach().getFirstName() + " " + activePlan.getCoach().getLastName());
				result.setActivePlanCount(activePlan.getEntityCount());
				results.put(activePlan.getCoach().getId(), result);
			}
		}
		List<PlanAdvisorCountTO> sortedResults = Lists.newArrayList(results.values());
		Collections.sort(sortedResults, PlanAdvisorCountTO.COACH_NAME_COMPARATOR);
		return sortedResults;
	}	
	
	
	@SuppressWarnings("unchecked")
	public List<PlanCourseCountTO> getPlanCourseCount(SearchPlanTO form){
		
		StringBuilder selectPlanCourses = new  StringBuilder("select count(distinct pc.id) as plan_studentCount, " +
				"pc.courseCode as plan_courseCode, " +
				"pc.formattedCourse as plan_formattedCourse, " +
				"pc.courseTitle as plan_courseTitle, " +
				"pc.termCode as plan_termCode " +
				"from Plan p, PlanCourse pc, Person person, ExternalCourse ec, ExternalPersonPlanStatus ps ");
		
		buildQueryWhereClause(selectPlanCourses, form);
		if(form.getPlanStatus() != null)
			selectPlanCourses.append(" and ps.schoolId = person.schoolId");
		
		selectPlanCourses.append(" group by pc.courseCode, pc.formattedCourse, pc.courseTitle, pc.termCode");
		
		Query query = createHqlQuery(selectPlanCourses.toString()).setInteger("objectStatus", ObjectStatus.ACTIVE.ordinal() );
		buildCourseSearchParamList(form,  query);
		List<PlanCourseCountTO> planCoursesCount = query.setResultTransformer(new NamespacedAliasToBeanResultTransformer(
								PlanCourseCountTO.class, "plan_")).list();
		
		return planCoursesCount;
	}
	
	private void buildQueryWhereClause(StringBuilder query, SearchPlanTO form){
		query.append(" where p.objectStatus = :objectStatus and pc.plan.id = p.id and p.person.id = person.id  and ec.code = pc.courseCode ");
		if(!StringUtils.isEmpty(form.getSubjectAbbreviation()))
		{			
			query.append(" and ec.subjectAbbreviation = :subjectAbbreviation ");
		}
		
		if(!StringUtils.isEmpty(form.getNumber()))
		{
						
			query.append(" and ec.number = :courseNumber ");
		}
		
		if(!form.getTermCodes().isEmpty())
		{
			query.append(" and pc.termCode in :termCodes ");
		}
		
		if(!StringUtils.isEmpty(form.getFormattedCourse()))
		{
			query.append(" and ec.formattedCourse = :formattedCourse ");
		}
		
		if(form.getPlanStatus() != null)
		{
			query.append(" and ps.status = :planStatus ");
		}
		
		
	}
	
	private void buildCourseSearchParamList(SearchPlanTO form, Query hqlQuery) {
		
		if(!StringUtils.isEmpty(form.getSubjectAbbreviation()))
		{
			hqlQuery.setString("subjectAbbreviation", form.getSubjectAbbreviation());
		}
		
		if(!StringUtils.isEmpty(form.getNumber()))
		{
			hqlQuery.setString("courseNumber", form.getNumber());
		}
		if(!StringUtils.isEmpty(form.getFormattedCourse()))
		{
			hqlQuery.setString("formattedCourse", form.getFormattedCourse());
		}
		if(!form.getTermCodes().isEmpty())
		{
			hqlQuery.setParameterList("termCodes", form.getTermCodes());
		}
		
		if(form.getPlanStatus() != null)
		{
			hqlQuery.setString("planStatus", form.getPlanStatus().toString());
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<PlanStudentStatusTO> getPlanStudentStatusByCourse(SearchPlanTO form){
		
		StringBuilder selectPlanCourses = new  StringBuilder("select " +
				"distinct person.schoolId as plan_studentId, " +
				"pc.formattedCourse as plan_formattedCourse, " +
				"pc.courseTitle as plan_courseTitle, " +
				"p.objectStatus as plan_planObjectStatus, " +
				"ps.statusReason as plan_statusDetails, " +
				"ps.status as plan_planStatus " +
				"from Plan p, Person person, PlanCourse pc, ExternalCourse ec, ExternalPersonPlanStatus ps");
		
		buildQueryWhereClause(selectPlanCourses, form);
		selectPlanCourses.append(" and ps.schoolId = person.schoolId");
		
		
		Query query = createHqlQuery(selectPlanCourses.toString()).setInteger("objectStatus", ObjectStatus.ACTIVE.ordinal() );
		buildCourseSearchParamList(form,  query);
		List<PlanStudentStatusTO> planStudentStatus = query.setResultTransformer(new NamespacedAliasToBeanResultTransformer(
				PlanStudentStatusTO.class, "plan_")).list();
		
		return planStudentStatus;
	}
}
