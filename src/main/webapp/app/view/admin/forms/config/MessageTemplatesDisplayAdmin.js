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
Ext.define('Ssp.view.admin.forms.config.MessageTemplatesDisplayAdmin', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.messagetemplatesdisplayadmin',
    title: '',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.config.MessageTemplatesDisplayAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils'
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
            columns: [{
                header: 'Name',
                dataIndex: 'name',
                flex: 1
            }, {
                header: 'Description',
                dataIndex: 'description',
                flex: 1
            }],
            
            dockedItems: [{
                xtype: 'pagingtoolbar',
                dock: 'bottom',
                displayInfo: true,
                pageSize: me.apiProperties.getPagingSize()
            }, {
                xtype: 'toolbar',
                items: [{
                    text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   action: 'edit',
     		                   itemId: 'editButton'
                }]
            }, {
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'label',
                    text: 'Click on an existing Message to Edit.'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
