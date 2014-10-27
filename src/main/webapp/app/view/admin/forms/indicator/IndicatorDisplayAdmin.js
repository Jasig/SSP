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
Ext.define('Ssp.view.admin.forms.indicator.IndicatorDisplayAdmin', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.indicatordisplayadmin',
    title: 'Status Indicator Admin',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.indicator.IndicatorDisplayAdminViewController',
    inject: {
		appEventsController: 'appEventsController',
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        store: 'statusIndicatorsStore'
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
                stopSelection: true,
                renderer: function(value, meta, record){
                    return '<center><input type="checkbox" name="checkbox"' + (value==="ACTIVE" ? 'checked' : '') + 
						' onclick="var view = Ext.ComponentQuery.query(\'indicatordisplayadmin\')[0];' +
						'var store = view.getStore();' +
						'var rec = store.getAt(store.findExact(\'id\',\'' + record.get('id') + '\'));' +
						'var args = {rec: rec, value: this.checked};' +
						'view.appEventsController.getApplication().fireEvent(\'setActiveIndicator\', args);' +
						'"></center>';
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
                header: 'Description',
                dataIndex: 'description',
                flex: 1
            }, {
                header: 'Indicator Code',
                dataIndex: 'code',
                width: 120,
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
                    // hidden: !me.authenticatedPerson.hasAccess('STATUS_INDICATOR_DELETE_BUTTON'),
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
