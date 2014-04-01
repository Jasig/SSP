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
Ext.define('Ssp.controller.tool.actionplan.EmailActionPlanController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        person: 'currentPerson',
        goalsStore: 'goalsStore',
        strengthsStore: 'strengthsStore',
        store: 'tasksStore',
        personLite: 'personLite'
    },
    config: {
        personEmailTaskUrl: ''
    },
    control: {
        'emailActionPlanButton': {
            click: 'onEmailActionPlanClick'
        }
    },
    
    init: function(){
        var me = this;
        return this.callParent(arguments);
    },
    
    onEmailActionPlanClick: function(cb, nv, ov){
        var me = this;
        var view = me.getView();
        var form = Ext.ComponentQuery.query('.emailactionplan > form')[0];
        
        var emailForm = form.getForm();
        
        var arrRecipientEmailAddresses = [];
        
        var emailAPToPrimary = Ext.ComponentQuery.query('#emailAPToPrimary', me.getView())[0].getValue();
        
        var emailAPToSecondary = Ext.ComponentQuery.query('#emailAPToSecondary', me.getView())[0].getValue();
        
        var emailAPToCoach = Ext.ComponentQuery.query('#emailAPToCoach', me.getView())[0].getValue();
        
        var ccAPToAdditional = Ext.ComponentQuery.query('#ccAPToAdditional', me.getView())[0].getValue();
        
        var primaryEmailAddress = me.person.get('primaryEmailAddress');
        
        var coachPrimaryEmailAddress = me.person.getCoachPrimaryEmailAddress();
        
        var secondaryEmailAddress = me.person.get('secondaryEmailAddress');
        
        
        
        if (!(emailForm.isValid())) {
            Ext.Msg.alert('SSP Error', 'Please correct the highlighted errors before resubmitting the form.');
            return;
        }
        if (!emailAPToPrimary && !emailAPToSecondary && !emailAPToCoach && ccAPToAdditional === "") {
            Ext.Msg.alert('SSP Error', 'Please select or enter an email address for the recipient.');
            return;
        }
        if (emailAPToPrimary && !primaryEmailAddress) {
            Ext.Msg.alert('SSP Error', 'Student does not have a primary email, please unselect that option.');
            return;
        }
        
        if (emailAPToSecondary && !secondaryEmailAddress) {
            Ext.Msg.alert('SSP Error', 'Student does not have a secondary email, please unselect that option.');
            return;
        }
        
        if (emailAPToCoach && !coachPrimaryEmailAddress) {
            Ext.Msg.alert('SSP Error', 'Coach does not have a  email, please unselect that option.');
            return;
        }
        
        
        
        var ccAddresses = ccAPToAdditional;
        var valid = true
        if (ccAddresses) {
            sendToAdditionalEmail = true;
            // validate email addresses
            if (ccAddresses.indexOf(",")) {
                emailTestArr = ccAddresses.split(',');
                Ext.each(emailTestArr, function(emailAddress, index){
                    if (valid == true) 
                        valid = this.validateEmailAddress(emailAddress.trim());
                    
                }, this);
            }
            else {
                valid = this.validateEmailAddress(ccAddresses);
                
            }
            if (valid == false) {
                Ext.Msg.alert('Error', 'One or more of the addresses you entered are invalid. Please correct the form and try again.');
                return;
            }
        }
        else {
            sendToAdditionalEmail = false;
        }
        
        
        var allGoals = [];
        me.goalsStore.each(function(rec){
            allGoals.push(rec.get('id'));
        });
        
        
        var allStrenghts = [];
        me.strengthsStore.each(function(rec){
            allStrenghts.push(rec.get('id'));
        });
        
        var allTasks = [];
        
        if (me.getSelectedTasks().length == 0) {
            me.store.each(function(rec){
                allTasks.push(rec.get('id'));
            });
        }
        else {
            var allTasks = me.getSelectedTasks();
        }
        
        //scrub emails from the json if the option has not been selected
        if (!emailAPToPrimary) {
            primaryEmailAddress = null;
        }
        if (!emailAPToSecondary) {
            secondaryEmailAddress = null;
        }
        if (!emailAPToCoach) {
            coachPrimaryEmailAddress = null;
        }
        
        jsonData = {
            "taskIds": allTasks,
            "goalIds": allGoals,
            "strengthIds": allStrenghts,
            "sendToPrimaryEmail": emailAPToPrimary,
            "primaryEmail": primaryEmailAddress,
            "sendToSecondaryEmail": emailAPToSecondary,
            "secondaryEmail": secondaryEmailAddress,
            "sendToCoachEmail": emailAPToCoach,
            "coachEmail": coachPrimaryEmailAddress,
            "sendToAdditionalEmail": sendToAdditionalEmail,
            "additionalEmail": ccAddresses
        };
        
        personId = me.personLite.get('id');
        
        me.personEmailTaskUrl = me.apiProperties.getItemUrl('personEmailTask');
        me.personEmailTaskUrl = me.personEmailTaskUrl.replace('{id}', personId);
        
        url = me.apiProperties.createUrl(me.personEmailTaskUrl);
        
        me.apiProperties.makeRequest({
            url: url,
            method: 'POST',
            jsonData: jsonData,
            successFunc: function(){
                Ext.ComponentQuery.query('.emailactionplan')[0].close();
                Ext.Msg.alert('Success', 'The task list has been sent to the listed recipient(s).');
            },
            failureFunc: function(){
                Ext.Msg.alert('SSP Error', 'There was an issue sending your action plan email.  Please contact your administrator');
                
            }
        });
    },
    
    getSelectedTasks: function(){
        var me = this;
        var allTasksGrid = Ext.ComponentQuery.query('#tasksPanel')[0];
        var allTaskIds = me.getSelectedIdsArray(allTasksGrid.getView().getSelectionModel().getSelection());
        var taskIds = Ext.Array.merge(allTaskIds);
        return taskIds;
    },
    
    getSelectedIdsArray: function(arr){
        var selectedIds = [];
        Ext.each(arr, function(item, index){
            selectedIds.push(item.get('id'));
        });
        
        return selectedIds;
    },
    
    
    handleNull: function(value, defaultValue){
        if (defaultValue == null || defaultValue == undefined) 
            defaultValue = "";
        if (value == null || value == undefined || value == 'null') 
            return defaultValue;
        return value;
    },
    
    validateEmailAddress: function(value){
        var emailExpression = filter = new RegExp('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$');
        return emailExpression.test(value);
    }
    
});
