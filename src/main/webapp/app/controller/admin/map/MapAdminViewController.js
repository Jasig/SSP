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
Ext.define('Ssp.controller.admin.map.MapAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
		store: 'planTemplatesStore',
        formUtils: 'formRendererUtils',
        currentTemplate: 'currentMapPlan'
    },
    config: {
        containerToLoadInto: 'adminforms'
    },
    refs: [
           {
               ref : 'templatePanel',
               selector: '#templatePanel'
           }
    ],
    control: {
        isTemplateActive: {
            selector: '#isTemplateActive',
            listeners: {
            	checkChange: 'onCheckChange'
            }
        },
	    programCodeNameColumn: {
	        selector: '#programCodeNameColumn',
	        renderer: function(value) {
			    var text = 'TEST';
			    return text;
			} 
	    },
		'editElectiveCoursesButton': {
			click: 'onEditElectiveCoursesClick'
		}
    },

    init: function() {    	
        var me=this;

        me.formUtils.reconfigureGridPanel( me.getView(), me.store);
        me.store.load();

        return me.callParent(arguments);
    },

    destroy: function() {
        var me=this;
        if ( me.electiveCoursesPopUp ) {
            me.electiveCoursesPopUp.destroy();
        }
        return me.callParent( arguments );
    },

    onEditElectiveCoursesClick: function(button) {
        var me = this;
        var grid, record, idx;
        grid = button.up('grid');
        record = grid.getView().getSelectionModel().getSelection()[0];

        if (record && record.data.id) {
            if ( me.electiveCoursesPopUp ) {
                me.electiveCoursesPopUp.destroy();
            }

            if (me.currentTemplate) {
                me.currentTemplate.data = record.data;
                me.currentTemplate.data.isTemplate = true;
            }

            me.electiveCoursesPopUp = Ext.create('Ssp.view.admin.forms.map.MapElectiveCourses', {
                currentTemplateGetId: me.currentTemplate
            });

            me.electiveCoursesPopUp.show();

        } else {
            Ext.Msg.alert('SSP Error', 'Please select a Template before assigning Elective Courses!');
        }
    },

    displayEditor: function(){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    },
    
    onGetTemplateSuccess: function(response, t) {    	
    	var me = t;
    	var callbacks = new Object();
    	callbacks.success = me.onLoadCompleteSuccess;
		callbacks.failure = me.onLoadCompleteFailure;
		callbacks.scope = me;		
    	var mapPlanService = Ext.create('Ssp.service.MapPlanService');
		var plan = Ext.create('Ssp.model.tool.map.Plan');
		var grid = Ext.getCmp("templatePanel");		
    	var planfromResponse = Ext.decode(response.responseText);
    	    	
    	if (planfromResponse.objectStatus == 'INACTIVE')
    	{
    		planfromResponse.objectStatus = 'ACTIVE';
    	}
    	else
    	{
    		planfromResponse.objectStatus = 'INACTIVE';
    	}
    	
    	plan.loadFromServer(planfromResponse);    	
		plan.set('isTemplate', true);
        //plan.set('isPrivate', false);
                
        var planCourses = plan.get('planCourses');
        for(var k = 0; k < planCourses.length; k++){
    		course = planCourses[k];
    		course.id=null;
    	}
            	
    	var url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('templatePlan') );
    	
    	// update
		me.apiProperties.makeRequest({
			url: url+'/'+ plan.get('id'), 
			method: 'PUT',
			jsonData: plan.getSimpleJsonData(),
			successFunc: callbacks.success,
			failureFunc: callbacks.failure,
			scope: me
		});	   	
    },
    
    onGetTemplateFailure: function(response, t) {
		alert('Get Template From ServerFailure: ' + response + ' ' + t);
    },
    
    onCheckChange: function(column, rowIndex, checked, eOpts){    
    	var me = this;    	
    	var mapPlanService = Ext.create('Ssp.service.MapPlanService');
		var plan = Ext.create('Ssp.model.tool.map.Plan');
		var grid = Ext.getCmp("templatePanel");		
		var apiProps = Ext.create('Ssp.service.MapPlanService');
		
		var callbacks = new Object();
		callbacks.success = me.onGetTemplateSuccess;
		callbacks.failure = me.onLoadCompleteFailure;
		callbacks.scope = me;		
		plan.loadFromServer(grid.store.getAt(rowIndex).data);
		    
		mapPlanService.getTemplate(plan.get('id'), callbacks);		
    },
                 
    onLoadCompleteFailure: function(response, t) {
     	alert('failure: ' + response + ' '+ t);
    },
    
    onLoadCompleteSuccess: function(response, t) {
    	var me = t;
    	//Ext.getCmp('templatePanel').getStore().load();
    	var grid = Ext.getCmp("templatePanel");
    	var params = {};
    	var me = this;
		me.setParam(params, Ext.getCmp('program'), 'programCode');
    	me.setParam(params, Ext.getCmp('department'), 'departmentCode');
    	me.setParam(params, Ext.getCmp('division'), 'divisionCode');
    	me.setParam(params, Ext.getCmp('templateNameFilter'), 'name');
		params["objectStatus"] = "ALL"; //Object status and object type filtered client side.
    	grid.store.on('load', me.onLoadComplete, this, {single: true});
    	grid.store.load({params: params});
    },
    setParam: function(params, field, fieldName){
    	if (field.getValue() && field.getValue().length > 0) {
            params[fieldName] = field.getValue();
        }
    },
    onLoadComplete: function(){
        var me = this;
        me.onObjectStatusFilterSelect();
    },
    onObjectStatusFilterSelect:function(){
        var me = this;
        //do nothing at this time
    }
});