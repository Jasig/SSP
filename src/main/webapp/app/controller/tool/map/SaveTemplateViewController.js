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
Ext.define('Ssp.controller.tool.map.SaveTemplateViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan',
		programsStore: 'programsStore',
        departmentsStore: 'departmentsStore',
        authenticatedPerson: 'authenticatedPerson',
    	mapEventUtils: 'mapEventUtils',
        divisionsStore: 'divisionsStore',
        catalogYearsStore: 'catalogYearsStore',
        mapTemplateTagsStore: 'mapTemplateTagsAllStore',
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
		},
		visibilityField: '#visibility',
		mapTemplateTagsField: '#mapTemplateTagsId'

	},
	init: function() {
		var me=this;
	    me.programsStore.addListener("load", me.onShow, me, {single:true});
		me.departmentsStore.addListener("load", me.onShow,me, {single:true});
		me.divisionsStore.addListener("load", me.onShow, me, {single:true});
		me.catalogYearsStore.addListener("load", me.onShow, me, {single:true});
		me.mapTemplateTagsStore.addListener("load", me.onShow, me, {single:true});
		me.programsStore.load();
		me.departmentsStore.load();
		me.divisionsStore.load();
		me.catalogYearsStore.load();

		me.mapTemplateTagsStore.clearFilter(true);
       	me.mapTemplateTagsStore.load({callback:me.afterMapTemplateTagStoreLoaded,scope:me,single:true})

		if(!me.authenticatedPerson.hasAccess('MAP_TOOL_PUBLIC_TEMPLATE_WRITE')){
			me.getVisibilityField().setValue('PRIVATE');
			me.getVisibilityField().setDisabled(true);
		}else{
			me.getVisibilityField().setDisabled(false);
		}
		
		me.appEventsController.removeEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
   		me.appEventsController.removeEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});
		me.appEventsController.assignEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
   		me.appEventsController.assignEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});

		return me.callParent(arguments);
    },

	afterMapTemplateTagStoreLoaded: function() {
            var me = this;
            //Add a blank map template tag to the store
    		var mapTemplateTag = new Ssp.model.reference.MapTemplateTag();
    		mapTemplateTag.data.id = null;
    		mapTemplateTag.data.name = '';
    		mapTemplateTag.data.objectStatus = 'ACTIVE';
            var data = [];
    		data.push(mapTemplateTag);
    		me.mapTemplateTagsStore.insert(0, data);
    		me.mapTemplateTagsStore.commitChanges();
    	},

	checkEmpty: function(str){
    	return !str || !/[^\s]+/.test(str);
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

	onCancelClick: function(){
		me = this;
		me.doCloseWindow(false)
	},

	doCloseWindow: function(saving) {
		me.getView().close();
		if(me.getView().viewToClose){
			me.getView().viewToClose.close();
		}else if (!saving) {
			me.doNavigation();
			me.appEventsController.removeEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
			me.appEventsController.removeEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});
		}
	},
	doNavigation: function() {
		me = this;
		if(me.getView().loaderDialogEventName){
			if(me.getView().loaderDialogEventName === 'doToolsNav')
			{
				me.getView().navController.loadTool(me.getView().secondaryNavInfo);
			} else
			if(me.getView().loaderDialogEventName === 'doPersonNav')
			{
				me.appEventsController.getApplication().fireEvent('loadPerson');
			}
			else
			{
				me.appEventsController.getApplication().fireEvent(me.getView().loaderDialogEventName,me.getView().status,me.getView().actionOnPersonId);
			}
		}
	},

	doAfterSaveSuccess: function() {
		me = this;
		Ext.Msg.alert(
			me.textStore.getValueByCode('ssp.message.save-template.save-title','Save'),
			me.textStore.getValueByCode('ssp.message.save-template.save-successful','Your changes have been saved.'),
			function (btn) {me.doNavigation()}, me
			);
		me.appEventsController.removeEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
		me.appEventsController.removeEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});
	},

	doAfterSaveFailure: function() {
		me = this;
		me.doNavigation();
		me.appEventsController.removeEvent({eventName: 'doAfterSaveSuccess', callBackFunc: me.doAfterSaveSuccess, scope: me});
		me.appEventsController.removeEvent({eventName: 'doAfterSaveFailure', callBackFunc: me.doAfterSaveFailure, scope: me});
	},

    onSaveClick:function(){
    	me = this;
    	var nameField = me.getView().query('textfield[name="name"]')[0].getValue();
		var form =  me.getView().query('form')[0].getForm();
    	if(!nameField || nameField == '')
    	{
			Ext.Msg.alert(
				me.textStore.getValueByCode('ssp.message.save-template.error-title','SSP Error'),
				me.textStore.getValueByCode('ssp.message.save-template.save-error','Please give the template a name.')
				);
    		return;
    	}
    	var visibility = me.getVisibilityField().getValue();
		var validateResult = me.formUtils.validateForms( form );
		if (validateResult.valid) {
			if (visibility != 'PRIVATE') {
				if (!me.authenticatedPerson.hasAccess('MAP_TOOL_PUBLIC_TEMPLATE_WRITE')) {
					me.getVisibilityField().setValue('PRIVATE');
					Ext.Msg.alert(
						me.textStore.getValueByCode('ssp.message.save-template.error-title','SSP Error'),
						me.textStore.getValueByCode('ssp.message.save-template.no-permission','You do not have permission to save a public template.')
						);
					return;
				}
				else {
					var programCode = me.getView().query('combobox[name="programCode"]')[0].getValue();
					var departmentCode = me.getView().query('combobox[name="departmentCode"]')[0].getValue();
					var noProgramCode = (programCode == null || programCode.length <= 1);
					var noDepartmentCode = (departmentCode == null || departmentCode.length <= 1);
					var message = "It is recommended that you save a public Template associated to a specific program and a department. " +
									(noProgramCode ? "Program" : "") +
									(noProgramCode && noDepartmentCode ? " and " : "") +
									(noDepartmentCode ? "Department" : "") +
									(noProgramCode && noDepartmentCode ? " are " : " is ") +
									"not currently selected. Please select preferred option.";
					var yesButtonText = "Save with No " + (noProgramCode ? "Program" : "")
										+ (noProgramCode && noDepartmentCode ? "/" : "")
										+ (noDepartmentCode ? "Department" : "");
					if (noProgramCode || noDepartmentCode) {
						var messageBox = Ext.Msg.confirm({
							title: me.textStore.getValueByCode('ssp.message.save-template.confirm-save-title','Save Template No Program/Department Selected'),
							msg: me.textStore.getValueByCode('ssp.message.save-template.confirm-save-body',message),
							buttons: Ext.Msg.YESNOCANCEL,
							fn: me.completeSave,
							scope: me
						});
						messageBox.msgButtons['yes'].setText(me.textStore.getValueByCode('ssp.label.save-template.confirm-yes-button',yesButtonText));
						messageBox.msgButtons['no'].setText(me.textStore.getValueByCode('ssp.label.save-template.confirm-no-button',"Return To Save Dialog"));
						messageBox.msgButtons['cancel'].setText(me.textStore.getValueByCode('ssp.label.save-template.confirm-cancel-button',"Cancel Save"));
						return;
					}
				}
			}
			me.completeSave('yes');
		}
		else {
			me.formUtils.displayErrors(validateResult.fields);
		}
    },
    
    completeSave: function(btnId){
    	me = this;
    	if(btnId == 'yes'){
	    	var form =  me.getView().query('form')[0].getForm();
	    	
			form.updateRecord(me.currentMapPlan);
			// Checkbox is disabled in this case, so will be skipped on a Ext.form.Basic.updateRecord()
			// http://stackoverflow.com/questions/13364510/update-form-record-for-disabled-fields#comment18246782_13365321
			if(!me.authenticatedPerson.hasAccess('MAP_TOOL_PUBLIC_TEMPLATE_WRITE')){
				me.currentMapPlan.set('isPrivate', true);
				me.currentMapPlan.set('visibility', 'PRIVATE');
			}
			
			if(me.currentMapPlan.get('visibility') == 'PRIVATE'){
				me.currentMapPlan.set('isPrivate', true);
			}

			me.currentMapPlan.set('objectStatus', (me.getView().query('checkbox[name=objectStatus]')[0].getValue()) ? 'ACTIVE' : 'INACTIVE');
			if(!me.currentMapPlan.get('isTemplate')){
				me.currentMapPlan.set('id', '');
				me.currentMapPlan.setIsTemplate(true);
			} else {
       			var mapTemplateTagIds = me.getView().query('combobox[name="mapTemplateTagsId"]')[0].getValue();
                var records = new Array();
                var j=0;
                for (var i=0; i < mapTemplateTagIds.length; i++) {
                    var record = me.mapTemplateTagsStore.findRecord('id',mapTemplateTagIds[i], 0, false, false, true);
                    if (record != null) {
                        records[j++] = record;
                    }
                }
   				me.currentMapPlan.set('mapTemplateTags', records);
			}
	    	me.mapEventUtils.saveTemplate(me.getView().saveAs);
    	}else if(btnId == 'no'){
    		return;
    	}
    	me.doCloseWindow(true);
    },
    
    resetForm: function() {
        var me = this;
        me.getView().query('form')[0].getForm().reset();
    },
    onShow: function(){
    	var me=this;

		me.resetForm();
	    me.getView().query('form')[0].loadRecord( me.currentMapPlan );
		
	    var activenessCheckbox = me.getView().query('checkbox[name=objectStatus]')[0];
	    if (!me.currentMapPlan.get('id') || !(me.currentMapPlan.get('isTemplate')) || me.getView().saveAs) {
	        activenessCheckbox.setValue(false); // Create mode. New Templates intentionally
	                                             // inactive by default (https://issues.jasig.org/browse/SSP-1828)
	    } else {
	        // Edit mode. Preserve current state as the default.
	        activenessCheckbox.setValue(me.currentMapPlan.getAsBoolean('objectStatus',"ACTIVE"));
	    }
	    if(!me.authenticatedPerson.hasAccess('MAP_TOOL_PUBLIC_TEMPLATE_WRITE')){
	    	me.getVisibilityField().setValue('PRIVATE');
	    	me.getVisibilityField().setDisabled(true);
	    }else{
	    	if(me.getVisibilityField().getValue() == null || me.getVisibilityField().getValue() == '')
	    		me.getVisibilityField().setValue('AUTHENTICATED');
	    }
		me.checkForContactInfo();

        var mapTemplateTags = me.currentMapPlan.get('mapTemplateTags');
		if (mapTemplateTags) {
		    var mapTemplateTagIds = new Array(mapTemplateTags.length);
            for (var i=0; i < mapTemplateTags.length; i++) {
                mapTemplateTagIds[i] = mapTemplateTags[i].id;
            }
   			me.getMapTemplateTagsField().setValue(mapTemplateTagIds);
		} else {
			me.formUtils.applyActiveOnlyFilter(me.mapTemplateTagsStore);
		}
    },

	setCheckBox: function(query, fieldName){
		var me=this;
		me.getView().query(query)[0].setValue(me.currentMapPlan.getBoolean(fieldName));
	},
	
	setField: function(query, fieldName){
		var me=this;
		me.currentMapPlan.set(fieldName, me.getView().query(query)[0].getValue());
	},
	destroy:function(){
	    var me=this;
	    me.programsStore.removeListener("load", me.onShow, me, {single:true});
		me.departmentsStore.removeListener("load", me.onShow,me, {single:true});
		me.divisionsStore.removeListener("load", me.onShow, me, {single:true});
		me.catalogYearsStore.removeListener("load", me.onShow, me, {single:true});
		me.mapTemplateTagsStore.removeListener("load", me.onShow, me, {single:true});
	    return me.callParent( arguments );
	}
});
