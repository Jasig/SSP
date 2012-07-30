Ext.define('Ssp.service.AbstractService', {  
    extend: 'Ext.Component',	
    initComponent: function() {
		return this.callParent( arguments );
    },
    
    /**
     * Remove all inactive objects from an array
     * of items 
     */
    filterInactiveChildren: function( arr ){
    	var me=this;
    	var itemsToErase = new Array();
    	if (arr != null)
    	{
    		itemsToErase = me.collectInactiveChildren( arr );
    	}
  
    	Ext.Array.forEach(itemsToErase,function(item,idx){
    		// Erase each inactive steps
			Ext.Array.remove(item.array, item.inactiveItem);
    	});

    	return arr;
    },
    
    /**
     * Method to determine if a collection of objects
     * and their children are inactive
     */
    collectInactiveChildren: function( arr ){
		var me=this;
    	var itemsToErase = [];
    	Ext.Array.each( arr, function( item, s){
    		// determine inactive
    		if ( me.isItemInactive( item ) )
    		{
    			itemsToErase.push({array:arr, inactiveItem: item}); 			
    		}else{
    			// recurse children
    			for (prop in item)
    			{
    				if (item[prop] instanceof Array)
    				{
    					var items = me.collectInactiveChildren( item[prop] );
    					itemsToErase = Ext.Array.merge(itemsToErase, items);
    				}
    			} 
    		}
    	});
    	return itemsToErase;
    },
    
    /**
     * Determine if an object is Inactive
     */
    isItemInactive: function( item ){
    	var isInactive = false;
    	for (prop in item)
    	{
    		if (prop == "objectStatus")
    		{
    			if (item[prop].toLowerCase()=="inactive")
    			{
    				isInactive=true;
    			}
    		}
    	} 
    	return isInactive;
    }
});