Ext.define('Ssp.controller.admin.campus.EditCampusEarlyAlertRoutingViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampusEarlyAlertRouting',
    	campus: 'currentCampus',
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
		}   	
    },
    
	init: function() {
		var me=this;
		me.getView().getForm().reset();
		me.getView().getForm().loadRecord( me.model );
		return me.callParent(arguments);
    },
    
	onSaveClick: function(button) {
		var me = this;
		var record, id, jsonData, url;
		url = me.url;	
		if ( me.getView().getForm().isValid() )
		{
			me.getView().getForm().updateRecord();
			record = me.model;
			id = record.get('id');
			jsonData = record.data;
			
			// ensure null if person is not set
			if (record.data.person == "")
			{
				jsonData.person=null;
			}
			
			me.getView().setLoading( true );
			
			me.service.saveCampusEarlyAlertRouting( me.campus.get('id'), jsonData, {
				success: saveSuccess,
				failure: failureSuccess,
				scope=me
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