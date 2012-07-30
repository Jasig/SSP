package org.jasig.ssp.util.sort;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.util.collections.Pair;

import com.google.common.collect.Lists;

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

	final private transient Integer maxResults;

	final private transient String defaultSortProperty;

	final private transient SortDirection defaultSortDirection;

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
		this.status = status;
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
	 *            here. Can not exceed {@link #MAXIMUM_ALLOWABLE_RESULTS}.
	 * @param sortFields
	 *            Ordered list of sort fields and directions
	 * @param defaultSortProperty
	 *            A default sort property if the sort parameter is null.
	 * @param defaultSortDirection
	 *            A default sort direction if the sort parameter is null.
	 */
	public SortingAndPaging(final ObjectStatus status,
			final Integer firstResult, final Integer maxResults,
			final List<Pair<String, SortDirection>> sortFields,
			final String defaultSortProperty,
			final SortDirection defaultSortDirection) {
		this.status = status;
		this.firstResult = (firstResult == null)
				|| (firstResult < Integer.valueOf(0)) ? Integer.valueOf(0)
				: firstResult;
		this.maxResults = maxResults == null
				? DEFAULT_MAXIMUM_RESULTS
				: (maxResults > MAXIMUM_ALLOWABLE_RESULTS ? MAXIMUM_ALLOWABLE_RESULTS
						: (maxResults < Integer.valueOf(1) ? DEFAULT_MAXIMUM_RESULTS
								: maxResults));
		this.sortFields = sortFields;
		this.defaultSortProperty = defaultSortProperty;
		this.defaultSortDirection = defaultSortDirection;
	}

	/**
	 * Gets the object status
	 * 
	 * @return the object status
	 */
	public ObjectStatus getStatus() {
		return status;
	}

	/**
	 * Gets the first result filter setting. 0-based index
	 * 
	 * @return the first result filter setting
	 */
	public Integer getFirstResult() {
		return firstResult;
	}

	/**
	 * Gets the maximum results filter setting
	 * 
	 * @return the maximum results filter setting
	 */
	public Integer getMaxResults() {
		return maxResults;
	}

	/**
	 * Gets the default sort property
	 * 
	 * @return the default sort property
	 */
	public String getDefaultSortProperty() {
		return defaultSortProperty;
	}

	/**
	 * Gets the default sort direction
	 * 
	 * @return the default sort direction
	 */
	public SortDirection getDefaultSortDirection() {
		return defaultSortDirection;
	}

	/**
	 * Gets the ordered list of sort fields, in a Pair with the field (property)
	 * as the first argument and the sort direction as the second argument.
	 * 
	 * @return the ordered list of sort fields
	 */
	public List<Pair<String, SortDirection>> getSortFields() {
		return sortFields;
	}

	/**
	 * True if the {@link ObjectStatus} filter is set to anything besides
	 * {@link ObjectStatus#ALL}.
	 * 
	 * @return true if filtering by object status
	 */
	public boolean isFilteredByStatus() {
		return (status != ObjectStatus.ALL);
	}

	/**
	 * True if these filters include paging, determined by the use of a maximum
	 * results and valid first result filters.
	 * 
	 * @return true if these filters include paging
	 */
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
	public static SortingAndPaging createForSingleSort(
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
}