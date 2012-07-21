Ext.define('Ssp.model.tool.studentintake.PersonEducationGoal', {
	extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
    		 {name: 'educationGoalId', type: 'string'},
    		 {name: 'description', type: 'string'},
    		 {name: 'plannedOccupation', type: 'string'},
    		 {name: 'howSureAboutMajor', type: 'int'},
    		 {name: 'careerDecided', type:'boolean'},
    		 {name: 'howSureAboutOccupation', type:'int'},
    		 {name: 'confidentInAbilities', type: 'boolean'},
    		 {name: 'additionalAcademicProgramInformationNeeded', type: 'boolean'}]
});