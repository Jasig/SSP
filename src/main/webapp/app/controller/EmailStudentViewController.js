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
        },
        'createJournalEntry': {
            change: 'onCreateJournalEntryChange'
        },
        confidentialityLevel: true
    },

    getIsBulk: function() {
        var me = this;
        return me.getView().getIsBulk();
    },

    getBulkCriteria: function() {
        var me = this;
        return me.getView().getBulkCriteria();
    },

    init: function() {
        var me=this;

        me.confidentialityLevelsStore.clearFilter(true);
        me.confidentialityLevelsStore.filter('objectStatus', 'ACTIVE');

        if ( me.getIsBulk() ) {
            var model = new  Ext.create('Ssp.model.BulkEmailStudentRequest', {
                criteria: me.getBulkCriteria()
            });
        } else {
            var model = new  Ext.create('Ssp.model.EmailStudentRequest');
            model.set('studentId',me.person.get('id'));
            model.set('primaryEmail',me.person.get('primaryEmailAddress'));
            model.set('sendToPrimaryEmail',me.person.get('primaryEmailAddress') ? true : false);
            model.set('secondaryEmail',me.person.get('secondaryEmailAddress'));
        }

        me.getView().getForm().loadRecord(model);
        return this.callParent(arguments);
    },
    
    onSaveClick: function(){
    	
    	var me=this;
    	var view = me.getView();
    	var form = view.getForm();

        if ( !(form.isValid()) ) {
            Ext.Msg.alert('SSP Error','Please correct the highlighted errors before resubmitting the form.');
            return ;
        }

        var record = form.updateRecord().getRecord();

        if ( me.getIsBulk() ) {
            // In bulk mode there's really no point in sending mail just to a CC addr, so we force you to pick
            // primary or secondary mail for the current search/caseload/watch set. In non-bulk mode we're a little
            // bit more lenient.
            if(!record.get("sendToPrimaryEmail") && !record.get("sendToSecondaryEmail")) {
                Ext.Msg.alert('SSP Error','Please select either primary or secondary email addresses for recipients.');
                return;
            }
        } else {
            if(!record.get("sendToPrimaryEmail") && !record.get("sendToSecondaryEmail") && record.get("additionalEmail").trim() === "" ) {
                Ext.Msg.alert('SSP Error','Please select or enter an email address for the recipient.');
                return;
            }
            if(record.get("sendToPrimaryEmail") && !record.get("primaryEmail") ) {
                Ext.Msg.alert('SSP Error','Student does not have a primary email, please unselect that option.');
                return;
            }
            if(record.get("sendToSecondaryEmail") && !record.get("secondaryEmail") ) {
                Ext.Msg.alert('SSP Error','Student does not have a secondary email, please unselect that option.');
                return;
            }
        }

        var ccAddresses = record.get("additionalEmail");
        var valid = true
        if (ccAddresses)
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

        me.sendEmail(record);

    },
    closeView: function() {
        var me = this;
        me.getView().up('.window').close();
    },
    sendEmail: function(formModel) {
        var me = this;
        var isBulk = me.getIsBulk();
        var url = isBulk ?
            me.apiProperties.createUrl( me.apiProperties.getItemUrl('bulk') )+'/email' :
            me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') )+'/email';

        var jsonData = formModel.data;

        var success;
        var failure = function(resp) {
            if ( resp && resp.responseText ) {
                var rspTextStruct = Ext.decode(resp.responseText);
                if ( rspTextStruct.message && rspTextStruct.message.indexOf("Person search parameters matched no records") > -1 ) {
                    Ext.Msg.alert('SSP Error','No user records matched your current search criteria. Try pasting your message into another document to save it for later, canceling out of this form, and retrying with different search criteria.');
                    return;
                }
            }
            Ext.Msg.alert('SSP Error','There was an issue sending your email. Please contact your administrator');
        }
        if ( isBulk ) {
            success = function() {
                Ext.Msg.alert('Bulk Email Request Queued','Your bulk email request has been queued successfully. Bulk email is sent gradually. Please expect delivery to take somewhat longer than when emailing a single student.');
                me.closeView();
            }
        } else {
            success = function() {
                Ext.Msg.alert('Email Queued','Your email has been queued successfully. Please allow several minutes for delivery.');
                me.closeView();
            }
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
        me.closeView();
    },
    validateEmailAddress: function( value ){
        var emailExpression = filter = new RegExp('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$');
        return emailExpression.test( value );
    },
    onCreateJournalEntryChange: function(field, newValue, oldValue, eOpts) {
        var me = this;
        var clCtrl = me.getConfidentialityLevel();
        clCtrl.allowBlank = !(newValue);
        Ssp.util.Util.decorateFormField(clCtrl); // highlight required fields or undo that highlighting
        if ( newValue ) {
            clCtrl.setDisabled(false);
            clCtrl.show();
        } else {
            clCtrl.setDisabled(true);
            clCtrl.hide();
        }
    }
    
});