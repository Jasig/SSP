Ext.define('Ssp.controller.tool.actionplan.ActionPlanTasksViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	person: 'currentPerson',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	store: 'tasksStore'
    },
    
    config: {
    	filteredTaskStatus: null,
    	filterAuthenticated: false,
    	personTaskUrl: '',
    	personViewHistoryUrl: '',
    	personPrintTaskUrl: ''
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
		
		'emailTasksButton': {
			click: 'onEmailTasksClick'
		},

		'printTasksButton': {
			click: 'onPrintTasksClick'
		}
	},
	
	init: function() {
		var me = this;
		var personId;
		var successFunc = function(response,view){
	    	var r, records;
	    	r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
		    	me.store.loadData(r.rows);
	    	}else{
	    		me.store.loadData([]);
	    	}
    		me.filteredTaskStatus="ACTIVE";
    		me.filterTasks();
		};
		
		personId = this.person.get('id');
		this.personTaskUrl = this.apiProperties.getItemUrl('personTask');
		this.personTaskUrl = this.personTaskUrl.replace('{id}',personId);
		
		this.personViewHistoryUrl = this.apiProperties.getItemUrl('personViewHistory');
		this.personViewHistoryUrl = this.personViewHistoryUrl.replace('{id}',personId);
		this.personPrintTaskUrl = this.apiProperties.getItemUrl('personPrintTask');
		this.personPrintTaskUrl = this.personPrintTaskUrl.replace('{id}',personId);
		
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl(this.personTaskUrl),
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
			return (item.get('createdBy').id == authenticatedId); 
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
		Ext.Msg.alert('ActionPlanTasksViewController->onViewHistoryClick. This item is completed in the ui. Uncomment to display the History Report when it is complete.');
		/*
		var url = this.apiProperties.createUrl( this.personViewHistoryUrl );
        this.apiProperties.makeRequest({
			url: url,
			method: 'GET',
			jsonData: jsonData,
			successFunc: function(){
				// handle response here
			}
		});
		*/
    },

    onEmailTasksClick: function(button) {
		Ext.Msg.alert('Email all selected tasks.');
		// TODO:
		// display a window to assign an email address
		// create an object with the email addresses
		// send the tasks through email
    },
    
    onPrintTasksClick: function(button) {
		Ext.Msg.alert('ActionPlanTasksViewController->onPrintTasksClick. This item is completed in the ui. Uncomment to display the printed task report when it is complete.'); 	
    	/*
    	var grid, url, jsonData;	
		var grid = button.up('panel').down('grid');
	    var jsonData = this.getSelectedIdsArray( grid.getView().getSelectionModel().getSelection() );
	    if (jsonData.length > 0)
	    {
			url = this.apiProperties.createUrl( this.personPrintTaskUrl );
	        this.apiProperties.makeRequest({
				url: url,
				method: 'GET',
				jsonData: jsonData,
				successFunc: function(){
					// handle response here
				}
			});	    	
	    }else{
	    	Ext.Msg.alert('Please select the tasks you would like to print.');
	    }
	    */
    },
    
    getSelectedIdsArray: function(arr){
		var selectedIds = [];
		Ext.each(arr, function(item, index) {
			var obj = {id: item.get('id')};
			selectedIds.push( obj );
		});
		
		return selectedIds;
    }
});