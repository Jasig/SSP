Ext.define('Ssp.controller.admin.campus.EditCampusViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	store: 'campusesStore'
    },
	init: function() {
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		return this.callParent(arguments);
    }
});