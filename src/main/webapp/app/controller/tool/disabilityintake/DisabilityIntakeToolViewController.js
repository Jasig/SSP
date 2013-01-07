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
Ext.define('Ssp.controller.tool.disabilityintake.DisabilityIntakeToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson',
        appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	disabilityAccommodationsStore: 'disabilityAccommodationsStore',
    	disabilityAgenciesStore: 'disabilityAgenciesStore',
    	disabilityIntake: 'currentDisabilityIntake',
    	disabilityStatusesStore: 'disabilityStatusesStore',
    	disabilityTypesStore: 'disabilityTypesStore',
        personLite: 'personLite',
        person: 'currentPerson',
        service: 'disabilityIntakeService', 	       
    }, 
    config: {
    	disabilityIntakeForm: null
    },
    control: {
		'saveButton': {
			click: 'onSaveClick'
		},
		
    	'cancelButton': {
    		click: 'onCancelClick'
    	},
    	
    	saveSuccessMessage: '#saveSuccessMessage'
	},
    
	init: function() {
		var me=this;	
		
		// Load the views dynamically
		// otherwise duplicate id's will be registered
		// on cancel
		me.initDisabilityIntakeViews();
	
		// This enables mapped text fields and mapped text areas
		// to be shown or hidden upon selection from a parent object
		// such as a dynamic check box.
		me.appEventsController.getApplication().addListener('dynamicCompChange', function( comp ){
			var tfArr = Ext.ComponentQuery.query('.mappedtextfield');
			var taArr = Ext.ComponentQuery.query('.mappedtextarea');
			
			// show or hide mapped text fields
			Ext.each(tfArr,function(item, index){
				if (comp.id==item.parentId)
				{
					if(comp.checked)
					{
						item.show();
						Ext.apply(item,{allowBlank:false});
					}else{
						item.hide();
						Ext.apply(item,{allowBlank:true});
					}
				}	
			},this);
			
			// show or hide mapped text area components
			Ext.each(taArr,function(item, index){
				if (comp.id==item.parentId)
				{
					if(comp.checked)
					{
						item.show();
						Ext.apply(item,{allowBlank:false});
					}else{
						item.hide();
						Ext.apply(item,{allowBlank:true});
					}
				}	
			},this);
		},me);
		
		// display loader
		me.getView().setLoading( true );
		
		me.service.get(me.personLite.get('id'),{
			success: me.getDisabilityIntakeSuccess,
			failure: me.getDisabilityIntakeFailure,
			scope: me
		});
		
		return me.callParent(arguments);
    },

    destroy: function() {
    	//this.appEventsController.removeEvent({eventName: 'dynamicCompChange', callBackFunc: this.onDynamicCompChange, scope: this});

        return this.callParent( arguments );
    },     
    
    initDisabilityIntakeViews: function(){
    	var me=this;
    	var items = [ Ext.createWidget('tabpanel', {
	        width: '100%',
	        height: '100%',
	        activeTab: 0,
			border: 0,
	        items: [ { title: 'General',
	        	       autoScroll: true,
	        		   items: [{xtype: 'disabilitygeneral'}]
	        		},{
	            		title: 'Agency Contacts',
	            		autoScroll: true,
	            		items: [{xtype: 'disabilityagencycontacts'}]
	        		},{
	            		title: 'Disability',
	            		autoScroll: true,
	            		items: [{xtype: 'disabilitytypes'}]
	        		},{
	            		title: 'Disposition',
	            		autoScroll: true,
	            		items: [{xtype: 'disabilitydisposition'}]
	        		},{
	            		title: 'Accommodations',
	            		autoScroll: true,
	            		items: [{xtype: 'disabilityaccommodations'}]
	        		}]
	    	})
	    
		];
    	
    	me.getView().add( items );
    },
    
    getDisabilityIntakeSuccess: function( r, scope ){
    	var me=scope;
    	var disabilityIntakeClass;
		
    	// hide the loader
    	me.getView().setLoading( false );
    	
    	if ( r != null )
    	{  		
        	disabilityIntakeClass = Ext.ModelManager.getModel('Ssp.model.tool.disabilityintake.DisabilityIntakeForm');
    		me.disabilityIntake.data = disabilityIntakeClass.getProxy().getReader().read( r ).records[0].data;
    		me.buildDisabilityIntake( me.disabilityIntake );    		
    	}else{
    		Ext.Msg.alert('Error','There was an error loading the Disability Intake form for this student.');
    	}
	},
	
	getDisabilityIntakeFailure: function( response, scope){
		var me=scope;
		me.getView().setLoading( false );
	},
    
	buildDisabilityIntake: function( formData ){
		var me=this; // formData
		
    	// PERSON RECORD
		var person = formData.data.person;
		var personDisability = formData.data.personDisability;
		var personDisabilityAgencies = formData.data.personDisabilityAgencies;
		var personDisabilityTypes = formData.data.personDisabilityTypes;
		var personDisabilityAccommodations = formData.data.personDisabilityAccommodations;
		
		var disabilityIntakeGeneralForm = Ext.getCmp('DisabilityIntakeGeneral');
		var disabilityIntakeAgencyContactNameForm = Ext.getCmp('DisabilityIntakeAgencyContactName');
		var disabilityIntakeDispositionForm = Ext.getCmp('DisabilityIntakeDisposition');
		
		/*
		 * For drawing reference check boxes dynamically
		 */
		var disabilityAgencyFormProps;
		var disabilityAgenciesAdditionalFieldsMap;	
		var disabilityTypesFormProps;
		var disabilityTypesAdditionalFieldsMap;
		var disabilityAccommodationsFormProps;
		var disabilityAccommodationsAdditionalFieldsMap;
		var defaultLabelWidth;

		// REFERENCE OBJECTS
		var disabilityAccommodations = me.formUtils.alphaSortByField( formData.data.referenceData.disabilityAccommodations, 'name' );
		var disabilityAgencies = me.formUtils.alphaSortByField( formData.data.referenceData.disabilityAgencies, 'name' );
		var disabilityStatuses =  me.formUtils.alphaSortByField( formData.data.referenceData.disabilityStatuses, 'name' );
		var disabilityTypes = me.formUtils.alphaSortByField( formData.data.referenceData.disabilityTypes, 'name');
		
		me.disabilityAgenciesStore.loadData( disabilityAgencies );
		me.disabilityAccommodationsStore.loadData( disabilityAccommodations );
		me.disabilityStatusesStore.loadData( disabilityStatuses );
		me.disabilityTypesStore.loadData( disabilityTypes );
		
		// LOAD RECORDS FOR EACH OF THE FORMS
		
		if ( personDisability != null && personDisability != undefined ){
			disabilityIntakeGeneralForm.loadRecord( personDisability );
		}
		
		if ( personDisability != null && personDisability != undefined ){
			disabilityIntakeAgencyContactNameForm.loadRecord( personDisability );
		}
		
		if ( personDisability != null && personDisability != undefined ){
			disabilityIntakeDispositionForm.loadRecord( personDisability );
		}	

		defaultLabelWidth = 150;

		// DEFINE DISABILITY AGENCY DYNAMIC FIELDS
		
		disabilityAgenciesAdditionalFieldsMap = [{parentId: Ssp.util.Constants.DISABILITY_AGENCY_OTHER_ID, 
											  parentName: "other",
											  name: "description", 
											  label: "Please Explain", 
											  fieldType: "mappedtextarea",
											  labelWidth: defaultLabelWidth}];
		
		disabilityAgencyFormProps = {
				mainComponentType: 'checkbox',
				formId: 'DisabilityIntakeDisabilityAgencies',
                fieldSetTitle: 'Select all agencies that apply',
                itemsArr: disabilityAgencies, 
                selectedItemsArr: personDisabilityAgencies, 
                idFieldName: 'id', 
                selectedIdFieldName: 'disabilityAgencyId',
                additionalFieldsMap: disabilityAgenciesAdditionalFieldsMap };
		
		me.formUtils.createForm( disabilityAgencyFormProps );
		
		// DEFINE DISABILITY TYPE DYNAMIC FIELDS
		
		disabilityTypesAdditionalFieldsMap = [];
		
		// For each disability type provide a mapped description field
		// this will allow the user to provide a description for the disability
		Ext.Array.each(disabilityTypes, function(name,index,self){
			var item = disabilityTypes[index];
			var map = {parentId: item.id, 
				  parentName: item.name,
				  name: "description", 
				  label: "Please Explain", 
				  fieldType: "mappedtextarea",
				  labelWidth: 80,
				  width: '350'};
			disabilityTypesAdditionalFieldsMap.push(map);
		});	
		
		disabilityTypesFormProps = {
		mainComponentType: 'checkbox',
		formId: 'DisabilityIntakeDisabilityTypes', 
		fieldSetTitle: 'Select all that apply',
		itemsArr: disabilityTypes, 
		selectedItemsArr: personDisabilityTypes, 
		idFieldName: 'id', 
		selectedIdFieldName: 'disabilityTypeId',
		additionalFieldsMap: disabilityTypesAdditionalFieldsMap };
		
		me.formUtils.createForm( disabilityTypesFormProps );

		// DEFINE DISABILITY ACCOMMODATION DYNAMIC FIELDS			

		disabilityAccommodationsAdditionalFieldsMap = [];
		
		// For each disability type provide a mapped description field
		// this will allow the user to provide a description for the disability
		Ext.Array.each(disabilityAccommodations, function(name,index,self){
			var map;
			var item = disabilityAccommodations[index];
			var fieldType = "mappedtextfield";
			if (item.useDescription === true)
			{
				// determine field type
				// long = mappedtextarea
				// short = mappedtextfield
				if (item.descriptionFieldType.toLowerCase() === "long")
				{
					fieldType = "mappedtextarea";
				}				
				
				map = {parentId: item.id, 
						  parentName: item.name,
						  name: "description", 
						  label: item.descriptionFieldLabel, 
						  fieldType: fieldType,
						  labelWidth: 100,
						  width: '350'};
				disabilityAccommodationsAdditionalFieldsMap.push(map);				
			}
		});		
		
		disabilityAccommodationsFormProps = {
			mainComponentType: 'checkbox',
			formId: 'DisabilityIntakeDisabilityAccommodations', 
			fieldSetTitle: 'Select all that apply',
			itemsArr: disabilityAccommodations, 
			selectedItemsArr: personDisabilityAccommodations, 
			idFieldName: 'id', 
			selectedIdFieldName: 'disabilityAccommodationId',
			additionalFieldsMap: disabilityAccommodationsAdditionalFieldsMap };
		
		me.formUtils.createForm( disabilityAccommodationsFormProps );
	},
	
	onSaveClick: function( button ) {
		var me=this;
		var formUtils = me.formUtils;

		var disabilityForm = Ext.getCmp('DisabilityIntakeGeneral').getForm();
		var disabilityIntakeAgencyContactNameForm = Ext.getCmp('DisabilityIntakeAgencyContactName').getForm();
		var disabilityDispositionForm = Ext.getCmp('DisabilityIntakeDisposition').getForm();
		var disabilityAgenciesForm = Ext.getCmp('DisabilityIntakeDisabilityAgencies').getForm();		
		var disabilityTypesForm = Ext.getCmp('DisabilityIntakeDisabilityTypes').getForm();		
		var disabilityAccommodationsForm = Ext.getCmp('DisabilityIntakeDisabilityAccommodations').getForm();		

		var disabilityAgenciesFormValues = null;
		var disabilityAccommodationsFormValues = null;
		var disabilityTypesFormValues = null;
		var disabilityIntakeFormModel = null;
		var personId = "";
		var disabilityIntakeData = {};
		
		var formsToValidate = [disabilityForm,
		             disabilityIntakeAgencyContactNameForm,
		             disabilityDispositionForm,
		             disabilityAgenciesForm,
		             disabilityAccommodationsForm,
		             disabilityTypesForm];

		// validate and save the model
		var validateResult = me.formUtils.validateForms( formsToValidate );
		if ( validateResult.valid )
		{
			// update the model with changes from the forms
			disabilityForm.updateRecord( me.disabilityIntake.get('personDisability') );
	
			// update the model with changes from the forms
			disabilityIntakeAgencyContactNameForm.updateRecord( me.disabilityIntake.get('personDisability') );			

			// update the model with changes from the forms
			disabilityDispositionForm.updateRecord( me.disabilityIntake.get('personDisability') );			
			
			// save the full model
			personId = me.disabilityIntake.get('person').data.id;
			disabilityIntakeData = {
				person: me.disabilityIntake.get('person').data,
				personDisability: me.disabilityIntake.get('personDisability').data,
				personDisabilityAgencies: [],
				personDisabilityAccommodations: [],
				personDisabilityTypes: []
			};
						
			// date saved as null is ok 
			if (disabilityIntakeData.personDisability.eligibleLetterDate != null)
			{
				// account for date offset
				disabilityIntakeData.personDisability.eligibleLetterDate = me.formUtils.fixDateOffset( disabilityIntakeData.personDisability.eligibleLetterDate );			
			}

			// date saved as null is ok 
			if (disabilityIntakeData.personDisability.ineligibleLetterDate != null)
			{
				// account for date offset
				disabilityIntakeData.personDisability.ineligibleLetterDate = me.formUtils.fixDateOffset( disabilityIntakeData.personDisability.ineligibleLetterDate );			
			}			
			
			// cleans properties that will be unable to be saved if not null
			// arrays set to strings should be null rather than string in saved
			// json
			disabilityIntakeData.person = me.person.setPropsNullForSave( disabilityIntakeData.person );			
			
			disabilityIntakeData.personDisability.personId = personId;
			// Clean personDisability props that won't save as empty string
			disabilityIntakeData.personDisability = me.disabilityIntake.get('personDisability').setPropsNullForSave( disabilityIntakeData.personDisability );			
			
			disabilityAgenciesFormValues = disabilityAgenciesForm.getValues();
			disabilityIntakeData.personDisabilityAgencies = formUtils.createTransferObjectsFromSelectedValues('disabilityAgencyId', disabilityAgenciesFormValues, personId);

			disabilityTypesFormValues = disabilityTypesForm.getValues();
			disabilityIntakeData.personDisabilityTypes = formUtils.createTransferObjectsFromSelectedValues('disabilityTypeId', disabilityTypesFormValues, personId);
			
			disabilityAccommodationsFormValues = disabilityAccommodationsForm.getValues();
			disabilityIntakeData.personDisabilityAccommodations = formUtils.createTransferObjectsFromSelectedValues('disabilityAccommodationId', disabilityAccommodationsFormValues, personId);
			
			// display loader
			me.getView().setLoading( true );
			
			me.service.save(me.personLite.get('id'), disabilityIntakeData, {
				success: me.saveDisabilityIntakeSuccess,
				failure: me.saveDisabilityIntakeFailure,
				scope: me
			});

		}else{
			me.formUtils.displayErrors( validateResult.fields );
		}
	},
	
	saveDisabilityIntakeSuccess: function(r, scope) {
		var me=scope;

		me.getView().setLoading( false );
		
		if( r.success == true ) {
			me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );							
		}								
	},
	
	saveDisabilityIntakeFailure: function(response, scope) {
		var me=scope;
		me.getView().setLoading( false );							
	},	
	
	onCancelClick: function( button ){
		var me=this;
		me.getView().removeAll();
		me.initDisabilityIntakeViews();
		me.buildDisabilityIntake( me.disabilityIntake );	
	}
});