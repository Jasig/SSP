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


public class MessageTemplateIT extends AbstractReferenceTest {

    private static final String MESSAGE_TEMPLATE_PATH = REFERENCE_PATH + "messageTemplate";

    private static final JSONObject MESSAGE_TEMPLATE_CCE;
    private static final JSONObject MESSAGE_TEMPLATE_CUST;
    private static final JSONObject MESSAGE_TEMPLATE_ACTNPLN;
    private static final JSONObject MESSAGE_TEMPLATE_NEW;
    private static final JSONObject MESSAGE_TEMPLATE_MAP;
    private static final JSONObject MESSAGE_TEMPLATE_ACTSTEP;
    private static final JSONObject MESSAGE_TEMPLATE_EAJOURN;
    private static final JSONObject MESSAGE_TEMPLATE_EACONFADV;
    private static final JSONObject MESSAGE_TEMPLATE_EATOSTUD;
    private static final JSONObject MESSAGE_TEMPLATE_EACONF;
    private static final JSONObject MESSAGE_TEMPLATE_EARSPNSE;
    private static final JSONObject MESSAGE_TEMPLATE_MAPFULL;

    private static final JSONArray MESSAGE_TEMPLATE_ROWS;
    private static final JSONObject MESSAGE_TEMPLATE_RESPONSE;

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

        MESSAGE_TEMPLATE_NEW = new JSONObject();
        MESSAGE_TEMPLATE_NEW.put("id", "9d3ce5b1-e27d-40c8-8f45-abcb1bccf3b0");
        MESSAGE_TEMPLATE_NEW.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_NEW.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_NEW.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_NEW.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_NEW.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_NEW.put("name", "New Student Intake Task Email");
        MESSAGE_TEMPLATE_NEW.put("description", "New Student Intake Task Email");
        MESSAGE_TEMPLATE_NEW.put("subject", "Student Intake Form Request");
        MESSAGE_TEMPLATE_NEW.put("body", "<html><body><p>Hello ${fullName}!</p><p>Your Coach or Advisor has asked " +
                "that you complete a Task online, the Student Intake form.  ${description}  The form is a series of " +
                "questions that should only take a few minutes to complete.  The information collected will assist us " +
                "in providing you with the best services and advice that we can.</p> <p>Please login with your college" +
                " username and password at the following URL:  <a href=\"https://www.studentsuccessplan.org\">" +
                "https://www.studentsuccessplan.org</a></p><p>The Student Intake link will appear in the Task list on " +
                "the right side of the screen.</p><p>Please complete this task by ${dueDateFormatted}</p><p>Thank you" +
                " for your time and cooperation.  We look forward to providing you great service!</p><p>-College Team" +
                "</p><p>If you have questions, please contact your Coach or Advisor directly. " +
                "This is an automated message, please do not use the reply option.</p></body></html>");

        MESSAGE_TEMPLATE_MAP = new JSONObject();
        MESSAGE_TEMPLATE_MAP.put("id", "aa2e0356-46df-4acd-ab3e-b96a6aa943d3");
        MESSAGE_TEMPLATE_MAP.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_MAP.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_MAP.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_MAP.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_MAP.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_MAP.put("name", "Map Plan Printout");
        MESSAGE_TEMPLATE_MAP.put("description", "Map Plan Printout");
        MESSAGE_TEMPLATE_MAP.put("subject", "Here is your MAP Plan");
        MESSAGE_TEMPLATE_MAP.put("body", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" " +
                "\"http://www.w3.org/TR/html4/loose.dtd\"> <html> <head> <style> @media print {   " +
                "table { page-break-after:auto }   tr    { page-break-inside:avoid; page-break-after:auto; " +
                "page-break-before:auto }   td    { page-break-inside:avoid; page-break-after:auto; " +
                "page-break-before:auto }   thead { display:table-header-group }   tfoot { " +
                "display:table-footer-group } } </style> <meta http-equiv=\"Content-Type\" content=\"text/html; " +
                "charset=iso-8859-1\"> <title>$title</title> </head>  <body style=\"font-family: Tahoma; font-size: " +
                "12px;\"><div> <p style=\"padding-left:20px\"> $!studentFullName</br> $!studentEmail</br> School Id: " +
                "$!studentSchoolId </p> </div> <div> <p style=\"text-align:center\"> $title </p> </div> " +
                "<div style=\"position:relative;left:100px\"> \t#foreach($termCourse in $termCourses)  \t\t" +
                "<table cellpadding=\"0\" cellspacing=\"0\" style=\"background-color:white;width:80%;" +
                "border-collapse:collapse;\"> \t\t\t<caption  style=\"font-size:small;text-align:left\">" +
                "$termCourse.term.name</caption> \t\t\t<thead> \t\t\t\t<tr > \t\t\t\t\t<th style=\"text-align:center;" +
                "border:1px solid black;width:325\"> \t\t\t\t\t\tCourse<br/>Number \t\t\t\t\t</th> \t\t\t\t\t" +
                "<th style=\"text-align:center;border:1px solid black;width:60%\"> \t\t\t\t\t\tCourse Title " +
                "\t\t\t\t\t</th> \t\t\t\t\t<th style=\"text-align:center;border:1px solid black;width:15%\"> " +
                "\t\t\t\t\t\tCredit <br/> Hours \t\t\t\t\t</th> \t\t\t\t</tr> \t\t\t</thead> \t\t\t" +
                "#foreach($course in $termCourse.courses) \t\t\t<tr> \t\t\t\t<td style=\"text-align:center;border:1px" +
                " solid black;width:25%;padding-bottom:5px; padding-top:5px;padding-left:5px\"> \t\t\t\t\t" +
                "$!course.formattedCourse \t\t\t\t</td> \t\t\t\t<td style=\"text-align:left;border:1px solid black;" +
                "width:60%;padding-bottom:5px; padding-top:5px;padding-left:5px\"> \t\t\t\t\t \t\t\t\t\t" +
                "$!course.courseTitle \t\t\t\t</td> \t\t\t\t<td style=\"text-align:center;border:1px solid black;" +
                "width:15%;padding-bottom:5px; padding-top:5px;padding-left:5px\"> \t\t\t\t\t$!course.creditHours" +
                " \t\t\t\t</td> \t\t\t</tr> \t\t\t<tr> \t\t\t#end \t\t\t<tr> \t\t\t<td colspan=\"3\" " +
                "style=\"height:35px; padding-bottom:5px; padding-left:80px;vertical-align:bottom\"> \t\t\t \t" +
                "<b>Total Credit Hours:</b>  $!termCourse.totalCreditHours \t\t\t</td> \t\t\t</tr> \t\t\t<tr> \t\t\t" +
                "<td colspan=\"3\" style=\"height:15px\"> \t\t\t<br/> \t\t\t</td> \t\t\t</tr> \t\t</table> \t#end \t" +
                "<table style=\"width:80%\"> \t\t<tbody> \t\t<tr> \t\t<td style=\"text-align:center\"> \t\t" +
                "<b>CREDIT HOURS FOR PLAN: $!totalPlanHours</b> \t\t</td> \t\t</tr> \t\t</tbody> \t</table> </div> \t" +
                "<div> \t<p style=\"padding-left:20px\"> \t<b>If I have questions or concerns about my plan I will " +
                "contact: \t</p> \t<p style=\"padding-left:20px\"> \t$!coachFullName</br> \t$!coachPhone2</br> \t" +
                "$!coachEmail \t</p> \t</div> \t</body> \t</html>");

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
        MESSAGE_TEMPLATE_ACTSTEP.put("body", "${task.person.firstName},<br/>An Action Item identified through the " +
                "Community College Resources application needs your attention.  Please login to your account at " +
                "www.studentsuccessplan.org to review and complete the assigned item.<br/><br/>The following item " +
                "is due for review:<br/>Challenge: ${task.challenge.name}<br/>Referral: ${task.challengeReferral.name}" +
                "<br/>Due Date: ${dueDateFormatted}");

        MESSAGE_TEMPLATE_EAJOURN = new JSONObject();
        MESSAGE_TEMPLATE_EAJOURN.put("id", "b528c1ac-6104-435b-ae62-08eb4f7ee2f9");
        MESSAGE_TEMPLATE_EAJOURN.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EAJOURN.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EAJOURN.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EAJOURN.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EAJOURN.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_EAJOURN.put("name", "Early Alert Journal Entry");
        MESSAGE_TEMPLATE_EAJOURN.put("description", "");
        MESSAGE_TEMPLATE_EAJOURN.put("subject", "Student $termToRepresentEarlyAlert Journal Entry - " +
                "$earlyAlert.person.firstName $earlyAlert.person.lastName #if( $earlyAlert.courseName) : " +
                "$earlyAlert.courseName#end");
        MESSAGE_TEMPLATE_EAJOURN.put("body", "Summary: Early Alert Course: $earlyAlert.courseName Early Alert Created " +
                "Date: $earlyAlert.createdDate  Response Created by: $earlyAlertResponse.createdBy.firstName " +
                "$earlyAlertResponse.createdBy.lastName Response Details: Outcome: " +
                "$!earlyAlertResponse.earlyAlertOutcome.name #foreach( $earlyAlertOutreach in " +
                "$earlyAlertResponse.earlyAlertOutreachIds ) Outreach: $earlyAlertOutreach.name #end " +
                "#foreach( $earlyAlertReferral in $earlyAlertResponse.earlyAlertReferralIds ) " +
                "Referral: $earlyAlertReferral.name #end Comment: $earlyAlertResponse.comment");

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

        MESSAGE_TEMPLATE_EATOSTUD = new JSONObject();
        MESSAGE_TEMPLATE_EATOSTUD.put("id", "b528c1ac-6104-435b-ae62-08eb4f8ef40e");
        MESSAGE_TEMPLATE_EATOSTUD.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EATOSTUD.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EATOSTUD.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EATOSTUD.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EATOSTUD.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_EATOSTUD.put("name", "Early Alert Sent to Student");
        MESSAGE_TEMPLATE_EATOSTUD.put("description", "Message sent from system to student when faculty member sends " +
                "an early alert.");
        MESSAGE_TEMPLATE_EATOSTUD.put("subject", "Student $TermToRepresentEarlyAlert Notice - $FirstName> $LastName : " +
                "$CourseName");
        MESSAGE_TEMPLATE_EATOSTUD.put("body", "<html><body> <p>Dear $earlyAlert.person.firstName " +
                "$earlyAlert.person.lastName</p>  <p>Your instructor for #if( $earlyAlert.courseName )" +
                "$earlyAlert.courseName#else<COURSE NAME UNAVAILABLE>#end notified me that you are experiencing " +
                "issues that might affect your success#if( $institutionName ) at $institutionName#end.  " +
                "An academic advisor, counselor or coach will contact you soon to discuss your situation. " +
                "In the meantime, here are a few resources which may help you find academic success.</p>  " +
                "<table><tr><th>Instructor</th><td>$earlyAlert.createdBy.firstName $earlyAlert.createdBy.lastName" +
                "#if( $earlyAlert.createdBy.workPhone )</td><tr> <tr><th>Phone</th>" +
                "<td>$earlyAlert.createdBy.workPhone#end</td><tr> <tr><th>Email</th>" +
                "<td>$earlyAlert.createdBy.primaryEmailAddress</td><tr> </table>  " +
                "<p>The Tutoring and Learning Center (Library: 555-4506) provides professional tutoring in the subject" +
                " areas of English grammar and writing, basic math and study skills. The Center also provides " +
                "additional quiet computer areas for students, online resources, and supplemental learning materials. " +
                "Walk-ins are available during open hours. Call for schedule.</p>  <p>Tutorial Services " +
                "(Library: 555-2792) provides free educational assistance for students who are enrolled in most 100 " +
                "level courses (including DEV and some 200 level classes). Student tutors provide individual tutoring" +
                " sessions by appointment.</p>  <p>The Writing Center (Library: 555-5106) provides a comfortable " +
                "learning environment for classroom instruction and tutorial assistance for writing.</p>  " +
                "<p>The Math Lab (Building 1, Room 315: 555-2286) provides tutoring and homework help for students in" +
                " MAT classes. Walk-ins are available during open hours.</p>  <p>COPE is a unique series of workshops" +
                " designed to help students Conquer some of the Obstacles that can Prevent the achievement of their" +
                " Educational goals. A schedule of topics is available in 10-424 or online at " +
                "http://www.studentsuccessplan.org Contact Anthony at 555-2752 for handouts of past or future " +
                "workshops.</p>  <p>Information on Dropping: Before withdrawing from any or all classes, consult an" +
                " academic advisor. If you are using financial aid to pay for your tuition or books, contact the " +
                "Financial Aid & Scholarships Office: Building 10, 3rd Floor, 555 555-3000.</p>  " +
                "<p>We value you as a student #if( $institutionName ) at $institutionName#end and want to support" +
                " your success. If you have not been contacted by someone within a week, please feel free to speak" +
                " with me.</p>  <p>Sincerely,</p>  <p>$earlyAlert.person.coach.firstName " +
                "$earlyAlert.person.coach.lastName <br />$earlyAlert.person.coach.title#if( " +
                "$earlyAlert.person.coach.staffDetails.officeLocation ) " +
                "<br />$earlyAlert.person.coach.officeLocation#end#if( $earlyAlert.person.coach.workPhone ) " +
                "<br />$earlyAlert.person.coach.workPhone#end <br />$earlyAlert.person.coach.primaryEmailAddress</p>  " +
                "<p>Email generated by $!institutionName $!applicationTitle $!termToRepresentEarlyAlert</p>  " +
                "<p>This is a system generated email. Please do not reply to this confirmation.</p> </body></html>");

        MESSAGE_TEMPLATE_EACONF = new JSONObject();
        MESSAGE_TEMPLATE_EACONF.put("id", "b528c1ac-6104-435b-ae62-09eb5f8ef55f");
        MESSAGE_TEMPLATE_EACONF.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EACONF.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EACONF.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EACONF.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EACONF.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_EACONF.put("name", "Early Alert Confirmation to Faculty");
        MESSAGE_TEMPLATE_EACONF.put("description", "Message sent from system to faculty member when faculty member " +
                "sends an alert.");
        MESSAGE_TEMPLATE_EACONF.put("subject", "Student$!termToRepresentEarlyAlert Confirmation Notice - " +
                "$earlyAlert.person.firstName $earlyAlert.person.lastName #if( $earlyAlert.courseName) : " +
                "$earlyAlert.courseName#end");
        MESSAGE_TEMPLATE_EACONF.put("body", "<html><body> <p><b>STUDENT $!termToRepresentEarlyAlert SYSTEM MESSAGE</b>" +
                "</p> <table> <tr><th>Student Name</th><td>$earlyAlert.person.firstName $earlyAlert.person.lastName" +
                "</td></tr> <tr><th>Student ID</th><td>$!earlyAlert.person.schoolId</td></tr> <tr><th>Student Phone</th>" +
                "<td>$!earlyAlert.person.homePhone</td></tr> <tr><th>Student Email</th>" +
                "<td>$earlyAlert.person.primaryEmailAddress</td></tr> <tr><th>Address</th>" +
                "<td>#if( $earlyAlert.person.addressLine1 )$earlyAlert.person.addressLine1, " +
                "#end#if( $earlyAlert.person.addressLine2 )$earlyAlert.person.addressLine2, #end#if( " +
                "$earlyAlert.person.city )$earlyAlert.person.city, #end#if( $earlyAlert.person.state )" +
                "$earlyAlert.person.state, #end$!earlyAlert.person.zipCode</td></tr> <tr><th>In the class</th><td" +
                ">$!earlyAlert.courseName</td></tr> <tr><th>Instructor</th><td>$earlyAlert.createdBy.firstName " +
                "$earlyAlert.createdBy.lastName</td></tr> <tr><th>Campus</th><td>$!earlyAlert.campus.name</td></tr> " +
                "<tr><th>Instructor Office Location</th><td>$!earlyAlert.createdBy.staffDetails.officeLocation</td></tr>" +
                " <tr><th>Instructor Email</th><td>$earlyAlert.createdBy.primaryEmailAddress</td></tr> " +
                "<tr><th>Instructor Phone</th><td>$!earlyAlert.createdBy.workPhone</td></tr> <tr><th>Advisor</th>" +
                "<td>$earlyAlert.person.coach.firstName $earlyAlert.person.coach.lastName</td></tr> </table> " +
                "<p>Student has been identified by his/her instructor for the following reason:</ul>  " +
                "<ul> #foreach( $earlyAlertReason in $earlyAlert.earlyAlertReasonIds ) <li>$earlyAlertReason.name</li>" +
                " #end </ul>  <p><b>The faculty suggestions are</b></p> <ul> #foreach( $earlyAlertSuggestion in " +
                "$earlyAlert.earlyAlertSuggestionIds ) <li>$earlyAlertSuggestion.name</li> #end </ul>  " +
                "#if( $earlyAlert.comment ) <p> ******************* INSTRUCTOR COMMENTS ******************* <br /> " +
                "$earlyAlert.comment <br /> ******************* INSTRUCTOR COMMENTS ******************* </p> #end   " +
                "<p>Email generated by $!institutionName $!applicationTitle $!termToRepresentEarlyAlert</p>  " +
                "<p>This is a system generated email. Please do not reply to this confirmation.</p> </body></html>");

        MESSAGE_TEMPLATE_EARSPNSE = new JSONObject();
        MESSAGE_TEMPLATE_EARSPNSE.put("id", "b528c1ac-6104-435b-ae62-09fb5f9ef680");
        MESSAGE_TEMPLATE_EARSPNSE.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EARSPNSE.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EARSPNSE.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_EARSPNSE.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_EARSPNSE.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_EARSPNSE.put("name", "Early Alert Response to Faculty from Coach");
        MESSAGE_TEMPLATE_EARSPNSE.put("description", "Message sent to faculty member (creator of the alert) when the " +
                "Advisor responds via SSP.");
        MESSAGE_TEMPLATE_EARSPNSE.put("subject", "Student$!termToRepresentEarlyAlert Advisor Reply - $earlyAlert.person." +
                "firstName $earlyAlert.person.lastName #if( $earlyAlert.courseName) : $earlyAlert.courseName#end");
        MESSAGE_TEMPLATE_EARSPNSE.put("body", "<html><body> <p><b>STUDENT $!termToRepresentEarlyAlert SYSTEM MESSAGE" +
                "</b></p>  <p>This email is in response to an early alert notice initiated on $earlyAlert.createdDate " +
                "for the following student:</p>  <table> <tr><th>Student Name</th><td>$earlyAlert.person.firstName " +
                "$earlyAlert.person.lastName</td></tr> <tr><th>Student ID</th><td>$!earlyAlert.person.schoolId</td></tr> " +
                "<tr><th>In the class</th><td>$!earlyAlert.courseName</td></tr> <tr><th>Instructor</th>" +
                "<td>$earlyAlert.createdBy.firstName $earlyAlert.createdBy.lastName</td></tr> <tr><th>Advisor(s)</th>" +
                "<td>$earlyAlert.person.coach.firstName $earlyAlert.person.coach.lastName</td></tr> </table>  " +
                "#if( $earlyAlert.closedDate ) <p>This incident was addressed and CLOSED on $earlyAlert.closedDate. " +
                "The final outcome for this incident was:</p> #else <p>This incident was addressed on " +
                "$earlyAlertResponse.modifiedDate. The outcome was:</p> #end  " +
                "<p>$!earlyAlertResponse.earlyAlertOutcome.name</p>  " +
                "#if( $earlyAlertResponse.earlyAlertOutcomeOtherDescription ) " +
                "<p>$earlyAlertResponse.earlyAlertOutcomeOtherDescription</p> #end  " +
                "<p>The student has been referred by his/her counselor to the following departments or services based" +
                " upon early alert status:</p> <ul> #foreach( $earlyAlertReferral in " +
                "$earlyAlertResponse.earlyAlertReferralIds ) <li>$earlyAlertReferral.name</li> #end </ul>  " +
                "#if( $earlyAlertResponse.comment ) <p>******************* INSTRUCTOR COMMENTS ******************* " +
                "<br /> $earlyAlertResponse.comment <br /> ******************* INSTRUCTOR COMMENTS *******************</p>" +
                " #end  <p>Email generated by $!institutionName $!applicationTitle $!termToRepresentEarlyAlert</p> " +
                "</body></html>");

        MESSAGE_TEMPLATE_MAPFULL = new JSONObject();
        MESSAGE_TEMPLATE_MAPFULL.put("id", "df47a4b0-b666-11e2-9e96-0800200c9a66");
        MESSAGE_TEMPLATE_MAPFULL.put("createdDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_MAPFULL.put("createdBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_MAPFULL.put("modifiedDate", getDefaultCreatedModifiedByDate());
        MESSAGE_TEMPLATE_MAPFULL.put("modifiedBy", getDefaultCreatedModifiedBy());
        MESSAGE_TEMPLATE_MAPFULL.put("objectStatus", "ACTIVE");
        MESSAGE_TEMPLATE_MAPFULL.put("name", "Map Plan Full Printout");
        MESSAGE_TEMPLATE_MAPFULL.put("description", "Map Plan FULL Printout");
        MESSAGE_TEMPLATE_MAPFULL.put("subject", "Here is your MAP Plan");
        MESSAGE_TEMPLATE_MAPFULL.put("body", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"" +
                "http://www.w3.org/TR/html4/loose.dtd\"> <html> <head> <style> @media print {   " +
                "table { page-break-after:auto }   tr    { " +
                "page-break-inside:avoid; page-break-after:auto; page-break-before:auto }   td    { " +
                "page-break-inside:avoid; page-break-after:auto; page-break-before:auto }   thead { " +
                "display:table-header-group }   tfoot { display:table-footer-group } } </style> " +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\"> " +
                "<title>$title</title> </head>  <body style=\"font-family: Tahoma; font-size: 12px;\"> <div> " +
                "<div style=\"width: 980px; height: 120px;\"> \t<span style=\"float: left;\">" +
                "<img src=\"$!baseUrl/ssp/images/institution.gif\" width=\"113\" height=\"119\" alt=\"$institution\" " +
                "border=\"0\"></span> \t<span style=\"float: left; padding: 6px 0px 0px 12px; font-family: Tahoma; " +
                "font-size: 22px;\">$institution<br>Address<br>City, State 55555<br>1.480.558.2400</span> \t" +
                "<span style=\"float: right; padding: 6px 12px 0px 0px; font-family: Tahoma; font-size: 18px;\">" +
                "$createdDateFormatted<br><br>$studentFullName<br>$studentEmail<br>School Id: $studentSchoolId<br></span>" +
                " </div>  #if($includeHeaderFooter) <div style=\"width: 980px; font-family: Tahoma; font-size: 14px;\">" +
                "  \t<div style=\"padding: 6px 12px 0px 12px;\">Below you will see the Academic Plan (MAP) that you " +
                "have developed in cooperation with an Academic/Faculty Advisor. Please contact your \t" +
                "Academic/Faculty Advisor or Counselor if you have any questions or need to make modifications to MAP.</div>" +
                " \t<div style=\"padding: 6px 12px 0px 12px;\"><strong>Please Note:</strong> Information from the" +
                " department(s) regarding planned course offerings was used by the advisor to recommend your courses. " +
                "Courses \trecommended on your MAP may not be available as planned or at the times, locations, and/or " +
                "delivery methods you prefer. \tCourses recommended on your MAP do not secure an open spot in a course " +
                "or section that you desire; therefore, you are encouraged to register early.</div> </div> #end </div> " +
                "<div style=\"position:relative;left:10px;top:20px;width: 980px\"> \t" +
                "#foreach($termCourse in $termCourses)  \t\t<table cellpadding=\"0\" cellspacing=\"0\" " +
                "style=\"table-layout: fixed;margin-top:15px;background-color:white;width:100%;border:1px solid #ABABAB\">" +
                " \t\t\t<tr> \t\t\t\t<td style=\"font-size:small;text-align:left;padding-left:5px;color:#ABABAB\">" +
                "<strong>$termCourse.term.name</strong></td> \t\t\t\t<td style=\"font-size:small;text-align:right;" +
                "padding-right:5px;color:#ABABAB\"><strong>Term Cr. Hrs: $termCourse.totalCreditHours</strong> </td> " +
                "\t\t\t</tr> \t\t\t \t\t\t#foreach($course in $termCourse.courses) \t\t\t\t#set ($odd = " +
                "$foreach.count % 2) \t\t\t\t#if( $odd > 0 ) \t\t       \t\t<tr> \t\t    \t#end \t\t\t \t\t\t\t" +
                "<td valign=\"top\" style=\"width:50%;word-wrap:break-word;text-align:left;padding-bottom:5px; " +
                "padding-top:5px;padding-left:10px; padding-right:5px\"> \t\t\t\t\t<strong>$course.formattedCourse - " +
                "$course.courseTitle $course.creditHours Cr. Hrs.</strong><br> \t\t\t\t\t#if(\"" +
                "$!course.planToOffer\" != \"\")PLAN TO OFFER: $course.planToOffer #end #if($includeCourseDescription)" +
                "$!course.courseDescription #end \t\t\t\t\t \t\t\t\t\t#if(\"$!course.contactNotes\" != \"\" || \"" +
                "$!course.studentNotes\" != \"\") \t\t\t\t\t\t<p style=\"font-size:10px; margin-top:5px\"> \t\t\t\t\t\t" +
                "#if(!$isPrivate && \"$!course.contactNotes\" != \"\") \t\t\t\t\t\t\t<span style=\"color:#ABABAB\">" +
                "<strong>Advisor Notes:</strong></span> $course.contactNotes \t\t\t\t\t\t#end \t\t\t\t\t\t#if(" +
                "!$isPrivate && \"$!course.contactNotes\" != \"\" && \"$!course.studentNotes\" != \"\") \t\t\t\t\t\t<br/>" +
                "<br/> \t\t\t\t\t\t#end \t\t\t\t\t\t#if(\"$!course.studentNotes\" != \"\") \t\t\t\t\t\t\t<span style=\"" +
                "color:#ABABAB\"><strong>Student Notes:</strong></span> $course.studentNotes \t\t\t\t\t\t#end " +
                "\t\t\t\t\t#end \t\t\t\t</td> \t\t\t\t\t \t\t\t\t#if( $odd == 0 || $foreach.last) \t\t\t       " +
                "<tr> \t\t\t\t#end \t\t\t#end \t\t\t#if($includeTotalTimeExpected) \t\t\t<tr> \t\t\t<td colspan=\"2\" " +
                "style=\"height:15px;border-top:1px solid #ABABAB;padding-left:10px\"> \t\t\t\t#set( $timeExpected = " +
                "$termCourse.totalCreditHours * 2 ) \t\t\tTime expected outside Class: $timeExpected (hours/week) \t\t\t</td>" +
                " \t\t\t</tr> \t\t\t#end \t\t\t#if(\"$!termCourse.contactNotes\" != \"\" || \"" +
                "$!termCourse.studentNotes\" != \"\") \t\t\t<tr> \t\t\t\t<td colspan=\"2\" style=\"padding-top: 5px; " +
                "padding-bottom: 5px; padding-left:10px; padding-right:10px;border-top:1px solid #ABABAB;\"> \t\t\t\t\t" +
                "#if(!$isPrivate && \"$termCourse.contactNotes\" != \"\") \t\t\t\t\t<span style=\"color:#ABABAB\">" +
                "<strong>Advisor Notes:</strong></span> $termCourse.contactNotes \t\t\t\t\t#end \t\t\t\t\t#if(!$isPrivate" +
                " && \"$termCourse.contactNotes\" != \"\" && \"$termCourse.studentNotes\" != \"\") \t\t\t\t\t<br/><br/> " +
                "\t\t\t\t\t#end \t\t\t\t\t#if(\"$termCourse.studentNotes\" != \"\") \t\t\t\t\t<span style=\"color:#ABABAB\">" +
                "<strong>Student Notes:</strong></span> $termCourse.studentNotes \t\t\t\t\t#end \t\t\t\t</td> \t\t\t</tr>" +
                " \t\t\t#end \t\t</table> \t#end </div> \t<div style=\"width: 980px\"> \t\t<div style=\"" +
                "left:10px;margin-top:30px\"> \t\t<p style=\"text-align:center; color:#ABABAB;\"> \t\t" +
                "<strong>Total MAP Credit HRS: TERM:</strong> $totalPlanHours <strong>DEV:</strong> " +
                "$totalPlanDevHours \t\t</p> \t\t</div> \t#if($includeHeaderFooter) \t<div style=\"width: 980px\"> \t\t" +
                "<div style=\"left:10px;margin-top:30px\"> \t\t<p style=\"text-align:left; color:#000000;\"> \t\t" +
                "<strong>Attempted Hours - </strong> $!attemptedHours \t\t</p> \t\t<p style=\"text-align:left; " +
                "color:#000000;\"> \t\t<strong>Completed Hours - </strong> $!completedHours \t\t</p> \t\t" +
                "<p style=\"text-align:left; color:#000000;\"> \t\t<strong>Current Completion Rate - </strong> " +
                "$!completionRage \t\t</p> \t\t<p style=\"text-align:left; color:#000000;text-indent:15px;\"> \t\t" +
                "<strong>Number of hours needed to earn 67% completion rate - </strong> $!neededFor67PercentCompletion" +
                " \t\t</p> \t\t<p style=\"text-align:left; color:#000000;\"> \t\t<strong>Financial Aid Cum GPA - " +
                "</strong> $!financialAidGpa \t\t</p> \t\t<p style=\"text-align:left; color:#000000;text-indent:15px;\">" +
                " \t\t<strong>Number of needed to earn a 2.0 Cum GPA with all B - </strong> $!hoursNeededB \t\t</p> \t</div>" +
                " \t#end \t#if(\"$!planContactNotes\" != \"\" || \"$!planStudentNotes\" != \"\") \t\t<p style=\"" +
                "padding-left:20px;margin-top:15px\"> \t\t#if(!$isPrivate && \"$!planContactNotes\" != \"\") \t\t\t" +
                "<span style=\"color:#ABABAB\"><strong>Advisor Notes:</strong></span>&nbsp;&nbsp;&nbsp;$!planContactNotes" +
                " \t\t#end \t\t#if(!$isPrivate && \"$!planContactNotes\" != \"\" && \"$!planStudentNotes\" != \"\") \t\t" +
                "<br/><br/> \t\t#end \t\t#if(\"$!planStudentNotes\" != \"\") \t\t\t<span style=\"color:#ABABAB\">" +
                "<strong>Student notes:</strong></span>&nbsp;&nbsp;&nbsp;&nbsp;$!planStudentNotes \t\t#end \t\t</p> " +
                "\t#end \t<p style=\"padding-left:20px;font-size:small;margin-top:30px;\"> \t<strong>If I have questions" +
                " or concerns about my plan I will contact:</strong> \t</p> \t<p style=\"padding-left:20px;" +
                "padding-left:20px;font-size:small\"> \t\t<strong> \t#if(\"$!contactName\" != \"\") $!contactTitle " +
                "$contactName #else $coachFullName #end</br> \t#if(\"$!contactPhone\" != \"\") $contactPhone #else " +
                "$coachPhone2 #end</br> \t#if(\"$!contactEmail\" != \"\") $contactEmail #else $coachEmail #end \t</strong>" +
                " \t</p> \t #if($includeHeaderFooter) \t<p style=\"padding-left:20px;font-size:small\"> \t" +
                "I realize that agreeing to this plan and reaching my educational goals is my choice, and I understand " +
                "that deviation from this plan may have negative academic \tor financial impacts on me as a student " +
                "reaching my stated goals. I will contact my Academic Advisor or Counselor if I have any questions or " +
                "need to make \tmodifications to my Academic Plan. \t</p> \t<div style=\"padding: 106px 12px 0px 12px;\">" +
                " \t\t<span style=\"border-top: 1px solid; width: 350px; float: left;\">Student Signature</span> \t\t" +
                "<span style=\"width: 90px; font-family: Tahoma; font-size: 14px; float: left;\">&nbsp;</span> \t\t" +
                "<span style=\"border-top: 1px solid; width: 350px; float: left;\">Advisor/Counselor Signature</span> \t</div> " +
                "\t#end \t</div> </body></html>");

        MESSAGE_TEMPLATE_ROWS = new JSONArray();
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_ACTNPLN);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_ACTSTEP);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_CCE);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_CUST);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_EACONFADV);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_EACONF);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_EAJOURN);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_EARSPNSE);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_EATOSTUD);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_MAPFULL);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_MAP);
        MESSAGE_TEMPLATE_ROWS.add(MESSAGE_TEMPLATE_NEW);

        MESSAGE_TEMPLATE_RESPONSE = new JSONObject();
        MESSAGE_TEMPLATE_RESPONSE.put("success", "true");
        MESSAGE_TEMPLATE_RESPONSE.put("message", "");
        MESSAGE_TEMPLATE_RESPONSE.put("results", MESSAGE_TEMPLATE_ROWS.size());
        MESSAGE_TEMPLATE_RESPONSE.put("rows", MESSAGE_TEMPLATE_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsMessageTemplateReference() {
        final JSONObject testPostPutNegative = (JSONObject)MESSAGE_TEMPLATE_CCE.clone();
        testPostPutNegative.put("name", "testPostUnAuth");

        referenceAuthenticationControlledMethodNegativeTest(MESSAGE_TEMPLATE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMessageTemplateReferenceAllBody() {

        testResponseBody(MESSAGE_TEMPLATE_PATH, MESSAGE_TEMPLATE_RESPONSE);
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
        final JSONObject testPostPutPositive = (JSONObject) MESSAGE_TEMPLATE_MAPFULL.clone();
        testPostPutPositive.put("name", "testPostPositive");

        referencePositiveSupportedMethodTest(MESSAGE_TEMPLATE_PATH, MESSAGE_TEMPLATE_ACTSTEP.get("id").toString(), testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testMessageTemplateReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) MESSAGE_TEMPLATE_EAJOURN.clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = MESSAGE_TEMPLATE_NEW;

        referenceNegativeSupportedMethodTest(MESSAGE_TEMPLATE_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}
