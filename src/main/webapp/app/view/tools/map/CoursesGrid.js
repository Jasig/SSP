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
Ext.define('Ssp.view.tools.map.CoursesGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.coursesgrid',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.CoursesGridController',
	inject: {
	        store: 'coursesStore'
	},

    width: 290,
    border: 0,
    title: 'All Courses',
    hideHeaders: false,
    columnLines: false,
	itemId: 'coursesGrid',
    
    
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            			store: this.store,
						
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
                            width: 80
                        },
                        {
                             xtype: 'gridcolumn',
                             dataIndex: 'title',
                             text: 'Title',
                             width: 80
                         },
						 
						{
                            xtype: 'gridcolumn',
                             dataIndex: 'description',
                             text: 'Course',
							 hidden:true,
							 hideable: false,
                         },
                         {
                           xtype: 'gridcolumn',
                            dataIndex: 'minCreditHours',
                            text: 'Credit Hours',
                            width: 40
                        },
                        {
                            xtype: 'gridcolumn',
                             dataIndex: 'maxCreditHours',
                             text: 'Max H',
                             hidden:true,
							 hideable: false,
                         },
                         {
                             xtype: 'gridcolumn',
                              dataIndex: 'tags',
                              text: 'Tags',
                              width: 40
                          },
                        ],
                		viewConfig: {
								copy: true,
						        plugins: {
						            ptype: 'gridviewdragdrop',
									ddGroup: 'ddGroupForCourses',
									dragGroup: 'coursesDDGroup',
									dragText: 'Drag and drop course onto desired semester.'
						        },
					
						    },
                   
        });
        
        return me.callParent(arguments);
    }
    
});
