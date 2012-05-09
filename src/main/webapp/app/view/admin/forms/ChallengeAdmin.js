Ext.define('Ssp.view.admin.forms.ChallengeAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.challengeadmin',
	title: 'Challenge Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.ChallengeAdminViewController',
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
	                  	xtype: 'displaychallengesadmin', 
	                  	flex: 1
	                  },{
	                  	xtype: 'displaychallengecategoriesadmin', 
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});