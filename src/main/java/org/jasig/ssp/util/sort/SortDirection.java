package org.jasig.ssp.util.sort;

/**
 * Sort direction
 */
public enum SortDirection {
	/**
	 * Ascending sort
	 */
	ASC,

	/**
	 * Descending sort
	 */
	DESC;

	/**
	 * Parses a string to get the equivalent sort direction enum.
	 * 
	 * @param sortDirection
	 *            Sort direction (must be either "ASC" or "DESC",
	 *            case-insensitive)
	 * @return An equivalent sort direction, or {@link #ASC} if null or invalid
	 *         parameters were sent.
	 */
	public static SortDirection getSortDirection(final String sortDirection) {
		if (sortDirection == null) {
			return ASC;
		} else {
			try {
				return valueOf(sortDirection.toUpperCase()); // NOPMD
			} catch (final Exception e) {
				return ASC;
			}
		}
	}
}