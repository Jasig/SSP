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
		colorsStore: 'colorsStore',
		colorsUnpagedStore: 'colorsUnpagedStore', 
		colorsAllStore: 'colorsAllStore', 
    	termsStore:'termsStore',
		colorsAllUnpagedStore: 'colorsAllUnpagedStore',
		textStore: 'sspTextStore',
		columnRendererUtils: 'columnRendererUtils'
	},
	store: null,
    controller: 'Ssp.controller.tool.map.SemesterPanelViewController',
    pastTerm: false,
    columnLines: false,
    hideHeaders: true,
    enableDragAndDrop: true,
    height: 200,
    width: 225,
    layout: {
        type: 'fit'
    },
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            title: me.store.termName,
            scroll: true,
            itemId: me.store.termCode,
            invalidRecord: function(record) {
                return record.get('isValidInTerm') === false || record.get('hasCorequisites')  === false || record.get('hasPrerequisites')  === false;
            },
            plugins: [
                Ext.create('Ext.grid.plugin.CellEditing', {
                    clicksToEdit: 1,
                    listeners: {
                        beforeedit : function (editor, e, eOpts) {
                            if (!e.record.get('hasElectiveCourses') || me.currentMapPlan.get('isTemplate')) {
                                return false;
                            }
                        }
                    }
                })
            ],
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
		    columns: [{
                xtype: 'gridcolumn',
                width: 5,
                height: 5,
                flex:0,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    var isImportant = record.get('isImportant');
                    var color = isImportant ? '#ff9900' : 'rgba(0,0,0,0.0)';
                    metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;'
                    if ( isImportant ) {
                        metaData.tdAttr = 'data-qtip="' + me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.orange-transcript','Orange indicates course is important') + '"';
                    }
                }
            }, {
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
                        if (!duplicateOfTranscript) {
                            metaData.tdAttr = 'data-qtip="' + me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.yellow-transcript', 'Yellow indicates course is already on this student\'s transcript') + '"';
                        } else {
                            metaData.tdAttr = 'data-qtip="' + me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.blue-transcript','Blue indicates course is a duplicate of one on this student\'s transcript but in a different term') + '"';
                        }
                    }
                    metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;';
                }
            }, {
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
                        metaData.tdAttr = 'data-qtip="' +
                            me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.blue-transcript',
                                'Course is an elective with code: %ELECTIVE-CODE%',
                                {'%ELECTIVE-CODE%': elective.get('code')}) +
                            '"';
                    }
                    return elective;
                }
            }, {
                xtype: 'gridcolumn',
                width: 5,
                height: 5,
                flex:0,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    var isDev = record.isDev();
                    var color = isDev ? '#ff0000' : 'rgba(0,0,0,0.0)';
                    metaData.style = 'background-color: '+ color +'; background-image: none; margin:2px 2px 2px 2px;';
                    if ( isDev ) {
                        metaData.tdAttr = 'data-qtip="' + me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.red-transcript','Red indicates course is a developmental course') + '"';
                    }
                }
            }, {
                dataIndex: 'title',
                xtype: 'gridcolumn',
                hidden: true,
                hideable: false

            }, {
                dataIndex: 'formattedCourse',
                xtype: 'gridcolumn',
                flex:1,
                width:160,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    var me=this;
                    if (me.columns[colIndex].editor) {
                        if (record.get('planElectiveCourseElectives')) {
                            var store = new Ssp.store.PlanElectiveCourse();
                            store.loadData(record.get('planElectiveCourseElectives'));
                            me.columns[colIndex].editor.store = store;
                        }
                        value = value; //+ '&nbsp;&nbsp;&nbsp;' + me.columnRendererUtils.renderPhotoIcon(Ssp.util.Constants.ADD_TERM_NOTE_ICON_PATH);
                    }
                    if (me.invalidRecord(record)) {
                        metaData.style = 'font-style:italic;color:#FF0000';
                        metaData.tdAttr = 'data-qtip="' + me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.concerns','Concerns:') + record.get("invalidReasons") + '"';
                    }
                    return value;
                },
                editor: {
                    xtype: 'combo',
                    itemId: 'courseElectivesCombo',
                    editable: false,
                    mode: 'local',
                    queryMode: 'local',
                    displayField: 'formattedCourse',
                    valueField: 'formattedCourse',
                    listeners: {
                        change : function(comboBox, newValue, oldValue, e) {
                            var me = this;
                            var selectedModel = this.up('grid').getSelectionModel().getSelection()[0];
                            if (selectedModel && selectedModel.data.planElectiveCourseElectives) {
                                for (i = 0; i < selectedModel.data.planElectiveCourseElectives.length; i++) {
                                    if (selectedModel.data.planElectiveCourseElectives[i].formattedCourse == newValue ) {
                                        selectedModel.data.code = selectedModel.data.planElectiveCourseElectives[i].courseCode;
                                        selectedModel.data.description = selectedModel.data.planElectiveCourseElectives[i].courseDescription;
                                        selectedModel.data.title = selectedModel.data.planElectiveCourseElectives[i].courseTitle;
                                        selectedModel.data.creditHours = selectedModel.data.planElectiveCourseElectives[i].creditHours;
                                    }
                                }
                            }
                        }
                    }
                }
            }, {
                xtype: 'gridcolumn',
                width: 20,
                height: 5,
                flex:0,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    if (record.get('hasElectiveCourses')) {
                      // metaData.style = 'background-color: #0000FF; background-image: none; margin:2px 2px 2px 2px;';
                        return me.columnRendererUtils.renderPhotoIcon(Ssp.util.Constants.COURSE_ELECTIVES_ICON_PATH);
                    }
                }
            }, {
                dataIndex: 'creditHours',
                xtype: 'gridcolumn',
                flex:0.5,
                width:25,
                renderer: function(value, metaData, record, rowIndex, colIndex, store) {
                    var me=this;
                    if (me.invalidRecord(record)) {
                        metaData.style = 'font-style:italic;color:#FF0000';
                    }
                    return value;
                }
            }, {
                dataIndex: 'maxCreditHours',
                xtype: 'gridcolumn',
                hidden: true,
                hideable:false
            }, {
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
                    if ((record.data.contactNotes != undefined && record.data.contactNotes.length > 0) ||
                                                    (record.data.studentNotes != undefined && record.data.studentNotes.length > 0) ) {
                        var tooltip = '';
                        if (record.data.contactNotes && record.data.contactNotes.length > 0) {
                            tooltip += me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.contact-notes', 'Contact Notes: %CONTACT-NOTES%', {'%CONTACT-NOTES%': record.data.contactNotes});
                        }
                        if (record.data.contactNotes && record.data.contactNotes.length > 0) {
                            tooltip += '<br/>' + me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.student-notes', 'Student Notes: %STUDENT-NOTES%', {'%STUDENT-NOTES%': record.data.studentNotes});
                        }
                        if (elective) {
                            tooltip += '<br/>' + me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.elective-name', 'Elective: %ELECTIVE-NAME%', {'%ELECTIVE-NAME%': elective.get('name')});
                        }

                        metaData.tdAttr += 'data-qtip="' + tooltip + '"';

                        return '<img src="/ssp/images/' + Ssp.util.Constants.EDIT_COURSE_NOTE_NAME + '" />'
                    }
                    if (me.invalidRecord(record)) {
                        metaData.style = 'font-style:italic;color:#FF0000';
                    }
                    return "";
                }
            }],
            tools: [{
                xtype: 'button',
                itemId: 'isImportantTermButton',
                width: 10,
                height: 20,
                cls: 'importantIconSmall',
                text:'',
                hidden: true,
                tooltip: me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.is-important-term','This is an important term!')
            }, {
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
                tooltip: me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.past-term','This term is in the past and cannot be edited.')
            }, {
                xtype: 'button',
                itemId: 'termNotesButton',
                width: 20,
                height: 20,
                cls: 'editPencilIcon',
                text:'',
                tooltip: me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.term-notes','Term Notes')
            }, {
                xtype: 'button',
                itemId: 'deleteButton',
                width: 20,
                height: 20,                
                text:'',
                cls: 'deleteIcon',
                tooltip: me.textStore.getValueByCode('ssp.tooltip.map.semester-panel.delete-button','Select a course and press this button to remove it from the term.')
            }],
            dockedItems: [{
                dock: 'bottom',
                xtype: 'toolbar',
                height: '25',
				itemId: "semesterBottomDock",
                items: [{
                    xtype: 'tbspacer',
                    flex: 0.5
                }, {
                    text: 'Term Cr. Hrs:',
                    xtype: 'label'
                }, {
                    text: '0',
                    name: 'termCrHrs',
                    itemId: 'termCrHrs',
                    xtype: 'label',
					width: 20
                }, {
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