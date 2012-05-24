Ext.define('Ssp.model.reference.ConfidentialityLevel', {
    extend: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },
    
    fields: [{name:'acronym',type:'string',defaultValue:'DEFAULT'}] ,

	constructor: function(){
		Ext.apply(this.getProxy(), 
				{ 
			url: this.apiProperties.createUrl( this.apiProperties.getItemUrl('confidentialityLevel') )
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
			type: 'json',
			successProperty: 'success',
			message: 'message'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});