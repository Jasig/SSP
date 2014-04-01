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
Ext.define('Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        formUtils: 'formRendererUtils',
        model: 'currentGoal',
        personLite: 'personLite',
        preferences: 'preferences',
        store: 'goalsStore',
        confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore'
    },
    
    control: {
        view: {
            viewready: 'onViewReady',
            beforeedit: 'onBeforeEdit',
            edit: 'editRecord'
        },
        
        addGoalButton: {
            selector: '#addGoalButton',
            listeners: {
                click: 'onAddGoalClick'
            }
        },
        
        'deleteGoalButton': {
            click: 'onDeleteGoalClick'
        }
    },
    
    constructor: function(){
        // reconfigure the url for the current person
        this.url = this.apiProperties.createUrl(this.apiProperties.getItemUrl('personGoal'));
        this.url = this.url.replace('{id}', this.personLite.get('id'));
        
        
        // apply the person url to the store proxy
        Ext.apply(this.store.getProxy(), {
            url: this.url
        });
        
        // load records
        this.store.load();
        
        return this.callParent(arguments);
    },
    
    onViewReady: function(comp, obj){
        var me = this;
        me.getAddGoalButton().setDisabled(!me.authenticatedPerson.hasPermission('ROLE_PERSON_GOAL_WRITE'));
        me.appEventsController.assignEvent({
            eventName: 'deleteGoal',
            callBackFunc: this.deleteConfirmation,
            scope: this
        });
        
    },
    
    destroy: function(){
    
        this.appEventsController.removeEvent({
            eventName: 'deleteGoal',
            callBackFunc: this.deleteConfirmation,
            scope: this
        });
        
        return this.callParent(arguments);
    },
    
    onAddGoalClick: function(button){
        var me = this;
        
        var grid = button.up('grid');
        var store = grid.getStore();
        
        var item = new Ssp.model.PersonGoal();
        grid.plugins[0].cancelEdit();
        
        
        //set default values
        Ext.Array.each(grid.columns, function(col, index){
        
            if (col.defaultValue != null) {
                item.set(col.dataIndex, col.defaultValue);
            }
            else {
                item.set(col.dataIndex, 'default');
                
            }
        });
        
        store.insert(0, item);
        grid.plugins[0].startEdit(0, 0);
        var editorItems = grid.plugins[0].editor.items;
        if (editorItems.getAt(0).xtype == "textfield") {
            editorItems.getAt(0).selectText();
        }
        
    },
    
    onBeforeEdit: function(editor, e, eOpts){
        var me = this;
        
        var access = me.authenticatedPerson.hasAccess('EDIT_GOAL_BUTTON');
        if (!me.authenticatedPerson.hasAccess('EDIT_GOAL_BUTTON')) {
            return false;
            
        }
        else {
            var items = editor.editor.items.items;
            me.formUtils.applyAssociativeStoreFilter(me.confidentialityLevelsStore, me.model.get('confidentialityLevel').id);
            
            var cmb = Ext.ComponentQuery.query('#goalsCFCombo')[0];
            Ext.Array.each(items, function(item, index, count){
                if (item.xtype == 'combo') {
					me.confidentialityLevelsStore.on({
						load: {
							fn: function(store, records, state, operation, opts){
								if (e.record.get('confidentialityLevel').id == null || e.record.get('confidentialityLevel').id == '') {
									var confidentialityLevelId = me.confidentialityLevelsStore.findRecord('name', 'EVERYONE').get('id');
									item.setValue(confidentialityLevelId);
								}
								else {
									item.setValue(e.record.get('confidentialityLevel').id);
								}
							},
							scope: this,
							single: true
						}
					});
                    me.confidentialityLevelsStore.load();
                  
                }
            });
        }
        return access;
    },
    
    editRecord: function(editor, e, eOpts){
        var me = this;
        var record = e.record;
        var id = record.get('id');
        var jsonData = record.data;
        
        var confLevelId = e.newValues.confidentialityLevel;
        var confLevelName = me.confidentialityLevelsStore.findRecord('id', confLevelId).get('name');
        jsonData.confidentialityLevel = {
            id: confLevelId,
            name: confLevelName
        };
        
        var store = editor.grid.getStore();
        
        var persistMethod = record.data.createdDate != null ? 'PUT' : 'POST';
        
        Ext.each(editor.editor.items.items, function(item){
            if (item.store != undefined && item.store != null) {
                item.store.clearFilter(true);
            }
            
        });
        
        Ext.Ajax.request({
            url: this.url + "/" + id,
            method: persistMethod,
            headers: {
                'Content-Type': 'application/json'
            },
            jsonData: jsonData,
            success: function(response, view){
                if (persistMethod == "PUT") {
                    var r = Ext.decode(response.responseText);
                    record.persisted = true;
                    
                    if (record.dirty) {
                        record.commit();
                        editor.grid.getSelectionModel().select(record);
                        var h = editor.grid.getView().getSelectedNodes()[0];
                        Ext.get(h).highlight(Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_COLOR, Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_OPTIONS);
                        
                    }
                }
                else {
                    var r = Ext.decode(response.responseText);
                    record.populateFromGenericObject(r);
                    store.totalCount = store.totalCount + 1;
                    
                    if (record.dirty) {
                        record.commit();
                        editor.grid.getSelectionModel().select(0);
                        
                        var h1 = editor.grid.getView().getSelectedNodes()[0];
                        Ext.get(h1).highlight(Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_COLOR, Ssp.util.Constants.SSP_EDITED_ROW_HIGHLIGHT_OPTIONS);
                        
                    }
                }
            },
            failure: this.apiProperties.handleError
        }, this);
    },
    
    
    
    deleteConfirmation: function(){
        if (this.model.get('id') != "") {
            Ext.Msg.confirm({
                title: 'Delete Goal?',
                msg: 'You are about to delete the goal: "' + this.model.get('name') + '". Would you like to continue?',
                buttons: Ext.Msg.YESNO,
                fn: this.deleteGoal,
                scope: this
            });
        }
        else {
            Ext.Msg.alert('SSP Error', 'Unable to delete goal.');
        }
    },
    
    deleteGoal: function(btnId){
        var store = this.store;
        var id = this.model.get('id');
        if (btnId == "yes") {
            this.apiProperties.makeRequest({
                url: this.url + "/" + id,
                method: 'DELETE',
                successFunc: function(response, responseText){
                    store.remove(store.getById(id));
                }
            });
        }
    },
    
    onDeleteGoalClick: function(button){
        var grid, record;
        grid = button.up('grid');
        record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) {
            this.model.data = record.data;
            this.appEventsController.getApplication().fireEvent('deleteGoal');
        }
        else {
            Ext.Msg.alert('SSP Error', 'Please select a goal to delete.');
        }
    }
});
