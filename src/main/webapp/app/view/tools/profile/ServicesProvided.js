Ext.define('Ssp.view.tools.profile.ServicesProvided', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profileservicesprovided',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ServicesProvidedViewController',	
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