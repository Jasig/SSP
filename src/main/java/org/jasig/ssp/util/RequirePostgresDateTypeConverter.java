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
package org.jasig.ssp.util;

import liquibase.database.Database;
import liquibase.database.typeconversion.TypeConverter;
import liquibase.exception.CustomPreconditionErrorException;
import liquibase.exception.CustomPreconditionFailedException;
import liquibase.exception.ServiceNotFoundException;
import liquibase.ext.PostgresDateTypeConverter;
import liquibase.precondition.CustomPrecondition;
import liquibase.servicelocator.ServiceLocator;

/**
 * Can be used to gate changesets which would not function properly if the
 * {@link PostgresDateTypeConverter} plugin is not visible to Liquibase.
 */
public class RequirePostgresDateTypeConverter implements CustomPrecondition {
	@Override
	public void check(Database database) throws CustomPreconditionFailedException, CustomPreconditionErrorException {
		try {
			final Class[] impls = ServiceLocator.getInstance().findClasses(TypeConverter.class);
			for ( Class impl : impls ) {
				if ( impl.getName().equals(PostgresDateTypeConverter.class.getName()) ) {
					return;
				}
			}
			throw new CustomPreconditionFailedException("PostgresDateTypeConverter not found");
		} catch ( ServiceNotFoundException e ) {
			throw new CustomPreconditionFailedException("PostgresDateTypeConverter not found", e);
		}
	}
}
