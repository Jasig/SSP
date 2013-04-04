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
Ext.define('Ssp.view.tools.profile.DroppedCourses', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.profiledroppedcourses',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.CurrentDroppedScheduleViewController',
    width: '100%',
    height: '100%',
    autoScroll: true,
	title: 'Dropped Courses',
    inject: {
        store: 'currentDroppedScheduleStore'
    },
    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            //store: me.store,
            xtype: 'gridcolumn',
            columns: [{
                dataIndex: 'formattedCourse',
                text: 'Course',
				flex: 1
            }, {
                dataIndex: 'creditEarned',
                text: 'Cr Hrs',
				flex: 1
            }, {
            
                dataIndex: 'title',
                text: 'Course Title',
				flex: 1
            },
			{
                dataIndex: 'termCode',
                text: 'Term',
                flex: 1
            }, {
            
                dataIndex: 'facultyName',
                text: 'Instructor',
                flex: 1
            }, {
            
                dataIndex: 'audited',
                text: 'Audited',
                flex: 1
            }
			],
            viewConfig: {}
        });
        
        me.callParent(arguments);
    }
});