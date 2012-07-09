Ext.define('Ssp.view.tools.actionplan.TaskTree', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.tasktree',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TaskTreeViewController',
    inject: {
        store: 'treeStore'
    },
	height: 250,
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 dockedItems: [
     		              {
     		               xtype: 'toolbar',
     		               items: [/*{
     	                      xtype: 'textfield',
     	                      fieldLabel: 'Search'
     	                     },
     	                      {
     	                    	  xtype: 'button',
     	                    	  text: 'GO',
     	                    	  action: 'search',
     	                    	  itemId: 'searchButton'
     	                      }*/
	                       {
	                         xtype: "label",
	                         text: "Select a task to add to the Student's Action Plan:"
	                       }]
     		           }]
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});