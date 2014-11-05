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
Ext.define('Ssp.view.tools.map.CourseDetails', {
    extend: 'Ext.window.Window',
    alias: 'widget.coursedetails',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    //controller: 'Ssp.controller.tool.map.CoursesGridController',
    inject: {
        columnRendererUtils: 'columnRendererUtils'
    },
    height: 400,
    width: 650,
    resizable: true,
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Course Details',
            items: [{
                xtype: 'form',
                flex: 1,
                border: 0,
                frame: false,
                layout: {
                    align: 'stretch',
                    type: 'vbox'
                },
                width: '100%',
                height: '100%',
                bodyPadding: 5,
                autoScroll: true,
                itemId: 'courseDetailsPopupWindow',
                items: [
                        
        			    	{
                            xtype: 'container',
                            defaultType: 'displayfield',
        			    	border: 1,
                            margin: '0 0 0 2',
                            padding: '0 0 0 5',
                            //flex: 1,
                            layout: {
                                align: 'stretch',
                                type: 'vbox'
                            },
                            items: [
                                    {
                                        xtype: 'fieldset',
                                        border: 0,
                                        title: '',
                                        defaultType: 'displayfield',
                                        margin: '0 0 0 2',
                                        padding: '0 0 0 5',
                                        layout: 'vbox',
                                        align: 'stretch',
                                        
                                        items: [
                    				               {
                                				        fieldLabel: 'Course',
														labelWidth:120,
                                				        name: 'formatted_course_title',
                                				        itemId: 'formatted_course_title'
                                				    },{
                                				        fieldLabel: 'Course Description',
														labelWidth:120,
                                				        name: 'description',
                                				        itemId: 'description',
														width: 500
                                				    }
                                				    ]},
                                				    {
                                                        xtype: 'fieldset',
                                                        border: 1,
                                                        title: '',
                                                        defaultType: 'displayfield',
                                                        margin: '0 0 0 2',
                                                        padding: '0 0 0 5',
                                                        layout: 'vbox',
                                                        align: 'stretch',
                                                        
                                                        items: [
                                        				    {
                                        				        fieldLabel: 'Max Credit Hours',
                                        				        name: 'maxCreditHours',
                                        				        itemId: 'maxCreditHours',
                                        				        width: 200
                                        				    },{
                                        				        fieldLabel: 'Min Credit Hours',
                                        				        name: 'minCreditHours',
                                        				        itemId: 'minCreditHours',
																 width: 200
                                        				    },
                                        				    {
                                        				        fieldLabel: 'Department',
                                        				        name: 'departmentCode',
                                        				        itemId: 'departmentCode'
                                        				        
                                        				    },{
                                        				        fieldLabel: 'Division',
                                        				        name: 'divisionCode',
                                        				        itemId: 'divisionCode'
                                        				    },{
                                        				        fieldLabel: 'Tags',
                                        				        name: 'tags',
                                        				        itemId: 'tags'
                                        				    }
                                				    
                                				    ]},
                                				    {
                                                        xtype: 'fieldset',
                                                        border: 1,
                                                        title: '',
                                                        defaultType: 'displayfield',
                                                        margin: '0 0 0 2',
                                                        padding: '0 0 0 5',
                                                        layout: 'vbox',
                                                        align: 'stretch',
                                                        
                                                        items: [
                                        				    {
                                        				        fieldLabel: 'Requisites',
                                        				        name: 'prereqs',
                                        				        itemId: 'prereqs',
                                        				        fieldStyle:'color:red'
                                        				    },{
                                        				    	fieldLabel:  '<a href="">Master Syllabus</a>',
                                                                name: 'mastersyllabus',
                                                                itemId: 'mastersyllabus',
                                                                labelSeparator : ''
                                        				    },
                                        				    {
                                        				        fieldLabel: '<a href="">Academic Link</a>',
                                                                name: 'academiclink',
                                                                itemId: 'academiclink',
                                                                labelSeparator : ''
                                        				    }
                                				    ]}
            			    ]
                    
                    }
                    ]
            }
            
            ]
            
        });
        
        return me.callParent(arguments);
    }
    
});