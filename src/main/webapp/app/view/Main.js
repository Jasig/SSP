Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.main',
    id: 'MainView',
    title: 'Student Success Plan',
    width: '100%',
    height: '100%',

    initComponent: function(){
    	Ext.apply(this,
    			{
		    	    layout: {
		    	    	type: 'hbox',
		    	    	align: 'stretch'
		    	    },
		    	    
		    	    dockedItems: {
		    	    	id: 'MainNav',
		    	        xtype: 'toolbar',
		    	        items: [ 
		    			        {
		    			            xtype: 'button',
		    			            id: 'studentViewNav',
		    			            text: 'Students'
		    			        }, {
		    			            xtype: 'button',
		    			            id: 'adminViewNav',
		    			            text: 'Admin'
		    			        }
		    	        ]
		    	    }    		
    			});
    	
    	this.callParent(arguments);
    }
});