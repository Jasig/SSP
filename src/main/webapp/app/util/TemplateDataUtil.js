Ext.define('Ssp.util.TemplateDataUtil',{
	extend: 'Ext.Component',

	initComponent: function() {
		return this.callParent( arguments );
    },
	
	prepareTemplateData: function( dataStore )
	{
		var arr = dataStore.data.items;
		var cleanArr = new Array();
		for (var i=0; i<arr.length; i++)
		{
			cleanArr.push( arr[i].raw );
		}

		return cleanArr;
	}
	
});