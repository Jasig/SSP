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
Ext.define('Ssp.view.tools.map.SemesterGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.semestergrid',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	controller: 'Ssp.controller.tool.map.SemesterGridViewController',
	inject:{
		appEventsController: 'appEventsController'
	},
    columnLines: false,
	hideHeaders: true,
 	width: 210,
    border: 0,
    
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            
            columns: [{
                dataIndex: 'title',
                xtype: 'gridcolumn',
				hidden: true,
				hideable: false
            }, 
			{
                dataIndex: 'formattedCourse',
                xtype: 'gridcolumn',
				width:145
            },
			{
                dataIndex: 'description',
                xtype: 'gridcolumn',
				hidden: true,
				hideable: false
            },
			{
                dataIndex: 'creditHours',
                xtype: 'gridcolumn',
				width:25
            },
            {
                dataIndex: 'maxCreditHours',
                xtype: 'gridcolumn',
				hidden: true,
				hideable:false
            }, 
			{
                dataIndex: 'minCreditHours',
                xtype: 'gridcolumn',
				hidden: true,
				hideable:false
            },
			{
                dataIndex: 'code',
                xtype: 'gridcolumn',
				hidden: true,
				hideable:false
            },
            {
                dataIndex: 'isDev',
                xtype: 'gridcolumn',
				hidden: true,
				hideable:false
            },
			
            {
                xtype: 'actioncolumn',
                width: 45,
				renderer: function(value, metaData, record, rowIndex, colIndex, store) {
							var me = this;
							if((record.data.contactNotes != undefined && record.data.contactNotes.length > 0) ||
								(record.data.studentNotes != undefined && record.data.studentNotes.length > 0) ){
								me.items[0].icon = Ssp.util.Constants.EDIT_COURSE_NOTE_ICON_PATH;
								return;
							}
				             me.items[0].icon = Ssp.util.Constants.ADD_COURSE_NOTE_ICON_PATH;
				         },
	                items: [{
	                    icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
	                    tooltip: 'Edit planItem',
						name: 'edit_button',
	                    handler: function(grid, rowIndex, colIndex){
							var me = this;
	                    	me.appEventsController.getApplication().fireEvent('onViewCourseNotes',{store:grid.getStore(),
	                    		rowIndex: rowIndex});
	                    },
	                    scope: me,

	                }, {
	                    icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	                    tooltip: 'Delete planItem',
	                    handler: function(grid, rowIndex, colIndex){
	                        me.getStore().removeAt(rowIndex);
	                    },
	                    scope: me
	                }]
	            }],
			viewConfig: {
			        plugins: {
			            ptype: 'gridviewdragdrop',
						ddGroup: 'ddGroupForCourses',
						dropGroup: 'coursesDDGroup',
						dragGroup: 'coursesDDGroup',
						pluginId: 'semesterviewdragdrop',
			    },
			},
        });
        
        return me.callParent(arguments);
    }
    
});