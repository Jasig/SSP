Ext.define('Ssp.view.tools.profile.ServicesProvided', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profileservicesprovided',
	width: '100%',
	height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
		            autoScroll: true,
    		        columns: [
    		                { header: 'Provided By',  
    		                  dataIndex: 'createdBy',
    		                  flex: .50,
    		                },{ header: 'Date Provided',  
    		                  dataIndex: 'createdDate',
    		                  flex: .50,
    		                }],
				});
		
		return this.callParent(arguments);
	}
});