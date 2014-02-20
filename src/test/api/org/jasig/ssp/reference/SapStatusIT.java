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


public class SapStatusIT extends AbstractReferenceTest {

    private static final String SAP_STATUS_PATH = REFERENCE_PATH + "sapstatus";

    private static final JSONObject SAP_STATUS_GOV_1;
    private static final JSONObject SAP_STATUS_GOV_2;
    private static final JSONObject SAP_STATUS_STATE_1;
    private static final JSONObject SAP_STATUS_STATE_2;
    private static final JSONObject SAP_STATUS_COUNTY_1;
    private static final JSONObject SAP_STATUS_COUNTY_2;
    private static final JSONObject SAP_STATUS_CITY_1;

    private static final JSONArray SAP_STATUS_ROWS;
    private static final JSONObject SAP_STATUS_RESPONSE;

    static {

        SAP_STATUS_GOV_1 = new JSONObject();
        SAP_STATUS_GOV_1.put("id", "9f73e1f0-66aa-47f6-a7bc-2daecb915207");
        SAP_STATUS_GOV_1.put("createdDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_GOV_1.put("createdBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_GOV_1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_GOV_1.put("modifiedBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_GOV_1.put("objectStatus", "ACTIVE");
        SAP_STATUS_GOV_1.put("name", "GOV ONE");
        SAP_STATUS_GOV_1.put("description", "GOV_1 document");
        SAP_STATUS_GOV_1.put("code", "GOV_1");

        SAP_STATUS_GOV_2 = new JSONObject();
        SAP_STATUS_GOV_2.put("id", "83e7967f-fc67-408c-929f-fc361eece175");
        SAP_STATUS_GOV_2.put("createdDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_GOV_2.put("createdBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_GOV_2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_GOV_2.put("modifiedBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_GOV_2.put("objectStatus", "ACTIVE");
        SAP_STATUS_GOV_2.put("name", "GOV TWO");
        SAP_STATUS_GOV_2.put("description", "GOV_2 document");
        SAP_STATUS_GOV_2.put("code", "GOV_2");

        SAP_STATUS_STATE_1 = new JSONObject();
        SAP_STATUS_STATE_1.put("id", "ff149156-a02f-4e9d-bfb2-ef9dfb32eef2");
        SAP_STATUS_STATE_1.put("createdDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_STATE_1.put("createdBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_STATE_1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_STATE_1.put("modifiedBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_STATE_1.put("objectStatus", "ACTIVE");
        SAP_STATUS_STATE_1.put("name", "STATE ONE");
        SAP_STATUS_STATE_1.put("description", "STATE_1 document");
        SAP_STATUS_STATE_1.put("code", "STATE_1");

        SAP_STATUS_STATE_2 = new JSONObject();
        SAP_STATUS_STATE_2.put("id", "79a9a1a7-35ff-459c-9fed-a233d9421761");
        SAP_STATUS_STATE_2.put("createdDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_STATE_2.put("createdBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_STATE_2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_STATE_2.put("modifiedBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_STATE_2.put("objectStatus", "ACTIVE");
        SAP_STATUS_STATE_2.put("name", "STATE TWO");
        SAP_STATUS_STATE_2.put("description", "STATE_2 document");
        SAP_STATUS_STATE_2.put("code", "STATE_2");

        SAP_STATUS_COUNTY_1 = new JSONObject();
        SAP_STATUS_COUNTY_1.put("id", "fa80f025-5405-4355-9747-84dd3fa66df6");
        SAP_STATUS_COUNTY_1.put("createdDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_COUNTY_1.put("createdBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_COUNTY_1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_COUNTY_1.put("modifiedBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_COUNTY_1.put("objectStatus", "ACTIVE");
        SAP_STATUS_COUNTY_1.put("name", "COUNTY ONE");
        SAP_STATUS_COUNTY_1.put("description", "COUNTY_1 document");
        SAP_STATUS_COUNTY_1.put("code", "COUNTY_1");


        SAP_STATUS_COUNTY_2 = new JSONObject();
        SAP_STATUS_COUNTY_2.put("id", "dec0364a-d576-424d-94ce-79544c21e8c8");
        SAP_STATUS_COUNTY_2.put("createdDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_COUNTY_2.put("createdBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_COUNTY_2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_COUNTY_2.put("modifiedBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_COUNTY_2.put("objectStatus", "ACTIVE");
        SAP_STATUS_COUNTY_2.put("name", "COUNTY TWO");
        SAP_STATUS_COUNTY_2.put("description", "COUNTY_2 document");
        SAP_STATUS_COUNTY_2.put("code", "COUNTY_2");


        SAP_STATUS_CITY_1 = new JSONObject();
        SAP_STATUS_CITY_1.put("id", "35efc2bd-c8df-4b2e-b821-43278fdd4839");
        SAP_STATUS_CITY_1.put("createdDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_CITY_1.put("createdBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_CITY_1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SAP_STATUS_CITY_1.put("modifiedBy", getDefaultCreatedModifiedBy());
        SAP_STATUS_CITY_1.put("objectStatus", "ACTIVE");
        SAP_STATUS_CITY_1.put("name", "CITY ONE");
        SAP_STATUS_CITY_1.put("description", "CITY_1 document");
        SAP_STATUS_CITY_1.put("code", "CITY_1");

        SAP_STATUS_ROWS = new JSONArray();
         
        SAP_STATUS_ROWS.add(SAP_STATUS_CITY_1);
        SAP_STATUS_ROWS.add(SAP_STATUS_COUNTY_1);
        SAP_STATUS_ROWS.add(SAP_STATUS_COUNTY_2);
        SAP_STATUS_ROWS.add(SAP_STATUS_GOV_1);
        SAP_STATUS_ROWS.add(SAP_STATUS_GOV_2);
        SAP_STATUS_ROWS.add(SAP_STATUS_STATE_1);
        SAP_STATUS_ROWS.add(SAP_STATUS_STATE_2);        

        SAP_STATUS_RESPONSE = new JSONObject();
        SAP_STATUS_RESPONSE.put("success", "true");
        SAP_STATUS_RESPONSE.put("message", "");
        SAP_STATUS_RESPONSE.put("results", SAP_STATUS_ROWS.size());
        SAP_STATUS_RESPONSE.put("rows", SAP_STATUS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsSapStatusReference() {
        final JSONObject testPostPutNegative = (JSONObject) SAP_STATUS_GOV_1.clone();
        testPostPutNegative.put("name", "GOV ONE");
        testPostPutNegative.put("code", ("GOV_1" + testPassDeConflictNumber));

        referenceAuthenticationControlledMethodNegativeTest(SAP_STATUS_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSapStatusReferenceAllBody() {

        testResponseBody(SAP_STATUS_PATH, SAP_STATUS_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSapStatusReferenceSingleItemBody() {

        testSingleItemResponseBody(SAP_STATUS_PATH, SAP_STATUS_GOV_2);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsSapStatusReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, SAP_STATUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSapStatusReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) SAP_STATUS_GOV_2.clone();
        testPostPutPositive.put("name", "GOV TWO");
        testPostPutPositive.put("code", ("POSTST" + testPassDeConflictNumber));

        referencePositiveSupportedMethodTest(SAP_STATUS_PATH, SAP_STATUS_GOV_2.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSapStatusReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) SAP_STATUS_COUNTY_1.clone();
        testNegativePostObject.put("name", ("COUNTY ONE" + testPassDeConflictNumber));
        testNegativePostObject.put("code", ("COUNTY_1" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = SAP_STATUS_COUNTY_1;

        referenceNegativeSupportedMethodTest(SAP_STATUS_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
