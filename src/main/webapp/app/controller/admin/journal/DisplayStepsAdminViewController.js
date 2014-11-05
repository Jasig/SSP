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
Ext.define('Ssp.controller.admin.journal.DisplayStepsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'journalStepsAllStore',
    	unpagedStore: 'journalStepsAllUnpagedStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentJournalStep',
    	storeUtils: 'storeUtils',
		adminSelectedIndex: 'adminSelectedIndex'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'editjournalstep'
    },
    control: {  	
    	'editButton': {
			click: 'onEditClick'
		},
		
		'addButton': {
			click: 'onAddClick'
		}  	
    },       
    
	init: function() {
		var me=this;
		
		 var params = {store:me.store, 
					unpagedStore:me.unpagedStore, 
					propertyName:"name", 
					grid:me.getView(),
					model:me.model,
					selectedIndex: me.adminSelectedIndex};
		me.storeUtils.onStoreUpdate(params);
		
		return me.callParent(arguments);
    },    
    
	onEditClick: function(button) {
		var grid, record,idx;
		grid = button.up('grid');
		record = grid.getView().getSelectionModel().getSelection()[0];
		this.adminSelectedIndex.set('value',-1);
        if (record) 
        {		
        	this.model.data=record.data;
        	this.displayEditor();
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }
	},
	
	onAddClick: function(button){
		var model = new Ssp.model.reference.JournalStep();
		this.model.data = model.data;
		
		this.adminSelectedIndex.set('value',-1);
		this.displayEditor();
	},
	
    
	
	displayEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});