Ext.define('Ssp.view.admin.forms.Challenges', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.ChallengesAdmin',
	title: 'Challenges Admin',
	id: 'ChallengesAdmin',
    store: Ext.getStore('reference.Challenges'),
    plugins: [
              Ext.create('Ext.grid.plugin.RowEditing')
    ], 
    columns: [
         { header: 'Name',  
           dataIndex: 'name',
           field: {
               xtype: 'textfield'
           },
           flex: 50 },
         { header: 'Description',
           dataIndex: 'description', 
           flex: 50,
           field: {
               xtype: 'textfield'
           }
         }
    ],
	
    dockedItems: [{
        xtype: 'toolbar',
        items: [{
            text: 'Add',
            iconCls: 'icon-add',
            handler: function(){
            	
    			Ext.getStore('reference.Challenges').insert(0, new Ssp.model.reference.ChallengeTO() );
    			Ext.getCmp('ChallengesAdmin').plugins[0].startEdit(0, 0);
            }
        }, '-', {
            text: 'Delete',
            iconCls: 'icon-delete',
            handler: function(){
                var selection = Ext.getCmp('ChallengesAdmin').getView().getSelectionModel().getSelection()[0];
                if (selection) {
                	Ext.getStore('reference.Challenges').remove(selection);
                }
            }
        }]
    }]

});