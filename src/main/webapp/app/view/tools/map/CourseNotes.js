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
Ext.define('Ssp.view.tools.map.CourseNotes', {
    extend: 'Ext.window.Window',
    alias: 'widget.coursenotes',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
	inject: {
		electiveStore : 'electivesAllUnpagedStore',
	    formUtils: 'formRendererUtils',
    	currentMapPlan: 'currentMapPlan',
    	textStore: 'sspTextStore'
	},
    height: 390,
    width: 480,
    resizable: true,
    parentGrid: null,
    enableFields: true,
    initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
					layout: {
                align: 'stretch',
                type: 'vbox'
            },
            title: me.textStore.getValueByCode('ssp.label.map.course-notes.title','Course Notes'),
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
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.course-notes.contact-notes','Advisor/Coach Notes'),
				        name: 'contactNotes',
				        allowBlank:true,
				        xtype: 'textareafield',
				        itemId: 'contactNotes',
				        autoscroll: true,
				        disabled: !me.enableFields && !me.currentMapPlan.get('isTemplate'),
				        flex:1,
				        maxLength: 4000,
				        enforceMaxLength: true
				    },{
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.course-notes.student-notes','Student Notes'),
				        name: 'studentNotes',
				        allowBlank:true,
				        itemId: 'studentNotes',
				        xtype: 'textareafield',
				        flex:1,
				        disabled: !me.enableFields && !me.currentMapPlan.get('isTemplate'),
				        autoscroll: true,
				        maxLength: 4000,
				        enforceMaxLength: true
				    },
				    {
				    	xtype: 'numberfield',
				        anchor: '100%',
				        maxValue: 99,
				        allowDecimals: true,
				        minValue: 0,
				        step: 1,
				        fieldLabel: me.textStore.getValueByCode('ssp.label.map.course-notes.credit-hours','Credit hours (over ride)'),
				        name: 'creditHours',
				        allowBlank:true,
				        itemId: 'creditHours',
				        disabled: !me.enableFields && !me.currentMapPlan.get('isTemplate')
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
                    	boxLabel: me.textStore.getValueByCode('ssp.label.map.course-notes.is-important','Mark As Important'),
                    	fieldLabel: me.textStore.getValueByCode('ssp.label.map.course-notes.is-important','Mark As Important'),
				        disabled: !me.enableFields && !me.currentMapPlan.get('isTemplate')

                    },
					{
				    xtype: 'container',
				    border: 0,
				    title: '',
				    margin: '0 0 0 0',
				    padding: '5 0 0 5',
				    layout: 'hbox',
				    defaults: {
				        align: 'stretch'
				    },
				    items: [
                    {
                        xtype: 'combobox',
                        itemId: 'electiveId',
						name: 'electiveId',
                        store: me.electiveStore,
                        fieldLabel: '',
                        emptyText: me.textStore.getValueByCode('ssp.empty-text.map.course-notes.elective','Elective'),
                        valueField: 'id',
                        displayField: 'name',
                        mode: 'local',
                        typeAhead: true,
                        forceSelection: true,
                        allowBlank: true,
                        queryMode: 'local',
                        width: 400,
				        disabled: !me.enableFields && !me.currentMapPlan.get('isTemplate'),
				        associativeField: 'id'
                    },{
				        tooltip: me.textStore.getValueByCode('ssp.tooltip.map.course-notes.reset','Reset'),
				        text: '',
				        width: 23,
				        height: 25,
				        name: 'electiveCancel',
				        cls: 'mapClearSearchIcon',
				        xtype: 'button',
				        itemId: 'electiveCancel',
						listeners: {
		                    	click:function(){
		                    		me = this;
									Ext.ComponentQuery.query('#electiveId')[0].reset();
		                    	},
		                    	scope: me
		               }
				    }
				    ]}
					],
				    dockedItems: [{
		                xtype: 'toolbar',
		                dock: 'top',
		                items: [{
		                    xtype: 'button',
		                    itemId: 'saveButton',
		                    text: me.textStore.getValueByCode('ssp.label.save-button','Save'),
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
		                    text: me.textStore.getValueByCode('ssp.label.cancel-button','Cancel'),
		                    
		                    listeners: {
		                    	click:function(){
		                    		me = this;
		                    		me.close();
		                    	},
		                    	scope: me
		                    }
		                }]
		            
		            }]
		            }],
                  listeners: {
                      afterrender: function(c){
                          c.el.dom.setAttribute('role', 'dialog');
                      }
                  }
				});
		
		return me.callParent(arguments);
	}
});