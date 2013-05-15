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
        earlyAlertResponseFormDisplay: 'earlyalertresponse',
        earlyAlertListDisplay: 'earlyalert',
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

        me.getView().setLoading( true );

        var personId = me.personLite.get('id');
        var earlyAlertId = me.model.get('id');

        var responseDispatcher = Ext.create('Ssp.util.ResponseDispatcher', {
            remainingOpNames: ['earlyalert','earlyalertresponses']
        });
        responseDispatcher.setAfterLastCallback(me.afterLastServiceResponse, me);

        if ( me.getView().reloadEarlyAlert ) {
            me.earlyAlertService.get(earlyAlertId, personId, {
               success: responseDispatcher.setSuccessCallback('earlyalert', me.getEarlyAlertSuccess, me),
               failure: responseDispatcher.setFailureCallback('earlyalert', me.getEarlyAlertFailure, me),
               scope: responseDispatcher
            });
        } else {
            // still need to go through the request dispatcher so we can fire the
            // 'all clear' once all sync and async data loads have completed
            responseDispatcher.setSuccessCallback('earlyalert', me.bindEarlyAlertToView, me).apply(responseDispatcher, null);
        }

        me.earlyAlertService.getAllEarlyAlertResponses(personId,
            earlyAlertId, {
                success: responseDispatcher.setSuccessCallback('earlyalertresponses', me.getEarlyAlertResponsesSuccess, me),
                failure: responseDispatcher.setFailureCallback('earlyalertresponses', me.getEarlyAlertResponsesFailure, me),
                scope: responseDispatcher
            });
        
        return this.callParent(arguments);
    },

    getEarlyAlertSuccess: function(response) {
        var me = this;
        // TODO I have no idea how correct this model state management is. The
        // property copying loop that EarlyAlertToolViewController.js and
        // onGridClick() uses doesn't work here for reasons that currently
        // elude me (e.g. date types are all wrong). IMHO, it seems all wrong
        // anyway... shouldn't this all be hidden behind a Ext Store? Playing
        // low-level reinitialization games with singleton Models can't possibly
        // be right... can it?
        var earlyAlert= new Ssp.model.tool.earlyalert.PersonEarlyAlert();
        me.model.data = earlyAlert.data;
        if ( response ) {
            me.model.populateFromGenericObject(response);
        } // TODO else what? a horror show is likely to ensue...
        me.bindEarlyAlertToView();
    },

    bindEarlyAlertToView: function() {
        var me = this;
        var campus = me.campusesStore.getById( me.model.get('campusId') );
        var reasonId;
        if ( me.model.get('earlyAlertReasonIds') && me.model.get('earlyAlertReasonIds').length ) {
            reasonId = me.model.get('earlyAlertReasonIds')[0].id;
        } else {
            reasonId = me.model.get('earlyAlertReasonId');
        }
        var reason;
        if ( reasonId ) {
            reason = me.reasonsStore.getById( reasonId );
        }

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
        var selectedSuggestions = me.formUtils.getSimpleItemsForDisplay( me.suggestionsStore, me.model.get('earlyAlertSuggestionIds'), 'Suggestions' );
        me.selectedSuggestionsStore.removeAll();
        me.selectedSuggestionsStore.loadData( selectedSuggestions );

        if ( me.model.get('closedById') ) {
            if ( me.model.get('closedByName') ) {
                me.getClosedByField().setValue( me.model.get('closedByName') );
            } else {
                me.getClosedByField().setValue('UNKNOWN');
            }
        }

    },

    getEarlyAlertFailure: function() {
        // no-op for now... error probably already  presented to end user as
        // part of the service call.
    },

    getEarlyAlertResponsesSuccess: function( r, scope){
        var me=scope;
        // clear the current Early Alert Response
        var earlyAlertResponse = new Ssp.model.tool.earlyalert.EarlyAlertResponse();
        me.earlyAlertResponse.data = earlyAlertResponse.data;
    },

    getEarlyAlertResponsesFailure: function( r, scope){
        // no-op for now... error probably already presented to end user as
        // part of the service call.
    },

    afterLastServiceResponse: function() {
        var me = this;
        me.getView().setLoading( false );
    },

    onFinishButtonClick: function( button ){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertListDisplay(), true, {});
    },

    onDetailRespondClick: function( button ){
        var me=this;
        me.loadEarlyAlertResponseForm(button);
    },

    loadEarlyAlertResponseForm: function(button){
        this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertResponseFormDisplay(), true, {});
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