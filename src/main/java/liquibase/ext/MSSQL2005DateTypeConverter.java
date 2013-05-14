package liquibase.ext;

import liquibase.database.Database;
import liquibase.database.structure.type.DateType;
import liquibase.database.typeconversion.core.MSSQLTypeConverter;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.PrioritizedService;

/**
 * Uses <code>datetime</code> as the physical type for the logical Liquibase
 * <code>date</code> type in SQLServer versions prior to 2008. We need this b/c
 * the default mapping is to the physical <code>smalldatetime</code> type, which
 * Hibernate will refuse to handle as a <code>java.sql.Date</code>.
 *
 * @see MSSQL2008DateTypeConverter
 */
public class MSSQL2005DateTypeConverter extends MSSQLTypeConverter {
	private static final int MSSQL_2008_VERSION = 10;

	@Override
	public boolean supports(Database database) {
		try {
			return super.supports(database) && database.getDatabaseMajorVersion() < MSSQL_2008_VERSION;
		} catch (DatabaseException e) {
			return false;
		}
	}

	@Override
	public int getPriority() {
		return PrioritizedService.PRIORITY_DATABASE + 1;
	}

	@Override
	public DateType getDateType() {
		return new DateType("DATETIME");
	}
}
