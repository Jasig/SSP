Ext.define('Ssp.model.PersonGoal', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name: 'confidentialityLevel',
                 convert: function(value, record) {
                	 var obj  = {id:'',name:''};
                	 if (value != null)
                	 {
                		 obj.id  = value.id;
                		 obj.name = value.name;
                	 }	
   		            return obj;
                 }
   		      }]
});