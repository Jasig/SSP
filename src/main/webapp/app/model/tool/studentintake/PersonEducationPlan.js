Ext.define('Ssp.model.tool.studentintake.PersonEducationPlan', {
	extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
             {name: 'studentStatusId', type: 'string'},
             {name: 'newOrientationComplete', type: 'boolean'},
             {name: 'registeredForClasses', type: 'boolean'},
             {name: 'collegeDegreeForParents', type: 'boolean'},
             {name: 'specialNeeds', type: 'boolean'},
             {name: 'gradeTypicallyEarned', type: 'string'}]
});