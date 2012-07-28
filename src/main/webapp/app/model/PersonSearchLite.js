Ext.define('Ssp.model.PersonSearchLite', {
    extend: 'Ext.data.Model',
    fields: [{name:'id',type:'string'},
             {name:'firstName', type:'string'},
             {name:'lastName', type: 'string'},
             {name:'middleName',type:'string'},
             {name: 'displayFullName', 
   	          convert: function(value, record) {
   	        	  return record.get('firstName') + " " + record.get('lastName');
   		      }
             }]        
});