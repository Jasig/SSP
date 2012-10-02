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
package org.jasig.ssp.util.hibernate;


import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;

import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Cherry-picking from
 * <a href="http://blog.saddey.net/wp-content/uploads/2011/12/minimalsqlserver2008dialect.java">this</a>.
 *
 */
public class SQLServerViolatedConstraintNameExtractor
		implements ViolatedConstraintNameExtracter {

	/**
	 * Works around default SQLServer dialect behavior which never finds a
	 * violated constraint name.
	 *
	 * @param sqle
	 * @return
	 */
	@Override
	public String extractConstraintName(SQLException sqle) {
		final String detailMessage = sqle.getMessage();
		final Matcher matcher
				= Pattern.compile(".+(constraint|unique index) '([^']+)'\\..*")
				.matcher(detailMessage);
		if (matcher.matches()) {
			final String violName = matcher.group(2);
			return violName;
		}
		return null;
	}
}
