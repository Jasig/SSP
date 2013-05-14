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
        statesStore: 'statesStore'
    },
	width: '100%',
    height: '100%',    
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
				        fieldLabel: 'First Name',
				        name: 'firstName',
				        itemId: 'firstName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: 'Middle Name',
				        name: 'middleName',
				        itemId: 'middleName',
				        maxLength: 50,
				        allowBlank:true
				    },{
				        fieldLabel: 'Last Name',
				        name: 'lastName',
				        itemId: 'lastName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: 'Student ID',
				        name: 'schoolId',
				        minLength: 0,
				        maxLength: 7,
				        itemId: 'studentId',
				        allowBlank:false
				    },{
				    	xtype: 'datefield',
				    	fieldLabel: 'Birth Date',
				    	itemId: 'birthDate',
				    	altFormats: 'm/d/Y|m-d-Y',
				    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
				        name: 'birthDate',
				        allowBlank:false
				    },{
				        fieldLabel: 'Home Phone',
				        name: 'homePhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'homePhone' 
				    },{
				        fieldLabel: 'Work Phone',
				        name: 'workPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'workPhone'
				    },{
				        fieldLabel: 'Cell Phone',
				        name: 'cellPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maxLength: 25,
				        allowBlank:true,
				        itemId: 'cellPhone'
				    },{
				        fieldLabel: 'School Email',
				        name: 'primaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:true,
				        itemId: 'primaryEmailAddress'
				    },{
				        fieldLabel: 'Alternate Email',
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
				    	fieldLabel: 'Non-local',
				    	name: 'nonLocalAddress',
				    	renderer: me.columnRendererUtils.renderFriendlyBoolean
				    },{
				        fieldLabel: 'Address Line 1',
				        name: 'addressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'addressLine1'
				    },{
				        fieldLabel: 'Address Line 2',
				        name: 'addressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'addressLine2'
				    },{
				        fieldLabel: 'City',
				        name: 'city',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'city'
				    },{
				        xtype: 'combobox',
				        name: 'state',
				        fieldLabel: 'State',
				        emptyText: 'Select a State',
				        store: me.statesStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true,
				        forceSelection: true,
				        itemId: 'state'
					},{
				        fieldLabel: 'Zip Code',
				        name: 'zipCode',
				        maxLength: 10,
				        allowBlank:true,
				        itemId: 'zipCode'
				    },{
				    	xtype: 'displayfield',
				    	fieldLabel: 'ALTERNATE ADDRESS'
				    },{
				    	xtype:'checkbox',
				    	fieldLabel: 'In Use',
				    	name: 'alternateAddressInUse'
				    },{
				        fieldLabel: 'Address',
				        name: 'alternateAddressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'alternateAddress'
				    },{
				        fieldLabel: 'City',
				        name: 'alternateAddressCity',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'alternateAddressCity'
				    },{
				        xtype: 'combobox',
				        name: 'alternateAddressState',
				        fieldLabel: 'State',
				        emptyText: 'Select a State',
				        store: me.statesStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true,
				        forceSelection: true,
				        itemId: 'alternateAddressState'
					},{
				        fieldLabel: 'Zip Code',
				        name: 'alternateAddressZipCode',
				        maxLength: 10,
				        allowBlank:true,
				        itemId: 'alternateAddressZipCode'
				    },{
				        fieldLabel: 'Country',
				        name: 'alternateAddressCountry',
				        allowBlank:true,
				        itemId: 'alternateAddressCountry'
				    }]
				    }]
				});
		
		return me.callParent(arguments);
	}
});
