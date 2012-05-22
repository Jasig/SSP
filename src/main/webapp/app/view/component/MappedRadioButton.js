Ext.define('Ssp.view.component.MappedRadioButton', {
	extend: 'Ext.form.field.Radio',
	alias : 'widget.mappedradiobutton',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});