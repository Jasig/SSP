Ext.define('Ssp.model.tool.StudentIntakeFormTO', {
    extend: 'Ext.data.Model',
    fields: ['studentId','StudentIntakeReferenceDataTO','StudentIntakeTO'],
    autoLoad: true,
   
    proxy: {
		type: 'ajax',
		api: {
			read: 'data/tools/StudentIntakeFormTO.json'
		},
		reader: {
			type: 'json',
			root: 'StudentIntakeFormTO',
			successProperty: 'success'
		}
	},
	
	load: function(record, operation) {
		console.log(this.data);
	}   
    
});