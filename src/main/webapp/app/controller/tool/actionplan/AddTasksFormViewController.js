Ext.define('Ssp.controller.tool.actionplan.AddTasksFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	model: 'currentTask',
    	person: 'currentPerson',
    	formUtils: 'formRendererUtils',
    	appEventsController: 'appEventsController'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan',
    	model: 'currentTask',
    	url: ''
    },    
    control: {    	
    	'addButton': {
			click: 'onAddClick'
		},
		
		'closeButton': {
			click: 'onCloseClick'
		}
	},
 
	init: function() {		
		return this.callParent(arguments);
    },	
	
	constructor: function(){
		this.url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personTask') );
		this.url = this.url.replace('{id}',this.person.get('id'));
		
		this.appEventsController.getApplication().addListener('loadTask', function(){
    		this.initForm();		
		},this);
    	
		return this.callParent(arguments);
	},
	
	initForm: function(){
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		Ext.ComponentQuery.query('#confidentialityLevel')[0].setValue( this.model.get('confidentialityLevel').id );
	},
    
    onAddClick: function(button){
    	var me=this;
    	var form = this.getView().getForm();
    	if ( form.isValid() )
    	{
    		form.updateRecord();
        	var model = this.model;
    		model.set('type','SSP');
    		model.set('personId', this.person.get('id') );    		
    		model.set('confidentialityLevel',{id: form.getValues().confidentialityLevelId});
    		this.apiProperties.makeRequest({
    			url: me.url,
    			method: 'POST',
    			jsonData: model.data,
    			successFunc: function(response ,view){
    		    	   Ext.Msg.confirm({
    		    		     title:'Success',
    		    		     msg: 'The task was added successfully. Would you like to create another task?',
    		    		     buttons: Ext.Msg.YESNO,
    		    		     fn: me.createTaskConfirmResult,
    		    		     scope: me
    		    		});
    			}
    		});   	
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },

    createTaskConfirmResult: function( btnId ){
    	if (btnId=="yes")
    	{
    		var task = new Ssp.model.tool.actionplan.Task();
    		this.model.data = task.data;
    		this.initForm();
    	}else{
    		this.loadDisplay();
    	}
    },    
    
    onCloseClick: function(button){
    	this.loadDisplay();
    },
    
    loadDisplay: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});