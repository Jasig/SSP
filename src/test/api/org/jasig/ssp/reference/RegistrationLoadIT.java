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


public class RegistrationLoadIT extends AbstractReferenceTest {

    private static final String REGISTRATION_LOAD_PATH = REFERENCE_PATH + "registrationLoad";

    private static final JSONObject REGISTRATION_LOAD_LPTS;
    private static final JSONObject REGISTRATION_LOAD_FTS;
    private static final JSONObject REGISTRATION_LOAD_PTS;

    private static final JSONArray REGISTRATION_LOAD_ROWS;
    private static final JSONObject REGISTRATION_LOAD_RESPONSE;

    static {

        REGISTRATION_LOAD_LPTS = new JSONObject();
        REGISTRATION_LOAD_LPTS.put("id", "7c7df05a-92c2-4806-93c7-5b8d99d42657");
        REGISTRATION_LOAD_LPTS.put("createdDate", getDefaultCreatedModifiedByDate());
        REGISTRATION_LOAD_LPTS.put("createdBy", getDefaultCreatedModifiedBy());
        REGISTRATION_LOAD_LPTS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REGISTRATION_LOAD_LPTS.put("modifiedBy", getDefaultCreatedModifiedBy());
        REGISTRATION_LOAD_LPTS.put("objectStatus", "ACTIVE");
        REGISTRATION_LOAD_LPTS.put("name", "1-6");
        REGISTRATION_LOAD_LPTS.put("description", "Light Part Time Schedule");

        REGISTRATION_LOAD_FTS = new JSONObject();
        REGISTRATION_LOAD_FTS.put("id", "ac85ac8e-90e6-4425-b74e-7e8b0c7bee7a");
        REGISTRATION_LOAD_FTS.put("createdDate", getDefaultCreatedModifiedByDate());
        REGISTRATION_LOAD_FTS.put("createdBy", getDefaultCreatedModifiedBy());
        REGISTRATION_LOAD_FTS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REGISTRATION_LOAD_FTS.put("modifiedBy", getDefaultCreatedModifiedBy());
        REGISTRATION_LOAD_FTS.put("objectStatus", "ACTIVE");
        // this name is a hack to get consistent sort ordering across db platforms
        REGISTRATION_LOAD_FTS.put("name", "0");
        REGISTRATION_LOAD_FTS.put("description", "Full Time Schedule");

        REGISTRATION_LOAD_PTS = new JSONObject();
        REGISTRATION_LOAD_PTS.put("id", "fbd43e12-bd41-437e-85ba-a45ba0e12327");
        REGISTRATION_LOAD_PTS.put("createdDate", getDefaultCreatedModifiedByDate());
        REGISTRATION_LOAD_PTS.put("createdBy", getDefaultCreatedModifiedBy());
        REGISTRATION_LOAD_PTS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REGISTRATION_LOAD_PTS.put("modifiedBy", getDefaultCreatedModifiedBy());
        REGISTRATION_LOAD_PTS.put("objectStatus", "ACTIVE");
        REGISTRATION_LOAD_PTS.put("name", "7-12");
        REGISTRATION_LOAD_PTS.put("description", "Part Time Schedule");

        REGISTRATION_LOAD_ROWS = new JSONArray();
        REGISTRATION_LOAD_ROWS.add(REGISTRATION_LOAD_FTS);
        REGISTRATION_LOAD_ROWS.add(REGISTRATION_LOAD_LPTS);
        REGISTRATION_LOAD_ROWS.add(REGISTRATION_LOAD_PTS);

        REGISTRATION_LOAD_RESPONSE = new JSONObject();
        REGISTRATION_LOAD_RESPONSE.put("success", "true");
        REGISTRATION_LOAD_RESPONSE.put("message", "");
        REGISTRATION_LOAD_RESPONSE.put("results", REGISTRATION_LOAD_ROWS.size());
        REGISTRATION_LOAD_RESPONSE.put("rows", REGISTRATION_LOAD_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsRegistrationLoadReference() {
        final JSONObject testPostPutNegative = (JSONObject)REGISTRATION_LOAD_LPTS.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(REGISTRATION_LOAD_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testRegistrationLoadReferenceAllBody() {

        testResponseBody(REGISTRATION_LOAD_PATH, REGISTRATION_LOAD_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testRegistrationLoadReferenceSingleItemBody() {

       testSingleItemResponseBody(REGISTRATION_LOAD_PATH, REGISTRATION_LOAD_PTS);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsRegistrationLoadReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, REGISTRATION_LOAD_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testRegistrationLoadReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) REGISTRATION_LOAD_FTS.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(REGISTRATION_LOAD_PATH, REGISTRATION_LOAD_LPTS.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testRegistrationLoadReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) REGISTRATION_LOAD_PTS.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = REGISTRATION_LOAD_FTS;

        referenceNegativeSupportedMethodTest(REGISTRATION_LOAD_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
