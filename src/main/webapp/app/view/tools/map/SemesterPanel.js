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
Ext.define('Ssp.view.tools.map.SemesterPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.semesterpanel',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	inject:{
		appEventsController: 'appEventsController'
	},
    //controller: 'Ssp.controller.tool.map.SemesterPanelViewController',
    autoScroll: true,
    columnLines: false,
    
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            hideHeaders: true,
			height: '260',
		    width: '220',
			minHeight: '260',
			maxWidth: '220',
            tools: [{
                xtype: 'button',
                itemId: 'termNotesButton',
                width: 20,
                height: 20,
                hidden:true,
                hideable:false,
                cls: 'editPencilIcon',
                text:'',
                tooltip: 'Term Notes',
                listeners: {
                    click: function() {
                        var termNotesPopUp = Ext.create('Ssp.view.tools.map.TermNotes');
                        termNotesPopUp.center();
                        termNotesPopUp.show();
                    }
                  }
            }],
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
                dataIndex: 'maxCreditHours',
                xtype: 'gridcolumn',
				hidden: true,
				hideable:false
            }, 
			{
                dataIndex: 'minCreditHours',
                xtype: 'gridcolumn',
				width:25
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
                items: [{
                    icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
                    tooltip: 'Edit planItem',
                    handler: function(grid, rowIndex, colIndex){
						var me = this;
                    	me.appEventsController.getApplication().fireEvent('onViewCourseNotes',{store:grid.getStore(),
                    		rowIndex: rowIndex});
                    },
                    scope: me
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
            dockedItems: [{
                dock: 'bottom',
                xtype: 'toolbar',
                height: '25',
				itemId: "semesterBottomDock",
                items: [
                {
                    xtype: 'tbspacer',
                    flex: .5
                },{
                    text: 'Term Cr. Hrs:',
                    xtype: 'label'
                }, {
                    text: '',
                    name: 'termCrHrs',
                    itemId: 'termCrHrs',
                    xtype: 'label',
					width: 20
                }
                ,
                 {
                    xtype: 'tbspacer',
                    flex: .5
                }]
            }]
        
        });
        
        return me.callParent(arguments);
    }
    
});