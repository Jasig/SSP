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
Ext.define('Ssp.view.tools.map.StudentCourseHistory', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.studentcoursehistory',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.map.StudentCourseHistoryViewController',
    inject: {
        store: 'studentCourseHistoryStore'
    },
	width: '100%',
	height: '100%',
	minHeight: 615,
    autoScroll: true,
    initComponent: function(){
        var me = this;
        
        Ext.applyIf(me, {
        	queryMode:'local',
            store: me.store,
            columns: [{
                xtype: 'gridcolumn',
                dataIndex: 'termCode',
                text: 'Term',
                flex: 0.10
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'formattedCourse',
                text: 'Course',
                flex: 0.15
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'title',
                text: 'Course Title',
                flex: 0.30
            }, {
                xtype: 'numbercolumn',
                dataIndex: 'creditEarned',
                text: 'Cr Hrs',
                format: '0.00',
                flex: 0.10
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'grade',
                text: 'Grade',
                sortable: 'false',
                flex: 0.20
            }, {
                xtype: 'gridcolumn',
                dataIndex: 'creditType',
                text: 'Credit Type',
                flex: 0.20
            }],
            viewConfig: {
                markDirty: false
            }
        });
        
        me.callParent(arguments);
    }
});
