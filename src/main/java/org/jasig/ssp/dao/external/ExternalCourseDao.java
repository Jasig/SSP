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
package org.jasig.ssp.dao.external;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalCourse;
import org.jasig.ssp.transferobject.external.SearchExternalCourseTO;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.orm.hibernate4.SessionHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Data access class for the External Person entity
 */
@Repository
public class ExternalCourseDao extends AbstractExternalReferenceDataDao<ExternalCourse> implements InitializingBean {

	public ExternalCourseDao() {
		super(ExternalCourse.class);
	}

	private static List<ExternalCourse> courseCache = new Vector<ExternalCourse>();
	
	private static Calendar lastCacheFlush = new GregorianCalendar();

	@SuppressWarnings("unchecked")
	public List<ExternalCourse> getAll() {
		Criteria criteria = createCriteria();
		criteria.addOrder(Order.asc("formattedCourse"));
		List<ExternalCourse> result = criteria.list();
		return result;
	}

	@Override
	@Deprecated
	public PagingWrapper<ExternalCourse> getAll(SortingAndPaging sAndP) {
		throw new NotImplementedException("Use getAll()");
	}

	@SuppressWarnings("unchecked")
	public List<String> getTagsForCourse(String code) {
		String getTagsForCourseBaseQuery = "select ect.tag from ExternalCourseTag ect, Tag tag " +
										   "where ect.courseCode = :courseCode and ect.tag = tag.code and tag.objectStatus = :objectStatus";
		return createHqlQuery(getTagsForCourseBaseQuery)
			   .setString("courseCode", code)
			   .setInteger("objectStatus", ObjectStatus.ACTIVE.ordinal())
			   .list();
	}
	public Boolean validateCourseForTerm(String code, String termCode) {
		String baseValidateCourseHqlQuery = "from ExternalCourseTerm ect where ect.courseCode = :courseCode and ect.termCode = :termCode";
		return createHqlQuery(baseValidateCourseHqlQuery)
		.setString("courseCode", code)
		.setString("termCode", termCode)
		.list().size() > 0 ;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getValidCourseCodesForTerm(String termCode, List<String> codes){
		String baseValidateCourseHqlQuery = "select ect.courseCode from ExternalCourseTerm ect where ect.courseCode in :courseCodes and ect.termCode = :termCode";
		Query query = createHqlQuery(baseValidateCourseHqlQuery)
		.setParameterList("courseCodes", codes)
		.setString("termCode", termCode);
		return (List<String>)query.list();
	}


	@SuppressWarnings("unchecked")
	public List<ExternalCourse> search(SearchExternalCourseTO form) {
		//Performance kludge, we are going to cache the unbounded search result
			if(form.isUnbounded())
			{
				if(ExternalCourseDao.courseCache.isEmpty() || isCacheExpired())
				{
					flushAndLoadCache();
				}
				return ExternalCourseDao.courseCache;
			}
		//End kludge
		
		Query hqlQuery = createHqlQuery(buildCourseSearchQuery(form));
		
		buildCourseSearchParamList(form, hqlQuery);	
		
		List<ExternalCourse> result = hqlQuery
		.list();
		for (ExternalCourse externalCourse : result) {
			List<String> tags = getTagsForCourse(externalCourse.getCode());
			StringBuilder tagBuilder = new StringBuilder();
			for (String tagg : tags) {
				tagBuilder.append(tagg+",");
			}
			externalCourse.setPivotedTags(tagBuilder.toString());
		}
		return result;
	}

	private boolean isCacheExpired() {
		return ((Calendar.getInstance().getTimeInMillis()-lastCacheFlush.getTimeInMillis())>getCacheLifeSpanInMillis());
	}

	public void flushAndLoadCache() {
		ExternalCourseDao.courseCache.clear();
		List<ExternalCourse> all = getAll();
		for (ExternalCourse externalCourse : all) {
			List<String> tags = getTagsForCourse(externalCourse.getCode());
			StringBuilder tagBuilder = new StringBuilder();
			for (String tagg : tags) {
				tagBuilder.append(tagg+",");
			}
			externalCourse.setPivotedTags(tagBuilder.toString());
		}
		ExternalCourseDao.courseCache.addAll(all);
		lastCacheFlush = Calendar.getInstance();
	}


	private void buildCourseSearchParamList(SearchExternalCourseTO form, Query hqlQuery) {
		
		if(!StringUtils.isEmpty(form.getSubjectAbbreviation()))
		{
			hqlQuery.setString("subjectAbbreviation", form.getSubjectAbbreviation());
		}
		
		if(!StringUtils.isEmpty(form.getCourseNumber()))
		{
			hqlQuery.setString("number", form.getCourseNumber());
		}
		if(!StringUtils.isEmpty(form.getProgramCode()))
		{
			hqlQuery.setString("programCode", form.getProgramCode());
		}
		if(!StringUtils.isEmpty(form.getProgramCode()))
		{
			hqlQuery.setString("programCode", form.getProgramCode());
		}
		if(!StringUtils.isEmpty(form.getTermCode()))
		{
			hqlQuery.setString("termCode", form.getTermCode());
		}
		if(!StringUtils.isEmpty(form.getTag()))
		{
			hqlQuery.setInteger("objectStatus", ObjectStatus.ACTIVE.ordinal());
			hqlQuery.setString("tag", form.getTag());
		}
	}


	private String buildCourseSearchQuery(SearchExternalCourseTO form) {
		boolean firstWhereCondition = true;
		StringBuilder query = new StringBuilder();
		query.append(" select distinct ec from ExternalCourse ec ");
		
		if(!StringUtils.isEmpty(form.getSubjectAbbreviation()))
		{			
			query.append("where ec.subjectAbbreviation = :subjectAbbreviation ");
			firstWhereCondition = false;
		}
		
		if(!StringUtils.isEmpty(form.getCourseNumber()))
		{
			if(!firstWhereCondition)
			{
				query.append(" and ");
			}
			else
			{
				query.append(" where ");
			}			
			query.append("where ec.number = :courseNumber ");
			firstWhereCondition = false;
		}
		
		if(!StringUtils.isEmpty(form.getProgramCode()))
		{
			query.append(" ,ExternalCourseProgram ecp ");
		}
		if(!StringUtils.isEmpty(form.getTag()))
		{
			query.append(" ,ExternalCourseTag ectg ");
			query.append(" ,Tag tag ");
		}
		if(!StringUtils.isEmpty(form.getTermCode()))
		{
			query.append(" ,ExternalCourseTerm ectr ");
		}
		if(!StringUtils.isEmpty(form.getProgramCode()))
		{
			query.append(" where ");
			query.append(" ec.code = ecp.courseCode ");
			query.append(" and ecp.programCode = :programCode  ");
			firstWhereCondition = false;
		}	
		if(!StringUtils.isEmpty(form.getTag()))
		{
			if(!firstWhereCondition)
			{
				query.append(" and ");
			}
			else
			{
				query.append(" where ");
			}
			query.append(" ec.code = ectg.courseCode ");
			query.append(" and ectg.tag = :tag ");
			query.append(" and tag.code = :tag ");
			query.append(" and tag.objectStatus = :objectStatus ");
			firstWhereCondition = false;
		}	
		if(!StringUtils.isEmpty(form.getTermCode()))
		{
			if(!firstWhereCondition)
			{
				query.append(" and ");
			}
			else
			{
				query.append(" where ");
			}			
			query.append(" ec.code = ectr.courseCode ");
			query.append(" and ectr.termCode = :termCode  ");
			query.append(" order by ec.formattedCourse desc ");
		}
		return query.toString();
	}

	@Override
    public void afterPropertiesSet() throws Exception {
        try {
            Session session = SessionFactoryUtils.openSession(sessionFactory);
            TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
    		flushAndLoadCache();
    		lastCacheFlush = Calendar.getInstance();            
        } finally {
            SessionHolder sessionHolder =
                    (SessionHolder) TransactionSynchronizationManager.unbindResource(sessionFactory);
            SessionFactoryUtils.closeSession(sessionHolder.getSession());
        }
    }
}
