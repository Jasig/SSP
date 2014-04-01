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
        task: 'currentTask',
    },
    
    config: {
        containerToLoadInto: 'tools',
        formToDisplay: 'actionplan',
        dueDatesArray: []
    },
    
    control: {
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
        
        saveBulkActionPlanButton: {
            click: 'onSaveBulkActionPlanButtonClick'
        },
        
        cancelButton: {
            click: 'onCancelClick'
        },
        
        'categoryCancel': {
            selector: '#categoryCancel',
            hidden: true,
            listeners: {
                click: 'onCategoryCancelClick'
            }
        },
        
        'challengeCancel': {
            selector: '#challengeCancel',
            hidden: true,
            listeners: {
                click: 'onChallengeCancelClick'
            }
        },
        
        'searchCancel': {
            selector: '#searchCancel',
            hidden: true,
            listeners: {
                click: 'onSearchCancelClick'
            }
        },
		
		searchKeyword: '#searchKeyword'
    
    
    
    },
    
    init: function(){
        var me = this;
        
        me.challengeCategoriesStore.load();
        
        me.challengesStore.load();
        
        me.url = me.apiProperties.createUrl(me.apiProperties.getItemUrl('personTask'));
        me.url = me.url.replace('{id}', me.personLite.get('id'));
        
		me.appEventsController.assignEvent({eventName: 'onSearchKeyword', callBackFunc: me.onSearchKeywordClick, scope: me});
        
        return me.callParent(arguments);
    },
	
	destroy: function() {
    	var me=this;  	

		me.appEventsController.removeEvent({eventName: 'onSearchKeyword', callBackFunc: me.onSearchKeywordClick, scope: me});
    	
        return me.callParent( arguments );
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
        if (me.getCategoryNameCombo().getValue() !== null) {
            selectedCategoryId = me.getCategoryNameCombo().getValue();
        }
        if (me.getCategoryChallengeNameCombo().getValue() !== null) {
            selectedChallengeId = me.getCategoryChallengeNameCombo().getValue();
        }
		if(me.getView().query('textfield[name=searchKeyword]')[0].value != '')
		{
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
    
    onCategoryCancelClick: function(button){
        var me = this;
        me.getCategoryNameCombo().setValue("");
        me.handleSelect(me);
		
    },
    
    onChallengeCancelClick: function(button){
        var me = this;
        me.getCategoryChallengeNameCombo().setValue("");
        me.handleSelect(me);
		me.searchChallengeReferralStore.removeAll();
    },
	
	onSearchCancelClick: function(button){
        var me = this;
        me.getSearchKeyword().setValue("");
		 if (me.getCategoryChallengeNameCombo().getValue() !== null) {
		 	me.searchChallenege();
		 }
		 else
		 {
		 	me.searchChallengeReferralStore.removeAll();
		 }
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
        me.setParam(params, me.getCategoryNameCombo(), 'id');
        me.setParam(params, me.getCategoryChallengeNameCombo(), 'id');
        return params;
    },
    
    doFaceting: function(params){
        var me = this;
        var facets = [me.getCategoryNameCombo(), me.getCategoryChallengeNameCombo()];
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
        
        
        me.apiProperties.makeRequest({
            url: baseUrl + 'reference/category/' + comp.getValue() + '/challenge',
            method: 'GET',
            successFunc: me.successCategoryFunc
        });
        
        
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
        if (me.getCategoryNameCombo().getValue() == null) {
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
        me.store.each(function(item, index, count){
           
            item.set('type', 'SSP');
            item.set('personId', me.personLite.get('id'));
			item.set('confidentialityLevel',{id: item.data.confidentialityLevel.id});
         
            data.push(item.getData());
        });
		
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
        
    },
    
    onCancelClick: function(button){
        this.loadDisplay();
    },
    
    loadDisplay: function(){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }
    
    
    
});
