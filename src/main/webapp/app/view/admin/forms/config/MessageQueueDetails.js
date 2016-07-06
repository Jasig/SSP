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
Ext.define('Ssp.view.admin.forms.config.MessageQueueDetails', {
    extend: 'Ext.window.Window',
    alias: 'widget.messagequeuedetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    title: 'Display Message Details',
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        message: 'currentMessage'
    },
    height: 615,
    width: 820,
    resizable: true,
    modal: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'vbox',
                align: 'stretch'
            },
            defaults: {
                minWidth: 80,
                labelWidth: 150
            },
            items: [{
                xtype: 'displayfield',
                fieldLabel: 'Sent',
                name: 'sent',
                value: me.message.get('sent')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Sent Date',
                name: 'sentDate',
                value: me.message.get('sentDate'),
                renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Created On',
                name: 'createdDate',
                value: me.message.get('createdDate'),
                renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Retry Count',
                name: 'retryCount',
                value: me.message.get('retryCount')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Sender',
                name: 'sender',
                renderer: function() {
                    if (me.message.get('sender') && me.message.get('sender').fullName) {
                        return me.message.get('sender').fullName;
                    } else {
                        return 'Not Available';
                    }
                }
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Recipient Address',
                name: 'recipientEmailAddress',
                value: me.message.get('recipientEmailAddress')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Sent To Address',
                name: 'sentToAddresses',
                value: me.message.get('sentToAddresses')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Carbon Copy Address',
                name: 'carbonCopy',
                value: me.message.get('carbonCopy')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Sent CC Address',
                name: 'sentCcAddresses',
                value: me.message.get('sentCcAddresses')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Sent BCC Address',
                name: 'sentBccAddresses',
                value: me.message.get('sentBccAddresses')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Sent From Address',
                name: 'sentFromAddress',
                value: me.message.get('sentFromAddress')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Sent Reply Address',
                name: 'sentReplyToAddress',
                value: me.message.get('sentReplyToAddress')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Subject',
                name: 'subject',
                value: me.message.get('subject')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Body',
                name: 'body'
            }, {
                xtype: 'panel',
                id: 'bodyPanel',
                html: me.message.get('body'),
                autoScroll: true,
                flex: 1
            }],
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'bottom',
                layout: {
                  pack: 'center',
                  type: 'hbox'
                },
                items: [{
                    xtype: 'button',
                    text: 'Ok',
                    listeners: {
                        click: function(){
                            me = this;
                            me.close();
                        },
                        scope: me
                    }
                }]
            }]
        });
        return this.callParent(arguments);
    }
});
