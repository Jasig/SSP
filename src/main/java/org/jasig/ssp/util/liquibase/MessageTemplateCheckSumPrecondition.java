package org.jasig.ssp.util.liquibase;
/***
 *   Use this in liquibase scripts to verify checksum has not changed from the default value
 *   so users custom template changes are not overwritten
 *   
 *  <customPrecondition className="org.jasig.ssp.util.liquibase.MessageTemplateCheckSumPrecondition">
 *   <param name="messageTemplateId" value="0b7e484d-44e4-4f0d-8db5-3518d015b495"/>
 *   <param name="columnName" value="body"/>
 *	</customPrecondition>
 *  where the columnName is the name of the column that is used to generate the checksum
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import liquibase.database.Database;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.CustomPreconditionErrorException;
import liquibase.exception.CustomPreconditionFailedException;
import liquibase.precondition.CustomPrecondition;

import org.apache.commons.lang.StringUtils;

public class MessageTemplateCheckSumPrecondition implements
		CustomPrecondition {

	private String messageTemplateId;
	
	private String columnName;
	
	public MessageTemplateCheckSumPrecondition() {
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

	@Override
	public void check(Database database)
			throws CustomPreconditionFailedException,
			CustomPreconditionErrorException {
		
		if(StringUtils.isBlank(columnName))
			throw new CustomPreconditionErrorException("columnName is empty");
		if(StringUtils.isBlank(messageTemplateId))
			throw new CustomPreconditionErrorException("messageTemplateId is empty");
		
		final String query = "select " + columnName + " from message_template where id = '" + messageTemplateId + "'";
		String columnValue = processQuery(database, query);
		
		String currentChecksum = "";
		try {
			currentChecksum = getCheckSum(StringUtils.deleteWhitespace(columnValue));
		} catch (NoSuchAlgorithmException e) {
			throw new CustomPreconditionErrorException("Unable to calculate checksum");
		}
		
		final String defaultCheckSumQuery = "select " + columnName  + "_checksum" + " from message_template where id = '" + messageTemplateId + "'";
		final String defaultCheckSum = processQuery(database, defaultCheckSumQuery);
		
		if(!defaultCheckSum.equals(currentChecksum))
			throw new CustomPreconditionFailedException("checkSums did not match for message template id: " 
													+ messageTemplateId + " and column" + columnName);
	}
	
	private String processQuery(Database database, String query) throws CustomPreconditionErrorException{
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
			throw new CustomPreconditionErrorException("Query [" + query + "] failed", e);
		} finally {
			if ( rs != null ) {
				try {
					rs.close();
				} catch ( Exception ee ) {
					if ( error == null ) {
						throw new CustomPreconditionErrorException(
								"Failed to cleanup result set after query [" + query + "]", ee);
					}
				}
			}
			if ( preparedQuery != null ) {
				try {
					preparedQuery.close();
				} catch ( Exception ee ) {
					if ( error == null ) {
						throw  new CustomPreconditionErrorException(
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
}
