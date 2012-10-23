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
package org.jasig.ssp.util.uuid;


import org.hibernate.type.PostgresUUIDType;
import org.hibernate.type.descriptor.sql.SqlTypeDescriptor;
import org.hibernate.type.descriptor.sql.VarcharTypeDescriptor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class UUIDCustomTypeTest {

	private Field sqlDescriptionField;
	private SqlTypeDescriptor origSqlDescription;

	@Before
	public void setUp() throws NoSuchFieldException, IllegalAccessException {
		sqlDescriptionField =
				UUIDCustomType.class.getDeclaredField("sqlDescription");
		sqlDescriptionField.setAccessible(true);
		origSqlDescription = (SqlTypeDescriptor) sqlDescriptionField.get(null);
	}

	@After
	public void tearDown() throws IllegalAccessException {
		// Undo the static field setting that goes on in these tests so
		// as not to accidentally undermine db integration tests
		sqlDescriptionField.set(origSqlDescription, null);
	}

	@Test
	public void testMatchesPostgresDialect() {
		UUIDCustomType.initSettings("org.hibernate.dialect.PostgreSQLDialect");
		UUIDCustomType uuidCustomType = new UUIDCustomType();
		SqlTypeDescriptor sqlTypeDescriptor =
				uuidCustomType.getSqlTypeDescriptor();
		assertEquals(PostgresUUIDType.PostgresUUIDSqlTypeDescriptor.INSTANCE,
				sqlTypeDescriptor);
	}

	@Test
	public void testMatchesStandardSqlServer2000Dialect() {
		UUIDCustomType.initSettings("org.hibernate.dialect.SQLServerDialect");
		expectSqlServerTypeDescriptor();
	}

	@Test
	public void testMatchesStandardSqlServer2005Dialect() {
		UUIDCustomType.initSettings("org.hibernate.dialect.SQLServer2005Dialect");
		expectSqlServerTypeDescriptor();
	}

	@Test
	public void testMatchesStandardSqlServer2008Dialect() {
		UUIDCustomType.initSettings("org.hibernate.dialect.SQLServer2008Dialect");
		expectSqlServerTypeDescriptor();
	}

	@Test
	public void testMatchesExtendedSqlServer2005Dialect() {
		UUIDCustomType.initSettings("org.jasig.ssp.util.hibernate.ExtendedSQLServer2005Dialect");
		expectSqlServerTypeDescriptor();
	}

	@Test
	public void testMatchesExtendedSqlServer2008Dialect() {
		UUIDCustomType.initSettings("org.jasig.ssp.util.hibernate.ExtendedSQLServer2008Dialect");
		expectSqlServerTypeDescriptor();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDoesNotMatchStandardSqlServer2012Dialect() {
		// dialect doesn't actually exist at this writing (Aug 8, 2012), but
		// we know we have problems w/ SQLServer 2012
		UUIDCustomType.initSettings("org.hibernate.dialect.SQLServer2012Dialect");
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testDoesNotMatchNonsenseDialect() {
		UUIDCustomType.initSettings("foo");
	}

	private void expectSqlServerTypeDescriptor() {
		UUIDCustomType uuidCustomType = new UUIDCustomType();
		SqlTypeDescriptor sqlTypeDescriptor =
				uuidCustomType.getSqlTypeDescriptor();
		assertEquals(VarcharTypeDescriptor.INSTANCE,
				sqlTypeDescriptor);
	}

}
