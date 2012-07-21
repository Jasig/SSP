Ext.define('Ssp.controller.admin.campus.EditCampusViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	campusService: 'campusService',
    	formUtils: 'formRendererUtils',
    	model: 'currentCampus',
    	store: 'campusesStore'
    },
    config: {
    	containerToLoadInto: 'adminforms',
    	formToDisplay: 'campusadmin',
    	url: null
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
		this.getView().getForm().reset();
		this.getView().getForm().loadRecord( this.model );
		return this.callParent(arguments);
    },
	onSaveClick: function(button) {
		var me = this; 
		me.getView().getForm().updateRecord();
		me.getView().setLoading( true );
		me.campusService.saveCampus( me.model.data, {
			success: me.saveSuccess,
			failure: me.saveFailure,
			scope: me
		} );
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},

    saveSuccess: function( r, scope ){
		var me=scope;
		me.getView().setLoading( false );
		me.displayMain();
    },
    
    saveFailure: function( response, scope ){
    	var me=scope;  	
    	me.getView().setLoading( false );
    },	
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});