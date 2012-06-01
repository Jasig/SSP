Ext.define('Ssp.model.tool.journal.JournalEntry', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'comment',type:'string'},
             {name: 'confidentialityLevel', type:'auto'
		/*        
    	convert: function(value, record) {
		       	 var obj  = {id:'',name: ''}
		       	 if (value != null)
		       	 {
		       		 obj.id  = value.id;
		       		 obj.name = value.name;
		       	 }	
			            return obj;
		        }
		        */
			 },
			 {name:'journalSource', type:'auto'},
			 {name:'journalTrack', type:'auto'},
			 {name:'journalEntryDetails',type:'auto'}]

});