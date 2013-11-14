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


public class SpecialServiceGroupIT extends AbstractReferenceTest {

    private static final String SPECIAL_SERVICE_GROUP_PATH = REFERENCE_PATH + "specialServiceGroup";

    private static final JSONObject SPECIAL_SERVICE_GROUP_PTH;
    private static final JSONObject SPECIAL_SERVICE_GROUP_LEARN;
    private static final JSONObject SPECIAL_SERVICE_GROUP_DEI;
    private static final JSONObject SPECIAL_SERVICE_GROUP_ATD;
    private static final JSONObject SPECIAL_SERVICE_GROUP_STS;
    private static final JSONObject SPECIAL_SERVICE_GROUP_STU;
    private static final JSONObject SPECIAL_SERVICE_GROUP_UAAI;
    private static final JSONObject SPECIAL_SERVICE_GROUP_PSEO;
    private static final JSONObject SPECIAL_SERVICE_GROUP_HON;
    private static final JSONObject SPECIAL_SERVICE_GROUP_DIS;
    private static final JSONObject SPECIAL_SERVICE_GROUP_SA;
    private static final JSONObject SPECIAL_SERVICE_GROUP_DL;

    private static final JSONArray SPECIAL_SERVICE_GROUP_ROWS;
    private static final JSONObject SPECIAL_SERVICE_GROUP_RESPONSE;

    static {

        SPECIAL_SERVICE_GROUP_PTH = new JSONObject();
        SPECIAL_SERVICE_GROUP_PTH.put("id", "0a640a2a-409d-1271-8140-d0bde65200fb");
        SPECIAL_SERVICE_GROUP_PTH.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_PTH.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_PTH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_PTH.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_PTH.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_PTH.put("name", "Pathways to Completion");
        SPECIAL_SERVICE_GROUP_PTH.put("description", "Student participating in Pathways to Completion");

        SPECIAL_SERVICE_GROUP_LEARN = new JSONObject();
        SPECIAL_SERVICE_GROUP_LEARN.put("id", "0a640a2a-409d-1271-8140-d0c0fc9600fc");
        SPECIAL_SERVICE_GROUP_LEARN.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_LEARN.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_LEARN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_LEARN.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_LEARN.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_LEARN.put("name", "Learning Community");
        SPECIAL_SERVICE_GROUP_LEARN.put("description", "Learning Community");

        SPECIAL_SERVICE_GROUP_DEI = new JSONObject();
        SPECIAL_SERVICE_GROUP_DEI.put("id", "0a640a2a-409d-1271-8140-d0c138a100fd");
        SPECIAL_SERVICE_GROUP_DEI.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_DEI.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_DEI.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_DEI.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_DEI.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_DEI.put("name", "DEI Focus Group");
        SPECIAL_SERVICE_GROUP_DEI.put("description", "DEI Focus Group");

        SPECIAL_SERVICE_GROUP_ATD = new JSONObject();
        SPECIAL_SERVICE_GROUP_ATD.put("id", "0a640a2a-409d-1271-8140-d0c1692a00fe");
        SPECIAL_SERVICE_GROUP_ATD.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_ATD.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_ATD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_ATD.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_ATD.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_ATD.put("name", "ATD Pilot");
        SPECIAL_SERVICE_GROUP_ATD.put("description", "ATD Pilot");

        SPECIAL_SERVICE_GROUP_STS = new JSONObject();
        SPECIAL_SERVICE_GROUP_STS.put("id", "0a640a2a-409d-1271-8140-d0c1cdf500ff");
        SPECIAL_SERVICE_GROUP_STS.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_STS.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_STS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_STS.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_STS.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_STS.put("name", "Seniors to Sophmores");
        SPECIAL_SERVICE_GROUP_STS.put("description", "Seniors to Sophmores");

        SPECIAL_SERVICE_GROUP_STU = new JSONObject();
        SPECIAL_SERVICE_GROUP_STU.put("id", "0a640a2a-409d-1271-8140-d0c224f20100");
        SPECIAL_SERVICE_GROUP_STU.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_STU.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_STU.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_STU.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_STU.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_STU.put("name", "State U Dual Admission");
        SPECIAL_SERVICE_GROUP_STU.put("description", "State U Dual Admission");

        SPECIAL_SERVICE_GROUP_UAAI = new JSONObject();
        SPECIAL_SERVICE_GROUP_UAAI.put("id", "0a640a2a-409d-1271-8140-d0c31c810101");
        SPECIAL_SERVICE_GROUP_UAAI.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_UAAI.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_UAAI.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_UAAI.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_UAAI.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_UAAI.put("name", "UAAI");
        SPECIAL_SERVICE_GROUP_UAAI.put("description", "Urban African American Intiative");

        SPECIAL_SERVICE_GROUP_PSEO = new JSONObject();
        SPECIAL_SERVICE_GROUP_PSEO.put("id", "0a640a2a-409d-1271-8140-d0c3ceb80102");
        SPECIAL_SERVICE_GROUP_PSEO.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_PSEO.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_PSEO.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_PSEO.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_PSEO.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_PSEO.put("name", "Pseo");
        SPECIAL_SERVICE_GROUP_PSEO.put("description", "PSEO Student");

        SPECIAL_SERVICE_GROUP_HON = new JSONObject();
        SPECIAL_SERVICE_GROUP_HON.put("id", "40b6b8aa-bca1-11e1-9344-037cb4088c72");
        SPECIAL_SERVICE_GROUP_HON.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_HON.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_HON.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_HON.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_HON.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_HON.put("name", "Honor Student");
        SPECIAL_SERVICE_GROUP_HON.put("description", "Students who are part of the Honors Program");

        SPECIAL_SERVICE_GROUP_DIS = new JSONObject();
        SPECIAL_SERVICE_GROUP_DIS.put("id", "8563dbee-1326-11e2-8e53-406c8f22c3ce");
        SPECIAL_SERVICE_GROUP_DIS.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_DIS.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_DIS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_DIS.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_DIS.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_DIS.put("name", "Displaced Worker");
        SPECIAL_SERVICE_GROUP_DIS.put("description", "This group is typically focused on vocational retraining.");

        SPECIAL_SERVICE_GROUP_SA = new JSONObject();
        SPECIAL_SERVICE_GROUP_SA.put("id", "856a3600-1326-11e2-9b79-406c8f22c3ce");
        SPECIAL_SERVICE_GROUP_SA.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_SA.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_SA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_SA.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_SA.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_SA.put("name", "Student Athlete");
        SPECIAL_SERVICE_GROUP_SA.put("description", "Student Athletes often face significant schedule balancing challenges.");

        SPECIAL_SERVICE_GROUP_DL = new JSONObject();
        SPECIAL_SERVICE_GROUP_DL.put("id", "f6201a04-bb31-4ca5-b606-609f3ad09f87");
        SPECIAL_SERVICE_GROUP_DL.put("createdDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_DL.put("createdBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_DL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SPECIAL_SERVICE_GROUP_DL.put("modifiedBy", getDefaultCreatedModifiedBy());
        SPECIAL_SERVICE_GROUP_DL.put("objectStatus", "ACTIVE");
        SPECIAL_SERVICE_GROUP_DL.put("name", "Distance Learning");
        SPECIAL_SERVICE_GROUP_DL.put("description", "Student is primarily a distance learner");

        SPECIAL_SERVICE_GROUP_ROWS = new JSONArray();
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_ATD);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_DEI);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_DIS);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_DL);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_HON);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_LEARN);
        SPECIAL_SERVICE_GROUP_ROWS.add( SPECIAL_SERVICE_GROUP_PTH);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_PSEO);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_STS);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_STU);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_SA);
        SPECIAL_SERVICE_GROUP_ROWS.add(SPECIAL_SERVICE_GROUP_UAAI);

        SPECIAL_SERVICE_GROUP_RESPONSE = new JSONObject();
        SPECIAL_SERVICE_GROUP_RESPONSE.put("success", "true");
        SPECIAL_SERVICE_GROUP_RESPONSE.put("message", "");
        SPECIAL_SERVICE_GROUP_RESPONSE.put("results", SPECIAL_SERVICE_GROUP_ROWS.size());
        SPECIAL_SERVICE_GROUP_RESPONSE.put("rows", SPECIAL_SERVICE_GROUP_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsSpecialServiceGroupReference() {
        final JSONObject testPostPutNegative = (JSONObject) SPECIAL_SERVICE_GROUP_PTH.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(SPECIAL_SERVICE_GROUP_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSpecialServiceGroupReferenceAllBody() {

        testResponseBody(SPECIAL_SERVICE_GROUP_PATH, SPECIAL_SERVICE_GROUP_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSpecialServiceGroupReferenceSingleItemBody() {

        testSingleItemResponseBody(SPECIAL_SERVICE_GROUP_PATH, SPECIAL_SERVICE_GROUP_LEARN);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsSpecialServiceGroupReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, SPECIAL_SERVICE_GROUP_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSpecialServiceGroupReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) SPECIAL_SERVICE_GROUP_LEARN.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(SPECIAL_SERVICE_GROUP_PATH, SPECIAL_SERVICE_GROUP_ATD.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSpecialServiceGroupReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) SPECIAL_SERVICE_GROUP_STS.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = SPECIAL_SERVICE_GROUP_DEI;

        referenceNegativeSupportedMethodTest(SPECIAL_SERVICE_GROUP_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
