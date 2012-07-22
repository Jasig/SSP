Ext.define('Ssp.controller.admin.ConfidentialityDisclosureAgreementAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'confidentialityDisclosureAgreementsStore',
    	service: 'confidentialityDisclosureAgreementService'
    },
    
    control: {
		'saveButton': {
			click: 'save'
		}
    },
    
	init: function() {
		this.store.load({scope: this, callback: this.loadConfidentialityDisclosureAgreementResult});
		
		return this.callParent(arguments);
    }, 
    
    loadConfidentialityDisclosureAgreementResult: function(records, operation, success){
    	var model = new Ssp.model.reference.ConfidentialityDisclosureAgreement();
    	model.populateFromGenericObject(records[0].data);
    	this.getView().loadRecord( model );
    },
    
	save: function(button){
		var record, id, jsonData;
		var me=this;
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
	},
	
	saveSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading(false);
	},
	
    saveFailure: function( response, scope ){
    	var me=scope;  
		me.getView().setLoading(false);
    }
});