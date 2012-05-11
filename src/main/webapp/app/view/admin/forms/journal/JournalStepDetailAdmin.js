Ext.define('Ssp.view.admin.forms.journal.JournalStepDetailAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.journalstepdetailadmin',
	title: 'Challenge Referral Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.JournalStepDetailAdminViewController',
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
	                  	xtype: 'displaydetailsadmin', 
	                  	flex: 1
	                  },{
		                  	xtype: 'associatestepdetailsadmin', 
		                  	flex: 1
		                  }
	                 ]});
    	return this.callParent(arguments);
    }
});