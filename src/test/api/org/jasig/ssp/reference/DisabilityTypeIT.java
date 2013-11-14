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


public class DisabilityTypeIT extends AbstractReferenceTest {

    private static final String DISABILITY_TYPE_PATH = REFERENCE_PATH + "disabilityType";

    private static final JSONObject DISABILITY_TYPE_ADD;
    private static final JSONObject DISABILITY_TYPE_BLIND;
    private static final JSONObject DISABILITY_TYPE_DF;
    private static final JSONObject DISABILITY_TYPE_DH;
    private static final JSONObject DISABILITY_TYPE_EMO;
    private static final JSONObject DISABILITY_TYPE_HDH;
    private static final JSONObject DISABILITY_TYPE_LD;
    private static final JSONObject DISABILITY_TYPE_MULTI;
    private static final JSONObject DISABILITY_TYPE_OR;
    private static final JSONObject DISABILITY_TYPE_OTHER;
    private static final JSONObject DISABILITY_TYPE_SP;
    private static final JSONObject DISABILITY_TYPE_TBI;
    private static final JSONObject DISABILITY_TYPE_VIS;

    private static final JSONArray DISABILITY_TYPE_ROWS;
    private static final JSONObject DISABILITY_TYPE_RESPONSE;

    static {

        DISABILITY_TYPE_ADD = new JSONObject();
        DISABILITY_TYPE_ADD.put("id", "00df56f6-f673-42ed-b73d-d4bceda0d24b");
        DISABILITY_TYPE_ADD.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_ADD.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_ADD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_ADD.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_ADD.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_ADD.put("name", "ADD/ADHD");
        DISABILITY_TYPE_ADD.put("description", "9");

        DISABILITY_TYPE_BLIND = new JSONObject();
        DISABILITY_TYPE_BLIND.put("id", "c9afc03f-b7a2-4d8d-9603-09d313783a04");
        DISABILITY_TYPE_BLIND.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_BLIND.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_BLIND.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_BLIND.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_BLIND.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_BLIND.put("name", "BLIND");
        DISABILITY_TYPE_BLIND.put("description", "11");

        DISABILITY_TYPE_DF = new JSONObject();
        DISABILITY_TYPE_DF.put("id", "9e8377a3-ded3-4338-9780-8161748601fc");
        DISABILITY_TYPE_DF.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_DF.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_DF.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_DF.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_DF.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_DF.put("name", "DF");
        DISABILITY_TYPE_DF.put("description", "3");

        DISABILITY_TYPE_DH = new JSONObject();
        DISABILITY_TYPE_DH.put("id", "c049aec0-9c5c-46a6-8586-604688e9ac69");
        DISABILITY_TYPE_DH.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_DH.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_DH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_DH.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_DH.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_DH.put("name", "DH");
        DISABILITY_TYPE_DH.put("description", "1");

        DISABILITY_TYPE_EMO = new JSONObject();
        DISABILITY_TYPE_EMO.put("id", "997df364-627b-4fae-a58a-646e20d7ab6f");
        DISABILITY_TYPE_EMO.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_EMO.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_EMO.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_EMO.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_EMO.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_EMO.put("name", "EMO");
        DISABILITY_TYPE_EMO.put("description", "6");

        DISABILITY_TYPE_HDH = new JSONObject();
        DISABILITY_TYPE_HDH.put("id", "faf8ae05-a865-4696-8585-e7a7b65d3600");
        DISABILITY_TYPE_HDH.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_HDH.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_HDH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_HDH.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_HDH.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_HDH.put("name", "HDH");
        DISABILITY_TYPE_HDH.put("description", "2");

        DISABILITY_TYPE_LD = new JSONObject();
        DISABILITY_TYPE_LD.put("id", "6babd878-46ba-4106-b5e4-3651fdbf3a71");
        DISABILITY_TYPE_LD.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_LD.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_LD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_LD.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_LD.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_LD.put("name", "LD");
        DISABILITY_TYPE_LD.put("description", "8");

        DISABILITY_TYPE_MULTI = new JSONObject();
        DISABILITY_TYPE_MULTI.put("id", "92a689f2-1850-4d7a-ae58-0ce349cfde6d");
        DISABILITY_TYPE_MULTI.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_MULTI.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_MULTI.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_MULTI.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_MULTI.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_MULTI.put("name", "MULTI");
        DISABILITY_TYPE_MULTI.put("description", "12");

        DISABILITY_TYPE_OR = new JSONObject();
        DISABILITY_TYPE_OR.put("id", "960e507a-2c82-4d6e-bf55-b12e4f0a3a86");
        DISABILITY_TYPE_OR.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_OR.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_OR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_OR.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_OR.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_OR.put("name", "OR");
        DISABILITY_TYPE_OR.put("description", "7");

        DISABILITY_TYPE_OTHER = new JSONObject();
        DISABILITY_TYPE_OTHER.put("id", "4afd60bf-a5ea-4215-abb9-8276a6b68827");
        DISABILITY_TYPE_OTHER.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_OTHER.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_OTHER.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_OTHER.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_OTHER.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_OTHER.put("name", "OTHER");
        DISABILITY_TYPE_OTHER.put("description", "13");

        DISABILITY_TYPE_SP = new JSONObject();
        DISABILITY_TYPE_SP.put("id", "35ed9080-e88b-473a-83a4-565c5c56a756");
        DISABILITY_TYPE_SP.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_SP.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_SP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_SP.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_SP.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_SP.put("name", "SP");
        DISABILITY_TYPE_SP.put("description", "4");

        DISABILITY_TYPE_TBI = new JSONObject();
        DISABILITY_TYPE_TBI.put("id", "2715e5e8-34b2-4992-aea4-7f21b4b10bb9");
        DISABILITY_TYPE_TBI.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_TBI.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_TBI.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_TBI.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_TBI.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_TBI.put("name", "TBI");
        DISABILITY_TYPE_TBI.put("description", "10");

        DISABILITY_TYPE_VIS = new JSONObject();
        DISABILITY_TYPE_VIS.put("id", "b7c3a3c0-de28-4d46-a42b-3de5f294a07f");
        DISABILITY_TYPE_VIS.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_VIS.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_VIS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_TYPE_VIS.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_TYPE_VIS.put("objectStatus", "ACTIVE");
        DISABILITY_TYPE_VIS.put("name", "VIS");
        DISABILITY_TYPE_VIS.put("description", "5");


        DISABILITY_TYPE_ROWS = new JSONArray();
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_ADD);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_BLIND);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_DF);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_DH);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_EMO);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_HDH);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_LD);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_MULTI);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_OR);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_OTHER);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_SP);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_TBI);
        DISABILITY_TYPE_ROWS.add(DISABILITY_TYPE_VIS);

        DISABILITY_TYPE_RESPONSE = new JSONObject();
        DISABILITY_TYPE_RESPONSE.put("success", "true");
        DISABILITY_TYPE_RESPONSE.put("message", "");
        DISABILITY_TYPE_RESPONSE.put("results", DISABILITY_TYPE_ROWS.size());
        DISABILITY_TYPE_RESPONSE.put("rows", DISABILITY_TYPE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsDisabilityTypeReference() {
        final JSONObject testPostPutNegative = (JSONObject)DISABILITY_TYPE_ADD.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(DISABILITY_TYPE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityTypeReferenceAllBody() {

        testResponseBody(DISABILITY_TYPE_PATH, DISABILITY_TYPE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityTypeReferenceSingleItemBody() {

        testSingleItemResponseBody(DISABILITY_TYPE_PATH, DISABILITY_TYPE_BLIND);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsDisabilityTypeReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, DISABILITY_TYPE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityTypeReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) DISABILITY_TYPE_DF.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(DISABILITY_TYPE_PATH, DISABILITY_TYPE_DH.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityTypeReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) DISABILITY_TYPE_EMO.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = DISABILITY_TYPE_HDH;

        referenceNegativeSupportedMethodTest(DISABILITY_TYPE_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
