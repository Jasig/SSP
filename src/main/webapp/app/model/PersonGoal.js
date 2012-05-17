Ext.define('Ssp.model.PersonGoal', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'personId',type:'string'},
             {name:'description',type:'string'},
             {name:'confidentialityLevelId',type:'string'}]
});