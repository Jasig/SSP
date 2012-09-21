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
	            buttons: ["add", "remove"],
				listeners: {
					toField: {
						boundList: {
							scope: me,
							drop: me.maybeRefireFromFieldLoadWithNonEmptyStore
						}
					},
					fromField: {
						boundList: {
							scope: me,
							itemdblclick: me.maybeRefireFromFieldLoadWithNonEmptyStore

						}
					}
				}
	        }];
    		view.add(items);
			me.registerAdditionalListeners();
    	}
	},
	
    getAllFailure: function( response, scope ){
    	var me=scope;  	
    },

	// TODO abstract copy/paste from SpecialServiceGroupsViewController.js
	registerAdditionalListeners: function() {
		var me = this;
		var itemSelector = me.findItemSelector();
		var addButton = itemSelector.query('button[iconCls=x-form-itemselector-add]')[0];
		var origAddButtonHandler = addButton.handler;
		addButton.setHandler(function() {
			var me = this;
			origAddButtonHandler.apply(itemSelector);
			me.maybeRefireFromFieldLoadWithNonEmptyStore();
		}, me);
	},

	// TODO abstract copy/paste from SpecialServiceGroupsViewController.js
	maybeRefireFromFieldLoadWithNonEmptyStore: function() {
		var me = this;
		var itemSelector = me.findItemSelector();
		var fromField = me.itemSelector.fromField;
		var toField = me.itemSelector.toField;
		var origGetCount = fromField.store.getCount;
		if ( origGetCount.apply(fromField.store) === 0 ) {
			fromField.store.getCount = function() { return 1; };
			fromField.store.fireEvent('load', fromField.store);
			fromField.store.getCount = origGetCount;
		}
	},

	// TODO abstract copy/paste from SpecialServiceGroupsViewController.js
	findItemSelector: function() {
		var me = this;
		me.itemSelector = me.itemSelect ||  me.getView().form.findField("referralSources");
		return me.itemSelector;
	}
});