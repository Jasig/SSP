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

import org.hibernate.dialect.SQLServer2008Dialect;
import org.hibernate.exception.spi.ViolatedConstraintNameExtracter;

/**
 * Extensions to {@link SQLServer2008Dialect} to work around SQLServer-specific
 * issues with SSP.
 */
public class ExtendedSQLServer2008Dialect extends SQLServer2008Dialect {

	public ExtendedSQLServer2008Dialect() {
		super();
	}

	private static final ViolatedConstraintNameExtracter MS_SQL_EXTRACTER = new SQLServerViolatedConstraintNameExtractor();

	/**
	 * Provides a {@link ViolatedConstraintNameExtracter} that tries to return
	 * the name of the violated constraint (or unique index). Note: The default
	 * (super) implementation always returns null.
	 */
	@Override
	public ViolatedConstraintNameExtracter getViolatedConstraintNameExtracter() {
		return MS_SQL_EXTRACTER;
	}

	/**
	 * Overriden to deal with <a href="https://issues.jasig.org/browse/SSP-1681">SSP-1681</a>.
	 * Had to copy paste the entire thing, unfortunately, because what we
	 * really needed to do was override {@code insertRowNumberFunction()},
	 * which was static.
	 *
	 * @param querySqlString
	 * @param hasOffset
	 * @return
	 */
	@Override
	public String getLimitString(String querySqlString, boolean hasOffset) {
		StringBuilder sb = new StringBuilder( querySqlString.trim().toLowerCase() );

		int orderByIndex = sb.indexOf( "order by" );
		CharSequence orderby = orderByIndex > 0 ? sb.subSequence( orderByIndex, sb.length() )
				: "ORDER BY CURRENT_TIMESTAMP";

		// Delete the order by clause at the end of the query
		if ( orderByIndex > 0 ) {
			sb.delete( orderByIndex, orderByIndex + orderby.length() );
		}

		// HHH-5715 bug fix
		replaceDistinctWithGroupBy( sb );

		insertRowNumberFunctionOverride( sb, orderby );

		// Wrap the query within a with statement:
		sb.insert( 0, "WITH query AS (" ).append( ") SELECT * FROM query " );
		sb.append( "WHERE __hibernate_row_nr__ >= ? AND __hibernate_row_nr__ < ?" );

		return sb.toString();
	}

	/**
	 * Actually an override of
	 * {@link SQLServer2005Dialect#insertRowNumberFunctionOverride}, which
	 * is static. (Had to change the name else the compiler will complain.)
	 *
	 * <p>Fixes <a href="https://issues.jasig.org/browse/SSP-1681">SSP-1681</a></p>
	 *
	 * @param sql
	 * @param orderby
	 */
	protected void insertRowNumberFunctionOverride(StringBuilder sql, CharSequence orderby) {
		// Find the end of the select statement but be sure to skip any
		// subselects that might be in the column list, but don't skip *into*
		// subselects in the table list. We happen to know that subselects in
		// the column list are named with the substring "formula", so find
		// the last one of those, then the first occurrance of "from" after that
		int lastFormulaIndex = Math.max(0, sql.lastIndexOf( "formula" ));
		int selectEndIndex = sql.indexOf("from ", lastFormulaIndex); // FROM constant is private in SQLServer2005Dialect, alas

		// Insert after the select statement the row_number() function:
		sql.insert( selectEndIndex - 1, ", ROW_NUMBER() OVER (" + orderby + ") as __hibernate_row_nr__" );
	}
}
