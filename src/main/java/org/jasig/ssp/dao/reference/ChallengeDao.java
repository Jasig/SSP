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
package org.jasig.ssp.dao.reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.Query;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.external.ExternalPerson;
import org.jasig.ssp.model.reference.Challenge;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.hibernate.BatchProcessor;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the Challenge reference entity.
 */
@Repository
public class ChallengeDao extends AbstractReferenceAuditableCrudDao<Challenge>
		implements AuditableCrudDao<Challenge> {

	/**
	 * Constructor to initialize the class with the appropriate class type for
	 * use by the parent class methods.
	 */
	public ChallengeDao() {
		super(Challenge.class);
	}

	/**
	 * Retrieves all Challenges that have been marked "Affirmative" for the
	 * specified SelfHelpGuideResponseId.
	 * <p>
	 * Orders the list alphabetically by Challenge.Name.
	 * 
	 * @param selfHelpGuideResponseId
	 *            Specific SelfHelpGuideResponse identifier to use in the
	 *            criteria filter.
	 * @return All Challenges that have been marked "Affirmative" for the
	 *         specified SelfHelpGuideResponseId.
	 */
	@SuppressWarnings(UNCHECKED)
	public List<Challenge> selectAffirmativeBySelfHelpGuideResponseId(
			@NotNull final UUID selfHelpGuideResponseId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select distinct c "
								+ "from Challenge c "
								+ "inner join c.selfHelpGuideQuestions shgq "
								+ "inner join shgq.selfHelpGuideQuestionResponses shgqr "
								+ "where shgqr.response = true "
								+ "and shgqr.selfHelpGuideResponse.id = ? "
								+ "order by c.name")
				.setParameter(0, selfHelpGuideResponseId).list();
	}

	/**
	 * Retrieve all Challenges that match the specified text query via a simple
	 * LIKE clause on the SelfHelpGuide Question, Description, and Tags fields.
	 * <p>
	 * Also filters out inactive Challenges, and those that are not marked to
	 * show in the SelfHelpSearch.
	 * 
	 * @param query
	 *            Text string to compare with a SQL LIKE clause on the
	 *            SelfHelpGuide Question, Description, and Tags fields
	 * @param selfHelpGuide 
	 * @return All Challenges that match the specified criteria.
	 */
	@SuppressWarnings(UNCHECKED)
	public List<Challenge> searchByQuery(final String query, boolean selfHelpGuide) {
		String selfHelpGuideString = selfHelpGuide ? "and showInSelfHelpGuide = true" : "";
		final String beginningHql = "select distinct c from Challenge c inner join c.challengeChallengeReferrals ccr where c.objectStatus = :objectStatus and c.showInSelfHelpSearch = true "; // NOPMD
		final String endHql = " and exists (from ChallengeReferral where id = ccr.challengeReferral.id " + selfHelpGuideString + " and objectStatus = :objectStatus) order by c.name"; // NOPMD

		if (!StringUtils.isNotBlank(query)) {
			// no query specified so don't bother filtering by a wildcard string
			return sessionFactory
					.getCurrentSession()
					.createQuery(beginningHql + endHql)
					.setParameter("objectStatus", ObjectStatus.ACTIVE).list();
		}

		return sessionFactory
				.getCurrentSession()
				.createQuery(beginningHql
						+ "and (upper(c.name) like :query "
						+ "or upper(c.selfHelpGuideQuestion) like :query "
						+ "or upper(c.selfHelpGuideDescription) like :query "
						+ "or upper(c.tags) like :query) "
						+ endHql)
				.setParameter("query",
						"%" + query.toUpperCase(Locale.getDefault()) + "%")
				.setParameter("objectStatus", ObjectStatus.ACTIVE).list();
	}

	@SuppressWarnings(UNCHECKED)
	public List<Challenge> searchByQueryNoSelfHelpGuide(final String query) {
		final String beginningHql = "select distinct c from Challenge c inner join c.challengeChallengeReferrals ccr where c.objectStatus = :objectStatus and c.showInSelfHelpSearch = true "; // NOPMD
		final String endHql = " and exists (from ChallengeReferral where id = ccr.challengeReferral.id  and objectStatus = :objectStatus) order by c.name"; // NOPMD

		if (!StringUtils.isNotBlank(query)) {
			// no query specified so don't bother filtering by a wildcard string
			return sessionFactory
					.getCurrentSession()
					.createQuery(beginningHql + endHql)
					.setParameter("objectStatus", ObjectStatus.ACTIVE).list();
		}

		return sessionFactory
				.getCurrentSession()
				.createQuery(beginningHql
						+ "and (upper(c.name) like :query "
						+ "or upper(c.selfHelpGuideQuestion) like :query "
						+ "or upper(c.selfHelpGuideDescription) like :query "
						+ "or upper(c.tags) like :query) "
						+ endHql)
				.setParameter("query",
						"%" + query.toUpperCase(Locale.getDefault()) + "%")
				.setParameter("objectStatus", ObjectStatus.ACTIVE).list();
	}
	/**
	 * Retrieves all Challenges that are marked to be able to be shown in the
	 * StudentIntake interface.
	 * 
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return List of all Challenges that are marked to be able to be shown in
	 *         the StudentIntake interface.
	 */
	public PagingWrapper<Challenge> getAllInStudentIntake(
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		query.add(Restrictions.eq("showInStudentIntake", true));
		sAndP.appendSortField("name", SortDirection.ASC);
		return processCriteriaWithStatusSortingAndPaging(query, sAndP);
	}

	/**
	 * Get all Challenges for the specified Category.
	 * 
	 * <p>
	 * Filters out associations based on specified sAndP, but not any
	 * associations that may point to deleted Categories or Challenges.
	 * 
	 * @param categoryId
	 *            Category identifier
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return All specified associations. (Referenced Categories or Challenges
	 *         may be {@link ObjectStatus#INACTIVE} though.)
	 */
	public PagingWrapper<Challenge> getAllForCategory(
			@NotNull final UUID categoryId,
			@NotNull final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		if (!sAndP.isSorted()) {
			sAndP.appendSortField("objectStatus", SortDirection.ASC);
			sAndP.appendSortField("name", SortDirection.ASC);
		}
		
		final Criteria subQuery = query.createCriteria("challengeCategories");
		subQuery.add(Restrictions.eq("category.id", categoryId));
		sAndP.addStatusFilterToCriteria(subQuery);

		return processCriteriaWithStatusSortingAndPaging(query, sAndP);
	}

	/**
	 * Get all Challenges for the specified Person.
	 * 
	 * <p>
	 * Filters out associations based on specified sAndP, but not any
	 * associations that may point to deleted Categories or People.
	 * 
	 * @param personId
	 *            Person identifier
	 * @param sAndP
	 *            Sorting and paging parameters
	 * @return All Challenges for the specified Person
	 */
	public PagingWrapper<Challenge> getAllForPerson(final UUID personId,
			final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		/*final Criteria subQuery = query.createCriteria("peopleWithChallenge");
		subQuery.add(Restrictions.eq("person.id", personId));
		sAndP.addStatusFilterToCriteria(subQuery);*/
		return processCriteriaWithStatusSortingAndPaging(query, sAndP);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public PagingWrapper<Challenge> getAll(
			final SortingAndPaging sAndP) {
		SortingAndPaging sp = sAndP;
		if (sp == null) {
			sp = new SortingAndPaging(ObjectStatus.ACTIVE);
		}
		if (!sp.isSorted()) {
			sp.appendSortField("objectStatus", SortDirection.ASC);
			sp.appendSortField("name", SortDirection.ASC);
		}
				
		StringBuilder statement = new StringBuilder("select id from Challenge");
		if(sp.getStatus()  != null && !sp.getStatus().equals(ObjectStatus.ALL)){
			statement.append(" where objectStatus=:objectStatus");
		}
		
		Query query = createHqlQuery(statement.toString());
		if(sp.getStatus()  != null && !sp.getStatus().equals(ObjectStatus.ALL)){
			query.setParameter("objectStatus", sp.getStatus());
		}
		List<UUID> uuids = query.list();
		int size = uuids.size();
		
		if(size > 0){
			BatchProcessor<UUID, Challenge> processor =  new BatchProcessor<UUID,Challenge>(uuids, sp);
			do{
				Criteria criteria = createCriteria();
				criteria.setFetchMode( "challengeChallengeReferrals", FetchMode.JOIN );
				criteria.setFetchMode( "selfHelpGuideQuestions", FetchMode.JOIN );
				criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
				processor.process(criteria, "id");
			}while(processor.moreToProcess());
			
			return processor.getSortedAndPagedResults();
		}
		return new PagingWrapper<Challenge>(0,new ArrayList<Challenge>());
	}
}