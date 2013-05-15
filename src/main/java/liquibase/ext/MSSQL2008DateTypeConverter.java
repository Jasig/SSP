/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
		return super.getPriority()+1;
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
