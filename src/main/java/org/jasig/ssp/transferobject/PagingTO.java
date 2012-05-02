package org.jasig.ssp.transferobject;

import java.io.Serializable;
import java.util.Collection;

import org.jasig.ssp.model.Auditable;

/**
 * Wrap a paged response in a form that ExtJS may consume.
 * 
 * @author jon.adams
 * 
 * @param <TO>
 *            Any serializable domain model. Must be TransferObject<M>, but
 *            should be some type of {@link TransferObject}.
 *            <p>
 *            This should be enforced with an extends requirement like
 *            <code>TO extends TransferObject&lt;TO&gt;</code>, but Jackson can
 *            not serialize it correctly then due to its bugs with generics.
 * @param <T>
 *            The model type that the transfer object T maps.
 */
public class PagingTO<TO, T extends Auditable>
		implements Serializable {

	private static final long serialVersionUID = -4699337121115833047L;

	private boolean success;

	private long results;

	private Collection<TO> rows;

	/**
	 * Constructor that initializes the success of the service call and the
	 * paged rows to return.
	 * <p>
	 * The total row count is assumed to be only the rows in the collection, and
	 * is set accordingly.
	 * 
	 * @param success
	 *            Success of the service call
	 * @param rows
	 *            Only the paged rows that were requested
	 */
	public PagingTO(final boolean success, final Collection<TO> rows) {
		this(success, rows == null ? 0 : rows.size(), rows);
	}

	/**
	 * Constructor that initializes the success of the service call, total row
	 * count, and the paged rows to return.
	 * 
	 * @param success
	 *            Success of the service call
	 * @param totalCount
	 *            Total row count for all matching rows in the database
	 * @param rows
	 *            Only the paged rows that were requested
	 */
	public PagingTO(final boolean success, final long totalCount,
			final Collection<TO> rows) {
		if (totalCount < 0) {
			throw new IllegalArgumentException(
					"Total row count may not be a negative value.");
		}

		if (rows == null) {
			throw new IllegalArgumentException(
					"Collection of rows may not be null.");
		}

		this.success = success;
		this.results = totalCount;
		this.rows = rows;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success
	 *            the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

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
	public Collection<TO> getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(Collection<TO> rows) {
		this.rows = rows;
	}
}
