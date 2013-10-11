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
    	store: 'campusesStore',
		personService: 'personService',
		adminSelectedIndex: 'adminSelectedIndex'
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
		var me = this;
		me.getView().getForm().reset();
		me.missingEaCoord = null;
		me.eaCoordStore = me.getView().store;
		if ( me.model.get('id') ) {
			// Must be edit mode. Make best effort at trying to find the
			// associated coach and inject it into the view. Currently
			// associated coach might not be in the store already, e.g. if she
			// were "demoted" in the directory system we query to get all
			// coaches
			var eaCoordId = me.model.get('earlyAlertCoordinatorId');
			if ( !(me.eaCoordStore.getById(eaCoordId)) ) {
				me.lookupMissingEaCoordinator(eaCoordId, me.afterMissingEaCoordinatorLookup, me);
			} else {
				me.showForm();
			}
		} else {
			me.showForm();
		}
		return me.callParent(arguments);
	},
	destroy: function() {
		var me = this;
		me.maybeClearMissingEaCoordinator();
		return me.callParent(arguments);
	},
	lookupMissingEaCoordinator: function(eaCoordId, after, afterScope) {
		var me = this;
		me.missingEaCoord = Ext.create('Ssp.model.Coach', {
			id: eaCoordId,
			firstName: "UNKNOWN",
			lastName: "PERSON"
		});
		me.personService.getLite(eaCoordId, {
			success: function(person) {
				if (person) {
					me.missingEaCoord = Ext.create('Ssp.model.Coach', {
						id: person.id,
						firstName: person.firstName,
						lastName: person.lastName
					});
				}
				after.apply(afterScope);
			},
			failure: function() {
				// likely we can just proceed
				after.apply(afterScope);
			}
		});
	},
	afterMissingEaCoordinatorLookup: function() {
		var me = this;
		me.eaCoordStore.add(me.missingEaCoord);
		me.eaCoordStore.sort('lastName', 'ASC');
		me.showForm();
	},
	maybeClearMissingEaCoordinator: function() {
		var me = this;
		if ( me.missingEaCoord && me.eaCoordStore) {
			me.eaCoordStore.remove(me.missingEaCoord);
			me.eaCoordStore.sort('lastName', 'ASC');
			me.missingEaCoord = null;
		}
	},
	showForm: function() {
		var me = this;
		me.getView().getForm().loadRecord( this.model );
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
		var me = this;
		me.maybeClearMissingEaCoordinator();
		this.displayMain();
	},

    saveSuccess: function( r, scope ){
		var me=scope;
		me.maybeClearMissingEaCoordinator();
		
				var rowid = r['id'];
				me.store.load({
					params: {
						limit: 500
					},
					callback: function(records) {
						
						var rowidx = me.store.find('id',rowid);
						
						me.adminSelectedIndex.set('value',rowidx);
						
						me.displayMain();
					}
				});
		me.getView().setLoading( false );
		
    },
    
    saveFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    },	
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});