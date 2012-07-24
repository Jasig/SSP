Ext.define('Ssp.util.Constants',{
	extend: 'Ext.Component',
    statics: {
    	isRestrictedAdminItemId: function( id ){
    		var restrictedIdsArray = [
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
    		        	Ssp.util.Constants.OTHER_EARLY_ALERT_OUTCOME_ID
    		        ];
    		return ((Ext.Array.indexOf( restrictedIdsArray, id ) != -1)? true : false);
    	},
    	
    	// EDUCATION GOALS
        EDUCATION_GOAL_OTHER_ID: '78b54da7-fb19-4092-bb44-f60485678d6b',
        EDUCATION_GOAL_MILITARY_ID: '6c466885-d3f8-44d1-a301-62d6fe2d3553',
        EDUCATION_GOAL_BACHELORS_DEGREE_ID: 'efeb5536-d634-4b79-80bc-1e1041dcd3ff',
        
        // EDUCATION LEVELS
        EDUCATION_LEVEL_NO_DIPLOMA_GED_ID: '5d967ba0-e086-4426-85d5-29bc86da9295',
        EDUCATION_LEVEL_GED_ID: '710add1c-7b53-4cbe-86cb-8d7c5837d68b',
        EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID: 'f4780d23-fd8a-4758-b772-18606dca32f0',
        EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID: 'c5111182-9e2f-4252-bb61-d2cfa9700af7',
        EDUCATION_LEVEL_OTHER_ID: '247165ae-3db4-4679-ac95-ca96488c3b27',
        
        // FUNDING SOURCES
        FUNDING_SOURCE_OTHER_ID: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
        
        // CHALLENGES
        CHALLENGE_OTHER_ID: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
    
        // EARLY ALERT OUTCOME
        OTHER_EARLY_ALERT_OUTCOME_ID: '0a080114-3799-1bf5-8137-9a778e200004',
        
        GRID_ITEM_DELETE_ICON_PATH: '/ssp/images/delete-icon.png',
        GRID_ITEM_EDIT_ICON_PATH: '/ssp/images/edit-icon.jpg',
        GRID_ITEM_CLOSE_ICON_PATH: '/ssp/images/close-icon.jpg',
        GRID_ITEM_MAIL_REPLY_ICON_PATH: '/ssp/images/mail-reply-icon.png',
        
        REQUIRED_ASTERISK_DISPLAY: '<span style="color: rgb(255, 0, 0); padding-left: 2px;">*</span>',
        
        DATA_SAVE_SUCCESS_MESSAGE_STYLE: "font-weight: 'bold'; color: rgb(0, 0, 0); padding-left: 2px;",
        DATA_SAVE_SUCCESS_MESSAGE: '&#10003 Data was successfully saved',
        DATA_SAVE_SUCCESS_MESSAGE_TIMEOUT: 3000
    },

	initComponent: function() {
		return this.callParent( arguments );
    }
});