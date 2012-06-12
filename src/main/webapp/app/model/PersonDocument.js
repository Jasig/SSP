Ext.define('Ssp.model.PersonDocument', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'note',type:'string'},
             {name: 'confidentialityLevel',type: 'auto'}]
});