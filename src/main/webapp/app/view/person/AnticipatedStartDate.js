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
Ext.define('Ssp.view.person.AnticipatedStartDate', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personanticipatedstartdate',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.AnticipatedStartDateViewController',
    inject: {
    	anticipatedStartTermsStore: 'anticipatedStartTermsStore',
    },
	initComponent: function() {	
		Ext.apply(this, 
				{
		        fieldDefaults: {
		        msgTarget: 'side',
		        labelAlign: 'right',
		        labelWidth: 125
		    },
			border: 0,
			items: [{
		        xtype: 'checkboxgroup',
		        fieldLabel: 'Ability to Benefit',
		        columns: 1,
		        items: [
		            {boxLabel: '', name: 'abilityToBenefit'}
		        ]
		    },{
		        xtype: 'combobox',
		        name: 'anticipatedStartTerm',
		        fieldLabel: 'Anticipated Start Term',
		        emptyText: 'Select One',
		        store: this.anticipatedStartTermsStore,
		        valueField: 'name',
		        displayField: 'name',
		        mode: 'local',
		        typeAhead: true,
		        queryMode: 'local',
		        allowBlank: true
			},{
		        xtype: 'textfield',
		        name: 'anticipatedStartYear',
		        fieldLabel: 'Anticipated Start Year',
                minLength: 4,
                maxLength: 4,
                width: 200,
                emptyText: 'xxxx',
                maskRe: /\d/,
                regex: /^\d{4}$/,
                regexText: 'Must be a four-digit number',
		        allowBlank: true
			}]
		});
		
		return this.callParent(arguments);
	}
});