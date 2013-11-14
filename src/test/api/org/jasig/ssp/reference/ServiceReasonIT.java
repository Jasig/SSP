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
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.Matchers.equalTo;


public class ServiceReasonIT extends AbstractReferenceTest {

    private static final String SERVICE_REASON_PATH = REFERENCE_PATH + "serviceReason";

    private static final JSONObject SERVICE_REASON_RA;
    private static final JSONObject SERVICE_REASON_SA;
    private static final JSONObject SERVICE_REASON_ESL;
    private static final JSONObject SERVICE_REASON_SCHOL;
    private static final JSONObject SERVICE_REASON_MIN;
    private static final JSONObject SERVICE_REASON_COUNS;
    private static final JSONObject SERVICE_REASON_ADV;
    private static final JSONObject SERVICE_REASON_PRO;
    private static final JSONObject SERVICE_REASON_BH;
    private static final JSONObject SERVICE_REASON_CAREER;
    private static final JSONObject SERVICE_REASON_UNPRPR;
    private static final JSONObject SERVICE_REASON_UND;
    private static final JSONObject SERVICE_REASON_COND;
    private static final JSONObject SERVICE_REASON_INC;
    private static final JSONObject SERVICE_REASON_RISK;

    private static final JSONArray SERVICE_REASON_ROWS;
    private static final JSONObject SERVICE_REASON_RESPONSE;

    static {

        SERVICE_REASON_RA = new JSONObject();
        SERVICE_REASON_RA.put("id", "0a640a2a-409d-1271-8140-d0b26b6500f3");
        SERVICE_REASON_RA.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_RA.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_RA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_RA.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_RA.put("objectStatus", "ACTIVE");
        SERVICE_REASON_RA.put("name", "Re-Admit Process");
        SERVICE_REASON_RA.put("description", "Re-Admit Process");

        SERVICE_REASON_SA = new JSONObject();
        SERVICE_REASON_SA.put("id", "0a640a2a-409d-1271-8140-d0b2bc6500f4");
        SERVICE_REASON_SA.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_SA.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_SA.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_SA.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_SA.put("objectStatus", "ACTIVE");
        SERVICE_REASON_SA.put("name", "Satisfactory Academic Process");
        SERVICE_REASON_SA.put("description", "Satisfactory Academic Process");

        SERVICE_REASON_ESL = new JSONObject();
        SERVICE_REASON_ESL.put("id", "0a640a2a-409d-1271-8140-d0b331f300f5");
        SERVICE_REASON_ESL.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_ESL.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_ESL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_ESL.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_ESL.put("objectStatus", "ACTIVE");
        SERVICE_REASON_ESL.put("name", "English as a Second Language");
        SERVICE_REASON_ESL.put("description", "English as a Second Language");

        SERVICE_REASON_SCHOL = new JSONObject();
        SERVICE_REASON_SCHOL.put("id", "0a640a2a-409d-1271-8140-d0b3878000f6");
        SERVICE_REASON_SCHOL.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_SCHOL.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_SCHOL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_SCHOL.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_SCHOL.put("objectStatus", "ACTIVE");
        SERVICE_REASON_SCHOL.put("name", "Scholarship Recipient");
        SERVICE_REASON_SCHOL.put("description", "Scholarship Recipient");

        SERVICE_REASON_MIN = new JSONObject();
        SERVICE_REASON_MIN.put("id", "0a640a2a-409d-1271-8140-d0b4163d00f7");
        SERVICE_REASON_MIN.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_MIN.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_MIN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_MIN.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_MIN.put("objectStatus", "ACTIVE");
        SERVICE_REASON_MIN.put("name", "Minority Support Program");
        SERVICE_REASON_MIN.put("description", "Minority Support Program");

        SERVICE_REASON_COUNS = new JSONObject();
        SERVICE_REASON_COUNS.put("id", "0a640a2a-409d-1271-8140-d0b4e00000f8");
        SERVICE_REASON_COUNS.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_COUNS.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_COUNS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_COUNS.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_COUNS.put("objectStatus", "ACTIVE");
        SERVICE_REASON_COUNS.put("name", "Counseling Support");
        SERVICE_REASON_COUNS.put("description", "Counseling Support");

        SERVICE_REASON_ADV = new JSONObject();
        SERVICE_REASON_ADV.put("id", "0a640a2a-409d-1271-8140-d0b5037600f9");
        SERVICE_REASON_ADV.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_ADV.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_ADV.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_ADV.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_ADV.put("objectStatus", "ACTIVE");
        SERVICE_REASON_ADV.put("name", "Academic Advising");
        SERVICE_REASON_ADV.put("description", "Academic Advising");

        SERVICE_REASON_PRO = new JSONObject();
        SERVICE_REASON_PRO.put("id", "0a640a2a-409d-1271-8140-d0b6986a00fa");
        SERVICE_REASON_PRO.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_PRO.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_PRO.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_PRO.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_PRO.put("objectStatus", "ACTIVE");
        SERVICE_REASON_PRO.put("name", "Academic Probation");
        SERVICE_REASON_PRO.put("description", "Academic Probation");

        SERVICE_REASON_BH = new JSONObject();
        SERVICE_REASON_BH.put("id", "0a640a2a-40e7-15de-8140-ea77a1d40075");
        SERVICE_REASON_BH.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_BH.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_BH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_BH.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_BH.put("objectStatus", "ACTIVE");
        SERVICE_REASON_BH.put("name", "Blue Hats");
        SERVICE_REASON_BH.put("description", "?????????");

        SERVICE_REASON_CAREER = new JSONObject();
        SERVICE_REASON_CAREER.put("id", "205df6c0-fea0-11e1-9678-406c8f22c3ce");
        SERVICE_REASON_CAREER.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_CAREER.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_CAREER.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_CAREER.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_CAREER.put("objectStatus", "ACTIVE");
        SERVICE_REASON_CAREER.put("name", "Career Coaching");
        SERVICE_REASON_CAREER.put("description", "Career Coaching");

        SERVICE_REASON_UNPRPR = new JSONObject();
        SERVICE_REASON_UNPRPR.put("id", "85709794-1326-11e2-8bab-406c8f22c3ce");
        SERVICE_REASON_UNPRPR.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_UNPRPR.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_UNPRPR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_UNPRPR.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_UNPRPR.put("objectStatus", "ACTIVE");
        SERVICE_REASON_UNPRPR.put("name", "Academically Unprepared");
        SERVICE_REASON_UNPRPR.put("description", "Suspected or demonstrated gaps in core competencies.");

        SERVICE_REASON_UND = new JSONObject();
        SERVICE_REASON_UND.put("id", "8576ff78-1326-11e2-940d-406c8f22c3ce");
        SERVICE_REASON_UND.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_UND.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_UND.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_UND.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_UND.put("objectStatus", "ACTIVE");
        SERVICE_REASON_UND.put("name", "Undecided Major");
        SERVICE_REASON_UND.put("description", "Might require assistance in selecting an academic discipline.");

        SERVICE_REASON_INC = new JSONObject();
        SERVICE_REASON_INC.put("id", "857da24c-1326-11e2-bb0c-406c8f22c3ce");
        SERVICE_REASON_INC.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_INC.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_INC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_INC.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_INC.put("objectStatus", "ACTIVE");
        SERVICE_REASON_INC.put("name", "Income Level");
        SERVICE_REASON_INC.put("description", "Financial challenges put academic performance at risk.");

        SERVICE_REASON_COND = new JSONObject();
        SERVICE_REASON_COND.put("id", "85848345-1326-11e2-afda-406c8f22c3ce");
        SERVICE_REASON_COND.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_COND.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_COND.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_COND.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_COND.put("objectStatus", "ACTIVE");
        SERVICE_REASON_COND.put("name", "Student Conduct");
        SERVICE_REASON_COND.put("description", "Behavioral correction.");

        SERVICE_REASON_RISK = new JSONObject();
        SERVICE_REASON_RISK.put("id", "f6201a04-bb31-4ca5-b606-609f3ad09f87");
        SERVICE_REASON_RISK.put("createdDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_RISK.put("createdBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_RISK.put("modifiedDate", getDefaultCreatedModifiedByDate());
        SERVICE_REASON_RISK.put("modifiedBy", getDefaultCreatedModifiedBy());
        SERVICE_REASON_RISK.put("objectStatus", "ACTIVE");
        SERVICE_REASON_RISK.put("name", "At-Risk Student");
        SERVICE_REASON_RISK.put("description", "Student needing additional structure based on institutional criteria.");

        SERVICE_REASON_ROWS = new JSONArray();
        SERVICE_REASON_ROWS.add(SERVICE_REASON_ADV);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_UNPRPR);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_PRO);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_RISK);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_BH);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_CAREER);
        SERVICE_REASON_ROWS.add( SERVICE_REASON_COUNS);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_ESL);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_INC);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_MIN);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_RA);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_SA);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_SCHOL);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_COND);
        SERVICE_REASON_ROWS.add(SERVICE_REASON_UND);

        SERVICE_REASON_RESPONSE = new JSONObject();
        SERVICE_REASON_RESPONSE.put("success", "true");
        SERVICE_REASON_RESPONSE.put("message", "");
        SERVICE_REASON_RESPONSE.put("results", SERVICE_REASON_ROWS.size());
        SERVICE_REASON_RESPONSE.put("rows", SERVICE_REASON_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsServiceReasonReference() {
        final JSONObject testPostPutNegative = (JSONObject) SERVICE_REASON_RA.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(SERVICE_REASON_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testServiceReasonReferenceAllBody() {

        final JSONArray serviceReasonAlternate = new JSONArray();
        serviceReasonAlternate.add(SERVICE_REASON_ADV);
        serviceReasonAlternate.add(SERVICE_REASON_PRO);
        serviceReasonAlternate.add(SERVICE_REASON_UNPRPR);
        serviceReasonAlternate.add(SERVICE_REASON_RISK);
        serviceReasonAlternate.add(SERVICE_REASON_BH);
        serviceReasonAlternate.add(SERVICE_REASON_CAREER);
        serviceReasonAlternate.add( SERVICE_REASON_COUNS);
        serviceReasonAlternate.add(SERVICE_REASON_ESL);
        serviceReasonAlternate.add(SERVICE_REASON_INC);
        serviceReasonAlternate.add(SERVICE_REASON_MIN);
        serviceReasonAlternate.add(SERVICE_REASON_RA);
        serviceReasonAlternate.add(SERVICE_REASON_SA);
        serviceReasonAlternate.add(SERVICE_REASON_SCHOL);
        serviceReasonAlternate.add(SERVICE_REASON_COND);
        serviceReasonAlternate.add(SERVICE_REASON_UND);

        final JSONObject serviceReasonAlternateResponse = new JSONObject();
        serviceReasonAlternateResponse.put("success", "true");
        serviceReasonAlternateResponse.put("message", "");
        serviceReasonAlternateResponse.put("results", 15);
        serviceReasonAlternateResponse.put("rows", serviceReasonAlternate);

        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", anyOf(equalTo(SERVICE_REASON_RESPONSE), equalTo(serviceReasonAlternateResponse)))
        .when()
            .get(SERVICE_REASON_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testServiceReasonReferenceSingleItemBody() {

       testSingleItemResponseBody(SERVICE_REASON_PATH, SERVICE_REASON_SA);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsServiceReasonReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, SERVICE_REASON_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testServiceReasonReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) SERVICE_REASON_SA.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(SERVICE_REASON_PATH, SERVICE_REASON_SCHOL.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testServiceReasonReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) SERVICE_REASON_MIN.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = SERVICE_REASON_ESL;

        referenceNegativeSupportedMethodTest(SERVICE_REASON_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
