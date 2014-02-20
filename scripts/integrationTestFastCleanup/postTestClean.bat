@REM
@REM Licensed to Jasig under one or more contributor license
@REM agreements. See the NOTICE file distributed with this work
@REM for additional information regarding copyright ownership.
@REM Jasig licenses this file to you under the Apache License,
@REM Version 2.0 (the "License"); you may not use this file
@REM except in compliance with the License. You may obtain a
@REM copy of the License at:
@REM
@REM http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on
@REM an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied. See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM

rem
rem This script "resets" the database for less downtime after 
rem   SSP API Integration Test Development
rem
rem To run a single test see here: 
rem  http://maven.apache.org/surefire/maven-failsafe-plugin/examples/single-test.html
rem

@echo off

echo.
echo Clean the database after SSP API INTEGRATION Test runs
echo.
 
setlocal
set "TABLESTOCLEAN=tag student_type student_status veteran_status special_service_group service_reason self_help_guide self_help_guide_group self_help_guide_question registration_load referral_source race program_status program_status_change_reason personality_type military_affiliation marital_status message_template lassi journal_track journal_track_journal_step journal_step_detail journal_source journal_step journal_step_journal_step_detail funding_source ethnicity education_level education_goal enrollment_status elective early_alert_suggestion early_alert_referral early_alert_reason early_alert_outreach early_alert_outcome disability_type disability_status disability_agency disability_accommodation coursework_hours confidentiality_level confidentiality_disclosure_agreement color citizenship child_care_arrangement challenge_referral campus_service campus category challenge_category challenge_challenge_referral challenge financial_aid_file sap_status"

(
 for %%i in (%TABLESTOCLEAN%) do (
  echo( Deleting rows with object status equal to 2 for: %%i.
  sqlcmd  -d ssp -Q "DELETE FROM %%i WHERE object_status = '2'"  
  echo(
 )
)






rem Reference API Custom Updates for API's where Delete not possible
sqlcmd -d ssp -Q "UPDATE config SET name = 'coachSetFromExternalData', modified_date = '2013-01-01 00:00:00', modified_by = '58ba5ee3-734e-4ae9-b9c5-943774b4de41', object_status = 1 WHERE id = '1b5528d2-d789-11e1-9d78-0026b9e7ff4c';"

sqlcmd -d ssp -Q "UPDATE config SET name = 'client_timeout', modified_date = '2013-01-01 00:00:00', modified_by = '58ba5ee3-734e-4ae9-b9c5-943774b4de41', object_status = 1 WHERE id = '0ed5d4e3-77cb-11e3-a151-406c8f22c3ce';"

sqlcmd -d ssp -Q "UPDATE blurb SET name = 'Intake Alternate Email Label', modified_date = '2013-01-01 00:00:00', modified_by = '58ba5ee3-734e-4ae9-b9c5-943774b4de41', object_status = 1 WHERE id = '347fb756-ae34-4b94-a6ed-4aafb0b985a2';"

sqlcmd -d ssp -Q "DELETE FROM challenge_category WHERE category_id = '0a640a2a-409d-1271-8140-d0c5b90a0105' AND challenge_id = '43719c57-ec92-4e4a-9fb6-25208936fd18';"

sqlcmd -d ssp -Q "DELETE FROM challenge_challenge_referral WHERE challenge_id = '07b5c3ac-3bdf-4d12-b65d-94cb55167998' AND challenge_referral_id = '3d8a27c6-920f-462b-8730-1fa91da9f78c';"
rem End of updates for Reference API where delete not possible




echo.
echo DB Clean After SSP API Integration Test Complete!
echo.
pause

rem
rem END OF SCRIPT
rem

