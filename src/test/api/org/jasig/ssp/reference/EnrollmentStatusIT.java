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


import com.jayway.restassured.specification.ResponseSpecification;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.jasig.ssp.ExpectationUtils.expectListResponseObjectAtIndex;


public class EnrollmentStatusIT extends AbstractReferenceTest {

    private static final String ENROLLMENT_STATUS_PATH = REFERENCE_PATH + "enrollmentStatus";

    private static final JSONObject ENROLLMENT_STATUS_EN;
    private static final JSONObject ENROLLMENT_STATUS_UNEN;
    private static final JSONObject ENROLLMENT_STATUS_UNKNWN;

    private static final JSONArray ENROLLMENT_STATUS_ROWS;
    private static final JSONObject ENROLLMENT_STATUS_RESPONSE;

    static {

        ENROLLMENT_STATUS_EN = new JSONObject();
        ENROLLMENT_STATUS_EN.put("id", "3a6352c9-e7fe-e555-7f69-0124a5e5fe71");
        ENROLLMENT_STATUS_EN.put("createdDate", getDefaultCreatedModifiedByDate());
        ENROLLMENT_STATUS_EN.put("createdBy", getDefaultCreatedModifiedBy());
        ENROLLMENT_STATUS_EN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ENROLLMENT_STATUS_EN.put("modifiedBy", getDefaultCreatedModifiedBy());
        ENROLLMENT_STATUS_EN.put("objectStatus", "ACTIVE");
        ENROLLMENT_STATUS_EN.put("name", "default");
        ENROLLMENT_STATUS_EN.put("description", "Enrolled");
        ENROLLMENT_STATUS_EN.put("code", "enrolled");

        ENROLLMENT_STATUS_UNEN = new JSONObject();
        ENROLLMENT_STATUS_UNEN.put("id", "3a7352c9-e7fe-e555-7f69-0124a5e5fe71");
        ENROLLMENT_STATUS_UNEN.put("createdDate", getDefaultCreatedModifiedByDate());
        ENROLLMENT_STATUS_UNEN.put("createdBy", getDefaultCreatedModifiedBy());
        ENROLLMENT_STATUS_UNEN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ENROLLMENT_STATUS_UNEN.put("modifiedBy", getDefaultCreatedModifiedBy());
        ENROLLMENT_STATUS_UNEN.put("objectStatus", "ACTIVE");
        // lower-cased to work around platform-specific sorting issues
        ENROLLMENT_STATUS_UNEN.put("name", "unenrolled");
        ENROLLMENT_STATUS_UNEN.put("description", "UnEnrolled");
        ENROLLMENT_STATUS_UNEN.put("code", "unenrolled");

        ENROLLMENT_STATUS_UNKNWN = new JSONObject();
        ENROLLMENT_STATUS_UNKNWN.put("id", "3a8352c9-e7fe-e555-7f69-0124a5e5fe71");
        ENROLLMENT_STATUS_UNKNWN.put("createdDate", getDefaultCreatedModifiedByDate());
        ENROLLMENT_STATUS_UNKNWN.put("createdBy", getDefaultCreatedModifiedBy());
        ENROLLMENT_STATUS_UNKNWN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        ENROLLMENT_STATUS_UNKNWN.put("modifiedBy", getDefaultCreatedModifiedBy());
        ENROLLMENT_STATUS_UNKNWN.put("objectStatus", "ACTIVE");
        // lower-cased to work around platform-specific sorting issues
        ENROLLMENT_STATUS_UNKNWN.put("name", "unknown");
        ENROLLMENT_STATUS_UNKNWN.put("description", "Unknown");
        ENROLLMENT_STATUS_UNKNWN.put("code", "unknown");

        
        ENROLLMENT_STATUS_ROWS = new JSONArray();
        ENROLLMENT_STATUS_ROWS.add(ENROLLMENT_STATUS_EN);
        ENROLLMENT_STATUS_ROWS.add(ENROLLMENT_STATUS_UNEN);
        ENROLLMENT_STATUS_ROWS.add(ENROLLMENT_STATUS_UNKNWN);

        
        ENROLLMENT_STATUS_RESPONSE = new JSONObject();
        ENROLLMENT_STATUS_RESPONSE.put("success", "true");
        ENROLLMENT_STATUS_RESPONSE.put("message", "");
        ENROLLMENT_STATUS_RESPONSE.put("results", ENROLLMENT_STATUS_ROWS.size());
        ENROLLMENT_STATUS_RESPONSE.put("rows", ENROLLMENT_STATUS_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEnrollmentStatusReference() {
        final JSONObject testPostPutNegative = (JSONObject) ENROLLMENT_STATUS_EN.clone();
        testPostPutNegative.put("name", "testPostUnAuth");
        testPostPutNegative.put("code", ("NEGTST" + testPassDeConflictNumber));

        referenceAuthenticationControlledMethodNegativeTest(ENROLLMENT_STATUS_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEnrollmentStatusReferenceAllBody() {

        // At least one of the other tests POSTs an ACTIVE record. So we use GTE
        // assertion on the spec below, else unpredictable test execution
        // ordering will make this test unstable. Was just simpler at the time
        // than figuring out either how to make that POST test follow-up with
        // a DELETE or maintaining a class-scoped context for adjusting
        // test expectations based on accumulated changes to persistent data.

        ResponseSpecification spec =
                expect()
                        .contentType("application/json")
                        .statusCode(200)
                        .log().ifError()
                        .body("results", greaterThanOrEqualTo(3))
                        .and()
                        .body("success", equalTo("true"))
                        .and()
                        .body("rows", hasSize(greaterThanOrEqualTo(3)))
                        .and();

        spec = expectListResponseObjectAtIndex(spec, 0, ENROLLMENT_STATUS_ROWS);
        spec.when().get(ENROLLMENT_STATUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEnrollmentStatusReferenceSingleItemBody() {

        testSingleItemResponseBody(ENROLLMENT_STATUS_PATH, ENROLLMENT_STATUS_UNEN);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEnrollmentStatusReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, ENROLLMENT_STATUS_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEnrollmentStatusReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ENROLLMENT_STATUS_UNEN.clone();
        testPostPutPositive.put("name", "testPostPositive");
        testPostPutPositive.put("code", ("POSTST" + testPassDeConflictNumber));

        referencePositiveSupportedMethodTest(ENROLLMENT_STATUS_PATH, ENROLLMENT_STATUS_UNKNWN.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEnrollmentStatusReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ENROLLMENT_STATUS_EN.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        testNegativePostObject.put("code", ("SUPPNEGTST" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = ENROLLMENT_STATUS_UNKNWN;

        referenceNegativeSupportedMethodTest(ENROLLMENT_STATUS_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}