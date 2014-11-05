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
Ext.define('Ssp.view.person.AnticipatedStartDate', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personanticipatedstartdate',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.AnticipatedStartDateViewController',
    inject: {
		termsStore: 'termsStore'
    },
	
	initComponent: function() {	
		var abilityToBenefit = Ext.create('Ext.data.Store', {
            fields: [{name: 'abilityToBenefit'},
			{name: 'value'}],
            data: [{
                'abilityToBenefit': 'No', value: false
            }, {
                'abilityToBenefit': 'Yes', value: true
            }]
        });
		Ext.apply(this, 
				{
		        fieldDefaults: {
		        msgTarget: 'side',
		        labelAlign: 'top',
		        labelWidth: 125
		    },
			border: 0,
			items: [
			{
		        xtype: 'combobox',
		        name: 'anticipatedStartTerm',
		        itemId: 'anticipatedStartTerm',
		        fieldLabel: 'Anticipated Start Term',
		        emptyText: 'Select One',
		        store: this.termsStore.getCurrentAndFutureTermsStore(true),
		        valueField: 'name',
		        displayField: 'name',
		        mode: 'local',
		        queryMode: 'local',
		        allowBlank: true,
				forceSelection: true,
				typeAhead: false,
				editable: false,
				width: 250
			},{
		        xtype: 'combobox',
		        name: 'anticipatedStartYear',
		        itemId: 'anticipatedStartYear',
		        fieldLabel: 'Anticipated Start Year',
		        emptyText: 'Select One',
		        store: this.termsStore.getCurrentAndFutureYearStore(true),
		        valueField: 'reportYear',
		        displayField: 'reportYear',
		        mode: 'local',
		        queryMode: 'local',
		        allowBlank: true,
				typeAhead: false,
				editable: false,
				width: 250
			},
			{
				xtype: 'combobox',
		        name: 'abilityToBenefit',
		        itemId: 'abilityToBenefit',
		        fieldLabel: 'Ability to Benefit',
		        //emptyText: 'Select One',
		        store: abilityToBenefit,
		        valueField: 'value',
		        displayField: 'abilityToBenefit',
		        mode: 'local',
		        queryMode: 'local',
		        allowBlank: true,
				forceSelection: true,
				typeAhead: false,
				editable: false,
				width: 250
			}
			]
		});
		
		return this.callParent(arguments);
	}
});