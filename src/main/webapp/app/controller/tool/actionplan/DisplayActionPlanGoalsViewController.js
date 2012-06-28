Ext.define('Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	model: 'currentGoal',
    	person: 'currentPerson',
    	store: 'goalsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editgoalform',
    	url: ''
    },
    control: {
		view: {
			viewready: 'onViewReady'
		},
    	
    	'addGoalButton': {
			click: 'onAddGoalClick'
		}
    },
    
    constructor: function() {
    	// reconfigure the url for the current person
    	this.url = this.apiProperties.createUrl(this.apiProperties.getItemUrl('personGoal'));
    	this.url = this.url.replace('{id}',this.person.get('id'));
    	
    	// apply the person url to the store proxy
    	Ext.apply(this.store.getProxy(), { url: this.url });

    	// load records
    	this.store.load();

		return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
    	this.appEventsController.assignEvent({eventName: 'editGoal', callBackFunc: this.editGoal, scope: this});
    	this.appEventsController.assignEvent({eventName: 'deleteGoal', callBackFunc: this.deleteConfirmation, scope: this});
    },
    
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'editGoal', callBackFunc: this.editGoal, scope: this});
    	this.appEventsController.removeEvent({eventName: 'deleteGoal', callBackFunc: this.deleteConfirmation, scope: this});

        return this.callParent( arguments );
    },
    
    onAddGoalClick: function( button ){
		var goal = new Ssp.model.PersonGoal();
		this.model.data = goal.data;
		this.loadEditor();
    },
 
    editGoal: function(){
  	   this.loadEditor();
    },

    deleteConfirmation: function() {
       if (this.model.get('id') != "") 
       {
    	   Ext.Msg.confirm({
    		     title:'Delete Goal?',
    		     msg: 'You are about to delete the goal: "'+ this.model.get('name') + '". Would you like to continue?',
    		     buttons: Ext.Msg.YESNO,
    		     fn: this.deleteGoal,
    		     scope: this
    		});
       }else{
    	   Ext.Msg.alert('SSP Error', 'Unable to delete goal.'); 
       }
    },
    
    deleteGoal: function( btnId ){
    	var store = this.store;
    	var id = this.model.get('id');
    	if (btnId=="yes")
    	{
        	this.apiProperties.makeRequest({
     		   url: this.url+id,
     		   method: 'DELETE',
     		   successFunc: function(response,responseText){
     			  store.remove( store.getById( id ) );
     		   }
     	    });    		
    	}
    },
    
    loadEditor: function(){
    	var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});