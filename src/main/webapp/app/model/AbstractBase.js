Ext.define('Ssp.model.AbstractBase', {
    extend: 'Ext.data.Model',
    fields: [{name: 'id', type: 'string'},
             {name: 'createdBy',
              convert: function(value, record) {
		            var obj  = {id:value.id || '',
		                        firstName: value.firstName || '',
		                        lastName: value.lastName || ''};	
		            return obj;
		      }
             },
             {name: 'modifiedBy',
              convert: function(value, record) {
 		            var obj  = {id:value.id || '',
 		                        firstName: value.firstName || '',
 		                        lastName: value.lastName || ''};	
 		            return obj;
 		      }
             }
             ,{name: 'createdDate', type: 'date', dateFormat: 'time'}
             /*,{name: 'objectStatus', type: 'string'}*/
             /*,
             {name: 'modifiedDate', type: 'date', dateFormat: 'time'},
             */],
    
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
    },
    
    getCreatedByPersonName: function(){
    	return this.get('createdBy').firstName + ' ' + this.get('createdBy').lastName;
    },

    getCreatedById: function(){
    	return this.get('createdBy').id + ' ' + this.get('createdBy').id;
    },

});
