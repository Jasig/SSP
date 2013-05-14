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

import java.util.TimeZone;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;

/**
 * Holds configuration of global-default <code>TimeZone/s</code> for JDBC
 * types. We need this configuration to be global for date handling in
 * particular so that JSON de/serialization always agrees with database
 * de/serialization. E.g. if you decode a JSON date (no time component) in UTC
 * then write to the database in America/Phoenix, you'll lose a calendar day.
 *
 * <p>Note that changing zone configuration at runtime may change date/time
 * de/serialization behavior immediately, but existing data will not undergo any
 * sort of automatic migration. I.e. this configuration is intended to support
 * values which may vary from installation to installation but which are
 * effectively constant in a given installation. So set this config once,
 * at application deployment, and let Spring apply that configuration at
 * application startup, but leave it alone thereafter.</p>
 *
 * <p>Only supports one zone at this writing, but structure implies it
 * could support several in the future. Previous revs had allowed deployers
 * to configure separate zones for timestamps vs dates, but this was
 * backed out as likely to cause more confusion than it solved. But we would
 * not be surprised if we needed to add something like that back in the future,
 * e.g. to support certain types of calculations against date-only
 * types, e.g. isToday()-style ops.</p>
 */
public class SspTimeZones implements InitializingBean {

	public static final String DEFAULT_DB_TIME_ZONE_ID = "UTC";

	/**
	 * Although usually instantiated and injected as a Spring bean, the
	 * current config sometimes needs to be accessed statically so we allow
	 * callers to register a particular instance here for that purpose. E.g.
	 * Jackson JSON de/serialization needs access to current configuration via
	 * this field.
	 *
	 * <p>Make sure this field is properly initialized prior to serving
	 * any JSON API calls and/or executing any Hibernate database interactions.
	 * This will be taken care of for you in a properly configured
	 * Spring-wired application. See {@link #afterPropertiesSet()}. The
	 * defaulting here is just a weak convenience, mainly for testing, or
	 * for a system where the defaults (UTC for both types) really do work
	 * just fine.</p>
	 */
	public static volatile SspTimeZones INSTANCE = new SspTimeZones();

	@Value("#{configProperties.db_time_zone}")
	private String dbTimeZoneId = DEFAULT_DB_TIME_ZONE_ID;

	private TimeZone dbTimeZone = TimeZone.getTimeZone(dbTimeZoneId);

	/**
	 * Replace the current global configuration, if any, with this instance.
	 * Note that while this may take effect immediately w/r/t date/time
	 * de/serialization behavior, a change has *no* effect on existing data. So
	 * changing this after startup usually isn't what you want. Usually you'll
	 * just set once and at application startup (usually via Spring and
	 * {@link #afterPropertiesSet()}) and never change the actual config
	 * values again once the app is deployed.
	 */
	public void registerAsGlobal() {
		SspTimeZones.INSTANCE = this;
	}

	/**
	 * Ensures configured IDs are resolved to <code>TimeZones</code> and
	 * registers this instance as the global configuration.
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		initTimeZones();
		registerAsGlobal();
	}

	private void initTimeZones() {
		setDbTimeZoneId(dbTimeZoneId);
	}

	public String getDbTimeZoneId() {
		return dbTimeZoneId;
	}

	public void setDbTimeZoneId(String dbTimeZoneId) {
		this.dbTimeZoneId = dbTimeZoneId;
		dbTimeZone = TimeZone.getTimeZone(this.dbTimeZoneId);
	}

	public TimeZone getDbTimeZone() {
		return this.dbTimeZone;
	}

}
