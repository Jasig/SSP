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


public class LassiIT extends AbstractReferenceTest {

    private static final String LASSI_PATH = REFERENCE_PATH + "lassi";

    private static final JSONObject LASSI_SELF;
    private static final JSONObject LASSI_ATT;
    private static final JSONObject LASSI_ANX;
    private static final JSONObject LASSI_CONC;
    private static final JSONObject LASSI_INFO;
    private static final JSONObject LASSI_TIME;
    private static final JSONObject LASSI_STUDY;
    private static final JSONObject LASSI_MOTV;
    private static final JSONObject LASSI_TEST;
    private static final JSONObject LASSI_SELMAIN;

    private static final JSONArray LASSI_ROWS;
    private static final JSONObject LASSI_RESPONSE;

    static {

        LASSI_SELF = new JSONObject();
        LASSI_SELF.put("id", "1af31ae9-0832-496c-b3ed-d545d6bf9a89");
        LASSI_SELF.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_SELF.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_SELF.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_SELF.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_SELF.put("objectStatus", "ACTIVE");
        LASSI_SELF.put("name", "Self-Testing");
        LASSI_SELF.put("description", "The Self-Testing Scale assesses use of reviewing comprehension " +
                "monitoring techniques to determine their level of understanding of the information to be learned " +
                "-sample item: I stop periodically while reading and mentally go over or review what was said. " +
                "Low scoring students may need to develop an appreciation for the importance of self-testing, " +
                "and learn effective techniques for reviewing information and monitoring their level of " +
                "understanding or ability to apply what they are learning.");

        LASSI_ANX = new JSONObject();
        LASSI_ANX.put("id", "346321d3-b1a3-4ca0-b032-a788cc2114a1");
        LASSI_ANX.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_ANX.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_ANX.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_ANX.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_ANX.put("objectStatus", "ACTIVE");
        LASSI_ANX.put("name", "Anxiety");
        LASSI_ANX.put("description", "The Anxiety Scale assesses the degree to which students worry about school " +
                "and their academic performance. Students who score low on this scale are experiencing high levels " +
                "of anxiety associated with school -note that this scale is reverse scored. High levels of anxiety " +
                "can help direct attention away from completing academic tasks -sample item: When I am studying, " +
                "worrying about doing poorly in a course interferes with my concentration. Students who score low " +
                "on this scale may need to develop techniques for coping with anxiety and reducing worry so that " +
                "attention can be focused on the task at hand.");

        LASSI_ATT = new JSONObject();
        LASSI_ATT.put("id", "349c6a52-2cd1-489c-b958-b6e5abdb02ff");
        LASSI_ATT.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_ATT.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_ATT.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_ATT.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_ATT.put("objectStatus", "ACTIVE");
        LASSI_ATT.put("name", "Attitude");
        LASSI_ATT.put("description", "The Attitude Scale assesses attitudes and interest in college and " +
                "academic success. It examines how facilitative or debilitative their approach to college and " +
                "academics is for helping them get their work done and succeeding in college -sample item: I have a " +
                "positive attitude about attending my classes. Students who score low on this scale may not believe " +
                "college is relevant or important to them and may need to develop a better understanding of how " +
                "college and their academic performance relates to their future life goals.");

        LASSI_INFO = new JSONObject();
        LASSI_INFO.put("id", "48e6c049-eae6-477c-b13f-4abb9f8d64b8");
        LASSI_INFO.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_INFO.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_INFO.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_INFO.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_INFO.put("objectStatus", "ACTIVE");
        LASSI_INFO.put("name", "Information Processing");
        LASSI_INFO.put("description", "The Information Processing Scale assesses how well they can use " +
                "imagery, verbal elaboration, organization strategies, and reasoning skills as learning strategies " +
                "to help build bridges between what they already know and what they are trying to learn and remember, " +
                "i.e., knowledge acquisition, retention and future application -sample item: I translate what I am " +
                "studying into my own words. Students who score low on this scale may have difficulty making information" +
                " meaningful and storing it in memory in a way that will help them recall it in the future.");

        LASSI_CONC = new JSONObject();
        LASSI_CONC.put("id", "683c6a7d-18bd-407d-8058-22be356cc079");
        LASSI_CONC.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_CONC.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_CONC.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_CONC.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_CONC.put("objectStatus", "ACTIVE");
        LASSI_CONC.put("name", "Concentration");
        LASSI_CONC.put("description", "The Concentration Scale assesses ability to direct and maintain " +
                "attention on academic tasks -sample item: I find that during lectures I think of other things and " +
                "dont really listen to what is being said. Low scoring students may need to learn to monitor their " +
                "level of concentration and develop techniques to redirect attention and eliminate interfering " +
                "thoughts or feelings so that they can be more effective and efficient learners.");

        LASSI_TIME = new JSONObject();
        LASSI_TIME.put("id", "753ea301-aa13-4588-8c71-b7dc8bb26734");
        LASSI_TIME.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_TIME.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_TIME.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_TIME.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_TIME.put("objectStatus", "ACTIVE");
        LASSI_TIME.put("name", "Time Management");
        LASSI_TIME.put("description", "The Time Management Scale assesses application of time management " +
                "principles to academic situations -sample item: I set aside more time to study the subjects that " +
                "are difficult for me. Students who score low on this scale may need to develop effective scheduling " +
                "and monitoring techniques in order to assure timely completion of academic tasks and to avoid " +
                "procrastination while realistically including non-academic activities in their schedule.");

        LASSI_STUDY = new JSONObject();
        LASSI_STUDY.put("id", "816d8fc1-7e78-45c2-938b-eeb58a57066d");
        LASSI_STUDY.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_STUDY.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_STUDY.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_STUDY.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_STUDY.put("objectStatus", "ACTIVE");
        LASSI_STUDY.put("name", "Study Aids");
        LASSI_STUDY.put("description", "The Study Aids Scale assesses use of supports or resources to " +
                "help them learn or retain information -sample item: I use special study helps, such as italics " +
                "and headings, that are in my textbooks. Students with low scores may need to develop a better " +
                "understanding of the resources available to them and how to use these resources to help them be " +
                "more effective and efficient learners.");

        LASSI_MOTV = new JSONObject();
        LASSI_MOTV.put("id", "927cf28c-89c1-4e05-b675-ee5e45047797");
        LASSI_MOTV.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_MOTV.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_MOTV.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_MOTV.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_MOTV.put("objectStatus", "ACTIVE");
        LASSI_MOTV.put("name", "Motivation");
        LASSI_MOTV.put("description", "The Motivation Scale assesses diligence, self-discipline, and " +
                "willingness to exert the effort necessary to successfully complete academic requirements " +
                "-sample item: When work is difficult I either give up or study only the easy parts. " +
                "Students who score low on this scale need to accept more responsibility for their academic " +
                "outcomes and learn how to set and use goals to help accomplish specific tasks.");

        LASSI_TEST = new JSONObject();
        LASSI_TEST.put("id", "969c0bc0-0395-402d-b03a-8dd226187349");
        LASSI_TEST.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_TEST.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_TEST.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_TEST.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_TEST.put("objectStatus", "ACTIVE");
        LASSI_TEST.put("name", "Test Strategies");
        LASSI_TEST.put("description", "The Test Strategies Scale assesses use of test preparation and " +
                "test taking strategies -sample item: In taking tests, writing papers, etc., I find I have " +
                "misunderstood what is wanted and lose points because of it. Low scoring students may need to " +
                "learn more effective techniques for preparing for and taking tests so that they are able to " +
                "effectively demonstrate their knowledge of the subject matter.");

        LASSI_SELMAIN = new JSONObject();
        LASSI_SELMAIN.put("id", "f2adf116-8e2d-4831-a315-0c8c8a404efa");
        LASSI_SELMAIN.put("createdDate", getDefaultCreatedModifiedByDate());
        LASSI_SELMAIN.put("createdBy", getDefaultCreatedModifiedBy());
        LASSI_SELMAIN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        LASSI_SELMAIN.put("modifiedBy", getDefaultCreatedModifiedBy());
        LASSI_SELMAIN.put("objectStatus", "ACTIVE");
        LASSI_SELMAIN.put("name", "Selecting Main Ideas");
        LASSI_SELMAIN.put("description", "The Selecting Main Ideas Scale assesses skill at identifying important " +
                "information for further study from among less important information and supporting details -sample " +
                "item: When studying, I seem to get lost in the details and miss the important information. Students " +
                "who score low on this may need to develop their skill at separating out critical information on which " +
                "to focus their attention. Tasks such as reading a textbook can be overwhelming if students focus on " +
                "every detail presented.");

        LASSI_ROWS = new JSONArray();
        LASSI_ROWS.add(LASSI_ANX);
        LASSI_ROWS.add(LASSI_ATT);
        LASSI_ROWS.add(LASSI_CONC);
        LASSI_ROWS.add(LASSI_INFO);
        LASSI_ROWS.add(LASSI_MOTV);
        LASSI_ROWS.add(LASSI_SELMAIN);
        LASSI_ROWS.add(LASSI_SELF);
        LASSI_ROWS.add(LASSI_STUDY);
        LASSI_ROWS.add(LASSI_TEST);
        LASSI_ROWS.add(LASSI_TIME);

        LASSI_RESPONSE = new JSONObject();
        LASSI_RESPONSE.put("success", "true");
        LASSI_RESPONSE.put("message", "");
        LASSI_RESPONSE.put("results", LASSI_ROWS.size());
        LASSI_RESPONSE.put("rows", LASSI_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsLassiReference() {
        final JSONObject testPostPutNegative = (JSONObject)LASSI_SELF.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(LASSI_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testLassiReferenceAllBody() {

        testResponseBody(LASSI_PATH, LASSI_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testLassiReferenceSingleItemBody() {

        testSingleItemResponseBody(LASSI_PATH, LASSI_ATT);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsLassiReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, LASSI_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testLassiReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) LASSI_ANX.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(LASSI_PATH, LASSI_MOTV.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testLassiReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) LASSI_INFO.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = LASSI_CONC;

        referenceNegativeSupportedMethodTest(LASSI_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
