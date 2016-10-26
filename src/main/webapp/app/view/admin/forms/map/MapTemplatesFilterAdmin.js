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


Ext.define('Ssp.view.admin.forms.map.MapTemplatesFilterAdmin', {
	extend: 'Ext.form.Panel',
	alias : 'widget.maptemplatesfilteradmin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.map.LoadTemplateViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
		programsStore: 'programsStore',
		departmentsStore: 'departmentsStore',
		divisionsStore: 'divisionsStore',
        catalogYearsStore: 'catalogYearsStore',
        mapTemplateTagsStore: 'mapTemplateTagsStore',
        textStore: 'sspTextStore'

    },
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
			        title: 'Filter Templates',
		    		autoScroll: true,
					width: '100%',
		    		height: '100%',
		    		bodyPadding: 5,
				    layout: 'anchor',
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 125
				    },
				    defaultType: 'displayfield',
				    
				    items: [
			                {
			                    xtype: 'fieldcontainer',
			                    border: 0,
			                    title: '',
			                    defaultType: 'displayfield',
			                    layout: 'hbox',
			                    align: 'stretch',
			                    padding: 0,
			                    margin: '0 0 0 0',
			                    
			                    items: [
			                      
			                    {
			                    xtype: 'fieldset',
			                    border: 0,
			                    title: '',
			                    defaultType: 'displayfield',
			                    layout: 'vbox',
			                    align: 'stretch',
			                    padding: 0,
			                    margin: '0 0 0 5',
			                    items: [ 
			                             {
									    xtype: 'container',
									    border: 0,
									    title: '',
									    margin: '0 0 0 0',
									    padding: '0 0 0 5',
									    layout: 'hbox',
									    defaults: {
									        anchor: '100%'
									    },
									    items: [{
									        xtype: 'combobox',
									        name: 'program',
										id: 'program',
									        store: me.programsStore,
									        fieldLabel: 'Program',
											labelWidth:80,
									        emptyText: 'Filter by Program',
									        valueField: 'code',
									        displayField: 'name',
									        mode: 'local',
									        queryMode: 'local',
									        allowBlank: true,
									        itemId: 'program',
									        width: 260
									    	}, {
										        tooltip: 'Reset to All Programs',
										        text: '',
										        width: 23,
										        height: 25,
										        name: 'programCancel',
										        cls: 'mapClearSearchIcon',
										        xtype: 'button',
										        itemId: 'programCancel'
										    }]
										},
										{
										    xtype: 'fieldset',
										    border: 0,
										    title: '',
										    margin: '0 0 0 0',
										    padding: '0 0 0 5',
										    layout: 'hbox',
										    defaults: {
										        anchor: '100%'
										    	},
										    items: [{
										        xtype: 'combobox',
										        name: 'department',
											id: 'department',
										        store: me.departmentsStore,
										        fieldLabel: 'Department',
												labelWidth:80,
										        emptyText: 'Filter by Department',
										        valueField: 'code',
										        displayField: 'name',
										        mode: 'local',
										        queryMode: 'local',
										        allowBlank: true,
										        itemId: 'department',
										        width: 260
										    	}, {
										        tooltip: 'Reset to All Departments',
										        text: '',
										        width: 23,
										        height: 25,
										        name: 'departmentCancel',
										        cls: 'mapClearSearchIcon',
										        xtype: 'button',
										        itemId: 'departmentCancel'
										    	}]
										},{
										    xtype: 'fieldset',
										    border: 0,
										    title: '',
										    margin: '0 0 0 0',
										    padding: '0 0 0 5',
										    layout: 'hbox',
										    defaults: {
										        anchor: '100%'
										    	},
										    items: [{
										        xtype: 'combobox',
										        name: 'division',
											id: 'division',
										        store: me.divisionsStore,
										        fieldLabel: 'Division',
												labelWidth:80,
										        emptyText: 'Filter by Division',
										        valueField: 'code',
										        displayField: 'name',
										        mode: 'local',
										        queryMode: 'local',
										        allowBlank: true,
										        itemId: 'division',
										        width: 260
										    	}, {
										        tooltip: 'Reset to All Divisions',
										        text: '',
										        width: 23,
										        height: 25,
										        name: 'divisionCancel',
										        cls: 'mapClearSearchIcon',
										        xtype: 'button',
										        itemId: 'divisionCancel'
															}]
										},{
											xtype: 'fieldset',
											border: 0,
											title: '',
											margin: '0 0 0 0',
											padding: '0 0 0 5',
											layout: 'hbox',
											defaults: {
												anchor: '100%'
												},
											items: [{
												xtype: 'combobox',
												name: 'catalogYear',
												store: me.catalogYearsStore,
												fieldLabel: 'Catalog Year',
												labelWidth:80,
												emptyText: 'Filter by Catalog Year',
												valueField: 'code',
												displayField: 'name',
												mode: 'local',
												queryMode: 'local',
												allowBlank: true,
												itemId: 'catalogYear',
												width: 260
												}, {
												tooltip: 'Reset to All Catalog Years',
												text: '',
												width: 23,
												height: 25,
												name: 'catalogYearCancel',
												cls: 'mapClearSearchIcon',
												xtype: 'button',
												itemId: 'catalogYearCancel'
												}]
										},{
											xtype: 'fieldset',
											border: 0,
											title: '',
											margin: '0 0 0 0',
											padding: '0 0 0 5',
											layout: 'hbox',
											defaults: {
												anchor: '100%'
												},
											items: [{
												xtype: 'combobox',
												name: 'mapTemplateTag',
												store: me.mapTemplateTagsStore,
												fieldLabel: (me.textStore.getValueByCode('ssp.label.map-template-tag') ? me.textStore.getValueByCode('ssp.label.map-template-tag') : "Template Tag"),
												labelWidth:80,
												emptyText: 'Filter by ' + (me.textStore.getValueByCode('ssp.label.map-template-tag') ? me.textStore.getValueByCode('ssp.label.map-template-tag') : "Template Tag"),
												valueField: 'id',
												displayField: 'name',
												mode: 'local',
												queryMode: 'local',
												allowBlank: true,
												itemId: 'mapTemplateTag',
												width: 260
												}, {
												tooltip: 'Reset to All '  + (me.textStore.getValueByCode('ssp.label.map-template-tag') ? me.textStore.getValueByCode('ssp.label.map-template-tag') : "Template Tags"),
												text: '',
												width: 23,
												height: 25,
												name: 'mapTemplateTagCancel',
												cls: 'mapClearSearchIcon',
												xtype: 'button',
												itemId: 'mapTemplateTagCancel'
												}]
										},{
											xtype: 'fieldset',
											border: 0,
											title: '',
											margin: '0 0 0 0',
											padding: '0 0 0 5',
											layout: 'hbox',
											defaults: {
													anchor: '100%'
													},
											items: [{
												xtype: 'combobox',
												name: 'objectStatusFilter',
												id: 'objectStatusFilter',
												fieldLabel: 'Status',
												labelWidth:80,
												emptyText: 'Filter by Status',
												store: Ext.create('Ext.data.Store', {
													fields: ['value', 'name'],
														data : [
																{"value":"ALL","name":"ALL"},
																{"value":"ACTIVE","name":"ACTIVE"},
																{"value":"INACTIVE","name":"INACTIVE"}
															]
												}),
												valueField: 'name',
												displayField: 'name',
												defaultValue: "ALL",
												value:'ALL',
												mode: 'local',
												queryMode: 'local',
												allowBlank: false,
												itemId: 'objectStatusFilter',
												width: 290
												}]
										},
										{
			                            xtype: 'fieldset',
								    	border: 0,
								    	title: '',
								    	margin: '0 0 0 0',
								    	padding: '0 0 0 5',
								    	layout: 'hbox',
								    	defaults: {
								        		anchor: '100%'
								    			},
			                            items: [
			                            	{
											xtype: 'textfield',
			                                fieldLabel: 'Title',
			                                name: 'templateNameFilter',
			                                itemId: 'templateNameFilter',
			                                id: 'templateNameFilter',
			                                maxLength: 50,
			                                allowBlank:true,
			                                labelWidth:80,
											width: 290,
											enableKeyEvents:true											
			                            }]}
			            ]}]
			         }],
					    
		           dockedItems: [
     		              {
     		               xtype: 'toolbar',
     		               items: [{
		        	    	xtype: 'label',
		        	    	html: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE,
		        	    	itemId: 'saveSuccessMessage',
		        	    	style: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_STYLE,
		        	    	hidden: true
		        	    }]
     		           }]
				});
		
	     return me.callParent(arguments);
	}

});