Ext.define('Ssp.model.PersonGoal', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'goal',type:'string'},
             {name:'personId',type:'string'},
             {name:'confidentialityLevel',type:'string'}]
});