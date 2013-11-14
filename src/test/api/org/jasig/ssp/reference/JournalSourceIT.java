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


public class JournalSourceIT extends AbstractReferenceTest {

    private static final String JOURNAL_SOURCE_PATH = REFERENCE_PATH + "journalSource";

    private static final JSONObject JOURNAL_SOURCE_APPT;
    private static final JSONObject JOURNAL_SOURCE_ASSESS;
    private static final JSONObject JOURNAL_SOURCE_CASEMNG;
    private static final JSONObject JOURNAL_SOURCE_CACTSTPS;
    private static final JSONObject JOURNAL_SOURCE_EARSPNSE;
    private static final JSONObject JOURNAL_SOURCE_EMAIL;
    private static final JSONObject JOURNAL_SOURCE_FACCONT;
    private static final JSONObject JOURNAL_SOURCE_PHNCALL;
    private static final JSONObject JOURNAL_SOURCE_REFRL;
    private static final JSONObject JOURNAL_SOURCE_STUDJUD;
    private static final JSONObject JOURNAL_SOURCE_TXTMSG;

    private static final JSONArray JOURNAL_SOURCE_ROWS;
    private static final JSONObject JOURNAL_SOURCE_RESPONSE;

    static {

        JOURNAL_SOURCE_APPT = new JSONObject();
        JOURNAL_SOURCE_APPT.put("id", "b2d07973-5056-a51a-8073-1d3641ce507f");
        JOURNAL_SOURCE_APPT.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_APPT.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_APPT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_APPT.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_APPT.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_APPT.put("name", "Appointment");
        JOURNAL_SOURCE_APPT.put("description", "Appointment");

        JOURNAL_SOURCE_ASSESS = new JSONObject();
        JOURNAL_SOURCE_ASSESS.put("id", "0a640a2a-409d-1271-8140-d11b26280143");
        JOURNAL_SOURCE_ASSESS.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_ASSESS.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_ASSESS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_ASSESS.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_ASSESS.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_ASSESS.put("name", "Assessment or Data");
        JOURNAL_SOURCE_ASSESS.put("description", "Assessment or Data");

        JOURNAL_SOURCE_CASEMNG = new JSONObject();
        JOURNAL_SOURCE_CASEMNG.put("id", "b2d07993-5056-a51a-8076-d6513a772d80");
        JOURNAL_SOURCE_CASEMNG.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_CASEMNG.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_CASEMNG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_CASEMNG.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_CASEMNG.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_CASEMNG.put("name", "Case Management");
        JOURNAL_SOURCE_CASEMNG.put("description", "Case Management");

        JOURNAL_SOURCE_CACTSTPS = new JSONObject();
        JOURNAL_SOURCE_CACTSTPS.put("id", "b2d07a10-5056-a51a-80c2-2dbb2a81aae6");
        JOURNAL_SOURCE_CACTSTPS.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_CACTSTPS.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_CACTSTPS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_CACTSTPS.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_CACTSTPS.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_CACTSTPS.put("name", "Completed Action Steps");
        JOURNAL_SOURCE_CACTSTPS.put("description", "Completed Action Steps");

        JOURNAL_SOURCE_EARSPNSE = new JSONObject();
        JOURNAL_SOURCE_EARSPNSE.put("id", "b2d07a00-5056-a51a-80b5-f725f1c5c3e2");
        JOURNAL_SOURCE_EARSPNSE.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_EARSPNSE.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_EARSPNSE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_EARSPNSE.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_EARSPNSE.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_EARSPNSE.put("name", "Early Alert Response");
        JOURNAL_SOURCE_EARSPNSE.put("description", "Early Alert Response");

        JOURNAL_SOURCE_EMAIL = new JSONObject();
        JOURNAL_SOURCE_EMAIL.put("id", "b2d079a2-5056-a51a-8075-328971518ff0");
        JOURNAL_SOURCE_EMAIL.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_EMAIL.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_EMAIL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_EMAIL.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_EMAIL.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_EMAIL.put("name", "Email");
        JOURNAL_SOURCE_EMAIL.put("description", "Email");

        JOURNAL_SOURCE_FACCONT = new JSONObject();
        JOURNAL_SOURCE_FACCONT.put("id", "b2d07983-5056-a51a-80d0-3fbcf39fa253");
        JOURNAL_SOURCE_FACCONT.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_FACCONT.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_FACCONT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_FACCONT.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_FACCONT.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_FACCONT.put("name", "Faculty Contact");
        JOURNAL_SOURCE_FACCONT.put("description", "Faculty Contact");

        JOURNAL_SOURCE_PHNCALL = new JSONObject();
        JOURNAL_SOURCE_PHNCALL.put("id", "b2d079c1-5056-a51a-80e1-c40f1e56af00");
        JOURNAL_SOURCE_PHNCALL.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_PHNCALL.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_PHNCALL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_PHNCALL.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_PHNCALL.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_PHNCALL.put("name", "Phone Call");
        JOURNAL_SOURCE_PHNCALL.put("description", "Default Description");

        JOURNAL_SOURCE_REFRL = new JSONObject();
        JOURNAL_SOURCE_REFRL.put("id", "b2d079f0-5056-a51a-8013-b982c069afb7");
        JOURNAL_SOURCE_REFRL.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_REFRL.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_REFRL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_REFRL.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_REFRL.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_REFRL.put("name", "Referral");
        JOURNAL_SOURCE_REFRL.put("description", "Default Description");

        JOURNAL_SOURCE_STUDJUD = new JSONObject();
        JOURNAL_SOURCE_STUDJUD.put("id", "b2d079b2-5056-a51a-80c8-753b509bc90b");
        JOURNAL_SOURCE_STUDJUD.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_STUDJUD.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_STUDJUD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_STUDJUD.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_STUDJUD.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_STUDJUD.put("name", "Student Judicial");
        JOURNAL_SOURCE_STUDJUD.put("description", "Student Judicial");

        JOURNAL_SOURCE_TXTMSG = new JSONObject();
        JOURNAL_SOURCE_TXTMSG.put("id", "0a640a2a-409d-1271-8140-d11ac13b0142");
        JOURNAL_SOURCE_TXTMSG.put("createdDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_TXTMSG.put("createdBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_TXTMSG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        JOURNAL_SOURCE_TXTMSG.put("modifiedBy", getDefaultCreatedModifiedBy());
        JOURNAL_SOURCE_TXTMSG.put("objectStatus", "ACTIVE");
        JOURNAL_SOURCE_TXTMSG.put("name", "Text Message");
        JOURNAL_SOURCE_TXTMSG.put("description", "Text Message");

        JOURNAL_SOURCE_ROWS = new JSONArray();
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_APPT);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_ASSESS);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_CASEMNG);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_CACTSTPS);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_EARSPNSE);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_EMAIL);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_FACCONT);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_PHNCALL);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_REFRL);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_STUDJUD);
        JOURNAL_SOURCE_ROWS.add(JOURNAL_SOURCE_TXTMSG);

        JOURNAL_SOURCE_RESPONSE = new JSONObject();
        JOURNAL_SOURCE_RESPONSE.put("success", "true");
        JOURNAL_SOURCE_RESPONSE.put("message", "");
        JOURNAL_SOURCE_RESPONSE.put("results", JOURNAL_SOURCE_ROWS.size());
        JOURNAL_SOURCE_RESPONSE.put("rows", JOURNAL_SOURCE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsJournalSourceReference() {
        final JSONObject testPostPutNegative = (JSONObject) JOURNAL_SOURCE_APPT.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(JOURNAL_SOURCE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalSourceReferenceAllBody() {

        testResponseBody(JOURNAL_SOURCE_PATH, JOURNAL_SOURCE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalSourceReferenceSingleItemBody() {

        testSingleItemResponseBody(JOURNAL_SOURCE_PATH, JOURNAL_SOURCE_ASSESS);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsJournalSourceReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, JOURNAL_SOURCE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalSourceReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) JOURNAL_SOURCE_ASSESS.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(JOURNAL_SOURCE_PATH, JOURNAL_SOURCE_CACTSTPS.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalSourceReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) JOURNAL_SOURCE_EARSPNSE.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = JOURNAL_SOURCE_CASEMNG;

        referenceNegativeSupportedMethodTest(JOURNAL_SOURCE_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
