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
Ext.define('Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampusEarlyAlertRouting',
    	campus: 'currentCampus',
    	coachesStore: 'coachesStore',
    	service: 'campusEarlyAlertRoutingService',
        personService: 'personService'
    },
    config: {
    	containerToLoadInto: 'campusearlyalertroutingsadmin',
    	formToDisplay: 'earlyalertroutingsadmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		personCombo: '#personCombo'
    },
    
	init: function() {
		var me=this;		
		me.getView().getForm().reset();
		me.missingEaRoutingPerson = null;

		 me.coachesStore.clearFilter(true);
		if ( me.model.get('id') ) {
			me.filterEarlyAlertReasonsForEdit();

			// needs to be loaded before the person lookup callbacks, which
			// manipulate form state.
			me.getView().getForm().loadRecord( me.model );

            // EA routing model has a person ID, first name, and last name but
            // our form represents this association in an incremental search
            // box. The latter needs to be backed by something resembling a
            // Ssp.model.PersonSearchLite model. Can't the latter directly
            // from our JSON. Previous impls used the search API to find the
            // person by fname+lname. SSP-564 changed this to an ID lookup
            // for reliability. In the future consider just passing a minimal
            // PersonSearchLite mapped from EA routing JSON model. Should
            // be much more efficient. But not sure about unexpected
            // compatibility problems with peopleSearchLiteStore.
			// EDIT: The above no longer is true. SSP-1408 Changes Person field 
			// from search to combobox for coaches only.
			var personToLookUp = me.model.get('person');
            if ( personToLookUp && personToLookUp.id ) {                
                if ( !(me.coachesStore.getById(personToLookUp.id)) ) {
                	
                	me.lookupMissingEaRoutingPerson(personToLookUp.id, 
                			me.afterMissingEaRoutingPersonLookup, me );
                } else {
                    me.getPersonCombo().setValue(personToLookUp.id);
                	me.showForm();
                }
             }            
		} else {
			me.filterEarlyAlertReasonsForCreate();			
			me.showForm();
		}

		return me.callParent(arguments);
    },
    
    lookupMissingEaRoutingPerson: function(eaRoutingId, after, afterScope) {
    	var me = this;
    	me.missingEaRoutingPerson = Ext.create('Ssp.model.Coach', { 
    		id: eaRoutingId,
    		firstName: "UNKNOWN",
    		lastName: "PERSON"
    	});
    	me.personService.getLite(eaRoutingId, {
    		success: function(person) {
    			if ( person ) {
    				me.missingEaRoutingPerson = Ext.create('Ssp.model.Coach', {
    					id: person.id,
    					firstName: person.firstName,
    					lastName: person.lastName    					
    				});
    me.getPersonCombo().setValue(person.id);			}
    			after.apply(afterScope);
    		},
    		failure: function() {
    			//most likely can just proceed
    			after.apply(afterScope);
    		}
    	});    	
    },
    
    afterMissingEaRoutingPersonLookup: function() {
    	var me = this;
    	var coachStore = me.getView().coachesStore;
    	coachStore.add(me.missingEaRoutingPerson);
    	coachStore.sort('lastName', 'ASC');
    	me.getPersonCombo().setValue(me.missingEaRoutingPerson);
    	me.showForm();
    },
    
    maybeClearMissingEaRoutingPerson: function() {
    	var me = this;
    	if ( me.missingEaRoutingPerson ) {
    		var coachStore = me.getView().coachesStore;
    		coachStore.remove(me.missingEaRoutingPerson);
    		coachStore.sort('lastName', 'ASC');
    		me.missingEaRoutingPerson = null;
    	}
    },

	initEarlyAlertReasonsStore: function(postProcess, postProcessScope) {
		var me = this;
		// cache on 'me' b/c we need to clear the filter on destroy, but
		// by that time the view is gone
		me.eaReasonsStore = me.getView().earlyAlertReasonsStore;
		me.eaReasonsStore.clearFilter(true);
		me.eaReasonsStore.load();
		if ( postProcess ) {
			postProcess.apply(postProcessScope ? postProcessScope : me, [me.eaReasonsStore]);
		}
	},

	filterEarlyAlertReasonsForEdit: function() {
		var me = this;
		me.initEarlyAlertReasonsStore(function(eaReasonsStore) {
			me.formUtils.applyAssociativeStoreFilter(eaReasonsStore, me.model.get('earlyAlertReasonId'));
		}, me);
	},

	filterEarlyAlertReasonsForCreate: function() {
		var me = this;
		me.initEarlyAlertReasonsStore(function(eaReasonsStore) {
			me.formUtils.applyActiveOnlyFilter(eaReasonsStore);
		}, me);
	},

	showForm: function() {
		var me = this;
		me.getView().getForm().loadRecord( this.model );
	},
    
	onSaveClick: function(button) {
		var me = this;
		var record, jsonData, url, selectedPersonId;
		url = me.url;	
        var groupName = me.getView().getForm().findField('groupName').getValue();
        var groupEmail = me.getView().getForm().findField('groupEmail').getValue();
        var personCombo = me.getView().getForm().findField('personCombo').getValue();
        var earlyAlertReasonId = me.getView().getForm().findField('earlyAlertReasonId').getValue();

		if (earlyAlertReasonId && ((groupName && groupEmail) || personCombo) )
		{
			if (! me.getView().getForm().isValid() )
			{
				Ext.Msg.alert('SSP Error', 'Please enter a valid email address.');
				return;
			}

			me.getView().getForm().updateRecord();
			record = me.model;			
			jsonData = record.data;

			// set the selected person
			if ( me.getPersonCombo().value )
			{
				jsonData.person={ id:me.getPersonCombo().value };
			}else{	
				jsonData.person=null;
			}
			
			me.getView().setLoading( true );
			me.service.saveCampusEarlyAlertRouting( me.campus.get('id'), jsonData, {
				success: me.saveSuccess,
				failure: me.saveFailure,
				scope: me
			});			
		}else{
			Ext.Msg.alert('SSP Error', 'Please enter a Reason and either a Group name and email OR choose a Person.');
		}
	},

	saveSuccess: function( r, scope ) {
		var me=scope;
		me.maybeClearMissingEaRoutingPerson();
		me.getView().setLoading( false );
		me.displayMain();
	},

	saveFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
	},
	
	onCancelClick: function(button){
		var me = this;
		me.maybeClearMissingEaRoutingPerson();
		me.displayMain();
	},

	destroy: function() {
		var me = this;
		me.maybeClearMissingEaRoutingPerson();
		me.clearEarlyAlertReasonsFilters();		
		return me.callParent(arguments);
	},

	clearEarlyAlertReasonsFilters: function() {
		var me = this;
		// don't try to get the store from the view... it's probably already
		// been destroy()ed
		if ( me.eaReasonsStore ) {
			me.eaReasonsStore.clearFilter(true);
		}
	},
	
	displayMain: function(){
		var me = this;
		me.clearEarlyAlertReasonsFilters();
		var comp = me.formUtils.loadDisplay(me.getContainerToLoadInto(), me.getFormToDisplay(), true, {});
	}
});