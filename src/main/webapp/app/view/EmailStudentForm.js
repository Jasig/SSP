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
Ext.define('Ssp.view.EmailStudentForm', {
	extend: 'Ext.form.Panel',
    alias: 'widget.emailstudentform',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		columnRendererUtils : 'columnRendererUtils',
    	confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore'
    },
    controller: 'Ssp.controller.EmailStudentViewController',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            bodyStyle : 'background:none',            
            bodyPadding: 10,
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                height:30,
                layout: {
                    type: 'hbox'
                },                
				items: [{
				        xtype: 'checkbox',
				        name: 'createJournalEntry',
				        fieldLabel: '',
				        itemId: 'createJournalEntry',
				        labelSeparator: '',
				        hideLabel: true,
				        boxLabel: 'Include this Email in the Students Journal Notes',
				        fieldLabel: 'text'				        
				    },
					{
		                xtype: 'tbspacer',
		                width: 85
		            },
		            {
		                xtype: 'combobox',
		                itemId: 'confidentialityLevel',
		                name: 'confidentialityLevelId',
		                fieldLabel: '',
		                emptyText: 'Confidentiality Level',
		                store: me.confidentialityLevelsStore,
		                valueField: 'id',
		                displayField: 'name',
		                mode: 'local',
		                typeAhead: true,
		                queryMode: 'local',
		                allowBlank: true,
                        width:180,
		                forceSelection: true
		            }]
            },
            {
                xtype: 'fieldcontainer',
                fieldLabel: '',
                height:30,
                layout: {
                    type: 'hbox'
                },                
				items: [{
				        xtype: 'checkbox',
				        name: 'sendToPrimaryEmail',
				        fieldLabel: '',
				        itemId: 'sendToPrimaryEmail',
				        labelSeparator: '',
				        hideLabel: true,
				        checked:true,
				        boxLabel: 'Send To Primary Email Address',
				        fieldLabel: 'text'				        
				    },
					{
		                xtype: 'tbspacer',
		                width: 178
		            },				    
				    {
		            	xtype: 'displayfield',
				    	fieldLabel: '',
                        name: 'primaryEmail',
                        itemId: 'primaryEmail',
                        labelSeparator : '',
                        value: 'aarland@unicon.net',
                        fieldStyle: 'color:blue'
				    }]
            },
            {
                xtype: 'fieldcontainer',
                fieldLabel: '',
                height:30,
                layout: {
                    type: 'hbox'
                },                
				items: [{
				        xtype: 'checkbox',
				        name: 'sendToSecondaryEmail',
				        fieldLabel: '',
				        itemId: 'sendToSecondaryEmail',
				        labelSeparator: '',
				        hideLabel: true,
				        boxLabel: 'Send To Secondary Email Address',
				        fieldLabel: 'text'				        
				    },
					{
		                xtype: 'tbspacer',
		                width: 162
		            },				    
				    {
		            	xtype: 'displayfield',
				    	fieldLabel: '',
                        name: 'secondaryEmail',
                        itemId: 'secondaryEmail',
                        labelSeparator : '',
                        value: 'aarland@unicon.net',
                        fieldStyle: 'color:blue',
                        
				    }]
            },
            {
                xtype: 'fieldcontainer',
                fieldLabel: '',
                height:30,
                layout: {
                    type: 'hbox'
                },                
				items: [
					    {
				            xtype: 'displayfield',
		                    value: 'CC This email to additional recipients (comma seperated)',
		                    fieldStyle: 'color:black'
					    },
						{
			                xtype: 'tbspacer',
			                width: 50
			            },						    
					    {
			            	xtype: 'textfield',
					    	fieldLabel: '',
	                        name: 'additionalEmail',
	                        itemId: 'additionalEmail',
	                        width:180,
	                        labelSeparator : ''
				    }]
            },
            {
            	xtype: 'textfield',
		    	fieldLabel: 'Subject',
                name: 'emailSubject',
                itemId: 'emailSubject',
                allowBlank: false,
                width: '100%'
		    },
            {
                xtype: 'fieldset',
                title: 'Email Message',
                border: 1,
				items: [{
				        xtype: 'textareafield',
				        name: 'emailBody',
                        allowBlank: false,
				        fieldLabel: '',
				        itemId: 'emailBody',
				        labelSeparator: '',
		                width: '100%',
		                height: 200
				    }]
            }
            ]
		    ,
		    dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'button',
                    itemId: 'saveButton',
                    text: 'Save'
                    
                }, '-', {
                    xtype: 'button',
                    itemId: 'cancelButton',
                    text: 'Cancel'
                }]
            
            }]
            
        
        });
        
        return me.callParent(arguments);
    }
});
