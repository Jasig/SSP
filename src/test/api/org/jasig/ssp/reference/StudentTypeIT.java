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


public class StudentTypeIT extends AbstractReferenceTest {

    private static final String STUDENT_TYPE_PATH = REFERENCE_PATH + "studentType";

    private static final JSONObject STUDENT_TYPE_NONDEG;
    private static final JSONObject STUDENT_TYPE_TRANS;
    private static final JSONObject STUDENT_TYPE_RET;
    private static final JSONObject STUDENT_TYPE_HS;
    private static final JSONObject STUDENT_TYPE_FTIC;
    private static final JSONObject STUDENT_TYPE_EAL;

    private static final JSONArray STUDENT_TYPE_ROWS;
    private static final JSONObject STUDENT_TYPE_RESPONSE;

    static {

        STUDENT_TYPE_NONDEG = new JSONObject();
        STUDENT_TYPE_NONDEG.put("id", "0a640a2a-409d-1271-8140-d0af4a4200ef");
        STUDENT_TYPE_NONDEG.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_NONDEG.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_NONDEG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_NONDEG.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_NONDEG.put("objectStatus", "ACTIVE");
        STUDENT_TYPE_NONDEG.put("name", "Non-Degree Seeking");
        STUDENT_TYPE_NONDEG.put("description", "A student that wishes to take courses but not earn a degree " +
                "with the institution. ");
        STUDENT_TYPE_NONDEG.put("requireInitialAppointment", false);
        STUDENT_TYPE_NONDEG.put("code", "NONDEG");

        STUDENT_TYPE_TRANS = new JSONObject();
        STUDENT_TYPE_TRANS.put("id", "0a640a2a-409d-1271-8140-d0afceae00f1");
        STUDENT_TYPE_TRANS.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_TRANS.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_TRANS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_TRANS.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_TRANS.put("objectStatus", "ACTIVE");
        STUDENT_TYPE_TRANS.put("name", "Transfer");
        STUDENT_TYPE_TRANS.put("description", "A student who has taken college courses from another institution " +
                "but not this institution.");
        STUDENT_TYPE_TRANS.put("requireInitialAppointment", false);
        STUDENT_TYPE_TRANS.put("code", "TRANS");

        STUDENT_TYPE_RET = new JSONObject();
        STUDENT_TYPE_RET.put("id", "0a640a2a-409d-1271-8140-d0b03f9000f2");
        STUDENT_TYPE_RET.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_RET.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_RET.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_RET.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_RET.put("objectStatus", "ACTIVE");
        STUDENT_TYPE_RET.put("name", "Returning");
        STUDENT_TYPE_RET.put("description", "A returning student with more than 1 credit hour at the institution.");
        STUDENT_TYPE_RET.put("requireInitialAppointment", false);
        STUDENT_TYPE_RET.put("code", "RET");

        STUDENT_TYPE_HS = new JSONObject();
        STUDENT_TYPE_HS.put("id", "b2d058eb-5056-a51a-80a7-8a20c30d1e91");
        STUDENT_TYPE_HS.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_HS.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_HS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_HS.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_HS.put("objectStatus", "ACTIVE");
        STUDENT_TYPE_HS.put("name", "H.S. Student");
        STUDENT_TYPE_HS.put("description", "A student currently in high school " +
                "(participating in dual enrollment courses)");
        STUDENT_TYPE_HS.put("requireInitialAppointment", true);
        STUDENT_TYPE_HS.put("code", "HS");

        STUDENT_TYPE_FTIC = new JSONObject();
        STUDENT_TYPE_FTIC.put("id", "b2d05919-5056-a51a-80bd-03e5288de771");
        STUDENT_TYPE_FTIC.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_FTIC.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_FTIC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_FTIC.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_FTIC.put("objectStatus", "ACTIVE");
        STUDENT_TYPE_FTIC.put("name", "First Time in College");
        STUDENT_TYPE_FTIC.put("description", "A new student who hasn't attended an institution");
        STUDENT_TYPE_FTIC.put("requireInitialAppointment", true);
        STUDENT_TYPE_FTIC.put("code", "FTIC");

        STUDENT_TYPE_EAL = new JSONObject();
        STUDENT_TYPE_EAL.put("id", "b2d05939-5056-a51a-8004-d803265d2645");
        STUDENT_TYPE_EAL.put("createdDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_EAL.put("createdBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_EAL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        STUDENT_TYPE_EAL.put("modifiedBy", getDefaultCreatedModifiedBy());
        STUDENT_TYPE_EAL.put("objectStatus", "ACTIVE");
        STUDENT_TYPE_EAL.put("name", "EAL");
        STUDENT_TYPE_EAL.put("description", "this is a student who has an early alert submitted by a faculty member " +
                "but has not been previously entered into SSP");
        STUDENT_TYPE_EAL.put("requireInitialAppointment", false);
        STUDENT_TYPE_EAL.put("code", "EAL");

        STUDENT_TYPE_ROWS = new JSONArray();
        STUDENT_TYPE_ROWS.add(STUDENT_TYPE_EAL);
        STUDENT_TYPE_ROWS.add(STUDENT_TYPE_FTIC);
        STUDENT_TYPE_ROWS.add(STUDENT_TYPE_HS);
        STUDENT_TYPE_ROWS.add(STUDENT_TYPE_NONDEG);
        STUDENT_TYPE_ROWS.add(STUDENT_TYPE_RET);
        STUDENT_TYPE_ROWS.add(STUDENT_TYPE_TRANS);

        STUDENT_TYPE_RESPONSE = new JSONObject();
        STUDENT_TYPE_RESPONSE.put("success", "true");
        STUDENT_TYPE_RESPONSE.put("message", "");
        STUDENT_TYPE_RESPONSE.put("results", STUDENT_TYPE_ROWS.size());
        STUDENT_TYPE_RESPONSE.put("rows", STUDENT_TYPE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsStudentTypeReference() {
        final JSONObject testPostPutNegative = (JSONObject)STUDENT_TYPE_NONDEG.clone();
        testPostPutNegative.put("code", ("NONDEG" + testPassDeConflictNumber));
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(STUDENT_TYPE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testStudentTypeReferenceAllBody() {

        testResponseBody(STUDENT_TYPE_PATH, STUDENT_TYPE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testStudentTypeReferenceSingleItemBody() {

       testSingleItemResponseBody(STUDENT_TYPE_PATH, STUDENT_TYPE_RET);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsStudentTypeReference() {
        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, STUDENT_TYPE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testStudentTypeReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) STUDENT_TYPE_TRANS.clone();
        testPostPutPositive.put("code", ("TRANS" + testPassDeConflictNumber));
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(STUDENT_TYPE_PATH, STUDENT_TYPE_EAL.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testStudentTypeReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) STUDENT_TYPE_HS.clone();
        testNegativePostObject.put("code", ("HS" + testPassDeConflictNumber));
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = STUDENT_TYPE_FTIC;

        referenceNegativeSupportedMethodTest(STUDENT_TYPE_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
