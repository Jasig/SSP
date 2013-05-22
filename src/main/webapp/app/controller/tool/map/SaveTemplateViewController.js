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
    	currentMapPlan: 'currentMapPlan'
    },
    
	control: {
		view: {
			show: 'onShow'
		},
    	'saveButton': {
			click: 'onSaveClick',
		},
		'cancelButton': {
			click: 'onCancelClick',
		},
	},

	init: function() {
		var me=this;
	    me.resetForm();
	    me.getView().query('form')[0].loadRecord( me.currentMapPlan );

	    me.getView().query('checkbox[name=objectStatus]')[0].setValue(me.currentMapPlan.getAsBoolean('objectStatus',"ACTIVE"));
		me.setCheckBox('checkbox[name=isPrivate]', 'isPrivate');
		return me.callParent(arguments);
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
    
    onSaveClick: function(){
    	me = this;
		
    	var form =  me.getView().query('form')[0].getForm();
		form.updateRecord(me.currentMapPlan);
    	me.currentMapPlan.set('objectStatus', (me.getView().query('checkbox[name=objectStatus]')[0].getValue()) ? 'ACTIVE' : 'INACTIVE');
		me.setField('checkbox[name=isPrivate]', 'isPrivate');
		
		if(!me.currentMapPlan.get('isTemplate')){
			me.currentMapPlan.set('id', '');
			me.currentMapPlan.set('isTemplate', true);
		}
    	me.appEventsController.getApplication().fireEvent("onUpdateCurrentMapPlanPlanToolView");
    	if(me.getView().saveAs)
    	{
    		me.appEventsController.getApplication().fireEvent('onSaveAsTemplatePlan');
    	}
    	else
    	{
    		me.appEventsController.getApplication().fireEvent('onSaveTemplatePlan');
    	}
    	me.onCancelClick();
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
