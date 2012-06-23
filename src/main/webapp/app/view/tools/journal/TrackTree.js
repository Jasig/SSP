Ext.define('Ssp.view.tools.journal.TrackTree', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.journaltracktree',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.TrackTreeViewController',
    inject: {
        store: 'treeStore'
    },
	height: 200,
	width: '100%',
	
    initComponent: function(){
    	Ext.apply(this,
    			{
   		     singleExpand: false,
			 store: this.store,
			 useArrows: true,
			 rootVisible: false ,
			 hideCollapseTool: true,

			 dockedItems: [{
		               xtype: 'toolbar',
		               items: [{
     		                   text: 'Save Details',
     		                   xtype: 'button',
     		                   action: 'save',
     		                   itemId: 'saveButton'
     		               }, '-', {
     		                   text: 'Cancel',
     		                   xtype: 'button',
     		                   action: 'cancel',
     		                   itemId: 'cancelButton'
     		               }]
		           },{
 		               xtype: 'toolbar',
  		               dock: 'top',
  		               items: [{
  		                         xtype: 'label',
  		                         text: 'Select the details for this Journal Session'
  		                       }]  
  		            }]			 
			 
    });   	
    	
    	return this.callParent(arguments);
    }
});