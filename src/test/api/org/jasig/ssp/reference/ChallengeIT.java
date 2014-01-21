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
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import com.jayway.restassured.response.Response;
import com.sun.java_cup.internal.runtime.virtual_parse_stack;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.Matchers;
import org.jasig.ssp.security.ApiAuthentication;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


public class ChallengeIT extends AbstractReferenceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChallengeIT.class);

    private static final String CHALLENGE_REFERRAL_ID = "de37299c-feac-4e71-9583-13a91c44370c";
    private static final String CHALLENGE_PATH = REFERENCE_PATH + "challenge";
    private static final String CHALLENGE_REFERRAL_PATH = CHALLENGE_PATH + "/" + CHALLENGE_REFERRAL_ID + "/challengeReferral";

    private static final String[] CHALLENGE_UUIDS;
    private static final String[] CHALLENGE_NAMES;
    private static final String[] CHALLENGE_DESCRIPTIONS;
    private static final String[] CHALLENGE_SELF_HELP_GUIDE_DESCRIPTIONS;
    private static final String[] CHALLENGE_SELF_HELP_GUIDE_QUESTIONS;
    private static final String[] CHALLENGE_TAGS;

    private static final Map<String, String[]> CHALLENGE_REFERRAL_MAPPINGS;

    private static final JSONArray CHALLENGE_ROWS;
    private static final JSONObject CHALLENGE_RESPONSE;

    private static final String[] CHALLENGE_REFERRAL_UUIDS;
    private static final String[] CHALLENGE_REFERRAL_NAMES;
    private static final String[] CHALLENGE_REFERRAL_DESCRIPTIONS;
    private static final String[] CHALLENGE_REFERRAL_PUBLIC_DESCRIPTIONS;
    private static final boolean[] CHALLENGE_REFERRAL_SELF_HELP_GUIDE_VALUES;
    private static final String[] CHALLENGE_REFERRAL_LINKS;

    static {

        //Challenge Referral Data
        CHALLENGE_REFERRAL_UUIDS = new String[] {
                "de37299c-feac-4e71-9583-13a91c44370c", "b1b323be-41ea-437f-bf2f-03ad0bd758f2",
                "7cc73413-02b9-4f8b-aa3f-38b082abc706", "ea8396c8-5e13-4a62-ba62-17cade2c57a5",
                "19fbec43-8c0b-478b-9d5f-00ec6ec57511", "eba6b6c1-7d62-4b3d-8f61-1722ce93418b",
                "eb4ddd20-ce13-417b-b110-2a54e1d471d5", "b17d9ff8-b28c-40a9-8779-1b3d7a9d64c1",
                "fd01167f-f99c-456b-80ad-0fc44a99ac24", "780bb947-1124-4f6f-b55c-2b02dbb03c64",
                "a81a21b6-bfd7-4fd3-a07e-04025a078a7d", "e9e29c50-0e3e-4f3b-a0a8-1deb5ba7352f",
                "3d8a27c6-920f-462b-8730-1fa91da9f78c", "7a69f78f-ecbf-48fb-8747-121c65cee660",
                "4e2aac3f-5056-a51a-8034-10522b001062", "fe7ce526-758f-4a2a-838a-1482adecec6b",
                "56aa29ef-e13e-4a26-b151-16e0cb1f9e86", "6e68497f-d4af-4f51-a9f7-251f7e2f680c",
                "c9b60122-466c-4a25-a1e3-1a5e946468a5", "d9b99e47-1714-450a-8db1-2d3913ac90cb",
                "660da4ff-7b02-4fa0-b0da-160e488b947b", "74b23392-139d-4dfb-a808-29b9a1265214",
                "7eda3a29-5fe8-44c0-b86f-0f084b51b228", "0baf5091-398d-408d-81d9-3763695dbd7a",
                "1086f9d4-90da-418c-a106-004e29490093", "79de802e-afd6-46e3-99f8-134b2a41d8a0",
                "b4c42bfd-7ad4-41ef-b650-31a135c660ce", "46f21d93-5056-a51a-80fe-16fb711058e6",
                "f298779d-2417-4b2d-b6b8-1f9df9acf481", "e218ad4a-4797-4818-89a9-0b0580db4274",
                "751a3383-7398-469f-a35f-3a86a08d8fcd", "03f7d2ea-504e-4b31-9099-30ca9ecd2afa",
                "0cc3f7f0-f583-4920-baa9-2024284acd10", "75e20872-17c8-436e-a3c0-07319f818c26",
                "5044bf0f-3eb1-47e5-9d30-3b60b7ed63bb", "c8f59411-d74f-4e93-b70f-370c6b297e96",
                "7fa465ae-c8cd-430c-a263-04c6a5046068", "31476a55-fad6-4d5a-afc6-266294bee0a4",
                "96cc3609-0ec2-42d6-8123-0b15492a9d2c", "c72ef7ef-ea06-4282-92ad-17153e136bb5",
                "51a92fd5-7639-46dd-accc-215305e46bc0", "db9229fe-2511-4939-8f9a-18d17e674e0c",
                "db2bb56c-5af5-499b-acc5-2bcf29123e6c", "43724de8-93cb-411c-a9fe-322a62756d04",
                "59ac3691-ea0d-4237-a468-0573ef07e895", "40a0ba63-3af9-49ae-a42c-20e43005b36d",
                "fc523dca-a1ba-4711-9802-04e85c3174db", "238aebce-887d-480a-b63b-122935d60337",
                "1f831a35-8944-4e9a-b459-38364df365ea", "fd6e5ff6-8907-4a2d-b3df-28fcc659a189",
                "d1650a52-b6e0-415b-9781-38504be570ac", "bc7967cf-bf1a-4445-885c-19705a0e686b",
                "0a640a2a-40e7-15de-8141-13495bef00fe", "bed66095-78f5-46e5-b277-2ca5ee7adb2d",
                "8c1612bc-d8cd-4878-8d5d-1e330ee8c136", "f1f881fd-83af-444e-9331-1d6ac7cd60c9",
                "75830250-d95d-4187-b8cd-38a110d1a832"
        };

        CHALLENGE_REFERRAL_NAMES = new String[] {
                "Academic Petition", "Admission - Application", "Alcoholics-anonymous", "Attendance Issues",
                "Campus Organization", "Career Coach", "Child Support Enforcement Agency", "Class - Grief & Loss",
                "Computer Access", "COPE Workshop", "Counseling Services",
                "Counseling - Weekend Intervention Program (WIP)", "Courses and Class Schedule", "CrisisCare",
                "Day-VEST", " Dialogue on Race Relations", "FAFSA & Student Guide - Spanish", "Family Services",
                "Fee Bill", "Financial Aid Award", "Financial Aid Deadlines", "Financial Aid Office Application",
                "Health and well being Handouts", "Holistic Counseling - Process", "Improve English Skills",
                "Improvement in Class Work", "Individualizing a Degree Program", "Large Font Size", "LASSI",
                "LASSI Results Handout", "Learning Labs", "Medicaid Programs", "Meet with Instructor",
                "MHS Community Referral", "Minority Peer Group", "MyPass Card - Transportation",
                "Open Learning Labs", "Orientation-IT", "Parenting Skills - PSY 130",
                "Part-time Demo College Opportunity Grant (OCOG)", "Payment", "Pell Transfer to Student Card",
                "Personal Interest Career Development Transient or HS Student", "Placement Testing", "Resource Room",
                "Stress Management Classes", "Student Impact Survey", "Student Judicial Affairs",
                "Student Success Course", "Student Support Services", "Study Skills Website",
                "Substance Abuse Treatment", "Test", "Tutorial Services and Labs", "TYPE", "Typology Test - MBTI",
                "Withdraw date"
        };

        CHALLENGE_REFERRAL_DESCRIPTIONS = new String[] {
                "If poor grades are the result of emergency circumstances provide documentation to support an " +
                        "academic petition to Vice President for Instruction Office 7330 or call 555- 2522.  " +
                        "For more information please reference the Student Handbook at " +
                        "http://www.studentsuccessplan.org.", "Complete Application for Admission (new and " +
                "returning students who have not attended for one year).  Application is available in Admissions " +
                "Office 10112 555-3648 www.studentsuccessplan.org.", "AL-ANON/ALATEEN:  For family members and " +
                "friends whose lives are affected by someone who drinks visit the national Al-non/Alateen website at " +
                "www.al-anon.alateen.org or the local website at www.al-anondemo.org", "Attend all classes and " +
                "appointments with counselors. If unable to attend contact the instructor or counselor as soon as " +
                "possible and discuss arrangements to make up missed assignments or to reschedule the appointment.",
                "Contact Student Leadership Development 8025 555-2509 for: campus organizations to increase " +
                        "social/cultural contacts  learn about organizations pertaining to career field   and/or for " +
                        "Student Leadership Development opportunity for additional information visit " +
                        "www.studentsuccessplan.org", "Career Coaches are available to assist you in planning out how " +
                "your education will lead to the career you deserve.", "Child Support Enforcement Agency for " +
                "enforcement of child support orders 555-4600 www.studentsuccessplan.org.", "Consider taking the " +
                "PSY135 Living with Loss Death & Grief class to learn more information.", "Free computer access may " +
                "be found at the following locations: Local Community Libraries and Community College Tartan " +
                "Marketplace – lower level of building 7 Library Teleport – 13223 Walkway in building 14 second floor " +
                "or Open Computer Lab Bldg. 14 first floor", "COPE WORKSHOP:  Attend Counseling Services’ C.O.P.E " +
                "Workshop on test anxiety 10424 555-2752 www.studentsuccessplan.org", "Contact Counseling Services " +
                "at: 10-424, 937-555-2752, www.studentsuccessplan.org/counseling for an individual appointment to " +
                "address identified concerns.", "Complete alcohol counseling by attending scheduled appointments. " +
                "If unable to keep scheduled appointment call counselor to reschedule appointment.",
                "Select courses and develop a class schedule with the assistance of ILP Counselor and/or Academic " +
                        "Advisor.", "CRISISCARE:  For 24 hour assessment and services for the uninsured contact " +
                "CrisisCare NW Building 1st Floor 601 Third Blvd. 224-4646.", "A volunteer employment service for " +
                "those seeking professional positions as well as an employment resource for members employers and " +
                "other professionals.  www.studentsuccessplan.org.  Contact James 555-555-5555", "Contact  Dialogue " +
                "on Race Relations  Room 555 555-5555 or 371 W. Second Street Suite 555  Demo 55555 or call " +
                "555 555-5555.", "To obtain the FAFSA form and Student Guide online in Spanish access the " +
                "following link: http://www.fafsa.ed.gov/es_ES/index.htm.", "FAMILY SERVICES:  Contact James at " +
                "Family Services Association 222-9481 for parenting education program.  Parenting 101 Classes are " +
                "offered throughout the year.  Sliding fee scale will establish the fee and scholarships may be " +
                "available.", "If question balance on fee bill take fee bill to financial aid for clarification.",
                "Consider the amount of award is based upon number of credit hours carried each quarter.",
                "Consider Quarterly Financial Aid deadlines (deadline to guarantee process of aid if eligible by " +
                        "fee payment date). Summer May 1 Fall August 1 Winter November 15 Spring February 15",
                "FINANCIAL AID OFFICE APPLICATION:  Submit College Financial Aid Office application to the Financial " +
                        "Aid Office 11346 555-3000.", "HANDOUTS:  Review handouts in reference to Health Well-Being " +
                "and Success in College.", "Holistic case management process to facilitate Student Success",
                "To improve English skills at no cost (not enrolled in classes) contact Barbara Gilbert 555-2894 for " +
                        "computer audio-visual programs. ", "Meet with instructor to discuss specific needs for " +
                "class work improvement.", "Consider individualizing a degree program.  For additional information " +
                "contact James at 555-2101 for A.A. programs not listed in the catalog.",
                "Increase fonts for written or published class material.",
                "Complete Learning and Study Strategies Inventory (LASSI) to obtain feedback on study habits:    " +
                        "Website:  http://www.studentsuccessplan.org     School Number: 77813     User Name:  prwa   " +
                        "User Password:  pf4y", "Select and apply three helpful strategies from the LASSI handouts. ",
                "Take advantage of various Learning Labs on campus www.studentsuccessplan.org.  Tutorial Services " +
                        "(937/555-2792) and Tutoring & Learning Center (937/555-4506) are located in the Library in " +
                        "7L07.  The Math Lab is located in Building 1 Room 1-315 or call (937/555-2286)",
                "For Medicaid programs (e.g. Healthy Start/Healthy Families; Demo Works First; Aged Blind and Disabled;" +
                        " Low Income Family (LIF)/Healthy Start Medicaid) visit the Job Center 111 S. Eigth St.  " +
                        "or call 555-4148.", "Meet with instructor about study tips for his/her class to confirm " +
                "progress/grades in class and/or discuss extra credit assignment.  If appropriate discuss specific " +
                "needs for class work improvement. ", "Contact insurance company for list of approved providers who " +
                "offer mental health services.", "MINORITY PEER GROUP:  Contact  Minority Student Retention  Counselor" +
                " for a Minority Peer Mentor 10324 555-2752 www.studentsuccessplan.org", "Put money on SchoolId Card " +
                "to pay reduced fee of $1.00 for parking in garage daily.", "OPEN LEARNING LABS:   Take advantage of" +
                " Open Learning Labs on campus www.studentsuccessplan.org", "Attend student information technology" +
                " session to learn how to access accounts and do things electronically on campus. For example learn" +
                " about web portal email web advisor my.trainingSSP and other IT services.  Sessions are held in the" +
                " Teleport Room 13-223.  For day and times sessions are offered call 555-4357 visit Teleport or check" +
                " out my.trainingSSP.", "PSY 130:  Discuss with academic counselor the possibility of taking  class to" +
                " address parenting skills.  For example PSY 130 Effective Parenting  (course offered irregularly).  " +
                "Check the course bulletin at: www.studentsuccessplan.org", "If financial aid file is complete and " +
                "attend part-time apply in person (if student prior to 2006-2007) by taking fee bill to the Financial " +
                "Aid Office in 10324 or call 555-3000.", "Make sure there is a zero balance on your fee bill by the " +
                "payment deadline date. If not speak with the appropriate office (e.g. Financial Aid Office 555 or " +
                "Bursar Office 5555 or call 555-3000).", "If you have Pell Grant visit the Bookstore within the first" +
                " 2 weeks to transfer up to $100 per quarter of your financial aid award to your College ID card to" +
                " purchase bus pass(es) academic expenses and daily expenses at College.", " If Personal Interest " +
                "Career Development Transient or High School Student visit the Enrollment Center 10444 or call " +
                "555-3210.", "If seeking a certificate or degree and do not have placement test scores within the" +
                " last two years visit the Enrollment Center in 10422 or call 555-4549.", "RESOURCE ROOM:  " +
                "Visit Counseling Services 'Resource Room' for additional resources (e.g. books brochures videos)" +
                " 10424 555-2752 www.studentsuccessplan.org", "CLASSES:  Consider taking classes to help manage " +
                "stressors or emotions.  For example EXL 105 Study Skills PSY 126 Stress Management " +
                "PSY 140 Psy of Interaction and Human Potential PSY 142 Self Esteem:  Building Life Skills and " +
                "PED Classes or ART Classes.  Please refer to the course bulletin at: www.studentsuccessplan.org.",
                "Complete ILP Survey or Counseling Services Survey.", "Refer student to Student Judicial Affairs " +
                "Building 8 room 8025 555-2509.", "Register for Student Success Course -Section 101 from all " +
                "divisions to learn strategies for success.", "If eligible contact Student Support Services for " +
                "support and guidance to achieve educational goals and to discuss services requirements and/or " +
                "application process in 11342 or call 555-3550.", "Any student may access the study skills site " +
                "designated for probation students at http://www.studentsuccessplan.org or" +
                " http://www.aims.edu/student/arc/index.php.", "Visit website for listing of substance abuse " +
                "treatment facilities located in  http://www.studentsuccessplan.org?state=Demo&city= and the " +
                "state of Demo http://www.studentsuccessplan.org.tst.us", "Description", "Attend tutorial labs " +
                "or help rooms as suggested by the instructor (listed below). Tutorial Services located in the " +
                "Library or call 555-2792 Disability Services 10421 or call 555-5113 Academic Math Lab 1315 or " +
                "call 555-2286 Writing Center - Library 7L or call 555-5106 Math Help Room - Library 7L Tutoring " +
                "and Learning Center - Library 7L or call 555-2792", "Complete the Humanmetrics Jung Typology Test " +
                "(same as Myers Briggs MBTI) to identify preferences which can assist with career selection learning " +
                "and/or relationships.  Web address:  http://www.humanmetrics.com/cgi-win/JTypes1.htm.  " +
                "Read instructions and click on 'Do It.'  Answer the questions click on 'Score It.' " +
                " When finished print page and bring it to your scheduled appointment with your counselor.",
                "TYPE:  Complete Typology Test.  Type:http://www.humanmetrics.com/cgi-win/Jtypes1.htm in the " +
                        "address field/box of the web browser.  Press ENTER.  Carefully read the directions " +
                        "and click on the 'Do It' button at the bottom of the text box. Click 'Score it' when done." +
                        "  PRINT PAGE!!  Bring printed page to your scheduled appointment with your counselor.",
                "Remember the withdraw date. If thinking about withdrawing be sure to speak with the Financial " +
                        "Aid Office for possible impact of withdrawing on your financial aid. Before withdrawing" +
                        " speak with ILP Counselor to see if there might be other options."
        };

        CHALLENGE_REFERRAL_PUBLIC_DESCRIPTIONS = new String[] {
                "If poor grades are the result of emergency circumstances provide documentation to support an " +
                        "academic petition to Vice President for Instruction Office 7330 or call 555- 2522.  " +
                        "For more information please reference the Student Handbook at " +
                        "http://www.studentsuccessplan.org.", "Complete Application for Admission " +
                "(new and returning students who have not attended for one year).  " +
                "Application is available in Admissions Office, 10-112, 937-555-3648 www.studentsuccessplan.org.",
                "AL-ANON/ALATEEN:  For family members and friends whose lives are affected by someone who drinks " +
                        "visit the national Al-non/Alateen website at www.al-anon.alateen.org or the local website " +
                        "at www.al-anondemo.org", "Attend all classes and appointments with counselors. If unable to " +
                "attend contact the instructor or counselor as soon as possible and discuss arrangements to make up " +
                "missed assignments or to reschedule the appointment.", "Contact Student Leadership Development 8025 " +
                "555-2509 for: campus organizations to increase social/cultural contacts  learn about organizations " +
                "pertaining to career field   and/or for Student Leadership Development opportunity for additional" +
                " information visit www.studentsuccessplan.org", "Career Coaches are available to assist you in" +
                " planning out how your education will lead to the career you deserve.", "Child Support Enforcement" +
                " Agency for enforcement of child support orders 555-5555 www.studentsuccessplan.org.", "Consider " +
                "taking the PSY135 Living with Loss Death & Grief class to learn more information.", "Free computer" +
                " access may be found at the following locations: Local Community Libraries and Community College" +
                " Marketplace – lower level of building 7 Library Teleport – 13223 Walkway in building 14 " +
                "second floor or Open Computer Lab Bldg. 14 first floor", "Test Anxiety - COPE WORKSHOP: " +
                " Attend Counseling Services’ C.O.P.E Workshop on test anxiety 10424 555-2752 " +
                "www.studentsuccessplan.org", "Contact Counseling Services at: 10-424, 937-555-2752," +
                " www.studentsuccessplan.org counseling for an individual appointment to address identified concerns.",
                "Complete alcohol counseling by attending scheduled appointments. " +
                        "If unable to keep scheduled appointment call counselor to reschedule appointment.",
                "Select courses and develop a class schedule with the assistance of ILP Counselor and/or Academic" +
                        " Advisor.", "CRISISCARE:  For 24 hour assessment and services for the uninsured contact " +
                "CrisisCare NW Building 1st Floor 601 Third Blvd. 224-4646.", "A volunteer employment service for" +
                " those seeking professional positions as well as an employment resource for members employers and" +
                " other professionals.  www.studentsuccessplan.org.  Contact 937-222-9065", "Contact  Dialogue on" +
                " Race Relations  Room 555 555-5555 or 371 W. Second Street Suite 555  Demo 55555 or call" +
                " 555 555-5555.",
                "To obtain the FAFSA form and Student Guide online in Spanish access the following link: " +
                        "http://www.fafsa.ed.gov/es_ES/index.htm.", "FAMILY SERVICES:  " +
                "Contact James at Family Services Association 222-9481 for parenting education program.  " +
                "Parenting 101 Classes are offered throughout the year.  Sliding fee scale will establish the " +
                "fee and scholarships may be available.", "If question balance on fee bill take fee bill to " +
                "financial aid for clarification.", "Consider the amount of award is based upon number of " +
                "credit hours carried each quarter.", "Consider Quarterly Financial Aid deadlines (deadline to " +
                "guarantee process of aid if eligible by fee payment date). Summer May 1 Fall August 1 Winter " +
                "November 15 Spring February 15", "FINANCIAL AID OFFICE APPLICATION:  Submit College Financial " +
                "Aid Office application to the Financial Aid Office 11346 555-3000.", "HANDOUTS:  Review handouts " +
                "in reference to Health Well-Being and Success in College.", "Holistic case management process to " +
                "facilitate Student Success", "", "", "Consider individualizing a degree program.  For additional" +
                " information contact James at 555-2101 for A.A. programs not listed in the catalog.", "Increase fonts" +
                " for written or published class material.", "Complete Learning and Study Strategies Inventory (LASSI)" +
                " to obtain feedback on study habits:    Website:  http://www.studentsuccessplan.org     School Number:" +
                " 77813     User Name:  prwa   User Password:  pf4y", "Select and apply three helpful strategies from " +
                "the LASSI handouts. ", "Take advantage of various Learning Labs on campus www.studentsuccessplan.org." +
                "  Tutorial Services (937/555-2792) and Tutoring & Learning Center (937/555-4506) are located in " +
                "the Library in 7L07.  The Math Lab is located in Building 1 Room 1-315 or call (937/555-2286)",
                "For Medicaid programs (e.g. Healthy Start/Healthy Families; Demo Works First; Aged Blind and" +
                        " Disabled; Low Income Family (LIF)/Healthy Start Medicaid) visit the Job Center 111 S. " +
                        "Eigth St.  or call 555-4148.", "Meet with instructor about study tips for his/her class " +
                "to confirm progress/grades in class and/or discuss extra credit assignment. If appropriate discuss " +
                "specific needs for class work improvement.", "Contact insurance company for list of approved " +
                "providers who offer mental health services.", "MINORITY PEER GROUP:  Contact Minority Student " +
                "Retention  Counselor for a Minority Peer Mentor 10324 555-2752 www.studentsuccessplan.org",
                "Put money on SchoolId Card to pay reduced fee of $1.00 for parking in garage daily.",
                "OPEN LEARNING LABS:   Take advantage of Open Learning Labs on campus www.studentsuccessplan.org/",
                "Attend student information technology session to learn how to access accounts and do things " +
                        "electronically on campus. For example learn about web portal email web advisor my.trainingSSP " +
                        "and other IT services.  Sessions are held in the Teleport Room 13-223.  For day and " +
                        "times sessions are offered call 555-4357 visit Teleport or check out my.trainingSSP.",
                "PSY 130:  Discuss with academic counselor the possibility of taking  class to address parenting " +
                        "skills.  For example PSY 130 Effective Parenting  (course offered irregularly).  " +
                        "Check the course bulletin at: www.studentsuccessplan.org", "If financial aid file is " +
                "complete and attend part-time apply in person (if student prior to 2006-2007) by taking fee bill to" +
                " the Financial Aid Office in 10324 or call 555-3000.", "Make sure there is a zero balance on your " +
                "fee bill by the payment deadline date. If not speak with the appropriate office (e.g. Financial " +
                "Aid Office 555 or Bursar Office 5555 or call 555-3000).", "If have Pell Grant transfer up to $100 " +
                "per quarter of your financial aid award to your tartan card to purchase bus pass(es) academic" +
                " expenses and daily expenses at College.", " If Personal Interest Career Development Transient o" +
                "r High School Student visit the Enrollment Center 10444 or call 555-3210.", "If seeking a certificate" +
                " or degree and do not have placement test scores within the last two years visit the Enrollment " +
                "Center in 10422 or call 555-4549.", "RESOURCE ROOM:  Visit Counseling Services 'Resource Room' " +
                "for additional resources (e.g. books brochures videos) 10424 555-2752 www.studentsuccessplan.org",
                "CLASSES:  Consider taking classes to help manage stressors or emotions.  For example EXL 105 Study" +
                        " Skills PSY 126 Stress Management PSY 140 Psy of Interaction and Human Potential PSY 142 Self " +
                        "Esteem:  Building Life Skills and PED Classes or ART Classes.  Please refer to the course " +
                        "bulletin at: www.studentsuccessplan.org.", "Complete ILP Survey or Counseling " +
                "Services Survey.", "Refer student to Student Judicial Affairs Building 8 room 8025 555-2509.",
                "Register for Student Success Course -Section 101 from all divisions to learn strategies for" +
                        " success.", "If eligible contact Student Support Services for support and guidance to" +
                " achieve educational goals and to discuss services requirements and/or application process in 11342" +
                " or call 555-3550.", "Any student may access the study skills site designated for probation students " +
                "at www.studentsuccessplan.org or www.aims.edu/student/arc/index.php.", "Visit website for listing of " +
                "substance abuse treatment facilities located in  " +
                "http://www.substanceabusetreatment.info/city.htm?state=Demo&city= and the state of Demo " +
                "http://www.studentsuccessplan.org.state.tst.us", "", "Attend tutorial labs or help rooms as " +
                "suggested by the instructor (listed below). ' Tutorial Services located in the Library or call " +
                "555-2792 ' Disability Services 10421 or call 555-5113 ' Academic Math Lab 1315 or call 555-2286 '" +
                " Writing Center - Library 7L or call 555-5106 ' Math Help Room - Library 7L ' Tutoring and Learning " +
                "Center - Library 7L or call 555-2792", "", "TYPE:  Complete Typology Test.  " +
                "Type:http://www.humanmetrics.com/cgi-win/Jtypes1.htm in the address field/box of the web browser. " +
                " Press ENTER.  Carefully read the directions and click on the 'Do It'" +
                " button at the bottom of the text box. Click 'Score it' when done.  PRINT PAGE!!" +
                "  Bring printed page to your scheduled appointment with your counselor.",
                "Remember the withdraw date. If thinking about withdrawing be sure to speak with the Financial " +
                        "Aid Office for possible impact of withdrawing on your financial aid. Before withdrawing " +
                        "speak with ILP Counselor to see if there might be other options."
        };

        CHALLENGE_REFERRAL_SELF_HELP_GUIDE_VALUES = new boolean[] {
                true, true, false, false, true, false, false, true, true, false, true, false, true, true, true,
                true, true, true, true, true, true, true, false, false, false, false, true, false, true, false,
                true, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true,
                false, true, true, true, true, true, false, true, false, true, true
        };

        CHALLENGE_REFERRAL_LINKS = new String[] {
                "", "apply.studentsuccessplan.org", "www.al-anondemo.org", "", "www.studentsuccessplan.org", "",
                "www.studentsuccessplan.org", "", "", "www.studentsuccessplan.org", "www.studentsuccessplan.org", "",
                "schedule.studentsuccessplan.org", "", "", "", "www.fafsa.ed.gov/es_ES/index.htm", "", "", "", "", "",
                "", "", null, null, "", "", "www.studentsuccessplan.org", "", "www.studentsuccessplan.org", "", "", "",
                "www.studentsuccessplan.org", "", "www.studentsuccessplan.org", "", "www.studentsuccessplan.org", "",
                "", "", "", "", "www.studentsuccessplan.org", "www.studentsuccessplan.org", "", "", "", "",
                "www.aims.edu/student/arc/index.php", "www.studentsuccessplan.org.state.tst.us",
                "http://www.pinterest.com/all/food_drink/", "", null, "www.humanmetrics.com/cgi-win/Jtypes1.htm", ""
        };

        //Challenge Data
        CHALLENGE_UUIDS = new String[] {
                "5d6dc03f-b000-42b1-a078-253c55867ff1", "0a640a2a-409d-1271-8140-d0cf543a0106",
                "07b5c3ac-3bdf-4d12-b65d-94cb55167998", "33c6207a-3302-405f-8a2e-9d9bd750dac0",
                "f067c6ca-50ad-447a-ad12-f47dffdce42e", "cab7d5a5-2ca5-4af7-a644-b3882ddc9b41",
                "6e4b6a6c-8b67-48df-ac7f-c9f225e872b8", "af7e472c-3b7c-4d00-a667-04f52f560940",
                "dbb8741c-ece0-4830-8ebf-774151cb6a1b", "7c0e5b76-9933-484a-b265-58cb280305a5",
                "70693703-b2c1-4d3c-b79f-e43e93393b8c", "1f5b63a9-9b50-412b-9971-23602f87444c",
                "fb206a68-78db-489d-9d5d-dce554f54eed", "22d23035-74f0-40f1-ac41-47a22c798af7",
                "2fd9ab0b-4afb-43d3-8ae2-5cf462c847e5", "bd886899-96d5-4ec8-9d6c-3cb2d4e0f09b",
                "72de7c95-eab3-46b2-93cf-108397befcbb", "7ad819ef-d1e3-4ebf-a05b-e233f17f9e55",
                "f6bb0a62-1756-4ea2-857d-5821ee44a1da", "609527d4-e768-4caa-a65a-2bb3f3da2948",
                "615225df-c3b7-4ae2-a828-6fad663c629b", "c1428288-ec1e-432e-87df-6567d9618f42",
                "6c34e91c-3ef3-4b3f-8061-fab1b7ff59ca", "80c5b019-1946-4a98-a7fd-b8d62684558c",
                "eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3", "431abcf2-43fe-4d6a-8f83-b47c91157a15",
                "b9ac1cb5-d40a-4451-8ec2-08240698aaf3", "38f7ae25-902f-4381-851e-2e2319adb1bd",
                "43719c57-ec92-4e4a-9fb6-25208936fd18"
        };

        CHALLENGE_NAMES = new String[] {
                "Alcohol and/or Substance Abuse", "Books - Educational Resources", "Child or Adult Care",
                "Classroom Issues", "Computer/Email", "Concentration (Poor Concentration)",
                "Cultural Awareness or Issues", "Emotions, Moods and Stress", "English as a Second Language",
                "Finances - Education", "Finances - Personal", "Goals/Career Choices (Unclear)", "Grades",
                "Grief and Loss", "Housing/Shelter", "Legal - Immigration", "Maps/Directions",
                "Motivation/Attitude", "Other", "Physical Health", "Relationship Issues",
                "Social Support (Lack of Support)", "Steps to Begin College", "Study Resources",
                "Test Anxiety", "Time Management", "Tired/Fatigue", "Transportation", "Undecided Major or Career Field"
        };

        CHALLENGE_DESCRIPTIONS = new String[] {
                "Alcohol and/or Substance Abuse",
                "Books and education resources are often critical to the learning process and ensuring a student " +
                        "has the needed material and information for use in their studies is important.",
                "Child or Adult Care", "Classroom Issues", "Computer/Email", "Staying focused while studying and " +
                "listening in class contributes greatly to academic success.  Concentration strategies and " +
                "self-discipline will enhance your ability to stay on task and retain information.",
                "Cultural Awareness and issues surrounding cultural similarities and differences can impact a " +
                        "students learning process. ", "It is important for students to take care of themselves " +
                "and attend to all areas of their life (e.g. intellectual, emotional, physical, social and spiritual)." +
                "  When students have difficulty managing stress and addressing/balancing areas of their life, " +
                "this can have a negative impact on school performance. ", "Educational and social support is " +
                "provided through the office of ESL/Enrollment Services.  Students who speak English as a " +
                "second language are encouraged to seek assistance from the ESL coordinator.",
                "A majority of college students need assistance paying for their classes and books.  " +
                        "Having a plan to pay your college expenses while maintaining a low student loan " +
                        "commitment will free you to focus on you coursework.", "Personal finances can often " +
                "impact a student in how they are able to pay for college, books and support themselves in all aspects" +
                " of life during their studies.", "Early identification of a chosen career program motivates students" +
                " and provides focus for goal achievement.  Quarterly evaluation of goals will help you stay on track.",
                "Achieving and maintaining good grades while at college is key to reaching your academic and career " +
                        "goals.  Utilizing resources on campus is a significant step in achieving good grades and " +
                        "reaching your goals.   ", "Grief and loss can impact a student profoundly, either personally" +
                " or in those around them, and should not be dismissed or neglected in terms of maintaining a healthy" +
                " lifestyle. ", "Adequate housing or shelter is critical for a students success, and many options, " +
                "supports and information are available to address this challenge. ", "Immigration status, concerns" +
                " and questions of a student or others close to them, can cause a student stress impacts academic" +
                " studies. Many resources are available to help understand and navigate the immigration system.",
                "Maps/Directions to the college locations.",
                "Motivation involves completing something which you start even when faced with difficulties.  " +
                        "For example, a highly motivated student is able to start and complete a class assignment " +
                        "even when he/she is faced with difficulties, such as extra work hours, computer problems " +
                        "and/or personal concerns.", "Other, unspecified challenge.", "Being physically healthy is " +
                "a great way to stay mentally alert and prepared for academic studies. Students or their loved ones, " +
                "sometimes encounter health issues or concerns with the cost of health care, and many resources are" +
                " available to help address these concerns.", "Relationships with our loved ones, family, co-workers" +
                " and peers can impact our moods, stress levels and may distract from academic success. Many options" +
                " for managing relationships in healthy ways are available. ", "Social Support can assist you in " +
                "quality of life, it can allow you to further your success and further you goals.",
                "Steps to Begin College", "Good study habits lay the foundation for academic success.  " +
                "Many high school graduates and adults have not learned effective study habits required for success " +
                "in college.", "Some students report being confident and prepared for tests.  " +
                "Other students report they experience test anxiety causing their grades to be negatively impacted.  " +
                "For example, they report feeling anxious, experiencing physical symptoms (e.g. sick to stomach), " +
                "and having problems thinking (e.g. difficulty concentrating and mind goes blank).", "Students " +
                "frequently have a lot of responsibilities to complete within a limited amount of time.  " +
                "Some students are able to complete these responsibilities and devote time to all major areas of " +
                "their life (physical, social, emotional, intellectual and spiritual) while taking care of themselves" +
                " and engaging in activities of importance and value in their life.", "Fatigue can reduce a students" +
                " ability to focus on academic endeavors and keep you from performing at your best level. " +
                "Many options are available to help address this challenge.", "Instructors expect students to be in" +
                " class, on time, every time.  Being late or missing just one class forces you to play “catch-up” " +
                "and can negatively affect your grades.", "Selecting a major or career field is an important first " +
                "step on the path to your future career.  Deciding on a major can provide a goal for you to work " +
                "towards and the purpose behind future career activities and getting a degree.  "
        };

        CHALLENGE_SELF_HELP_GUIDE_DESCRIPTIONS = new String[] {
                "Drug and Alcohol abuse will create roads blocks limiting your ability to achieve your educational " +
                        "and personal goals.  ",
                "Books and education resources are often critical to the learning process and ensuring a student " +
                        "has the needed material and information for use in their studies is important.",
                "Child or adult care demands often pose challenges for success in college.  " +
                        "It is important to develop a plan to address these challenges before they become barriers " +
                        "to your academic success.",
                "Classroom Issues", "Email, not post office mail, is the number one means of communication by the " +
                "College with students.  Basic computer skills to access your email and web advisor tools are required " +
                "for all new students even before classes begin.",
                "Staying focused while studying and listening in class contributes greatly to academic success.  " +
                        "Concentration strategies and self-discipline will enhance your ability to stay on task and " +
                        "retain information.", "Cultural Awareness and issues surrounding cultural similarities and " +
                "differences can impact a students learning process. ", "It is important for students to take care of " +
                "themselves and attend to all areas of their life (e.g. intellectual, emotional, physical, social " +
                "and spiritual).  When students have difficulty managing stress and addressing/balancing areas of" +
                " their life, this can have a negative impact on school performance. ",
                "Educational and social support is provided through the office of ESL/Enrollment Services.  " +
                        "Students who speak English as a second language are encouraged to seek assistance from " +
                        "the ESL coordinator.",
                "A majority of college students need assistance paying for their classes and books.  " +
                        "Having a plan to pay your college expenses while maintaining a low student loan commitment " +
                        "will free you to focus on you coursework.", "Personal finances can often impact a student " +
                "in how they are able to pay for college, books and support themselves in all aspects of life during " +
                "their studies.",
                "Early identification of a chosen career program motivates students and provides focus for goal " +
                        "achievement.  Quarterly evaluation of goals will help you stay on track.",
                "Achieving and maintaining good grades while at college is key to reaching your academic and career" +
                        " goals.  Utilizing resources on campus is a significant step in achieving good grades and " +
                        "reaching your goals.   ", "Grief and loss can impact a student profoundly, either personally " +
                "or in those around them, and should not be dismissed or neglected in terms of maintaining a health" +
                "y lifestyle. ",
                "Adequate housing or shelter is critical for a students success, and many options, supports and " +
                        "information are available to address this challenge. ",
                "Immigration status, concerns and questions of a student or others close to them, can cause a student " +
                        "stress impacts academic studies. Many resources are available to help understand and navigate" +
                        " the immigration system.", "Maps/Directions to the college locations.", "Motivation involves " +
                "completing something which you start even when faced with difficulties.  " +
                "For example, a highly motivated student is able to start and complete a class assignment even when" +
                " he/she is faced with difficulties, such as extra work hours, computer problems and/or personal " +
                "concerns.", "Test self-help guide description.",
                "Being physically healthy is a great way to stay mentally alert and prepared for academic studies. " +
                        "Students sometimes encounter health issues or concerns with the cost of health care, " +
                        "and many resources are available to help address these concerns.",
                "Relationships with our loved ones, family, co-workers and peers can impact our moods, stress levels " +
                        "and may distract from academic success. Many options for managing relationships in healthy" +
                        " ways are available. ",
                "Social Support can assist you in quality of life, it can allow you to further your success and" +
                        " further you goals.", "Steps to Begin College",
                "Good study habits lay the foundation for academic success.  " +
                        "Many high school graduates and adults have not learned effective study habits " +
                        "required for success in college.", "Some students report being confident and prepared for" +
                " tests.  Other students report they experience test anxiety causing their grades to be" +
                " negatively impacted.  For example, they report feeling anxious, experiencing physical " +
                "symptoms (e.g. sick to stomach), and having problems thinking (e.g. difficulty concentrating" +
                " and mind goes blank).", "Students frequently have a lot of responsibilities to complete within a" +
                " limited amount of time.  Some students are able to complete these responsibilities and devote time" +
                " to all major areas of their life (physical, social, emotional, intellectual and spiritual) while " +
                "taking care of themselves and engaging in activities of importance and value in their life.",
                "Fatigue can reduce a students ability to focus on academic endeavors and keep you from performing " +
                        "at your best level. Many options are available to help address this challenge.",
                "Instructors expect students to be in class, on time, every time.  " +
                        "Being late or missing just one class forces you to play “catch-up” and can negatively " +
                        "affect your grades.", "Selecting a major or career field is an important first step on " +
                "the path to your future career.  Deciding on a major can provide a goal for you to work towards " +
                "and the purpose behind future career activities and getting a degree.  "
        };

        CHALLENGE_SELF_HELP_GUIDE_QUESTIONS = new String[] {
                "Do you feel your alcohol and/or drug use is out of control and would like some assistance to address " +
                        "this concern?",
                "Do you have concerns or challenges with acquiring and utilizing the books and educational resources " +
                        "required by your course work?",
                "Could my child or adult care cause me to be absent from class or fail to complete assignments?",
                "Are issues in the Classroom interfering with your college success?",
                "Does my lack of computer skills keep me from fully participating in class and college activities?",
                "Do I have trouble focusing for at least an hour and a half when studying and listening?",
                "I would like to know more about becoming more culturally aware, or to discover tools and methods to " +
                        "address cultural issues or challenges that may impact my success in college.",
                "Would I like to learn about different resources that address stress and wellness in my life?",
                "Would I like to learn more about services available to ESL students?",
                "Am I unfamiliar with all of the different types of aid that the college offers that could address " +
                        "college expenses?",
                "I would like more information and resources to help me better understand and manage my personal " +
                        "finances?", "Do I need assistance in developing clearly defined goals for success?",
                "Do I need information that can assist me in achieving and maintaining good grades?",
                "I would like to know more about the options and support the college has available for addressing " +
                        "grief and loss personally or in those around me. ",
                "I need more information on the available options, support, and information about Housing & Shelter?",
                "I would like more information about the resources available to assist with immigration questions, " +
                        "process, or legal concerns?", "I need assistance with Maps or Directions to the college " +
                "locations?",
                "Do I lack motivation to start or complete activities?",
                "Test self-help guide question?",
                "I would like to learn more about what resources and information are available about Physical Health?",
                "My relationships may cause me to lose focus on my studies and be less successful in college?",
                "Are you looking for or have a lack of social support?",
                "I could use more information about the steps to get started in college?",
                "Do I need to learn how to effectively learn?",
                "Is test anxiety negatively impacting my test performance?",
                "Would I like to learn new time management strategies?",
                "I often am so tired or fatigued that it might impact my academic performance?",
                "Do I have transportation issues that could cause me to be late or miss class?",
                "Do I need assistance in selecting a major and/or career path? "
        };

        CHALLENGE_TAGS = new String[] {
                "drink, drug, smoke, pill, drugs, addiction, treatment, recovery, AA, NA, " +
                        "Counseling, binge drinking, rehab, drinking, drug dependence, substance abuse, alcohol",
                "rent, books, funds,  pay, Bookstore, e-books, book scholarship, reserve, book money, location, " +
                        "ordering books, text book, used books, borrow book, Book", "child, baby, sitter, adult," +
                " senior care, daycare, childcare, parent care, home care, elder care, 4C, Childcare Works, " +
                "Agency on Aging, Head Start, Job and Family Services, babysitter, parenting", "accommodation, " +
                "disability services, police, disrespect in classroom, disruptive, inconsiderate, teacher, instructor, " +
                "conflict, problems, trouble, extra time, advocate, environment, faculty",
                "help desk, computer, e-mail, web advisor, on-line courses, password, computer labs, Angel, labs, " +
                        "my.trainingSSP, access", "tutoring, workshops, counseling, accommodation, attention, ADD, ADHD, " +
                "IEP, brain fog, confused, distraction, focus, concentration, forgetting",
                "African American, initiatives, support, culture, clubs, humanities, ESL, International series, " +
                        "organizations, race, relations, advocate, ombudsman, PED, Physical Education, legal",
                "emotions, moods, stress, performance, anger, dietetic, mental health, referral, workshops, ADAHMS, " +
                        "abuse, rape, crisis, organizations, ministries, violence, EAP, advocate, ombudsman, behavioral," +
                        " hotline, help", "ESL, English as a second language, admissions, f1, visa, " +
                "FAFSA, legal, immigrant, International student", "Getting Financial Aid, refund check, pay for books," +
                " accepting FA, Accepting Financial Aid, extra cash, paying for school, grants, Pell, FAFSA, FA, " +
                "Scholarship, Tuition payment Financial Aid Steps, student loans, workshops, cash, loans, FACTS",
                "finances, assistance, resources, job and family services, money, bills, credit, counseling, medicaid, " +
                        "evictions, utility, shut off, bankruptcy",
                "goals, career, major, workshops, counseling, advising, Discover, resume, cover letter",
                "grades, dispute, low grades, workshops, support, services, labs, tutoring, tutor, tutorial, " +
                        "study, petition, advisor, counselor, withdrawal, help", "grief, loss, death, counseling," +
                " advising, hospice, campus ministries, church, support",
                "housing, shelter, home, homeless, bed, resources, crisis, outreach, helplink, united way, eviction, " +
                        "shutoff",
                "legal, immigration, immigrate, ESL, F1, F-1, International, second language",
                "maps, directions, locations, branches, offices",
                "organizations, career services, counseling, attitude, motivation, Demo Fellows, goals, study", null,
                "health, referrals, medical, doctors, healthcare, health, clinics, urgent care, medicaid, physical, " +
                        "helplink, disability, Abilities First, physician, presription insurance, workshops, " +
                        "counseling ", "relationship, partner, domestic violence, violence, campus ministries, " +
                "organizations, class, counseling, marriage works, womanline, referral, resources, emotions, " +
                "moods, stress", "support, help, ministries, volunteering, Demo Fellows, Physical education, " +
                "programs, Student Services, group, study", "Apply, enroll, register, pay, started, catalog, program, " +
                "course, financial aid, cost, FAFSA, scholarships, steps, advisor, counselor, admission",
                "study, resources, workshops, videos, labs, tutor, tutoring, tutorial, student services, math, " +
                        "online, instructor, counseling, resources, website, writing, PED", "test, testing, help, " +
                "counseling, workshops, videos, COPE, student services, PED, stress, relaxation, study, group," +
                " website, tutor, tutoring, tutorial, anxiety, student services",
                "time, management, help, assistance, student services, workshops, videos, website, counseling," +
                        " advising, advisor", "tired, fatigue, exhausted, physical, health, workshop, video, " +
                "wellness, resources, emotions, moods, stress, anxiety", "ride, car ride, bus, rideshare, car," +
                " repairs, RTA, pass, ride share, parking, TRAC, transportation, public transit",
                "undecided, major, career, discover, job, CAP 105, resources, student services, interviewing, " +
                        "shadowing, degree field, volunteering"
        };

        CHALLENGE_REFERRAL_MAPPINGS = new HashMap<String, String[]>();
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[0], new String[] { "7cc73413-02b9-4f8b-aa3f-38b082abc706",
                "ea8396c8-5e13-4a62-ba62-17cade2c57a5", "e9e29c50-0e3e-4f3b-a0a8-1deb5ba7352f",
                "bc7967cf-bf1a-4445-885c-19705a0e686b"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[1], new String[] { "d1650a52-b6e0-415b-9781-38504be570ac",
                "d9b99e47-1714-450a-8db1-2d3913ac90cb", "fd01167f-f99c-456b-80ad-0fc44a99ac24",
                "751a3383-7398-469f-a35f-3a86a08d8fcd", "7fa465ae-c8cd-430c-a263-04c6a5046068"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[2], new String[] { "ea8396c8-5e13-4a62-ba62-17cade2c57a5",
                "96cc3609-0ec2-42d6-8123-0b15492a9d2c", "6e68497f-d4af-4f51-a9f7-251f7e2f680c",
                "eb4ddd20-ce13-417b-b110-2a54e1d471d5"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[3], new String[] { "ea8396c8-5e13-4a62-ba62-17cade2c57a5",
                "75830250-d95d-4187-b8cd-38a110d1a832", "238aebce-887d-480a-b63b-122935d60337",
                "8c1612bc-d8cd-4878-8d5d-1e330ee8c136"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[4], new String[] { "fd01167f-f99c-456b-80ad-0fc44a99ac24" });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[5], new String[] { "a81a21b6-bfd7-4fd3-a07e-04025a078a7d",
                "40a0ba63-3af9-49ae-a42c-20e43005b36d", "59ac3691-ea0d-4237-a468-0573ef07e895",
                "bed66095-78f5-46e5-b277-2ca5ee7adb2d", "e218ad4a-4797-4818-89a9-0b0580db4274"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[6], new String[] { "a81a21b6-bfd7-4fd3-a07e-04025a078a7d",
                "59ac3691-ea0d-4237-a468-0573ef07e895", "19fbec43-8c0b-478b-9d5f-00ec6ec57511",
                "fe7ce526-758f-4a2a-838a-1482adecec6b"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[7], new String[] { "19fbec43-8c0b-478b-9d5f-00ec6ec57511",
                "ea8396c8-5e13-4a62-ba62-17cade2c57a5", "7a69f78f-ecbf-48fb-8747-121c65cee660",
                "780bb947-1124-4f6f-b55c-2b02dbb03c64", "40a0ba63-3af9-49ae-a42c-20e43005b36d",
                "59ac3691-ea0d-4237-a468-0573ef07e895", "75e20872-17c8-436e-a3c0-07319f818c26"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[8], new String[] { "b1b323be-41ea-437f-bf2f-03ad0bd758f2",
                "1f831a35-8944-4e9a-b459-38364df365ea", "56aa29ef-e13e-4a26-b151-16e0cb1f9e86",
                "1086f9d4-90da-418c-a106-004e29490093"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[9], new String[] { "74b23392-139d-4dfb-a808-29b9a1265214",
                "51a92fd5-7639-46dd-accc-215305e46bc0", "d9b99e47-1714-450a-8db1-2d3913ac90cb",
                "c72ef7ef-ea06-4282-92ad-17153e136bb5", "db9229fe-2511-4939-8f9a-18d17e674e0c",
                "660da4ff-7b02-4fa0-b0da-160e488b947b", "c9b60122-466c-4a25-a1e3-1a5e946468a5",
                "56aa29ef-e13e-4a26-b151-16e0cb1f9e86"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[10], new String[] { "db9229fe-2511-4939-8f9a-18d17e674e0c",
                "03f7d2ea-504e-4b31-9099-30ca9ecd2afa"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[11], new String[] { "eba6b6c1-7d62-4b3d-8f61-1722ce93418b",
                "4e2aac3f-5056-a51a-8034-10522b001062"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[12], new String[] { "75830250-d95d-4187-b8cd-38a110d1a832",
                "238aebce-887d-480a-b63b-122935d60337", "1f831a35-8944-4e9a-b459-38364df365ea",
                "751a3383-7398-469f-a35f-3a86a08d8fcd", "d1650a52-b6e0-415b-9781-38504be570ac",
                "de37299c-feac-4e71-9583-13a91c44370c"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[13], new String[] { "780bb947-1124-4f6f-b55c-2b02dbb03c64",
                "7a69f78f-ecbf-48fb-8747-121c65cee660", "75e20872-17c8-436e-a3c0-07319f818c26",
                "b17d9ff8-b28c-40a9-8779-1b3d7a9d64c1"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[14], new String[] { "59ac3691-ea0d-4237-a468-0573ef07e895" });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[15], new String[] { "b1b323be-41ea-437f-bf2f-03ad0bd758f2",
                "56aa29ef-e13e-4a26-b151-16e0cb1f9e86", "a81a21b6-bfd7-4fd3-a07e-04025a078a7d"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[16], new String[] { "fd01167f-f99c-456b-80ad-0fc44a99ac24" });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[17], new String[] { "a81a21b6-bfd7-4fd3-a07e-04025a078a7d",
                "eba6b6c1-7d62-4b3d-8f61-1722ce93418b", "19fbec43-8c0b-478b-9d5f-00ec6ec57511"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[18], new String[] { "eba6b6c1-7d62-4b3d-8f61-1722ce93418b" });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[19], new String[] { "7eda3a29-5fe8-44c0-b86f-0f084b51b228",
                "40a0ba63-3af9-49ae-a42c-20e43005b36d", "03f7d2ea-504e-4b31-9099-30ca9ecd2afa"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[20], new String[] { "40a0ba63-3af9-49ae-a42c-20e43005b36d",
                "19fbec43-8c0b-478b-9d5f-00ec6ec57511"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[21], new String[] { "5044bf0f-3eb1-47e5-9d30-3b60b7ed63bb",
                "fd6e5ff6-8907-4a2d-b3df-28fcc659a189", "19fbec43-8c0b-478b-9d5f-00ec6ec57511"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[22], new String[] { "c9b60122-466c-4a25-a1e3-1a5e946468a5",
                "d9b99e47-1714-450a-8db1-2d3913ac90cb", "660da4ff-7b02-4fa0-b0da-160e488b947b",
                "74b23392-139d-4dfb-a808-29b9a1265214", "1086f9d4-90da-418c-a106-004e29490093",
                "31476a55-fad6-4d5a-afc6-266294bee0a4", "1f831a35-8944-4e9a-b459-38364df365ea",
                "43724de8-93cb-411c-a9fe-322a62756d04", "b1b323be-41ea-437f-bf2f-03ad0bd758f2",
                "db2bb56c-5af5-499b-acc5-2bcf29123e6c"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[23], new String[] { "e218ad4a-4797-4818-89a9-0b0580db4274",
                "f298779d-2417-4b2d-b6b8-1f9df9acf481", "751a3383-7398-469f-a35f-3a86a08d8fcd",
                "0cc3f7f0-f583-4920-baa9-2024284acd10", "31476a55-fad6-4d5a-afc6-266294bee0a4",
                "f1f881fd-83af-444e-9331-1d6ac7cd60c9", "b4c42bfd-7ad4-41ef-b650-31a135c660ce",
                "bed66095-78f5-46e5-b277-2ca5ee7adb2d", "d1650a52-b6e0-415b-9781-38504be570ac",
                "1f831a35-8944-4e9a-b459-38364df365ea"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[24], new String[] { "40a0ba63-3af9-49ae-a42c-20e43005b36d",
                "59ac3691-ea0d-4237-a468-0573ef07e895", "bed66095-78f5-46e5-b277-2ca5ee7adb2d",
                "d1650a52-b6e0-415b-9781-38504be570ac", "e218ad4a-4797-4818-89a9-0b0580db4274"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[25], new String[] { "e218ad4a-4797-4818-89a9-0b0580db4274" });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[26], new String[] { "a81a21b6-bfd7-4fd3-a07e-04025a078a7d",
                "780bb947-1124-4f6f-b55c-2b02dbb03c64", "ea8396c8-5e13-4a62-ba62-17cade2c57a5"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[27], new String[] { "ea8396c8-5e13-4a62-ba62-17cade2c57a5",
                "c8f59411-d74f-4e93-b70f-370c6b297e96", "db9229fe-2511-4939-8f9a-18d17e674e0c"
        });
        CHALLENGE_REFERRAL_MAPPINGS.put(CHALLENGE_UUIDS[28], new String[] { "a81a21b6-bfd7-4fd3-a07e-04025a078a7d",
                "19fbec43-8c0b-478b-9d5f-00ec6ec57511", "a81a21b6-bfd7-4fd3-a07e-04025a078a7d",
                "19fbec43-8c0b-478b-9d5f-00ec6ec57511", "b4c42bfd-7ad4-41ef-b650-31a135c660ce" });

        CHALLENGE_ROWS = new JSONArray();

        final JSONArray blankArrayForSelfHelpGuideQuestions = new JSONArray();

        for ( int index = 0; index < CHALLENGE_UUIDS.length; index++ ) {
            JSONObject temp = new JSONObject();
            temp.put("id", CHALLENGE_UUIDS[index]);
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("objectStatus", "ACTIVE");
            temp.put("name", CHALLENGE_NAMES[index]);
            temp.put("description", CHALLENGE_DESCRIPTIONS[index]);
            temp.put("selfHelpGuideDescription", CHALLENGE_SELF_HELP_GUIDE_DESCRIPTIONS[index]);
            temp.put("selfHelpGuideQuestion", CHALLENGE_SELF_HELP_GUIDE_QUESTIONS[index]);
            temp.put("selfHelpGuideQuestions", blankArrayForSelfHelpGuideQuestions);

            if ( index != 1 ) {
                temp.put("showInStudentIntake", true);
            } else {
                temp.put("showInStudentIntake", false);
            }

            temp.put("showInSelfHelpSearch", true);
            temp.put("tags", CHALLENGE_TAGS[index]);
            temp.put("defaultConfidentialityLevelId", null);

            JSONArray tempReferralArray = loadChallengeReferralsForChallenge(
                    CHALLENGE_REFERRAL_MAPPINGS.get(CHALLENGE_UUIDS[index]));

            temp.put("challengeChallengeReferrals", tempReferralArray);
            temp.put("referralCount", tempReferralArray.size());

            CHALLENGE_ROWS.add(temp);
        }

        CHALLENGE_RESPONSE = new JSONObject();
        CHALLENGE_RESPONSE.put("success", "true");
        CHALLENGE_RESPONSE.put("message", "");
        CHALLENGE_RESPONSE.put("results", CHALLENGE_ROWS.size());
        CHALLENGE_RESPONSE.put("rows", CHALLENGE_ROWS);

    }


    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsChallengeReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject)CHALLENGE_ROWS.get(0)).clone();
        testPostPutNegative.put("name", "testPostUnAuth");

  //      referenceAuthenticationControlledMethodNegativeTest(CHALLENGE_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsChallengeReferenceChallengeReferral() {

   /*     //tests permission on get /{id}/challengeReferral method
        expect()
            .statusCode(403)
        .when()
            .get(CHALLENGE_REFERRAL_PATH);

        //tests permission on post /{id}/challengeReferral method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(UUID.fromString(CHALLENGE_REFERRAL_FULL_FOR_POST_RESPONSE.get("id").toString()))
        .when()
            .post(CHALLENGE_REFERRAL_PATH);

        //tests permission on delete /{id}/challengeReferral method
        expect()
            .statusCode(403)
        .given()
            .contentType("application/json")
            .body(UUID.fromString(CHALLENGE_REFERRAL.get("id").toString()))
        .when()
            .delete(CHALLENGE_REFERRAL_PATH);   */
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferenceAllBody() {

 //       testResponseBody(CHALLENGE_PATH, CHALLENGE_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferenceSingleItemBody() {

    /*      for ( Object a : CHALLENGE_ROWS ) {

             Response test = expect()
                  .contentType("application/json")
              .when()
                  .get(CHALLENGE_PATH + "/" +((JSONObject) a).get("id"));

              Map test2 = test.getBody().jsonPath().getMap("");
              Set keys = ((JSONObject) a).keySet();

              System.out.println("\n\n********************************************************************\n\n");

              for ( Object key : keys ) {
                  Object temp239 = ((JSONObject)a).get(key);
                  Object temp392 = test2.get(key);
             //     System.out.println("[Key = " + key.toString() + " |\nAct= " + temp392 + " \n\nExp= " + temp239 +"\n\n");

                  if ( temp239 != null && temp392 != null ) {
                      if ( !temp392.equals(temp239) ) {

             //           System.out.println("\n***NOT EQUALS : " + key.toString() + "\n\n");
                      }

                  }
              }

              System.out.println("\n\n********************************************************************\n\n");


              testSingleItemResponseBody(CHALLENGE_PATH, (JSONObject) a);     */



  /*        String toGet = "selfHelpGuideQuestions";
              System.out.println("\nTESTING:"+ toGet +"\n\n");
            Object temp23 = ((JSONObject)a).get(toGet);

           String id =  ((JSONObject) a).get("id").toString();

              System.out.println("\n Testing : " + id + "\n\n");


          expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body(toGet, equalTo(temp23))
         .when()
            .get(CHALLENGE_PATH + "/" + id);
          }                 */

 //       testSingleItemResponseBody(CHALLENGE_PATH, (JSONObject) CHALLENGE_ROWS.get(1));

    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferenceSingleItemBodyChallengeReferral() {

   /*     //tests get /{id}/challengeReferral method
        expect()
            .contentType("application/json")
            .statusCode(200)
            .log().ifError()
            .body("", equalTo(CHALLENGE_REFERRAL_RESPONSE))
        .when()
            .get(CHALLENGE_REFERRAL_PATH);  */
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsChallengeReference() {

   //     testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CHALLENGE_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsChallengeReferenceChallengeReferral() {

  //      testUnsupportedMethods(new String[]{"GET", "POST", "DELETE"}, CHALLENGE_REFERRAL_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ((JSONObject)CHALLENGE_ROWS.get(3)).clone();
        testPostPutPositive.put("name", "testPostPositive");

 //       referencePositiveSupportedMethodTest(CHALLENGE_PATH, ((JSONObject) CHALLENGE_ROWS.get(4)).get("id").toString()
  //              testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferenceSupportedMethodsPositiveChallengeReferral() {

        int checkResultCount = 1;

/*        //get /id/challenge
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CHALLENGE_REFERRAL_PATH);

        //post /id/challenge
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(UUID.fromString(CHALLENGE_REFERRAL_FULL_FOR_POST_RESPONSE.get("id").toString()))
        .when()
            .post(CHALLENGE_REFERRAL_PATH);

        //verify post worked      /challenge
        Response postResponse = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount + 1))
        .when()
            .get(CHALLENGE_REFERRAL_PATH);

        assertEquals( CHALLENGE_REFERRAL_FULL_FOR_POST_RESPONSE.get("id").toString(),
                postResponse.getBody().jsonPath().getJsonObject("rows[1].id").toString() );

        //delete   /challenge
        expect()
            .statusCode(200)
            .log().ifError()
        .given()
            .contentType("application/json")
            .body(UUID.fromString(CHALLENGE_REFERRAL_FULL_FOR_POST_RESPONSE.get("id").toString()))
        .when()
            .delete(CHALLENGE_REFERRAL_PATH);

        //get (verify result # matches)  /challenge
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
            .get(CHALLENGE_REFERRAL_PATH);      */
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject)CHALLENGE_ROWS.get(5)).clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = (JSONObject) CHALLENGE_ROWS.get(6);

   //     referenceNegativeSupportedMethodTest(CHALLENGE_PATH, testNegativePostObject, testNegativeValidateObject);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferenceSupportedMethodsNegativeChallengeReferral() {

        final String nonExistentUUID = "70b982b0-68d7-11e3-949a-0800200c9a66";
        final UUID testPostInvalid = UUID.fromString(nonExistentUUID);
        final JSONObject testGetInvalid = new JSONObject();
        int checkResultCount = 0;

        //get /challenge
 /*       Response checkItemCount = expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
        .when()
            .get(CHALLENGE_REFERRAL_PATH);

        String result = checkItemCount.getBody().jsonPath().getJsonObject("results").toString();

        if ( StringUtils.isNotBlank(result) ) {
            checkResultCount = Integer.parseInt(result);
        } else {
            LOGGER.error("Get all method failed in Negative Test! No results returned.");
            fail("GET all failed Negative Tests.");
        }

        testGetInvalid.put("success", "true");
        testGetInvalid.put("message", "");
        testGetInvalid.put("results", 0);
        testGetInvalid.put("rows", new JSONArray());

        //get invalid id   /challengeChallengeReferral
        expect()
            .statusCode(404)
            .contentType("application/json")
        .when()
            .get(CHALLENGE_PATH + "/" + nonExistentUUID);

        //post unassigned uuid name /challenge
        expect()
            .statusCode(404)
        .given()
            .contentType("application/json")
            .body(testPostInvalid)
        .when()
            .post(CHALLENGE_REFERRAL_PATH);


        //delete  /challenge
        expect()
            .statusCode(404)
        .given()
            .contentType("application/json")
            .body(UUID.fromString(nonExistentUUID))
        .when()
            .delete(CHALLENGE_REFERRAL_PATH);

        //get all (verify result # is unchanged)    /challenge
        expect()
            .statusCode(200)
            .log().ifError()
            .contentType("application/json")
            .body("results", equalTo(checkResultCount))
        .when()
            .get(CHALLENGE_REFERRAL_PATH);   */
    }


    private static final JSONArray loadChallengeReferralsForChallenge( final String[] challengeReferralIDs ) {

        final JSONArray jsonArrayOfReferralsToReturn = new JSONArray();
        int referralUUIDIndex;

        for ( String challengeReferralID : challengeReferralIDs ) {

            JSONObject tempChallengeReferral = new JSONObject();

           for (referralUUIDIndex = 0; referralUUIDIndex < CHALLENGE_REFERRAL_UUIDS.length; referralUUIDIndex++) {
                if ( challengeReferralID.equals(CHALLENGE_REFERRAL_UUIDS[referralUUIDIndex]) ) {
                    break;
                }
           }

            if ( referralUUIDIndex < CHALLENGE_REFERRAL_UUIDS.length ) {

                tempChallengeReferral.put("id", CHALLENGE_REFERRAL_UUIDS[ referralUUIDIndex ]);
                tempChallengeReferral.put("createdDate", getDefaultCreatedModifiedByDate());
                tempChallengeReferral.put("createdBy", getDefaultCreatedModifiedBy());
                tempChallengeReferral.put("modifiedDate", getDefaultCreatedModifiedByDate());
                tempChallengeReferral.put("modifiedBy", getDefaultCreatedModifiedBy());
                tempChallengeReferral.put("objectStatus", "ACTIVE");
                tempChallengeReferral.put("name", CHALLENGE_REFERRAL_NAMES[referralUUIDIndex]);
                tempChallengeReferral.put("description", CHALLENGE_REFERRAL_DESCRIPTIONS[referralUUIDIndex]);
                tempChallengeReferral.put("publicDescription", CHALLENGE_REFERRAL_PUBLIC_DESCRIPTIONS[referralUUIDIndex]);
                tempChallengeReferral.put("showInSelfHelpGuide", CHALLENGE_REFERRAL_SELF_HELP_GUIDE_VALUES[referralUUIDIndex]);

                if ( referralUUIDIndex != 24 && referralUUIDIndex != 25 && referralUUIDIndex != 54  ) {
                    tempChallengeReferral.put("showInStudentIntake", null);
                } else {
                    tempChallengeReferral.put("showInStudentIntake", true);
                }

                tempChallengeReferral.put("link", CHALLENGE_REFERRAL_LINKS[referralUUIDIndex]);

                jsonArrayOfReferralsToReturn.add(tempChallengeReferral);

            } else {
                fail("ERROR: ChallengeReferral not found! Index: " + referralUUIDIndex + " of " + challengeReferralIDs.length +
                        " and ReferralIDs given: [" + challengeReferralIDs.toString() + "]. \n");
            }
        }

        return jsonArrayOfReferralsToReturn;
    }
}
