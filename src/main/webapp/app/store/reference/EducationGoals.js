Ext.define('Ssp.store.reference.EducationGoals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EducationGoal',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('educationGoal')});
    }
});