Ext.define('Ssp.view.tools.studentintake.EducationLevels', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakeeducationlevels',
	id : 'StudentIntakeEducationLevels',
    width: '100%',
    height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
				    bodyPadding: 5,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});