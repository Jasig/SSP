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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;


public class SelfHelpGuideQuestionIT extends AbstractReferenceTest {

    private static final String SELF_HELP_GUIDE_QUESTION_PATH = "selfHelpGuides/selfHelpGuideQuestions";

    private static final String[] SELF_HELP_GUIDE_QUESTION_UUIDS;
    private static final int[] SELF_HELP_GUIDE_QUESTION_QUESTION_NUMBERS;
    private static final String[] SELF_HELP_GUIDE_QUESTION_CHALLENGE_ID;
    private static final String[] SELF_HELP_GUIDE_QUESTION_SELF_HELP_GUIDE_ID;
    private static final Map<String,String> CHALLENGE_UUIDS_TO_NAMES;

    private static final JSONArray SELF_HELP_GUIDE_QUESTION_ROWS;
    private static final JSONObject SELF_HELP_GUIDE_QUESTION_RESPONSE;

    private static final Logger LOGGER = LoggerFactory
            .getLogger(SelfHelpGuideQuestionIT.class);

    static {

        SELF_HELP_GUIDE_QUESTION_UUIDS = new String[] {
                "3fb44068-df2b-49bf-b71d-035070435726", "81953c1b-4930-4d62-90da-1fb032be399a",
                "ad78dc04-bb23-4e62-b135-3983cb208ba6", "3c6abe0b-69cc-4555-88c8-380ef129459a",
                "7973e86d-f0a6-4653-8dd2-4365a3861861", "961c2085-e546-408d-8b5f-41911b053b40",
                "ee618726-1bb1-40fc-a696-01ca1fb3d558", "14f7a05a-df89-45f4-a2b0-18c0fe9a53be",
                "209d97cf-2f51-4261-80a5-21db7f0d7aa2", "ddcc44a1-9d1b-4bec-8aab-3ab800b31115",
                "9c2e722e-2eac-4821-993b-3af6cd2d94af", "26523538-3d22-40de-a3a2-4cb7c8be2443",
                "0e46733e-193d-4950-ba29-4cd0f9620561", "850942d7-86d4-470d-8a43-7a23b3be679a",
                "7125d031-a0b9-4de2-9d81-a2663f032471", "7787fab3-d09c-4fcc-ae6a-a26898a23ee4",
                "e5cf1d3f-f044-41db-8b65-c62be7d02f3b", "cf39078c-183e-4e31-a912-7d4ceb59e6ce",
                "db8cdf11-9f1d-4b68-b39f-9d7ae7ce19a0", "9d3cbfec-97ca-492b-a9d0-f4020c84ed3a",
                "f62330bd-0fc3-4ea3-8d77-b777dabe8484", "c5a508fa-bdbc-4286-8999-ac208804d547",
                "bdb02005-ec5e-4957-b485-e988d139eeb3", "a4bf9024-3f11-4bdd-be01-60ec8154fd6e",
                "d549486e-6355-443a-a27c-8e5859a6dd25", "1b417345-aa6a-4859-94ab-9f1913f81872"
        };

        SELF_HELP_GUIDE_QUESTION_QUESTION_NUMBERS = new int[] {
                10, 6, 2, 3, 4, 6, 9, 7, 3, 11, 2, 1, 10, 4, 6, 8, 5, 5, 8, 5, 4, 7, 6, 1, 5, 1
        };

        SELF_HELP_GUIDE_QUESTION_CHALLENGE_ID = new String[] {
                "dbb8741c-ece0-4830-8ebf-774151cb6a1b", "38f7ae25-902f-4381-851e-2e2319adb1bd",
                "cab7d5a5-2ca5-4af7-a644-b3882ddc9b41", "eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3",
                "cab7d5a5-2ca5-4af7-a644-b3882ddc9b41", "f067c6ca-50ad-447a-ad12-f47dffdce42e",
                "431abcf2-43fe-4d6a-8f83-b47c91157a15", "80c5b019-1946-4a98-a7fd-b8d62684558c",
                "cab7d5a5-2ca5-4af7-a644-b3882ddc9b41", "af7e472c-3b7c-4d00-a667-04f52f560940",
                "f067c6ca-50ad-447a-ad12-f47dffdce42e", "07b5c3ac-3bdf-4d12-b65d-94cb55167998",
                "38f7ae25-902f-4381-851e-2e2319adb1bd", "7c0e5b76-9933-484a-b265-58cb280305a5",
                "7ad819ef-d1e3-4ebf-a05b-e233f17f9e55", "eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3",
                "1f5b63a9-9b50-412b-9971-23602f87444c", "f067c6ca-50ad-447a-ad12-f47dffdce42e",
                "07b5c3ac-3bdf-4d12-b65d-94cb55167998", "fb206a68-78db-489d-9d5d-dce554f54eed",
                "eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3", "431abcf2-43fe-4d6a-8f83-b47c91157a15",
                "f067c6ca-50ad-447a-ad12-f47dffdce42e", "80c5b019-1946-4a98-a7fd-b8d62684558c",
                "fb206a68-78db-489d-9d5d-dce554f54eed", "43719c57-ec92-4e4a-9fb6-25208936fd18"
        };

        SELF_HELP_GUIDE_QUESTION_SELF_HELP_GUIDE_ID = new String[] {
                "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5", "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5",
                "6dce9c28-e7fe-e555-762e-f82ea0c75580", "894073e7-e7fe-e555-7c05-ff79555478e7",
                "894073e7-e7fe-e555-7c05-ff79555478e7", "894073e7-e7fe-e555-7c05-ff79555478e7",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "4fd534df-e7fe-e555-7c71-0042593b1990",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "4fd534df-e7fe-e555-7c71-0042593b1990",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "4fd534df-e7fe-e555-7c71-0042593b1990",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "4fd534df-e7fe-e555-7c71-0042593b1990",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "4fd534df-e7fe-e555-7c71-0042593b1990",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5",
                "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5", "6dce9c28-e7fe-e555-762e-f82ea0c75580",
                "6dce9c28-e7fe-e555-762e-f82ea0c75580", "6dce9c28-e7fe-e555-762e-f82ea0c75580",
                "6dce9c28-e7fe-e555-762e-f82ea0c75580", "6dce9c28-e7fe-e555-762e-f82ea0c75580",
                "894073e7-e7fe-e555-7c05-ff79555478e7", "3a6352c9-e7fe-e555-7f69-0124a5e5fe71"
        };

        CHALLENGE_UUIDS_TO_NAMES = new HashMap<String, String>();
        CHALLENGE_UUIDS_TO_NAMES.put("5d6dc03f-b000-42b1-a078-253c55867ff1", "Alcohol and/or Substance Abuse");
        CHALLENGE_UUIDS_TO_NAMES.put("0a640a2a-409d-1271-8140-d0cf543a0106","Books - Educational Resources");
        CHALLENGE_UUIDS_TO_NAMES.put("07b5c3ac-3bdf-4d12-b65d-94cb55167998","Child or Adult Care");
        CHALLENGE_UUIDS_TO_NAMES.put("33c6207a-3302-405f-8a2e-9d9bd750dac0","Classroom Issues");
        CHALLENGE_UUIDS_TO_NAMES.put("f067c6ca-50ad-447a-ad12-f47dffdce42e","Computer/Email");
        CHALLENGE_UUIDS_TO_NAMES.put("cab7d5a5-2ca5-4af7-a644-b3882ddc9b41","Concentration (Poor Concentration)");
        CHALLENGE_UUIDS_TO_NAMES.put("6e4b6a6c-8b67-48df-ac7f-c9f225e872b8","Cultural Awareness or Issues");
        CHALLENGE_UUIDS_TO_NAMES.put("af7e472c-3b7c-4d00-a667-04f52f560940","Emotions, Moods and Stress");
        CHALLENGE_UUIDS_TO_NAMES.put("dbb8741c-ece0-4830-8ebf-774151cb6a1b","English as a Second Language");
        CHALLENGE_UUIDS_TO_NAMES.put("7c0e5b76-9933-484a-b265-58cb280305a5","Finances - Education");
        CHALLENGE_UUIDS_TO_NAMES.put("70693703-b2c1-4d3c-b79f-e43e93393b8c","Finances - Personal");
        CHALLENGE_UUIDS_TO_NAMES.put("1f5b63a9-9b50-412b-9971-23602f87444c","Goals/Career Choices (Unclear)");
        CHALLENGE_UUIDS_TO_NAMES.put("fb206a68-78db-489d-9d5d-dce554f54eed","Grades");
        CHALLENGE_UUIDS_TO_NAMES.put("22d23035-74f0-40f1-ac41-47a22c798af7","Grief and Loss");
        CHALLENGE_UUIDS_TO_NAMES.put("2fd9ab0b-4afb-43d3-8ae2-5cf462c847e5","Housing/Shelter");
        CHALLENGE_UUIDS_TO_NAMES.put("bd886899-96d5-4ec8-9d6c-3cb2d4e0f09b","Legal - Immigration");
        CHALLENGE_UUIDS_TO_NAMES.put("72de7c95-eab3-46b2-93cf-108397befcbb","Maps/Directions");
        CHALLENGE_UUIDS_TO_NAMES.put("7ad819ef-d1e3-4ebf-a05b-e233f17f9e55","Motivation/Attitude");
        CHALLENGE_UUIDS_TO_NAMES.put("f6bb0a62-1756-4ea2-857d-5821ee44a1da","Other");
        CHALLENGE_UUIDS_TO_NAMES.put("609527d4-e768-4caa-a65a-2bb3f3da2948","Physical Health");
        CHALLENGE_UUIDS_TO_NAMES.put("615225df-c3b7-4ae2-a828-6fad663c629b","Relationship Issues");
        CHALLENGE_UUIDS_TO_NAMES.put("c1428288-ec1e-432e-87df-6567d9618f42","Social Support (Lack of Support)");
        CHALLENGE_UUIDS_TO_NAMES.put("6c34e91c-3ef3-4b3f-8061-fab1b7ff59ca","Steps to Begin College");
        CHALLENGE_UUIDS_TO_NAMES.put("80c5b019-1946-4a98-a7fd-b8d62684558c","Study Resources");
        CHALLENGE_UUIDS_TO_NAMES.put("eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3","Test Anxiety");
        CHALLENGE_UUIDS_TO_NAMES.put("431abcf2-43fe-4d6a-8f83-b47c91157a15","Time Management");
        CHALLENGE_UUIDS_TO_NAMES.put("b9ac1cb5-d40a-4451-8ec2-08240698aaf3","Tired/Fatigue");
        CHALLENGE_UUIDS_TO_NAMES.put("38f7ae25-902f-4381-851e-2e2319adb1bd","Transportation");
        CHALLENGE_UUIDS_TO_NAMES.put("43719c57-ec92-4e4a-9fb6-25208936fd18","Undecided Major or Career Field");

        SELF_HELP_GUIDE_QUESTION_ROWS = new JSONArray();

        for ( int index = 0; index < SELF_HELP_GUIDE_QUESTION_UUIDS.length; index++ ) {

            JSONObject temp = new JSONObject();
            temp.put("id", SELF_HELP_GUIDE_QUESTION_UUIDS[index]);
            temp.put("name", CHALLENGE_UUIDS_TO_NAMES.get(SELF_HELP_GUIDE_QUESTION_CHALLENGE_ID[index]));

            if ( index > 5 && index < 17 ) {
                temp.put("description", "");
            } else {
                temp.put("description", null);
            }
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("objectStatus", "ACTIVE");
            temp.put("questionNumber", SELF_HELP_GUIDE_QUESTION_QUESTION_NUMBERS[index]);
            temp.put("mandatory", false);
            temp.put("critical", false);
            temp.put("challengeId", SELF_HELP_GUIDE_QUESTION_CHALLENGE_ID[index]);
            temp.put("selfHelpGuideId", SELF_HELP_GUIDE_QUESTION_SELF_HELP_GUIDE_ID[index]);

            SELF_HELP_GUIDE_QUESTION_ROWS.add(temp);
        }

        SELF_HELP_GUIDE_QUESTION_RESPONSE = new JSONObject();
        SELF_HELP_GUIDE_QUESTION_RESPONSE.put("success", "true");
        SELF_HELP_GUIDE_QUESTION_RESPONSE.put("message", "");
        SELF_HELP_GUIDE_QUESTION_RESPONSE.put("results", SELF_HELP_GUIDE_QUESTION_ROWS.size());
        SELF_HELP_GUIDE_QUESTION_RESPONSE.put("rows", SELF_HELP_GUIDE_QUESTION_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsSelfHelpGuideQuestionDetailReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(0)).clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(SELF_HELP_GUIDE_QUESTION_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideQuestionDetailReferenceAllBody() {

       //Currently this is disabled via a Get controller method override, left it here, in case of future testing/changes
       // testResponseBody(SELF_HELP_GUIDE_QUESTION_PATH, SELF_HELP_GUIDE_QUESTION_RESPONSE);

       //Using alternate test here see comments above
       for ( Object toTest : SELF_HELP_GUIDE_QUESTION_ROWS ) {
           testSingleItemResponseBody( SELF_HELP_GUIDE_QUESTION_PATH, (JSONObject) toTest );
       }
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideQuestionDetailReferenceSingleItemBody() {

        testSingleItemResponseBody(SELF_HELP_GUIDE_QUESTION_PATH, (JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(1));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsSelfHelpGuideQuestionDetailReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, SELF_HELP_GUIDE_QUESTION_PATH + "/" +
                SELF_HELP_GUIDE_QUESTION_UUIDS[3]);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideQuestionDetailReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ((JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(2)).clone();
        testPostPutPositive.put("description", "testPostPositive");

        int checkResultCount = -2;

        //get all (store results to check at the end)
        Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(SELF_HELP_GUIDE_QUESTION_PATH);

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
            .get(SELF_HELP_GUIDE_QUESTION_PATH + "/" + testPostPutPositive.get("id"));

        testPostPutPositive.remove("id");

        //post
        Response postResponse = expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(testPostPutPositive)
        .when()
            .post(SELF_HELP_GUIDE_QUESTION_PATH);

        final String postContentUUID = postResponse.getBody().jsonPath().getJsonObject("id").toString();

        //get more complete data from post using get (MSSQL version is not reliable without this extra get)
        postResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(SELF_HELP_GUIDE_QUESTION_PATH + "/" + postContentUUID);

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
            .get(SELF_HELP_GUIDE_QUESTION_PATH + "/" + postContentUUID);

        testPostPutPositive.remove("id");
        testPostPutPositive.put("description", ("testReferencePut" + testPassDeConflictNumber));

        //put
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(testPostPutPositive)
        .when()
            .put(SELF_HELP_GUIDE_QUESTION_PATH + "/" + postContentUUID);

        //get more complete data from put using get
        final Response putResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(SELF_HELP_GUIDE_QUESTION_PATH + "/" + postContentUUID);

        testPostPutPositive.put("id", postContentUUID);
        testPostPutPositive.put("modifiedDate", putResponse.getBody().jsonPath().getJsonObject("modifiedDate"));

        //verify put worked
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPostPutPositive))
        .when()
            .get(SELF_HELP_GUIDE_QUESTION_PATH + "/" + postContentUUID);

        //delete
        expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .delete(SELF_HELP_GUIDE_QUESTION_PATH + "/" + postContentUUID);

        testPostPutPositive.put("objectStatus", "INACTIVE");

        //get verify delete worked
        final Response deleteCheckResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(SELF_HELP_GUIDE_QUESTION_PATH + "/" + postContentUUID);

        testPostPutPositive.put("modifiedDate", deleteCheckResponse.getBody().jsonPath().getJsonObject("modifiedDate"));

        //verify delete is still intact but inactive
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPostPutPositive))
        .when()
            .get(SELF_HELP_GUIDE_QUESTION_PATH + "/" + postContentUUID);

        //get (verify result # matches expected active)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .given()
            .queryParam("status", "ACTIVE")
        .when()
            .get(SELF_HELP_GUIDE_QUESTION_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideQuestionDetailReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(4)).clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = (JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(5);

        referenceNegativeSupportedMethodTest(SELF_HELP_GUIDE_QUESTION_PATH, testNegativePostObject,
               testNegativeValidateObject);
    }
}