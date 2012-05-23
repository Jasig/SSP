Ext.define('Ssp.view.tools.ActionPlan', {
	extend: 'Ext.container.Container',
	alias : 'widget.actionplan',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.ActionPlanToolViewController',
    width: '100%',
	height: '100%',   
	layout: 'fit',
	initComponent: function() {	
		Ext.apply(this,{items: [{xtype: 'actionplantasks'}]});

		return this.callParent(arguments);
	}
		
});