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
        store: 'journalEntriesUnpagedStore',
        textStore: 'sspTextStore'
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
            title:  me.textStore.getValueByCode('ssp.label.journal.list-title', 'Journal List'),
            store: me.store,
            columns: [{
                header: me.textStore.getValueByCode('ssp.label.journal.modified-date','Modified Date'),
                dataIndex: 'modifiedDate',
                flex: 1,
                renderer: me.columnRendererUtils.renderModifiedByDate
            }, {
                header: me.textStore.getValueByCode('ssp.label.journal.modified-by','Modified By'),
                dataIndex: 'journalModifiedBy',
                flex: 1,
                renderer: me.columnRendererUtils.renderJournalModifiedBy,
				sortable: true
            }, {
                header: me.textStore.getValueByCode('ssp.label.journal.source','Source'),
                dataIndex: 'journalS',
                flex: 1,
                renderer: me.columnRendererUtils.renderJournalSourceName,
				sortable: true
			
            }, {
                header: me.textStore.getValueByCode('ssp.label.journal.confidentiality-level','Confidentiality'),
                dataIndex: 'journalCFlevel',
                flex: 1,
                renderer: me.columnRendererUtils.renderConfidentialityLevelName,
				sortable: true
            }, {
                header: me.textStore.getValueByCode('ssp.label.journal.entry-date','Entry Date'),
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
                header: me.textStore.getValueByCode('ssp.label.journal.entered-by','Entered By'),
                dataIndex: 'journalCreatedBy',
                flex: 1,
                renderer: me.columnRendererUtils.renderJournalCreatedBy,
				sortable: true
            }],
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
                items: [{
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.journal.add-button','Add Journal Note'),
                    text: me.textStore.getValueByCode('ssp.label.add-button','Add'),
                    xtype: 'button',
                    hidden: !me.authenticatedPerson.hasAccess('ADD_JOURNAL_ENTRY_BUTTON'),
                    itemId: 'addButton'
                }, 
				{
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.journal.save-button','Save Journal Note'),
                    text: me.textStore.getValueByCode('ssp.label.save-button','Save'),
                    xtype: 'button',
                    itemId: 'saveButton'
                }, 
				{
					tooltip: me.textStore.getValueByCode('ssp.tooltip.journal.cancel-button','Cancel Journal Changes Since Last Save'),
					text: me.textStore.getValueByCode('ssp.label.cancel-button','Cancel'),
					xtype: 'button',
					itemId: 'cancelButton'
				},
				{
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.journal.delete-button','Delete Journal Note'),
                    text: me.textStore.getValueByCode('ssp.label.delete-button','Delete'),
                    xtype: 'button',
                    hidden: !me.authenticatedPerson.hasAccess('DELETE_JOURNAL_ENTRY_BUTTON'),
                    itemId: 'deleteButton'
                },
                {
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.journal.history-link','Click to generate report of student\'s Journal History'),
                    text: me.textStore.getValueByCode('ssp.label.journal.history-link','Journal History'),
                    width: 105,
                    height: 20,
                    xtype: 'button',
    				cls: "makeTransparent x-btn-link",
    				hidden: !me.authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON'),
                    itemId: 'journalHistoryButton'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
