Ext.define('Ssp.controller.admin.campus.CampusEarlyAlertRoutingsAdminViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	formUtils: 'formRendererUtils'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	campusEditorForm: 'campusadmin'
    },
    control: {
    	'finishButton': {
			click: 'onFinishClick'
		}
    },	
    init: function() {
		return this.callParent(arguments);
    },
    
	onFinishClick: function( button ){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getCampusEditorForm(), true, {});
	}
});