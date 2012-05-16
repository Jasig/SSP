Ext.define('Ssp.model.AbstractBase', {
    extend: 'Ext.data.Model',
    fields: [{name: 'id', type: 'string'}
             /*{name: 'createdById', type: 'string'},
             {name: 'createdDate', type: 'date', dateFormat: 'time'},
             {name: 'modifiedDate', type: 'date', dateFormat: 'time'},
             {name: 'modifiedById', type: 'string'},
             {name: 'objectStatus', type: 'string'}*/],
    
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
