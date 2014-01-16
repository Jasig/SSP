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



/* ***THIS IS A COMPILATION SCRIPT FILE of ALL SCRIPTS RAN TO SETUP SSP TRAINING*** 
 *  It will work with a blank ssp database setup after liquibase has run.
 *        Version is rel-2-0-patches on Oct. 10th 2013.
 */

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = public, pg_catalog;


--
-- Deletes For SSP Default Data
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


--***END OF SQL SCRIPT***