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

package org.jasig.ssp;


import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import org.jasig.ssp.security.ApiAuthentication;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ServerIT extends AbstractBaseIntegrationTest {

    private static final String SERVER_PATH = "server/";
    private static final String VERSION_PATH = SERVER_PATH + "version";
    private static final String DATETIME_PATH = SERVER_PATH + "datetime";

    private static final Logger LOGGER = LoggerFactory
            .getLogger(ServerIT.class);

    @Test
    @ApiAuthentication(mode="noauth")
    public void testServerVersion() {

        try {
            final Long expectedBuildDate = Long.parseLong(System.getProperty("buildDate").trim());
            final String expectedArtifactVersion = System.getProperty("artifactVersion");
            final String expectedSCMRevision = System.getProperty("scmRevision");

            expect()
                .statusCode(200)
                .body("artifact", equalTo("org.jasig.ssp:ssp"),
                    "artifactVersion", equalTo(expectedArtifactVersion),
                    "name", equalTo("SSP"),
                    "scmRevision", equalTo(expectedSCMRevision),
                    "buildDate", lessThan(expectedBuildDate))
            .when()
                .get(VERSION_PATH);

        } catch ( NullPointerException n ) {
            LOGGER.error("Couldn't find one or more of the following maven properties: " +
                    "buildDate, artifactVersion, scmRevison. \n", n);
        }
    }

    @Test
    @ApiAuthentication(mode="noauth")
    public void testUnsupportedMethodsServer() {
        testUnsupportedMethods( new String[]{"GET"}, VERSION_PATH );
    }

    @Test
    @ApiAuthentication(mode="noauth")
    public void testServerDateTime() {

        final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        expect()
            .statusCode(200)
            .body("timestamp", greaterThanOrEqualTo(new Date().getTime()),
                "date", equalTo(df.format(new Date())))
        .when()
            .get(DATETIME_PATH);

    }

    @Test
    @ApiAuthentication(mode="noauth")
    public void testUnsupportedMethodsDateTime() {
        testUnsupportedMethods( new String[] {"GET"}, DATETIME_PATH );
    }
}