Ext.define('Ssp.view.admin.AdminTreeMenu', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.AdminTreeMenu',
	id: 'AdminTreeMenu', 
    store: Ext.getStore('admin.AdminTreeMenus'),  
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	adminTreeMenusStore: 'adminTreeMenusStore'
    },    
	initComponent: function() {	
		Ext.apply(this, 
				{
					store: this.adminTreeMenusStore,
					fields: ['title','form','text'],	
				});
		
	     this.callParent(arguments);
	}	
}); 