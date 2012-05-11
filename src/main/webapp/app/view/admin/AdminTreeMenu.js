Ext.define('Ssp.view.admin.AdminTreeMenu', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.admintreemenu',
	id: 'AdminTreeMenu',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.AdminViewController',
    inject: {
    	store: 'adminTreeMenusStore'
    },    
	initComponent: function() {	
		Ext.apply(this, 
				{
					store: this.store,
					fields: ['title','form','text'],	
				});
		
	     this.callParent(arguments);
	}	
}); 