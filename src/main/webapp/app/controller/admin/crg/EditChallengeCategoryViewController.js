Ext.define('Ssp.controller.admin.crg.EditChallengeCategoryViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	currentChallengeCategory: 'currentChallengeCategory',
    	challengeCategoriesStore: 'challengeCategoriesStore'
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
       this.getView().getForm().loadRecord(this.currentChallengeCategory);
		
		return this.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData;
		var url = this.challengeCategoriesStore.getProxy().url;
		this.getView().getForm().updateRecord();
		record = this.currentChallengeCategory;
		id = record.get('id');
		jsonData = record.data;
		successFunc = function(response, view) {
			me.displayMain();
		};
		
		if (id.length > 0)
		{
			this.apiProperties.makeRequest({
				url: url+id,
				method: 'PUT',
				jsonData: jsonData,
				successFunc: successFunc 
			});
			
		}else{
			
			this.apiProperties.makeRequest({
				url: url,
				method: 'POST',
				jsonData: jsonData,
				successFunc: successFunc 
			});		
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay('adminforms','challengeadmin', true, {});
	}
});