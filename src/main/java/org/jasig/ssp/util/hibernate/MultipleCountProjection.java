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

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.AggregateProjection;
import org.hibernate.criterion.CriteriaQuery;
import org.hibernate.type.LongType;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;
import org.jasig.ssp.util.uuid.UUIDCustomType;

import com.google.common.collect.Lists;

/**
 * Hibernate {@link org.hibernate.criterion.Projection} for counting combinations
 * of possibly distinct column values. This is a workaround for
 * {@link org.hibernate.criterion.Projections#countDistinct(String)}
 * only accepting a single column reference. The original version of this
 * class was found <a href="https://forum.hibernate.org/viewtopic.php?p=2321638#p2321638">here</a>.
 * Customized here to delegate to the current
 * {@link org.hibernate.dialect.Dialect} to use string coercion and
 * concatenation functions appropriate for the current db.
 *
 * <p>The original use case for this was needing to select a count (for
 * pagination) from a subquery where the subquery had an aggregate function
 * over multiple group by columns. I.e.
 * <code>select count(*) from ( select count(foo) from bar group by baz, baz2 )</code>.
 * <a href="http://stackoverflow.com/questions/7406954/count-of-the-result-of-a-hibernate-criteria-group-by-total-grouped-records-ret">This post</a>
 * pointed out this this is conceptually identical to what this class allows
 * you to do, e.g. for the above:</p>
 *
 * <p>
 * <code>query.setProjection(new MultipleCountProjection("baz;baz2").setDistinct()).uniqueResult();</code>
 * </p>
 */
public class MultipleCountProjection extends AggregateProjection {

	private boolean distinct;

	public MultipleCountProjection(String prop) {
		super("count", prop);
	}

	public String toString() {
		if(distinct) {
			return "distinct " + super.toString();
		} else {
			return super.toString();
		}
	}

	public Type[] getTypes(Criteria criteria, CriteriaQuery criteriaQuery)
			throws HibernateException {
		return new Type[] { LongType.INSTANCE };
	}

	public String toSqlString(Criteria criteria, int position, CriteriaQuery criteriaQuery)
			throws HibernateException {
		StringBuffer buf = new StringBuffer();
		buf.append("count(");
		if (distinct) buf.append("distinct ");
		String[] properties = propertyName.split(";");
		//concat(cast(col), cast_col))
		if ( properties.length == 1 ) {
			buf.append(criteriaQuery.getColumn(criteria, properties[0]));
		} else {
			String concatSql = null;
			for (int i = 0; i < properties.length; i++) {
				String asSqlStr = asSqlStr(properties[i], criteria, criteriaQuery);
				if ( concatSql == null ) {
					concatSql = asSqlStr;
					continue;
				}
				concatSql = this.sqlConcatLiterals(concatSql, asSqlStr, criteria, criteriaQuery);
			}
			buf.append(concatSql);
		}
		buf.append(") as y");
		buf.append(position);
		buf.append('_');
		return buf.toString();
	}

	public MultipleCountProjection setDistinct() {
		distinct = true;
		return this;
	}

	/**
	 * Coerce a proerty to a string-ish type using the current
	 * <code>Dialect</code>'s "str" function.
	 *
	 * @param propertyName
	 * @param criteria
	 * @param criteriaQuery
	 * @return
	 */
	private String asSqlStr(String propertyName, Criteria criteria, CriteriaQuery criteriaQuery) {
		return super.getFunction("cast", criteriaQuery).render(
				criteriaQuery.getType(criteria,propertyName),
				Lists.newArrayList((String)buildFunctionParameterList(
						criteriaQuery.getColumn(criteria, propertyName)).get(0),
						"string"),
				criteriaQuery.getFactory());
	}

	/**
	 * Concats literals as opposed to field references. Useful for concating
	 * the SQL output by
	 * {@link #asSqlStr(String, org.hibernate.Criteria, org.hibernate.criterion.CriteriaQuery)}
	 *
	 * @param str1
	 * @param str2
	 * @param criteria
	 * @param criteriaQuery
	 * @return
	 */
	private String sqlConcatLiterals(String str1, String str2, Criteria criteria, CriteriaQuery criteriaQuery) {
		return super.getFunction("concat", criteriaQuery).render(
				StringType.INSTANCE,
				Lists.newArrayList(str1, str2),
				criteriaQuery.getFactory());
	}
}
