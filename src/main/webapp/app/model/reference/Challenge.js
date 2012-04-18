Ssp.ChallengeTO = Ext.define('Ssp.model.reference.Challenge', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name: 'tags', type:'string'},
             {name: 'showInStudentIntake', type: 'boolean'},
             {name: 'showInSelfHelpSearch', type: 'boolean'},
             {name: 'selfHelpGuideQuestion', type: 'string'},
             {name: 'selfHelpGuideDescription', type: 'string'}
             //,{name: 'defaultConfidentialityLevel', type: 'string'}
             ]
});