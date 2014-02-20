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


public class EarlyAlertOutreachIT extends AbstractReferenceTest {

    private static final String EARLY_ALERT_OUTREACH_PATH = REFERENCE_PATH + "earlyAlertOutreach";

    private static final JSONObject EARLY_ALERT_OUTREACH_EMAIL;
    private static final JSONObject EARLY_ALERT_OUTREACH_INPERS;
    private static final JSONObject EARLY_ALERT_OUTREACH_IM;
    private static final JSONObject EARLY_ALERT_OUTREACH_LETTR;
    private static final JSONObject EARLY_ALERT_OUTREACH_PHONE;
    private static final JSONObject EARLY_ALERT_OUTREACH_MSG;
    private static final JSONObject EARLY_ALERT_OUTREACH_TXT;

    private static final JSONArray EARLY_ALERT_OUTREACH_ROWS;
    private static final JSONObject EARLY_ALERT_OUTREACH_RESPONSE;

    static {

        EARLY_ALERT_OUTREACH_EMAIL = new JSONObject();
        EARLY_ALERT_OUTREACH_EMAIL.put("id", "3383d46f-8051-4a86-886d-a3efe75b8f3a");
        EARLY_ALERT_OUTREACH_EMAIL.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_EMAIL.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_EMAIL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_EMAIL.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_EMAIL.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTREACH_EMAIL.put("name", "Email");
        EARLY_ALERT_OUTREACH_EMAIL.put("description", "Email");
        EARLY_ALERT_OUTREACH_EMAIL.put("sortOrder", 10);

        EARLY_ALERT_OUTREACH_INPERS = new JSONObject();
        EARLY_ALERT_OUTREACH_INPERS.put("id", "612ed2c5-6d9a-4cda-9007-b22756888ca8");
        EARLY_ALERT_OUTREACH_INPERS.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_INPERS.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_INPERS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_INPERS.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_INPERS.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTREACH_INPERS.put("name", "In Person");
        EARLY_ALERT_OUTREACH_INPERS.put("description", "In person");
        EARLY_ALERT_OUTREACH_INPERS.put("sortOrder", 25);

        EARLY_ALERT_OUTREACH_IM = new JSONObject();
        EARLY_ALERT_OUTREACH_IM.put("id", "0a640a2a-40e7-15de-8140-e9e372950024");
        EARLY_ALERT_OUTREACH_IM.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_IM.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_IM.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_IM.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_IM.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTREACH_IM.put("name", "Instant Message");
        EARLY_ALERT_OUTREACH_IM.put("description", "");
        EARLY_ALERT_OUTREACH_IM.put("sortOrder", 0);

        EARLY_ALERT_OUTREACH_LETTR = new JSONObject();
        EARLY_ALERT_OUTREACH_LETTR.put("id", "ba387302-b08b-4dc2-bd3f-aefc2fc8d092");
        EARLY_ALERT_OUTREACH_LETTR.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_LETTR.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_LETTR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_LETTR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_LETTR.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTREACH_LETTR.put("name", "Letter");
        EARLY_ALERT_OUTREACH_LETTR.put("description", "Letter");
        EARLY_ALERT_OUTREACH_LETTR.put("sortOrder", 15);

        EARLY_ALERT_OUTREACH_PHONE = new JSONObject();
        EARLY_ALERT_OUTREACH_PHONE.put("id", "9842eff0-6557-4fb2-81c2-614991d5cbfb");
        EARLY_ALERT_OUTREACH_PHONE.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_PHONE.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_PHONE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_PHONE.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_PHONE.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTREACH_PHONE.put("name", "Phone Call");
        EARLY_ALERT_OUTREACH_PHONE.put("description", "Phone call");
        EARLY_ALERT_OUTREACH_PHONE.put("sortOrder", 5);

        EARLY_ALERT_OUTREACH_MSG = new JSONObject();
        EARLY_ALERT_OUTREACH_MSG.put("id", "0a640a2a-40e7-15de-8140-e9e34f500023");
        EARLY_ALERT_OUTREACH_MSG.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_MSG.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_MSG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_MSG.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_MSG.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTREACH_MSG.put("name", "Phone Message");
        EARLY_ALERT_OUTREACH_MSG.put("description", "");
        EARLY_ALERT_OUTREACH_MSG.put("sortOrder", 0);

        EARLY_ALERT_OUTREACH_TXT = new JSONObject();
        EARLY_ALERT_OUTREACH_TXT.put("id", "e7908476-e67d-4fb2-890b-2d4e6c9b0e42");
        EARLY_ALERT_OUTREACH_TXT.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_TXT.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_TXT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_OUTREACH_TXT.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_OUTREACH_TXT.put("objectStatus", "ACTIVE");
        EARLY_ALERT_OUTREACH_TXT.put("name", "Text");
        EARLY_ALERT_OUTREACH_TXT.put("description", "Text");
        EARLY_ALERT_OUTREACH_TXT.put("sortOrder", 20);

        EARLY_ALERT_OUTREACH_ROWS = new JSONArray();
        EARLY_ALERT_OUTREACH_ROWS.add(EARLY_ALERT_OUTREACH_EMAIL);
        EARLY_ALERT_OUTREACH_ROWS.add(EARLY_ALERT_OUTREACH_INPERS);
        EARLY_ALERT_OUTREACH_ROWS.add(EARLY_ALERT_OUTREACH_IM);
        EARLY_ALERT_OUTREACH_ROWS.add(EARLY_ALERT_OUTREACH_LETTR);
        EARLY_ALERT_OUTREACH_ROWS.add(EARLY_ALERT_OUTREACH_PHONE);
        EARLY_ALERT_OUTREACH_ROWS.add(EARLY_ALERT_OUTREACH_MSG);
        EARLY_ALERT_OUTREACH_ROWS.add(EARLY_ALERT_OUTREACH_TXT);


        EARLY_ALERT_OUTREACH_RESPONSE = new JSONObject();
        EARLY_ALERT_OUTREACH_RESPONSE.put("success", "true");
        EARLY_ALERT_OUTREACH_RESPONSE.put("message", "");
        EARLY_ALERT_OUTREACH_RESPONSE.put("results", EARLY_ALERT_OUTREACH_ROWS.size());
        EARLY_ALERT_OUTREACH_RESPONSE.put("rows", EARLY_ALERT_OUTREACH_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEarlyAlertOutreachReference() {
        final JSONObject testPostPutNegative = (JSONObject)EARLY_ALERT_OUTREACH_EMAIL.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(EARLY_ALERT_OUTREACH_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertOutreachReferenceAllBody() {

        testResponseBody(EARLY_ALERT_OUTREACH_PATH, EARLY_ALERT_OUTREACH_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertOutreachReferenceSingleItemBody() {

        testSingleItemResponseBody(EARLY_ALERT_OUTREACH_PATH, EARLY_ALERT_OUTREACH_INPERS);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEarlyAlertOutreachReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, EARLY_ALERT_OUTREACH_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertOutreachReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) EARLY_ALERT_OUTREACH_LETTR.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(EARLY_ALERT_OUTREACH_PATH, EARLY_ALERT_OUTREACH_LETTR.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertOutreachReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) EARLY_ALERT_OUTREACH_PHONE.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = EARLY_ALERT_OUTREACH_TXT;

        referenceNegativeSupportedMethodTest(EARLY_ALERT_OUTREACH_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}

