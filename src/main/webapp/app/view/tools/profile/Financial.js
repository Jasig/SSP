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
Ext.define('Ssp.view.tools.profile.Financial', {
    extend: 'Ext.form.Panel',
    alias: 'widget.profilefinancial',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfilePersonFinancialViewController',
    inject: {
		textStore: 'sspTextStore'
    },
    width: '100%',
    height: '100%',
    
    initComponent: function(){
        var me = this;
		
		var faAwardsStore = Ext.create('Ext.data.Store', {
	    	fields: [{name: 'accepted', type: 'string'},
	                 {name: 'schoolId', type: 'string'},
	                 {name: 'termCode', type: 'string'}]
		});
		
		var faFilesStore = Ext.create('Ext.data.Store', {
	    	fields: [{name: 'code', type: 'string'},
	                 {name: 'description', type: 'string'},
	                 {name: 'status', type: 'string'},
	                 {name: 'name', type: 'string'}]
		});
        
        Ext.apply(me, {
            border: 0,
            bodyPadding: 10,
            layout: 'anchor',
            defaults: {
                anchor: '100%',
                padding: 0,
                border: 0
            },
            
            items: [{
                xtype: 'container',
                flex: 5,
                layout: {
                    type: 'hbox',
                    align: 'stretch'
                },
                width: '100%',
                items: [{
                    xtype: 'fieldset',
                    title: 'Financial Aid and Loan Information',
                    flex: 5,
                    width: '100%',
                    layout: 'anchor',
                    padding: '6 10 10 10',
                    defaults: {
                        anchor: '100%',
                        height: 18,
                        margin: 0
                    },
                    defaultType: 'displayfield',
                    items: [{
                        fieldLabel: 'Balance',
                        name: 'balanceOwed',
                        itemId: 'balanceOwed',
						labelWidth: 60
                    }, {
                        fieldLabel: 'FA GPA',
                        name: 'financialAidGpa',
                        itemId: 'financialAidGpa',
						labelWidth: 55
                    }, {
                        fieldLabel: 'FAFSA',
                        name: 'fafsaDate',
                        itemId: 'fafsaDate',
						labelWidth: 50
                    }, {
                        fieldLabel: 'FA File',
                        name: 'financialAidFileStatusDetails',
                        itemId: 'financialAidFileStatusDetails',
						labelWidth: 50
                    }, {
                        fieldLabel: 'FA Amount',
                        name: 'financialAidRemaining',
                        itemId: 'financialAidRemaining',
						labelWidth: 75
                    }, {
                        fieldLabel: 'FA Remaining Terms',
                        name: 'termsLeft',
                        itemId: 'termsLeft',
						labelWidth: 130
                    }, {
                        fieldLabel: 'Institutional Loan Amount',
                        name: 'institutionalLoanAmount',
                        itemId: 'institutionalLoanAmount',
						labelWidth: 150
                    }, {
                        fieldLabel: 'Loan Amount',
                        name: 'originalLoanAmount',
                        itemId: 'originalLoanAmount',
						labelWidth: 80
                    }, {
                        fieldLabel: 'SAP Code',
                        name: 'sapStatusCode',
                        itemId: 'sapStatusCode',
						labelWidth: 70
                    }, {
                        fieldLabel: 'SAP Description',
                        name: 'sapStatusDescription',
                        itemId: 'sapStatusDescription'
                    }]
                }, {
                    xtype: 'tbspacer',
                    width: 20
                }, {
                    xtype: 'fieldset',
                    title: 'Financial Aid Awarded',
                    flex: 4,
                    width: '100%',
                    padding: '6 10 10 10',
                    items: [{
						xtype: 'grid',
						itemId: 'financialAidAwards',
						width: '100%',
						height: '100%',
						store: faAwardsStore,
						queryMode:'local',
						autoScroll: true,
			 			markDirty: false,
			            columns: [{
			            	xtype: 'gridcolumn',
			                text: 'Term',
			                dataIndex: 'termCode',
			                flex: 1
			            },{
			            	xtype: 'gridcolumn',
			            	text: 'Accepted',
			                dataIndex: 'acceptedLong',
			                flex: 1
			            }]
					}]
                }]
            }, {
                xtype: 'tbspacer',
                height: 20
            }, {
                xtype: 'container',
                flex: 4,
                layout: 'fit',
                width: '100%',
                items: [{
                    xtype: 'fieldset',
                    title: 'Financial Aid File Status',
                    width: '100%',
                    padding: '6 10 10 10',
                    items: [{
						xtype: 'grid',
						itemId: 'financialAidFiles',
						width: '100%',
						anchor: '100%',
						store: faFilesStore,
						queryMode:'local',
						autoScroll: true,
			 			markDirty: false,
			            columns: [{
			            	xtype: 'gridcolumn',
			                text: 'File Code',
			                dataIndex: 'code',
			                width: 100
			            },{
			            	xtype: 'gridcolumn',
			            	text: 'File Name',
			                dataIndex: 'name',
			                flex: 1
			            },{
			            	xtype: 'gridcolumn',
			            	text: 'File Description',
			                dataIndex: 'description',
			                flex: 2
			            },{
			            	xtype: 'gridcolumn',
			                text: 'File Status',
			                dataIndex: 'status',
			                flex: 1
			            }]
					}]
                }]
            }]
        });
        
        return me.callParent(arguments);
    }
    
});
