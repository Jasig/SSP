Ext.define('Ssp.controller.MainViewController', {
    extend: 'Ext.app.Controller',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        formUtils: 'formRendererUtils'
    },    
	views: [
        'Main'
    ],
 	
	init: function() {
		this.control({
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
    	this.displayRecordView();
    },
    
    cleanSspView: function(){
    	var sspView = Ext.getCmp('sspView');
    	if (sspView.items.length > 0)
    	{
    		this.formUtils.cleanAll(sspView);
    	}
    	return sspView;
    },
    
    displayRecordView: function(){
    	var mainView;
    	var arrViewItems;
    	var sspView = this.cleanSspView();
    	
    	mainView = Ext.create('Ssp.view.Main');
		arrViewItems = [{xtype: 'search', flex: 2 },
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
					        Ext.create('Ssp.view.admin.AdminTreeMenu',{ flex:1 }), 
					        Ext.create('Ssp.view.admin.AdminForms',{ flex:4 }) 
					],flex:4})
		 		 ];
		
		mainView.add( arrViewItems ); 
		sspView.add(mainView);
    }
});