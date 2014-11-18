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
Ext.define('Ssp.view.admin.forms.apikey.oauth2.EditOAuth2Client',{
    extend: 'Ext.form.Panel',
    alias : 'widget.editoauth2client',
    mixins: [ 'Deft.mixin.Injectable',
        'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.apikey.oauth2.EditOAuth2ClientAdminViewController',
    title: 'Edit OAuth2 Client',
    inject: {
        authenticatedPerson: 'authenticatedPerson'
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
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Client ID',
                    anchor: '60%',
                    name: 'clientId',
                    allowBlank: false,
                    minLength: 1,
                    maxLength: 25,
// This regex only works part of the time even for the exact same string. Not sure why.
//                    regex: /\S/g,
//                    regexText: "Client ID cannot contain whitespace",
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'A Client ID acts an OAuth2 client\'s username. Be sure to select a value that is unlikley to conflict with a \'real\' user\'s username'
                            });
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'First Name',
                    anchor: '60%',
                    name: 'firstName',
                    allowBlank: false,
                    minLength: 1,
                    maxLength: 50,
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'OAuth2 Clients need first and last names because they can act just like normal users and the SSP will refer to them by first and last name.'
                            });
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Last Name',
                    anchor: '60%',
                    name: 'lastName',
                    allowBlank: false,
                    minLength: 1,
                    maxLength: 50,
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'OAuth2 Clients need first and last names because they can act just like normal users and the SSP will refer to them by first and last name.'
                            });
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Email',
                    anchor: '60%',
                    name: 'primaryEmailAddress',
                    allowBlank: false,
                    vtype: 'email',
                    minLength: 1,
                    maxLength: 100

                },
                {
                    xtype: 'textfield',
                    inputType: 'password',
                    fieldLabel: 'Secret',
                    anchor: '60%',
                    name: 'secret',
                    id: 'secret',
                    minLength: 1,
                    maxLength: 32,
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'OAuth2 Clients will authenticate using this Secret as their password. Leave it blank if you don\'t want to change it. Use the \'Delete Secret\' checkbox if you really want to prevent authentication by removing an existing password'
                            });
                        }
                    }
                },
                {
                    xtype: 'textfield',
                    inputType: 'password',
                    fieldLabel: 'Confirm Secret',
                    anchor: '60%',
                    minLength: 1,
                    maxLength: 32,
                    name: 'confirmSecret',
                    id: 'confirmSecret',
                    vtype : 'passwordConfirm',
                    initialPassField : 'secret'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Delete Secret',
                    anchor: '60%',
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
                    xtype: 'textfield',
                    fieldLabel: 'Token Expiration (seconds)',
                    anchor: '60%',
                    name: 'accessTokenValiditySeconds',
                    regex: /^[0-9]*$/,
                    regexText: "Can be blank or contain numbers",
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'Controls how long the OAuth2 Client-equivalent of a logged in session will last. If this field is left empty, the application will select a long default expiration'
                            });
                        }
                    }
                },
                {
                    xtype: 'fieldcontainer',
                    layout: 'hbox',
                    items: [
                        {
                            xtype: 'button',
                            text: 'Hide Permissions',
                            itemId: 'showPermissions',
                            nextText: 'Show Permissions',
                            toggleText: function() {
                                var me = this;
                                var nextNext = me.getText();
                                me.setText(me.nextText);
                                me.nextText = nextNext;
                            },
                            margin: '0 5 0 0'
                        },
                        {
                            xtype: 'button',
                            text: 'Select All Permissions',
                            itemId: 'selectAllPermissions',
                            hidden: true,
                            margin: '0 5 0 5'
                        },
                        {
                            xtype: 'button',
                            text: 'Deselect All Permissions',
                            itemId: 'deselectAllPermissions',
                            hidden: true,
                            margin: '0 0 0 5'
                        }
                    ]
                },
                {
                    xtype: 'checkboxgroup',
                    fieldLabel: 'Permissions',
                    labelWidth: 100,
                    anchor: '70%',
                    itemId: 'authorities',
                    columns: 3, // 'auto' blows up
                    vertical: true
                },
                {
                    xtype: 'oscheckbox',
                    fieldLabel: 'Active',
                    checked: true,
                    anchor: '60%',
                    name: 'objectStatus',
                    listeners: {
                        render: function(field){
                            Ext.create('Ext.tip.ToolTip', {
                                target: field.getEl().down('label'),
                                html: 'De-select the \'Active\' checkbox to disable an OAuth2 Client without deleting any of its data.'
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
