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
Ext.define('Ssp.view.tools.map.LoadTemplates', {
    extend: 'Ext.window.Window',
    alias: 'widget.loadtemplates',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	controller: 'Ssp.controller.tool.map.LoadTemplateViewController',
    inject: {
        columnRendererUtils: 'columnRendererUtils',
        programsStore: 'programsStore',
        departmentsStore: 'departmentsStore',
        divisionsStore: 'divisionsStore',
        catalogYearsStore: 'catalogYearsStore',
        mapTemplateTagsStore: 'mapTemplateTagsStore',
        store: 'planTemplatesSummaryStore',
        textStore: 'sspTextStore'
    },
    height: 600,
    width: 900,
    resizable: true,
    modal: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'hbox'
            },
            title: me.textStore.getValueByCode('ssp.label.map.load-template.title','Load Template'),
			dockedItems: [{
                xtype: 'toolbar',
                dock: 'top',
                items: [{
                    xtype: 'button',
                    itemId: 'newButton',
                    text: me.textStore.getValueByCode('ssp.label.new-button','New')
                }, '-', {
                    xtype: 'button',
                    itemId: 'openButton',
                    text: me.textStore.getValueByCode('ssp.label.open-button','Open')
                }, '-', {
                    xtype: 'button',
                    itemId: 'cancelButton',
                    text: me.textStore.getValueByCode('ssp.label.cancel-button','Cancel')
                }]
            }],
            items: [{
                xtype: 'form',
                border: 0,
                frame: false,
				title: me.textStore.getValueByCode('ssp.label.map.load-template.filters',"Filters"),
				titleAlign:"center",
                layout: {
                    align: 'stretch',
                    type: 'hbox'
                },
                width: 330,
                height: '80%',
                bodyPadding: 10,
                autoScroll: true,
                items: [{
                    xtype: 'fieldcontainer',
                    border: 0,
                    title: '',
                    defaultType: 'displayfield',
                    layout: 'hbox',
                    align: 'stretch',
                    padding: 0,
                    margin: '0 0 0 0',
                    
                    items: [{
                    xtype: 'container',
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
						        store: me.programsStore,
						        fieldLabel: me.textStore.getValueByCode('ssp.label.map.load-template.program','Program'),
								labelWidth:80,
						        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.load-template.program','Filter by Program'),
						        valueField: 'code',
						        displayField: 'name',
						        mode: 'local',
						        queryMode: 'local',
						        allowBlank: true,
						        itemId: 'program',
						        width: 260
						    	}, {
							        tooltip: me.textStore.getValueByCode('ssp.tooltip.map.load-template.program-reset','Reset to All Programs'),
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
							        name: 'department',
							        store: me.departmentsStore,
							        fieldLabel: me.textStore.getValueByCode('ssp.label.map.load-template.department','Department'),
									labelWidth:80,
							        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.load-template.department','Filter by Department'),
							        valueField: 'code',
							        displayField: 'name',
							        mode: 'local',
							        queryMode: 'local',
							        allowBlank: true,
							        itemId: 'department',
							        width: 260
							    	}, {
							        tooltip: me.textStore.getValueByCode('ssp.tooltip.map.load-template.department-reset','Reset to All Departments'),
							        text: '',
							        width: 23,
							        height: 25,
							        name: 'departmentCancel',
							        cls: 'mapClearSearchIcon',
							        xtype: 'button',
							        itemId: 'departmentCancel'
                                }]
							},{
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
							        name: 'division',
							        store: me.divisionsStore,
							        fieldLabel: me.textStore.getValueByCode('ssp.label.map.load-template.division','Division'),
									labelWidth:80,
							        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.load-template.division','Filter by Division'),
							        valueField: 'code',
							        displayField: 'name',
							        mode: 'local',
							        queryMode: 'local',
							        allowBlank: true,
							        itemId: 'division',
							        width: 260
							    	}, {
							        tooltip: me.textStore.getValueByCode('ssp.tooltip.map.load-template.division','Reset to All Divisions'),
							        text: '',
							        width: 23,
							        height: 25,
							        name: 'divisionCancel',
							        cls: 'mapClearSearchIcon',
							        xtype: 'button',
							        itemId: 'divisionCancel'
                                }]
							},{
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
							        name: 'catalogYear',
							        store: me.catalogYearsStore,
							        fieldLabel: me.textStore.getValueByCode('ssp.label.map.load-template.catalog-year','Catalog Year'),
									labelWidth:80,
							        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.load-template.catalog-year','Filter by Catalog Year'),
							        valueField: 'code',
							        displayField: 'name',
							        mode: 'local',
							        queryMode: 'local',
							        allowBlank: true,
							        itemId: 'catalogYear',
							        width: 260
                                }, {
							        tooltip: me.textStore.getValueByCode('ssp.tooltip.map.load-template.catalog-year','Reset to All Catalog Years'),
							        text: '',
							        width: 23,
							        height: 25,
							        name: 'catalogYearCancel',
							        cls: 'mapClearSearchIcon',
							        xtype: 'button',
							        itemId: 'catalogYearCancel'
                                }]
							},{
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
							        name: 'mapTemplateTag',
							        store: me.mapTemplateTagsStore,
							        fieldLabel: me.textStore.getValueByCode('ssp.label.map-template-tag',"Template Tag"),
									labelWidth:80,
							        emptyText: me.textStore.getValueByCode('ssp.empty-text.map-template-tag',"Filter by Template Tag"),
							        valueField: 'id',
							        displayField: 'name',
							        mode: 'local',
							        queryMode: 'local',
							        allowBlank: true,
							        itemId: 'mapTemplateTag',
							        width: 260
							    	}, {
							        tooltip: me.textStore.getValueByCode('ssp.tooltip.map-template-tag',"Reset to All Template Tags"),
							        text: '',
							        width: 23,
							        height: 25,
							        name: 'mapTemplateTagCancel',
							        cls: 'mapClearSearchIcon',
							        xtype: 'button',
							        itemId: 'mapTemplateTagCancel'
                                }]
							},{
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
						        	name: 'isPrivateFilter',
						        	fieldLabel: me.textStore.getValueByCode('ssp.label.map.load-template.visibility','Visibility'),
									labelWidth:80,
									store: Ext.create('Ext.data.Store', {
									    fields: ['value', 'name'],
									    	data : [
										        	{"value":"ALL","name":"ALL"},
										        	{"value":"ANONYMOUS","name":"ANONYMOUS"},
										        	{"value":"AUTHENTICATED","name":"AUTHENTICATED"},
										        	{"value":"PRIVATE","name":"PRIVATE"}
                                            ]
									}),
                                    emptyText: me.textStore.getValueByCode('ssp.empty-text.map.load-template.template-type','Filter by Template Type'),
                                    valueField: 'value',
                                    displayField: 'name',
                                    value: "ALL",
                                    mode: 'local',
                                    queryMode: 'local',
                                    allowBlank: false,
                                    itemId: 'typeFilter',
                                    width: 290
						    		}]
								},{
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
				        		name: 'objectStatusFilter',
				        		fieldLabel: me.textStore.getValueByCode('ssp.label.map.load-template.status','Status'),
								labelWidth:80,
				        		emptyText: me.textStore.getValueByCode('ssp.empty-text.map.load-template.status','Filter by Status'),
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
								defaultValue: "ACTIVE",
								value:'ACTIVE',
				        		mode: 'local',
				        		queryMode: 'local',
				        		allowBlank: false,
				        		itemId: 'objectStatusFilter',
				        		width: 290
				    			}]
							},
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
                            items: [
                            	{
								xtype: 'textfield',
                                fieldLabel: me.textStore.getValueByCode('ssp.label.map.load-template.template-name-filter-title','Title'),
                                name: 'templateNameFilter',
                                itemId: 'templateNameFilter',
                                maxLength: 50,
                                allowBlank:true,
                                labelWidth:80,
								width: 290,
								enableKeyEvents:true,
								listeners:{
									keyup: function(textField, e, eOpts) {
										var me = this;
				                        var searchString = textField.getValue().trim();
				                        var templatesGrid = me.findParentByType('loadtemplates').query('#allPlansTemplateGridPanel')[0];
										templatesGrid.getStore().filterBy(getFilterRecord(['name'], searchString)); 
				                    }
								}
                            }]}
                            
            
            ]}]
         }]
		},	{
         xtype: 'gridpanel',
         title: '',
         id: 'allPlansTemplateGridPanel',
         width: '90%',
         flex: 1,
         height: '100%',
         border: true,
         autoScroll: true,
         columnLines: true,
         store: me.store,
         columns: [
         {
             text: me.textStore.getValueByCode('ssp.label.map.load-template.visibility-column','Vis'),
             width: 45,
             dataIndex: 'visibility',
             sortable: true,
				renderer:me.columnRendererUtils.renderTemplateVisibility
         },	{
                 text: me.textStore.getValueByCode('ssp.label.map.load-template.status-column','Status'),
                 width: 55,
                 dataIndex: 'objectStatus',
                 sortable: true
         },{
             text: me.textStore.getValueByCode('ssp.label.map.load-template.name-column','Template Title'),
             width: 200,
             dataIndex: 'name',
             sortable: true
         }, {
             text: me.textStore.getValueByCode('ssp.label.map.load-template.modified-date-column','Date/ Time'),
             width: 125,
             dataIndex: 'modifiedDate',
             sortable: true,
             renderer: Ext.util.Format.dateRenderer(me.textStore.getValueByCode('ssp.format.map.load-template.modified-date-column','Y-m-d g:i A'))
             
         }, {
             text: me.textStore.getValueByCode('ssp.label.map.load-template.owner-column','Owner'),
             width: 120,
             sortable: true,
             dataIndex: 'ownerName'
             
         }, {
		    text: me.textStore.getValueByCode('ssp.label.map.load-template.program-column','Program'),
		    width: 200,
		    sortable: true,
		    dataIndex: 'programCode',
		    renderer: function(value){ return me.columnRendererUtils.renderNameForCodeInStore(value, me.programsStore);}
		 }, {
			 text: me.textStore.getValueByCode('ssp.label.map.load-template.department-column','Department'),
			 width: 200,
			 sortable: true,
			 dataIndex: 'departmentCode',
			 renderer: function(value){ return me.columnRendererUtils.renderNameForCodeInStore(value, me.departmentsStore);}

		 }, {
		     text: me.textStore.getValueByCode('ssp.label.map.load-template.division-column','Division'),
		     width: 200,
		     sortable: true,
		     dataIndex: 'divisionCode',
             renderer: function(value){ return me.columnRendererUtils.renderNameForCodeInStore(value, me.divisionsStore);}

	     }, {
			 text: me.textStore.getValueByCode('ssp.label.map.load-template.catalog-year-column','Catalog Year'),
			 width: 85,
			 sortable: true,
			 dataIndex: 'catalogYearCode',
   			 renderer: function(value){ return me.columnRendererUtils.renderNameForCodeInStore(value, me.catalogYearsStore);}

	     }, {
			 text: me.textStore.getValueByCode('ssp.label.map.load-template.template-tag-column',"Template Tag"),
			 width: 200,
			 sortable: true,
			 dataIndex: 'mapTemplateTags',
			 renderer: function(value, metadata, record) {
				if (record != null && record.get('mapTemplateTags') != null) {
				    var mapTemplateTags = record.get('mapTemplateTags');
				    if (mapTemplateTags.length > 0) {
				        var rtn = '';
				        for (i=0; i < mapTemplateTags.length; i++) {
				            if (i > 0) {
				                rtn = rtn + ', ';
				            }
				            rtn = rtn + mapTemplateTags[i].name
				        }
				        return rtn;
				    }
				}
			 },
			 doSort: function(state) {
				var ds = this.up('grid').getStore();
				var field = this.getSortParam();
				ds.sort({
					 property: field,
					 direction: state,
					 sorterFn: function(v1, v2){
							v1 = (v1.get('mapTemplateTags')[0] ? v1.get('mapTemplateTags')[0].name : '');
							v2 = (v2.get('mapTemplateTags')[0] ? v2.get('mapTemplateTags')[0].name : '');
							return v1.localeCompare(v2);
					 }
				 });
			 }
		 }
         ]}],
          listeners: {
              afterrender: function(c){
                  c.el.dom.setAttribute('role', 'dialog');
              }
          }
        });
        
        return me.callParent(arguments);
    }   
});
