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


public class MaritalStatusIT extends AbstractReferenceTest {

    private static final String MARITAL_STATUS_PATH = REFERENCE_PATH + "maritalStatus";

    private static final JSONObject MARITAL_STATUS_WID;
    private static final JSONObject MARITAL_STATUS_SINGL;
    private static final JSONObject MARITAL_STATUS_DIV;
    private static final JSONObject MARITAL_STATUS_SEP;
    private static final JSONObject MARITAL_STATUS_MARR;

    private static final JSONArray MARITAL_STATUS_ROWS;
    private static final JSONObject MARITAL_STATUS_RESPONSE;

    static {

        MARITAL_STATUS_WID = new JSONObject();
        MARITAL_STATUS_WID.put("id", "4e8d1ee2-6862-463f-b3f6-9c96d85873fe");
        MARITAL_STATUS_WID.put("createdDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_WID.put("createdBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_WID.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_WID.put("modifiedBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_WID.put("objectStatus", "ACTIVE");
        MARITAL_STATUS_WID.put("name", "Widowed");
        MARITAL_STATUS_WID.put("description", "Widowed");

        MARITAL_STATUS_DIV = new JSONObject();
        MARITAL_STATUS_DIV.put("id", "a159c9bf-4e94-4f68-874a-3065e2d9651e");
        MARITAL_STATUS_DIV.put("createdDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_DIV.put("createdBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_DIV.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_DIV.put("modifiedBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_DIV.put("objectStatus", "ACTIVE");
        MARITAL_STATUS_DIV.put("name", "Divorced");
        MARITAL_STATUS_DIV.put("description", "Divorced");

        MARITAL_STATUS_SINGL = new JSONObject();
        MARITAL_STATUS_SINGL.put("id", "c1d6598e-622d-428c-b7c2-1c4283d73126");
        MARITAL_STATUS_SINGL.put("createdDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_SINGL.put("createdBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_SINGL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_SINGL.put("modifiedBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_SINGL.put("objectStatus", "ACTIVE");
        MARITAL_STATUS_SINGL.put("name", "Single");
        MARITAL_STATUS_SINGL.put("description", "Single");

        MARITAL_STATUS_MARR = new JSONObject();
        MARITAL_STATUS_MARR.put("id", "c30d2993-0e96-4b70-9fd1-15f83ce281c1");
        MARITAL_STATUS_MARR.put("createdDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_MARR.put("createdBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_MARR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_MARR.put("modifiedBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_MARR.put("objectStatus", "ACTIVE");
        MARITAL_STATUS_MARR.put("name", "Married");
        MARITAL_STATUS_MARR.put("description", "Married");

        MARITAL_STATUS_SEP = new JSONObject();
        MARITAL_STATUS_SEP.put("id", "f415d6c7-9723-42ea-8bb6-9417ca8fec6f");
        MARITAL_STATUS_SEP.put("createdDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_SEP.put("createdBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_SEP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MARITAL_STATUS_SEP.put("modifiedBy", getDefaultCreatedModifiedBy());
        MARITAL_STATUS_SEP.put("objectStatus", "ACTIVE");
        MARITAL_STATUS_SEP.put("name", "Separated");
        MARITAL_STATUS_SEP.put("description", "Separated");

        MARITAL_STATUS_ROWS = new JSONArray();
        MARITAL_STATUS_ROWS.add(MARITAL_STATUS_DIV);
        MARITAL_STATUS_ROWS.add(MARITAL_STATUS_MARR);
        MARITAL_STATUS_ROWS.add(MARITAL_STATUS_SEP);
        MARITAL_STATUS_ROWS.add(MARITAL_STATUS_SINGL);
        MARITAL_STATUS_ROWS.add(MARITAL_STATUS_WID);

        MARITAL_STATUS_RESPONSE = new JSONObject();
        MARITAL_STATUS_RESPONSE.put("success", "true");
        MARITAL_STATUS_RESPONSE.put("message", "");
        MARITAL_STATUS_RESPONSE.put("results", MARITAL_STATUS_ROWS.size());
        MARITAL_STATUS_RESPONSE.put("rows", MARITAL_STATUS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsMaritalStatusReference() {
        final JSONObject testPostPutNegative = (JSONObject)MARITAL_STATUS_WID.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(MARITAL_STATUS_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMaritalStatusReferenceAllBody() {

        testResponseBody(MARITAL_STATUS_PATH, MARITAL_STATUS_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMaritalStatusReferenceSingleItemBody() {

        testSingleItemResponseBody(MARITAL_STATUS_PATH, MARITAL_STATUS_SINGL);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsMaritalStatusReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, MARITAL_STATUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMaritalStatusReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) MARITAL_STATUS_SINGL.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(MARITAL_STATUS_PATH, MARITAL_STATUS_SEP.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMaritalStatusReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) MARITAL_STATUS_MARR.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = MARITAL_STATUS_DIV;

        referenceNegativeSupportedMethodTest(MARITAL_STATUS_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
