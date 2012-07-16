Ext.define('Ssp.view.tools.earlyalert.EarlyAlertReferrals', {
	extend: 'Ext.form.Panel',
	alias: 'widget.earlyalertreferrals',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertReferralsViewController',
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(this, 
				{
				    bodyPadding: 5,
				    layout: 'anchor'
				});
		
		return me.callParent(arguments);
	}
});