/**
 * This is a special case where the item selector on the add/edit person
 * appointment view is bound to the store and adds new fields. Since,
 * I don't yet know all of the fields to clean-up and the new
 * fields cause issues in the reference admin tools after loading the
 * bound store, I am creating a separate store to use for this case.
 * TODO: Clean-up this condition and resolve in a better way.
 */
Ext.define('Ssp.store.reference.ReferralSourcesBind', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ReferralSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('referralSource')});
    }
});