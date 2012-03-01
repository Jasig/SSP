Ext.define('Ssp.model.AbstractBaseTO', {
    extend: 'Ext.data.Model',
    fields: ['id','createdDate','createdByPersonId','modifiedDate','modifiedByPersonId','objectStatus'],
    
	populateFromGenericObject: function( record ){
		for (fieldName in this.data)
    	{
			if (record[fieldName])
    		{
    			this.set(fieldName, record[fieldName] );
    		}
    	}
    }

});
