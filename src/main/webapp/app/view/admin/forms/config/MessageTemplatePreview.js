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
Ext.define('Ssp.view.admin.forms.config.MessageTemplatePreview', {
    extend: 'Ext.window.Window',
    alias: 'widget.messagetemplatepreview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    title: 'Preview Message Template',
    inject: {
        authenticatedPerson: 'authenticatedPerson'
    },
    height: 615,
    width: 820,
    resizable: true,
	modal: true,
	config: {
        messageTemplatePreviewData: null
    },
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'vbox',       // Arrange child items vertically
                align: 'stretch'
            },
            items: [{
                xtype: 'displayfield',
                fieldLabel: 'Template',
                name: 'name',
                value: me.getMessageTemplatePreviewData().get('name')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Subject',
                name: 'subject',
                value: me.getMessageTemplatePreviewData().get('subject')
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Body',
                name: 'body'
            }, {
                xtype: 'panel'
                ,id: 'bodyPanel'
                ,html: me.getMessageTemplatePreviewData().get('body')
                ,autoScroll: true
                ,flex: 1
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