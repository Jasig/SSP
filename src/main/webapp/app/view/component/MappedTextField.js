Ext.define('Ssp.view.component.MappedTextField', {
	extend: 'Ext.form.field.Text',
	alias : 'widget.mappedtextfield',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});