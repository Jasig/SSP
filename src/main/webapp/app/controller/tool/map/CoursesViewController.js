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
		terms: 'termsFacetedStore',
		programs: 'programsFacetedStore',
		tags: 'facetedTagsStore'
		/*departments: 'departmentsStore',
		divisions: 'divisionsStore'*/
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
           }
		
    },
    resetForm: function() {
        var me = this;
        me.getView().getForm().reset();
    },
	init: function() {
		var me=this;
		var view = me.getView();
		me.programs.load();
		me.terms.load();
		me.tags.load();		
		return this.callParent(arguments);
    },
    
    
    onProgramSelect: function(){
        var me=this;
        me.handleSelect(me);
    },  
    
    onProgramCancelClick: function(button){
        var me=this;
        me.getProgram().setValue("");
        me.handleSelect(me);
    },
    
    onTagSelect: function(){
        var me=this;
        me.handleSelect(me);
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
    	var params = me.getAllParams();
    	me.store.load({params: params});
		var params = me.getAllParams();	
    	me.doFaceting(params);
    },

	getAllParams:function(){
		var me = this;
		var params = {};
		me.setParam(params, me.getProgram(), 'programCode');
    	me.setParam(params, me.getTag(), 'tag');
    	me.setParam(params, me.getTerm(), 'termCode');
		return params;
	},
    
    doFaceting: function(params){
    	var me = this;
    	var facets = [me.getProgram(), me.getTag(), me.getTerm()];
    	facets.forEach(function(facet){
    		facet.getStore().load({params:params});
    	});
    },
    
    setParam: function(params, field, fieldName){
    	if(field.getValue() && field.getValue().length > 0){
    		params[fieldName] = field.getValue();
		}
    },


	destroy:function(){
	    var me=this;
	    return me.callParent( arguments );
	}
	
});