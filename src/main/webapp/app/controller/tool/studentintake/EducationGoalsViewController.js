Ext.define('Ssp.controller.tool.studentintake.EducationGoalsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	formUtils: 'formRendererUtils',
    	model: 'currentStudentIntake',
    	sspConfig: 'sspConfig'
    },
    control: {
    	careerDecidedCheckOn: '#careerDecidedCheckOn',
    	careerDecidedCheckOff: '#careerDecidedCheckOff',
    	confidentInAbilitiesCheckOn: '#confidentInAbilitiesCheckOn',
    	confidentInAbilitiesCheckOff: '#confidentInAbilitiesCheckOff',
    	additionalAcademicProgramInformationNeededCheckOn: '#additionalAcademicProgramInformationNeededCheckOn',
    	additionalAcademicProgramInformationNeededCheckOff: '#additionalAcademicProgramInformationNeededCheckOff'
    },
	init: function() {
		var me=this;
		var personEducationGoal = me.model.get('personEducationGoal');
		var careerDecided = me.model.get('personEducationGoal').get('careerDecided')
		var confidentInAbilities = me.model.get('personEducationGoal').get('confidentInAbilities');
		var additionalAcademicProgramInformationNeeded = me.model.get('personEducationGoal').get('additionalAcademicProgramInformationNeeded');
		
		if ( personEducationGoal != null && personEducationGoal != undefined )
		{
			me.getCareerDecidedCheckOn().setValue( careerDecided );
			me.getCareerDecidedCheckOff().setValue( !careerDecided );
			
			me.getConfidentInAbilitiesCheckOn().setValue( confidentInAbilities );
			me.getConfidentInAbilitiesCheckOff().setValue( !confidentInAbilities );
			
			me.getAdditionalAcademicProgramInformationNeededCheckOn().setValue( additionalAcademicProgramInformationNeeded );
			me.getAdditionalAcademicProgramInformationNeededCheckOff().setValue( !additionalAcademicProgramInformationNeeded );
		}		

		return me.callParent(arguments);
    }
});