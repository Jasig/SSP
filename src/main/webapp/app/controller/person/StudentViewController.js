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
Ext.define('Ssp.controller.person.StudentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
     	formUtils: 'formRendererUtils'
    },
    control: {
		studentIntakeRequestDateInCaseloadField: '#studentIntakeRequestDateInCaseload',
        studentIntakeCompleteDateInCaseloadField: '#studentIntakeCompleteDateInCaseload'
    },  
	init: function() {
		var me=this;
    	
		me.getStudentIntakeRequestDateInCaseloadField().reset();
		me.getStudentIntakeRequestDateInCaseloadField().setValue(me.handleNull(me.person.get('studentIntakeRequestDate')));
		me.getStudentIntakeCompleteDateInCaseloadField().setValue(me.handleNull(me.person.get('studentIntakeCompleteDate')));
		
		
		return me.callParent(arguments);
    },
	
	handleNull: function(value, defaultValue){
		if(defaultValue == null || defaultValue == undefined)
			defaultValue = "";
		if(value == null || value == undefined || value == 'null')
			return defaultValue;
		return value;
	}
    
  
});