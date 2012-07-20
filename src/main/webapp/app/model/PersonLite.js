Ext.define('Ssp.model.PersonLite', {
    extend: 'Ext.data.Model',
    fields: [{name:'id',type:'string'},
             {name:'firstName', type:'string'},
             {name:'lastName', type: 'string'},
             {name:'middleInitial',type:'string'},
             {name: 'displayFullName', 
	          convert: function(value, record) {
	        	  return record.get('firstName') + " " + record.get('lastName');
		      }
             }]
});