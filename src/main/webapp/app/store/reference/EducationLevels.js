Ext.define('Ssp.store.reference.EducationLevels', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EducationLevel',
    storeId: 'educationLevelsReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'educationLevel/'});
    }
});