Ext.define('Ssp.model.reference.ChallengeCategory', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [],
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'challengeCategory/'});
    }
});