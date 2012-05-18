Ext.define('Ssp.controller.tool.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        currentPerson: 'currentPerson',
        sspConfig: 'sspConfig'
    }, 

	init: function() {
		var studentIdAlias = this.sspConfig.get('studentIdAlias');
		studentId = Ext.ComponentQuery.query('#studentId')[0];
		studentId.setFieldLabel(studentIdAlias);
		
		this.getView().loadRecord(this.currentPerson);
		
		return this.callParent(arguments);
    }
});