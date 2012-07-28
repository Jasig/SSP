Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.mainview',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    controller: 'Ssp.controller.MainViewController',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
		    			{
		    	    layout: {
		    	    	type: 'hbox',
		    	    	align: 'stretch'
		    	    },

		    	    dockedItems: {
		    	        xtype: 'toolbar',
		    	        items: [{
		    			            xtype: 'button',
		    			            text: 'Students',
		    			            hidden: !me.authenticatedPerson.hasAccess('STUDENTS_NAVIGATION_BUTTON'),
		    			            itemId: 'studentViewNav',
		    			            action: 'displayStudentRecord'
		    			        }, {
		    			            xtype: 'button',
		    			            text: 'Admin',
		    			            hidden: !me.authenticatedPerson.hasAccess('ADMIN_NAVIGATION_BUTTON'),
		    			            itemId: 'adminViewNav',
		    			            action: 'displayAdmin'
		    			        },{
			       		        	xtype: 'tbspacer',
			       		        	flex: 1
			       		        },{
	    		    	    	  id: 'report',
	    		    	    	  xtype: 'sspreport'
	    		    	    	}]
		    	    }    		
    			});
    	
    	return me.callParent(arguments);
    }
});