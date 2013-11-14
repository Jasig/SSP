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


import static com.jayway.restassured.RestAssured.*;
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


public class JournalTrackIT extends AbstractReferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JournalTrackIT.class);

    private static final String JOURNAL_TRACK_PATH = REFERENCE_PATH + "journalTrack";

    private static final JSONObject JOURNAL_TRACK_COACH;
    private static final JSONObject JOURNAL_TRACK_DISPLWRKRS;
    private static final JSONObject JOURNAL_TRACK_HON;
    private static final JSONObject JOURNAL_TRACK_AA;
    private static final JSONObject JOURNAL_TRACK_APSAP;
    private static final JSONObject JOURNAL_TRACK_FYA;
    private static final JSONObject JOURNAL_TRACK_STUDJUDC;
    private static final JSONObject JOURNAL_TRACK_BIT;
    private static final JSONObject JOURNAL_TRACK_ATD;
    private static final JSONObject JOURNAL_TRACK_FINCAID;
    private static final JSONObject JOURNAL_TRACK_PATHWAY;
    private static final JSONObject JOURNAL_TRACK_COUNS;
    private static final JSONObject JOURNAL_TRACK_EAL;

    private static final JSONArray JOURNAL_TRACK_ROWS;
    private static final JSONObject JOURNAL_TRACK_RESPONSE;

    private static final JSONObject JOURNAL_STEP_TESTPOST_FOR_EAL;
    private static final JSONObject JOURNAL_STEP_FOR_EAL;
    private static final JSONObject JOURNAL_STEP_POST_FOR_EAL;
    private static final JSONArray JOURNAL_STEP_FOR_EAL_ROWS;
    private static final JSONObject JOURNAL_STEP_FOR_EAL_RESPONSE;

    private static final JSONObject JOURNAL_STEP_TRACK_FOR_EAL;
    private static final JSONArray JOURNAL_STEP_TRACK_FOR_EAL_ROWS;
    private static final JSONObject JOURNAL_STEP_TRACK_FOR_EAL_RESPONSE;

    static {

        JOURNAL_TRACK_COACH = new JSONObject();
        JOURNAL_TRACK_COACH.put("id", "0a640a2a-409d-1271-8140-d10d65990119");
        JOURNAL_TRACK_COACH.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_COACH.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_COACH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_COACH.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_COACH.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_COACH.put("name", "Career Coaching");
        JOURNAL_TRACK_COACH.put("description", "Career Coaching");
        JOURNAL_TRACK_COACH.put("sortOrder", 0);

        JOURNAL_TRACK_DISPLWRKRS = new JSONObject();
        JOURNAL_TRACK_DISPLWRKRS.put("id", "0a640a2a-409d-1271-8140-d10eafbe011a");
        JOURNAL_TRACK_DISPLWRKRS.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_DISPLWRKRS.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_DISPLWRKRS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_DISPLWRKRS.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_DISPLWRKRS.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_DISPLWRKRS.put("name", "Displaced Workers");
        JOURNAL_TRACK_DISPLWRKRS.put("description", "Displaced Workers");
        JOURNAL_TRACK_DISPLWRKRS.put("sortOrder", 0);

        JOURNAL_TRACK_HON = new JSONObject();
        JOURNAL_TRACK_HON.put("id", "0a640a2a-409d-1271-8140-d10ee009011b");
        JOURNAL_TRACK_HON.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_HON.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_HON.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_HON.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_HON.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_HON.put("name", "Honors Students");
        JOURNAL_TRACK_HON.put("description", "Honors Students");
        JOURNAL_TRACK_HON.put("sortOrder", 0);

        JOURNAL_TRACK_AA = new JSONObject();
        JOURNAL_TRACK_AA.put("id", "0a640a2a-409d-1271-8140-d10fe43a011c");
        JOURNAL_TRACK_AA.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_AA.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_AA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_AA.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_AA.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_AA.put("name", "Academic Advising - Programs");
        JOURNAL_TRACK_AA.put("description", "Academic Advising");
        JOURNAL_TRACK_AA.put("sortOrder", 0);

        JOURNAL_TRACK_APSAP = new JSONObject();
        JOURNAL_TRACK_APSAP.put("id", "0a640a2a-409d-1271-8140-d110c0f0011d");
        JOURNAL_TRACK_APSAP.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_APSAP.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_APSAP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_APSAP.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_APSAP.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_APSAP.put("name", "Academic Probation / SAP");
        JOURNAL_TRACK_APSAP.put("description", "Academic Probation / SAP");
        JOURNAL_TRACK_APSAP.put("sortOrder", 0);

        JOURNAL_TRACK_FYA = new JSONObject();
        JOURNAL_TRACK_FYA.put("id", "0a640a2a-409d-1271-8140-d111762b011e");
        JOURNAL_TRACK_FYA.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_FYA.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_FYA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_FYA.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_FYA.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_FYA.put("name", "First Year Advising");
        JOURNAL_TRACK_FYA.put("description", "First Year Advising");
        JOURNAL_TRACK_FYA.put("sortOrder", 0);

        JOURNAL_TRACK_STUDJUDC = new JSONObject();
        JOURNAL_TRACK_STUDJUDC.put("id", "0a640a2a-409d-1271-8140-d1128dc8011f");
        JOURNAL_TRACK_STUDJUDC.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_STUDJUDC.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_STUDJUDC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_STUDJUDC.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_STUDJUDC.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_STUDJUDC.put("name", "Student Judicial");
        JOURNAL_TRACK_STUDJUDC.put("description", "Student Judicial");
        JOURNAL_TRACK_STUDJUDC.put("sortOrder", 0);

        JOURNAL_TRACK_BIT = new JSONObject();
        JOURNAL_TRACK_BIT.put("id", "0a640a2a-409d-1271-8140-d112bf620120");
        JOURNAL_TRACK_BIT.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_BIT.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_BIT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_BIT.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_BIT.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_BIT.put("name", "BIT Team");
        JOURNAL_TRACK_BIT.put("description", "BIT Team");
        JOURNAL_TRACK_BIT.put("sortOrder", 0);

        JOURNAL_TRACK_ATD = new JSONObject();
        JOURNAL_TRACK_ATD.put("id", "0a640a2a-409d-1271-8140-d1130f230121");
        JOURNAL_TRACK_ATD.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_ATD.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_ATD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_ATD.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_ATD.put("objectStatus", "ACTIVE");
        // name hacked to ensure consistent alpha sort ordering across db platforms
        JOURNAL_TRACK_ATD.put("name", "Atd Grant");
        JOURNAL_TRACK_ATD.put("description", "ATD Grant");
        JOURNAL_TRACK_ATD.put("sortOrder", 0);

        JOURNAL_TRACK_FINCAID = new JSONObject();
        JOURNAL_TRACK_FINCAID.put("id", "0a640a2a-409d-1271-8140-e5a4fc9c01df");
        JOURNAL_TRACK_FINCAID.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_FINCAID.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_FINCAID.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_FINCAID.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_FINCAID.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_FINCAID.put("name", "Financial AID");
        JOURNAL_TRACK_FINCAID.put("description", "Financial AID");
        JOURNAL_TRACK_FINCAID.put("sortOrder", 0);

        JOURNAL_TRACK_PATHWAY = new JSONObject();
        JOURNAL_TRACK_PATHWAY.put("id", "b2d07a7d-5056-a51a-80a8-96ae5188a188");
        JOURNAL_TRACK_PATHWAY.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_PATHWAY.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_PATHWAY.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_PATHWAY.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_PATHWAY.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_PATHWAY.put("name", "Pathways to Completion");
        JOURNAL_TRACK_PATHWAY.put("description", "Pathways to Completion");
        JOURNAL_TRACK_PATHWAY.put("sortOrder", 0);

        JOURNAL_TRACK_COUNS = new JSONObject();
        JOURNAL_TRACK_COUNS.put("id", "b2d07abb-5056-a51a-8065-991dd0f74782");
        JOURNAL_TRACK_COUNS.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_COUNS.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_COUNS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_COUNS.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_COUNS.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_COUNS.put("name", "Counseling");
        JOURNAL_TRACK_COUNS.put("description", "Counseling");
        JOURNAL_TRACK_COUNS.put("sortOrder", 0);

        JOURNAL_TRACK_EAL = new JSONObject();
        JOURNAL_TRACK_EAL.put("id", "b2d07b38-5056-a51a-809d-81ea2f3b27bf");
        JOURNAL_TRACK_EAL.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_EAL.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_EAL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_TRACK_EAL.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_TRACK_EAL.put("objectStatus", "ACTIVE");
        JOURNAL_TRACK_EAL.put("name", "EAL");
        JOURNAL_TRACK_EAL.put("description", "Early Alert");
        JOURNAL_TRACK_EAL.put("sortOrder", 3);

        JOURNAL_TRACK_ROWS = new JSONArray();
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_AA);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_APSAP);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_ATD);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_BIT);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_COACH);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_COUNS);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_DISPLWRKRS);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_EAL);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_FINCAID);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_FYA);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_HON);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_PATHWAY);
        JOURNAL_TRACK_ROWS.add(JOURNAL_TRACK_STUDJUDC);


        JOURNAL_TRACK_RESPONSE = new JSONObject();
        JOURNAL_TRACK_RESPONSE.put("success", "true");
        JOURNAL_TRACK_RESPONSE.put("message", "");
        JOURNAL_TRACK_RESPONSE.put("results", JOURNAL_TRACK_ROWS.size());
        JOURNAL_TRACK_RESPONSE.put("rows", JOURNAL_TRACK_ROWS);

        JOURNAL_STEP_FOR_EAL = new JSONObject();
        JOURNAL_STEP_FOR_EAL.put("id", "0a640a2a-409d-1271-8140-d11539db0123");
        JOURNAL_STEP_FOR_EAL.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_FOR_EAL.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_FOR_EAL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_FOR_EAL.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_FOR_EAL.put("objectStatus", "ACTIVE");
        JOURNAL_STEP_FOR_EAL.put("name", "Follow-Up");
        JOURNAL_STEP_FOR_EAL.put("description", "Follow-Up");
        JOURNAL_STEP_FOR_EAL.put("sortOrder", 0);
        JOURNAL_STEP_FOR_EAL.put("usedForTransition", false);

        JOURNAL_STEP_TESTPOST_FOR_EAL = new JSONObject();
        JOURNAL_STEP_TESTPOST_FOR_EAL.put("id", "0a640a2a-409d-1271-8140-e5b53e1201fe"); //id of FIN AID Probation JStep
        JOURNAL_STEP_TESTPOST_FOR_EAL.put("sortOrder", "3");

        JOURNAL_STEP_POST_FOR_EAL = new JSONObject();
        JOURNAL_STEP_POST_FOR_EAL.put("id", "0a640a2a-409d-1271-8140-e5b53e1201fe");  //full FIN AID Probation JStep
        JOURNAL_STEP_POST_FOR_EAL.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_POST_FOR_EAL.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_POST_FOR_EAL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_POST_FOR_EAL.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_POST_FOR_EAL.put("objectStatus", "ACTIVE");
        // name changed from original to match JournalStepIT, which had to change it to
        // ensure consisting sorting across db platforms
        JOURNAL_STEP_POST_FOR_EAL.put("name", "Financial Aid Probation");
        JOURNAL_STEP_POST_FOR_EAL.put("description", "FIN AID Probation");
        JOURNAL_STEP_POST_FOR_EAL.put("sortOrder", 0);
        JOURNAL_STEP_POST_FOR_EAL.put("usedForTransition", false);

        JOURNAL_STEP_FOR_EAL_ROWS = new JSONArray();
        JOURNAL_STEP_FOR_EAL_ROWS.add(JOURNAL_STEP_FOR_EAL);

        JOURNAL_STEP_FOR_EAL_RESPONSE = new JSONObject();
        JOURNAL_STEP_FOR_EAL_RESPONSE.put("success", "true");
        JOURNAL_STEP_FOR_EAL_RESPONSE.put("message", "");
        JOURNAL_STEP_FOR_EAL_RESPONSE.put("results", JOURNAL_STEP_FOR_EAL_ROWS.size());
        JOURNAL_STEP_FOR_EAL_RESPONSE.put("rows", JOURNAL_STEP_FOR_EAL_ROWS);

        JOURNAL_STEP_TRACK_FOR_EAL = new JSONObject();
        JOURNAL_STEP_TRACK_FOR_EAL.put("id", "0a640a2a-409d-1271-8140-d1173d770138");
        JOURNAL_STEP_TRACK_FOR_EAL.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_TRACK_FOR_EAL.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_TRACK_FOR_EAL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_STEP_TRACK_FOR_EAL.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_STEP_TRACK_FOR_EAL.put("objectStatus", "ACTIVE");
        JOURNAL_STEP_TRACK_FOR_EAL.put("journalTrack", JOURNAL_TRACK_EAL);
        JOURNAL_STEP_TRACK_FOR_EAL.put("journalStep", JOURNAL_STEP_FOR_EAL);
        JOURNAL_STEP_TRACK_FOR_EAL.put("sortOrder", 0);


        JOURNAL_STEP_TRACK_FOR_EAL_ROWS = new JSONArray();
        JOURNAL_STEP_TRACK_FOR_EAL_ROWS.add(JOURNAL_STEP_TRACK_FOR_EAL);

        JOURNAL_STEP_TRACK_FOR_EAL_RESPONSE = new JSONObject();
        JOURNAL_STEP_TRACK_FOR_EAL_RESPONSE.put("success", "true");
        JOURNAL_STEP_TRACK_FOR_EAL_RESPONSE.put("message", "");
        JOURNAL_STEP_TRACK_FOR_EAL_RESPONSE.put("results", JOURNAL_STEP_TRACK_FOR_EAL_ROWS.size());
        JOURNAL_STEP_TRACK_FOR_EAL_RESPONSE.put("rows", JOURNAL_STEP_TRACK_FOR_EAL_ROWS);

    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsJournalTrackReference() {
        final JSONObject testPostPutNegative = (JSONObject)JOURNAL_TRACK_COACH.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(JOURNAL_TRACK_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsJournalTrackReference_JournalStep() {

       final String invalidJournalStepPath = JOURNAL_TRACK_PATH + "/" + JOURNAL_TRACK_EAL.get("id") + "/journalStep";

        //tests permission on get /{journalTrackId}/journalTrackJournalStep method
        expect()
            .statusCode(403)
        .when()
            .get(JOURNAL_TRACK_PATH + "/" + JOURNAL_TRACK_EAL.get("id") + "/journalTrackJournalStep");

        //tests permission on get /{id}/journalStep method
        expect()
            .statusCode(403)
        .when()
            .get(invalidJournalStepPath);

        //tests permission on post /{id}/journalStep method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(JOURNAL_STEP_TESTPOST_FOR_EAL)
        .when()
            .post(invalidJournalStepPath);

        //tests permission on delete /{id}/journalStep method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(UUID.fromString(JOURNAL_STEP_FOR_EAL.get("id").toString()))
        .when()
            .delete(invalidJournalStepPath);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalTrackReferenceAllBody() {

        testResponseBody(JOURNAL_TRACK_PATH, JOURNAL_TRACK_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalTrackReferenceSingleItemBody() {

        testSingleItemResponseBody(JOURNAL_TRACK_PATH, JOURNAL_TRACK_BIT);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalTrackReferenceSingleItemBody_JournalStep() {

        //tests get /{journalTrackId}/journalTrackJournalStep method
        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", equalTo(JOURNAL_STEP_TRACK_FOR_EAL_RESPONSE))
        .when()
            .get(JOURNAL_TRACK_PATH + "/" + JOURNAL_TRACK_EAL.get("id") + "/journalTrackJournalStep");

        //tests get /{id}/journalStep method
        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", equalTo(JOURNAL_STEP_FOR_EAL_RESPONSE))
        .when()
            .get(JOURNAL_TRACK_PATH + "/" + JOURNAL_TRACK_EAL.get("id") + "/journalStep");

    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsJournalTrackReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, JOURNAL_TRACK_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsJournalTrackReference_JournalStep() {

        testUnsupportedMethods(new String[] {"GET"}, JOURNAL_TRACK_PATH + "/" + JOURNAL_STEP_FOR_EAL.get("id") +
                  "/journalTrackJournalStep");

        testUnsupportedMethods(new String[] {"GET", "POST", "DELETE"}, JOURNAL_TRACK_PATH + "/" +
                  JOURNAL_STEP_FOR_EAL.get("id") + "/journalStep");
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalTrackReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) JOURNAL_TRACK_ATD.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(JOURNAL_TRACK_PATH, JOURNAL_TRACK_HON.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalTrackReferenceSupportedMethodsPositive_JournalStep() {

        final JSONObject testPostJournalStep = (JSONObject) JOURNAL_STEP_POST_FOR_EAL.clone();
        final JSONArray postRows = new JSONArray();
        final JSONObject postResponse = new JSONObject();
        final String validJournalStepPath = JOURNAL_TRACK_PATH + "/" + JOURNAL_TRACK_EAL.get("id") + "/journalStep";
        final String validJournalTrackStepPath = JOURNAL_TRACK_PATH + "/" + JOURNAL_TRACK_EAL.get("id") +
                "/journalTrackJournalStep";
        int checkResultCount = 1;

        //get /id /journalStep
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(validJournalStepPath);


        //get /id /journalStepJournalTrack
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(validJournalTrackStepPath);

        //post       /journalStep
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(JOURNAL_STEP_TESTPOST_FOR_EAL)
        .when()
            .post(validJournalStepPath);

        postRows.add(JOURNAL_STEP_FOR_EAL);
        postRows.add(testPostJournalStep);

        postResponse.put("success", "true");
        postResponse.put("message", "");
        postResponse.put("results", 2);
        postResponse.put("rows", postRows);

        //verify post worked      /journalStep
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(postResponse))
        .when()
            .get(validJournalStepPath);

        //delete   /journalStep
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body( UUID.fromString(JOURNAL_STEP_POST_FOR_EAL.get("id").toString()) )
        .when()
            .delete(validJournalStepPath);

        //verify delete is inactive   /journalStepJournalTrack
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("rows[0].objectStatus", equalTo("INACTIVE"))
        .given()
            .queryParam("status", "INACTIVE")
        .when()
            .get(validJournalTrackStepPath);

        //get (verify result # matches)  /journalStep
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
             .get(validJournalStepPath);

        //get (verify result # matches)  /journalStepJournalTrack
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
            .get(validJournalTrackStepPath);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalTrackReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) JOURNAL_TRACK_EAL.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = JOURNAL_TRACK_FYA;

        referenceNegativeSupportedMethodTest(JOURNAL_TRACK_PATH, testNegativePostObject, testNegativeValidateObject);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalTrackReferenceSupportedMethodsNegative_JournalStep() {

        final JSONObject testPostInvalid = (JSONObject) JOURNAL_STEP_TESTPOST_FOR_EAL.clone();
        final JSONObject testGetInvalid = new JSONObject();
        final String invalidJournalStepPath = JOURNAL_TRACK_PATH + "/" + JOURNAL_TRACK_EAL.get("id") + "/journalStep";
        final String nonExistentUUID = "70b982b0-68d7-11e3-949a-0800200c9a66";
        int checkResultCount = 0;

        //get /journalStep
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

        //get invalid id   /journalTrackJournalStep
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testGetInvalid))
        .when()
            .get(JOURNAL_TRACK_PATH + "/" + nonExistentUUID +"/journalTrackJournalStep");

        //get invalid id   /journalStepJournalTrack
        expect()
            .statusCode(404)
            .contentType("application/json")
        .when()
            .get(JOURNAL_TRACK_PATH + "/" + nonExistentUUID);

        testPostInvalid.put("id", nonExistentUUID);

        //post unassigned uuid name /journalStep
        expect()
            .statusCode(404)
            .given()
            .contentType("application/json")
            .body(testPostInvalid)
        .when()
            .post(invalidJournalStepPath);


        //delete  /journalStep
        expect()
            .statusCode(404)
            .given()
            .contentType("application/json")
            .body(UUID.fromString(nonExistentUUID))
        .when()
            .delete(invalidJournalStepPath);

        //get all (verify result # is unchanged)    /journalStep
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .body("results", equalTo(checkResultCount))
            .when()
            .get(invalidJournalStepPath);
    }
}
