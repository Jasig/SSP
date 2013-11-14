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
import static org.junit.Assert.fail;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang.StringUtils;
import org.jasig.ssp.AbstractBaseIntegrationTest;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;
import java.util.Random;


public class AbstractReferenceTest extends AbstractBaseIntegrationTest {

    protected static final String REFERENCE_PATH = "reference/";
    protected static final String[] REFERENCE_SUPPORTED_METHODS = { "GET", "POST", "PUT", "DELETE" };

    protected static int testPassDeConflictNumber = new Random().nextInt(10); //allows names/codes to not conflict

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractReferenceTest.class);


    /**
     * Tests supported HTTP Get methods using a positive test. Should be authenticated before running.
     * Note: contentToPostPut should have a UUID named "id" typical to SSP db convention
     *     Do not expect passed objects to have the same dates and created/modified fields after this test.
     * @param urlToTest
     * @param individualUUIDToTest
     * @param contentToPostPut
     */
    public final void referencePositiveSupportedMethodTest( final String urlToTest, final String individualUUIDToTest,
                                                           final JSONObject contentToPostPut ) {

        int checkResultCount = -2;

        //get all (store results to check at the end)
        Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(urlToTest);

        String result = checkItemCount.getBody().jsonPath().getJsonObject("results").toString();

        if ( StringUtils.isNotBlank(result) ) {
            checkResultCount = Integer.parseInt(result);
        } else {
            LOGGER.error("Get all method failed at beginning of Positive Test! No results returned.");
            fail("GET all failed.");
        }

        //get /id
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(urlToTest + "/" + individualUUIDToTest);

        contentToPostPut.remove("id");

        //post
        Response postResponse = expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(contentToPostPut)
        .when()
            .post(urlToTest);

        final String postContentUUID = postResponse.getBody().jsonPath().getJsonObject("id").toString();

        //get more complete data from post using get (MSSQL version is not reliable without this extra get)
        postResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(urlToTest + "/" + postContentUUID);

        final Map parsedPostResponse = postResponse.getBody().jsonPath().getJsonObject("");

        contentToPostPut.put("id", postContentUUID);
        contentToPostPut.put("createdBy", getCurrentLoginCreatedModifiedBy());
        contentToPostPut.put("modifiedBy", getCurrentLoginCreatedModifiedBy());
        contentToPostPut.put("createdDate", parsedPostResponse.get("createdDate"));
        contentToPostPut.put("modifiedDate", parsedPostResponse.get("modifiedDate"));

        //verify post worked
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(contentToPostPut))
        .when()
            .get(urlToTest + "/" + postContentUUID);

        contentToPostPut.remove("id");
        contentToPostPut.put("name", ("testReferencePut" + testPassDeConflictNumber));

        //put
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(contentToPostPut)
        .when()
            .put(urlToTest + "/" + postContentUUID);

        //get more complete data from put using get
        final Response putResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(urlToTest + "/" + postContentUUID);

        contentToPostPut.put("id", postContentUUID);
        contentToPostPut.put("modifiedDate", putResponse.getBody().jsonPath().getJsonObject("modifiedDate"));

        //verify put worked
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(contentToPostPut))
        .when()
            .get(urlToTest + "/" + postContentUUID);

        //delete
        expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .delete(urlToTest + "/" + postContentUUID);

        contentToPostPut.put("objectStatus", "INACTIVE");

        //get verify delete worked
        final Response deleteCheckResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(urlToTest + "/" + postContentUUID);

        contentToPostPut.put("modifiedDate", deleteCheckResponse.getBody().jsonPath().getJsonObject("modifiedDate"));

        //verify delete is still intact but inactive
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(contentToPostPut))
        .when()
            .get(urlToTest + "/" + postContentUUID);

        //get (verify result # matches expected active)
       expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo( checkResultCount ))
       .given()
           .queryParam("status", "ACTIVE")
       .when()
            .get(urlToTest);

        //get (verify result # matches expected inactive)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(1))
        .given()
            .queryParam("status", "INACTIVE")
        .when()
            .get(urlToTest); 
   }


    /**
     * Tests supported HTTP methods with negative tests. Should be authenticated before running.
     * Note: invalid content can be valid and this method will make changes to make it invalid
     *     Do not expect passed objects to be valid after this test.
     * @param urlToTest
     * @param invalidContentToPostPut
     * @param validContentToVerify
     */
    public final void referenceNegativeSupportedMethodTest( final String urlToTest,
                                                            final JSONObject invalidContentToPostPut,
                                                            final JSONObject validContentToVerify ) {

        int checkResultCount = 0;

        //get all (store results to check at the end)
        Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(urlToTest);

        String result = checkItemCount.getBody().jsonPath().getJsonObject("results").toString();

        if ( StringUtils.isNotBlank(result) ) {
            checkResultCount = Integer.parseInt(result);
        } else {
            LOGGER.error("Get all method failed in Negative Test! No results returned.");
            fail("GET all failed Negative Tests.");
        }

        //get invalid id
        expect()
            .statusCode(404)
            .contentType("application/json")
        .when()
            .get(urlToTest + "/70b982b0-68d7-11e3-949a-0800200c9a66");

        invalidContentToPostPut.remove("id");
        final String name = invalidContentToPostPut.get("name").toString();
        invalidContentToPostPut.remove("name");

        //post empty name
        expect()
            .statusCode(400)
        .given()
            .contentType("application/json")
            .body(invalidContentToPostPut)
        .when()
            .post(urlToTest);

        invalidContentToPostPut.put("name", name);

        if ( invalidContentToPostPut.containsKey("code") ) {
            invalidContentToPostPut.remove("code");

            //post empty code
            expect()
                .statusCode(500)
            .given()
                .contentType("application/json")
                .body(invalidContentToPostPut)
            .when()
                .post(urlToTest);
        }

        invalidContentToPostPut.put("objectStatus", "");

        //put
        expect()
            .statusCode(500)
        .given()
            .contentType("application/json")
            .body(invalidContentToPostPut)
        .when()
            .put(urlToTest + "/" + validContentToVerify.get("id"));

        //verify put didn't work
        expect()
            .statusCode(200)
            .contentType("application/json")
            .body("", equalTo(validContentToVerify))
        .when()
            .get(urlToTest + "/" + validContentToVerify.get("id"));

        //delete
        expect()
            .statusCode(404)
        .when()
            .delete(urlToTest + "/70b982b0-68d7-11e3-949a-0800200c9a66");


        //get all (verify result # is unchanged)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
            .get(urlToTest);

    }


    /**
     * Tests permission protected method(s) unauthenticated for a negative test.
     * Note: This method cannot take any validation measures as it is designed to be unauthenticated.
     *        Other validation measures (e.g. response body validation etc.) should be taken after this method.
     *           Do not expect passed objects to be the same after this test.
     * @param urlToTest
     * @param contentToPostPut
     */
    public final void referenceAuthenticationControlledMethodNegativeTest( final String urlToTest,
                                                                       final JSONObject contentToPostPut ) {

        final String idToSave = contentToPostPut.get("id").toString();
        contentToPostPut.remove("id");

        //tests permission on get all method
        expect()
            .statusCode(403)
        .when()
            .get(urlToTest);

        //tests permission on get id method
        expect()
            .statusCode(403)
        .when()
            .get(urlToTest + "/" + idToSave);

        //tests permission on post method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(contentToPostPut)
        .when()
            .post(urlToTest);

        contentToPostPut.put("name", ("testReferencePutUnAuth" + testPassDeConflictNumber));

        //tests permission on put method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(contentToPostPut)
        .when()
            .put(urlToTest + "/" + idToSave);

        //tests permission on delete method
        expect()
            .statusCode(403)
        .when()
            .delete(urlToTest + "/" + idToSave);

    }
}
