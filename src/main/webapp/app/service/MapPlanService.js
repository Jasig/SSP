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
Ext.define('Ssp.service.MapPlanService', {  
    extend: 'Ssp.service.AbstractService',   		
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	currentMapPlan: 'currentMapPlan',
        personLite: 'personLite'
    },
    initComponent: function() {
		return this.callParent( arguments );
    },

    getBaseUrl: function(id){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personMapPlan') );
		baseUrl = baseUrl.replace('{id}', id);
		return baseUrl;
    },
    
    get: function( personId, callbacks ){
		var me=this;
		var url = me.getBaseUrl(personId);
	    var success = function( response, view ){
			callbacks.success( response, callbacks.scope );
	    };
	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url+'/current', 
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
	  
    getTermCodes: function(mapPlan){ 
    	var termCodes = [];
    	var i = 0;
    	var planCourses = mapPlan.get('planCourses');
    	planCourses.forEach(function(planCourse){
    	if(termCodes.indexOf(planCourse.termCode) < 0)
    		termCodes[i++] = planCourse.termCode;
    	  })
    	return termCodes;
    	},
    	
    updateCurrentMap: function(semesterStores){ 
        var me = this;
        me.currentMapPlan.set('ownerId',me.personLite.get('id'));
        me.currentMapPlan.set('personId',me.personLite.get('id'));
        
        var i = 0;
        var planCourses = new Array();
        for(var index in semesterStores){
        	var semesterStore = semesterStores[index];
            var models = semesterStore.getRange();
            models.forEach(function(model){
            	var planCourse = new Object();
            		planCourse.courseTitle = model.get('title');
            		planCourse.courseCode = model.get('code');
            		planCourse.termCode = index;
            		planCourse.creditHours = model.get('minCreditHours');
            		planCourse.formattedCourse = model.get('formattedCourse');
            		planCourse.courseDescription = model.get('description');
            		planCourse.orderInTerm = i;
            		planCourse.isDev = model.get('isDev');
            		planCourses[i++] = planCourse;
            	})
            }
            me.currentMapPlan.set('planCourses',planCourses);
            }, 
            
    save: function(semesterStores, callbacks, currentMapPlan ){
		var me=this;
		var url = me.getBaseUrl(currentMapPlan.get('personId'));
	    var success = function( response, view ){
	    	var r = Ext.decode(response.responseText);
			//callbacks.success( r, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	//callbacks.failure( response, callbacks.scope );
	    };
		
	    me.updateCurrentMap(semesterStores);
	    
		// save
		if (!me.currentMapPlan.get('id') || me.currentMapPlan.get('id') == '')
		{				
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: me.currentMapPlan.data,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		}else{
			// update
    		me.apiProperties.makeRequest({
    			url: url, 
    			method: 'PUT',
    			jsonData: me.currentMapPlan.data,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}	
    },
    	
});