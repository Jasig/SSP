Ext.define('Ssp.controller.MainViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        formUtils: 'formRendererUtils'
    },  

    control: {
		'studentViewNav': {
			click: 'onStudentRecordViewNavClick'
		},

		'adminViewNav': {
			click: 'onAdminViewNavClick'
		},
		
	},
	
	init: function() {
		this.displayStudentRecordView();

		return this.callParent(arguments);
    },
    
    onStudentRecordViewNavClick: function(obj, eObj){ 
		this.displayStudentRecordView();
	},	
	
	onAdminViewNavClick: function(obj, eObj){ 
		this.displayAdminView();
	},
    
    displayStudentRecordView: function(){
    	var mainView = Ext.getCmp('MainView');
    	var arrViewItems;
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [{xtype:'search',flex: 2},
					    {xtype: 'StudentRecord',
						 flex: 4,
			 			 items: [{xtype:'ToolsMenu',flex:1},
								 {xtype: 'Tools', flex:4}]
			 		  	}];
		
		mainView.add( arrViewItems );
    },    
    
    displayAdminView: function() { 
    	var mainView = Ext.getCmp('MainView');
    	var arrViewItems;
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [{xtype:'AdminMain',
					     items:[{xtype: 'AdminTreeMenu', flex:2 }, 
					            {xtype: 'AdminForms', flex: 3 }],
					     flex:5}];
		
		mainView.add( arrViewItems );
    }
});