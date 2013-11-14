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


public class CampusIT extends AbstractReferenceTest {

    private static final String CAMPUS_PATH = REFERENCE_PATH + "campus";

    private static final JSONObject CAMPUS_DIST;
    private static final JSONObject CAMPUS_NORTH;
    private static final JSONObject CAMPUS_WEST;

    private static final JSONArray CAMPUS_ROWS;
    private static final JSONObject CAMPUS_RESPONSE;

    static {

        CAMPUS_DIST = new JSONObject();
        CAMPUS_DIST.put("id", "0a640a2a-409d-1271-8140-d10a81010118");
        CAMPUS_DIST.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_DIST.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_DIST.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_DIST.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_DIST.put("objectStatus", "ACTIVE");
        CAMPUS_DIST.put("name", "Distance Learning");
        CAMPUS_DIST.put("description", "");
        CAMPUS_DIST.put("earlyAlertCoordinatorId", getDefaultCreatedModifiedBy().get("id"));

        CAMPUS_NORTH = new JSONObject();
        CAMPUS_NORTH.put("id", "901e104b-4dc7-43f5-a38e-581015e204e1");
        CAMPUS_NORTH.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_NORTH.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_NORTH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_NORTH.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_NORTH.put("objectStatus", "ACTIVE");
        CAMPUS_NORTH.put("name", "North Campus");
        CAMPUS_NORTH.put("description", "");
        CAMPUS_NORTH.put("earlyAlertCoordinatorId", getDefaultCreatedModifiedBy().get("id"));

        CAMPUS_WEST = new JSONObject();
        CAMPUS_WEST.put("id", "0a640a2a-409d-1271-8140-d10a51770117");
        CAMPUS_WEST.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_WEST.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_WEST.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_WEST.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_WEST.put("objectStatus", "ACTIVE");
        CAMPUS_WEST.put("name", "West Campus");
        CAMPUS_WEST.put("description", "");
        CAMPUS_WEST.put("earlyAlertCoordinatorId", getDefaultCreatedModifiedBy().get("id"));

        CAMPUS_ROWS = new JSONArray();
        CAMPUS_ROWS.add(CAMPUS_DIST);
        CAMPUS_ROWS.add(CAMPUS_NORTH);
        CAMPUS_ROWS.add(CAMPUS_WEST);


        CAMPUS_RESPONSE = new JSONObject();
        CAMPUS_RESPONSE.put("success", "true");
        CAMPUS_RESPONSE.put("message", "");
        CAMPUS_RESPONSE.put("results", CAMPUS_ROWS.size());
        CAMPUS_RESPONSE.put("rows", CAMPUS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsCampusReference() {
        final JSONObject testPostPutNegative = (JSONObject)CAMPUS_DIST.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(CAMPUS_PATH,
                testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCampusReferenceAllBody() {

        testResponseBody(CAMPUS_PATH, CAMPUS_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCampusReferenceSingleItemBody() {

        testSingleItemResponseBody(CAMPUS_PATH, CAMPUS_NORTH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsCampusReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CAMPUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCampusReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) CAMPUS_WEST.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(CAMPUS_PATH, CAMPUS_DIST.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCampusReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) CAMPUS_NORTH.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = CAMPUS_WEST;

        referenceNegativeSupportedMethodTest(CAMPUS_PATH, testNegativePostObject,
                testNegativeValidateObject);
    }
}