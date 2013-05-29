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
/* http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.controller.tool.map.MAPController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		columnRendererUtils : 'columnRendererUtils',
    	currentMapPlan: 'currentMapPlan',
		semesterStores : 'currentSemesterStores'
    },
	control: {
		view: {
			beforedestroy:{
				fn: 'onDestroy',
				single: true,
			},
		},
	},
	resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },
	init: function() {
		var me=this;
		return this.callParent(arguments);
    },
	
	
	onDestroy:function(){
		var me = this;
		if(me.currentMapPlan.isDirty(me.semesterStores)){
			if(me.currentMapPlan.get("isTemplate"))
				Ext.Msg.confirm("Template Has Changed!", "It appears the template has been altered. Do you wish to save your changes?", me.templateDataHasChanged, me);
			else
				Ext.Msg.confirm("Map Plan Has Changed!", "It appears the MAP plan has been altered. Do you wish to save your changes?", me.planDataHasChanged, me);
			return false;
		}
		return true;
	},
	
	planDataHasChanged:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.appEventsController.getApplication().fireEvent('onSavePlanRequest', {viewToClose:me.getView()});
		}
	},
	
	templateDataHasChanged:function(buttonId){
		var me = this;
		if(buttonId == "on" || buttonId == "yes"){
			me.appEventsController.getApplication().fireEvent('onSaveTemplateRequest', {viewToClose:me.getView()});
		}
	},
	
	destroy:function(){
	    var me=this;
	    return me.callParent( arguments );
	}
});