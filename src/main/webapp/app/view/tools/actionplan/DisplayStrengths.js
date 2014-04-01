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
Ext.define('Ssp.view.tools.actionplan.DisplayStrengths', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaystrengths',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayStrengthsViewController',
    inject: {
        appEventsController: 'appEventsController',
        authenticatedPerson: 'authenticatedPerson',
        columnRendererUtils: 'columnRendererUtils',
        model: 'currentStrength',
        store: 'strengthsStore',
		confidentialityLevelsAllUnpagedStore: 'confidentialityLevelsAllUnpagedStore'
    },
    width: '100%',
    height: '100%',
	autoScroll: true,
    layout: 'fit',
    itemId: 'strengthsPanel',
    
    initComponent: function(){
        var me = this;
		
		var cellEditor = Ext.create('Ext.grid.plugin.RowEditing', { 
    							clicksToEdit: 2,
    							listeners: {
	    							cancelEdit: function(rowEditor, item){
	    								var columns = rowEditor.grid.columns;
	    								var record = rowEditor.context.record;
	    								
										if(record.get('id') == '' || record.get('id') == null || record.get('id') == undefined){
											me.store.load();
										}
										
	    								
										
										
	    							}
    							}
    					});
        
        Ext.apply(me, {
            plugins: cellEditor,
			selType: 'rowmodel',
            title: 'Strengths',
            store: me.store,
			viewConfig: {
                markDirty: false
            },
            columns: [
			{
                header: 'Name',
                flex: .25,
                dataIndex: 'name',
				
				field: {
                    xtype: 'textfield',
					fieldStyle: "margin-bottom:12px;",
					maxLength: 80,
					allowBlank: false
                }
            }, {
                header: 'Description',
                flex: .50,
                dataIndex: 'description',
				field: {
                    xtype: 'textfield',
					fieldStyle: "margin-bottom:12px;",
					maxLength: 2000
                }
            }, {
                header: 'Confidentiality',
                dataIndex: 'confidentialityLevel',
                renderer: me.columnRendererUtils.renderConfidentialityLevel,
                required: true,
                flex: .25,
                field: {
                    xtype: 'combo',
                    store: me.confidentialityLevelsAllUnpagedStore,
                    displayField: 'name',
                    valueField: 'id',
                    forceSelection: true
                }
            
            }],
            
            dockedItems: [{
                dock: 'top',
                xtype: 'toolbar',
				width: '100%',
                items: [{
                    tooltip: 'Add a Strength',
                    text: 'Add Strength',
                    hidden: !me.authenticatedPerson.hasAccess('ADD_STRENGTH_BUTTON'),
                    xtype: 'button',
                    itemId: 'addStrengthButton'
                },
				{
					tooltip: 'Delete a Strength',
                    text: 'Delete Strength',
                    hidden: !me.authenticatedPerson.hasAccess('DELETE_STRENGTH_BUTTON'),
                    xtype: 'button',
                    itemId: 'deleteStrengthButton'
				},
				{
                    xtype: 'tbspacer',
                    width: '200'
                },
				 {
                    xtype: 'emailandprintactionplan'
                }]
            }, {
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'label',
                    text: 'Double-click to edit a strength'
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
