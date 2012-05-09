Ext.define('Ssp.controller.tool.ActionPlanTasksViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	tasksStore: 'tasksStore'
    	
    },
    config: {
    	filteredTaskStatus: null,
    	filterAuthenticated: false
    },
    
    control: {
    	'taskStatusTabs': {
    		tabchange: 'onTaskStatusTabChange'
    	},
    	
		'filterTasksBySelfCheck': {
			change: 'onFilterTasksBySelfChange'
		},

		'viewHistoryButton': {
			click: 'onViewHistoryClick'
		},

		'addTasksButton': {
			click: 'onAddTasksClick'
		},
		
		'closeTasksButton': {
			click: 'onCloseTasksClick'
		},
		
		'deleteTasksButton': {
			click: 'onDeleteTasksClick'
		},
		
		'emailTasksButton': {
			click: 'onEmailTasksClick'
		},

		'printTasksButton': {
			click: 'onPrintTasksClick'
		}
	},
	
	init: function() {
		this.filteredTaskStatus="ACTIVE";
		this.filterTasks();
		return this.callParent(arguments);
    },

    onTaskStatusTabChange: function(panel, newComp, oldComp, eOpts) {
		this.filteredTaskStatus = newComp.action.toUpperCase();
		this.filterTasks();
    },

    onFilterTasksBySelfChange: function(comp) {
		this.filterAuthenticated=comp.checked;
		this.filterTasks();
    },
    
    filterTasks: function(){
    	var filtersArr = [];
		var filterStatusFunc = null;
		var authenticatedId = this.authenticatedPerson.get('id');
		var filterAuthenticatedFunc = function(item) { return (item.get('createdById') == authenticatedId); }; 

		switch (this.filteredTaskStatus)
		{
			case 'ACTIVE':
				filterStatusFunc = function(item) { return (item.get('completed') == false); };
				break;
			
			case 'COMPLETE':
				filterStatusFunc = function(item) { return (item.get('completed') == true); };
				break;
		}
		
		if (filterStatusFunc != null)
			filtersArr.push(filterStatusFunc);
		
		if (this.filterAuthenticated)
			filtersArr.push(filterAuthenticatedFunc);
		
		// reset filter
		this.tasksStore.clearFilter();
		
		// apply new filters
		this.tasksStore.filter(filtersArr);
    },
    
    onViewHistoryClick: function(button) {
		Ext.Msg.alert('Display History Report.');
    },
    
    onAddTasksClick: function(button) {
		var comp = this.formUtils.loadDisplay('actionplan','addtask', true, {});
    },
    
    onCloseTasksClick: function(button) {
		console.log('ActionPlanToolViewController->onCloseTasksClick');
    },
    
    onDeleteTasksClick: function(button) {
	   console.log('ActionPlanToolViewController->onDeleteTasksClick');
	   var grid = Ext.ComponentQuery.query('actionplantasks')[0];;
	   var store = grid.getStore();
       var selectionArr = grid.getView().getSelectionModel().getSelection();
       var id="";
       if (selectionArr.length > 0) 
       {
    	   Ext.each(selectionArr, function(item, index){
    		   id = item.get('id');
		   	   Ext.Ajax.request({
					url: store.getProxy().url+id,
					method: 'DELETE',
					headers: { 'Content-Type': 'application/json' },
					success: function(response, view) {
						var r = Ext.decode(response.responseText);
						store.remove( item );
					},
					failure: this.apiProperties.handleError
			   }, this);	    		   
    	   });
       }else{
    	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
       }    
    },

    onEmailTasksClick: function(button) {
		Ext.Msg.alert('Email all selected tasks.');
    },
    
    onPrintTasksClick: function(button) {
		Ext.Msg.alert('Print all selected tasks.');
    }
});