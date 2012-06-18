Ext.define('Ssp.model.Coach', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'fullName',type:'string'},
             {name:'department',type:'string'},
             {name: 'phone', type:'string'},
             {name: 'emailAddress', type:'string'},
             {name: 'office', type:'string'}]
});