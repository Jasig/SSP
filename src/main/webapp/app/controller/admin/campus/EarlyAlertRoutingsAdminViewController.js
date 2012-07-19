Ext.define('Ssp.controller.admin.campus.EarlyAlertRoutingsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	store: 'campusEarlyAlertRoutingsStore',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    },
	init: function() {
		var me=this;
		var campusId = me.model.get('id');
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('campusEarlyAlertRouting') );
		baseUrl = baseUrl.replace( '{campusId}', campusId );
		if (campusId != "")
		{
			me.store.load( baseUrl );
		}
		return this.callParent(arguments);
    }
});