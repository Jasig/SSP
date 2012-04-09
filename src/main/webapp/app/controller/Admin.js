Ext.require('Ext.tab.*');
Ext.define('Ssp.controller.Admin', {
    extend: 'Ssp.controller.AbstractController',
    
    views: [
        'admin.AdminMenu', 'admin.AdminTreeMenu', 'admin.forms.AbstractReferenceAdmin'
    ],

    stores: ['reference.Challenges',
             'reference.ChildCareArrangements',
             'reference.Citizenships',
             'reference.EducationalGoals',
             'reference.EducationLevels',
             'reference.Ethnicities',
             'reference.FundingSources',
             'reference.StudentStatuses',
             'reference.VeteranStatuses'],

	init: function() {
        console.log('Initialized Admin Controller!');
        
        this.control({
			'AdminMenu': {
				itemclick: this.itemClick,
				scope: this
			},
			
			'AdminTreeMenu': {
				itemclick : this.treeItemClick,
				scope: this
			},
			
			'AbstractReferenceAdmin': {
				edit: this.adminGridEdit,
				scope: this
			}
        
		}); 
		
		this.callParent(arguments);
    },
 
 	/*
	 * Load the selected admin tool.
	 */  
	itemClick: function(grid,record,item,index){ 
		this.loadAdmin( record.get('title'), record.get('form') );		
	},
	
	/*
	 * Handle selecting an item in the tree grid
	 */
	treeItemClick: function(view,record,item,index,eventObj) {
		
		if (record.raw != undefined )
			if ( record.raw.form != "")
				this.loadAdmin( record.raw.title, record.raw.form );         
	},
	
	loadAdmin: function( title ,form ) {
		var adminFormsView = Ext.getCmp('AdminForms');
		var store = Ext.getStore('reference.' + form);
		var comp = adminFormsView.getComponent('AbstractReferenceAdmin');
		var pager = null;
		
		// set the store page size
		// store.pageSize = 15;
		
		if (comp == undefined)
		{
			comp = Ext.create('Ssp.view.admin.forms.AbstractReferenceAdmin');			
			adminFormsView.add( comp );
		}
		
		comp.setTitle(title + ' Admin');
		comp.reconfigure(store); // ,columns
		comp.getStore().load();		
	},
	
	adminGridEdit: function(editor, e, eOpts) {
	    // commit the changes right after editing finished
	    editor.record.commit();
	    editor.grid.getStore().sync();
	}
    
});