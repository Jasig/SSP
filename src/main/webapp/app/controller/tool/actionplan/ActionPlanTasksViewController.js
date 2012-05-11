Ext.define('Ssp.controller.tool.actionplan.ActionPlanTasksViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	currentPerson: 'currentPerson',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	store: 'tasksStore'
    	
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
		var me = this;
		var successFunc = function(response,view){
	    	var r, records;
	    	r = Ext.decode(response.responseText);
	    	if (r.length > 0)
	    	{
	    		if (r.rows != null)
	    		{
		    		me.store.loadData(r.rows);
	    		}
	    	}else{
	    		var sampDataArr = [{"id" : "23befc50-7f91-11e1-b0c4-0800200c9a66",
	    	        "createdDate" : "1332216000000",
	    	        "createdById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	    	        "modifiedDate" : "1332216000000",
	    	        "modifiedById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	    	        "objectStatus" : "ACTIVE",
	    	        "name" : "Family Services",
	    	        "description" : "FAMILY SERVICES:  Contact Pat Davis at Family Services Association 222-9481 for parenting education program.  Parenting 101 Classes are offered throughout the year.  Sliding fee scale will establish the fee and scholarships may be available.",
	    	        "dueDate" : "04/23/2012",
	    	        "reminderSentDate" : "1332216000000",
	    	        "completed" : "true",
	    	        "completedDate" : "1332216000000",
	    	        "challengeId" : "9D6E3B8F-AFB3-4D86-A527-9778035B94E1",
	    	        "deletableByStudent" : "true",
	    	        "closableByStudent" : "true",
	    	        "confidentialityLevel" : "EVERYONE",
	    	        "type" : "SSP"},
	    		{"id" : "7ed6d720-7f91-11e1-b0c4-0800200c9a66",
	    	        "createdDate" : "1332216000000",
	    	        "createdById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	    	        "modifiedDate" : "1332216000000",
	    	        "modifiedById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	    	        "objectStatus" : "ACTIVE",
	    	        "name" : "Montgomery County Child Support Enforcement Agency",
	    	        "description" : "Contact Montgomery County Child Support Enforcement Agency for enforcement of child support orders 225-4600 www.mcsea.org.",
	    	        "dueDate" : "04/23/2012",
	    	        "reminderSentDate" : "",
	    	        "completed" : "false",
	    	        "completedDate" : "1332216000000",
	    	        "challengeId" : "9D6E3B8F-AFB3-4D86-A527-9778035B94E1",
	    	        "deletableByStudent" : true,
	    	        "closableByStudent" : true,
	    	        "confidentialityLevel" : "DISABILITY",
	    	        "type" : "SSP"},
	    	        {"id" : "7ed6d720-7f91-11e1-b0c4-0800200c9a66",
	    	        "createdDate" : "1332216000000",
	    	        "createdById" : "7d36a3a9-9f8a-4fa9-8ea0-e6a38d2f4194",
	    	        "modifiedDate" : "1332216000000",
	    	        "modifiedById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	    	        "objectStatus" : "ACTIVE",
	    	        "name" : "Montgomery County Child Support Enforcement Agency",
	    	        "description" : "Contact Montgomery County Child Support Enforcement Agency for enforcement of child support orders 225-4600 www.mcsea.org.",
	    	        "dueDate" : "04/23/2012",
	    	        "reminderSentDate" : "",
	    	        "completed" : "false",
	    	        "completedDate" : "1332216000000",
	    	        "challengeId" : "9D6E3B8F-AFB3-4D86-A527-9778035B94E1",
	    	        "deletableByStudent" : true,
	    	        "closableByStudent" : true,
	    	        "confidentialityLevel" : "DISABILITY",
	    	        "type" : "SSP"}];
	    		me.store.loadData(sampDataArr);
	    	}
    		me.filteredTaskStatus="ACTIVE";
    		me.filterTasks();
		};
		
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl('person/'+ this.currentPerson.get('id') + '/task/'),
			method: 'GET',
			successFunc: successFunc 
		});
		
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
		var filterAuthenticatedFunc = function(item) { 
			return (item.get('createdById') == authenticatedId); 
		}; 

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
		this.store.clearFilter();
		
		// apply new filters
		this.store.filter(filtersArr);
    },
    
    onViewHistoryClick: function(button) {
		Ext.Msg.alert('Display History Report.');
    },
    
    onAddTasksClick: function(button) {
		var comp = this.formUtils.loadDisplay('tools','addtask', true, {});
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