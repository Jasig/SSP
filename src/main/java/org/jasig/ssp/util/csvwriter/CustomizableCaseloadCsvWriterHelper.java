/**
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jasig.ssp.util.csvwriter;

import com.google.common.collect.Lists;
import org.jasig.ssp.model.PersonSearchResultFull;
import org.springframework.util.CollectionUtils;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;


public class CustomizableCaseloadCsvWriterHelper extends AbstractCsvWriterHelper<PersonSearchResultFull> {

	private static String[] colHeaders = new String[60];

	private static String COL_PERSON_ID = "PERSON_ID";
	private static String COL_SCHOOL_ID = "SCHOOL_ID";
    private static String COL_USERNAME = "USERNAME";
	private static String COL_FIRST_NAME = "FIRST_NAME";
	private static String COL_MIDDLE_NAME = "MIDDLE_NAME";
	private static String COL_LAST_NAME = "LAST_NAME";
	private static String COL_BIRTH_DATE = "BIRTH_DATE";
    private static String COL_PRIMARY_EMAIL = "PRIMARY_EMAIL";
    private static String COL_ALTERNATE_EMAIL = "ALTERNATE_EMAIL";
    private static String COL_ADDRESS_LINE_1 = "ADDRESS_LINE_1";
    private static String COL_ADDRESS_LINE_2 = "ADDRESS_LINE_2";
    private static String COL_CITY = "CITY";
    private static String COL_STATE = "STATE";
    private static String COL_ZIP_CODE = "ZIP_CODE";
    private static String COL_RESIDENCY_COUNTY = "RESIDENCY_COUNTY";
    private static String COL_HOME_PHONE = "HOME_PHONE";
    private static String COL_WORK_PHONE = "WORK_PHONE";
    private static String COL_CELL_PHONE = "CELL_PHONE";
    private static String COL_START_TERM = "START_TERM";
    private static String COL_START_YEAR = "START YEAR";
    private static String COL_F1_STATUS = "F1_STATUS";
    private static String COL_COACH_SCHOOL_ID = "COACH_SCHOOL_ID";
    private static String COL_COACH_FIRST_NAME = "COACH_FIRST_NAME";
    private static String COL_COACH_LAST_NAME = "COACH_LAST_NAME";
    private static String COL_DEPARTMENT_NAME = "DEPARTMENT_NAME";
	private static String COL_STUDENT_TYPE_NAME = "STUDENT_TYPE";
	private static String COL_RESERVED = "RESERVED";
	private static String COL_STUDENT_INTAKE_COMPLETE_DATE = "STUDENT_INTAKE_COMPLETE_DATE";
	private static String COL_ACTIVE_ALERTS = "ACTIVE_ALERTS";
	private static String COL_CLOSED_ALERTS = "CLOSED_ALERTS";
	private static String COL_NUM_EARLY_ALERT_RESPONSES_REQUIRED = "NUM_EARLY_ALERT_RESPONSES_REQUIRED";
	private static String COL_CURRENT_PROGRAM_STATUS = "CURRENT_PROGRAM_STATUS";
    private static String COL_PROGRAM_CODE = "PROGRAM_CODE";
    private static String COL_PROGRAM_NAME = "PROGRAM_NAME";
    private static String COL_INTENDED_PROGRAM_AT_ADMIT = "INTENDED_PROGRAM_AT_ADMIT";
    private static String COL_GRADE_POINT_AVERAGE = "GRADE_POINT_AVERAGE";
    private static String COL_ACADEMIC_STANDING = "ACADEMIC_STANDING";
    private static String COL_LOCAL_GPA = "LOCAL_GPA";
    private static String COL_PROGRAM_GPA = "PROGRAM_GPA";
    private static String COL_CURRENT_REGISTRATION_STATUS = "CURRENT_REGISTRATION_STATUS";
    private static String COL_FA_COMPLETION_RATE = "FA_COMPLETION_RATE";
    private static String COL_CREDIT_HOURS_EARNED = "CREDIT_HOURS_EARNED";
    private static String COL_SAP_STATUS = "SAP_STATUS";
    private static String COL_SERVICE_REASONS = "SERVICE_REASONS";
    private static String COL_REFERRAL_SOURCES = "REFERRAL_SOURCES";
    private static String COL_SPECIAL_SERVICE_GROUPS = "SPECIAL_SERVICE_GROUPS";
    private static String COL_PLAN_TITLE = "PLAN_TITLE";
    private static String COL_PLAN_PROGRAM = "PLAN_PROGRAM";
    private static String COL_PLAN_CATALOG_YEAR = "PLAN_CATALOG_YEAR";
    private static String COL_MAP_PLAN_OWNER = "PLAN_OWNER";
    private static String COL_REQUIRED_FOR_FINANCIAL_AID_SAP = "REQUIRED_FINANCIAL_AID_SAP";
    private static String COL_REQUIRED_FOR_F1_VISA = "REQUIRED_F1_VISA";
    private static String COL_LAST_REVISED_BY = "LAST_REVISED_BY";
    private static String COL_LAST_REVISED_DATE = "LAST_REVISED_DATE";
    private static String COL_FINANCIAL_AID_GPA = "FINANCIAL_AID_GPA";
    private static String COL_HOME_CAMPUS = "HOME_CAMPUS";
    private static String COL_NUM_CONFIGURED_SUCCESS_INDICATORS_LOW = "LOW_SUCCESS_INDICATORS";
    private static String COL_NUM_CONFIGURED_SUCCESS_INDICATORS_MEDIUM = "MEDIUM_SUCCESS_INDICATORS";
    private static String COL_TRANSFER_GOAL = "TRANSFER_GOAL";
    private static String COL_PLAN_PARTIAL = "PARTIAL";

    static {
		colHeaders[0] = COL_PERSON_ID;
		colHeaders[1] = COL_SCHOOL_ID;
		colHeaders[2] = COL_FIRST_NAME;
		colHeaders[3] = COL_MIDDLE_NAME;
		colHeaders[4] = COL_LAST_NAME;
        colHeaders[5] = COL_USERNAME;
		colHeaders[6] = COL_PRIMARY_EMAIL;
        colHeaders[7] = COL_ALTERNATE_EMAIL;
		colHeaders[8] = COL_BIRTH_DATE;
        colHeaders[9] = COL_ADDRESS_LINE_1;
        colHeaders[10] = COL_ADDRESS_LINE_2;
        colHeaders[11] = COL_CITY;
        colHeaders[12] = COL_STATE;
        colHeaders[13] = COL_ZIP_CODE;
        colHeaders[14] = COL_RESIDENCY_COUNTY;
        colHeaders[15] = COL_HOME_PHONE;
        colHeaders[16] = COL_WORK_PHONE;
        colHeaders[17] = COL_CELL_PHONE;
        colHeaders[18] = COL_COACH_SCHOOL_ID;
        colHeaders[19] = COL_COACH_FIRST_NAME;
        colHeaders[20] = COL_COACH_LAST_NAME;
        colHeaders[21] = COL_DEPARTMENT_NAME;
        colHeaders[22] = COL_CURRENT_PROGRAM_STATUS;
		colHeaders[23] = COL_STUDENT_TYPE_NAME;
		colHeaders[24] = COL_RESERVED;
		colHeaders[25] = COL_STUDENT_INTAKE_COMPLETE_DATE;
		colHeaders[26] = COL_ACTIVE_ALERTS;
		colHeaders[27] = COL_CLOSED_ALERTS;
		colHeaders[28] = COL_NUM_EARLY_ALERT_RESPONSES_REQUIRED;
		colHeaders[29] = COL_START_TERM;
        colHeaders[30] = COL_START_YEAR;
        colHeaders[31] = COL_GRADE_POINT_AVERAGE;
        colHeaders[32] = COL_LOCAL_GPA;
        colHeaders[33] = COL_PROGRAM_GPA;
        colHeaders[34] = COL_CURRENT_REGISTRATION_STATUS;
        colHeaders[35] = COL_F1_STATUS;
        colHeaders[36] = COL_CREDIT_HOURS_EARNED;
        colHeaders[37] = COL_PROGRAM_CODE;
        colHeaders[38] = COL_PROGRAM_NAME;
        colHeaders[39] = COL_INTENDED_PROGRAM_AT_ADMIT;
        colHeaders[40] = COL_FA_COMPLETION_RATE;
        colHeaders[41] = COL_ACADEMIC_STANDING;
        colHeaders[42] = COL_SAP_STATUS;
        colHeaders[43] = COL_SERVICE_REASONS;
        colHeaders[44] = COL_REFERRAL_SOURCES;
        colHeaders[45] = COL_SPECIAL_SERVICE_GROUPS;
        colHeaders[46] = COL_PLAN_TITLE;
        colHeaders[47] = COL_PLAN_PROGRAM;
        colHeaders[48] = COL_PLAN_CATALOG_YEAR;
        colHeaders[49] = COL_MAP_PLAN_OWNER;
        colHeaders[50] = COL_REQUIRED_FOR_FINANCIAL_AID_SAP;
        colHeaders[51] = COL_REQUIRED_FOR_F1_VISA;
        colHeaders[52] = COL_LAST_REVISED_BY;
        colHeaders[53] = COL_LAST_REVISED_DATE;
        colHeaders[54] = COL_FINANCIAL_AID_GPA;
        colHeaders[55] = COL_HOME_CAMPUS;
        colHeaders[56] = COL_NUM_CONFIGURED_SUCCESS_INDICATORS_LOW;
        colHeaders[57] = COL_NUM_CONFIGURED_SUCCESS_INDICATORS_MEDIUM;
        colHeaders[58] = COL_TRANSFER_GOAL;
        colHeaders[59] = COL_PLAN_PARTIAL;
    }

    private final Map<Integer, Boolean> customOptions;
    private final List<String> columns;

    public CustomizableCaseloadCsvWriterHelper (PrintWriter writer, Map<Integer, Boolean> customOptions) {
		super(writer);
        this.customOptions = customOptions;
        this.columns = Lists.newArrayList();
	}

	@Override
	protected String[] csvHeaderRow() {
        if (CollectionUtils.isEmpty(columns)) {

            //Defaults that are not optional
            columns.add(colHeaders[0]);
            columns.add(colHeaders[1]);
            columns.add(colHeaders[2]);
            columns.add(colHeaders[3]);
            columns.add(colHeaders[4]);
            columns.add(colHeaders[5]);
            columns.add(colHeaders[6]);
            columns.add(colHeaders[8]);
            columns.add(colHeaders[15]);
            columns.add(colHeaders[18]);
            columns.add(colHeaders[19]);
            columns.add(colHeaders[20]);
            columns.add(colHeaders[55]); //homecampus
            columns.add(colHeaders[56]); //config success indicators low
            columns.add(colHeaders[57]); //config success indicators med


            if ( customOptions.get(0) ) {
                columns.add(colHeaders[ 9 ]);  //address
                columns.add(colHeaders[ 10 ]);
                columns.add(colHeaders[ 11 ]);
                columns.add(colHeaders[ 12 ]);
                columns.add(colHeaders[ 13 ]);
                columns.add(colHeaders[ 14 ]);
            }
            if ( customOptions.get(1) ) {
                columns.add(colHeaders[ 7 ]); //alternate contact info
                columns.add(colHeaders[ 16 ]);
                columns.add(colHeaders[ 17 ]);
            }
            if ( customOptions.get(2) ) {
                columns.add(colHeaders[ 22 ]); //program status
            }
            if ( customOptions.get(3) ) {
                //demographics
            }
            if ( customOptions.get(4) ) {
                columns.add(colHeaders[ 37 ]); //academicprogram
                columns.add(colHeaders[ 38 ]);
                columns.add(colHeaders[ 39 ]);
            }
            if ( customOptions.get(5) ) {
                columns.add(colHeaders[ 31 ]); //academic gpa
                columns.add(colHeaders[ 54 ]);
                columns.add(colHeaders[ 32 ]);
                columns.add(colHeaders[ 33 ]);
            }
            if ( customOptions.get(6) ) {
                columns.add(colHeaders[ 41 ]); //academicstanding
            }
            if ( customOptions.get(7) ) {
                columns.add(colHeaders[ 21 ]); //department
            }
            if ( customOptions.get(8) ) {
                columns.add(colHeaders[ 43 ]); //servicereasons
            }
            if ( customOptions.get(9) ) {
                columns.add(colHeaders[ 44 ]); //referralsources
            }
            if ( customOptions.get(10) ) {
                columns.add(colHeaders[ 45 ]);//specialservicegroups
            }
            if ( customOptions.get(11) ) {
                columns.add(colHeaders[ 42 ]); //sapstatus
            }
            if ( customOptions.get(12) ) {
                columns.add(colHeaders[ 29 ]); //starttermyear
                columns.add(colHeaders[ 30 ]);
            }
            if ( customOptions.get(13) ) {
                columns.add(colHeaders[ 40 ]); //facompletionrate
            }
            if ( customOptions.get(14) ) {
                columns.add(colHeaders[ 46 ]); //mapinfo
                columns.add(colHeaders[ 47 ]);
                columns.add(colHeaders[ 48 ]);
                columns.add(colHeaders[ 49 ]);
                columns.add(colHeaders[ 50 ]);
                columns.add(colHeaders[ 51 ]);
                columns.add(colHeaders[ 52 ]);
                columns.add(colHeaders[ 53 ]);
                columns.add(colHeaders[ 58 ]);
                columns.add(colHeaders[ 59 ]);
            }
            if ( customOptions.get(15) ) {
                columns.add(colHeaders[ 23 ]); //extra
                columns.add(colHeaders[ 25 ]);
                columns.add(colHeaders[ 26 ]);
                columns.add(colHeaders[ 27 ]);
                columns.add(colHeaders[ 28 ]);
                columns.add(colHeaders[ 34 ]);
                columns.add(colHeaders[ 35 ]);
                columns.add(colHeaders[ 36 ]);
            }
        }

        return columns.toArray(new String[columns.size()]);
    }

	@Override
	protected List<String[]> csvBodyRows(PersonSearchResultFull model) {
        final List<String> bodyRowsToReturn = Lists.newArrayList();

        //Defaults that are not optional
        bodyRowsToReturn.add(formatUuid(model.getPersonId()));
        bodyRowsToReturn.add(model.getSchoolId());
        bodyRowsToReturn.add(model.getFirstName());
        bodyRowsToReturn.add(model.getMiddleName());
        bodyRowsToReturn.add(model.getLastName());
        bodyRowsToReturn.add(model.getUsername());
        bodyRowsToReturn.add(model.getPrimaryEmailAddress());
        bodyRowsToReturn.add(formatDate(model.getBirthDate()));
        bodyRowsToReturn.add(model.getHomePhone());
        bodyRowsToReturn.add(model.getCoachSchoolId());
        bodyRowsToReturn.add(model.getCoachFirstName());
        bodyRowsToReturn.add(model.getCoachLastName());
        bodyRowsToReturn.add(model.getCampusName());
        bodyRowsToReturn.add(formatInt(model.getConfiguredSuccessIndicatorsLow()));
        bodyRowsToReturn.add(formatInt(model.getConfiguredSuccessIndicatorsMedium()));


        if ( customOptions.get(0) ) {
            bodyRowsToReturn.add(model.getAddressLine1());  //address
            bodyRowsToReturn.add(model.getAddressLine2());
            bodyRowsToReturn.add(model.getCity());
            bodyRowsToReturn.add(model.getState());
            bodyRowsToReturn.add(model.getZipCode());
            bodyRowsToReturn.add(model.getResidencyCounty());
        }
        if ( customOptions.get(1) ) {
            bodyRowsToReturn.add(model.getSecondaryEmailAddress()); //alternate contact info
            bodyRowsToReturn.add(model.getWorkPhone());
            bodyRowsToReturn.add(model.getCellPhone());
        }
        if ( customOptions.get(2) ) {
            bodyRowsToReturn.add(model.getCurrentProgramStatusName()); //program status
        }
        if ( customOptions.get(3) ) {
            //demographics
        }
        if ( customOptions.get(4) ) {
            bodyRowsToReturn.add(model.getAcademicProgramCode()); //academicprogram
            bodyRowsToReturn.add(model.getAcademicProgramName());
            bodyRowsToReturn.add(model.getIntendedProgramAtAdmit());
        }
        if ( customOptions.get(5) ) {
            bodyRowsToReturn.add(formatBigDecimal(model.getGradePointAverage())); //academic gpa
            bodyRowsToReturn.add(formatBigDecimal(model.getFinancialAidGpa()));
            bodyRowsToReturn.add(formatBigDecimal(model.getLocalGpa()));
            bodyRowsToReturn.add(formatBigDecimal(model.getProgramGpa()));
        }
        if ( customOptions.get(6) ) {
            bodyRowsToReturn.add(model.getAcademicStanding()); //academicstanding
        }
        if ( customOptions.get(7) ) {
            bodyRowsToReturn.add(model.getDepartmentName()); //department
        }
        if ( customOptions.get(8) ) {
            bodyRowsToReturn.add(model.getServiceReasons()); //servicereasons
        }
        if ( customOptions.get(9) ) {
            bodyRowsToReturn.add(model.getReferralSources()); //referralsources
        }
        if ( customOptions.get(10) ) {
            bodyRowsToReturn.add(model.getSpecialServiceGroups());//specialservicegroups
        }
        if ( customOptions.get(11) ) {
            bodyRowsToReturn.add(model.getSapStatus()); //sapstatus
        }
        if ( customOptions.get(12) ) {
            bodyRowsToReturn.add(model.getActualStartTerm()); //starttermyear
            bodyRowsToReturn.add(formatInt(model.getActualStartYear()));
        }
        if ( customOptions.get(13) ) {
            //facompletionrate
        }
        if ( customOptions.get(14) ) {
            bodyRowsToReturn.add(model.getPlanTitle()); //mapinfo
            bodyRowsToReturn.add(model.getPlanProgram());
            bodyRowsToReturn.add(model.getPlanCatalogYear());
            bodyRowsToReturn.add(model.getPlanOwner());
            bodyRowsToReturn.add(formatFriendlyBoolean(model.getPlanRequiredForFinancialAidSap()));
            bodyRowsToReturn.add(formatFriendlyBoolean(model.getPlanRequiredForF1Visa()));
            bodyRowsToReturn.add(model.getLastRevisedBy());
            bodyRowsToReturn.add(formatDate(model.getLastRevisedDate()));
            bodyRowsToReturn.add(model.getPlanTransferGoal());
            bodyRowsToReturn.add(formatFriendlyBoolean(model.isPlanIsPartial()));
        }
        if ( customOptions.get(15) ) {
            bodyRowsToReturn.add(model.getStudentTypeName()); //extra
            bodyRowsToReturn.add(formatDate(model.getStudentIntakeCompleteDate()));
            bodyRowsToReturn.add(formatInt(model.getActiveAlerts()));
            bodyRowsToReturn.add(formatInt(model.getClosedAlerts()));
            bodyRowsToReturn.add(formatInt(model.getNumberEarlyAlertResponsesRequired()));
            bodyRowsToReturn.add(formatInt(model.getCurrentRegistrationStatus()));
            bodyRowsToReturn.add(model.getF1Status());
            bodyRowsToReturn.add(formatBigDecimal(model.getCreditHoursEarned()));
        }

        return wrapCsvRowInList(bodyRowsToReturn.toArray(new String[bodyRowsToReturn.size()]));
	}
}
