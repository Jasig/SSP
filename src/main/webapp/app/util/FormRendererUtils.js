Ext.define('Ssp.util.FormRendererUtils',{
	
	extend: 'Ext.Component',
	
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
			
			form.insert(form.items.length,cb);
		}
		form.doLayout();
    }
	
});

