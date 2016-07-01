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
// Object only here to force scrolling on grid
Ext.define('Ssp.view.tools.map.CoursesGridPanel', {
	   extend: 'Ext.grid.Panel',
	    alias: 'widget.coursesgrid',
	    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	    controller: 'Ssp.controller.tool.map.CoursesGridController',
		inject: {
		        store: 'coursesStore',
				columnRendererUtils : 'columnRendererUtils',
				textStore: 'sspTextStore'
		},

	    width: 300,
	    height: '100%',
	    border: 0,
        autoScroll: true,
	    hideHeaders: false,
	    columnLines: false,
		itemId: 'coursesGrid',
	    initComponent: function(){
	        var me = this;
	        Ext.apply(me, {
	            			store: this.store,
						    width: 300,
	            		    height: '100%',
							flex: 20,
						    title: me.textStore.getValueByCode('ssp.label.map.all-course.title', 'All Courses'),
            				viewConfig: {
	            				loadMask: false
	            			},
	                        columns: [{
	                            text: 'code',
	                            dataIndex: 'code',
	                            xtype: 'gridcolumn',
	                            hidden: true,
	                            hideable: false
	                        },
	                        {
	                            xtype: 'gridcolumn',
	                            dataIndex: 'formattedCourse',
	                            text: me.textStore.getValueByCode('ssp.label.map.all-course.course', 'Course'),
	                            width: 75
	                        },
	                        {
	                             xtype: 'gridcolumn',
	                             dataIndex: 'title',
	                             text: me.textStore.getValueByCode('ssp.label.map.all-course.course-title', 'Title'),
	                             width: 120
	                         },
							 
							{
	                            xtype: 'gridcolumn',
	                             dataIndex: 'description',
	                             text: me.textStore.getValueByCode('ssp.label.map.all-course.description', 'Description'),
								 hidden:true,
								 hideable: false
	                         },
	                         {
	                           xtype: 'gridcolumn',
	                            dataIndex: 'minCreditHours',
	                            text: me.textStore.getValueByCode('ssp.label.map.all-course.min-credit-hours', 'Cr.'),
	                            width: 30
	                        },
	                        {
	                            xtype: 'gridcolumn',
	                             dataIndex: 'maxCreditHours',
	                             text: me.textStore.getValueByCode('ssp.label.map.all-course.max-credit-hours', 'Max H'),
	                             hidden:true,
								 hideable: false
	                         },
							   
	                         {
	                             xtype: 'gridcolumn',
	                              dataIndex: 'tags',
	                              renderer: me.columnRendererUtils.renderTags,                              
	                              text: me.textStore.getValueByCode('ssp.label.map.all-course.tags', 'Tags'),
	                              width: 40
	                          },
							  {
					            xtype: 'gridcolumn',
					            width: 16,
					            flex:0,
								text: me.textStore.getValueByCode('ssp.label.map.all-course.dev-course','D'),
					            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
					            		var isDev = record.isDev();
										if ( isDev == true) {
											metaData.tdAttr = 'data-qtip="' + me.textStore.getValueByCode('ssp.tooltip.map.all-course.dev-course','D indicates course is a dev course.') + '"';
											return "D"
										}
										return "";					
						         }		            
	                        }],
	                		viewConfig: {
									copy: true,
							        plugins: {
							            ptype: 'gridviewdragdrop',
										ddGroup: 'ddGroupForCourses',
										dragGroup: 'coursesDDGroup',
										dragText: me.textStore.getValueByCode('ssp.label.map.all-course.drag-text','Drag and drop course to desired location.')
							        }

							    }
	                   
	        });
	        
	        return me.callParent(arguments);
	    }
	    
	});
