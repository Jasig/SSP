Ext.define('Ssp.store.ApplicationForms', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.ApplicationForm',
    construction: function(){
    	Ext.apply(this,{
    			autoLoad: true,
			    proxy: {
					type: 'ajax',
					api: {
						read: 'data/forms.json'
					},
					reader: {
						type: 'json',
						root: 'items',
						successProperty: 'success'
					}
				}
    	});
    	return this.callParent(arguments);
    }
});