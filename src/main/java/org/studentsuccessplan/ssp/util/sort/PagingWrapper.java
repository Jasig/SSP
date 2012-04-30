package org.studentsuccessplan.ssp.util.sort;

import java.io.Serializable;
import java.util.Collection;

public class PagingWrapper<T> implements Serializable {

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
	public void setResults(long results) {
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
	public void setRows(Collection<T> rows) {
		this.rows = rows;
	}
}
