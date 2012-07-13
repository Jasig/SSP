Ext.define('Ssp.controller.tool.actionplan.AddTasksFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	confidentialityLevelsStore: 'confidentialityLevelsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentTask',
    	person: 'currentPerson'
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
 
	init: function(){
		var me=this;
		
		// apply confidentiality level filter
		me.authenticatedPerson.applyConfidentialityLevelsFilter( me.confidentialityLevelsStore );
		
		me.url = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personTask') );
		me.url = me.url.replace('{id}',me.person.get('id'));
		
		me.initForm();
		
    	me.appEventsController.assignEvent({eventName: 'loadTask', callBackFunc: me.initForm, scope: me});    	
    	
		return me.callParent(arguments);
	},
	
    destroy: function() {
    	var me=this;  	

    	// clear confidentiality level filter
    	me.confidentialityLevelsStore.clearFilter();
    	
    	me.appEventsController.removeEvent({eventName: 'loadTask', callBackFunc: me.initForm, scope: me});
    	
        return me.callParent( arguments );
    },	
	
	initForm: function(){
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		Ext.ComponentQuery.query('#confidentialityLevel')[0].setValue( this.model.get('confidentialityLevel').id );
	},
    
    onAddClick: function(button){
    	var me=this;
    	var successFunc;
    	var form = this.getView().getForm();
    	var model = this.model;
    	var id = model.get('id');
    	if ( form.isValid() )
    	{
    		form.updateRecord();
    		
			successFunc = function(response ,view){
		    	   Ext.Msg.confirm({
		    		     title:'Success',
		    		     msg: 'The task was saved successfully. Would you like to create another task?',
		    		     buttons: Ext.Msg.YESNO,
		    		     fn: me.createTaskConfirmResult,
		    		     scope: me
		    		});
			};	
    		
    		if (id == "")
    		{
        		model.set('type','SSP');
        		model.set('personId', this.person.get('id') );    		
        		model.set('confidentialityLevel',{id: form.getValues().confidentialityLevelId});

    			// add the task
    			this.apiProperties.makeRequest({
	    			url: me.url,
	    			method: 'POST',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});
    		}else{
    			
    			// This removes the group property from
    			// a TaskGroup item before it is saved
    			// as a Task. Task grouping is handled in the Tasks display.
        		if (model.data.group != null)
        			delete model.data.group;    			
    			
    			// edit the task
	    		this.apiProperties.makeRequest({
	    			url: me.url+"/"+id,
	    			method: 'PUT',
	    			jsonData: model.data,
	    			successFunc: successFunc
	    		});    			
    		}
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