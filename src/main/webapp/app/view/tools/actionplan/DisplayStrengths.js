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
		confidentialityLevelsAllUnpagedStore: 'confidentialityLevelsAllUnpagedStore',
		textStore: 'sspTextStore'
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
    						    saveBtnText  : me.textStore.getValueByCode('ssp.label.update-button', 'Update'),
                                cancelBtnText: me.textStore.getValueByCode('ssp.label.cancel-button', 'Cancel'),
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
            title: me.textStore.getValueByCode('ssp.label.action-plan.display-strengths.title', 'Strengths'),
            store: me.store,
			viewConfig: {
                markDirty: false
            },
            columns: [
			{
                header: me.textStore.getValueByCode('ssp.label.action-plan.display-strengths.name', 'Name'),
                flex: 0.25,
                dataIndex: 'name',
				
				field: {
                    xtype: 'textfield',
					fieldStyle: "margin-bottom:12px;",
					maxLength: 80,
					allowBlank: false
                }
            }, {
                header: me.textStore.getValueByCode('ssp.label.action-plan.display-strengths.description', 'Description'),
                flex: 0.50,
                dataIndex: 'description',
				field: {
                    xtype: 'textfield',
					fieldStyle: "margin-bottom:12px;",
					maxLength: 2000,
					allowBlank: false
                }
            }, {
                header: me.textStore.getValueByCode('ssp.label.action-plan.display-strengths.confidentiality', 'Confidentiality'),
                dataIndex: 'confidentialityLevel',
                renderer: me.columnRendererUtils.renderConfidentialityLevel,
                required: true,
                flex: 0.25,
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
                    tooltip: me.textStore.getValueByCode('ssp.tooltip.add-strength-button', 'Add a Strength'),
                    text: me.textStore.getValueByCode('ssp.label.add-strength-button', 'Add Strength'),
                    hidden: !me.authenticatedPerson.hasAccess('ADD_STRENGTH_BUTTON'),
                    xtype: 'button',
                    itemId: 'addStrengthButton'
                },
				{
					tooltip: me.textStore.getValueByCode('ssp.tooltip.delete-strength-button', 'Delete a Strength'),
                    text: me.textStore.getValueByCode('ssp.label.delete-strength-button', 'Delete Strength'),
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
                    text: me.textStore.getValueByCode('ssp.label.action-plan.display-goals.edit-goal', 'Double-click to edit a strength')
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
