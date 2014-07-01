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
Ext.define('Ssp.view.tools.profile.CurrentSchedule', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.profilecurrentschedule',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.CurrentScheduleViewController',
    width: '100%',
    height: '100%',
	minHeight: 615,
    autoScroll: true,
	title: 'Current Schedule',
    inject: {
        store: 'currentScheduleStore'
    },
    initComponent: function(){
        var me = this;
        Ext.applyIf(me, {
            //store: me.store,
            xtype: 'gridcolumn',
            columns: [
			{
                dataIndex: 'termCode',
                text: 'Term',
                flex: .10
            }, {
                dataIndex: 'formattedCourse',
                text: 'Course',
				flex: .14
            }, 
			{
                dataIndex: 'sectionNumber',
                text: 'Section',
				flex: .10
            },  
			{
            
                dataIndex: 'title',
                text: 'Course Title',
				flex: .25
            },
			{
                dataIndex: 'creditEarned',
                text: 'Cr Hrs',
				flex: .07
            },
			{
            
                dataIndex: 'facultyName',
                text: 'Instructor',
                flex: .20
            }, {
            
                dataIndex: 'statusCode',
                text: 'Status',
                flex: .07
            }, {
            
                dataIndex: 'audited',
                text: 'Audited',
                flex: .07
            }
			],
            viewConfig: {}
        });
        
        me.callParent(arguments);
    }
});