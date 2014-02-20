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


public class VeteranStatusIT extends AbstractReferenceTest {

    private static final String VETERAN_STATUS_PATH = REFERENCE_PATH + "veteranStatus";

    private static final JSONObject VETERAN_STATUS_DEP;
    private static final JSONObject VETERAN_STATUS_NA;
    private static final JSONObject VETERAN_STATUS_CR;
    private static final JSONObject VETERAN_STATUS_VET;
    private static final JSONObject VETERAN_STATUS_VEAP;
    private static final JSONObject VETERAN_STATUS_CAD;

    private static final JSONArray VETERAN_STATUS_ROWS;
    private static final JSONObject VETERAN_STATUS_RESPONSE;

    static {

        VETERAN_STATUS_DEP = new JSONObject();
        VETERAN_STATUS_DEP.put("id", "4b584fdb-dbc8-44ff-a30d-8c3e0a2d8295");
        VETERAN_STATUS_DEP.put("createdBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_DEP.put("sortOrder", 0);
        VETERAN_STATUS_DEP.put("description", "Dependent of Veteran");
        VETERAN_STATUS_DEP.put("modifiedBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_DEP.put("name", "Dependent of Veteran");
        VETERAN_STATUS_DEP.put("objectStatus", "ACTIVE");
        VETERAN_STATUS_DEP.put("createdDate", getDefaultCreatedModifiedByDate());
        VETERAN_STATUS_DEP.put("modifiedDate", getDefaultCreatedModifiedByDate());

        VETERAN_STATUS_NA = new JSONObject();
        VETERAN_STATUS_NA.put("id", "5c584fdb-dcc8-44ff-a30d-8c3e0a2d8206");
        VETERAN_STATUS_NA.put("createdBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_NA.put("sortOrder", -1);
        VETERAN_STATUS_NA.put("description", "");
        VETERAN_STATUS_NA.put("modifiedBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_NA.put("name", "Not applicable");
        VETERAN_STATUS_NA.put("objectStatus", "ACTIVE");
        VETERAN_STATUS_NA.put("createdDate", getDefaultCreatedModifiedByDate());
        VETERAN_STATUS_NA.put("modifiedDate", getDefaultCreatedModifiedByDate());

        VETERAN_STATUS_CR = new JSONObject();
        VETERAN_STATUS_CR.put("id", "7120ff28-67b1-4e44-b9f2-5f663c3c9a9c");
        VETERAN_STATUS_CR.put("createdBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_CR.put("sortOrder", 0);
        VETERAN_STATUS_CR.put("description", " County Reservist");
        VETERAN_STATUS_CR.put("modifiedBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_CR.put("name", "County Reservist");
        VETERAN_STATUS_CR.put("objectStatus", "ACTIVE");
        VETERAN_STATUS_CR.put("createdDate", getDefaultCreatedModifiedByDate());
        VETERAN_STATUS_CR.put("modifiedDate", getDefaultCreatedModifiedByDate());

        VETERAN_STATUS_VET = new JSONObject();
        VETERAN_STATUS_VET.put("id", "8d1bb38c-5670-469d-96a5-b8a79ae7856f");
        VETERAN_STATUS_VET.put("createdBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_VET.put("sortOrder", 0);
        VETERAN_STATUS_VET.put("description", "Veteran");
        VETERAN_STATUS_VET.put("modifiedBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_VET.put("name", "Veteran");
        VETERAN_STATUS_VET.put("objectStatus", "ACTIVE");
        VETERAN_STATUS_VET.put("createdDate", getDefaultCreatedModifiedByDate());
        VETERAN_STATUS_VET.put("modifiedDate", getDefaultCreatedModifiedByDate());

        VETERAN_STATUS_VEAP = new JSONObject();
        VETERAN_STATUS_VEAP.put("id", "8f66c758-aeb7-44e8-b343-48289c051a9b");
        VETERAN_STATUS_VEAP.put("createdBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_VEAP.put("sortOrder", 0);
        VETERAN_STATUS_VEAP.put("description", "VEAP");
        VETERAN_STATUS_VEAP.put("modifiedBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_VEAP.put("name", "VEAP");
        VETERAN_STATUS_VEAP.put("objectStatus", "ACTIVE");
        VETERAN_STATUS_VEAP.put("createdDate", getDefaultCreatedModifiedByDate());
        VETERAN_STATUS_VEAP.put("modifiedDate", getDefaultCreatedModifiedByDate());

        VETERAN_STATUS_CAD = new JSONObject();
        VETERAN_STATUS_CAD.put("id", "b106d9e6-6666-48c8-96e3-4dc0807c0557");
        VETERAN_STATUS_CAD.put("createdBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_CAD.put("sortOrder", 0);
        VETERAN_STATUS_CAD.put("description", " County Active Duty");
        VETERAN_STATUS_CAD.put("modifiedBy", getDefaultCreatedModifiedBy());
        VETERAN_STATUS_CAD.put("name", " County Active Duty");
        VETERAN_STATUS_CAD.put("objectStatus", "ACTIVE");
        VETERAN_STATUS_CAD.put("createdDate", getDefaultCreatedModifiedByDate());
        VETERAN_STATUS_CAD.put("modifiedDate", getDefaultCreatedModifiedByDate());

        VETERAN_STATUS_ROWS = new JSONArray();
        VETERAN_STATUS_ROWS.add(VETERAN_STATUS_CAD);
        VETERAN_STATUS_ROWS.add(VETERAN_STATUS_CR);
        VETERAN_STATUS_ROWS.add(VETERAN_STATUS_DEP);
        VETERAN_STATUS_ROWS.add(VETERAN_STATUS_NA);
        VETERAN_STATUS_ROWS.add(VETERAN_STATUS_VEAP);
        VETERAN_STATUS_ROWS.add(VETERAN_STATUS_VET);

        VETERAN_STATUS_RESPONSE = new JSONObject();
        VETERAN_STATUS_RESPONSE.put("success", "true");
        VETERAN_STATUS_RESPONSE.put("message", "");
        VETERAN_STATUS_RESPONSE.put("results", VETERAN_STATUS_ROWS.size());
        VETERAN_STATUS_RESPONSE.put("rows", VETERAN_STATUS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsVeteranStatusReference() {
        final JSONObject testPostPutNegative = (JSONObject)VETERAN_STATUS_CAD.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(VETERAN_STATUS_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testVeteranStatusReferenceAllBody() {

        testResponseBody(VETERAN_STATUS_PATH, VETERAN_STATUS_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testVeteranStatusReferenceSingleItemBody() {

        testSingleItemResponseBody(VETERAN_STATUS_PATH, VETERAN_STATUS_CR);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsVeteranStatusReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, VETERAN_STATUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testVeteranStatusReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) VETERAN_STATUS_CR.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(VETERAN_STATUS_PATH, VETERAN_STATUS_CAD.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testVeteranStatusReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) VETERAN_STATUS_DEP.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = VETERAN_STATUS_VEAP;

        referenceNegativeSupportedMethodTest(VETERAN_STATUS_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}


