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


public class FundingSourceIT extends AbstractReferenceTest {

    private static final String FUNDING_SOURCE_PATH = REFERENCE_PATH + "fundingSource";

    private static final JSONObject FUNDING_SOURCE_ASST;
    private static final JSONObject FUNDING_SOURCE_EMPLYR;
    private static final JSONObject FUNDING_SOURCE_PELL;
    private static final JSONObject FUNDING_SOURCE_FINAID;
    private static final JSONObject FUNDING_SOURCE_GIBILL;
    private static final JSONObject FUNDING_SOURCE_MILTUIT;
    private static final JSONObject FUNDING_SOURCE_OTHR;
    private static final JSONObject FUNDING_SOURCE_PARNT;
    private static final JSONObject FUNDING_SOURCE_SCHOL;
    private static final JSONObject FUNDING_SOURCE_SELF;
    private static final JSONObject FUNDING_SOURCE_SPOUSE;
    private static final JSONObject FUNDING_SOURCE_STUDLOAN;
    private static final JSONObject FUNDING_SOURCE_TAA;
    private static final JSONObject FUNDING_SOURCE_WIA;


    private static final JSONArray FUNDING_SOURCE_ROWS;
    private static final JSONObject FUNDING_SOURCE_RESPONSE;

    static {

       FUNDING_SOURCE_ASST = new JSONObject();
       FUNDING_SOURCE_ASST.put("id", "e2ca9893-c808-47ce-bea5-3b9d97ae6626");
       FUNDING_SOURCE_ASST.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_ASST.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_ASST.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_ASST.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_ASST.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_ASST.put("name", "Assistance Needed");
       FUNDING_SOURCE_ASST.put("description", "Assistance Needed");

       FUNDING_SOURCE_EMPLYR = new JSONObject();
       FUNDING_SOURCE_EMPLYR.put("id", "77df68fe-2a5f-47ef-914b-d38774e5a485");
       FUNDING_SOURCE_EMPLYR.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_EMPLYR.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_EMPLYR.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_EMPLYR.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_EMPLYR.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_EMPLYR.put("name", "Employer");
       FUNDING_SOURCE_EMPLYR.put("description", "Employer");

       FUNDING_SOURCE_PELL = new JSONObject();
       FUNDING_SOURCE_PELL.put("id", "74f8b985-70a3-4379-991f-a660bb99ea9c");
       FUNDING_SOURCE_PELL.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_PELL.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_PELL.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_PELL.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_PELL.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_PELL.put("name", "Federal Pell Grant");
       FUNDING_SOURCE_PELL.put("description", "Federal Pell Grant");

       FUNDING_SOURCE_FINAID = new JSONObject();
       FUNDING_SOURCE_FINAID.put("id", "41358be9-c202-4dda-b8ab-99cba9a9432f");
       FUNDING_SOURCE_FINAID.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_FINAID.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_FINAID.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_FINAID.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_FINAID.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_FINAID.put("name", "Financial Aid");
       FUNDING_SOURCE_FINAID.put("description", "Financial Aid");

       FUNDING_SOURCE_GIBILL = new JSONObject();
       FUNDING_SOURCE_GIBILL.put("id", "db26cf97-02af-493a-a0e4-d3f7fa08629f");
       FUNDING_SOURCE_GIBILL.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_GIBILL.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_GIBILL.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_GIBILL.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_GIBILL.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_GIBILL.put("name", "GI Bill");
       FUNDING_SOURCE_GIBILL.put("description", "GI Bill");

       FUNDING_SOURCE_MILTUIT = new JSONObject();
       FUNDING_SOURCE_MILTUIT.put("id", "80372221-b00b-4977-8c22-f908697b2762");
       FUNDING_SOURCE_MILTUIT.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_MILTUIT.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_MILTUIT.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_MILTUIT.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_MILTUIT.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_MILTUIT.put("name", "Military Tuition Assistance");
       FUNDING_SOURCE_MILTUIT.put("description", "Military Tuition Assistance");

       FUNDING_SOURCE_OTHR = new JSONObject();
       FUNDING_SOURCE_OTHR.put("id", "365e8c95-f356-4f1f-8d79-4771ae8b0291");
       FUNDING_SOURCE_OTHR.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_OTHR.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_OTHR.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_OTHR.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_OTHR.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_OTHR.put("name", "Other");
       FUNDING_SOURCE_OTHR.put("description", "Other");

       FUNDING_SOURCE_PARNT = new JSONObject();
       FUNDING_SOURCE_PARNT.put("id", "9d36198b-eaa0-49e8-859a-ffaa646b318f");
       FUNDING_SOURCE_PARNT.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_PARNT.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_PARNT.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_PARNT.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_PARNT.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_PARNT.put("name", "Parent/Other Relative");
       FUNDING_SOURCE_PARNT.put("description", "Parent/Other Relative");

       FUNDING_SOURCE_SCHOL = new JSONObject();
       FUNDING_SOURCE_SCHOL.put("id", "c0d24dd9-d6ce-4f22-99d0-ed12df8b8bd0");
       FUNDING_SOURCE_SCHOL.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_SCHOL.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_SCHOL.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_SCHOL.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_SCHOL.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_SCHOL.put("name", "Scholarship");
       FUNDING_SOURCE_SCHOL.put("description", "Scholarship");

       FUNDING_SOURCE_SELF = new JSONObject();
       FUNDING_SOURCE_SELF.put("id", "425be625-78ce-4099-8920-e903dd19900a");
       FUNDING_SOURCE_SELF.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_SELF.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_SELF.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_SELF.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_SELF.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_SELF.put("name", "Self");
       FUNDING_SOURCE_SELF.put("description", "Self");

       FUNDING_SOURCE_SPOUSE = new JSONObject();
       FUNDING_SOURCE_SPOUSE.put("id", "9e9d3655-74f7-45b4-9187-0536e4445ac0");
       FUNDING_SOURCE_SPOUSE.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_SPOUSE.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_SPOUSE.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_SPOUSE.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_SPOUSE.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_SPOUSE.put("name", "Spousal Assistance");
       FUNDING_SOURCE_SPOUSE.put("description", "Spousal Assistance");

       FUNDING_SOURCE_STUDLOAN = new JSONObject();
       FUNDING_SOURCE_STUDLOAN.put("id", "7321d900-39d1-47f3-82c6-f3c232e492aa");
       FUNDING_SOURCE_STUDLOAN.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_STUDLOAN.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_STUDLOAN.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_STUDLOAN.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_STUDLOAN.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_STUDLOAN.put("name", "Student Loan");
       FUNDING_SOURCE_STUDLOAN.put("description", "Student Loan");

       FUNDING_SOURCE_TAA = new JSONObject();
       FUNDING_SOURCE_TAA.put("id", "86445dc9-3947-4e53-b91f-2a41cceeb0f4");
       FUNDING_SOURCE_TAA.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_TAA.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_TAA.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_TAA.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_TAA.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_TAA.put("name", "Trade Adjustment Act (TAA)");
       FUNDING_SOURCE_TAA.put("description", "Trade Adjustment Act (TAA)");

       FUNDING_SOURCE_WIA = new JSONObject();
       FUNDING_SOURCE_WIA.put("id", "380c15c4-16d9-440b-9b32-6f909401eb8b");
       FUNDING_SOURCE_WIA.put("createdDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_WIA.put("createdBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_WIA.put("modifiedDate", getDefaultCreatedModifiedByDate());
       FUNDING_SOURCE_WIA.put("modifiedBy", getDefaultCreatedModifiedBy());
       FUNDING_SOURCE_WIA.put("objectStatus", "ACTIVE");
       FUNDING_SOURCE_WIA.put("name", "Workforce Investment Act");
       FUNDING_SOURCE_WIA.put("description", "Workforce Investment Act");


       FUNDING_SOURCE_ROWS = new JSONArray();
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_ASST);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_EMPLYR);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_PELL);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_FINAID);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_GIBILL);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_MILTUIT);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_OTHR);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_PARNT);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_SCHOL);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_SELF);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_SPOUSE);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_STUDLOAN);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_TAA);
       FUNDING_SOURCE_ROWS.add(FUNDING_SOURCE_WIA);


       FUNDING_SOURCE_RESPONSE = new JSONObject();
       FUNDING_SOURCE_RESPONSE.put("success", "true");
       FUNDING_SOURCE_RESPONSE.put("message", "");
       FUNDING_SOURCE_RESPONSE.put("results", FUNDING_SOURCE_ROWS.size());
       FUNDING_SOURCE_RESPONSE.put("rows", FUNDING_SOURCE_ROWS);
    }


    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsFundingSourceReference() {
        final JSONObject testPostPutNegative = (JSONObject) FUNDING_SOURCE_ASST.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(FUNDING_SOURCE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFundingSourceReferenceAllBody() {

        testResponseBody(FUNDING_SOURCE_PATH, FUNDING_SOURCE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFundingSourceReferenceSingleItemBody() {

        testSingleItemResponseBody(FUNDING_SOURCE_PATH, FUNDING_SOURCE_PARNT);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsFundingSourceReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, FUNDING_SOURCE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFundingSourceReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) FUNDING_SOURCE_EMPLYR.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(FUNDING_SOURCE_PATH, FUNDING_SOURCE_PELL.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testFundingSourceReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) FUNDING_SOURCE_FINAID.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = FUNDING_SOURCE_SCHOL;

        referenceNegativeSupportedMethodTest(FUNDING_SOURCE_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
