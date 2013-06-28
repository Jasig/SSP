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
    	currentMapPlan: 'currentMapPlan',
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
			invalidRecord: function(record) { 
						            return record.get('validInTerm') === false || record.get('hasCorequisites')  === false || record.get('hasPrerequisites')  === false; 
						        },
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
							metaData.tdAttr = 'data-qtip="Orange indicates Course is Important"';
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
		            	var duplicatedOfTranscript = record.get('duplicateOfTranscript');
		            	var color = isTranscript ? '#ffff00' : 'rgba(0,0,0,0.0)';
		            	color = duplicatedOfTranscript ? '#0000FF' : color;
						
						if ( isTranscript ) {
							if(!duplicatedOfTranscript)
								metaData.tdAttr = 'data-qtip="Yellow indicates course is already on students\' transcript"';
							else{
								metaData.tdAttr = 'data-qtip="Blue indicates course is a duplicate of one on students\' transcript but in a different term."';
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
		            	var elective = me.electiveStore.getById(record.get('electiveId'))
		            	var colorId = elective ? elective.get('color') : null;
		            	var color = colorId ? me.colorsStore.getById(colorId) : null;
		            	var colorCode = color ? '#'+color.get('hexCode') : 'rgba(0,0,0,0.0)';
						metaData.style = 'background-color: '+colorCode+'; background-image: none; margin:2px 2px 2px 2px;'
						if ( elective ) {
							metaData.tdAttr = 'data-qtip="This is an elective. Elective code: ' + elective.get('code') + '"';
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
		            		var isDev = record.get('isDev');
			            	var color = isDev ? '#ff0000' : 'rgba(0,0,0,0.0)';
							metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;';
							if ( isDev ) {
								metaData.tdAttr = 'data-qtip="Red indicates course is a dev course."';
							}					
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
			viewConfig: {
					copy: true,
			        plugins: {
			            ptype: 'gridviewdragdrop',
						ddGroup: 'ddGroupForCourses',
						dropGroup: 'coursesDDGroup',
						dragGroup: 'coursesDDGroup',
						pluginId: 'semesterviewdragdrop',
						enableDrag: me.enableDragAndDrop || me.currentMapPlan.get('isTemplate'),
						enableDrop: me.enableDragAndDrop || me.currentMapPlan.get('isTemplate')
			    }
			}
        });
        
        return me.callParent(arguments);
    }
    
});