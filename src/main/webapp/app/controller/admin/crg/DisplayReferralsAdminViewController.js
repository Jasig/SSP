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
Ext.define('Ssp.controller.admin.crg.DisplayReferralsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'challengeReferralsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentChallengeReferral'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editreferral'
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
		
		me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		me.store.load();
		
		return me.callParent(arguments);
    },

	onEditClick: function(button) {
		var grid, record;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.ChallengeReferral();
		this.model.data = model.data;
		this.displayEditor();
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
      		me.apiProperties.makeRequest({
        		   url: store.getProxy().url+"/"+id,
        		   method: 'DELETE',
        		   successFunc: function(response,responseText){
        			   store.remove( store.getById( id ) );
        		   }
        	    });
        }
 	},
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});