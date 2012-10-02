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
Ext.define('Ssp.view.tools.profile.Person', {
	extend: 'Ext.form.Panel',
	alias : 'widget.profileperson',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfilePersonViewController',
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
    	sspConfig: 'sspConfig'
    },
    width: '100%',
	height: '100%',
    initComponent: function() {	
		var me=this;
    	Ext.apply(me, 
				{
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
				    defaultType: 'displayfield',
				    items: [{
				            xtype: 'fieldset',
				            border: 0,
				            title: '',
				            defaultType: 'displayfield',
				            defaults: {
				                anchor: '100%'
				            },
				       items: 
				       [{
					        fieldLabel: 'Student',
					        name: 'name',
					        itemId: 'studentName'
					    },{
					        fieldLabel: 'Student Id',
					        itemId: 'studentId',
					        name: 'schoolId'
					    }, {
					        fieldLabel: 'Birth Date',
					        name: 'birthDate',
					        itemId: 'birthDate'
					    }, {
					        fieldLabel: 'Home Phone',
					        name: 'homePhone'
					    }, {
					        fieldLabel: 'Cell Phone',
					        name: 'cellPhone'
					    }, {
					        fieldLabel: 'Address',
					        name: 'addressLine1'
					    }, {
					        fieldLabel: 'City',
					        name: 'city'
					    }, {
					        fieldLabel: 'State',
					        name: 'state'
					    }, {
					        fieldLabel: 'Zip Code',
					        name: 'zipCode'
					    }, {
					        fieldLabel: 'School Email',
					        name: 'primaryEmailAddress'
					    }, {
					        fieldLabel: 'Alternate Email',
					        name: 'secondaryEmailAddress'
					    }, {
					        fieldLabel: 'Student Type',
					        name: 'studentType',
					        itemId: 'studentType'
					    }, {
					        fieldLabel: 'SSP Program Status',
					        name: 'programStatus',
					        itemId: 'programStatus'
					    }, {
					        fieldLabel: 'Registered for Current Term',
					        name: 'registeredForCurrentTerm',
					        renderer: me.columnRendererUtils.renderFriendlyBoolean
					    }, {
					        fieldLabel: 'Payment Status',
					        name: 'paymentStatus',
					        hidden: true
					    }, {
					        fieldLabel: 'CUM GPA',
					        name: 'cumGPA',
					        hidden: true
					    },{
					        fieldLabel: 'Academic Program',
					        name: 'academicPrograms',
					        hidden: true
					    },{
					        fieldLabel: me.sspConfig.get('coachFieldLabel'),
					        name: 'coachName',
					        itemId: 'coachName'
					    },{
					        fieldLabel: me.sspConfig.get('coachFieldLabel') + ' Phone',
					        name: 'coachWorkPhone',
					        itemId: 'coachWorkPhone'
					    },{
					        fieldLabel: me.sspConfig.get('coachFieldLabel') + ' Department',
					        name: 'coachDepartmentName',
					        itemId: 'coachDepartmentName'
					    },{
					        fieldLabel: me.sspConfig.get('coachFieldLabel') + ' Office',
					        name: 'coachOfficeLocation',
					        itemId: 'coachOfficeLocation'
					    },{
					        fieldLabel: me.sspConfig.get('coachFieldLabel') + ' Email',
					        name: 'coachPrimaryEmailAddress',
					        itemId: 'coachPrimaryEmailAddress'
					    }]
					    }],
				});
		
	     return me.callParent(arguments);
	}
	
});