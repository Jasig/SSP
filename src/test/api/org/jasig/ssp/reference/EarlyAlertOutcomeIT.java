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


public class EarlyAlertOutcomeIT extends AbstractReferenceTest {

    private static final String EARLY_ALERT_OUTCOME_PATH = REFERENCE_PATH + "earlyAlertOutcome";

    private static final JSONObject EARLY_ALERT_OUTCOME_DUP;
    private static final JSONObject EARLY_ALERT_OUTCOME_NEAC;
    private static final JSONObject EARLY_ALERT_OUTCOME_SDNR;
    private static final JSONObject EARLY_ALERT_OUTCOME_SR;
    private static final JSONObject EARLY_ALERT_OUTCOME_WAIT;

    private static final JSONArray EARLY_ALERT_OUTCOME_ROWS;
    private static final JSONObject EARLY_ALERT_OUTCOME_RESPONSE;

    static {

        EARLY_ALERT_OUTCOME_DUP = new JSONObject();
        EARLY_ALERT_OUTCOME_DUP.put("id", "d944d62e-0974-4058-85c7-e6b3d6159d73");
        EARLY_ALERT_OUTCOME_DUP.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_DUP.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_DUP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_DUP.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_DUP.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTCOME_DUP.put("name", "Duplicate Early Alert Notice");
        EARLY_ALERT_OUTCOME_DUP.put("description", "Duplicate early alert notice");
        EARLY_ALERT_OUTCOME_DUP.put("sortOrder", 5);

        EARLY_ALERT_OUTCOME_NEAC = new JSONObject();
        EARLY_ALERT_OUTCOME_NEAC.put("id", "14e390d5-2371-48b4-a9de-2d35bc18e868");
        EARLY_ALERT_OUTCOME_NEAC.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_NEAC.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_NEAC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_NEAC.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_NEAC.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTCOME_NEAC.put("name", "Not an Early Alert Class");
        EARLY_ALERT_OUTCOME_NEAC.put("description", "Not an early alert class");
        EARLY_ALERT_OUTCOME_NEAC.put("sortOrder", 4);

        EARLY_ALERT_OUTCOME_SDNR = new JSONObject();
        EARLY_ALERT_OUTCOME_SDNR.put("id", "9a98ff78-92af-4681-8111-adb3300cbe1c");
        EARLY_ALERT_OUTCOME_SDNR.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_SDNR.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_SDNR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_SDNR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_SDNR.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTCOME_SDNR.put("name", "Student Did Not Respond");
        EARLY_ALERT_OUTCOME_SDNR.put("description", "Student did not respond");
        EARLY_ALERT_OUTCOME_SDNR.put("sortOrder", 2);

        EARLY_ALERT_OUTCOME_SR = new JSONObject();
        EARLY_ALERT_OUTCOME_SR.put("id", "12a58804-45dc-40f2-b2f5-d7e4403acee1");
        EARLY_ALERT_OUTCOME_SR.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_SR.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_SR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_SR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_SR.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTCOME_SR.put("name", "Student Responded");
        EARLY_ALERT_OUTCOME_SR.put("description", "Student responded");
        EARLY_ALERT_OUTCOME_SR.put("sortOrder", 1);

        EARLY_ALERT_OUTCOME_WAIT = new JSONObject();
        EARLY_ALERT_OUTCOME_WAIT.put("id", "7148606f-9034-4538-8fc2-c852a5c912ee");
        EARLY_ALERT_OUTCOME_WAIT.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_WAIT.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_WAIT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTCOME_WAIT.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTCOME_WAIT.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTCOME_WAIT.put("name", "Waiting for Response");
        EARLY_ALERT_OUTCOME_WAIT.put("description", "Waiting for response");
        EARLY_ALERT_OUTCOME_WAIT.put("sortOrder", 3);


        EARLY_ALERT_OUTCOME_ROWS = new JSONArray();
        EARLY_ALERT_OUTCOME_ROWS.add(EARLY_ALERT_OUTCOME_DUP);
        EARLY_ALERT_OUTCOME_ROWS.add(EARLY_ALERT_OUTCOME_NEAC);
        EARLY_ALERT_OUTCOME_ROWS.add(EARLY_ALERT_OUTCOME_SDNR);
        EARLY_ALERT_OUTCOME_ROWS.add(EARLY_ALERT_OUTCOME_SR);
        EARLY_ALERT_OUTCOME_ROWS.add(EARLY_ALERT_OUTCOME_WAIT);


        EARLY_ALERT_OUTCOME_RESPONSE = new JSONObject();
        EARLY_ALERT_OUTCOME_RESPONSE.put("success", "true");
        EARLY_ALERT_OUTCOME_RESPONSE.put("message", "");
        EARLY_ALERT_OUTCOME_RESPONSE.put("results", EARLY_ALERT_OUTCOME_ROWS.size());
        EARLY_ALERT_OUTCOME_RESPONSE.put("rows", EARLY_ALERT_OUTCOME_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEarlyAlertOutcomeReference() {
        final JSONObject testPostPutNegative = (JSONObject)EARLY_ALERT_OUTCOME_DUP.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(EARLY_ALERT_OUTCOME_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertOutcomeReferenceAllBody() {

        testResponseBody(EARLY_ALERT_OUTCOME_PATH, EARLY_ALERT_OUTCOME_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertOutcomeReferenceSingleItemBody() {

        testSingleItemResponseBody(EARLY_ALERT_OUTCOME_PATH, EARLY_ALERT_OUTCOME_NEAC);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEarlyAlertOutcomeReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, EARLY_ALERT_OUTCOME_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertOutcomeReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) EARLY_ALERT_OUTCOME_SR.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(EARLY_ALERT_OUTCOME_PATH, EARLY_ALERT_OUTCOME_SR.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertOutcomeReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) EARLY_ALERT_OUTCOME_WAIT.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = EARLY_ALERT_OUTCOME_DUP;

        referenceNegativeSupportedMethodTest(EARLY_ALERT_OUTCOME_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}

