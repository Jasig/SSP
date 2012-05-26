Ext.define('Ssp.controller.tool.journal.JournalToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
        journalEntriesStore: 'journalEntriesStore',
        journalSourcesStore: 'journalSourcesStore',
    	journalTracksStore: 'journalTracksStore',
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editjournal',
    	personJournalUrl: ''
    },
    control: {
    	'addButton': {
			click: 'onAddClick'
		},
		
		'editButton': {
			click: 'onEditClick'
		},
		
		'viewHistoryButton': {
			click: 'onViewHistoryClick'
		}
	},
    init: function() {
		var me = this;
		var personId = this.person.get('id');
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.journalEntriesStore.loadData(r.rows);
	    	}
		};

    	this.confidentialityLevelsStore.load();
		this.journalSourcesStore.load();
		this.journalTracksStore.load();
		
		this.personJournalUrl = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personJournalEntry') );
		this.personJournalUrl = this.personJournalUrl.replace('{id}',personId);		

		this.apiProperties.makeRequest({
			url: this.personJournalUrl,
			method: 'GET',
			successFunc: successFunc
		});
    	
		return this.callParent(arguments);
    },
    
    onAddClick: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    },
    
    onEditClick: function(button){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    },

    onViewHistoryClick: function(button){
	 console.log('JournalToolViewController->onViewHistoryClick');
    }
});