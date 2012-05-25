Ext.define('Ssp.view.tools.actionplan.DisplayStrengths', {
	extend: 'Ext.form.Panel',
	alias : 'widget.displaystrengths',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayStrengthsViewController',
    width: '100%',
	height: '100%',
	initComponent: function() {	
		Ext.applyIf(this,{
	        title: 'Strengths',
			items:[{
		        xtype:'form',
		        layout:'anchor',
		        items :[{
		            xtype: 'textarea',
		            anchor: '100%',
		            height: 50,
		            fieldLabel: 'Strengths',
		            itemId: 'strengths',
		            name: 'strengths'
		        }]
			}],
			
    	    dockedItems: [{
		        dock: 'top',
		        xtype: 'toolbar',
		        items: [{
		            tooltip: 'Save Strengths',
		            text: 'Save',
		            xtype: 'button',
		            itemId: 'saveButton'
		        }]
    	    }]
		
		});
		
		return this.callParent(arguments);
	}
});