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
        formUtils: 'formRendererUtils',
		personService: 'personService',
		authenticatedPerson: 'authenticatedPerson',
		contactPersonStore: 'contactPersonStore'
    },
    config: {
    	personButtonsVisible: true
    },
    control: {
    	'studentViewNav': {
			click: 'onStudentRecordViewNavClick'
		},

		'adminViewNav': {
			click: 'onAdminViewNavClick'
		}
	},
	
	init: function() {
		var me=this;
		me.appEventsController.assignEvent({eventName: 'displayStudentRecordView', callBackFunc: this.onDisplayStudentRecordView, scope: this});
		me.displayStudentRecordView();
		
		me.personService.get( me.authenticatedPerson.get('id'), {
				success: me.getContactPersonSuccess,
				failure: me.getContactPersonFailure,
				scope: me			
			});
			
			
		return this.callParent(arguments);
    },

    destroy: function() {
	   	this.appEventsController.removeEvent({eventName: 'displayStudentRecordView', callBackFunc: this.onDisplayStudentRecordView, scope: this});
        return this.callParent( arguments );
    },
	
	getContactPersonSuccess: function( r, scope ){
    	var me=scope;
		var data = [];
		data.push(r);
		me.contactPersonStore.loadData(data);
	},
		
   getContactPersonFailure: function( response, scope ){
    	
    },
    
    onDisplayStudentRecordView: function(){
    	this.displayStudentRecordView();
    },
    
    onStudentRecordViewNavClick: function(obj, eObj){ 
    	var me = this;
        var skipCallBack = me.appEventsController.getApplication().fireEvent('studentsNav',me);  
    	if(skipCallBack)
    	{
    	      	me.displayStudentRecordView();
    	}
   	},
    	 	
   	onAdminViewNavClick: function(obj, eObj){ 
  		var me = this;
        var skipCallBack = this.appEventsController.getApplication().fireEvent('adminNav',me);  
        if(skipCallBack)
        {
          	me.displayAdminView();
        }
   	},

    
    displayStudentRecordView: function(){
    	var me=this;
    	var mainView = Ext.ComponentQuery.query('mainview')[0];
    	var arrViewItems;
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [
		                {xtype:'search',flex: 2},		                
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
					     items:[{xtype: 'admintreemenu', region:'west' ,  width: 275}, 
					            {xtype: 'adminforms', region:'center' }],
					     flex:5}];
		
		mainView.add( arrViewItems );
    }
});