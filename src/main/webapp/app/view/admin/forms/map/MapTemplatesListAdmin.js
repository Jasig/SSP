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

  


Ext.define(      
				'Ssp.view.admin.forms.map.MapTemplatesListAdmin',
				{
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
						columnRendererUtils : 'columnRendererUtils'
					},
					height : '100%',
					width : '100%',

					initComponent : function() {
						var me = this;
						Ext
								.apply(
										me,
										{
											viewConfig : {

											},
											autoScroll : true,
											selType : 'rowmodel',
											enableDragDrop : false,
											cls : 'configgrid',
											columns : [
													{
														text : 'Vis',
														width : 45,
														dataIndex : 'visibility',
														sortable : true,
														renderer : me.columnRendererUtils.renderTemplateVisibility
													},
													{
														name : 'isTemplateActive',
														id: 'isTemplateActive',
														xtype : 'checkcolumn',
														text : 'Active',
														width : 55,
														dataIndex : 'objectStatus',
													    inputValue: 'ACTIVE',	
													    uncheckedValue : 'INACTIVE',
														sortable : true,
														renderer: function(value) {
														    var text = '<input type="checkbox" ';
														    if(value == 'ACTIVE') {
														        text += ' checked="checked"';
														    }
														    return text + '/>';
														}
													},													
													{
														text : 'Template Title',
														width : 200,
														dataIndex : 'name',
														sortable : true
													},
													{
														text : 'Program Code',
														width : 200,
														dataIndex : 'programCode',
														sortable : true
													},
													{
														text : 'Date/ Time',
														width : 125,
														dataIndex : 'modifiedDate',
														sortable : true,
														renderer : Ext.util.Format
																.dateRenderer('Y-m-d g:i A')

													}, {
														text : 'Owner',
														width : 120,
														sortable : true,
														dataIndex : 'ownerName'

													} ],

											dockedItems : [
													{
														xtype : 'pagingtoolbar',
														dock : 'bottom',
														displayInfo : true,
														pageSize : me.apiProperties
																.getPagingSize()
													}
													]
										});

						return me.callParent(arguments);
					}
				});