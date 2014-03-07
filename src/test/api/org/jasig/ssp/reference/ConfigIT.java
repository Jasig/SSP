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

package org.jasig.ssp.reference;


import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matchers;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.jasig.ssp.ExpectationUtils.*;
import static org.junit.Assert.fail;


public class ConfigIT extends AbstractReferenceTest {

    private static final String CONFIG_PATH = REFERENCE_PATH + "config";
    private static final JSONArray CONFIG_ROWS;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigIT.class);


    // Slightly different approach here than what you see in other tests since
    // as of late-2.3.0 development we're not going to be concerned with
    // complete validation of list response bodies. It's just too painful
    // to maintain the list/s of all possible values. Still need to have
    // a few expectations set up here, though. But since there are so few
    // relative to what we've maintained in the past, I switched to more
    // explicit object construction and away from the more abstract
    // array-based construction that you'll find in many other tests.
    static {

        CONFIG_ROWS = new JSONArray();

        final JSONObject expectedResult1 = new JSONObject();
        expectedResult1.put("id", "065e9d10-853d-11e3-baa7-0800200c9a66");
        expectedResult1.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult1.put("description", "Setting this value to true allows map template designers to allow access to map template without authentication.");
        expectedResult1.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult1.put("name", "anonymous_map_template_access");
        expectedResult1.put("objectStatus", "ACTIVE");
        expectedResult1.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult1.put("value", "false");
        expectedResult1.put("valueValidation", null);
        expectedResult1.put("sortOrder", 502);
        expectedResult1.put("defaultValue", "false");

        CONFIG_ROWS.add(expectedResult1);

        final JSONObject expectedResult2 = new JSONObject();
        expectedResult2.put("id", "67bd120e-9be1-11e1-ad1f-0026b9e7ff4c");
        expectedResult2.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult2.put("description", "The Title of the application");
        expectedResult2.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult2.put("name", "app_title");
        expectedResult2.put("objectStatus", "ACTIVE");
        expectedResult2.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult2.put("value", "SSP");
        expectedResult2.put("valueValidation", null);
        expectedResult2.put("sortOrder", 100);
        expectedResult2.put("defaultValue", "SSP");

        CONFIG_ROWS.add(expectedResult2);

        final JSONObject expectedResult3 = new JSONObject();
        expectedResult3.put("id", "59dbcf48-9be3-11e1-bded-0026b9e7ff4c");
        expectedResult3.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult3.put("description", "The email address to blind carbon copy on every message");
        expectedResult3.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult3.put("name", "bcc_email_address");
        expectedResult3.put("objectStatus", "ACTIVE");
        expectedResult3.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult3.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult3.put("value", "noone@test.com");
        expectedResult3.put("valueValidation", null);
        expectedResult3.put("sortOrder", 100);
        expectedResult3.put("defaultValue", "noone@test.com");

        CONFIG_ROWS.add(expectedResult3);

        final JSONObject expectedResult4 = new JSONObject();
        expectedResult4.put("id", "96d5b6fe-d771-462c-945a-5353b298a0fe");
        expectedResult4.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult4.put("description", "turns on/off cron job that will drive the calculation");
        expectedResult4.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult4.put("name", "calculate_map_plan_status");
        expectedResult4.put("objectStatus", "ACTIVE");
        expectedResult4.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult4.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult4.put("value", "false");
        expectedResult4.put("valueValidation", null);
        expectedResult4.put("sortOrder", 600);
        expectedResult4.put("defaultValue", "false");

        CONFIG_ROWS.add(expectedResult4);

        final JSONObject expectedResult5 = new JSONObject();
        expectedResult5.put("id", "0ed5d4e3-77cb-11e3-a151-406c8f22c3ce");
        expectedResult5.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult5.put("description", "Minutes of browser inactivity after which the user will be prompted to extend her session. Else the session will automatically end. This is distinct from the server-side session timeout and should generally be some fraction of the latter (1/3 by default).");
        expectedResult5.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult5.put("name", "client_timeout");
        expectedResult5.put("objectStatus", "ACTIVE");
        expectedResult5.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult5.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult5.put("value", "20");
        expectedResult5.put("valueValidation", null);
        expectedResult5.put("sortOrder", 501);
        expectedResult5.put("defaultValue", "20");

        CONFIG_ROWS.add(expectedResult5);

    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsConfigReference() {
        final JSONObject testPostPutNegative = copyOfExpectedObjectAtIndex(0, CONFIG_ROWS);
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(CONFIG_PATH, testPostPutNegative);

        //tests permission on get name method
        expect()
            .statusCode(403)
        .given()
            .queryParam("name", expectedStringFieldValueAtIndex("name", 0, CONFIG_ROWS))
        .when()
            .get(CONFIG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfigReferenceAllBody() {

        ResponseSpecification spec =
                expect()
                    .contentType("application/json")
                    .statusCode(200)
                    .log().ifError()
                    .body("results", equalTo(40))
                    .and()
                    .body("success", equalTo("true"))
                    .and()
                    .body("rows", hasSize(40))
                    .and();

        spec = expectListResponseObjectAtIndex(spec, 0, CONFIG_ROWS);

        // One of the other tests soft-deletes a config record. So we either
        // need a status=ALL or GTE assertion on the spec above, else
        // unpredictable test execution ordering will make this test unstable.
        // Since this particular API doesn't support POST, we know the total
        // record count will never grow, so we go with an exact match on
        // record counts in the assertions, which then requires the status=ALL
        spec.when().get(CONFIG_PATH + "?status=ALL");

    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfigReferenceSingleItemBody() {

        testSingleItemResponseBody(CONFIG_PATH, expectedObjectAtIndex(1, CONFIG_ROWS));

        //get /name
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", Matchers.equalTo(expectedObjectAtIndex(1, CONFIG_ROWS)))
        .given()
            .queryParam("name", expectedStringFieldValueAtIndex("name", 1, CONFIG_ROWS))
        .when()
            .get(CONFIG_PATH);

    }
     

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsConfigReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CONFIG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfigReferenceSupportedMethodsPositive() {
        final JSONObject testPutPositive = copyOfExpectedObjectAtIndex(2, CONFIG_ROWS);
        testPutPositive.put("name", "testPutPositive");

        int checkResultCount = -2;

        //get all (store results to check at the end)
        Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CONFIG_PATH);

        String result = checkItemCount.getBody().jsonPath().getJsonObject("results").toString();

        if ( StringUtils.isNotBlank(result) ) {
            checkResultCount = Integer.parseInt(result);
        } else {
            LOGGER.error("Get all method failed at beginning of Positive Test! No results returned.");
            fail("GET all failed.");
        }

        //get /id
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CONFIG_PATH + "/" + expectedStringFieldValueAtIndex("id", 1, CONFIG_ROWS));

        //get /name
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
      .given()
            .queryParam("name", expectedStringFieldValueAtIndex("name", 1, CONFIG_ROWS))
        .when()
            .get(CONFIG_PATH);

        final String putUUID = testPutPositive.get("id").toString();
        testPutPositive.remove("id");

        //post
        Response postResponse = expect()
            .statusCode(500)
        .given()
            .contentType("application/json")
            .body(testPutPositive)
        .when()
            .post(CONFIG_PATH);

       //verify post did not set data (see ConfigController)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("")
        .given()
            .queryParam("name", testPutPositive.get("name"))
        .when()
            .get(CONFIG_PATH);

        //put
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(testPutPositive)
        .when()
            .put(CONFIG_PATH + "/" + putUUID);

        //get more complete data from put using get
        final Response putResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CONFIG_PATH + "/" + putUUID);

        final Map parsedPutResponse = putResponse.getBody().jsonPath().getJsonObject("");
        testPutPositive.put("id", putUUID);
        testPutPositive.put("modifiedBy", getCurrentLoginCreatedModifiedBy());
        testPutPositive.put("modifiedDate", parsedPutResponse.get("modifiedDate"));

        //verify put worked
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPutPositive))
        .when()
            .get(CONFIG_PATH + "/" + putUUID);

        //delete
        expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .delete(CONFIG_PATH + "/" + putUUID);

        testPutPositive.put("objectStatus", "INACTIVE");

        //get verify delete worked
        final Response deleteCheckResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CONFIG_PATH + "/" + putUUID);

        testPutPositive.put("modifiedDate", deleteCheckResponse.getBody().jsonPath().getJsonObject("modifiedDate"));

        //verify delete is still intact but inactive
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPutPositive))
        .when()
            .get(CONFIG_PATH + "/" + putUUID);

        //get (verify result # matches expected active)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount-1))
        .given()
            .queryParam("status", "ACTIVE")
            .when()
            .get(CONFIG_PATH);

        //get (verify result # matches expected inactive)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(1))
        .given()
            .queryParam("status", "INACTIVE")
        .when()
            .get(CONFIG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfigReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = copyOfExpectedObjectAtIndex(3, CONFIG_ROWS);
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = expectedObjectAtIndex(4, CONFIG_ROWS);

        referenceNegativeSupportedMethodTest(CONFIG_PATH, testNegativePostObject,
                testNegativeValidateObject);

        //get invalid name
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("")
        .given()
            .queryParam("name", "null")
        .when()
            .get(CONFIG_PATH);
    }
}

