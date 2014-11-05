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
Ext.define('Ssp.view.tools.accommodation.Disposition', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitydisposition',
    height: '100%',
    width: '100%',
    id : 'AccommodationDisposition',
    initComponent: function() {
        var me = this;
        Ext.apply(me, {
        	border: 0,
            padding: 10,
		    defaults: {
		        anchor: '95%'
		    },
            autoScroll: true,
            items: [{
		                xtype: 'fieldcontainer',
		                fieldLabel: 'Disposition',
		                layout: 'vbox',
		                items: [
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Release Signed',
                                name: 'releaseSigned'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Records Requested',
                                name: 'recordsRequested'
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Records Requested From',
        		                labelWidth: 150,
                                name: 'recordsRequestedFrom',
								maxLength: 50
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Referred for Screening of LD/ADD',
                                name: 'referForScreening'
                            },
                            {
                                xtype: 'textfield',
                                fieldLabel: 'Documents Requested From',
                                labelWidth: 180,
                                name: 'documentsRequestedFrom',
								maxLength: 50
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Rights and Duties',
                                name: 'rightsAndDuties'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Eligibility Letter Sent',
                                name: 'eligibleLetterSent'
                            },{
        				    	xtype: 'datefield',
        				    	fieldLabel: 'Eligibility Letter Date',
        				    	itemId: 'eligibleLetterDate',
        				    	labelWidth: 180,
        				    	altFormats: 'm/d/Y|m-d-Y',
        				    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
        				        name: 'eligibleLetterDate',
        				        allowBlank:true,
        				        showToday:false, // b/c 'today' would be browser-local
								listeners: {
									render: function(field){
										Ext.create('Ext.tip.ToolTip',{
											target: field.getEl(),
											html: 'This is the date on which the Eligibility Letter for this student was received, in the institution\'s time zone. The system will not attempt to convert this value to or from your current time zone.'
										});
									}
								}
        				    },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Ineligibility Letter Sent',
                                name: 'ineligibleLetterSent'
                            },{
        				    	xtype: 'datefield',
        				    	fieldLabel: 'Ineligibility Letter Date',
        				    	itemId: 'ineligibleLetterDate',
        				    	labelWidth: 180,
        				    	altFormats: 'm/d/Y|m-d-Y',
        				    	invalidText: '{0} is not a valid date - it must be in the format: 06/02/2012 or 06-02-2012',
        				        name: 'ineligibleLetterDate',
        				        allowBlank:true,
        				        showToday:false, // b/c 'today' would be browser-local
								listeners: {
									render: function(field){
										Ext.create('Ext.tip.ToolTip',{
											target: field.getEl(),
											html: 'This is the date on which the Ineligibility Letter for this student was received, in the institution\'s time zone. The system will not attempt to convert this value to or from your current time zone.'
										});
									}
								}
        				    },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'No disability documentation received',
                                name: 'noDocumentation'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Inadequate documentation',
                                name: 'inadequateDocumentation'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'Document states individual has no disability',
                                name: 'noDisability'
                            },
                            {
                                xtype: 'checkboxfield',
                                boxLabel: 'HS reports no special ed placement/no report',
                                name: 'noSpecialEd'
                            }
                        ]
                    },
                    {
                        xtype: 'checkboxfield',
                        boxLabel: '',
                        fieldLabel: 'On Medication',
                        name: 'onMedication'
                    },
                    {
                        xtype: 'textareafield',
                        fieldLabel: 'Please list medications',
                        name: 'medicationList',
						maxLength: 50
                    },
                    {
                        xtype: 'textareafield',
                        fieldLabel: 'Functional limitations?, please explain',
                        name: 'functionalLimitations',
						maxLength: 50
                    }]
        });

        me.callParent(arguments);
    }

});