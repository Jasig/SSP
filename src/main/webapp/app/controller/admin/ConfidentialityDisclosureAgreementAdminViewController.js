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
Ext.define('Ssp.controller.admin.ConfidentialityDisclosureAgreementAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	store: 'confidentialityDisclosureAgreementsStore',
    	service: 'confidentialityDisclosureAgreementService'
    },
    
    control: {
		'saveButton': {
			click: 'save'
		},
		
		saveSuccessMessage: '#saveSuccessMessage'
    },
    
	init: function() {
		this.store.load({scope: this, callback: this.loadConfidentialityDisclosureAgreementResult});
		
		return this.callParent(arguments);
    }, 
    
    loadConfidentialityDisclosureAgreementResult: function(records, operation, success){
    	var model = new Ssp.model.reference.ConfidentialityDisclosureAgreement();
    	if (success)
    	{
        	if ( records.length > 0 )
        	{
        		model.populateFromGenericObject(records[0].data);
        	}    		
    	}
    	this.getView().loadRecord( model );
    },
    
	save: function(button){
		var record, id, jsonData;
		var me=this;
		if(me.getView().getForm().isValid())
		{
			var view = me.getView();
			view.getForm().updateRecord();
			record = view.getRecord();
			id = record.get('id');
			jsonData = record.data;
			
			view.setLoading(true);
	
			me.service.save( jsonData, {
				success: me.saveSuccess,
				failure: me.saveFailure,
				scope: me
			});
		} else {
			Ext.Msg.alert('SSP Error', 'There are errors highlighted in red'); 
		}
	},
	
	saveSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading(false);
		me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );
	},
	
    saveFailure: function( response, scope ){
    	var me=scope;  
		me.getView().setLoading(false);
    }
});