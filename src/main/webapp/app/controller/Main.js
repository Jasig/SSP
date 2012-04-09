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
		
		this.callParent(arguments);
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
	    var login = Ext.create('Ssp.view.security.Login',{});      
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
    	
		// display the default student record view
    	this.displayStudentRecordView();
    },
    
    cleanSspView: function(){
    	var sspView = Ext.getCmp('sspView');
    	this.formRendererUtils.cleanAll(sspView);
    	return sspView;
    },
    
    displayStudentRecordView: function(){
    	var mainView;
    	var arrViewItems;
    	var sspView = this.cleanSspView();
    	mainView = Ext.create('Ssp.view.Main');
		arrViewItems = [{ 
							xtype: 'searchresults', 
							flex: 2 
						},
							Ext.create('Ssp.view.StudentRecord',{
								flex: 4,
				 			  items: [{
				 				  	xtype: 'toolsmenu',
				 				  	flex: 1
				 				  },{
				 					  xtype: 'tools',
				 					  flex: 4
				 			  }]
			 		  		 				 
						})];
		
		mainView.add( arrViewItems );
		sspView.add(mainView);
		sspView.render();
    },    
    
    displayAdminView: function() { 
    	var mainView;
    	var arrViewItems;
    	var sspView = this.cleanSspView();
    	mainView = Ext.create('Ssp.view.Main');
		arrViewItems = [
		 		  Ext.create('Ssp.view.admin.AdminMain',
					{items:[
					        Ext.create('Ssp.view.admin.AdminTreeMenu',{ flex:2 }), 
					        Ext.create('Ssp.view.admin.AdminForms',{ flex:4 }) 
					],flex:4})
		 		 ];
		
		mainView.add( arrViewItems ); 
		sspView.add(mainView);
    }
	
});