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


public class FinancialAidFileIT extends AbstractReferenceTest {

    private static final String FINANCIAL_AID_FILE_PATH = REFERENCE_PATH + "financialAidFile";

    private static final JSONObject FINANCIAL_AID_FILE_GOV_1;
    private static final JSONObject FINANCIAL_AID_FILE_GOV_2;
    private static final JSONObject FINANCIAL_AID_FILE_STATE_1;
    private static final JSONObject FINANCIAL_AID_FILE_STATE_2;
    private static final JSONObject FINANCIAL_AID_FILE_COUNTY_1;
    private static final JSONObject FINANCIAL_AID_FILE_COUNTY_2;
    private static final JSONObject FINANCIAL_AID_FILE_CITY_1;

    private static final JSONArray FINANCIAL_AID_FILE_ROWS;
    private static final JSONObject FINANCIAL_AID_FILE_RESPONSE;

    static {

        FINANCIAL_AID_FILE_GOV_1 = new JSONObject();
        FINANCIAL_AID_FILE_GOV_1.put("id", "9f73e1f0-66aa-47f6-a7bc-2daecb915207");
        FINANCIAL_AID_FILE_GOV_1.put("createdDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_GOV_1.put("createdBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_GOV_1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_GOV_1.put("modifiedBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_GOV_1.put("objectStatus", "ACTIVE");
        FINANCIAL_AID_FILE_GOV_1.put("name", "GOV ONE");
        FINANCIAL_AID_FILE_GOV_1.put("description", "GOV_1 document");
        FINANCIAL_AID_FILE_GOV_1.put("code", "GOV_1");

        FINANCIAL_AID_FILE_GOV_2 = new JSONObject();
        FINANCIAL_AID_FILE_GOV_2.put("id", "83e7967f-fc67-408c-929f-fc361eece175");
        FINANCIAL_AID_FILE_GOV_2.put("createdDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_GOV_2.put("createdBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_GOV_2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_GOV_2.put("modifiedBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_GOV_2.put("objectStatus", "ACTIVE");
        FINANCIAL_AID_FILE_GOV_2.put("name", "GOV TWO");
        FINANCIAL_AID_FILE_GOV_2.put("description", "GOV_2 document");
        FINANCIAL_AID_FILE_GOV_2.put("code", "GOV_2");

        FINANCIAL_AID_FILE_STATE_1 = new JSONObject();
        FINANCIAL_AID_FILE_STATE_1.put("id", "ff149156-a02f-4e9d-bfb2-ef9dfb32eef2");
        FINANCIAL_AID_FILE_STATE_1.put("createdDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_STATE_1.put("createdBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_STATE_1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_STATE_1.put("modifiedBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_STATE_1.put("objectStatus", "ACTIVE");
        FINANCIAL_AID_FILE_STATE_1.put("name", "STATE ONE");
        FINANCIAL_AID_FILE_STATE_1.put("description", "STATE_1 document");
        FINANCIAL_AID_FILE_STATE_1.put("code", "STATE_1");

        FINANCIAL_AID_FILE_STATE_2 = new JSONObject();
        FINANCIAL_AID_FILE_STATE_2.put("id", "79a9a1a7-35ff-459c-9fed-a233d9421761");
        FINANCIAL_AID_FILE_STATE_2.put("createdDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_STATE_2.put("createdBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_STATE_2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_STATE_2.put("modifiedBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_STATE_2.put("objectStatus", "ACTIVE");
        FINANCIAL_AID_FILE_STATE_2.put("name", "STATE TWO");
        FINANCIAL_AID_FILE_STATE_2.put("description", "STATE_2 document");
        FINANCIAL_AID_FILE_STATE_2.put("code", "STATE_2");

        FINANCIAL_AID_FILE_COUNTY_1 = new JSONObject();
        FINANCIAL_AID_FILE_COUNTY_1.put("id", "fa80f025-5405-4355-9747-84dd3fa66df6");
        FINANCIAL_AID_FILE_COUNTY_1.put("createdDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_COUNTY_1.put("createdBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_COUNTY_1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_COUNTY_1.put("modifiedBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_COUNTY_1.put("objectStatus", "ACTIVE");
        FINANCIAL_AID_FILE_COUNTY_1.put("name", "COUNTY ONE");
        FINANCIAL_AID_FILE_COUNTY_1.put("description", "COUNTY_1 document");
        FINANCIAL_AID_FILE_COUNTY_1.put("code", "COUNTY_1");


        FINANCIAL_AID_FILE_COUNTY_2 = new JSONObject();
        FINANCIAL_AID_FILE_COUNTY_2.put("id", "dec0364a-d576-424d-94ce-79544c21e8c8");
        FINANCIAL_AID_FILE_COUNTY_2.put("createdDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_COUNTY_2.put("createdBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_COUNTY_2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_COUNTY_2.put("modifiedBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_COUNTY_2.put("objectStatus", "ACTIVE");
        FINANCIAL_AID_FILE_COUNTY_2.put("name", "COUNTY TWO");
        FINANCIAL_AID_FILE_COUNTY_2.put("description", "COUNTY_2 document");
        FINANCIAL_AID_FILE_COUNTY_2.put("code", "COUNTY_2");


        FINANCIAL_AID_FILE_CITY_1 = new JSONObject();
        FINANCIAL_AID_FILE_CITY_1.put("id", "35efc2bd-c8df-4b2e-b821-43278fdd4839");
        FINANCIAL_AID_FILE_CITY_1.put("createdDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_CITY_1.put("createdBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_CITY_1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        FINANCIAL_AID_FILE_CITY_1.put("modifiedBy", getDefaultCreatedModifiedBy());
        FINANCIAL_AID_FILE_CITY_1.put("objectStatus", "ACTIVE");
        FINANCIAL_AID_FILE_CITY_1.put("name", "CITY ONE");
        FINANCIAL_AID_FILE_CITY_1.put("description", "CITY_1 document");
        FINANCIAL_AID_FILE_CITY_1.put("code", "CITY_1");

        FINANCIAL_AID_FILE_ROWS = new JSONArray();
        FINANCIAL_AID_FILE_ROWS.add(FINANCIAL_AID_FILE_CITY_1);
        FINANCIAL_AID_FILE_ROWS.add(FINANCIAL_AID_FILE_COUNTY_1);
        FINANCIAL_AID_FILE_ROWS.add(FINANCIAL_AID_FILE_COUNTY_2);
        FINANCIAL_AID_FILE_ROWS.add(FINANCIAL_AID_FILE_GOV_1);
        FINANCIAL_AID_FILE_ROWS.add(FINANCIAL_AID_FILE_GOV_2);
        FINANCIAL_AID_FILE_ROWS.add(FINANCIAL_AID_FILE_STATE_1);
        FINANCIAL_AID_FILE_ROWS.add(FINANCIAL_AID_FILE_STATE_2);
        
        FINANCIAL_AID_FILE_RESPONSE = new JSONObject();
        FINANCIAL_AID_FILE_RESPONSE.put("success", "true");
        FINANCIAL_AID_FILE_RESPONSE.put("message", "");
        FINANCIAL_AID_FILE_RESPONSE.put("results", FINANCIAL_AID_FILE_ROWS.size());
        FINANCIAL_AID_FILE_RESPONSE.put("rows", FINANCIAL_AID_FILE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsFinancialAidFileReference() {
        final JSONObject testPostPutNegative = (JSONObject) FINANCIAL_AID_FILE_GOV_1.clone();
        testPostPutNegative.put("name", "GOV ONE");
        testPostPutNegative.put("code", ("GOV_1" + testPassDeConflictNumber));

        referenceAuthenticationControlledMethodNegativeTest(FINANCIAL_AID_FILE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFinancialAidFileReferenceAllBody() {

        testResponseBody(FINANCIAL_AID_FILE_PATH, FINANCIAL_AID_FILE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFinancialAidFileReferenceSingleItemBody() {

        testSingleItemResponseBody(FINANCIAL_AID_FILE_PATH, FINANCIAL_AID_FILE_GOV_2);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsFinancialAidFileReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, FINANCIAL_AID_FILE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFinancialAidFileReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) FINANCIAL_AID_FILE_GOV_2.clone();
        testPostPutPositive.put("name", "GOV TWO");
        testPostPutPositive.put("code", ("POSTST" + testPassDeConflictNumber));

        referencePositiveSupportedMethodTest(FINANCIAL_AID_FILE_PATH, FINANCIAL_AID_FILE_GOV_2.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFinancialAidFileReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) FINANCIAL_AID_FILE_COUNTY_1.clone();
        testNegativePostObject.put("name", ("COUNTY ONE" + testPassDeConflictNumber));
        testNegativePostObject.put("code", ("COUNTY_1" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = FINANCIAL_AID_FILE_COUNTY_1;

        referenceNegativeSupportedMethodTest(FINANCIAL_AID_FILE_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
