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
Ext.define('Ssp.view.tools.map.FAView', {
    extend: 'Ext.window.Window',
    alias: 'widget.faview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.FAViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils'
        //sspConfig: 'sspConfig'
    },
    height: 475,
    width: 380,
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Financial Aid',
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
                    msgTarget: 'side'
                },
                items: [ {
                    xtype: 'fieldset',
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
                        fieldLabel: 'GPA',
                        name: 'cumGPA',
                        itemId: 'cumGPA',
                        labelWidth: 30
                    
                    },  
                    {
                        xtype: 'tbspacer'
                    }, {
                        fieldLabel: 'Standing',
                        name: 'academicStanding',
                        itemId: 'academicStanding',
                        labelWidth: 60
                    }, {
                        fieldLabel: 'Restrictions',
                        name: 'currentRestrictions',
                        itemId: 'currentRestrictions',
                        labelWidth: 80
                    }, {
                        xtype: 'tbspacer',
                        height: '10'
                    },
                    {
                        fieldLabel: 'FA GPA',
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
                        fieldLabel: 'Hours needed to earn a 2.0 GPA with all A grades',
                        name: 'gpa20AHrsNeeded',
                        itemId: 'gpa20AHrsNeeded',
						value: "N/A",
						labelWidth: 280
                    },
                    {
                    	padding: '0 0 0 20',
                        fieldLabel: 'Hours needed to earn a 2.0 GPA with all B grades',
                        name: 'gpa20BHrsNeeded',
                        itemId: 'gpa20BHrsNeeded',
						value: "N/A",
						labelWidth: 280
                    },
                    {
                        fieldLabel: 'Hrs Earned',
                        name: 'creditHoursEarned',
                        itemId: 'creditHoursEarned',
						labelWidth: 80
                    }, {
                        fieldLabel: 'Hrs Attempted',
                        name: 'creditHoursAttempted',
                        itemId: 'creditHoursAttempted',
						labelWidth: 100
                    }, {
                        fieldLabel: 'Comp Rate',
                        name: 'creditCompletionRate',
                        itemId: 'creditCompletionRate',
						labelWidth: 80
                    },
                    {
                    	padding: '0 0 0 20',
                        fieldLabel: 'Hours need to earn a 67% completion rate',
                        name: 'neededFor67PtcCompletion',
                        itemId: 'neededFor67PtcCompletion',
						value: "N/A",
						labelWidth: 280,
                    },
                    {
                        xtype: 'tbspacer',
                        height: '10'
                    },{
		                fieldLabel: 'Reg',
                        name: 'registeredTerms',
                        itemId: 'registeredTerms',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'Payment',
                        name: 'paymentStatus',
                        itemId: 'paymentStatus',
                        labelWidth: 80
                    }, {
                        fieldLabel: 'Balance',
                        name: 'balanceOwed',
                        itemId: 'balanceOwed',
                        labelWidth: 80
                    },{
                        fieldLabel: 'SAP',
                        name: 'sapStatus',
                        itemId: 'sapStatus',
                        labelWidth: 30
                    }, {
                        fieldLabel: 'F1',
                        name: 'f1Status',
                        itemId: 'f1Status',
                        labelWidth: 30
		             },{
                        fieldLabel: 'FASFA',
                        name: 'fafsaDate',
                        itemId: 'fafsaDate',
						labelWidth: 60
                    
                    },{
                        fieldLabel: 'FA Award',
                        name: 'currentYearFinancialAidAward',
                        itemId: 'currentYearFinancialAidAward',
						labelWidth: 80
                    
                    },{
                        fieldLabel: 'FA Amount Remaining',
                        name: 'financialAidRemaining',
                        itemId: 'financialAidRemaining',
						labelWidth: 80
                    
                    },{
                        fieldLabel: 'Loan Amount',
                        name: 'originalLoanAmount',
                        itemId: 'originalLoanAmount',
						labelWidth: 80
                	}]
				}]
			}]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
