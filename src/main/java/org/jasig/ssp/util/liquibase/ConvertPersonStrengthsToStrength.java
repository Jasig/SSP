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
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.model.Person;

public class ConvertPersonStrengthsToStrength implements CustomSqlChange {

	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String CONFIDENTIALITY_LEVEL_EVERYONE_ID = "B3D077A7-4055-0510-7967-4A09F93A0357";
	private ResourceAccessor resourceAccessor;

	@Override
	public String getConfirmationMessage() {
		// TODO Auto-generated method stub
		return "ConvertPersonStrengthsToStrength custom migration task updated person and strength tables";
	}

	@Override
	public void setUp() throws SetupException {
		ValidationErrors errors =  new ValidationErrors();
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
		final SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
		final String query = "select id, coach_id, strengths from person where strengths IS NOT NULL AND strengths != ''";
		processQuery(database, query, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				if(StringUtils.isNotBlank(rs.getString(3)))
				{
					String personId = rs.getString(1);
					String coachId = StringUtils.isNotBlank(rs.getString(2)) ? rs.getString(2) : 
							Person.SYSTEM_ADMINISTRATOR_ID.toString();
					String strength = rs.getString(3);
					Date now = new Date();
						SqlStatement statement =
								new RawSqlStatement("insert into strength (id, "
										+ " name,"
										+ " description,"
										+ " person_id,"
										+ " confidentiality_level_id,"
										+ " object_status,"
										+ " created_by,"
										+ " modified_by,"
										+ " created_date,"
										+ " modified_date ) values('"
										+ UUID.randomUUID().toString() 
										+ "', '" + "Migrated"
										+ "', '" + StringEscapeUtils.escapeSql(strength)
										+ "', '" + personId
										+ "', '" + CONFIDENTIALITY_LEVEL_EVERYONE_ID
										+ "',1"
										+ ", '" + coachId
										+ "', '" + coachId
										+ "', '" + df.format(now)
										+ "', '" + df.format(now) + "')");
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


}
