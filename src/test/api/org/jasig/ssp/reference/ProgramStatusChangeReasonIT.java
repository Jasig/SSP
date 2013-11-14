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


public class ProgramStatusChangeReasonIT extends AbstractReferenceTest {

    private static final String PROGRAM_STATUS_CHANGE_REASON_PATH = REFERENCE_PATH + "programStatusChangeReason";

    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_FUTPB;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_FUTPT;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_CHILD;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_TRANS;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_PERS;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_MED;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_TRNSFR;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_SRVNN;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_MOV;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_UNKNWN;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_MIL;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_DEATH;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_EMPLOY;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_PAP;

    private static final JSONArray PROGRAM_STATUS_CHANGE_REASON_ROWS;
    private static final JSONObject PROGRAM_STATUS_CHANGE_REASON_RESPONSE;

    static {

        PROGRAM_STATUS_CHANGE_REASON_FUTPB = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_FUTPB.put("id", "b2d1271b-5056-a51a-8018-f5ed8477b481");
        PROGRAM_STATUS_CHANGE_REASON_FUTPB.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_FUTPB.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_FUTPB.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_FUTPB.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_FUTPB.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_FUTPB.put("name", "Financially unable to purchase books");
        PROGRAM_STATUS_CHANGE_REASON_FUTPB.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_FUTPT = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_FUTPT.put("id", "b2d12798-5056-a51a-8072-da587a14081f");
        PROGRAM_STATUS_CHANGE_REASON_FUTPT.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_FUTPT.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_FUTPT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_FUTPT.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_FUTPT.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_FUTPT.put("name", "Financially unable to pay for tuition");
        PROGRAM_STATUS_CHANGE_REASON_FUTPT.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_CHILD = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_CHILD.put("id", "b2d127b7-5056-a51a-8084-e0468053cc30");
        PROGRAM_STATUS_CHANGE_REASON_CHILD.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_CHILD.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_CHILD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_CHILD.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_CHILD.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_CHILD.put("name", "Childcare issues");
        PROGRAM_STATUS_CHANGE_REASON_CHILD.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_TRANS = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_TRANS.put("id", "b2d127d7-5056-a51a-802d-3717c4316d29");
        PROGRAM_STATUS_CHANGE_REASON_TRANS.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_TRANS.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_TRANS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_TRANS.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_TRANS.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_TRANS.put("name", "Transportation issues");
        PROGRAM_STATUS_CHANGE_REASON_TRANS.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_PERS = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_PERS.put("id", "b2d12844-5056-a51a-80c7-7059b24ccbce");
        PROGRAM_STATUS_CHANGE_REASON_PERS.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_PERS.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_PERS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_PERS.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_PERS.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_PERS.put("name", "Personal issues");
        PROGRAM_STATUS_CHANGE_REASON_PERS.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_MED = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_MED.put("id", "b2d12863-5056-a51a-808a-1241d680a42f");
        PROGRAM_STATUS_CHANGE_REASON_MED.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_MED.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_MED.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_MED.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_MED.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_MED.put("name", "Medical issues");
        PROGRAM_STATUS_CHANGE_REASON_MED.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_TRNSFR = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_TRNSFR.put("id", "b2d12873-5056-a51a-805c-99222defc6d5");
        PROGRAM_STATUS_CHANGE_REASON_TRNSFR.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_TRNSFR.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_TRNSFR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_TRNSFR.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_TRNSFR.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_TRNSFR.put("name", "Transferred to another school");
        PROGRAM_STATUS_CHANGE_REASON_TRNSFR.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_SRVNN = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_SRVNN.put("id", "b2d12883-5056-a51a-80a8-bb4616af9bd0");
        PROGRAM_STATUS_CHANGE_REASON_SRVNN.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_SRVNN.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_SRVNN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_SRVNN.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_SRVNN.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_SRVNN.put("name", "Services not needed");
        PROGRAM_STATUS_CHANGE_REASON_SRVNN.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_MOV = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_MOV.put("id", "b2d128f0-5056-a51a-803f-8cef57177aea");
        PROGRAM_STATUS_CHANGE_REASON_MOV.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_MOV.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_MOV.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_MOV.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_MOV.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_MOV.put("name", "Moving");
        PROGRAM_STATUS_CHANGE_REASON_MOV.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_UNKNWN = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_UNKNWN.put("id", "b2d1290f-5056-a51a-8094-3b321a901899");
        PROGRAM_STATUS_CHANGE_REASON_UNKNWN.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_UNKNWN.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_UNKNWN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_UNKNWN.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_UNKNWN.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_UNKNWN.put("name", "Unknown/unable to contact");
        PROGRAM_STATUS_CHANGE_REASON_UNKNWN.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_MIL = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_MIL.put("id", "b2d1295d-5056-a51a-80bb-3094bab8f9c4");
        PROGRAM_STATUS_CHANGE_REASON_MIL.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_MIL.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_MIL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_MIL.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_MIL.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_MIL.put("name", "Military Service");
        PROGRAM_STATUS_CHANGE_REASON_MIL.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_DEATH = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_DEATH.put("id", "b2d1299c-5056-a51a-8052-7e123c180661");
        PROGRAM_STATUS_CHANGE_REASON_DEATH.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_DEATH.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_DEATH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_DEATH.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_DEATH.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_DEATH.put("name", "Death");
        PROGRAM_STATUS_CHANGE_REASON_DEATH.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_EMPLOY = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_EMPLOY.put("id", "b2d129bb-5056-a51a-8082-5e6613ad6f5d");
        PROGRAM_STATUS_CHANGE_REASON_EMPLOY.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_EMPLOY.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_EMPLOY.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_EMPLOY.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_EMPLOY.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_EMPLOY.put("name", "Employment interferes with school");
        PROGRAM_STATUS_CHANGE_REASON_EMPLOY.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_PAP = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_PAP.put("id", "b2d129cb-5056-a51a-8035-ff5834c72153");
        PROGRAM_STATUS_CHANGE_REASON_PAP.put("createdDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_PAP.put("createdBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_PAP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PROGRAM_STATUS_CHANGE_REASON_PAP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PROGRAM_STATUS_CHANGE_REASON_PAP.put("objectStatus", "ACTIVE");
        PROGRAM_STATUS_CHANGE_REASON_PAP.put("name", "Poor academic performance (Dropout)");
        PROGRAM_STATUS_CHANGE_REASON_PAP.put("description", "Default description");

        PROGRAM_STATUS_CHANGE_REASON_ROWS = new JSONArray();
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_CHILD);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_DEATH);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_EMPLOY);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_FUTPT);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_FUTPB);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_MED);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_MIL);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_MOV);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_PERS);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_PAP);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_SRVNN);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_TRNSFR);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_TRANS);
        PROGRAM_STATUS_CHANGE_REASON_ROWS.add(PROGRAM_STATUS_CHANGE_REASON_UNKNWN);

        PROGRAM_STATUS_CHANGE_REASON_RESPONSE = new JSONObject();
        PROGRAM_STATUS_CHANGE_REASON_RESPONSE.put("success", "true");
        PROGRAM_STATUS_CHANGE_REASON_RESPONSE.put("message", "");
        PROGRAM_STATUS_CHANGE_REASON_RESPONSE.put("results", PROGRAM_STATUS_CHANGE_REASON_ROWS.size());
        PROGRAM_STATUS_CHANGE_REASON_RESPONSE.put("rows", PROGRAM_STATUS_CHANGE_REASON_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsProgramStatusChangeReasonReference() {
        final JSONObject testPostPutNegative = (JSONObject) PROGRAM_STATUS_CHANGE_REASON_FUTPB.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(PROGRAM_STATUS_CHANGE_REASON_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testProgramStatusChangeReasonReferenceAllBody() {

        testResponseBody(PROGRAM_STATUS_CHANGE_REASON_PATH, PROGRAM_STATUS_CHANGE_REASON_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testProgramStatusChangeReasonReferenceSingleItemBody() {

        testSingleItemResponseBody(PROGRAM_STATUS_CHANGE_REASON_PATH, PROGRAM_STATUS_CHANGE_REASON_FUTPT);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsProgramStatusChangeReasonReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, PROGRAM_STATUS_CHANGE_REASON_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testProgramStatusChangeReasonReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) PROGRAM_STATUS_CHANGE_REASON_FUTPT.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(PROGRAM_STATUS_CHANGE_REASON_PATH, PROGRAM_STATUS_CHANGE_REASON_TRANS.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testProgramStatusChangeReasonReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) PROGRAM_STATUS_CHANGE_REASON_PERS.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = PROGRAM_STATUS_CHANGE_REASON_CHILD;

        referenceNegativeSupportedMethodTest(PROGRAM_STATUS_CHANGE_REASON_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
