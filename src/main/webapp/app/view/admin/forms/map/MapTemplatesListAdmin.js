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
	controller : 'Ssp.controller.admin.map.MapAdminViewController',
	inject : {
		apiProperties : 'apiProperties',
		authenticatedPerson : 'authenticatedPerson',
		columnRendererUtils : 'columnRendererUtils',
		programsStore: 'programsStore',
		departmentsStore: 'departmentsStore',
		divisionsStore: 'divisionsStore',
		catalogYearsStore: 'catalogYearsStore',
		textStore: 'sspTextStore'
	},
	height : '100%',
	width : '100%',
	initComponent : function() {
		var me = this;
		Ext.apply(me, {
			autoScroll: true,
			selType: 'rowmodel',
			enableDragDrop: false,
			cls: 'configgrid',
			columns: [{
				text: 'Vis',
				width: 45,
				dataIndex: 'visibility',
				sortable: true,
				renderer: me.columnRendererUtils.renderTemplateVisibility
			}, {
				name: 'isTemplateActive',
				id: 'isTemplateActive',
				xtype: 'checkcolumn',
				text: 'Active',
				width: 55,
				dataIndex: 'objectStatus',
				inputValue: 'ACTIVE',
				uncheckedValue: 'INACTIVE',
				sortable: true,
				renderer: function (value) {
					var text = '<input type="checkbox" ';
					if (value == 'ACTIVE') {
						text += ' checked="checked"';
					}
					return text + '/>';
				}
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
				dataIndex: 'mapTemplateTag',
				renderer: function (value, metadata, record) {
					if (record != null && record.get('mapTemplateTag') != null) {
						return record.get('mapTemplateTag').name;
					}
				},
				doSort: function (state) {
					var ds = this.up('grid').getStore();
					var field = this.getSortParam();
					ds.sort({
						property: field,
						direction: state,
						sorterFn: function (v1, v2) {
							v1 = (v1.get('mapTemplateTag') ? v1.get('mapTemplateTag').name : '');
							v2 = (v2.get('mapTemplateTag') ? v2.get('mapTemplateTag').name : '');
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
				xtype: 'pagingtoolbar',
				dock: 'bottom',
				displayInfo: true,
				pageSize: me.apiProperties.getPagingSize()
			}, {
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
	}
});