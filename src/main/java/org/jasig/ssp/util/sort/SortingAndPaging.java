package org.jasig.ssp.util.sort;

import java.util.LinkedHashMap;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.jasig.ssp.model.ObjectStatus;

import com.google.common.collect.Maps;

public class SortingAndPaging {

	final private ObjectStatus status;
	final private Integer firstResult, maxResults;
	final private String defaultSortProperty;
	final private SortDirection defaultSortDirection;
	private LinkedHashMap<String, SortDirection> sortFields;

	public void appendSortField(final String fieldname,
			final SortDirection direction) {
		if (sortFields == null) {
			sortFields = new LinkedHashMap<String, SortDirection>();
		}
		sortFields.put(fieldname, direction);
	}

	public void prependSortField(final String fieldname,
			final SortDirection direction) {
		if (sortFields == null) {
			sortFields = new LinkedHashMap<String, SortDirection>();
			sortFields.put(fieldname, direction);
		} else {
			final LinkedHashMap<String, SortDirection> newOrdering = Maps
					.newLinkedHashMap();
			newOrdering.put(fieldname, direction);
			newOrdering.putAll(sortFields);
			sortFields = newOrdering;
		}
	}

	public SortingAndPaging(final ObjectStatus status) {
		this.status = status;
		this.firstResult = null;
		this.maxResults = null;
		this.defaultSortProperty = null;
		this.defaultSortDirection = null;
		this.sortFields = null;
	}

	public static SortingAndPaging createForSingleSort(
			final ObjectStatus status,
			final Integer firstResult, final Integer maxResults,
			final String sort, final String sortDirection,
			final String defaultSortProperty) {

		LinkedHashMap<String, SortDirection> sortFields;
		SortDirection defaultSortDirection;

		// if there has been a sort passed in, use it, otherwise use the default
		// sort
		if (sort == null) {
			sortFields = null;
			defaultSortDirection = SortDirection
					.getSortDirection(sortDirection);
		} else {
			sortFields = Maps.newLinkedHashMap();
			sortFields.put(sort, SortDirection.getSortDirection(sortDirection));
			defaultSortDirection = null;
		}

		final SortingAndPaging sAndP = new SortingAndPaging(
				status == null ? ObjectStatus.ACTIVE : status,
				firstResult, maxResults, sortFields, defaultSortProperty,
				defaultSortDirection);

		return sAndP;
	}

	public SortingAndPaging(final ObjectStatus status,
			final Integer firstResult, final Integer maxResults,
			final LinkedHashMap<String, SortDirection> sortFields,
			final String defaultSortProperty,
			final SortDirection defaultSortDirection) {
		this.status = status;
		this.firstResult = firstResult;
		this.maxResults = maxResults;
		this.sortFields = sortFields;
		this.defaultSortProperty = defaultSortProperty;
		this.defaultSortDirection = defaultSortDirection;
	}

	public Integer getFirstResult() {
		return firstResult;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public String getDefaultSortProperty() {
		return defaultSortProperty;
	}

	public SortDirection getDefaultSortDirection() {
		return defaultSortDirection;
	}

	public LinkedHashMap<String, SortDirection> getSortFields() {
		return sortFields;
	}

	public boolean isFilteredByStatus() {
		return (status != ObjectStatus.ALL);
	}

	public boolean isPaged() {
		return ((null != maxResults)
				&& (maxResults > 0)
				&& (null != firstResult));
	}

	public boolean isSorted() {
		if ((sortFields == null) || sortFields.isEmpty()) {
			return false;
		} else {
			final Entry<String, SortDirection> entry = sortFields.entrySet()
					.iterator().next();
			return !StringUtils.isEmpty(entry.getKey());
		}
	}

	public boolean isDefaultSorted() {
		return (null != defaultSortProperty)
				&& !StringUtils.isEmpty(defaultSortProperty);
	}

	public void addStatusFilterToCriteria(final Criteria criteria) {
		if (isFilteredByStatus()) {
			criteria.add(Restrictions.eq("objectStatus", status));
		}
	}

	public void addPagingToCriteria(final Criteria criteria) {
		if (isPaged()) {
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(maxResults);
		}
	}

	public void addSortingToCriteria(final Criteria criteria) {
		if (isSorted()) {
			// sort by each entry in the map
			for (Entry<String, SortDirection> entry : sortFields.entrySet()) {
				addSortToCriteria(criteria, entry.getKey(), entry.getValue());
			}
		} else if (isDefaultSorted()) {
			// sort by the default property
			addSortToCriteria(criteria, defaultSortProperty,
					defaultSortDirection);
		} else {
			// no sorting done, don't continue
			return;
		}
	}

	private void addSortToCriteria(final Criteria criteria,
			final String sort, final SortDirection sortDirection) {
		if (sortDirection.equals(SortDirection.ASC)) {
			criteria.addOrder(Order.asc(sort));
		} else {
			criteria.addOrder(Order.desc(sort));
		}
	}

	public void addAll(final Criteria criteria) {
		addPagingToCriteria(criteria);
		addSortingToCriteria(criteria);
		addStatusFilterToCriteria(criteria);
	}
}
