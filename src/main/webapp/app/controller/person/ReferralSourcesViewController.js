Ext.define('Ssp.controller.person.ReferralSourcesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	person: 'currentPerson',
        store: 'referralSourcesBindStore',
        service: 'referralSourceService'
    },
	init: function() {
		var me=this;
		
		me.service.getAll({
			success: me.getAllSuccess,
			failure: me.getAllFailure,
			scope: me
		});
		
		return me.callParent(arguments);
    },
    
	getAllSuccess: function( r, scope ){
		var me=scope;
    	var items;
    	var view = me.getView();
    	var selectedReferralSources = me.columnRendererUtils.getSelectedIdsForMultiSelect( me.person.get('referralSources') );
    	if (r.rows.length > 0)
    	{
    		me.store.loadData(r.rows);
    		
    		items = [{
	            xtype: 'itemselectorfield',
	            itemId: 'referralSourcesItemSelector',
	            name: 'referralSources',
	            anchor: '100%',
	            height: 250,
	            fieldLabel: 'Referral Sources',
	            store: me.store,
	            displayField: 'name',
	            valueField: 'id',
	            value: ((selectedReferralSources.length>0) ? selectedReferralSources : [] ),
	            allowBlank: true,
	            buttons: ["add", "remove"]
	        }];
    		
    		view.add(items);
    	}
	},
	
    getAllFailure: function( response, scope ){
    	var me=scope;  	
    }
});