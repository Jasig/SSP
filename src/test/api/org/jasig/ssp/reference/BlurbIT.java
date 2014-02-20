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
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matchers;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.fail;


public class BlurbIT extends AbstractReferenceTest {

    private static final String BLURB_PATH = "blurb";

    private static final String[] BLURB_UUIDS;
    private static final String[] BLURB_NAMES;
    private static final String[] BLURB_VALUES;
    private static final String[] BLURB_CODES;
    private static final String[] BLURB_DESCRIPTIONS;

    private static final JSONArray BLURB_ROWS;
    private static final JSONObject BLURB_RESPONSE;

    static {

        BLURB_UUIDS = new String[] {
                "1a92f3e6-9364-4e6f-8388-aee95a3af55d", "343eede9-b777-4d63-84f7-c65c71eee052",
                "eb189389-3112-4214-85c2-c406c773d245", "5187c5cd-5c20-44c1-920e-00dea367b3b6",
                "9a24e721-b59b-4d40-8edd-c522ac29f37a", "7b1d2b79-4f09-4338-944e-893f4f3cdeb7",
                "e45e465e-efde-4193-93c6-59996818d360", "baf298c9-5ddb-4c67-8957-c7e7445d2f08",
                "301ece83-ffe7-4d2e-aec1-5c07b806573a", "3a389f67-0e46-454f-96e2-51fb9518f53a",
                "2c921081-23b5-4bcb-b620-db191cedf09a", "06ad79bf-4e10-44f2-8c4f-16f2da8f7d2f",
                "ed71bc25-3190-4fd4-a68c-de14fcd264dc", "7b300c10-f858-4271-bfdd-1d271da358dd",
                "703b31a6-2d23-49cd-bbbc-a867920c2635", "ce66754b-869a-4a21-9e45-e414544cde0b",
                "e79a610e-6d61-4879-b3d3-b0a1d52495b5", "e43ec7f7-1a8a-422d-beca-bb3aad14cb36",
                "0893976d-f7bd-41ed-8ea1-0d1089ff0ed8", "002d9807-8986-4db7-bbfb-f547381a8ff0",
                "13d9e636-6a50-4a43-9de0-e9f50307c693", "4133e249-f43d-4cf2-ab3b-9855f0c04aa6",
                "ec3ce67d-b149-466f-a2fe-cc9b176fc321", "e591579e-27cf-49fe-9294-00130fe0f16d",
                "5c611b41-64bf-4c81-ae51-0687ddf0fcb0", "6b7ebbf3-2466-4189-9cb4-2f925dc8c4e2",
                "0f12811c-e5fb-4d32-b488-98f776e31f4f", "7ae1a511-566f-4b95-bc7a-30e1434fc4a2",
                "6e1641cb-845b-4022-aa93-5a5f3de513fc", "27f41528-2093-418a-871d-dbbd9434c79c",
                "3f1c5fb9-7577-429f-8e00-22c510c55866", "89ba37b3-4358-40d0-b61f-444a0712a03f",
                "7333c451-41f9-4e15-bea6-cd5effb26d23", "5c0edf26-f8ba-4242-b8ba-bfecee59d376",
                "97cb9704-39be-4f08-ae89-64ddc236aa73", "8d8dbc91-f30f-4f87-b405-bb2993da04d8",
                "5e0e9f07-e654-425f-a787-270c674b1048", "87f0c97c-4853-4b73-86f2-3c141bec532a",
                "b3c450ad-96c1-4fcb-a6a0-2561a2142e59", "b40f82d2-6d8c-4c75-be27-294ce73a70ea",
                "ebeb62d7-8d1a-4268-9839-ba3aafa690be", "7781ce38-5d18-4efa-8461-9e1f56aeb07f",
                "921133e9-8c3b-4783-9f13-08ef1b277120", "37a01fd6-810c-48a5-a6e9-81d3ec8801c1",
                "b8658813-b7c9-4d38-b8f9-cb306b2142eb", "9287622c-2a2a-4d50-b016-c7586fec1247",
                "3258a72a-9014-472f-93c6-179a99ff7c2e", "9cf8048b-63d1-46e4-ad8f-1e33d764df33",
                "946b05aa-2d3d-403f-854c-4a2071e51a5e", "663e877a-2ca9-493b-a181-f529d00358a2",
                "1c0bf63a-eb36-4af1-911f-804e95efd75b", "41c2488f-74b7-4100-9cc4-6dc188eb1fdf",
                "fdbe6203-a828-4fe8-8924-2463223646cb", "14aa910d-6061-4194-83e5-0cb97323b49a",
                "0b08f251-4970-4415-a37a-f4bae0820f23", "f0a1b90c-7424-42cb-9a5b-a56e5d381d72",
                "347fb756-ae34-4b94-a6ed-4aafb0b985a2", "35c412fd-838f-47e2-897a-adbd88a629ad",
                "f640d0f8-1763-4471-a2cb-71b062b6af78", "a696238c-2c77-4d84-b4df-a8c832d1c91a",
                "1026cbd8-7a8a-499a-a404-7f3af488fa65", "dabfee4c-f975-4c89-a749-1ff467da9884",
                "64ef22e1-0cf3-44df-9ccb-993ab7548baa", "fd6dc9c9-219a-4634-9ee7-c5fcbbef6b8e",
                "337d77c4-3689-4581-b924-6dc0dcdb1be1", "9b1182c7-9783-4be0-bc09-a30bf0d45f47",
                "2be74b03-8cf8-4410-a75b-20d8d4304cca", "247c1850-b368-4f29-8c8b-7b25efce8605",
                "7112e79a-0a61-413a-8948-8c4c19f2bb18", "cb3a7e29-4303-4a7a-87b5-fe9a10027e45",
                "06b4151e-8857-4e65-870c-36da50268ee7", "8ae3cabd-4401-4ecb-b0bb-f1096a21b48b",
                "bc816c86-04c8-4d7b-87a5-0a74ad3ea23a", "f079a6e4-8867-4c6a-b84a-b8fa07d8696c",
                "08e22868-8f83-489b-865d-95000b7ec374"
        };

        BLURB_NAMES = new String[] {
                "Intake Tab 1 Label", "Intake Tab 2 Label", "Intake Childcare Arrangements Label",
                "Intake Childcare Needed Label", "Intake Children Ages Label", "Intake Citizenship Label",
                "Intake Country of Citizenship Label", "Intake Employed Label", "Intake Military Affiliation Label",
                "Intake Number of Children Label", "Intake Place of Employment Label", "Intake Primary Caregiver Label",
                "Intake Shift Label", "Intake Total Hours Worked Label", "Intake Veteran Status Label",
                "Intake Wage Label", "Intake Tab 3 Label", "Intake Student Completed Label",
                "Intake Parents College Label", "Intake Special Needs Label", "Intake Student Status Label",
                "Intake Typical Grade Label", "Intake Tab 4 Label", "Intake EduLevel Question Label",
                "Please Explain Label", "Highest Grade Completed Label", "High School Attended Label",
                "Last Year Attended Label", "Year Graduated Label", "Year of GED Label", "Intake Tab 5 Label",
                "Intake Additional Info Label", "Intake Anticipated Graduation Date Label",
                "Bachelor's Degree Major Label", "Intake Confident Ability Label", "Intake Decided Occupation Label",
                "Intake Education/Career Goal Label", "Intake Hours Coursework Label", "Military Branch Label",
                "Other Funding Label", "Other Goal Label", "Intake Planned Major Label",
                "Intake Planned Occupation Label", "Intake Registration Load Label", "Intake Sure About Major Label",
                "Intake Sure Occupation Label", "Intake Funding Tab Label", "Intake Funding Question Label",
                "Other Challeneges Label", "Other Funding Label", "Intake Challenges Tab Label",
                "Intake Challenges Question Label", "Checklist tab label", "intake checklist label",
                "Intake Address 1 Label", "Intake Address 2 Label", "Intake Alternate Email Label",
                "Intake Alternate In Use Label", "Intake Cell Phone Label", "Intake City Label", "Intake Country Label",
                "Intake Ethnicity Label", "Intake First Name Label", "Intake Gender Label", "Intake Home Phone Label",
                "Intake Last Name Label", "Intake Marital Status Label", "Intake Middle Name Label",
                "Intake Non Local Label", "Intake Race Label", "Intake School Email Label", "Intake State Label",
                "Intake Birth Date Label", "Intake Work Phone Label", "Intake Zip Code Label"
        };

        BLURB_VALUES = new String[] {
                "Person", "Demographics", "If yes, what are your childcare arrangements?", "Childcare Needed?",
                "Ages? Separate each age with a comma. (1,5,12)", "Citizenship", "Country of Citizenship",
                "Are you Employed?", "Military Affiliation", "If You have Children, How Many?", "Place of Employment",
                "Are you a Primary Caregiver?", "Shift", "Total Hours worked weekly while attending school",
                "Veteran Status", "Wage", "EduPlan", "Check all that you have completed",
                "Have your parents obtained a college degree?", "Special needs or require special accomodation?",
                "Student Status", "What grade did you typically earn at your highest level of education?", "EduLevel",
                "Education level completed: Select all that apply", "Please Explain", "Highest Grade Completed",
                "High School Attended", "Last Year Attended", "Year Graduated", "Year of GED", "EduGoal",
                "Do you need additional information about which academic programs may lead to a future career?",
                "Anticipated Graduation Date?", "Bachelor's Degree Major",
                "Are you confident your abilities are compatible with the career field?",
                "Have you decided on a career/occupation?", "Education/Career Goal", "Hours per Week for Coursework",
                "Military Branch", "Other", "Other Goal", "What is your planned major?",
                "What is your planned occupation?", "Registration Load", "How sure are you about your major?",
                "How sure are you about your occupation?", "Funding", "How will you pay for college?", "Other",
                "Other", "Challenges", "Select all challenges that may be barriers to your academic success",
                "Completed Items", "Check all that apply", "Address Line 1", "Address Line 2", "Alternate Email",
                "In Use", "Cell Phone", "City", "Country", "Ethnicity", "First Name", "Gender", "Home Phone",
                "Last Name", "Marital Status", "Middle Name", "Non Local", "Race", "School Email", "State",
                "Birth Date", "Work Phone", "Zip Code"
        };

        BLURB_CODES = new String[] {
                "intake.tab1.label.tab1", "intake.tab2.label", "intake.tab2.label.childcare-arrangements",
                "intake.tab2.label.childcare-needed", "intake.tab2.label.children-ages",
                "intake.tab2.label.citizenship", "intake.tab2.label.country-citizenship", "intake.tab2.label.employed",
                "intake.tab2.label.military-affiliation", "intake.tab2.label.number-children",
                "intake.tab2.label.place-of-employment", "intake.tab2.label.primary-caregiver",
                "intake.tab2.label.shift", "intake.tab2.label.total-hours-worked", "intake.tab2.label.veteran-status",
                "intake.tab2.label.wage", "intake.tab3.label", "intake.tab3.label.completed",
                "intake.tab3.label.parents-college", "intake.tab3.label.special-needs",
                "intake.tab3.label.student-status", "intake.tab3.label.typical-grade", "intake.tab4.label",
                "intake.tab4.label.edu-level", "intake.tab4.label.explain-credits", "intake.tab4.label.highest-grade",
                "intake.tab4.label.highschool-attended", "intake.tab4.label.last-year-attended",
                "intake.tab4.label.year-graduated", "intake.tab4.label.year-of-ged", "intake.tab5.label",
                "intake.tab5.label.additional-info", "intake.tab5.label.anticipated-graduation",
                "intake.tab5.label.bachelor-major", "intake.tab5.label.confident-ability",
                "intake.tab5.label.decided-occupation", "intake.tab5.label.goal", "intake.tab5.label.hours-coursework",
                "intake.tab5.label.military-branch", "intake.tab5.label.other-funding", "intake.tab5.label.other-goal",
                "intake.tab5.label.planned-major", "intake.tab5.label.planned-occupation",
                "intake.tab5.label.registration-load", "intake.tab5.label.sure-major",
                "intake.tab5.label.sure-occupation", "intake.tab6.label", "intake.tab6.label.funding-question",
                "intake.tab6.label.other-challenges", "intake.tab6.label.other-funding", "intake.tab7.label",
                "intake.tab7.label.challenges-question", "intake.tab8.label", "intake.tab8.label.checklist-question",
                "ssp.label.address-1", "ssp.label.address-2", "ssp.label.alternate-email", "ssp.label.alt-in-use",
                "ssp.label.cell-phone", "ssp.label.city", "ssp.label.country", "ssp.label.ethnicity",
                "ssp.label.first-name", "ssp.label.gender", "ssp.label.home-phone", "ssp.label.last-name",
                "ssp.label.marital-status", "ssp.label.middle-name", "ssp.label.non-local", "ssp.label.race",
                "ssp.label.school-email", "ssp.label.state", "ssp.label.student-id", "ssp.label.work-phone",
                "ssp.label.zip"
        };

        BLURB_DESCRIPTIONS = new String[] {
                "Person tab label", "Demographics tab label", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "EduPlan tab label", "", "", "", "", "", "EduLevel tab label", "", "", "Highest Grade Completed", "",
                "", "", "", "EduGoal tab label", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                ""
        };

        BLURB_ROWS = new JSONArray();

        for ( int index = 0; index < BLURB_UUIDS.length; index++ ) {

            JSONObject temp = new JSONObject();
            temp.put("id", BLURB_UUIDS[index]);
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("objectStatus", "ACTIVE");
            temp.put("name", BLURB_NAMES[index]);
            temp.put("description", BLURB_DESCRIPTIONS[index]);
            temp.put("code", BLURB_CODES[index]);
            temp.put("value", BLURB_VALUES[index]);

            BLURB_ROWS.add(temp);
        }

        BLURB_RESPONSE = new JSONObject();
        BLURB_RESPONSE.put("success", "true");
        BLURB_RESPONSE.put("message", "");
        BLURB_RESPONSE.put("results", BLURB_ROWS.size());
        BLURB_RESPONSE.put("rows", BLURB_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsBlurbReference() {
        final JSONObject testPutNegative = (JSONObject) ((JSONObject) BLURB_ROWS.get(0)).clone();
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

       testResponseBody(BLURB_PATH, BLURB_RESPONSE);
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
        final JSONObject testPostPutPositive = (JSONObject) BLURB_ROWS.get(2);
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
            .body( (JSONObject)BLURB_ROWS.get(2) )
        .when()
            .put(BLURB_PATH + "/" + testPostPutPositive.get("id"));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testBlurbReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePutObject = (JSONObject) ((JSONObject) BLURB_ROWS.get(4)).clone();
        testNegativePutObject.put("name", ("testPutNegative" + testPassDeConflictNumber));
        testNegativePutObject.put("objectStatus", "");
        final JSONObject testNegativeValidateObject = (JSONObject) BLURB_ROWS.get(5);

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
            .body("results", equalTo(BLURB_ROWS.size()))
        .when()
            .get(BLURB_PATH);
    }
}