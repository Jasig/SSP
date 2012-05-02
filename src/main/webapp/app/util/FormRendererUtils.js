Ext.define('Ssp.util.FormRendererUtils',{	
	extend: 'Ext.Component',
	
	init: function() {
		this.callParent(arguments);
    },	
	
    cleanAll: function(view){
    	if (view.items)
    	{
        	if (view.items.length > 0)
        	{
        		view.removeAll(true);
        	}	
    	}
    },
    
    cleanItems: function(view){
    	var i = view.items.length;
    	while (view.items.length > 0)
    	{
    		var item = view.items.getAt(i);
    		if (item != undefined)
    			view.remove(item, true);
    		i=i-1;
    	}
    },	
	
    getProfileFormItems: function(){
		var cleaner = Ext.create( 'Ssp.util.TemplateDataUtil' );
		var applicationFormsStore =  Ext.getStore('ApplicationForms');
        return cleaner.prepareTemplateData(  applicationFormsStore );  	
    },    
   
    createRadioButtonGroup: function( formId, groupId, itemsArr, selectedItemId, idFieldName, selectedIdFieldName ){
    	var form = Ext.getCmp(formId);
    	var rbGroup = Ext.getCmp(groupId);
		var items = itemsArr;
		var setSelectedItems = false;
		for (i=0; i<items.length; i++)
		{
			var rb = {xtype:'radio'};
			var item = items[i];
			rb.boxLabel = item.name;
			rb.name = selectedIdFieldName;
			rb.inputValue = item[idFieldName];
			if (selectedItemId==item[idFieldName])
			{
				rb.checked = true;
			}
			
			rbGroup.insert(i,rb);
		}
		form.doLayout();
    },
    
    createCheckBoxForm: function( formId, itemsArr, selectedItemsArr, idFieldName, selectedIdFieldName ){
    	var form = Ext.getCmp(formId);
		var items = itemsArr;
		var selectedItems = selectedItemsArr;
		var setSelectedItems = false;
		if (selectedItems != null)
		{
			setSelectedItems = true;
		}
		for (i=0; i<items.length; i++)
		{
			var cb = {xtype:'checkbox'};
			var item = items[i];
			cb.boxLabel = item.name;
			cb.inputValue = item[idFieldName];
			if (setSelectedItems == true)
			{
				for (var s=0; s<selectedItems.length; s++)
				{
					selectedId = selectedItems[s][selectedIdFieldName];
					if (selectedId==item[idFieldName])
					{
						cb.checked = true;
						break;
					}
				}
			}
			
			form.insert(i,cb);
		}
		form.doLayout();
    },
    
    createCheckBoxFormFromStore: function( formId, itemsArr, selectedItemsArr, idFieldName ){
    	form = Ext.getCmp(formId);
		items = itemsArr;
		selectedItems = selectedItemsArr;
		for (i=0; i<items.length; i++)
		{
			var cb = {xtype:'checkbox'};
			var item = items[i];
			var selectionId = item.get(idFieldName);
			var name = item.get('name')
			cb.boxLabel = name;
			cb.inputValue = selectionId;
			for (var s=0; s<selectedItems.length; s++)
			{
				id = selectedItems[s][idFieldName];
				if (id===selectionId)
				{
					cb.checked = true;
					break;
				}
			}
			
			form.insert(i,cb);
		}
		
		form.doLayout();
    },
   
    alphaSortByField: function( arrayToSort, fieldName ){
    	return Ext.Array.sort(arrayToSort, function(a, b){
    		 var nameA=a[fieldName].toLowerCase(), nameB=b[fieldName].toLowerCase()
    		 if (nameA < nameB) //sort string ascending
    		  return -1
    		 if (nameA > nameB)
    		  return 1
    		 return 0 //default return value (no sorting)
    		});
    },
    
    getSelectedValuesAsTransferObject: function( values, modelType ){
		var selectedItems = [];
		for ( prop in values )
		{
			var obj = Ext.create(modelType,{id: values[prop]} );
			selectedItems.push( obj.data );
		}
		return selectedItems;
    }
	
});

