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
package liquibase.sqlgenerator.ext;

import liquibase.database.Database;
import liquibase.database.core.MSSQLDatabase;
import liquibase.exception.DatabaseException;
import liquibase.exception.UnexpectedLiquibaseException;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.sqlgenerator.core.DropDefaultValueGenerator;
import liquibase.statement.core.DropDefaultValueStatement;

/**
 * Workaround for Liquibase issue when trying to drop constraints against SQLServer versions later than 2008 (2012 in
 * particular at this writing): <a href="https://liquibase.jira.com/browse/CORE-1141">CORE-1141</a>.
 *
 * <p>See <a href="https://liquibase.jira.com/wiki/display/CONTRIB/SqlGenerator">Liquibase docs</a> on {@code SqlGenerator}
 * plugins.</p>
 */
public class MSSQL2012OrLaterDropDefaultValueGenerator extends DropDefaultValueGenerator {

    /**
     * System property that can turn this plugin off. Plugin is on by default. Set this property to any non-'true'
     * value to disable it.
     */
    public static final String KILL_SWITCH = "ssp.enable.mssql.2012.drop.default.liquibase.ext";

    @Override
    /**
     * Makes sure we're given a chance to completely hide the default DropDefaultValueGenerator implementation.
     */
    public int getPriority() {
        return super.getPriority()+1;
    }

    @Override
    public boolean supports(DropDefaultValueStatement statement, Database database) {
        if ( !(isEnabled()) ) {
            return false;
        }
        return super.supports(statement, database) && isMssql2012OrLater(database);
    }

    @Override
    public Sql[] generateSql(DropDefaultValueStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        // 'query' building here is a direct copy paste out of the super impl. No other way to get to it except by wrapping
        // 'database' in a proxy that would override connection metadata. That's just way too complicated.

        String query = "DECLARE @default sysname\n";
        query += "SELECT @default = object_name(default_object_id) FROM sys.columns WHERE object_id=object_id('" + statement.getSchemaName() + "." + statement.getTableName() + "') AND name='" + statement.getColumnName() + "'\n";
        query += "EXEC ('ALTER TABLE " + database.escapeTableName(statement.getSchemaName(), statement.getTableName()) + " DROP CONSTRAINT ' + @default)";

        return new Sql[] {
                new UnparsedSql(query)
        };
    }

    protected boolean isMssql2012OrLater(Database database) {
        // See similar logic in super generateSql() impl. Main difference is that impl just checks explicitly
        // for major versions 9 and 10 and falls back to a default query if neither of those match. The problem
        // is that the query it falls back to doesn't work on 11 (2012). So here we assume we want also to use the
        // "9- and 10-ish" query for any major version 11 or higher.
        if (database instanceof MSSQLDatabase) {
            try {
                String productVersionStr = database.getDatabaseProductVersion();
                String[] productDotVersionStrs = productVersionStr.split("\\.");
                if ( productDotVersionStrs == null || productDotVersionStrs.length == 0 ) {
                    return false;
                }
                try {
                    String productMajorVersionStr = productDotVersionStrs[0];
                    int productMajorVersion = Integer.parseInt(productMajorVersionStr);
                    return productMajorVersion >= 11;
                } catch ( NumberFormatException e ) {
                    return false;
                }
            } catch (DatabaseException e) {
                throw new UnexpectedLiquibaseException(e);
            }
        }
        return false;
    }

    protected boolean isEnabled() {
        String killSwitch = System.getProperty(KILL_SWITCH);
        killSwitch = killSwitch == null ? null : killSwitch.toLowerCase();
        return killSwitch == null || Boolean.parseBoolean(killSwitch);
    }
}
