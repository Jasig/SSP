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
Ext.define('Ssp.controller.tool.map.SaveTemplateViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan',
		programsStore: 'programsFacetedStore',
        departmentsStore: 'departmentsStore',
        authenticatedPerson: 'authenticatedPerson',
    	mapEventUtils: 'mapEventUtils',
        divisionsStore: 'divisionsStore',
        catalogYearsStore: 'catalogYearsStore',
		contactPersonStore: 'contactPersonStore'
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
		visibilityField: '#visibility'
	},
	init: function() {
		var me=this;
	    me.programsStore.addListener("load", me.onShow, me, {single:true});
		me.departmentsStore.addListener("load", me.onShow,me, {single:true});
		me.divisionsStore.addListener("load", me.onShow, me, {single:true});
		me.catalogYearsStore.addListener("load", me.onShow, me, {single:true});
		me.programsStore.load();
		me.departmentsStore.load();
		me.divisionsStore.load();
		me.catalogYearsStore.load();
		if(!me.authenticatedPerson.hasAccess('MAP_TOOL_PUBLIC_TEMPLATE_WRITE')){
			me.getVisibilityField().setValue('PRIVATE');
			me.getVisibilityField().setDisabled(true);
		}else{
			me.getVisibilityField().setDisabled(false);
		}
		
	

		return me.callParent(arguments);
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
    	me.getView().close();
		if(me.getView().viewToClose){
			me.getView().viewToClose.close();
		}else if(me.getView().loaderDialogEventName){
			me.appEventsController.getApplication().fireEvent(me.getView().loaderDialogEventName);
		}
    },
    
    onSaveClick:function(){
    	me = this;
    	var nameField = me.getView().query('textfield[name="name"]')[0].getValue();
    	if(!nameField || nameField == '')
    	{
    		Ext.Msg.alert('Error','Please give the template a name.');
    		return;
    	}
    	var visibility = me.getVisibilityField().getValue();
    	if(visibility != 'PRIVATE'){
    		if(!me.authenticatedPerson.hasAccess('MAP_TOOL_PUBLIC_TEMPLATE_WRITE')){
    			me.getVisibilityField().setValue('PRIVATE');
    			Ext.Msg.alert('Error','You do not have permission to save a public template.');
        		return;
    		}else{
    			var programCode = me.getView().query('combobox[name="programCode"]')[0].getValue();
    			var departmentCode = me.getView().query('combobox[name="departmentCode"]')[0].getValue();
				var noProgramCode = (programCode == null || programCode.length <= 1);
				var noDepartmentCode = (departmentCode == null || departmentCode.length <= 1);
				
    			if( noProgramCode || noDepartmentCode){
    				var messageBox = Ext.Msg.confirm({
            		     title:'Save Template No Program/Department Selected',
            		     msg: "It is recommended that you save a public Template associated to a specific program and a department. " +
								 (noProgramCode ? "Program":"") +
											(noProgramCode && noDepartmentCode ?" and ":"") + 
											(noDepartmentCode ? "Department":"") +
											(noProgramCode && noDepartmentCode ? " are ":" is ") +
            		     		"not currently selected. Please select preferred option.",
            		     buttons: Ext.Msg.YESNOCANCEL,
            		     fn: me.completeSave,
            		     scope: me
            		   });
    					messageBox.msgButtons['yes'].setText("Save with No " + (noProgramCode ? "Program":"") +
									(noProgramCode && noDepartmentCode ?"/":"") + 
									(noDepartmentCode ? "Department":""));
    					messageBox.msgButtons['no'].setText("Return To Save Dialog");
    					messageBox.msgButtons['cancel'].setText("Cancel Save");
    				return;
    			}
    		}
    	}
    	me.completeSave('yes');
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
			}
	    	me.mapEventUtils.saveTemplate(me.getView().saveAs);
    	}else if(btnId == 'no'){
    		return;
    	}
    	me.onCancelClick();
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
