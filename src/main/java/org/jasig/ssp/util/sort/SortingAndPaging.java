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
package org.jasig.ssp.util.sort;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.collections.Pair;
import java.lang.reflect.Field;
import java.util.*;


/**
 * Encapsulate common filtering options for the DAO layer including
 * {@link ObjectStatus}, paging, and sorting.
 * 
 * <p>
 * Never allows more than {@link #MAXIMUM_ALLOWABLE_RESULTS} results in one
 * query. Use multiple requests with different firstResult settings to see more
 * results beyond the {@link #MAXIMUM_ALLOWABLE_RESULTS} setting.
 */
public final class SortingAndPaging { // NOPMD

	/**
	 * The default maximum results if an upper limit was not placed when
	 * creating the instance.
	 */
	final public static Integer DEFAULT_MAXIMUM_RESULTS = 100;

	/**
	 * The maximum allowable results
	 */
	final public static Integer MAXIMUM_ALLOWABLE_RESULTS = 1000;

	final transient private ObjectStatus status;

	final private transient Integer firstResult;

	@JsonProperty
	final private transient Integer maxResults;

	@JsonProperty
	final private transient String defaultSortProperty;

	@JsonProperty
	final private transient SortDirection defaultSortDirection;

	@JsonProperty
	private transient List<Pair<String, SortDirection>> sortFields;

	/**
	 * Construct a basic instance with only an {@link ObjectStatus} filter but
	 * not paging or sorting filters.
	 * 
	 * <p>
	 * Will automatically use {@link #DEFAULT_MAXIMUM_RESULTS} for the maximum
	 * result setting.
	 * 
	 * @param status
	 *            Object status filter
	 */
	public SortingAndPaging(final ObjectStatus status) {
		this.status = status == null ? ObjectStatus.ACTIVE : status;
		firstResult = 0;
		maxResults = DEFAULT_MAXIMUM_RESULTS;
		defaultSortProperty = null;
		defaultSortDirection = null;
	}

	/**
	 * Construct a full instance of these settings with a multi-column field
	 * sort.
	 * 
	 * @param status
	 *            Object status
	 * @param firstResult
	 *            First result to return (0-based)
	 * @param maxResults
	 *            Maximum total results to return.
	 * 
	 *            <p>
	 *            Will use {@link #DEFAULT_MAXIMUM_RESULTS} if not specified
	 *            here. Can not exceed {@link #MAXIMUM_ALLOWABLE_RESULTS}
	 *            unless a value less than zero is specified.
	 * @param sortFields
	 *            Ordered list of sort fields and directions
	 * @param defaultSortProperty
	 *            A default sort property if the sort parameter is null.
	 * @param defaultSortDirection
	 *            A default sort direction if the sort parameter is null.
	 */
	//@JsonCreator
	public SortingAndPaging(final ObjectStatus status,
							final Integer firstResult,
							final Integer maxResults,
							final List<Pair<String, SortDirection>> sortFields,
							final String defaultSortProperty,
							final SortDirection defaultSortDirection) {
		this.status = status == null ? ObjectStatus.ACTIVE : status;
		this.firstResult = (firstResult == null)
				|| (firstResult < Integer.valueOf(0)) ? Integer.valueOf(0)
				: firstResult;
		if ( maxResults == null ||
				maxResults > MAXIMUM_ALLOWABLE_RESULTS) {
			this.maxResults = MAXIMUM_ALLOWABLE_RESULTS;
		} else if ( maxResults == 0 ) {
			this.maxResults = DEFAULT_MAXIMUM_RESULTS;
		} else if ( maxResults < 0 ) {
			this.maxResults = null; // inifinite. see isPaged()
		} else {
			this.maxResults = maxResults;
		}
		this.sortFields = sortFields;
		this.defaultSortProperty = defaultSortProperty;
		this.defaultSortDirection = defaultSortDirection;
	}
	
	/**
	 * 
	 * @param status The status
	 * @param sortFields The sortFields
	 * @param defaultSortProperty The defaultSortProperty
	 * @param defaultSortDirection The defaultSortDirection
	 * 
	 * Use this constructor if there is to be no paging.  firstResult and maxResults are set to null
	 */
	
	public SortingAndPaging(final ObjectStatus status,
			final List<Pair<String, SortDirection>> sortFields,
			final String defaultSortProperty,
			final SortDirection defaultSortDirection) {
		this.status = status == null ? ObjectStatus.ACTIVE : status;
		this.firstResult = null;
		this.maxResults = null;
		this.sortFields = sortFields;
		this.defaultSortProperty = defaultSortProperty;
		this.defaultSortDirection = defaultSortDirection;
	}

	/**
	 * Special {@code private} constructor for full-fidelity deserialization from JSON. Can't use existing
	 * constructors because they either don't have enough fields or coerce values such that we lose fidelity, e.g.
	 * a null {@code firstResult} is coerced to a default.
	 *
	 * <p>The extra 'foo' argument exists to avoid collisions with the other historical constructor which would
	 * otherwise have exactly the same argument list.</p>
	 *
	 * @param status
	 * @param firstResult
	 * @param maxResults
	 * @param sortFields
	 * @param defaultSortProperty
	 * @param defaultSortDirection
	 * @param foo
	 */
	@JsonCreator
	private SortingAndPaging(@JsonProperty("status") final ObjectStatus status,
							 @JsonProperty("firstResult") final Integer firstResult,
							 @JsonProperty("maxResults") final Integer maxResults,
							 @JsonProperty("sortFields") final List<Pair<String, SortDirection>> sortFields,
							 @JsonProperty("defaultSortProperty") final String defaultSortProperty,
							 @JsonProperty("defaultSortDirection") final SortDirection defaultSortDirection,
							 @JsonProperty("foo") final SortDirection foo) {
		this.status = status;
		this.firstResult = firstResult;
		this.maxResults = maxResults;
		this.sortFields = sortFields;
		this.defaultSortProperty = defaultSortProperty;
		this.defaultSortDirection = defaultSortDirection;
	}

	/**
	 * Gets the object status
	 * 
	 * @return the object status
	 */
	@JsonProperty
	public ObjectStatus getStatus() {
		return status;
	}

	/**
	 * Gets the first result filter setting. 0-based index
	 * 
	 * @return the first result filter setting
	 */
	@JsonProperty
	public Integer getFirstResult() {
		return firstResult;
	}

	/**
	 * Gets the maximum results filter setting
	 * 
	 * @return the maximum results filter setting
	 */
	@JsonProperty
	public Integer getMaxResults() {
		return maxResults;
	}

	/**
	 * Gets the default sort property
	 * 
	 * @return the default sort property
	 */
	@JsonProperty
	public String getDefaultSortProperty() {
		return defaultSortProperty;
	}

	/**
	 * Gets the default sort direction
	 * 
	 * @return the default sort direction
	 */
	@JsonProperty
	public SortDirection getDefaultSortDirection() {
		return defaultSortDirection;
	}

	/**
	 * Gets the ordered list of sort fields, in a Pair with the field (property)
	 * as the first argument and the sort direction as the second argument.
	 * 
	 * @return the ordered list of sort fields
	 */
	@JsonProperty
	public List<Pair<String, SortDirection>> getSortFields() {
		return sortFields;
	}

	/**
	 * True if the {@link ObjectStatus} filter is set to anything besides
	 * {@link ObjectStatus#ALL}.
	 * 
	 * @return true if filtering by object status
	 */
	@JsonIgnore
	public boolean isFilteredByStatus() {
		return (status != null && status != ObjectStatus.ALL);
	}

	/**
	 * True if these filters include paging, determined by the use of a maximum
	 * results and valid first result filters.
	 * 
	 * @return true if these filters include paging
	 */
	@JsonIgnore
	public boolean isPaged() {
		return ((null != maxResults)
				&& (maxResults > 0)
				&& (null != firstResult)
				&& (firstResult >= 0));
	}

	/**
	 * True if these filters include any sorting fields, by determining if there
	 * are any non-default, non-empty sort fields.
	 * 
	 * @return true if these filters include sorting fields
	 */
	@JsonIgnore
	public boolean isSorted() {
		if ((sortFields == null) || sortFields.isEmpty()) {
			return false;
		} else {
			final Pair<String, SortDirection> entry = sortFields.iterator()
					.next();
			return !StringUtils.isEmpty(entry.getFirst());
		}
	}

	/**
	 * True if a default sort property is available when no sort fields are
	 * explicitly defined.
	 * 
	 * @return true if a default sort property is available
	 */
	@JsonIgnore
	public boolean isDefaultSorted() {
		return (null != defaultSortProperty)
				&& !StringUtils.isEmpty(defaultSortProperty);
	}

	/**
	 * Add the current object status filter to the specified criteria.
	 * 
	 * @param criteria
	 *            Object status filter will be added to this criteria
	 */
	public void addStatusFilterToCriteria(final Criteria criteria) {
		if (isFilteredByStatus()) {
			criteria.add(Restrictions.eq("objectStatus", status));
		}
	}
	
	public StringBuilder addStatusFilterToQuery(final StringBuilder query, String objectName, Boolean isInitialRestriction) {
		if (isFilteredByStatus()) {
			if(isInitialRestriction == null || isInitialRestriction.equals(true)){
				query.append(" where ");
			}else{
				query.append(" and ");
			}
			query.append(objectName);
			query.append(".objectStatus = :objectStatus");
		}
		return query;
	}

	/**
	 * Add the current paging filter to the specified criteria.
	 * 
	 * @param criteria
	 *            Paging filter will be added to this criteria
	 */
	public void addPagingToCriteria(final Criteria criteria) {
		if (isPaged()) {
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(maxResults);
		}
	}
	
	/**
	 * Add the current paging filter to the specified criteria.
	 * 
	 * @param query
	 *            Paging filter will be added to this criteria
	 * @return The Query
	 */
	public Query addPagingToQuery(final Query query) {
		if (isPaged()) {
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResults);
		}
		return query;
	}
	
	public Pair<Long,Query> applySortingAndPagingToPagedQuery(Object session,
			  final String countColumn,
			  final String hqlSelectClause,
			  final StringBuilder hqlWithoutSelect,
			  final boolean filterByStatus,String objectToAddStatusFilter, Boolean isInitialRestriction, Map<String,Object> bindParams) {

		if (filterByStatus && StringUtils.isNotBlank(objectToAddStatusFilter)) {
			addStatusFilterToQuery(hqlWithoutSelect, objectToAddStatusFilter, isInitialRestriction);
			bindParams.put("objectStatus", getStatus());
		}
		
		// When using HQL, subqueries can only occur in the select and the where, not in the from.
		// So we have the client explicitly tell us where the select clause ends and the from+where
		// clause begins so we can unambiguously execute the latter twice, once with our count()
		// function and once for the "real" results. (Parsing to find where the from clause starts is
		// fraught. E.g. see https://issues.jasig.org/browse/SSP-2192 and related tickets.)
		final StringBuilder rowCntHql = new StringBuilder("select ");
		if ( StringUtils.isBlank(countColumn) ) {
			rowCntHql.append("count(*) ");
		} else {
			rowCntHql.append("count(distinct ").append(countColumn).append(") ");
		}
		rowCntHql.append(hqlWithoutSelect);
		
		Query fullQuery = null;
		Query rowCntQuery = null;
		final StringBuilder fullHql = new StringBuilder(hqlSelectClause).append(addSortingToQuery(hqlWithoutSelect));
		if(session instanceof Session)
		{
			Session thisSession = (Session)session;
			fullQuery = addPagingToQuery(thisSession.createQuery(fullHql.toString())).setProperties(bindParams);
			fullQuery = postProcessBindParams(fullQuery, bindParams);
			rowCntQuery = thisSession.createQuery(rowCntHql.toString());
		} else
		if(session instanceof StatelessSession)
		{
			StatelessSession thisStatelessSession = (StatelessSession)session;
			fullQuery = addPagingToQuery(thisStatelessSession.createQuery(fullHql.toString())).setProperties(bindParams);
			fullQuery = postProcessBindParams(fullQuery, bindParams);
			rowCntQuery = thisStatelessSession.createQuery(rowCntHql.toString());
		} else
		{
			throw new IllegalArgumentException("session paramter for org.jasig.ssp.util.sort.SortingAndPaging.applySortingAndPagingToPagedQuery(Object, StringBuilder, boolean, String, Boolean, Map<String, Object>) must "+
					"must be of type Session or StatelessSession");
		}

		rowCntQuery.setProperties(bindParams);
		rowCntQuery = postProcessBindParams(rowCntQuery, bindParams);
		final Long totalRows = (Long)rowCntQuery.list().get(0);

		// Sorting not added until here b/c if it's present in the count() query
		// above, the db will usually complain about that field not being
		// present in a group by/aggr function
		return new Pair<Long,Query>(totalRows,fullQuery);
	}

	/**
	 * Workaround for <a href="https://issues.jasig.org/browse/SSP-2981">SSP-2981</a> /
	 * <a href="https://hibernate.atlassian.net/browse/HHH-7705">HHH-7705</a>.
	 *
	 * @param query
	 * @param bindParams
	 * @return
	 */
	private Query postProcessBindParams(Query query, Map<String,Object> bindParams) {
		if ( bindParams == null || bindParams.isEmpty() ) {
			return query;
		}
		for ( Map.Entry<String,Object> entry : bindParams.entrySet() ) {
			if ( entry.getValue() == null ) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return query;
	}

	public Long applySortingAndPagingToPagedQuery(final Criteria query,
			final boolean filterByStatus) {

		if (filterByStatus) {
			addStatusFilterToCriteria(query);
		}

		Long totalRows = null;
		// Only query for total count if query is paged or filtered
		if (isPaged()
				|| (filterByStatus && isFilteredByStatus())) {
			totalRows = (Long) query.setProjection(
					Projections.rowCount()).uniqueResult();

			// clear the count projection from the query
			query.setProjection(null);
		}

		// Add Sorting and Paging
		addPagingToCriteria(query);
		addSortingToCriteria(query);

		return totalRows;
	}

	/**
	 * Add the current sorting filter to the specified criteria.
	 * 
	 * @param criteria
	 *            Paging filter will be added to this criteria
	 */
	public void addSortingToCriteria(final Criteria criteria) {
		if (isSorted()) {
			// sort by each entry in the map
			for (final Pair<String, SortDirection> entry : sortFields) {
				addSortToCriteria(criteria, entry.getFirst(), entry.getSecond());
			}
		} else if (isDefaultSorted()) {
			// sort by the default property
			addSortToCriteria(criteria, defaultSortProperty,
					defaultSortDirection);
		}
	}
	
	/**
	 * Add the current sorting filter to the specified criteria.
	 * 
	 * @param query
	 *            Paging filter will be added to this criteria
	 * @return The query StringBuilder
	 */
	public StringBuilder addSortingToQuery(final StringBuilder query) {
		if (isSorted()) {
			query.append(" Order By ");
			String seperator = "";
			for (final Pair<String, SortDirection> entry : sortFields) {
				query.append(seperator);
				addSortToQuery(query, entry.getFirst(), entry.getSecond());
				seperator = ", ";
			}
		} else if (isDefaultSorted()) {
			query.append(" Order By ");
			addSortToQuery(query, defaultSortProperty,
					defaultSortDirection);
		}
		return query;
	}
	
	public List<Object> sortAndPageList(List<Object> list) throws NoSuchFieldException, SecurityException, ClassNotFoundException{
		final List<Object> sortedList = sortList(list);
		return pageList(sortedList);
	}
	
	private List<Object> pageList(final List<Object> list){
		if(isPaged()){
			List<Object> uniques;
			Integer start = getFirstResult();
	    	Integer max =getMaxResults();
	    	if(start >= list.size())
	    	{
	    		uniques = new ArrayList<Object>();
	    	}else{
	    		max = start + max > list.size() || max < 0 ? list.size():start + max;
	    		uniques = list.subList(start, max);
	    	}
	    	return uniques;
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	private List<Object> sortList(List<Object> results) throws NoSuchFieldException, SecurityException, ClassNotFoundException{
		if (results == null || results.isEmpty()) {
            return results;
        }
		
		if (isSorted()) {
			Collections.sort(results, new GenericComparator<Object>(results.get(0).getClass(), getSortFields()));
		} else if (isDefaultSorted()) {
			Collections.sort(results, new GenericComparator<Object>(results.get(0).getClass(), Arrays.asList(new Pair<String, SortDirection>(getDefaultSortProperty(), SortDirection.ASC))));
		}
		return results;
	}

	/**
	 * Add all the current filters to the specified criteria
	 * 
	 * @param criteria
	 *            All the current filters will be added to this criteria
	 */
	public void addAll(final Criteria criteria) {
		addPagingToCriteria(criteria);
		addSortingToCriteria(criteria);
		addStatusFilterToCriteria(criteria);
	}

	private void addSortToCriteria(final Criteria criteria,
			final String sort, final SortDirection sortDirection) {
		if (sortDirection.equals(SortDirection.ASC)) {
			criteria.addOrder(Order.asc(sort));
		} else {
			criteria.addOrder(Order.desc(sort));
		}
	}
	
	private void addSortToQuery(final StringBuilder query,
			final String sort, final SortDirection sortDirection) {
		if (sortDirection.equals(SortDirection.ASC)) {
			query.append(sort);
			query.append(" asc ");
		} else {
			query.append(sort);
			query.append(" desc ");
		}
	}
	

	
	public static class GenericComparator<T> implements Comparator<T> {
		
		private List<Pair<Field,SortDirection>> sorters = new ArrayList<Pair<Field,SortDirection>>();
		
		 public GenericComparator(Class listObjClass, List<Pair<String, SortDirection>> sorters) throws NoSuchFieldException, SecurityException, ClassNotFoundException{
			 setSorters(Class.forName(listObjClass.getName()), sorters);
		 }
		 
		 private void setSorters(Class cls, List<Pair<String, SortDirection>> sorters)
		 {
			 for(Pair<String, SortDirection> sorter:sorters){
				 Field field = getField( cls,  sorter.getFirst());
				 if(field != null)
					 this.sorters.add(new Pair<Field, SortDirection>(field, sorter.getSecond()));
			 }
		 }
		 
		private Field getField(Class cls, String propertyName){
			 Field field = null;
			 while(field == null && cls != null){
				 try{
					 field = cls.getDeclaredField(propertyName);
				 }catch(Exception exp){
					 
				 }
				 if(field == null){
					 cls = cls.getSuperclass();
				 }else{
					 field.setAccessible(true);
					 return field;
				 }
			 }
			 return null;
		 }

		@Override
		public int compare(T o1, T o2) {
			int compared = 0;
			for(Pair<Field, SortDirection> sorter:sorters){
				Comparable<Object> prop1 = getComparableProperty(o1, sorter.getFirst());
				Comparable<Object> prop2 = getComparableProperty(o2,  sorter.getFirst());
				
				if(prop1 == null && prop2 != null){
					if(sorter.getSecond().equals(SortDirection.ASC))
						return -1;
					else
						return 1;
				}
				
				if(prop2 == null && prop1 != null){
					if(sorter.getSecond().equals(SortDirection.ASC))
						return 1;
					else
						return -1;
				}
				
				if(prop2 == null && prop1 == null){
					return 0;
				}
			
			
				if(sorter.getSecond().equals(SortDirection.ASC)){
					compared = prop1.compareTo(prop2);
				} else {
					compared = prop2.compareTo(prop1);
				}
				if(compared != 0)
					return compared;
			}
			return compared;
		}
		
		private Comparable<Object> getComparableProperty(T obj, Field field){
			try {
				 return (Comparable)field.get(obj);
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		
	}

	/**
	 * Append a sort field to the end of the current sort order list.
	 * 
	 * @param fieldname
	 *            Field (property) name
	 * @param direction
	 *            Sort direction
	 */
	public void appendSortField(final String fieldname,
			final SortDirection direction) {
		if (sortFields == null) {
			sortFields = new ArrayList<Pair<String, SortDirection>>();
		}

		sortFields.add(new Pair<String, SortDirection>(fieldname, direction));
	}

	/**
	 * Prepend (insert) a sort field to the beginning of the current sort order
	 * list.
	 * 
	 * @param fieldname
	 *            Field (property) name
	 * @param direction
	 *            Sort direction
	 */
	public void prependSortField(final String fieldname,
			final SortDirection direction) {
		if (sortFields == null) {
			sortFields = new ArrayList<Pair<String, SortDirection>>();
			sortFields
					.add(new Pair<String, SortDirection>(fieldname, direction));
		} else {
			final List<Pair<String, SortDirection>> newOrdering = Lists
					.newArrayList();
			newOrdering.add(new Pair<String, SortDirection>(fieldname,
					direction));
			newOrdering.addAll(sortFields);
			sortFields = newOrdering;
		}
	}

	/**
	 * Construct a full instance of these settings with a single field sort.
	 * 
	 * @param status
	 *            Object status
	 * @param firstResult
	 *            First result to return (0-based)
	 * @param maxResults
	 *            Maximum total results to return.
	 * 
	 *            <p>
	 *            Will use {@link #DEFAULT_MAXIMUM_RESULTS} if not specified
	 *            here. Can not exceed {@link #MAXIMUM_ALLOWABLE_RESULTS}.
	 * @param sort
	 *            Sort field (property)
	 * @param sortDirection
	 *            Sort direction
	 * @param defaultSortProperty
	 *            A default sort property if the sort parameter is null.
	 * @return An instance with the specified filters.
	 */
	public static SortingAndPaging createForSingleSortWithPaging(
			final ObjectStatus status,
			final Integer firstResult, final Integer maxResults,
			final String sort, final String sortDirection,
			final String defaultSortProperty) {

		List<Pair<String, SortDirection>> sortFields;
		SortDirection defaultSortDirection;

		// use sort parameter if available, otherwise use the default
		if (sort == null) {
			sortFields = null; // NOPMD
			defaultSortDirection = SortDirection
					.getSortDirection(sortDirection);
		} else {
			sortFields = Lists.newArrayList();
			sortFields.add(new Pair<String, SortDirection>(sort, SortDirection
					.getSortDirection(sortDirection)));
			defaultSortDirection = null; // NOPMD
		}

		final SortingAndPaging sAndP = new SortingAndPaging(
				status == null ? ObjectStatus.ACTIVE : status,
				firstResult, maxResults, sortFields, defaultSortProperty,
				defaultSortDirection);

		return sAndP;
	}
	
	/**
	 * Construct a full instance of these settings with a single field sort.
	 * 
	 * @param status
	 *            Object status
	 * @param sortField
     *            String sortField
     * @param sortDirection
     *            String sortDirection
	 * @return An instance with the specified filters.
	 */
	public static SortingAndPaging createForSingleSortAll(
			final ObjectStatus status,
			final String sortField, final String sortDirection) {

		List<Pair<String, SortDirection>> sortFields;
		SortDirection defaultSortDirection;

		// use sort parameter if available, otherwise use the default
		if (sortField == null) {
			sortFields = null; // NOPMD
			defaultSortDirection = SortDirection
					.getSortDirection(sortDirection);
		} else {
			sortFields = Lists.newArrayList();
			sortFields.add(new Pair<String, SortDirection>(sortField, SortDirection
					.getSortDirection(sortDirection)));
			defaultSortDirection = null; // NOPMD
		}

		final SortingAndPaging sAndP = new SortingAndPaging(
				status == null ? ObjectStatus.ACTIVE : status,
				sortFields, null,
				defaultSortDirection);
		
		return sAndP;
	}

	public static SortingAndPaging allActive() {
		return SortingAndPaging.allActiveSorted(null);
	}

	public static SortingAndPaging allActiveSorted(String sortOn) {
		return SortingAndPaging.createForSingleSortWithPaging(ObjectStatus.ACTIVE, 0, -1, sortOn, null, null);
	}

}
