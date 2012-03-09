Ext.define('Ssp.util.FormRendererUtils',{	
	extend: 'Ext.Component',

	init: function() {
        console.log('Initialized util.FormRendererUtils!');
		
		this.superclass.init.call(this, arguments);
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
    
    createCheckBoxForm: function( formId, itemsArr, selectedItemsArr, idFieldName ){
    	form = Ext.getCmp(formId);
		items = itemsArr;
		selectedItems = selectedItemsArr;
		for (i=0; i<items.length; i++)
		{
			var cb = {xtype:'checkbox'};
			var item = items[i];
			cb.boxLabel = item.name;
			cb.inputValue = item[idFieldName];
			for (var s=0; s<selectedItems.length; s++)
			{
				id = selectedItems[s][idFieldName];
				if (id===item[idFieldName])
				{
					cb.checked = true;
					break;
				}
			}
			
			form.insert(i-1,cb);
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
			
			form.insert(form.items.length,cb);
		}
		
		form.doLayout();
    },
    
    getSelectedValuesAsTransferObject: function( values, modelType ){
		var selectedItems = [];
		for ( prop in values )
		{
			var obj = Ext.create(modelType,{id: values[prop]} );
			selectedItems.push( obj );
		}
		return selectedItems;
    }
	
});

