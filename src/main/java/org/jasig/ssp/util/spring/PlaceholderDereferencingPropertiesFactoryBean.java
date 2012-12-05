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
package org.jasig.ssp.util.spring;


import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * Transforms an injected {@link Properties} instance into a new
 * {@link Properties} instance where all <code>${name}</code>-style placeholders
 * embedded in original property <em>values</em> have been dereferenced.
 *
 * <p>E.g. this:</p>
 *
 * <p><code>foo=bar<br/>
 * baz=${foo}</code></p>
 *
 * <p>Becomes:</p>
 *
 * <p><code>foo=bar<br/>
 * baz=bar</code></p>
 *
 * Useful when you want the property placeholder dereferencing behavior of the
 * classic Spring <code>PropertyPlaceholderConfigurer</code>, but you don't want
 * its <code>BeanFactoryPostProcessor</code> side-effects. E.g. maybe you want
 * to explicitly look up config properties with SpEL expressions embedded in
 * bean defs (as is the case with SSP), so you can't use
 * <code>PropertyPlaceholderConfigurer</code> to load and apply your config,
 * but you also want to avoid a bunch of duplicate values in your config. Go
 * ahead and normalize your properties with <code>${name}</code>-style
 * placeholders, reference those properties files as usual in a
 * a <code>PropertyFactoryBean</code> bean definition, then wrap that definition
 * with a <code>PlaceholderDereferencingPropertiesFactoryBean</code>.
 */
public class PlaceholderDereferencingPropertiesFactoryBean
		implements FactoryBean<Properties> {

	private Properties properties;
	private Properties derefedProperties;

	public PlaceholderDereferencingPropertiesFactoryBean() {}

	public PlaceholderDereferencingPropertiesFactoryBean(Properties properties) {
		this.properties = properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	@Override
	public Properties getObject() throws Exception {
		if ( derefedProperties == null ) {
			derefProperties();
		}
		return derefedProperties;
	}

	private void derefProperties() {
		if ( properties == null ) {
			return;
		}
		this.derefedProperties = new Properties();
		if ( properties.isEmpty() ) {
			return;
		}
		PropertyPlaceholderHelper derefHelper =
				new PropertyPlaceholderHelper("${","}");
		for (Map.Entry<Object,Object> entry: properties.entrySet() ) {
			String keyAsString = (String)entry.getKey();
			String valAsString = (String)entry.getValue();
			derefedProperties.setProperty(keyAsString,
					derefHelper.replacePlaceholders(valAsString, properties));
		}
	}

	@Override
	public Class<?> getObjectType() {
		return Properties.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}
}
