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
Ext.define('Ssp.view.tools.actionplan.EditGoalForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.editgoalform',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.EditGoalFormViewController',
    inject: {
        store: 'confidentialityLevelsAllUnpagedStore',
        textStore: 'sspTextStore'
    },
	initComponent: function() {
        Ext.applyIf(this, {
        	title: me.textStore.getValueByCode('ssp.label.action-plan.edit-goal-form.title', 'Add Goal'),
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 150
            },            
        	items: [
                {
                    xtype: 'textfield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.action-plan.edit-goal-form.name', 'Name'),
                    anchor: '100%',
                    name: 'name',
                    allowBlank: false
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.action-plan.edit-goal-form.description', 'Description'),
                    anchor: '100%',
                    name: 'description',
                    allowBlank: false
                },{
			        xtype: 'combobox',
			        itemId: 'confidentialityLevel',
			        name: 'confidentialityLevelId',
			        fieldLabel: me.textStore.getValueByCode('ssp.label.action-plan.edit-goal-form.confidentiality', 'Confidentiality Level'),
			        emptyText: 'Select One',
			        store: this.store,
			        valueField: 'id',
			        displayField: 'name',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true
				}],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: me.textStore.getValueByCode('ssp.label.save-button', 'Save'),
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: me.textStore.getValueByCode('ssp.label.cancel-button', 'Cancel'),
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});