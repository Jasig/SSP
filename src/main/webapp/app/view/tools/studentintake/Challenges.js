Ext.define('Ssp.view.tools.studentintake.Challenges', {
	extend: 'Ext.form.Panel',
	id : 'StudentIntakeChallenges',
    
    width: '100%',
    height: '100%',
    bodyPadding: 5,

    // Fields will be arranged vertically, stretched to full width
    layout: 'vbox',
    defaults: {
        autoScroll: true,
		autoHeight: true
        
    },

    // The fields
    defaultType: 'checkbox',
    
    items: [],
	

	initComponent: function() {	
		this.superclass.initComponent.call(this, arguments);
	}
	
});