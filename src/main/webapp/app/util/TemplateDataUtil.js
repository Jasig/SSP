Ext.define('Ssp.util.TemplateDataUtil',{
	extend: 'Ext.Component',

	init: function() {
        console.log('Initialized util.TemplateDataUtil!');
		
		this.superclass.init.call(this, arguments);
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