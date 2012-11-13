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

import java.sql.Types;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.criterion.Order;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

import com.google.common.collect.Lists;

/**
 * Workaround for the fact that you can't use SQL functions to qualify
 * property names passed to {@link Order} criteria. E.g. in HQL you can do like
 * <code>order by (cast foo as string)</code>, but you can't when using the
 * {@link Criteria} API.
 */
public class OrderAsString extends Order {

	private String propertyName; // b/c field is private in super
	private boolean ascending; // b/c field is private in super
	private boolean ignoreCase; // b/c field is private in super

	protected OrderAsString(String propertyName, boolean ascending) {
		super(propertyName, ascending);
		this.propertyName = propertyName;
		this.ascending = ascending;
	}

	/**
	 * Overriden b/c underlying field is hidden in {@link Order}.
	 * @return
	 */
	@Override
	public Order ignoreCase() {
		super.ignoreCase();
		this.ignoreCase = true;
		return this;
	}

	/**
	 * Ascending order. Same as {@link Order#asc(String)} but
	 *
	 * @param propertyName
	 * @return Order
	 */
	public static OrderAsString asc(String propertyName) {
		return new OrderAsString(propertyName, true);
	}

	/**
	 * Descending order
	 *
	 * @param propertyName
	 * @return Order
	 */
	public static OrderAsString desc(String propertyName) {
		return new OrderAsString(propertyName, false);
	}

	/**
	 * Mainly a copy/paste of {@link Order#toSqlString(org.hibernate.Criteria, org.hibernate.criterion.CriteriaQuery)}
	 * with column name rendering modified to wrap column names in a function
	 * call. The super class impl was sufficiently monolothic that the only
	 * alternative was to implement a find-and-replace on column names in its
	 * output. But the Hibernate code should be stable enough that a copy/paste
	 * is much less risky than an impl that might accidentally replace
	 * substrings instead of entire column names.
	 *
	 * @param criteria
	 * @param criteriaQuery
	 * @return
	 * @throws HibernateException
	 */
	@Override
	public String toSqlString(Criteria criteria, CriteriaQuery criteriaQuery)
			throws HibernateException {

		String[] columns = criteriaQuery.getColumnsUsingProjection(criteria, propertyName);
		Type type = criteriaQuery.getTypeUsingProjection(criteria, propertyName);
		StringBuffer fragment = new StringBuffer();
		for ( int i=0; i<columns.length; i++ ) {
			SessionFactoryImplementor factory = criteriaQuery.getFactory();
			boolean lower = ignoreCase && type.sqlTypes( factory )[i]== Types.VARCHAR;
			if (lower) {
				fragment.append( factory.getDialect().getLowercaseFunction() )
						.append('(');
			}

			// org.hibernate.criterion.Order.toSqlString has this:
			//fragment.append( columns[i] );

			// which we replace with:
			fragment.append(castToSqlString(columns[i], type, criteriaQuery));

			if (lower) fragment.append(')');
			fragment.append( ascending ? " asc" : " desc" );
			if ( i<columns.length-1 ) fragment.append(", ");
		}
		return fragment.toString();

	}

	/**
	 * Outputs a cast of the given property name (mapped to its actual column"
	 * + " name in the given criteria context) as raw SQL. Mostly a copy/paste
	 * from {@link MultipleCountProjection}.
	 *
	 * @param column
	 * @param columnType
	 * @param criteriaQuery
	 * @return
	 */
	private String castToSqlString(String column, Type columnType, CriteriaQuery criteriaQuery) {
		return getFunction("cast", criteriaQuery).render(columnType,
				Lists.newArrayList(column, "string"),
				criteriaQuery.getFactory());
	}

	/**
	 * Looks up a named {@link SQLFunction} in the current criteria context,
	 * failing if such a function cannot be found. Copy/paste from
	 * <code>org.hibernate.criterion.AggregateProjection</code>.
	 *
	 * @param functionName
	 * @param criteriaQuery
	 * @return
	 * @throws HibernateException if the function does not exist
	 */
	protected SQLFunction getFunction(String functionName, CriteriaQuery criteriaQuery)
	throws HibernateException {
		SQLFunction function = criteriaQuery.getFactory()
				.getSqlFunctionRegistry()
				.findSQLFunction( functionName );
		if ( function == null ) {
			throw new HibernateException( "Unable to locate mapping for function named [" + functionName + "]" );
		}
		return function;
	}
}
