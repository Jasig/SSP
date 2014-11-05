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
Ext.define('Ssp.controller.admin.campus.EarlyAlertRoutingsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'campusEarlyAlertRoutingsStore',
    	service: 'campusEarlyAlertRoutingService',
    	formUtils: 'formRendererUtils',
    	campus: 'currentCampus',
    	model: 'currentCampusEarlyAlertRouting'
    },
    config: {
    	containerToLoadInto: 'campusearlyalertroutingsadmin',
    	formToDisplay: 'editcampusearlyalertrouting'
    },
    control: {  	
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		},

		'deleteButton': {
			click: 'deleteConfirmation'
		} 	
    },
	init: function() {
		var me=this;
		var campusId = me.campus.get('id');
		me.getView().setLoading( true );
		me.service.getAllCampusEarlyAlertRoutings( campusId, {
			success: me.getAllCampusEarlyAlertRoutingsSuccess,
			failure: me.getAllCampusEarlyAlertRoutingsFailure,
			scope: me
		});
		return me.callParent(arguments);
    },

    getAllCampusEarlyAlertRoutingsSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
    },
    
    getAllCampusEarlyAlertRoutingsFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },    
    
	onEditClick: function(button) {
		var me=this;
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	me.model.data=record.data;
        	me.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var me=this;
		var model = new Ssp.model.reference.CampusEarlyAlertRouting();
		me.model.data = model.data;
		me.displayEditor();
	},    
 
    deleteConfirmation: function( button ) {
   	   var me=this;
         var grid = button.up('grid');
         var store = grid.getStore();
         var selection = grid.getView().getSelectionModel().getSelection()[0];
         var message;
         if(selection != null)
         {
             if ( selection.get('id') ) 
             {
          	   message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
          	      	   
                 Ext.Msg.confirm({
         		     title:'Delete?',
         		     msg: message,
         		     buttons: Ext.Msg.YESNO,
         		     fn: me.deleteRecord,
         		     scope: me
         		   });
              }else{
           	   Ext.Msg.alert('SSP Error', 'Unable to delete item.'); 
              }        	 
         }else{
        	Ext.Msg.alert('SSP Error', 'Select an item to delete.'); 
         }
       },	
  	
  	deleteRecord: function( btnId ){
  		var me=this;
  		var campusId = me.campus.get('id');
  		var grid=me.getView();
  		var store = grid.getStore();
  	    var selection = grid.getView().getSelectionModel().getSelection()[0];
       	var id = selection.get('id');
       	if (btnId=="yes")
       	{
       		me.service.destroy( campusId, id, {
       			success: me.destroySuccess,
       			failure: me.destroyFailure,
       			scope: me
       		});
         }
  	},
  	
  	destroySuccess: function( r, id, scope ){
  		var me=scope;
  		var grid=me.getView();
  		var store = grid.getStore();
  		store.remove( store.getById( id ) );
  	},
  	
  	destroyFailure: function( response, scope ){
  		var me=scope;
  	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});