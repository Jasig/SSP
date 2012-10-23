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
Ext.define('Ssp.view.admin.forms.campus.EditCampusEarlyAlertRouting',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editcampusearlyalertrouting',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController',
    inject: {
    	earlyAlertReasonsStore: 'earlyAlertReasonsStore',
    	peopleSearchLiteStore: 'peopleSearchLiteStore',
    	sspConfig: 'sspConfig'
    },
	title: 'Edit Routing Group',
	initComponent: function() {
        var me=this;
		Ext.applyIf(me, {
		    fieldDefaults: {
		        msgTarget: 'side',
		        labelAlign: 'right',
		        labelWidth: 125
		    },
            items: [{
			        xtype: 'combobox',
			        name: 'earlyAlertReasonId',
			        itemId: 'earlyAlertReasonCombo',
			        fieldLabel: 'Early Alert Reason',
			        emptyText: 'Select One',
			        store: me.earlyAlertReasonsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        width: 500
				},{
                    xtype: 'textfield',
                    fieldLabel: 'Group Name',
                    width: 500,
                    name: 'groupName',
                    allowBlank: false
                },{
                    xtype: 'textfield',
                    fieldLabel: 'Group Email',
                    name: 'groupEmail',
                    width: 500,
                    vtype:'email',
			        maxLength: 100,
			        allowBlank: false
                },{
		            xtype: 'combo',
		            store: me.peopleSearchLiteStore,
		            itemId: 'personCombo',
		            displayField: 'displayFullName',
		            emptyText: 'Name or ' + me.sspConfig.get('studentIdAlias'),
		            valueField:'id',
		            typeAhead: false,
		            fieldLabel: 'Person',
		            hideTrigger:true,
		            queryParam: 'searchTerm',
		            allowBlank: true,
		            width: 500,

		            listConfig: {
		                loadingText: 'Searching...',
		                emptyText: 'No matching people found.',
		                getInnerTpl: function() {
		                    return '{firstName} {lastName}';
		                }
		            },
		            pageSize: 10
		        }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return me.callParent(arguments);
    }	
});