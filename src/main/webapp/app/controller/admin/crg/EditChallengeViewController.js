Ext.define('Ssp.controller.admin.crg.EditChallengeViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	currentChallenge: 'currentChallenge',
    	challengesStore: 'challengesStore'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		}   	
    },
    
	init: function() {
		this.getView().getForm().loadRecord(this.currentChallenge);
		return this.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		console.log('EditChallengeViewController->onSaveClick');
		var record, id, jsonData, comp;
		var formUtils = this.formUtils;
		var apiProps = this.apiProperties;
		this.getView().getForm().updateRecord();
		record = this.currentChallenge;
		id = record.get('id');
		jsonData = record.data;
		console.log(record);
		console.log(jsonData);
		if (id.length > 0)
		{
			// EDITING
			Ext.Ajax.request({
				url: this.challengesStore.getProxy().url+id,
				method: 'PUT',
				headers: { 'Content-Type': 'application/json' },
				jsonData: jsonData,
				success: function(response, view) {
					var r = Ext.decode(response.responseText);
					this.displayMain();
				},
				failure: apiProps.handleError
			}, this);			
		}else{
			// ADDING
			Ext.Ajax.request({
				url: this.challengesStore.getProxy().url,
				method: 'POST',
				headers: { 'Content-Type': 'application/json' },
				jsonData: jsonData,
				success: function(response, view) {
					var r = Ext.decode(response.responseText);
					this.displayMain();
				},
				failure: apiProps.handleError
			}, this);			
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay('adminforms','challengeadmin', true, {});
	}
});