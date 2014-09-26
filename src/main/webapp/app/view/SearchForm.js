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
        personTableTypesStore: 'personTableTypesStore',
        currentlyRegisteredStore: 'currentlyRegisteredStore',
        earlyAlertResponseLateStore: 'earlyAlertResponseLateStore',
        programsStore: 'programsStore',
        configStore: 'configurationOptionsUnpagedStore',
        specialServiceGroupsActiveUnpagedStore: 'specialServiceGroupsActiveUnpagedStore',
        textStore:'sspTextStore',
        caseloadActionsStore: 'caseloadActionsStore',
        termsStore: 'termsStore'
    },
    collapsible: true,
	layout: {
        type: 'vbox',
        align: 'stretch'
    },    
    width: '100%',
    height: '100%',
    scroll: 'vertical',
	listeners: {
	      afterlayout: function() {
	        Ext.ComponentQuery.query('[name=schoolId]')[0].focus();
	      }
	},
    
	initComponent: function() {
    	var me=this;
    	me.configStore.load();
    	me.mapStatusStore.configStore = me.configStore;
        Ext.applyIf(me, {
            width: '50%',
            manageOverflow: 2,
            header: {
            	toolFirst: true,
            	buttonAlign: 'left'
            },
			fieldDefaults: {
			            anchor: '100%'
			        },
			
		    bodyPadding: 10,
			defaults:{
			    	 enableKeyEvents:true
			},
            tools: [
                    {
              
                        text: 'Search',
                        tooltip: 'Search for Student',
                        xtype: 'button',
                        type: 'search',
                        itemId: 'searchStudentButton',
	                    height: 25,
                        align: 'left'
            		},
        			{
                        tooltip: 'Reset',
                        text: 'Reset',
                        type: 'refresh',
                        xtype: 'button',
                        itemId: 'resetStudentSearchButton',
	                    height: 25,
                        align: 'left'
        			} 
                    ], 
            items: [
			{  layout:'column',
			   border: false,
	           manageOverflow: 3,
			   items:[
				{
			       xtype: 'checkboxfield',
			       fieldLabel: 'My Plans',
			       name: 'myPlans',
			       itemId: 'myPlans',
				   columnWidth: 0.33,
			       listeners: {
			           change: function() {
			           	Ext.ComponentQuery.query('[name=planStatus]')[0].focus();
			           }
			       }
			   },
				{
			        xtype: 'checkboxfield',
			        fieldLabel: 'My Watch List',
			        name: 'myWatchList',
				    itemId: 'myWatchList',
				    enableKeyEvents:true,
				    labelWidth:100,
				    labelAlign: 'right',
				    columnWidth: 0.33
			   },
				{
			        xtype: 'checkboxfield',
			        fieldLabel: 'My Caseload',
			        name: 'myCaseload',
				    itemId: 'myCaseload',
				    enableKeyEvents:true,
				    labelWidth:100,
			        listeners: {
			            change: function() {
			             	Ext.ComponentQuery.query('[name=planStatus]')[0].focus();
			              }
			        },
				   labelAlign: 'right',
				   columnWidth: 0.33
			   }
			]

			},
                {
                    xtype: 'textfield',
                    fieldLabel: 'School ID',
                    emptyText: 'Enter School ID (Exact)',
                    width: 100,
                    name: 'schoolId',
                    itemId: 'schoolId',
					enableKeyEvents:true,
					listeners: {
					      afterrender: function(field) {
					        field.focus(false, 0);
					      }
					}
                },

                {	layout: 'column',
                    border: false,
                    items: [
             
                            {
                                xtype: 'textfield',
                                fieldLabel: 'First',
                                emptyText: 'Enter All or Part of First Name',
                                columnWidth: 0.5,
                                name: 'firstName',
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
                                xtype: 'textfield',
                                fieldLabel: 'Last',
                                emptyText: 'Enter All or Part of Last Name ',
                                columnWidth: 0.5,
								labelWidth:50,
                                name: 'lastName',
								labelAlign: 'right',
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
                            }]},
							{	layout: 'column',
						                    border: false,
						                    items: [
							{
					   		    xtype: 'combobox',
			                    fieldLabel: 'Student Exists In',
					   		    store: me.personTableTypesStore,
			   		   		    valueField: 'code',
			   		   		    emptyText: 'Anywhere',
					   		    displayField: 'displayValue',
								columnWidth: 0.5,
				                itemId: 'personTableType',
			                    name: 'personTableType',
			                    editable: false
			                },	{
							       xtype: 'datefield',
							       format: 'm/d/Y',
				             	   allowBlank: true,
				             	   showToday:false, 
				             	   validateOnChange: false,
				 					columnWidth: 0.5,
				             	   labelAlign: 'right',
								   labelWidth: 110,
				             	   labelSeparator: '',
				             	   emptyText: 'mm/dd/yyyy',
				             	   fieldLabel: (me.textStore.getValueByCode('ssp.label.dob') ? me.textStore.getValueByCode('ssp.label.dob') : "DOB") + ":",
				             	   name: 'birthDate',
								   itemId: 'birthDate',
								  onExpand: function() {
									    var value = this.getValue();
									    var today = new Date();
									    this.picker.setValue(Ext.isDate(value) ? value : new Date(today.getYear()-20, today.getMonth(), today.getDate(), 0, 0, 0, 0));
									}}]},
				{	layout: 'column',
			                    border: false,
			                    items: [
								
                {
		   		    xtype: 'combobox',
                    fieldLabel: 'Program Status',
                    emptyText: 'Select Status',
		   		    store: me.programStatusesStore,
   		   		    valueField: 'id',
		   		    displayField: 'name',
                    columnWidth: 0.5,
	                itemId: 'programStatus',
                    name: 'programStatus',
                    editable: false
                }, 
				{
		   		    xtype: 'combobox',
                    fieldLabel: 'Registered',
		   		    emptyText: 'Any',
					columnWidth: 0.5,
	                itemId: 'currentlyRegistered',
                    name: 'currentlyRegistered',
					labelAlign: 'right',
					labelWidth:100,
                    store: me.currentlyRegisteredStore,
   		   		    valueField: 'booleanValue',
		   		    displayField: 'displayValue',
		   		    editable: false
                }]},	{
			   		    xtype: 'combobox',
	                    fieldLabel: 'Special Service Group',
	                    emptyText: 'Select Special Service Group',
			   		    store: me.specialServiceGroupsActiveUnpagedStore,
	   		   		    valueField: 'id',
			   		    displayField: 'name',
			            width:100,
		                itemId: 'specialServiceGroup',
	                    name: 'specialServiceGroup',
	                    editable: false
	                },
	                {
	                    xtype: 'combobox',
	                    fieldLabel: 'Start Term',
	                    emptyText: 'Select Start Term',
	                    store: me.termsStore,
	                    valueField: 'code',
	                    displayField: 'name',
	                    width: 50,
		                itemId: 'actualStartTerm',
	                    name: 'actualStartTerm',
	                    mode: 'local',
	                    editable: false
	                },
	               {
			   		    xtype: 'combobox',
	                    fieldLabel: 'Declared Major',
	                    emptyText: 'Select Major',
	                    width:100,
		                itemId: 'declaredMajor',
	                    name: 'declaredMajor',
			   		    store: this.programsStore,
	   		   		    valueField: 'code',
			   		    displayField: 'name',
	   		   		    mode: 'local',
					    editable: false                     
	                },
				
               
				 {
			   		    xtype: 'combobox',
	                    fieldLabel: 'Assigned Coach',
	                    emptyText: 'Select Coach',
	                    width: 100,
	                    name: 'coachId',
		                itemId: 'coachId',
			   		    store: this.coachesStore,
	   		   		    valueField: 'id',
			   		    displayField: 'fullName',
	   		   		    mode: 'local',
					    editable: false                    
	                },	{	layout: 'column',
					                    border: false,
					                    items: [	{
				   		    xtype: 'combobox',
		                    fieldLabel: 'Early Alert Status',
				   		    emptyText: 'Any',
							 columnWidth: 0.5,
		                    name: 'earlyAlertResponseLate',
			                itemId: 'earlyAlertResponseLate',
		                    store: me.earlyAlertResponseLateStore,
		   		   		    valueField: 'code',
				   		    displayField: 'displayValue',
				   		    editable: false
		                }, 
		                {
				   		    xtype: 'combobox',
		                    fieldLabel: 'FA SAP Status',
		                     columnWidth: 0.5,
							labelWidth:100,
							labelAlign:'right',
		                    emptyText: 'Any',
		                    name: 'financialAidSapStatusCode',
			                itemId: 'financialAidSapStatusCode',
		                    store: me.sapStatusesStore,
		   		   		    valueField: 'code',
				   		    displayField: 'name',
				   		    editable: false
		                } ]},
		                ,
	    				{	layout: 'column',
					                    border: false,
					                    items: [
	                {
			   		    xtype: 'combobox',
	                    fieldLabel: 'MAP Status',
	                    columnWidth: 0.5,
	                    emptyText: 'Any',
	                    name: 'mapStatus',
		                itemId: 'mapStatus',
	                    store: me.mapStatusStore,
	   		   		    valueField: 'code',
			   		    displayField: 'displayValue',
			   		    editable: false
	                },      
	                {
			   		    xtype: 'combobox',
	                    fieldLabel: 'Plan Status',
	                    columnWidth: 0.5,
	                    emptyText: 'Any',
	                    name: 'planStatus',
		                itemId: 'planStatus',
						labelAlign: 'right',
						labelWidth:100,
	                    store: me.planStatusStore,
	   		   		    valueField: 'code',
			   		    displayField: 'displayValue',
			   		 editable: false
	                }]},        
                {   layout: 'column',
                	border: false,
                	items: [
                {
                    xtype: 'label',
                    text: 'Hours Earned',
                    columnWidth: 0.10
                },
                {
                     xtype: 'numberfield',
                     allowDecimals: true,
                     allowBlank: true,
                     minValue: 0.0,
                     columnWidth: 0.45,
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
            	   columnWidth: 0.45,
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
                        	   columnWidth: 0.10
                           },             
                           {
                        	   xtype: 'numberfield',
                        	   minValue: 0.0,
                        	   maxValue: 5.0,
                        	   decimalPrecision:2,                 
                        	   allowDecimals: true,
                        	   allowBlank: true,
                        	   labelAlign: 'right',
                        	   columnWidth: 0.45,
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
                        	   columnWidth: 0.45,
                        	   fieldLabel: 'To',
                        	   name: 'gpaMax',
							   itemId: 'gpaMax',
							   enableKeyEvents:true
                           }              
                ]}      
            ]
              
        });
        return this.callParent(arguments);
    }	
});
