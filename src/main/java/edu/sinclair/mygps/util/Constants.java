package edu.sinclair.mygps.util;

import java.util.UUID;

public class Constants {

	public static final String ANONYMOUS_PERSON_FIRSTNAME = "Guest";
	public static final UUID ANONYMOUS_PERSON_ID = UUID
			.fromString("46DA4CB4-6EB4-4B91-8E39-8F9FA4D85552");
	public static final String ANONYMOUS_PERSON_LASTNAME = "User";
	public static final String ANONYMOUS_PERSON_USERNAME = "anonymousUser";

	public static final int SHORTENED_TEXT_LENGTH = 140;

	public static final String ACTION_PLAN_TASK = "ActionPlanTask";
	public static final String CUSTOM_ACTION_PLAN_TASK = "CustomActionPlanTask";
	public static final String SSP_ACTION_PLAN_TASK = "SSPActionPlanTask"; // action
																			// plan
																			// tasks
																			// created
																			// by
																			// your
																			// advisor
																			// -
																			// someone
																			// other
																			// than
																			// the
																			// student

	public static final String TASKTO_ID_PREFIX_DELIMITER = ":";
	public static final String TASKTO_ID_PREFIX_ACTION_PLAN_TASK = "ACT";
	public static final String TASKTO_ID_PREFIX_CUSTOM_ACTION_PLAN_TASK = "CUS";
	public static final String TASKTO_ID_PREFIX_SSP_ACTION_PLAN_TASK = "SSP";

	// EarlyAlertReferralReasonLU
	public static final String EARLY_ALERT_REFERRAL_REASON_SELF_HELP_GUIDE_THRESHOLD_EXCEEDED = "300D68EF-38C2-4B7D-AD46-7874AA5D34AC";
	public static final String EARLY_ALERT_REFERRAL_REASON_SELF_HELP_GUIDE_CRITICAL_QUESTION = "1F5729AF-0337-4E58-A001-8A9F80DBF8AA";

	// EarlyAlertFacultySuggestionLU
	public static final String EARLY_ALERT_FACULTY_SUGGESTION_SEE_ADVISOR_OR_COACH = "B2D11151-5056-A51A-80513ACDF99FEF84";

	// Early Alert Default Campus ID
	public static final String EARLY_ALERT_DEFAULT_CAMPUS_ID = "1";

	// Form Types
	public static final String FORM_TYPE_AGREEMENT = "agreement";
	public static final String FORM_TYPE_CHECKLIST = "checklist";
	public static final String FORM_TYPE_LABEL = "label";
	public static final String FORM_TYPE_RADIOLIST = "radiolist";
	public static final String FORM_TYPE_SELECT = "select";
	public static final String FORM_TYPE_TEXTAREA = "textarea";
	public static final String FORM_TYPE_TEXTINPUT = "textinput";

	// Default value for unfilled drop-down
	public static final String DEFAULT_DROPDOWN_LIST_LABEL = "-- Select One --";
	public static final String DEFAULT_DROPDOWN_LIST_VALUE = "";

	// Max number of results returned for challenge & challenge referral
	// searches
	public static final int SEARCH_RESULT_COUNT_LIMIT = 100;
	public static final int RESULT_COUNT_LIMIT = 100;

}
