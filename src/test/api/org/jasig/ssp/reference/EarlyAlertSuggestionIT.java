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


public class EarlyAlertSuggestionIT extends AbstractReferenceTest {

    private static final String EARLY_ALERT_SUGGESTION_PATH = REFERENCE_PATH + "earlyAlertSuggestion";

    private static final JSONObject EARLY_ALERT_SUGGESTION_INSTR;
    private static final JSONObject EARLY_ALERT_SUGGESTION_ADVS;
    private static final JSONObject EARLY_ALERT_SUGGESTION_COUNS;
    private static final JSONObject EARLY_ALERT_SUGGESTION_TLCENT;
    private static final JSONObject EARLY_ALERT_SUGGESTION_WRIT;
    private static final JSONObject EARLY_ALERT_SUGGESTION_TUTR;
    private static final JSONObject EARLY_ALERT_SUGGESTION_DISSI;
    private static final JSONObject EARLY_ALERT_SUGGESTION_WTHDRW;
    private static final JSONObject EARLY_ALERT_SUGGESTION_DEVPROTUTR;
    private static final JSONObject EARLY_ALERT_SUGGESTION_OTHR;

    private static final JSONArray EARLY_ALERT_SUGGESTION_ROWS;
    private static final JSONObject EARLY_ALERT_SUGGESTION_RESPONSE;

    static {

        EARLY_ALERT_SUGGESTION_INSTR = new JSONObject();
        EARLY_ALERT_SUGGESTION_INSTR.put("id", "b2d11141-5056-a51a-80c1-c1250ba820f8");
        EARLY_ALERT_SUGGESTION_INSTR.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_INSTR.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_INSTR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_INSTR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_INSTR.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_INSTR.put("name", "See Instructor");
        EARLY_ALERT_SUGGESTION_INSTR.put("description", "");
        EARLY_ALERT_SUGGESTION_INSTR.put("sortOrder", 1);

        EARLY_ALERT_SUGGESTION_ADVS = new JSONObject();
        EARLY_ALERT_SUGGESTION_ADVS.put("id", "b2d11151-5056-a51a-8051-3acdf99fef84");
        EARLY_ALERT_SUGGESTION_ADVS.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_ADVS.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_ADVS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_ADVS.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_ADVS.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_ADVS.put("name", "See Advisor or Coach");
        EARLY_ALERT_SUGGESTION_ADVS.put("description", "");
        EARLY_ALERT_SUGGESTION_ADVS.put("sortOrder", 2);

        EARLY_ALERT_SUGGESTION_COUNS = new JSONObject();
        EARLY_ALERT_SUGGESTION_COUNS.put("id", "b2d11160-5056-a51a-807d-9897c14bdd44");
        EARLY_ALERT_SUGGESTION_COUNS.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_COUNS.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_COUNS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_COUNS.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_COUNS.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_COUNS.put("name", "Counseling Services");
        EARLY_ALERT_SUGGESTION_COUNS.put("description", "");
        EARLY_ALERT_SUGGESTION_COUNS.put("sortOrder", 3);

        EARLY_ALERT_SUGGESTION_TLCENT = new JSONObject();
        EARLY_ALERT_SUGGESTION_TLCENT.put("id", "b2d11170-5056-a51a-8002-b5ce9f25e2bc");
        EARLY_ALERT_SUGGESTION_TLCENT.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_TLCENT.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_TLCENT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_TLCENT.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_TLCENT.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_TLCENT.put("name", "The Tutoring/Learning Center");
        EARLY_ALERT_SUGGESTION_TLCENT.put("description", "");
        EARLY_ALERT_SUGGESTION_TLCENT.put("sortOrder", 4);

        EARLY_ALERT_SUGGESTION_WRIT = new JSONObject();
        EARLY_ALERT_SUGGESTION_WRIT.put("id", "b2d111be-5056-a51a-8075-c5a65da17079");
        EARLY_ALERT_SUGGESTION_WRIT.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_WRIT.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_WRIT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_WRIT.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_WRIT.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_WRIT.put("name", "Writing Center");
        EARLY_ALERT_SUGGESTION_WRIT.put("description", "");
        EARLY_ALERT_SUGGESTION_WRIT.put("sortOrder", 5);

        EARLY_ALERT_SUGGESTION_TUTR = new JSONObject();
        EARLY_ALERT_SUGGESTION_TUTR.put("id", "b2d111ce-5056-a51a-8017-676c4d8c4f1d");
        EARLY_ALERT_SUGGESTION_TUTR.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_TUTR.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_TUTR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_TUTR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_TUTR.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_TUTR.put("name", "Tutoring Services");
        EARLY_ALERT_SUGGESTION_TUTR.put("description", "");
        EARLY_ALERT_SUGGESTION_TUTR.put("sortOrder", 6);

        EARLY_ALERT_SUGGESTION_DISSI = new JSONObject();
        EARLY_ALERT_SUGGESTION_DISSI.put("id", "b2d111dd-5056-a51a-8034-909bd6af80d5");
        EARLY_ALERT_SUGGESTION_DISSI.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_DISSI.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_DISSI.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_DISSI.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_DISSI.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_DISSI.put("name", "Disability Service Intervention");
        EARLY_ALERT_SUGGESTION_DISSI.put("description", "");
        EARLY_ALERT_SUGGESTION_DISSI.put("sortOrder", 7);

        EARLY_ALERT_SUGGESTION_WTHDRW = new JSONObject();
        EARLY_ALERT_SUGGESTION_WTHDRW.put("id", "b2d111ed-5056-a51a-8046-5291453e8720");
        EARLY_ALERT_SUGGESTION_WTHDRW.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_WTHDRW.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_WTHDRW.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_WTHDRW.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_WTHDRW.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_WTHDRW.put("name", "Withdraw");
        EARLY_ALERT_SUGGESTION_WTHDRW.put("description", "");
        EARLY_ALERT_SUGGESTION_WTHDRW.put("sortOrder", 8);

        EARLY_ALERT_SUGGESTION_DEVPROTUTR = new JSONObject();
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("id", "b2d111fd-5056-a51a-80fe-6f3344174dbe");
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("name", "DEV Professional Tutoring");
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("description", "");
        EARLY_ALERT_SUGGESTION_DEVPROTUTR.put("sortOrder", 9);

        EARLY_ALERT_SUGGESTION_OTHR = new JSONObject();
        EARLY_ALERT_SUGGESTION_OTHR.put("id", "b2d1120c-5056-a51a-80ea-c779a3109f8f");
        EARLY_ALERT_SUGGESTION_OTHR.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_OTHR.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_OTHR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_SUGGESTION_OTHR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_SUGGESTION_OTHR.put("objectStatus", "ACTIVE");
        EARLY_ALERT_SUGGESTION_OTHR.put("name", "Other");
        EARLY_ALERT_SUGGESTION_OTHR.put("description", "");
        EARLY_ALERT_SUGGESTION_OTHR.put("sortOrder", 10);


        EARLY_ALERT_SUGGESTION_ROWS = new JSONArray();
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_COUNS);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_DEVPROTUTR);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_DISSI);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_OTHR);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_ADVS);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_INSTR);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_TLCENT);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_TUTR);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_WTHDRW);
        EARLY_ALERT_SUGGESTION_ROWS.add(EARLY_ALERT_SUGGESTION_WRIT);


        EARLY_ALERT_SUGGESTION_RESPONSE = new JSONObject();
        EARLY_ALERT_SUGGESTION_RESPONSE.put("success", "true");
        EARLY_ALERT_SUGGESTION_RESPONSE.put("message", "");
        EARLY_ALERT_SUGGESTION_RESPONSE.put("results", EARLY_ALERT_SUGGESTION_ROWS.size());
        EARLY_ALERT_SUGGESTION_RESPONSE.put("rows", EARLY_ALERT_SUGGESTION_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEarlyAlertSuggestionReference() {
        final JSONObject testPostPutNegative = (JSONObject)EARLY_ALERT_SUGGESTION_INSTR.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(EARLY_ALERT_SUGGESTION_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertSuggestionReferenceAllBody() {

        testResponseBody(EARLY_ALERT_SUGGESTION_PATH, EARLY_ALERT_SUGGESTION_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertSuggestionReferenceSingleItemBody() {

        testSingleItemResponseBody(EARLY_ALERT_SUGGESTION_PATH, EARLY_ALERT_SUGGESTION_ADVS);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEarlyAlertSuggestionReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, EARLY_ALERT_SUGGESTION_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertSuggestionReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) EARLY_ALERT_SUGGESTION_COUNS.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(EARLY_ALERT_SUGGESTION_PATH, EARLY_ALERT_SUGGESTION_TLCENT.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertSuggestionReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) EARLY_ALERT_SUGGESTION_DEVPROTUTR.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = EARLY_ALERT_SUGGESTION_OTHR;

        referenceNegativeSupportedMethodTest(EARLY_ALERT_SUGGESTION_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}

