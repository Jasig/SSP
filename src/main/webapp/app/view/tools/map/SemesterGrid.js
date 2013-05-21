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
		appEventsController: 'appEventsController',
		electiveStore: 'electiveStore',
		colorsStore: 'colorsStore'
	},
    columnLines: false,
	hideHeaders: true,
 	width: 210,
    border: 0,
	enableDragAndDrop: true,
    initComponent: function(){
        var me = this;       
        Ext.apply(me, {
            columns: [
            	{
		            xtype: 'gridcolumn',
		            width: 10,
		            height: 5,
		            toolTip:'Orange indicates Course is Important',
		            flex:0,
		            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
		            	var isImportant = record.get('isImportant');
		            	var color = isImportant ? '#ff9900' : 'rgba(0,0,0,0.0)';
						metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;'
			         },		            
		        },
            	{
		            xtype: 'gridcolumn',
		            width: 10,
		            height: 5,
		            toolTip:'Yellow indicates course is already on students\' transcript',
		            flex:0,
		            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
		            	var isTranscript = record.get('isTranscript');
		            	var color = isTranscript ? '#ffff00' : 'rgba(0,0,0,0.0)';
						metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;';
			         }		            
		        },
            	{
		            xtype: 'gridcolumn',
		            width: 10,
		            height: 5,
		            flex:0,
		            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
		            	var me=this;
		            	var elective = me.electiveStore.getById(record.get('electiveId'))
		            	var colorId = elective ? elective.get('color') : null;
		            	var color = colorId ? me.colorsStore.getById(colorId) : null;
		            	var colorCode = color ? '#'+color.get('hexCode') : 'rgba(0,0,0,0.0)';
						metaData.style = 'background-color: '+colorCode+'; background-image: none; margin:2px 2px 2px 2px;'
			         }		            
		        },
		        {
                dataIndex: 'title',
                xtype: 'gridcolumn',
				hidden: true,
				hideable: false
            }, 
			{
                dataIndex: 'formattedCourse',
                xtype: 'gridcolumn',
		        flex:1,
				width:145
            },
        	{
	            xtype: 'gridcolumn',
	            flex:0.5,
	            renderer: function(value, metaData, record, rowIndex, colIndex, store) {
	            	var me=this;
	            	var elective = me.electiveStore.getById(record.get('electiveId'))
	            	value = elective ? elective.get('code') : '';
	            	return value;
		         }		            
	        },            
			{
                dataIndex: 'creditHours',
                xtype: 'gridcolumn',
	            flex:0.5,
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
       ],
			viewConfig: {
			        plugins: {
			            ptype: 'gridviewdragdrop',
						ddGroup: 'ddGroupForCourses',
						dropGroup: 'coursesDDGroup',
						dragGroup: 'coursesDDGroup',
						pluginId: 'semesterviewdragdrop',
						enableDrag: me.enableDragAndDrop,
						enableDrop: me.enableDragAndDrop
			    },
			},
        });
        
        return me.callParent(arguments);
    }
    
});