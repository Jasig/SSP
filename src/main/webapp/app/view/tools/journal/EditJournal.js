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
Ext.define('Ssp.view.tools.journal.EditJournal', {
    extend: 'Ext.form.Panel',
    alias: 'widget.editjournal',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.EditJournalViewController',
    inject: {
        confidentialityLevelsAllUnpagedStore: 'confidentialityLevelsAllUnpagedStore',
        journalSourcesStore: 'journalSourcesAllUnpagedStore',
        journalTracksAllUnpagedStore: 'journalTracksAllUnpagedStore',
        model: 'currentJournalEntry'
    },
    width: '100%',
    height: '100%',
    //minHeight: 1,
    autoScroll: true,
    itemId: 'editjournalGrid',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 0,
            bodyPadding: 0,
            items: [{
                xtype: 'label',
                text: 'Journal Entry',
                padding: '0 0 0 10',
                style: 'font-weight: bold'
            }, {
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'hbox',
                defaultType: 'displayfield',
                border: 0,
                fieldDefaults: {
                    msgTarget: 'side'
                },
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    margin: '0 0 0 2',
                    defaults: {
                        anchor: '100%,100%'
                    },
                    
                    flex: 0.35,
                    
                    items: [{
                        xtype: 'datefield',
                        fieldLabel: 'Entry Date',
                        itemId: 'entryDateField',
                        altFormats: 'm/d/Y|m-d-Y',
                        name: 'entryDate',
                        anchor: '98%',
                        allowBlank: false,
                        showToday: false, // because this would be 'today' browser time,
                        listeners: {
                            render: function(field){
                                Ext.create('Ext.tip.ToolTip', {
                                    target: field.getEl(),
                                    html: 'Use this to set the calendar date, in the institution\'s time zone, on which the journaled session actually occurred. The system will not attempt to convert this value to or from your current time zone.'
                                });
                            }
                        }
                    }, {
                        xtype: 'combobox',
                        itemId: 'confidentialityLevelCombo',
                        name: 'confidentialityLevelId',
                        fieldLabel: 'Confidentiality Level',
                        emptyText: 'Select One',
                        store: me.confidentialityLevelsAllUnpagedStore,
                        valueField: 'id',
                        displayField: 'name',
                        typeAhead: true,
                        queryMode: 'local',
                        allowBlank: false,
                        forceSelection: true,
                        anchor: '98%'
                    }, {
                        xtype: 'combobox',
                        itemId: 'journalSourceCombo',
                        name: 'journalSourceId',
                        fieldLabel: 'Source',
                        emptyText: 'Select One',
                        store: me.journalSourcesStore,
                        valueField: 'id',
                        displayField: 'name',
                        typeAhead: true,
                        queryMode: 'local',
                        allowBlank: false,
                        forceSelection: true,
                        anchor: '98%'
                    }, 
					{
		                xtype: 'fieldset',
		                title: 'Comment (Optional)',
		                border: 1,
						margin: '0 0 0 0',
						padding: '0 0 0 0',
						items: [{
						        xtype: 'htmleditor',
						        itemId: 'commentTxt',
		                        name: 'comment',
		                        allowBlank: false,
						        fieldLabel: '',
						        anchor: '100%',
						        labelSeparator: '',
				                width: '100%',
				                height: 350
						    }]
            		}]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: '0 0 0 5',
                    margin: '0 0 0 0',
                    flex: 0.65,
                    items: [{
                        xtype: 'label',
                        text: 'Track-Step-Detail',
                        padding: '5 0 5 0',
						style: 'color: blue'
                    }, {
                        xtype: 'fieldset',
                        border: 0,
                        title: '',
                        layout: 'hbox',
                        padding: ' 0 0 0 0',
                        margin: '0 0 0 0',
                        defaultType: 'displayfield',
                        defaults: {
                            anchor: '100%'
                        },
                        
                        items: [{
                            xtype: 'combobox',
                            itemId: 'journalTrackCombo',
                            name: 'journalTrackId',
                            fieldLabel: '',
                            emptyText: 'Select One',
                            store: me.journalTracksAllUnpagedStore,
                            valueField: 'id',
                            displayField: 'name',
                            typeAhead: true,
                            queryMode: 'local',
                            allowBlank: true,
                            forceSelection: false,
                            flex: 1
                        }, {
                            xtype: 'tbspacer',
                            width: 10
                        }, {
                            tooltip: 'Removes the assigned Journal Track and Session Details',
                            text: 'Remove',
                            xtype: 'button',
                            itemId: 'removeJournalTrackButton'
                        }]
                    }, {
                        xtype: 'journaltracktree',
                        flex: 0.90,
                        itemId: 'journalTrackTree',
                        minHeight: 1
                    
                    }]
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
