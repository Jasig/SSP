Ext.define('Ssp.store.reference.EducationalGoals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EducationalGoal',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'educationGoal/'});
    }
});