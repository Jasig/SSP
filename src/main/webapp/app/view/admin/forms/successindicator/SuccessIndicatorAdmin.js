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
Ext.define('Ssp.view.admin.forms.successindicator.SuccessIndicatorAdmin', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.successindicatoradmin',
    title: 'Success Indicator Admin',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.successindicator.SuccessIndicatorAdminViewController',
    inject: {
        appEventsController: 'appEventsController',
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        store: 'successIndicatorsAllStore'
    },
    height: '100%',
    width: '100%',

    initComponent: function(){
        var me = this;


        Ext.apply(me, {
            viewConfig: {},
            autoScroll: true,
            selType: 'rowmodel',
            enableDragDrop: false,
            cls: 'configgrid',
            viewConfig: {
                markDirty: false
            },
            tools: [{
                type: 'down'
            }],
            columns: [{
                header: 'Active',
                dataIndex: 'objectStatus',
                width: 44,
                renderer: me.columnRendererUtils.renderObjectStatus,
                field: {
                    xtype: 'oscheckbox'
                }
            }, {
                header: 'Indicator Group',
                dataIndex: 'indicatorGroup',
                width: 100
            }, {
                header: 'Model Code',
                dataIndex: 'modelCode',
                width: 100,
                renderer: 'htmlEncode'
            }, {
                header: 'Model Name',
                dataIndex: 'modelName',
                width: 160,
                renderer: 'htmlEncode'
            }, {
                header: 'Indicator Name',
                dataIndex: 'name',
                width: 160
            }, {
                header: 'Indicator Code',
                dataIndex: 'code',
                width: 120,
                renderer: 'htmlEncode'
            }, {
                header: 'Sort',
                dataIndex: 'sortOrder',
                width: 50,
                renderer: 'htmlEncode'
            }],

            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                store: me.store,
                pageSize: me.apiProperties.getPagingSize(),
                displayInfo: true
            }, {
                xtype: 'toolbar',
                items: [{
                    text: 'Add',
                    iconCls: 'icon-add',
                    xtype: 'button',
                    action: 'add',
                    itemId: 'addButton'
                }]
            }, {
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'label',
                    text: 'Double-click on an existing Indicator to Edit.  Click on "Add" to create a new Indicator.'
                }]
            }]
        });

        return me.callParent(arguments);
    }
});
