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
package liquibase.ext;

import liquibase.database.structure.type.DateTimeType;
import liquibase.database.typeconversion.core.Postgres83TypeConverter;
import liquibase.servicelocator.PrioritizedService;

/**
 * Forces the Liquibase 'datetime' type to map to Postgres
 * 'TIMSTAMP WITHOUT TIME ZONE' type, which is what we want to standardize on
 * for timestamp storage. Previously, you had to remember to inject
 * a SQL override rule in your Liquibase scripts.
 */
public class PostgresDateTypeConverter extends Postgres83TypeConverter {

	@Override
	public int getPriority() {
		return super.getPriority()+1;
	}

	@Override
	public DateTimeType getDateTimeType() {
		return new DateTimeType("TIMESTAMP WITHOUT TIME ZONE");
	}
}
