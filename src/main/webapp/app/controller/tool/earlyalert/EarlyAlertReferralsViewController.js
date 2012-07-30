Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertReferralsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	earlyAlertResponse: 'currentEarlyAlertResponse',
    	service: 'earlyAlertReferralService',
        store: 'earlyAlertReferralsBindStore'
    },
	init: function() {
		var me=this;
		
		me.service.getAll({
			success: me.getAllSuccess,
			failure: me.getAllFailure,
			scope: me
		});
		
		return this.callParent(arguments);
    },

	getAllSuccess: function( r, scope ){
    	var me=scope;
    	var items;
    	var view = me.getView();
    	var selectedReferrals = me.earlyAlertResponse.get('earlyAlertReferralIds');
    	if (r.rows.length > 0)
    	{
    		me.store.loadData(r.rows);
    		
    		items = [{
	            xtype: 'itemselectorfield',
	            itemId: 'earlyAlertReferralsItemSelector',
	            name: 'earlyAlertReferrals',
	            anchor: '100%',
	            height: 250,
	            fieldLabel: 'Department Referrals',
	            store: me.store,
	            displayField: 'name',
	            valueField: 'id',
	            value: ((selectedReferrals.length>0) ? selectedReferrals : [] ),
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