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


public class DisabilityAccommodationIT extends AbstractReferenceTest {

    private static final String DISABILITY_ACCOMMODATION_PATH = REFERENCE_PATH + "disabilityAccommodation";

    private static final String[] DISABILITY_ACCOMMODATION_UUIDS;
    private static final String[] DISABILITY_ACCOMMODATION_NAMES;
    private static final boolean[] DISABILITY_USE_DESCRIPTION_VALUES;
    private static final String[] DISABILITY_DESCRIPTION_FIELD_LABELS;
    private static final String[] DISABILITY_DESCRIPTION_FIELD_TYPES;
    private static final JSONArray DISABILITY_ACCOMMODATION_ROWS;
    private static final JSONObject DISABILITY_ACCOMMODATION_RESPONSE;

    static {

        DISABILITY_ACCOMMODATION_UUIDS = new String[] {
                "74603eff-ea00-413a-acd5-a55755b79778", "1f18e705-187a-447d-8f03-8814fc854fc6",
                "85138559-4d40-420d-bd78-119276a7b42c", "13588bfe-c9b9-4134-bb6f-ee875c2e8789",
                "f4620bbb-35b5-4fac-9746-60e81abeb5b9", "3424f88b-614c-4686-af57-760ea0b57d2f",
                "a1d9b1c8-6841-46f5-9dda-5b7e4130fd42", "a3b9e31e-b3af-41b3-b602-151b60253938",
                "6d745998-8a2f-4bf0-a104-0e0499c8f18f", "90ed8cf5-040c-4f68-83c3-863120da0388",
                "9c49cb24-0949-4472-988b-2f539fbe5843", "08dd4152-82db-429f-9a7f-7f9a749c46f7",
                "f0ff3e53-bf45-421e-8bbf-c0ef8a2049b4", "c2d81565-0dd5-4f1a-ad54-2640f4fd7749",
                "3761d6a4-6f71-4c88-a1d7-f9bea7079346", "2da4bd54-29b8-487e-ae81-82867c20631f",
                "51155fbf-f681-4492-a059-37626e08e732", "595f1fb6-81f6-40d4-b82a-e0cce834156c",
                "0412e725-05d7-4161-84cd-fd77f494583d", "3c3a0e9d-ed9d-47ad-9a5c-c4afe4edbe69"
        };

        DISABILITY_ACCOMMODATION_NAMES = new String[] {
                "Allow students to record answers on regular paper -unable to fill scantron sheet",
                "Closed captioning for video tapes", "Distraction-free testing environment -Testing Center",
                "Enlarged print materials", "Extended time on exams/quizzes", "FM Loop System with neck coil",
                "Interpreter", "Other adjustments-accommodations", "Preferential seating in front of class",
                "Proctored Exam with reader in Tutorial Center", "Proctored Exam with writer in Tutorial Center",
                "Provide Time Management schedule sheet", "Tape of a recorded lecture", "Tape recorded textbooks " +
                "-Eligible from RFB and D", "To be determined", "Training on computer software", "Tutors",
                "Use of a computer for tests, writing assignments, etc.", "Use of calculator", "Volunteer Note Taker"
        };

        DISABILITY_USE_DESCRIPTION_VALUES = new boolean[] {
                false, false, false, true, true, false, false, true, false, false, false, false, false, false, false,
                true, false, false, false, false
        };

        DISABILITY_DESCRIPTION_FIELD_LABELS = new String[] {
                "Specify Font Size", "Specify Time", "Specify", "Specify"
        };

        DISABILITY_DESCRIPTION_FIELD_TYPES = new String[] {
                "short", "short", "long", "long"
        };


        DISABILITY_ACCOMMODATION_ROWS = new JSONArray();

        int descriptionArraysIndex = 0;
        for ( int index = 0; index < DISABILITY_ACCOMMODATION_UUIDS.length; index++ ) {

            JSONObject temp = new JSONObject();
            temp.put("id", DISABILITY_ACCOMMODATION_UUIDS[index]);
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("description", "");
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("name", DISABILITY_ACCOMMODATION_NAMES[index]);
            temp.put("objectStatus", "ACTIVE");
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());

            if ( !DISABILITY_USE_DESCRIPTION_VALUES[index] ) {
                temp.put("useDescription", null);
                temp.put("descriptionFieldLabel", null);
                temp.put("descriptionFieldType", null);
            }  else {
                temp.put("useDescription", true);
                temp.put("descriptionFieldLabel", DISABILITY_DESCRIPTION_FIELD_LABELS[descriptionArraysIndex]);
                temp.put("descriptionFieldType", DISABILITY_DESCRIPTION_FIELD_TYPES[descriptionArraysIndex]);
                descriptionArraysIndex++;
            }

            DISABILITY_ACCOMMODATION_ROWS.add(temp);
        }

        DISABILITY_ACCOMMODATION_RESPONSE = new JSONObject();
        DISABILITY_ACCOMMODATION_RESPONSE.put("success", "true");
        DISABILITY_ACCOMMODATION_RESPONSE.put("message", "");
        DISABILITY_ACCOMMODATION_RESPONSE.put("results", DISABILITY_ACCOMMODATION_ROWS.size());
        DISABILITY_ACCOMMODATION_RESPONSE.put("rows", DISABILITY_ACCOMMODATION_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsDisabilityAccommodationReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject) DISABILITY_ACCOMMODATION_ROWS.get(0)).clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(DISABILITY_ACCOMMODATION_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityAccommodationReferenceAllBody() {

        testResponseBody(DISABILITY_ACCOMMODATION_PATH, DISABILITY_ACCOMMODATION_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityAccommodationReferenceSingleItemBody() {

        testSingleItemResponseBody(DISABILITY_ACCOMMODATION_PATH, (JSONObject) DISABILITY_ACCOMMODATION_ROWS.get(1));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsDisabilityAccommodationReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, DISABILITY_ACCOMMODATION_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityAccommodationReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ((JSONObject) DISABILITY_ACCOMMODATION_ROWS.get(2)).clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(DISABILITY_ACCOMMODATION_PATH, DISABILITY_ACCOMMODATION_UUIDS[3],
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testDisabilityAccommodationReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject) DISABILITY_ACCOMMODATION_ROWS.get(4)).clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = (JSONObject) DISABILITY_ACCOMMODATION_ROWS.get(5);

        referenceNegativeSupportedMethodTest(DISABILITY_ACCOMMODATION_PATH, testNegativePostObject,
                testNegativeValidateObject);
    }
}

