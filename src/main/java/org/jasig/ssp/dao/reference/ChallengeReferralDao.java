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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.dao.AuditableCrudDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.Person;
import org.jasig.ssp.model.reference.ChallengeReferral;
import org.jasig.ssp.model.reference.ChallengeReferralSearchResult;
import org.jasig.ssp.transferobject.reference.ChallengeReferralSearchFormTO;
import org.jasig.ssp.transferobject.reports.EntityStudentCountByCoachTO;
import org.jasig.ssp.security.SspUser;
import org.jasig.ssp.util.collections.Pair;
import org.jasig.ssp.util.hibernate.NamespacedAliasToBeanResultTransformer;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortDirection;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.springframework.stereotype.Repository;

/**
 * Data access class for the ChallengeReferral reference entity.
 */
@Repository
public class ChallengeReferralDao extends
		AbstractReferenceAuditableCrudDao<ChallengeReferral> implements
		AuditableCrudDao<ChallengeReferral> {

	public ChallengeReferralDao() {
		super(ChallengeReferral.class);
	}

	@SuppressWarnings(UNCHECKED)
	// :TODO paging?
	public List<ChallengeReferral> byChallengeId(final UUID challengeId) {
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select cr "
						+ "from ChallengeReferral cr " // NOPMD
								+ "inner join cr.challengeChallengeReferrals ccr " // NOPMD
								+ "where cr.showInSelfHelpGuide = true " // NOPMD
								+ "and ccr.challenge.id = ? "
								+ "and cr.objectStatus = ? "
								+ "order by cr.name")
				.setParameter(0, challengeId)
				.setParameter(1, ObjectStatus.ACTIVE).setMaxResults(100).list();
	}

	@SuppressWarnings(UNCHECKED)
	// :TODO paging?
	public List<ChallengeReferral> byChallengeIdAndQuery(
			final UUID challengeId,
			final String query) {
		final String wildcardQuery = "%" + query.toUpperCase() + "%"; // NOPMD

		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select cr "
								+ "from ChallengeReferral cr "
								+ "inner join cr.challengeChallengeReferrals ccr "
								+ "inner join ccr.challenge c "
								+ "where cr.showInSelfHelpGuide = true "
								+ "and c.showInSelfHelpSearch = true "
								+ "and ccr.challenge.id = ? "
								+ "and cr.objectStatus = ? "
								+ "and (upper(cr.name) like ? "
								+ "or upper(cr.publicDescription) like ?) "
								+ "order by cr.name")
				.setParameter(0, challengeId)
				.setParameter(1, ObjectStatus.ACTIVE)
				.setParameter(2, wildcardQuery)
				.setParameter(3, wildcardQuery).setMaxResults(100).list();
	}

	public long countByChallengeIdNotOnActiveTaskList(final UUID challengeId,
			final Person student, final String sessionId, boolean selfHelpGuide) {

		String selfHelpGuideString = selfHelpGuide ? "and cr.showInSelfHelpGuide = true " : "";
		return (Long) sessionFactory
				.getCurrentSession()
				.createQuery(
						"select count(cr) "
								+ "from ChallengeReferral cr "
								+ "inner join cr.challengeChallengeReferrals ccr "
								+ "where "
								+ "ccr.challenge.id = :challengeId "
								+ selfHelpGuideString
								+ "and cr.objectStatus = :objectStatus "
								+ "and not exists (from Task "
								+ "where challengeReferral.id = cr.id "
								+ "and objectStatus = :objectStatus "
								+ "and person.id = :studentId "
								+ "and sessionId = case "
								+ "when person.id != :anonPersonId "
								+ "then sessionId else :webSessionId end)")
				.setParameter("challengeId", challengeId)
				.setParameter("studentId", student.getId())
				.setParameter("anonPersonId", SspUser.ANONYMOUS_PERSON_ID)
				.setParameter("webSessionId", sessionId)
				.setParameter("objectStatus", ObjectStatus.ACTIVE)
				.uniqueResult();
	}

	@SuppressWarnings(UNCHECKED)
	// :TODO paging?
	public List<ChallengeReferral> byChallengeIdNotOnActiveTaskList(
			final UUID challengeId, final Person student, final String sessionId, boolean selfHelpGuide) {

		String selfHelpGuideString = selfHelpGuide ? "and cr.showInSelfHelpGuide = true " : "";
		return sessionFactory
				.getCurrentSession()
				.createQuery(
						"select cr "
								+ "from ChallengeReferral cr "
								+ "inner join cr.challengeChallengeReferrals ccr "
								+ "where "
								+ "ccr.challenge.id = :challengeId "
								+ selfHelpGuideString
								+ "and cr.objectStatus = :objectStatus "
								+ "and not exists " + "(from Task "
								+ "where challengeReferral.id = cr.id "
								+ "and objectStatus = :objectStatus "
								+ "and person.id = :studentId "
								+ "and sessionId = " + "case "
								+ "when person.id != :anonPersonId "
								+ "then sessionId " + "else "
								+ ":webSessionId " + "end)")
				.setParameter("challengeId", challengeId)
				.setParameter("studentId", student.getId())
				.setParameter("anonPersonId", SspUser.ANONYMOUS_PERSON_ID)
				.setParameter("webSessionId", sessionId)
				.setParameter("objectStatus", ObjectStatus.ACTIVE).list();
	}

	public PagingWrapper<ChallengeReferral> getAllForChallenge(
			final UUID challengeId, final SortingAndPaging sAndP) {
		final Criteria query = createCriteria();
		final Criteria subQuery = query
				.createCriteria("challengeChallengeReferrals");
		subQuery.add(Restrictions.eq("challenge.id", challengeId));
		sAndP.addStatusFilterToCriteria(subQuery);

		return processCriteriaWithStatusSortingAndPaging(query, sAndP);
	}
	
	@Override
	public PagingWrapper<ChallengeReferral> getAll(
			final SortingAndPaging sAndP) {
		SortingAndPaging sp = sAndP;
		if (sp == null) {
			sp = new SortingAndPaging(ObjectStatus.ACTIVE);
		}

		if (!sp.isSorted()) {
			sp.appendSortField("objectStatus", SortDirection.ASC);
			sp.appendSortField("name", SortDirection.ASC);
		}

		return super.getAll(sp);
	}
	
	@SuppressWarnings("unchecked")
	public PagingWrapper<ChallengeReferralSearchResult> summarySearch(ChallengeReferralSearchFormTO searchForm){
		Query categoryChallenge = createHqlQuery(categoryChallengeQuery(searchForm).toString()).setResultTransformer(new NamespacedAliasToBeanResultTransformer(
				ChallengeReferralSearchResult.class, "cr_sel_"));
		Query challenge =  createHqlQuery(challengeQuery(searchForm).toString()).setResultTransformer(new NamespacedAliasToBeanResultTransformer(
				ChallengeReferralSearchResult.class, "cr_sel_"));
		Query challengeReferral =  createHqlQuery(challengeReferralQuery(searchForm.hasSearchPhrase()).toString()).setResultTransformer(new NamespacedAliasToBeanResultTransformer(
				ChallengeReferralSearchResult.class, "cr_sel_"));
		if(searchForm.getCategoryId() != null)
			categoryChallenge.setParameter("categoryId", searchForm.getCategoryId());
		if(searchForm.getChallengeId() != null){
			challenge.setParameter("challengeId", searchForm.getChallengeId());
			categoryChallenge.setParameter("challengeId", searchForm.getChallengeId());
		}
		ObjectStatus objectStatus = searchForm.getSortAndPage() != null && 
				searchForm.getSortAndPage().getStatus() != null ? searchForm.getSortAndPage().getStatus() : ObjectStatus.ACTIVE;
		
		challenge.setParameter("objectStatus", objectStatus);
	    categoryChallenge.setParameter("objectStatus", objectStatus);
	    challengeReferral.setParameter("objectStatus", objectStatus);
	    if(searchForm.hasSearchPhrase()){
	    	String searchPhrase = "%"+ searchForm.getSearchPhrase().toUpperCase() + "%";
		    challenge.setString("searchPhrase", searchPhrase);
		    categoryChallenge.setString("searchPhrase", searchPhrase);
		    challengeReferral.setString("searchPhrase", searchPhrase);
	    }
	    List<ChallengeReferralSearchResult> uniques = new ArrayList<ChallengeReferralSearchResult>();
	    Map<UUID,ChallengeReferralSearchResult> ctr = new HashMap<UUID,ChallengeReferralSearchResult>();
	    Map<UUID, Map<UUID, ChallengeReferralSearchResult>> ctc = new HashMap<UUID, Map<UUID, ChallengeReferralSearchResult>>();
	    Iterator<ChallengeReferralSearchResult> iter = categoryChallenge.list().iterator();
	    while(iter.hasNext()){
	    	ChallengeReferralSearchResult crsr = iter.next();
	    	if(ctc.containsKey(crsr.getChallengeId())){
	    		Map<UUID, ChallengeReferralSearchResult> ccr = ctc.get(crsr.getChallengeId());
	    		ccr.put(crsr.getChallengeReferralId(), crsr);
	    	}
	    	else{
	    		Map<UUID, ChallengeReferralSearchResult> ccr =  new HashMap<UUID, ChallengeReferralSearchResult>();
	    		ccr.put(crsr.getChallengeReferralId(), crsr);
	    		ctc.put(crsr.getChallengeId(), ccr);
	    	}
	    	ctr.put(crsr.getChallengeReferralId(), crsr);
	    	uniques.add(crsr);
	    }
	    
	    // if search CategoryId is null then we need to pull challenges with empty Category associations
	    if(searchForm.getCategoryId() == null){
	    	Iterator<ChallengeReferralSearchResult> iterC = challenge.list().iterator();
		    while(iterC.hasNext()){
		    	ChallengeReferralSearchResult crsr = iterC.next();
		    	if(!ctc.containsKey(crsr.getChallengeId())){
		    		ctr.put(crsr.getChallengeReferralId(), crsr);
		    		uniques.add(crsr);
		    	}else{
		    		Map<UUID, ChallengeReferralSearchResult> ccr = ctc.get(crsr.getChallengeId());
		    		if(!ccr.containsKey(crsr.getChallengeReferralId())){
		    			ctr.put(crsr.getChallengeReferralId(), crsr);
			    		uniques.add(crsr);
		    		}
		    	}
		    }
	
		    // if search CategorId and challenge id is null then we need to pull free challenge referrals
		    if(searchForm.getChallengeId() == null){
			    Iterator<ChallengeReferralSearchResult> iterCCR = challengeReferral.list().iterator();
			    while(iterCCR.hasNext()){
			    	ChallengeReferralSearchResult crsr = iterCCR.next();
			    	if(!ctr.containsKey(crsr.getChallengeReferralId())){
			    		ctr.put(crsr.getChallengeReferralId(), crsr);
			    		uniques.add(crsr);
			    	}
			    }
		    }
	    }
	    
	    Collections.sort(uniques, new Comparator<ChallengeReferralSearchResult>() {
	        public int compare(ChallengeReferralSearchResult o1, ChallengeReferralSearchResult o2) {
	        	ChallengeReferralSearchResult p1 = (ChallengeReferralSearchResult) o1;
	        	ChallengeReferralSearchResult p2 = (ChallengeReferralSearchResult) o2;
	        	if(p1.getChallengeName() == null){
	        		if(p2.getChallengeName() == null)
	        			 return p1.getChallengeReferralName().compareToIgnoreCase(
	     	                    p2.getChallengeReferralName());
	        		else
	        			return 1;
	        	}
	        	if(p2.getChallengeName() == null){
	        			return -1;
	        	}
		       return p1.getChallengeName().compareToIgnoreCase(
	                    p2.getChallengeName());
	        }
	    });

	    
		return new PagingWrapper<ChallengeReferralSearchResult>(uniques.size(), uniques);
		
	}
	

	private void baseQuery(StringBuilder statement){
		statement.append("select distinct ct.name as cr_sel_categoryName, ");
		statement.append("ct.id as cr_sel_categoryId,  ");
		statement.append("c.name as cr_sel_challengeName, ");
		statement.append("c.id as cr_sel_challengeId, ");
		statement.append("cr.name as cr_sel_challengeReferralName, ");
		statement.append("cr.id as cr_sel_challengeReferralId, ");
		statement.append("cr.description as cr_sel_challengeReferralDescription ");
		statement.append("from ChallengeCategory as cc, ");
		statement.append("ChallengeChallengeReferral as ccr, ");
		statement.append("ChallengeReferral as cr, ");
		statement.append("Challenge as c, ");
		statement.append("Category as ct ");
	}
	private StringBuilder categoryChallengeQuery(ChallengeReferralSearchFormTO searchForm){
		StringBuilder statement = new StringBuilder();
		baseQuery(statement);
		statement.append("where cc.challenge.id = c.id and cc.category.id = ct.id ");
		statement.append("and ccr.challenge.id = c.id and ccr.challengeReferral.id = cr.id ");
		statement.append("and cr.objectStatus = :objectStatus ");
		statement.append("and cc.objectStatus = :objectStatus ");
		statement.append("and c.objectStatus = :objectStatus ");
		if(searchForm.getCategoryId() != null){
			statement.append("and ct.id = :categoryId ");
		}
		if(searchForm.getChallengeId() != null){
			statement.append("and c.id = :challengeId ");
		}
		if(searchForm.hasSearchPhrase()){
			statement.append("and (upper(c.name) like :searchPhrase ");
			statement.append("or upper(c.description) like :searchPhrase ");
			statement.append("or upper(ct.name) like :searchPhrase ");
			statement.append("or upper(ct.description) like :searchPhrase ");
			statement.append("or upper(cr.name) like :searchPhrase ");
			statement.append("or upper(cr.description) like :searchPhrase) ");
		}
		return statement;
	}
	
	private StringBuilder challengeQuery(ChallengeReferralSearchFormTO searchForm){
		StringBuilder statement = new StringBuilder();
		statement.append("select distinct c.name as cr_sel_challengeName, ");
		statement.append("c.id as cr_sel_challengeId, ");
		statement.append("cr.name as cr_sel_challengeReferralName, ");
		statement.append("cr.id as cr_sel_challengeReferralId, ");
		statement.append("cr.description as cr_sel_challengeReferralDescription ");
		statement.append("from ChallengeChallengeReferral as ccr, ");
		statement.append("ChallengeReferral as cr, ");
		statement.append("Challenge as c ");
		statement.append("where ccr.challenge.id = c.id and ccr.challengeReferral.id = cr.id ");
		statement.append("and cr.objectStatus = :objectStatus ");
		statement.append("and c.objectStatus = :objectStatus ");
		if(searchForm.getChallengeId() != null){
			statement.append("and c.id = :challengeId ");
		}
		if(searchForm.hasSearchPhrase()){
			statement.append("and (upper(c.name) like :searchPhrase ");
			statement.append("or upper(c.description) like :searchPhrase ");
			statement.append("or upper(cr.name) like :searchPhrase ");
			statement.append("or upper(cr.description) like :searchPhrase)");
		}
		return statement;
	}
	
	private StringBuilder challengeReferralQuery(Boolean hasSearchPhrase){
		StringBuilder statement = new StringBuilder();
		statement.append("select distinct cr.name as cr_sel_challengeReferralName, ");
		statement.append("cr.id as cr_sel_challengeReferralId, ");
		statement.append("cr.description as cr_sel_challengeReferralDescription ");
		statement.append("from ChallengeReferral as cr ");
		statement.append("where cr.objectStatus = :objectStatus ");
		if(hasSearchPhrase){
			statement.append("and (upper(cr.name) like :searchPhrase ");
			statement.append("or upper(cr.description) like :searchPhrase)");
		}
		return statement;
	}
	


}