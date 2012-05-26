Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.Main',
    id: 'MainView',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.MainViewController',
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
		    			            text: 'Students',
		    			            itemId: 'studentViewNav',
		    			            action: 'displayStudentRecord'
		    			        }, {
		    			            xtype: 'button',
		    			            text: 'Admin',
		    			            itemId: 'adminViewNav',
		    			            action: 'displayAdmin'
		    			        }
		    	        ]
		    	    }    		
    			});
    	
    	return this.callParent(arguments);
    }
});