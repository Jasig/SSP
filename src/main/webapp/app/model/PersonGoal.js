Ext.define('Ssp.model.PersonGoal', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name: 'confidentialityLevel',
                 convert: function(value, record) {
                	 var defaultConfidentialityLevelId = Ssp.util.Constants.DEFAULT_SYSTEM_CONFIDENTIALITY_LEVEL_ID;
                	 var obj  = {id:defaultConfidentialityLevelId,name: ''};
                	 if (value != null)
                	 {
                		 if (value != "")
                		 {
                    		 obj.id  = value.id;
                    		 obj.name = value.name;                			 
                		 }
                	 }
   		            return obj;
                 }
   		      }]
});