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
package org.jasig.ssp.service.reference.impl;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jasig.ssp.dao.reference.ConfigDao;
import org.jasig.ssp.model.ObjectStatus;
import org.jasig.ssp.model.reference.Config;
import org.jasig.ssp.service.ObjectNotFoundException;
import org.jasig.ssp.service.reference.ConfigException;
import org.jasig.ssp.util.sort.PagingWrapper;
import org.jasig.ssp.util.sort.SortingAndPaging;
import org.jasig.ssp.web.api.validation.ValidationException;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;

/**
 * Test the Config service implementation class
 * 
 * @author daniel.bower
 */
public class ConfigServiceTest {

	private transient ConfigServiceImpl service;

	private transient ConfigDao dao;

	/**
	 * Test setup
	 */
	@Before
	public void setUp() {
		service = new ConfigServiceImpl();
		dao = createMock(ConfigDao.class);

		service.setDao(dao);
	}

	/**
	 * Test the {@link ConfigServiceImpl#getAll(SortingAndPaging)} method.
	 */
	@Test
	public void testGetAll() {
		final List<Config> daoAll = new ArrayList<Config>();
		daoAll.add(new Config());

		expect(dao.getAll(isA(SortingAndPaging.class))).andReturn(
				new PagingWrapper<Config>(daoAll));

		replay(dao);

		final Collection<Config> all = service.getAll(
				new SortingAndPaging(ObjectStatus.ACTIVE)).getRows();
		assertFalse("List should not have been empty.", all.isEmpty());
		verify(dao);
	}

	/**
	 * Test the {@link ConfigServiceImpl#get(UUID)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testGet() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Config daoOne = new Config(id);

		expect(dao.get(id)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Get method should have returned a non-null instance.",
				service.get(id));
		verify(dao);
	}

	/**
	 * Test the {@link ConfigServiceImpl#save(Config)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 * @throws ValidationException
	 *             If there were any validation errors.
	 */
	@Test
	public void testSave() throws ObjectNotFoundException, ValidationException {
		final UUID id = UUID.randomUUID();
		final Config daoOne = new Config(id);

		expect(dao.save(daoOne)).andReturn(daoOne);

		replay(dao);

		assertNotNull("Save method return model should not have been null.",
				service.save(daoOne));
		verify(dao);
	}

	/**
	 * Test the {@link ConfigServiceImpl#delete(UUID)} method.
	 * 
	 * @throws ObjectNotFoundException
	 *             Should not be thrown in this test since it uses mocked
	 *             objects.
	 */
	@Test
	public void testDelete() throws ObjectNotFoundException {
		final UUID id = UUID.randomUUID();
		final Config daoOne = new Config(id);

		expect(dao.get(id)).andReturn(daoOne);
		expect(dao.save(daoOne)).andReturn(daoOne);
		expect(dao.get(id)).andThrow(new ObjectNotFoundException("", "Config"));

		replay(dao);

		service.delete(id);

		try {
			final Config daoTwo = service.get(id);
			assertNull(
					"Recently deleted object should not have been able to be reloaded.",
					daoTwo);
		} catch (final ObjectNotFoundException e) {
			// expected exception
			assertNotNull(
					"Recently deleted object should not have been found when attempting to reload.",
					e);
		}

		verify(dao);
	}

	@Test
	public void testJsonValueDeserialization() {
		final UUID id = UUID.randomUUID();
		final Config daoOne = new Config(id);
		daoOne.setName("foo");
		daoOne.setValue("{\"bar-key\": \"bar-value\", \"baz-key\": \"baz-value\"}");

		expect(dao.getByName("foo")).andReturn(daoOne);

		replay(dao);

		final Map result = service.getObjectByNameOrDefault("foo", Map.class, new HashMap());
		final Map<String, String> expected = Maps.newHashMap();
		expected.put("bar-key", "bar-value");
		expected.put("baz-key", "baz-value");

		assertEquals(expected, result);
	}

	@Test
	public void testJsonValueDeserializationToPojo() {

		final UUID id = UUID.randomUUID();
		final Config daoOne = new Config(id);
		daoOne.setName("foo");
		daoOne.setValue("{\"barKey\": \"bar-value\", \"bazKey\": \"baz-value\"}");

		expect(dao.getByName("foo")).andReturn(daoOne);

		replay(dao);

		final Foo result = service.getObjectByNameOrDefault("foo", Foo.class, new Foo());
		final Foo expected = new Foo();
		expected.setBarKey("bar-value");
		expected.setBazKey("baz-value");

		assertEquals(expected, result);
	}

	@Test
	public void testJsonDefaultValueDeserialization() {
		final UUID id = UUID.randomUUID();
		final Config daoOne = new Config(id);
		daoOne.setName("foo");
		daoOne.setValue(null);
		daoOne.setDefaultValue("{\"bar-key\": \"bar-value\", \"baz-key\": \"baz-value\"}");

		expect(dao.getByName("foo")).andReturn(daoOne);

		replay(dao);

		final Map result = service.getObjectByNameOrDefault("foo", Map.class, new HashMap());
		final Map<String, String> expected = Maps.newHashMap();
		expected.put("bar-key", "bar-value");
		expected.put("baz-key", "baz-value");

		assertEquals(expected, result);
	}

	@Test
	public void testJsonDefaultValueEchoIfNoConfigValuesSet() {
		final UUID id = UUID.randomUUID();
		final Config daoOne = new Config(id);
		daoOne.setName("foo");
		daoOne.setValue(null);
		daoOne.setDefaultValue(null);

		expect(dao.getByName("foo")).andReturn(daoOne);

		replay(dao);

		final Map<String, String> expected = Maps.newHashMap();
		expected.put("bar-key", "bar-value");
		expected.put("baz-key", "baz-value");

		final Map result = service.getObjectByNameOrDefault("foo", Map.class, expected);

		assertEquals(expected, result);
	}

	@Test(expected = ConfigException.class)
	public void testConfigExceptionForMalformedJsonValue() {
		final UUID id = UUID.randomUUID();
		final Config daoOne = new Config(id);
		daoOne.setName("foo");
		daoOne.setValue("malformed json");

		expect(dao.getByName("foo")).andReturn(daoOne);

		replay(dao);

		service.getObjectByNameOrDefault("foo", Map.class, new HashMap());
	}

	static class Foo {
		private String barKey;
		private String bazKey;

		public String getBarKey() {
			return barKey;
		}

		public void setBarKey(String barKey) {
			this.barKey = barKey;
		}

		public String getBazKey() {
			return bazKey;
		}

		public void setBazKey(String bazKey) {
			this.bazKey = bazKey;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Foo)) return false;

			Foo foo = (Foo) o;

			if (barKey != null ? !barKey.equals(foo.barKey) : foo.barKey != null)
				return false;
			if (bazKey != null ? !bazKey.equals(foo.bazKey) : foo.bazKey != null)
				return false;

			return true;
		}

		@Override
		public int hashCode() {
			int result = barKey != null ? barKey.hashCode() : 0;
			result = 31 * result + (bazKey != null ? bazKey.hashCode() : 0);
			return result;
		}
	}
}
