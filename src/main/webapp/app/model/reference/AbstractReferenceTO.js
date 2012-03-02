Ext.define('Ssp.model.reference.AbstractReferenceTO', {
    extend: 'Ext.data.Model',
    fields: [{name:'id', type: 'string'},
             'name',
             'description',
             {name: 'createdDate', type: 'string', defaultValue: '2012-03-01T16:30:00.000-0500'},
             {name: 'createdById', type: 'string', defaultValue: '58ba5ee3-734e-4ae9-b9c5-943774b4de41'}]
    
});