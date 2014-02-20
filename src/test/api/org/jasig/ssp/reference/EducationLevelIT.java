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


public class EducationLevelIT extends AbstractReferenceTest {

    private static final String EDUCATION_LEVEL_PATH = REFERENCE_PATH + "educationLevel";

    private static final JSONObject EDUCATION_LEVEL_ATTDHS;
    private static final JSONObject EDUCATION_LEVEL_CD2YR;
    private static final JSONObject EDUCATION_LEVEL_CD4YR;
    private static final JSONObject EDUCATION_LEVEL_GED;
    private static final JSONObject EDUCATION_LEVEL_HSGRAD;
    private static final JSONObject EDUCATION_LEVEL_NONE;
    private static final JSONObject EDUCATION_LEVEL_OTHR;
    private static final JSONObject EDUCATION_LEVEL_SOME;

    private static final JSONArray EDUCATION_LEVEL_ROWS;
    private static final JSONObject EDUCATION_LEVEL_RESPONSE;


    static {

        EDUCATION_LEVEL_ATTDHS = new JSONObject();
        EDUCATION_LEVEL_ATTDHS.put("id", "742d0a11-2f4f-4f5c-bf30-ab5c4122e66e");
        EDUCATION_LEVEL_ATTDHS.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_ATTDHS.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_ATTDHS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_ATTDHS.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_ATTDHS.put("objectStatus", "ACTIVE");
        EDUCATION_LEVEL_ATTDHS.put("name", "Attending High School");
        EDUCATION_LEVEL_ATTDHS.put("description", "Attending High School");

        EDUCATION_LEVEL_CD2YR = new JSONObject();
        EDUCATION_LEVEL_CD2YR.put("id", "e79e6be0-80e2-40a6-a21e-7544b08f0bce");
        EDUCATION_LEVEL_CD2YR.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_CD2YR.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_CD2YR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_CD2YR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_CD2YR.put("objectStatus", "ACTIVE");
        EDUCATION_LEVEL_CD2YR.put("name", "College Degree - 2 Year");
        EDUCATION_LEVEL_CD2YR.put("description", "College Degree - 2 Year");

        EDUCATION_LEVEL_CD4YR = new JSONObject();
        EDUCATION_LEVEL_CD4YR.put("id", "459f4cb3-2274-4f47-b757-68b987f8707e");
        EDUCATION_LEVEL_CD4YR.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_CD4YR.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_CD4YR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_CD4YR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_CD4YR.put("objectStatus", "ACTIVE");
        EDUCATION_LEVEL_CD4YR.put("name", "College Degree - 4 Year");
        EDUCATION_LEVEL_CD4YR.put("description", "College Degree - 4 Year");

        EDUCATION_LEVEL_GED = new JSONObject();
        EDUCATION_LEVEL_GED.put("id", "710add1c-7b53-4cbe-86cb-8d7c5837d68b");
        EDUCATION_LEVEL_GED.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_GED.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_GED.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_GED.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_GED.put("objectStatus", "ACTIVE");
        EDUCATION_LEVEL_GED.put("name", "GED");
        EDUCATION_LEVEL_GED.put("description", "GED");

        EDUCATION_LEVEL_HSGRAD = new JSONObject();
        EDUCATION_LEVEL_HSGRAD.put("id", "f4780d23-fd8a-4758-b772-18606dca32f0");
        EDUCATION_LEVEL_HSGRAD.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_HSGRAD.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_HSGRAD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_HSGRAD.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_HSGRAD.put("objectStatus", "ACTIVE");
        EDUCATION_LEVEL_HSGRAD.put("name", "High School Graduation");
        EDUCATION_LEVEL_HSGRAD.put("description", "High School Graduation");

        EDUCATION_LEVEL_NONE = new JSONObject();
        EDUCATION_LEVEL_NONE.put("id", "5d967ba0-e086-4426-85d5-29bc86da9295");
        EDUCATION_LEVEL_NONE.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_NONE.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_NONE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_NONE.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_NONE.put("objectStatus", "ACTIVE");
        EDUCATION_LEVEL_NONE.put("name", "No Diploma/No GED");
        EDUCATION_LEVEL_NONE.put("description", "No Diploma/No GED");

        EDUCATION_LEVEL_OTHR = new JSONObject();
        EDUCATION_LEVEL_OTHR.put("id", "247165ae-3db4-4679-ac95-ca96488c3b27");
        EDUCATION_LEVEL_OTHR.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_OTHR.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_OTHR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_OTHR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_OTHR.put("objectStatus", "ACTIVE");
        EDUCATION_LEVEL_OTHR.put("name", "Other");
        EDUCATION_LEVEL_OTHR.put("description", "Other");

        EDUCATION_LEVEL_SOME = new JSONObject();
        EDUCATION_LEVEL_SOME.put("id", "c5111182-9e2f-4252-bb61-d2cfa9700af7");
        EDUCATION_LEVEL_SOME.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_SOME.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_SOME.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_LEVEL_SOME.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_LEVEL_SOME.put("objectStatus", "ACTIVE");
        EDUCATION_LEVEL_SOME.put("name", "Some College Credits");
        EDUCATION_LEVEL_SOME.put("description", "Some College Credits");


        EDUCATION_LEVEL_ROWS = new JSONArray();
        EDUCATION_LEVEL_ROWS.add(EDUCATION_LEVEL_ATTDHS);
        EDUCATION_LEVEL_ROWS.add(EDUCATION_LEVEL_CD2YR);
        EDUCATION_LEVEL_ROWS.add(EDUCATION_LEVEL_CD4YR);
        EDUCATION_LEVEL_ROWS.add(EDUCATION_LEVEL_GED);
        EDUCATION_LEVEL_ROWS.add(EDUCATION_LEVEL_HSGRAD);
        EDUCATION_LEVEL_ROWS.add(EDUCATION_LEVEL_NONE);
        EDUCATION_LEVEL_ROWS.add(EDUCATION_LEVEL_OTHR);
        EDUCATION_LEVEL_ROWS.add(EDUCATION_LEVEL_SOME);


        EDUCATION_LEVEL_RESPONSE = new JSONObject();
        EDUCATION_LEVEL_RESPONSE.put("success", "true");
        EDUCATION_LEVEL_RESPONSE.put("message", "");
        EDUCATION_LEVEL_RESPONSE.put("results", EDUCATION_LEVEL_ROWS.size());
        EDUCATION_LEVEL_RESPONSE.put("rows", EDUCATION_LEVEL_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEducationLevelReference() {
        final JSONObject testPostPutNegative = (JSONObject) EDUCATION_LEVEL_ATTDHS.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(EDUCATION_LEVEL_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEducationLevelReferenceAllBody() {

        testResponseBody(EDUCATION_LEVEL_PATH, EDUCATION_LEVEL_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEducationLevelReferenceSingleItemBody() {

        testSingleItemResponseBody(EDUCATION_LEVEL_PATH, EDUCATION_LEVEL_CD2YR);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEducationLevelReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, EDUCATION_LEVEL_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEducationLevelReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) EDUCATION_LEVEL_GED.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(EDUCATION_LEVEL_PATH, EDUCATION_LEVEL_NONE.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEducationLevelReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) EDUCATION_LEVEL_OTHR.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = EDUCATION_LEVEL_SOME;

        referenceNegativeSupportedMethodTest(EDUCATION_LEVEL_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
