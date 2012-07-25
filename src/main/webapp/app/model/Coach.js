Ext.define('Ssp.model.Coach', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'firstName',type:'string'},
             {name:'middleName',type:'string'},
             {name:'lastName',type:'string'},
             {
                 name: 'fullName',
                 convert: function(value, record) {
                     return record.get('firstName') + ' '+ record.get('lastName');
                 }
             },
             {name:'departmentName',type:'string', defaultValue:'Web Systems'},
             {name: 'workPhone', type:'string'},
             {name: 'primaryEmailAddress', type:'string'},
             {name: 'officeLocation', type:'string', defaultValue:'13023S'}]
});