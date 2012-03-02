Ext.define('Ssp.view.admin.forms.Ethnicity', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.EthnicityAdmin',
	title: 'Ethnicity Admin',
	id: 'EthnicitiesAdmin',
    store: Ext.getStore('reference.Ethnicities'),
  
    columns: [
         { header: 'Name',  dataIndex: 'name', flex: 50 },
         { header: 'Description',  dataIndex: 'description', flex: 50 }
    ]

});