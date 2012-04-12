Ext.define('Ssp.model.AbstractBaseTO', {
    extend: 'Ext.data.Model',
    fields: ['id','createdDate','createdById','modifiedDate','modifiedById','objectStatus'],
    
	populateFromGenericObject: function( record ){
		if (record != null)
		{
			for (fieldName in this.data)
	    	{
				if (record[fieldName])
	    		{
	    			this.set(fieldName, record[fieldName] );
	    		}
	    	}
		}
    }

});
