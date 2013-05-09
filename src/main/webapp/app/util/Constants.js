/*
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
Ext.define('Ssp.util.Constants',{
	extend: 'Ext.Component',
	
    statics: {
    	/* 
    	 * id values referenced in the restrictedIds Array will be restricted
    	 * from administrative functionality through the use of a
    	 * test against a supplied id value. In general, these
    	 * items are critically linked to other operations in the UI,
    	 * such as a field value like 'Other' which displays a description
    	 * field to collect additional associated data.
    	 * 
    	 * For example:
    	 * This method can be used inside a function call from a button or other
    	 * interface item to determine if the item is restricted. 
    	 * Ssp.util.Constants.isRestrictedAdminItemId( selection.get('id')  )
    	 * 
    	 */
    	isRestrictedAdminItemId: function( id ){
    		var restrictedIds = [
    		            Ssp.util.Constants.DISABILITY_AGENCY_OTHER_ID,
    		        	Ssp.util.Constants.EDUCATION_GOAL_OTHER_ID,
    		        	Ssp.util.Constants.EDUCATION_GOAL_MILITARY_ID,
    		        	Ssp.util.Constants.EDUCATION_GOAL_BACHELORS_DEGREE_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_NO_DIPLOMA_GED_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_GED_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID,
    		        	Ssp.util.Constants.EDUCATION_LEVEL_OTHER_ID,
    		        	Ssp.util.Constants.FUNDING_SOURCE_OTHER_ID,
    		        	Ssp.util.Constants.CHALLENGE_OTHER_ID,
    		        	Ssp.util.Constants.EARLY_ALERT_STUDENT_TYPE,
    		        	Ssp.util.Constants.OTHER_EARLY_ALERT_OUTCOME_ID,
    		        	Ssp.util.Constants.OTHER_EARLY_ALERT_REASON_ID,
    		        	Ssp.util.Constants.OTHER_EARLY_ALERT_SUGGESTION_ID,
    		        	Ssp.util.Constants.EARLY_ALERT_JOURNAL_TRACK_ID,
    		        	Ssp.util.Constants.ACTIVE_PROGRAM_STATUS_ID,
    		        	Ssp.util.Constants.NO_SHOW_PROGRAM_STATUS_ID,
    		        	Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_ID,
    		        	Ssp.util.Constants.INACTIVE_PROGRAM_STATUS_ID,
    		        	Ssp.util.Constants.TRANSITIONED_PROGRAM_STATUS_ID
    		        ];
    		return ((Ext.Array.indexOf( restrictedIds, id ) != -1)? true : false);
    	},
    	
    	// DEFAULT CONFIDENTIALITY LEVEL ID
    	// If a value is assigned here then it will be used for the default
    	// confidentiality level for lists in the SSP portlet.
    	// default to EVERYONE: 'b3d077a7-4055-0510-7967-4a09f93a0357'
    	DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID: '',

    	// DISABILITY AGENCY - ID VALUES RELATED TO ACCOMMODATION DISABILITY AGENCIES
        DISABILITY_AGENCY_OTHER_ID: '224b03d9-90da-4f9c-8959-ea2e97661f40',
    	   	
    	// EDUCATION GOALS - ID VALUES RELATED TO STUDENT INTAKE EDUCATION GOALS
        EDUCATION_GOAL_OTHER_ID: '78b54da7-fb19-4092-bb44-f60485678d6b',
        EDUCATION_GOAL_MILITARY_ID: '6c466885-d3f8-44d1-a301-62d6fe2d3553',
        EDUCATION_GOAL_BACHELORS_DEGREE_ID: 'efeb5536-d634-4b79-80bc-1e1041dcd3ff',
        
        // EDUCATION LEVELS - ID VALUES RELATED TO STUDENT INTAKE EDUCATION LEVELS
        EDUCATION_LEVEL_NO_DIPLOMA_GED_ID: '5d967ba0-e086-4426-85d5-29bc86da9295',
        EDUCATION_LEVEL_GED_ID: '710add1c-7b53-4cbe-86cb-8d7c5837d68b',
        EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID: 'f4780d23-fd8a-4758-b772-18606dca32f0',
        EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID: 'c5111182-9e2f-4252-bb61-d2cfa9700af7',
        EDUCATION_LEVEL_OTHER_ID: '247165ae-3db4-4679-ac95-ca96488c3b27',
        
        // FUNDING SOURCES - ID VALUES RELATED TO STUDENT INTAKE FUNDING SOURCES
        FUNDING_SOURCE_OTHER_ID: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
        
        // CHALLENGES - ID VALUES RELATED TO STUDENT INTAKE CHALLENGES
        CHALLENGE_OTHER_ID: '365e8c95-f356-4f1f-8d79-4771ae8b0291',

        //EARLY ALERT STUDENT TYPE - ID VALUE THAT IS USED WHEN EA IS PLACED ON STUDENT
        //WITHOUT A STUDENT TYPE
        EARLY_ALERT_STUDENT_TYPE: 'b2d05939-5056-a51a-8004-d803265d2645',
    
        // EARLY ALERT OUTCOME - ID VALUES RELATED TO EARLY ALERT OUTCOMES
        OTHER_EARLY_ALERT_OUTCOME_ID: '0a080114-3799-1bf5-8137-9a778e200004',
        OTHER_EARLY_ALERT_REASON_ID: 'b2d11335-5056-a51a-80ea-074f8fef94ea',
        OTHER_EARLY_ALERT_SUGGESTION_ID: 'b2d1120c-5056-a51a-80ea-c779a3109f8f',
        
        // EARLY ALERT - JOURNAL TRACK
        EARLY_ALERT_JOURNAL_TRACK_ID: 'b2d07b38-5056-a51a-809d-81ea2f3b27bf',
        
        // PROGRAM STATUS - ID VALUES RELATED TO PROGRAM STATUS REFERENCE DATA
        ACTIVE_PROGRAM_STATUS_ID: 'b2d12527-5056-a51a-8054-113116baab88',
        NON_PARTICIPATING_PROGRAM_STATUS_ID: 'b2d125c3-5056-a51a-8004-f1dbabde80c2',
        NO_SHOW_PROGRAM_STATUS_ID: 'b2d12640-5056-a51a-80cc-91264965731a',
        INACTIVE_PROGRAM_STATUS_ID: 'b2d125a4-5056-a51a-8042-d50b8eff0df1',
        TRANSITIONED_PROGRAM_STATUS_ID: 'b2d125e3-5056-a51a-800f-6891bc7d1ddc',
        
        // ICON PATHS FOR ACTION STYLE GRID BUTTONS NOT APPLIED THROUGH CSS 
        GRID_ITEM_DELETE_ICON_PATH: '/ssp/images/delete-icon.png',
        GRID_ITEM_EDIT_ICON_PATH: '/ssp/images/edit-icon.jpg',
		GRID_ITEM_HAS_NOTES_ICON_PATH: '/ssp/images/pencil.png',
        GRID_ITEM_CLOSE_ICON_PATH: '/ssp/images/close-icon.jpg',
        GRID_ITEM_MAIL_REPLY_ICON_PATH: '/ssp/images/mail-reply-icon.png',
        DEFAULT_NO_STUDENT_PHOTO_URL:'/ssp/images/no-photo.jpg',
        
        // CAN BE APPLIED TO THE LABEL OF A FIELD TO SHOW A RED REQUIRED ASTERISK
        REQUIRED_ASTERISK_DISPLAY: '<span style="color: rgb(255, 0, 0); padding-left: 2px;">*</span>',

        // CAN BE APPLIED TO THE LABEL OF A FIELD OR CONTAINER TO ALTER THE LABEL STYLE
        SSP_LABEL_STYLE: "color:#04408c;",        
        
        // CONFIGURES THE MESSAGE DISPLAYED NEXT TO THE SAVE BUTTON FOR TOOLS WHERE A SAVE IS ON A SINGLE SCREEN
        // FOR EXAMPLE: THIS FUNCTIONALITY IS APPLIED TO THE STUDENT INTAKE TOOL, ACTION PLAN STRENGTHS AND CONFIDENTIALITY DISCLOSURE AGREEMENT
        DATA_SAVE_SUCCESS_MESSAGE_STYLE: "font-weight: 'bold'; color: rgb(0, 0, 0); padding-left: 2px;",
        DATA_SAVE_SUCCESS_MESSAGE: '&#10003 Data was successfully saved',
        DATA_SAVE_SUCCESS_MESSAGE_TIMEOUT: 3000
    },

	initComponent: function() {
		return this.callParent( arguments );
    }
});