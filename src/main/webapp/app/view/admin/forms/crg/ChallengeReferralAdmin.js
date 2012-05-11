Ext.define('Ssp.view.admin.forms.crg.ChallengeReferralAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.challengereferraladmin',
	title: 'Challenge Referral Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.ChallengeReferralAdminViewController',
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
	                  	xtype: 'displayreferralsadmin', 
	                  	flex: 1
	                  },{
		                  	xtype: 'displaychallengereferralsadmin', 
		                  	flex: 1
		                  }
	                 ]});
    	return this.callParent(arguments);
    }
});