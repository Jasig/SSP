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


public class ProgramStatusIT extends AbstractReferenceTest {

    private static final String PROGRAM_STATUS_PATH = REFERENCE_PATH + "programStatus";

    private static final JSONObject PROGRAM_STATUS_ACTIVE;
    private static final JSONObject PROGRAM_STATUS_INACTIVE;
    private static final JSONObject PROGRAM_STATUS_NONPART;
    private static final JSONObject PROGRAM_STATUS_TRANS;
    private static final JSONObject PROGRAM_STATUS_NOSHW;

    private static final JSONArray PROGRAM_STATUS_ROWS;
    private static final JSONObject PROGRAM_STATUS_RESPONSE;

    static {

        PROGRAM_STATUS_ACTIVE = new JSONObject();
        PROGRAM_STATUS_ACTIVE.put("id", "b2d12527-5056-a51a-8054-113116baab88");
        PROGRAM_STATUS_ACTIVE.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_ACTIVE.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_ACTIVE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_ACTIVE.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_ACTIVE.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_ACTIVE.put("name", "Active");
        PROGRAM_STATUS_ACTIVE.put("description", "Student is active in SSP");
        PROGRAM_STATUS_ACTIVE.put("programStatusChangeReasonRequired", false);

        PROGRAM_STATUS_INACTIVE = new JSONObject();
        PROGRAM_STATUS_INACTIVE.put("id", "b2d125a4-5056-a51a-8042-d50b8eff0df1");
        PROGRAM_STATUS_INACTIVE.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_INACTIVE.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_INACTIVE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_INACTIVE.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_INACTIVE.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_INACTIVE.put("name", "Inactive");
        PROGRAM_STATUS_INACTIVE.put("description", "Student is inactive in SSP");
        PROGRAM_STATUS_INACTIVE.put("programStatusChangeReasonRequired", false);

        PROGRAM_STATUS_NONPART = new JSONObject();
        PROGRAM_STATUS_NONPART.put("id", "b2d125c3-5056-a51a-8004-f1dbabde80c2");
        PROGRAM_STATUS_NONPART.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_NONPART.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_NONPART.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_NONPART.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_NONPART.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_NONPART.put("name", "Non-participating");
        PROGRAM_STATUS_NONPART.put("description", "Student is not participating in ILP program");
        PROGRAM_STATUS_NONPART.put("programStatusChangeReasonRequired", false);

        PROGRAM_STATUS_TRANS = new JSONObject();
        PROGRAM_STATUS_TRANS.put("id", "b2d125e3-5056-a51a-800f-6891bc7d1ddc");
        PROGRAM_STATUS_TRANS.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_TRANS.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_TRANS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_TRANS.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_TRANS.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_TRANS.put("name", "Transitioned");
        PROGRAM_STATUS_TRANS.put("description", "Student has completed the ILP Program.");
        PROGRAM_STATUS_TRANS.put("programStatusChangeReasonRequired", false);

        PROGRAM_STATUS_NOSHW = new JSONObject();
        PROGRAM_STATUS_NOSHW.put("id", "b2d12640-5056-a51a-80cc-91264965731a");
        PROGRAM_STATUS_NOSHW.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_NOSHW.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_NOSHW.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_NOSHW.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_NOSHW.put("objectStatus", "ACTIVE");
        // name is a hack to get consistent alpha sorting across db platforms
        PROGRAM_STATUS_NOSHW.put("name", "Non-show");
        PROGRAM_STATUS_NOSHW.put("description", "Student did not show up for an appointment");
        PROGRAM_STATUS_NOSHW.put("programStatusChangeReasonRequired", false);

        PROGRAM_STATUS_ROWS = new JSONArray();
        PROGRAM_STATUS_ROWS.add(PROGRAM_STATUS_ACTIVE);
        PROGRAM_STATUS_ROWS.add(PROGRAM_STATUS_INACTIVE);
        PROGRAM_STATUS_ROWS.add(PROGRAM_STATUS_NONPART);
        PROGRAM_STATUS_ROWS.add(PROGRAM_STATUS_NOSHW);
        PROGRAM_STATUS_ROWS.add(PROGRAM_STATUS_TRANS);

        PROGRAM_STATUS_RESPONSE = new JSONObject();
        PROGRAM_STATUS_RESPONSE.put("success", "true");
        PROGRAM_STATUS_RESPONSE.put("message", "");
        PROGRAM_STATUS_RESPONSE.put("results", PROGRAM_STATUS_ROWS.size());
        PROGRAM_STATUS_RESPONSE.put("rows", PROGRAM_STATUS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsProgramStatusReference() {
        final JSONObject testPostPutNegative = (JSONObject) PROGRAM_STATUS_ACTIVE.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(PROGRAM_STATUS_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testProgramStatusReferenceAllBody() {

        testResponseBody(PROGRAM_STATUS_PATH, PROGRAM_STATUS_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testProgramStatusReferenceSingleItemBody() {

        testSingleItemResponseBody(PROGRAM_STATUS_PATH, PROGRAM_STATUS_INACTIVE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsProgramStatusReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, PROGRAM_STATUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testProgramStatusReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) PROGRAM_STATUS_INACTIVE.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(PROGRAM_STATUS_PATH, PROGRAM_STATUS_TRANS.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testProgramStatusReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) PROGRAM_STATUS_NOSHW.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = PROGRAM_STATUS_NONPART;

        referenceNegativeSupportedMethodTest(PROGRAM_STATUS_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
