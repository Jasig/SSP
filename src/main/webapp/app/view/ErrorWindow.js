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
	title: 'Error! Please correct the errors listed below:',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			   {
				    modal: true, 
		    		layout: 'fit',
    				items: [{
    			        xtype: 'grid',
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
    			        	 text: 'OK', 
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