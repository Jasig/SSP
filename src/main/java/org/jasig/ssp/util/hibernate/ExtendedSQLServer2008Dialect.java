package org.jasig.ssp.util.hibernate;

import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extensions to {@link SQLServer2008Dialect} to work around SQLServer-specific
 * issues with SSP.
 */
public class ExtendedSQLServer2008Dialect extends SQLServer2008Dialect {

	public ExtendedSQLServer2008Dialect() {
		super();
	}

	private static final ViolatedConstraintNameExtracter MS_SQL_EXTRACTER
			= new SQLServerViolatedConstraintNameExtractor();

	/**
	 * Provides a {@link ViolatedConstraintNameExtracter} that tries to return
	 * the name of the violated constraint (or unique index). Note: The default
	 * (super) implementation always returns null.
	 */
	@Override
	public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
		return MS_SQL_EXTRACTER;
	}
}
