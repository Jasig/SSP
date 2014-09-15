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
Ext.define('Ssp.view.tools.profile.Details', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profiledetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfilePersonDetailsViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        textStore:'sspTextStore'
    },
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            border: 0,
            bodyPadding: 0,
            layout: 'anchor',
            name: 'profileDetails',
            itemId: 'profileDetails',
            defaults: {
                anchor: '100%'
            },
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'hbox',
                margin: '0 0 0 0',
                padding: '0 0 0 0',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side'
                },
                items: [{
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: 0.45,
                    items: [{
                        xtype: 'profileperson'
                    }, {
                        xtype: 'fieldset',
                        border: 0,
                        title: '',
                        layout: 'hbox',
                        padding: ' 0 0 0 0',
                        margin: '0 0 0 0',
                        defaultType: 'displayfield',
                        defaults: {
                            anchor: '100%'
                        },
                        
                        items: [                        /*{
                         xtype: 'fieldset',
                         border: 0,
                         title: '',
                         layout: 'vbox',
                         defaultType: 'displayfield',
                         padding: ' 0 0 0 0',
                         margin: '0 0 0 0',
                         defaults: {
                         anchor: '100%'
                         },
                         width: '120',
                         //flex: .40,
                         items: [{
                         
                         xtype: 'button',
                         text: 'SSN',
                         autowidth: 'false',
                         padding: '0 33 5 33'
                         },
                         {
                         xtype: 'tbspacer',
                         height: '10'
                         },
                         
                         {
                         xtype: 'textfield',
                         itemId: 'ssn',
                         fieldLabel: '',
                         disabled: true,
                         width: '100'
                         }]
                         },*/
                        {
                            xtype: 'fieldset',
                            border: 0,
                            title: '',
                            layout: 'vbox',
                            padding: ' 0 0 0 0',
                            margin: '0 0 0 5',
                            defaultType: 'displayfield',
                            defaults: {
                                anchor: '100%'
                            },
                            
                            items: [{
                                fieldLabel: me.textStore.getValueByCode('ssp.label.gender'),
                                name: 'gender',
                                itemId: 'gender',
                                labelWidth: 50
                            }, {
                                fieldLabel: me.textStore.getValueByCode('ssp.label.marital-status'),
                                name: 'maritalStatus',
                                itemId: 'maritalStatus',
                                labelWidth: 80
                            }, {
                                fieldLabel: me.textStore.getValueByCode('ssp.label.ethnicity'),
                                name: 'ethnicity',
                                itemId: 'ethnicity',
                                labelWidth: 55
                            }, {
								fieldLabel: me.textStore.getValueByCode('ssp.label.race'),
								name: 'race',
								itemId: 'race',
								labelWidth: 55
								}
							]
                        }]
                    }, {
                        xtype: 'profileacademicprogram'
                    }, {
                        fieldLabel: 'Intended Program at Admit',
                        itemId: 'intendedProgramAtAdmit',
                        name: 'intendedProgramAtAdmit',
                        labelAlign: 'top',
                        labelPad: 0,
                        margin: '0 0 10 0'
                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 1,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    flex: 0.25,
                    height: '470',
                    margin: '0 0 10 0',
                    items: [
					{
                        fieldLabel: 'Anticipated Start Year/Term',
                        name: 'anticipatedStartYearTerm',
                        itemId: 'anticipatedStartYearTerm'
                    },
					{
                        fieldLabel: 'GPA',
                        name: 'cumGPA',
                        itemId: 'cumGPA'
                    }, {
                        fieldLabel: 'FA GPA',
                        name: 'financialAidGpa',
                        itemId: 'financialAidGpa'
                    }, {
                        fieldLabel: 'Standing',
                        name: 'academicStanding',
                        itemId: 'academicStanding'
                    }, {
                        fieldLabel: 'Restrictions',
                        name: 'currentRestrictions',
                        itemId: 'currentRestrictions'
                    }, {
                        fieldLabel: 'Hrs Earned',
                        name: 'creditHoursEarned',
                        itemId: 'creditHoursEarned'
                    }, {
                        fieldLabel: 'Hrs Attempted',
                        name: 'creditHoursAttempted',
                        itemId: 'creditHoursAttempted'
                    }, {
                        fieldLabel: 'Comp Rate',
                        name: 'creditCompletionRate',
                        itemId: 'creditCompletionRate'
                    }, {
                        fieldLabel: 'Transfer Hrs',
                        name: 'transferHrs',
                        itemId: 'transferHrs',
                        labelWidth: 80
                    }, {
                        fieldLabel: 'Reg',
                        name: 'registeredTerms',
                        itemId: 'registeredTerms',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'Payment',
                        name: 'paymentStatus',
                        itemId: 'paymentStatus',
                        labelWidth: 50
                    }, {
                        fieldLabel: 'Balance',
                        name: 'balanceOwed',
                        itemId: 'balanceOwed'
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    }, {
                        fieldLabel: 'FAFSA',
                        name: 'fafsaDate',
                        itemId: 'fafsaDate'
                    }, {
                        xtype: 'tbspacer',
                        height: 8
                    },{
                        fieldLabel: 'Eligible Fed Aid',
                        name: 'eligibleFederalAid',
                        itemId: 'eligibleFederalAid',
						hidden:true,
                        labelWidth: 60
                    },{
	                        name: 'financialAidFileStatusDetails',
	                        itemId: 'financialAidFileStatusDetails',
	                        xtype:'label',
	                        listeners: { element: 'el', click: function () { 
	                        	var view = Ext.ComponentQuery.query("#profileDetails");
	                        	if(view && view.length > 0)
			                		view[0].getController().onShowFinancialAidFileStatuses();
	                        } } 
	                    },{
	                        xtype: 'tbspacer',
	                        height: 8
	                    },
                    {
	                    	xtype:'label',
	                        name: 'financialAidAcceptedTerms',
	                        itemId: 'financialAidAcceptedTerms',
	                        listeners: { element: 'el', click: function () { 
	                        	var view = Ext.ComponentQuery.query("#profileDetails");
	                        	if(view && view.length > 0)
			                		view[0].getController().onShowFinancialAidAwards();
	                        } }
	                        
                    },		{
                        xtype: 'tbspacer',
                        height: 8
                    },{
	                        fieldLabel: 'FA Amount',
	                        name: 'financialAidRemaining',
	                        itemId: 'financialAidRemaining'

	                    },
                    {
                        fieldLabel: 'Institutional Loan Amount',
                        name: 'institutionalLoanAmount',
                        itemId: 'institutionalLoanAmount',
                        labelWidth: 150
                    },	{
		                        fieldLabel: 'Loan Amount',
		                        name: 'originalLoanAmount',
		                        itemId: 'originalLoanAmount'

		                    },
                    {
                        fieldLabel: 'Remaining FA Terms',
                        name: 'termsLeft',
                        itemId: 'termsLeft',
                        labelWidth: 120
                    },{
                        xtype: 'tbspacer',
                        height: 5
                    }, {
                        name: 'sapStatusCode',
                        itemId: 'sapStatusCode',
                        xtype: 'label',
                        listeners: { element: 'el', click: function (me) { 
                        	var view = Ext.ComponentQuery.query("#profileDetails");
                        	if(view && view.length > 0)
		                		view[0].getController().onShowSAPCodeInfo();
                        } } 
                    },{
	                        fieldLabel: 'F1',
	                        name: 'f1Status',
	                        itemId: 'f1Status',
	                        labelWidth: 15
	                    }]
                
                }, {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    defaults: {
                        anchor: '100%'
                    },
                    padding: 2,
                    margin: '0 0 0 5',
                    flex: 0.30,
                    items: [{
                        fieldLabel: 'Residency',
                        name: 'residencyCounty',
                        itemId: 'residencyCounty',
                        labelWidth: 60
                    }, {
                        xtype: 'tbspacer',
                        height: '20'
                    }, {
                        xtype: 'recenttermactivity'
                    }]
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
});
