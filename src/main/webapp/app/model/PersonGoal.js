Ext.define('Ssp.model.PersonGoal', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name: 'confidentialityLevel', type:'auto'}]
});