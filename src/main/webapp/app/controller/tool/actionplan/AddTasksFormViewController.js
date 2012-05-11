Ext.define('Ssp.controller.tool.actionplan.AddTasksFormViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'actionplan'
    },    
    control: {
		'addButton': {
			click: 'onAddClick'
		},
		
		'closeButton': {
			click: 'onCloseClick'
		}
	},
    
    init: function() {
		return this.callParent(arguments);
    },
    
    onAddClick: function(button){
    	console.log('AddTasksFormViewController->onAddClick');    	
    },
    
    onCloseClick: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    }    
});