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

	getTemplateBaseUrl: function(){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('templatePlan') );
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

	getSummary: function( personId, callbacks ){
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
			url: url+'/summary', 
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
    
    getTemplateSummary: function(callbacks ){
		var me=this;
		var url = me.getTemplateBaseUrl(personId);
	    var success = function( response, view ){
			callbacks.success( response, callbacks.scope );
	    };
	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	    	
	    	callbacks.failure( response, callbacks.scope );
	    };
	    
		me.apiProperties.makeRequest({
			url: url+'/summary', 
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },

	getCurrentSummary: function( personId, callbacks ){
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
			url: url+'/currentsummary', 
			method: 'GET',
			successFunc: success,
			failureFunc: failure,
			scope: me
		});    	
    },
    
    getPlan: function( planId, callbacks ){
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
			scope: callbacks.scope
		});    	
    },
    
    getTemplate: function( planId, callbacks ){
		var me=this;
		var url = me.getTemplateBaseUrl();
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
    	if (!planCourses) {
    		return termCodes;
		}
    	
    	Ext.Array.forEach(planCourses, function(planCourse) {		
			if( Ext.Array.indexOf(termCodes, planCourse.termCode) < 0 ){
				termCodes[i++] = planCourse.termCode;
			  }});
			return termCodes;
    },
    
    updateCurrentMap: function(semesterStores){ 
        var me = this;
        me.currentMapPlan.set('personId',  me.personLite.get('id'));
        
        var i = 0;
		me.currentMapPlan.clearPlanCourses();
        me.currentMapPlan.updatePlanCourses(semesterStores);
    },

    updatePrintableMap: function(semesterStores,printableMap){ 
        var me = this;
        printableMap.set('personId',  me.personLite.get('id'));
        
        var i = 0;
        printableMap.clearPlanCourses();
        printableMap.updatePlanCourses(semesterStores);
    },

	getBoolean: function(model, fieldName){
		var me = model;
		if (me.get(fieldName) == 'on' || me.get(fieldName) == true || me.get(fieldName) == 1 ||
                me.get(fieldName) == 'true') {
			return true;
		}

		return false;
	},

    save: function(semesterStores, callbacks, currentMapPlan, saveAs ){
		var me=this;
		var url = me.getBaseUrl(me.personLite.get('id'));
	    var success = function( response ){
	    	callbacks.success( response, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	 
	    	callbacks.failure( response, callbacks.scope );
	    };
		
	    me.updateCurrentMap(semesterStores);
		me.currentMapPlan.set('ownerId',me.authenticatedPerson.get('id'));

		// save
		if ((!me.currentMapPlan.get('id') || me.currentMapPlan.get('id') == '') || saveAs || currentMapPlan.get('isTemplate') == true) {

		    me.currentMapPlan.set('id','');

            me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: me.currentMapPlan.getSimpleJsonData(),
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				

		} else {
			// update
    		me.apiProperties.makeRequest({
    			url: url+'/'+ me.currentMapPlan.get('id'), 
    			method: 'PUT',
    			jsonData: me.currentMapPlan.getSimpleJsonData(),
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}	
    },
    
    saveTemplate: function(semesterStores, callbacks, currentMapPlan, saveAs ){
		var me=this;
		var url = me.getTemplateBaseUrl();
		me.currentMapPlan.set('ownerId',me.authenticatedPerson.get('id'));

	    var success = function( response ){
	    	callbacks.success( response, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );
	    	callbacks.failure( response, callbacks.scope );
	    };
		
	    me.updateCurrentMap(semesterStores);

		// save
		if ((!me.currentMapPlan.get('id') || me.currentMapPlan.get('id') == '') || saveAs || currentMapPlan.get('isTemplate') == false ) {

			me.currentMapPlan.set('id','');
			me.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: me.currentMapPlan.getSimpleJsonData(),
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});				
		} else {
			// update
    		me.apiProperties.makeRequest({
    			url: url+'/'+ me.currentMapPlan.get('id'), 
    			method: 'PUT',
    			jsonData: me.currentMapPlan.getSimpleJsonData(),
    			successFunc: success,
    			failureFunc: failure,
    			scope: me
    		});	
		}
    },
    
    print: function(semesterStores, outputData, callbacks, planType){
		var me = this;
    	me.outputMap(semesterStores, callbacks, outputData, 'print', planType);
    },

    printCurrent: function(semesterStores, outputData, callbacks, planType ){
		var me = this;
    	me.outputCurrentMap(semesterStores, callbacks, outputData, 'printCurrent');
    },

    email: function(semesterStores, outputData, callbacks, planType ){
		var me = this;
    	me.outputMap(semesterStores, callbacks, outputData, 'email');
    },

    emailCurrent: function(semesterStores, outputData, callbacks, planType ){
		var me = this;
    	me.outputCurrentMap(semesterStores, callbacks, outputData, 'emailCurrent');
    },

    outputCurrentMap: function(semesterStores, callbacks, outputData, outputType){
    	var me=this;
		var url = me.getBaseUrl(me.personLite.get('id'));
	    var success = function( response ){
			callbacks.success( response, callbacks.scope );
	    };
	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	 
	    	callbacks.failure(response, callbacks.scope);
	    };
	    outputData.set("plan",null);
		me.apiProperties.makeRequest({
   			url: url+'/' + outputType,
   			method: 'POST',
   			jsonData: outputData.data,
   			successFunc: success,
   			failureFunc: failure,
   			scope: me
   		});
    },
    outputMap: function(semesterStores, callbacks, outputData, outputType, planType){
    	var me=this;
    	var url = null;

        if (planType == null) {
            planType = "plan";
        }

        if (planType=="plan") {
            url = me.getBaseUrl(me.personLite.get('id'));
        } else {
            url = me.getTemplateBaseUrl();
        }
    	
	    var success = function( response ){
			callbacks.success( response, callbacks.scope );
	    };

	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	 
	    	callbacks.failure(response, callbacks.scope);
	    };

	    me.prepareMapOutput(semesterStores, outputData, callbacks.isPrivate);
		me.apiProperties.makeRequest({
   			url: url+'/' + outputType,
   			method: 'POST',
   			jsonData: outputData.data,
   			successFunc: success,
   			failureFunc: failure,
   			scope: me
   		});
    },

	validate: function(plan, isTemplate, callbacks){
		var me=this;

        if (plan == null) {
	    	callbacks.failure("Plan not found", callbacks.scope);
		}
			var url = me.getBaseUrl(plan.get('personId'));
		if (isTemplate) {
			plan.set("personId","");
			plan.setIsTemplate(isTemplate);
            url = me.getTemplateBaseUrl();
        }

        if (!plan.get('planElectiveCourses')) {
            plan.set('planElectiveCourses', []);
        }

        if (!plan.get('mapTemplateTags')) {
            plan.set('mapTemplateTags', []);
        }

	    var success = function( response ){
			callbacks.success( response, callbacks.scope );
	    };
	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	 
	    	callbacks.failure(response, callbacks.scope);
	    };

	    me.apiProperties.makeRequest({
   			url: url+'/validate',
   			method: 'POST',
   			jsonData: plan.getSimpleJsonData(),
   			successFunc: success,
   			failureFunc: failure,
   			scope: me
   		});
	},
	
	planStatus: function(plan, callbacks){
		var me=this;
		if (plan.get("isTemplate")) {
			if ( callbacks.failure ) {
				if ( callbacks.scope ) {
					callbacks.failure.apply(callbacks.scope, ["Is template, no plan status."]);
				} else {
					callbacks.failure("Is template, no plan status.");
				}
			}
			return;
		}
		// Little bit of weirdness here to support those clients that need an
		// explicit invocation scope to be passed through to callbacks. This
		// shouldn't be necessary b/c the makeRequest() will invoke the
		// callback in the specified scope, so 'this' in the callback will refer
		// to that same value (the same way it does here). But we need that
		// explicit scope arg for backward compatibility.
		var success = function( response ){
			callbacks.success.apply(this, [response, this]);
		};
		var failure = function( response ){
			callbacks.failure.apply(this, [response, this]);
		};
		var url = me.getBaseUrl(me.personLite.get('id'));

        me.apiProperties.makeRequest({
   			url: url+'/calculatedPlanstatus',
   			method: 'GET',
   			successFunc: success,
   			failureFunc: failure,
   			scope: callbacks.scope
   		});
	},
    
    prepareMapOutput: function(semesterStores, outputData, isPrivate){
    	/**** TODO when semesterStores is null this is printing from somewhere 
	      other than the map tool.  planCourses as brought in need to be
		  converted to semesterCourses because of some inconsistencies
		   this needs to be corrected                                        *****/		
		var me = this;	
		var printableMap = me.currentMapPlan.copy();
    	var objectStatus = printableMap.get('objectStatus');

        if (objectStatus != 'ACTIVE' || objectStatus != 'INACTIVE') {
			printableMap.set('objectStatus','ACTIVE');
		}

		var failure = function( response ){
	    	me.apiProperties.handleError( response );
		    callbacks.failure( response, callbacks.scope );
	    };

	    if(semesterStores == null) {
			var planCourses = printableMap.get('planCourses');
			var semsetersStore = new Ssp.store.SemesterCourses();
			semesterStores = [semsetersStore];
			Ext.Array.forEach(planCourses, function(planCourse){
				semsetersStore.add(new Ssp.model.tool.map.SemesterCourse(planCourse));
			});
		}
	    me.updatePrintableMap(semesterStores, printableMap);
	    outputData.set("plan", printableMap.getSimpleJsonData());
	    outputData.set("isPrivate",isPrivate);
    }
});