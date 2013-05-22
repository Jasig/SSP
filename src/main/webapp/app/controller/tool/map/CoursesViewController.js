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
Ext.define('Ssp.controller.tool.map.CoursesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
		columnRendererUtils : 'columnRendererUtils',
		store: 'coursesStore',
		termsStore: 'termsStore',
		programs: 'programsStore',
		tags: 'facetedTagsStore',
		departments: 'departmentsStore',
		divisions: 'divisionsStore',
    },
    control: {
    	'program': {
    	   selector: '#program',
    	   listeners: {
            select: 'onProgramSelect'
           }
        },
        
        'programCancel':{
         selector: '#programCancel',
         hidden: true,
           listeners: {
            click: 'onProgramCancelClick'
           }
        },
        
        'tag':{
           selector: '#tag',
           hidden: false,
           listeners: {
            select: 'onTagSelect'
           }
        },
        
        
        'tagCancel':{
           selector: '#tagCancel',
           hidden: false,
           listeners: {
            click: 'onTagCancelClick'
           }
        },
        
        'department':{
           selector: '#department',
           hidden: false,
           listeners: {
            select: 'onDepartmentSelect'
           }
        },
        
        'departmentCancel':{
            selector: '#departmentCancel',
            hidden: false,
            listeners: {
             click: 'onDepartmentCancelClick'
            }
         },
        
         'term':{
             selector: '#term',
             hidden: false,
             listeners: {
              select: 'onTermSelect'
             }
          },
          
          'termCancel':{
              selector: '#termCancel',
              hidden: false,
              listeners: {
               click: 'onTermCancelClick'
              }
           },
           
        'division':{
           selector: '#division',
           listeners: {
            select: 'onDivisionSelect'
           }
        },
        
        'divisionCancel':{
           selector: '#divisionCancel',
           listeners: {
            click: 'onDivisionCancelClick'
           }
        },
		
    },
    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },
	init: function() {
		var me=this;
		var view = me.getView();
		me.programs.load();
		me.divisions.load();
		me.departments.load();
		me.tags.load();		
		if(me.termsStore.getTotalCount() == 0){
			me.termsStore.addListener("load", me.onTermsStoreLoad, me);
			me.termsStore.load();
	}else{
		me.initialiseTerms();
	}
		return this.callParent(arguments);
    },
    
    
    onProgramSelect: function(){
        var me=this;
        me.handleSelect(me);
        var params = {};
        me.setParam(params, me.getProgram(), "programCode");
        me.doFaceting([me.getTag(), me.getTerm()], params);
    },  
    
    onProgramCancelClick: function(button){
        var me=this;
        me.getProgram().setValue("");
        me.handleSelect(me);
    },
    
    onTagSelect: function(){
        var me=this;
        me.handleSelect(me);
        var params = {};
        me.setParam(params, me.getTag(), "tagCode");
        me.doFaceting([me.getProgram(), me.getTerm()], params);
    },  
 
    onTagCancelClick: function(button){
        var me=this;
        me.getTag().setValue("");
        me.handleSelect(me);
    },
    
    onTermSelect: function(){
        var me=this;
        me.handleSelect(me);
        
    }, 
    
    onTermCancelClick: function(button){
        var me=this;
        me.getTerm().setValue("");
        me.handleSelect(me);
    },
    
    onDepartmentSelect: function(){
        var me=this;
		me.handleSelect(me);
    }, 
    
    onDepartmentCancelClick: function(button){
        var me=this;
        me.getDepartment().setValue("");
        handleSelect(me);
    },
    
    onDivisionSelect: function(){
        var me=this;
		handleSelect(me);
    },   
    
    onDivisionCancelClick: function(button){
        var me=this;
        me.getDivision().setValue("");
        handleSelect(me);
    },
    
    handleSelect: function(me){
    	var params = {};
    	me.setParam(params, me.getProgram(), 'programCode');
    	me.setParam(params, me.getTag(), 'tag');
    	me.setParam(params, me.getDepartment(), 'department');
    	me.setParam(params, me.getDivision(), 'division');
    	me.setParam(params, me.getTerm(), 'termCode');
    	me.store.on('load', this.onLoad, this, {single: true});
    	me.store.load({params: params});
    	me.doFaceting(params);
    },
    
    doFaceting: function(params){
    	var me = this;
    	var facets = [me.getProgram(), me.getTag()];
    	facets.forEach(function(facet){
    		facet.getStore().load({params:params});
    	});
    },
    
    onLoadComplete: function(){
    	//here if we need to rebind stores.
    },
    
    setParam: function(params, field, fieldName){
    	if(field.getValue() && field.getValue().length > 0)
    		params[fieldName] = field.getValue();
    },
    
    onTermsStoreLoad:function(){
		var me = this;
		me.termsStore.removeListener( "onTermsStoreLoad", me.onTermsStoreLoad, me );
		me.initialiseTerms();
	},
	
	initialiseTerms: function(){
		var me = this;
		var futureTermsStore = me.termsStore.getCurrentAndFutureTermsStore(true);
		me.getTerm().bindStore(futureTermsStore);
	},

	destroy:function(){
	    var me=this;
	    return me.callParent( arguments );
	}
	
});