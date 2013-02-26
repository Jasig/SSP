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
Ext.define('Ssp.controller.StudentRecordViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
	inject: {
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
		apiProperties: 'apiProperties',
        personLite: 'personLite'
	},
	
    config: {
        personViewHistoryUrl: ''
       
    },
	
    control: {
		view: {
			collapse: 'onCollapsed',
			expand: 'onExpanded'
		},
		'studentRecordEditButton': {
            click: 'onStudentRecordEditButtonClick'
        },
		'viewCoachingHistoryButton': {
            click: 'onViewCoachingHistoryButtonClick'
        },
        'emailCoachButton': {
            click: 'onEmailCoachButtonClick'
        },
	},
	
    init: function() {
		var me=this;
		var personId = me.personLite.get('id');
		
		me.personViewHistoryUrl = me.apiProperties.getAPIContext() + me.apiProperties.getItemUrl('personViewHistory');
		
        me.personViewHistoryUrl = me.personViewHistoryUrl.replace('{id}',personId);
 		return this.callParent(arguments);
    },
    
    onCollapsed: function(){
    	this.appEventsController.getApplication().fireEvent('collapseStudentRecord');
    },
    
    onExpanded: function(){
    	this.appEventsController.getApplication().fireEvent('expandStudentRecord');
    },
	
	onStudentRecordEditButtonClick: function(button){
        var me=this;
        
        var comp = this.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1}); 
    },
	
	onEmailCoachButtonClick: function(button){
        var me=this;
        this.appEventsController.getApplication().fireEvent('emailCoach');
        
    },
	
	onViewCoachingHistoryButtonClick: function(button){
        var me=this;
       this.appEventsController.getApplication().fireEvent('viewCoachHistory');
    }
});