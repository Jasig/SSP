Ext.define('Ssp.controller.AdminViewController', {
    extend: 'Ext.app.Controller',
    
    views: [
        'admin.AdminTreeMenu', 
        'admin.forms.AbstractReferenceAdmin',
        'admin.forms.ChallengeAdmin',
        'admin.forms.ConfidentialityLevelAdmin'
    ],
       
	init: function() {

		this.control({
			'AdminTreeMenu': {
				itemclick : this.treeItemClick,
				scope: this
			},

			'AbstractReferenceAdmin': {
				edit: this.adminGridEdit,
				scope: this
			},

			'ChallengeAdmin': {
				edit: this.adminGridEdit,
				scope: this
			},

			'ConfidentialityLevelAdmin': {
				edit: this.adminGridEdit,
				scope: this
			}
        
		}); 

		this.callParent(arguments);
    },	

	/*
	 * Handle selecting an item in the tree grid
	 */
	treeItemClick: function(view,record,item,index,eventObj) {
		if (record.raw != undefined )
			if ( record.raw.form != "")
				this.loadAdmin( record.raw.title, record.raw.form, record.raw.store );         
	},

	loadAdmin: function( title ,form, storeName ) {
		var adminFormsView = Ext.getCmp('AdminForms');
		if (adminFormsView.items.length > 0)
			adminFormsView.removeAll();
		var store = Ext.getStore('reference.' + storeName);
		var comp = adminFormsView.getComponent(form); // 'AbstractReferenceAdmin'

		if (comp == undefined)
		{
			comp = Ext.create('Ssp.view.admin.forms.' + form); // AbstractReferenceAdmin			
			adminFormsView.add( comp );
		}

		comp.setTitle(title + ' Admin');
		comp.reconfigure(store); // ,columns
		comp.getStore().load();		
	},

	adminGridEdit: function(editor, e, eOpts) {
	    editor.record.commit();
	    editor.grid.getStore().sync();
	}
    
});