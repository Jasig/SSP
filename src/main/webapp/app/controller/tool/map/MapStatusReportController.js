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
Ext.define('Ssp.controller.tool.map.MapStatusReportController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		columnRendererUtils : 'columnRendererUtils',
    	currentMapPlan: 'currentMapPlan',
    	personLite: 'personLite',
    	mapStatusReportStore: 'mapStatusReportStore',
    	mapStatusReportCourseDetailsStore: 'mapStatusReportCourseDetailsStore',
    	mapStatusReportTermDetailsStore: 'mapStatusReportTermDetailsStore',
    	mapStatusReportSubstitutionDetailsStore: 'mapStatusReportSubstitutionDetailsStore',
		person: 'currentPerson'
    },	
    control: {
    	'closeButton': {
			click: 'onClose'
		},
		'calcPlanStatusButton': {
			click: 'onCalcPlanStatusButton'
		}
	},
	resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');
        me.mapStatusReportStore.addListener('load',me.loadForm, me);
		if(personId != ""){
			me.mapStatusReportStore.load(personId);
			me.mapStatusReportTermDetailsStore.load(personId);
			me.mapStatusReportCourseDetailsStore.load(personId);
			me.mapStatusReportSubstitutionDetailsStore.load(personId);

	    }
		return this.callParent(arguments);
    },
    getBaseUrl: function(id){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('mapStatusReport') );
		baseUrl = baseUrl.replace('{id}', id);
		return baseUrl;
    },    
    onCalcPlanStatusButton:function() {
		var me=this;
		var url = me.getBaseUrl(me.personLite.get('id'));
		me.getView().setLoading(true);
	    var success = function( response, view ){
	    		var personId = me.personLite.get('id');
				me.mapStatusReportStore.load(personId);
				me.mapStatusReportTermDetailsStore.load(personId);
				me.mapStatusReportCourseDetailsStore.load(personId);
				me.mapStatusReportSubstitutionDetailsStore.load(personId);
	    	view.scope.getView().setLoading(false);
			callbacks.success( response, callbacks.scope );
	    };
	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url+'/calculateStatus', 
			method: 'PUT',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});  
    },
    onClose:function() {
    	var me=this;
    	me.getView().close();
    },
	loadForm:function (){
		var me=this;
		var record = me.mapStatusReportStore.first();
		if(record)
		{
			me.getView().query("#planStatus")[0].setValue(record.get("planStatus"));
			me.getView().query("#planNote")[0].setValue(record.get("planNote"));
			me.getView().query("#planRatio")[0].setValue(record.get("planRatio"));	
			me.getView().query("#planRatioDemerits")[0].setValue(record.get("planRatioDemerits"));	
			me.getView().query("#totalPlanCourses")[0].setValue(record.get("totalPlanCourses"));	
			
		}
		
	},
	
	destroy:function(){
	    var me=this;
        me.mapStatusReportStore.removeListener('load',me.loadForm, me);
	    return me.callParent( arguments );
	}

});