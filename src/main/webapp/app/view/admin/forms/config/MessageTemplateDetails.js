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
Ext.define('Ssp.view.admin.forms.config.MessageTemplateDetails', {
    extend: 'Ext.form.Panel',
    alias: 'widget.messagetemplatedetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.config.MessageTemplateDetailsAdminViewController',
    title: 'Edit Message Template',
    inject: {
        messageTemplatesStore: 'messageTemplatesStore',
        authenticatedPerson: 'authenticatedPerson'
    },
    autoScroll: true,
    collapsible: true,
    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            items: [{
                xtype: 'displayfield',
                fieldLabel: 'Template',
                anchor: '100%',
                name: 'name'
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Description',
                anchor: '100%',
                name: 'description'
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Last Updated',
                anchor: '100%',
                name: 'modifiedDate',
                itemId: 'modifiedDate'
            }, {
                xtype: 'displayfield',
                fieldLabel: 'Last Updated By',
                anchor: '100%',
                name: 'modifiedBy',
                itemId: 'modifiedBy'
            }, {
                xtype: 'textareafield',
                fieldLabel: 'Subject',
                anchor: '100%',
                name: 'subject'
            }, {
                xtype: 'textareafield',
                fieldLabel: 'Body',
                anchor: '100%, 95%',
                name: 'body'
            }, {
                xtype: 'tbspacer',
                height: '10'
            }],
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: 'Save',
                    xtype: 'button',
                    action: 'save',
					hidden: !me.authenticatedPerson.hasAccess('ABSTRACT_REFERENCE_ADMIN_ADD_BUTTON'),
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
