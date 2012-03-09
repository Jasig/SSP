Ext.define('Ssp.controller.Main', {
    extend: 'Ssp.controller.AbstractController',
    requires: ['Ext.window.*'],
	
	models: ['security.UserTO'],
    
	views: [
        'Main',
        'security.Login',
        'security.Roles'
    ],
 
		
	init: function() {
        console.log('Initialized Main View Controller!');
		        
		this.control({
			'#securityLoginButton': {
				click: this.loginButtonClick,
				scope: this
			},
			
			'#securityRolesButton': {
				click: this.roleSelectionClick,
				scope: this
			},
			
			'#studentViewNav': {
				click: this.studentRecordViewNavClick,
				scope: this
			},

			'#adminViewNav': {
				click: this.adminViewNavClick,
				scope: this
			},
			
		}); 
		
		this.superclass.init.call(this, arguments);
    },
  
	/*
	 * Handle login button click.
	 */    
	loginButtonClick: function(obj, eObj){ 
		this.displayRoleList();
	},

	/*
	 * Handle assigning a role.
	 */    
	roleSelectionClick: function(obj, eObj){ 
		this.displayApplication();
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
	
	displayLoginWindow: function(){
		var sspView = Ext.getCmp('sspView');
		// Create a variable to hold our EXT Form Panel. 
		// Assign various config options as seen.	 
	    var login = Ext.create('Ssp.view.security.Login',{});	   		
		// This just creates a window to wrap the login form. 
		// The login object is passed to the items collection.       
	    var loginWin = new Ext.Window({
	        layout:'fit',
	        id: 'loginWin',
	        width:300,
	        height:150,
	        closable: false,
	        resizable: false,
	        plain: true,
	        border: false,
	        title: 'Please Login',
	        items: [login]
		});
		loginWin.show();
		
		sspView.add(loginWin);
		
	},
 
 
	displayRoleList: function(){
		var loginWin = Ext.getCmp('loginWin');
		var roleList = Ext.create('Ssp.view.security.Roles');
		loginWin.removeAll();
		loginWin.add(roleList);
	},
 
    displayApplication: function(){
    	// var loginWin = Ext.getCmp('loginWin');
    	
		// Hide the login window
		// loginWin.hide();

		var sspView = Ext.getCmp('sspView');
		
		var appView = Ext.create('Ssp.view.Main');
								 
		// Add the Student Record View
		sspView.add( appView );    	
    	
		// display the default student record view
    	this.displayStudentRecordView();
    },
    
    displayStudentRecordView: function(){
		var mainView = Ext.getCmp('Main');
		var arrViewItems = [
		 		  Ext.create('Ssp.view.SearchResults', {flex:2}),
		 		  
		 		  Ext.create('Ssp.view.StudentRecord',
					{items:[Ext.create('Ssp.view.ToolsMenu',{ flex:1 }), 
					Ext.create('Ssp.view.Tools',{ flex:4 }) 
					],flex:4})
		 		 ];
		
		
		this.formRendererUtils.cleanItems(mainView);
		mainView.add( arrViewItems );
    },    
    
    displayAdminView: function() { 
		var mainView = Ext.getCmp('Main');
		var arrViewItems = [
		 		  Ext.create('Ssp.view.admin.AdminMain',
					{items:[
					        Ext.create('Ssp.view.admin.AdminTreeMenu',{ flex:2 }), 
					        Ext.create('Ssp.view.admin.AdminForms',{ flex:4 }) 
					],flex:4})
		 		 ];
		
		this.formRendererUtils.cleanItems(mainView);
		mainView.add( arrViewItems );    	
    }
	
});