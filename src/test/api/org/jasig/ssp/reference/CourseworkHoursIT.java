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


public class CourseworkHoursIT extends AbstractReferenceTest {

    private static final String COURSEWORK_HOURS_PATH = REFERENCE_PATH + "courseworkHours";

    private static final JSONObject COURSEWORK_HOURS_LL;
    private static final JSONObject COURSEWORK_HOURS_LLFT;
    private static final JSONObject COURSEWORK_HOURS_SLFT;
    private static final JSONObject COURSEWORK_HOURS_ML;
    private static final JSONObject COURSEWORK_HOURS_HLFT;

    private static final JSONArray COURSEWORK_HOURS_ROWS;
    private static final JSONObject COURSEWORK_HOURS_RESPONSE;

    static {

        COURSEWORK_HOURS_LL = new JSONObject();
        COURSEWORK_HOURS_LL.put("id", "7c7df05a-92c2-4806-93c7-5b8d99d42657");
        COURSEWORK_HOURS_LL.put("createdDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_LL.put("createdBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_LL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_LL.put("modifiedBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_LL.put("objectStatus", "ACTIVE");
        COURSEWORK_HOURS_LL.put("name", "0-5");
        COURSEWORK_HOURS_LL.put("description", "Light Load");

        COURSEWORK_HOURS_LLFT = new JSONObject();
        COURSEWORK_HOURS_LLFT.put("id", "ac85ac8e-90e6-4425-b74e-7e8b0c7bee7a");
        COURSEWORK_HOURS_LLFT.put("createdDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_LLFT.put("createdBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_LLFT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_LLFT.put("modifiedBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_LLFT.put("objectStatus", "ACTIVE");
        COURSEWORK_HOURS_LLFT.put("name", "11-15");
        COURSEWORK_HOURS_LLFT.put("description", "Light Load Fulltime Student");

        COURSEWORK_HOURS_SLFT = new JSONObject();
        COURSEWORK_HOURS_SLFT.put("id", "24efd91a-0a06-4f8c-a910-de2f4225e618");
        COURSEWORK_HOURS_SLFT.put("createdDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_SLFT.put("createdBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_SLFT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_SLFT.put("modifiedBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_SLFT.put("objectStatus", "ACTIVE");
        COURSEWORK_HOURS_SLFT.put("name", "16-20");
        COURSEWORK_HOURS_SLFT.put("description", "Standard Load Fulltime Student");

        COURSEWORK_HOURS_ML = new JSONObject();
        COURSEWORK_HOURS_ML.put("id", "fbd43e12-bd41-437e-85ba-a45ba0e12327");
        COURSEWORK_HOURS_ML.put("createdDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_ML.put("createdBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_ML.put("modifiedDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_ML.put("modifiedBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_ML.put("objectStatus", "ACTIVE");
        COURSEWORK_HOURS_ML.put("name", "6-10");
        COURSEWORK_HOURS_ML.put("description", "Moderate Load");

        COURSEWORK_HOURS_HLFT = new JSONObject();
        COURSEWORK_HOURS_HLFT.put("id", "c0a8018d-3d4a-11a2-813d-4af28ad9003b");
        COURSEWORK_HOURS_HLFT.put("createdDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_HLFT.put("createdBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_HLFT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        COURSEWORK_HOURS_HLFT.put("modifiedBy", getDefaultCreatedModifiedBy());
        COURSEWORK_HOURS_HLFT.put("objectStatus", "ACTIVE");
        COURSEWORK_HOURS_HLFT.put("name", "More than 20");
        COURSEWORK_HOURS_HLFT.put("description", "Heavy Load Fulltime Student");


        COURSEWORK_HOURS_ROWS = new JSONArray();
        COURSEWORK_HOURS_ROWS.add(COURSEWORK_HOURS_LL);
        COURSEWORK_HOURS_ROWS.add(COURSEWORK_HOURS_LLFT);
        COURSEWORK_HOURS_ROWS.add(COURSEWORK_HOURS_SLFT);
        COURSEWORK_HOURS_ROWS.add(COURSEWORK_HOURS_ML);
        COURSEWORK_HOURS_ROWS.add(COURSEWORK_HOURS_HLFT);


        COURSEWORK_HOURS_RESPONSE = new JSONObject();
        COURSEWORK_HOURS_RESPONSE.put("success", "true");
        COURSEWORK_HOURS_RESPONSE.put("message", "");
        COURSEWORK_HOURS_RESPONSE.put("results", COURSEWORK_HOURS_ROWS.size());
        COURSEWORK_HOURS_RESPONSE.put("rows", COURSEWORK_HOURS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsCourseworkHoursReference() {
        final JSONObject testPostPutNegative = (JSONObject)COURSEWORK_HOURS_LL.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(COURSEWORK_HOURS_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCourseworkHoursReferenceAllBody() {

        testResponseBody(COURSEWORK_HOURS_PATH, COURSEWORK_HOURS_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCourseworkHoursReferenceSingleItemBody() {

        testSingleItemResponseBody(COURSEWORK_HOURS_PATH, COURSEWORK_HOURS_LLFT);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsCourseworkHoursReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, COURSEWORK_HOURS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCourseworkHoursReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) COURSEWORK_HOURS_SLFT.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(COURSEWORK_HOURS_PATH, COURSEWORK_HOURS_ML.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCourseworkHoursReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) COURSEWORK_HOURS_HLFT.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = COURSEWORK_HOURS_LL;

        referenceNegativeSupportedMethodTest(COURSEWORK_HOURS_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
