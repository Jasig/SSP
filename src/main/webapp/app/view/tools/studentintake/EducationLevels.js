Ext.define('Ssp.view.tools.studentintake.EducationLevels', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakeeducationlevels',
	id : 'StudentIntakeEducationLevels',
	autoScroll: true,    
    width: '100%',
    height: '100%',
    bodyPadding: 5,

    // Fields will be arranged vertically, stretched to full width
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },

    // The fields
    defaultType: 'checkbox',
    items: []
	
});