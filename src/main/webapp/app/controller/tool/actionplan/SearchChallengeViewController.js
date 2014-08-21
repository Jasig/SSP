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
Ext.define('Ssp.controller.tool.actionplan.SearchChallengeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        formUtils: 'formRendererUtils',
        challengeCategoriesStore: 'challengeCategoriesStore',
        challengesStore: 'challengesAllUnpagedStore',
        searchChallengeReferralStore: 'searchChallengeReferralStore',
        personLite: 'personLite',
        confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore',
        searchChallengeReferralService: 'searchChallengeReferralService',
        store: 'addTasksStore',
        task: 'currentTask'
    },
    
    config: {
        containerToLoadInto: 'tools',
        formToDisplay: 'actionplan',
        dueDatesArray: []
    },
    
    control: {
		view: {
			render: 'onRender'
		},
        categoryNameCombo: {
            selector: '#categoryNameCombo',
            listeners: {
                select: 'onCategoryNameComboSelect'
            }
        },
        
        categoryChallengeNameCombo: {
            selector: '#categoryChallengeNameCombo',
            listeners: {
                select: 'onCategoryChallengeNameComboSelect'
            }
        },
        
        searchKeywordButton: {
            click: 'onSearchKeywordClick'
        },
        
        addChallengeReferralButton: {
            click: 'onAddChallengeReferralButtonClick'
        },
        
        addAllChallengeReferralButton: {
            click: 'onAddAllChallengeReferralButtonClick'
        },
        
        challengesgrid: {
            itemdblclick: 'onItemDblClick'
        },
        
        challengeGridPager: '#challengeGridPager',
        
        saveBulkActionPlanButton: {
            click: 'onSaveBulkActionPlanButtonClick'
        },
        
        cancelButton: {
            click: 'onCancelClick'
        },
        
        'resetChallengesButton': {
            selector: '#resetChallengesButton',
            listeners: {
                click: 'onResetChallengesButtonClick'
            }
        },
        
        searchKeyword: '#searchKeyword'
    
    
    
    },
    
    init: function(){
        var me = this;
        
        me.challengeCategoriesStore.load({
            callback: function(records, operation, success){
                var newStIntake = new Ssp.model.reference.ChallengeCategory();
                
                newStIntake.set('name', 'Student Intake Challenges');
                newStIntake.set('description', '');
                newStIntake.set('objectStatus', '');
                newStIntake.set('modifiedBy', '');
                newStIntake.set('modifiedDate', '');
                newStIntake.set('id', '1234');
                newStIntake.set('createdDate', '');
                newStIntake.set('createdBy', '');
                var data = [];
                data.push(newStIntake);
                me.challengeCategoriesStore.insert(0, data);
                me.challengeCategoriesStore.commitChanges();
            }
        });
        
        me.challengesStore.clearFilter();
        me.challengesStore.load();
        me.url = me.apiProperties.createUrl(me.apiProperties.getItemUrl('personTask'));
        me.url = me.url.replace('{id}', me.personLite.get('id'));
        
        
        me.appEventsController.assignEvent({
            eventName: 'onSearchKeyword',
            callBackFunc: me.onSearchKeywordClick,
            scope: me
        });
        
        
        
        return me.callParent(arguments);
    },
	
	onRender: function() {
		var me = this;
		me.reset();
	},
    
    destroy: function(){
        var me = this;
        
        me.appEventsController.removeEvent({
            eventName: 'onSearchKeyword',
            callBackFunc: me.onSearchKeywordClick,
            scope: me
        });
        
        return me.callParent(arguments);
    },
    
    successFunc: function(response, view){
       
    },
    
   
    
    onSearchKeywordClick: function(button){
        var me = this;
        me.searchChallenege();
    },
    
    searchChallenege: function(){
        var me = this;
        var selectedCategoryId = '';
        var selectedChallengeId = '';
        var searchText = '';
        if (me.getCategoryNameCombo().getValue() !== null && me.getCategoryNameCombo().getValue() != '1234'){
			
            selectedCategoryId = me.getCategoryNameCombo().getValue();
        }
        if (me.getCategoryChallengeNameCombo().getValue() !== null) {
            selectedChallengeId = me.getCategoryChallengeNameCombo().getValue();
			
        }
        if (me.getView().query('textfield[name=searchKeyword]')[0].value != '') {
            searchText = me.getView().query('textfield[name=searchKeyword]')[0].value;
			
        }
        if (selectedCategoryId != '' || selectedChallengeId != '' || searchText != '') {
			
            me.searchChallengeReferralService.search(selectedCategoryId, selectedChallengeId, searchText, {
                success: me.searchSuccess,
                failure: me.searchFailure,
                scope: me
            });
        }
        else 
            me.searchChallengeReferralStore.removeAll();
    },
    
    
    
    onResetChallengesButtonClick: function(button){
        var me = this;
        me.reset();
        
    },
    
    reset: function(){
        var me = this;
        
        me.getCategoryChallengeNameCombo().setValue("");
        me.getSearchKeyword().setValue("");
        me.challengesStore.clearFilter();
        me.searchChallengeReferralStore.clearFilter();
        me.searchChallengeReferralStore.removeAll();
        me.searchChallengeReferralStore.totalCount = 0;
        me.getChallengeGridPager().onLoad();
        me.handleSelect(me);
		me.getCategoryNameCombo().reset();
        
    },
    
    
    
    handleSelect: function(me){
        var params = me.getAllParams();
        me.store.load({
            params: params
        });
        var params = me.getAllParams();
        me.doFaceting(params);
        
    },
    
    getAllParams: function(){
        var me = this;
        var params = {};
       
        me.setParam(params, me.getCategoryChallengeNameCombo(), 'id');
        return params;
    },
    
    doFaceting: function(params){
        var me = this;
        var facets = [me.getCategoryChallengeNameCombo()];
        facets.forEach(function(facet){
            facet.getStore().load({
                params: params
            });
        });
    },
    
    setParam: function(params, field, fieldName){
        if (field.getValue() && field.getValue().length > 0) {
            params[fieldName] = field.getValue();
        }
    },
    
    
    searchSuccess: function(r, scope){
        var me = scope;
      
        me.getView().setLoading(false);
        
    },
    
    searchFailure: function(r, scope){
        var me = scope;
        me.getView().setLoading(false);
    },
    
    
    
    onCategoryNameComboSelect: function(comp, records, eOpts){
        var me = this;
        var baseUrl = me.apiProperties.createUrl(me.apiProperties.getItemUrl('challengeCategoriesStore'));
		
		if (Ext.ComponentQuery.query('#categoryNameCombo')[0].getRawValue() == 'Student Intake Challenges') {
			var personChallengeUrl = me.apiProperties.createUrl(me.apiProperties.getItemUrl('personChallenge'));
        	personChallengeUrl = personChallengeUrl.replace('{id}', me.personLite.get('id'));
			
			me.apiProperties.makeRequest({
				url: personChallengeUrl,
				method: 'GET',
				successFunc: me.successPersonChallengeFunc
			});
			
		}
		else {
			me.apiProperties.makeRequest({
				url: baseUrl + 'reference/category/' + comp.getValue() + '/challenge',
				method: 'GET',
				successFunc: me.successCategoryFunc
			});
		}
        
    },
	
	successPersonChallengeFunc: function(r, scope){
        var me = scope;
        
        var data = Ext.JSON.decode(r.responseText).rows;
        
        var combo = Ext.ComponentQuery.query('#categoryChallengeNameCombo')[0];
        combo.getStore().removeAll();
        combo.getStore().loadData(data);
        
        
    },
    
    successCategoryFunc: function(r, scope){
        var me = scope;
        
        var data = Ext.JSON.decode(r.responseText).rows;
        
        var combo = Ext.ComponentQuery.query('#categoryChallengeNameCombo')[0];
        combo.getStore().removeAll();
        combo.getStore().loadData(data);
        
        
    },
    
    onCategoryChallengeNameComboSelect: function(comp, records, eOpts){
        var me = this;
        if (me.getCategoryNameCombo().getValue() == null || me.getCategoryNameCombo().getRawValue() == 'Student Intake Challenges') {
			
            me.searchChallengeReferralService.search('', comp.getValue(), '', {
                success: me.searchSuccess,
                failure: me.searchFailure,
                scope: me
            });
        }
        else {
            me.searchChallengeReferralService.search(me.getCategoryNameCombo().getValue(), comp.getValue(), '', {
                success: me.searchSuccess,
                failure: me.searchFailure,
                scope: me
            });
        }
    },
    
    
    
    onAddChallengeReferralButtonClick: function(button){
        var me = this;
        
        var s = Ext.ComponentQuery.query('.challengesgrid')[0].getView().getSelectionModel().getSelection();
        Ext.each(s, function(item){
            var task = new Ssp.model.tool.actionplan.Task();
            task.set('name', item.data.challengeReferralName);
            task.set('description', item.data.challengeReferralDescription);
            task.set('link', item.data.challengeReferralLink);
            task.set('challengeReferralId', item.data.challengeReferralId);
            
            task.set('challengeId', item.data.challengeId);
            me.store.add(task);
            
        });
        
    },
    
    onAddAllChallengeReferralButtonClick: function(button){
        var me = this;
        
        var sAll = Ext.ComponentQuery.query('.challengesgrid')[0].getView().getSelectionModel().selectAll();
        var s = Ext.ComponentQuery.query('.challengesgrid')[0].getView().getSelectionModel().getSelection();
       
        Ext.each(s, function(item){
            var task = new Ssp.model.tool.actionplan.Task();
            task.set('name', item.data.challengeReferralName);
            task.set('description', item.data.challengeReferralDescription);
            task.set('link', item.data.challengeReferralLink);
            task.set('challengeReferralId', item.data.challengeReferralId);
            
            task.set('challengeId', item.data.challengeId);
            me.store.add(task);
            
        });
        Ext.ComponentQuery.query('.challengesgrid')[0].getView().getSelectionModel().deselectAll();
        
    },
    
    onItemDblClick: function(grid, record, item, index, e, eOpts){
        var me = this;
        var referralRecord = record;
        var task = new Ssp.model.tool.actionplan.Task();
        
        task.set('name', referralRecord.get('challengeReferralName'));
        task.set('description', referralRecord.get('challengeReferralDescription'));
        task.set('link', referralRecord.get('challengeReferralLink'));
        task.set('challengeReferralId', referralRecord.get('challengeReferralId'));
        
        task.set('challengeId', referralRecord.get('challengeId'));
        
        me.store.add(task);
    },
    
    onSaveBulkActionPlanButtonClick: function(button){
        var me = this;
        me.getView().setLoading(true);
        var data = [];
        var isValid = true;
        var badTasks = "";
        var seperator = "";
		
		if (me.store.data.length) {
			me.store.each(function(item, index, count){
			
				item.set('type', 'SSP');
				item.set('personId', me.personLite.get('id'));
				item.set('confidentialityLevel', {
					id: item.data.confidentialityLevel.id
				});
				if (!item.get('confidentialityLevel') || !item.get('dueDate')) {
					isValid = false;
					badTasks += seperator + item.get('name');
					seperator = " : ";
				}
				
				data.push(item.getData());
				
			});
			
			if (!isValid) {
				me.getView().setLoading(false);
				Ext.Msg.alert('SSP Error', 'There are errors in your task definitions: Confidentiality Level must be selected and a due date set. Tasks missing necessary data: ' + badTasks);
				return;
			}
			successFunc = function(response, view){
			
				me.getView().setLoading(false);
				me.loadDisplay();
				
				
			};
			failureFunc = function(response){
				me.getView().setLoading(false);
				
				me.apiProperties.handleError(response);
			};
			
			me.apiProperties.makeRequest({
				url: me.url + "/bulk",
				method: 'POST',
				jsonData: data,
				successFunc: successFunc,
				failureFunc: failureFunc
			});
		}
		else
		{
			me.getView().setLoading(false);
			Ext.Msg.alert('SSP Error', 'Please first create a Task to save it');
		} 
    },
    
    onCancelClick: function(button){
        this.loadDisplay();
    },
    
    loadDisplay: function(){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
    
    
    
});
