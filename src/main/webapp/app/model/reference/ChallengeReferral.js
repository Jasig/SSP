Ext.define('Ssp.model.reference.ChallengeReferral', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name: 'publicDescription', type: 'string'},
             {name: 'showInSelfHelpGuide', type: 'boolean'}]
});