Ext.define('Ssp.model.PersonAppointment', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'startDate', type: 'date', dateFormat: 'time'},
             {name: 'endDate', type: 'date', dateFormat: 'time'}]
});