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


public class ColorIT extends AbstractReferenceTest {

    private static final String COLOR_PATH = REFERENCE_PATH + "color";

    private static final String[] COLOR_UUIDS;
    private static final String[] COLOR_NAMES_DESCRIPTIONS;
    private static final String[] COLOR_CODES;
    private static final String[] COLOR_HEX_CODES;

    private static final JSONArray COLOR_ROWS;
    private static final JSONObject COLOR_RESPONSE;

    static {

        COLOR_UUIDS = new String[] {
                "8442bfcc-b44c-4b70-a99c-2b67691568ae", "5985772a-cda6-4a08-b76d-27b0e4e601f2",
                "27d1dfde-2f16-4365-9315-32c32998d84e", "2615ab88-cc09-48d4-a404-0774d71bf8a4",
                "64502a6d-c4f6-4850-87b9-856aab5955de", "8dad87be-936b-450d-8f4a-f733b679a7dd",
                "43cbb2d1-4f5b-4dc7-ad71-f59df121d8fb", "0ada03c8-6ec0-49c8-987c-e33a4d439ee3",
                "2f70bc51-7173-4d83-9c7a-17cca2e8d4db", "ff5733f2-ba81-4daa-8c78-50915c935506",
                "d0770680-f0ef-4dab-ad0b-7f175ede779d", "9a6b915b-91c8-49d5-8e1a-47c67c5aed59",
                "93e117d8-bd62-4bff-a812-94cae3a65a12", "e0432f26-ba18-43bd-9475-d3cd899e2f8c",
                "f7a4b243-a1bd-4c28-b557-fc1f6b8439eb"
        };

        COLOR_NAMES_DESCRIPTIONS = new String[] {
                "Aqua", "Brown", "Fuchsia", "Gray", "Green", "Light Blue", "Light Green", "Orange", "Pink",
                "Purple", "Red", "Salmon", "Tan", "Violet", "Yellow"
        };

        COLOR_CODES = new String[] {
                "AQUA", "BROWN", "FUCHSIA", "GRAY", "GREEN", "LT_BLUE", "LT_GREEN", "ORANGE", "PINK", "PURPLE",
                "RED", "SALMON", "TAN", "VIOLET", "YELLOW"
        };

        COLOR_HEX_CODES = new String[] {
                "00FFFF", "A52A2A", "FF00FF", "808080", "008000", "ADD8E6", "90EE90", "FFA500", "FFC0CB", "800080",
                "FF0000", "FA8072", "D2B48C", "EE82EE", "FFFF00"
        };

        COLOR_ROWS = new JSONArray();

        for ( int index = 0; index < COLOR_UUIDS.length; index++ ) {
            JSONObject temp = new JSONObject();
            temp.put("id", COLOR_UUIDS[index]);
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("objectStatus", "ACTIVE");
            temp.put("name", COLOR_NAMES_DESCRIPTIONS[index]);

            if ( index != 7 && index != 14 ) {
                temp.put("description", COLOR_NAMES_DESCRIPTIONS[index]);
            } else if ( index == 7 ) {
                temp.put("description", "Default for important courses");
            } else {
                temp.put("description", "Default for transcripted courses");
            }

            temp.put("code", COLOR_CODES[index]);
            temp.put("hexCode", COLOR_HEX_CODES[index]);

            COLOR_ROWS.add(temp);
        }

        COLOR_RESPONSE = new JSONObject();
        COLOR_RESPONSE.put("success", "true");
        COLOR_RESPONSE.put("message", "");
        COLOR_RESPONSE.put("results", COLOR_ROWS.size());
        COLOR_RESPONSE.put("rows", COLOR_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsColorReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject) COLOR_ROWS.get(0)).clone();
        testPostPutNegative.put("code", ("NOCOLOR" + testPassDeConflictNumber));
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(COLOR_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testColorReferenceAllBody() {

        testResponseBody(COLOR_PATH, COLOR_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testColorReferenceSingleItemBody() {

        testSingleItemResponseBody(COLOR_PATH, ((JSONObject) COLOR_ROWS.get(1)));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsColorReference() {
        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, COLOR_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testColorReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ((JSONObject) COLOR_ROWS.get(2)).clone();
        testPostPutPositive.put("code", ("TRANS" + testPassDeConflictNumber));
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(COLOR_PATH, COLOR_UUIDS[3], testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testColorReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject) COLOR_ROWS.get(4)).clone();
        testNegativePostObject.put("code", ("HS" + testPassDeConflictNumber));
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = ((JSONObject) COLOR_ROWS.get(5));

        referenceNegativeSupportedMethodTest(COLOR_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}