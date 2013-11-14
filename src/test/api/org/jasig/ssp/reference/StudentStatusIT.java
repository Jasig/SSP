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


public class StudentStatusIT extends AbstractReferenceTest {

    private static final String STUDENT_STATUS_PATH = REFERENCE_PATH + "studentStatus";

    private static final JSONObject STUDENT_STATUS_NEW;
    private static final JSONObject STUDENT_STATUS_PRE;
    private static final JSONObject STUDENT_STATUS_TRANS;
    private static final JSONObject STUDENT_STATUS_FORMER;
    private static final JSONObject STUDENT_STATUS_CURRENT;

    private static final JSONArray STUDENT_STATUS_ROWS;
    private static final JSONObject STUDENT_STATUS_RESPONSE;

    static {

        STUDENT_STATUS_NEW = new JSONObject();
        STUDENT_STATUS_NEW.put("id", "0b150cea-c3de-40ef-8564-fc2f53847a43");
        STUDENT_STATUS_NEW.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_NEW.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_NEW.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_NEW.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_NEW.put("objectStatus", "ACTIVE");
        STUDENT_STATUS_NEW.put("name", "New");
        STUDENT_STATUS_NEW.put("description", "New");

        STUDENT_STATUS_PRE = new JSONObject();
        STUDENT_STATUS_PRE.put("id", "4e73f0e0-ea42-485f-9b9a-d3af38d8f696");
        STUDENT_STATUS_PRE.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_PRE.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_PRE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_PRE.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_PRE.put("objectStatus", "ACTIVE");
        STUDENT_STATUS_PRE.put("name", "Pre-College");
        STUDENT_STATUS_PRE.put("description", "Pre-College");

        STUDENT_STATUS_TRANS = new JSONObject();
        STUDENT_STATUS_TRANS.put("id", "c640a23e-7ebf-4d0b-8e68-295f05982401");
        STUDENT_STATUS_TRANS.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_TRANS.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_TRANS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_TRANS.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_TRANS.put("objectStatus", "ACTIVE");
        STUDENT_STATUS_TRANS.put("name", "Transfer");
        STUDENT_STATUS_TRANS.put("description", "Transfer");

        STUDENT_STATUS_FORMER = new JSONObject();
        STUDENT_STATUS_FORMER.put("id", "c9e10b4a-6855-4a77-860f-979f8a3fd1eb");
        STUDENT_STATUS_FORMER.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_FORMER.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_FORMER.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_FORMER.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_FORMER.put("objectStatus", "ACTIVE");
        STUDENT_STATUS_FORMER.put("name", "Former");
        STUDENT_STATUS_FORMER.put("description", "Former");

        STUDENT_STATUS_CURRENT = new JSONObject();
        STUDENT_STATUS_CURRENT.put("id", "eac2ecfc-6c32-45ed-b70d-0b23b06cfa74");
        STUDENT_STATUS_CURRENT.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_CURRENT.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_CURRENT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_STATUS_CURRENT.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_STATUS_CURRENT.put("objectStatus", "ACTIVE");
        STUDENT_STATUS_CURRENT.put("name", "Current");
        STUDENT_STATUS_CURRENT.put("description", "Current");


        STUDENT_STATUS_ROWS = new JSONArray();
        STUDENT_STATUS_ROWS.add(STUDENT_STATUS_CURRENT);
        STUDENT_STATUS_ROWS.add(STUDENT_STATUS_FORMER);
        STUDENT_STATUS_ROWS.add(STUDENT_STATUS_NEW);
        STUDENT_STATUS_ROWS.add(STUDENT_STATUS_PRE);
        STUDENT_STATUS_ROWS.add(STUDENT_STATUS_TRANS);

        STUDENT_STATUS_RESPONSE = new JSONObject();
        STUDENT_STATUS_RESPONSE.put("success", "true");
        STUDENT_STATUS_RESPONSE.put("message", "");
        STUDENT_STATUS_RESPONSE.put("results", STUDENT_STATUS_ROWS.size());
        STUDENT_STATUS_RESPONSE.put("rows", STUDENT_STATUS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsStudentStatusReference() {
        final JSONObject testPostPutNegative = (JSONObject)STUDENT_STATUS_NEW.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(STUDENT_STATUS_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testStudentStatusReferenceAllBody() {

        testResponseBody(STUDENT_STATUS_PATH, STUDENT_STATUS_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testStudentStatusReferenceSingleItemBody() {

        testSingleItemResponseBody(STUDENT_STATUS_PATH, STUDENT_STATUS_PRE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsStudentStatusReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, STUDENT_STATUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testStudentStatusReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) STUDENT_STATUS_PRE.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(STUDENT_STATUS_PATH, STUDENT_STATUS_FORMER.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testStudentStatusReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) STUDENT_STATUS_CURRENT.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = STUDENT_STATUS_TRANS;

        referenceNegativeSupportedMethodTest(STUDENT_STATUS_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
