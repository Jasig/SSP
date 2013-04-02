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
    //controller: 'Ssp.controller.tool.profile.ProfileToolViewController',
    width: 290,
    minHeight: 500,
    height: '100%',
    border: 0,
    title: 'All Courses',
    autoScroll: true,
    hideHeaders: true,
    columnLines: false,
    listeners: {
    itemdblclick: function() {
        var courseDetailsPopUp = Ext.create('Ssp.view.tools.map.CourseDetails');
        courseDetailsPopUp.center();
        courseDetailsPopUp.show();
        }
    },
    
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            
                        columns: [{
                            text: 'course',
                            dataIndex: 'course',
                            xtype: 'gridcolumn'
                        },
                         {
                           xtype: 'gridcolumn',
                            dataIndex: 'crHrs',
                            text: 'crHrs',
                            width: 50
                        },
                        {
                           xtype: 'gridcolumn',
                            dataIndex: 'type',
                            text: 'type',
                            width: 50
                            
                        }
                        ],

                viewConfig: {
                    //markDirty:false
                }
                   
        });
        
        return me.callParent(arguments);
    }
    
});
