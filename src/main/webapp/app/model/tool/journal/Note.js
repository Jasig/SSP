Ext.define('Ssp.model.tool.journal.Note', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'comment',type:'string'},
             {name:'confidentialityLevelId',type:'string'}]
});