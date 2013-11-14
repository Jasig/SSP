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
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import com.jayway.restassured.RestAssured;
import org.apache.commons.validator.UrlValidator;
import org.jasig.ssp.security.ApiAuthentication;
import org.jasig.ssp.security.PortalLoginService;
import org.jasig.ssp.security.PortalLoginServiceImpl;
import org.json.simple.JSONObject;
import org.junit.Rule;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Before;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;


public abstract class AbstractBaseIntegrationTest {

    private static final String[] HTTP_METHODS = { "GET","POST", "PUT", "DELETE", "HEAD", "OPTIONS" };
    private static final String BASE_PATH = "/ssp/api/1";
    private static final JSONObject CREATED_MODIFIED_BY_DEFAULT = new JSONObject();
    private static final JSONObject CREATED_MODIFIED_BY_CURRENT = new JSONObject();
    private static final Long NORMALIZED_JSON_DATE = new Long("1356998400000");

    private static String baseURI = "http://localhost";
    private static int port = 8080;
    private static Boolean propertiesLoaded = false;
    private static int userLoggedIn = 0;
    private static PortalLoginService portalLoginService = new PortalLoginServiceImpl( baseURI,  port );

    private static final Logger LOGGER = LoggerFactory
            .getLogger(AbstractBaseIntegrationTest.class);


    public static final Long getDefaultCreatedModifiedByDate() {
        return NORMALIZED_JSON_DATE;
    }

    /**
     * Returns the default created by or modified by object used  in the SSP API Test data.
     * @return
     */
    public static final JSONObject getDefaultCreatedModifiedBy() {

        if ( CREATED_MODIFIED_BY_DEFAULT.isEmpty() || CREATED_MODIFIED_BY_DEFAULT == null ) {
            CREATED_MODIFIED_BY_DEFAULT.put("id", "58ba5ee3-734e-4ae9-b9c5-943774b4de41");
            CREATED_MODIFIED_BY_DEFAULT.put("firstName", "System");
            CREATED_MODIFIED_BY_DEFAULT.put("lastName", "Administrator");
        }

        return CREATED_MODIFIED_BY_DEFAULT;
    }


    /**
     * Returns the currently logged in user's created modified by object use for modifying data in the SSP API Tests
     * @return
     */
    public final JSONObject getCurrentLoginCreatedModifiedBy() {

        CREATED_MODIFIED_BY_CURRENT.put("id", portalLoginService.getCurrentlyLoggedInUserInfo().get("id"));
        CREATED_MODIFIED_BY_CURRENT.put("firstName", portalLoginService.getCurrentlyLoggedInUserInfo().get("firstName"));
        CREATED_MODIFIED_BY_CURRENT.put("lastName", portalLoginService.getCurrentlyLoggedInUserInfo().get("lastName"));

        return CREATED_MODIFIED_BY_CURRENT;
    }


    @Rule
    public TestRule watcher = new TestWatcher() {
        @Override
        protected void starting( Description description ) {

            for ( Annotation index : description.getAnnotations() ) {

                if ( index.annotationType().equals(ApiAuthentication.class) ) {

                    if ( ((ApiAuthentication) index).mode().equals("auth")  ) {

                        if ( userLoggedIn == 0 ) {
                            org.junit.Assume.assumeTrue(portalLoginService.login());
                            userLoggedIn = 1;
                        }
                    } else {
                        if ( userLoggedIn != 0 ) {
                            org.junit.Assume.assumeTrue(portalLoginService.logout());
                            userLoggedIn = 0;
                        }
                    }
                }
            }
        }
    };


    private static final void loadProperties() {

        try {
            String inputBaseURL = System.getProperty("apiURL");
            String inputPort = System.getProperty("apiPORT");

            if ( org.apache.commons.lang.StringUtils.isNotBlank(inputBaseURL) ) {
                baseURI = inputBaseURL;

                try {
                    new URL(null, baseURI);
                } catch ( Exception e ) {
                    LOGGER.warn("Error validating URL (" + baseURI + "). Reason: ["
                            + e.getMessage() + "]. Errors may be encountered.");
                }
            }
            if ( org.apache.commons.lang.StringUtils.isNotBlank(inputPort) ) {
                port = Integer.parseInt(inputPort);
            }
        }
        catch (NumberFormatException n) {
            LOGGER.error("Error loading port, not a number! Using default: " + port +".");
        }
        finally {
            propertiesLoaded = true;
        }

        LOGGER.info("Base URI = " + baseURI);
        LOGGER.info("Port = " + port +"\n");
        LOGGER.info("Remember to reset the database between tests whether or not the result was successful!\n");
        LOGGER.info("If errors are encountered, go to the first test that failed. " +
                "Many tests can be failed by upstream failures.\n\n");
    }

    @Before
    public final void setup() {

         if ( !propertiesLoaded ) {

            loadProperties();

            portalLoginService.setBaseURI(baseURI);
            portalLoginService.setPort(port);

            RestAssured.basePath = BASE_PATH;
            RestAssured.baseURI = baseURI;
            RestAssured.port = port;
        }

    }


    /**
     * Tests unsupported HTTP methods. Returns false if a method in the supported list isn't valid.
     * Otherwise, rest-assured will handle test failures.
     * @param supported_HTTP_Methods
     * @param urlToTest
     * @return boolean
     */
    public boolean testUnsupportedMethods( final String[] supported_HTTP_Methods, final String urlToTest ) {

        boolean testResult = true;
        ArrayList<String> methodsToTest = new ArrayList<String>();
        List<String> methodsNotToTest = new ArrayList<String>();

        Collections.addAll(methodsNotToTest, supported_HTTP_Methods);

        for ( final String indexAllMethods : HTTP_METHODS ) {
           if ( !methodsNotToTest.contains(indexAllMethods) ) {
               methodsToTest.add(indexAllMethods);
           }
        }

        for ( final String index : methodsToTest ) {

            if ( index.equals("GET") ) {
                expect()
                    .statusCode(405)
                .when()
                    .get(urlToTest);

            } else if ( index.equals("PUT") ) {
                expect()
                    .statusCode(405)
                .when()
                    .put(urlToTest);

            } else if ( index.equals("POST") ) {
                expect()
                    .statusCode(405)
                .when()
                    .post(urlToTest);

            } else if ( index.equals("DELETE")) {
                expect()
                    .statusCode(405)
                .when()
                    .delete(urlToTest);

            } else if ( index.equals("HEAD") ) {
                expect()
                    .statusCode(405)
                .when()
                    .head(urlToTest);

            } else if ( index.equals("OPTIONS") ) {
                expect()
                    .statusCode(200)
                .when()
                    .options(urlToTest);

            } else {
                testResult = false;
            }
        }

        return testResult;
    }


    /**
     * Tests the response body returned on a GET all method to the url specified.
     * @param urlToTest
     * @param jsonResponseExpected
     */
    public void testResponseBody( final String urlToTest, final JSONObject jsonResponseExpected ) {

        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", equalTo(jsonResponseExpected))
        .when()
            .get(urlToTest);

    }


    /**
     * Tests the response body returned on a single item GET /id method to the url specified
     * Note: JSON object expected must have item "id" defined
     * @param urlToTest
     * @param singleJSONResponseExpected
     */
    public void testSingleItemResponseBody( final String urlToTest, final JSONObject singleJSONResponseExpected ) {

        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", equalTo(singleJSONResponseExpected))
        .when()
            .get(urlToTest + "/" +singleJSONResponseExpected.get("id"));

    }

}
