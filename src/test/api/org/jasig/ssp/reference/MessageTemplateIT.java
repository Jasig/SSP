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


import com.jayway.restassured.specification.ResponseSpecification;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import static com.jayway.restassured.RestAssured.expect;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.jasig.ssp.ExpectationUtils.*;


public class MessageTemplateIT extends AbstractReferenceTest {

    private static final String MESSAGE_TEMPLATE_PATH = REFERENCE_PATH + "messageTemplate";

    private static final JSONObject MESSAGE_TEMPLATE_ACTNPLN;
    private static final JSONObject MESSAGE_TEMPLATE_ACTSTEP;
    private static final JSONObject MESSAGE_TEMPLATE_CCE;
    private static final JSONObject MESSAGE_TEMPLATE_CUST;
    private static final JSONObject MESSAGE_TEMPLATE_EACONFADV;

    private static final JSONArray MESSAGE_TEMPLATE_ROWS;

    static {

        MESSAGE_TEMPLATE_CCE = new JSONObject();
        MESSAGE_TEMPLATE_CCE.put("id", "0b7e484d-44e4-4f0d-8db5-3518d015b495");
        MESSAGE_TEMPLATE_CCE.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_CCE.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_CCE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_CCE.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_CCE.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_CCE.put("name", "Contact Coach Email");
        MESSAGE_TEMPLATE_CCE.put("description", "Contact Coach Email");
        MESSAGE_TEMPLATE_CCE.put("subject", "A Message from Your Advisee");
        MESSAGE_TEMPLATE_CCE.put("body", "From: ${fullName} <br/> Subject: ${subject} <br/><br/>${message}");
       
        MESSAGE_TEMPLATE_CUST = new JSONObject();
        MESSAGE_TEMPLATE_CUST.put("id", "31cf8d8d-2bc9-44e0-aad1-d8ba43530bb0");
        MESSAGE_TEMPLATE_CUST.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_CUST.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_CUST.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_CUST.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_CUST.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_CUST.put("name", "Custom Action Plan Task Email");
        MESSAGE_TEMPLATE_CUST.put("description", "Custom Action Plan Task Email");
        MESSAGE_TEMPLATE_CUST.put("subject", "An Action Item is Due for Review");
        MESSAGE_TEMPLATE_CUST.put("body", "${task.person.firstName},<br/>An Action Item identified through the " +
                "Community College Resources application needs your attention.  Please login to your account at " +
                "https://www.studentsuccessplan.org to review and complete the assigned item.<br/><br/>The following " +
                "item is due for review:<br/>Name: ${task.name}<br/>Description: ${task.description}<br/>Due Date: " +
                "${dueDateFormatted}");
        
        MESSAGE_TEMPLATE_ACTNPLN = new JSONObject();
        MESSAGE_TEMPLATE_ACTNPLN.put("id", "5d183f35-023d-40ea-b8d9-66fbe190fffb");
        MESSAGE_TEMPLATE_ACTNPLN.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_ACTNPLN.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_ACTNPLN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_ACTNPLN.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_ACTNPLN.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_ACTNPLN.put("name", "Action Plan Email");
        MESSAGE_TEMPLATE_ACTNPLN.put("description", "Action Plan Email");
        MESSAGE_TEMPLATE_ACTNPLN.put("subject", "Action plan for ${fullName}");
        MESSAGE_TEMPLATE_ACTNPLN.put("body", "<html> <body>Dear ${fullName},<br/> <br/> As a result of completing the " +
                "self help guide questionnaire(s) you have  identified the following resource information as being " +
                "imperative to your  success.&nbsp;&nbsp;Below you will find the solutions you felt offered the best" +
                "  path to your success.<br/> <br/> <br/> #foreach($taskTO in $taskTOs)<b>Resource:</b>&nbsp;" +
                "${taskTO.name}<br/><b>Description:</b>&nbsp;${taskTO.description}<br/><br/>#end <br/> " +
                "#foreach($goalTO in $goalTOs)<b>Resource:</b>&nbsp;${goalTO.name}<br/><b>Description:</b>" +
                "&nbsp;${goalTO.description}<br/><br/>#end <br/> Should you have any questions concerning the" +
                " resource information listed please contact the resource in question or the counseling staff at" +
                " your institution.<br/> <br/> Thank you.</body></html>");
      
        MESSAGE_TEMPLATE_ACTSTEP = new JSONObject();
        MESSAGE_TEMPLATE_ACTSTEP.put("id", "aec07252-1ff0-479d-a2ef-c0e017e1c05d");
        MESSAGE_TEMPLATE_ACTSTEP.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_ACTSTEP.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_ACTSTEP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_ACTSTEP.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_ACTSTEP.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_ACTSTEP.put("name", "Action Plan Step Email");
        MESSAGE_TEMPLATE_ACTSTEP.put("description", "Action Plan Step Email");
        MESSAGE_TEMPLATE_ACTSTEP.put("subject", "An Action Item is Due for Review");
        MESSAGE_TEMPLATE_ACTSTEP.put("body", "${task.person.firstName},<br/>An Action Item identified "
        		+ "through the Community College Resources application needs your attention.  "
        		+ "Please login to your account at www.studentsuccessplan.org to review "
        		+ "and complete the assigned item.<br/><br/>The following item is due for "
        		+ "review:<br/>Challenge: ${task.challenge.name}<br/>"
        		+ "Referral: ${task.challengeReferral.name}<br/>Due Date: ${dueDateFormatted}");
 
        MESSAGE_TEMPLATE_EACONFADV = new JSONObject();
        MESSAGE_TEMPLATE_EACONFADV.put("id", "b528c1ac-6104-435b-ae62-08eb4f7ee3fc");
        MESSAGE_TEMPLATE_EACONFADV.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EACONFADV.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EACONFADV.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EACONFADV.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EACONFADV.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_EACONFADV.put("name", "Early Alert Confirmation to Advisor");
        MESSAGE_TEMPLATE_EACONFADV.put("description", "Message sent from system to advisor when faculty member sends " +
                "an alert.");
        MESSAGE_TEMPLATE_EACONFADV.put("subject", "Student#if( $termToRepresentEarlyAlert ) " +
                "$termToRepresentEarlyAlert#end Notice - $earlyAlert.person.firstName " +
                "$earlyAlert.person.lastName#if( $earlyAlert.courseName) : $earlyAlert.courseName#end");
        MESSAGE_TEMPLATE_EACONFADV.put("body", "<html><body> <p><b>STUDENT $!termToRepresentEarlyAlert SYSTEM MESSAGE" +
                "</b></p>  <table> <tr><th>Student Name</th><td>$earlyAlert.person.firstName " +
                "$earlyAlert.person.lastName</td></tr> <tr><th>Student ID</th><td>$!earlyAlert.person.schoolId</td>" +
                "</tr> <tr><th>Student Phone</th><td>$!earlyAlert.person.homePhone</td></tr> <tr><th>Student Email" +
                "</th><td>$earlyAlert.person.primaryEmailAddress</td></tr> <tr><th>Address</th><td>#if( " +
                "$earlyAlert.person.addressLine1 )$earlyAlert.person.addressLine1, #end#if( " +
                "$earlyAlert.person.addressLine2 )$earlyAlert.person.addressLine2, #end#if( $earlyAlert.person.city )" +
                "$earlyAlert.person.city, #end#if( $earlyAlert.person.state )$earlyAlert.person.state, " +
                "#end$!earlyAlert.person.zipCode</td></tr> <tr><th>In the class</th><td>$!earlyAlert.courseName</td>" +
                "</tr> <tr><th>Instructor</th><td>$earlyAlert.createdBy.firstName $earlyAlert.createdBy.lastName</td>" +
                "</tr> <tr><th>Campus</th><td>$earlyAlert.campus.name</td></tr> <tr><th>Instructor Office Location</th>" +
                "<td>$!earlyAlert.createdBy.staffDetails.officeLocation</td></tr> <tr><th>Instructor Email</th><td>" +
                "$earlyAlert.createdBy.primaryEmailAddress</td></tr> <tr><th>Instructor Phone</th><td>" +
                "$!earlyAlert.createdBy.workPhone</td></tr> <tr><th>Advisor</th><td>$earlyAlert.person.coach.firstName " +
                "$earlyAlert.person.coach.lastName</td></tr> </table>  " +
                "<p><b>Student has been identified by his/her instructor for the following reason:</b></p> " +
                "<ul> #foreach( $earlyAlertReason in $earlyAlert.earlyAlertReasonIds ) <li>$earlyAlertReason.name</li> " +
                "#end </ul>  <p><b>The faculty suggestions are:</b></p> <ul> #foreach( $earlyAlertSuggestion in " +
                "$earlyAlert.earlyAlertSuggestionIds ) <li>$earlyAlertSuggestion.name</li> #end </ul>  " +
                "#if( $earlyAlert.comment ) <p> ******************* INSTRUCTOR COMMENTS ******************* <br /> " +
                "$earlyAlert.comment <br /> ******************* INSTRUCTOR COMMENTS ******************* <p> #end  " +
                "<p>To access additional information or respond to this alert, log-in#if( $applicationTitle ) to " +
                "$applicationTitle#end#if( $termForEarlyAlert ) and view the student&apos;s " +
                "$termForEarlyAlert Tool#end.  $!linkToSSP </p>  <p>Email generated by $!applicationTitle " +
                "$!termForEarlyAlert</p> </body></html>");


        MESSAGE_TEMPLATE_ROWS = new JSONArray();
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_ACTNPLN);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_ACTSTEP);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_CCE);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_CUST);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_EACONFADV);

    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsMessageTemplateReference() {
        final JSONObject testPostPutNegative = copyOfExpectedObjectAtIndex(0, MESSAGE_TEMPLATE_ROWS);
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(MESSAGE_TEMPLATE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMessageTemplateReferenceAllBody() {

        ResponseSpecification spec =
                expect()
                        .contentType("application/json")
                        .statusCode(200)
                        .log().ifError()
                        .body("results", equalTo(17))
                        .and()
                        .body("success", equalTo("true"))
                        .and()
                        .body("rows", hasSize(17))
                        .and();

        spec = expectListResponseObjectAtIndex(spec, 0, MESSAGE_TEMPLATE_ROWS);
        spec.when().get(MESSAGE_TEMPLATE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMessageTemplateReferenceSingleItemBody() {

        testSingleItemResponseBody(MESSAGE_TEMPLATE_PATH, MESSAGE_TEMPLATE_EACONFADV);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsMessageTemplateReference() {

       testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, MESSAGE_TEMPLATE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMessageTemplateReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = copyOfExpectedObjectAtIndex(1, MESSAGE_TEMPLATE_ROWS);
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(MESSAGE_TEMPLATE_PATH, expectedStringFieldValueAtIndex("id", 2, MESSAGE_TEMPLATE_ROWS), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMessageTemplateReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = copyOfExpectedObjectAtIndex(3, MESSAGE_TEMPLATE_ROWS);
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = expectedObjectAtIndex(4, MESSAGE_TEMPLATE_ROWS);

        referenceNegativeSupportedMethodTest(MESSAGE_TEMPLATE_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
