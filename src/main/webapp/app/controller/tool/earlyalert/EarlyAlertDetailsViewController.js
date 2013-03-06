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
Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertDetailsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        campusesStore: 'campusesStore',
        formUtils: 'formRendererUtils',
        model: 'currentEarlyAlert',
        personService: 'personService',
        reasonsStore: 'earlyAlertReasonsStore',
        suggestionsStore: 'earlyAlertSuggestionsStore',
        selectedSuggestionsStore: 'earlyAlertDetailsSuggestionsStore',
        appEventsController: 'appEventsController',
		earlyAlertResponse: 'currentEarlyAlertResponse',
		personLite: 'personLite',
		earlyAlertService: 'earlyAlertService'
		
    },
    config: {
        containerToLoadInto: 'tools',
        formToDisplay: 'earlyalert',
		earlyAlertResponseDetailsDisplay: 'earlyalertresponsedetails'
    },
    control: {
        'finishButton': {
            click: 'onFinishButtonClick'
        },
        'detailRespondButton': {
            click: 'onDetailRespondClick'
        },
		
		'detailResponseGridPanel': {
			cellClick : 'onGridClick'
		},
        
        earlyAlertSuggestionsList: '#earlyAlertSuggestionsList',
        campusField: '#campusField',
        earlyAlertReasonField: '#earlyAlertReasonField',
        statusField: '#statusField',
        createdByField: '#createdByField',
        closedByField: '#closedByField'
    },
    init: function() {
		
        var me=this;
        var selectedSuggestions=[];
        var campus = me.campusesStore.getById( me.model.get('campusId') );
        var reasonId = ((me.model.get('earlyAlertReasonIds') != null )?me.model.get('earlyAlertReasonIds')[0].id : me.model.get('earlyAlertReasonId') );
        var reason = me.reasonsStore.getById( reasonId );

        // Reset and populate general fields comments, etc.
        me.getView().getForm().reset();
        me.getView().loadRecord( me.model );
        
        me.getCreatedByField().setValue( me.model.getCreatedByPersonName() );
        
        // Early Alert Status: 'Open', 'Closed'
        me.getStatusField().setValue( ((me.model.get('closedDate'))? 'Closed' : 'Open') );
        
        // Campus
        me.getCampusField().setValue( ((campus)? campus.get('name') : "No Campus Defined") );
        
        // Reason
        me.getEarlyAlertReasonField().setValue( ((reason)? reason.get('name') : "No Reason Defined") );
        
        // Suggestions
        selectedSuggestions = me.formUtils.getSimpleItemsForDisplay( me.suggestionsStore, me.model.get('earlyAlertSuggestionIds'), 'Suggestions' );
        me.selectedSuggestionsStore.removeAll();
        me.selectedSuggestionsStore.loadData( selectedSuggestions );
        
        if ( me.model.get('closedById') != null )
        {
            if (me.model.get('closedById') != "")
            {
                me.getView().setLoading( true );
                me.personService.get( me.model.get('closedById'),{
                    success: me.getPersonSuccess,
                    failure: me.getPersonFailure,
                    scope: me
                });             
            }
        }
		
		var personId = me.personLite.get('id');
		
		me.earlyAlertService.getAllEarlyAlertResponses(personId, me.model.get('id'),
                        {success:me.getEarlyAlertResponsesSuccess, 
                 failure:me.getEarlyAlertResponsesFailure, 
                 scope: me} );
        
        return this.callParent(arguments);
    },
	
	 getEarlyAlertResponsesSuccess: function( r, scope){
        var me=scope;
        // clear the current Early Alert Response
        var earlyAlertResponse = new Ssp.model.tool.earlyalert.EarlyAlertResponse();
        me.earlyAlertResponse.data = earlyAlertResponse.data;
        me.getView().setLoading(false);
    },

    getEarlyAlertResponsesFailure: function( r, scope){
        var me=scope;
        me.getView().setLoading(false);
    }, 
	
    
    getPersonSuccess: function( r, scope ){
        var me=scope;
        var fullName="";
        me.getView().setLoading( false );
        if (r != null )
        {
            fullName=r.firstName + " " + (r.middleName ? r.middleName + " " : "") + r.lastName;
            me.getClosedByField().setValue( fullName );
        }
    },    
    
    getPersonFailure: function( response, scope ){
        var me=scope;  
        me.getView().setLoading( false );
    },   
    
    onFinishButtonClick: function( button ){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});        
    },
    
    onDetailRespondClick: function( button ){
        this.appEventsController.getApplication().fireEvent('goToResponse');
       
    },
	
	onGridClick: function(){
		var me=this;
		var record = Ext.getCmp('detailResponseGridPanel').getSelectionModel().getSelection()[0];
		
		for (prop in me.earlyAlertResponse.data)
                {
                    me.earlyAlertResponse.data[prop] = record.data[prop];
                }
        this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertResponseDetailsDisplay(), true, {});
       
    }
});