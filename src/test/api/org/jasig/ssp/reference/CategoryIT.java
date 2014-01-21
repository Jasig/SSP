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


import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class CategoryIT extends AbstractReferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryIT.class);


    private static final String CATEGORY_CHALLENGE_ID = "0a640a2a-409d-1271-8140-d0c5b90a0105";
    private static final String CATEGORY_PATH = REFERENCE_PATH + "category";
    private static final String CATEGORY_CHALLENGE_PATH = CATEGORY_PATH + "/" + CATEGORY_CHALLENGE_ID + "/challenge";

    private static final JSONObject CATEGORY_DIST;
    private static final JSONObject CATEGORY_NORTH;
    private static final JSONObject CATEGORY_WEST;
    private static final JSONArray CATEGORY_ROWS;
    private static final JSONObject CATEGORY_RESPONSE;

    private static final JSONObject CATEGORY_CHALLENGE_REFERRAL;
    private static final JSONObject CATEGORY_CHALLENGE;
    private static final JSONObject CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE;
    private static final JSONArray CATEGORY_CHALLENGE_CHALLENGE_REFERRALS;
    private static final JSONArray CATEGORY_CHALLENGE_ROWS;
    private static final JSONObject CATEGORY_CHALLENGE_RESPONSE;


    static {

        CATEGORY_DIST = new JSONObject();
        CATEGORY_DIST.put("id", "0a640a2a-409d-1271-8140-d0c4f7c50103");
        CATEGORY_DIST.put("createdDate", getDefaultCreatedModifiedByDate());
        CATEGORY_DIST.put("createdBy", getDefaultCreatedModifiedBy());
        CATEGORY_DIST.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CATEGORY_DIST.put("modifiedBy", getDefaultCreatedModifiedBy());
        CATEGORY_DIST.put("objectStatus", "ACTIVE");
        CATEGORY_DIST.put("name", "Distance Learning");
        CATEGORY_DIST.put("description", "Challenges specific to Distance Learning");

        CATEGORY_NORTH = new JSONObject();
        CATEGORY_NORTH.put("id", "0a640a2a-409d-1271-8140-d0c598ee0104");
        CATEGORY_NORTH.put("createdDate", getDefaultCreatedModifiedByDate());
        CATEGORY_NORTH.put("createdBy", getDefaultCreatedModifiedBy());
        CATEGORY_NORTH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CATEGORY_NORTH.put("modifiedBy", getDefaultCreatedModifiedBy());
        CATEGORY_NORTH.put("objectStatus", "ACTIVE");
        CATEGORY_NORTH.put("name", "North Campus");
        CATEGORY_NORTH.put("description", "North Campus");

        CATEGORY_WEST = new JSONObject();
        CATEGORY_WEST.put("id", "0a640a2a-409d-1271-8140-d0c5b90a0105");
        CATEGORY_WEST.put("createdDate", getDefaultCreatedModifiedByDate());
        CATEGORY_WEST.put("createdBy", getDefaultCreatedModifiedBy());
        CATEGORY_WEST.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CATEGORY_WEST.put("modifiedBy", getDefaultCreatedModifiedBy());
        CATEGORY_WEST.put("objectStatus", "ACTIVE");
        CATEGORY_WEST.put("name", "West Campus");
        CATEGORY_WEST.put("description", "West Campus");


        CATEGORY_ROWS = new JSONArray();
        CATEGORY_ROWS.add(CATEGORY_DIST);
        CATEGORY_ROWS.add(CATEGORY_NORTH);
        CATEGORY_ROWS.add(CATEGORY_WEST);


        CATEGORY_RESPONSE = new JSONObject();
        CATEGORY_RESPONSE.put("success", "true");
        CATEGORY_RESPONSE.put("message", "");
        CATEGORY_RESPONSE.put("results", CATEGORY_ROWS.size());
        CATEGORY_RESPONSE.put("rows", CATEGORY_ROWS);



        CATEGORY_CHALLENGE_REFERRAL = new JSONObject();
        CATEGORY_CHALLENGE_REFERRAL.put("id", "eba6b6c1-7d62-4b3d-8f61-1722ce93418b");
        CATEGORY_CHALLENGE_REFERRAL.put("createdDate", getDefaultCreatedModifiedByDate());
        CATEGORY_CHALLENGE_REFERRAL.put("createdBy", getDefaultCreatedModifiedBy());
        CATEGORY_CHALLENGE_REFERRAL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CATEGORY_CHALLENGE_REFERRAL.put("modifiedBy", getDefaultCreatedModifiedBy());
        CATEGORY_CHALLENGE_REFERRAL.put("objectStatus", "ACTIVE");
        CATEGORY_CHALLENGE_REFERRAL.put("name", "Career Coach");
        CATEGORY_CHALLENGE_REFERRAL.put("description", "Career Coaches are available to assist you in planning out" +
                " how your education will lead to the career you deserve.");
        CATEGORY_CHALLENGE_REFERRAL.put("publicDescription", "Career Coaches are available to assist you in planning " +
                "out how your education will lead to the career you deserve.");
        CATEGORY_CHALLENGE_REFERRAL.put("showInSelfHelpGuide", false);
        CATEGORY_CHALLENGE_REFERRAL.put("showInStudentIntake", null);
        CATEGORY_CHALLENGE_REFERRAL.put("link", "");

        CATEGORY_CHALLENGE_CHALLENGE_REFERRALS = new JSONArray();
        CATEGORY_CHALLENGE_CHALLENGE_REFERRALS.add(CATEGORY_CHALLENGE_REFERRAL);

        CATEGORY_CHALLENGE = new JSONObject();
        CATEGORY_CHALLENGE.put("id", "f6bb0a62-1756-4ea2-857d-5821ee44a1da");
        CATEGORY_CHALLENGE.put("createdDate", getDefaultCreatedModifiedByDate());
        CATEGORY_CHALLENGE.put("createdBy", getDefaultCreatedModifiedBy());
        CATEGORY_CHALLENGE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CATEGORY_CHALLENGE.put("modifiedBy", getDefaultCreatedModifiedBy());
        CATEGORY_CHALLENGE.put("objectStatus", "ACTIVE");
        CATEGORY_CHALLENGE.put("name", "Other");
        CATEGORY_CHALLENGE.put("description", "Other, unspecified challenge.");
        CATEGORY_CHALLENGE.put("selfHelpGuideDescription", "Test self-help guide description.");
        CATEGORY_CHALLENGE.put("selfHelpGuideQuestion", "Test self-help guide question?");
        CATEGORY_CHALLENGE.put("selfHelpGuideQuestions", new JSONArray());
        CATEGORY_CHALLENGE.put("showInStudentIntake", true);
        CATEGORY_CHALLENGE.put("showInSelfHelpSearch", true);
        CATEGORY_CHALLENGE.put("tags", null);
        CATEGORY_CHALLENGE.put("defaultConfidentialityLevelId", null);
        CATEGORY_CHALLENGE.put("challengeChallengeReferrals", CATEGORY_CHALLENGE_CHALLENGE_REFERRALS);
        CATEGORY_CHALLENGE.put("referralCount", CATEGORY_CHALLENGE_CHALLENGE_REFERRALS.size());

        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE = new JSONObject();
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("id", "43719c57-ec92-4e4a-9fb6-25208936fd18");
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("createdDate", getDefaultCreatedModifiedByDate());
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("createdBy", getDefaultCreatedModifiedBy());
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("modifiedBy", getDefaultCreatedModifiedBy());
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("objectStatus", "ACTIVE");
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("name", "Undecided Major or Career Field");
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("description", "Selecting a major or career field is an " +
                "important first step on the path to your future career.  Deciding on a major can provide a goal for " +
                "you to work towards and the purpose behind future career activities and getting a degree.  ");
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("selfHelpGuideDescription", "Selecting a major or career field " +
                "is an important first step on the path to your future career.  Deciding on a major can provide a goal" +
                " for you to work towards and the purpose behind future career activities and getting a degree.  ");
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("selfHelpGuideQuestion", "Do I need assistance in selecting a " +
                "major and/or career path? ");
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("showInStudentIntake", true);
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("showInSelfHelpSearch", true);
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("tags", "undecided, major, career, discover, job, CAP 105, " +
                "resources, student services, interviewing, shadowing, degree field, volunteering");
        CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.put("defaultConfidentialityLevelId", null);

        CATEGORY_CHALLENGE_ROWS = new JSONArray();
        CATEGORY_CHALLENGE_ROWS.add(CATEGORY_CHALLENGE);

        CATEGORY_CHALLENGE_RESPONSE = new JSONObject();
        CATEGORY_CHALLENGE_RESPONSE.put("success", "true");
        CATEGORY_CHALLENGE_RESPONSE.put("message", "");
        CATEGORY_CHALLENGE_RESPONSE.put("results", CATEGORY_CHALLENGE_ROWS.size());
        CATEGORY_CHALLENGE_RESPONSE.put("rows", CATEGORY_CHALLENGE_ROWS);

    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsCategoryReference() {
        final JSONObject testPostPutNegative = (JSONObject) CATEGORY_DIST.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(CATEGORY_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsCategoryReferenceChallenge() {

        //tests permission on get /{id}/challenge method
        expect()
            .statusCode(403)
        .when()
            .get(CATEGORY_CHALLENGE_PATH);

        //tests permission on post /{id}/challenge method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(UUID.fromString(CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.get("id").toString()))
        .when()
            .post(CATEGORY_CHALLENGE_PATH);

        //tests permission on delete /{id}/challenge method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(UUID.fromString(CATEGORY_CHALLENGE.get("id").toString()))
        .when()
            .delete(CATEGORY_CHALLENGE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCategoryReferenceAllBody() {

        testResponseBody(CATEGORY_PATH, CATEGORY_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCategoryReferenceSingleItemBody() {

        testSingleItemResponseBody( CATEGORY_PATH, CATEGORY_NORTH );
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCategoryReferenceSingleItemBodyChallenge() {

        //tests get /{id}/challenge method
        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", equalTo(CATEGORY_CHALLENGE_RESPONSE))
        .when()
            .get(CATEGORY_CHALLENGE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsCategoryReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CATEGORY_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsCategoryReferenceChallengeDetail() {

        testUnsupportedMethods(new String[]{"GET", "POST", "DELETE"}, CATEGORY_CHALLENGE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCategoryReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) CATEGORY_WEST.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(CATEGORY_PATH, CATEGORY_DIST.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCategoryReferenceSupportedMethodsPositiveChallenge() {

        int checkResultCount = 1;

        //get /id/challenge
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CATEGORY_CHALLENGE_PATH);

        //post /id/challenge
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body( UUID.fromString(CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.get("id").toString()) )
        .when()
            .post(CATEGORY_CHALLENGE_PATH);

        //verify post worked      /challenge
        List postResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo( checkResultCount+1 ))
        .when()
            .get(CATEGORY_CHALLENGE_PATH).getBody().jsonPath().getList("rows");

        assertThat( CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.get("id").toString(),
                anyOf(equalTo(((HashMap) postResponse.get(0)).get("id")),
                       equalTo(((HashMap) postResponse.get(1)).get("id"))) );

        //delete   /challenge
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body( UUID.fromString(CATEGORY_CHALLENGE_FULL_FOR_POST_RESPONSE.get("id").toString()) )
        .when()
            .delete(CATEGORY_CHALLENGE_PATH);

        //get (verify result # matches)  /challenge
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
            .get(CATEGORY_CHALLENGE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCategoryReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) CATEGORY_NORTH.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = (JSONObject) CATEGORY_WEST;

        referenceNegativeSupportedMethodTest(CATEGORY_PATH, testNegativePostObject, testNegativeValidateObject);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCategoryReferenceSupportedMethodsNegativeChallenge() {

        final String nonExistentUUID = "70b982b0-68d7-11e3-949a-0800200c9a66";
        final UUID testPostInvalid = UUID.fromString(nonExistentUUID);
        final JSONObject testGetInvalid = new JSONObject();
        int checkResultCount = 0;

        //get /challenge
        Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CATEGORY_CHALLENGE_PATH);

        String result = checkItemCount.getBody().jsonPath().getJsonObject("results").toString();

        if ( StringUtils.isNotBlank(result) ) {
            checkResultCount = Integer.parseInt(result);
        } else {
            LOGGER.error("Get all method failed in Negative Test! No results returned.");
            fail("GET all failed Negative Tests.");
        }

        testGetInvalid.put("success", "true");
        testGetInvalid.put("message", "");
        testGetInvalid.put("results", 0);
        testGetInvalid.put("rows", new JSONArray());

        //get invalid id   /challengeCategoryDetail
        expect()
            .statusCode(404)
            .contentType("application/json")
        .when()
            .get(CATEGORY_PATH + "/" + nonExistentUUID);

        //post unassigned uuid name /challenge
        expect()
            .statusCode(404)
        .given()
            .contentType("application/json")
            .body(testPostInvalid)
        .when()
            .post(CATEGORY_CHALLENGE_PATH);


        //delete  /challenge
        expect()
            .statusCode(404)
        .given()
            .contentType("application/json")
            .body(UUID.fromString(nonExistentUUID))
        .when()
            .delete(CATEGORY_CHALLENGE_PATH);

        //get all (verify result # is unchanged)    /challenge
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
            .get(CATEGORY_CHALLENGE_PATH);
    }
}
