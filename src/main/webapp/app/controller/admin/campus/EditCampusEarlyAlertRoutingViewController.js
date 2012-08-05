Ext.define('Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampusEarlyAlertRouting',
    	campus: 'currentCampus',
    	peopleSearchLiteStore: 'peopleSearchLiteStore',
    	searchService: 'searchService',
    	service: 'campusEarlyAlertRoutingService'
    },
    config: {
    	containerToLoadInto: 'campusearlyalertroutingsadmin',
    	formToDisplay: 'earlyalertroutingsadmin'
    },
    control: {
    	'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		personCombo: '#personCombo'
    },
    
	init: function() {
		var me=this;
		var person;
		me.getView().getForm().reset();
		me.getView().getForm().loadRecord( me.model );
		if (me.model.get('person') != null)
		{
			person = me.model.get('person');
			me.getView().setLoading(true);
			me.searchService.search(person.firstName+' '+person.lastName, true, {
				success: me.searchSuccess,
				failure: me.searchFailure,
				scope: me
			});	
		}
		return me.callParent(arguments);
    },
    
    searchSuccess: function( r, scope ){
    	var me=scope;
    	me.getView().setLoading(false);
    	if (r.rows.length > 0)
    	{
    		me.peopleSearchLiteStore.loadData(r.rows);
    		me.getPersonCombo().setValue(me.model.get('person').id);
    	}
    },
    
    searchFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading(false);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, jsonData, url, selectedPersonId;
		url = me.url;	
		if ( me.getView().getForm().isValid() )
		{
			me.getView().getForm().updateRecord();
			record = me.model;			
			jsonData = record.data;
			
			// set the selected person
			if (me.getPersonCombo().value != "")
			{
				jsonData.person={ id:me.getPersonCombo().value };
			}else{	
				jsonData.person=null;
			}
			
			me.getView().setLoading( true );
			me.service.saveCampusEarlyAlertRouting( me.campus.get('id'), jsonData, {
				success: me.saveSuccess,
				failure: me.saveFailure,
				scope: me
			});			
		}else{
			Ext.Msg.alert('SSP Error', 'Please correct the errors before saving this item.');
		}
	},

	saveSuccess: function( r, scope ) {
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
	},

	saveFailure: function( response, scope ) {
		var me=scope;
		me.getView().setLoading( false );
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});