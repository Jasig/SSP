Ext.define('Ssp.view.component.MappedCheckBox', {
	extend: 'Ext.form.field.Checkbox',
	alias : 'widget.mappedcheckbox',
	config: {
		mapId: null
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});