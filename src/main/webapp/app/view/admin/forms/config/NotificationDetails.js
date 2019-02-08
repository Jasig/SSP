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
Ext.define('Ssp.view.admin.forms.config.NotificationDetails', {
    extend: 'Ext.window.Window',
    alias: 'widget.notificationdetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    title: 'Display Notification Details',
    inject: {
        authenticatedPerson: 'authenticatedPerson',
        notification: 'currentNotification'
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
                labelWidth: 150,
                padding: '5 0 0 5'
            },
            items: [{
                xtype: 'displayfield',
                fieldLabel: 'Id',
                name: 'id',
                value: me.notification.get('id')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Category',
                name: 'category',
                value: me.notification.get('category')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Priority',
                name: 'priority',
                value: me.notification.get('priority')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Created On',
                name: 'createdDate',
                value: me.notification.get('createdDate'),
                renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Duplicate Count',
                name: 'duplicateCount',
                value: me.notification.get('duplicateCount')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Subject',
                name: 'subject',
                value: me.notification.get('subject')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Recipient Count',
                name: 'recipientCount',
                value: me.notification.get('notificationRecipients').length
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Read Count',
                name: 'readCount',
                value: me.notification.get('notificationReads').length
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Body',
                name: 'body'
            }, {
                xtype: 'panel',
                id: 'bodyPanel',
                html: me.notification.get('body'),
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
