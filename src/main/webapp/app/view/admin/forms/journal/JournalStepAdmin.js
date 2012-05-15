Ext.define('Ssp.view.admin.forms.journal.JournalStepAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.journalstepadmin',
	title: 'Challenge Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.JournalStepAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displaystepsadmin', 
	                  	flex: 1
	                  },{
	                  	xtype: 'associatetrackstepsadmin', 
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});