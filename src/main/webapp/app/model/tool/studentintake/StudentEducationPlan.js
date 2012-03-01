Ext.define('Ssp.model.tool.studentintake.StudentEducationPlan', {
	extend: 'Ssp.model.AbstractBaseTO',
    fields: ['id',
             'studentId',
             'studentStatus',
             'newOrientationComplete',
             'registeredForClasses',
             'collegeDegreeForParents',
             'specialNeeds',
             'gradeTypicallyEarned']

});