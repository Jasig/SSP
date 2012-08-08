package org.jasig.ssp.util.hibernate;


import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;

/**
 * Extensions to {@link SQLServerDialect} to work around SQLServer-specific
 * issues with SSP.
 */
public class ExtendedSQLServerDialect extends SQLServerDialect {

	public ExtendedSQLServerDialect() {
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
