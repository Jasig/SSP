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

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matchers;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.jasig.ssp.ExpectationUtils.copyOfExpectedObjectAtIndex;
import static org.jasig.ssp.ExpectationUtils.expectListResponseObjectAtIndex;
import static org.jasig.ssp.ExpectationUtils.expectedObjectAtIndex;
import static org.junit.Assert.fail;


public class BlurbIT extends AbstractReferenceTest {

    private static final String BLURB_PATH = "blurb";

    private static final JSONArray BLURB_ROWS;

    static {

        BLURB_ROWS = new JSONArray();

        JSONObject expectedResult1 = new JSONObject();
        expectedResult1.put("id", "1a92f3e6-9364-4e6f-8388-aee95a3af55d");
        expectedResult1.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult1.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult1.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult1.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult1.put("objectStatus", "ACTIVE");
        expectedResult1.put("name", "Intake Tab 1 Label");
        expectedResult1.put("description", "Person tab label");
        expectedResult1.put("code",  "intake.tab1.label");
        expectedResult1.put("value", "Person");

        BLURB_ROWS.add(expectedResult1);

        JSONObject expectedResult2 = new JSONObject();
        expectedResult2.put("id", "343eede9-b777-4d63-84f7-c65c71eee052");
        expectedResult2.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult2.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult2.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult2.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult2.put("objectStatus", "ACTIVE");
        expectedResult2.put("name", "Intake Tab 2 Label");
        expectedResult2.put("description", "Demographics tab label");
        expectedResult2.put("code",  "intake.tab2.label");
        expectedResult2.put("value", "Demographics");

        BLURB_ROWS.add(expectedResult2);

        JSONObject expectedResult3 = new JSONObject();
        expectedResult3.put("id", "eb189389-3112-4214-85c2-c406c773d245");
        expectedResult3.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult3.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult3.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult3.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult3.put("objectStatus", "ACTIVE");
        expectedResult3.put("name", "Intake Childcare Arrangements Label");
        expectedResult3.put("description", "");
        expectedResult3.put("code", "intake.tab2.label.childcare-arrangements");
        expectedResult3.put("value", "If yes, what are your childcare arrangements?");

        BLURB_ROWS.add(expectedResult3);

        JSONObject expectedResult4 = new JSONObject();
        expectedResult4.put("id", "5187c5cd-5c20-44c1-920e-00dea367b3b6");
        expectedResult4.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult4.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult4.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult4.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult4.put("objectStatus", "ACTIVE");
        expectedResult4.put("name", "Intake Childcare Needed Label");
        expectedResult4.put("description", "");
        expectedResult4.put("code", "intake.tab2.label.childcare-needed");
        expectedResult4.put("value", "Childcare Needed?");

        BLURB_ROWS.add(expectedResult4);

        JSONObject expectedResult5 = new JSONObject();
        expectedResult5.put("id", "9a24e721-b59b-4d40-8edd-c522ac29f37a");
        expectedResult5.put("createdDate", getDefaultCreatedModifiedByDate());
        expectedResult5.put("createdBy", getDefaultCreatedModifiedBy());
        expectedResult5.put("modifiedDate", getDefaultCreatedModifiedByDate());
        expectedResult5.put("modifiedBy", getDefaultCreatedModifiedBy());
        expectedResult5.put("objectStatus", "ACTIVE");
        expectedResult5.put("name", "Intake Children Ages Label");
        expectedResult5.put("description", "");
        expectedResult5.put("code", "intake.tab2.label.children-ages");
        expectedResult5.put("value", "Ages? Separate each age with a comma. (1,5,12)");

        BLURB_ROWS.add(expectedResult5);

    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsBlurbReference() {
        final JSONObject testPutNegative = copyOfExpectedObjectAtIndex(0, BLURB_ROWS);
        testPutNegative.put("name", "testReferencePutUnAuth");

        //tests permission on get all method
        expect()
            .statusCode(401)    //should this be a 401?
        .when()
            .get(BLURB_PATH);


        //tests permission on put method
        expect()
            .statusCode(401)   //should this be a 401?
        .given()
            .contentType("application/json")
            .body(testPutNegative)
        .when()
            .put(BLURB_PATH + "/" + testPutNegative.get("id"));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testBlurbReferenceAllBody() {

        ResponseSpecification spec =
                expect()
                        .contentType("application/json")
                        .statusCode(200)
                        .log().ifError()
                        .body("results", equalTo(76))
                        .and()
                        .body("success", equalTo("true"))
                        .and()
                        .body("rows", hasSize(76))
                        .and();

        spec = expectListResponseObjectAtIndex(spec, 0, BLURB_ROWS);
        spec.when().get(BLURB_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testBlurbReferenceSingleItemBody() {

       //Functionality not coded at this time
       // testSingleItemResponseBody(BLURB_PATH, (JSONObject) BLURB_ROWS.get(1));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsBlurbReference() {

        testUnsupportedMethods(new String[] {"GET", "PUT"}, BLURB_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testBlurbReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = expectedObjectAtIndex(2, BLURB_ROWS);
        testPostPutPositive.put("name", "testReferencePutPositive" + testPassDeConflictNumber);

        //put
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(testPostPutPositive)
        .when()
            .put(BLURB_PATH + "/" + testPostPutPositive.get("id"));

        //verify put worked
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("rows[2].name", equalTo(testPostPutPositive.get("name")))
        .when()
            .get(BLURB_PATH);

        //put data back to original state if everything above was successful
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body( expectedObjectAtIndex(2, BLURB_ROWS) )
        .when()
            .put(BLURB_PATH + "/" + testPostPutPositive.get("id"));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testBlurbReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePutObject = copyOfExpectedObjectAtIndex(3, BLURB_ROWS);
        testNegativePutObject.put("name", ("testPutNegative" + testPassDeConflictNumber));
        testNegativePutObject.put("objectStatus", "");
        final JSONObject testNegativeValidateObject = expectedObjectAtIndex(4, BLURB_ROWS);

        //put
        expect()
            .statusCode(400)
        .given()
            .contentType("application/json")
            .body(testNegativePutObject)
        .when()
            .put(BLURB_PATH + "/" + testNegativeValidateObject.get("id"));

        //verify put didn't work
        expect()
            .statusCode(200)
            .contentType("application/json")
            .body("results", equalTo(76))
        .when()
            .get(BLURB_PATH);
    }
}