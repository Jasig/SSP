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
Ext.define('Ssp.view.tools.map.SemesterPanel', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.semesterpanel',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	inject:{
		appEventsController: 'appEventsController',
    	currentMapPlan: 'currentMapPlan',
		appEventsController: 'appEventsController',
		electiveStore: 'electivesAllUnpagedStore',
    	currentMapPlan: 'currentMapPlan',
		colorsStore: 'colorsStore', 
		colorsUnpagedStore: 'colorsUnpagedStore', 
		colorsAllStore: 'colorsAllStore', 
    	termsStore:'termsStore',
		colorsAllUnpagedStore: 'colorsAllUnpagedStore'    	
	},

	store: null,
    controller: 'Ssp.controller.tool.map.SemesterPanelViewController',
    columnLines: false,
    layout: {
                type: 'fit'
            },
	height: 200,
	width: 225,
	pastTerm: false,
 	enableDragAndDrop: true,
    columnLines: false,
 	hideHeaders: true, 	
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
			viewConfig: {
				copy: false,
		        plugins: {
		            ptype: 'gridviewdragdrop',
					ddGroup: 'ddGroupForCourses',
					dropGroup: 'coursesDDGroup',
					dragGroup: 'coursesDDGroup',
					pluginId: 'semesterviewdragdrop',
					enableDrag: me.editable || me.currentMapPlan.get('isTemplate'),
					enableDrop: me.editable || me.currentMapPlan.get('isTemplate')
		    }
		}, 
		invalidRecord: function(record) { 
            return record.get('isValidInTerm') === false || record.get('hasCorequisites')  === false || record.get('hasPrerequisites')  === false; 
        },	
        title: me.store.termName,
        scroll: true,
        itemId: me.store.termCode,
		columns: [
					{
					    xtype: 'gridcolumn',
					    width: 5,
					    height: 5,
					    flex:0,
					    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
					    	var isImportant = record.get('isImportant');
					    	var color = isImportant ? '#ff9900' : 'rgba(0,0,0,0.0)';
							metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;'
							if ( isImportant ) {
								metaData.tdAttr = 'data-qtip="Orange indicates course is important"';
							}
					     }		            
					},
					{
					    xtype: 'gridcolumn',
					    width: 5,
					    height: 5,
					    flex:0,
					    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
					    	var isTranscript = record.get('isTranscript');
					    	var duplicateOfTranscript = record.get('duplicateOfTranscript');
					    	var color = isTranscript ? '#ffff00' : 'rgba(0,0,0,0.0)';
					    	color = duplicateOfTranscript ? '#0000FF' : color;
							
							if ( isTranscript ) {
								if(!duplicateOfTranscript)
									metaData.tdAttr = 'data-qtip="Yellow indicates course is already on this student\'s transcript"';
								else{
									metaData.tdAttr = 'data-qtip="Blue indicates course is a duplicate of one on this student\'s transcript but in a different term"';
								}
							}
							metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;';
					     }		            
					},
					{
					    xtype: 'gridcolumn',
					    width: 5,
					    height: 5,
					    flex:0,
					    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
					    	var me=this;
					    	var elective = me.electiveStore.getById(record.get('electiveId'));
					    	var colorId = elective ? elective.get('color') : null;
					    	var color = colorId ? me.colorsAllUnpagedStore.getById(colorId) : null;
					    	var colorCode = color ? '#'+color.get('hexCode') : 'rgba(0,0,0,0.0)';
							metaData.style = 'background-color: '+colorCode+'; background-image: none; margin:2px 2px 2px 2px;'
							if ( elective ) {
								metaData.tdAttr = 'data-qtip="Course is an elective with code: ' + elective.get('code') + '"';
							}
							return elective;						
					     }		            
					},
					{
					    xtype: 'gridcolumn',
					    width: 5,
					    height: 5,
					    flex:0,
					    renderer: function(value, metaData, record, rowIndex, colIndex, store) {
					    		var isDev = record.isDev();
					        	var color = isDev ? '#ff0000' : 'rgba(0,0,0,0.0)';
								metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;';
								if ( isDev ) {
									metaData.tdAttr = 'data-qtip="Red indicates course is a developmental course"';
								}					
					     }		            
					}
					,
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
					width:160,
					renderer: function(value, metaData, record, rowIndex, colIndex, store) {
					    	var me=this;
						    if(me.invalidRecord(record)){
						    	metaData.style = 'font-style:italic;color:#FF0000';
								metaData.tdAttr = 'data-qtip="Concerns:' + record.get("invalidReasons") + '"';
							}
							return value;
					    }		
					},            
					{
					dataIndex: 'creditHours',
					xtype: 'gridcolumn',
					flex:0.5,
					width:25,
					renderer: function(value, metaData, record, rowIndex, colIndex, store) {
					    	var me=this;
							if(me.invalidRecord(record))
						    	metaData.style = 'font-style:italic;color:#FF0000';
							return value;
					    }		
					},{
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
					},{
					dataIndex: 'code',
					xtype: 'gridcolumn',
					hidden: true,
					hideable:false
					},{
					dataIndex: 'isDev',
					xtype: 'gridcolumn',
					hidden: true,
					hideable:false
					},{
					xtype: 'gridcolumn',
					flex:0.5,
					renderer: function(value, metaData, record, rowIndex, colIndex, store) {
						var me=this;
						var elective = me.electiveStore.getById(record.get('electiveId'))
						if((record.data.contactNotes != undefined && record.data.contactNotes.length > 0) ||
														(record.data.studentNotes != undefined && record.data.studentNotes.length > 0) ){
							if(record.data.contactNotes && record.data.contactNotes.length > 0)
								metaData.tdAttr = 'data-qtip="Contact Notes: ' + record.data.contactNotes;
							if(record.data.contactNotes && record.data.contactNotes.length > 0)
								metaData.tdAttr += ' Student Notes: ' + record.data.studentNotes 
							if(elective)
								metaData.tdAttr += ' Elective: ' + elective.get('name');
							
							metaData.tdAttr += '"';
							return '<img src="/ssp/images/' + Ssp.util.Constants.EDIT_COURSE_NOTE_NAME + '" />'
						}
						if(me.invalidRecord(record))
					    	metaData.style = 'font-style:italic;color:#FF0000';
						return "";
					 }		            
					}
					],
		
            tools: [
			{
                xtype: 'button',
                itemId: 'isImportantTermButton',
                width: 10,
                height: 20,
                cls: 'importantIconSmall',
                text:'',
                hidden: true,
                tooltip: 'This is an important term!'
                
            },	{
                    xtype: 'tbspacer',
                    flex: 0.8
                },{
                xtype: 'button',
                itemId: 'pastTermButton',
                width: 20,
                height: 20,
                cls: 'helpIconSmall',
                text:'',
                hidden: me.editable || me.currentMapPlan.get('isTemplate'),
                tooltip: 'This term is in the past and cannot be edited.'
                
            },{
                xtype: 'button',
                itemId: 'termNotesButton',
                width: 20,
                height: 20,
                cls: 'editPencilIcon',
                text:'',
                tooltip: 'Term Notes'
                
            },{
                xtype: 'button',
                itemId: 'deleteButton',
                width: 20,
                height: 20,                
                text:'',
                cls: 'deleteIcon',
                tooltip: 'Select a course and press this button to remove it from the term.'
            }],
            dockedItems: [{
                dock: 'bottom',
                xtype: 'toolbar',
                height: '25',
				itemId: "semesterBottomDock",
                items: [
                {
                    xtype: 'tbspacer',
                    flex: 0.5
                },{
                    text: 'Term Cr. Hrs:',
                    xtype: 'label'
                }, {
                    text: '0',
                    name: 'termCrHrs',
                    itemId: 'termCrHrs',
                    xtype: 'label',
					width: 20
                }
                ,
                 {
                    xtype: 'tbspacer',
                    flex: 0.5
                }]
            }]
        
        });
        
        return me.callParent(arguments);
    },
	getStore: function() {
		var me=this;
		return me.store;
	}
      
    
});