Ext.define('Ssp.controller.admin.ConfidentialityDisclosureAgreementAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	confidentialityDisclosureAgreementsStore: 'confidentialityDisclosureAgreementsStore'
    },
    
    control: {
		'saveButton': {
			click: 'save'
		}
    },
    
	init: function() {
		console.log('ConfidentialityDisclosureAgreementAdminViewController->init');
		this.confidentialityDisclosureAgreementsStore.load({scope: this, callback: this.loadConfidentialityDisclosureAgreementResult});
		
		return this.callParent(arguments);
    }, 
    
    loadConfidentialityDisclosureAgreementResult: function(records, operation, success){
    	var model = new Ssp.model.reference.ConfidentialityDisclosureAgreement();
    	model.populateFromGenericObject(records[0].data);
    	this.getView().loadRecord(model);
    },
    
	save: function(button){
		console.log('ConfidentialityDisclosureAgreementAdminViewController->save');

		var record = this.getView().getRecord();
		var id = record.get('id');
		var jsonData = record.data;
		
		Ext.Ajax.request({
			url: this.confidentialityDisclosureAgreementsStore.getProxy().url+id,
			method: 'PUT',
			headers: { 'Content-Type': 'application/json' },
			jsonData: jsonData,
			success: function(response, view) {
				var r = Ext.decode(response.responseText);
				record.commit();

				console.log('ConfidentialityDisclosureAgreementAdminViewController->save->success');
				console.log(r);
			},
			failure: this.apiProperties.handleError
		}, this);

	}
});