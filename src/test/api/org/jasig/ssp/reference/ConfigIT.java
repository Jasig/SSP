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
import org.hamcrest.Matchers;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;


public class ConfigIT extends AbstractReferenceTest {

    private static final String CONFIG_PATH = REFERENCE_PATH + "config";

    private static final String[] CONFIG_UUIDS;
    private static final String[] CONFIG_NAMES;
    private static final String[] CONFIG_DESCRIPTIONS;
    private static final String[] CONFIG_VALUES;
    private static final String[] CONFIG_DEFAULT_VALUES;
    private static final int[] CONFIG_SORT_ORDERS;
    private static final JSONArray CONFIG_ROWS;
    private static final JSONObject CONFIG_RESPONSE;

    private static final Logger LOGGER = LoggerFactory.getLogger(ConfigIT.class);

    static {

        CONFIG_UUIDS = new String[] {
                "67bd120e-9be1-11e1-ad1f-0026b9e7ff4c", "59dbcf48-9be3-11e1-bded-0026b9e7ff4c",
                "0ed5d4e3-77cb-11e3-a151-406c8f22c3ce", "1b5528d2-d789-11e1-9d78-0026b9e7ff4c",
                "d1442778-f3a0-11e1-a285-406c8f22c3ce", "312c069e-ee71-11e2-80f4-406c8f22c3ce",
                "8298fc28-9be1-11e1-933d-0026b9e7ff4c", "e99ff3c7-6a59-4812-b3b1-285fa8764d3a",
                "333c4e98-ccef-11e1-8ff5-0026b9e7ff4c", "645d19ea-9be3-11e1-8ffe-0026b9e7ff4c",
                "5e42ccb0-876c-11e2-9e96-0800200c9a66", "3eb8d2ee-9baa-11e1-844c-0026b9e7ff4c",
                "e6c63bb0-9be2-11e1-a6cb-0026b9e7ff4c", "cdfafe17-5aa5-11e2-82f7-406c8f22c3ce",
                "33f3c840-d903-11e2-a28f-0800200c9a66", "5d62d170-d904-11e2-a28f-0800200c9a66",
                "65f23fe8-e829-11e2-b231-406c8f22c3ce", "d22f5bf3-e829-11e2-97e9-406c8f22c3ce",
                "21a321e8-e03a-11e2-bb74-406c8f22c3ce", "161b5557-e03b-11e2-b770-406c8f22c3ce",
                "80e51b59-3936-436a-93e2-9a116c1fd03c", "43bd3066-a5a8-11e1-8e16-0026b9e7ff4c",
                "9e454391-666c-11e2-84d4-406c8f22c3ce", "bc5f37e0-880f-11e2-9e96-0800200c9a66"
        };

        CONFIG_NAMES = new String[] {
                "app_title", "bcc_email_address", "client_timeout", "coachSetFromExternalData",
                "coachUnsetFromExternalData", "highly_trusted_ips_enabled", "inst_home_url", "inst_name",
                "manage_integration_database", "numberOfDaysPriorForTaskReminder", "registration_load_ranges",
                "send_mail", "serverExternalPath", "status_code_mappings", "studentTypeSetFromExternalData",
                "studentTypeUnsetFromExternalData", "task_external_person_sync_batch_size",
                "task_external_person_sync_max_batches_per_exec", "task_external_person_sync_trigger",
                "task_scheduler_config_poll_trigger", "term_to_represent_early_alert", "test_env_mock_mail_server_port",
                "up_coach_query", "weekly_course_work_hour_ranges"
        };

        CONFIG_DESCRIPTIONS = new String[] {
                "The Title of the application", "The email address to blind carbon copy on every message",
                "Minutes of browser inactivity after which the user will be prompted to extend her session. " +
                        "Else the session will automatically end. This is distinct from the server-side session timeout" +
                        " and should generally be some fraction of the latter (1/3 by default).",
                "Coach is always set from external data if this is set to true.  It will overwrite any local " +
                        "changes for coach.", "All coach assignments to be deleted based on external data, " +
                "overwriting any local changes or externally assigned coach.",
                "Boolean value indicating whether or not to consider highly_trusted_ips",
                "The homepage of the Organization", "Institution name",
                "Whether the system should manage the integration database.  " +
                        "If true the tables and views will be manipulated automatically as the application evolves " +
                        "(tables added/modified/dropped, views added/modified/dropped).  " +
                        "If false, the administrator must follow and apply the changes.",
                "The Number of days prior to expiration to send out a Task Reminder",
                "To properly indicate the term load activity ranges need to be sepcified, is proper json with " +
                        "range name and [start,end] range.", "Whether the system should send out mails, or just " +
                "log their generation", "The externally visible url of the application",
                "Map status codes to more human-friendly names. Should be well-formed JSON with keys being code " +
                        "and values being names. Use special key 'default' for naming missing statuses.",
                "Student type is always set from external data if this is set to true.  It will overwrite any " +
                        "local changes for student type.", "All student type assignments to be deleted based on " +
                "external data, overwriting any local changes or externally assigned student type.",
                "Maximum number of person records to sync from the external person table in a single transaction. " +
                        "Negative values treated as unlimited. A zero (0) will be treated the same way as the web " +
                        "APIs (100 at this writing).", "Maximum number of transactions per execution of the " +
                "external person sync task. Negative values treated as unlimited. A zero (0) will disable the task.",
                "Frequency with which the person and external person tables will be synchronized. This is resource " +
                        "intensive, so should be run during off hours. Specify a number to run the sync every x-many " +
                        "milliseconds. Or specify two numbers separated by a slash (60000/1000) to represent both a " +
                        "period and an initial offset. Or specify a cron expression. " +
                        "See http://www.manpagez.com/man/5/crontab/ for cron expression syntax. Default value means " +
                        "'daily at 1AM, server-local time.'", "Frequency with which execution schedules for " +
                "background tasks will be checked for new configuration. Specify a number to run the sync every " +
                "x-many milliseconds.  Or specify two numbers separated by a slash (60000/1000) to represent " +
                "both a period and an initial offset. Or specify a cron expression. " +
                "See http://www.manpagez.com/man/5/crontab/ for cron expression syntax. Default value means " +
                "'every 15 minutes after an initial offset of 5 minutes.'", "Term to refer to early alerts," +
                " early intervention, or whatever you want to call them.", "The port to start the mock mail server " +
                "on in the test environment", "uPortal user lookup criteria by attribute name/value pairs. " +
                "(Currently these are anded.) Should be well-formed JSON that will parse to a Java Map<String,String>." +
                "", "To properly indate the weekly course work hour ranges need to be sepcified, is proper json with" +
                " range name and [start,end] range."
        };

        CONFIG_VALUES = new String[] {
                "SSP", "noone@test.com", "20", null, "false", "false", "http://test.edu", "My Edu", "true", "3",
                "[{\"name\":\"LT\",\"description\":\"Light part time schedule.\",\"rangeStart\":1," +
                        "\"rangeEnd\":6,\"rangeLabel\":\"1-6\"},\n\t\t\t\t\t\t\t\t\t\t\t{\"name\":\"PT\",\"description" +
                        "\":\"Part time student\",\"rangeStart\":7,\"rangeEnd\":12,\"rangeLabel\":\"7-12\"}, " +
                        "\n\t\t\t\t\t\t\t\t\t\t\t{\"name\":\"FT\",\"description\":\"Full time student\",\"rangeStart" +
                        "\":13,\"rangeEnd\":1000,\"rangeLabel\":\"13 or more\"}]",
                "false", "http://ssp.test.edu",
                "{\"default\": \"Enrolled\"}",
                "false", "false", "100",
                "-1", "0 0 1 * * *", "900000/300000", "Early Alert", "40025",
                "{\"SSP_ROLES\": \"SSP_COACH\"}",
                "[{\"name\":\"LTPT\",\"description\":\"Light Load\",\"rangeStart\":0,\"rangeEnd\":5,\"rangeLabel\":\"0-5\"},\n" +
                        "\t\t\t{\"name\":\"Moderate\",\"description\":\"Moderate Load\",\"rangeStart\":6,\"rangeEnd\":10,\"rangeLabel\":\"6-10\"}, \n" +
                        "\t\t\t{\"name\":\"LTFT\",\"description\":\"Light load Full Time Student\",\"rangeStart\":11,\"rangeEnd\":15,\"rangeLabel\":\"11-15\"},\n" +
                        "\t\t\t{\"name\":\"STFT\",\"description\":\"Standard load Full Time Student\",\"rangeStart\":16,\"rangeEnd\":20,\"rangeLabel\":\"16-20\"},\n" +
                        "\t\t\t{\"name\":\"HVFT\",\"description\":\"Heavy load Full Time Student\",\"rangeStart\":21,\"rangeEnd\":1000,\"rangeLabel\":\"More Than 20\"}]"
        };

        CONFIG_DEFAULT_VALUES = new String[] {
                "SSP", "noone@test.com", "20", "true", "false", "false", "http://test.edu", "My Edu", "true", "3",
                "[{\"name\":\"LT\",\"description\":\"Light part time schedule\",\"rangeStart\":1,\"rangeEnd\":6,\"" +
                        "rangeLabel\":\"1-6\"},\n\t\t\t\t\t\t\t\t\t\t\t{\"name\":\"PT\",\"description\":\"" +
                        "Part time student\",\"rangeStart\":7,\"rangeEnd\":12,\"rangeLabel\":\"7-12\"}, " +
                        "\n\t\t\t\t\t\t\t\t\t\t\t{\"name\":\"FT\",\"description\":\"Full time student\"," +
                        "\"rangeStart\":13,\"rangeEnd\":1000,\"rangeLabel\":\"13 or more\"}]",
                "false", "http://ssp.test.edu",
                "{\"default\": \"Enrolled\"}",
                "false", "false", "100", "-1", "0 0 1 * * *", "900000/300000", "Early Alert", "40025",
                "{\"SSP_ROLES\": \"SSP_COACH\"}",
                "[{\"name\":\"LTPT\",\"description\":\"Light Load\",\"rangeStart\":0,\"rangeEnd\":5,\"rangeLabel\":\"0-5\"},\n" +
                        "\t\t\t{\"name\":\"Moderate\",\"description\":\"Moderate Load\",\"rangeStart\":6,\"rangeEnd\":10,\"rangeLabel\":\"6-10\"}, \n" +
                        "\t\t\t{\"name\":\"LTFT\",\"description\":\"Light load Full Time Student\",\"rangeStart\":11,\"rangeEnd\":15,\"rangeLabel\":\"11-15\"},\n" +
                        "\t\t\t{\"name\":\"STFT\",\"description\":\"Standard load Full Time Student\",\"rangeStart\":16,\"rangeEnd\":20,\"rangeLabel\":\"16-20\"},\n" +
                        "\t\t\t{\"name\":\"HVFT\",\"description\":\"Heavy load Full Time Student\",\"rangeStart\":21,\"rangeEnd\":1000,\"rangeLabel\":\"More Than 20\"}]"
        };

        CONFIG_SORT_ORDERS = new int[] {
                100, 100, 501, 100, 102, 406, 100, 99, 100, 100, 105, 100, 100, 103, 102, 102, 306, 306, 206, 216, 101,
                100, 104, 106
        };

        CONFIG_ROWS = new JSONArray();

        for ( int index = 0; index < CONFIG_UUIDS.length; index++ ) {

            JSONObject temp = new JSONObject();
            temp.put("id", CONFIG_UUIDS[index]);
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("description", CONFIG_DESCRIPTIONS[index]);
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("name", CONFIG_NAMES[index]);
            temp.put("objectStatus", "ACTIVE");
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());
            temp.put("value", CONFIG_VALUES[index]);
            temp.put("valueValidation", null);
            temp.put("sortOrder", CONFIG_SORT_ORDERS[index]);
            temp.put("defaultValue", CONFIG_DEFAULT_VALUES[index]);

            CONFIG_ROWS.add(temp);
        }

        CONFIG_RESPONSE = new JSONObject();
        CONFIG_RESPONSE.put("success", "true");
        CONFIG_RESPONSE.put("message", "");
        CONFIG_RESPONSE.put("results", CONFIG_ROWS.size());
        CONFIG_RESPONSE.put("rows", CONFIG_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsConfigReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject) CONFIG_ROWS.get(0)).clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(CONFIG_PATH, testPostPutNegative);

        //tests permission on get name method
        expect()
            .statusCode(403)
        .given()
            .queryParam("name", CONFIG_NAMES[0])
        .when()
            .get(CONFIG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfigReferenceAllBody() {

        testResponseBody(CONFIG_PATH, CONFIG_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfigReferenceSingleItemBody() {

        testSingleItemResponseBody(CONFIG_PATH, (JSONObject) CONFIG_ROWS.get(1));

        //get /name
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", Matchers.equalTo(CONFIG_ROWS.get(1)))
        .given()
            .queryParam("name", CONFIG_NAMES[1])
        .when()
            .get(CONFIG_PATH);

    }
     

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsConfigReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CONFIG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfigReferenceSupportedMethodsPositive() {
        final JSONObject testPutPositive = (JSONObject) ((JSONObject) CONFIG_ROWS.get(2)).clone();
        testPutPositive.put("name", "testPutPositive");

        int checkResultCount = -2;

        //get all (store results to check at the end)
        Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CONFIG_PATH);

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
            .get(CONFIG_PATH + "/" + CONFIG_UUIDS[1]);

        //get /name
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
      .given()
            .queryParam("name", CONFIG_NAMES[1])
        .when()
            .get(CONFIG_PATH);

        final String putUUID = testPutPositive.get("id").toString();
        testPutPositive.remove("id");

        //post
        Response postResponse = expect()
            .statusCode(500)
        .given()
            .contentType("application/json")
            .body(testPutPositive)
        .when()
            .post(CONFIG_PATH);

       //verify post did not set data (see ConfigController)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("")
        .given()
            .queryParam("name", testPutPositive.get("name"))
        .when()
            .get(CONFIG_PATH);

        //put
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(testPutPositive)
        .when()
            .put(CONFIG_PATH + "/" + putUUID);

        //get more complete data from put using get
        final Response putResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CONFIG_PATH + "/" + putUUID);

        final Map parsedPutResponse = putResponse.getBody().jsonPath().getJsonObject("");
        testPutPositive.put("id", putUUID);
        testPutPositive.put("modifiedBy", getCurrentLoginCreatedModifiedBy());
        testPutPositive.put("modifiedDate", parsedPutResponse.get("modifiedDate"));

        //verify put worked
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPutPositive))
        .when()
            .get(CONFIG_PATH + "/" + putUUID);

        //delete
        expect()
            .statusCode(200)
            .log().ifError()
        .when()
            .delete(CONFIG_PATH + "/" + putUUID);

        testPutPositive.put("objectStatus", "INACTIVE");

        //get verify delete worked
        final Response deleteCheckResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CONFIG_PATH + "/" + putUUID);

        testPutPositive.put("modifiedDate", deleteCheckResponse.getBody().jsonPath().getJsonObject("modifiedDate"));

        //verify delete is still intact but inactive
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("", equalTo(testPutPositive))
        .when()
            .get(CONFIG_PATH + "/" + putUUID);

        //get (verify result # matches expected active)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount-1))
        .given()
            .queryParam("status", "ACTIVE")
            .when()
            .get(CONFIG_PATH);

        //get (verify result # matches expected inactive)
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(1))
        .given()
            .queryParam("status", "INACTIVE")
        .when()
            .get(CONFIG_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testConfigReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject) CONFIG_ROWS.get(4)).clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = (JSONObject) CONFIG_ROWS.get(5);

        referenceNegativeSupportedMethodTest(CONFIG_PATH, testNegativePostObject,
                testNegativeValidateObject);

        //get invalid name
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("")
        .given()
            .queryParam("name", "null")
        .when()
            .get(CONFIG_PATH);
    }
}

