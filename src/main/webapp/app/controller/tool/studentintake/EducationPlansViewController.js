Ext.define('Ssp.controller.tool.studentintake.EducationPlansViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	sspConfig: 'sspConfig'
    },

	init: function() {
		var parentsDegreeLabel = this.sspConfig.get('educationPlanParentsDegreeLabel');
		var specialNeedsLabel = this.sspConfig.get('educationPlanSpecialNeedsLabel');
		var parentsDegree = Ext.ComponentQuery.query('#parentsDegree')[0].setFieldLabel(parentsDegreeLabel);
		var specialNeeds = Ext.ComponentQuery.query('#specialNeeds')[0].setFieldLabel(specialNeedsLabel);
        
		return this.callParent(arguments);
    }
});