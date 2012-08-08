package org.jasig.ssp.util.uuid;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang.StringUtils;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.PostgresUUIDType;
import org.hibernate.type.descriptor.java.UUIDTypeDescriptor;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.jasig.ssp.util.hibernate.ExtendedSQLServer2008Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UUID type specific for the configured DBMS. Supports PostgreSQL and Microsoft
 * SQL Server.
 * 
 * <p>
 * Usage: Annotate an entity's identifier with
 * <code>@Type(type="uuid-custom")</code>
 * 
 * <p>
 * Note: The database dialect must be initialized with a call to
 * {@link #initSettings(String)} before Hibernate is initialized.
 * 
 * <p>
 * Modified from the code at <a href=
 * "https://zorq.net/b/2012/04/21/switching-hibernates-uuid-type-mapping-per-database/"
 * >Switching Hibernate’s UUID Type Mapping per Database</a>.
 * 
 * @author David Beaumont
 * @see org.hibernate.type.PostgresUUIDType
 * @author jon.adams
 */
public final class UUIDCustomType extends
		AbstractSingleColumnStandardBasicType<UUID> {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(UUIDCustomType.class);

	private static final long serialVersionUID = 6899061246976147229L;

	private static SqlTypeDescriptor sqlDescription;

	/**
	 * Initialize the configured dialect. Must be called before Hibernate is
	 * initialized.
	 * 
	 * @param dialect
	 *            DBMS Hibernate dialect.
	 * 
	 *            <p>
	 *            Supported dialects:
	 *            <ul>
	 *            <li><code>org.hibernate.dialect.PostgreSQLDialect</code></li>
	 *            <li><code>org.hibernate.dialect.SQLServerDialect</code></li>
	 *            <li><code>org.hibernate.dialect.SQLServer2008Dialect</code></li>
	 *            </ul>
	 * @throws UnsupportedOperationException
	 *             If the dialect is not in the supported list.
	 */
	public static void initSettings(@NotNull final String dialect) {
		if (!StringUtils.isNotBlank(dialect)) {
			throw new IllegalArgumentException("Dialect must be specified.");
		}

		if ("org.hibernate.dialect.PostgreSQLDialect".equalsIgnoreCase(dialect)) {
			sqlDescription = PostgresUUIDType.PostgresUUIDSqlTypeDescriptor.INSTANCE;
		} else {
			final Matcher matcher
					= Pattern.compile(".*SQLServer.*Dialect", Pattern.CASE_INSENSITIVE)
					.matcher(dialect);
			if ( matcher.matches() ) {
				sqlDescription = VarcharTypeDescriptor.INSTANCE;
			} else {
				throw new UnsupportedOperationException("Unsupported database dialect! (" + dialect + ")");
			}
		}
	}

	public UUIDCustomType() {
		super(sqlDescription, UUIDTypeDescriptor.INSTANCE);
		LOGGER.trace("UUIDCustomType initialized with SQL description: "
				+ sqlDescription);
	}

	@Override
	public String getName() {
		return "uuid-custom";
	}

}