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
Ext.define('Ssp.view.person.Coach', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personcoach',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.CoachViewController',
    inject: {
    	coachesStore: 'coachesStore',
        configStore: 'configStore'
    },
	width: '100%',
	padding: '0 0 0 0',
	layout: {
		type:'vbox'
	},
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
			    border: 0,
			    fieldDefaults: {
			        msgTarget: 'side',
			        labelAlign: 'top',
			        labelWidth: 100
			    },	
				items: [{
			            xtype: 'fieldset',
			            border: 0,
			            padding: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			                //anchor: '100%'
			            },
			       items: [{
				        xtype: 'combobox',
				        name: 'coachId',
				        itemId: 'coachCombo',
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' +  me.configStore.getConfigByName('coachFieldLabel'),
				        emptyText: 'Select One',
				        store: me.coachesStore,
				        valueField: 'id',
				        displayField: 'fullName',
				        mode: 'local',
				        queryMode: 'local',
				        allowBlank: false,
						forceSelection: true,
						typeAhead: false,
				        editable: false,
						width: 300
					},{
				        fieldLabel: 'Office',
				        itemId: 'officeField',
				        name: 'coachOffice',
						width: 300,
						disabled: true
				    },{
				        fieldLabel: 'Phone',
				        itemId: 'phoneField',
				        name: 'coachPhone',
						width: 300,
						disabled: true
				    },{
				        fieldLabel: 'Email',
				        itemId: 'emailAddressField',
				        name: 'coachEmailAddress',
						width: 300,
						disabled: true
				    },{
				        fieldLabel: 'Department',
				        itemId: 'departmentField',
				        name: 'coachDepartment',
						width: 300,
						disabled: true
				    }]
			    }]
			});
		
		return me.callParent(arguments);
	}
});