Ext.define('Ssp.controller.MainViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        formUtils: 'formRendererUtils'
    },  

    control: {
		'studentViewNav': {
			click: 'studentRecordViewNavClick'
		},

		'adminViewNav': {
			click: 'adminViewNavClick'
		},
		
	},
	
	init: function() {
		this.displayApplication();

		return this.callParent(arguments);
    },
    
	/*
	 * Handle the studentRecordViewNav click.
	 */    
	studentRecordViewNavClick: function(obj, eObj){ 
		this.displayStudentRecordView();
	},	
	
	/*
	 * Handle the adminViewNav click.
	 */    
	adminViewNavClick: function(obj, eObj){ 
		this.displayAdminView();
	},
 
    displayApplication: function(){
		// display the default record view
    	this.displayStudentRecordView();
    },
    
    displayStudentRecordView: function(){
    	var mainView = Ext.getCmp('MainView');
		if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		var arrViewItems;
		arrViewItems = [Ext.create('Ssp.view.Search',{flex: 2}),
							Ext.create('Ssp.view.StudentRecord',{
								flex: 4,
				 			  items: [
									Ext.create('Ssp.view.ToolsMenu',{flex:1}),
									Ext.create('Ssp.view.Tools',{flex:4})
									]
			 		  		 				 
						})];
		
		mainView.add( arrViewItems );
    },    
    
    displayAdminView: function() { 
    	var mainView = Ext.getCmp('MainView');
		if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		var arrViewItems;
		arrViewItems = [
		 		  Ext.create('Ssp.view.admin.AdminMain',
					{items:[
					        Ext.create('Ssp.view.admin.AdminTreeMenu',{ flex:1 }), 
					        Ext.create('Ssp.view.admin.AdminForms',{ flex:4 }) 
					],flex:4})
		 		 ];
		
		mainView.add( arrViewItems );
    }
});