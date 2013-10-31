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
Ext.define('Ssp.controller.EmailStudentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
	inject: {
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
		apiProperties: 'apiProperties',
        person: 'currentPerson'
	},
	
    control: {
		'saveButton': {
            click: 'onSaveClick'
        },		
		'cancelButton': {
            click: 'onCancelClick'
        }
	},
	
    init: function() {
		var me=this;
		me.confidentialityLevelsStore.clearFilter(true);
		
		me.confidentialityLevelsStore.filter('active', true);

		var model = new  Ext.create('Ssp.model.EmailStudentRequest');
		model.set('studentId',me.person.get('id'));
		model.set('primaryEmail',me.person.get('primaryEmailAddress'));
		model.set('sendToPrimaryEmail',me.person.get('primaryEmailAddress') ? true : false);
		model.set('secondaryEmail',me.person.get('secondaryEmailAddress'));
		
		me.getView().getForm().loadRecord(model);
 		return this.callParent(arguments);
    },
    
    onSaveClick: function(){
    	
    	var me=this;
    	var view = me.getView();
    	var form = view.getForm();
    	var record = view.getRecord();
    	form.updateRecord();
    	
        if ( !(form.isValid()) ) {
            Ext.Msg.alert('SSP Error','Please correct the highlighted errors before resubmitting the form.');
            return ;
        }		
        if(!record.get("sendToPrimaryEmail") && !record.get("sendToSecondaryEmail") &&  record.get("additionalEmail") ==="" ) {
            Ext.Msg.alert('SSP Error','Please select or enter an email address for the recipient.');
            return ;
        }
        if(record.get("sendToPrimaryEmail") && !record.get("primaryEmail") ) {
            Ext.Msg.alert('SSP Error','Student does not have a primary email, please unselect that option.');
            return ;
        }       
        
        if(record.get("sendToSecondaryEmail") && !record.get("secondaryEmail") ) {
            Ext.Msg.alert('SSP Error','Student does not have a secondary email, please unselect that option.');
            return ;
        }
        
        var ccAddresses = record.get("additionalEmail");
        var valid = true
	    if (ccAddresses != null)
	    {
	    	// validate email addresses
		    if ( ccAddresses.indexOf(",") )
		    {
		    	emailTestArr = ccAddresses.split(',');
		    	Ext.each(emailTestArr,function(emailAddress,index){
		    		if (valid == true)
		    			valid = this.validateEmailAddress( emailAddress );
		    	}, this);
		    }else{
		    	valid = this.validateEmailAddress( ccAddresses );
		    }

		    if (valid==false)
		    {
		    	Ext.Msg.alert('Error','One or more of the addresses you entered are invalid. Please correct the form and try again.');
		    	return;
		    }
	    }
		    
        
        //scrub emails from the json if the option has not been selected
        if(!record.get("sendToPrimaryEmail"))
        {
        	record.set("primaryEmail",null);
        }
        if(!record.get("sendToSecondaryEmail"))
        {
        	record.set("secondaryEmail",null);
        }        
        
        var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') )+'/email';
        var jsonData = 	record.data;

        var success = function()
        {
            Ext.Msg.alert('Email Sent','Your student email has been sent successfully.');
        	me.getView().up('.window').close();
        }
        
        var failure = function()
        {
            Ext.Msg.alert('SSP Error','There was an issue sending your student email.  Please contact your administrator');
        	me.getView().up('.window').close();
        }
        
        me.apiProperties.makeRequest({
			url: url,
			method: 'POST',
			jsonData: jsonData,
			successFunc: success,
			failureFunc: failure,
			scope: me
		});	
    	
    },
    onCancelClick: function(){
    	var me=this;
    	me.getView().up('.window').close();
    },
    validateEmailAddress: function( value ){
    	var emailExpression = filter = new RegExp('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$');
    	return emailExpression.test( value );
    }
    
});