Ext.define('Ssp.controller.StudentRecordViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
	inject: {
		appEventsController: 'appEventsController'
	},
	
    control: {
		view: {
			collapse: 'onCollapsed',
			expand: 'onExpanded'
		}
	},
	
    init: function() {
 		return this.callParent(arguments);
    },
    
    onCollapsed: function(){
    	this.appEventsController.getApplication().fireEvent('collapseStudentRecord');
    },
    
    onExpanded: function(){
    	this.appEventsController.getApplication().fireEvent('expandStudentRecord');
    }
});