Ext.define('Ssp.controller.person.SpecialServiceGroupsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'specialServiceGroupsBindStore'
    },
    control: {
    	itemSelector: '#specialServiceGroupsItemSelector'
    },
    
	init: function() {
		var me=this;
		var url = this.apiProperties.createUrl( this.apiProperties.getItemUrl('specialServiceGroup') );
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	var is = me.getItemSelector();
	    	console.log(r);
	    	if (r.rows.length > 0)
	    	{
	    		me.store.loadData(r.rows);
	    		is.bindStore(me.store);
	    	}
		};
				
		me.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			successFunc: successFunc
		});
		
		return this.callParent(arguments);
    },
    
    destroy: function(){
    	var is = this.getItemSelector();
    	var store = this.store;
        is.clearValue();
        
        if(store.getCount()>0){
            is.fromField.store.removeAll();
        }
    }
});