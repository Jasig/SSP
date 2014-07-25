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
		'emailStudentButton': {
            click: 'onEmailStudentButtonClick'
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
		me.updateStudentRecord();
	},
    
    
    onCollapsed: function(){
    	var me=this;
    	me.appEventsController.getApplication().fireEvent('collapseStudentRecord');
    },
    onExpanded: function(){
    	var me=this;
    	me.appEventsController.getApplication().fireEvent('expandStudentRecord');
    },
    onEmailStudentButtonClick: function(button){
        var me = this;
   		me.emailStudentPopup = Ext.create('Ssp.view.EmailStudentView');
   		me.emailStudentPopup.show();
    },	
    getBaseUrl: function(id){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personWatch') );
		baseUrl = baseUrl.replace('{id}', id);
		return baseUrl;
    },    
    onWatchStudentButtonClick: function(button){
        var me = this;
        
   		var watchStudent = Ext.create('Ssp.model.WatchStudent');
   		watchStudent.set('studentId',me.person.get('id'));
   		watchStudent.set('watcherId',me.authenticatedPerson.get('id'));

		var url = me.getBaseUrl(me.authenticatedPerson.get('id'));
	    var successWatch = function( response ){
	    	if(response.responseText)
	    	{
	    		me.person.watchId = Ext.decode(response.responseText).id;
	    	}
	    	me.getWatchStudentButton().setText('<u>Un-Watch Student</u>');
	    	me.appEventsController.getApplication().fireEvent('onStudentWatchAction');
            Ext.Msg.alert('You are now watching this student');
	    };
	    var successUnWatch = function( response ){
	    	if(response.responseText)
	    	{
	    		me.person.watchId = null;
	    	}
	    	me.getWatchStudentButton().setText('<u>Watch Student</u>');
	        me.appEventsController.getApplication().fireEvent('onStudentWatchAction');
            Ext.Msg.alert('You are no longer watching this student');
	    };
	    var failure = function( response ){
	    	me.apiProperties.handleError( response );	 
	    };
	    if(me.getWatchStudentButton().getText() === '<u>Watch Student</u>')
	    {
			Ext.MessageBox.confirm('Watch Student', 'Are you sure you want to watch this student?', function(btn){
				if(btn === 'yes'){
		    		me.apiProperties.makeRequest({
		    			url: url, 
		    			method: 'POST',
		    			jsonData: watchStudent.data,
		    			successFunc: successWatch,
		    			failureFunc: failure,
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
			   	   	watchStudent.set('id',me.person.watchId);
		    		me.apiProperties.makeRequest({
		    			url: url+'/'+watchStudent.get('id'), 
		    			method: 'DELETE',
		    			jsonData: watchStudent.data,
		    			successFunc: successUnWatch,
		    			failureFunc: failure,
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
        me.appEventsController.getApplication().fireEvent('viewCoachHistory');
    },
    
    updateStudentRecord: function(args){
		var me = this;
    	if(args && args.person){
    		
            var successFunc = function(response, view){
            	if(response.responseText === '')
            	{
                    me.getWatchStudentButton().setText('<u>Watch Student</u>');
            	}
            	else
            	{
        	    	if(response.responseText)
        	    	{
        	    		me.person.watchId = Ext.decode(response.responseText).id;
        	    	}
            		me.getWatchStudentButton().setText('<u>Un-Watch Student</u>');
            	}
            };  		
            var failureFunc = function(response, view){
            	me.getWatchStudentButton().setText('<u>Watch Student</u>');
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

			me.showByPermission(me.getViewCoachingHistoryButton(), me.authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON'));
			me.showByPermission(me.getEmailStudentButton(), me.authenticatedPerson.hasAccess('EMAIL_STUDENT_BUTTON'));
			me.showByPermission(me.getWatchStudentButton(), me.authenticatedPerson.hasAccess('WATCHLIST_WATCH_BUTTON'));
			
			me.showElement(me.getStudentRecordEditButton());
			me.showElement(me.getEmailCoachButton());
			var fullName = args.person.getFullName();
			var coachName = args.person.getCoachFullName();
			if(me.getView())
	        	me.getView().setTitle('Student: ' + fullName + '          ' + '  -   ID#: ' + args.person.get('schoolId'));
			if(me.getEmailCoachButton())
	        	me.getEmailCoachButton().setText('<u>Coach: ' + coachName + '</u>');
			
		}else{
			me.hideElement(me.getWatchStudentButton());
			me.hideElement(me.getViewCoachingHistoryButton());
			me.hideElement(me.getStudentRecordEditButton());
			if(me.getView())
				me.getView().setTitle('');
			me.hideElement(me.getEmailStudentButton());
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