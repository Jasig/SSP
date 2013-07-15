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
Ext.define('Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampusEarlyAlertRouting',
    	campus: 'currentCampus',
    	peopleSearchLiteStore: 'peopleSearchLiteStore',
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
		me.getView().setLoading(true);
		me.getView().getForm().reset();

		if (me.model.get('id'))
		{

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
			var person = me.model.get('person');
            if ( person && person.id ) {
                me.getView().setLoading(true);
                me.personService.getSearchLite(person.id, {
                    success: me.routingPersonLookupSuccess,
                    failure: me.routingPersonLookupFailure,
                    scope: me
                });
            }
		} else {
			me.filterEarlyAlertReasonsForCreate();
			me.getView().getForm().loadRecord( me.model );
			me.showForm();
		}


		return me.callParent(arguments);
    },

	initEarlyAlertReasonsStore: function(postProcess, postProcessScope) {
		var me = this;
		var eaReasonsStore = me.getView().earlyAlertReasonsStore;
		eaReasonsStore.clearFilter(true);
		eaReasonsStore.load();
		if ( postProcess ) {
			postProcess.apply(postProcessScope ? postProcessScope : me, [eaReasonsStore]);
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

    routingPersonLookupSuccess: function( r, scope ){
    	var me=scope;
    	if (r && r.id )
    	{
    		me.peopleSearchLiteStore.loadData([r]);
    		me.getPersonCombo().setValue(me.model.get('person').id);
    	}
		me.showForm();
    },

    routingPersonLookupFailure: function( response, scope ){
    	var me=scope;
    	me.showForm();
    },

	showForm: function() {
		var me = this;
		me.getView().setLoading(false);
	},
    
	onSaveClick: function(button) {
		var me = this;
		var record, jsonData, url, selectedPersonId;
		url = me.url;	
		if ( me.getView().getForm().isValid() )
		{
			me.getView().getForm().updateRecord();
			record = me.model;			
			jsonData = record.data;
			
			// set the selected person
			if (me.getPersonCombo().value != "")
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
			Ext.Msg.alert('SSP Error', 'Please correct the errors before saving this item.');
		}
	},

	saveSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
	},

	saveFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},

	destroy: function() {
		var me = this;
		me.clearEarlyAlertReasonsFilters();
	},

	clearEarlyAlertReasonsFilters: function() {
		var me = this;
		me.getView().earlyAlertReasonsStore.clearFilter(true);
	},
	
	displayMain: function(){
		var me = this;
		me.clearEarlyAlertReasonsFilters();
		var comp = me.formUtils.loadDisplay(me.getContainerToLoadInto(), me.getFormToDisplay(), true, {});
	}
});