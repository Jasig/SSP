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

Ext.define('Ssp.view.SearchForm',{
	extend: 'Ext.form.Panel',
	alias : 'widget.searchForm',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchFormViewController',
    inject: {
     	appEventsController: 'appEventsController',
    	programStatusesStore: 'programStatusesStore',
        coachesStore: 'coachesStore',
        planExistsStore: 'planExistsStore',
        sapStatusesStore: 'sapStatusesActiveUnpagedStore',
        planStatusStore: 'planStatusStore',
        personTableTypesStore: 'personTableTypesStore',
        currentlyRegisteredStore: 'currentlyRegisteredStore',
        earlyAlertResponseLateStore: 'earlyAlertResponseLateStore',
        programsStore: 'programsStore',
        configStore: 'configurationOptionsUnpagedStore',
        specialServiceGroupsActiveUnpagedStore: 'specialServiceGroupsActiveUnpagedStore',
        textStore:'sspTextStore',
        termsStore: 'termsStore',
        campusesStore: 'campusesAllUnpagedStore'
    },
    collapsible: true,
	hideCollapseTool: true,
	border: 0,
    padding: '0 14px 0 0',
	header: false,
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
    	me.planStatusStore.configStore = me.configStore;
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
		    bodyPadding: '0 4 0 4',
			defaults:{
			    	 enableKeyEvents:true
			},
            items: [{
               layout:'column',
			   border: false,
	           manageOverflow: 3,
			   items:[{
				// Not doing relative column widths on these things b/c it makes us susceptible to wrapping
				// which makes the form scroll vertically on small resolutions, but something is wrong with visibility
				// calculations and you then can't scroll the entire form into view (as discussed at length above)
                   xtype: 'checkboxfield',
			       fieldLabel: me.textStore.getValueByCode('ssp.label.search.my-plans', 'My Plans'),
			       name: 'myPlans',
			       itemId: 'myPlans',
			       labelWidth: 60
			    }, {
			        xtype: 'checkboxfield',
			        fieldLabel: me.textStore.getValueByCode('ssp.label.search.my-watches', 'My Watches'),
			        name: 'myWatchList',
				    itemId: 'myWatchList',
				    enableKeyEvents:true,
				    labelWidth: 80,
				    labelAlign: 'right'
			    }, {
			        xtype: 'checkboxfield',
			        fieldLabel: me.textStore.getValueByCode('ssp.label.search.my-caseload', 'My Caseload'),
			        name: 'myCaseload',
				    itemId: 'myCaseload',
				    enableKeyEvents:true,
				    labelWidth: 80,
				    labelAlign: 'right'
			    }]
			}, {
				xtype: 'textfield',
				fieldLabel: me.textStore.getValueByCode('ssp.label.student-id', 'Student Id'),
				emptyText: me.textStore.getValueByCode('ssp.empty-text.search.student-id', 'Enter School ID (Exact)'),
				width: 100,
				name: 'schoolId',
				itemId: 'schoolId',
				enableKeyEvents:true,
				listeners: {
					  afterrender: function(field) {
						field.focus(false, 0);
					  }
				}
			}, {
				layout: 'column',
                border: false,
                items: [{
					xtype: 'textfield',
					fieldLabel: me.textStore.getValueByCode('ssp.label.search.first-name', 'First'),
					emptyText: me.textStore.getValueByCode('ssp.empty-text.search.first-name', 'Enter All or Part of First Name'),
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
				}, {
					xtype: 'textfield',
					fieldLabel: me.textStore.getValueByCode('ssp.label.search.last-name', 'Last'),
					emptyText: me.textStore.getValueByCode('ssp.empty-text.search.last-name', 'Enter All or Part of Last Name'),
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
				}]
			}, {
				layout: 'column',
				border: false,
				items: [
	        
				        {					
					xtype: 'combobox',
					fieldLabel: me.textStore.getValueByCode('ssp.label.search.student-exists-in', 'Student Exists In'),
					store: me.personTableTypesStore,
					valueField: 'code',
					emptyText: me.textStore.getValueByCode('ssp.empty-text.search.student-exists-in', 'Anywhere'),
					displayField: 'displayValue',
					columnWidth: 0.5,
					itemId: 'personTableType',
					name: 'personTableType',
					editable: false,
					//tpl: comboTPLDisplayValueDisplay,
					multiSelect: true
				}, {
				    xtype: 'datefield',
				    format: 'm/d/Y',
				    allowBlank: true,
				    showToday:false,
				    validateOnChange: false,
					columnWidth: 0.5,
				    labelAlign: 'right',
				    labelWidth: 50,
				    emptyText: me.textStore.getValueByCode('ssp.empty-text.search.dob', 'mm/dd/yyyy'),
				    fieldLabel: me.textStore.getValueByCode('ssp.label.dob', 'DOB'),
				    name: 'birthDate',
				    itemId: 'birthDate',
				    onExpand: function() {
						var value = this.getValue();
						var today = new Date();
						this.picker.setValue(Ext.isDate(value) ? value : new Date(today.getYear()-20, today.getMonth(), today.getDate(), 0, 0, 0, 0));
					}
				}]
			}, {
				layout: 'column',
			    border: false,
			    items: [           
			            {
		   		    xtype: 'combobox',
		   		    multiSelect: true,
                    fieldLabel: me.textStore.getValueByCode('ssp.label.program-status', 'Program Status'),
                    emptyText: me.textStore.getValueByCode('ssp.empty-text.program-status', 'Select Status'),
		   		    store: me.programStatusesStore,
   		   		    valueField: 'id',
		   		    displayField: 'name',
                    columnWidth: 0.5,
	                itemId: 'programStatus',
                    name: 'programStatus',
                    editable: false
                    //tpl: comboTPLNameDisplay
                }, {
		   		    xtype: 'combobox',
                    fieldLabel: me.textStore.getValueByCode('ssp.label.search.registered', 'Registered'),
		   		    emptyText: me.textStore.getValueByCode('ssp.empty-text.search.registered', 'Any'),
					columnWidth: 0.5,
	                itemId: 'currentlyRegistered',
                    name: 'currentlyRegistered',
					labelAlign: 'right',
					labelWidth:80,
                    store: me.currentlyRegisteredStore,
   		   		    valueField: 'booleanValue',
		   		    displayField: 'displayValue',
		   		    editable: false
		   		    //tpl: comboTPLDisplayValueDisplay
                }]
			}, {
				xtype: 'combobox',
				multiSelect: true,
			    fieldLabel: me.textStore.getValueByCode('ssp.label.home-campus','Home Campus'),
				emptyText: me.textStore.getValueByCode('ssp.empty-text.search.home-campus', 'Select Home Campus'),
				store: me.campusesStore,
				valueField: 'id',
				displayField: 'name',
				width:100,
				itemId: 'homeCampus',
				name: 'homeCampus',
				editable: false
			}, {
				xtype: 'combobox',
				multiSelect: true,
				fieldLabel: me.textStore.getValueByCode('ssp.label.search.special-service-group', 'Special Service Group'),
				emptyText: me.textStore.getValueByCode('ssp.empty-text.search.special-service-group', 'Select Special Service Group'),
				store: me.specialServiceGroupsActiveUnpagedStore,
				valueField: 'id',
				displayField: 'name',
				width:100,
				itemId: 'specialServiceGroup',
				name: 'specialServiceGroup',
				editable: false
				//tpl: comboTPLNameDisplay
			}, {
				xtype: 'combobox',
				multiSelect: true,
				fieldLabel: me.textStore.getValueByCode('ssp.label.search.start-term', 'Start Term'),
				emptyText: me.textStore.getValueByCode('ssp.empty-text.search.start-term', 'Select Start Term'),
				store: me.termsStore,
				valueField: 'code',
				displayField: 'name',
				width: 50,
				itemId: 'actualStartTerm',
				name: 'actualStartTerm',
				mode: 'local',
				editable: false
				//tpl: comboTPLNameDisplay
			}, {
				xtype: 'combobox',
				multiSelect: true,
				fieldLabel: me.textStore.getValueByCode('ssp.label.search.declared-major','Declared Major'),
				emptyText: me.textStore.getValueByCode('ssp.empty-text.search.declared-major', 'Select Major'),
				width:100,
				itemId: 'declaredMajor',
				name: 'declaredMajor',
				store: this.programsStore,
				valueField: 'code',
				displayField: 'name',
				mode: 'local',
				editable: false
				//tpl: comboTPLNameDisplay
			}, {
				xtype: 'combobox',
				fieldLabel: me.textStore.getValueByCode('ssp.label.search.assigned-coach', 'Assigned Coach'),
				multiSelect: true,
				emptyText: me.textStore.getValueByCode('ssp.empty-text.search.assigned-coach', 'Select Coach'),
				width: 100,
				name: 'coachId',
				itemId: 'coachId',
				store: this.coachesStore,
				valueField: 'id',
				displayField: 'fullName',
				mode: 'local',
				editable: false
				//tpl: comboTPLFullNameDisplay
			},	{
				layout: 'column',
				border: false,
				items: [{
					xtype: 'combobox',
					fieldLabel: me.textStore.getValueByCode('ssp.label.search.early-alert-status', 'Early Alert Status'),
					emptyText: me.textStore.getValueByCode('ssp.empty-text.search.early-alert-status', 'Any'),
					columnWidth: 0.5,
					name: 'earlyAlertResponseLate',
					itemId: 'earlyAlertResponseLate',
					store: me.earlyAlertResponseLateStore,
					valueField: 'code',
					displayField: 'displayValue',
					editable: false
					//tpl: comboTPLDisplayValueDisplay
				}, {
					xtype: 'combobox',
					fieldLabel: me.textStore.getValueByCode('ssp.label.search.fa-sap-status', 'FA SAP Status'),
					columnWidth: 0.5,
					labelWidth:100,
					labelAlign:'right',
					emptyText: me.textStore.getValueByCode('ssp.empty-text.search.fa-sap-status', 'Any'),
					name: 'financialAidSapStatusCode',
					itemId: 'financialAidSapStatusCode',
					store: me.sapStatusesStore,
					valueField: 'code',
					displayField: 'name',
					editable: false,
					multiSelect: true
					//tpl: comboTPLNameDisplay
				}]
			}, {
				layout: 'column',
				border: false,
				items: [{
					xtype: 'combobox',
					fieldLabel: me.textStore.getValueByCode('ssp.label.search.plan-status','Plan Status'),
					columnWidth: 0.5,
					emptyText: me.textStore.getValueByCode('ssp.empty-text.search.plan-status', 'Any'),
					name: 'planStatus',
					itemId: 'planStatus',
					store: me.planStatusStore,
					valueField: 'code',
					displayField: 'displayValue',
					editable: false
					//tpl: comboTPLDisplayValueDisplay
				}, {
					xtype: 'combobox',
					fieldLabel: me.textStore.getValueByCode('ssp.label.search.plan-exists', 'Plan Exists'),
					columnWidth: 0.5,
					emptyText: me.textStore.getValueByCode('ssp.empty-text.search.plan-exists', 'Any'),
					name: 'planExists',
					itemId: 'planExists',
					labelAlign: 'right',
					labelWidth:80,
					store: me.planExistsStore,
					valueField: 'code',
					displayField: 'displayValue',
			   		editable: false
			   		//tpl: comboTPLDisplayValueDisplay
				}]
			}, {
				layout: {
					type: 'hbox',
					align: 'stretch'
				},
				border: false,
				items: [{
					layout: {
						type: 'vbox'
					},
                	border: false,
                	width: 105,
                	items: [{
						xtype: 'label',
						text: me.textStore.getValueByCode('ssp.label.search.hours-earned', 'Hours Earned'),
						style: 'display:inline-block;text-align:center;color:#15428B',
						padding: '0 0 5 25',
						width: 105 // no relative column widths b/c you really want all three cols always close together
                	}, {
						xtype: 'numberfield',
						minValue: 0.0,
						step: 1,
						allowDecimals: true,
						decimalPrecision: 2,
						allowBlank: true,
						width: 95, // no relative column widths b/c you really want all three cols always close together
						labelWidth: 30, // no relative column widths b/c you really want all three cols always close together
						labelAlign: 'right',
						fieldLabel: me.textStore.getValueByCode('ssp.label.search.high','High'),
						name: 'hoursEarnedMax',
						itemId: 'hoursEarnedMax',
						enableKeyEvents:true
               		}, {
						xtype: 'numberfield',
						allowDecimals: true,
						decimalPrecision: 2,
						allowBlank: true,
						minValue: 0.0,
						step: 1,
						labelWidth: 30, // no relative column widths b/c you really want all three cols always close together
						width: 95, // no relative column widths b/c you really want all three cols always close together
						labelAlign: 'right',
						fieldLabel: me.textStore.getValueByCode('ssp.label.search.low','Low'),
						name: 'hoursEarnedMin',
						enableKeyEvents:true,
						itemId: 'hoursEarnedMin'
	  				}]
                }, {
					layout: {
						type: 'vbox'
					},
					border: false,
				    width: 75,
					items: [{
					   xtype: 'label',
					   text: me.textStore.getValueByCode('ssp.label.search.gpa', 'GPA'),
					   style: 'display:inline-block;text-align:center;color:#15428B',
                       padding: '0 0 5 0',
					   width: 65 // no relative column widths b/c you really want all three cols always close together
					}, {
						   xtype: 'numberfield',
						   minValue: 0.001,
						   maxValue: 5,
						   step: 0.1,
						   decimalPrecision:2,
						   allowDecimals: true,
						   allowBlank: true,
						   width: 65, // no relative column widths b/c you really want all three cols always close together
						   name: 'gpaMax',
						   itemId: 'gpaMax',
						   enableKeyEvents:true
					}, {
						   xtype: 'numberfield',
						   minValue: 0.0,
						   maxValue: 5.0,
						   step: 0.1,
						   decimalPrecision:2,
						   allowDecimals: true,
						   allowBlank: true,
						   width: 65, // no relative column widths b/c you really want all three cols always close together
						   name: 'gpaMin',
						   itemId: 'gpaMin',
						   enableKeyEvents:true
				    }]
                }, {
					layout: {
						type: 'vbox'
					},
					border: false,
					width: 70,
					items: [{
					   xtype: 'label',
					   text: me.textStore.getValueByCode('ssp.label.local-gpa', 'Local GPA'),
					   itemId: 'localGpa',
					   style: 'display:inline-block;text-align:center;color:#15428B',
					   padding: '0 0 5 0',
					   width: 65 // no relative column widths b/c you really want all three cols always close together
					}, {
						   xtype: 'numberfield',
						   minValue: 0.001,
						   maxValue: 5,
						   step: 0.1,
						   decimalPrecision:2,
						   allowDecimals: true,
						   allowBlank: true,
						   width: 65, // no relative column widths b/c you really want all three cols always close together
						   name: 'localGpaMax',
						   itemId: 'localGpaMax',
						   enableKeyEvents:true
					}, {
						   xtype: 'numberfield',
						   minValue: 0.0,
						   maxValue: 5.0,
						   step: 0.1,
						   decimalPrecision:2,
						   allowDecimals: true,
						   allowBlank: true,
						   width: 65, // no relative column widths b/c you really want all three cols always close together
						   name: 'localGpaMin',
						   itemId: 'localGpaMin',
						   enableKeyEvents:true
				    }]
                }, {
					layout: {
						type: 'vbox',
						padding: '0 0 0 2'
					},
					border: false,
					width: 85,
					items: [{
					   xtype: 'label',
					   itemId: 'programGpa',
					   text: me.textStore.getValueByCode('ssp.label.program-gpa','Program GPA'),
					   style: 'display:inline-block;text-align:center;color:#15428B',
                       padding: '0 0 5 0',
					   width: 75 // no relative column widths b/c you really want all three cols always close together
					}, {
					   xtype: 'numberfield',
					   minValue: 0.001,
					   maxValue: 5,
					   step: 0.1,
					   decimalPrecision:2,
					   allowDecimals: true,
					   allowBlank: true,
					   padding: '0 0 0 3',
					   width: 65, // no relative column widths b/c you really want all three cols always close together
					   name: 'programGpaMax',
					   itemId: 'programGpaMax',
					   enableKeyEvents:true
					}, {
					   xtype: 'numberfield',
					   minValue: 0.0,
					   maxValue: 5.0,
					   step: 0.1,
					   decimalPrecision:2,
					   allowDecimals: true,
					   allowBlank: true,
					   padding: '0 0 0 3',
					   width: 65, // no relative column widths b/c you really want all three cols always close together
					   name: 'programGpaMin',
					   itemId: 'programGpaMin',
					   enableKeyEvents:true
                	}]
				}]
			}]
        });
        return this.callParent(arguments);
    }	
});
