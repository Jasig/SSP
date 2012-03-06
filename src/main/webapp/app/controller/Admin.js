Ext.require('Ext.tab.*');
Ext.define('Ssp.controller.Admin', {
    extend: 'Ssp.controller.AbstractController',
    
    views: [
        'admin.AdminMenu'
    ],

    stores: ['reference.Challenges',
             'reference.Ethnicities'],

	init: function() {
        console.log('Initialized Admin Controller!');
        
        this.control({
			'AdminMenu': {
				itemclick: this.itemClick,
				scope: this
			}
		}); 
		
		this.superclass.init.call(this, arguments);
    },
 
 	/*
	 * Load the selected admin tool.
	 */  
	itemClick: function(grid,record,item,index){ 
		this.loadAdmin( record.get('form') );		
	},
	
	loadAdmin: function( form ) {
		var adminFormsView = Ext.getCmp('AdminForms');
		var store = Ext.getStore('reference.' + form)
		var comp = Ext.create('Ssp.view.admin.forms.AbstractReferenceAdmin', {store: store}); // +form
		this.formRendererUtils.cleanItems(adminFormsView);
		adminFormsView.add( comp );
	}
    
});