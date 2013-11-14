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


public class CitizenshipIT extends AbstractReferenceTest {

    private static final String CITIZENSHIP_PATH = REFERENCE_PATH + "citizenship";

    private static final JSONObject CITIZENSHIP_INT;
    private static final JSONObject CITIZENSHIP_NAT;
    private static final JSONObject CITIZENSHIP_PERM;
    private static final JSONObject CITIZENSHIP_US;

    private static final JSONArray CITIZENSHIP_ROWS;
    private static final JSONObject CITIZENSHIP_RESPONSE;

    static {

        CITIZENSHIP_INT = new JSONObject();
        CITIZENSHIP_INT.put("id", "80c7c0b5-54d3-4e08-8102-f522f4c8f1af");
        CITIZENSHIP_INT.put("createdBy", getDefaultCreatedModifiedBy());
        CITIZENSHIP_INT.put("description", "International");
        CITIZENSHIP_INT.put("modifiedBy", getDefaultCreatedModifiedBy());
        CITIZENSHIP_INT.put("name", "International");
        CITIZENSHIP_INT.put("objectStatus", "ACTIVE");
        CITIZENSHIP_INT.put("createdDate", getDefaultCreatedModifiedByDate());
        CITIZENSHIP_INT.put("modifiedDate", getDefaultCreatedModifiedByDate());

        CITIZENSHIP_NAT = new JSONObject();
        CITIZENSHIP_NAT.put("id", "66be33cb-ae0c-444f-8b3c-3e22a4cbc5cb");
        CITIZENSHIP_NAT.put("createdBy", getDefaultCreatedModifiedBy());
        CITIZENSHIP_NAT.put("description", "Naturalized Citizen");
        CITIZENSHIP_NAT.put("modifiedBy", getDefaultCreatedModifiedBy());
        CITIZENSHIP_NAT.put("name", "Naturalized Citizen");
        CITIZENSHIP_NAT.put("objectStatus", "ACTIVE");
        CITIZENSHIP_NAT.put("createdDate", getDefaultCreatedModifiedByDate());
        CITIZENSHIP_NAT.put("modifiedDate", getDefaultCreatedModifiedByDate());

        CITIZENSHIP_PERM = new JSONObject();
        CITIZENSHIP_PERM.put("id", "048459c9-8178-4822-b136-240cba86b5d7");
        CITIZENSHIP_PERM.put("createdBy", getDefaultCreatedModifiedBy());
        CITIZENSHIP_PERM.put("description", "Permanent Resident");
        CITIZENSHIP_PERM.put("modifiedBy", getDefaultCreatedModifiedBy());
        CITIZENSHIP_PERM.put("name", "Permanent Resident");
        CITIZENSHIP_PERM.put("objectStatus", "ACTIVE");
        CITIZENSHIP_PERM.put("createdDate", getDefaultCreatedModifiedByDate());
        CITIZENSHIP_PERM.put("modifiedDate", getDefaultCreatedModifiedByDate());

        CITIZENSHIP_US = new JSONObject();
        CITIZENSHIP_US.put("id", "53c5efe6-c149-45d3-a719-6c8f1ac2e056");
        CITIZENSHIP_US.put("createdBy", getDefaultCreatedModifiedBy());
        CITIZENSHIP_US.put("description", "US Citizen");
        CITIZENSHIP_US.put("modifiedBy", getDefaultCreatedModifiedBy());
        CITIZENSHIP_US.put("name", "US Citizen");
        CITIZENSHIP_US.put("objectStatus", "ACTIVE");
        CITIZENSHIP_US.put("createdDate", getDefaultCreatedModifiedByDate());
        CITIZENSHIP_US.put("modifiedDate", getDefaultCreatedModifiedByDate());


        CITIZENSHIP_ROWS = new JSONArray();
        CITIZENSHIP_ROWS.add(CITIZENSHIP_INT);
        CITIZENSHIP_ROWS.add(CITIZENSHIP_NAT);
        CITIZENSHIP_ROWS.add(CITIZENSHIP_PERM);
        CITIZENSHIP_ROWS.add(CITIZENSHIP_US);


        CITIZENSHIP_RESPONSE = new JSONObject();
        CITIZENSHIP_RESPONSE.put("success", "true");
        CITIZENSHIP_RESPONSE.put("message", "");
        CITIZENSHIP_RESPONSE.put("results", CITIZENSHIP_ROWS.size());
        CITIZENSHIP_RESPONSE.put("rows", CITIZENSHIP_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsCitizenshipReference() {
        final JSONObject testPostPutNegative = (JSONObject)CITIZENSHIP_INT.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(CITIZENSHIP_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCitizenshipReferenceAllBody() {

        testResponseBody(CITIZENSHIP_PATH, CITIZENSHIP_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCitizenshipReferenceSingleItemBody() {

        testSingleItemResponseBody(CITIZENSHIP_PATH, CITIZENSHIP_PERM);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsCitizenshipReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CITIZENSHIP_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCitizenshipReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) CITIZENSHIP_PERM.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(CITIZENSHIP_PATH, CITIZENSHIP_NAT.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCitizenshipReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) CITIZENSHIP_INT.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = CITIZENSHIP_US;

        referenceNegativeSupportedMethodTest(CITIZENSHIP_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}

