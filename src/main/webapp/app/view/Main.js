Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.mainview',
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
		    	        xtype: 'toolbar',
		    	        items: [{
		    			            xtype: 'button',
		    			            text: 'Students',
		    			            itemId: 'studentViewNav',
		    			            action: 'displayStudentRecord'
		    			        }, {
		    			            xtype: 'button',
		    			            text: 'Admin',
		    			            itemId: 'adminViewNav',
		    			            action: 'displayAdmin'
		    			        },{
			       		        	xtype: 'tbspacer',
			       		        	flex: 1
			       		        },{
								    tooltip: 'Add Student',
								    text: '',
								    width: 25,
								    height: 25,
								    cls: 'addPersonIcon',
								    xtype: 'button',
								    itemId: 'addPersonButton'
								},{
								    tooltip: 'Edit Student',
								    text: '',
								    width: 25,
								    height: 25,
								    cls: 'editPersonIcon',
								    xtype: 'button',
								    itemId: 'editPersonButton'
								},{
								    tooltip: 'Delete Student',
								    text: '',
								    width: 25,
								    height: 25,
								    cls: 'deletePersonIcon',
								    xtype: 'button',
								    itemId: 'deletePersonButton'
								}]
		    	    }    		
    			});
    	
    	return this.callParent(arguments);
    }
});