/*
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
Ext.define('Ssp.controller.tool.map.SavePlanViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan',
        programsStore: 'programsStore',
		authenticatedPerson: 'authenticatedPerson',
    	mapEventUtils: 'mapEventUtils',
		contactPersonStore: 'contactPersonStore',
		textStore: 'sspTextStore'
    },
	control: {
		view: {
			show: 'onShow'
		},
    	'saveButton': {
			click: 'onSaveClick'
		},
		'cancelButton': {
			click: 'onCancelClick'
		}
    },
    init: function() {
        var me=this;

        me.resetForm();
        me.getView().query('form')[0].loadRecord( me.currentMapPlan );
        var activenessCheckbox = me.getView().query('checkbox[name=objectStatus]')[0];

        if ( me.currentMapPlan.get('id') ) {
            activenessCheckbox.setValue(me.currentMapPlan.getAsBoolean('objectStatus',"ACTIVE"));
        } else {
            activenessCheckbox.setValue(true);
        }

        me.setCheckBox('checkbox[name=isFinancialAid]', 'isFinancialAid');
        me.setCheckBox('checkbox[name=isImportant]', 'isImportant');
        me.setCheckBox('checkbox[name=isF1Visa]', 'isF1Visa');
        me.programsStore.load();

        me.checkForContactInfo();

        me.appEventsController.removeEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
        me.appEventsController.removeEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});
        me.appEventsController.assignEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
        me.appEventsController.assignEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});

        return me.callParent(arguments);
    },

    checkForContactInfo: function(){
        var me = this;

            if (me.checkEmpty(me.getView().query('textfield[name="contactName"]')[0].getValue())) {

                me.contactPersonStore.each(function(rec){
                var displayFullName = rec.get('displayFullName');
                var primaryEmailAddress = rec.get('primaryEmailAddress');
                var workPhone = rec.get('workPhone');

                me.getView().query('textfield[name="contactName"]')[0].setValue(displayFullName);
                me.getView().query('textfield[name="contactEmail"]')[0].setValue(primaryEmailAddress);
                me.getView().query('textfield[name="contactPhone"]')[0].setValue(workPhone);
                me.getView().query('textfield[name="contactTitle"]')[0].setValue('Academic Advisor');
            });
        }
    },

    checkEmpty: function(str){
        return !str || !/[^\s]+/.test(str);
    },
	
    getContactPersonSuccess: function( r, scope ){
        var me=scope;
    },
		
    getContactPersonFailure: function( response, scope ){
        var me=scope;
        me.getView().setLoading( false );
    },

    onCancelClick: function(){
        var me = this;
        me.doCloseWindow(false);
    },

    doCloseWindow: function(saving) {
        var me = this;
        me.getView().close();

        if (me.getView().viewToClose) {
			me.getView().viewToClose.close();
		} else if (!saving) {
			me.doNavigation();
			me.appEventsController.removeEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
			me.appEventsController.removeEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});
		}
    },

    doNavigation: function() {
        var me = this;
        if (me.getView().loaderDialogEventName) {
			if (me.getView().loaderDialogEventName === 'doToolsNav') {
				me.getView().navController.loadTool(me.getView().secondaryNavInfo);
			} else {
                if (me.getView().loaderDialogEventName === 'doPersonNav') {
                    me.appEventsController.getApplication().fireEvent('loadPerson');
                }
                else {
                    me.appEventsController.getApplication().fireEvent(me.getView().loaderDialogEventName, me.getView().status, me.getView().actionOnPersonId);
                }
            }
		}
    },

	doAfterSaveSuccess: function() {
		var me = this;
		Ext.Msg.alert(
			me.textStore.getValueByCode('ssp.message.save-plan.save-title','Save'),
			me.textStore.getValueByCode('ssp.message.save-plan.save-body','Your changes have been saved.'),
			function (btn) {me.doNavigation()}, me
        );
		me.appEventsController.removeEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
		me.appEventsController.removeEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});
	},

	doAfterSaveFailure: function() {
		var me = this;
		me.doNavigation();
		me.appEventsController.removeEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
		me.appEventsController.removeEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});
	},

    onSaveClick: function(){
    	var me = this;

    	var form =  me.getView().query('form')[0].getForm();
    	var nameField = me.getView().query('textfield[name="name"]')[0].getValue();
    	if (!nameField || nameField == '') {
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.save-plan.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.save-plan.save-error','Please give the plan a name.')
            );
    		return;
    	}

		var validateResult = me.formUtils.validateForms( form );

		if ( validateResult.valid )	{
			form.updateRecord(me.currentMapPlan);
			me.currentMapPlan.set('objectStatus', (me.getView().query('checkbox[name=objectStatus]')[0].checked) ? 'ACTIVE' : 'INACTIVE');
			me.setField('checkbox[name=isFinancialAid]', 'isFinancialAid');
			me.setField('checkbox[name=isImportant]', 'isImportant');
			me.setField('checkbox[name=isF1Visa]', 'isF1Visa');
			me.appEventsController.getApplication().fireEvent("onAfterPlanLoad");

			if (me.currentMapPlan.get('isTemplate') == true) {
				me.currentMapPlan.set('basedOnTemplateId', me.currentMapPlan.get('id'));
			}

			me.currentMapPlan.setIsTemplate(false);
			me.mapEventUtils.save(me.getView().saveAs,me.getView().secondaryNavInfo,me.getView().navController);
			me.doCloseWindow(true);

		} else {
			me.formUtils.displayErrors( validateResult.fields );
		}
    },
    resetForm: function() {
        var me = this;
        me.getView().query('form')[0].getForm().reset();
    },
    onShow: function(){
    	var me=this;
    },
	
	setCheckBox: function(query, fieldName){
		var me=this;
		me.getView().query(query)[0].setValue(me.currentMapPlan.getBoolean(fieldName));
	},
	
	setField: function(query, fieldName){
		var me=this;
		me.currentMapPlan.set(fieldName, me.getView().query(query)[0].getValue());
	}
});
