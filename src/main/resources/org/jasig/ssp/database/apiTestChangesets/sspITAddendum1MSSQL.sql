--
-- Licensed to Jasig under one or more contributor license
-- agreements. See the NOTICE file distributed with this work
-- for additional information regarding copyright ownership.
-- Jasig licenses this file to you under the Apache License,
-- Version 2.0 (the "License"); you may not use this file
-- except in compliance with the License. You may obtain a
-- copy of the License at:
--
-- http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing,
-- software distributed under the License is distributed on
-- an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
-- KIND, either express or implied. See the License for the
-- specific language governing permissions and limitations
-- under the License.
--

--
-- Deletes For SSP Data
--
DELETE FROM sap_status;
DELETE FROM financial_aid_file;
DELETE FROM external_student_financial_aid_award_term;
DELETE FROM external_student_financial_aid_file;

--END Deletes


--DATA INSERT START

--
-- Data for Name: sap_status; Type: TABLE DATA; Schema: public; Owner: sspadmin
--

INSERT INTO sap_status (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('9f73e1f0-66aa-47f6-a7bc-2daecb915207', 'GOV_1', 'GOV_1 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'GOV ONE');
INSERT INTO sap_status (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('83e7967f-fc67-408c-929f-fc361eece175', 'GOV_2', 'GOV_2 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'GOV TWO');
INSERT INTO sap_status (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('ff149156-a02f-4e9d-bfb2-ef9dfb32eef2', 'STATE_1', 'STATE_1 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'STATE ONE');
INSERT INTO sap_status (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('79a9a1a7-35ff-459c-9fed-a233d9421761', 'STATE_2', 'STATE_2 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'STATE TWO');
INSERT INTO sap_status (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('fa80f025-5405-4355-9747-84dd3fa66df6', 'COUNTY_1', 'COUNTY_1 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'COUNTY ONE');
INSERT INTO sap_status (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('dec0364a-d576-424d-94ce-79544c21e8c8', 'COUNTY_2', 'COUNTY_2 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'COUNTY TWO');
INSERT INTO sap_status (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('35efc2bd-c8df-4b2e-b821-43278fdd4839', 'CITY_1', 'CITY_1 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'CITY ONE');


--
-- Data for Name: sap_status; Type: TABLE DATA; Schema: public; Owner: sspadmin
--

INSERT INTO financial_aid_file (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('9f73e1f0-66aa-47f6-a7bc-2daecb915207', 'GOV_1', 'GOV_1 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'GOV ONE');
INSERT INTO financial_aid_file (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('83e7967f-fc67-408c-929f-fc361eece175', 'GOV_2', 'GOV_2 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'GOV TWO');
INSERT INTO financial_aid_file (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('ff149156-a02f-4e9d-bfb2-ef9dfb32eef2', 'STATE_1', 'STATE_1 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'STATE ONE');
INSERT INTO financial_aid_file (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('79a9a1a7-35ff-459c-9fed-a233d9421761', 'STATE_2', 'STATE_2 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'STATE TWO');
INSERT INTO financial_aid_file (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('fa80f025-5405-4355-9747-84dd3fa66df6', 'COUNTY_1', 'COUNTY_1 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'COUNTY ONE');
INSERT INTO financial_aid_file (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('dec0364a-d576-424d-94ce-79544c21e8c8', 'COUNTY_2', 'COUNTY_2 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'COUNTY TWO');
INSERT INTO financial_aid_file (id, code, description, created_date, modified_date, created_by, modified_by, object_status, name) VALUES ('35efc2bd-c8df-4b2e-b821-43278fdd4839', 'CITY_1', 'CITY_1 document', '2012-03-20 00:00:00', '2012-03-20 00:00:00', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', '58ba5ee3-734e-4ae9-b9c5-943774b4de41', 1, 'CITY ONE');


--
-- Data for Name: external_student_financial_aid_file; Type: TABLE DATA; Schema: public; Owner: sspadmin
--

insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('bjones27','COMPLETE','CITY_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('bjones27','PENDING','STATE_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('bjones27','PENDING','GOV_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('bjones27','COMPLETE','GOV_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('bjones27','COMPLETE','COUNTY_2');

insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('btaylor30','COMPLETE','CITY_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('btaylor30','PENDING','STATE_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('btaylor30','PENDING','GOV_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('btaylor30','COMPLETE','GOV_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('btaylor30','COMPLETE','COUNTY_2');

insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cgarcia1','COMPLETE','CITY_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cgarcia1','PENDING','STATE_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cgarcia1','PENDING','GOV_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cgarcia1','INCOMPLETE','GOV_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cgarcia1','COMPLETE','COUNTY_2');

insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('charris223','COMPLETE','CITY_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('charris223','PENDING','STATE_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('charris223','PENDING','GOV_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('charris223','INCOMPLETE','GOV_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('charris223','COMPLETE','COUNTY_2');

insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cjackson52','COMPLETE','CITY_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cjackson52','PENDING','STATE_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cjackson52','PENDING','GOV_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cjackson52','INCOMPLETE','GOV_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cjackson52','COMPLETE','COUNTY_2');

insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cmiller114','COMPLETE','CITY_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cmiller114','PENDING','STATE_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cmiller114','PENDING','GOV_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cmiller114','INCOMPLETE','GOV_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('cmiller114','COMPLETE','COUNTY_2');

insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('csimth29','COMPLETE','CITY_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('csimth29','PENDING','STATE_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('csimth29','PENDING','GOV_2');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('csimth29','INCOMPLETE','GOV_1');
insert into external_student_financial_aid_file (school_id, file_status, financial_file_code) values ('csimth29','COMPLETE','COUNTY_2');

--
-- Data for Name: external_student_financial_aid_award_term; Type: TABLE DATA; Schema: public; Owner: sspadmin
--

insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('bjones27','Y','FA2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('bjones27','N','FA2013');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('bjones27','N','SP2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('bjones27','Y','SP2014');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('bjones27','Y','SU2012');

insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('btaylor30','Y','FA2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('btaylor30','N','FA2013');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('btaylor30','N','SP2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('btaylor30','Y','SP2014');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('btaylor30','Y','SU2012');

insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cgarcia1','Y','FA2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cgarcia1','N','FA2013');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cgarcia1','N','SP2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cgarcia1','Y','SP2014');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cgarcia1','Y','SU2012');

insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('charris223','Y','FA2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('charris223','N','FA2013');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('charris223','N','SP2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('charris223','Y','SP2014');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('charris223','Y','SU2012');

insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cjackson52','Y','FA2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cjackson52','N','FA2013');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cjackson52','N','SP2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cjackson52','Y','SP2014');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cjackson52','Y','SU2012');

insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cmiller114','Y','FA2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cmiller114','N','FA2013');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cmiller114','N','SP2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cmiller114','Y','SP2014');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('cmiller114','Y','SU2012');

insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('csimth29','Y','FA2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('csimth29','N','FA2013');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('csimth29','N','SP2012');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('csimth29','Y','SP2014');
insert into external_student_financial_aid_award_term (school_id, accepted, term_code) values ('csimth29','Y','SU2012');


--
-- Data for Name: external_student_financial_aid; Type: TABLE DATA; Schema: public; Owner: sspadmin
--

UPDATE external_student_financial_aid set sap_status_code='CITY_1', institutional_loan_amount=10000.00, eligible_federal_aid='Y', financial_aid_file_status='COMPLETE', terms_left=4 WHERE school_id LIKE 'b%';

UPDATE external_student_financial_aid set sap_status_code='COUNTY_1', institutional_loan_amount=15000.00, eligible_federal_aid='N', financial_aid_file_status='PENDING', terms_left=48 WHERE school_id LIKE 'c%';

UPDATE external_student_financial_aid set sap_status_code='STATE_1', institutional_loan_amount=15000.00, eligible_federal_aid='N', financial_aid_file_status='PENDING', terms_left=48 WHERE school_id LIKE 'c%';

UPDATE external_student_financial_aid set sap_status_code='COUNTY_2', institutional_loan_amount=15000.00, eligible_federal_aid='Y', financial_aid_file_status='INCOMPLETE', terms_left=8 WHERE school_id LIKE 'j%';

UPDATE external_student_financial_aid set sap_status_code='STATE_2', institutional_loan_amount=8000.00, eligible_federal_aid='Y', financial_aid_file_status='INCOMPLETE', terms_left=8 WHERE school_id LIKE 'l%';

UPDATE external_student_financial_aid set sap_status_code='GOV_2', institutional_loan_amount=17000.00, eligible_federal_aid='Y', financial_aid_file_status='COMPLETE', terms_left=2 WHERE school_id LIKE 'm%';

UPDATE external_student_financial_aid set sap_status_code='GOV_1', institutional_loan_amount=10000.00, eligible_federal_aid='n', financial_aid_file_status='COMPLETE', terms_left=4 WHERE school_id LIKE 'n%';


UPDATE map_template set visibility=0 WHERE is_private=1;
UPDATE map_template set visibility=1 WHERE is_private=0;

--***END OF SQL SCRIPT***
