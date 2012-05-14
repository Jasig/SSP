Ext.define('Ssp.controller.tool.actionplan.AddTasksFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	task: 'currentTask',
    	currentPerson: 'currentPerson',
    	formUtils: 'formRendererUtils',
    	appEventsController: 'appEventsController'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan'
    },    
    control: {
    	'addButton': {
			click: 'onAddClick'
		},
		
		'closeButton': {
			click: 'onCloseClick'
		}
	},
    
	constructor: function(){
    	// TODO: ensure the appropriate challengeId, challengeReferralId, confidentialityLevelId, etc. are set
		this.appEventsController.getApplication().addListener('loadTask', function(args){
    		var model = new Ssp.model.tool.actionplan.Task();
    		this.task.data = model.data;
    		this.task.set('name',args.name || '');
    		this.task.set('description', args.description || '');
    		this.task.set('challengeId', args.challengeId || '7c0e5b76-9933-484a-b265-58cb280305a5');
    		this.task.set('challengeReferralId', args.challengeReferralId || '')
    		this.task.set('confidentialityLevel', args.confidentialityLevel || '');
    		this.task.set('type','SSP');
    		this.task.set('personId', this.currentPerson.get('id') || '');
    		this.getView().getForm().loadRecord(this.task);		
		},this);
    	
		return this.callParent(arguments);
	},
    
    onAddClick: function(button){
    	console.log('AddTasksFormViewController->onAddClick');
    	var form, url;
    	form = this.getView().getForm();
    	if ( form.isValid() )
    	{
    		form.updateRecord();
    		url = this.apiProperties.createUrl('person/' + this.currentPerson.get('id') + '/task/');
    		this.apiProperties.makeRequest({
    			url: url,
    			method: 'POST',
    			jsonData: this.task.data,
    			successFunc: function(response ,view){
    				Ext.Msg.alert('The record was saved successfully');
    			}
    		});    	
    	}else{
    		Ext.Msg.alert('Error', 'Please correct the errors in your form before continuing.');
    	}
    },
    
    onCloseClick: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }    
});