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
        personLite: 'personLite',
		authenticatedPerson: 'authenticatedPerson'
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
 
    
    getCurrent: function( personId, callbacks ){
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
    
    getPlan: function( planId, personId, callbacks ){
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
			url: url+'/'+planId, 
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
        me.currentMapPlan.set('personId',  me.personLite.get('id'));
		me.currentMapPlan.set('ownerId',  me.authenticatedPerson.get('id'));
        
        var i = 0;
		me.currentMapPlan.clearPlanCourses();
        var planCourses = new Array();
        for(var index in semesterStores){
        	var semesterStore = semesterStores[index];
            var models = semesterStore.getRange();
            models.forEach(function(model){
            	var planCourse = new Object();
            		planCourse.courseTitle = model.get('title');
            		planCourse.courseCode = model.get('code');
					//TODO This has to do with conflicts with print and save
					if(!model.get('termCode') || model.get('termCode') == "")
            			planCourse.termCode = index;
					else
						planCourse.termCode = model.get('termCode');
            		planCourse.creditHours = model.get('creditHours');
            		planCourse.formattedCourse = model.get('formattedCourse');
            		planCourse.courseDescription = model.get('description');
            		planCourse.orderInTerm = i;
					planCourse.objectStatus = 'ACTIVE';
            		planCourse.isDev = model.get('isDev');
            		planCourses[i++] = planCourse;
            })
         }
         me.currentMapPlan.set('planCourses',planCourses);
    }, 
            
    save: function(semesterStores, callbacks, currentMapPlan, view, saveAs ){
		var me=this;
		var url = me.getBaseUrl(currentMapPlan.get('personId'));
	    var success = function( response ){
	    	var r = Ext.decode(response.responseText);
	    	callbacks.success(view);
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	 
	    	callbacks.failure(view);
	    };
		
	    me.updateCurrentMap(semesterStores);
	    
		// save
		if ((!me.currentMapPlan.get('id') || me.currentMapPlan.get('id') == '') || saveAs )
		{	
			me.currentMapPlan.set('id','');
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
    			url: url+'/'+me.currentMapPlan.get('id'), 
    			method: 'PUT',
    			jsonData: me.currentMapPlan.data,
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}	
    },
    
    print: function(semesterStores, callbacks ){
		var me=this;
		var url = me.getBaseUrl(me.currentMapPlan.get('personId'));
	    var success = function( response, view ){
			callbacks.success( response, callbacks.scope );
	    };
		var objectStatus = me.currentMapPlan.get('objectStatus');
		if(objectStatus != 'ACTIVE' || objectStatus != 'INACTIVE')
			me.currentMapPlan.set('objectStatus','ACTIVE');
	    var failure = function( response ){
	    	me.apiProperties.handleError( response );
		    callbacks.failure( response, callbacks.scope );
	    };
	
		/**** TODO when semesterStores is null this is printing from somewhere 
		      other than the map tool.  planCourses as brought in need to be
			  converted to semesterCourses because of some inconsistencies
			   this needs to be corrected                                        *****/			
		 
		if(semesterStores == null){
			var planCourses = me.currentMapPlan.get('planCourses');
			var semsetersStore = new Ssp.store.SemesterCourses();
			semesterStores = [semsetersStore];
			planCourses.forEach(function(planCourse){
				semsetersStore.add(new Ssp.model.tool.map.SemesterCourse(planCourse));
			})
		}
	    me.updateCurrentMap(semesterStores);
		
		me.apiProperties.makeRequest({
   			url: url+'/print',
   			method: 'POST',
   			jsonData: me.currentMapPlan.data,
   			successFunc: success,
   			failureFunc: failure,
   			scope: me
   		});					
    },
    	
});