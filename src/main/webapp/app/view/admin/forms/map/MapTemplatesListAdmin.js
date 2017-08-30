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
Ext.define('Ssp.view.admin.forms.map.MapTemplatesListAdmin', {
	extend : 'Ext.grid.Panel',
	id: 'templatePanel',
	name: 'templatePanel',
	itemid: 'templatePanel',
	alias : 'widget.maptemplateslistadmin',
	title : 'MAP Templates Admin (Select checkboxes to Activate/DeActivate)',
	mixins : [ 'Deft.mixin.Injectable',
			'Deft.mixin.Controllable' ],
	controller : 'Ssp.controller.admin.map.MapTemplateListAdminViewController',
	inject : {
		apiProperties : 'apiProperties',
		authenticatedPerson : 'authenticatedPerson',
		columnRendererUtils : 'columnRendererUtils',
		programsStore: 'programsStore',
		departmentsStore: 'departmentsStore',
		divisionsStore: 'divisionsStore',
		catalogYearsStore: 'catalogYearsStore',
		textStore: 'sspTextStore',
		store: 'planTemplatesSummaryStore'
	},
	height : '100%',
	width : '100%',
	initComponent : function() {
		var me = this;
        var cellEditor = Ext.create('Ext.grid.plugin.CellEditing', {
            clicksToEdit: 1
        });
		Ext.apply(me, {
			autoScroll: true,
			selType: 'rowmodel',
            plugins: cellEditor,
			enableDragDrop: false,
			cls: 'configgrid',
			columns: [{
				text: 'Vis',
				width: 45,
				dataIndex: 'visibility',
				sortable: true,
				renderer: me.columnRendererUtils.renderTemplateVisibility
			}, {
                header: 'Active',
                dataIndex: 'objectStatus',
                id: 'objectStatusColumn',
                name: 'objectStatusColumn',
                xtype: 'gridcolumn',
                required: true,
                width: 80,
                editable: true,
                editor: {
                    xtype: 'combo',
    			    name: 'objectStatusCombo',
	    		    id: 'objectStatusCombo',
                    store: Ext.create('Ext.data.Store', {
                        fields: ['value', 'name'],
                            data : [
                                    {"value":"ACTIVE","name":"ACTIVE"},
                                    {"value":"INACTIVE","name":"INACTIVE"},
                                    {"value":"OBSOLETE","name":"OBSOLETE"},
                                    {"value":"DELETED","name":"DELETED"}
                                ]
                    }),
                    valueField: 'value',
                    displayField: 'name',
                    forceSelection: false,
                    editable: true,
                    allowBlank: false,
                    listeners: {
                        change : function(comboBox, newValue, oldValue, e) {
                            var plan = this.up('grid').getSelectionModel().getSelection()[0];
                            var mapPlanService = Ext.create('Ssp.service.MapPlanService');

                            var callbacks = new Object();
                            if (newValue=='ACTIVE') {
                                callbacks.success = me.onGetTemplateACTIVESuccess;
                            } else if (newValue=='INACTIVE') {
                                callbacks.success = me.onGetTemplateINACTIVESuccess;
                            } else if (newValue=='OBSOLETE') {
                                callbacks.success = me.onGetTemplateOBSOLETESuccess;
                            } else if (newValue=='DELETED') {
                                callbacks.success = me.onGetTemplateDELETEDSuccess;
                            }
                            callbacks.failure = me.onLoadCompleteFailure;
                            callbacks.scope = me;

                            mapPlanService.getTemplate(plan.get('id'), callbacks);
                        }
                    }

                },
                sortOrder: 40
			}, {
				text: 'Template Title',
				width: 200,
				dataIndex: 'name',
				sortable: true
			}, {
				text: 'Program Name',
				width: 200,
				dataIndex: 'programCode',
				id: 'programCodeNameColumn',
				name: 'programCodeNameColumn',
				sortable: true,
				renderer: function (value) {
					var text = '';
					var index = this.programsStore.findExact('code', value);
					if (index != -1) {
						var record = this.programsStore.getAt(index);
						text = record.get('name');
					}
					return text;
				}
			}, {
				text: 'Department',
				width: 200,
				sortable: true,
				dataIndex: 'departmentCode',
				renderer: function (value) {
					return me.columnRendererUtils.renderNameForCodeInStore(value, me.departmentsStore);
				}

			}, {
				text: 'Division',
				width: 200,
				sortable: true,
				dataIndex: 'divisionCode',
				renderer: function (value) {
					return me.columnRendererUtils.renderNameForCodeInStore(value, me.divisionsStore);
				}

			}, {
				text: 'Catalog Year',
				width: 85,
				sortable: true,
				dataIndex: 'catalogYearCode',
				renderer: function (value) {
					return me.columnRendererUtils.renderNameForCodeInStore(value, me.catalogYearsStore);
				}
			}, {
				text: (me.textStore.getValueByCode('ssp.label.map-template-tag') ? me.textStore.getValueByCode('ssp.label.map-template-tag') : "Template Tag"),
				width: 200,
				sortable: true,
				dataIndex: 'mapTemplateTags',
				renderer: function (value, metadata, record) {
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
				doSort: function (state) {
					var ds = this.up('grid').getStore();
					var field = this.getSortParam();
					ds.sort({
						property: field,
						direction: state,
						sorterFn: function (v1, v2) {
							v1 = (v1.get('mapTemplateTags')[0] ? v1.get('mapTemplateTags')[0].name : '');
							v2 = (v2.get('mapTemplateTags')[0] ? v2.get('mapTemplateTags')[0].name : '');
							return v1.localeCompare(v2);
						}
					});
				}
			}, {
				text: 'Date/ Time',
				width: 125,
				dataIndex: 'modifiedDate',
				sortable: true,
				renderer: Ext.util.Format.dateRenderer('Y-m-d g:i A')
			}, {
				text: 'Owner',
				width: 120,
				sortable: true,
				dataIndex: 'ownerName'

			}],
			dockedItems: [{
//				xtype: 'pagingtoolbar',
//				dock: 'bottom',
//				displayInfo: true,
//				pageSize: me.apiProperties.getPagingSize()
//			}, {
				xtype: 'toolbar',
				items: [{
					text: 'Edit Template Elective Courses',
                    tooltip:  me.textStore.getValueByCode('ssp.tooltip.show-template-electives-button','Edit Template Elective Courses'),
                    iconCls: 'icon-edit',
					xtype: 'button',
					hidden: !me.authenticatedPerson.hasAccess('TEMPLATE_TOOL'),
					action: 'editElectiveCourses',
					itemId: 'editElectiveCoursesButton'
				}]
			}, {
				xtype: 'toolbar',
				dock: 'top',
				items: [{
					xtype: 'label',
					text: 'Click on a Template to Edit Elective Courses.'
				}]
			}]
		});

		return me.callParent(arguments);
	},

    onGetTemplateACTIVESuccess: function(response, t) {
        return t.onGetTemplateSuccess(response, t, 'ACTIVE');
	},
    onGetTemplateINACTIVESuccess: function(response, t) {
        return t.onGetTemplateSuccess(response, t, 'INACTIVE');
	},
    onGetTemplateOBSOLETESuccess: function(response, t) {
        return t.onGetTemplateSuccess(response, t, 'OBSOLETE');
	},
    onGetTemplateDELETEDSuccess: function(response, t) {
        return t.onGetTemplateSuccess(response, t, 'DELETED');
	},

    onGetTemplateSuccess: function(response, t, objectStatus) {
        var me = t;
        var callbacks = new Object();
        callbacks.success = me.onLoadCompleteSuccess;
        callbacks.failure = me.onLoadCompleteFailure;
        callbacks.scope = me;
        var mapPlanService = Ext.create('Ssp.service.MapPlanService');
        var plan = Ext.create('Ssp.model.tool.map.Plan');
        var grid = Ext.getCmp("templatePanel");
        var planfromResponse = Ext.decode(response.responseText);
        planfromResponse.objectStatus = objectStatus;

        plan.loadFromServer(planfromResponse);
        plan.set('isTemplate', true);

        var planCourses = plan.get('planCourses');
        for(var k = 0; k < planCourses.length; k++){
            course = planCourses[k];
            course.id=null;
        }

        var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('templatePlan') );

        // update
        me.apiProperties.makeRequest({
            url: url+'/'+ plan.get('id'),
            method: 'PUT',
            jsonData: plan.getSimpleJsonData(),
            successFunc: callbacks.success,
            failureFunc: callbacks.failure,
            scope: me
        });
    },

    onLoadCompleteSuccess: function(response, t) {
     	var grid = Ext.getCmp("templatePanel");
     	var params = {};
     	var me = this;

        me.setParam(params, Ext.getCmp('program'), 'programCode');
        me.setParam(params, Ext.getCmp('department'), 'departmentCode');
        me.setParam(params, Ext.getCmp('division'), 'divisionCode');
        me.setParam(params, Ext.getCmp('templateNameFilter'), 'name');
        me.setParam(params, Ext.getCmp('catalogYear'), 'catalogYearCode');
        me.setParam(params, Ext.getCmp('mapTemplateTag'), 'objectStatus');
        me.setParam(params, Ext.getCmp('objectStatusFilter'), 'objectStatusFilter');

        grid.store.on('load', me.onLoadComplete, this, {single: true});
        grid.store.load({params: params});

    },

    onLoadComplete: function(){
    },

    setParam: function(params, field, fieldName){
    	if(field.getValue() && field.getValue().length > 0)
    		params[fieldName] = field.getValue();
    },

    onLoadCompleteFailure: function(response, t) {
     	alert('failure: ' + response + ' '+ t);
    }
});