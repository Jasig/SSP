Ext.define('Ssp.controller.AdminViewController', {
    extend: 'Ssp.controller.AbstractViewController',
    
    views: [
        'admin.AdminTreeMenu', 'admin.forms.AbstractReferenceAdmin'
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

		this.control({
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
	    editor.record.commit();
	    editor.grid.getStore().sync();
	}
    
});