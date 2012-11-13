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
				    bodyPadding: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'  
				    },
				    items: [{
				        xtype: 'fieldcontainer',
				        fieldLabel: '',
				        layout: 'hbox',
				        margin: '0 5 0 0',
					    defaultType: 'displayfield',
					    fieldDefaults: {
					        msgTarget: 'side',
					        labelAlign: 'right',
					        labelWidth: 100
					    },
				        items: [{
				            xtype: 'fieldset',
				            border: 0,
				            title: '',
				            defaultType: 'displayfield',
				            defaults: {
				                anchor: '100%'
				            },
				            flex: .55,
				            items:[{
							        fieldLabel: 'Student',
							        name: 'name',
							        itemId: 'studentName'
							    },{
							        fieldLabel: 'Student Id',
							        itemId: 'studentId',
							        name: 'schoolId'
							    },{
							        fieldLabel: 'School Email',
							        name: 'primaryEmailAddress'
							    },{
							        fieldLabel: 'Alternate Email',
							        name: 'secondaryEmailAddress'
							    },{
							        fieldLabel: 'Home Phone',
							        name: 'homePhone'
							    },{
							        fieldLabel: 'Cell Phone',
							        name: 'cellPhone'
							    },{
							        fieldLabel: 'Birth Date',
							        name: 'birthDate',
							        itemId: 'birthDate'
							    },{
							        fieldLabel: 'Student Type',
							        name: 'studentType',
							        itemId: 'studentType'
							    },{
							        fieldLabel: 'Caseload Status',
							        name: 'programStatus',
							        itemId: 'programStatus'
							    },{
							        fieldLabel: 'Registered',
							        name: 'registeredForCurrentTerm',
							        renderer: me.columnRendererUtils.renderFriendlyBoolean
							    },{
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
							    }]
				            
					    },{
				            xtype: 'fieldset',
				            border: 0,
				            title: '',
				            defaultType: 'displayfield',
				            defaults: {
				                anchor: '100%'
				            },
				            padding: 0,
				            flex: .45,
					        items:[{
					            xtype: 'fieldset',
					            border: 1,
					            cls:'ssp-form',
					            title: 'Coach',
					            defaultType: 'displayfield',
					            defaults: {
					                anchor: '100%'
					            },
					            flex: 1,
					            items:[{
							        fieldLabel: me.sspConfig.get('coachFieldLabel'),
							        name: 'coachName',
							        itemId: 'coachName',
							        labelWidth: 80
							    },{
							        fieldLabel: 'Phone',
							        name: 'coachWorkPhone',
							        itemId: 'coachWorkPhone',
							        labelWidth: 80
							    },{
							        fieldLabel: 'Email',
							        name: 'coachPrimaryEmailAddress',
							        itemId: 'coachPrimaryEmailAddress',
							        labelWidth: 80
							    },{
							        fieldLabel: 'Department',
							        name: 'coachDepartmentName',
							        itemId: 'coachDepartmentName',
							        labelWidth: 80
							    },{
							        fieldLabel: 'Office',
							        name: 'coachOfficeLocation',
							        itemId: 'coachOfficeLocation',
							        labelWidth: 80
							    }]},{
						            xtype: 'fieldset',
						            border: 1,
						            cls:'ssp-form',
						            title: 'Student Mailing Address',
						            defaultType: 'displayfield',
						            defaults: {
						                anchor: '100%'
						            },
						            flex: 1,
						            items:[{
							        fieldLabel: 'Non-local',
							        name: 'nonLocalAddress',
							        labelWidth: 80,
							        renderer: me.columnRendererUtils.renderFriendlyBoolean
							    },{
							        fieldLabel: 'Address',
							        height: '60',
							        name: 'address',
							        labelWidth: 80,
							        itemId: 'address'
							    }]},{
						            xtype: 'fieldset',
						            border: 1,
						            cls:'ssp-form',
						            title: 'Student Alternate Address',
						            defaultType: 'displayfield',
						            defaults: {
						                anchor: '100%'
						            },
						            flex: 1,
						            items:[{
							        fieldLabel: 'In Use',
							        name: 'alternateAddressInUse',
							        labelWidth: 80,
							        itemId: 'alternateAddressInUse'
							    },{
							    	fieldLabel: 'Address',
							        name: 'alternateAddress',
							        labelWidth: 80,
							        height: '60',
							        itemId: 'alternateAddress'
							    }]}
							    ]
				       }]
				    }]
				});
		
	     return me.callParent(arguments);
	}
	
});