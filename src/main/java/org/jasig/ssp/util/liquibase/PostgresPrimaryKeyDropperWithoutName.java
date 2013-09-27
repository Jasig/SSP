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
package org.jasig.ssp.util.liquibase;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.DatabaseException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.executor.jvm.RowMapper;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;

/**
 * Moves persistent timestamps from one timezone to another.
 * Could theoretically work against any db but only necessary in SQLServer b/c
 * it doesn't have any in-built functions for treating timestamps as if they
 * were in a particular time zone.
 */
public class PostgresPrimaryKeyDropperWithoutName implements CustomSqlChange {

	private String tableName;
	private ResourceAccessor resourceAccessor;
	
	@Override
	public String getConfirmationMessage() {
		return "PostgresPrimaryKeyDropperWithoutName custom migration task dropped "+tableName+"'s primarykey";
	}

	@Override
	public void setUp() throws SetupException {
		ValidationErrors errors =  new ValidationErrors();
		errors.checkRequiredField("tableName", this.getTableName());
		if ( errors.hasErrors() ) {
			throw new SetupException(errors.getErrorMessages().toString());
		}		
	}

	@Override
	public void setFileOpener(ResourceAccessor resourceAccessor) {
		this.resourceAccessor = resourceAccessor;
	}

	@Override
	public ValidationErrors validate(Database database) {
		return new ValidationErrors();
	}

	@Override
	public SqlStatement[] generateStatements(Database database)
			throws CustomChangeException {
		
		final List<SqlStatement> statements = new ArrayList<SqlStatement>();
		String constraintNameSql = "select constraint_name from information_schema.table_constraints "
				+ "where table_name = '"+tableName+"' and constraint_type = 'PRIMARY KEY'";
		
		JdbcConnection conn = (JdbcConnection)database.getConnection();
		PreparedStatement prepareStatement;
		
		RowMapper rowMapper = new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					String constraintName = rs.getString(1);
					String dropConstraintSql = "Alter table "+tableName+" drop constraint "+constraintName;
					SqlStatement sqlStatement = new RawSqlStatement(dropConstraintSql);
					statements.add(sqlStatement);
				return null;
			}
		};
		
		try {
			 prepareStatement = conn.prepareStatement(constraintNameSql);
			 prepareStatement.executeQuery(constraintNameSql);
		} catch (DatabaseException e) {
			throw new CustomChangeException(e);
		} catch (SQLException e) {
			throw new CustomChangeException(e);
		}
			int i=0;
			try {
				while(prepareStatement.getResultSet().next())
				{
					rowMapper.mapRow(prepareStatement.getResultSet(),i++);
				}
			} catch (SQLException e) {
				throw new CustomChangeException(e);
			}
		return statements.toArray(new SqlStatement[statements.size()]);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	
}
