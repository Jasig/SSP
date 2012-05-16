Ext.define('Ssp.controller.tool.actionplan.ActionPlanGoalsViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	goal: 'currentGoal',
    	person: 'currentPerson',
    	store: 'goalsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'addgoalform',
    	personGoalUrl: ''
    },
    control: {
		'addGoalButton': {
			click: 'onAddGoalClick'
		}
    },
    
    constructor: function() {
    	
    	this.personGoalUrl = this.apiProperties.createUrl(this.apiProperties.getItemUrl('personGoal'));
    	this.personGoalUrl = this.personGoalUrl.replace('{id}',this.person.get('id'));
    	
    	Ext.apply(this.store.getProxy(),
    			{
    		     url: this.personGoalUrl
    			});

    	this.store.load();

		return this.callParent(arguments);
    },
    
    onAddGoalClick: function( button ){
    	console.log("ActionPlanGoalsViewController->onAddGoalClick");
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});