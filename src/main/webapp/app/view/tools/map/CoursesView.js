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
Ext.define('Ssp.view.tools.map.CoursesView', {
    extend: 'Ext.form.Panel',
    alias: 'widget.coursesview',
    mixins: ['Deft.mixin.Injectable', 'Deft.mixin.Controllable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		columnRendererUtils : 'columnRendererUtils',
		programs: 'programsStore',
		tags: 'tagsStore',
		departments: 'departmentsStore',
		divisions: 'divisionsStore',
    },
    controller: 'Ssp.controller.tool.map.CoursesViewController',
    
    width: '100%',
    height: '100%',
    initComponent: function(){
        var me = this;
        Ext.apply(me, {
            layout: {
                type: 'fit'
            },
            padding: 0,
            preventHeader: true,
            items: [{
                xtype: 'fieldcontainer',
                fieldLabel: '',
                layout: 'vbox',
                margin: '0 0 0 0',
                padding: '0 0 0 0',
                width: '100%',
                height: '100%',
                items: [
                    {
				    xtype: 'fieldset',
				    border: 0,
				    title: '',
				    margin: '0 0 0 0',
				    padding: '5 0 5 5',
				    layout: 'hbox',
				    defaults: {
				        anchor: '100%'
				    },
				    items: [{
				        xtype: 'combobox',
				        name: 'program',
				        store: me.programs,
				        fieldLabel: '',
				        emptyText: 'Filter by Program',
				        valueField: 'code',
				        displayField: 'name',
				        mode: 'local',
				        queryMode: 'local',
				        allowBlank: true,
				        itemId: 'program',
				        width: 260,
				    }, {
				        tooltip: 'Reset to All Programs',
				        text: '',
				        width: 30,
				        height: 25,
				        name: 'programCancel',
				        cls: 'mapClearSearchIcon',
				        xtype: 'button',
				        itemId: 'programCancel',
				    }]
				},

                {
				    xtype: 'fieldset',
				    border: 0,
				    title: '',
				    margin: '0 0 0 0',
				    padding: '5 0 5 5',
				    layout: 'hbox',
				    defaults: {
				        anchor: '100%'
				    },
				    items: [{
				        xtype: 'combobox',
				        name: 'tag',
				        fieldLabel: '',
				        store: me.tags,
				        emptyText: 'Filter by Tag',
				        valueField: 'code',
				        displayField: 'name',
				        mode: 'local',
				        queryMode: 'local',
				        allowBlank: true,
				        itemId: 'tag',
				        width: 260,
				    }, {
				        tooltip: 'Reset to All Tags',
				        text: '',
				        width: 30,
				        height: 25,
				        name: 'tagCancel',
				        cls: 'mapClearSearchIcon',
				        xtype: 'button',
				        itemId: 'tagCancel',
				    }]
				},
                {
				    xtype: 'fieldset',
				    border: 0,
				    title: '',
				    margin: '0 0 0 0',
				    padding: '5 0 5 5',
				    layout: 'hbox',
				    defaults: {
				        anchor: '100%'
				    },
				    items: [{
				        xtype: 'combobox',
				        name: 'term',
				        fieldLabel: '',
				        emptyText: 'Filter by Term',
				        valueField: 'code',
				        displayField: 'name',
				        mode: 'local',
				        queryMode: 'local',
				        allowBlank: true,
				        itemId: 'term',
				        width: 260,
				    }, {
				        tooltip: 'Reset to All Terms',
				        text: '',
				        width: 30,
				        height: 25,
				        name: 'tagCancel',
				        cls: 'mapClearSearchIcon',
				        xtype: 'button',
				        itemId: 'termCancel',
				    }]
				},
                {
				    xtype: 'fieldset',
				    border: 0,
				    title: '',
				    margin: '0 0 0 0',
				    padding: '5 0 5 5',
				    layout: 'hbox',
				    hidden:true,
				    defaults: {
				        anchor: '100%'
				    },
				    items: [{
				        xtype: 'combobox',
				        name: 'department',
				        store: me.departments,
				        fieldLabel: '',
				        emptyText: 'Filter by Department',
				        valueField: 'code',
				        displayField: 'name',
				        mode: 'local',
				        queryMode: 'local',
				        allowBlank: true,
				        itemId: 'department',
				        width: 260,
				    }, {
				        tooltip: 'Reset to All Departments',
				        text: '',
				        width: 30,
				        height: 25,
				        name: 'departmentCancel',
				        cls: 'mapClearSearchIcon',
				        xtype: 'button',
				        itemId: 'departmentCancel',
				    }]
				},
                {
				    xtype: 'fieldset',
				    border: 0,
				    title: '',
				    margin: '0 0 0 0',
				    padding: '5 0 5 5',
				    layout: 'hbox',
				    hidden: true,
				    defaults: {
				        anchor: '100%'
				    },
				    items: [{
				        xtype: 'combobox',
				        name: 'division',
				        store: me.divisions,
				        fieldLabel: '',
				        emptyText: 'Filter by Division',
				        valueField: 'code',
				        displayField: 'name',
				        mode: 'local',
				        queryMode: 'local',
				        allowBlank: true,
				        itemId: 'division',
				        width: 260,
				    }, {
				        tooltip: 'Reset to All Division',
				        text: '',
				        width: 30,
				        height: 25,
				        name: 'divisionCancel',
				        cls: 'mapClearSearchIcon',
				        xtype: 'button',
				        itemId: 'divisionCancel',
				    }]
				},
				 {
                    xtype: 'container',
                    autoEl: 'hr'
                },
                 {
                    xtype: 'fieldset',
                    border: 0,
                    title: '',
                    defaultType: 'textfield',
                    margin: '0 0 0 0',
					padding: '0 0 5 5',
                    layout: 'hbox',
                    defaults: {
                        anchor: '100%'
                    },
                    
                    items: [
					{
						 xtype: 'tbspacer',
                       width: 75
					},
					
					{
                       fieldLabel: 'Filter By',
						xtype:"textfield",
                        name: 'filterBy',
                        itemId: 'filterBy',
                        maxLength: 50,
                        allowBlank:true,
						labelWidth:50,
						enableKeyEvents:true,
						listeners:{
							keyup: function(textField, e, eOpts) {
								var me = this;
		                        var searchString = textField.getValue().trim();
		                        var coursesGrid = me.findParentByType('coursesview').query('#coursesGrid')[0];
								coursesGrid.getStore().filterBy(getFilterRecord(['title', 'formattedCourse'], searchString)); 
		                    }
						},
						
                    }, {
                        tooltip: 'Clear Filter',
                        text: '',
                        width: 30,
                        height: 25,
                        cls: 'mapClearSearchIcon',
                        xtype: 'button',
                        itemId: 'cancelFilterButton',
						listeners:{
							click : function(cancelButton, t, opts) {
								var me = this;
								var parent =  cancelButton.findParentByType("fieldset");
								var filterBy = parent.getComponent("filterBy");
								filterBy.setValue("");
								var coursesGrid = me.findParentByType('coursesview').query('#coursesGrid')[0];
								coursesGrid.getStore().filterBy(getFilterRecord(['title', 'formattedCourse'], "")); 
		                    }
						},
                    }]
                
                },
                {
                    xtype : 'coursesgridpanel',
                }
            
                ]
            }
            
            
            ]
        
        });
        
        return me.callParent(arguments);
    },
});

function getFilterRecord(fields, searchString){
	var f = [];
	f.push({
		filter: function(record){
			return filterString(searchString, fields, record);
		}
	});
	var len = f.length;
	return function(record){
		for (var i = 0; i < len; i++){
			if(!f[i].filter(record)){
				return false;
			}
		}
		return true;
	}
}

function filterString(value, fields, record){
	var matches = false;
	fields.forEach(function(field){
		var val = record.get(field);
		if (typeof val == "string"){
			if(val.toLowerCase().indexOf(value.toLowerCase()) > -1)
				matches = true;
		}
	});
	return matches;
}