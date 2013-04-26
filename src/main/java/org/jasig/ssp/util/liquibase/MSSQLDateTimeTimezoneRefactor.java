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
public class MSSQLDateTimeTimezoneRefactor implements CustomSqlChange {

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String DEFAULT_ID_COLUMN_NAME = "id";
	private ResourceAccessor resourceAccessor;
	private String tableName;
	private String columnName;
	private String idColumnName = DEFAULT_ID_COLUMN_NAME;
	private TimeZone origTimeZone;
	private TimeZone newTimeZone;

	@Override
	public SqlStatement[] generateStatements(Database database) throws CustomChangeException {
		if ( origTimeZone.equals(newTimeZone) ) {
			return new SqlStatement[0];
		}

		final List<SqlStatement> statements = new ArrayList<SqlStatement>();
		final SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
		df.setTimeZone(newTimeZone);
		final String query = "select " + idColumnName + ", " + columnName + " from " + tableName;
		processQuery(database, query, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String id = rs.getString(1);
				Date origDate = rs.getTimestamp(2, Calendar.getInstance(origTimeZone));
				if ( origDate != null ) {
					String newFormattedDate = df.format(origDate);
					SqlStatement statement =
							new RawSqlStatement("update "+tableName+" set " +
									columnName+" = '" + newFormattedDate +
									"' where " + idColumnName + " = '" + id + "'");
					statements.add(statement);
				}
				return null;
			}
		});

		return statements.toArray(new SqlStatement[statements.size()]);
	}

	private void processQuery(Database database, String query, RowMapper callback)
			throws CustomChangeException {
		PreparedStatement preparedQuery = null;
		ResultSet rs = null;
		CustomChangeException error = null;
		try {
			JdbcConnection conn = (JdbcConnection)database.getConnection();
			preparedQuery = conn.prepareStatement(query);
			rs = preparedQuery.executeQuery();
			int cnt = 0;
			while ( rs.next() ) {
				callback.mapRow(rs, cnt++);
			}
		} catch ( Exception e ) {
			error = new CustomChangeException("Query [" + query + "] failed", e);
		} finally {
			if ( rs != null ) {
				try {
					rs.close();
				} catch ( Exception ee ) {
					if ( error == null ) {
						error = new CustomChangeException(
								"Failed to cleanup result set after query [" + query + "]", ee);
					}
				}
			}
			if ( preparedQuery != null ) {
				try {
					preparedQuery.close();
				} catch ( Exception ee ) {
					if ( error == null ) {
						error = new CustomChangeException(
								"Failed to cleanup statement after query [" + query + "]", ee);
					}
				}
			}
			// assume LB cleans up conn
		}
		if ( error != null ) {
			throw error;
		}
	}

	@Override
	public String getConfirmationMessage() {
		return "MSSQLDateTimeTimezoneRefactor custom migration task updated "+tableName+"."+columnName;
	}

	@Override
	public void setUp() throws SetupException {
		ValidationErrors errors =  new ValidationErrors();
		errors.checkRequiredField("tableName", this.getTableName());
		errors.checkRequiredField("columnName", this.getColumnName());
		errors.checkRequiredField("idColumnName", this.getIdColumnName());
		errors.checkRequiredField("origTimeZoneId", this.getOrigTimeZoneId());
		errors.checkRequiredField("newTimeZoneId", this.getNewTimeZoneId());
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

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getIdColumnName() {
		return idColumnName;
	}

	public void setIdColumnName(String idColumnName) {
		this.idColumnName = idColumnName;
	}

	public String getOrigTimeZoneId() {
		return origTimeZone == null ? null : origTimeZone.getID();
	}

	public void setOrigTimeZoneId(String id) {
		this.origTimeZone = TimeZone.getTimeZone(id);
	}

	public String getNewTimeZoneId() {
		return newTimeZone == null ? null : newTimeZone.getID();
	}

	public void setNewTimeZoneId(String id) {
		this.newTimeZone = TimeZone.getTimeZone(id);
	}
}
