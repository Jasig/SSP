Ext.define('Ssp.model.AbstractBase', {
    extend: 'Ext.data.Model',
    fields: [{name: 'id', type: 'string'}],
    
    /*
     'createdDate',
     'createdById',
     'modifiedDate',
     'modifiedById',
     'objectStatus'
     */
    
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
