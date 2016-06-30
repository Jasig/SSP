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
        toolsStore: 'toolsStore',
        textStore: 'textStore',
        isTemplateFlag: 'isTemplateMode'
    },
    control: {
        view: {
            itemclick: 'onItemClick',
            viewready: 'onViewReady'
        }
    },
    
    init: function() {
        var me = this;
        return me.callParent(arguments);
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
        me.appEventsController.assignEvent({eventName: 'doSelectTool', callBackFunc: me.onItemClick, scope: me});
        me.appEventsController.assignEvent({eventName: 'emailCoach', callBackFunc: me.onEmailCoach, scope: me});
       
        if (me.personLite.get('id') != "") {
            me.loadPerson();
        }
    },

    doToolsNav: function(){
    	var me = this;
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
        me.appEventsController.removeEvent({eventName: 'doSelectTool', callBackFunc: me.onItemClick, scope: me});
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
		} else if(me.person.get('id') ) {
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
    	if (tool == -1) {
            me.loadFirstTool();
        } else {
            me.loadPerson();
        }
    	me.appEventsController.getApplication().fireEvent('updateStudentRecord',{'person':me.person});
	},

    getPersonFailure: function() {
	    var me = this;
        me.appEventsController.loadMaskOff();
    },
    
    onLoadIntake: function() {
        var me = this;
        me.loadIntake();
    },    
    onTransitionStudent: function(){
        var me = this;
        me.selectTool('journal');
        me.loadTool('journal');
    },
    
    loadFirstTool: function(){
        var me = this;
        var tool = me.toolsStore.getRange()[0];
        me.getView().getSelectionModel().select(tool);
        me.loadTool(tool.get('toolType'));
    },
    
    loadPerson: function(){
        var me = this;
        me.selectTool('profile');
        me.loadTool('profile');
    },
    loadIntake: function(){
        var me = this;
        me.selectTool('studentintake');
        me.loadTool('studentintake');
    },
    selectTool: function(toolType){
        var me = this;
        var tool = me.toolsStore.findExact('toolType', toolType)
        me.getView().getSelectionModel().select(tool);
    },
    
    onItemClick: function(grid, record, item, index){
        var me = this;

        //Listeners will need to return false to halt navigation from this point.
        var skipCallBack = me.appEventsController.getApplication().fireEvent('toolsNav', record, me);
        
        if (record.get('active') && me.personLite.get('id') != "" && skipCallBack) {
			if (record.get('toolType') === 'emailstudent'){
				if ( me.isAllowedToLoadTool(record.get('toolType')) ) {
					if ( me.emailStudentPopup ) {
						me.emailStudentPopup.destroy();
					}
					me.emailStudentPopup = Ext.create('Ssp.view.EmailStudentView');
					me.emailStudentPopup.show();
				} else {
					me.authenticatedPerson.showUnauthorizedAccessAlert();
				}
			} else if (record.get('toolType') === 'template' || record.get('toolType') === 'map') {
                    me.onTemplateOrMap(record.get('toolType')); //switch between Template non-student and MAP views
            } else {
                me.loadTool(record.get('toolType'));
			}
        } else {
            if (record.get('toolType') === 'toolcaseloadreassignment' && skipCallBack) {
                me.loadTool(record.get('toolType'));
            } else if (record.get('toolType') === 'template' && skipCallBack) {
                me.onTemplateOrMap(record.get('toolType'));   //able to select Template tool without a student selected
            } else if (skipCallBack) {
                Ext.Msg.alert(
                    me.textStore.getValueByCode('ssp.message.no-student-selected-title', 'No Student Selected'),
                    me.textStore.getValueByCode('ssp.message.no-student-selected-body',
                            'No student is selected. Please select a student before using this tool!')
                );
            } else {
                //Do nothing as not a valid navigation
            }
        }
    },

    isAllowedToLoadTool: function(toolType) {
        var me = this;
        return me.authenticatedPerson.hasAccess(toolType.toUpperCase() + '_TOOL');
    },

    loadTool: function(toolType){
        var me = this;
        var comp;

		//TAKE OUT LEGACY AND DOCUMENTS GRANT
        if (me.isAllowedToLoadTool(toolType)) {
            if (toolType == 'template') {
                me.onTemplateOrMap(toolType); //handles rare cases where doNav or this method is called without going through item click above
            } else {
                comp = me.formUtils.loadDisplay('tools', toolType, true, {});
            }
        } else {
            me.authenticatedPerson.showUnauthorizedAccessAlert();
        }
    },
    
    onEmailCoach: function(){
        var me = this;
        if (me.person.getCoachPrimaryEmailAddress()) {
            window.location = 'mailto:' + me.person.getCoachPrimaryEmailAddress();
        }
    },

    onTemplateOrMap: function(toolType) {
        var me = this;
        if (toolType && toolType === 'template') {
            isTemplateFlag = true;
        } else {
            isTemplateFlag = false;
        }
        me.loadTool('map'); //always load map tool which uses the flag set above
    }
});