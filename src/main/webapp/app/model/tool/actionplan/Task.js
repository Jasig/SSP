Ext.define('Ssp.model.tool.actionplan.Task', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name:'dueDate',type:'date', },
             {name:'confidentialityLevel',type:'string'},
             {name:'deletable',type:'boolean'},
             {name:'completed',type:'boolean'},
             {name:'challengeId',type:'string'},
             {name:'type',type:'string'}]
});