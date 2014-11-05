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
Ext.define('Ssp.controller.tool.actionplan.ActionPlanToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	person: 'currentPerson',
        personLite: 'personLite'
    },
    constructor: function() {
    	var me=this;   	
    	 var id = me.personLite.get('id');
         var currentId = me.person.get('id');
         if (id != "" && currentId == "") {
 	        me.personService.get(id, {
 	            success: me.newServiceSuccessHandler('person', me.getPersonSuccess, serviceResponses),
 	            failure: me.newServiceFailureHandler('person', me.getPersonFailure, serviceResponses),
 	            scope: me
 	        });
         }
         
		return me.callParent(arguments);
    }, 
    
    getPersonSuccess: function(serviceResponses) {
        var me = this;
        var personResponse = serviceResponses.successes.person;
        me.person.populateFromGenericObject(personResponse);
        me.getView().loadRecord(me.person);

     },

     getPersonFailure: function() {
     }
});