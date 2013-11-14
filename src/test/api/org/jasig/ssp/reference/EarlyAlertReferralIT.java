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


public class EarlyAlertReferralIT extends AbstractReferenceTest {

    private static final String EARLY_ALERT_REFERRAL_PATH = REFERENCE_PATH + "earlyAlertReferral";

    private static final JSONObject EARLY_ALERT_REFERRAL_AC;
    private static final JSONObject EARLY_ALERT_REFERRAL_ATH;
    private static final JSONObject EARLY_ALERT_REFERRAL_CAP;
    private static final JSONObject EARLY_ALERT_REFERRAL_DC;
    private static final JSONObject EARLY_ALERT_REFERRAL_ECED;
    private static final JSONObject EARLY_ALERT_REFERRAL_ESL;
    private static final JSONObject EARLY_ALERT_REFERRAL_FAO;
    private static final JSONObject EARLY_ALERT_REFERRAL_IC;
    private static final JSONObject EARLY_ALERT_REFERRAL_REG;
    private static final JSONObject EARLY_ALERT_REFERRAL_MC;

    private static final JSONArray EARLY_ALERT_REFERRAL_ROWS;
    private static final JSONObject EARLY_ALERT_REFERRAL_RESPONSE;

    static {

        EARLY_ALERT_REFERRAL_AC = new JSONObject();
        EARLY_ALERT_REFERRAL_AC.put("id", "b2d112a9-5056-a51a-8010-b510525ea3a8");
        EARLY_ALERT_REFERRAL_AC.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_AC.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_AC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_AC.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_AC.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_AC.put("name", "Academic Counselors");
        EARLY_ALERT_REFERRAL_AC.put("description", "");
        EARLY_ALERT_REFERRAL_AC.put("sortOrder", 1);
        EARLY_ALERT_REFERRAL_AC.put("acronym", "AC");

        EARLY_ALERT_REFERRAL_ATH = new JSONObject();
        EARLY_ALERT_REFERRAL_ATH.put("id", "b2d112b8-5056-a51a-8067-1fda2849c3e5");
        EARLY_ALERT_REFERRAL_ATH.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_ATH.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_ATH.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_ATH.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_ATH.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_ATH.put("name", "Athletic Advisor");
        EARLY_ALERT_REFERRAL_ATH.put("description", "");
        EARLY_ALERT_REFERRAL_ATH.put("sortOrder", 2);
        EARLY_ALERT_REFERRAL_ATH.put("acronym", "ATH");


        EARLY_ALERT_REFERRAL_CAP = new JSONObject();
        EARLY_ALERT_REFERRAL_CAP.put("id", "b2d11335-5056-a51a-80ea-074f8fef94ea");
        EARLY_ALERT_REFERRAL_CAP.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_CAP.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_CAP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_CAP.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_CAP.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_CAP.put("name", "Counseling Action Plan");
        EARLY_ALERT_REFERRAL_CAP.put("description", "");
        EARLY_ALERT_REFERRAL_CAP.put("sortOrder", 8);
        EARLY_ALERT_REFERRAL_CAP.put("acronym", "CAP");


        EARLY_ALERT_REFERRAL_DC = new JSONObject();
        EARLY_ALERT_REFERRAL_DC.put("id", "b2d112c8-5056-a51a-80d5-beec7d48cb5d");
        EARLY_ALERT_REFERRAL_DC.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_DC.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_DC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_DC.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_DC.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_DC.put("name", "Developmental Studies Counselors");
        EARLY_ALERT_REFERRAL_DC.put("description", "");
        EARLY_ALERT_REFERRAL_DC.put("sortOrder", 3);
        EARLY_ALERT_REFERRAL_DC.put("acronym", "DC");


        EARLY_ALERT_REFERRAL_ECED = new JSONObject();
        EARLY_ALERT_REFERRAL_ECED.put("id", "b2d112d7-5056-a51a-80aa-795e56155af5");
        EARLY_ALERT_REFERRAL_ECED.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_ECED.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_ECED.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_ECED.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_ECED.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_ECED.put("name", "Early Childhood Education Center");
        EARLY_ALERT_REFERRAL_ECED.put("description", "");
        EARLY_ALERT_REFERRAL_ECED.put("sortOrder", 4);
        EARLY_ALERT_REFERRAL_ECED.put("acronym", "ECED");


        EARLY_ALERT_REFERRAL_ESL = new JSONObject();
        EARLY_ALERT_REFERRAL_ESL.put("id", "b2d112e7-5056-a51a-80e8-a30645c463e4");
        EARLY_ALERT_REFERRAL_ESL.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_ESL.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_ESL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_ESL.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_ESL.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_ESL.put("name", "English as Second Language");
        EARLY_ALERT_REFERRAL_ESL.put("description", "");
        EARLY_ALERT_REFERRAL_ESL.put("sortOrder", 5);
        EARLY_ALERT_REFERRAL_ESL.put("acronym", "ESL");


        EARLY_ALERT_REFERRAL_FAO = new JSONObject();
        EARLY_ALERT_REFERRAL_FAO.put("id", "b2d11316-5056-a51a-80f9-79421bdf08bf");
        EARLY_ALERT_REFERRAL_FAO.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_FAO.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_FAO.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_FAO.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_FAO.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_FAO.put("name", "Financial Aid Office");
        EARLY_ALERT_REFERRAL_FAO.put("description", "");
        EARLY_ALERT_REFERRAL_FAO.put("sortOrder", 6);
        EARLY_ALERT_REFERRAL_FAO.put("acronym", "FAO");


        EARLY_ALERT_REFERRAL_IC = new JSONObject();
        EARLY_ALERT_REFERRAL_IC.put("id", "b2d11326-5056-a51a-806c-79f352d0c2b2");
        EARLY_ALERT_REFERRAL_IC.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_IC.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_IC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_IC.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_IC.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_IC.put("name", "Instructor for Course");
        EARLY_ALERT_REFERRAL_IC.put("description", "");
        EARLY_ALERT_REFERRAL_IC.put("sortOrder", 7);
        EARLY_ALERT_REFERRAL_IC.put("acronym", "IC");


        EARLY_ALERT_REFERRAL_REG = new JSONObject();
        EARLY_ALERT_REFERRAL_REG.put("id", "300d68ef-38c2-4b7d-ad46-7874aa5d34ac");
        EARLY_ALERT_REFERRAL_REG.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_REG.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_REG.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_REG.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_REG.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_REG.put("name", "Registration");
        EARLY_ALERT_REFERRAL_REG.put("description", "");
        EARLY_ALERT_REFERRAL_REG.put("sortOrder", 10);
        EARLY_ALERT_REFERRAL_REG.put("acronym", "REG");


        EARLY_ALERT_REFERRAL_MC = new JSONObject();
        EARLY_ALERT_REFERRAL_MC.put("id", "1f5729af-0337-4e58-a001-8a9f80dbf8aa");
        EARLY_ALERT_REFERRAL_MC.put("createdDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_MC.put("createdBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_MC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        EARLY_ALERT_REFERRAL_MC.put("modifiedBy", getDefaultCreatedModifiedBy());
        EARLY_ALERT_REFERRAL_MC.put("objectStatus", "ACTIVE");
        EARLY_ALERT_REFERRAL_MC.put("name", "The Tutoring and Learning Center");
        EARLY_ALERT_REFERRAL_MC.put("description", "");
        EARLY_ALERT_REFERRAL_MC.put("sortOrder", 9);
        EARLY_ALERT_REFERRAL_MC.put("acronym", "MC");


        EARLY_ALERT_REFERRAL_ROWS = new JSONArray();
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_AC);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_ATH);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_CAP);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_DC);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_ECED);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_ESL);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_FAO);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_IC);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_REG);
        EARLY_ALERT_REFERRAL_ROWS.add(EARLY_ALERT_REFERRAL_MC);


        EARLY_ALERT_REFERRAL_RESPONSE = new JSONObject();
        EARLY_ALERT_REFERRAL_RESPONSE.put("success", "true");
        EARLY_ALERT_REFERRAL_RESPONSE.put("message", "");
        EARLY_ALERT_REFERRAL_RESPONSE.put("results", EARLY_ALERT_REFERRAL_ROWS.size());
        EARLY_ALERT_REFERRAL_RESPONSE.put("rows", EARLY_ALERT_REFERRAL_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsEarlyAlertReferralReference() {
        final JSONObject testPostPutNegative = (JSONObject)EARLY_ALERT_REFERRAL_AC.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(EARLY_ALERT_REFERRAL_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertReferralReferenceAllBody() {

        testResponseBody(EARLY_ALERT_REFERRAL_PATH, EARLY_ALERT_REFERRAL_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertReferralReferenceSingleItemBody() {

        testSingleItemResponseBody(EARLY_ALERT_REFERRAL_PATH, EARLY_ALERT_REFERRAL_ATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsEarlyAlertReferralReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, EARLY_ALERT_REFERRAL_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertReferralReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) EARLY_ALERT_REFERRAL_CAP.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(EARLY_ALERT_REFERRAL_PATH, EARLY_ALERT_REFERRAL_DC.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testEarlyAlertReferralReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) EARLY_ALERT_REFERRAL_REG.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = EARLY_ALERT_REFERRAL_MC;

        referenceNegativeSupportedMethodTest(EARLY_ALERT_REFERRAL_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}

