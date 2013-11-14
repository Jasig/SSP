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


public class CampusServiceIT extends AbstractReferenceTest {

    private static final String CAMPUS_SERVICE_PATH = REFERENCE_PATH + "campusService";

    private static final JSONObject CAMPUS_SERVICE_MIN;
    private static final JSONObject CAMPUS_SERVICE_CARREER;
    private static final JSONObject CAMPUS_SERVICE_COUNS;
    private static final JSONObject CAMPUS_SERVICE_DIS;
    private static final JSONObject CAMPUS_SERVICE_PHYACT;
    private static final JSONObject CAMPUS_SERVICE_STUDACT;
    private static final JSONObject CAMPUS_SERVICE_STUDEMPLY;
    private static final JSONObject CAMPUS_SERVICE_SSS;
    private static final JSONObject CAMPUS_SERVICE_TUTORSER;

    private static final JSONArray CAMPUS_SERVICE_ROWS;
    private static final JSONObject CAMPUS_SERVICE_RESPONSE;

    static {

        CAMPUS_SERVICE_MIN = new JSONObject();
        CAMPUS_SERVICE_MIN.put("id", "4a24986d-c8d8-407a-ab98-8211e5946f89");
        CAMPUS_SERVICE_MIN.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_MIN.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_MIN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_MIN.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_MIN.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_MIN.put("name", "Campus Ministries");
        CAMPUS_SERVICE_MIN.put("description", "Campus Ministries");

        CAMPUS_SERVICE_CARREER = new JSONObject();
        CAMPUS_SERVICE_CARREER.put("id", "8aeab4a2-a8ad-4ab5-b5cd-374692da6bfe");
        CAMPUS_SERVICE_CARREER.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_CARREER.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_CARREER.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_CARREER.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_CARREER.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_CARREER.put("name", "Career Services");
        CAMPUS_SERVICE_CARREER.put("description", "Career Services");

        CAMPUS_SERVICE_COUNS = new JSONObject();
        CAMPUS_SERVICE_COUNS.put("id", "4fb04701-095f-4676-b693-1c9d1bfd813f");
        CAMPUS_SERVICE_COUNS.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_COUNS.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_COUNS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_COUNS.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_COUNS.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_COUNS.put("name", "Counseling Services");
        CAMPUS_SERVICE_COUNS.put("description", "Counseling Services");

        CAMPUS_SERVICE_DIS = new JSONObject();
        CAMPUS_SERVICE_DIS.put("id", "68c85cd0-3694-4ea6-9a13-f2b6e704d4b4");
        CAMPUS_SERVICE_DIS.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_DIS.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_DIS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_DIS.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_DIS.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_DIS.put("name", "Disability Services");
        CAMPUS_SERVICE_DIS.put("description", "Disability Services");

        CAMPUS_SERVICE_PHYACT = new JSONObject();
        CAMPUS_SERVICE_PHYACT.put("id", "90c0ff50-cefc-4461-9253-421f27747c5e");
        CAMPUS_SERVICE_PHYACT.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_PHYACT.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_PHYACT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_PHYACT.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_PHYACT.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_PHYACT.put("name", "Physical Activities Center");
        CAMPUS_SERVICE_PHYACT.put("description", "Physical Activities Center");

        CAMPUS_SERVICE_STUDACT = new JSONObject();
        CAMPUS_SERVICE_STUDACT.put("id", "47979b52-5773-4593-bd37-f39db075a9b4");
        CAMPUS_SERVICE_STUDACT.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_STUDACT.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_STUDACT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_STUDACT.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_STUDACT.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_STUDACT.put("name", "Student Activities/Clubs");
        CAMPUS_SERVICE_STUDACT.put("description", "Student Activities/Clubs");

        CAMPUS_SERVICE_STUDEMPLY = new JSONObject();
        CAMPUS_SERVICE_STUDEMPLY.put("id", "2d55b449-0764-4a97-a96e-eea6dc6e575c");
        CAMPUS_SERVICE_STUDEMPLY.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_STUDEMPLY.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_STUDEMPLY.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_STUDEMPLY.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_STUDEMPLY.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_STUDEMPLY.put("name", "Student Employment");
        CAMPUS_SERVICE_STUDEMPLY.put("description", "Student Employment");

        CAMPUS_SERVICE_SSS = new JSONObject();
        CAMPUS_SERVICE_SSS.put("id", "cd3de8de-33bf-4c5f-a1d5-1ea31fe44554");
        CAMPUS_SERVICE_SSS.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_SSS.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_SSS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_SSS.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_SSS.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_SSS.put("name", "Student Support Services");
        CAMPUS_SERVICE_SSS.put("description", "Student Support Services");

        CAMPUS_SERVICE_TUTORSER = new JSONObject();
        CAMPUS_SERVICE_TUTORSER.put("id", "c21f54ae-335f-4866-8673-ee0804a57170");
        CAMPUS_SERVICE_TUTORSER.put("createdDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_TUTORSER.put("createdBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_TUTORSER.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CAMPUS_SERVICE_TUTORSER.put("modifiedBy", getDefaultCreatedModifiedBy());
        CAMPUS_SERVICE_TUTORSER.put("objectStatus", "ACTIVE");
        CAMPUS_SERVICE_TUTORSER.put("name", "Tutorial Services");
        CAMPUS_SERVICE_TUTORSER.put("description", "Tutorial Services");

        
        CAMPUS_SERVICE_ROWS = new JSONArray();
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_MIN);
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_CARREER);
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_COUNS);
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_DIS);
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_PHYACT);
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_STUDACT);
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_STUDEMPLY);
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_SSS);
        CAMPUS_SERVICE_ROWS.add(CAMPUS_SERVICE_TUTORSER);
     

        CAMPUS_SERVICE_RESPONSE = new JSONObject();
        CAMPUS_SERVICE_RESPONSE.put("success", "true");
        CAMPUS_SERVICE_RESPONSE.put("message", "");
        CAMPUS_SERVICE_RESPONSE.put("results", CAMPUS_SERVICE_ROWS.size());
        CAMPUS_SERVICE_RESPONSE.put("rows", CAMPUS_SERVICE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsCampusServiceReference() {
        final JSONObject testPostPutNegative = (JSONObject)CAMPUS_SERVICE_MIN.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(CAMPUS_SERVICE_PATH,
                testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCampusServiceReferenceAllBody() {

        testResponseBody(CAMPUS_SERVICE_PATH, CAMPUS_SERVICE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCampusServiceReferenceSingleItemBody() {

        testSingleItemResponseBody(CAMPUS_SERVICE_PATH, CAMPUS_SERVICE_CARREER);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsCampusServiceReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CAMPUS_SERVICE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCampusServiceReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) CAMPUS_SERVICE_COUNS.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(CAMPUS_SERVICE_PATH, CAMPUS_SERVICE_DIS.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testCampusServiceReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) CAMPUS_SERVICE_PHYACT.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = CAMPUS_SERVICE_STUDACT;

        referenceNegativeSupportedMethodTest(CAMPUS_SERVICE_PATH, testNegativePostObject,
                testNegativeValidateObject);
    }
}