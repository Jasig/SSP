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
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;


public class PermissionIT extends AbstractReferenceTest {

    private static final String PERMISSION_PATH = REFERENCE_PATH + "permission";

    private static final String[] PERMISSION_NAMES;
    private static final String[] AUTHORITY_NAMES;

    private static final JSONArray PERMISSION_ROWS;
    private static final JSONObject PERMISSION_RESPONSE;

    static {

        PERMISSION_NAMES = new String[] {
                "DATA_EVERYONE","DATA_ACADEMIC_RESOURCE_CENTER","DATA_COUNSELING_SERVICES",
                "DATA_DISABILITY","DATA_DISPLACED_WORKERS","DATA_EARLY_ALERT","DATA_ENGLISH_SECOND_LANGUAGE",
                "DATA_FAST_FORWARD","DATA_INDIVIDUALIZED_LEARNING_PLAN","DATA_CL_01","DATA_CL_02","DATA_CL_03",
                "DATA_CL_04","DATA_CL_05","DATA_CL_06","DATA_CL_07","DATA_CL_08","DATA_CL_09","DATA_CL_10",
                "DATA_CL_11","DATA_CL_12","DATA_CL_13","DATA_CL_14","DATA_CL_15","DATA_CL_16","DATA_CL_17",
                "DATA_CL_18","DATA_CL_19","DATA_CL_20","DATA_MANAGER","DATA_STAFF","DATA_TEST","UNKNOWN",
                "ACCOMMODATION_READ","ACCOMMODATION_WRITE","MAP_PUBLIC_TEMPLATE_WRITE","PERSON_APPOINTMENT_READ",
                "PERSON_APPOINTMENT_WRITE","PERSON_APPOINTMENT_DELETE","PERSON_CASELOAD_READ","PERSON_CHALLENGE_READ",
                "PERSON_CHALLENGE_WRITE","PERSON_CHALLENGE_DELETE","PERSON_DOCUMENT_READ","PERSON_DOCUMENT_WRITE",
                "PERSON_DOCUMENT_DELETE","PERSON_EARLY_ALERT_READ","PERSON_EARLY_ALERT_WRITE",
                "PERSON_EARLY_ALERT_DELETE","PERSON_EARLY_ALERT_RESPONSE_READ","PERSON_EARLY_ALERT_RESPONSE_WRITE",
                "PERSON_EARLY_ALERT_RESPONSE_DELETE","PERSON_GOAL_READ","PERSON_GOAL_WRITE","PERSON_GOAL_DELETE",
                "PERSON_JOURNAL_ENTRY_READ","PERSON_JOURNAL_ENTRY_WRITE","PERSON_JOURNAL_ENTRY_DELETE","PERSON_READ",
                "PERSON_WRITE","PERSON_DELETE","PERSON_SEARCH_READ","PERSON_PROGRAM_STATUS_READ",
                "PERSON_PROGRAM_STATUS_WRITE","PERSON_PROGRAM_STATUS_DELETE","PERSON_TASK_READ","PERSON_TASK_WRITE",
                "PERSON_TASK_DELETE","PERSON_MAP_READ","PERSON_MAP_WRITE","PERSON_LEGACY_REMARK_READ",
                "PERSON_LEGACY_REMARK_WRITE","PERSON_INSTRUCTION_READ","REPORT_READ","REFERENCE_READ",
                "REFERENCE_WRITE","SELF_HELP_GUIDE_ADMIN_READ","SELF_HELP_GUIDE_ADMIN_WRITE",
                "SELF_HELP_GUIDE_ADMIN_DELETE","MY_GPS_TOOL","STUDENT_INTAKE_READ","STUDENT_INTAKE_WRITE",
                "API_KEY_READ","API_KEY_WRITE"
        };

        AUTHORITY_NAMES = new String[] {
                "ROLE_DATA_EVERYONE","ROLE_DATA_ACADEMIC_RESOURCE_CENTER",
                "ROLE_DATA_COUNSELING_SERVICES","ROLE_DATA_DISABILITY","ROLE_DATA_DISPLACED_WORKERS",
                "ROLE_DATA_EARLY_ALERT","ROLE_DATA_ENGLISH_SECOND_LANGUAGE","ROLE_DATA_FAST_FORWARD",
                "ROLE_DATA_INDIVIDUALIZED_LEARNING_PLAN","ROLE_DATA_CL_01","ROLE_DATA_CL_02","ROLE_DATA_CL_03",
                "ROLE_DATA_CL_04","ROLE_DATA_CL_05","ROLE_DATA_CL_06","ROLE_DATA_CL_07","ROLE_DATA_CL_08",
                "ROLE_DATA_CL_09","ROLE_DATA_CL_10","ROLE_DATA_CL_11","ROLE_DATA_CL_12","ROLE_DATA_CL_13",
                "ROLE_DATA_CL_14","ROLE_DATA_CL_15","ROLE_DATA_CL_16","ROLE_DATA_CL_17","ROLE_DATA_CL_18",
                "ROLE_DATA_CL_19","ROLE_DATA_CL_20","ROLE_DATA_MANAGER","ROLE_DATA_STAFF","ROLE_DATA_TEST",
                "ROLE_UNKNOWN","ROLE_ACCOMMODATION_READ","ROLE_ACCOMMODATION_WRITE","ROLE_MAP_PUBLIC_TEMPLATE_WRITE",
                "ROLE_PERSON_APPOINTMENT_READ","ROLE_PERSON_APPOINTMENT_WRITE","ROLE_PERSON_APPOINTMENT_DELETE",
                "ROLE_PERSON_CASELOAD_READ","ROLE_PERSON_CHALLENGE_READ","ROLE_PERSON_CHALLENGE_WRITE",
                "ROLE_PERSON_CHALLENGE_DELETE","ROLE_PERSON_DOCUMENT_READ","ROLE_PERSON_DOCUMENT_WRITE",
                "ROLE_PERSON_DOCUMENT_DELETE","ROLE_PERSON_EARLY_ALERT_READ","ROLE_PERSON_EARLY_ALERT_WRITE",
                "ROLE_PERSON_EARLY_ALERT_DELETE","ROLE_PERSON_EARLY_ALERT_RESPONSE_READ",
                "ROLE_PERSON_EARLY_ALERT_RESPONSE_WRITE","ROLE_PERSON_EARLY_ALERT_RESPONSE_DELETE",
                "ROLE_PERSON_GOAL_READ","ROLE_PERSON_GOAL_WRITE","ROLE_PERSON_GOAL_DELETE",
                "ROLE_PERSON_JOURNAL_ENTRY_READ","ROLE_PERSON_JOURNAL_ENTRY_WRITE",
                "ROLE_PERSON_JOURNAL_ENTRY_DELETE","ROLE_PERSON_READ","ROLE_PERSON_WRITE","ROLE_PERSON_DELETE",
                "ROLE_PERSON_SEARCH_READ","ROLE_PERSON_PROGRAM_STATUS_READ","ROLE_PERSON_PROGRAM_STATUS_WRITE",
                "ROLE_PERSON_PROGRAM_STATUS_DELETE","ROLE_PERSON_TASK_READ","ROLE_PERSON_TASK_WRITE",
                "ROLE_PERSON_TASK_DELETE","ROLE_PERSON_MAP_READ","ROLE_PERSON_MAP_WRITE",
                "ROLE_PERSON_LEGACY_REMARK_READ","ROLE_PERSON_LEGACY_REMARK_WRITE","ROLE_PERSON_INSTRUCTION_READ",
                "ROLE_REPORT_READ","ROLE_REFERENCE_READ","ROLE_REFERENCE_WRITE","ROLE_SELF_HELP_GUIDE_ADMIN_READ",
                "ROLE_SELF_HELP_GUIDE_ADMIN_WRITE","ROLE_SELF_HELP_GUIDE_ADMIN_DELETE","ROLE_MY_GPS_TOOL",
                "ROLE_STUDENT_INTAKE_READ","ROLE_STUDENT_INTAKE_WRITE","ROLE_API_KEY_READ","ROLE_API_KEY_WRITE"
        };

        PERMISSION_ROWS = new JSONArray();
        for ( int index = 0; index < PERMISSION_NAMES.length; index++  ) {
            JSONObject temp = new JSONObject();
            temp.put("name", PERMISSION_NAMES[index]);
            temp.put("authority", AUTHORITY_NAMES[index]);

            PERMISSION_ROWS.add(temp);
        }

        PERMISSION_RESPONSE = new JSONObject();
        PERMISSION_RESPONSE.put("success", "true");
        PERMISSION_RESPONSE.put("message", "");
        PERMISSION_RESPONSE.put("results", PERMISSION_ROWS.size());
        PERMISSION_RESPONSE.put("rows", PERMISSION_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsPermissionReference() {

        expect()
            .statusCode(403)
        .when()
            .get(PERMISSION_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testPermissionReferenceAllBody() {

       testResponseBody(PERMISSION_PATH, PERMISSION_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsPermissionReference() {

        testUnsupportedMethods(new String[]{"GET"}, PERMISSION_PATH);
    }

}
