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
Ext.define('Ssp.view.Search', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.search',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchViewController',
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        apiProperties: 'apiProperties',
        caseloadActionsStore: 'caseloadActionsStore',
        columnRendererUtils: 'columnRendererUtils',
        programStatusesStore: 'caseloadProgramStatusesStore'
    },
    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            border: 0,
            columns: [{
                text: 'Name',
                dataIndex: 'lastName',
                renderer: me.columnRendererUtils.renderSearchStudentName,
                flex: 50
            }],
            dockedItems: [{
                xtype: 'pagingtoolbar',
                itemId: 'searchGridPager',
                dock: 'bottom',
                displayInfo: true,
                listeners: {
                    afterrender: function(){
                        var a = Ext.query("button[data-qtip=Refresh]");
                        for (var x = 0; x < a.length; x++) {
                            a[x].style.display = "none";
                        }
                    }
                }
            }, {
                xtype: 'toolbar',
                dock: 'top',
                itemId: 'searchBar',
                hidden: !me.authenticatedPerson.hasAccess('STUDENT_SEARCH'),
                items: [{
  	              	xtype: 'searchForm', 
  	              	hidden: true,
                    flex: 1
                }]
            }, {
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    tooltip: 'Add Student',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('ADD_STUDENT_BUTTON'),
                    cls: 'addPersonIcon',
                    xtype: 'button',
                    itemId: 'addPersonButton'
                }, {
                    tooltip: 'Edit Student',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('EDIT_STUDENT_BUTTON'),
                    cls: 'editPersonIcon',
                    xtype: 'button',
                    itemId: 'editPersonButton'
                },{
                    xtype: 'tbspacer',
                    flex: 1
                },{
                    xtype: 'label',
                    text: 'Action:',
                    style: 'font-weight:bold;'
                },{
                    xtype: 'combobox',
                    itemId: 'caseloadActionCombo',
                    fieldLabel: '',
                    emptyText: 'Select Action',
                    store: me.caseloadActionsStore,
                    valueField: 'id',
                    displayField: 'name',
                    //mode: 'local',
                    align: 'center',
                    typeAhead: false,
                    editable: false,
                    queryMode: 'local',
                    allowBlank: true,
                    forceSelection: false,
                    width: 100,
                    labelWidth: 75
                },{
                    xtype: 'label',
                    text: 'Change Status:',
                    style: 'font-weight:bold;'
                }, {
                    tooltip: 'Set Student to Active status',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_ACTIVE_STATUS_BUTTON'),
                    cls: 'setActiveStatusIcon',
                    xtype: 'button',
                    action: 'active',
                    itemId: 'setActiveStatusButton'
                }, {
                    tooltip: 'Set Student to Transitioned status',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: true, // Temp fix for SSP-434: !me.authenticatedPerson.hasAccess('SET_TRANSITION_STATUS_BUTTON')
                    cls: 'setTransitionStatusIcon',
                    xtype: 'button',
                    action: 'transition',
                    itemId: 'setTransitionStatusButton'
                }, {
                    tooltip: 'Set Student to Non-Participating status',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_NON_PARTICIPATING_STATUS_BUTTON'),
                    cls: 'setNonParticipatingStatusIcon',
                    xtype: 'button',
                    action: 'non-participating',
                    itemId: 'setNonParticipatingStatusButton'
                }, {
                    tooltip: 'Set Student to No-Show status',
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_NO_SHOW_STATUS_BUTTON'),
                    cls: 'setNoShowStatusIcon',
                    xtype: 'button',
                    action: 'no-show',
                    itemId: 'setNoShowStatusButton'
                }, {
                    xtype: 'tbspacer',
                    flex: 1
                } ,{
                    xtype: 'combobox',
                    itemId: 'caseloadStatusCombo',
                    name: 'programStatusId',
                    fieldLabel: '',
                    emptyText: 'Select One',
                    store: me.programStatusesStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    align: 'center',
                    typeAhead: false,
                    editable: true,
                    queryMode: 'local',
                    allowBlank: false,
                    hidden: !me.authenticatedPerson.hasAccess('CASELOAD_FILTERS') || me.tabContext === 'search',
                    forceSelection: false,
                    width: 100,
                    labelWidth: 75
                }    ]
            }]
        });
        
        return me.callParent(arguments);
    }
});
