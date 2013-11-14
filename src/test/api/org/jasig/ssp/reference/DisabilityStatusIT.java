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


public class DisabilityStatusIT extends AbstractReferenceTest {

    private static final String DISABILITY_STATUS_PATH = REFERENCE_PATH + "disabilityStatus";

    private static final JSONObject DISABILITY_STATUS_ELIG;
    private static final JSONObject DISABILITY_STATUS_INELIG;
    private static final JSONObject DISABILITY_STATUS_PEND;
    private static final JSONObject DISABILITY_STATUS_TEMPELIG;

    private static final JSONArray DISABILITY_STATUS_ROWS;
    private static final JSONObject DISABILITY_STATUS_RESPONSE;

    static {

        DISABILITY_STATUS_ELIG = new JSONObject();
        DISABILITY_STATUS_ELIG.put("id", "e0208429-aeb2-4854-ab7c-3c9281c96002");
        DISABILITY_STATUS_ELIG.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_STATUS_ELIG.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_STATUS_ELIG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_STATUS_ELIG.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_STATUS_ELIG.put("objectStatus", "ACTIVE");
        DISABILITY_STATUS_ELIG.put("name", "Eligible");
        DISABILITY_STATUS_ELIG.put("description", "Eligible");

        DISABILITY_STATUS_INELIG = new JSONObject();
        DISABILITY_STATUS_INELIG.put("id", "5dab94e2-d5a0-4203-b462-8d0841d63786");
        DISABILITY_STATUS_INELIG.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_STATUS_INELIG.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_STATUS_INELIG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_STATUS_INELIG.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_STATUS_INELIG.put("objectStatus", "ACTIVE");
        DISABILITY_STATUS_INELIG.put("name", "Ineligible");
        DISABILITY_STATUS_INELIG.put("description", "Ineligible");

        DISABILITY_STATUS_PEND = new JSONObject();
        DISABILITY_STATUS_PEND.put("id", "c2609cdf-6aa2-4948-b0e3-3779f2541783");
        DISABILITY_STATUS_PEND.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_STATUS_PEND.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_STATUS_PEND.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_STATUS_PEND.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_STATUS_PEND.put("objectStatus", "ACTIVE");
        DISABILITY_STATUS_PEND.put("name", "Pending");
        DISABILITY_STATUS_PEND.put("description", "Pending");

        DISABILITY_STATUS_TEMPELIG = new JSONObject();
        DISABILITY_STATUS_TEMPELIG.put("id", "24d12b6f-1d58-4f13-ac5e-c09cd249ba43");
        DISABILITY_STATUS_TEMPELIG.put("createdDate", getDefaultCreatedModifiedByDate());
        DISABILITY_STATUS_TEMPELIG.put("createdBy", getDefaultCreatedModifiedBy());
        DISABILITY_STATUS_TEMPELIG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        DISABILITY_STATUS_TEMPELIG.put("modifiedBy", getDefaultCreatedModifiedBy());
        DISABILITY_STATUS_TEMPELIG.put("objectStatus", "ACTIVE");
        DISABILITY_STATUS_TEMPELIG.put("name", "Temporary Eligibility");
        DISABILITY_STATUS_TEMPELIG.put("description", "Temporary Eligibility");


        DISABILITY_STATUS_ROWS = new JSONArray();
        DISABILITY_STATUS_ROWS.add(DISABILITY_STATUS_ELIG);
        DISABILITY_STATUS_ROWS.add(DISABILITY_STATUS_INELIG);
        DISABILITY_STATUS_ROWS.add(DISABILITY_STATUS_PEND);
        DISABILITY_STATUS_ROWS.add(DISABILITY_STATUS_TEMPELIG);

        
        DISABILITY_STATUS_RESPONSE = new JSONObject();
        DISABILITY_STATUS_RESPONSE.put("success", "true");
        DISABILITY_STATUS_RESPONSE.put("message", "");
        DISABILITY_STATUS_RESPONSE.put("results", DISABILITY_STATUS_ROWS.size());
        DISABILITY_STATUS_RESPONSE.put("rows", DISABILITY_STATUS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsDisabilityStatusReference() {
        final JSONObject testPostPutNegative = (JSONObject)DISABILITY_STATUS_ELIG.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(DISABILITY_STATUS_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityStatusReferenceAllBody() {

        testResponseBody(DISABILITY_STATUS_PATH, DISABILITY_STATUS_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityStatusReferenceSingleItemBody() {

        testSingleItemResponseBody(DISABILITY_STATUS_PATH, DISABILITY_STATUS_INELIG);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsDisabilityStatusReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, DISABILITY_STATUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityStatusReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) DISABILITY_STATUS_PEND.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(DISABILITY_STATUS_PATH, DISABILITY_STATUS_TEMPELIG.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityStatusReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) DISABILITY_STATUS_TEMPELIG.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = DISABILITY_STATUS_ELIG;

        referenceNegativeSupportedMethodTest(DISABILITY_STATUS_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
