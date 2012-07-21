Ext.define('Ssp.controller.person.CaseloadAssignmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appointmentService: 'appointmentService',
    	appEventsController: 'appEventsController',
    	apiProperties: 'apiProperties',
     	appointment: 'currentAppointment',
     	formUtils: 'formRendererUtils',
        person: 'currentPerson',
        personLite: 'personLite',
        personService: 'personService',
        personProgramStatusService: 'personProgramStatusService',
        currentPersonAppointment: 'currentPersonAppointment'
    },
    control: {
    	'saveButton':{
    		click: 'onSaveClick'
    	},
    	
    	'cancelButton': {
    		click: 'onCancelClick'
    	},

    	'printButton':{
    		click: 'onPrintClick'
    	},
    	
    	'emailButton': {
    		click: 'onEmailClick'
    	}
    },
    
	init: function() {
		var me=this;
		var id = me.personLite.get('id');
		// initialize the appointment and personAppointment
		var personAppointment = new Ssp.model.PersonAppointment();
		var appointment = new Ssp.model.Appointment();
		me.appointment.data = appointment.data;
		me.currentPersonAppointment.data = personAppointment.data;
		
		// load the person record and init the view
		if (id.length > 0)
		{
			me.getView().setLoading( true );
			
	    	me.personService.getPerson( id, {success:me.getPersonSuccess, 
	    									  failure:me.getPersonFailure, 
	    									  scope: me} );
		}else{
			me.initForms();
			me.updateTitle();
		}
		
		me.appEventsController.assignEvent({eventName: 'studentNameChange', callBackFunc: this.onPersonNameChange, scope: this});    
		
		return this.callParent(arguments);
    },
    
    destroy: function(){
		this.appEventsController.removeEvent({eventName: 'studentNameChange', callBackFunc: this.onPersonNameChange, scope: this});    
    	
    	return this.callParent( arguments );
    },
  
    initForms: function(){
		// retrieve the appointment screen and define items for the screen
    	var caseloadAssignmentView, items; 
    	var caseloadAssignmentView = Ext.ComponentQuery.query('.caseloadassignment')[0];
		
		items = [{ title: 'Personal'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
        	       autoScroll: true,
        		   items: [{xtype: 'editperson'}]
        		},{
            		title: 'Appointment'+Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,
            		autoScroll: true,
            		items: [{xtype: 'personcoach'},
            		        {xtype:'personappointment'}]
        		},{
            		title: 'Special Service Groups',
            		autoScroll: true,
            		items: [{xtype: 'personspecialservicegroups'}]
        		},{
            		title: 'Referral Sources',
            		autoScroll: true,
            		items: [{xtype: 'personreferralsources'}]
        		},{
            		title: 'Reasons for Service',
            		autoScroll: true,
            		items: [{xtype: 'personservicereasons'}]
        		},{
            		title: 'Ability to Benefit/Anticipated Start Date',
            		autoScroll: true,
            		items: [{xtype: 'personanticipatedstartdate'}]
        		}];
    	
    	// adding a record, so simply init the view
		caseloadAssignmentView.add(items);
    },

    getPersonSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
		me.getCurrentAppointment();
		me.updateTitle();
    },
    
    getPersonFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },

    getCurrentAppointment: function(){
		var me=this;
		var personId = me.person.get('id');
    	if (personId != null)
		{
			
			me.getView().setLoading( true );
		    me.appointmentService.getCurrentAppointment( personId, {success:me.getAppointmentSuccess, 
			    						                             failure:me.getAppointmentFailure, 
			    						                             scope: me} );
		}else{
			me.initForms();
		}    	
    },
    
    getAppointmentSuccess: function( r, scope ){
		var me=scope;	
		me.getView().setLoading( false );
		me.initForms();
    },
    
    getAppointmentFailure: function( response, scope ){
    	var me=scope;   	
    	me.getView().setLoading( false );
    },    
    
    onPersonNameChange: function(){
    	this.updateTitle();
    },
    
    updateTitle: function(){
    	var me=this;
    	me.getView().setTitle( 'Caseload Assignment ' + ((me.person.get('id') != "")?"Edit":"Add") + ' - ' + me.person.getFullName());
    },
    
    onSaveClick: function(button){
		var me=this;
		var model=me.person;
		var id = model.get('id');
		var jsonData = new Object();
		var currentPersonAppointment;
		
		// edit person view
		var personView = Ext.ComponentQuery.query('.editperson')[0];
		var personForm = personView.getForm();

		// coach view
		var coachView = Ext.ComponentQuery.query('.personcoach')[0];
		var coachForm = coachView.getForm();		
		
		// appointment view
		var appointmentView = Ext.ComponentQuery.query('.personappointment')[0];
		var appointmentForm = appointmentView.getForm();
		
		// special service groups view
		var specialServiceGroupsView = Ext.ComponentQuery.query('.personspecialservicegroups')[0];
		var specialServiceGroupsForm = specialServiceGroupsView.getForm();
		var specialServiceGroupsItemSelector = Ext.ComponentQuery.query('#specialServiceGroupsItemSelector')[0];
		var selectedSpecialServiceGroups = [];
		
		// referral sources view
		var referralSourcesView = Ext.ComponentQuery.query('.personreferralsources')[0];
		var referralSourcesForm = referralSourcesView.getForm();
		var referralSourcesItemSelector = Ext.ComponentQuery.query('#referralSourcesItemSelector')[0];	
		var selectedReferralSources = [];
		
		// service reasons view
		var serviceReasonsView = Ext.ComponentQuery.query('.personservicereasons')[0];
		var serviceReasonsForm = serviceReasonsView.getForm();
		var selectedServiceReasons = [];

		// anticipated start date view
		var anticipatedStartDateView = Ext.ComponentQuery.query('.personanticipatedstartdate')[0];
		var anticipatedStartDateForm = anticipatedStartDateView.getForm();

		var formsToValidate = [personForm,
	                 coachForm,
	                 appointmentForm,
	                 anticipatedStartDateForm,
	                 serviceReasonsForm,
	                 specialServiceGroupsForm,
	                 referralSourcesForm];		

		var validateResult = me.formUtils.validateForms( formsToValidate );
		console.log( appointmentForm );
		console.log( appointmentForm.isValid() );
		// Validate all of the forms
		if ( validateResult.valid ) 
		{
			personForm.updateRecord();	
			anticipatedStartDateForm.updateRecord();

			//set coach and student type
			model.setCoachId( coachForm.findField('coachId').getValue() );
			model.setStudentTypeId( coachForm.findField('studentTypeId').getValue() );			
			
			// update the appointment
			appointmentForm.updateRecord();
						
			// set special service groups
			specialServiceGroupsFormValues = specialServiceGroupsItemSelector.getValue();
			selectedSpecialServiceGroups = me.getSelectedItemSelectorIdsForTransfer(specialServiceGroupsFormValues);
			if (selectedSpecialServiceGroups.length > 0)
			{
				model.set('specialServiceGroups', selectedSpecialServiceGroups);
			}else{
				model.data.specialServiceGroups=null;
			}

			// referral sources
			referralSourcesFormValues = referralSourcesItemSelector.getValue();
			selectedReferralSources = me.getSelectedItemSelectorIdsForTransfer(referralSourcesFormValues);
			if (selectedReferralSources.length > 0)
			{			
			   model.set('referralSources', selectedReferralSources);
			}else{
			   model.data.referralSources=null;
			}
			
			// set the service reasons
			serviceReasonsFormValues = serviceReasonsForm.getValues();
			selectedServiceReasons = me.formUtils.getSelectedIdsAsArray( serviceReasonsFormValues );
			if (selectedServiceReasons.length > 0)
			{
				model.set('serviceReasons', selectedServiceReasons);
			}else{
				model.data.serviceReasons=null;
			}
						
			// TODO: Handle username field
			if (model.get("username") == "")
				model.set('username',model.get('firstName')+'.'+model.get('lastName'));				

			if (model.get("userId") == "")
				model.set('userId',model.get('firstName')+'.'+model.get('lastName'));		
			
			me.getView().setLoading( true );
			
			jsonData = model.data;
			
			// TODO: Fix API to allow programStatus liteTO 
			delete jsonData.programStatuses;			

			me.personService.savePerson( jsonData, 
	    			                    {success:me.savePersonSuccess, 
				                         failure:me.savePersonFailure, 
				                         scope: me} );

		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
    },
    
    savePersonSuccess: function( r, scope ){
		var me=scope;
		var personProgramStatus;
		me.getView().setLoading( false );    	
    	if (r.id != "")
		{
    		// new student save an Active program status
    		if (me.person.get('id') == "")
    		{
    			// TODO: Get Active Program Status Id
    			personProgramStatus = new Ssp.model.PersonProgramStatus();
    			personProgramStatus.set('programStatusId','b2d12527-5056-a51a-8054-113116baab88');
    			personProgramStatus.set('effectiveDate', new Date());
    			console.log(r.id);
    			console.log(personProgramStatus.data);
    			me.personProgramStatusService.savePersonProgramStatus( 
    					r.id, 
    					personProgramStatus.data, 
    					{
    				success: me.saveProgramStatusSuccess,
                    failure: me.saveProgramStatusFailure,
                    scope: me 
                });
    		}
    		me.person.populateFromGenericObject( r );
    		me.saveAppointment();
		}else{
			Ext.Msg.alert('Error','Error saving student record. Please see your administrator for additional details.')
		}    	
    },
    
    savePersonFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );   	
    },

    saveAppointment: function(){
    	var me=this;
    	var jsonData, personId;
    	if (me.appointment.get('appointmentDate') != null && me.appointment.get('startTime') != null && me.appointment.get('endTime') !=null)
		{
    		me.currentPersonAppointment.setAppointment( me.appointment.getStartDate() , 
    				                                    me.appointment.getEndDate() );
    		jsonData = me.currentPersonAppointment.data;
    		personId = me.person.get('id');
			
    		// Fix startTime and endTime to represent appropriate date and time without GMT offset
			jsonData.startTime = me.formUtils.fixDateOffsetWithTime( jsonData.startTime );
			jsonData.endTime = me.formUtils.fixDateOffsetWithTime( jsonData.endTime );
 
    		me.appointmentService.saveAppointment( personId, 
    				                               jsonData, 
    				                               {success: me.saveAppointmentSuccess,
    			                                    failure: me.saveAppointmentFailure,
    			                                    scope: me } );
		}else{
			// no appointment is required
			// load students view
			me.loadStudentToolsView();			
		}
    },
    
    saveAppointmentSuccess: function( r, scope ){
		var me=scope; 
    	me.getView().setLoading( false );
		me.loadStudentToolsView();  	
    },    
    
    saveAppointmentFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );   	
    },    
    
    getSelectedItemSelectorIdsForTransfer: function(values){
		var selectedIds = new Array();
    	Ext.Array.each(values,function(name, index){
    		if ( name != undefined )
    		{
        		selectedIds.push({"id":name});    			
    		}
		});
    	return selectedIds;
    }, 
    
    onCancelClick: function(button){
		this.loadStudentToolsView();
    },
 
    onPrintClick: function(button){
		Ext.Msg.alert('Attention','This feature is not yet implemented');
    },    

    onEmailClick: function(button){
		Ext.Msg.alert('Attention','This feature is not yet implemented');
    },      
    
    loadStudentToolsView: function(){
    	this.appEventsController.getApplication().fireEvent('displayStudentRecordView');
    }
});