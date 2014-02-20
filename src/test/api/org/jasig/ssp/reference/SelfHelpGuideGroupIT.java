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


public class SelfHelpGuideGroupIT extends AbstractReferenceTest {

    private static final String SELF_HELP_GUIDE_GROUP_PATH = REFERENCE_PATH + "selfHelpGuideGroup";

    private static final JSONObject SELF_HELP_GUIDE_GROUP_GRP1;
    private static final JSONObject SELF_HELP_GUIDE_GROUP_GRP2;
    private static final JSONObject SELF_HELP_GUIDE_GROUP_GRP3;

    private static final JSONArray SELF_HELP_GUIDE_GROUP_ROWS;
    private static final JSONObject SELF_HELP_GUIDE_GROUP_RESPONSE;

    static {

        SELF_HELP_GUIDE_GROUP_GRP1 = new JSONObject();
        SELF_HELP_GUIDE_GROUP_GRP1.put("id", "24e17020-74ba-11e3-981f-0800200c9a66");
        SELF_HELP_GUIDE_GROUP_GRP1.put("createdDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_GROUP_GRP1.put("createdBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_GROUP_GRP1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_GROUP_GRP1.put("modifiedBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_GROUP_GRP1.put("objectStatus", "ACTIVE");
        SELF_HELP_GUIDE_GROUP_GRP1.put("name", "Self Help Guide Group 1");
        SELF_HELP_GUIDE_GROUP_GRP1.put("description", "Self Help Guide Group 1");

        SELF_HELP_GUIDE_GROUP_GRP2 = new JSONObject();
        SELF_HELP_GUIDE_GROUP_GRP2.put("id", "24e17021-74ba-11e3-981f-0800200c9a66");
        SELF_HELP_GUIDE_GROUP_GRP2.put("createdDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_GROUP_GRP2.put("createdBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_GROUP_GRP2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_GROUP_GRP2.put("modifiedBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_GROUP_GRP2.put("objectStatus", "ACTIVE");
        SELF_HELP_GUIDE_GROUP_GRP2.put("name", "Self Help Guide Group 2");
        SELF_HELP_GUIDE_GROUP_GRP2.put("description", "Self Help Guide Group 2");

        SELF_HELP_GUIDE_GROUP_GRP3 = new JSONObject();
        SELF_HELP_GUIDE_GROUP_GRP3.put("id", "24e17022-74ba-11e3-981f-0800200c9a66");
        SELF_HELP_GUIDE_GROUP_GRP3.put("createdDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_GROUP_GRP3.put("createdBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_GROUP_GRP3.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SELF_HELP_GUIDE_GROUP_GRP3.put("modifiedBy", getDefaultCreatedModifiedBy());
        SELF_HELP_GUIDE_GROUP_GRP3.put("objectStatus", "ACTIVE");
        SELF_HELP_GUIDE_GROUP_GRP3.put("name", "Self Help Guide Group 3");
        SELF_HELP_GUIDE_GROUP_GRP3.put("description", "Self Help Guide Group 3");


        SELF_HELP_GUIDE_GROUP_ROWS = new JSONArray();
        SELF_HELP_GUIDE_GROUP_ROWS.add(SELF_HELP_GUIDE_GROUP_GRP1);
        SELF_HELP_GUIDE_GROUP_ROWS.add(SELF_HELP_GUIDE_GROUP_GRP2);
        SELF_HELP_GUIDE_GROUP_ROWS.add(SELF_HELP_GUIDE_GROUP_GRP3);


        SELF_HELP_GUIDE_GROUP_RESPONSE = new JSONObject();
        SELF_HELP_GUIDE_GROUP_RESPONSE.put("success", "true");
        SELF_HELP_GUIDE_GROUP_RESPONSE.put("message", "");
        SELF_HELP_GUIDE_GROUP_RESPONSE.put("results", SELF_HELP_GUIDE_GROUP_ROWS.size());
        SELF_HELP_GUIDE_GROUP_RESPONSE.put("rows", SELF_HELP_GUIDE_GROUP_ROWS);
    }


    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsFundingSourceReference() {
        final JSONObject testPostPutNegative = (JSONObject) SELF_HELP_GUIDE_GROUP_GRP1.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(SELF_HELP_GUIDE_GROUP_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFundingSourceReferenceAllBody() {

        testResponseBody(SELF_HELP_GUIDE_GROUP_PATH, SELF_HELP_GUIDE_GROUP_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFundingSourceReferenceSingleItemBody() {

        testSingleItemResponseBody(SELF_HELP_GUIDE_GROUP_PATH, SELF_HELP_GUIDE_GROUP_GRP1);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsFundingSourceReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, SELF_HELP_GUIDE_GROUP_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFundingSourceReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) SELF_HELP_GUIDE_GROUP_GRP2.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(SELF_HELP_GUIDE_GROUP_PATH, SELF_HELP_GUIDE_GROUP_GRP3.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFundingSourceReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) SELF_HELP_GUIDE_GROUP_GRP2.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = SELF_HELP_GUIDE_GROUP_GRP1;

        referenceNegativeSupportedMethodTest(SELF_HELP_GUIDE_GROUP_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
