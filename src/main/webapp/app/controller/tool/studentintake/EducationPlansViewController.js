Ext.define('Ssp.controller.tool.studentintake.EducationPlansViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils',
    	model: 'currentStudentIntake',
    	sspConfig: 'sspConfig'
    },
    control: {
    	parentsDegreeField: '#collegeDegreeForParents',
    	collegeDegreeForParentsCheckOn: '#collegeDegreeForParentsCheckOn',
    	collegeDegreeForParentsCheckOff: '#collegeDegreeForParentsCheckOff',
    	specialNeedsField: '#specialNeeds',
    	specialNeedsCheckOn: '#specialNeedsCheckOn',
    	specialNeedsCheckOff: '#specialNeedsCheckOff'
    },
	init: function() {
		var me=this;
		var personEducationPlan = me.model.get('personEducationPlan');
		var parentsDegreeLabel = me.sspConfig.get('educationPlanParentsDegreeLabel');
		var specialNeedsLabel = me.sspConfig.get('educationPlanSpecialNeedsLabel');
		var collegeDegreeForParents = me.model.get('personEducationPlan').get('collegeDegreeForParents')
		var specialNeeds = me.model.get('personEducationPlan').get('specialNeeds');
		me.getParentsDegreeField().setFieldLabel(parentsDegreeLabel);
		me.getSpecialNeedsField().setFieldLabel(specialNeedsLabel);
		
		if ( personEducationPlan != null && personEducationPlan != undefined )
		{
			// college degree for parents
			me.getCollegeDegreeForParentsCheckOn().setValue(collegeDegreeForParents);
			me.getCollegeDegreeForParentsCheckOff().setValue(!collegeDegreeForParents);
			
			me.getSpecialNeedsCheckOn().setValue( specialNeeds );
			me.getSpecialNeedsCheckOff().setValue( !specialNeeds );
		}		

		return me.callParent(arguments);
    }
});