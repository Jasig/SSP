Ext.define('Ssp.controller.person.CaseloadAssignmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	apiProperties: 'apiProperties',
     	appointment: 'currentAppointment',
     	formUtils: 'formRendererUtils',
        person: 'currentPerson'
    },
    config: {
    	appointmentUrl: null,
    	person: 'currentPerson',
    	personUrl: null
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
		var assignmentScreen, items, id;
		var successFunc = function(response ,view){
			var r = Ext.decode(response.responseText);
			
			// load the person record
			me.person.populateFromGenericObject(r);
			
			me.getView().setTitle( 'Caseload Assignment: Edit Student - ' + me.person.getFullName() );
			
			// init the view
			caseloadAssignmentView.add(items);
		};

		id = me.person.get('id');
			
		me.personUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );
		me.appointmentUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personAppointment') );
		
		// retrieve the appointment screen and define items for the screen
		caseloadAssignmentView = Ext.ComponentQuery.query('.caseloadassignment')[0];
		
		items = [{ title: 'Personal',
        	       autoScroll: true,
        		   items: [{xtype: 'editperson'}]
        		},{
            		title: 'Appointment',
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
		
		// load the person record and init the view
		if (id.length > 0)
		{
			// load the person to edit
			me.apiProperties.makeRequest({
				url: me.personUrl+id,
				method: 'GET',
				successFunc: successFunc
			});
		}else{
			// adding a record, so simply init the view
			caseloadAssignmentView.add(items);

			me.getView().setTitle( 'Caseload Assignment: Add Student' );
		}
		
		return this.callParent(arguments);
    },
    
    onSaveClick: function(button){
		var me=this;
		var model=me.person;
		var id = model.get('id');
		var jsonData = new Object();
		
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

		var personSuccessFunc = function(response,view){
			var r = Ext.decode(response.responseText);
			var personId;
			console.log( 'CaseloadAssignmentViewController->onSaveClick - Save Person Success' );
			console.log( response );
			if (r.status==200)
			{
				personId = r.id;

				// assign the personId to use for the appointment
				me.appointmentUrl = me.appointmentUrl.replace('{id}', personId);

				// save the appointment
				var personAppointment = new Ssp.model.PersonAppointment();
				
				appointmentForm.updateRecord();
				
				personAppointment.set( 'startDate', me.appointment.getStartDate() );
				personAppointment.set( 'endDate', me.appointment.getEndDate() );

				// display the search screen
				me.loadStudentToolsView();	
				
			}else{
				Ext.Msg.alert('Error','Error saving student record. Please see your administrator for additional details.')
			}
		};
		
		if ( personForm.isValid() && coachForm.isValid() && appointmentForm.isValid() && anticipatedStartDateForm.isValid() && serviceReasonsForm.isValid() && specialServiceGroupsForm.isValid() && referralSourcesForm.isValid()) 
		{
			personForm.updateRecord();
			coachForm.updateRecord();
			anticipatedStartDateForm.updateRecord();			
			
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
			
			if (id=="")
			{
				// remove createdDate so no flags are thrown
				// from the API
				delete jsonData.createdDate;

				// TODO: Handle username field
				model.set('username',model.getFullName());				
				
				// create
				me.apiProperties.makeRequest({
	    			url: me.personUrl,
	    			method: 'POST',
	    			jsonData: model.data,
	    			successFunc: personSuccessFunc
	    		});				
			}else{
				// update
	    		me.apiProperties.makeRequest({
	    			url: me.personUrl+id,
	    			method: 'PUT',
	    			jsonData: model.data,
	    			successFunc: personSuccessFunc
	    		});	
			}
			
		}else{
			Ext.Msg.alert('Error','Please correct the errors in your form before continuing.');		
		}
    },
    
    getSelectedItemSelectorIdsForTransfer: function(values){
		var selectedIds = new Array();
    	Ext.Array.each(values,function(name, index){
    		if (name != undefined)
    			selectedIds.push({"id":name});
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
    	// Display the Student Record
    	//var comp = this.formUtils.loadDisplay('studentrecord', 'toolsmenu', true, {flex:1});    	
    	//comp = this.formUtils.loadDisplay('studentrecord', 'tools', false, {flex:4});
    	this.appEventsController.getApplication().fireEvent('displayStudentRecordView');
    }
});