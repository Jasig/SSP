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
import java.util.UUID;


public class JournalStepIT extends AbstractReferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalStepIT.class);

    private static final String JOURNAL_STEP_PATH = REFERENCE_PATH + "journalStep";

    private static final String[] JOURNAL_STEP_UUIDS;
    private static final String[] JOURNAL_STEP_NAMES;
    private static final String[] JOURNAL_STEP_DESCRIPTIONS;
    private static final JSONArray JOURNAL_STEP_ROWS;
    private static final JSONObject JOURNAL_STEP_RESPONSE;

    private static final JSONObject JOURNAL_STEP_DETAIL_TESTPOST_FOR_TRANS;
    private static final JSONObject JOURNAL_STEP_DETAIL_FOR_TRANS;
    private static final JSONObject JOURNAL_STEP_DETAIL_POST_FOR_TRANS;
    private static final JSONArray JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS;
    private static final JSONObject JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE;

    private static final JSONObject JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS;
    private static final JSONArray JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS;
    private static final JSONObject JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE;

    private static final String JOURNAL_STEP_TRANSITION_ID = "0a640a2a-409d-1271-8140-d11564190124";

    static {

        JOURNAL_STEP_UUIDS = new String[] {
                "0a640a2a-409d-1271-8140-e5bcd62a0229", "0a640a2a-409d-1271-8140-e5b2b4b001f2",
                "0a640a2a-409d-1271-8140-e5b2d9c401f3", "0a640a2a-409d-1271-8140-e5af08d201eb",
                "0a640a2a-409d-1271-8140-e5b189a201ed", "0a640a2a-409d-1271-8140-e5c492a6023b",
                "0a640a2a-409d-1271-8140-e5b4a16501fb", "0a640a2a-409d-1271-8140-e56755e901c1",
                "0a640a2a-409d-1271-8140-e567a09b01c2", "0a640a2a-409d-1271-8140-e5b9ecbf0217",
                "0a640a2a-409d-1271-8140-e5b53e1201fe", "0a640a2a-409d-1271-8140-d11539db0123",
                "0a640a2a-409d-1271-8140-d114218e0122", "0a640a2a-409d-1271-8140-e56562b001be",
                "0a640a2a-409d-1271-8140-e5b1cdb201ee", "0a640a2a-409d-1271-8140-e5631c5401b8",
                "0a640a2a-409d-1271-8140-e56397f001b9", "0a640a2a-409d-1271-8140-e563f24f01ba",
                "0a640a2a-409d-1271-8140-e5b68bd10204", "0a640a2a-409d-1271-8140-e5b62e110202",
                "0a640a2a-409d-1271-8140-d11564190124"
        };

        // Following names modified from originals to ensure consistent alpha sorting across db platforms:
        //    ATD Milestone 1 -> Atd Milestone 1
        //    ATD Milestone 2 -> Atd Milestone 2
        //    Fianancial AID AID -> Financial Aid
        //    FIN AID Probation -> Financial Aid Probation
        //    PTC Session 1 -> Ptc Session 1
        //    PTC Session 2 -> Ptc Session 2
        //    PTC Session 3 -> Ptc Session 3
        JOURNAL_STEP_NAMES = new String[] {
                "Academic Advising", "Atd Milestone 1", "Atd Milestone 2", "BIT Assessment", "Career Assessment",
                "Case Management", "Counseling Process", "DSW Session 1", "DSW Session 2", "Financial Aid",
                "Financial Aid Probation", "Follow-Up", "Intake Session", "Probation Review", "Program Assessment",
                "Ptc Session 1", "Ptc Session 2", "Ptc Session 3", "Scholarship Review", "Student Judicial Process",
                "Transition"
        };

        JOURNAL_STEP_DESCRIPTIONS = new String[] {
                "Academic Advising", "ATD Milestone 1", "ATD Milestone 2",
                "Behavioral Intervention Team Assessment Process", "Career Assessment", "Case Management",
                "Counseling Process", "Displaced Worker Session 1", "Second session for Displaced Workers",
                "Fianancial AID", "FIN AID Probation", "Follow-Up", "Intake Session", "Probation Review Process",
                "Program Assessment", "First Session in the PTC process", "Second Session in the PTC process",
                "Third Session in the PTC Process", "Scholarship Review", "Student Judicial Process", "Transition"
        };

        JOURNAL_STEP_ROWS = new JSONArray();

        for ( int index = 0; index < JOURNAL_STEP_UUIDS.length; index++ ) {

            JSONObject temp = new JSONObject();
            temp.put("id", JOURNAL_STEP_UUIDS[index]);
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("sortOrder", 0);
            temp.put("description", JOURNAL_STEP_DESCRIPTIONS[index]);
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("name", JOURNAL_STEP_NAMES[index]);
            temp.put("objectStatus", "ACTIVE");
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());

            if ( !JOURNAL_STEP_UUIDS[index].equals(JOURNAL_STEP_TRANSITION_ID) ) {
                temp.put("usedForTransition", false);
            } else {
                temp.put("usedForTransition", true);
            }

            JOURNAL_STEP_ROWS.add(temp);
        }

        JOURNAL_STEP_RESPONSE = new JSONObject();
        JOURNAL_STEP_RESPONSE.put("success", "true");
        JOURNAL_STEP_RESPONSE.put("message", "");
        JOURNAL_STEP_RESPONSE.put("results", JOURNAL_STEP_ROWS.size());
        JOURNAL_STEP_RESPONSE.put("rows", JOURNAL_STEP_ROWS);

        JOURNAL_STEP_DETAIL_FOR_TRANS = new JSONObject();
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("id", "0a640a2a-409d-1271-8140-e5a141a901d9");
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("objectStatus", "ACTIVE");
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("name", "Graduation check");
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("description", "Discuss the graduation process and do a graduation check " +
                "with the student");
        JOURNAL_STEP_DETAIL_FOR_TRANS.put("sortOrder", 0);

        JOURNAL_STEP_DETAIL_TESTPOST_FOR_TRANS = new JSONObject();
        JOURNAL_STEP_DETAIL_TESTPOST_FOR_TRANS.put("id", "0a640a2a-409d-1271-8140-e5a8293001e2"); //id of FIN AID JDetail
        JOURNAL_STEP_DETAIL_TESTPOST_FOR_TRANS.put("sortOrder", 5);

        JOURNAL_STEP_DETAIL_POST_FOR_TRANS = new JSONObject();
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("id", "0a640a2a-409d-1271-8140-e5a8293001e2");
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("objectStatus", "ACTIVE");
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("name", "Financial aid applying for appeal ");
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("description", "Worked with the student on the FIN AID appeal process " +
                "and discussed the options they have");
        JOURNAL_STEP_DETAIL_POST_FOR_TRANS.put("sortOrder", 0);

        JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS = new JSONArray();
        JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS.add(JOURNAL_STEP_DETAIL_FOR_TRANS);

        JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE = new JSONObject();
        JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE.put("success", "true");
        JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE.put("message", "");
        JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE.put("results", JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS.size());
        JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE.put("rows", JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS);

        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS = new JSONObject();
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("id", "0a640a2a-409d-1271-8140-e5b8558f0211");
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("objectStatus", "ACTIVE");
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("journalStep",
                JOURNAL_STEP_ROWS.get(JOURNAL_STEP_ROWS.size()-1)); //JStep Transition
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("journalStepDetail", JOURNAL_STEP_DETAIL_FOR_TRANS);
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS.put("sortOrder", 0);


        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS = new JSONArray();
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS.add(JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS);

        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE = new JSONObject();
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE.put("success", "true");
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE.put("message", "");
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE.put("results",
                JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS.size());
        JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE.put("rows",
                JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_ROWS);

    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsJournalStepReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject) JOURNAL_STEP_ROWS.get(0)).clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(JOURNAL_STEP_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsJournalStepReference_JournalStepDetail() {

       final String invalidJournalStepPath = JOURNAL_STEP_PATH + "/" + JOURNAL_STEP_TRANSITION_ID + "/journalStepDetail";

        //tests permission on get /{journalTrackId}/journalStepJournalStepDetail method
        expect()
            .statusCode(403)
        .when()
            .get(JOURNAL_STEP_PATH + "/" + JOURNAL_STEP_TRANSITION_ID + "/journalStepJournalStepDetail");

        //tests permission on get /{id}/journalStepDetail method
        expect()
            .statusCode(403)
        .when()
            .get(invalidJournalStepPath);

        //tests permission on post /{id}/journalStepDetail method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(JOURNAL_STEP_DETAIL_TESTPOST_FOR_TRANS)
        .when()
            .post(invalidJournalStepPath);

        //tests permission on delete /{id}/journalStepDetail method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(UUID.fromString(JOURNAL_STEP_DETAIL_FOR_TRANS.get("id").toString()))
        .when()
            .delete(invalidJournalStepPath);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepReferenceAllBody() {

        testResponseBody(JOURNAL_STEP_PATH, JOURNAL_STEP_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepReferenceSingleItemBody() {

        testSingleItemResponseBody( JOURNAL_STEP_PATH, (JSONObject) JOURNAL_STEP_ROWS.get(1) );
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepReferenceSingleItemBody_JournalStepDetail() {

        //tests get /{journalTrackId}/journalStepJournalStepDetail method
        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", equalTo(JOURNAL_STEP_JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE))
        .when()
            .get(JOURNAL_STEP_PATH + "/" + JOURNAL_STEP_TRANSITION_ID + "/journalStepJournalStepDetail");

        //tests get /{id}/journalStepDetail method
        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", equalTo(JOURNAL_STEP_DETAIL_FOR_TRANS_RESPONSE))
        .when()
            .get(JOURNAL_STEP_PATH + "/" + JOURNAL_STEP_TRANSITION_ID + "/journalStepDetail");

    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsJournalStepReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, JOURNAL_STEP_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsJournalStepReference_JournalStepDetail() {

        testUnsupportedMethods(new String[]{"GET"}, JOURNAL_STEP_PATH + "/" + JOURNAL_STEP_DETAIL_FOR_TRANS.get("id") +
                "/journalStepJournalStepDetail");

        testUnsupportedMethods(new String[]{"GET", "POST", "DELETE"}, JOURNAL_STEP_PATH + "/" +
                JOURNAL_STEP_DETAIL_FOR_TRANS.get("id") + "/journalStepDetail");
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ((JSONObject) JOURNAL_STEP_ROWS.get(2)).clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(JOURNAL_STEP_PATH, JOURNAL_STEP_UUIDS[ 3 ], testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepReferenceSupportedMethodsPositive_JournalStepDetail() {

        final JSONObject testPostJournalStep = (JSONObject) JOURNAL_STEP_DETAIL_POST_FOR_TRANS.clone();
        final JSONArray postRows = new JSONArray();
        final JSONObject postResponse = new JSONObject();
        final String validJournalStepPath = JOURNAL_STEP_PATH + "/" + JOURNAL_STEP_TRANSITION_ID + "/journalStepDetail";
        final String validJournalStepStepPath = JOURNAL_STEP_PATH + "/" + JOURNAL_STEP_TRANSITION_ID +
                "/journalStepJournalStepDetail";
        int checkResultCount = 1;

        //get /id /journalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(validJournalStepPath);


        //get /id /journalStepDetailJournalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(validJournalStepStepPath);

        //post       /journalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(JOURNAL_STEP_DETAIL_TESTPOST_FOR_TRANS)
        .when()
            .post(validJournalStepPath);

        postRows.add(JOURNAL_STEP_DETAIL_FOR_TRANS);
        postRows.add(testPostJournalStep);

        postResponse.put("success", "true");
        postResponse.put("message", "");
        postResponse.put("results", 2);
        postResponse.put("rows", postRows);

        //verify post worked      /journalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(postResponse))
        .when()
            .get(validJournalStepPath);

        //delete   /journalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body( UUID.fromString(JOURNAL_STEP_DETAIL_POST_FOR_TRANS.get("id").toString()) )
        .when()
            .delete(validJournalStepPath);

        //verify delete is inactive   /journalStepDetailJournalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("rows[0].objectStatus", equalTo("INACTIVE"))
        .given()
            .queryParam("status", "INACTIVE")
        .when()
            .get(validJournalStepStepPath);

        //get (verify result # matches)  /journalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
             .get(validJournalStepPath);

        //get (verify result # matches)  /journalStepDetailJournalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
            .get(validJournalStepStepPath);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject) JOURNAL_STEP_ROWS.get(4)).clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = (JSONObject) JOURNAL_STEP_ROWS.get(5);

        referenceNegativeSupportedMethodTest(JOURNAL_STEP_PATH, testNegativePostObject, testNegativeValidateObject);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepReferenceSupportedMethodsNegative_JournalStepDetail() {

        final JSONObject testPostInvalid = (JSONObject) JOURNAL_STEP_DETAIL_TESTPOST_FOR_TRANS.clone();
        final JSONObject testGetInvalid = new JSONObject();
        final String invalidJournalStepPath = JOURNAL_STEP_PATH + "/" + JOURNAL_STEP_TRANSITION_ID + "/journalStepDetail";
        final String nonExistentUUID = "70b982b0-68d7-11e3-949a-0800200c9a66";
        int checkResultCount = 0;

        //get /journalStepDetail
        Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(invalidJournalStepPath);

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

        //get invalid id   /journalStepJournalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testGetInvalid))
        .when()
            .get(JOURNAL_STEP_PATH + "/" + nonExistentUUID +"/journalStepJournalStepDetail");

        //get invalid id   /journalStepDetailJournalStepDetail
        expect()
            .statusCode(404)
            .contentType("application/json")
        .when()
            .get(JOURNAL_STEP_PATH + "/" + nonExistentUUID);

        testPostInvalid.put("id", nonExistentUUID);

        //post unassigned uuid name /journalStepDetail
        expect()
            .statusCode(404)
            .given()
            .contentType("application/json")
            .body(testPostInvalid)
        .when()
            .post(invalidJournalStepPath);


        //delete  /journalStepDetail
        expect()
            .statusCode(500)
            .given()
            .contentType("application/json")
            .body(UUID.fromString(nonExistentUUID))
        .when()
            .delete(invalidJournalStepPath);

        //get all (verify result # is unchanged)    /journalStepDetail
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .body("results", equalTo(checkResultCount))
            .when()
            .get(invalidJournalStepPath);
    }
}
