Ext.define('Ssp.view.ErrorWindow', {
	extend: 'Ext.window.Window',
	alias : 'widget.ssperrorwindow',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	store: 'errorsStore'
    },
	width: '100%',
	height: '100%',
	title: 'Invalid Data',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			   {
				    modal: true, 
		    		layout: 'fit',
    				items: [{
    			        xtype: 'grid',
    			        title: 'Please correct the errrors listed below:',
    			        border: false,
    			        columns:[{ header: 'Error', 
		 				           dataIndex: 'label',
						           sortable: false,
						           menuDisabled: true,
						           flex:.25 
						         },{ header: 'Message', 
						           dataIndex: 'errorMessage',
						           sortable: false,
						           menuDisabled: true,
						           flex:.75 
						         }],     
    			        store: me.store
    			    }]
    				
		    	    });
    	
    	return me.callParent(arguments);
    }
});