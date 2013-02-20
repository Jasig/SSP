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
/*
 * 
 * Generates an xml liquibase change set for testing large database sets.
 * Usage:  
 * BASE_LOCATION_CHANGESET set this to either TEST_LOCATION_CHANGESET or FULL_DATA_BASE_LOCATION_CHANGESET depending on which datachange set you wish to generate
 * fileName is the name of the changeset file to be generated
 * WRITE_UPORTAL_USERS set to true if you want to generate the user.xml files to register the users with uportal (coaches only)
 * BASE_LOCATION_UPORTAL_USERS set the appropriate location for you machine
 * author YOU
 * filed description:  this is the description of the changeset that is stored in the databasechangelog table
 * TOTAL_NUMBER_OF_COACHES 
 * STUDENT_MULTIPLIER set to 1 if you want all coaches to have the same number of students.
 * BASE_NUMBER_OF_STUDENTS if STUDENT_MULTIPLIER is one all coaches will have this number of students.
 * 
 */

import java.util.Calendar;
import java.util.Date;
import java.util.UUID
import org.jasig.ssp.model.Person
import static Constants.*
class Constants{
	
	static final campusId = "901e104b-4dc7-43f5-a38e-581015e204e1"
	static final base_created_date = "2012-08-20T00:00:00"

	/* Change Sets, change as needed */
	static final fileName = "000012-test.xml";
	static final TEST_LOCATION_CHANGESET = './src/test/resources/org/jasig/ssp/database/changesets/'
	static final FULL_DATA_BASE_LOCATION_CHANGESET = './src/main/resources/org/jasig/ssp/database/testingchangesets/'
	static final BASE_LOCATION_CHANGESET = TEST_LOCATION_CHANGESET
	static final WRITE_UPORTAL_USERS = false;
	static final BASE_LOCATION_UPORTAL_USERS = "/Users/jamesstanley/Documents/opensource/uPortal/uportal-war/src/main/data/ssp_entities/user/"
	static final author = "james.stanley"
	static final fileDescription = "Adding Randomized Data Set"
	
	
	/* parameter values for generation */
	
	static final TOTAL_NUMBER_OF_COACHES = 100 as Integer
	static final STUDENT_MULTIPLIER = 4 as Integer //use this to set the multiplier that determines the number of students a coach has. (coachIndex * STUDENT_MULTIPLIER)
	static final BASE_NUMBER_OF_STUDENTS = 15 as Integer
	static final MAX_EARLY_ALERTS = 8 as Integer
	static final MAX_EARLY_ALERT_RESPONSES = 4 as Integer
	static final MAX_MANY_TO_MANY = 8 as Integer
	static final MAXIMUM_TASKS = 10 as Integer
	static final MAXIMUM_JOURNAL_ENTRIES = 8 as Integer
	static final FREQUENCY_OF_EARLY_ALERT_RESPONSE = 3 as Integer
	static final FREQUENCY_OF_EARLY_ALERT_SUGGESTION = 2 as Integer
	static final FREQUENCY_OF_EARLY_ALERT_REASON = 1 as Integer
	static final FREQUENCY_CLOSED_EARLY_ALERTS = 4 as Integer
	static final FREQUENCY_DISABILITY = 3 as Integer
	
	static final BASE_COACH_NAME = "Coach"
	static final BASE_STUDENT_NAME = "Student"
	static final STUDENT_TYPE_ID_PARAMS = [max:3,mod:3]  // array size 3, where max is less than size of id array mod greater than array size causes mod-size null selections
	static final PROGRAM_STATUS_ID_PARAMS = [max:5,mod:5] // array size 5
	static final EARLY_ALERT_OUTREACH_ID_PARAMS = [max:5,mod:8] // array size 5
	static final EARLY_ALERT_OUTCOME_ID_PARAMS = [max:6,mod:8] // array size 6
	static final EARLY_ALERT_REASON_ID_PARAMS = [max:10,mod:14] // array size 10
	static final EARLY_ALERT_REFERRAL_ID_PARAMS = [max:10,mod:14] // array size 10
	static final EARLY_ALERT_SUGGESTION_ID_PARAMS = [max:10,mod:6] // array size 10
	static final JOURNAL_SOURCE_ID_PARAMS = [max:9,mod:8] // array size 9
	static final CONFIDENTIALITY_LEVEL_ID_PARAMS = [max:12,mod:12] // array size 12
	static final CHALLENGE_ID_PARAMS = [max:30,mod:30] // array size 30
	static final CHALLENGE_REFERRAL_ID_PARAMS = [max:56,mod:46] // array size 56
	static final JOURNAL_TRACK_ID_PARAMS = [max:3,mod:3] // array size 3
	static final SPECIAL_SERVICE_GROUP_ID_PARAMS = [max:2,mod:4] // array size 2
	static final REFERRAL_SOURCE_ID_PARAMS = [max:3,mod:5] // array size 2
	
	static final DISABILITY_AGENCY_PARAMS = [max:6,mod:6] // array size 6
	static final DISABILITY_ACCOMMODATION_PARAMS = [max:21,mod:21] // array size 21
	static final DISABILITY_STATUS_PARAMS = [max:5,mod:5] // array size 5
	static final DISABILITY_TYPE_PARAMS = [max:14,mod:14] // array size 14
	static final EDUCATION_GOAL_PARAMS = [max:9,mod:9] // array size 9
	static final MAJOR_PARAMS = [max:9,mod:9] // array size 9
	static final VETERAN_STATUS_PARAMS = [max:6,mod:6] // array size 6
	static final ETHNICITY_PARAMS = [max:8,mod:8] // array size 8
	
	static final ANTICIPATED_START_TERM = "FA12"
	static final ANTICIPATED_START_YEAR = 2013
	static final ACTUAL_START_TERM = "FA12"
	static final ACTUAL_START_YEAR = 2013
	
	
	static final CLOSED_EARLY_ALERT_OFF_SET_DATE_PARAMS = [baseOffsetDays:30, mod:9]
	static final EARLY_ALERT_OFF_SET_DATE_PARAMS = [baseOffsetDays:1, mod:10]
	
/* Set of Static Ideas that are generated in the test sets and used should not change */
	
static final studentTypeIds = ["b2d058eb-5056-a51a-80a7-8a20c30d1e91",
	"b2d05919-5056-a51a-80bd-03e5288de771",
	"b2d05939-5056-a51a-8004-d803265d2645" ] as String[]

static final programStatusIds = ["b2d12527-5056-a51a-8054-113116baab88",
	"b2d125a4-5056-a51a-8042-d50b8eff0df1",
	"b2d125c3-5056-a51a-8004-f1dbabde80c2",
	"b2d125e3-5056-a51a-800f-6891bc7d1ddc",
	"b2d12640-5056-a51a-80cc-91264965731a"] as String[]
	
static final earlyAlertOutreachIds = ["3383d46f-8051-4a86-886d-a3efe75b8f3a",
	"612ed2c5-6d9a-4cda-9007-b22756888ca8",
	"9842eff0-6557-4fb2-81c2-614991d5cbfb",
	"ba387302-b08b-4dc2-bd3f-aefc2fc8d092",
	"e7908476-e67d-4fb2-890b-2d4e6c9b0e42"] as String[]

static final earlyAlertOutcomeIds = ["077a1d57-6c85-42f7-922b-7642be9f70eb",
		"12a58804-45dc-40f2-b2f5-d7e4403acee1",
		"14e390d5-2371-48b4-a9de-2d35bc18e868",
		"7148606f-9034-4538-8fc2-c852a5c912ee",
		"9a98ff78-92af-4681-8111-adb3300cbe1c",
		"d944d62e-0974-4058-85c7-e6b3d6159d73"] as String[]

static final earlyAlertReasonIds = ["1f5729af-0337-4e58-a001-8a9f80dbf8aa",
		"300d68ef-38c2-4b7d-ad46-7874aa5d34ac",
		"b2d112a9-5056-a51a-8010-b510525ea3a8",
		"b2d112b8-5056-a51a-8067-1fda2849c3e5",
		"b2d112c8-5056-a51a-80d5-beec7d48cb5d",
		"b2d112d7-5056-a51a-80aa-795e56155af5",
		"b2d112e7-5056-a51a-80e8-a30645c463e4",
		"b2d11316-5056-a51a-80f9-79421bdf08bf",
		"b2d11326-5056-a51a-806c-79f352d0c2b2",
		"b2d11335-5056-a51a-80ea-074f8fef94ea"] as String[]
		
static final earlyAlertReferralIds = ["1f5729af-0337-4e58-a001-8a9f80dbf8aa",
		"300d68ef-38c2-4b7d-ad46-7874aa5d34ac",
		"b2d112a9-5056-a51a-8010-b510525ea3a8",
		"b2d112b8-5056-a51a-8067-1fda2849c3e5",
		"b2d112c8-5056-a51a-80d5-beec7d48cb5d",
		"b2d112d7-5056-a51a-80aa-795e56155af5",
		"b2d112e7-5056-a51a-80e8-a30645c463e4",
		"b2d11316-5056-a51a-80f9-79421bdf08bf",
		"b2d11326-5056-a51a-806c-79f352d0c2b2",
		"b2d11335-5056-a51a-80ea-074f8fef94ea"] as String[]
		
static final earlyAlertSuggestionIds = ["b2d11141-5056-a51a-80c1-c1250ba820f8",
			"b2d11151-5056-a51a-8051-3acdf99fef84",
			"b2d11160-5056-a51a-807d-9897c14bdd44",
			"b2d11170-5056-a51a-8002-b5ce9f25e2bc",
			"b2d111be-5056-a51a-8075-c5a65da17079",
			"b2d111ce-5056-a51a-8017-676c4d8c4f1d",
			"b2d111dd-5056-a51a-8034-909bd6af80d5",
			"b2d111ed-5056-a51a-8046-5291453e8720",
			"b2d111fd-5056-a51a-80fe-6f3344174dbe",
			"b2d1120c-5056-a51a-80ea-c779a3109f8f"] as String[]
			
static final journalSourceIds =["b2d07973-5056-a51a-8073-1d3641ce507f",
			"b2d07983-5056-a51a-80d0-3fbcf39fa253",
			"b2d07993-5056-a51a-8076-d6513a772d80",
			"b2d079a2-5056-a51a-8075-328971518ff0",
			"b2d079b2-5056-a51a-80c8-753b509bc90b",
			"b2d079c1-5056-a51a-80e1-c40f1e56af00",
			"b2d079f0-5056-a51a-8013-b982c069afb7",
			"b2d07a00-5056-a51a-80b5-f725f1c5c3e2",
			"b2d07a10-5056-a51a-80c2-2dbb2a81aae6"
			] as String[]
			
static final confidentialityLevelIds = ["afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c",
				"b2d077a7-4055-0510-7957-4a09f93a0346",
				"b2d078b8-5056-a51a-8057-4a09f93a0347",
				"b2d07906-5056-a51a-80fd-9d19f636e501",
				"b2d07915-5056-a51a-80ce-334eca0e0a10",
				"b2d07935-5056-a51a-80db-caa0dadd3f2e",
				"b2d07944-5056-a51a-8047-654512d4d083",
				"b2d07964-5056-a51a-800a-a95d3bb3c4d1",
				"b2d07964-5157-a51a-800a-a95d3bb3c4e3",
				"b2d07964-5157-a51a-800a-a95d3bb3c4f4",
				"b2d07964-5157-a51a-800a-a95d3bb3c505",
				"b3d077a7-4055-0510-7967-4a09f93a0357"
			] as String[]
		
static final challengeIds = ["01bb0a62-1756-4ea2-857d-5821ee54a1b9",
			"07b5c3ac-3bdf-4d12-b65d-94cb55167998",
			"1f5b63a9-9b50-412b-9971-23602f87444c",
			"22d23035-74f0-40f1-ac41-47a22c798af7",
			"2fd9ab0b-4afb-43d3-8ae2-5cf462c847e5",
			"33c6207a-3302-405f-8a2e-9d9bd750dac0",
			"38f7ae25-902f-4381-851e-2e2319adb1bd",
			"431abcf2-43fe-4d6a-8f83-b47c91157a15",
			"43719c57-ec92-4e4a-9fb6-25208936fd18",
			"5d6dc03f-b000-42b1-a078-253c55867ff1",
			"609527d4-e768-4caa-a65a-2bb3f3da2948",
			"615225df-c3b7-4ae2-a828-6fad663c629b",
			"6c34e91c-3ef3-4b3f-8061-fab1b7ff59ca",
			"6e4b6a6c-8b67-48df-ac7f-c9f225e872b8",
			"70693703-b2c1-4d3c-b79f-e43e93393b8c",
			"72de7c95-eab3-46b2-93cf-108397befcbb",
			"7ad819ef-d1e3-4ebf-a05b-e233f17f9e55",
			"7c0e5b76-9933-484a-b265-58cb280305a5",
			"80c5b019-1946-4a98-a7fd-b8d62684558c",
			"af7e472c-3b7c-4d00-a667-04f52f560940",
			"b9ac1cb5-d40a-4451-8ec2-08240698aaf3",
			"bd886899-96d5-4ec8-9d6c-3cb2d4e0f09b",
			"c1428288-ec1e-432e-87df-6567d9618f42",
			"cab7d5a5-2ca5-4af7-a644-b3882ddc9b41",
			"dbb8741c-ece0-4830-8ebf-774151cb6a1b",
			"eb0dc2c0-3157-43c1-8b2e-55a5c2e2f4c3",
			"f067c6ca-50ad-447a-ad12-f47dffdce42e",
			"f5bb0a62-1756-4ea2-857d-5821ee44a1d0",
			"f6bb0a62-1756-4ea2-857d-5821ee44a1da",
			"fb206a68-78db-489d-9d5d-dce554f54eed"
			] as String[]
			
static final challengeReferralIds = ["03f7d2ea-504e-4b31-9099-30ca9ecd2afa",
		"0baf5091-398d-408d-81d9-3763695dbd7a",
		"0cc3f7f0-f583-4920-baa9-2024284acd10",
		"1086f9d4-90da-418c-a106-004e29490093",
		"19fbec43-8c0b-478b-9d5f-00ec6ec57511",
		"1f831a35-8944-4e9a-b459-38364df365ea",
		"238aebce-887d-480a-b63b-122935d60337",
		"31476a55-fad6-4d5a-afc6-266294bee0a4",
		"3d8a27c6-920f-462b-8730-1fa91da9f78c",
		"40a0ba63-3af9-49ae-a42c-20e43005b36d",
		"43724de8-93cb-411c-a9fe-322a62756d04",
		"46f21d93-5056-a51a-80fe-16fb711058e6",
		"4e2aac3f-5056-a51a-8034-10522b001062",
		"5044bf0f-3eb1-47e5-9d30-3b60b7ed63bb",
		"51a92fd5-7639-46dd-accc-215305e46bc0",
		"56aa29ef-e13e-4a26-b151-16e0cb1f9e86",
		"59ac3691-ea0d-4237-a468-0573ef07e895",
		"660da4ff-7b02-4fa0-b0da-160e488b947b",
		"6e68497f-d4af-4f51-a9f7-251f7e2f680c",
		"74b23392-139d-4dfb-a808-29b9a1265214",
		"751a3383-7398-469f-a35f-3a86a08d8fcd",
		"75830250-d95d-4187-b8cd-38a110d1a832",
		"75e20872-17c8-436e-a3c0-07319f818c26",
		"780bb947-1124-4f6f-b55c-2b02dbb03c64",
		"79de802e-afd6-46e3-99f8-134b2a41d8a0",
		"7a69f78f-ecbf-48fb-8747-121c65cee660",
		"7cc73413-02b9-4f8b-aa3f-38b082abc706",
		"7eda3a29-5fe8-44c0-b86f-0f084b51b228",
		"7fa465ae-c8cd-430c-a263-04c6a5046068",
		"8c1612bc-d8cd-4878-8d5d-1e330ee8c136",
		"96cc3609-0ec2-42d6-8123-0b15492a9d2c",
		"a81a21b6-bfd7-4fd3-a07e-04025a078a7d",
		"b17d9ff8-b28c-40a9-8779-1b3d7a9d64c1",
		"b1b323be-41ea-437f-bf2f-03ad0bd758f2",
		"b4c42bfd-7ad4-41ef-b650-31a135c660ce",
		"bc7967cf-bf1a-4445-885c-19705a0e686b",
		"bed66095-78f5-46e5-b277-2ca5ee7adb2d",
		"c72ef7ef-ea06-4282-92ad-17153e136bb5",
		"c8f59411-d74f-4e93-b70f-370c6b297e96",
		"c9b60122-466c-4a25-a1e3-1a5e946468a5",
		"d1650a52-b6e0-415b-9781-38504be570ac",
		"d9b99e47-1714-450a-8db1-2d3913ac90cb",
		"db2bb56c-5af5-499b-acc5-2bcf29123e6c",
		"db9229fe-2511-4939-8f9a-18d17e674e0c",
		"de37299c-feac-4e71-9583-13a91c44370c",
		"e218ad4a-4797-4818-89a9-0b0580db4274",
		"e9e29c50-0e3e-4f3b-a0a8-1deb5ba7352f",
		"ea8396c8-5e13-4a62-ba62-17cade2c57a5",
		"eb4ddd20-ce13-417b-b110-2a54e1d471d5",
		"eba6b6c1-7d62-4b3d-8f61-1722ce93418b",
		"f1f881fd-83af-444e-9331-1d6ac7cd60c9",
		"f298779d-2417-4b2d-b6b8-1f9df9acf481",
		"fc523dca-a1ba-4711-9802-04e85c3174db",
		"fd01167f-f99c-456b-80ad-0fc44a99ac24",
		"fd6e5ff6-8907-4a2d-b3df-28fcc659a189",
		"fe7ce526-758f-4a2a-838a-1482adecec6b"
	] as String[]
	
static final disabilityAccommodationIds = ["0412e725-05d7-4161-84cd-fd77f494583d",
				"08dd4152-82db-429f-9a7f-7f9a749c46f7",
				"13588bfe-c9b9-4134-bb6f-ee875c2e8789",
				"1f18e705-187a-447d-8f03-8814fc854fc6",
				"2da4bd54-29b8-487e-ae81-82867c20631f",
				"3424f88b-614c-4686-af57-760ea0b57d2f",
				"3761d6a4-6f71-4c88-a1d7-f9bea7079346",
				"3c3a0e9d-ed9d-47ad-9a5c-c4afe4edbe69",
				"51155fbf-f681-4492-a059-37626e08e732",
				"595f1fb6-81f6-40d4-b82a-e0cce834156c",
				"6d745998-8a2f-4bf0-a104-0e0499c8f18f",
				"74603eff-ea00-413a-acd5-a55755b79778",
				"85138559-4d40-420d-bd78-119276a7b42c",
				"90ed8cf5-040c-4f68-83c3-863120da0388",
				"9c49cb24-0949-4472-988b-2f539fbe5843",
				"9e15bba9-b955-477f-968c-4336b9401f58",
				"a1d9b1c8-6841-46f5-9dda-5b7e4130fd42",
				"a3b9e31e-b3af-41b3-b602-151b60253938",
				"c2d81565-0dd5-4f1a-ad54-2640f4fd7749",
				"f0ff3e53-bf45-421e-8bbf-c0ef8a2049b4",
				"f4620bbb-35b5-4fac-9746-60e81abeb5b9",
				] as String[]

static final disabilityAgencyIds = ["02aa9557-c8f1-4716-bbae-2b3401e386f6",
"224b03d9-90da-4f9c-8959-ea2e97661f40",
"6b209a6d-70b9-416c-95a5-17cab4594f85",
"7845fdea-9da7-49be-a3cf-c9da03c38d56",
"7f92b5bb-8e9c-44c7-88fd-2ffdce68ef98",
"c0f0d778-5e07-44ed-aadf-6752c9a994ec",
] as String[]

static final disabilityStatusIds = ["00df56f6-f673-42ed-b73d-d4bceda0d24b",
"24d12b6f-1d58-4f13-ac5e-c09cd249ba43",
"5dab94e2-d5a0-4203-b462-8d0841d63786",
"c2609cdf-6aa2-4948-b0e3-3779f2541783",
"e0208429-aeb2-4854-ab7c-3c9281c96002",
] as String[]

static final disabilityTypeIds = ["00df56f6-f673-42ed-b73d-d4bceda0d24b",
"2715e5e8-34b2-4992-aea4-7f21b4b10bb9",
"35ed9080-e88b-473a-83a4-565c5c56a756",
"4afd60bf-a5ea-4215-abb9-8276a6b68827",
"6babd878-46ba-4106-b5e4-3651fdbf3a71",
"914d2329-28d0-4c79-b81e-c421af56476f",
"92a689f2-1850-4d7a-ae58-0ce349cfde6d",
"960e507a-2c82-4d6e-bf55-b12e4f0a3a86",
"997df364-627b-4fae-a58a-646e20d7ab6f",
"9e8377a3-ded3-4338-9780-8161748601fc",
"b7c3a3c0-de28-4d46-a42b-3de5f294a07f",
"c049aec0-9c5c-46a6-8586-604688e9ac69",
"c9afc03f-b7a2-4d8d-9603-09d313783a04",
"faf8ae05-a865-4696-8585-e7a7b65d3600",
] as String[]

		
static final journalTrackIds = ["b2d07a7d-5056-a51a-80a8-96ae5188a188",
					"b2d07abb-5056-a51a-8065-991dd0f74782",
					"b2d07b38-5056-a51a-809d-81ea2f3b27bf"
					] as String[]

static final specialServiceGroupIds = ['40b6b8aa-bca1-11e1-9344-037cb4088c72',
	"f6201a04-bb31-4ca5-b606-609f3ad09f87"] as String[]
	
	static final def eol = System.properties.'line.separator'
	
static final referralSourceIds = ["c54aa656-bd7a-11e1-9ced-5b723f71da43",
"ccadd634-bd7a-11e1-8d28-3368721922dc",
"f6201a04-bb31-4ca5-b606-609f3ad09f87"] as String[]
	


static final educationGoalIds = ["00afdf46-2f0e-46e4-9d56-bc45b9266642",
"5cccdca1-9a73-47e8-814f-134663a2ae67",
"6c466885-d3f8-44d1-a301-62d6fe2d3553",
"78b54da7-fb19-4092-bb44-f60485678d6b",
"7e3e6f05-612c-4636-a370-7b038e98510f",
"9bf33704-e41e-4922-bc7f-07b98b276824",
"d25e224b-a0ca-48f0-ac30-1ddf5bdb9e0d",
"d632046f-1fbf-4361-ac1e-3ca67f78e104",
"efeb5536-d634-4b79-80bc-1e1041dcd3ff"
] as String[]

static final majorIds = ["Physics", 
	"MATH", 
	"ENGISH", 
	"Aerospace", 
	"Biology",
	 "Physcial Ed", 
	 "Animation", 
	 "Basket Weaving",
	 "Tennis"] as String[]



	static final veteranStatusIds = ["4b584fdb-dbc8-44ff-a30d-8c3e0a2d8295",
	"5c584fdb-dcc8-44ff-a30d-8c3e0a2d8206",
	"7120ff28-67b1-4e44-b9f2-5f663c3c9a9c",
	"8d1bb38c-5670-469d-96a5-b8a79ae7856f",
	"8f66c758-aeb7-44e8-b343-48289c051a9b",
	"b106d9e6-6666-48c8-96e3-4dc0807c0557",
	] as String[]
	
	static final ethnicityIds = ["35efc2bd-c8df-4b2e-b821-43278fdd4839",
	"79a9a1a7-35ff-459c-9fed-a233d9421761",
	"83e7967f-fc67-408c-929f-fc361eece175",
	"9f73e1f0-66aa-47f6-a7bc-2daecb915207",
	"dec0364a-d576-424d-94ce-79544c21e8c8",
	"f6201a04-bb31-4ca5-b606-609f3ad09f87",
	"fa80f025-5405-4355-9747-84dd3fa66df6",
	"ff149156-a02f-4e9d-bfb2-ef9dfb32eef2"
	] as String[]

}

def writer = new FileWriter(BASE_LOCATION_CHANGESET + fileName)
def xml = new groovy.xml.MarkupBuilder(writer) as groovy.xml.MarkupBuilder
writer << '<?xml version="1.0" encoding="UTF-8" standalone="no"?>' << eol
writer << getXmlLicense() << eol
xml.databaseChangeLog( xmlns : "http://www.liquibase.org/xml/ns/dbchangelog"
					 , "xmlns:xsi" : "http://www.w3.org/2001/XMLSchema-instance"
					 , "xsi:schemaLocation" : "http://www.liquibase.org/xml/ns/dbchangelog http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-2.0.xsd http://www.liquibase.org/xml/ns/dbchangelog-ext http://www.liquibase.org/xml/ns/dbchangelog/dbchangelog-ext.xsd"
					 ) {
					 changeSet(author:author, id:fileDescription) {
						 	
							ArrayList<UUID> rollbackList = new ArrayList<UUID>()
							ArrayList<GroovyPerson> groovyPersons = new ArrayList<GroovyPerson>()
							generatePeople(xml, null, "", 0, TOTAL_NUMBER_OF_COACHES, rollbackList, groovyPersons)
							generateRollbacks(xml, 'person', rollbackList)
							
							if(WRITE_UPORTAL_USERS == true){
								def ldif_writer = new FileWriter('./src/main/config/devExternal/users_1.ldif')
								writeLdif(ldif_writer, groovyPersons)
								writeuPortUsers(groovyPersons)
							}
					 }
}


ArrayList<UUID> generatePeople(xml, coachId, coachSuffix, startIndex, endIndex, rollbackList, groovyPersons){
	 ArrayList<UUID> earlyAlertIds = new ArrayList<UUID>()
	 ArrayList<UUID> earlyAlertResponseIds = new ArrayList<UUID>()
	 ArrayList<UUID> programStatusPersonIds = new ArrayList<UUID>()
	 ArrayList<UUID> specialServiceGroupPersonIds = new ArrayList<UUID>()
	 ArrayList<UUID> referralSourcePersonIds = new ArrayList<UUID>()
	 ArrayList<UUID> journalIds = new ArrayList<UUID>()
	 ArrayList<UUID> taskIds = new ArrayList<UUID>()
	 ArrayList<UUID> personDisabilityIds = new ArrayList<UUID>()
	 ArrayList<UUID> personDisabilityAccommodationIds = new ArrayList<UUID>()
	 ArrayList<UUID> personDisabilityAgencyIds = new ArrayList<UUID>()
	 ArrayList<UUID> personDisabilityTypeIds = new ArrayList<UUID>()
	 ArrayList<UUID> personEducationGoalIds = new ArrayList<UUID>()
	 ArrayList<UUID> personDemographicsIds = new ArrayList<UUID>()
	 
	 for(Integer i = startIndex; i < endIndex; i++){
		 UUID personId = UUID.randomUUID();
		 rollbackList.add(personId)
		 
		 if(coachId == null){
			 addPerson(xml, personId, true, null, BASE_COACH_NAME , null, i, null, null, null, null, null, null, null, null, null)
			 groovyPersons.add(makePerson(personId, true, null, BASE_COACH_NAME , null, i))
			 Integer multiplier = i%STUDENT_MULTIPLIER
			 rollbackList = generatePeople(xml, personId, BASE_COACH_NAME+i, startIndex, multiplier * BASE_NUMBER_OF_STUDENTS, rollbackList, groovyPersons)
		 }else{
			studentTypeId = retrieveListValue(studentTypeIds, i, STUDENT_TYPE_ID_PARAMS)
			UUID personDisabilityId = null
			if(i%FREQUENCY_DISABILITY == 0){
				personDisabilityId = UUID.randomUUID();
				personDisabilityIds.add(personDisabilityId)

				String disabilityStatusId =  retrieveListValue(disabilityStatusIds, i, DISABILITY_STATUS_PARAMS)
				
				addPersonDisability(xml, personDisabilityId, disabilityStatusId, null)
			}
			
			UUID personEducationGoalId = UUID.randomUUID()
			personEducationGoalIds.add(personEducationGoalId)
			String educationGoalId =  retrieveListValue(educationGoalIds, i, EDUCATION_GOAL_PARAMS)
			String majorId =  retrieveListValue(majorIds, i, MAJOR_PARAMS)
			addPersonEducationGoal(xml, personEducationGoalId, personId, educationGoalId, majorId, "Having Fun", null)
			
			UUID personDemographicsId = UUID.randomUUID()
			personDemographicsIds.add(personDemographicsId)
			String ethnicityId =  retrieveListValue(ethnicityIds, i, ETHNICITY_PARAMS)
			String veteranStatusId =  retrieveListValue(veteranStatusIds, i, VETERAN_STATUS_PARAMS)
			addPersonDemographics(xml, personDemographicsId, personId, ethnicityId, veteranStatusId, null)
			
			addPerson(xml, personId, true, 
				coachId, 
				coachSuffix + BASE_STUDENT_NAME , 
				studentTypeId, 
				i, 
				null, 
				null,
				ANTICIPATED_START_TERM,
				ANTICIPATED_START_YEAR,
				ACTUAL_START_TERM,
				ACTUAL_START_YEAR,
				personDisabilityId,
				personDemographicsId,
				personEducationGoalId
				)
			
			groovyPersons.add(makePerson(personId, true, coachId, coachSuffix + BASE_STUDENT_NAME , studentTypeId, i))
			UUID programStatusPersonId = UUID.randomUUID()
			programStatusPersonIds.add(programStatusPersonId)
			String programStatusId = retrieveListValue(programStatusIds, i, PROGRAM_STATUS_ID_PARAMS)
			if(programStatusId != null)
				addPersonProgramStatus(xml, programStatusPersonId, programStatusId, personId, 1, null, null)
				
			UUID specialServiceGroupPersonId = UUID.randomUUID()
			specialServiceGroupPersonIds.add(specialServiceGroupPersonId)
			String specialServiceGroupId = retrieveListValue(specialServiceGroupIds, i, SPECIAL_SERVICE_GROUP_ID_PARAMS)
			if(specialServiceGroupId != null)
				addPersonSpecialServiceGroup(xml, specialServiceGroupPersonId, specialServiceGroupId, personId, 1, null, null)
			
			UUID referralSourcePersonId = UUID.randomUUID()
			referralSourcePersonIds.add(referralSourcePersonId)
			String referralSourceId = retrieveListValue(referralSourceIds, i, REFERRAL_SOURCE_ID_PARAMS)
			if(referralSourceId != null)
				addPersonReferralSource(xml, referralSourcePersonId, referralSourceId, personId, 1, null, null)
			
			generateEarlyAlerts(xml, personId, coachId, i, base_created_date.toString(), earlyAlertIds, earlyAlertResponseIds, campusId)
			generateJournalEntries(xml, personId, coachId, i, base_created_date.toString(), journalIds)
			generateTasks(xml, personId, coachId, i, base_created_date.toString(), taskIds)
			
			
			
			if(personDisabilityId != null){ 
				String disabilityAccomodationId =  retrieveListValue(disabilityAccommodationIds, i, DISABILITY_ACCOMMODATION_PARAMS)
				String disabilityAgencyId =  retrieveListValue(disabilityAgencyIds, i, DISABILITY_AGENCY_PARAMS)
				String disabilityTypeId =  retrieveListValue(disabilityTypeIds, i, DISABILITY_TYPE_PARAMS)
	
				UUID personAccommodationDisabilityId = UUID.randomUUID();
				personDisabilityAccommodationIds.add(personAccommodationDisabilityId)
				
				UUID personAgencyDisabilityId = UUID.randomUUID();
				personDisabilityAgencyIds.add(personAgencyDisabilityId)
				
				UUID personTypeDisabilityId = UUID.randomUUID();
				personDisabilityTypeIds.add(personTypeDisabilityId)
				
				addPersonDisabilityAccomodation(xml, personAccommodationDisabilityId, personId, disabilityAccomodationId, null)
				addPersonDisabilityAgency(xml, personAgencyDisabilityId, personId, disabilityAgencyId, null)
				
				personAgencyDisabilityId = UUID.randomUUID();
				personDisabilityAgencyIds.add(personAgencyDisabilityId)
				disabilityAgencyId =  retrieveListValue(disabilityAgencyIds, i + 1, DISABILITY_AGENCY_PARAMS)
				addPersonDisabilityAgency(xml, personAgencyDisabilityId, personId, disabilityAgencyId, null)
				
				addPersonDisabilityType(xml, personTypeDisabilityId, personId, disabilityTypeId, null)
			}
		 }
	 }
	 
	 generateRollbacks(xml, 'person_demographics', personDemographicsIds)
	 generateRollbacks(xml, 'person_education_goal', personEducationGoalIds)
	 generateRollbacks(xml, 'person_disability', personDisabilityIds)
	 generateRollbacks(xml, 'person_disability_accommodation', personDisabilityAccommodationIds)
	 generateRollbacks(xml, 'person_disability_agency', personDisabilityAgencyIds)
	 generateRollbacks(xml, 'person_disability_type', personDisabilityTypeIds)
	 generateRollbacks(xml, 'person_referral_source', referralSourcePersonIds)
	 generateRollbacks(xml, 'person_program_status', programStatusPersonIds)
	 generateRollbacks(xml, 'person_special_service_group', specialServiceGroupPersonIds)
	 generateRollbacks(xml, 'early_alert', earlyAlertIds)
	 generateRollbacks(xml, 'journal_entry', journalIds)
	 generateRollbacks(xml, 'task', taskIds)
	 generateRollbacksManyToMany(xml, "early_alert_early_alert_reason", "early_alert_id", earlyAlertIds)
	 generateRollbacksManyToMany(xml, "early_alert_early_alert_suggestion", "early_alert_id", earlyAlertIds)
	 generateRollbacks(xml, 'early_alert_response', earlyAlertResponseIds)
	 generateRollbacksManyToMany(xml, "early_alert_response_early_alert_outreach", "early_alert_response_id", earlyAlertResponseIds)
	 generateRollbacksManyToMany(xml, "early_alert_response_early_alert_referral", "early_alert_response_id", earlyAlertResponseIds)
	 
	 return rollbackList;
}

ArrayList<UUID> generateJournalEntries(xml, personId, coachId, i, startDate, journalIds){
	for(Integer k = 0; k < i%MAXIMUM_JOURNAL_ENTRIES; k++){
		confidentialityLevelId = retrieveListValue(confidentialityLevelIds,k, CONFIDENTIALITY_LEVEL_ID_PARAMS)
		journalSourceId = retrieveListValue(journalSourceIds, k, JOURNAL_SOURCE_ID_PARAMS)
		journalTrackId = retrieveListValue(journalTrackIds, k, JOURNAL_TRACK_ID_PARAMS)
		journalId = UUID.randomUUID();
		objectStatus = null
		journalIds.add(journalId)
		addJournalEntry(xml, 
			journalId, k, personId, 
			coachId, startDate, 
			confidentialityLevelId, 
			journalSourceId, journalTrackId, objectStatus)
	}
	return journalIds;
}

ArrayList<UUID> generateTasks(xml, personId, coachId, i, startDate, taskIds){
	for(Integer k = 0; k < i%MAXIMUM_TASKS; k++){
		UUID taskId = UUID.randomUUID();
		taskIds.add(taskId)
		confidentialityLevelId = retrieveListValue(confidentialityLevelIds,k, CONFIDENTIALITY_LEVEL_ID_PARAMS)
		challengeId = retrieveListValue(challengeIds, i, CHALLENGE_ID_PARAMS)
		challengeReferralId = retrieveListValue(challengeReferralIds, i, CHALLENGE_REFERRAL_ID_PARAMS)
		objectStatus = null;
		Boolean deletable = true;
			
		addTask(xml, taskId, k, generateTaskName(k), 
			generateTaskDescription(k), 
			personId, 
			coachId, 
			startDate, 
			startDate, 
			generateSessionId(k), confidentialityLevelId, challengeId, challengeReferralId, deletable, objectStatus)
	}
	return taskIds;
}

ArrayList<UUID> generateEarlyAlerts(xml, personId, coachId, i, startDate, earlyAlertIds, earlyAlertResponseIds, campusId){
	for(Integer k = 0; k < i%MAX_EARLY_ALERTS + 1; k++){
		UUID earlyAlertId = UUID.randomUUID();
		earlyAlertIds.add(earlyAlertId)
		String closedDate = null;
		if((k+1)%FREQUENCY_CLOSED_EARLY_ALERTS == 0)
			closedDate = getDateWithOffSet(startDate, CLOSED_EARLY_ALERT_OFF_SET_DATE_PARAMS, i)
		addEarlyAlert(xml, earlyAlertId, 
							personId, 
							generateCourseName(i), 
							generateCourseTitle(i),
							coachId, 
							getDateWithOffSet(startDate, EARLY_ALERT_OFF_SET_DATE_PARAMS, i), 
							closedDate, 
							coachId, 1, campusId)
		if(k%FREQUENCY_OF_EARLY_ALERT_REASON != 0)
			generateManyToMany(xml, earlyAlertId, earlyAlertReasonIds, i, k, 10, EARLY_ALERT_REASON_ID_PARAMS, 
							"early_alert_early_alert_reason", 
							"early_alert_id", 
							"early_alert_reason_id")
	   if(k%FREQUENCY_OF_EARLY_ALERT_SUGGESTION != 0)
			generateManyToMany(xml, earlyAlertId, earlyAlertSuggestionIds, i, k, 10, EARLY_ALERT_SUGGESTION_ID_PARAMS,
				"early_alert_early_alert_suggestion",
				"early_alert_id",
				"early_alert_suggestion_id")
		if(k%FREQUENCY_OF_EARLY_ALERT_RESPONSE != 0)
			generateEarlyAlertResponses(xml, earlyAlertId, startDate, i, earlyAlertResponseIds)
	}
	return  earlyAlertIds;
}


ArrayList<UUID> generateEarlyAlertResponses(xml, earlyAlertId, startDate, i, earlyAlertResponseIds){
	for(Integer k = 0; k < i%MAX_EARLY_ALERT_RESPONSES + 1; k++){
		UUID earlyAlertResponseId = UUID.randomUUID();
		String earlyAlertOutcomeId = retrieveListValue(earlyAlertOutcomeIds, i + k, EARLY_ALERT_OUTCOME_ID_PARAMS)
		if(earlyAlertOutcomeId == null)
			continue
		earlyAlertResponseIds.add(earlyAlertResponseId)
		
		addEarlyAlertResponse(xml, 
			earlyAlertResponseId, 
			earlyAlertId, 
			earlyAlertOutcomeId, 
			generateOutcomeOtherDescription(i + k), 
			generateOutcomeComment(i + k), 
			1)
		generateManyToMany(xml, earlyAlertResponseId, earlyAlertOutreachIds, i, k, 5, EARLY_ALERT_OUTREACH_ID_PARAMS, 
						"early_alert_response_early_alert_outreach", "early_alert_response_id", "early_alert_outreach_id")
		generateManyToMany(xml, earlyAlertResponseId, earlyAlertReferralIds, i, k, 10, EARLY_ALERT_REFERRAL_ID_PARAMS,
			"early_alert_response_early_alert_referral", "early_alert_response_id", "early_alert_referral_id")
	}
}

String generateCourseName(i){
	return "Course Name" + i%6
}
String generateCourseTitle(i){
	return "Course Title" + i%6
}

String generateOutcomeOtherDescription(i){
	return "Just another Description " + i
}

String generateOutcomeComment(i){
	return "Just another Comment " + i
}

String generateTaskName(i){
	return "Tough Task" + i%6
}

String generateSessionId(i){
	return "ABCDEF" + i
}

String generateTaskDescription(i){
	return "Tough Task but not that tough,  just get it done! In" + i%6 + " days."
}


String getDateWithOffSet(dateString, dayOffsetParams, index){
	java.text.DateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
	date = dateFormat.parse(dateString)
	Calendar cal = Calendar.getInstance()
	cal.setTime(date)
	cal.add(Calendar.DAY_OF_MONTH, dayOffsetParams.baseOffsetDays + index%dayOffsetParams.mod);
	return new Date(cal.getTimeInMillis()).toString();
}

String retrieveListValue(list, i, params){
	index = i%params.mod;
	idValue = null;
	if(index < params.max)
		idValue = list[index]
	return idValue
}

void generateManyToMany(xml, id, manyIds, i, k, manyIdsSize, params, tableName, idName, manyIdName){
	for(Integer l = 0; l < (i+k)%MAX_MANY_TO_MANY; l++){
		manyId = retrieveListValue(manyIds, i + k + l, params)
		if(manyId == null)
			continue;
			xml.insert(tableName:tableName){
				xml.column(name:idName,  value:id.toString())
				xml.column(name:manyIdName,  value:manyId.toString())
		}
	}
}

void addPerson(xml, 
	personId, 
	isCoach, 
	coachId, 
	userNameSuffix, 
	studentTypeId, 
	i, 
	entryDate, 
	objectStatus, 
	anticipated_start_term,
	anticipated_start_year,
	actual_start_term,
	actual_start_year,
	personDisabilityId,
	personDemographicsId,
	personEducationGoalId)	{
	
	xml.insert(tableName:'person'){
		xml.column(name:"id", value:personId.toString())
		xml.column(name:"first_name", value:"test" )
		xml.column(name:"last_name", value:userNameSuffix + i)
		xml.column(name:"middle_name", value:"Mumford")
		xml.column(name:"primary_email_address", value:userNameSuffix + i + "@unicon.net")
		xml.column(name:"secondary_email_address", value:userNameSuffix + i + "@unicon.net")
		xml.column(name:"username", value:userNameSuffix + i)
		xml.column(name:"school_id", value:userNameSuffix + "." + i)
		xml.column(name:"home_phone", value:"480-775-2345")
		xml.column(name:"work_phone", value:"480-775-7894")
		xml.column(name:"address_line_1", value:i + " house on the corner")
		xml.column(name:"city", value:"Mesa")
		xml.column(name:"state", value:"AZ")
		xml.column(name:"zip_code", value:"85201")
		xml.column(name:"photo_url", value:"")
		xml.column(name:"school_id",  value:userNameSuffix + i)
		
		if(personDisabilityId != null)
			xml.column(name:"person_disability_id",  value:personDisabilityId.toString())
		
		if(personDemographicsId != null)
			xml.column(name:"person_demographics_id",  value:personDemographicsId.toString())
		
		if(personEducationGoalId != null)
			xml.column(name:"person_education_goal_id",  value:personEducationGoalId.toString())
		
		addCreatedModified(xml, coachId, entryDate)
		addObjectStatus(xml, objectStatus)
		if(coachId != null){
			xml.column(name:"anticipated_start_year", value:anticipated_start_year)
            xml.column(name:"anticipated_start_term", value:anticipated_start_term)
            xml.column(name:"actual_start_term", value:actual_start_term)
            xml.column(name:"actual_start_year", value:actual_start_year)
			xml.column(name:"coach_id",  value:coachId.toString())
			if(studentTypeId != null)
				xml.column(name:"student_type_id",  value:studentTypeId.toString())
		}	
	}
}

GroovyPerson makePerson(personId, isCoach, coachId, userNameSuffix, studentTypeId, i)	{
	
	GroovyPerson person = new GroovyPerson()
	person.id = personId.toString()
	person.first_name = "test" 
	person.last_name = userNameSuffix + i
	person.middle_name = "Mumford"
	person.primary_email_address = userNameSuffix + i + "@unicon.net"
	person.secondary_email_address = userNameSuffix + i + "@unicon.net"
	person.username = userNameSuffix + i
	person.school_id = userNameSuffix + "." + i
	person.home_phone = "480-775-2345"
	person.work_phone = "480-775-7894"
	person.address_line_1 = i + " house on the corner"
	person.city = "Mesa"
	person.state = "AZ"
	person.zip_code = "85201"
	person.photo_url = ""
	person.school_id = userNameSuffix + i
	person.created_by = "58ba5ee3-734e-4ae9-b9c5-943774b4de41"
	person.modified_by = "58ba5ee3-734e-4ae9-b9c5-943774b4de41"
	person.object_status = "1"
	if(coachId != null) {
		person.coach_id = coachId.toString()
		if(studentTypeId != null)
			person.student_type_id = studentTypeId.toString()
	}
    return person
}

void writeuPortUsers(groovyPersons){
	for(GroovyPerson groovyPerson:groovyPersons){
		if(groovyPerson.coach_id == null)
			writeuPortalUser(groovyPerson);
	}
}

String getXmlLicense(){
	return " <!-- 													\n\
			Licensed to Jasig under one or more contributor license		\n\
		    agreements. See the NOTICE file distributed with this work	\n\
		    for additional information regarding copyright ownership.	\n\
		    Jasig licenses this file to you under the Apache License,	\n\
		    Version 2.0 (the \"License\"); you may not use this file 	\n\
		    except in compliance with the License. You may obtain a 	\n\
		    copy of the License at: 									\n\
																		\n\
		    http://www.apache.org/licenses/LICENSE-2.0					 \n\
																		\n\
		    Unless required by applicable law or agreed to in writing,	\n\
		    software distributed under the License is distributed on	\n\
		    an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY	\n\
		    KIND, either express or implied. See the License for the	\n\
		    specific language governing permissions and limitations		\n\
		    under the License.											-->\n "
}

void writeuPortalUser(groovyPerson){
	def writer = new FileWriter(BASE_LOCATION_UPORTAL_USERS + groovyPerson.username + ".user.xml")
	writer << '<?xml version="1.0" encoding="UTF-8" standalone="no"?>' << eol
	writer << getXmlLicense() << eol
	def xml = new groovy.xml.MarkupBuilder(writer) as groovy.xml.MarkupBuilder
	
	
	xml.user( xmlns : "https://source.jasig.org/schemas/uportal/io/user"
						 , "xmlns:ns2" : "https://source.jasig.org/schemas/uportal/io/permission-owner"
						 , "xmlns:ns3" : "https://source.jasig.org/schemas/uportal/io/portlet-definition"
						 , "xmlns:ns5" : "https://source.jasig.org/schemas/uportal/io/subscribed-fragment"
						 , "xmlns:ns6" : "https://source.jasig.org/schemas/uportal/io/event-aggregation"
						 , "xmlns:ns7" : "https://source.jasig.org/schemas/uportal/io/stylesheet-descriptor"
						 , "xmlns:ns8" : "https://source.jasig.org/schemas/uportal/io/portlet-type",
						 , "xmlns:xsi" : "http://www.w3.org/2001/XMLSchema-instance"
						 , username : groovyPerson.username
						 , version : "4.0"
						 , "xsi:schemaLocation" : "https://source.jasig.org/schemas/uportal/io/user https://source.jasig.org/schemas/uportal/io/user/user-4.0.xsd"
						 ) {
							 def default_user = xml.createNode("default-user", "defaultTemplateUser")
							 xml.nodeCompleted(this, default_user)
							 
							 password("(SHA256)zWMUqUYhgT2qyvBaL85R8cRASb9LMrl93EJ6DAMYG26HWuo204tAJA==")
							 attribute(){
								 name( "givenName")
								 value(groovyPerson.last_name)
							 }
						 attribute(){
							 name("sn")
							 value(groovyPerson.first_name)
						 }
						 attribute(){
							 name("mail")
							 value(groovyPerson.primary_email_address)
						 }
						 attribute(){
							 name("telephoneNumber")
							 value(groovyPerson.work_phone)
						 }
						 attribute(){
							 name( "SSP_ROLES")
							 value("SSP_COACH")
						 }
	}
}

void generateRollbacks(xml, tableName, uuids)	{
	if (uuids == null || uuids.size() == 0)
	 	return;
	xml.rollback{
		for(UUID uuid:uuids){
			xml.delete(tableName:tableName){
				xml.where("id='" + uuid.toString()+"'")
			}
		}
	}
}

void generateRollbacksManyToMany(xml, tableName, columnName, uuids)	{
	if (uuids == null || uuids.size() == 0)
		return;
	xml.rollback{
		for(UUID uuid:uuids){
			xml.delete(tableName:tableName){
				xml.where(columnName + "='" + uuid.toString() + "'")
			}
		}
	}
}

void addPersonEducationGoal(xml, id, personId, educationGoalId, plannedMajor, description, objectStatus){
	xml.insert(tableName:"person_education_goal"){
		xml.column(name:"id",  value:id.toString())
		xml.column(name:"education_goal_id",  value:educationGoalId.toString())
		xml.column(name:"planned_major",  value:plannedMajor)
		xml.column(name:"description",  value:description)
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addPersonDemographics(xml, id, personId, ethnicityId, veteranStatusId, objectStatus){
	xml.insert(tableName:"person_demographics"){
		xml.column(name:"id",  value:id.toString())
		xml.column(name:"ethnicity_id",  value:ethnicityId.toString())
		xml.column(name:"veteran_status_id",  value:veteranStatusId)
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addChallenge(xml, challengeId, personId, name, description, objectStatus){
	xml.insert(tableName:"challenge"){
		xml.column(name:"challenge_id",  value:challengeId.toString())
		xml.column(name:"person_id",  value:personId.toString())
		xml.column(name:"name",  value:name)
		xml.column(name:"description",  value:description)
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addPersonDisability(xml, id, disabilityStatusId, objectStatus){
	xml.insert(tableName:"person_disability"){
		xml.column(name:"id",  value:id.toString())
		xml.column(name:"disability_status_id",  value:disabilityStatusId.toString())
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addPersonDisabilityAccomodation(xml, personAccommodationDisabilityId, personId, disabilityAccomodationId, objectStatus){
	xml.insert(tableName:"person_disability_accommodation"){
		xml.column(name:"id",  value:personAccommodationDisabilityId.toString())
		xml.column(name:"person_id",  value:personId.toString())
		xml.column(name:"disability_accommodation_id",  value:disabilityAccomodationId.toString())
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}
void addPersonDisabilityAgency(xml, personAgencyDisabilityId, personId, disabilityAgencyId, objectStatus){
	xml.insert(tableName:"person_disability_agency"){
		xml.column(name:"id",  value:personAgencyDisabilityId.toString())
		xml.column(name:"person_id",  value:personId.toString())
		xml.column(name:"disability_agency_id",  value:disabilityAgencyId.toString())
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}
void addPersonDisabilityType(xml, personTypeDisabilityId, personId, disabilityTypeId, objectStatus){
	xml.insert(tableName:"person_disability_type"){
		xml.column(name:"id",  value:personTypeDisabilityId.toString())
		xml.column(name:"person_id",  value:personId.toString())
		xml.column(name:"disability_type_id",  value:disabilityTypeId.toString())
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}


void addJournalEntry(xml, id, index, personId, coachId, entryDate, confidentialityLevelId, journalSourceId, journalTrackId, objectstatus){
	xml.insert(tableName:"journal_entry"){
		xml.column(name:"id",  value:id.toString())
		xml.column(name:"person_id",  value:personId)
		xml.column(name:"entry_date",  value:entryDate == null ? base_created_date.toString() : entryDate)
		xml.column(name:"confidentiality_level_id",  value: confidentialityLevelId)
		xml.column(name:"journal_source_id",  value:journalSourceId)
		xml.column(name:"journal_track_id",  value:journalTrackId)
		addCreatedModified(xml, coachId, entryDate)
		addObjectStatus(xml, objectStatus)
	}
}

void addTask(xml, id, index, name, description, personId, coachId, entryDate, dueDate, session_id, confidentialityLevelId, challengeId, challengeReferralId, deletable, objectStatus){
	xml.insert(tableName:"task"){
		xml.column(name:"id",  value:id.toString())
		xml.column(name:"person_id",  value:personId)
		xml.column(name:"name",  value:name)
		xml.column(name:"description",  value:description)
		xml.column(name:"due_date",  value:dueDate == null ? base_created_date.toString() : dueDate)
		xml.column(name:"completed_date",  value:dueDate == null ? base_created_date.toString() : dueDate)
		xml.column(name:"reminder_sent_date",  value:dueDate == null ? base_created_date.toString() : dueDate)
		xml.column(name:"session_id",  value:session_id)
		if(confidentialityLevelId != null)
			xml.column(name:"confidentiality_level_id",  value: confidentialityLevelId)
		if(challengeId != null)
			xml.column(name:"challenge_id",  value:challengeId)
		else
			throw new Exception("challenge_id")
		if(challengeReferralId != null)
			xml.column(name:"challenge_referral_id",  value:challengeReferralId)
		else
			throw new Exception("challenge_referral_id")
		xml.column(name:"deletable",  value:deletable)
		addCreatedModified(xml, coachId, entryDate)
		addObjectStatus(xml, objectStatus)
	}
}

void addJournalStep(xml, id, personId, name, description, objectStatus, sort_order){
	xml.insert(tableName:"journal_step"){
		xml.column(name:"id",  value:challengeId.toString())
		xml.column(name:"name",  value:name)
		xml.column(name:"description",  value:description)
		xml.column(name:"sort_order",  value:sort_order)
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addJournalStepDetail(xml, id, personId, name, description, objectStatus, sort_order){
	xml.insert(tableName:"journal_step_detail"){
		xml.column(name:"id",  value:challengeId.toString())
		xml.column(name:"name",  value:name)
		xml.column(name:"description",  value:description)
		xml.column(name:"sort_order",  value:sort_order)
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}


void addCreatedModified(xml, created_by, created_date){
	xml.column(name:"created_date",  value:created_date == null ? base_created_date.toString() : created_date)
	xml.column(name:"modified_date",  value:created_date == null ? base_created_date.toString() : created_date)
	xml.column(name:"created_by",  value:created_by == null ? "58ba5ee3-734e-4ae9-b9c5-943774b4de41": created_by)
	xml.column(name:"modified_by",  value:created_by == null ? "58ba5ee3-734e-4ae9-b9c5-943774b4de41": created_by)
}

void addPersonProgramStatus(xml, id, programStatusId, personId, objectStatus, effectiveDate, expirationDate){
	xml.insert(tableName:"person_program_status"){
		xml.column(name:"id",  value:id.toString())
		xml.column(name:"program_status_id",  value:programStatusId.toString())
		xml.column(name:"person_id",  value:personId.toString())
		if(effectiveDate == null)
			xml.column(name:"effective_date",  valueDate:base_created_date.toString())
		else
			xml.column(name:"effective_date",  valueDate:effectiveDate.toString())
		if(expirationDate != null)
			xml.column(name:"expiration_date",  valueDate:expirationDate.toString())
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addPersonSpecialServiceGroup(xml, id, specialServiceGroupId, personId, objectStatus, effectiveDate, expirationDate){
	xml.insert(tableName:"person_special_service_group"){
		xml.column(name:"id",  value:id.toString())
		xml.column(name:"special_service_group_id",  value:specialServiceGroupId.toString())
		xml.column(name:"person_id",  value:personId.toString())
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addPersonReferralSource(xml, id, referallSourceId, personId, objectStatus, effectiveDate, expirationDate){
	xml.insert(tableName:"person_referral_source"){
		xml.column(name:"id",  value:id.toString())
		xml.column(name:"referral_source_id",  value:referallSourceId.toString())
		xml.column(name:"person_id",  value:personId.toString())
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addEarlyAlert(xml, earlyAlertId, personId, courseName, courseTitle, coachId, createdDate, closedDate, closedBy, objectStatus, campusId){
	xml.insert(tableName:"early_alert"){
		xml.column(name:"id",  value:earlyAlertId.toString())
		xml.column(name:"person_id",  value:personId.toString())
		xml.column(name:"course_name",  value:courseName.toString())
		xml.column(name:"course_title",  value:courseTitle.toString())
		if(closedDate != null){
			xml.column(name:"closed_date",  value:closedDate.toString())
			xml.column(name:"closed_by_id",  value:closedBy.toString())
		}
		addCreatedModified(xml, coachId.toString(), createdDate)
		xml.column(name:"campus_id",  value:campusId.toString())
		addObjectStatus(xml, objectStatus)
	}
}
void addEarlyAlertEarlyAlertReason(earlyAlertId, earlyAlertReasonId){
	xml.insert(tableName:"early_alert_early_alert_reason"){
		xml.column(name:"early_alert_id",  value:earlyAlertId.toString())
		xml.column(name:"early_alert_reason_id",  value:earlyAlertReasonId.toString())
	}
}

void addEarlyAlertEarlyAlertSuggestion(xml, earlyAlertId, earlyAlertSuggestionId){
	xml.insert(tableName:"early_alert_early_alert_suggestion"){
		xml.column(name:"early_alert_id",  value:earlyAlertId.toString())
		xml.column(name:"early_alert_suggestion_id",  value:earlyAlertSuggestionId.toString())
	}
}

void addEarlyAlertResponse(xml, earlyAlertResponseId, earlyAlertId, earlyAlertOutcomeId, earlyAlertOutcomeOtherDescription, comment, objectStatus)
{
	xml.insert(tableName:"early_alert_response"){
		xml.column(name:"id",  value:earlyAlertResponseId.toString())
		xml.column(name:"early_alert_id",  value:earlyAlertId.toString())
		xml.column(name:"early_alert_outcome_id",  value:earlyAlertOutcomeId.toString())
		xml.column(name:"early_alert_outcome_other_description",  value:earlyAlertOutcomeOtherDescription.toString())
		xml.column(name:"comment",  value:comment.toString())
		addCreatedModified(xml, null, null)
		addObjectStatus(xml, objectStatus)
	}
}

void addEarlyAlertResponseEarlyAlertOutreach(xml, earlyAlertResponseId, earlyAlertOutreachId){
	xml.insert(tableName:"early_alert_response_early_alert_outreach"){
		xml.column(name:"early_alert_response_id",  value:earlyAlertResponseId.toString())
		xml.column(name:"early_alert_outreach_id",  value:earlyAlertOutreachId.toString())
	}
}

void addEarlyAlertResponseEarlyAlertReferral(xml, earlyAlertResponseId, earlyAlertReferralId){
	xml.insert(tableName:"early_alert_response_early_alert_referral"){
		xml.column(name:"early_alert_response_id",  value:earlyAlertResponseId.toString())
		xml.column(name:"early_alert_referral_id",  value:earlyAlertReferralId.toString())
	}
}

void addObjectStatus(xml, objectStatus)
{
	if(objectStatus == null)
		xml.column(name:"object_status",  value:"1")
	else
		xml.column(name:"object_status",  value:objectStatus.toString())
}

class GroovyPerson{
	String id
	String first_name
	String last_name
	String middle_name
	String primary_email_address
	String secondary_email_address
	String username
	String school_id
	String home_phone
	String work_phone
	String address_line_1
	String city
	String state
	String zip_code
	String photo_url
	String created_by
	String modified_by
	String object_status
	String coach_id
	String student_type_id
}

void writePerson(writer, username, password, firstName, lastName, email, phoneNumber)
{
	writer.write("dn: uid=" + username +",ou=users,dc=springframework,dc=org\n")
	writer.write("objectClass: uidObject\n")
	writer.write("objectClass: person\n")
	writer.write("objectClass: top\n")
	writer.write("objectClass: organizationalPerson\n")
	writer.write("uid: " + username +"\n")
	writer.write("userPassword: " + password +"\n")
	writer.write("cn: " + firstName +"\n")
	writer.write("sn: " + lastName +"\n")
	writer.write("mail: " + email +"\n")
	writer.write("telephonenumber: " + phoneNumber +"\n\n")
}



void writeLdif(writer, persons){
	
	writer.write("#\n")
	writer.write("# Licensed to Jasig under one or more contributor license\n")
	writer.write("# agreements. See the NOTICE file distributed with this work\n")
	writer.write("# for additional information regarding copyright ownership.\n")
	writer.write("# Jasig licenses this file to you under the Apache License,\n")
	writer.write("# Version 2.0 (the \"License\"); you may not use this file\n")
	writer.write("# except in compliance with the License. You may obtain a\n")
	writer.write("# copy of the License at:\n")
	writer.write("#\n")
	writer.write("# http://www.apache.org/licenses/LICENSE-2.0\n")
	writer.write("#\n")
	writer.write("# Unless required by applicable law or agreed to in writing,\n")
	writer.write("# software distributed under the License is distributed on\n")
	writer.write("# an \"AS IS\" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY\n")
	writer.write("# KIND, either express or implied. See the License for the\n")
	writer.write("# specific language governing permissions and limitations\n")
	writer.write("# under the License.\n")
	writer.write("#\n\n")

	writer.write("# root is dc=springframework,dc=org\n\n\n")
	
	
	writer.write("####\n")
	writer.write("# major ou sections:\n")
	writer.write("####\n")
	writer.write("dn: ou=users,dc=springframework,dc=org\n")
	writer.write("objectClass: extensibleObject\n")
	writer.write("objectClass: organizationalUnit\n")
	writer.write("objectClass: top\n")
	writer.write("ou: users\n\n")
	
	writer.write("dn: ou=groups,dc=springframework,dc=org\n")
	writer.write("objectClass: extensibleObject\n")
	writer.write("objectClass: organizationalUnit\n")
	writer.write("objectClass: top\n")
	writer.write("ou: groups\n\n")
	
	
	writer.write("####\n")
	writer.write("# Users:\n")
	writer.write("####\n\n")
	for(GroovyPerson person:persons){
		writePerson(writer,
			person.username,
			person.username,
			person.first_name,
			person.last_name,
			person.primary_email_address,
			person.home_phone)
	}
	
	writer.write("\n####\n")
	writer.write("# Groups:\n")
	writer.write("####\n\n")
	setObjectClass(writer, "COACH")
	writeUniqueMemberCoach(writer, persons, "cn")

	
	setObjectClass(writer, "REFERENCE_READ")
	writeUniqueMemberAll(writer, persons, "uid")
	
	setObjectClass(writer, "REFERENCE_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_APPOINTMENT_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_APPOINTMENT_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_APPOINTMENT_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_CASELOAD_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_CHALLENGE_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_CHALLENGE_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_CHALLENGE_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_CHALLENGE_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_DOCUMENT_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_DOCUMENT_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_DOCUMENT_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_EARLY_ALERT_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_EARLY_ALERT_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_EARLY_ALERT_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_EARLY_ALERT_RESPONSE_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_EARLY_ALERT_RESPONSE_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_EARLY_ALERT_RESPONSE_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_GOAL_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_GOAL_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_GOAL_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_JOURNAL_ENTRY_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_JOURNAL_ENTRY_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_JOURNAL_ENTRY_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_SEARCH_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_PROGRAM_STATUS_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_PROGRAM_STATUS_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_PROGRAM_STATUS_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_TASK_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_TASK_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_TASK_DELETE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "PERSON_INSTRUCTION_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "STUDENT_INTAKE_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "STUDENT_INTAKE_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "DISABILITY_INTAKE_READ")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "DISABILITY_INTAKE_WRITE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "DATA_STAFF")
	writeUniqueMemberCoach(writer, persons, "uid")
	
	setObjectClass(writer, "DATA_EVERYONE")
	writeUniqueMemberCoach(writer, persons, "uid")
	
}
void writeUniqueMemberCoach(writer, persons, name){
	for(GroovyPerson person:persons){
		if(person.coach_id == null || person.coach_id == "")
			uniqueMember(writer, name,  person.school_id)
	}
}

void writeUniqueMemberAll(writer, persons, name){
	for(GroovyPerson person:persons){
			uniqueMember(writer, name,  person.school_id)
	}
}

void setObjectClass(writer, className){
	writer.write("\ndn: cn=" + className + ",ou=groups,dc=springframework,dc=org\n")
	writer.write("objectClass: groupOfUniqueNames\n")
	writer.write("objectClass: top\n")
	writer.write("cn: " + className + "\n")
}


void uniqueMember(writer, name,  value){
	writer.write("uniqueMember: "+ name + "=" + value + ",ou=users,dc=springframework,dc=org\n")
}
