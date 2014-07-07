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
		currentMapPlan: 'currentMapPlan',
        configStore: 'configurationOptionsUnpagedStore',
		semesterStores : 'currentSemesterStores'
    },
    control: {
    	termCodeToBumpField: '#termCodeToBump',
		termCodeEndField: '#termCodeEnd',
		selectActionField: {
		   selector:'#selectAction'
		},
		movePlanButton:{
           selector: '#movePlanButton',
           listeners: {
            click: 'onMovePlan'
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
		
		me.editPastTerms = me.configStore.getConfigByName('map_edit_past_terms');
		// TODO the boolean logic needs to be changed once we have a proper getConfigAsBoolean() API
		// https://issues.jasig.org/browse/SSP-2591
		if(me.editPastTerms === true || me.editPastTerms === 'true')
		{
			me.currentAndFutureTermsStore = me.termsStore.getCurrentAndFutureTermsStore(true, 8, -1);
		}
		else
			me.currentAndFutureTermsStore = me.termsStore.getCurrentAndFutureTermsStore(true, 8, null);
		
		var availableTerms = me.getAvailableTerms();
				
		me.getTermCodeToBumpField().bindStore(availableTerms);
		me.getTermCodeEndField().bindStore(me.currentAndFutureTermsStore);
		
		var startTerm = availableTerms.getAt(0);
		me.getTermCodeToBumpField().setValue(startTerm.get('code'));
		
		var indexStartTerm = me.currentAndFutureTermsStore.findExact('code', startTerm.get('code'));
		me.getTermCodeEndField().setValue(me.currentAndFutureTermsStore.getAt(indexStartTerm + 1).get('code'));
		
		var actions = Ext.create('Ext.data.Store', {
		    fields: ['action', 'name'],
		    data : [
		        {"action":"movePlan", "name":"Move Plan"},
		        {"action":"insertTerm", "name":"Insert Blank Term"},
		        {"action":"removeTerm", "name":"Remove Term"}
		    ]
		});
		
		me.getSelectActionField().bindStore(actions);
		me.getSelectActionField().setValue('movePlan');
		return this.callParent(arguments);
    },

	getAvailableTerms: function(){
		var me = this;
		
		var termCodes = me.currentMapPlan.getTermCodes();
		var availableTerms = me.termsStore.getTermsFromTermCodesStore(termCodes);
		
		availableTerms.sort('startDate', 'ASC');
		if(availableTerms == null || availableTerms.getCount() < 3){
			return me.currentAndFutureTermsStore;
		}
		if(!me.currentMapPlan.get("isTemplate")){
			var startIndex = me.currentAndFutureTermsStore.findExact('code',availableTerms.getAt(0).get("code"));
			if(startIndex < 0)
				startIndex = 0;
			var endIndex = me.currentAndFutureTermsStore.findExact('code',availableTerms.getAt(availableTerms.getCount() - 1).get("code"));
			if(endIndex < 0)
				endIndex = me.currentAndFutureTermsStore.getCount() - 1;
			if(endIndex < 0)
				return;
			availableTerms.removeAll();
			availableTerms.loadRecords(me.currentAndFutureTermsStore.getRange(startIndex, endIndex));
		}
    
		return availableTerms;
	},

    onMovePlan: function(){
    	var me = this;
        var startTermCode = me.getTermCodeToBumpField().getValue();
        var endTermCode = me.getTermCodeEndField().getValue();
		var action = me.getSelectActionField().getValue();
		me.getView().close();
		me.bumpParams = {startTermCode:startTermCode, endTermCode:endTermCode, action:action};
		if(action == 'removeTerm' && me.currentMapPlan.hasCourse(startTermCode)){
			var startTerm = me.currentAndFutureTermsStore.findRecord('code', startTermCode, 0, false, false, true);
			var message = startTerm.get('name') + ' has courses. Are you sure you want to delete it?'
			Ext.Msg.confirm({
       		     title:'Delete Term With Courses?',
       		     msg: message,
       		     buttons: Ext.Msg.YESNO,
       		     fn: me.movePlan,
       		     scope: me
       		});
		}else{
			me.movePlan('yes');
		}
    },

	movePlan:function(btnId){
		if (btnId=="yes")
     	{
			var me = this;
			me.appEventsController.getApplication().fireEvent("onBumpRequested", me.bumpParams );
		}
	},

	
	setUIForInsertRemove: function(buttonText, dialogTitle, show){
		var me = this;
		if(show)
			me.getTermCodeEndField().show();
		else
			me.getTermCodeEndField().hide();
		me.getTermCodeToBumpField().setFieldLabel(dialogTitle);
		me.getMovePlanButton().setText(buttonText);
	},
	
	destroy:function(){
	    var me=this;
	    return me.callParent( arguments );
	}
	
});