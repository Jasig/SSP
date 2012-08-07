package org.jasig.ssp.util.hibernate;

import org.hibernate.dialect.SQLServer2005Dialect;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;


/**
 * Extensions to {@link SQLServer2005Dialect} to work around SQLServer-specific
 * issues with SSP.
 */
public class ExtendedSQLServer2005Dialect extends SQLServer2005Dialect {

	public ExtendedSQLServer2005Dialect() {
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
