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




Ext.define('Ssp.controller.admin.map.MapAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
		store: 'planTemplatesSummaryStore',
        formUtils: 'formRendererUtils',
        semesterStores : 'currentSemesterStores',
        authenticatedPerson: 'authenticatedPerson',
        personLite: 'personLite'
    },
    config: {
        containerToLoadInto: 'adminforms',
    },
    refs: [
           {
               ref : 'templatePanel',
               selector: '#templatePanel'
           }
   ],
    control: {
        //'saveButton': {
        //    click: 'onSaveClick'
        //},
              
        isTemplateActive: {
            selector: '#isTemplateActive',
            listeners: {
            	checkChange: 'onCheckChange'
            }
        }
        
    },
    init: function() {    	
        var me=this;

        me.formUtils.reconfigureGridPanel( me.getView(), me.store);
        me.store.load();

        return me.callParent(arguments);
    },

    onEditClick: function(view, record, item, index, event, eventListenerOpts) {
        this.displayEditor(record);
    },

    onSaveClick: function(button){
    	//alert('something was saved');
    },    
    
	getTemplateBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('templatePlan') );
		return baseUrl;
    },
    
    onGetTemplateSuccess: function(response, t) {    	
    	var me = t;
    	var callbacks = new Object();
    	callbacks.success = me.onLoadCompleteSuccess;
		callbacks.failure = me.onLoadCompleteFailure;
		callbacks.scope = me;		
    	var mapPlanService = Ext.create('Ssp.service.MapPlanService');
		var plan = Ext.create('Ssp.model.tool.map.Plan');
		var grid = Ext.getCmp("templatePanel");		
    	var planfromResponse = Ext.decode(response.responseText);
    	    	
    	if (planfromResponse.objectStatus == 'INACTIVE')
    	{
    		planfromResponse.objectStatus = 'ACTIVE';
    	}
    	else
    	{
    		planfromResponse.objectStatus = 'INACTIVE';
    	}
    	
    	plan.loadFromServer(planfromResponse);    	
		plan.set('ownerId',me.authenticatedPerson.get('id'));
		plan.set('isTemplate', true);
        plan.set('personId',  me.personLite.get('id'));
        plan.set('personId', false);
        plan.set('isPrivate', false);
        
        
        var planCourses = plan.get('planCourses');
        for(var k = 0; k < planCourses.length; k++){
    		course = planCourses[k];
    		course.id=null;
    	}
        
    	
    	var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('templatePlan') );
    	
    	// update
		me.apiProperties.makeRequest({
			url: url+'/'+ plan.get('id'), 
			method: 'PUT',
			jsonData: plan.getSimpleJsonData(),
			successFunc: callbacks.success,
			failureFunc: callbacks.failure,
			scope: me
		});	   	
    },
    onGetTemplateFailure: function(response, t) {
    	console.log('Get Template From ServerFailure: ' + JSON.stringify(response));
    },
    
    //maintain list of eventual saved items
    onCheckChange: function(column, rowIndex, checked, eOpts){
    	
    	var me = this;
    	
    	var mapPlanService = Ext.create('Ssp.service.MapPlanService');
		var plan = Ext.create('Ssp.model.tool.map.Plan');
		var grid = Ext.getCmp("templatePanel");		
		var apiProps = Ext.create('Ssp.service.MapPlanService');
		
		var callbacks = new Object();
		callbacks.success = me.onGetTemplateSuccess;
		callbacks.failure = me.onLoadCompleteFailure;
		callbacks.scope = me;		
		plan.loadFromServer(grid.store.getAt(rowIndex).data);
		    
		mapPlanService.getTemplate(plan.get('id'), callbacks);
		//me.updateTemplate(plan);
    },
    
    updateTemplate: function(plan) {
    	var me = this;
    	
    	//var plan = Ext.create('Ssp.model.tool.map.Plan');
    	//plan.loadFromServer(plant);
    	
    	var callbacks = new Object();
    	callbacks.success = me.onLoadCompleteSuccess;
		callbacks.failure = me.onLoadCompleteFailure;
		callbacks.scope = me;		
		var grid = Ext.getCmp("templatePanel");		
    	
    	
    	plan.set('ownerId',me.authenticatedPerson.get('id'));
		plan.set('isTemplate', true);
        plan.set('personId',  me.personLite.get('id'));
        plan.set('personId', false);
        plan.set('isPrivate', false);
        
        
        var planCourses = plan.get('planCourses');
        for(var k = 0; k < planCourses.length; k++){
    		course = planCourses[k];
    		course.id=null;
    	}
        
    	var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('templatePlan') );
    	
    	// update
		me.apiProperties.makeRequest({
			url: url+'/'+ plan.get('id'), 
			method: 'PUT',
			jsonData: plan.getSimpleJsonData(),
			successFunc: callbacks.success,
			failureFunc: callbacks.failure,
			scope: callbacks.scope
		});	   	
    },
    
    
    
    
	getTemplateBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('templatePlan') );
		return baseUrl;
    },
        
    saveTemplate: function(semesterStores, currentMapTemplate ){
		var me=this;
		var url = me.getTemplateBaseUrl();
		//me.currentMapTemplate.set('ownerId',me.authenticatedPerson.get('id'));
		
		
		var plan = Ext.create('Ssp.model.tool.map.Plan');
		plan.loadFromServer(currentMapTemplate);
				
	    console.log(plan.getSimpleJsonData());

		
		var callbacks = new Object();
		callbacks.success = me.onLoadCompleteSuccess;
		callbacks.failure = me.onLoadCompleteFailure;
		callbacks.scope = me;
		
	    var success = function( response ){
	    	callbacks.success( response, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	 
	    	callbacks.failure( response, callbacks.scope );
	    };
			   

	    
		// update
		me.apiProperties.makeRequest({
			url: url+'/'+ plan.get('id'), 
			//url: url+'/'+ plan.id,
			method: 'PUT',
			jsonData: plan.getSimpleJsonData(),
			successFunc: success,
			failureFunc: failure,
			scope: me
		});	
			
    },
    
    onLoadCompleteFailure: function(response, t) {
     	alert('failure: ' + response + ' '+ t);
    },
    
    onLoadCompleteSuccess: function(response, t) {
    	var me = t;
    	Ext.getCmp('templatePanel').getStore().load();
    }
    
    
    
    
});