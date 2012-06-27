Ext.define('Ssp.model.Coach', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'firstName',type:'string'},
             {name:'middleInitial',type:'string'},
             {name:'lastName',type:'string'},
             {
                 name: 'fullName',
                 convert: function(value, record) {
                     return record.get('firstName') + ' '+ record.get('lastName');
                 }
             },
             {name:'department',type:'string', defaultValue:'Web Systems'},
             {name: 'workPhone', type:'string'},
             {name: 'primaryEmailAddress', type:'string'},
             {name: 'office', type:'string', defaultValue:'13023S'}]
});