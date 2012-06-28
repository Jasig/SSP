Ext.define('Ssp.model.reference.ConfidentialityDisclosureAgreement', {
    extend: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
    fields: [{name:'text',type:'string'}],

	constructor: function(){
		Ext.apply(this.getProxy(), 
				{ 
			url: this.apiProperties.createUrl( this.apiProperties.getItemUrl('confidentialityDisclosureAgreement') )
			    }
		);
		return this.callParent(arguments);
	}, 	
	
	proxy: {
		type: 'rest',
		url: '',
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