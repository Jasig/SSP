package org.jasig.ssp.util.sort;

import java.io.Serializable;
import java.util.Collection;

import org.jasig.ssp.model.Auditable;

/**
 * Wrap results (rows) that have been paged but still include the total record
 * count in this returned data instance.
 * 
 * @author jon.adams
 * 
 * @param <T>
 *            Model type that must inherit from Auditable
 */
public class PagingWrapper<T extends Auditable> implements Serializable {

	private static final long serialVersionUID = -6028264862839080192L;

	private long results;

	private Collection<T> rows;

	/**
	 * Constructor that initializes the paged rows to return.
	 * <p>
	 * The total row count is assumed to be only the rows in the collection, and
	 * is set accordingly.
	 * 
	 * @param rows
	 *            Only the paged rows that were requested
	 */
	public PagingWrapper(final Collection<T> rows) {
		this(rows == null ? 0 : rows.size(), rows);
	}

	/**
	 * Constructor that initializes the total row count, and the paged rows to
	 * return.
	 * 
	 * @param totalCount
	 *            FS * Total row count for all matching rows in the database
	 * @param rows
	 *            Only the paged rows that were requested
	 */
	public PagingWrapper(final long totalCount, final Collection<T> rows) {
		if (totalCount < 0) {
			throw new IllegalArgumentException(
					"Total row count may not be a negative value.");
		}

		if (rows == null) {
			throw new IllegalArgumentException(
					"Collection of rows may not be null.");
		}

		this.results = totalCount;
		this.rows = rows;
	}

	/**
	 * @return the success
	 */

	/**
	 * @return the results
	 */
	public long getResults() {
		return results;
	}

	/**
	 * @param results
	 *            the results to set
	 */
	public void setResults(final long results) {
		this.results = results;
	}

	/**
	 * @return the rows
	 */
	public Collection<T> getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(final Collection<T> rows) {
		this.rows = rows;
	}
}
