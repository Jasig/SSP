Ext.define('Ssp.view.tools.actionplan.ActionPlanGoals', {
	extend: 'Ext.form.Panel',
	alias : 'widget.actionplangoals',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.ActionPlanGoalsViewController',
    width: '100%',
	height: '100%',   
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },	
	initComponent: function() {	
		Ext.apply(this, {
			    defaultType: 'textfield',
				items: [{
			        fieldLabel: 'Goals',
			        name: 'goals',
			        allowBlank: true
			    },{
			    	fieldLabel: 'Strengths',
			        name: 'strengths',
			        allowBlank: true
			    }],
				buttons: [{
			        text: 'Add'
			    }]
		});
		
		return this.callParent(arguments);
	}
});