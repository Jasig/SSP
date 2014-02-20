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


public class EducationGoalIT extends AbstractReferenceTest {

    private static final String EDUCATION_GOAL_PATH = REFERENCE_PATH + "educationGoal";

    private static final JSONObject EDUCATION_GOAL_ASSOC;
    private static final JSONObject EDUCATION_GOAL_BACH;
    private static final JSONObject EDUCATION_GOAL_CERT;
    private static final JSONObject EDUCATION_GOAL_MIL;
    private static final JSONObject EDUCATION_GOAL_OTHR;
    private static final JSONObject EDUCATION_GOAL_STCERT;
    private static final JSONObject EDUCATION_GOAL_TECH;
    private static final JSONObject EDUCATION_GOAL_UNCERT;
    private static final JSONObject EDUCATION_GOAL_WORKFRCE;

    private static final JSONArray EDUCATION_GOAL_ROWS;
    private static final JSONObject EDUCATION_GOAL_RESPONSE;

    static {

        EDUCATION_GOAL_ASSOC = new JSONObject();
        EDUCATION_GOAL_ASSOC.put("id", "d25e224b-a0ca-48f0-ac30-1ddf5bdb9e0d");
        EDUCATION_GOAL_ASSOC.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_ASSOC.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_ASSOC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_ASSOC.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_ASSOC.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_ASSOC.put("name", "Associates Degree");
        EDUCATION_GOAL_ASSOC.put("description", "Associates Degree");

        EDUCATION_GOAL_BACH = new JSONObject();
        EDUCATION_GOAL_BACH.put("id", "efeb5536-d634-4b79-80bc-1e1041dcd3ff");
        EDUCATION_GOAL_BACH.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_BACH.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_BACH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_BACH.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_BACH.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_BACH.put("name", "Bachelor");
        EDUCATION_GOAL_BACH.put("description", "Bachelor");

        EDUCATION_GOAL_CERT = new JSONObject();
        EDUCATION_GOAL_CERT.put("id", "00afdf46-2f0e-46e4-9d56-bc45b9266642");
        EDUCATION_GOAL_CERT.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_CERT.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_CERT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_CERT.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_CERT.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_CERT.put("name", "Certificate");
        EDUCATION_GOAL_CERT.put("description", "Certificate");

        EDUCATION_GOAL_MIL = new JSONObject();
        EDUCATION_GOAL_MIL.put("id", "6c466885-d3f8-44d1-a301-62d6fe2d3553");
        EDUCATION_GOAL_MIL.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_MIL.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_MIL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_MIL.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_MIL.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_MIL.put("name", "Military");
        EDUCATION_GOAL_MIL.put("description", "Military");

        EDUCATION_GOAL_OTHR = new JSONObject();
        EDUCATION_GOAL_OTHR.put("id", "78b54da7-fb19-4092-bb44-f60485678d6b");
        EDUCATION_GOAL_OTHR.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_OTHR.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_OTHR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_OTHR.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_OTHR.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_OTHR.put("name", "Other");
        EDUCATION_GOAL_OTHR.put("description", "Other");

        EDUCATION_GOAL_STCERT = new JSONObject();
        EDUCATION_GOAL_STCERT.put("id", "5cccdca1-9a73-47e8-814f-134663a2ae67");
        EDUCATION_GOAL_STCERT.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_STCERT.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_STCERT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_STCERT.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_STCERT.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_STCERT.put("name", "Short Term Certificate");
        EDUCATION_GOAL_STCERT.put("description", "Short Term Certificate");

        EDUCATION_GOAL_TECH = new JSONObject();
        EDUCATION_GOAL_TECH.put("id", "7e3e6f05-612c-4636-a370-7b038e98510f");
        EDUCATION_GOAL_TECH.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_TECH.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_TECH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_TECH.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_TECH.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_TECH.put("name", "Tech School");
        EDUCATION_GOAL_TECH.put("description", "Tech School");

        EDUCATION_GOAL_UNCERT = new JSONObject();
        EDUCATION_GOAL_UNCERT.put("id", "9bf33704-e41e-4922-bc7f-07b98b276824");
        EDUCATION_GOAL_UNCERT.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_UNCERT.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_UNCERT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_UNCERT.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_UNCERT.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_UNCERT.put("name", "Uncertain");
        EDUCATION_GOAL_UNCERT.put("description", "Uncertain");

        EDUCATION_GOAL_WORKFRCE = new JSONObject();
        EDUCATION_GOAL_WORKFRCE.put("id", "d632046f-1fbf-4361-ac1e-3ca67f78e104");
        EDUCATION_GOAL_WORKFRCE.put("createdDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_WORKFRCE.put("createdBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_WORKFRCE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EDUCATION_GOAL_WORKFRCE.put("modifiedBy", getDefaultCreatedModifiedBy());
        EDUCATION_GOAL_WORKFRCE.put("objectStatus", "ACTIVE");
        EDUCATION_GOAL_WORKFRCE.put("name", "Workforce");
        EDUCATION_GOAL_WORKFRCE.put("description", "Workforce");


        EDUCATION_GOAL_ROWS = new JSONArray();
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_ASSOC);
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_BACH);
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_CERT);
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_MIL);
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_OTHR);
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_STCERT);
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_TECH);
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_UNCERT);
        EDUCATION_GOAL_ROWS.add(EDUCATION_GOAL_WORKFRCE);


        EDUCATION_GOAL_RESPONSE = new JSONObject();
        EDUCATION_GOAL_RESPONSE.put("success", "true");
        EDUCATION_GOAL_RESPONSE.put("message", "");
        EDUCATION_GOAL_RESPONSE.put("results", EDUCATION_GOAL_ROWS.size());
        EDUCATION_GOAL_RESPONSE.put("rows", EDUCATION_GOAL_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEducationGoalReference() {
        final JSONObject testPostPutNegative = (JSONObject) EDUCATION_GOAL_ASSOC.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(EDUCATION_GOAL_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEducationGoalReferenceAllBody() {

        testResponseBody(EDUCATION_GOAL_PATH, EDUCATION_GOAL_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEducationGoalReferenceSingleItemBody() {

        testSingleItemResponseBody(EDUCATION_GOAL_PATH, EDUCATION_GOAL_CERT);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEducationGoalReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, EDUCATION_GOAL_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEducationGoalReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) EDUCATION_GOAL_OTHR.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(EDUCATION_GOAL_PATH, EDUCATION_GOAL_STCERT.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEducationGoalReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) EDUCATION_GOAL_OTHR.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = EDUCATION_GOAL_UNCERT;

        referenceNegativeSupportedMethodTest(EDUCATION_GOAL_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
