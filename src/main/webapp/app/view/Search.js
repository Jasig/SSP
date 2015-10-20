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
Ext.define('Ssp.view.Search', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.search',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchViewController',
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        caseloadActionsStore: 'caseloadActionsStore',
        columnRendererUtils: 'columnRendererUtils',
        programStatusesStore: 'caseloadFilterProgramStatusesStore',
        textStore:'sspTextStore'
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
                displayMsg: me.textStore.getValueByCode('ssp.label.paging.displayMsg','Displaying {0} - {1} of {2}'),
                emptyMsg: me.textStore.getValueByCode('ssp.label.paging.emptyMsg','No data to display'),
                beforePageText: me.textStore.getValueByCode('ssp.label.paging.beforePageText','Page'),
                afterPageText: me.textStore.getValueByCode('ssp.label.paging.afterPageText','of {0}'),
                firstText: me.textStore.getValueByCode('ssp.label.paging.firstText','First Page'),
                prevText: me.textStore.getValueByCode('ssp.label.paging.prevText','Previous Page'),
                nextText: me.textStore.getValueByCode('ssp.label.paging.nextText','Next Page'),
                lastText: me.textStore.getValueByCode('ssp.label.paging.lastText','Last Page'),
                listeners: {
                    afterrender: function(){
                        var a = Ext.query("button[data-qtip=Refresh]");
                        for (var x = 0; x < a.length; x++) {
                            a[x].style.display = "none";
                        }
                    }
                }
            },  
            {
                xtype: 'toolbar',
                padding: '0 14px 0 0',
                dock: 'top',
                // enableOverlow is really what we want, but just doesn't work in our current Ext.js version:
                // http://www.sencha.com/forum/showthread.php?269044-Combo-Selection-Event-and-Button-Click-Event-not-firing-inside-overflow-toolbar
                // enableOverflow: true,
                overflowX: 'hidden',
                items: [{
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.add-student', 'Add Student'),
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('ADD_STUDENT_BUTTON'),
                    cls: 'addPersonIcon',
                    xtype: 'button',
                    itemId: 'addPersonButton'
                }, {
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.edit-student', 'Edit Student'),
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('EDIT_STUDENT_BUTTON'),
                    cls: 'editPersonIcon',
                    xtype: 'button',
                    itemId: 'editPersonButton'
                }, {
                    xtype: 'tbspacer',
                    flex: 0.5
                }, {
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.set-student-active', 'Set Student to Active status'),
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_ACTIVE_STATUS_BUTTON'),
                    cls: 'setActiveStatusIcon',
                    xtype: 'button',
                    action: 'active',
                    itemId: 'setActiveStatusButton'
                }, {
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.set-student-transitioned', 'Set Student to Transitioned status'),
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: true, // Temp fix for SSP-434: !me.authenticatedPerson.hasAccess('SET_TRANSITION_STATUS_BUTTON')
                    cls: 'setTransitionStatusIcon',
                    xtype: 'button',
                    action: 'transition',
                    itemId: 'setTransitionStatusButton'
                }, {
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.set-student-non-participating', 'Set Student to Non-Participating status'),
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_NON_PARTICIPATING_STATUS_BUTTON'),
                    cls: 'setNonParticipatingStatusIcon',
                    xtype: 'button',
                    action: 'non-participating',
                    itemId: 'setNonParticipatingStatusButton'
                }, {
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.set-student-no-show', 'Set Student to No-Show status'),
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_NO_SHOW_STATUS_BUTTON'),
                    cls: 'setNoShowStatusIcon',
                    xtype: 'button',
                    action: 'no-show',
                    itemId: 'setNoShowStatusButton'
                }, {
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.set-student-inactive', 'Set Student to Inactive'),
                    text: '',
                    width: 25,
                    height: 25,
                    hidden: !me.authenticatedPerson.hasAccess('SET_INACTIVE_BUTTON'),
                    cls: 'setInactiveStatusIcon',
                    xtype: 'button',
                    action: 'inactive',
                    itemId: 'setInactiveButton'
                }, {
                    xtype: 'tbspacer',
                    flex: 0.5
                }, {
                    xtype: 'combobox',
                    itemId: 'caseloadActionCombo',
                    fieldLabel: '',
                    emptyText: me.textStore.getValueByCode('ssp.bulk-action.empty-text', 'Bulk Action'),
                    store: me.caseloadActionsStore,
                    valueField: 'id',
                    displayField: 'name',
                    align: 'center',
                    typeAhead: false,
                    editable: false,
                    hidden: !me.authenticatedPerson.hasAnyBulkPermissions(),
                    queryMode: 'local',
                    allowBlank: true,
                    forceSelection: true,
                    width: 90,
                    matchFieldWidth: false,
                    height: 25,
                    labelWidth: 75
                }, {
                    xtype: 'tbspacer',
                    flex: 0.5
                }, {
                    xtype: 'combobox',
                    itemId: 'caseloadStatusCombo',
                    name: 'programStatusId',
                    fieldLabel: '',
                    emptyText: 'Filter',
                    store: me.programStatusesStore,
                    valueField: 'id',
                    displayField: 'name',
                    mode: 'local',
                    align: 'center',
                    typeAhead: false,
                    editable: false,
                    queryMode: 'local',
                    allowBlank: false,
                    hidden: !me.authenticatedPerson.hasAnyProgramStatusFilterPermissions() || me.tabContext === 'search',
                    forceSelection: true,
                    width: 90,
                    matchFieldWidth: false,
                    height: 25,
                    labelWidth: 75
                }, {
                    type: 'up',
                    xtype: 'tool',
                    itemId: 'upTool',
                    handler: function(t){
                        var idx = 0;
                        if (me.authenticatedPerson.hasAccess('STUDENT_SEARCH')) {
                            idx = 2; //user has two, second is search
                        }
                        var panel = Ext.ComponentQuery.query('#searchBar')[idx];
                        if (panel.isHidden()) {
                            this.setType('up');
                            panel.show();
                        }
                        else {
                            this.setType('down');
                            panel.hide();
                        }
                    }
                }, {

                    text: me.textStore.getValueByCode('ssp.label.search-button', 'Search'),
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.search-button','Search for Student'),
                    xtype: 'button',
                    type: 'search',
                    itemId: 'searchStudentButton',
                    height: 25,
                    align: 'left',
                    hidden: !me.authenticatedPerson.hasAccess('STUDENT_SEARCH')
                }, {
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.reset-button','Reset'),
                    text: me.textStore.getValueByCode('ssp.label.reset-button', 'Reset'),
                    type: 'refresh',
                    xtype: 'button',
                    itemId: 'resetStudentSearchButton',
                    height: 25,
                    align: 'left',
                    hidden: !me.authenticatedPerson.hasAccess('STUDENT_SEARCH')
                }]
            }, {
                xtype: 'searchForm',
                itemId: 'searchBar',
                hidden: true,
                flex: 1,
                // searchForm needs to register to hear about inter-tab nav events so we pass along the configure
                // tabPanel as an attempt to avoid arbitrary componentmanager queries or fragile up().up().up()
                // navigation. Specifically, searchForm needs to manage the state of this component, so we also pass
                // ourselves in as 'tab'
                tab: me,
                tabPanelAccessor: me.tabPanelAccessor
            }]
        });
        
        return me.callParent(arguments);
    }
});
