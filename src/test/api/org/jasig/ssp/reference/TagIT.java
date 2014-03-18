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


import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasSize;
import static org.jasig.ssp.ExpectationUtils.expectListResponseObjectAtIndex;
import static org.junit.Assert.fail;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.ResponseSpecification;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import java.util.List;


public class TagIT extends AbstractReferenceTest {

    private static final String TAG_PATH = REFERENCE_PATH + "tag";
    private static final JSONObject TAG_DISTANCE_LEARNING;
    private static final JSONObject TAG_HONORS;
    private static final JSONObject TAG_TRANSFER_GUARANTEE;
    private static final JSONArray TAG_ROWS;
    private static final JSONObject TAG_RESPONSE;

    static {

        TAG_DISTANCE_LEARNING = new JSONObject();
        TAG_DISTANCE_LEARNING.put("id", "deac8cef-f87a-441e-ae64-495f1a78806b");
        TAG_DISTANCE_LEARNING.put("createdDate", getDefaultCreatedModifiedByDate());
        TAG_DISTANCE_LEARNING.put("createdBy", getDefaultCreatedModifiedBy());
        TAG_DISTANCE_LEARNING.put("modifiedDate", getDefaultCreatedModifiedByDate());
        TAG_DISTANCE_LEARNING.put("modifiedBy", getDefaultCreatedModifiedBy());
        TAG_DISTANCE_LEARNING.put("objectStatus", "ACTIVE");
        TAG_DISTANCE_LEARNING.put("name", "Distance Learning");
        TAG_DISTANCE_LEARNING.put("description", "Distance Learning Course");
        TAG_DISTANCE_LEARNING.put("code", "DL");

        TAG_HONORS = new JSONObject();
        TAG_HONORS.put("id", "4c38893f-cdd6-4b18-a685-fd3cf8e64cbc");
        TAG_HONORS.put("createdDate", getDefaultCreatedModifiedByDate());
        TAG_HONORS.put("createdBy", getDefaultCreatedModifiedBy());
        TAG_HONORS.put("modifiedDate", getDefaultCreatedModifiedByDate());
        TAG_HONORS.put("modifiedBy", getDefaultCreatedModifiedBy());
        TAG_HONORS.put("objectStatus", "ACTIVE");
        TAG_HONORS.put("name", "Honors");
        TAG_HONORS.put("description", "Honors Course");
        TAG_HONORS.put("code", "HON");

        TAG_TRANSFER_GUARANTEE = new JSONObject();
        TAG_TRANSFER_GUARANTEE.put("id", "2f87ea59-70b5-43f2-8387-664173806298");
        TAG_TRANSFER_GUARANTEE.put("createdDate", getDefaultCreatedModifiedByDate());
        TAG_TRANSFER_GUARANTEE.put("createdBy", getDefaultCreatedModifiedBy());
        TAG_TRANSFER_GUARANTEE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        TAG_TRANSFER_GUARANTEE.put("modifiedBy", getDefaultCreatedModifiedBy());
        TAG_TRANSFER_GUARANTEE.put("objectStatus", "ACTIVE");
        TAG_TRANSFER_GUARANTEE.put("name", "Transfer Guarantee");
        TAG_TRANSFER_GUARANTEE.put("description", "Transfer Assurance Guarantee for the state");
        TAG_TRANSFER_GUARANTEE.put("code", "TAG");

        TAG_ROWS = new JSONArray();
        TAG_ROWS.add(TAG_DISTANCE_LEARNING);
        TAG_ROWS.add(TAG_HONORS);
        TAG_ROWS.add(TAG_TRANSFER_GUARANTEE);

        TAG_RESPONSE = new JSONObject();
        TAG_RESPONSE.put("success", "true");
        TAG_RESPONSE.put("message", "");
        TAG_RESPONSE.put("results", TAG_ROWS.size());
        TAG_RESPONSE.put("rows", TAG_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsTagReference() {
        final JSONObject testPostPutNegative = (JSONObject)TAG_DISTANCE_LEARNING.clone();
        testPostPutNegative.put("code", ("DL" + testPassDeConflictNumber));
        testPostPutNegative.put("name", "testPostUnAuth");

        expect()
            .statusCode(403)
        .given()
            .queryParam("termCode", "WN2015")
        .when()
            .get(TAG_PATH + "/facet");

        referenceAuthenticationControlledMethodNegativeTest(TAG_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testTagReferenceAllBody() {

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

        spec = expectListResponseObjectAtIndex(spec, 0, TAG_ROWS);
        spec.when().get(TAG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testTagReferenceSingleItemBody() {

        testSingleItemResponseBody(TAG_PATH, TAG_TRANSFER_GUARANTEE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testTagReferenceGetFacet() {

        final JSONArray testFacet = new JSONArray();
        testFacet.add(TAG_HONORS);

        expect()
            .statusCode(200)
            .contentType("application/json")
            .log().ifError()
            .body("", equalTo(testFacet))
        .given()
            .queryParam("programCode", "AES-AS")
            .queryParam("termCode", "FA2016")
        .when()
            .get(TAG_PATH + "/facet");

        expect()
            .statusCode(200)
            .contentType("application/json")
            .log().ifError()
            .body("", equalTo(testFacet))
        .given()
            .queryParam("termCode", "WN2015")
        .when()
            .get(TAG_PATH + "/facet");

        testFacet.add(TAG_DISTANCE_LEARNING);

        final Response facetResponse = expect()
            .statusCode(200)
            .contentType("application/json")
            .log().ifError()
        .given()
            .queryParam("programCode", "AES-AS")
        .when()
            .get(TAG_PATH + "/facet");

        final List facetResponseList = facetResponse.path("");

        if ( !facetResponseList.contains(TAG_DISTANCE_LEARNING) || !facetResponseList.contains(TAG_HONORS) ) {
            fail("Facet response for program code doesn't match expected!");
        }
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsTagReference() {
        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, TAG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testTagReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject)TAG_HONORS.clone();
        testPostPutPositive.put("code", ("HON" + testPassDeConflictNumber));
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(TAG_PATH, TAG_DISTANCE_LEARNING.get("id").toString(), testPostPutPositive);

        //Test Get Method that returns all results
        expect()
            .statusCode(200)
            .contentType("application/json")
            .log().ifError()
            .body("results", equalTo(4))
        .when()
            .get(TAG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testTagReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject)TAG_TRANSFER_GUARANTEE.clone();
        testNegativePostObject.put("code", ("TAG" + testPassDeConflictNumber));
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = TAG_DISTANCE_LEARNING;

        expect()
            .statusCode(200)
            .contentType("application/json")
            .log().ifError()
            .body("", equalTo(new JSONArray()))
        .given()
            .queryParam("termCode", "X")
        .when()
            .get(TAG_PATH + "/facet");

        referenceNegativeSupportedMethodTest(TAG_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}

