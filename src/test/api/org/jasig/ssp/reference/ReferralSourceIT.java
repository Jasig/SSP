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


public class ReferralSourceIT extends AbstractReferenceTest {

    private static final String REFERRAL_SOURCE_PATH = REFERENCE_PATH + "referralSource";

    private static final JSONObject REFERRAL_SOURCE_GC;
    private static final JSONObject REFERRAL_SOURCE_CJC;
    private static final JSONObject REFERRAL_SOURCE_SELF;
    private static final JSONObject REFERRAL_SOURCE_FAC;
    private static final JSONObject REFERRAL_SOURCE_CON;
    private static final JSONObject REFERRAL_SOURCE_OMB;

    private static final JSONArray REFERRAL_SOURCE_ROWS;
    private static final JSONObject REFERRAL_SOURCE_RESPONSE;

    static {

        REFERRAL_SOURCE_GC = new JSONObject();
        REFERRAL_SOURCE_GC.put("id", "858b0cab-1326-11e2-818e-406c8f22c3ce");
        REFERRAL_SOURCE_GC.put("createdDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_GC.put("createdBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_GC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_GC.put("modifiedBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_GC.put("objectStatus", "ACTIVE");
        REFERRAL_SOURCE_GC.put("link", null);
        REFERRAL_SOURCE_GC.put("name", "HS Guidance Counselor");
        REFERRAL_SOURCE_GC.put("description", "Referral from a high school guidance counselor.");

        REFERRAL_SOURCE_CJC = new JSONObject();
        REFERRAL_SOURCE_CJC.put("id", "85919430-1326-11e2-ad10-406c8f22c3ce");
        REFERRAL_SOURCE_CJC.put("createdDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_CJC.put("createdBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_CJC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_CJC.put("modifiedBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_CJC.put("objectStatus", "ACTIVE");
        REFERRAL_SOURCE_CJC.put("link", null);
        REFERRAL_SOURCE_CJC.put("name", "County Job Center");
        REFERRAL_SOURCE_CJC.put("description", "Referral from a county-level vocational placement service.");

        REFERRAL_SOURCE_SELF = new JSONObject();
        REFERRAL_SOURCE_SELF.put("id", "85994cd7-1326-11e2-a521-406c8f22c3ce");
        REFERRAL_SOURCE_SELF.put("createdDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_SELF.put("createdBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_SELF.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_SELF.put("modifiedBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_SELF.put("objectStatus", "ACTIVE");
        REFERRAL_SOURCE_SELF.put("link", null);
        REFERRAL_SOURCE_SELF.put("name", "Self Referral");
        REFERRAL_SOURCE_SELF.put("description", "Purely voluntary participation.");

        REFERRAL_SOURCE_CON = new JSONObject();
        REFERRAL_SOURCE_CON.put("id", "859ff107-1326-11e2-8698-406c8f22c3ce");
        REFERRAL_SOURCE_CON.put("createdDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_CON.put("createdBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_CON.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_CON.put("modifiedBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_CON.put("objectStatus", "ACTIVE");
        REFERRAL_SOURCE_CON.put("link", null);
        REFERRAL_SOURCE_CON.put("name", "Student Conduct");
        REFERRAL_SOURCE_CON.put("description", "Behavioral correction.");

        REFERRAL_SOURCE_OMB = new JSONObject();
        REFERRAL_SOURCE_OMB.put("id", "ccadd634-bd7a-11e1-8d28-3368721922dc");
        REFERRAL_SOURCE_OMB.put("createdDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_OMB.put("createdBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_OMB.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_OMB.put("modifiedBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_OMB.put("objectStatus", "ACTIVE");
        REFERRAL_SOURCE_OMB.put("link", null);
        REFERRAL_SOURCE_OMB.put("name", "Ombudsman");
        REFERRAL_SOURCE_OMB.put("description", "Ombudsman");

        REFERRAL_SOURCE_FAC = new JSONObject();
        REFERRAL_SOURCE_FAC.put("id", "f6201a04-bb31-4ca5-b606-609f3ad09f87");
        REFERRAL_SOURCE_FAC.put("createdDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_FAC.put("createdBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_FAC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        REFERRAL_SOURCE_FAC.put("modifiedBy", getDefaultCreatedModifiedBy());
        REFERRAL_SOURCE_FAC.put("objectStatus", "ACTIVE");
        REFERRAL_SOURCE_FAC.put("link", null);
        REFERRAL_SOURCE_FAC.put("name", "Faculty / Staff Referral");
        REFERRAL_SOURCE_FAC.put("description", "Faculty / Staff Referral");


        REFERRAL_SOURCE_ROWS = new JSONArray();
        REFERRAL_SOURCE_ROWS.add(REFERRAL_SOURCE_CJC);
        REFERRAL_SOURCE_ROWS.add(REFERRAL_SOURCE_FAC);
        REFERRAL_SOURCE_ROWS.add(REFERRAL_SOURCE_GC);
        REFERRAL_SOURCE_ROWS.add(REFERRAL_SOURCE_OMB);
        REFERRAL_SOURCE_ROWS.add(REFERRAL_SOURCE_SELF);
        REFERRAL_SOURCE_ROWS.add(REFERRAL_SOURCE_CON);

        REFERRAL_SOURCE_RESPONSE = new JSONObject();
        REFERRAL_SOURCE_RESPONSE.put("success", "true");
        REFERRAL_SOURCE_RESPONSE.put("message", "");
        REFERRAL_SOURCE_RESPONSE.put("results", REFERRAL_SOURCE_ROWS.size());
        REFERRAL_SOURCE_RESPONSE.put("rows", REFERRAL_SOURCE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsReferralSourceReference() {
        final JSONObject testPostPutNegative = (JSONObject)REFERRAL_SOURCE_GC.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(REFERRAL_SOURCE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testReferralSourceReferenceAllBody() {

        testResponseBody(REFERRAL_SOURCE_PATH, REFERRAL_SOURCE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testReferralSourceReferenceSingleItemBody() {

       testSingleItemResponseBody(REFERRAL_SOURCE_PATH, REFERRAL_SOURCE_SELF);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsReferralSourceReference() {
        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, REFERRAL_SOURCE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testReferralSourceReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) REFERRAL_SOURCE_CJC.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(REFERRAL_SOURCE_PATH, REFERRAL_SOURCE_OMB.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testReferralSourceReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) REFERRAL_SOURCE_FAC.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = REFERRAL_SOURCE_CON;

        referenceNegativeSupportedMethodTest(REFERRAL_SOURCE_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
