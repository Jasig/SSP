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
Ext.define('Ssp.controller.admin.campus.CampusAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	campusService: 'campusService',
    	campusEarlyAlertRouting: 'currentCampusEarlyAlertRouting',
    	campusesStore: 'campusesAllStore',
    	unpagedStore: 'campusesAllUnpagedStore',
    	earlyAlertCoordinatorsStore: 'coachesStore',
    	earlyAlertReasonsStore: 'earlyAlertReasonsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
		adminSelectedIndex: 'adminSelectedIndex',
		apiProperties: 'apiProperties',
		storeUtils: 'storeUtils'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	campusEditorForm: 'editcampus',
    	campusEarlyAlertRoutingAdminForm: 'campusEarlyAlertRoutingsAdmin'
    },
    control: {
    	view: {
    		viewready: 'onViewReady'
    	},
    	
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
		
		me.earlyAlertCoordinatorsStore.load();
		me.earlyAlertReasonsStore.load();
		var params = {store:me.campusesStore, 
				unpagedStore:me.unpagedStore, 
				propertyName:"name", 
				grid:me.getView(),
				model:me.model,
				selectedIndex: me.adminSelectedIndex};
		me.storeUtils.onStoreUpdate(params);
		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	var me=this;
    	me.appEventsController.assignEvent({eventName: 'editCampusEarlyAlertRoutings', callBackFunc: me.onEditCampusEarlyAlertRoutings, scope: me});
    },    
 
    destroy: function() {
    	var me=this;
    	
    	me.appEventsController.removeEvent({eventName: 'editCampusEarlyAlertRoutings', callBackFunc: me.onEditCampusEarlyAlertRoutings, scope: me});

    	return me.callParent( arguments );
    },

    onEditCampusEarlyAlertRoutings: function(){
		var me=this;
    	var model = new Ssp.model.reference.CampusEarlyAlertRouting();
		me.campusEarlyAlertRouting.data = model.data;
		me.displayCampusEarlyAlertRoutingAdmin();
    }, 
    
	onEditClick: function(button) {
		var grid, record, idx;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
		this.adminSelectedIndex.set('value',-1);
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayCampusEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.Campus();
		this.model.data = model.data;
		this.adminSelectedIndex.set('value',-1);
		this.displayCampusEditor();
	},
	
    deleteConfirmation: function( button ) {
  	   var me=this;
        var grid = button.up('grid');
        var store = grid.getStore();
        var selection = grid.getView().getSelectionModel().getSelection()[0];
        var message;
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
      },	
 	
 	deleteRecord: function( btnId ){
 		var me=this;
 		var grid=me.getView();
 		var store = grid.getStore();
 	    var selection = grid.getView().getSelectionModel().getSelection()[0];
      	var id = selection.get('id');
      	if (btnId=="yes")
      	{
     		me.getView().setLoading( true );
     		me.campusService.destroy( id, {
     			success: me.destroyCampusSuccess,
     			failure: me.destroyCampusFailure,
     			scope: me
     		});
        }
 	},
 	
    destroyCampusSuccess: function( r, id, scope ) {
 		var me=scope;
 		var grid=me.getView();
 		var store = grid.getStore();
 		me.getView().setLoading( false );
 		store.remove( store.getById( id ) );
 	},

 	destroyCampusFailure: function( response, scope ) {
 		var me=scope;
 		me.getView().setLoading( false );
 	}, 

	displayCampusEarlyAlertRoutingAdmin: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getCampusEarlyAlertRoutingAdminForm(), true, {});
	},
 	
	displayCampusEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getCampusEditorForm(), true, {});
	}
});