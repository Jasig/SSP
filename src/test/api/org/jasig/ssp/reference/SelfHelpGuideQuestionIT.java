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


public class SelfHelpGuideQuestionIT extends AbstractReferenceTest {

    private static final String SELF_HELP_GUIDE_QUESTION_PATH = "selfHelpGuides/selfHelpGuideQuestions";
//TODO Fix error for name Transportation or call it an error ?!?
    private static final String[] SELF_HELP_GUIDE_QUESTION_UUIDS;
//    private static final String[] SELF_HELP_GUIDE_QUESTION_NAMES;
//    private static final String[] SELF_HELP_GUIDE_QUESTION_DESCRIPTIONS;
    private static final int[] SELF_HELP_GUIDE_QUESTION_QUESTION_NUMBERS;
    private static final String[] SELF_HELP_GUIDE_QUESTION_CHALLENGE_ID;
    private static final String[] SELF_HELP_GUIDE_QUESTION_SELF_HELP_GUIDE_ID;

    private static final JSONArray SELF_HELP_GUIDE_QUESTION_ROWS;
    private static final JSONObject SELF_HELP_GUIDE_QUESTION_RESPONSE;

    static {

        SELF_HELP_GUIDE_QUESTION_UUIDS = new String[] {
                "0e46733e-193d-4950-ba29-4cd0f9620561", "14f7a05a-df89-45f4-a2b0-18c0fe9a53be",
                "1b417345-aa6a-4859-94ab-9f1913f81872", "209d97cf-2f51-4261-80a5-21db7f0d7aa2",
                "26523538-3d22-40de-a3a2-4cb7c8be2443", "3c6abe0b-69cc-4555-88c8-380ef129459a",
                "3fb44068-df2b-49bf-b71d-035070435726", "7125d031-a0b9-4de2-9d81-a2663f032471",
                "7787fab3-d09c-4fcc-ae6a-a26898a23ee4", "7973e86d-f0a6-4653-8dd2-4365a3861861",
                "81953c1b-4930-4d62-90da-1fb032be399a", "850942d7-86d4-470d-8a43-7a23b3be679a",
                "961c2085-e546-408d-8b5f-41911b053b40", "9c2e722e-2eac-4821-993b-3af6cd2d94af",
                "9d3cbfec-97ca-492b-a9d0-f4020c84ed3a", "a4bf9024-3f11-4bdd-be01-60ec8154fd6e",
                "ad78dc04-bb23-4e62-b135-3983cb208ba6", "bdb02005-ec5e-4957-b485-e988d139eeb3",
                "c5a508fa-bdbc-4286-8999-ac208804d547", "cf39078c-183e-4e31-a912-7d4ceb59e6ce",
                "d549486e-6355-443a-a27c-8e5859a6dd25", "db8cdf11-9f1d-4b68-b39f-9d7ae7ce19a0",
                "ddcc44a1-9d1b-4bec-8aab-3ab800b31115", "e5cf1d3f-f044-41db-8b65-c62be7d02f3b",
                "ee618726-1bb1-40fc-a696-01ca1fb3d558", "f62330bd-0fc3-4ea3-8d77-b777dabe8484"
        };

  /*      SELF_HELP_GUIDE_QUESTION_NAMES = new String[] { "x"
        };

        SELF_HELP_GUIDE_QUESTION_DESCRIPTIONS = new String[] { "x"
        };    */

        SELF_HELP_GUIDE_QUESTION_QUESTION_NUMBERS = new int[] {
                1, 1, 1, 2, 2, 3, 3, 4, 4, 4, 5, 5, 5, 5, 6, 6, 6, 6, 7, 7, 8, 8, 9, 10, 10, 11
        };

        SELF_HELP_GUIDE_QUESTION_CHALLENGE_ID = new String[] {
                "80c5b019-1946-4a98-a7fd-b8d62684558c", "07b5c3ac-3bdf-4d12-b65d-94cb55167998",
                "43719c57-ec92-4e4a-9fb6-25208936fd18", "cab7d5a5-2ca5-4af7-a644-b3882ddc9b41",
                "f067c6ca-50ad-447a-ad12-f47dffdce42e", "eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3",
                "cab7d5a5-2ca5-4af7-a644-b3882ddc9b41", "7c0e5b76-9933-484a-b265-58cb280305a5",
                "cab7d5a5-2ca5-4af7-a644-b3882ddc9b41", "eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3",
                "fb206a68-78db-489d-9d5d-dce554f54eed", "fb206a68-78db-489d-9d5d-dce554f54eed",
                "1f5b63a9-9b50-412b-9971-23602f87444c", "f067c6ca-50ad-447a-ad12-f47dffdce42e",
                "7ad819ef-d1e3-4ebf-a05b-e233f17f9e55", "38f7ae25-902f-4381-851e-2e2319adb1bd",
                "f067c6ca-50ad-447a-ad12-f47dffdce42e", "f067c6ca-50ad-447a-ad12-f47dffdce42e",
                "80c5b019-1946-4a98-a7fd-b8d62684558c", "431abcf2-43fe-4d6a-8f83-b47c91157a15",
                "07b5c3ac-3bdf-4d12-b65d-94cb55167998", "eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3",
                "431abcf2-43fe-4d6a-8f83-b47c91157a15", "dbb8741c-ece0-4830-8ebf-774151cb6a1b",
                "38f7ae25-902f-4381-851e-2e2319adb1bd", "af7e472c-3b7c-4d00-a667-04f52f560940"
        };

        SELF_HELP_GUIDE_QUESTION_SELF_HELP_GUIDE_ID = new String[] {
                "6dce9c28-e7fe-e555-762e-f82ea0c75580", "4fd534df-e7fe-e555-7c71-0042593b1990",
                "3a6352c9-e7fe-e555-7f69-0124a5e5fe71", "6dce9c28-e7fe-e555-762e-f82ea0c75580",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "894073e7-e7fe-e555-7c05-ff79555478e7",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "4fd534df-e7fe-e555-7c71-0042593b1990",
                "894073e7-e7fe-e555-7c05-ff79555478e7", "6dce9c28-e7fe-e555-762e-f82ea0c75580",
                "6dce9c28-e7fe-e555-762e-f82ea0c75580", "894073e7-e7fe-e555-7c05-ff79555478e7",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5",
                "894073e7-e7fe-e555-7c05-ff79555478e7", "6dce9c28-e7fe-e555-762e-f82ea0c75580",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "6dce9c28-e7fe-e555-762e-f82ea0c75580",
                "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5", "4fd534df-e7fe-e555-7c71-0042593b1990",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "5b7bdb2a-e7fe-e555-7599-02c9e3cacfa5",
                "4fd534df-e7fe-e555-7c71-0042593b1990", "4fd534df-e7fe-e555-7c71-0042593b1990"
        };

        SELF_HELP_GUIDE_QUESTION_ROWS = new JSONArray();

        for ( int index = 0; index < SELF_HELP_GUIDE_QUESTION_UUIDS.length; index++ ) {

            JSONObject temp = new JSONObject();
            temp.put("id", SELF_HELP_GUIDE_QUESTION_UUIDS[index]);
            temp.put("name", "N/A");
            temp.put("description", "");
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("objectStatus", "ACTIVE");
            temp.put("questionNumber", SELF_HELP_GUIDE_QUESTION_QUESTION_NUMBERS[index]);
            temp.put("mandatory", "false");
            temp.put("critical", "false");
            temp.put("challengeId", SELF_HELP_GUIDE_QUESTION_CHALLENGE_ID[index]);
            temp.put("selfHelpGuideId", SELF_HELP_GUIDE_QUESTION_SELF_HELP_GUIDE_ID[index]);

            SELF_HELP_GUIDE_QUESTION_ROWS.add(temp);
        }

        SELF_HELP_GUIDE_QUESTION_RESPONSE = new JSONObject();
        SELF_HELP_GUIDE_QUESTION_RESPONSE.put("success", "true");
        SELF_HELP_GUIDE_QUESTION_RESPONSE.put("message", "");
        SELF_HELP_GUIDE_QUESTION_RESPONSE.put("results", SELF_HELP_GUIDE_QUESTION_ROWS.size());
        SELF_HELP_GUIDE_QUESTION_RESPONSE.put("rows", SELF_HELP_GUIDE_QUESTION_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsSelfHelpGuideQuestionDetailReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(0)).clone();
        testPostPutNegative.put("name", "testPostUnAuth");

  //      referenceAuthenticationControlledMethodNegativeTest(SELF_HELP_GUIDE_QUESTION_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideQuestionDetailReferenceAllBody() {

  //      testResponseBody(SELF_HELP_GUIDE_QUESTION_PATH, SELF_HELP_GUIDE_QUESTION_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideQuestionDetailReferenceSingleItemBody() {

        for ( Object a : SELF_HELP_GUIDE_QUESTION_ROWS) {
   //         testSingleItemResponseBody(SELF_HELP_GUIDE_QUESTION_PATH, (JSONObject) a );
        }

//        testSingleItemResponseBody(SELF_HELP_GUIDE_QUESTION_PATH, (JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(1));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsSelfHelpGuideQuestionDetailReference() {

//        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, SELF_HELP_GUIDE_QUESTION_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideQuestionDetailReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ((JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(2)).clone();
        testPostPutPositive.put("name", "testPostPositive");

//        referencePositiveSupportedMethodTest(SELF_HELP_GUIDE_QUESTION_PATH, SELF_HELP_GUIDE_QUESTION_UUIDS[3],
//                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testSelfHelpGuideQuestionDetailReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(4)).clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = (JSONObject) SELF_HELP_GUIDE_QUESTION_ROWS.get(5);

//        referenceNegativeSupportedMethodTest(SELF_HELP_GUIDE_QUESTION_PATH, testNegativePostObject,
 //               testNegativeValidateObject);
    }
}