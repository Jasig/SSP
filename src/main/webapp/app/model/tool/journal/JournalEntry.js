Ext.define('Ssp.model.tool.journal.JournalEntry', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'comment',type:'string'},
             {name: 'confidentialityLevel',
		        convert: function(value, record) {
		       	 var obj  = {id:'',name: ''}
		       	 if (value != null)
		       	 {
		       		 obj.id  = value.id;
		       		 obj.name = value.name;
		       	 }	
			            return obj;
		        }
			 },
			 {name:'journalSourceId', type:'string'},
			 {name:'journalTrackId', type:'string'}]/*,
			 
	 hasMany: {model: 'reference.JournalStepDetail', name: 'journalStepDetails'}
	 */
});