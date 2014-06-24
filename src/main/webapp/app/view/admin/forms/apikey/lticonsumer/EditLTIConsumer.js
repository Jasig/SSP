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
Ext.define('Ssp.view.admin.forms.apikey.lticonsumer.EditLTIConsumer',{
    extend: 'Ext.form.Panel',
    alias : 'widget.editlticonsumer',
    mixins: [ 'Deft.mixin.Injectable',
        'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.apikey.lticonsumer.EditLTIConsumerAdminViewController',
    title: 'Edit LTI Consumer',
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        ltiSspUserFieldNamesStore: 'ltiSspUserFieldNamesStore'
    },
    autoScroll: true,
    collapsible: true,
    scroll: 'vertical',

    // Little bit different approach here... no singleton model that the whole
    // app smashes on... caller needs to pass in the OAuth2Client to be worked
    // on for edit mode, else leave null for the form to run in create mode.
    // Some future change possible to support just passing in an ID instead
    // of a fully materialized model.
    client: null,

    initComponent: function() {
        var me=this;
        Ext.applyIf(me, {
        	fieldDefaults: {
		        labelAlign: 'right',
		        labelWidth: 160
		    },
		    defaultType: 'textfield',
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Consumer Name',
                    anchor: '40%',
                    name: 'name',
                    allowBlank: false,
                    minLength: 1,
                    maxLength: 50,
                    enforceMaxLength: true,
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'User friendly name for the Consumer Tool service. Uniqueness not required but suggested.'
                            });
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Consumer Key',
                    anchor: '40%',
                    name: 'consumerKey',
                    allowBlank: false,
                    minLength: 1,
                    maxLength: 50,
                    enforceMaxLength: true,
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'A Consumer Key acts as LTI client\'s username. Be sure to select a value that is unlikley to conflict with a \'real\' user\'s username'
                            });
                        }
                    }
                },
                
                {
                    xtype: 'textfield',
                    inputType: 'password',
                    fieldLabel: 'Secret',
                    anchor: '40%',
                    name: 'secret',
                    id: 'secret',
                    minLength: 1,
                    maxLength: 32,
                    enforceMaxLength: true,
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'LTI Clients will authenticate using this Secret as their password. Leave it blank if you don\'t want to change it. Use the \'Delete Secret\' checkbox if you really want to prevent authentication by removing an existing password'
                            });
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    inputType: 'password',
                    fieldLabel: 'Confirm Secret',
                    anchor: '40%',
                    name: 'confirmSecret',
                    id: 'confirmSecret',
                    vtype : 'passwordConfirm',
                    initialPassField : 'secret'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Lti User Id Field',
                    allowBlank: false,
                    anchor: '40%',
                    name: 'ltiUserIdField',
                },
                {
                	 xtype: 'combobox',
                	 store: me.ltiSspUserFieldNamesStore,
                    fieldLabel: 'SSP User Attribute',
					valueField: 'code',
					displayField: 'displayValue',
                    allowBlank: false,
                    anchor: '40%',
                    name: 'sspUserIdField',
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Lti Section Code Field',
                    allowBlank: false,
                    anchor: '40%',
                    name: 'ltiSectionCodeField',
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Delete Secret',
                    anchor: '40%',
                    name: 'deleteSecret',
                    id: 'deleteSecret',
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'Use the \'Delete Secret\' checkbox if you want to prevent authentication by removing an existing password'
                            });
                        }
                    }
                },
                {
                    xtype: 'oscheckbox',
                    fieldLabel: 'Active',
                    checked: true,
                    anchor: '40%',
                    name: 'objectStatus',
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'De-select the \'Active\' checkbox to disable an LTI Client without deleting any of its data.'
                            });
                        }
                    }
                }
            ],
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: 'Save',
                    xtype: 'button',
                    action: 'save',
                    formBind: true,
                    hidden: !me.authenticatedPerson.hasAccess('API_KEY_SAVE_BUTTON'),
                    itemId: 'saveButton'
                }, '-', {
                    text: 'Cancel',
                    xtype: 'button',
                    action: 'cancel',
                    itemId: 'cancelButton'
                }]
            }]
        });

        return this.callParent(arguments);
    }
});