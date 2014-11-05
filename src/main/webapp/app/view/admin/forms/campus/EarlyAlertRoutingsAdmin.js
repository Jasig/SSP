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
Ext.define('Ssp.view.admin.forms.campus.EarlyAlertRoutingsAdmin', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.earlyalertroutingsadmin',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EarlyAlertRoutingsAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        store: 'campusEarlyAlertRoutingsStore',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
    width: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            autoScroll: true,
            store: me.store,
            title: 'Early Alert Routing Groups',
            cls: 'configgrid',
            columns: [{
                header: 'Reason',
                dataIndex: 'earlyAlertReason',
                flex: 1,
                renderer: me.columnRendererUtils.renderEarlyAlertReason
            }, {
                header: 'Group Name',
                dataIndex: 'groupName',
                flex: 0.2
            }, {
                header: 'Group Email',
                dataIndex: 'groupEmail',
                flex: 0.3
            }, {
                header: 'Person',
                dataIndex: 'person',
                renderer: me.columnRendererUtils.renderPersonFullName,
                flex: 0.2
            }],
            
            dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: "label",
                    text: "Early Alert Routing Groups define optional endpoints where an Early Alert will be delivered when it's entered in the system."
                }]
            }, {
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                displayInfo: true,
                store: me.store,
                pageSize: me.apiProperties.getPagingSize()
            }, {
                xtype: 'toolbar',
                items: [{
                    text: 'Add',
                    tooltip: 'Add Early Alert Routing Group',
                    iconCls: 'icon-add',
                    xtype: 'button',
                    itemId: 'addButton'
                }, '-', {
                    text: 'Edit',
                    tooltip: 'Edit Early Alert Routing Group',
                    iconCls: 'icon-edit',
                    xtype: 'button',
                    itemId: 'editButton'
                }, '-', {
                    text: 'Delete',
                    tooltip: 'Delete Early Alert Routing Group',
                    iconCls: 'icon-delete',
                    xtype: 'button',
                    itemId: 'deleteButton'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
