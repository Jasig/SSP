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

import java.io.IOException;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.type.BasicType;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;

public class TypeRegistryOverridingLocalSessionFactoryBean extends LocalSessionFactoryBean {

	private List<BasicType> basicTypeOverrides;

	@Override
	public void afterPropertiesSet() throws IOException {
		super.afterPropertiesSet();
	}

	@Override
	protected SessionFactory buildSessionFactory(LocalSessionFactoryBuilder sfb) {
		overrideBasicTypes(sfb);
		return super.buildSessionFactory(sfb);
	}

	protected void overrideBasicTypes(LocalSessionFactoryBuilder sfb) {
		if ( basicTypeOverrides == null || basicTypeOverrides.isEmpty() ) {
			return;
		}
		for ( BasicType basicType : basicTypeOverrides ) {
			sfb.registerTypeOverride(basicType);
		}
	}

	public void setBasicTypeOverrides(List<BasicType> basicTypeOverrides) {
		this.basicTypeOverrides = basicTypeOverrides;
	}

}
