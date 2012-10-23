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
Ext.define('Ssp.controller.admin.campus.EditCampusViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	campusService: 'campusService',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	store: 'campusesStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'campusadmin',
    	url: null
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
	init: function() {
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		return this.callParent(arguments);
    },
	onSaveClick: function(button) {
		var me = this; 
		me.getView().getForm().updateRecord();
		me.getView().setLoading( true );
		me.campusService.saveCampus( me.model.data, {
			success: me.saveSuccess,
			failure: me.saveFailure,
			scope: me
		} );
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},

    saveSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
    },
    
    saveFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },	
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});