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
    //controller: 'Ssp.controller.tool.profile.ProfileToolViewController',
    minHeight: '200',
    minWidth: '200',
    autoHeight: true,
    autoScroll: true,
    columnLines: false,
    
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            title: 'Semester',
            hideHeaders: true,
            tools: [{
                xtype: 'button',
                itemId: 'termNotesButton',
                width: 20,
                height: 20,
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
                text: '',
                dataIndex: 'planItem',
                xtype: 'gridcolumn'
            }, 
            {
                text: '',
                dataIndex: 'crHrs',
                xtype: 'gridcolumn'
            }, 
            {
                xtype: 'actioncolumn',
                width: 65,
                items: [{
                    icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
                    tooltip: 'Edit planItem',
                    handler: function(grid, rowIndex, colIndex){
                        //goto CourseNotes.js
                        
                    },
                    
                    scope: me
                }, {
                    icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
                    tooltip: 'Delete planItem',
                    handler: function(grid, rowIndex, colIndex){
                        
                    },
                    scope: me
                }]
            }],
            dockedItems: [{
                dock: 'bottom',
                xtype: 'toolbar',
                height: '25',
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
                    xtype: 'label'
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
