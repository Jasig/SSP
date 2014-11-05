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
Ext.define('Ssp.controller.StudentRecordViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
	inject: {
		appEventsController: 'appEventsController',
		formUtils: 'formRendererUtils',
		apiProperties: 'apiProperties',
        person: 'currentPerson',
        authenticatedPerson: 'authenticatedPerson',
    	confidentialityLevelsStore: 'confidentialityLevelsAllUnpagedStore'
        
	},
	
    control: {
		view: {
			collapse: 'onExpanded',
			expand: 'onCollapsed',
			afterlayout: {
				fn: 'onAfterLayout',
				single: true
			}
		},
		'watchStudentButton': {
            click: 'onWatchStudentButtonClick'
        },	
		'studentRecordEditButton': {
            click: 'onStudentRecordEditButtonClick'
        },
		'viewCoachingHistoryButton': {
            click: 'onViewCoachingHistoryButtonClick'
        },
        'emailCoachButton': {
            click: 'onEmailCoachButtonClick'
        }
	},
	
    init: function() {
    	var me=this;
		me.confidentialityLevelsStore.load();
		me.appEventsController.assignEvent({eventName: 'doPersonToolbarEdit', callBackFunc: me.studentRecordEdit, scope: me});

        me.appEventsController.assignEvent({
            eventName: 'updateStudentRecord',
            callBackFunc: me.updateStudentRecord,
            scope: me
        });
        me.updateStudentRecord();
 		return this.callParent(arguments);
    },
    
	onAfterLayout: function(){
		var me = this;
		if (me.person.get('id')) {
		    me.updateStudentRecord({'person':me.person});
		} else {
		    me.updateStudentRecord();
		}
	},
    
    
    onCollapsed: function(){
    	var me=this;
    	me.appEventsController.getApplication().fireEvent('collapseStudentRecord');
    },
    onExpanded: function(){
    	var me=this;
    	me.appEventsController.getApplication().fireEvent('expandStudentRecord');
    },
    
    getBaseUrl: function(id){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personWatch') );
		baseUrl = baseUrl.replace('{id}', id);
		return baseUrl;
    },
    updateWatchUiWithId: function(id, isChanged) {
        var me = this;
        me.person.watchId = id;
        if ( id ) {
            me.getWatchStudentButton().setText('Un-Watch Student');
        } else {
            me.getWatchStudentButton().setText('Watch Student');
        }
        if ( isChanged ) {
            me.appEventsController.getApplication().fireEvent('onStudentWatchAction');
        }
        me.showByPermission(me.getWatchStudentButton(), me.authenticatedPerson.hasAccess('WATCHLIST_WATCH_BUTTON'));
        me.appEventsController.loadMaskOff();
    },
    successWatch: function( response ){
        var me = this;
        me.updateWatchUiWithId(Ext.decode(response.responseText).id, true);
    },
    successUnWatch: function( response ){
        var me = this;
        me.updateWatchUiWithId('', true);
    },
    fatalWatchUnWatchFailure: function(action) {
        var me = this;
        Ext.Msg.alert('SSP Error','There was an issue procesing your ' + action + ' request. Please reload the page and ' +
            'try again, or contact your system administrator');
        me.showByPermission(me.getWatchStudentButton(), me.authenticatedPerson.hasAccess('WATCHLIST_WATCH_BUTTON'));
        me.appEventsController.loadMaskOff();
    },
    failureWatch: function(response, options) {
        var me = this;
        if ( response && response.status === 409 && response.responseText &&
            response.responseText.indexOf('Found existing {org.jasig.ssp.model.WatchStudent}') ) {
            // already watching. just reload the underlying state and link
            me.updateWatchLink({ person: me.person });
        } else {
            me.fatalWatchUnWatchFailure('Watch');
        }
    },
    failureUnWatch: function(response, options) {
        var me = this;
        if ( response && response.status === 404 ) {
            // already not watching, so might as well be considered a success
            me.successUnWatch(response);
        } else {
            me.fatalWatchUnWatchFailure('Unwatch');
        }
    },
    onWatchStudentButtonClick: function(button){
        var me = this;
        
   		var watchStudent = Ext.create('Ssp.model.WatchStudent');
   		watchStudent.set('studentId',me.person.get('id'));
   		watchStudent.set('watcherId',me.authenticatedPerson.get('id'));

		var url = me.getBaseUrl(me.authenticatedPerson.get('id'));
	    if(!(me.person.watchId))
	    {
			Ext.MessageBox.confirm('Watch Student', 'Are you sure you want to watch this student?', function(btn){
				if(btn === 'yes'){
		    		me.getWatchStudentButton().hide();
		    		me.appEventsController.loadMaskOn();
		    		me.apiProperties.makeRequest({
		    			url: url, 
		    			method: 'POST',
		    			jsonData: watchStudent.data,
		    			successFunc: me.successWatch,
		    			failureFunc: me.failureWatch,
		    			scope: me
		    		});	
				} else if(btn === 'no') {
				   return;
				}
			});	

	    }
	    else
	    {
			Ext.MessageBox.confirm('Un-Watch Student', 'Are you sure you want to stop watching this student?', function(btn){
				if(btn === 'yes'){
			   	   	me.getWatchStudentButton().hide();
			   	   	me.appEventsController.loadMaskOn();
			   	   	watchStudent.set('id',me.person.watchId);
		    		me.apiProperties.makeRequest({
		    			url: url+'/'+watchStudent.get('id'), 
		    			method: 'DELETE',
		    			jsonData: watchStudent.data,
		    			successFunc: me.successUnWatch,
		    			failureFunc: me.failureUnWatch,
		    			scope: me
		    		});	
				} else if(btn === 'no') {
				   return;
				}
			});
    	
	    }
    },	   
	onStudentRecordEditButtonClick: function(button){
        var me=this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('personToolbarEdit',me);  
        if(skipCallBack)
        {
        	me.studentRecordEdit();
        }
    },
    studentRecordEdit: function(){
    	var me=this;
    	var comp = me.formUtils.loadDisplay('mainview', 'caseloadassignment', true, {flex:1}); 
    },
	onEmailCoachButtonClick: function(button){
        var me=this;
        me.appEventsController.getApplication().fireEvent('emailCoach');
    },
	onViewCoachingHistoryButtonClick: function(button){
        var me=this;
        var personId = me.person.get('id');
        var personViewHistoryUrl = (me.apiProperties.getAPIContext() + me.apiProperties.getItemUrl('personViewHistory')).replace('{id}',personId);
        me.apiProperties.getReporter().load({
            url:personViewHistoryUrl,
            params: ""
        });
    },
	updateWatchLink: function(args) {
		var me = this;
		if(me.authenticatedPerson.hasAccess('WATCHLIST_TOOL'))
		{
			me.getWatchStudentButton().hide();

			var successFunc = function(response, view){
				var watcherId = '';
				if(response && response.responseText) {
					watcherId = Ext.decode(response.responseText).id;
				}
				me.updateWatchUiWithId(watcherId, false);
			};
			var failureFunc = function(response, view){
				me.updateWatchUiWithId('', false);
			};
			studentId = args.person.get('id');
			var url = me.getBaseUrl(me.authenticatedPerson.get('id'));

			url = url + '/' + studentId;

			me.apiProperties.makeRequest({
				url: url,
				method: 'GET',
				successFunc: successFunc,
				failureFunc: failureFunc
			});
		}
	},
    updateStudentRecord: function(args){
		var me = this;
		if(args && args.person && args.person.get("id") && args.person.get("id").length && args.person.get("id").length > 0){
    		me.updateWatchLink(args);

			me.showByPermission(me.getViewCoachingHistoryButton(), me.authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON'));

			me.showElement(me.getStudentRecordEditButton());
			me.showElement(me.getEmailCoachButton());
			var fullName = args.person.getFullName();
			var coachName = args.person.getCoachFullName();
			if(args.person.get('homePhone') != '')
				var homePhone = '    ' + args.person.get('homePhone') + ' (H)' ;
			else
				var homePhone = '';
			if(args.person.get('cellPhone') != '')
				var cellPhone = '    ' + args.person.get('cellPhone') + ' (C)';
			else
				var cellPhone = '';
			
			
			if(me.getView())
	        	me.getView().setTitle(fullName + '          ' + '  -   ID#: ' + args.person.get('schoolId') + homePhone + cellPhone);
			if(me.getEmailCoachButton())
	        	me.getEmailCoachButton().setText('Coach: ' + coachName);
			
		}else{
			me.hideElement(me.getWatchStudentButton());
			me.hideElement(me.getViewCoachingHistoryButton());
			me.hideElement(me.getStudentRecordEditButton());
			if(me.getView())
				me.getView().setTitle('');
			me.hideElement(me.getEmailCoachButton());
		}
    },

	showByPermission:function(element, hasPermission){
		var me = this;
		if(hasPermission)
			me.showElement(element);
		else
			me.hideElement(element);
	},

	hideElement: function(element){
		if(element)
			element.hide();
	},
	
	showElement: function(element){
		if(element)
			element.show();
	},
    
    destroy: function(){
		var me = this;
    	 me.appEventsController.removeEvent({
             eventName: 'updateStudentRecord',
             callBackFunc: me.updateStudentRecord,
             scope: me
         });
    	 
 		me.appEventsController.removeEvent({eventName: 'doPersonToolbarEdit', callBackFunc: me.studentRecordEdit, scope: me});

    	 return me.callParent(arguments);
    }
});
