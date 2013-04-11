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

import java.util.Comparator;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.LiteralType;
import org.hibernate.type.TimestampType;
import org.hibernate.type.VersionType;
import org.hibernate.type.descriptor.java.JdbcTimestampTypeDescriptor;
import org.hibernate.type.descriptor.sql.TimestampTypeDescriptor;


public class ConfigurableTimestampType extends AbstractSingleColumnStandardBasicType<Date>
		implements VersionType<Date>, LiteralType<Date> {

	public ConfigurableTimestampType(TimestampTypeDescriptor descriptor) {
		super( descriptor, JdbcTimestampTypeDescriptor.INSTANCE );
	}

	public String getName() {
		return TimestampType.INSTANCE.getName();
	}

	@Override
	public String[] getRegistrationKeys() {
		return TimestampType.INSTANCE.getRegistrationKeys();
	}

	public Date next(Date current, SessionImplementor session) {
		return TimestampType.INSTANCE.next(current, session);
	}

	public Date seed(SessionImplementor session) {
		return TimestampType.INSTANCE.seed(session);
	}

	public Comparator<Date> getComparator() {
		return getJavaTypeDescriptor().getComparator();
	}

	public String objectToSQLString(Date value, Dialect dialect) throws Exception {
		return TimestampType.INSTANCE.objectToSQLString(value, dialect);
	}

	public Date fromStringValue(String xml) throws HibernateException {
		return TimestampType.INSTANCE.fromString(xml);
	}
}
