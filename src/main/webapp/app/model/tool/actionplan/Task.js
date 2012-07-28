Ext.define('Ssp.model.tool.actionplan.Task', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name:'dueDate', type:'date', dateFormat: 'time'},
             {name:'reminderSentDate', type:'date', dateFormat:'time'},
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
             {name:'deletable',type:'boolean'},
             //{name:'closableByStudent',type:'boolean'},
             {name:'completed',type:'boolean'},
             {name:'completedDate', type:'date', dateFormat:'time'},
             {name:'challengeId',type:'string'},
             {name:'type',type:'string'},
             {name:'personId',type:'string'}]
});