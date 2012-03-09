Ext.define('Ssp.view.tools.studentintake.Challenges', {
	extend: 'Ext.form.Panel',
	id : 'StudentIntakeChallenges',
	autoScroll: true,
    width: '100%',
    height: '100%',
    bodyPadding: 5,

    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },
    

    // The fields
    defaultType: 'checkbox',
    
    items: [],
	

	initComponent: function() {	
		this.superclass.initComponent.call(this, arguments);
	}
	
});