Ext.define('Ssp.controller.person.ReferralSourcesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	person: 'currentPerson',
        store: 'referralSourcesBindStore'
    },
	init: function() {
		var me=this;
		var url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('referralSource') );
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
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
		};
				
		me.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			successFunc: successFunc
		});
		
		return this.callParent(arguments);
    }
});