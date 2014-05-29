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
// Object only here to force scrolling on grid
Ext.define('Ssp.view.tools.map.CoursesGridPanel', {
	   extend: 'Ext.grid.Panel',
	    alias: 'widget.coursesgrid',
	    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	    controller: 'Ssp.controller.tool.map.CoursesGridController',
		inject: {
		        store: 'coursesStore',
				columnRendererUtils : 'columnRendererUtils'
		},

	    width: 290,
	    height: 500,
	    border: 0,
	    title: 'All Courses',
	    hideHeaders: false,
	    columnLines: false,
        scroll: true,
		itemId: 'coursesGrid',
	    initComponent: function(){
	        var me = this;
	        Ext.apply(me, {
	            			store: this.store,
	            	        scroll: true,
	            		    height: 500,
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
	                            text: 'Course',
	                            width: 75
	                        },
	                        {
	                             xtype: 'gridcolumn',
	                             dataIndex: 'title',
	                             text: 'Title',
	                             width: 120
	                         },
							 
							{
	                            xtype: 'gridcolumn',
	                             dataIndex: 'description',
	                             text: 'Course',
								 hidden:true,
								 hideable: false
	                         },
	                         {
	                           xtype: 'gridcolumn',
	                            dataIndex: 'minCreditHours',
	                            text: 'Cr.',
	                            width: 30
	                        },
	                        {
	                            xtype: 'gridcolumn',
	                             dataIndex: 'maxCreditHours',
	                             text: 'Max H',
	                             hidden:true,
								 hideable: false
	                         },
							   
	                         {
	                             xtype: 'gridcolumn',
	                              dataIndex: 'tags',
	                              renderer: me.columnRendererUtils.renderTags,                              
	                              text: 'Tags',
	                              width: 40
	                          },
							  {
					            xtype: 'gridcolumn',
					            width: 16,
					            flex:0,
								text: 'D',
					            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
					            		var isDev = record.isDev();
										if ( isDev == true) {
											metaData.tdAttr = 'data-qtip="D indicates course is a dev course."';
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
										dragText: 'Drag and drop course onto desired semester.'
							        }
						
							    }
	                   
	        });
	        
	        return me.callParent(arguments);
	    }
	    
	});
