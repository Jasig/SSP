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
Ext.define('Ssp.controller.tool.map.LoadPlanViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan',
    	store: 'planStore',
        personLite: 'personLite',
    	apiProperties: 'apiProperties',
    	mapPlanService:'mapPlanService'

    },
    
	control: {
    	'openButton': {
			click: 'onOpenClick'
		},	
		'cancelButton': {
			click: 'onCloseClick'
		}
	},

	init: function() {
		var me=this;
	    me.resetForm();
	    
		personId = me.personLite.get('id');
	    
		var successFunc = function(response,view){
			
	    	var r, records;
	    	var data=[];
	    	r = Ext.decode(response.responseText);
	    	
	    	// hide the loader
	    	me.getView().setLoading( false );
	    	
	    	if (r != null)
	    	{
	    		if(r.results == 0)
	    		{
			         Ext.Msg.confirm({
			 		     title:'Create New Plan?',
			 		     msg: 'No plans have been found for this student.  Create a new one?',
			 		     buttons: Ext.Msg.YESNO,
			 		     fn: me.initNewPlan,
			 		     scope: me
			 		   });	    			
	    		}
	    		Ext.Object.each(r,function(key,value){
		    		var plans = value;
		    		Ext.Array.each(plans,function(plan,index){
		    			if(plan.name)
		    			data.push(plan);
		    		},this);
		    	},this);		    		

	    		me.store.loadData(data);
	    	}
		};
		
		me.personMapPlanUrl = me.apiProperties.getItemUrl('personMapPlan');
		me.personMapPlanUrl = me.personMapPlanUrl.replace('{id}',personId);

    	me.getView().setLoading( true );

		me.apiProperties.makeRequest({
			url: me.apiProperties.createUrl(me.personMapPlanUrl+'/summary'),
			method: 'GET',
			successFunc: successFunc 
		});
		
		me.formUtils.reconfigureGridPanel( me.getView().query('gridpanel')[0], me.store);
	    
		return me.callParent(arguments);
    },
    resetForm: function() {
        var me = this;
       // me.getView().getForm().reset();
    },
    onOpenClick: function(button) {
    	var me = this;
		var grid, record;
		var callbacks = new Object();
		callbacks.success = me.onLoadCompleteSuccess;
		callbacks.failure = me.onLoadCompleteFailure;
		callbacks.scope = me;
		
		record = me.getView().query('gridpanel')[0].getView().getSelectionModel().getSelection()[0];
        if (record) 
        {	
        	 me.mapPlanService.getPlan(record.get('id'),record.get('personId'), callbacks)
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }    	
    },
	
	onLoadCompleteSuccess: function(serviceResponses){
        var me = this;
		if(!serviceResponses || !serviceResponses.responseText || serviceResponses.responseText.trim().length == 0) {

       	} else {
       		me.scope.currentMapPlan.loadFromServer(Ext.decode(serviceResponses.responseText));
			me.scope.appEventsController.getApplication().fireEvent('onLoadMapPlan');
			me.scope.getView().hide();
		}
	},

	initNewPlan: function( btnId ){
		var me=this;
		if (btnId=="yes")
		{
			me.appEventsController.getApplication().fireEvent('onCreateNewMapPlan');
		}
		else
		{
			me.appEventsController.getApplication().fireEvent('onShowMain');
		}
		me.getView().hide();
	},
	onLoadCompleteFailure: function(serviceResponses){
		var me = this;
		view.setLoading(false);
	},	    
	onCloseClick: function(){
		var me = this;
		me.getView().hide();
	},    
});
