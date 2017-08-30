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
Ext.define('Ssp.controller.admin.map.LoadTemplateViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject:{
        appEventsController: 'appEventsController',
        formUtils: 'formRendererUtils',
        currentMapPlan: 'currentMapPlan',
        store: 'planTemplatesSummaryStore',
        personLite: 'personLite',
        apiProperties: 'apiProperties',
        mapPlanService:'mapPlanService',
        programsStore: 'programsStore',
        departmentsStore: 'departmentsStore',
        mapEventUtils: 'mapEventUtils',
        catalogYearsStore: 'catalogYearsStore',
        mapTemplateTagsStore: 'mapTemplateTagsStore',
        divisionsStore: 'divisionsStore'
    },
	control: {
        'name': {
            selector: '#templateNameFilter'
        },
        'program': {
           selector: '#program',
           listeners: {
                select: 'onProgramSelect'
           }
        },
        'department':{
           selector: '#department',
           hidden: false,
           listeners: {
                select: 'onDepartmentSelect'
           }
        },
        'division':{
           selector: '#division',
           listeners: {
                select: 'onDivisionSelect'
           }
        },
        'programCancel':{
            selector: '#programCancel',
            hidden: true,
            listeners: {
                click: 'onProgramCancelClick'
            }
        },
        'departmentCancel':{
            selector: '#departmentCancel',
            hidden: false,
            listeners: {
                click: 'onDepartmentCancelClick'
            }
         },
        'divisionCancel':{
            selector: '#divisionCancel',
            listeners: {
                click: 'onDivisionCancelClick'
            }
        },
         'catalogYear':{
           selector: '#catalogYear',
           listeners: {
                select: 'onCatalogYearSelect'
           }
        },
         'mapTemplateTag':{
           selector: '#mapTemplateTag',
           listeners: {
                select: 'onMapTemplateTagSelect'
           }
        },
        'catalogYearCancel':{
           selector: '#catalogYearCancel',
           listeners: {
                click: 'onCatalogYearCancelClick'
           }
        },
        'mapTemplateTagCancel':{
           selector: '#mapTemplateTagCancel',
           listeners: {
                click: 'onMapTemplateTagCancelCancelClick'
           }
        },
        'objectStatusFilter':{
            selector: '#objectStatusFilter',
            hidden: false,
            listeners: {
                select: 'onObjectStatusFilterSelect'
            }
        },
		'templateNameFilter':{
            selector: '#templateNameFilter',
            hidden: false
        },
        view: {
            show: 'onShow'
        },
        'searchTemplates':{
           selector: '#searchTemplates',
           listeners: {
                click: 'onSearchTemplatesClick'
           }
        }
	},

	init: function() {
		var me=this;
	    //me.resetForm();

        if(me.programsStore.getTotalCount() < 1) {
            me.programsStore.load();
        }
		if(me.departmentsStore.getTotalCount() < 1) {
            me.departmentsStore.load();
        }
		if(me.divisionsStore.getTotalCount() < 1) {
            me.divisionsStore.load();
        }
	    if(me.catalogYearsStore.getTotalCount() < 1) {
            me.catalogYearsStore.load();
        }
	    if(me.mapTemplateTagsStore.getTotalCount() < 1) {
            me.mapTemplateTagsStore.load();
        }
	    me.store.addListener("load", me.onStoreLoaded, me);

        return me.callParent(arguments);
    },

    loadTemplates: function() {
        var me = this;

        me.getView().setLoading(true);
        me.store.load();
		// callback registered in init()
    },
    
    onStoreLoaded: function(){
    	var me = this;
    	me.store.sort();
    	me.getView().setLoading(false);
    },

    onShow: function() {
        // do this on show rather than init b/c this component isn't destroyed
        // when dismissed, but we need to make sure you see all the latest
        // Template changes whenever we do display this component. The easiest
        // way to do that is to just hit the store/server again every time the
        // view fires its show event
        var me = this;
        me.loadTemplates();
    },

    resetForm: function() {
        var me = this;
         me.getView().query("form")[0].getForm().reset();
    },
	
    onProgramSelect: function(){
        var me=this;
        me.handleSelect(me);
        var params = {};
        me.setParam(params, me.getProgram(), "programCode");
    },
    
    onProgramCancelClick: function(button){
        var me=this;
        me.getProgram().setValue("");
        me.handleSelect(me);
    },
    
    onDepartmentSelect: function(){
        var me=this;
		me.handleSelect(me);
    }, 
    
    onDepartmentCancelClick: function(button){
        var me=this;
        me.getDepartment().setValue("");
        me.handleSelect(me);
    },
    
    onDivisionSelect: function(){
        var me=this;
		me.handleSelect(me);
    },   
    
    onDivisionCancelClick: function(button){
        var me=this;
        me.getDivision().setValue("");
        me.handleSelect(me);
    },
    
    onCatalogYearSelect: function(){
        var me=this;
		me.handleSelect(me);
    },

    onCatalogYearCancelClick: function(button){
        var me=this;
        me.getCatalogYear().setValue("");
        me.handleSelect(me);
    },

    onMapTemplateTagSelect: function(){
        var me=this;
		me.handleSelect(me);
    },

    onMapTemplateTagCancelCancelClick: function(button){
        var me=this;
        me.getMapTemplateTag().setValue("");
        me.handleSelect(me);
    },

    onSearchTemplatesClick: function(button){
        var me=this;
        me.handleSelect(me);
    },

    handleSelect: function(mte){
		var grid = Ext.getCmp("templatePanel");
    	var params = {};
    	var me = this;

        me.setParam(params, Ext.getCmp('program'), 'programCode');
    	me.setParam(params, Ext.getCmp('department'), 'departmentCode');
    	me.setParam(params, Ext.getCmp('division'), 'divisionCode');
    	me.setParam(params, Ext.getCmp('templateNameFilter'), 'name');
    	me.setParam(params, me.getCatalogYear(), 'catalogYearCode');
    	me.setParam(params, me.getMapTemplateTag(), 'mapTemplateTagId');

    	grid.store.load({params: params});
    },

    setParam: function(params, field, fieldName){
    	if(field.getValue() && field.getValue().length > 0)
    		params[fieldName] = field.getValue();
    },

	onObjectStatusFilterSelect:function(){
        var me=this;
        me.handleSelect(me);
	},
	
	destroy:function(){
	    var me=this;
	    me.store.clearFilter(false);
	    me.store.removeListener("load", me.onStoreLoaded, me);
	    return me.callParent( arguments );
	}
});