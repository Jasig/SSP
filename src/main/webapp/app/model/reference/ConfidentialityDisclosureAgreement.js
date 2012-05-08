Ext.define('Ssp.model.reference.ConfidentialityDisclosureAgreement', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [],
	autoLoad: false,
 	proxy: {
		type: 'rest',
		url: '/ssp/api/1/reference/confidentialityDisclosureAgreement/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});