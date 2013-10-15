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
Ext.define('Ssp.controller.tool.map.MapPlanToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    control: {
    	'onPlanField': '#onPlan',
		'onPlanStatusDetails': '#onPlanStatusDetails'
    },
    inject:{
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan',
    	mapPlanService:'mapPlanService'
    },
    
	init: function() {
		var me=this;
	    me.resetForm();
		me.currentMapPlan.addListener();
		me.updatePlanStatus();
	    me.getView().loadRecord(me.currentMapPlan);
	   	me.appEventsController.getApplication().addListener("onUpdateCurrentMapPlanPlanToolView", me.onUpdateCurrentMapPlan, me);
		return me.callParent(arguments);
    },
    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },

    updatePlanStatus: function(){
		var me=this;
    	if(me.currentMapPlan.get('isTemplate') == true || me.currentMapPlan.get('personId') == ""){
    		me.getOnPlanField().setValue("");
    		return;
    	}
    	me.getView().setLoading(true);
 		var callbacks = new Object();
 		var serviceResponses = {
             failures: {},
             successes: {},
             responseCnt: 0,
             expectedResponseCnt: 1
         }
 		callbacks.success = me.newServiceSuccessHandler('planStatus', me.onPlanStatusSuccess, serviceResponses);
 		callbacks.failure = me.newServiceFailureHandler('planStatus', me.onPlanStatusFailure, serviceResponses);
 		callbacks.scope = me;
 		me.mapPlanService.planStatus(me.currentMapPlan, callbacks);
    },
    
	onUpdateCurrentMapPlan: function(){
		var me = this;
		me.getView().loadRecord(me.currentMapPlan);
		//me.updatePlanStatus();
		me.onCurrentMapPlanChange();
	},
	
	onPlanStatusSuccess:function(serviceResponses){
		var me = this;
		me.getView().setLoading(false);
		var planStatus = serviceResponses.successes.planStatus;
		if(planStatus.responseText && planStatus.responseText.length > 1)
		   planStatus = Ext.decode(planStatus.responseText);
		else
			planStatus = null;
			
		if(planStatus && planStatus.status == "ON"){
			me.getOnPlanField().setValue("On Plan");
			me.getOnPlanStatusDetails().setTooltip("Student is currently on plan.");
		}else if(planStatus && planStatus.status == "OFF"){
			me.getOnPlanField().setValue("Off Plan");
			me.getOnPlanStatusDetails().setTooltip(planStatus.statusReason);
		}
		else{
			me.getOnPlanField().setValue("No Status");
			me.getOnPlanStatusDetails().setTooltip("Currently, there is no status given for this student.");
		}
	},
	
	onPlanStatusFailure:function(){
		var me = this;
		me.getView().setLoading(false);
		me.getOnPlanField().setValue("No Status");
	},
	
	onCurrentMapPlanChange: function(){
		var me = this;
		me.appEventsController.getApplication().fireEvent("onCurrentMapPlanChangeUpdateMapView");
	},
	
	newServiceSuccessHandler: function(name, callback, serviceResponses) {
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response) {
            serviceResponses.successes[name] = response;
        });
    },

    newServiceFailureHandler: function(name, callback, serviceResponses) {
        var me = this;
        return me.newServiceHandler(name, callback, serviceResponses, function(name, serviceResponses, response) {
            serviceResponses.failures[name] = response;
        });
    },

    newServiceHandler: function(name, callback, serviceResponses, serviceResponsesCallback) {
        return function(r, scope) {
            var me = scope;
            serviceResponses.responseCnt++;
            if ( serviceResponsesCallback ) {
                serviceResponsesCallback.apply(me, [name, serviceResponses, r]);
            }
            if ( callback ) {
                callback.apply(me, [ serviceResponses ]);
            }
            me.afterServiceHandler(serviceResponses);
        };
    },

	afterServiceHandler: function(serviceResponses) {
        var me = this;
        if ( serviceResponses.responseCnt >= serviceResponses.expectedResponseCnt ) {
            //me.getView().setLoading(false);
        }
    },
	
	destroy: function(){
		var me = this;
		me.appEventsController.getApplication().removeListener("onUpdateCurrentMapPlanPlanToolView", me.onUpdateCurrentMapPlan, me);
	}
});