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
Ext.define('Ssp.view.tools.map.CourseNotes', {
    extend: 'Ext.window.Window',
    alias: 'widget.coursenotes',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	inject: {
		electiveStore : 'electiveStore',
	    formUtils: 'formRendererUtils',

	},
    height: 390,
    width: 480,
    resizable: true,
    parentGrid: null,
    initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
					layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: 'Course Notes',
            items:[{
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
                bodyPadding: 10,
                autoScroll: true,
                itemId: 'coursenotesForm',
                fieldDefaults: {
                        msgTarget: 'side',
                        labelAlign: 'left',
                        labelWidth: 100
                    },
               
				    items: [
				   {
				        fieldLabel: 'Advisor/Coach Notes',
				        name: 'contactNotes',
				        allowBlank:true,
				        xtype: 'textareafield',
				        autoscroll: true,
				        flex:1
				    },{
				        fieldLabel: 'Student Notes',
				        name: 'studentNotes',
				        allowBlank:true,
				        xtype: 'textareafield',
				        flex:1,
				        autoscroll: true
				    },
				    {
				    	xtype: 'numberfield',
				        anchor: '100%',
				        maxValue: 99,
				        allowDecimals: false,
				        minValue: 0,
				        step: 1,
				        fieldLabel: 'Credit hours(over ride)',
				        name: 'creditHours',
				        allowBlank:true,
				        itemId: 'creditHours',
				        //flex:1,
				        
				    },
				    {
                    	name: 'isImportant',
						itemId: 'isImportant',
                    	inputValue: 'isImportant',
                    	xtype:'checkbox',
                    	padding: '0 0 0 105',
                    	labelSeparator: '',
                    	hideLabel: true,
                    	boxLabel: 'Mark As Important',
                    	fieldLabel: 'Mark As Important',
                    },
                    {
                        xtype: 'combobox',
                        itemId: 'electiveId',
						name: 'electiveId',
                        store: me.electiveStore,
                        fieldLabel: '',
                        emptyText: 'Elective',
                        valueField: 'id',
                        displayField: 'name',
                        mode: 'local',
                        typeAhead: true,
                        allowBlank: true,
                        width: 250
                    },
				    ]
				    ,
				    dockedItems: [{
		                xtype: 'toolbar',
		                dock: 'top',
		                items: [{
		                    xtype: 'button',
		                    itemId: 'saveButton',
		                    text: 'Save',
		                    listeners: {
		                    	click:function(){
		                    		me = this;
									var mapCourse = me.semesterStore.getAt(me.rowIndex);
									me.query('form')[0].getForm().updateRecord(mapCourse);
		                    		me.close();
		                    	},
		                    	scope: me
		                    }
		                    
		                }, '-', {
		                    xtype: 'button',
		                    itemId: 'cancelButton',
		                    text: 'Cancel',
		                    
		                    listeners: {
		                    	click:function(){
		                    		me = this;
		                    		me.close();
		                    	},
		                    	scope: me
		                    }
		                }]
		            
		            }]
		            }]
				});
		
		return me.callParent(arguments);
	}
});