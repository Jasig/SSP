Ext.define('Ssp.util.FormRendererUtils',{	
	extend: 'Ext.Component',
	
	initComponent: function() {
		return this.callParent(arguments);
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
    
    /**
     * If you provide an 'Other' option in the items array
     * then an 'Other Description' field will be created
     * and inserted into the form alongside the checkboxes.
     * 
     * Assumes an items array with: {id: id, name: name, description: description}
     * @formId = the form to add the items into
     * @itemsArr = the array of items to add to the form
     * @selectedItemsArr = the array of items to select in the form
     * @idFieldName = the id field in the items array
     * @selectedIdFieldName - the id field in the selectedItems array. 
     *                        This value can be the same name or a different name than the idFieldName.
     * @fieldSetTitle - Provides a fieldset title for the fields
     */
    createCheckBoxForm: function( formId, fieldSetTitle, itemsArr, selectedItemsArr, idFieldName, selectedIdFieldName ){
    	var form = Ext.getCmp(formId);
		var selectedItems = selectedItemsArr;
		var selectedItem;
		var selectedId;
		var setSelectedItems = false;
		var otherId = "";
		var fieldSet = {xtype: 'fieldset', padding: 0, layout: { type: 'auto' },title: fieldSetTitle};
		var formFields = [];
		var selectedItems = selectedItemsArr || [];
		Ext.each(itemsArr, function(item, index){
			// create the items for the form
			var cb = {xtype:'checkbox'};
			cb.boxLabel = item.name;
			cb.name = item.name;
			cb.inputValue = item[idFieldName];
			if (item.name.toLowerCase()=='other')
			{
				otherId = item[idFieldName];
				var fields = {xtype: 'fieldset', padding: 0, layout: { type: 'auto' },title: ''};
				var otherField = { xtype: 'textfield', name: 'otherDescription', fieldLabel: 'Description' };
				cb.anchor = '100%';
				Ext.apply(fields, {items: [cb, otherField]});
			}
			// set selected items in the form
			for (var s=0; s<selectedItems.length; s++)
			{
				selectedItem = selectedItems[s]
				selectedId = selectedItem[selectedIdFieldName];					
				if (selectedId==item[idFieldName])
				{
					cb.checked = true;
					if (selectedId == otherId)
					{
						otherField.value = selectedItem['description'];
					}					
					break;
				}
			}
			// insert the fields in the form
			if (item.name.toLowerCase()!='other')
			{
				formFields.push(cb);
			}else{
				formFields.push(fields);
			}
		});
		form.removeAll();
		Ext.apply(fieldSet, {items: formFields});
		form.insert(form.items.length, fieldSet);
    },
    
    findPropByName: function(obj, propName){
    	var result = "";
    	Ext.iterate(obj, function(key, value) {
    		if (key.toLowerCase()=='otherdescription')
			{
    			result = value;
			}
		},this);
    	return result;
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
    },
    
 	/*
	 * load a display into a container in the interface.
	 * @containerAlias = alias/xtype of the container into which to load the display.
	 * @compAlias = alias/xtype of the component that is loaded
	 * @removeExisting = boolean: true=remove existing items before load, 
	 *                            false=keep existing items
	 * @args = optional params space
	 */	
	loadDisplay: function( containerAlias, compAlias, removeExisting, args ) {	
		var comp = null;
		var store = null;
		var view = Ext.ComponentQuery.query(containerAlias.toLowerCase())[0];
		
		if (view.items.length > 0 && removeExisting==true)
			view.removeAll();

		// create the new widget
		comp =  Ext.createWidget(compAlias.toLowerCase());	
		
		// add to the container
		view.add( comp );
		
		return comp;
	},

    /*
     * Compares a value against a record in a store, based on a provided
     * field for the comparison, as well as, criteria to find the record
     * in the store to use to compare against. If the values match
     * then the associated field will be hidden in the interface. Otherwise,
     * the field is shown.
     * @elementName - the field to hide
     * @compareValue - the value to compare against
     * @compareFieldName - the field in a record to compare against. For example: 'id'
     * @store - the store in which to find a value to compare against
     * @recordField - the name of the field in the store to find a record
     * @recordValue - the value of the field in the store to find a record against
     */
    showHideFieldByStoreValue: function( elementName, compareValue, compareFieldName, store, recordField, recordValue ){
		var queryValue = '#'+elementName;
    	var field = Ext.ComponentQuery.query(queryValue)[0];
		var record = store.findRecord(recordField, recordValue);
		if ( compareValue==record.get( compareFieldName ) )
		{
			field.show();
		}else{
			field.hide();
		}
    }    
    
});

