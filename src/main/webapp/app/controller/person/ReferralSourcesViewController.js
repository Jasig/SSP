Ext.define('Ssp.controller.person.ReferralSourcesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        store: 'referralSourcesStore'
    },
    control: {
    	itemSelector: '#referralSourcesItemSelector'
    },
    
	init: function() {
		this.store.load({
		    scope:this,
		    callback:function(records, operation, success){                 
		        if(success){
		        	var is = this.getItemSelector();
		        	is.bindStore(this.store);
		        }
		    }
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