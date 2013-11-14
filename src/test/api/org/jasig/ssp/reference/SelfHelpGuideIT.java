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
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;


public class SelfHelpGuideIT extends AbstractReferenceTest {

    private static final String SELF_HELP_GUIDE_PATH = REFERENCE_PATH + "selfHelpGuide";

    private static final JSONObject SELF_HELP_GUIDE_TYCP;
    private static final JSONObject SELF_HELP_GUIDE_SSC;
    private static final JSONObject SELF_HELP_GUIDE_NAS;
    private static final JSONObject SELF_HELP_GUIDE_QSGC;
    private static final JSONObject SELF_HELP_GUIDE_RAI;

    private static final JSONArray SELF_HELP_GUIDE_ROWS;
    private static final JSONObject SELF_HELP_GUIDE_RESPONSE;

    private static final Logger LOGGER = LoggerFactory.getLogger(SelfHelpGuideIT.class);

    static {

        SELF_HELP_GUIDE_TYCP = new JSONObject();
        SELF_HELP_GUIDE_TYCP.put("id", "3a6352c9-e7fe-e555-7f69-0124a5e5fe71");
        SELF_HELP_GUIDE_TYCP.put("createdDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_TYCP.put("createdBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_TYCP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_TYCP.put("modifiedBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_TYCP.put("objectStatus", "ACTIVE");
        SELF_HELP_GUIDE_TYCP.put("name", "Traveling Your Career Path");
        SELF_HELP_GUIDE_TYCP.put("description", "A listing of resources for making the most out of your future career.");

        SELF_HELP_GUIDE_SSC = new JSONObject();
        SELF_HELP_GUIDE_SSC.put("id", "4fd534df-e7fe-e555-7c71-0042593b1990");
        SELF_HELP_GUIDE_SSC.put("createdDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_SSC.put("createdBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_SSC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_SSC.put("modifiedBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_SSC.put("objectStatus", "ACTIVE");
        SELF_HELP_GUIDE_SSC.put("name", "Student Success Course");
        SELF_HELP_GUIDE_SSC.put("description", "SCC 101 - Student success course self help guide.");

        SELF_HELP_GUIDE_NAS = new JSONObject();
        SELF_HELP_GUIDE_NAS.put("id", "6dce9c28-e7fe-e555-762e-f82ea0c75580");
        SELF_HELP_GUIDE_NAS.put("createdDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_NAS.put("createdBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_NAS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_NAS.put("modifiedBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_NAS.put("objectStatus", "ACTIVE");
        SELF_HELP_GUIDE_NAS.put("name", "Navigating Academic Success");
        SELF_HELP_GUIDE_NAS.put("description", "Navigating Academic Success - The Next Step");

        SELF_HELP_GUIDE_QSGC = new JSONObject();
        SELF_HELP_GUIDE_QSGC.put("id", "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5");
        SELF_HELP_GUIDE_QSGC.put("createdDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_QSGC.put("createdBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_QSGC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_QSGC.put("modifiedBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_QSGC.put("objectStatus", "ACTIVE");
        SELF_HELP_GUIDE_QSGC.put("name", "A Quick-Start Guide to College");
        SELF_HELP_GUIDE_QSGC.put("description", "A guide for new students immediately after Application.");

        SELF_HELP_GUIDE_RAI = new JSONObject();
        SELF_HELP_GUIDE_RAI.put("id", "894073e7-e7fe-e555-7c05-ff79555478e7");
        SELF_HELP_GUIDE_RAI.put("createdDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_RAI.put("createdBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_RAI.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_RAI.put("modifiedBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_RAI.put("objectStatus", "ACTIVE");
        SELF_HELP_GUIDE_RAI.put("name", "Resources for Academic Intervention");
        SELF_HELP_GUIDE_RAI.put("description", "A guide for students on academic intervention.");


        SELF_HELP_GUIDE_ROWS = new JSONArray();
        SELF_HELP_GUIDE_ROWS.add(SELF_HELP_GUIDE_QSGC);
        SELF_HELP_GUIDE_ROWS.add(SELF_HELP_GUIDE_NAS);
        SELF_HELP_GUIDE_ROWS.add(SELF_HELP_GUIDE_RAI);
        SELF_HELP_GUIDE_ROWS.add(SELF_HELP_GUIDE_SSC);
        SELF_HELP_GUIDE_ROWS.add(SELF_HELP_GUIDE_TYCP);

        SELF_HELP_GUIDE_RESPONSE = new JSONObject();
        SELF_HELP_GUIDE_RESPONSE.put("success", "true");
        SELF_HELP_GUIDE_RESPONSE.put("message", "");
        SELF_HELP_GUIDE_RESPONSE.put("results", SELF_HELP_GUIDE_ROWS.size());
        SELF_HELP_GUIDE_RESPONSE.put("rows", SELF_HELP_GUIDE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsSelfHelpGuideReference() {
        final JSONObject testPostPutNegative = (JSONObject)SELF_HELP_GUIDE_TYCP.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(SELF_HELP_GUIDE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideReferenceAllBody() {

        testResponseBody(SELF_HELP_GUIDE_PATH, SELF_HELP_GUIDE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideReferenceSingleItemBody() {

        testSingleItemResponseBody(SELF_HELP_GUIDE_PATH, SELF_HELP_GUIDE_SSC);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsSelfHelpGuideReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, SELF_HELP_GUIDE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideReferenceSupportedMethodsPositive() {

        final JSONObject testPostPutPositive = (JSONObject) SELF_HELP_GUIDE_SSC.clone();
        testPostPutPositive.put("name", "testPostPositive");

        int checkResultCount = -2;

        //get all (store results to check at the end)
        Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(SELF_HELP_GUIDE_PATH);

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
            .get(SELF_HELP_GUIDE_PATH + "/" + SELF_HELP_GUIDE_QSGC.get("id").toString());

        testPostPutPositive.remove("id");

        final JSONObject testPostPutPositiveFull = (JSONObject) testPostPutPositive.clone();
        testPostPutPositiveFull.put("threshold", 0);
        testPostPutPositiveFull.put("introductoryText", "testIntroText");
        testPostPutPositiveFull.put("summaryText", "testSumText");
        testPostPutPositiveFull.put("summaryTextEarlyAlert", "testEarlyAlertText");
        testPostPutPositiveFull.put("summaryTextThreshold", "testThresholdText");
        testPostPutPositiveFull.put("authenticationRequired", false);
        testPostPutPositiveFull.put("active", true);

        //TODO Note: Uses SelfHelpGuideAdminController for post as SelfHelpGuide reference post was not working,(DB Error returned)
        //post
        Response postResponse = expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(testPostPutPositiveFull)
        .when()
            .post("selfHelpGuides/search");

        final String postContentUUID = postResponse.getBody().jsonPath().getJsonObject("id").toString();

        //get more complete data from post using get (MSSQL version is not reliable without this extra get)
        postResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(SELF_HELP_GUIDE_PATH + "/" + postContentUUID);

        final Map parsedPostResponse = postResponse.getBody().jsonPath().getJsonObject("");

        testPostPutPositive.put("id", postContentUUID);
        testPostPutPositive.put("createdBy", getCurrentLoginCreatedModifiedBy());
        testPostPutPositive.put("modifiedBy", getCurrentLoginCreatedModifiedBy());
        testPostPutPositive.put("createdDate", parsedPostResponse.get("createdDate"));
        testPostPutPositive.put("modifiedDate", parsedPostResponse.get("modifiedDate"));

        //verify post worked
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPostPutPositive))
        .when()
            .get(SELF_HELP_GUIDE_PATH + "/" + postContentUUID);

        testPostPutPositive.remove("id");
        testPostPutPositive.put("name", ("testReferencePut" + testPassDeConflictNumber));

        //put
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(testPostPutPositive)
        .when()
            .put(SELF_HELP_GUIDE_PATH + "/" + postContentUUID);

        //get more complete data from put using get
        final Response putResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(SELF_HELP_GUIDE_PATH + "/" + postContentUUID);

        testPostPutPositive.put("id", postContentUUID);
        testPostPutPositive.put("modifiedDate", putResponse.getBody().jsonPath().getJsonObject("modifiedDate"));

        //verify put worked
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPostPutPositive))
        .when()
            .get(SELF_HELP_GUIDE_PATH + "/" + postContentUUID);

        //delete
        expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .delete(SELF_HELP_GUIDE_PATH + "/" + postContentUUID);

        testPostPutPositive.put("objectStatus", "INACTIVE");

        //get verify delete worked
        final Response deleteCheckResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(SELF_HELP_GUIDE_PATH + "/" + postContentUUID);

        testPostPutPositive.put("modifiedDate", deleteCheckResponse.getBody().jsonPath().getJsonObject("modifiedDate"));

        //verify delete is still intact but inactive
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPostPutPositive))
        .when()
            .get(SELF_HELP_GUIDE_PATH + "/" + postContentUUID);

        //get (verify result # matches expected active)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo( checkResultCount ))
        .given()
            .queryParam("status", "ACTIVE")
        .when()
            .get(SELF_HELP_GUIDE_PATH);

        //get (verify result # matches expected inactive)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(1))
        .given()
            .queryParam("status", "INACTIVE")
        .when()
            .get(SELF_HELP_GUIDE_PATH);

    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) SELF_HELP_GUIDE_RAI.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = SELF_HELP_GUIDE_NAS;

        referenceNegativeSupportedMethodTest(SELF_HELP_GUIDE_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
