package org.jasig.ssp.util.sort;

public enum SortDirection {
	ASC, DESC;

	public static SortDirection getSortDirection(String sortDirection) {
		if (sortDirection == null) {
			return ASC;
		} else {
			SortDirection result;
			try {
				result = valueOf(sortDirection);
			} catch (Exception e) {
				result = ASC;
			}
			return result;
		}
	}
}
