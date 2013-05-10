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
Ext.define('Ssp.view.tools.journal.EditJournal',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.EditJournalViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore',
        journalSourcesStore: 'journalSourcesStore',
        journalTracksStore: 'journalTracksStore',
        model: 'currentJournalEntry'
    },	
    initComponent: function() {
    	var me=this;
    	Ext.applyIf(me, {
        	title: ((me.model.get('id') == "") ? "Add Journal" : "Edit Journal"),
        	autoScroll: true,
        	defaults: {
            	labelWidth: 150,
            	padding: 5,
            	labelAlign: 'right'
            },
        	items: [{
			    	xtype: 'datefield',
			    	fieldLabel: 'Entry Date',
			    	itemId: 'entryDateField',
			    	altFormats: 'm/d/Y|m-d-Y',
			        name: 'entryDate',
			        allowBlank:false,
			        showToday:false
			     },{
			        xtype: 'combobox',
			        itemId: 'confidentialityLevelCombo',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: me.confidentialityLevelsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
			        xtype: 'combobox',
			        itemId: 'journalSourceCombo',
			        name: 'journalSourceId',
			        fieldLabel: 'Source',
			        emptyText: 'Select One',
			        store: me.journalSourcesStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment (Optional)',
                    itemId: 'commentText',
                    anchor: '95%',
                    name: 'comment'
                },{
			        xtype: 'fieldcontainer',
			        fieldLabel: 'Journal Track (Optional)',
			        labelWidth: 155,
			        anchor: '95%',
			        layout: 'hbox',
			        items: [{
						        xtype: 'combobox',
						        itemId: 'journalTrackCombo',
						        name: 'journalTrackId',
						        fieldLabel: '',
						        emptyText: 'Select One',
						        store: me.journalTracksStore,
						        valueField: 'id',
						        displayField: 'name',
						        mode: 'local',
						        typeAhead: true,
						        queryMode: 'local',
						        allowBlank: true,
						        forceSelection: false,
						        flex: 1
							},{
								xtype: 'tbspacer',
								width: 10
							},{
					            tooltip: 'Removes the assigned Journal Track and Session Details',
					            text: 'Remove/Reset',
					            xtype: 'button',
					            itemId: 'removeJournalTrackButton',
					            hidden: ((me.model.get('id') == "")?false : true)
				    	    }]
				},{
			        xtype: 'fieldcontainer',
			        fieldLabel: 'Session Details',
			        labelWidth: 155,
			        anchor: '95%',
			        layout: 'hbox',
			        items: [{
					            tooltip: 'Add Journal Session Details',
					            text: 'Add/Edit Session Details',
					            xtype: 'button',
					            itemId: 'addSessionDetailsButton'
				    	    }]
				},
                { xtype: 'displayjournaldetails', autoScroll: true, anchor:'95% 50%' }
				],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return me.callParent(arguments);
    }	
});