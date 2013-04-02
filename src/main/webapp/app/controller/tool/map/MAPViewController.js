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
    	appEventsController: 'appEventsController'
       
    },
    control: {
    	'planFAButton': {
    	   selector: '#planFAButton',
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
        }
    },
    
	init: function() {
		var me=this;
		
		
		return this.callParent(arguments);
    },
    
    onFAButtonClick: function(button){
        var me=this;
        var faPopUp = Ext.create('Ssp.view.tools.map.FAView');
        faPopUp.setPosition(300,50);
        faPopUp.show();
 
    },
    
    onplanNotesButtonClick: function(button){
        var me=this;
        var notestPopUp = Ext.create('Ssp.view.tools.map.PlanNotes');
        notestPopUp.setPosition(300,50);
        notestPopUp.show();
 
    },
    
    onloadSavedPlanButtonClick: function(button){
        var me=this;
        var allPlansPopUp = Ext.create('Ssp.view.tools.map.LoadPlans');
        allPlansPopUp.setPosition(300,50);
        allPlansPopUp.show();
 
    },
    
    onloadTemplateButtonClick: function(button){
        var me=this;
        var allTemplatesPopUp = Ext.create('Ssp.view.tools.map.LoadTemplates');
        allTemplatesPopUp.setPosition(300,50);
        allTemplatesPopUp.show();
 
    },
    
    onsaveTemplateButtonClick: function(button){
        var me=this;
        var saveTemplatePopUp = Ext.create('Ssp.view.tools.map.SaveTemplate');
        saveTemplatePopUp.setPosition(300,50);
        saveTemplatePopUp.show();
 
    },
    
    onsavePlanButtonClick: function(button){
        var me=this;
        var savePlanPopUp = Ext.create('Ssp.view.tools.map.SavePlan');
        savePlanPopUp.setPosition(300,50);
        savePlanPopUp.show();
 
    },
    
    onemailPlanButtonClick: function(button){
        var me=this;
        var emailPlanPopUp = Ext.create('Ssp.view.tools.map.EmailPlan');
        emailPlanPopUp.setPosition(300,50);
        emailPlanPopUp.show();
 
    },
    
    onprintPlanButtonClick: function(button){
        var me=this;
        var printPlanPopUp = Ext.create('Ssp.view.tools.map.PrintPlan');
        printPlanPopUp.setPosition(300,50);
        printPlanPopUp.show();
 
    },
    
    ontermNotesButtonClick: function(button){
        var me=this;
        var termNotesPopUp = Ext.create('Ssp.view.tools.map.CourseNotes');
        termNotesPopUp.setPosition(300,50);
        termNotesPopUp.show();
 
    }

	
	
});
