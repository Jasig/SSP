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


public class PersonalityTypeIT extends AbstractReferenceTest {

    private static final String PERSONALITY_TYPE_PATH = REFERENCE_PATH + "personalityType";

    private static final JSONObject PERSONALITY_TYPE_ISFJ;
    private static final JSONObject PERSONALITY_TYPE_ESFP;
    private static final JSONObject PERSONALITY_TYPE_ESTP;
    private static final JSONObject PERSONALITY_TYPE_ENTP;
    private static final JSONObject PERSONALITY_TYPE_ESFJ;
    private static final JSONObject PERSONALITY_TYPE_ISTP;
    private static final JSONObject PERSONALITY_TYPE_INTJ;
    private static final JSONObject PERSONALITY_TYPE_ISFP;
    private static final JSONObject PERSONALITY_TYPE_INTP;
    private static final JSONObject PERSONALITY_TYPE_INFJ;
    private static final JSONObject PERSONALITY_TYPE_ESTJ;
    private static final JSONObject PERSONALITY_TYPE_ENTJ;
    private static final JSONObject PERSONALITY_TYPE_INFP;
    private static final JSONObject PERSONALITY_TYPE_ENFP;
    private static final JSONObject PERSONALITY_TYPE_ENFJ;
    private static final JSONObject PERSONALITY_TYPE_ISTJ;

    private static final JSONArray PERSONALITY_TYPE_ROWS;
    private static final JSONObject PERSONALITY_TYPE_RESPONSE;

    static {

        PERSONALITY_TYPE_ISFJ = new JSONObject();
        PERSONALITY_TYPE_ISFJ.put("id", "03dabbb6-f5b8-42a5-809b-e644e8b4fc0f");
        PERSONALITY_TYPE_ISFJ.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ISFJ.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ISFJ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ISFJ.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ISFJ.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ISFJ.put("name", "ISFJ");
        PERSONALITY_TYPE_ISFJ.put("description", "Inwardly directed and reflective, factual and detailed, " +
                "subjective/empathy, decisive and organized.");

        PERSONALITY_TYPE_ESFP = new JSONObject();
        PERSONALITY_TYPE_ESFP.put("id", "2e347e7b-b4c1-4ed7-97a9-c40d2bcb67d8");
        PERSONALITY_TYPE_ESFP.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ESFP.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ESFP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ESFP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ESFP.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ESFP.put("name", "ESFP");
        PERSONALITY_TYPE_ESFP.put("description", "Outward action and people oriented, factual and detailed, " +
                "subjective/empathy, enjoys being in the moment and open to change.");

        PERSONALITY_TYPE_ESTP = new JSONObject();
        PERSONALITY_TYPE_ESTP.put("id", "3c908c63-497c-4274-bcc5-c30900d8a816");
        PERSONALITY_TYPE_ESTP.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ESTP.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ESTP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ESTP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ESTP.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ESTP.put("name", "ESTP");
        PERSONALITY_TYPE_ESTP.put("description", "Outward action and people oriented, factual and detailed, " +
                "reasoned and objective, enjoys being in the moment and open to change.");

        PERSONALITY_TYPE_ENTP = new JSONObject();
        PERSONALITY_TYPE_ENTP.put("id", "46eef8be-abc8-4876-8458-a80f54e9112d");
        PERSONALITY_TYPE_ENTP.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ENTP.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ENTP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ENTP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ENTP.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ENTP.put("name", "ENTP");
        PERSONALITY_TYPE_ENTP.put("description", "Outward action and people oriented, intuitive/possibilities, " +
                "reasoned and objective, enjoys being in the moment and open to change.");

        PERSONALITY_TYPE_ESFJ = new JSONObject();
        PERSONALITY_TYPE_ESFJ.put("id", "4ba0245f-9841-4cfa-b5a7-aaa533017519");
        PERSONALITY_TYPE_ESFJ.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ESFJ.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ESFJ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ESFJ.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ESFJ.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ESFJ.put("name", "ESFJ");
        PERSONALITY_TYPE_ESFJ.put("description", "Outward action and people oriented, factual and detailed, " +
                "subjective/empathy, decisive and organized.");

        PERSONALITY_TYPE_ISTP = new JSONObject();
        PERSONALITY_TYPE_ISTP.put("id", "4eb1e689-4346-4749-b5a9-bf3788ba5979");
        PERSONALITY_TYPE_ISTP.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ISTP.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ISTP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ISTP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ISTP.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ISTP.put("name", "ISTP");
        PERSONALITY_TYPE_ISTP.put("description", "Inwardly directed and reflective, factual and detailed, reasoned and " +
                "objective, enjoys being in the moment and open to change.");

        PERSONALITY_TYPE_INTJ = new JSONObject();
        PERSONALITY_TYPE_INTJ.put("id", "6520de73-beb9-4ba0-9db9-481353038c8c");
        PERSONALITY_TYPE_INTJ.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_INTJ.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_INTJ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_INTJ.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_INTJ.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_INTJ.put("name", "INTJ");
        PERSONALITY_TYPE_INTJ.put("description", "Inwardly directed and reflective, intuitive/possibilities, " +
                "reasoned and objective, decisive and organized.");

        PERSONALITY_TYPE_ISFP = new JSONObject();
        PERSONALITY_TYPE_ISFP.put("id", "6755dd3c-02c9-4de7-8b76-c8d69e636d58");
        PERSONALITY_TYPE_ISFP.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ISFP.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ISFP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ISFP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ISFP.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ISFP.put("name", "ISFP");
        PERSONALITY_TYPE_ISFP.put("description", "Inwardly directed and reflective, factual and detailed, " +
                "subjective/empathy, enjoys being in the moment and open to change.");

        PERSONALITY_TYPE_INTP = new JSONObject();
        PERSONALITY_TYPE_INTP.put("id", "6fb0068c-b1dc-4f85-96bf-2f65e07e6341");
        PERSONALITY_TYPE_INTP.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_INTP.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_INTP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_INTP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_INTP.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_INTP.put("name", "INTP");
        PERSONALITY_TYPE_INTP.put("description", "Inwardly directed and reflective, intuitive/possibilities, " +
                "reasoned and objective, enjoys being in the moment and open to change.");

        PERSONALITY_TYPE_INFJ = new JSONObject();
        PERSONALITY_TYPE_INFJ.put("id", "7c2a88e7-a41e-4d95-b43e-940226dc82d7");
        PERSONALITY_TYPE_INFJ.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_INFJ.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_INFJ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_INFJ.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_INFJ.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_INFJ.put("name", "INFJ");
        PERSONALITY_TYPE_INFJ.put("description", "Inwardly directed and reflective, intuitive/possibilities, " +
                "subjective/empathy, decisive and organized.");

        PERSONALITY_TYPE_ESTJ = new JSONObject();
        PERSONALITY_TYPE_ESTJ.put("id", "83358819-cfc1-40f4-a090-ab057ca748a1");
        PERSONALITY_TYPE_ESTJ.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ESTJ.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ESTJ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ESTJ.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ESTJ.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ESTJ.put("name", "ESTJ");
        PERSONALITY_TYPE_ESTJ.put("description", "Outward action and people oriented, factual and detailed, " +
                "reasoned and objective, and decisive and organized.");

        PERSONALITY_TYPE_ENTJ = new JSONObject();
        PERSONALITY_TYPE_ENTJ.put("id", "a77bf286-df12-4da0-99b5-c2d480c62afb");
        PERSONALITY_TYPE_ENTJ.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ENTJ.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ENTJ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ENTJ.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ENTJ.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ENTJ.put("name", "ENTJ");
        PERSONALITY_TYPE_ENTJ.put("description", "Outward action and people oriented, intuitive/possibilities, " +
                "reasoned and objective, and decisive and organized.");

        PERSONALITY_TYPE_ENFP = new JSONObject();
        PERSONALITY_TYPE_ENFP.put("id", "d24e6e27-1a0d-4cb2-96b4-229bf131ff8e");
        PERSONALITY_TYPE_ENFP.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ENFP.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ENFP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ENFP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ENFP.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ENFP.put("name", "ENFP");
        PERSONALITY_TYPE_ENFP.put("description", "Outward action and people oriented, intuitive/possibilities, " +
                "subjective/empathy, enjoys being in the moment and open to change.");


        PERSONALITY_TYPE_INFP = new JSONObject();
        PERSONALITY_TYPE_INFP.put("id", "da31be44-e716-4103-a835-0dab915f55b6");
        PERSONALITY_TYPE_INFP.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_INFP.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_INFP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_INFP.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_INFP.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_INFP.put("name", "INFP");
        PERSONALITY_TYPE_INFP.put("description", "Inwardly directed and reflective, intuitive/possibilities, " +
                "subjective/empathy, enjoys being in the moment and open to change.");

        PERSONALITY_TYPE_ENFJ = new JSONObject();
        PERSONALITY_TYPE_ENFJ.put("id", "f8bcd9d9-d6b9-4f8c-b9c3-477ec49d7271");
        PERSONALITY_TYPE_ENFJ.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ENFJ.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ENFJ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ENFJ.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ENFJ.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ENFJ.put("name", "ENFJ");
        PERSONALITY_TYPE_ENFJ.put("description", "Outward action and people oriented, factual and detailed, " +
                "subjective/empathy, decisive and organized.");

        PERSONALITY_TYPE_ISTJ = new JSONObject();
        PERSONALITY_TYPE_ISTJ.put("id", "fee8ea81-8e09-4061-8b6a-a9556c4d6eee");
        PERSONALITY_TYPE_ISTJ.put("createdDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ISTJ.put("createdBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ISTJ.put("modifiedDate", getDefaultCreatedModifiedByDate());
        PERSONALITY_TYPE_ISTJ.put("modifiedBy", getDefaultCreatedModifiedBy());
        PERSONALITY_TYPE_ISTJ.put("objectStatus", "ACTIVE");
        PERSONALITY_TYPE_ISTJ.put("name", "ISTJ");
        PERSONALITY_TYPE_ISTJ.put("description", "Inwardly directed and reflective, factual and detailed, " +
                "reasoned and objective, decisive and organized.");


        PERSONALITY_TYPE_ROWS = new JSONArray();
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ENFJ);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ENFP);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ENTJ);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ENTP);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ESFJ);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ESFP);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ESTJ);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ESTP);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_INFJ);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_INFP);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_INTJ);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_INTP);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ISFJ);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ISFP);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ISTJ);
        PERSONALITY_TYPE_ROWS.add(PERSONALITY_TYPE_ISTP);

        PERSONALITY_TYPE_RESPONSE = new JSONObject();
        PERSONALITY_TYPE_RESPONSE.put("success", "true");
        PERSONALITY_TYPE_RESPONSE.put("message", "");
        PERSONALITY_TYPE_RESPONSE.put("results", PERSONALITY_TYPE_ROWS.size());
        PERSONALITY_TYPE_RESPONSE.put("rows", PERSONALITY_TYPE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsPersonalityTypeReference() {
        final JSONObject testPostPutNegative = (JSONObject) PERSONALITY_TYPE_ISFJ.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(PERSONALITY_TYPE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testPersonalityTypeReferenceAllBody() {
        testResponseBody(PERSONALITY_TYPE_PATH, PERSONALITY_TYPE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testPersonalityTypeReferenceSingleItemBody() {

        testSingleItemResponseBody(PERSONALITY_TYPE_PATH, PERSONALITY_TYPE_ESFP);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsPersonalityTypeReference() {

        testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, PERSONALITY_TYPE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testPersonalityTypeReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) PERSONALITY_TYPE_ESFP.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(PERSONALITY_TYPE_PATH, PERSONALITY_TYPE_ENTP.get("id").toString(),
                testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testPersonalityTypeReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) PERSONALITY_TYPE_ESFJ.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = PERSONALITY_TYPE_ESTP;

        referenceNegativeSupportedMethodTest(PERSONALITY_TYPE_PATH, testNegativePostObject, testNegativeValidateObject);
    }

}
