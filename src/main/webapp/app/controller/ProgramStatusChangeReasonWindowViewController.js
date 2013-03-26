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
Ext.define('Ssp.controller.ProgramStatusChangeReasonWindowViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	appEventsController: 'appEventsController',
    	personLite: 'personLite',
    	programStatusChangeReasonsStore: 'programStatusChangeReasonsStore',
    	personProgramStatusService: 'personProgramStatusService'
    },
    control: {
		view: {
			show: 'onShow'
		},
		
		'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		programStatusChangeReasonCombo: '#programStatusChangeReasonCombo'
	},
	
	init: function() {	
    	var me=this;
		return me.callParent(arguments);
    },
    
    onShow: function(){
    	var me=this;
		me.getProgramStatusChangeReasonCombo().reset();
    	me.programStatusChangeReasonsStore.load({params:{start:0, limit:50}});
    },
    
    onSaveClick: function( button ){
	   	var me=this;
	   	var personId = me.personLite.get('id');
	   	var valid = me.getProgramStatusChangeReasonCombo().isValid();
	   	var reasonId = me.getProgramStatusChangeReasonCombo().value;
	   	if (valid && reasonId != "")
	   	{
		   	if (personId != "")
		   	{
		   		personProgramStatus = new Ssp.model.PersonProgramStatus();
		   		personProgramStatus.set('programStatusId',Ssp.util.Constants.NON_PARTICIPATING_PROGRAM_STATUS_ID);
		   		personProgramStatus.set('effectiveDate', new Date());
		   		personProgramStatus.set('programStatusChangeReasonId', reasonId );
			   	me.getView().setLoading( true );
		   		me.personProgramStatusService.save( 
		   				personId, 
		   				personProgramStatus.data, 
		   				{
	   					success: me.saveProgramStatusSuccess,
		               failure: me.saveProgramStatusFailure,
		               scope: me 
		        });
		   	}else{
		   		Ext.Msg.alert('SSP Error','Unable to determine student to set to No-Show status');
		   	}	   		
	   	}else{
	   		Ext.Msg.alert('SSP Error','Please correct the hilited errors in the form');
	   	}
    },

    saveProgramStatusSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.appEventsController.getApplication().fireEvent('setNonParticipatingProgramStatusComplete');
		me.close();
    },

    saveProgramStatusFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
        me.appEventsController.getApplication().fireEvent('setNonParticipatingProgramStatusComplete');
        me.close();
    },    
    
    onCancelClick: function( button ){
    	this.close();
    },
    
    close: function(){
    	this.getView().close();
    }
});