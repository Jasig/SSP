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
Ext.define('Ssp.controller.tool.map.MAPViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		columnRendererUtils : 'columnRendererUtils',
    	currentMapPlan: 'currentMapPlan',
		semesterStores: 'currentSemesterStores'
    },
    control: {
		view: {
			afterlayout: {
				fn: 'onAfterLayout',
				single: true
			},
    	},
    	'planFAButton': {
    	   selector: '#planFAButton',
    	   hidden: true,
    	   listeners: {
            click: 'onFAButtonClick'
           }
        },
        
        'planNotesButton':{
         selector: '#planNotesButton',
           listeners: {
            click: 'onplanNotesButtonClick'
           }
        },
        
        'loadSavedPlanButton':{
           selector: '#loadSavedPlanButton',
            listeners: {
            click: 'onloadSavedPlanButtonClick'
           }
        },
        
        'loadTemplateButton':{
           selector: '#loadTemplateButton',
           listeners: {
            click: 'onloadTemplateButtonClick'
           }
        },
        
        'saveTemplateButton':{
           selector: '#saveTemplateButton',
           listeners: {
            click: 'onsaveTemplateButtonClick'
           }
        },

		'saveTemplateAsButton':{
           selector: '#saveTemplateAsButton',
           listeners: {
            click: 'onsaveTemplateAsButtonClick'
           }
        },
        
        'savePlanAsButton':{
           selector: '#savePlanAsButton',
           listeners: {
            click: 'onsavePlanAsButtonClick'
           }
        },

		'notesLabel':{
           selector: '#notesLabel',
        },

		'name':{
           selector: '#name',
        },

        'savePlanButton':{
            selector: '#savePlanButton',
            listeners: {
             click: 'onsavePlanButtonClick'
            }
         },       
        'emailPlanButton':{
           selector: '#emailPlanButton',
           listeners: {
            click: 'onemailPlanButtonClick'
           }
        },
        'emailLabel':{
           selector: '#emailLabel',
        },
        'printPlanButton':{
           selector: '#printPlanButton',
           listeners: {
            click: 'onprintPlanButtonClick'
           }
        },
		'printLabel':{
           selector: '#printLabel',
        },

		'planOverviewButton':{
           selector: '#planOverviewButton',
           listeners: {
            click: 'onplanOverviewButtonClick'
           }
        },

		'createNewPlanButton':{
           selector: '#createNewPlanButton',
           listeners: {
              click: 'oncreateNewMapPlanButton'
           }
        }
    },
    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },
	init: function() {
		var me=this;
		var view = me.getView();
		
		me.onUpdateSaveOption();
		me.appEventsController.assignEvent({eventName: 'onSavePlanRequest', callBackFunc: me.onSavePlanRequest, scope: me});
		me.appEventsController.assignEvent({eventName: 'onSaveTemplateRequest', callBackFunc: me.onSaveTemplateRequest, scope: me});
		
		me.appEventsController.assignEvent({eventName: 'loadTemplateDialog', callBackFunc: me.loadTemplateDialog, scope: me});
		me.appEventsController.assignEvent({eventName: 'loadPlanDialog', callBackFunc: me.loadPlanDialog, scope: me});
		
		me.appEventsController.assignEvent({eventName: 'onUpdateSaveOption', callBackFunc: me.onUpdateSaveOption, scope: me});
		me.appEventsController.assignEvent({eventName: 'onCurrentMapPlanChangeUpdateMapView', callBackFunc: me.onCurrentMapPlanChange, scope: me});
	
		return this.callParent(arguments);
    },

	onAfterLayout: function(){
		var me = this;
		me.setPlanNotesButtonIcon();
	},
	
	setPlanNotesButtonIcon: function(){
		var me = this;
		var contactNotes = me.currentMapPlan.get("contactNotes");
		var studentNotes = me.currentMapPlan.get("studentNotes");
		var academicGoals = me.currentMapPlan.get("academicGoals");
		if((contactNotes && contactNotes.length > 0) ||
			(studentNotes && studentNotes.length > 0) ||
			(academicGoals && academicGoals.length > 0)){
			me.getPlanNotesButton().setIcon(Ssp.util.Constants.EDIT_TERM_NOTE_ICON_PATH);
			return;
		}
        me.getPlanNotesButton().setIcon(Ssp.util.Constants.ADD_PLAN_NOTE_ICON_PATH);
	},

    onUpdateSaveOption: function(){
        var me=this;
		if(me.currentMapPlan.get('id') == '')
			me.getView().queryById('savePlanButton').hide();
		else
			me.getView().queryById('savePlanButton').show();
    },   

    onFAButtonClick: function(button){
        var me=this;
		if(me.faPopUp == null || me.faPopUp.isDestroyed)
       		me.faPopUp = Ext.create('Ssp.view.tools.map.FAView',{hidden:true});
		me.faPopUp.show();
    },
    
    onplanNotesButtonClick: function(button){
        var me=this;
		if(me.notesPopUp == null || me.notesPopUp.isDestroyed)
       		me.notesPopUp = Ext.create('Ssp.view.tools.map.PlanNotes',{hidden:true});
		var form =  me.notesPopUp.query('form')[0].getForm();
		form.loadRecord(me.currentMapPlan);
		me.notesPopUp.query('[name=saveButton]')[0].addListener('click', me.onPlanNotesSave, me, {single:true});
	    me.notesPopUp.center();
		me.notesPopUp.show();
    },
    
	onSavePlanRequest: function(values){
        var me=this;
		if(me.savePlanPopUp == null || me.savePlanPopUp.isDestroyed)
        	me.savePlanPopUp = Ext.create('Ssp.view.tools.map.SavePlan',{hidden:true,saveAs:false, viewToClose: values.viewToClose, loaderDialogEventName:values.loaderDialogEventName});
		me.savePlanPopUp.show();
    },

	onSaveTemplateRequest: function(values){
        var me=this;
		if(me.saveTemplatePopUp == null || me.saveTemplatePopUp.isDestroyed)
        	me.saveTemplatePopUp = Ext.create('Ssp.view.tools.map.SaveTemplate',{hidden:true,saveAs:false, viewToClose: values.viewToClose, loaderDialogEventName:values.loaderDialogEventName});
		me.saveTemplatePopUp.show();
    },


	onPlanNotesSave: function(button){
		var me = this;
		var form =  button.findParentByType('form').getForm();
		form.updateRecord(me.currentMapPlan);
		me.setPlanNotesButtonIcon();
		me.notesPopUp.close();
	},

    
    onloadSavedPlanButtonClick: function(button){
		var me = this;
    	if(me.currentMapPlan.isDirty(me.semesterStores)){
			if(me.currentMapPlan.get("isTemplate")){
				Ext.Msg.confirm("Template Has Changed!", "It appears the template has been altered. Do you wish to save your changes?", me.templateDataChangedLoadingPlans, me);
				
			}
			else
				Ext.Msg.confirm("Map Plan Has Changed!", "It appears the MAP plan has been altered. Do you wish to save your changes?", me.planDataChangedLoadingPlans, me);
		}else{
			me.loadPlanDialog()
		}
    },
    
    loadPlanDialog: function(button){
        var me=this; 
		me.allPlansPopUp = Ext.create('Ssp.view.tools.map.LoadPlans',{hidden:true});
		me.allPlansPopUp.show();
    },
    
    planDataChangedLoadingPlans:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.onSavePlanRequest({loaderDialogEventName:'loadPlanDialog'});
		}else{
			me.loadPlanDialog();
		}
	},
	
	templateDataChangedLoadingPlans:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.onSaveTemplateRequest({loaderDialogEventName:'laodPlanDialog'});
		}	else{
				me.loadPlanDialog();
		}
	},
    
    
    onloadTemplateButtonClick: function(button){
    	var me = this;
    	if(me.currentMapPlan.isDirty(me.semesterStores)){
			if(me.currentMapPlan.get("isTemplate")){
				Ext.Msg.confirm("Template Has Changed!", "It appears the template has been altered. Do you wish to save your changes?", me.templateDataChangedLoadingTemplate, me);
				
			}
			else
				Ext.Msg.confirm("Map Plan Has Changed!", "It appears the MAP plan has been altered. Do you wish to save your changes?", me.planDataChangedLoadingTemplate, me);
		}else{
			me.loadTemplateDialog()
		}
    },
    
    planDataChangedLoadingTemplate:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.onSavePlanRequest({loaderDialogEventName:'loadTemplateDialog'});
		}else{
				me.loadTemplateDialog();
		}
	},
	
	templateDataChangedLoadingTemplate:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.onSaveTemplateRequest({loaderDialogEventName:'loadTemplateDialog'});
		}else{
			me.loadTemplateDialog();
		}
	},
    
    loadTemplateDialog:function(){
    	var me=this;
        if(me.allTemplatesPopUp == null || me.allTemplatesPopUp.isDestroyed)
			me.allTemplatesPopUp = Ext.create('Ssp.view.tools.map.LoadTemplates',{hidden:true});
		me.allTemplatesPopUp.show();
    },
    
    
    onsaveTemplateButtonClick: function(button){
        var me=this;
		if(me.saveTemplatePopUp == null || me.saveTemplatePopUp.isDestroyed)
         	me.saveTemplatePopUp = Ext.create('Ssp.view.tools.map.SaveTemplate',{hidden:true,saveAs:false});
		 me.saveTemplatePopUp.show();
    },

	onsaveTemplateAsButtonClick: function(button){
        var me=this;
		if(me.saveTemplatePopUp == null || me.saveTemplatePopUp.isDestroyed)
         	me.saveTemplatePopUp = Ext.create('Ssp.view.tools.map.SaveTemplate',{hidden:true,saveAs:false});
		 me.saveTemplatePopUp.show();
    },
    
    onsavePlanButtonClick: function(button){
        var me=this;
        me.savePlanPopUp = Ext.create('Ssp.view.tools.map.SavePlan',{hidden:true,saveAs:false});
		me.savePlanPopUp.show();
    },

    onsavePlanAsButtonClick: function(button){
        var me=this;
        me.savePlanPopUp = Ext.create('Ssp.view.tools.map.SavePlan',{hidden:true,saveAs:true});
		me.savePlanPopUp.show();
    },
    
    onemailPlanButtonClick: function(button){
        var me=this;
		if(me.emailPlanPopUp == null || me.emailPlanPopUp.isDestroyed)
         	me.emailPlanPopUp = Ext.create('Ssp.view.tools.map.EmailPlan',{hidden:true});
		me.emailPlanPopUp.emailEvent = 'onEmailMapPlan';
		me.emailPlanPopUp.show();
    },

	onplanOverviewButtonClick: function(button){
		var me=this;
		me.appEventsController.getApplication().fireEvent('onShowMapPlanOverView');
	},
    
    onprintPlanButtonClick: function(button){
        var me=this;
		if(me.printPlanPopUp == null || me.printPlanPopUp.isDestroyed){
			me.printPlanPopUp = Ext.create('Ssp.view.tools.map.PrintPlan',{hidden:true});
		}
		me.printPlanPopUp.printEvent = 'onPrintMapPlan';	
		me.printPlanPopUp.show();
    },
    
    ontermNotesButtonClick: function(button){
        var me=this;
		if(me.termNotesPopUp == null || me.termNotesPopUp.isDestroyed)
        	me.termNotesPopUp = Ext.create('Ssp.view.tools.map.CourseNotes',{hidden:true});
		me.termNotesPopUp.show();
    },

	oncreateNewMapPlanButton: function(button){
		var me = this;
    	if(me.currentMapPlan.isDirty(me.semesterStores)){
			if(me.currentMapPlan.get("isTemplate")){
				Ext.Msg.confirm("Template Has Changed!", "It appears the template has been altered. Do you wish to save your changes?", me.templateDataChangedNewMap, me);
				
			}
			else
				Ext.Msg.confirm("Map Plan Has Changed!", "It appears the MAP plan has been altered. Do you wish to save your changes?", me.planDataChangedNewMap, me);
		}else{
			me.appEventsController.getApplication().fireEvent('onCreateNewMapPlan');
		}
    },
    
    planDataChangedNewMap:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.onSavePlanRequest({loaderDialogEventName:'onCreateNewMapPlan'});
		}else{
			me.appEventsController.getApplication().fireEvent('onCreateNewMapPlan');
		}
	},
	
	templateDataChangedNewMap:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.onSaveTemplateRequest({loaderDialogEventName:'onCreateNewMapPlan'});
		}else{
			me.appEventsController.getApplication().fireEvent('onCreateNewMapPlan');
		}
	},

	onCurrentMapPlanChange: function(){
			var me = this;
		 if(me.currentMapPlan.get('isTemplate') == true)
		{
			if(me.currentMapPlan.get('id') || me.currentMapPlan.get('id') != "" )
			{
				me.getSaveTemplateButton().show();
			}
			me.getSavePlanButton().hide();
			me.getSaveTemplateAsButton().show();
			me.getPrintPlanButton().hide();
			me.getPrintLabel().hide();
			me.getEmailPlanButton().hide();
			me.getEmailLabel().hide();
			me.getName().setFieldLabel("Template Title");
			me.getNotesLabel().setText("Template Notes");
			me.getPlanNotesButton().setTooltip("Template Notes");
		}else{
			me.getSavePlanButton().show();
			me.getSaveTemplateAsButton().show();
			me.getPrintPlanButton().show();
			me.getPrintLabel().show();
			me.getEmailPlanButton().show();
			me.getEmailLabel().show();
			me.getName().setFieldLabel("Plan Title");
			me.getNotesLabel().setText("Plan Notes");
			me.getPlanNotesButton().setTooltip("Plan Notes");
		}
	},
    

	destroy:function(){
	    var me=this;
		me.appEventsController.removeEvent({eventName: 'onSavePlanRequest', callBackFunc: me.onSavePlanRequest, scope: me});
		me.appEventsController.removeEvent({eventName: 'onSaveTemplateRequest', callBackFunc: me.onSaveTemplateRequest, scope: me});
		
		me.appEventsController.removeEvent({eventName: 'laodTemplateDialog', callBackFunc: me.laodTemplateDialog, scope: me});
		me.appEventsController.removeEvent({eventName: 'loadPlanDialog', callBackFunc: me.loadPlanDialog, scope: me});
		
		me.appEventsController.removeEvent({eventName: 'onUpdateSaveOption', callBackFunc: me.onUpdateSaveOption, scope: me});
		me.appEventsController.removeEvent({eventName: 'onCurrentMapPlanChangeUpdateMapView', callBackFunc: me.onCurrentMapPlanChange, scope: me});
		
		
		if(me.faPopUp != null && !me.faPopUp.isDestroyed)
			me.faPopUp.close();
		if(me.notesPopUp != null && !me.notesPopUp.isDestroyed)
	    	me.notesPopUp.close();
	    //me.allPlansPopUp.close();
		if(me.allTemplatesPopUp != null && !me.allTemplatesPopUp.isDestroyed)
	    	me.allTemplatesPopUp.close();
		if(me.saveTemplatePopUp != null && !me.saveTemplatePopUp.isDestroyed)
	    	me.saveTemplatePopUp.close();
		if(me.savePlanPopUp != null && !me.savePlanPopUp.isDestroyed)
	    	me.savePlanPopUp.close();
		if(me.emailPlanPopUp != null && !me.emailPlanPopUp.isDestroyed)
	    	me.emailPlanPopUp.close();
		if(me.printPlanPopUp != null && !me.printPlanPopUp.isDestroyed)
	    	me.printPlanPopUp.close();
		if(me.termNotesPopUp != null && !me.termNotesPopUp.isDestroyed)
	    	me.termNotesPopUp.close();
		if(me.allPlansPopUp != null && !me.allPlansPopUp.isDestroyed)
		    me.allPlansPopUp.close();
	    return me.callParent( arguments );
	}
	
});