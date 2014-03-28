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
Ext.define('Ssp.view.SearchForm',{
	extend: 'Ext.form.Panel',
	alias : 'widget.searchForm',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchFormViewController',
	title: 'Search',
    inject: {
     	appEventsController: 'appEventsController',
    	programStatusesStore: 'programStatusesStore',
        coachesStore: 'coachesStore',
        planStatusStore: 'planStatusStore',
        sapStatusesStore: 'sapStatusesActiveUnpagedStore',
        mapStatusStore: 'mapStatusStore',
        currentlyRegisteredStore: 'currentlyRegisteredStore',
        earlyAlertResponseLateStore: 'earlyAlertResponseLateStore',
        programsStore: 'programsStore',
        configStore: 'configurationOptionsUnpagedStore',
        specialServiceGroupsAllUnpagedStore: 'specialServiceGroupsAllUnpagedStore',
        textStore:'sspTextStore'
    },
    collapsible: true,
	layout: {
        type: 'vbox',
        align: 'stretch'
    },    
    scroll: 'vertical',
	listeners: {
	      afterlayout: function() {
	        Ext.ComponentQuery.query('[name=studentId]')[0].focus();
	      }
	},
	initComponent: function() {
    	var me=this;
    	me.configStore.load();
    	me.mapStatusStore.configStore = me.configStore;
        Ext.applyIf(me, {
            width: '50%',
            header: {
            	toolFirst: true,
            	buttonAlign: 'left'
            },
			defaults:{
			    	 enableKeyEvents:true,
			    	 listeners:{
			    	    specialkey:{
							scope: me,
							fn: function(field, el) {
									if(el.getKey() == Ext.EventObject.ENTER){
										this.appEventsController.getApplication().fireEvent("onStudentSearchRequested");
									}
							}	
			    	  	}
					}
			},
            tools: [
                    {
                        text: 'Search',
                        tooltip: 'Search for Student',
                        xtype: 'button',
                        type: 'search',
                        itemId: 'searchStudentButton',
                        align: 'left'
            		},
        			{
                        tooltip: 'Reset',
                        text: 'Reset',
                        type: 'refresh',
                        xtype: 'button',
                        itemId: 'resetStudentSearchButton',
                        align: 'left'
        			}  
                    ], 
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Student Name/ID',
                    emptyText: 'Enter Student Name/ID',
                    width: 100,
                    name: 'studentId',
					enableKeyEvents:true,
					listeners: {
					      afterrender: function(field) {
					        field.focus(false, 0);
					      },
						  specialkey:{
							scope: me,
							fn: function(field, el) {
								if(el.getKey() == Ext.EventObject.ENTER){
									this.appEventsController.getApplication().fireEvent("onStudentSearchRequested");
								}
							}	
			    	  	}
					}
                },
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'Program Status',
                    emptyText: 'Select Status',
		   		    store: me.programStatusesStore,
   		   		    valueField: 'id',
		   		    displayField: 'name',
                    width: 100,
                    name: 'programStatus'
                },    
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'Special Service Group',
                    emptyText: 'Select Special Service Group',
		   		    store: me.specialServiceGroupsAllUnpagedStore,
   		   		    valueField: 'id',
		   		    displayField: 'name',
                    width: 100,
                    name: 'specialServiceGroup'
                },  
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'Assigned Coach',
                    emptyText: 'Select Advisor',
                    width: 100,
                    name: 'coachId',
		   		    store: this.coachesStore,
   		   		    valueField: 'id',
		   		    displayField: 'fullName',
   		   		    mode: 'local',
				    editable: false                    
                },{
		   		    xtype: 'combobox',
                    fieldLabel: 'Declared Major',
                    emptyText: 'Select Major',
                    width: 100,
                    name: 'declaredMajor',
		   		    store: this.programsStore,
   		   		    valueField: 'code',
		   		    displayField: 'name',
   		   		    mode: 'local',
				    editable: false                     
                },
                {   layout: 'column',
                	border: false,
                	items: [
                {
                    xtype: 'label',
                    text: 'Hours Earned',
                    columnWidth: .10
                },
                {
                     xtype: 'numberfield',
                     allowDecimals: true,
                     allowBlank: true,
                     minValue: 0.0,
                     columnWidth: .45,
                     labelAlign: 'right',
                     fieldLabel: 'From',
                     name: 'hoursEarnedMin',
					 enableKeyEvents:true,
					 itemId: 'hoursEarnedMin'
               },
               {
                   xtype: 'numberfield',
                   minValue: 0.0,
                   allowDecimals: true,
                   allowBlank: true,
            	   columnWidth: .45,
                   labelAlign: 'right',
                   fieldLabel: 'To',
                   name: 'hoursEarnedMax',
				   itemId: 'hoursEarnedMax',
				   enableKeyEvents:true
              }]
                },
             {	   layout: 'column',
                   border: false,
                   items: [
                           {
                        	   xtype: 'label',
                        	   text: 'GPA',
                        	   columnWidth: .10
                           },             
                           {
                        	   xtype: 'numberfield',
                        	   minValue: 0.0,
                        	   maxValue: 5.0,
                        	   decimalPrecision:2,                 
                        	   allowDecimals: true,
                        	   allowBlank: true,
                        	   labelAlign: 'right',
                        	   columnWidth: .45,
                        	   fieldLabel: 'From',
                        	   name: 'gpaMin',
							   itemId: 'gpaMin',
							   enableKeyEvents:true
                           },
                           {
                        	   xtype: 'numberfield',
                        	   minValue: 0.001,
                        	   maxValue: 5,
                        	   decimalPrecision:2,               
                        	   allowDecimals: true,
                        	   allowBlank: true,
                        	   labelAlign: 'right',
                        	   columnWidth: .45,
                        	   fieldLabel: 'To',
                        	   name: 'gpaMax',
							   itemId: 'gpaMax',
							   enableKeyEvents:true
                           }              
                ]},
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'Currently Registered',
		   		    emptyText: 'Any',
                    name: 'currentlyRegistered',
                    store: me.currentlyRegisteredStore,
   		   		    valueField: 'booleanValue',
		   		    displayField: 'displayValue'                 
                }, 
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'Early Alert Response Status',
		   		    emptyText: 'Any',
                    name: 'earlyAlertResponseLate',
                    store: me.earlyAlertResponseLateStore,
   		   		    valueField: 'booleanValue',
		   		    displayField: 'displayValue' 
                }, 
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'Fin Aid SAP Status',
                    width: 100,
                    emptyText: 'Any',
                    name: 'financialAidSapStatusCode',
                    store: me.sapStatusesStore,
   		   		    valueField: 'code',
		   		    displayField: 'name'
                },     
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'MAP Status',
                    width: 100,
                    emptyText: 'Any',
                    name: 'mapStatus',
                    store: me.mapStatusStore,
   		   		    valueField: 'code',
		   		    displayField: 'displayValue'                   
                },      
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'Plan Status',
                    width: 100,
                    emptyText: 'Any',
                    name: 'planStatus',
                    store: me.planStatusStore,
   		   		    valueField: 'code',
		   		    displayField: 'displayValue'               
                },
                {	layout: 'column',
                    border: false,
                    items: [
                             {
                                 xtype: 'checkboxfield',
                                 fieldLabel: 'My Caseload',
                                 name: 'myCaseload',
                			     enableKeyEvents:true,
                                 listeners: {
                                     change: function() {
                                      	Ext.ComponentQuery.query('[name=planStatus]')[0].focus();
                                       }
                                 },
                         	   labelAlign: 'left',
                         	   columnWidth: .45
                            },
                            {
        				       xtype: 'datefield',
        				       format: 'm/d/Y',
       				    	   altFormats: 'm/d/Y|m-d-Y',
                         	   allowBlank: true,
                         	   showToday:false, 
                         	   validateOnChange: false,
                         	   labelAlign: 'left',
                         	   columnWidth: .55,
                         	   labelSeparator: '',
                         	   fieldLabel: me.textStore.getValueByCode('ssp.label.dob')+': (mm/dd/ccyy)',
                         	   name: 'birthDate',
 							   itemId: 'birthDate',
 							  onExpand: function() {
 								    var value = this.getValue();
 								    var today = new Date();
 								    this.picker.setValue(Ext.isDate(value) ? value : new Date(today.getYear()-20, today.getMonth(), today.getDate(), 0, 0, 0, 0));
 								}
                            }              
                 ]},

                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'My Plans',
                    name: 'myPlans',
                    listeners: {
                        change: function() {
                        	Ext.ComponentQuery.query('[name=planStatus]')[0].focus();
                        }
                    }
                }             
            ]
              
        });
        return this.callParent(arguments);
    }	
});
