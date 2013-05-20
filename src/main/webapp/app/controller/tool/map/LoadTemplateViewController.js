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
Ext.define('Ssp.controller.tool.map.LoadTemplateViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan',
    	store: 'planTemplatesSummaryStore',
        personLite: 'personLite',
    	apiProperties: 'apiProperties',
    	mapPlanService:'mapPlanService',
		programsStore: 'programsStore',
        departmentsStore: 'departmentsStore',
        divisionsStore: 'divisionsStore',
    },
    
	control: {
    	'openButton': {
			click: 'onOpenClick'
		},	
		'cancelButton': {
			click: 'onCloseClick'
		},
	   'program': {
    	   selector: '#program',
    	   listeners: {
            select: 'onProgramSelect'
           }
        },
          'department':{
           selector: '#department',
           hidden: false,
           listeners: {
            select: 'onDepartmentSelect'
           }
        },
         'division':{
           selector: '#division',
           listeners: {
            select: 'onDivisionSelect'
           }
        },

        'programCancel':{
            selector: '#programCancel',
            hidden: true,
              listeners: {
               click: 'onProgramCancelClick'
              }
           },
           
        'departmentCancel':{
            selector: '#departmentCancel',
            hidden: false,
            listeners: {
             click: 'onDepartmentCancelClick'
            }
         },
        
        
        'divisionCancel':{
           selector: '#divisionCancel',
           listeners: {
            click: 'onDivisionCancelClick'
           }
        },
		'objectStatusFilter':{
            selector: '#objectStatusFilter',
            hidden: false,
            listeners: {
             select: 'onObjectStatusFilterSelect'
            }
         },

		'typeFilter':{
            selector: '#typeFilter',
            hidden: false,
            listeners: {
             select: 'onTypeFilterSelect'
            }
         },
			
	},

	init: function() {
		var me=this;
	    me.resetForm();
	    me.store.load();
		//TODO this should be automatic
		me.programsStore.load();
		me.departmentsStore.load();
		me.divisionsStore.load();
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
        	 me.mapPlanService.getTemplate(record.get('id'), callbacks)
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }    	
    },
	
	onLoadCompleteSuccess: function(serviceResponses){
        var me = this;
		if(!serviceResponses || !serviceResponses.responseText || serviceResponses.responseText.trim().length == 0) {

       	} else {
       		me.scope.currentMapPlan.loadFromServer(Ext.decode(serviceResponses.responseText));
			me.scope.appEventsController.getApplication().fireEvent('onLoadTemplatePlan');
			me.scope.getView().hide();
		}
	},

	initNewPlan: function( btnId ){
		var me=this;
		if (btnId=="yes")
		{
			me.appEventsController.getApplication().fireEvent('onCreateNewTemplatePlan');
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
	
	 onProgramSelect: function(){
	        var me=this;
	        me.handleSelect(me);
	        var params = {};
	        me.setParam(params, me.getProgram(), "programCode");
	    },  
	    
	    onProgramCancelClick: function(button){
	        var me=this;
	        me.getProgram().setValue("");
	        me.handleSelect(me);
	    },
	    
	    onDepartmentSelect: function(){
	        var me=this;
			me.handleSelect(me);
	    }, 
	    
	    onDepartmentCancelClick: function(button){
	        var me=this;
	        me.getDepartment().setValue("");
	        me.handleSelect(me);
	    },
	    
	    onDivisionSelect: function(){
	        var me=this;
			me.handleSelect(me);
	    },   
	    
	    onDivisionCancelClick: function(button){
	        var me=this;
	        me.getDivision().setValue("");
	        me.handleSelect(me);
	    },
	    
	    handleSelect: function(me){
	    	var params = {};
	    	me.setParam(params, me.getProgram(), 'programCode');
	    	me.setParam(params, me.getDepartment(), 'departmentCode');
	    	me.setParam(params, me.getDivision(), 'divisionCode');
			params["objectStatus"] = "ALL"; //Object status and object type filtered client side.
	    	me.store.on('load', this.onLoad, this, {single: true});
	    	me.store.load({params: params});
	    },
	    
	    onLoadComplete: function(){
	    	//here if we need to rebind stores.
	    },
	    
	    setParam: function(params, field, fieldName){
	    	if(field.getValue() && field.getValue().length > 0)
	    		params[fieldName] = field.getValue();
	    },
	
		onTypeFilterSelect:function(){
			var me = this;
			var type = me.getTypeFilter().getValue();
			switch(type){
				case "ALL":me.store.clearFilter(false);
				break;
				case "PRIVATE":me.store.filter("isPrivate", true);
				break;
				case "PUBLIC":me.store.filter("isPrivate", false);
				break;
			}
		},
		
		onObjectStatusFilterSelect:function(){
				var me = this;
				var objectStatus = me.getObjectStatusFilter().getValue();
				switch(objectStatus){
					case "ALL":me.store.clearFilter(false);
					break;
					case "ACTIVE":me.store.filter("objectStatus", "ACTIVE");
					break;
					case "INACTIVE":me.store.filter("objectStatus", "INACTIVE");
					break;
				}
		},
	    
		
		destroy:function(){
		    var me=this;
		    return me.callParent( arguments );
		}
		
});
