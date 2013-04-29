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

import java.util.Date;

import org.hibernate.dialect.Dialect;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.type.DateType;
import org.hibernate.type.IdentifierType;
import org.hibernate.type.LiteralType;
import org.hibernate.type.descriptor.java.JdbcDateTypeDescriptor;
import org.hibernate.type.descriptor.sql.DateTypeDescriptor;


public class ConfigurableDateType extends AbstractSingleColumnStandardBasicType<Date>
		implements IdentifierType<Date>, LiteralType<Date> {

	public ConfigurableDateType(DateTypeDescriptor descriptor) {
		super( descriptor, JdbcDateTypeDescriptor.INSTANCE );
	}

	public String getName() {
		return DateType.INSTANCE.getName();
	}

	@Override
	public String[] getRegistrationKeys() {
		return DateType.INSTANCE.getRegistrationKeys();
	}

	public String objectToSQLString(Date value, Dialect dialect) throws Exception {
		return DateType.INSTANCE.objectToSQLString(value, dialect);
	}

	public Date stringToObject(String xml) {
		return DateType.INSTANCE.stringToObject(xml);
	}
}
