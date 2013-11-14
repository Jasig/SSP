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


public class ConfidentialityDisclosureAgreementIT extends AbstractReferenceTest {

    private static final String CONFIDENTIALITY_DISCLOSURE_AGREEMENT_PATH = REFERENCE_PATH +
            "confidentialityDisclosureAgreement";

    private static final JSONObject CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1;
    private static final JSONObject CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN;
    private static final JSONObject CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2;

    private static final JSONArray CONFIDENTIALITY_DISCLOSURE_AGREEMENT_ROWS;
    private static final JSONObject CONFIDENTIALITY_DISCLOSURE_AGREEMENT_RESPONSE;

    static {

        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1 = new JSONObject();
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("id", "0a740a2a-409d-1271-8140-a23f925500dc");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("name", "Confidentiality Agreement");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("description", "Confidentiality Agreement");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.put("text", "Detailed Confidentiality Agreement goes her1.<br>");

        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2 = new JSONObject();
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("id", "0a840a2a-409d-1271-8140-a23f925500dc");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("name", "Confidentiality Agreement1");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("description", "Confidentiality Agreement1");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.put("text", "Detailed Confidentiality Agreement goes here1.<br>");

        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN = new JSONObject();
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("id", "0a640a2a-409d-1271-8140-a23f925500dc");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("name", "Student-School Confidentiality Agreement");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("description", "Final Publication of the Student-School " +
                "Confidentiality Agreement");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.put("text", "This is a school confidentiality agreement for use of " +
                "your personal data in helping you to succeed. You agree to use our services with the best of " +
                "intentions, and not to defraud or harm other users or faculty or staff. Furthermore, we reserve the " +
                "right to store and keep record of any and all personal data given to us in the course of using " +
                "Student Success Plan and our Academic Counseling Services. We will not share your data with any " +
                "other person or entity and it will be reserved only for helping you succeed.&nbsp, <br><br>If you " +
                "agree to these terms, sign below.<br><br>X__________________ &nbsp,  &nbsp, Date: ___________________");


        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_ROWS = new JSONArray();
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_ROWS.add(CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1);
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_ROWS.add(CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2);
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_ROWS.add(CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN);


        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_RESPONSE = new JSONObject();
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_RESPONSE.put("success", "true");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_RESPONSE.put("message", "");
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_RESPONSE.put("results", CONFIDENTIALITY_DISCLOSURE_AGREEMENT_ROWS.size());
        CONFIDENTIALITY_DISCLOSURE_AGREEMENT_RESPONSE.put("rows", CONFIDENTIALITY_DISCLOSURE_AGREEMENT_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsConfidentialityDisclosureAgreementReference() {
        final JSONObject testPostPutNegative = (JSONObject)CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(CONFIDENTIALITY_DISCLOSURE_AGREEMENT_PATH,
                testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityDisclosureAgreementReferenceAllBody() {

        testResponseBody(CONFIDENTIALITY_DISCLOSURE_AGREEMENT_PATH, CONFIDENTIALITY_DISCLOSURE_AGREEMENT_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityDisclosureAgreementReferenceSingleItemBody() {

        testSingleItemResponseBody(CONFIDENTIALITY_DISCLOSURE_AGREEMENT_PATH, CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsConfidentialityDisclosureAgreementReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CONFIDENTIALITY_DISCLOSURE_AGREEMENT_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityDisclosureAgreementReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) CONFIDENTIALITY_DISCLOSURE_AGREEMENT_FIN.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(CONFIDENTIALITY_DISCLOSURE_AGREEMENT_PATH,
                CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityDisclosureAgreementReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA1.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = CONFIDENTIALITY_DISCLOSURE_AGREEMENT_CA2;

        referenceNegativeSupportedMethodTest(CONFIDENTIALITY_DISCLOSURE_AGREEMENT_PATH, testNegativePostObject,
                testNegativeValidateObject);
    }
}