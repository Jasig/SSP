Ext.define('Ssp.store.reference.EducationLevels', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EducationLevel',
    storeId: 'educationLevelsReferenceStore',
    constructor: function(){
    	this.callParent(arguments);
    	var url = this.getProxy().url;
    	Ext.apply(this.getProxy(),{url: url+'educationLevel/'});
    }
});