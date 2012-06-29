Ext.define('Ssp.view.ErrorWindow', {
	extend: 'Ext.window.Window',
	alias : 'widget.ssperrorwindow',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
    	store: 'errorsStore'
    },
	width: '100%',
	height: '100%',
	title: 'Error - Invalid Data',
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
						           renderer: me.columnRendererUtils.renderErrorMessage,
						           sortable: false,
						           menuDisabled: true,
						           flex:.75 
						         }],     
    			        store: me.store
    			    }],
    			    bbar: [
    			           { xtype: 'button', 
    			        	 text: 'Okay', 
    			        	 itemId: 'closeButton', 
    			        	 handler: function() {
    			        		 me.close();
    			             }
    			           }
    			         ]
		    	    });
    	
    	return me.callParent(arguments);
    }
});