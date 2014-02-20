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


public class EthnicityIT extends AbstractReferenceTest {

    private static final String ETHNICITY_PATH = REFERENCE_PATH + "ethnicity";

    private static final JSONObject ETHNICITY_AIAN;
    private static final JSONObject ETHNICITY_HL;
    private static final JSONObject ETHNICITY_API;
    private static final JSONObject ETHNICITY_AA;
    private static final JSONObject ETHNICITY_PNA;
    private static final JSONObject ETHNICITY_OTH;
    private static final JSONObject ETHNICITY_CW;

    private static final JSONArray ETHNICITY_ROWS;
    private static final JSONObject ETHNICITY_RESPONSE;

    static {

        ETHNICITY_AIAN = new JSONObject();
        ETHNICITY_AIAN.put("id", "35efc2bd-c8df-4b2e-b821-43278fdd4839");
        ETHNICITY_AIAN.put("createdDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_AIAN.put("createdBy", getDefaultCreatedModifiedBy());
        ETHNICITY_AIAN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_AIAN.put("modifiedBy", getDefaultCreatedModifiedBy());
        ETHNICITY_AIAN.put("objectStatus", "ACTIVE");
        ETHNICITY_AIAN.put("name", "American Indian/Alaskan Native");
        ETHNICITY_AIAN.put("description", "American Indian/Alaskan Native");

        ETHNICITY_HL = new JSONObject();
        ETHNICITY_HL.put("id", "79a9a1a7-35ff-459c-9fed-a233d9421761");
        ETHNICITY_HL.put("createdDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_HL.put("createdBy", getDefaultCreatedModifiedBy());
        ETHNICITY_HL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_HL.put("modifiedBy", getDefaultCreatedModifiedBy());
        ETHNICITY_HL.put("objectStatus", "ACTIVE");
        ETHNICITY_HL.put("name", "Hispanic/Latino");
        ETHNICITY_HL.put("description", "Hispanic/Latino");

        ETHNICITY_API = new JSONObject();
        ETHNICITY_API.put("id", "83e7967f-fc67-408c-929f-fc361eece175");
        ETHNICITY_API.put("createdDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_API.put("createdBy", getDefaultCreatedModifiedBy());
        ETHNICITY_API.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_API.put("modifiedBy", getDefaultCreatedModifiedBy());
        ETHNICITY_API.put("objectStatus", "ACTIVE");
        ETHNICITY_API.put("name", "Asian Pacific Islander");
        ETHNICITY_API.put("description", "Asian Pacific Islander");

        ETHNICITY_AA = new JSONObject();
        ETHNICITY_AA.put("id", "9f73e1f0-66aa-47f6-a7bc-2daecb915207");
        ETHNICITY_AA.put("createdDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_AA.put("createdBy", getDefaultCreatedModifiedBy());
        ETHNICITY_AA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_AA.put("modifiedBy", getDefaultCreatedModifiedBy());
        ETHNICITY_AA.put("objectStatus", "ACTIVE");
        ETHNICITY_AA.put("name", "African American");
        ETHNICITY_AA.put("description", "African American");

        ETHNICITY_PNA = new JSONObject();
        ETHNICITY_PNA.put("id", "dec0364a-d576-424d-94ce-79544c21e8c8");
        ETHNICITY_PNA.put("createdDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_PNA.put("createdBy", getDefaultCreatedModifiedBy());
        ETHNICITY_PNA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_PNA.put("modifiedBy", getDefaultCreatedModifiedBy());
        ETHNICITY_PNA.put("objectStatus", "ACTIVE");
        ETHNICITY_PNA.put("name", "Prefer Not To Answer");
        ETHNICITY_PNA.put("description", "Prefer Not To Answer");

        ETHNICITY_OTH = new JSONObject();
        ETHNICITY_OTH.put("id", "fa80f025-5405-4355-9747-84dd3fa66df6");
        ETHNICITY_OTH.put("createdDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_OTH.put("createdBy", getDefaultCreatedModifiedBy());
        ETHNICITY_OTH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_OTH.put("modifiedBy", getDefaultCreatedModifiedBy());
        ETHNICITY_OTH.put("objectStatus", "ACTIVE");
        ETHNICITY_OTH.put("name", "Other");
        ETHNICITY_OTH.put("description", "Other");

        ETHNICITY_CW = new JSONObject();
        ETHNICITY_CW.put("id", "ff149156-a02f-4e9d-bfb2-ef9dfb32eef2");
        ETHNICITY_CW.put("createdDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_CW.put("createdBy", getDefaultCreatedModifiedBy());
        ETHNICITY_CW.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ETHNICITY_CW.put("modifiedBy", getDefaultCreatedModifiedBy());
        ETHNICITY_CW.put("objectStatus", "ACTIVE");
        ETHNICITY_CW.put("name", "Caucasian");
        ETHNICITY_CW.put("description", "Caucasian");

        ETHNICITY_ROWS = new JSONArray();
        ETHNICITY_ROWS.add(ETHNICITY_AA);
        ETHNICITY_ROWS.add(ETHNICITY_AIAN);
        ETHNICITY_ROWS.add(ETHNICITY_API);
        ETHNICITY_ROWS.add(ETHNICITY_CW);
        ETHNICITY_ROWS.add(ETHNICITY_HL);
        ETHNICITY_ROWS.add(ETHNICITY_OTH);
        ETHNICITY_ROWS.add(ETHNICITY_PNA);

        ETHNICITY_RESPONSE = new JSONObject();
        ETHNICITY_RESPONSE.put("success", "true");
        ETHNICITY_RESPONSE.put("message", "");
        ETHNICITY_RESPONSE.put("results", ETHNICITY_ROWS.size());
        ETHNICITY_RESPONSE.put("rows", ETHNICITY_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEthnicityReference() {
        final JSONObject testPostPutNegative = (JSONObject) ETHNICITY_AIAN.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(ETHNICITY_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEthnicityReferenceAllBody() {

        testResponseBody(ETHNICITY_PATH, ETHNICITY_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEthnicityReferenceSingleItemBody() {

        testSingleItemResponseBody(ETHNICITY_PATH, ETHNICITY_HL);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEthnicityReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, ETHNICITY_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEthnicityReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ETHNICITY_HL.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(ETHNICITY_PATH, ETHNICITY_AA.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEthnicityReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ETHNICITY_PNA.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = ETHNICITY_API;

        referenceNegativeSupportedMethodTest(ETHNICITY_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
