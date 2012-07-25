Ext.define('Ssp.model.SearchCriteria', {
    extend: 'Ext.data.Model',
    fields: [{name:'searchTerm', type: 'string'},
             {name: 'outsideCaseload', type: 'boolean', defaultValue: true}]
});