Ext.define('Ssp.controller.MainViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
        formUtils: 'formRendererUtils'
    },
    config: {
    	personButtonsVisible: true
    },
    control: {
    	view: {
    		add: 'setListeners'
    	},
    	
    	'studentViewNav': {
			click: 'onStudentRecordViewNavClick'
		},

		'adminViewNav': {
			click: 'onAdminViewNavClick'
		},
    	
    	addPersonButton: {
    		selector: '#addPersonButton',
    		listeners: {
    			click: 'onAddPersonClick'
    		}
    	},
    	
    	editPersonButton: {
    		selector: '#editPersonButton',
    		listeners: {
    			click: 'onEditPersonClick'
    		}
    	},
    	
    	deletePersonButton: {
    		selector: '#deletePersonButton',
    		listeners: {
    			click: 'onDeletePersonClick'
    		}
    	}
	},
	
	init: function() {
		this.displayStudentRecordView();
		
		return this.callParent(arguments);
    },
    
    setListeners: function(container, component, index, obj){
		/**
		 * TODO: Figure out a better workaround than this for loading
		 * the listener that allows the display to be reset after
		 * saving the caseload assignment. This works because the Profile
		 * tool is dynamically added to the tools display after the interface
		 * is rendered. This event has to be assigned to the application after
		 * the application's onLaunch method has already fired.
		 * The issue with using the profile instance is that there may later
		 * be a requirement to load a different tool than the Profile first in the stack.
		 */
		if(component instanceof Ext.ClassManager.get('Ssp.view.tools.profile.Profile'))
		{
	       this.appEventsController.assignEvent({eventName: 'displayStudentRecordView', callBackFunc: this.onDisplayStudentRecordView, scope: this});			
		}
    },
    
    destroy: function() {
	   	this.appEventsController.removeEvent({eventName: 'displayStudentRecordView', callBackFunc: this.onDisplayStudentRecordView, scope: this});

        return this.callParent( arguments );
    },
    
    onDisplayStudentRecordView: function(){
    	this.displayStudentRecordView();
    },
    
    onStudentRecordViewNavClick: function(obj, eObj){ 
		this.displayStudentRecordView();
	},
	
	onAdminViewNavClick: function(obj, eObj){ 
		this.displayAdminView();
	},
    
    displayStudentRecordView: function(){
    	var me=this;
    	var mainView = Ext.ComponentQuery.query('mainview')[0];
    	var arrViewItems;
    	
    	me.personButtonsVisible=true;
    	me.setPersonButtons();
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [{xtype:'search',flex: 2},
					    {xtype: 'studentrecord', flex: 4}];
		
		mainView.add( arrViewItems );
    },
    
    displayAdminView: function() { 
    	var me=this;
    	var mainView = Ext.ComponentQuery.query('mainview')[0];
    	var arrViewItems;	
    	
    	me.personButtonsVisible=false;
    	me.setPersonButtons();
    	
    	if (mainView.items.length > 0)
		{
			mainView.removeAll();
		}
		
		arrViewItems = [{xtype:'adminmain',
					     items:[{xtype: 'admintreemenu', flex:1 }, 
					            {xtype: 'adminforms', flex: 3 }],
					     flex:5}];
		
		mainView.add( arrViewItems );
    },	
    
    onAddPersonClick: function( button ){
    	var me=this;
    	me.personButtonsVisible=false;
    	me.setPersonButtons();
    	this.appEventsController.getApplication().fireEvent('addPerson');
	},
	
	onEditPersonClick: function( button ){
    	var me=this;
    	me.personButtonsVisible=false;
    	me.setPersonButtons();
		this.appEventsController.getApplication().fireEvent('editPerson');
	},

	onDeletePersonClick: function( button ){
    	var me=this;
    	me.personButtonsVisible=false;
    	me.setPersonButtons();
		this.appEventsController.getApplication().fireEvent('deletePerson');
	},
	
	setPersonButtons: function(){
		var me=this;
		me.getAddPersonButton().setVisible( me.personButtonsVisible );
		me.getEditPersonButton().setVisible( me.personButtonsVisible );
		me.getDeletePersonButton().setVisible( me.personButtonsVisible );
	}
});