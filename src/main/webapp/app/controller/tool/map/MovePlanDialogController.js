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
Ext.define('Ssp.controller.tool.map.MovePlanDialogController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		termsStore:'termsStore',
		currentMapPlan: 'currentMapPlan'
    },
    control: {
    	termCodeToBumpField: '#termCodeToBump',
		termCodeEndField: '#termCodeEnd',
		splitPlanField: '#splitPlan',
		movePlanButton:{
           selector: '#movePlanButton',
           listeners: {
            click: 'onMovePlan'
           }
        },
    },
    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },

	init: function() {
		var me=this;
		var view = me.getView();
		var termCodes = me.currentMapPlan.getTermCodes();
		var availableTerms = me.termsStore.getTermsFromTermCodesStore(termCodes);
		availableTerms.sort('startDate', 'ASC');
		me.termsStore.sort('startDate', 'ASC');
		if(availableTerms == null || availableTerms.getCount() < 3){
			me.getTermCodeToBumpField().bindStore(me.termsStore);
			me.getTermCodeEndField().bindStore(me.termsStore);
			return;
		}
		me.getTermCodeToBumpField().store = availableTerms;
		me.getTermCodeEndField().store = me.termsStore;
		var startTerm = availableTerms.getAt(0);
		me.getTermCodeToBumpField().setValue(startTerm.get('code'));
		var indexStartTerm = me.termsStore.find('code', startTerm.get('code'));
		me.getTermCodeEndField().setValue(me.termsStore.getAt(indexStartTerm + 1).get('code'));
		return this.callParent(arguments);
    },

    onMovePlan: function(){
    	var me = this;
        var startTermCode = me.getTermCodeToBumpField().getValue();
        var endTermCode = me.getTermCodeEndField().getValue();
		var splitPlan = me.getSplitPlanField().getValue();
		me.getView().close();
    	me.appEventsController.getApplication().fireEvent("onBumpRequested", {startTermCode:startTermCode, endTermCode:endTermCode, split:splitPlan});
		
    },

	destroy:function(){
	    var me=this;
		me.termsStore.sort('startDate', 'DESC');
	    return me.callParent( arguments );
	}
	
});