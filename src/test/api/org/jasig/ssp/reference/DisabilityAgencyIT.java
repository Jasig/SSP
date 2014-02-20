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


public class DisabilityAgencyIT extends AbstractReferenceTest {

    private static final String DISABILITY_AGENCY_PATH = REFERENCE_PATH + "disabilityAgency";

    private static final JSONObject DISABILITY_AGENCY_BSVI;
    private static final JSONObject DISABILITY_AGENCY_BVR;
    private static final JSONObject DISABILITY_AGENCY_MH;
    private static final JSONObject DISABILITY_AGENCY_OTHR;
    private static final JSONObject DISABILITY_AGENCY_VA;

    private static final JSONArray DISABILITY_AGENCY_ROWS;
    private static final JSONObject DISABILITY_AGENCY_RESPONSE;

    static {

        DISABILITY_AGENCY_BSVI = new JSONObject();
        DISABILITY_AGENCY_BSVI.put("id", "7845fdea-9da7-49be-a3cf-c9da03c38d56");
        DISABILITY_AGENCY_BSVI.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_BSVI.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_BSVI.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_BSVI.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_BSVI.put("objectStatus", "ACTIVE");
        DISABILITY_AGENCY_BSVI.put("name", "BSVI");
        DISABILITY_AGENCY_BSVI.put("description", "BSVI");

        DISABILITY_AGENCY_BVR = new JSONObject();
        DISABILITY_AGENCY_BVR.put("id", "6b209a6d-70b9-416c-95a5-17cab4594f85");
        DISABILITY_AGENCY_BVR.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_BVR.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_BVR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_BVR.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_BVR.put("objectStatus", "ACTIVE");
        DISABILITY_AGENCY_BVR.put("name", "BVR");
        DISABILITY_AGENCY_BVR.put("description", "BVR");

        DISABILITY_AGENCY_MH = new JSONObject();
        DISABILITY_AGENCY_MH.put("id", "02aa9557-c8f1-4716-bbae-2b3401e386f6");
        DISABILITY_AGENCY_MH.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_MH.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_MH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_MH.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_MH.put("objectStatus", "ACTIVE");
        DISABILITY_AGENCY_MH.put("name", "MH");
        DISABILITY_AGENCY_MH.put("description", "MH");

        DISABILITY_AGENCY_OTHR = new JSONObject();
        DISABILITY_AGENCY_OTHR.put("id", "224b03d9-90da-4f9c-8959-ea2e97661f40");
        DISABILITY_AGENCY_OTHR.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_OTHR.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_OTHR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_OTHR.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_OTHR.put("objectStatus", "ACTIVE");
        DISABILITY_AGENCY_OTHR.put("name", "Other");
        DISABILITY_AGENCY_OTHR.put("description", "Other");

        DISABILITY_AGENCY_VA = new JSONObject();
        DISABILITY_AGENCY_VA.put("id", "7f92b5bb-8e9c-44c7-88fd-2ffdce68ef98");
        DISABILITY_AGENCY_VA.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_VA.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_VA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_AGENCY_VA.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_AGENCY_VA.put("objectStatus", "ACTIVE");
        DISABILITY_AGENCY_VA.put("name", "VA");
        DISABILITY_AGENCY_VA.put("description", "VA");


        DISABILITY_AGENCY_ROWS = new JSONArray();
        DISABILITY_AGENCY_ROWS.add(DISABILITY_AGENCY_BSVI);
        DISABILITY_AGENCY_ROWS.add(DISABILITY_AGENCY_BVR);
        DISABILITY_AGENCY_ROWS.add(DISABILITY_AGENCY_MH);
        DISABILITY_AGENCY_ROWS.add(DISABILITY_AGENCY_OTHR);
        DISABILITY_AGENCY_ROWS.add(DISABILITY_AGENCY_VA);

        
        DISABILITY_AGENCY_RESPONSE = new JSONObject();
        DISABILITY_AGENCY_RESPONSE.put("success", "true");
        DISABILITY_AGENCY_RESPONSE.put("message", "");
        DISABILITY_AGENCY_RESPONSE.put("results", DISABILITY_AGENCY_ROWS.size());
        DISABILITY_AGENCY_RESPONSE.put("rows", DISABILITY_AGENCY_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsDisabilityAgencyReference() {
        final JSONObject testPostPutNegative = (JSONObject)DISABILITY_AGENCY_BSVI.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(DISABILITY_AGENCY_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityAgencyReferenceAllBody() {

        testResponseBody(DISABILITY_AGENCY_PATH, DISABILITY_AGENCY_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityAgencyReferenceSingleItemBody() {

        testSingleItemResponseBody(DISABILITY_AGENCY_PATH, DISABILITY_AGENCY_BVR);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsDisabilityAgencyReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, DISABILITY_AGENCY_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityAgencyReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) DISABILITY_AGENCY_MH.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(DISABILITY_AGENCY_PATH, DISABILITY_AGENCY_OTHR.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityAgencyReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) DISABILITY_AGENCY_VA.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = DISABILITY_AGENCY_BSVI;

        referenceNegativeSupportedMethodTest(DISABILITY_AGENCY_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
