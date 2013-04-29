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


import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.TimeZone;

import org.hibernate.type.descriptor.ValueBinder;
import org.hibernate.type.descriptor.ValueExtractor;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaTypeDescriptor;
import org.hibernate.type.descriptor.sql.BasicBinder;
import org.hibernate.type.descriptor.sql.BasicExtractor;
import org.hibernate.type.descriptor.sql.DateTypeDescriptor;
import org.jasig.ssp.util.SspTimeZones;

/**
 * Same as {@link org.hibernate.type.descriptor.sql.DateTypeDescriptor} but
 * always specifies a <code>java.util.Calendar</code> when reading and writing
 * JDBC <code>Dates</code>. This allows us to configure the
 * <code>TimeZone</code> in which those <code>Dates</code> are interpreted.
 * We do so globally in {@link SspTimeZones}.
 *
 * <p>Based on <a href="http://stackoverflow.com/a/3430957">a StackOverflow answer</a>.</p>
 *
 * @see SspTimestampTypeDescriptor
 */
public class SspDateTypeDescriptor extends DateTypeDescriptor {

	public <X> ValueBinder<X> getBinder(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicBinder<X>( javaTypeDescriptor, this ) {
			@Override
			protected void doBind(PreparedStatement st, X value, int index, WrapperOptions options) throws SQLException {
				st.setDate( index, javaTypeDescriptor.unwrap( value, Date.class, options ), Calendar.getInstance(SspTimeZones.INSTANCE.getDbTimeZone()) );
			}
		};
	}

	public <X> ValueExtractor<X> getExtractor(final JavaTypeDescriptor<X> javaTypeDescriptor) {
		return new BasicExtractor<X>( javaTypeDescriptor, this ) {
			@Override
			protected X doExtract(ResultSet rs, String name, WrapperOptions options) throws SQLException {
				return javaTypeDescriptor.wrap( rs.getDate( name, Calendar.getInstance(SspTimeZones.INSTANCE.getDbTimeZone()) ), options );
			}
		};
	}
}
