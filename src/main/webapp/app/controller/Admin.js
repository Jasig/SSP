Ext.require('Ext.tab.*');
Ext.define('Ssp.controller.Admin', {
    extend: 'Ssp.controller.AbstractController',
    
    views: [
        'admin.AdminMenu', 'admin.forms.AbstractReferenceAdmin'
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
			
			'AbstractReferenceAdmin': {
				edit: this.adminGridEdit,
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
		var comp = adminFormsView.getComponent('AbstractReferenceAdmin'); 
		// this.formRendererUtils.cleanItems(adminFormsView);				
		if (comp == undefined)
		{
			comp = Ext.create('Ssp.view.admin.forms.AbstractReferenceAdmin',{store:store}); // +form			
			adminFormsView.add( comp );
		}
		/*
		var columns = [
				          { header: 'Name',  
				            dataIndex: 'name',
				            field: {
				                xtype: 'textfield'
				            },
				            flex: 50 },
				          { header: 'Description',
				            dataIndex: 'description', 
				            flex: 50,
				            field: {
				                xtype: 'textfield'
				            }
				          }
				     ]; 
		*/
		comp.reconfigure(store); // ,columns
		comp.getStore().load();		
	},
	
	adminGridEdit: function(editor, e, eOpts) {
	    // commit the changes right after editing finished
	    editor.record.commit();
	    editor.grid.getStore().sync();
	}
    
});