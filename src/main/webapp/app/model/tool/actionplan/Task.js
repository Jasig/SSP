Ext.define('Ssp.model.tool.actionplan.Task', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name:'dueDate', type:'date', dateFormat:'time'},
             {name:'reminderSentDate', type:'date', dateFormat:'time'},
             {name:'confidentialityLevel',type:'string'},
             {name:'deletable',type:'boolean'},
             //{name:'closable',type:'boolean'},
             {name:'completed',type:'boolean'},
             {name:'completedDate', type:'date', dateFormat:'time'},
             {name:'challengeId',type:'string'},
             {name:'type',type:'string'},
             {name:'personId',type:'string'}]
});