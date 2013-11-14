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


public class ChildCareArrangementIT extends AbstractReferenceTest {

    private static final String CHILD_CARE_ARRANGEMENT_PATH = REFERENCE_PATH + "childCareArrangement";

    private static final JSONObject CHILD_CARE_ARRANGEMENT_DC;
    private static final JSONObject CHILD_CARE_ARRANGEMENT_FAM;
    private static final JSONObject CHILD_CARE_ARRANGEMENT_HOME;
    private static final JSONObject CHILD_CARE_ARRANGEMENT_NEED;

    private static final JSONArray CHILD_CARE_ARRANGEMENT_ROWS;
    private static final JSONObject CHILD_CARE_ARRANGEMENT_RESPONSE;

    static {

        CHILD_CARE_ARRANGEMENT_DC = new JSONObject();
        CHILD_CARE_ARRANGEMENT_DC.put("id", "58828932-d2ec-4c23-a141-36656c61abbd");
        CHILD_CARE_ARRANGEMENT_DC.put("createdBy", getDefaultCreatedModifiedBy());
        CHILD_CARE_ARRANGEMENT_DC.put("description", "Day Care");
        CHILD_CARE_ARRANGEMENT_DC.put("modifiedBy", getDefaultCreatedModifiedBy());
        CHILD_CARE_ARRANGEMENT_DC.put("name", "Day Care");
        CHILD_CARE_ARRANGEMENT_DC.put("objectStatus", "ACTIVE");
        CHILD_CARE_ARRANGEMENT_DC.put("createdDate", getDefaultCreatedModifiedByDate());
        CHILD_CARE_ARRANGEMENT_DC.put("modifiedDate", getDefaultCreatedModifiedByDate());

        CHILD_CARE_ARRANGEMENT_FAM = new JSONObject();
        CHILD_CARE_ARRANGEMENT_FAM.put("id", "b82e644e-5152-493a-8b75-1a5e4f60f316");
        CHILD_CARE_ARRANGEMENT_FAM.put("createdBy", getDefaultCreatedModifiedBy());
        CHILD_CARE_ARRANGEMENT_FAM.put("description", "Family/Friend");
        CHILD_CARE_ARRANGEMENT_FAM.put("modifiedBy", getDefaultCreatedModifiedBy());
        CHILD_CARE_ARRANGEMENT_FAM.put("name", "Family/Friend");
        CHILD_CARE_ARRANGEMENT_FAM.put("objectStatus", "ACTIVE");
        CHILD_CARE_ARRANGEMENT_FAM.put("createdDate", getDefaultCreatedModifiedByDate());
        CHILD_CARE_ARRANGEMENT_FAM.put("modifiedDate", getDefaultCreatedModifiedByDate());

        CHILD_CARE_ARRANGEMENT_HOME = new JSONObject();
        CHILD_CARE_ARRANGEMENT_HOME.put("id", "1157e185-cf72-48f8-9117-3812e6812aed");
        CHILD_CARE_ARRANGEMENT_HOME.put("createdBy", getDefaultCreatedModifiedBy());
        CHILD_CARE_ARRANGEMENT_HOME.put("description", "Home Provider");
        CHILD_CARE_ARRANGEMENT_HOME.put("modifiedBy", getDefaultCreatedModifiedBy());
        CHILD_CARE_ARRANGEMENT_HOME.put("name", "Home Provider");
        CHILD_CARE_ARRANGEMENT_HOME.put("objectStatus", "ACTIVE");
        CHILD_CARE_ARRANGEMENT_HOME.put("createdDate", getDefaultCreatedModifiedByDate());
        CHILD_CARE_ARRANGEMENT_HOME.put("modifiedDate", getDefaultCreatedModifiedByDate());

        CHILD_CARE_ARRANGEMENT_NEED = new JSONObject();
        CHILD_CARE_ARRANGEMENT_NEED.put("id", "3ee60d0a-4ba3-48a7-ac4b-89595bd10636");
        CHILD_CARE_ARRANGEMENT_NEED.put("createdBy", getDefaultCreatedModifiedBy());
        CHILD_CARE_ARRANGEMENT_NEED.put("description", "Need to make arrangements");
        CHILD_CARE_ARRANGEMENT_NEED.put("modifiedBy", getDefaultCreatedModifiedBy());
        CHILD_CARE_ARRANGEMENT_NEED.put("name", "Need to make arrangements");
        CHILD_CARE_ARRANGEMENT_NEED.put("objectStatus", "ACTIVE");
        CHILD_CARE_ARRANGEMENT_NEED.put("createdDate", getDefaultCreatedModifiedByDate());
        CHILD_CARE_ARRANGEMENT_NEED.put("modifiedDate", getDefaultCreatedModifiedByDate());


        CHILD_CARE_ARRANGEMENT_ROWS = new JSONArray();
        CHILD_CARE_ARRANGEMENT_ROWS.add(CHILD_CARE_ARRANGEMENT_DC);
        CHILD_CARE_ARRANGEMENT_ROWS.add(CHILD_CARE_ARRANGEMENT_FAM);
        CHILD_CARE_ARRANGEMENT_ROWS.add(CHILD_CARE_ARRANGEMENT_HOME);
        CHILD_CARE_ARRANGEMENT_ROWS.add(CHILD_CARE_ARRANGEMENT_NEED);


        CHILD_CARE_ARRANGEMENT_RESPONSE = new JSONObject();
        CHILD_CARE_ARRANGEMENT_RESPONSE.put("success", "true");
        CHILD_CARE_ARRANGEMENT_RESPONSE.put("message", "");
        CHILD_CARE_ARRANGEMENT_RESPONSE.put("results", CHILD_CARE_ARRANGEMENT_ROWS.size());
        CHILD_CARE_ARRANGEMENT_RESPONSE.put("rows", CHILD_CARE_ARRANGEMENT_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsChildCareArrangementReference() {
        final JSONObject testPostPutNegative = (JSONObject)CHILD_CARE_ARRANGEMENT_DC.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(CHILD_CARE_ARRANGEMENT_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChildCareArrangementReferenceAllBody() {

        testResponseBody(CHILD_CARE_ARRANGEMENT_PATH, CHILD_CARE_ARRANGEMENT_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChildCareArrangementReferenceSingleItemBody() {

        testSingleItemResponseBody(CHILD_CARE_ARRANGEMENT_PATH, CHILD_CARE_ARRANGEMENT_HOME);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsChildCareArrangementReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CHILD_CARE_ARRANGEMENT_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChildCareArrangementReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) CHILD_CARE_ARRANGEMENT_HOME.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(CHILD_CARE_ARRANGEMENT_PATH, CHILD_CARE_ARRANGEMENT_FAM.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChildCareArrangementReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) CHILD_CARE_ARRANGEMENT_DC.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = CHILD_CARE_ARRANGEMENT_NEED;

        referenceNegativeSupportedMethodTest(CHILD_CARE_ARRANGEMENT_PATH, testNegativePostObject,
                testNegativeValidateObject);
    }

}

