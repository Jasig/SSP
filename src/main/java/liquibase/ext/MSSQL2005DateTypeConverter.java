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
		return super.getPriority()+1;
	}

	@Override
	public DateType getDateType() {
		return new DateType("DATETIME");
	}
}
