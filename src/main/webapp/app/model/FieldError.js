Ext.define('Ssp.model.FieldError', {
    extend: 'Ext.data.Model',
    fields: [{name:'label', type: 'string'},
             {name: 'errorMessage', type: 'string'}]
});