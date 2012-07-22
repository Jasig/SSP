Ext.define('Ssp.controller.person.SpecialServiceGroupsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	person: 'currentPerson',
    	store: 'specialServiceGroupsBindStore',
    	service: 'specialServiceGroupService'
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
    	var selectedSpecialServiceGroups = me.columnRendererUtils.getSelectedIdsForMultiSelect( me.person.get('specialServiceGroups') );
    	if (r.rows.length > 0)
    	{
    		me.store.loadData(r.rows);
    		
    		items = [{
	            xtype: 'itemselectorfield',
	            itemId: 'specialServiceGroupsItemSelector',
	            name: 'specialServiceGroups',
	            anchor: '100%',
	            height: 250,
	            fieldLabel: 'Service Groups',
	            store: me.store,
	            displayField: 'name',
	            valueField: 'id',
	            value: ((selectedSpecialServiceGroups.length>0) ? selectedSpecialServiceGroups : [] ),
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