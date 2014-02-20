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


public class ElectiveIT extends AbstractReferenceTest {

    private static final String ELECTIVE_PATH = REFERENCE_PATH + "elective";

    private static final JSONObject ELECTIVE_ART;
    private static final JSONObject ELECTIVE_ENG;
    private static final JSONObject ELECTIVE_GEN;
    private static final JSONObject ELECTIVE_HUM;
    private static final JSONObject ELECTIVE_LNG;
    private static final JSONObject ELECTIVE_PRG;

    private static final JSONArray ELECTIVE_ROWS;
    private static final JSONObject ELECTIVE_RESPONSE;

    static {

        ELECTIVE_ART = new JSONObject();
        ELECTIVE_ART.put("id", "bb0abe0b-d6f8-4987-8fff-27020dc9fe35");
        ELECTIVE_ART.put("createdBy", getDefaultCreatedModifiedBy());
        ELECTIVE_ART.put("sortOrder", 3);
        ELECTIVE_ART.put("description", "A Arts Elective");
        ELECTIVE_ART.put("modifiedBy", getDefaultCreatedModifiedBy());
        ELECTIVE_ART.put("name", "Arts Elective");
        ELECTIVE_ART.put("objectStatus", "ACTIVE");
        ELECTIVE_ART.put("createdDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_ART.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_ART.put("code", "ART");
        ELECTIVE_ART.put("color", "8442bfcc-b44c-4b70-a99c-2b67691568ae");

        ELECTIVE_ENG = new JSONObject();
        ELECTIVE_ENG.put("id", "989a9679-d925-4475-acbb-8bd9d81fcb04");
        ELECTIVE_ENG.put("createdBy", getDefaultCreatedModifiedBy());
        ELECTIVE_ENG.put("sortOrder", 5);
        ELECTIVE_ENG.put("description", "A Engineering Elective");
        ELECTIVE_ENG.put("modifiedBy", getDefaultCreatedModifiedBy());
        ELECTIVE_ENG.put("name", "Engineering Elective");
        ELECTIVE_ENG.put("objectStatus", "ACTIVE");
        ELECTIVE_ENG.put("createdDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_ENG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_ENG.put("code", "ENG");
        ELECTIVE_ENG.put("color", "93e117d8-bd62-4bff-a812-94cae3a65a12");

        ELECTIVE_GEN = new JSONObject();
        ELECTIVE_GEN.put("id", "9a07ced6-7b3a-4926-8a88-ba23f998fc46");
        ELECTIVE_GEN.put("createdBy", getDefaultCreatedModifiedBy());
        ELECTIVE_GEN.put("sortOrder", 0);
        ELECTIVE_GEN.put("description", "A General Education Elective");
        ELECTIVE_GEN.put("modifiedBy", getDefaultCreatedModifiedBy());
        ELECTIVE_GEN.put("name", "General Education Elective");
        ELECTIVE_GEN.put("objectStatus", "ACTIVE");
        ELECTIVE_GEN.put("createdDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_GEN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_GEN.put("code", "GEN");
        ELECTIVE_GEN.put("color", "9a6b915b-91c8-49d5-8e1a-47c67c5aed59");

        ELECTIVE_HUM = new JSONObject();
        ELECTIVE_HUM.put("id", "3122e73b-dd86-4f23-af05-22c2abd93414");
        ELECTIVE_HUM.put("createdBy", getDefaultCreatedModifiedBy());
        ELECTIVE_HUM.put("sortOrder", 4);
        ELECTIVE_HUM.put("description", "A Humanities Elective");
        ELECTIVE_HUM.put("modifiedBy", getDefaultCreatedModifiedBy());
        ELECTIVE_HUM.put("name", "Humanities Elective");
        ELECTIVE_HUM.put("objectStatus", "ACTIVE");
        ELECTIVE_HUM.put("createdDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_HUM.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_HUM.put("code", "HUM");
        ELECTIVE_HUM.put("color", "2615ab88-cc09-48d4-a404-0774d71bf8a4");

        ELECTIVE_LNG = new JSONObject();
        ELECTIVE_LNG.put("id", "db60bb67-a7e3-4610-a9af-3a0e4d48b58b");
        ELECTIVE_LNG.put("createdBy", getDefaultCreatedModifiedBy());
        ELECTIVE_LNG.put("sortOrder", 2);
        ELECTIVE_LNG.put("description", "A Modern Language Elective");
        ELECTIVE_LNG.put("modifiedBy", getDefaultCreatedModifiedBy());
        ELECTIVE_LNG.put("name", "Modern Language Elective");
        ELECTIVE_LNG.put("objectStatus", "ACTIVE");
        ELECTIVE_LNG.put("createdDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_LNG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_LNG.put("code", "LNG");
        ELECTIVE_LNG.put("color", "e0432f26-ba18-43bd-9475-d3cd899e2f8c");

        ELECTIVE_PRG = new JSONObject();
        ELECTIVE_PRG.put("id", "3bdda584-f7a2-4402-8863-4b5bd8273009");
        ELECTIVE_PRG.put("createdBy", getDefaultCreatedModifiedBy());
        ELECTIVE_PRG.put("sortOrder", 1);
        ELECTIVE_PRG.put("description", "A Program Elective");
        ELECTIVE_PRG.put("modifiedBy", getDefaultCreatedModifiedBy());
        ELECTIVE_PRG.put("name", "Program Elective");
        ELECTIVE_PRG.put("objectStatus", "ACTIVE");
        ELECTIVE_PRG.put("createdDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_PRG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ELECTIVE_PRG.put("code", "PRG");
        ELECTIVE_PRG.put("color", "8dad87be-936b-450d-8f4a-f733b679a7dd");

        ELECTIVE_ROWS = new JSONArray();
        ELECTIVE_ROWS.add(ELECTIVE_ART);
        ELECTIVE_ROWS.add(ELECTIVE_ENG);
        ELECTIVE_ROWS.add(ELECTIVE_GEN);
        ELECTIVE_ROWS.add(ELECTIVE_HUM);
        ELECTIVE_ROWS.add(ELECTIVE_LNG);
        ELECTIVE_ROWS.add(ELECTIVE_PRG);

        ELECTIVE_RESPONSE = new JSONObject();
        ELECTIVE_RESPONSE.put("success", "true");
        ELECTIVE_RESPONSE.put("message", "");
        ELECTIVE_RESPONSE.put("results", ELECTIVE_ROWS.size());
        ELECTIVE_RESPONSE.put("rows", ELECTIVE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsElectiveReference() {
        final JSONObject testPostPutNegative = (JSONObject)ELECTIVE_PRG.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(ELECTIVE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testElectiveReferenceAllBody() {

        testResponseBody(ELECTIVE_PATH, ELECTIVE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testElectiveReferenceSingleItemBody() {

        testSingleItemResponseBody(ELECTIVE_PATH, ELECTIVE_GEN);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsElectiveReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, ELECTIVE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testElectiveReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ELECTIVE_GEN.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(ELECTIVE_PATH, ELECTIVE_PRG.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testElectiveReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ELECTIVE_ART.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = ELECTIVE_LNG;

        referenceNegativeSupportedMethodTest(ELECTIVE_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}

