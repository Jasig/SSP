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
        // Cannot do anything in init that depends on the current plan
        // having been loaded because this component potentially (and at this
        // writing does) get initialized before the component that actually
        // loads the current plan (not to mention that that load is async). So
        // we just make sure to clear out any existing view state (paranoia?),
        // set up listeners, fire up a loading spinner, then wait for a
        // subsequent event to indicate the current plan is loaded.
        me.getView().setLoading(true);
        me.resetForm();
        me.appEventsController.getApplication().addListener("onUpdateCurrentMapPlanPlanToolView", me.onUpdateCurrentMapPlan, me);
        return me.callParent(arguments);
    },
    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },

    updatePlanStatus: function(){
		var me=this;
        // Important to cache this before the async plan status lookup rather
        // than in a success or failure handler because we know the event
        // that triggers this function execution can be fired multiple times
        // before those async calls return.
        me.currentMapPlanId = me.currentMapPlan.getId();

    	if(me.currentMapPlan.get('isTemplate') == true || me.currentMapPlan.get('personId') == ""){
    		me.afterUpdatePlanStatus();
    		return;
    	}
        me.mapPlanService.planStatus(me.currentMapPlan, {
            success: function(response) {
                me.onPlanStatusSuccess(response);
                me.afterUpdatePlanStatus();
            },
            failure: function(response) {
                me.onPlanStatusFailure(response);
                me.afterUpdatePlanStatus();
            },
            scope: me
        });
    },

    afterUpdatePlanStatus: function() {
        var me = this;
        me.onCurrentMapPlanChange();
        me.getView().setLoading(false);
    },
    
	onUpdateCurrentMapPlan: function(){
		var me = this;
        if ( me.guardPlanStatusLookup() ) {
            me.getView().setLoading(true);
            me.getView().loadRecord(me.currentMapPlan);
            me.updatePlanStatus();
        }
	},

    guardPlanStatusLookup: function() {
        var me = this;
        // Can't tell the difference between two different "new" plans without
        // listening for "onCreateNewMapPlan". But can't hook into that b/c
        // SemesterPanelContainerViewController translates that event into
        // "onUpdateCurrentMapPlanPlanToolView", which this component listens
        // for. So we might not receive "onCreateNewMapPlan" until after
        // "onUpdateCurrentMapPlanPlanToolView". So this guard ends up being
        // a bit imprecise. But we assume it's probably not necessary to reload
        // the plan status if all you're doing is replacing an unsaved plan with
        // another unsaved plan.
        return me.currentMapPlanId === undefined || // first time load
            me.currentMapPlanId !== me.currentMapPlan.getId(); // obviously changing plans
    },

	
	onPlanStatusSuccess:function(response){
		var me = this;
		if(response.responseText && response.responseText.length > 1)
		    var planStatus = Ext.decode(response.responseText);
		else
		    var planStatus = null;
			
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
		me.getOnPlanField().setValue("[Status Load Failed]");
	},
	
	onCurrentMapPlanChange: function(){
		var me = this;
		me.appEventsController.getApplication().fireEvent("onCurrentMapPlanChangeUpdateMapView");
	},

	
	destroy: function(){
		var me = this;
		me.appEventsController.getApplication().removeListener("onUpdateCurrentMapPlanPlanToolView", me.onUpdateCurrentMapPlan, me);
	}
});