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
Ext.define('Ssp.view.tools.map.FAView', {
    extend: 'Ext.window.Window',
    alias: 'widget.faview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.FAViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        textStore: 'sspTextStore'
    },
    height: 600,
    width: 380,
    style : 'z-index: -1;',  
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: me.textStore.getValueByCode('ssp.label.map.financial-aid.title','Financial Aid'),
            name:'financialAidMapPopup',
            itemId: 'financialAidMapPopup',
            items: [{
                xtype: 'form',
                flex: 1,
                border: 0,
                frame: false,
                layout: {
                    align: 'stretch',
                    type: 'vbox'
                },
                 width: '100%',
                height: '100%',
                bodyPadding: 0,
                autoScroll: true,
                itemId: 'faForm',
                items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'vbox',
                align: 'stretch',
                defaultType: 'displayfield',
                fieldDefaults: {
                    msgTarget: 'side',
                    width: '100%',
                    height: '100%'
                },
                items: [ {
                    xtype: 'container',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    margin: '0 0 0 2',
                    padding: '0 0 0 5',
                    layout: 'vbox',
                    align: 'stretch',
                    
                    items: [
					{
                        xtype: 'tbspacer'
                    },
                    {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.cumgpa','GPA'),
                        name: 'cumGPA',
                        itemId: 'cumGPA',
                        labelWidth: 30
                    
                    },  
                    {
                        xtype: 'tbspacer'
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.standing','Standing'),
                        name: 'academicStanding',
                        itemId: 'academicStanding',
                        labelWidth: 60
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.current-restrictions','Restrictions'),
                        name: 'currentRestrictions',
                        itemId: 'currentRestrictions',
                        labelWidth: 80
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.financial-aid-gpa','FA GPA'),
                        name: 'financialAidGpa',
                        itemId: 'financialAidGpa',
                        labelWidth: 50
                    }, 
                    {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    {
                    	padding: '0 0 0 20',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.gpa-20-a-hrs-needed','Hours needed to earn a 2.0 GPA with all A grades'),
                        name: 'gpa20AHrsNeeded',
                        itemId: 'gpa20AHrsNeeded',
						value: "N/A",
						labelWidth: 290
                    },
                    {
                    	padding: '0 0 0 20',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.gpa-20-b-hrs-needed','Hours needed to earn a 2.0 GPA with all B grades'),
                        name: 'gpa20BHrsNeeded',
                        itemId: 'gpa20BHrsNeeded',
						value: "N/A",
						labelWidth: 290
                    },
                    {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.cumgpa','Hrs Earned'),
                        name: 'creditHoursEarned',
                        itemId: 'creditHoursEarned',
						labelWidth: 80
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.credit-hours-attempted','Hrs Attempted'),
                        name: 'creditHoursAttempted',
                        itemId: 'creditHoursAttempted',
						labelWidth: 100
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.credit-completion-date','Comp Rate'),
                        name: 'creditCompletionRate',
                        itemId: 'creditCompletionRate',
						labelWidth: 80
                    },
                    {
                    	padding: '0 0 0 20',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.needed-for-67-ptc-completion','Hours need to earn a 67% completion rate'),
                        name: 'neededFor67PtcCompletion',
                        itemId: 'neededFor67PtcCompletion',
						value: "N/A",
						labelWidth: 240
                    },
                    {
                        xtype: 'tbspacer',
                        height: '10'
                    },{
		                fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.registered-terms','Reg'),
                        name: 'registeredTerms',
                        itemId: 'registeredTerms',
                        labelWidth: 30
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.payment-status','Payment'),
                        name: 'paymentStatus',
                        itemId: 'paymentStatus',
                        labelWidth: 80
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.balanced-owed','Balance'),
                        name: 'balanceOwed',
                        itemId: 'balanceOwed',
                        labelWidth: 80
                    },{
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.sap-status','SAP'),
                        name: 'sapStatus',
                        itemId: 'sapStatus',
                        labelWidth: 30
                    }, {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.f1-status','F1'),
                        name: 'f1Status',
                        itemId: 'f1Status',
                        labelWidth: 30
		             },{
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.fafsa-date','FAFSA'),
                        name: 'fafsaDate',
                        itemId: 'fafsaDate',
						labelWidth: 60
                    
                    },{
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.current-year-financial-aid-award','FA Award'),
                        name: 'currentYearFinancialAidAward',
                        itemId: 'currentYearFinancialAidAward',
						labelWidth: 80
                    
                    },{
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.financial-aid-remaining','FA Amount Remaining'),
                        name: 'financialAidRemaining',
                        itemId: 'financialAidRemaining',
						labelWidth: 130
                    
                    },	
                    {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.financial-aid-accepted-terms','FA Awarded'),
                        name: 'financialAidAcceptedTerms',
                        itemId: 'financialAidAcceptedTerms',
                        labelWidth: 100
                    },
                    {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.institutional-loan-amount','Institutional Loan Amount'),
                        name: 'institutionalLoanAmount',
                        itemId: 'institutionalLoanAmount',
                        labelWidth: 150
                    },{
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.original-loan-amount','Loan Amount'),
                        name: 'originalLoanAmount',
                        itemId: 'originalLoanAmount',
						labelWidth: 80
                	},
                    {
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.terms-left','Remaining FA Terms'),
                        name: 'termsLeft',
                        itemId: 'termsLeft',
                        labelWidth: 120
                    }, {
                        name: 'sapStatusCodeDetails',
                        itemId: 'sapStatusCodeDetails',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.sap-status-code-details','SAP')
                    },{
                        name: 'financialAidFileStatusDetails',
                        itemId: 'financialAidFileStatusDetails',
                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.financial-aid-files-status-details','FA File')
                    }	,{
	                        fieldLabel: me.textStore.getValueByCode('ssp.label.map.financial-aid.eligible-financial-aid','Eligible Fed Aid'),
	                        name: 'eligibleFederalAid',
	                        itemId: 'eligibleFederalAid',
							hidden:true,
	                        labelWidth: 60
	                    }]
				}]
			}]
            }],
                listeners: {
                     afterrender: function(c){
                         c.el.dom.setAttribute('role', 'dialog');
                     }
                }
        });
        
        return me.callParent(arguments);
    }
    
});
