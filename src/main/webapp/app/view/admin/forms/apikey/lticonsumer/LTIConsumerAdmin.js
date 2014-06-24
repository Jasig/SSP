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
Ext.define('Ssp.view.admin.forms.apikey.lticonsumer.LTIConsumerAdmin', {
    extend: 'Ext.grid.Panel',
    alias : 'widget.lticonsumeradmin',
    title: 'LTI Consumer Admin',
    mixins: [ 'Deft.mixin.Injectable',
        'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.apikey.lticonsumer.LTIConsumerAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
    width: '100%',

    initComponent: function(){
        var me=this;
        Ext.apply(me,
            {
                viewConfig: {

                },
                autoScroll: true,
                selType: 'rowmodel',
                enableDragDrop: false,
                cls: 'configgrid',
                columns: [
                    { header: 'Consumer Key',
                        dataIndex: 'consumerKey',
                        flex: 1
                    },
                    { header: 'Consumer Name',
                        dataIndex: 'name',
                        flex: 1
                    },
                    
                    { header: 'Active',
                        dataIndex: 'objectStatus',
                        renderer: me.columnRendererUtils.renderObjectStatus,
                        flex: 1
                    }
                ],

                dockedItems: [
                    {
                        xtype: 'pagingtoolbar',
                        dock: 'bottom',
                        displayInfo: true,
                        pageSize: me.apiProperties.getPagingSize()
                    },
                    {
                        xtype: 'toolbar',
                        items: [{
                            text: 'Add',
                            iconCls: 'icon-add',
                            xtype: 'button',
                            hidden: !me.authenticatedPerson.hasAccess('API_KEY_ADD_BUTTON'),
                            action: 'add',
                            itemId: 'addButton'
                        }]
                    },{
                        xtype: 'toolbar',
                        dock: 'top',
                        items: [{
                            xtype: 'label',
                            text: 'Double click on an existing Client to Edit or Deactivate. Click on "Add" to create a new Client.'
                        }]
                    }]
            });

        return me.callParent(arguments);
    }
});