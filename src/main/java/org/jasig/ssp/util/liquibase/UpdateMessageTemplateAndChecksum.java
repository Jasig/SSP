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

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import liquibase.change.custom.CustomSqlChange;
import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomChangeException;
import liquibase.exception.CustomPreconditionErrorException;
import liquibase.exception.SetupException;
import liquibase.exception.ValidationErrors;
import liquibase.resource.ResourceAccessor;
import liquibase.statement.SqlStatement;
import liquibase.statement.core.RawSqlStatement;
/*** Use this after a message template has been updated to update the checksum
 *   WARNING DO NOT USE UPDATE MESSAGE TEMPLATES WITHOUT VALIDATING CHECKSUMS 
 *   with MessageTemplateCheckSumPrecondition
 *   or you may override user changes
 * 
 * @author jamesstanley
 *
 * Example:
 * <customChange class="org.jasig.ssp.util.liquibase.UpdateMessageTemplateAndChecksum">
 *   <param name="messageTemplateId" value="0b7e484d-44e4-4f0d-8db5-3518d015b495"/>
 *   <param name="columnName" value="body"/>
 *   <param name="columnValue"><![CDATA[ <p>Templte Update<p>
                                <p>"Please See your advisor" </p>]]>]]</param>
 * </customChange>
 * 
 *                FULL SAMPLE CHANGE SET:
 *<changeSet id="sample change set" author="jim.stanley">
 *      <preConditions onFail="MARK_RAN" onFailMessage="Skipping update to message template ">
 *           <customPrecondition className="org.jasig.ssp.util.liquibase.MessageTemplateCheckSumPrecondition">
 *                 <param name="messageTemplateId" value="d6d1f68a-0533-426f-bd0b-d129a92edf81"/>
 *                <param name="columnName" value="subject"/>
 *          </customPrecondition>
 *      </preConditions>
 *          <customChange class="org.jasig.ssp.util.liquibase.UpdateMessageTemplateAndChecksum">
 *               <param name="messageTemplateId" value="d6d1f68a-0533-426f-bd0b-d129a92edf81"/>
 *              <param name="columnName" value="subject"/>
 *              <param name="columnValue"><![CDATA[ <p>Templte Update<p>
                                <p>"Please See your advisor" </p>]]></param>
 *          </customChange>
 *  </changeSet>
 *  
 *  
 ***/

public class UpdateMessageTemplateAndChecksum implements CustomSqlChange {
	
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	String messageTemplateId;
	
	String columnName;
	
	String columnValue;
	
	Boolean resetChecksum = false;
	
	private ResourceAccessor resourceAccessor;
	
	public UpdateMessageTemplateAndChecksum() {
		// TODO Auto-generated constructor stub
	}

	public String getMessageTemplateId() {
		return messageTemplateId;
	}

	public void setMessageTemplateId(String messageTemplateId) {
		this.messageTemplateId = messageTemplateId;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getColumnValue() {
		return columnValue;
	}

	public void setColumnValue(String columnValue) {
		this.columnValue = columnValue;
	}

	@Override
	public String getConfirmationMessage() {
		return "Checksum updated for " + columnName + " and message template: " + messageTemplateId;

	}

	@Override
	public void setUp() throws SetupException {
		ValidationErrors errors =  new ValidationErrors();
		
		if(StringUtils.isBlank(messageTemplateId))
			errors.addError("MessageTemplateId can not be blank.");
		if(resetChecksum == false && StringUtils.isBlank(columnName))
			errors.addError("columnName can not be blank.");
		
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
		List<SqlStatement> statements = new ArrayList<SqlStatement>();
		if(resetChecksum){
			final String query = "select " + columnName + " from message_template where id = '" + messageTemplateId + "'";
			columnValue = processQuery(database, query);
		}else{
			//UPDATE COLUMN
			statements.add(new RawSqlStatement("update message_template set " 
					+ columnName + " = '" + columnValue 
					+ "' where id = '" + messageTemplateId + "'"));
		}
		try{
			String newColumnCheckSum = getCheckSum(StringUtils.deleteWhitespace(columnValue));
			
			//UPDATE CHECKSUM
			statements.add(new RawSqlStatement("update message_template set " + columnName + "_checksum = '" + newColumnCheckSum 
					+ "' where id = '" + messageTemplateId + "'"));
			final SimpleDateFormat df = new SimpleDateFormat(DATE_TIME_FORMAT);
			String newFormattedDate = df.format(new Date());
			statements.add(new RawSqlStatement("update message_template set modified_date = '" +  newFormattedDate
					+ "' where id = '" + messageTemplateId + "'"));

		}catch(NoSuchAlgorithmException exp){
			throw new CustomChangeException("Jave version does not support Message Digest type SHA1", exp);
		}
		
		return statements.toArray(new SqlStatement[statements.size()]);
	}
	
	private String processQuery(Database database, String query) throws CustomChangeException{
		PreparedStatement preparedQuery = null;
		ResultSet rs = null;
		CustomPreconditionErrorException error = null;
		try {
			JdbcConnection conn = (JdbcConnection)database.getConnection();
			preparedQuery = conn.prepareStatement(query);
			rs = preparedQuery.executeQuery();
			rs.next();
			return rs.getString(1);
		} catch ( Exception e ) {
			throw new CustomChangeException("Query [" + query + "] failed", e);
		} finally {
			if ( rs != null ) {
				try {
					rs.close();
				} catch ( Exception ee ) {
					if ( error == null ) {
						throw new CustomChangeException(
								"Failed to cleanup result set after query [" + query + "]", ee);
					}
				}
			}
			if ( preparedQuery != null ) {
				try {
					preparedQuery.close();
				} catch ( Exception ee ) {
					if ( error == null ) {
						throw  new CustomChangeException(
								"Failed to cleanup statement after query [" + query + "]", ee);
					}
				}
			}
		}
	}
	
	private String getCheckSum(String  value) throws NoSuchAlgorithmException{
		 MessageDigest md = MessageDigest.getInstance("SHA1");
		 
		byte[] mdbytes = md.digest(value.getBytes());
		 		    StringBuffer sb = new StringBuffer("");
		for (int i = 0; i < mdbytes.length; i++) {
			sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		return sb.toString();
	}

	public Boolean getResetChecksum() {
		return resetChecksum;
	}

	public void setResetChecksum(Boolean resetChecksum) {
		this.resetChecksum = resetChecksum;
	}

}
