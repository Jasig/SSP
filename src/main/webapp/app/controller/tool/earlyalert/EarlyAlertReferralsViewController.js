Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertReferralsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	earlyAlertResponse: 'currentEarlyAlertResponse',
        store: 'earlyAlertReferralsBindStore'
    },
	init: function() {
		var me=this;
		var url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('earlyAlertReferral') );
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
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
		};
				
		me.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			successFunc: successFunc
		});
		
		return this.callParent(arguments);
    }
});