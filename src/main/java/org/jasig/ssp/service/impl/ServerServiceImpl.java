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
package org.jasig.ssp.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.service.ServerService;
import org.jasig.ssp.service.reference.ConfigService;
import org.jasig.ssp.transferobject.jsonserializer.DateOnlyFormatting;
import org.jasig.ssp.util.DateTimeUtils;
import org.jasig.ssp.web.api.ServerController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;

@Service
@Transactional(readOnly = true)
public class ServerServiceImpl implements ServerService {

	public ServerServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	private static final String SSP_ENTRY_PREFIX = "SSP-";
	private static final String SSP_EXTENSION_ENTRY_PREFIX = "SSP-Ext-";
	private static final String SSP_EXTENSION_ENTRY_DELIM = "-";

	private static final String ARTIFACT_ENTRY_NAME = "Artifact";
	private static final String ARTIFACT_VERSION_ENTRY_NAME = "Artifact-Version";
	private static final String BUILD_DATE_ENTRY_NAME = "Build-Date";
	private static final String SCM_REVISION_ENTRY_NAME = "SCM-Revision";
	private static final String NOTE_ENTRY_NAME = "Note";

	private static final String SSP_ARTIFACT_VERSION_ENTRY_NAME = SSP_ENTRY_PREFIX + ARTIFACT_VERSION_ENTRY_NAME;
	private static final String SSP_BUILD_DATE_ENTRY_NAME = SSP_ENTRY_PREFIX + BUILD_DATE_ENTRY_NAME;
	private static final String SSP_SCM_REVISION_ENTRY_NAME = SSP_ENTRY_PREFIX + SCM_REVISION_ENTRY_NAME;

	private static final String NAME_API_FIELD_NAME = "name";
	private static final String ARTIFACT_API_FIELD_NAME = "artifact";
	private static final String ARTIFACT_VERSION_API_FIELD_NAME = "artifactVersion";
	private static final String BUILD_DATE_API_FIELD_NAME = "buildDate";
	private static final String SCM_REVISION_API_FIELD_NAME = "scmRevision";
	private static final String NOTE_API_FIELD_NAME = "note";
	private static final String EXTENSIONS_API_FIELD_NAME = "extensions";

	private static final String SSP_VERSION_PROFILE_NAME = "SSP";

	private static final Map<String,String> ENTRY_NAME_MAPPINGS =
			Collections.unmodifiableMap(new HashMap<String,String>() {{
		put(ARTIFACT_ENTRY_NAME, ARTIFACT_API_FIELD_NAME);
		put(ARTIFACT_VERSION_ENTRY_NAME, ARTIFACT_VERSION_API_FIELD_NAME);
		put(BUILD_DATE_ENTRY_NAME, BUILD_DATE_API_FIELD_NAME);
		put(SCM_REVISION_ENTRY_NAME, SCM_REVISION_API_FIELD_NAME);
		put(NOTE_ENTRY_NAME, NOTE_API_FIELD_NAME);
	}});

	private static final String CLIENT_TIMEOUT_CONFIG_NAME = "client_timeout";

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private ConfigService configService;

	private Map<String,Object> versionProfile;

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ServerController.class);

	public Map<String,Object> getDateTimeProfile() {
		final Date now = new Date();
		final Map<String,Object> profile = new HashMap<String,Object>();
		profile.put("date", DateOnlyFormatting.dateFormatter().format(DateTimeUtils.midnight()));
		profile.put("timestamp", now.getTime());
		return profile;
	}
	
	public Map<String,Object> getVersionProfile() throws IOException {
		maybeCacheVersionProfile();
		return versionProfile;
	}

	// We want this particular config to be available anonymously, and the
	// caller doesn't need all the extra junk that comes with a "normal"
	// config API response, so we introduced this API as a way to bypass
	// both the permisssions and the "junk" on the config API for this
	// one entry in particular

	public Map<String,Object> getClientTimeout() {
		final Map<String,Object> timeout = new HashMap<String,Object>();
		timeout.put("timeoutMins", configService.getByNameExceptionOrDefaultAsInt(CLIENT_TIMEOUT_CONFIG_NAME));
		return timeout;
	}
	
    private synchronized void maybeCacheVersionProfile() throws IOException {
		if ( versionProfile == null ) {
			cacheVersionProfile();
		}
	}

	private void cacheVersionProfile() throws IOException {

		InputStream mfStream = null;
		try {
			mfStream = servletContext.getResourceAsStream("/META-INF/MANIFEST.MF");
			Manifest mf = new Manifest(mfStream);
			final Attributes mainAttributes = mf.getMainAttributes();
			final Map<String,Object> tmpVersionProfile = new HashMap<String, Object>();
			tmpVersionProfile.put(NAME_API_FIELD_NAME, SSP_VERSION_PROFILE_NAME);

			// For SSP itself the entry format is:
			//  SSP-<EntryName>
			// e.g.:
			//  SSP-Artifact-Version
			//
			// For "extensions" the entry format is:
			//  SSP-Ext-<ExtensionName>-<EntryName>
			// e.g.:
			//  SSP-Ext-UPOverlay-Artifact-Version
			//
			// We do not want to accidentally expose any sensitive config
			// placed into the manifest. So we only output values from recognized
			// <EntryName> values.
			Map<String,Object> extensions = null;
			for ( Map.Entry<Object,Object> entry : mainAttributes.entrySet() ) {
				String rawEntryName = entry.getKey().toString();
				if ( rawEntryName.startsWith(SSP_EXTENSION_ENTRY_PREFIX) ) {
					String[] parsedEntryName = rawEntryName.split(SSP_EXTENSION_ENTRY_DELIM);
					if ( parsedEntryName.length < 4 ) {
						continue;
					}
					String unqualifiedEntryName =
							StringUtils.join(parsedEntryName, SSP_EXTENSION_ENTRY_DELIM, 3, parsedEntryName.length);
					if ( !(isWellKnownEntryName(unqualifiedEntryName)) ) {
						continue;
					}
					String extName = parsedEntryName[2];
					if ( extensions == null ) {
						extensions = new HashMap<String,Object>();
					}
					Map<String,Object> thisExtension =
							(Map<String,Object>)extensions.get(extName);
					if ( thisExtension == null ) {
						thisExtension = new HashMap<String,Object>();
						thisExtension.put(NAME_API_FIELD_NAME, extName);
						extensions.put(extName, thisExtension);
					}
					mapWellKnownEntryName(unqualifiedEntryName, (String) entry.getValue(), thisExtension);
				} else if ( rawEntryName.startsWith(SSP_ENTRY_PREFIX) ) {
					String unqualifiedEntryName = rawEntryName.substring(SSP_ENTRY_PREFIX.length());
					if ( isWellKnownEntryName(unqualifiedEntryName) ) {
						mapWellKnownEntryName(unqualifiedEntryName, (String)entry.getValue(), tmpVersionProfile);
					}
				}
			}

			if ( extensions == null ) {
				tmpVersionProfile.put(EXTENSIONS_API_FIELD_NAME, Collections.EMPTY_MAP);
			} else {
				tmpVersionProfile.put(EXTENSIONS_API_FIELD_NAME, Lists.newArrayList(extensions.values()));
			}

			this.versionProfile = tmpVersionProfile; // lets not cache it until we're sure we loaded everything

		} finally {
			if ( mfStream != null ) {
				try {
					mfStream.close();
				} catch ( Exception e ) {}
			}
		}
	}

    private Long convertBuildDate( final String date ) {

        try {
            return Long.parseLong( date.replaceAll("[\\D+]", "") );
        } catch (NullPointerException n) {
            return null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private boolean isWellKnownEntryName(String extEntryName) {
        return ENTRY_NAME_MAPPINGS.containsKey(extEntryName);
    }

    private void mapWellKnownEntryName(String extEntryName,
                                       String value,
                                       Map<String, Object> into) {

        if ( extEntryName.equals(BUILD_DATE_ENTRY_NAME) ) {

            Long valueBuildDateInt = convertBuildDate(value);

            if ( valueBuildDateInt != null ) {
                into.put(ENTRY_NAME_MAPPINGS.get(extEntryName),valueBuildDateInt);
            }

        } else {
            into.put(ENTRY_NAME_MAPPINGS.get(extEntryName), value);
        }

    }

	protected Logger getLogger() {
		return LOGGER;
	}


}
