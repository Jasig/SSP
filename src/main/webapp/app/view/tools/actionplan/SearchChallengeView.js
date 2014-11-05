/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.view.tools.actionplan.SearchChallengeView', {
    extend: 'Ext.form.Panel',
    alias: 'widget.searchchallengeview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
        apiProperties: 'apiProperties',
        appEventsController: 'appEventsController',
        columnRendererUtils: 'columnRendererUtils',
        challengesStore: 'challengesAllUnpagedStore',
        challengeCategoriesStore: 'challengeCategoriesStore',
        challengeReferralsStore: 'challengeReferralsStore'
    },
    controller: 'Ssp.controller.tool.actionplan.SearchChallengeViewController',
    title: 'Add Task',
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'fit'
            },
            padding: 0,
            preventHeader: true,
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'vbox',
                margin: '0 0 0 0',
                padding: '0 0 0 15',
                width: '100%',
                height: '100%',
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    margin: '0 0 5 0',
                    padding: '5 0 0 5',
                    layout: 'hbox',
                    defaults: {
                        anchor: '100%'
                    },
                    items: [{
                        xtype: 'label',
                        padding: '0 0 2 3',
                        text: 'Filter Category, Challenge or Keyword'
                    }, {
                        xtype: 'tbspacer',
                        width: 195
                    }, {
                        tooltip: 'Reset',
                        text: 'Reset',
                        type: 'refresh',
                        xtype: 'button',
                        padding: '0 0 2 3',
                        itemId: 'resetChallengesButton'
                    }]
                }, {
                    xtype: 'combobox',
                    fieldLabel: '',
                    itemId: 'categoryNameCombo',
                    name: 'categoryNameCombo',
                    fieldLabel: '',
                    emptyText: 'Filter by Category',
                    store: me.challengeCategoriesStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: true,
                    width: 430,
                    padding: '0 0 0 10'
                }, {
                    xtype: 'combobox',
                    fieldLabel: '',
                    itemId: 'categoryChallengeNameCombo',
                    name: 'categoryChallengeNameCombo',
                    fieldLabel: '',
                    emptyText: 'Filter by Challenge',
                    store: me.challengesStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: true,
                    width: 430,
                    padding: '0 0 0 10'
                }, {
                    xtype: 'fieldcontainer',
                    margin: '0 0 0 10',
                    layout: {
                        align: 'stretch',
                        type: 'hbox'
                    },
                    fieldLabel: '',
                    items: [{
                        xtype: 'button',
                        text: 'Search',
                        itemId: 'searchKeywordButton'
                    }, {
                        xtype: 'tbspacer',
                        width: 10
                    }, {
                        xtype: 'textfield',
                        fieldLabel: '',
                        itemId: 'searchKeyword',
                        name: 'searchKeyword',
                        margin: ' 0 0 0 20',
                        width: 380,
                        enableKeyEvents: true,
                        listeners: {
                            afterrender: function(field){
                                field.focus(false, 0);
                            },
                            specialkey: {
                                scope: me,
                                fn: function(field, el){
                                    if (el.getKey() == Ext.EventObject.ENTER) {
                                        this.appEventsController.getApplication().fireEvent("onSearchKeyword");
                                    }
                                }
                            }
                        }
                    }]
                }, {
                    xtype: 'fieldset',
                    width: '100%',
                    padding: '0 305 0 10',
                    margin: '2',
                    layout: {
                        align: 'stretch',
                        type: 'hbox'
                    },
                    title: 'Add ChallengeReferral',
                    items: [{
                        xtype: 'button',
                        text: 'Add',
                        itemId: 'addChallengeReferralButton'
                    }, {
                        xtype: 'tbspacer',
                        width: 10
                    }, {
                        xtype: 'button',
                        text: 'Add All',
                        itemId: 'addAllChallengeReferralButton'
                    }]
                }, {
                    xtype: 'challengesgrid',
                    flex: 1,
                    itemId: 'challengesgrid'
                }]
            }],
            dockedItems: [{
                xtype: 'fieldcontainer',
                layout: {
                    align: 'stretch',
                    type: 'hbox'
                },
                fieldLabel: '',
                items: [{
                    xtype: 'button',
                    text: 'Save',
                    itemId: 'saveBulkActionPlanButton'
                }, {
                    xtype: 'button',
                    text: 'Cancel',
                    itemId: 'cancelButton'
                
                }]
            }]
        
        
        });
        
        return me.callParent(arguments);
    }
});

