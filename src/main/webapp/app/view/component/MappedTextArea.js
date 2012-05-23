Ext.define('Ssp.view.component.MappedTextArea', {
	extend: 'Ext.form.field.TextArea',
	alias : 'widget.mappedtextarea',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});