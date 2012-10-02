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
Ext.define('Ssp.controller.MainViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
        formUtils: 'formRendererUtils'
    },
    config: {
    	personButtonsVisible: true
    },
    control: {
    	view: {
    		add: 'setListeners'
    	},
    	
    	'studentViewNav': {
			click: 'onStudentRecordViewNavClick'
		},

		'adminViewNav': {
			click: 'onAdminViewNavClick'
		}
	},
	
	init: function() {
		this.displayStudentRecordView();
		
		return this.callParent(arguments);
    },
    
    setListeners: function(container, component, index, obj){
    	/**
		 * TODO: Figure out a better workaround than this for loading
		 * the listener that allows the display to be reset after
		 * saving the caseload assignment. This works because the Profile
		 * tool is dynamically added to the tools display after the interface
		 * is rendered. This event has to be assigned to the application after
		 * the application's onLaunch method has already fired.
		 * The issue with using the profile instance is that there may later
		 * be a requirement to load a different tool than the Profile first in the stack.
		 */
		if(component instanceof Ext.ClassManager.get('Ssp.view.tools.profile.Profile'))
		{
	       this.appEventsController.assignEvent({eventName: 'displayStudentRecordView', callBackFunc: this.onDisplayStudentRecordView, scope: this});			
		}
    },
    
    destroy: function() {
	   	this.appEventsController.removeEvent({eventName: 'displayStudentRecordView', callBackFunc: this.onDisplayStudentRecordView, scope: this});

        return this.callParent( arguments );
    },
    
    onDisplayStudentRecordView: function(){
    	this.displayStudentRecordView();
    },
    
    onStudentRecordViewNavClick: function(obj, eObj){ 
		this.displayStudentRecordView();
	},
	
	onAdminViewNavClick: function(obj, eObj){ 
		this.displayAdminView();
	},
    
    displayStudentRecordView: function(){
    	var me=this;
    	var mainView = Ext.ComponentQuery.query('mainview')[0];
    	var arrViewItems;
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [{xtype:'search',flex: 2},
					    {xtype: 'studentrecord', flex: 4}];
		
		mainView.add( arrViewItems );
    },
    
    displayAdminView: function() { 
    	var me=this;
    	var mainView = Ext.ComponentQuery.query('mainview')[0];
    	var arrViewItems;	
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [{xtype:'adminmain',
					     items:[{xtype: 'admintreemenu', flex:1 }, 
					            {xtype: 'adminforms', flex: 3 }],
					     flex:5}];
		
		mainView.add( arrViewItems );
    }
});