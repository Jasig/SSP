Ext.define('Ssp.controller.admin.ConfidentialityDisclosureAgreementAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'confidentialityDisclosureAgreementsStore'
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
    	this.getView().loadRecord(model);
    },
    
	save: function(button){
		var record, id, jsonData;
		this.getView().getForm().updateRecord();
		record = this.getView().getRecord();
		id = record.get('id');
		jsonData = record.data;
		
		Ext.Ajax.request({
			url: this.store.getProxy().url+"/"+id,
			method: 'PUT',
			headers: { 'Content-Type': 'application/json' },
			jsonData: jsonData,
			success: function(response, view) {
				var r = Ext.decode(response.responseText);
				record.commit();
			},
			failure: this.apiProperties.handleError
		}, this);

	}
});