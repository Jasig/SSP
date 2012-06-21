Ext.define('Ssp.view.tools.studentintake.Challenges', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakechallenges',
	id : 'StudentIntakeChallenges',
    width: '100%',
    height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
		    	    autoScroll: true,
					border: 0,	
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});