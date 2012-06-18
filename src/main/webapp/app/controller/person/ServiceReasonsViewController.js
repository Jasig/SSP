Ext.define('Ssp.controller.person.ServiceReasonsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils',
        person: 'currentPerson',
        serviceReasonsStore: 'serviceReasonsStore'
    },
    
	init: function() {
		var me=this;

		var serviceReasonsSuccessFunc = function(records,operation,success){
			if (records.length > 0)
	    	{
	    		var items = [];
				Ext.Array.each(records,function(item,index){
	    			items.push(item.raw);
	    		});
				var serviceReasonsFormProps = {
	    				mainComponentType: 'checkbox',
	    				formId: 'personservicereasons', 
	                    fieldSetTitle: 'Select all that apply:',
	                    itemsArr: items, 
	                    selectedItemsArr: [], 
	                    idFieldName: 'id', 
	                    selectedIdFieldName: 'serviceReasonId',
	                    additionalFieldsMap: [] };
	    		
	    		me.formUtils.createForm( serviceReasonsFormProps );	    		
	    	}
		};
		
		me.serviceReasonsStore.load({scope: me, callback: serviceReasonsSuccessFunc});
		
		return this.callParent(arguments);
    }
});