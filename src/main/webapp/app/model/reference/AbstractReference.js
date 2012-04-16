Ext.define('Ssp.model.reference.AbstractReference', {
    extend: 'Ext.data.Model',
    fields: [{name: 'id', type: 'string'},
             {name: 'name', type: 'string'},
             {name: 'description', type: 'string'}]
	/*
	 * 'createdDate',
	   'createdById',
	   'modifiedDate',
	   'modifiedById',
	   'objectStatus',
	 */
});