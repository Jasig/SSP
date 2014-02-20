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


public class JournalStepDetailIT extends AbstractReferenceTest {

    private static final String JOURNAL_STEP_DETAIL_PATH = REFERENCE_PATH + "journalStepDetail";

    private static final String[] JOURNAL_STEP_DETAIL_UUIDS;
    private static final String[] JOURNAL_STEP_DETAIL_NAMES;
    private static final String[] JOURNAL_STEP_DETAIL_DESCRIPTIONS;
    private static final JSONArray JOURNAL_STEP_DETAIL_ROWS;
    private static final JSONObject JOURNAL_STEP_DETAIL_RESPONSE;

    static {

        JOURNAL_STEP_DETAIL_UUIDS = new String[] {
                "0a640a2a-409d-1271-8140-e59ca3d501d4", "0a640a2a-409d-1271-8140-e590916901d1",
                "0a640a2a-409d-1271-8140-e5a47eae01de", "471afc02-ab5c-11e1-a997-0026b9e7ff4c",
                "0a640a2a-409d-1271-8140-e591370f01d2", "0a640a2a-409d-1271-8140-e59d964b01d5",
                "0a640a2a-409d-1271-8140-e591809201d3", "0a640a2a-409d-1271-8140-e5a65ff301e0",
                "0a640a2a-409d-1271-8140-e59fd0ac01d8", "0a640a2a-409d-1271-8140-e5a9b7f101e4",
                "0a640a2a-409d-1271-8140-e5a3c95601dd", "0a640a2a-409d-1271-8140-e5a349fe01dc",
                "0a640a2a-409d-1271-8140-e5a8293001e2", "0a640a2a-409d-1271-8140-e5a2a95a01db",
                "0a640a2a-409d-1271-8140-e5a141a901d9", "0a640a2a-409d-1271-8140-e5a1ee8a01da",
                "0a640a2a-409d-1271-8140-e5acca8901e8", "0a640a2a-409d-1271-8140-e5a76d4c01e1",
                "0a640a2a-409d-1271-8140-e59ee80701d6", "0a640a2a-409d-1271-8140-e5aad9d301e6",
                "0a640a2a-409d-1271-8140-e59f7c0b01d7", "0a640a2a-409d-1271-8140-e5a9004501e3",
                "471afc02-ab5c-11f1-a997-0026b9e7ff5d", "0a640a2a-409d-1271-8140-e5c57272023d",
                "0a640a2a-409d-1271-8140-e5ad5bd301e9", "0a640a2a-409d-1271-8140-e5aa558d01e5",
                "0a640a2a-409d-1271-8140-e5ae2e7601ea", "0a640a2a-409d-1271-8140-e5abf32701e7"
        };

        JOURNAL_STEP_DETAIL_NAMES = new String[] {
                "1 on 1 time completed", "Address immediate concerns", "Books using financial aid",
                "Completed student intake process", "Confirm 15 hours complete", "Confirm 30 hours complete",
                "Develop review map", "Discuss transfer expectations",
                "Discuss what the student learned in orientation", "Established a follow up appointment",
                "Fafsa review assistance", "Financial aid application", "Financial aid applying for appeal ",
                "Financial aid introduction", "Graduation check", "Introduce student success curriculum",
                "Reviewed academic goals", "Reviewed courses in the selected program", "Reviewed gpa",
                "Reviewed lassi scores", "Reviewed midterm grades", "Reviewed placement test scores",
                "Reviewed program choices with student", "Reviewed progress towards goals",
                "Reviewed student career goals", "Reviewed the jung typology",
                "Reviewed the student intake results", "Reviewed transfer credit"
        };

        JOURNAL_STEP_DETAIL_DESCRIPTIONS = new String[] {
                "1 on 1 time completed", "Address immediate concerns",
                "Discussed how to use Financial AID to buy books and supplies for courses. ",
                "Completed Student Intake Process", "Confirm 15 hours complete towards a program of study",
                "Confirm 30 hours complete in a specific program of study", "Develop / Review MAP",
                "Discussed transfer expectations with the student, and reviewed the college process for requesting a " +
                        "transcript be sent to another college", "Discuss what the student learned in Orientation",
                "Established a follow up appointment with the student to meet again at:",
                "FAFSA - Review / Assistance, Assisted the student with understanding the FAFSA and filling it out",
                "Financial AID - Application, assited the student with the financial aid application process, and " +
                        "review the FAFSA process",
                "Worked with the student on the FIN AID appeal process and discussed the options they have",
                "Financial AID - Introduction, covered financial aid in general and broad terms and discussed " +
                        "options the student might consider",
                "Discuss the graduation process and do a graduation check with the student",
                "Introduce student success curriculum to the student and review the next steps and expectations for" +
                        " the class.", "Reviewed academic goals with the student",
                "Reviewed courses in the selected program of study with the student and discussed elective options and" +
                        " difference between program electives and general electives",
                "Reviewed GPA with student and discussed options",
                "Reviewed LASSI scores and discussed with the student the types of learning strategies that might " +
                        "work best for them", "Reviewed mid-term grades with the student and adjusted accordingly",
                "Reviewed Placement Test Scores with the student and the impact those will have on the first term at " +
                        "the college", "Reviewed Program Choices with Student", "Reviewed progress towards goals",
                "Reviewed student career goals and discuss options to learn more about the desired field",
                "Reviewed the Jung Typology with the student and discussed the learning style and careers that " +
                        "match best with the results",
                "Reviewed the student intake results with the student to clarify and better understand the results",
                "Reviewed the students transfer credit for potential use at the college, the following was decided:"
        };

        JOURNAL_STEP_DETAIL_ROWS = new JSONArray();

        for ( int index = 0; index < JOURNAL_STEP_DETAIL_UUIDS.length; index++ ) {

            JSONObject temp = new JSONObject();
            temp.put("id", JOURNAL_STEP_DETAIL_UUIDS[index]);
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("sortOrder", 0);
            temp.put("description", JOURNAL_STEP_DETAIL_DESCRIPTIONS[index]);
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("name", JOURNAL_STEP_DETAIL_NAMES[index]);
            temp.put("objectStatus", "ACTIVE");
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());

            JOURNAL_STEP_DETAIL_ROWS.add(temp);
        }

        JOURNAL_STEP_DETAIL_RESPONSE = new JSONObject();
        JOURNAL_STEP_DETAIL_RESPONSE.put("success", "true");
        JOURNAL_STEP_DETAIL_RESPONSE.put("message", "");
        JOURNAL_STEP_DETAIL_RESPONSE.put("results", JOURNAL_STEP_DETAIL_ROWS.size());
        JOURNAL_STEP_DETAIL_RESPONSE.put("rows", JOURNAL_STEP_DETAIL_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsJournalStepDetailReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject) JOURNAL_STEP_DETAIL_ROWS.get(0)).clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(JOURNAL_STEP_DETAIL_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepDetailReferenceAllBody() {

       testResponseBody(JOURNAL_STEP_DETAIL_PATH, JOURNAL_STEP_DETAIL_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepDetailReferenceSingleItemBody() {

        testSingleItemResponseBody(JOURNAL_STEP_DETAIL_PATH, (JSONObject) JOURNAL_STEP_DETAIL_ROWS.get(1));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsJournalStepDetailReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, JOURNAL_STEP_DETAIL_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepDetailReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ((JSONObject) JOURNAL_STEP_DETAIL_ROWS.get(2)).clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(JOURNAL_STEP_DETAIL_PATH, JOURNAL_STEP_DETAIL_UUIDS[3],
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testJournalStepDetailReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject) JOURNAL_STEP_DETAIL_ROWS.get(4)).clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = (JSONObject) JOURNAL_STEP_DETAIL_ROWS.get(5);

        referenceNegativeSupportedMethodTest(JOURNAL_STEP_DETAIL_PATH, testNegativePostObject,
                testNegativeValidateObject);
    }
}


