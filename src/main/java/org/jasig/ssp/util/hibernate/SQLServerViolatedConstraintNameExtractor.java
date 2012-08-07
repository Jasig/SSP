package org.jasig.ssp.util.hibernate;


import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cherry-picking from
 * <a href="http://blog.saddey.net/wp-content/uploads/2011/12/minimalsqlserver2008dialect.java">this</a>.
 *
 */
public class SQLServerViolatedConstraintNameExtractor
		implements ViolatedConstraintNameExtracter {

	/**
	 * Works around default SQLServer dialect behavior which never finds a
	 * violated constraint name.
	 *
	 * @param sqle
	 * @return
	 */
	@Override
	public String extractConstraintName(SQLException sqle) {
		final String detailMessage = sqle.getMessage();
		final Matcher matcher
				= Pattern.compile(".+(constraint|unique index) '([^']+)'\\..*")
				.matcher(detailMessage);
		if (matcher.matches()) {
			final String violName = matcher.group(2);
			return violName;
		}
		return null;
	}
}
