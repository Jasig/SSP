Ext.define('Ssp.model.tool.studentintake.PersonEducationLevel', {
	extend: 'Ssp.model.AbstractBase',
    fields: ['personId',
             'educationLevelId',
             'graduatedYear',
             'highestGradeCompleted',
             'lastYearAttended',
             'description',
             'schoolName']
});