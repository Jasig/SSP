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
Ext.define('Ssp.view.tools.studentintake.Personal', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakepersonal',
	id: 'StudentIntakePersonal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.PersonalViewController',
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
        statesStore: 'statesStore',
        textStore:'sspTextStore',
        configStore: 'configStore'
    },
	width: '100%',
    height: '100%',
	minHeight: 1000,
	minWidth: 600,
	style: 'padding: 0px 5px 5px 10px',
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
					autoScroll: true,
    		        border: 0,	
				    bodyPadding: 5,				    
					layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 200
				    },
				    items: [{
				            xtype: 'fieldset',
				            border: 0,
				            title: '',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '95%'
				            },
				       items: [{
				    	xtype: 'displayfield',
				        fieldLabel: 'Intake Completion Date',
				        name: 'formattedStudentIntakeCompleteDate',
				        renderer: Ext.util.Format.dateRenderer('m/d/Y'),
				        listeners: {
				            render: function(field){
				                Ext.create('Ext.tip.ToolTip',{
				                    target: field.getEl(),
				                    html: 'This is the date on which intake data for this student was most recently received. It is shown in institution-local time. E.g. for a May 9, 11pm submission on the US west coast to an east coast school, this would display the "next" day, i.e. May 10.'
				                });
				            }
				        }
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.first-name'),
				        name: 'firstName',
				        itemId: 'firstName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.middle-name'),
				        name: 'middleName',
				        itemId: 'middleName',
				        maxLength: 50,
				        allowBlank:true
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.last-name'),
				        name: 'lastName',
				        itemId: 'lastName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.configStore.getConfigByName('studentIdAlias'),
				        name: 'schoolId',
				        minLength: 0,
				        maxLength: 7,
				        itemId: 'studentId',
				        allowBlank:false
				    },{
				    	xtype: 'datefield',
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.dob'),
				    	itemId: 'birthDate',
				    	altFormats: 'm/d/Y|m-d-Y',
				    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
				        name: 'birthDate',
				        allowBlank:false
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.home-phone'),
				        name: 'homePhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'homePhone' 
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.work-phone'),
				        name: 'workPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'workPhone'
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.cell-phone'),
				        name: 'cellPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'cellPhone'
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.school-email'),
				        name: 'primaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:true,
				        itemId: 'primaryEmailAddress'
				    },{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.alternate-email'),
				        name: 'secondaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:true,
				        itemId: 'secondaryEmailAddress'
				    },{
				    	xtype: 'displayfield',
				    	fieldLabel: 'CURRENT ADDRESS'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.non-local'),
				    	name: 'nonLocalAddress',
				    	renderer: me.columnRendererUtils.renderFriendlyBoolean
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.address-1'),
				        name: 'addressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'addressLine1'
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.address-2'),
				        name: 'addressLine2',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'addressLine2'
				    },{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.city'),
				        name: 'city',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'city'
				    },{
				        xtype: 'combobox',
				        name: 'state',
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.state'),
				        emptyText: 'Select a State',
				        store: me.statesStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true,
				        forceSelection: true,
				        itemId: 'state',
						listeners: {
								'select': function() {
								me.statesStore.clearFilter();
							}
						}
					},{
				        fieldLabel: '<span class="syncedField">(sync\'d)</span>  ' + me.textStore.getValueByCode('ssp.label.zip'),
				        name: 'zipCode',
				        maxLength: 10,
				        allowBlank:true,
				        itemId: 'zipCode'
				    },{
				    	xtype: 'displayfield',
				    	fieldLabel: 'ALTERNATE ADDRESS'
				    },{
				    	xtype:'checkbox',
				        fieldLabel: me.textStore.getValueByCode('ssp.label.alt-in-use'),
				    	name: 'alternateAddressInUse'
				    },{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.address-1'),
				        name: 'alternateAddressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'alternateAddressLine1'
				    },{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.address-2'),
						name: 'alternateAddressLine2',
						maxLength: 50,
						allowBlank: true,
						itemId: 'alternateAddressLine2'
					},{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.city'),
				        name: 'alternateAddressCity',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'alternateAddressCity'
				    },{
				        xtype: 'combobox',
				        name: 'alternateAddressState',
				        fieldLabel: me.textStore.getValueByCode('ssp.label.state'),
				        emptyText: 'Select a State',
				        store: me.statesStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true,
				        forceSelection: true,
				        itemId: 'alternateAddressState',
						listeners: {
								'select': function() {
								me.statesStore.clearFilter();
							}
						}
					},{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.zip'),
				        name: 'alternateAddressZipCode',
				        maxLength: 10,
				        allowBlank:true,
				        itemId: 'alternateAddressZipCode'
				    },{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.country'),
				        name: 'alternateAddressCountry',
				        allowBlank:true,
				        itemId: 'alternateAddressCountry',
						maxLength: 50
				    }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});
