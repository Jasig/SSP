Ext.define('Ssp.controller.person.EditPersonViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        person: 'currentPerson',
        sspConfig: 'sspConfig'
    },
    control: {
    	studentIdField: '#studentId'
    },  
	init: function() {
		var me=this;
		var studentIdField = me.getStudentIdField();
		// alias the studentId field
		var studentIdAlias = me.sspConfig.get('studentIdAlias');
		// Set defined configured label for the studentId field
		studentIdField.setFieldLabel(studentIdAlias+'<span style="color: rgb(255, 0, 0); padding-left: 2px;">*</span>');		
		
		me.getView().loadRecord( this.person );
		
		return me.callParent(arguments);
    }
});