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
import org.hamcrest.CoreMatchers;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.jasig.ssp.ExpectationUtils.expectListResponseObjectAtIndex;


public class ConfidentialityLevelIT extends AbstractReferenceTest {

    private static final String CONFIDENTIALITY_LEVEL_PATH = REFERENCE_PATH + "confidentialityLevel";
    private static final String OPTIONS = "/options";

    private static final JSONObject CONFIDENTIALITY_LEVEL_ARC;
    private static final JSONObject CONFIDENTIALITY_LEVEL_CNSL;
    private static final JSONObject CONFIDENTIALITY_LEVEL_DIS;
    private static final JSONObject CONFIDENTIALITY_LEVEL_DSW;
    private static final JSONObject CONFIDENTIALITY_LEVEL_EAL;
    private static final JSONObject CONFIDENTIALITY_LEVEL_ESL;
    private static final JSONObject CONFIDENTIALITY_LEVEL_EVRY;
    private static final JSONObject CONFIDENTIALITY_LEVEL_FFC;
    private static final JSONObject CONFIDENTIALITY_LEVEL_MGR;
    private static final JSONObject CONFIDENTIALITY_LEVEL_STAFF;
    private static final JSONObject CONFIDENTIALITY_LEVEL_SuCo;
    private static final JSONObject CONFIDENTIALITY_LEVEL_TST;

    private static final JSONArray CONFIDENTIALITY_LEVEL_ROWS;
    private static final JSONObject CONFIDENTIALITY_LEVEL_RESPONSE;

    private static final String[] CONFIDENTIALITY_LEVEL_OPTIONS;
    private static final JSONArray CONFIDENTIALITY_LEVEL_OPTION_ROWS;

    static {

        CONFIDENTIALITY_LEVEL_ARC = new JSONObject();
        CONFIDENTIALITY_LEVEL_ARC.put("id", "b2d077a7-4055-0510-7957-4a09f93a0346");
        CONFIDENTIALITY_LEVEL_ARC.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_ARC.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_ARC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_ARC.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_ARC.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_ARC.put("name", "Academic Resource Center");
        CONFIDENTIALITY_LEVEL_ARC.put("description", "");
        CONFIDENTIALITY_LEVEL_ARC.put("acronym", "ARC");
        CONFIDENTIALITY_LEVEL_ARC.put("permission", "DATA_ACADEMIC_RESOURCE_CENTER");

        CONFIDENTIALITY_LEVEL_CNSL = new JSONObject();
        CONFIDENTIALITY_LEVEL_CNSL.put("id", "b2d07935-5056-a51a-80db-caa0dadd3f2e");
        CONFIDENTIALITY_LEVEL_CNSL.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_CNSL.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_CNSL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_CNSL.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_CNSL.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_CNSL.put("name", "Counselor");
        CONFIDENTIALITY_LEVEL_CNSL.put("description", "");
        CONFIDENTIALITY_LEVEL_CNSL.put("acronym", "CNSL");
        CONFIDENTIALITY_LEVEL_CNSL.put("permission", "DATA_COUNSELING_SERVICES");

        CONFIDENTIALITY_LEVEL_DIS = new JSONObject();
        CONFIDENTIALITY_LEVEL_DIS.put("id", "b2d07915-5056-a51a-80ce-334eca0e0a10");
        CONFIDENTIALITY_LEVEL_DIS.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_DIS.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_DIS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_DIS.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_DIS.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_DIS.put("name", "Disability");
        CONFIDENTIALITY_LEVEL_DIS.put("description", "");
        CONFIDENTIALITY_LEVEL_DIS.put("acronym", "DIS");
        CONFIDENTIALITY_LEVEL_DIS.put("permission", "DATA_DISABILITY");

        CONFIDENTIALITY_LEVEL_DSW = new JSONObject();
        CONFIDENTIALITY_LEVEL_DSW.put("id", "b2d07964-5157-a51a-800a-a95d3bb3c4e3");
        CONFIDENTIALITY_LEVEL_DSW.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_DSW.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_DSW.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_DSW.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_DSW.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_DSW.put("name", "Displaced Workers");
        CONFIDENTIALITY_LEVEL_DSW.put("description", "");
        CONFIDENTIALITY_LEVEL_DSW.put("acronym", "DSW");
        CONFIDENTIALITY_LEVEL_DSW.put("permission", "DATA_DISPLACED_WORKERS");

        CONFIDENTIALITY_LEVEL_EAL = new JSONObject();
        CONFIDENTIALITY_LEVEL_EAL.put("id", "b2d07964-5157-a51a-800a-a95d3bb3c4f4");
        CONFIDENTIALITY_LEVEL_EAL.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_EAL.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_EAL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_EAL.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_EAL.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_EAL.put("name", "Early Alert ");
        CONFIDENTIALITY_LEVEL_EAL.put("description", "");
        CONFIDENTIALITY_LEVEL_EAL.put("acronym", "EAL");
        CONFIDENTIALITY_LEVEL_EAL.put("permission", "DATA_EARLY_ALERT");

        CONFIDENTIALITY_LEVEL_ESL = new JSONObject();
        CONFIDENTIALITY_LEVEL_ESL.put("id", "b2d07964-5056-a51a-800a-a95d3bb3c4d1");
        CONFIDENTIALITY_LEVEL_ESL.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_ESL.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_ESL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_ESL.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_ESL.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_ESL.put("name", "English as a Second Language");
        CONFIDENTIALITY_LEVEL_ESL.put("description", "");
        CONFIDENTIALITY_LEVEL_ESL.put("acronym", "ESL");
        CONFIDENTIALITY_LEVEL_ESL.put("permission", "DATA_ENGLISH_SECOND_LANGUAGE");

        CONFIDENTIALITY_LEVEL_EVRY = new JSONObject();
        CONFIDENTIALITY_LEVEL_EVRY.put("id", "b3d077a7-4055-0510-7967-4a09f93a0357");
        CONFIDENTIALITY_LEVEL_EVRY.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_EVRY.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_EVRY.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_EVRY.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_EVRY.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_EVRY.put("name", "Everyone");
        CONFIDENTIALITY_LEVEL_EVRY.put("description", "Not Confidential. Records may be viewed by all users.");
        CONFIDENTIALITY_LEVEL_EVRY.put("acronym", "EVERYONE");
        CONFIDENTIALITY_LEVEL_EVRY.put("permission", "DATA_EVERYONE");

        CONFIDENTIALITY_LEVEL_FFC = new JSONObject();
        CONFIDENTIALITY_LEVEL_FFC.put("id", "b2d07964-5157-a51a-800a-a95d3bb3c505");
        CONFIDENTIALITY_LEVEL_FFC.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_FFC.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_FFC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_FFC.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_FFC.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_FFC.put("name", "Fast Forward");
        CONFIDENTIALITY_LEVEL_FFC.put("description", "");
        CONFIDENTIALITY_LEVEL_FFC.put("acronym", "FFC");
        CONFIDENTIALITY_LEVEL_FFC.put("permission", "DATA_FAST_FORWARD");

        CONFIDENTIALITY_LEVEL_MGR = new JSONObject();
        CONFIDENTIALITY_LEVEL_MGR.put("id", "b2d07906-5056-a51a-80fd-9d19f636e501");
        CONFIDENTIALITY_LEVEL_MGR.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_MGR.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_MGR.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_MGR.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_MGR.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_MGR.put("name", "Manager");
        CONFIDENTIALITY_LEVEL_MGR.put("description", "");
        CONFIDENTIALITY_LEVEL_MGR.put("acronym", "MGR");
        CONFIDENTIALITY_LEVEL_MGR.put("permission", "DATA_MANAGER");

        CONFIDENTIALITY_LEVEL_STAFF = new JSONObject();
        CONFIDENTIALITY_LEVEL_STAFF.put("id", "b2d078b8-5056-a51a-8057-4a09f93a0347");
        CONFIDENTIALITY_LEVEL_STAFF.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_STAFF.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_STAFF.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_STAFF.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_STAFF.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_STAFF.put("name", "Staff");
        CONFIDENTIALITY_LEVEL_STAFF.put("description", "");
        CONFIDENTIALITY_LEVEL_STAFF.put("acronym", "STAFF");
        CONFIDENTIALITY_LEVEL_STAFF.put("permission", "DATA_STAFF");

        CONFIDENTIALITY_LEVEL_SuCo = new JSONObject();
        CONFIDENTIALITY_LEVEL_SuCo.put("id", "b2d07944-5056-a51a-8047-654512d4d083");
        CONFIDENTIALITY_LEVEL_SuCo.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_SuCo.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_SuCo.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_SuCo.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_SuCo.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_SuCo.put("name", "Success Coach");
        CONFIDENTIALITY_LEVEL_SuCo.put("description", "");
        CONFIDENTIALITY_LEVEL_SuCo.put("acronym", "SuCo");
        CONFIDENTIALITY_LEVEL_SuCo.put("permission", "DATA_INDIVIDUALIZED_LEARNING_PLAN");

        CONFIDENTIALITY_LEVEL_TST = new JSONObject();
        CONFIDENTIALITY_LEVEL_TST.put("id", "2eb131de-1e27-11e2-a5bc-406c8f22c3ce");
        CONFIDENTIALITY_LEVEL_TST.put("createdDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_TST.put("createdBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_TST.put("modifiedDate", getDefaultCreatedModifiedByDate());
        CONFIDENTIALITY_LEVEL_TST.put("modifiedBy", getDefaultCreatedModifiedBy());
        CONFIDENTIALITY_LEVEL_TST.put("objectStatus", "ACTIVE");
        CONFIDENTIALITY_LEVEL_TST.put("name", "Test");
        CONFIDENTIALITY_LEVEL_TST.put("description", null);
        CONFIDENTIALITY_LEVEL_TST.put("acronym", "TST");
        CONFIDENTIALITY_LEVEL_TST.put("permission", "DATA_TEST");


        CONFIDENTIALITY_LEVEL_ROWS = new JSONArray();
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_ARC);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_CNSL);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_DIS);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_DSW);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_EAL);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_ESL);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_EVRY);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_FFC);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_MGR);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_STAFF);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_SuCo);
        CONFIDENTIALITY_LEVEL_ROWS.add(CONFIDENTIALITY_LEVEL_TST);


        CONFIDENTIALITY_LEVEL_RESPONSE = new JSONObject();
        CONFIDENTIALITY_LEVEL_RESPONSE.put("success", "true");
        CONFIDENTIALITY_LEVEL_RESPONSE.put("message", "");
        CONFIDENTIALITY_LEVEL_RESPONSE.put("results", CONFIDENTIALITY_LEVEL_ROWS.size());
        CONFIDENTIALITY_LEVEL_RESPONSE.put("rows", CONFIDENTIALITY_LEVEL_ROWS);


        CONFIDENTIALITY_LEVEL_OPTIONS = new String[] {
                "DATA_CL_01", "DATA_CL_02", "DATA_CL_03", "DATA_CL_04", "DATA_CL_05", "DATA_CL_06", "DATA_CL_07",
                "DATA_CL_08", "DATA_CL_09", "DATA_CL_10", "DATA_CL_11", "DATA_CL_12", "DATA_CL_13", "DATA_CL_14",
                "DATA_CL_15", "DATA_CL_16", "DATA_CL_17", "DATA_CL_18", "DATA_CL_19", "DATA_CL_20", "UNKNOWN"
        };

        int idIndex = 9;
        CONFIDENTIALITY_LEVEL_OPTION_ROWS = new JSONArray();

        for ( String confLevel : CONFIDENTIALITY_LEVEL_OPTIONS ) {
            JSONObject temp = new JSONObject();
            temp.put("name", confLevel);

            if ( idIndex != 29 ) {
                temp.put("id", idIndex);
                idIndex++;
            } else {
                temp.put("id", 32);
            }

            CONFIDENTIALITY_LEVEL_OPTION_ROWS.add(temp);
        }

    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsConfidentialityLevelReference() {
        final JSONObject testPostPutNegative = (JSONObject) CONFIDENTIALITY_LEVEL_ARC.clone();
        testPostPutNegative.put("name", "testPostUnAuth");
        testPostPutNegative.put("permission", CONFIDENTIALITY_LEVEL_OPTIONS[00]);

        referenceAuthenticationControlledMethodNegativeTest(CONFIDENTIALITY_LEVEL_PATH, testPostPutNegative);

        //tests permissions on get options method
        expect()
            .statusCode(403)
        .when()
            .get(CONFIDENTIALITY_LEVEL_PATH + OPTIONS);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityLevelReferenceAllBody() {

        testResponseBody(CONFIDENTIALITY_LEVEL_PATH, CONFIDENTIALITY_LEVEL_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityLevelOptionReferenceAllBody() {

        // At least one of the other tests POSTs a permission to a CL, which
        // has the side-effect of de-listing that permission from the list
        // of unbound permissions, which is what this "options" API is all
        // about. So we use GTE assertion on the spec below, else unpredictable
        // test execution ordering will make this test unstable. Was just
        // simpler at the timethan figuring out either how to make that POST
        // test follow-up with a DELETE or maintaining a class-scoped context
        // for adjusting test expectations based on accumulated changes to persistent data.

        ResponseSpecification spec =
                expect()
                        .contentType("application/json")
                        .statusCode(200)
                        .log().ifError()
                        .body("results", greaterThanOrEqualTo(20))
                        .and()
                        .body("success", CoreMatchers.equalTo("true"))
                        .and()
                        .body("rows", hasSize(greaterThanOrEqualTo(20)))
                        .and();

        spec = expectListResponseObjectAtIndex(spec, 0, CONFIDENTIALITY_LEVEL_OPTION_ROWS);
        spec.when().get(CONFIDENTIALITY_LEVEL_PATH + OPTIONS);

    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityLevelReferenceSingleItemBody() {

        testSingleItemResponseBody(CONFIDENTIALITY_LEVEL_PATH, CONFIDENTIALITY_LEVEL_CNSL);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsConfidentialityLevelReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CONFIDENTIALITY_LEVEL_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsConfidentialityLevelOptionsReference() {

        // test /options
        expect()
            .statusCode(404)
        .when()
            .put(CONFIDENTIALITY_LEVEL_PATH + OPTIONS + "/" + CONFIDENTIALITY_LEVEL_ARC.get("id"));


        expect()
            .statusCode(405)
        .when()
            .post(CONFIDENTIALITY_LEVEL_PATH + OPTIONS);


        expect()
            .statusCode(404)
        .when()
            .delete(CONFIDENTIALITY_LEVEL_PATH + OPTIONS + "/" + CONFIDENTIALITY_LEVEL_ARC.get("id"));


        expect()
            .statusCode(405)
        .when()
            .head(CONFIDENTIALITY_LEVEL_PATH + OPTIONS);


        expect()
            .statusCode(200)
        .when()
            .options(CONFIDENTIALITY_LEVEL_PATH + OPTIONS);

    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityLevelReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) CONFIDENTIALITY_LEVEL_DIS.clone();
        testPostPutPositive.put("name", "testPostPositive");
        // Intentionally use the "last" data permission here in a lame attempt
        // to avoid disrupting a subsequent list operation on all unbound
        // data perms, which will typically assert on the first element in the
        // resulting list.
        testPostPutPositive.put("permission", CONFIDENTIALITY_LEVEL_OPTIONS[20]);

        referencePositiveSupportedMethodTest(CONFIDENTIALITY_LEVEL_PATH, CONFIDENTIALITY_LEVEL_DSW.get("id").toString(),
                testPostPutPositive);

        //get /options check options count, since an option should have been assigned during post in method call above
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo( CONFIDENTIALITY_LEVEL_OPTION_ROWS.size()-1 ))
        .when()
            .get(CONFIDENTIALITY_LEVEL_PATH + OPTIONS);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfidentialityLevelReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) CONFIDENTIALITY_LEVEL_EAL.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = CONFIDENTIALITY_LEVEL_ESL;

        referenceNegativeSupportedMethodTest(CONFIDENTIALITY_LEVEL_PATH, testNegativePostObject, testNegativeValidateObject);

        //get /options
        expect()
            .statusCode(404)
            .contentType("application/json")
        .when()
            .get(CONFIDENTIALITY_LEVEL_PATH + OPTIONS + "/70b982b0-68d7-11e3-949a-0800200c9a66");
    }

}