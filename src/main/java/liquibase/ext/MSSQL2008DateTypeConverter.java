package liquibase.ext;

import liquibase.database.Database;
import liquibase.database.structure.type.DateType;
import liquibase.database.structure.type.TimeType;
import liquibase.database.typeconversion.core.MSSQLTypeConverter;
import liquibase.exception.DatabaseException;
import liquibase.servicelocator.PrioritizedService;

/**
 * Custom Liquibase TypeConverter for Microsoft SQL Server 2008 and higher which
 * supports proper DATE and TIME datatypes. By default Liquibase will map the
 * logical <code>date</code> type to the physical <code>smalldatetime</code>,
 * which isn't what we want at all... we want no time component at all.
 * Also, Hibernate is going to complain when handling a
 * <code>smalldatetime</code> as a <code>java.sql.Date</code>.
 *
 * <p>Based on code posted in
 * <a href="http://forum.liquibase.org/topic/date-and-time-types-for-mssql">this forum thread</a></p>
 *
 */
public class MSSQL2008DateTypeConverter extends MSSQLTypeConverter {
	private static final int MSSQL_2008_VERSION = 10;

	@Override
	public boolean supports(Database database) {
		try {
			return super.supports(database) && database.getDatabaseMajorVersion() >= MSSQL_2008_VERSION;
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
		return new DateType("DATE");
	}

	@Override
	public TimeType getTimeType() {
		return new TimeType("TIME");
	}
}
