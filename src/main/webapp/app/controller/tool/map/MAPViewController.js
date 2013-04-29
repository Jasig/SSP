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
    	currentMapPlan: 'currentMapPlan'
    },
    control: {
    	'planFAButton': {
    	   selector: '#planFAButton',
    	   hidden: true,
    	   listeners: {
            click: 'onFAButtonClick'
           }
        },
        
        'planNotesButton':{
         selector: '#planNotesButton',
         hidden: true,
           listeners: {
            click: 'onplanNotesButtonClick'
           }
        },
        
        'loadSavedPlanButton':{
           selector: '#loadSavedPlanButton',
           hidden: true,
           listeners: {
            click: 'onloadSavedPlanButtonClick'
           }
        },
        
        'loadTemplateButton':{
           selector: '#loadTemplateButton',
           hidden: true,
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
        
        'savePlanAsButton':{
           selector: '#savePlanAsButton',
           listeners: {
            click: 'onsavePlanAsButtonClick'
           }
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
        
        'printPlanButton':{
           selector: '#printPlanButton',
           listeners: {
            click: 'onprintPlanButtonClick'
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
		
		if(me.currentMapPlan.get('id') == '')
			me.getView().queryById('savePlanButton').hide();
		else
			me.getView().queryById('savePlanButton').show();
		return this.callParent(arguments);
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
		me.notesPopUp.show();
    },
    
    onloadSavedPlanButtonClick: function(button){
        var me=this; 
		if(me.allPlansPopUp == null || me.allPlansPopUp.isDestroyed)
			me.allPlansPopUp = Ext.create('Ssp.view.tools.map.LoadPlans',{hidden:true});
		me.allPlansPopUp.show();
    },
    
    onloadTemplateButtonClick: function(button){
        var me=this;
        if(me.allTemplatesPopUp == null || me.allTemplatesPopUp.isDestroyed)
			me.allTemplatesPopUp = Ext.create('Ssp.view.tools.map.LoadTemplates',{hidden:true});
		me.allTemplatesPopUp.show();
    },
    
    onsaveTemplateButtonClick: function(button){
        var me=this;
		if(me.saveTemplatePopUp == null || me.saveTemplatePopUp.isDestroyed)
         	me.saveTemplatePopUp = Ext.create('Ssp.view.tools.map.SaveTemplate',{hidden:true});
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
		me.emailPlanPopUp.show();
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
        var me=this;
		this.appEventsController.getApplication().fireEvent('onCreateNewMapPlan');
    },
    

	destroy:function(){
	    var me=this;
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