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
Ext.define('Ssp.controller.ToolsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        formUtils: 'formRendererUtils',
        personLite: 'personLite',
		person: 'currentPerson',
		personService:'personService',
        toolsStore: 'toolsStore'
    },
    control: {
        view: {
            itemclick: 'onItemClick',
            viewready: 'onViewReady'
        }
    
    },
    
    init: function(){
        return this.callParent(arguments);
    },
    
    onViewReady: function(comp, obj){
        var me = this;
        me.appEventsController.assignEvent({
            eventName: 'loadPerson',
            callBackFunc: me.onLoadPerson,
            scope: me
        });
        me.appEventsController.assignEvent({
            eventName: 'loadIntake',
            callBackFunc: me.onLoadIntake,
            scope: me
        });
        me.appEventsController.assignEvent({
            eventName: 'transitionStudent',
            callBackFunc: me.onTransitionStudent,
            scope: me
        });
        
        me.appEventsController.assignEvent({eventName: 'doToolsNav', callBackFunc: me.doToolsNav, scope: me});
        me.appEventsController.assignEvent({eventName: 'emailCoach', callBackFunc: me.onEmailCoach, scope: me});
       
        if (me.personLite.get('id') != "") {
            me.loadPerson();
        }
    },
    doToolsNav: function(){
    	var me=this;
    	me.loadTool(toolsRecord.get('toolType'));
    },
    destroy: function(){
        var me = this;
        
        me.appEventsController.removeEvent({
            eventName: 'loadPerson',
            callBackFunc: me.onLoadPerson,
            scope: me
        });
        me.appEventsController.removeEvent({
            eventName: 'loadIntake',
            callBackFunc: me.onLoadIntake,
            scope: me
        });        
        me.appEventsController.removeEvent({
            eventName: 'transitionStudent',
            callBackFunc: me.onTransitionStudent,
            scope: me
        });
        
        me.appEventsController.removeEvent({eventName: 'doToolsNav', callBackFunc: me.doToolsNav, scope: me});
        
        me.appEventsController.removeEvent({eventName: 'emailCoach', callBackFunc: me.onEmailCoach, scope: me});

        if ( me.emailStudentPopup ) {
            me.emailStudentPopup.destroy();
        }

        return me.callParent(arguments);
    },
    
    onLoadPerson: function(){
		var me = this;
		if(me.person.get('id') != me.personLite.get('id')){
			var callbacks = {success: me.getPersonSuccess, failure:me.getPersonFailure, scope:me};
			me.appEventsController.loadMaskOn();
			me.personService.get(me.personLite.get('id'), callbacks);
		}else if(me.person.get('id') ){
			me.loadToolWithPersonLoaded();
		}  	
   	},

	getPersonSuccess: function(response, scope) {
        var me = scope;
        me.person.populateFromGenericObject(response);
        
		me.appEventsController.loadMaskOff();
		me.loadToolWithPersonLoaded();
    },
	
	loadToolWithPersonLoaded: function(){
		var me = this;
		var tool = me.toolsStore.find('toolType', 'profile');
    	if(tool == -1)
			me.loadFirstTool();
		else
			me.loadPerson();
    	me.appEventsController.getApplication().fireEvent('updateStudentRecord',{'person':me.person});
	},

    getPersonFailure: function() {
		 me.appEventsController.loadMaskOff();
    },
    
    onLoadIntake: function(){
        this.loadIntake();
    },    
    onTransitionStudent: function(){
        this.selectTool('journal');
        this.loadTool('journal');
    },
    
    loadFirstTool: function(){
    	var tool = this.toolsStore.getRange()[0];
        this.getView().getSelectionModel().select(tool);
        this.loadTool(tool.get('toolType'));
    },
    
    loadPerson: function(){
        this.selectTool('profile');
        this.loadTool('profile');
    },
    loadIntake: function(){
        this.selectTool('studentintake');
        this.loadTool('studentintake');
    },
    selectTool: function(toolType){
        var tool = this.toolsStore.findExact('toolType', toolType)
        this.getView().getSelectionModel().select(tool);
    },
    
    onItemClick: function(grid, record, item, index){
        var me = this;
        
        //Listeners will need to return false to halt navigation from this point. 
        var skipCallBack = this.appEventsController.getApplication().fireEvent('toolsNav', record, me);  
        
        if (record.get('active') && me.personLite.get('id') != "" && skipCallBack) {
			if (record.get('name') === 'Email Student'){
				me.getView().getSelectionModel().deselectAll();
					if (me.authenticatedPerson.hasAccess('EMAIL_STUDENT_BUTTON')) {
						if ( me.emailStudentPopup ) {
							me.emailStudentPopup.destroy();
						}
						me.emailStudentPopup = Ext.create('Ssp.view.EmailStudentView');
						me.emailStudentPopup.show();
					}
					else{
						Ext.Msg.alert('You do not have permission to email this student');
					}
			}
			else
            	this.loadTool(record.get('toolType'));
        }
        else
        if(record.get('toolType') === 'toolcaseloadreassignment' && skipCallBack) {
            this.loadTool(record.get('toolType'));
        }
    },
    
    loadTool: function(toolType){
        var me = this;
        var comp;

		//TAKE OUT LEGACY AND DOCUMENTS GRANT
        if (me.authenticatedPerson.hasAccess(toolType.toUpperCase() + '_TOOL')) {
            comp = me.formUtils.loadDisplay('tools', toolType, true, {});
        }
        else {
            me.authenticatedPerson.showUnauthorizedAccessAlert();
        }
    },
    
    onEmailCoach: function(){
        var me = this;
        if (me.person.getCoachPrimaryEmailAddress()) {
            window.location = 'mailto:' + me.person.getCoachPrimaryEmailAddress();
        }
    }
});
