Ext.define('Ssp.model.PersonProgramStatus', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'programStatusId', type: 'string'},
             {name: 'effectiveDate', type: 'date', dateFormat: 'time'},
             {name: 'expirationDate', type: 'date', dateFormat: 'time', defaultValue: null},
             {name: 'programStatusChangeReasonId', type: 'string', defaultValue: null}]
});