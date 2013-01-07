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
Ext.define('Ssp.view.tools.disabilityintake.General', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitygeneral',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.disabilityintake.GeneralViewController',
    inject: {
    	disabilityStatusesStore: 'disabilityStatusesStore'
    }, 
    height: '100%',
    width: '100%',
    id : 'DisabilityIntakeGeneral',
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
			autoScroll: true,
		    layout: {
		    	type: 'vbox',
		    	align: 'stretch'
		    },
		    border: 0,
		    padding: 10,
		    defaults: {
		        anchor: '95%'
		    },
		    fieldDefaults: {
		        msgTarget: 'side',
		        labelAlign: 'right',
		        labelWidth: 200
		    },
		    defaultType: 'textfield',
            items: [{
		                xtype: 'displayfield',
		                name: 'odsRegistrationDate',
		                fieldLabel: 'ODS Registration Date',
		                renderer: Ext.util.Format.dateRenderer('m/d/Y')
		            },{
                        xtype: 'combobox',
                        name: 'disabilityStatusId',
                        fieldLabel: 'ODS Status',
				        emptyText: 'Select One',
				        store: me.disabilityStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
                    },
                    {
                        xtype: 'textareafield',
                        fieldLabel: 'Please Explain Temporary Eligibility',
                        name: 'tempEligibilityDescription'
                    },
                    {
                        xtype: 'textfield',
                        name: 'intakeCounselor',
                        fieldLabel: 'ODS Counselor'
                    },
                    {
                        xtype: 'textfield',
                        name: 'referredBy',
                        fieldLabel: 'Referred to ODS by'
                    }]
        });

        me.callParent(arguments);
    }
});