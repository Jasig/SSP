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


public class MilitaryAffiliationIT extends AbstractReferenceTest {

    private static final String MILITARY_AFFILIATION_PATH = REFERENCE_PATH + "militaryAffiliation";

    private static final JSONObject MILITARY_AFFILIATION_VET;
    private static final JSONObject MILITARY_AFFILIATION_DEPDNT;
    private static final JSONObject MILITARY_AFFILIATION_DODCIV;
    private static final JSONObject MILITARY_AFFILIATION_ACTIVE;
    private static final JSONObject MILITARY_AFFILIATION_RESRVST;

    private static final JSONArray MILITARY_AFFILIATION_ROWS;
    private static final JSONObject MILITARY_AFFILIATION_RESPONSE;

    static {

        MILITARY_AFFILIATION_VET = new JSONObject();
        MILITARY_AFFILIATION_VET.put("id", "7c7df05a-92c2-4806-93c7-5b8d99d42657");
        MILITARY_AFFILIATION_VET.put("createdDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_VET.put("createdBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_VET.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_VET.put("modifiedBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_VET.put("objectStatus", "ACTIVE");
        MILITARY_AFFILIATION_VET.put("name", "Veteran");
        MILITARY_AFFILIATION_VET.put("description", "Veteran");

        MILITARY_AFFILIATION_DEPDNT = new JSONObject();
        MILITARY_AFFILIATION_DEPDNT.put("id", "9dbfee85-2a67-489e-9e23-2ec4ff161543");
        MILITARY_AFFILIATION_DEPDNT.put("createdDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_DEPDNT.put("createdBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_DEPDNT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_DEPDNT.put("modifiedBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_DEPDNT.put("objectStatus", "ACTIVE");
        MILITARY_AFFILIATION_DEPDNT.put("name", "Dependent");
        MILITARY_AFFILIATION_DEPDNT.put("description", "Dependent");

        MILITARY_AFFILIATION_DODCIV = new JSONObject();
        MILITARY_AFFILIATION_DODCIV.put("id", "ac85ac8e-90e6-4425-b74e-7e8b0c7bee7a");
        MILITARY_AFFILIATION_DODCIV.put("createdDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_DODCIV.put("createdBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_DODCIV.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_DODCIV.put("modifiedBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_DODCIV.put("objectStatus", "ACTIVE");
        // partially lower-cased in a departure from the canonical casing in
        // order to avoid platform-specific sorting inconsistencies
        MILITARY_AFFILIATION_DODCIV.put("name", "Dod Civilian");
        MILITARY_AFFILIATION_DODCIV.put("description", "DOD Civilian");

        MILITARY_AFFILIATION_ACTIVE = new JSONObject();
        MILITARY_AFFILIATION_ACTIVE.put("id", "fbd43e12-bd41-437e-85ba-a45ba0e12327");
        MILITARY_AFFILIATION_ACTIVE.put("createdDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_ACTIVE.put("createdBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_ACTIVE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_ACTIVE.put("modifiedBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_ACTIVE.put("objectStatus", "ACTIVE");
        MILITARY_AFFILIATION_ACTIVE.put("name", "Active Duty");
        MILITARY_AFFILIATION_ACTIVE.put("description", "Active Duty");

        MILITARY_AFFILIATION_RESRVST = new JSONObject();
        MILITARY_AFFILIATION_RESRVST.put("id", "fe48d1d0-c6bd-4c85-9e4d-e9f817e08be3");
        MILITARY_AFFILIATION_RESRVST.put("createdDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_RESRVST.put("createdBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_RESRVST.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MILITARY_AFFILIATION_RESRVST.put("modifiedBy", getDefaultCreatedModifiedBy());
        MILITARY_AFFILIATION_RESRVST.put("objectStatus", "ACTIVE");
        MILITARY_AFFILIATION_RESRVST.put("name", "Reservist");
        MILITARY_AFFILIATION_RESRVST.put("description", "Reservist");


        MILITARY_AFFILIATION_ROWS = new JSONArray();
        MILITARY_AFFILIATION_ROWS.add(MILITARY_AFFILIATION_ACTIVE);
        MILITARY_AFFILIATION_ROWS.add(MILITARY_AFFILIATION_DEPDNT);
        MILITARY_AFFILIATION_ROWS.add(MILITARY_AFFILIATION_DODCIV);
        MILITARY_AFFILIATION_ROWS.add(MILITARY_AFFILIATION_RESRVST);
        MILITARY_AFFILIATION_ROWS.add(MILITARY_AFFILIATION_VET);


        MILITARY_AFFILIATION_RESPONSE = new JSONObject();
        MILITARY_AFFILIATION_RESPONSE.put("success", "true");
        MILITARY_AFFILIATION_RESPONSE.put("message", "");
        MILITARY_AFFILIATION_RESPONSE.put("results", MILITARY_AFFILIATION_ROWS.size());
        MILITARY_AFFILIATION_RESPONSE.put("rows", MILITARY_AFFILIATION_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsMilitaryAffiliationReference() {
        final JSONObject testPostPutNegative = (JSONObject)MILITARY_AFFILIATION_VET.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(MILITARY_AFFILIATION_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMilitaryAffiliationReferenceAllBody() {

        testResponseBody(MILITARY_AFFILIATION_PATH, MILITARY_AFFILIATION_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMilitaryAffiliationReferenceSingleItemBody() {

        testSingleItemResponseBody(MILITARY_AFFILIATION_PATH, MILITARY_AFFILIATION_DEPDNT);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsMilitaryAffiliationReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, MILITARY_AFFILIATION_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMilitaryAffiliationReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) MILITARY_AFFILIATION_DEPDNT.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(MILITARY_AFFILIATION_PATH, MILITARY_AFFILIATION_ACTIVE.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMilitaryAffiliationReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) MILITARY_AFFILIATION_RESRVST.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = MILITARY_AFFILIATION_DODCIV;

        referenceNegativeSupportedMethodTest(MILITARY_AFFILIATION_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
