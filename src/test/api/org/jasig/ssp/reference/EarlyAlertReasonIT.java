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

import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;


public class EarlyAlertReasonIT extends AbstractReferenceTest {

    private static final String EARLY_ALERT_REASON_PATH = REFERENCE_PATH + "earlyAlertReason";

    private static final JSONObject EARLY_ALERT_REASON_ACC;
    private static final JSONObject EARLY_ALERT_REASON_CSHGQ;
    private static final JSONObject EARLY_ALERT_REASON_EA;
    private static final JSONObject EARLY_ALERT_REASON_LHQS;
    private static final JSONObject EARLY_ALERT_REASON_LTS;
    private static final JSONObject EARLY_ALERT_REASON_NVRATTD;
    private static final JSONObject EARLY_ALERT_REASON_OTHR;
    private static final JSONObject EARLY_ALERT_REASON_PC;
    private static final JSONObject EARLY_ALERT_REASON_SHGTE;
    private static final JSONObject EARLY_ALERT_REASON_TARDY;

    private static final JSONArray EARLY_ALERT_REASON_ROWS;
    private static final JSONObject EARLY_ALERT_REASON_RESPONSE;

    static {

        EARLY_ALERT_REASON_ACC = new JSONObject();
        EARLY_ALERT_REASON_ACC.put("id", "b2d112a9-5056-a51a-8010-b510525ea3a8");
        EARLY_ALERT_REASON_ACC.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_ACC.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_ACC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_ACC.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_ACC.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_ACC.put("name", "Academic Concern");
        EARLY_ALERT_REASON_ACC.put("description", "Academic Concern");
        EARLY_ALERT_REASON_ACC.put("sortOrder", 1);

        EARLY_ALERT_REASON_CSHGQ = new JSONObject();
        EARLY_ALERT_REASON_CSHGQ.put("id", "1f5729af-0337-4e58-a001-8a9f80dbf8aa");
        EARLY_ALERT_REASON_CSHGQ.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_CSHGQ.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_CSHGQ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_CSHGQ.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_CSHGQ.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_CSHGQ.put("name", "Answered Yes to Critical Self Help Guide Question");
        EARLY_ALERT_REASON_CSHGQ.put("description", "Answered Yes to Critical Self Help Guide Question");
        EARLY_ALERT_REASON_CSHGQ.put("sortOrder", 9);

        EARLY_ALERT_REASON_EA = new JSONObject();
        EARLY_ALERT_REASON_EA.put("id", "b2d112b8-5056-a51a-8067-1fda2849c3e5");
        EARLY_ALERT_REASON_EA.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_EA.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_EA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_EA.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_EA.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_EA.put("name", "Excessive Absences");
        EARLY_ALERT_REASON_EA.put("description", "Excessive Absences");
        EARLY_ALERT_REASON_EA.put("sortOrder", 2);

        EARLY_ALERT_REASON_LHQS = new JSONObject();
        EARLY_ALERT_REASON_LHQS.put("id", "b2d112c8-5056-a51a-80d5-beec7d48cb5d");
        EARLY_ALERT_REASON_LHQS.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_LHQS.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_LHQS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_LHQS.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_LHQS.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_LHQS.put("name", "Low Homework/Quizzes");
        EARLY_ALERT_REASON_LHQS.put("description", "Low Homework/Quizzes");
        EARLY_ALERT_REASON_LHQS.put("sortOrder", 3);

        EARLY_ALERT_REASON_LTS = new JSONObject();
        EARLY_ALERT_REASON_LTS.put("id", "b2d112d7-5056-a51a-80aa-795e56155af5");
        EARLY_ALERT_REASON_LTS.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_LTS.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_LTS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_LTS.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_LTS.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_LTS.put("name", "Low Test Scores");
        EARLY_ALERT_REASON_LTS.put("description", "Low Test Scores");
        EARLY_ALERT_REASON_LTS.put("sortOrder", 4);

        EARLY_ALERT_REASON_NVRATTD = new JSONObject();
        EARLY_ALERT_REASON_NVRATTD.put("id", "b2d112e7-5056-a51a-80e8-a30645c463e4");
        EARLY_ALERT_REASON_NVRATTD.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_NVRATTD.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_NVRATTD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_NVRATTD.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_NVRATTD.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_NVRATTD.put("name", "Never Attended");
        EARLY_ALERT_REASON_NVRATTD.put("description", "Never Attended");
        EARLY_ALERT_REASON_NVRATTD.put("sortOrder", 5);

        EARLY_ALERT_REASON_OTHR = new JSONObject();
        EARLY_ALERT_REASON_OTHR.put("id", "b2d11335-5056-a51a-80ea-074f8fef94ea");
        EARLY_ALERT_REASON_OTHR.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_OTHR.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_OTHR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_OTHR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_OTHR.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_OTHR.put("name", "Other");
        EARLY_ALERT_REASON_OTHR.put("description", "Other");
        EARLY_ALERT_REASON_OTHR.put("sortOrder", 8);

        EARLY_ALERT_REASON_PC = new JSONObject();
        EARLY_ALERT_REASON_PC.put("id", "b2d11316-5056-a51a-80f9-79421bdf08bf");
        EARLY_ALERT_REASON_PC.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_PC.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_PC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_PC.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_PC.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_PC.put("name", "Personal Concern");
        EARLY_ALERT_REASON_PC.put("description", "Personal Concern");
        EARLY_ALERT_REASON_PC.put("sortOrder", 6);

        EARLY_ALERT_REASON_SHGTE = new JSONObject();
        EARLY_ALERT_REASON_SHGTE.put("id", "300d68ef-38c2-4b7d-ad46-7874aa5d34ac");
        EARLY_ALERT_REASON_SHGTE.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_SHGTE.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_SHGTE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_SHGTE.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_SHGTE.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_SHGTE.put("name", "Self Help Guide Threshold Exceeded");
        EARLY_ALERT_REASON_SHGTE.put("description", "Self Help Guide Threshold Exceeded");
        EARLY_ALERT_REASON_SHGTE.put("sortOrder", 10);

        EARLY_ALERT_REASON_TARDY = new JSONObject();
        EARLY_ALERT_REASON_TARDY.put("id", "b2d11326-5056-a51a-806c-79f352d0c2b2");
        EARLY_ALERT_REASON_TARDY.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_TARDY.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_TARDY.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REASON_TARDY.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REASON_TARDY.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REASON_TARDY.put("name", "Tardiness");
        EARLY_ALERT_REASON_TARDY.put("description", "Tardiness");
        EARLY_ALERT_REASON_TARDY.put("sortOrder", 7);


        EARLY_ALERT_REASON_ROWS = new JSONArray();
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_ACC);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_CSHGQ);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_EA);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_LHQS);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_LTS);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_NVRATTD);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_OTHR);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_PC);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_SHGTE);
        EARLY_ALERT_REASON_ROWS.add(EARLY_ALERT_REASON_TARDY);


        EARLY_ALERT_REASON_RESPONSE = new JSONObject();
        EARLY_ALERT_REASON_RESPONSE.put("success", "true");
        EARLY_ALERT_REASON_RESPONSE.put("message", "");
        EARLY_ALERT_REASON_RESPONSE.put("results", EARLY_ALERT_REASON_ROWS.size());
        EARLY_ALERT_REASON_RESPONSE.put("rows", EARLY_ALERT_REASON_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEarlyAlertReasonReference() {
        final JSONObject testPostPutNegative = (JSONObject)EARLY_ALERT_REASON_ACC.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(EARLY_ALERT_REASON_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertReasonReferenceAllBody() {

        testResponseBody(EARLY_ALERT_REASON_PATH, EARLY_ALERT_REASON_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertReasonReferenceSingleItemBody() {

        testSingleItemResponseBody(EARLY_ALERT_REASON_PATH, EARLY_ALERT_REASON_CSHGQ);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEarlyAlertReasonReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, EARLY_ALERT_REASON_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertReasonReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) EARLY_ALERT_REASON_EA.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(EARLY_ALERT_REASON_PATH, EARLY_ALERT_REASON_LHQS.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertReasonReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) EARLY_ALERT_REASON_SHGTE.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = EARLY_ALERT_REASON_TARDY;

        referenceNegativeSupportedMethodTest(EARLY_ALERT_REASON_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}

