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
Ext.define('Ssp.view.tools.journal.JournalList', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.journallist',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.JournalToolViewController',
    inject: {
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        model: 'currentJournalEntry',
        store: 'journalEntriesStore'
    },
    
    width: '100%',
    height: '100%',
    
    // Probably want to add more clarification when and if we display other
    // timestamp-based audit fields. See example of problematic scenarios in
    // https://issues.jasig.org/browse/SSP-1093 comments.
    entryDateMsg: 'This represents the user-specified creation date for a journal entry date. It can be used, for example, to back-date entries. These dates are assumed to refer calendar dates in the institution\'s time zone.',
    entryDateFormatter: function(){
        return Ext.util.Format.dateRenderer('m/d/Y');
    },
    entryDateRenderer: function(){
        var me = this;
        return function(value, metaData){
            // http://www.sencha.com/forum/showthread.php?179016
            metaData.tdAttr = 'data-qtip="' + me.entryDateMsg + '"';
            return me.entryDateFormatter()(value);
        }
    },
    initComponent: function(){
        var me = this;
        var sm = Ext.create('Ext.selection.CheckboxModel');
        Ext.apply(me, {
            autoScroll: true,
            title: 'Journal List',
            store: me.store,
            columns: [{
                header: 'Modified Date',
                dataIndex: 'modifiedDate',
                flex: 1,
                renderer: me.columnRendererUtils.renderModifiedByDate
            }, {
                header: 'Modified By',
                dataIndex: 'modifiedBy',
                flex: 1,
                renderer: me.columnRendererUtils.renderModifiedBy
            }, {
                header: 'Source',
                dataIndex: 'journalSource',
                flex: 1,
                renderer: me.columnRendererUtils.renderJournalSourceName
            }, {
                header: 'Confidentiality',
                dataIndex: 'confidentialityLevel',
                flex: 1,
                renderer: me.columnRendererUtils.renderConfidentialityLevelName
            }, {
                header: 'Entry Date',
                dataIndex: 'entryDate',
                flex: 1,
                renderer: me.entryDateRenderer(),
                listeners: {
                    render: function(field){
                        Ext.create('Ext.tip.ToolTip', {
                            target: field.getEl(),
                            html: me.entryDateMsg
                        });
                    }
                }
               
            
            }, {
                header: 'Entered By',
                dataIndex: 'createdBy',
                flex: 1,
                renderer: me.columnRendererUtils.renderCreatedBy
            }],
            
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    tooltip: 'Add Journal Note',
                    text: 'Add',
                    xtype: 'button',
                    hidden: !me.authenticatedPerson.hasAccess('ADD_JOURNAL_ENTRY_BUTTON'),
                    itemId: 'addButton'
                }, {
                    tooltip: 'Delete Journal Note',
                    text: 'Delete',
                    xtype: 'button',
                    hidden: !me.authenticatedPerson.hasAccess('DELETE_JOURNAL_ENTRY_BUTTON'),
                    itemId: 'deleteButton'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
