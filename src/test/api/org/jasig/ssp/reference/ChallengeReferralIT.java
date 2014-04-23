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


public class ChallengeReferralIT extends AbstractReferenceTest {

    private static final String CHALLENGE_REFERRAL_PATH = REFERENCE_PATH + "challengeReferral";

    private static final String[] CHALLENGE_REFERRAL_UUIDS;
    private static final String[] CHALLENGE_REFERRAL_NAMES;
    private static final String[] CHALLENGE_REFERRAL_DESCRIPTIONS;
    private static final String[] CHALLENGE_REFERRAL_PUBLIC_DESCRIPTION;
    private static final boolean[] CHALLENGE_REFERRAL_SHOW_IN_SELF_HELP_GUIDE_VALUES;
    private static final boolean[] CHALLENGE_REFERRAL_SHOW_IN_STUDENT_INTAKE_VALUES;
    private static final String[] CHALLENGE_REFERRAL_LINKS;

    private static final JSONArray CHALLENGE_REFERRAL_ROWS;
    private static final JSONObject CHALLENGE_REFERRAL_RESPONSE;

    static {

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
                "Academic Petition", "Admission Application", "Alcoholics Anonymous", "Attendance Issues",
                "Campus Organization", "Career Coach", "Child Support Enforcement Agency", "Class Grief Loss",
                "Computer Access", "Cope Workshop", "Counseling Services",
                "Counseling Weekend Intervention Program", "Courses and Class Schedule", "CrisisCare",
                "Day Vest", "Dialogue on Race Relations", "Fafsa Student Guide Spanish", "Family Services",
                "Fee Bill", "Financial Aid Award", "Financial Aid Deadlines", "Financial Aid Office Application",
                "Health and well being Handouts", "Holistic Counseling Process", "Improve English Skills",
                "Improvement in Class Work", "Individualizing a Degree Program", "Large Font Size", "Lassi",
                "Lassi Results Handout", "Learning Labs", "Medicaid Programs", "Meet with Instructor",
                "Mhs Community Referral", "Minority Peer Group", "MyPass Card Transportation",
                "Open Learning Labs", "Orientation", "Parenting Skills",
                "Part Time Demo College Opportunity Grant", "Payment", "Pell Transfer to Student Card",
                "Personal Interest Career Development Transient or HS Student", "Placement Testing", "Resource Room",
                "Stress Management Classes", "Student Impact Survey", "Student Judicial Affairs",
                "Student Success Course", "Student Support Services", "Study Skills Website",
                "Substance Abuse Treatment", "Test", "Tutorial Services and Labs", "Type", "Typology Test",
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

        CHALLENGE_REFERRAL_PUBLIC_DESCRIPTION = new String[] {
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

        CHALLENGE_REFERRAL_SHOW_IN_SELF_HELP_GUIDE_VALUES = new boolean[] {
                true, true, false, false, true, false, false, true, true, false, true, false, true, true, true,
                true, true, true, true, true, true, true, false, false, false, false, true, false, true, false,
                true, true, true, false, true, true, true, true, true, true, true, true, true, true, true, true,
                false, true, true, true, true, true, false, true, false, true, true
        };
        
        CHALLENGE_REFERRAL_SHOW_IN_STUDENT_INTAKE_VALUES = new boolean[] {
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

        CHALLENGE_REFERRAL_ROWS = new JSONArray();

        for ( int index = 0; index < CHALLENGE_REFERRAL_UUIDS.length; index++ ) {
            JSONObject temp = new JSONObject();
            temp.put("id", CHALLENGE_REFERRAL_UUIDS[index]);
            temp.put("createdDate", getDefaultCreatedModifiedByDate());
            temp.put("createdBy", getDefaultCreatedModifiedBy());
            temp.put("modifiedDate", getDefaultCreatedModifiedByDate());
            temp.put("modifiedBy", getDefaultCreatedModifiedBy());
            temp.put("objectStatus", "ACTIVE");
            temp.put("name", CHALLENGE_REFERRAL_NAMES[index]);
            temp.put("description", CHALLENGE_REFERRAL_DESCRIPTIONS[index]);
            temp.put("publicDescription", CHALLENGE_REFERRAL_PUBLIC_DESCRIPTION[index]);
            temp.put("showInSelfHelpGuide", CHALLENGE_REFERRAL_SHOW_IN_SELF_HELP_GUIDE_VALUES[index]);
            temp.put("showInStudentIntake", CHALLENGE_REFERRAL_SHOW_IN_STUDENT_INTAKE_VALUES[index]);

            temp.put("link", CHALLENGE_REFERRAL_LINKS[index]);

            CHALLENGE_REFERRAL_ROWS.add(temp);
        }

        CHALLENGE_REFERRAL_RESPONSE = new JSONObject();
        CHALLENGE_REFERRAL_RESPONSE.put("success", "true");
        CHALLENGE_REFERRAL_RESPONSE.put("message", "");
        CHALLENGE_REFERRAL_RESPONSE.put("results", CHALLENGE_REFERRAL_ROWS.size());
        CHALLENGE_REFERRAL_RESPONSE.put("rows", CHALLENGE_REFERRAL_ROWS);
    }

    @Test
    @ApiAuthentication(mode="unauth")
    public void testPermissionProtectedMethodsChallengeReferralReference() {
        final JSONObject testPostPutNegative = (JSONObject) ((JSONObject) CHALLENGE_REFERRAL_ROWS.get(0)).clone();
        testPostPutNegative.put("name", "testPostUnAuth");

  //      referenceAuthenticationControlledMethodNegativeTest(CHALLENGE_REFERRAL_PATH, testPostPutNegative);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferralReferenceAllBody() {

//        testResponseBody(CHALLENGE_REFERRAL_PATH, CHALLENGE_REFERRAL_RESPONSE);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferralReferenceSingleItemBody() {

        testSingleItemResponseBody(CHALLENGE_REFERRAL_PATH, ((JSONObject) CHALLENGE_REFERRAL_ROWS.get(1)));
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testUnsupportedMethodsChallengeReferralReference() {

 //       testUnsupportedMethods(REFERENCE_SUPPORTED_METHODS, CHALLENGE_REFERRAL_PATH);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferralReferenceSupportedMethodsPositive() {
        final JSONObject testPostPutPositive = (JSONObject) ((JSONObject) CHALLENGE_REFERRAL_ROWS.get(2)).clone();
        testPostPutPositive.put("name", "testPostPositive");

 //       referencePositiveSupportedMethodTest(CHALLENGE_REFERRAL_PATH, CHALLENGE_REFERRAL_UUIDS[3], testPostPutPositive);
    }

    @Test
    @ApiAuthentication(mode="auth")
    public void testChallengeReferralReferenceSupportedMethodsNegative() {
        final JSONObject testNegativePostObject = (JSONObject) ((JSONObject) CHALLENGE_REFERRAL_ROWS.get(4)).clone();
        testNegativePostObject.put("name", ("testPostNegative" + testPassDeConflictNumber));
        final JSONObject testNegativeValidateObject = ((JSONObject) CHALLENGE_REFERRAL_ROWS.get(5));

 //       referenceNegativeSupportedMethodTest(CHALLENGE_REFERRAL_PATH, testNegativePostObject, testNegativeValidateObject);
    }
}