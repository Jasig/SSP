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


public class RaceIT extends AbstractReferenceTest {

    private static final String RACE_PATH = REFERENCE_PATH + "race";

    private static final JSONObject RACE_AIAN;
    private static final JSONObject RACE_HL;
    private static final JSONObject RACE_API;
    private static final JSONObject RACE_AA;
    private static final JSONObject RACE_PNA;
    private static final JSONObject RACE_OTH;
    private static final JSONObject RACE_CW;

    private static final JSONArray RACE_ROWS;
    private static final JSONObject RACE_RESPONSE;

    static {

        RACE_AIAN = new JSONObject();
        RACE_AIAN.put("id", "35efc2bd-c8df-4b2e-b821-43278fdd4839");
        RACE_AIAN.put("createdDate", getDefaultCreatedModifiedByDate());
        RACE_AIAN.put("createdBy", getDefaultCreatedModifiedBy());
        RACE_AIAN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        RACE_AIAN.put("modifiedBy", getDefaultCreatedModifiedBy());
        RACE_AIAN.put("objectStatus", "ACTIVE");
        RACE_AIAN.put("name", "American Indian/Alaskan Native");
        RACE_AIAN.put("description", "American Indian/Alaskan Native");
        RACE_AIAN.put("code", "AI/AN");

        RACE_HL = new JSONObject();
        RACE_HL.put("id", "79a9a1a7-35ff-459c-9fed-a233d9421761");
        RACE_HL.put("createdDate", getDefaultCreatedModifiedByDate());
        RACE_HL.put("createdBy", getDefaultCreatedModifiedBy());
        RACE_HL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        RACE_HL.put("modifiedBy", getDefaultCreatedModifiedBy());
        RACE_HL.put("objectStatus", "ACTIVE");
        RACE_HL.put("name", "Hispanic/Latino");
        RACE_HL.put("description", "Hispanic/Latino");
        RACE_HL.put("code", "LATINO");

        RACE_API = new JSONObject();
        RACE_API.put("id", "83e7967f-fc67-408c-929f-fc361eece175");
        RACE_API.put("createdDate", getDefaultCreatedModifiedByDate());
        RACE_API.put("createdBy", getDefaultCreatedModifiedBy());
        RACE_API.put("modifiedDate", getDefaultCreatedModifiedByDate());
        RACE_API.put("modifiedBy", getDefaultCreatedModifiedBy());
        RACE_API.put("objectStatus", "ACTIVE");
        RACE_API.put("name", "Asian Pacific Islander");
        RACE_API.put("description", "Asian Pacific Islander");
        RACE_API.put("code", "API");

        RACE_AA = new JSONObject();
        RACE_AA.put("id", "9f73e1f0-66aa-47f6-a7bc-2daecb915207");
        RACE_AA.put("createdDate", getDefaultCreatedModifiedByDate());
        RACE_AA.put("createdBy", getDefaultCreatedModifiedBy());
        RACE_AA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        RACE_AA.put("modifiedBy", getDefaultCreatedModifiedBy());
        RACE_AA.put("objectStatus", "ACTIVE");
        RACE_AA.put("name", "African American/Black");
        RACE_AA.put("description", "African American/Black");
        RACE_AA.put("code", "AFAM");

        RACE_PNA = new JSONObject();
        RACE_PNA.put("id", "dec0364a-d576-424d-94ce-79544c21e8c8");
        RACE_PNA.put("createdDate", getDefaultCreatedModifiedByDate());
        RACE_PNA.put("createdBy", getDefaultCreatedModifiedBy());
        RACE_PNA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        RACE_PNA.put("modifiedBy", getDefaultCreatedModifiedBy());
        RACE_PNA.put("objectStatus", "ACTIVE");
        RACE_PNA.put("name", "Prefer Not To Answer");
        RACE_PNA.put("description", "Prefer Not To Answer");
        RACE_PNA.put("code", "PRFNOANSWR");


        RACE_OTH = new JSONObject();
        RACE_OTH.put("id", "fa80f025-5405-4355-9747-84dd3fa66df6");
        RACE_OTH.put("createdDate", getDefaultCreatedModifiedByDate());
        RACE_OTH.put("createdBy", getDefaultCreatedModifiedBy());
        RACE_OTH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        RACE_OTH.put("modifiedBy", getDefaultCreatedModifiedBy());
        RACE_OTH.put("objectStatus", "ACTIVE");
        RACE_OTH.put("name", "Other");
        RACE_OTH.put("description", "Other");
        RACE_OTH.put("code", "OTHER");


        RACE_CW = new JSONObject();
        RACE_CW.put("id", "ff149156-a02f-4e9d-bfb2-ef9dfb32eef2");
        RACE_CW.put("createdDate", getDefaultCreatedModifiedByDate());
        RACE_CW.put("createdBy", getDefaultCreatedModifiedBy());
        RACE_CW.put("modifiedDate", getDefaultCreatedModifiedByDate());
        RACE_CW.put("modifiedBy", getDefaultCreatedModifiedBy());
        RACE_CW.put("objectStatus", "ACTIVE");
        RACE_CW.put("name", "Caucasian/White");
        RACE_CW.put("description", "Caucasian/White");
        RACE_CW.put("code", "WHITE");

        RACE_ROWS = new JSONArray();
        RACE_ROWS.add(RACE_AA);
        RACE_ROWS.add(RACE_AIAN);
        RACE_ROWS.add(RACE_API);
        RACE_ROWS.add(RACE_CW);
        RACE_ROWS.add(RACE_HL);
        RACE_ROWS.add(RACE_OTH);
        RACE_ROWS.add(RACE_PNA);

        RACE_RESPONSE = new JSONObject();
        RACE_RESPONSE.put("success", "true");
        RACE_RESPONSE.put("message", "");
        RACE_RESPONSE.put("results", RACE_ROWS.size());
        RACE_RESPONSE.put("rows", RACE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsRaceReference() {
        final JSONObject testPostPutNegative = (JSONObject) RACE_AIAN.clone();
        testPostPutNegative.put("name", "testPostUnAuth");
        testPostPutNegative.put("code", ("NEGTST" + testPassDeConflictNumber));

        referenceAuthenticationControlledMethodNegativeTest(RACE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testRaceReferenceAllBody() {

        testResponseBody(RACE_PATH, RACE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testRaceReferenceSingleItemBody() {

        testSingleItemResponseBody(RACE_PATH, RACE_HL);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsRaceReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, RACE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testRaceReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) RACE_HL.clone();
        testPostPutPositive.put("name", "testPostPositive");
        testPostPutPositive.put("code", ("POSTST" + testPassDeConflictNumber));

        referencePositiveSupportedMethodTest(RACE_PATH, RACE_AA.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testRaceReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) RACE_PNA.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        testNegativePostObject.put("code", ("SUPPNEGTST" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = RACE_API;

        referenceNegativeSupportedMethodTest(RACE_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
