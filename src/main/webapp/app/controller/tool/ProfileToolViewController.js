Ext.define('Ssp.controller.tool.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
        person: 'currentPerson',
        sspConfig: 'sspConfig'
    },
    
    control: {
    	nameField: '#studentName',
    	studentIdField: '#studentId',
    	birthDateField: '#birthDate',
    	
    	'viewHistoryButton': {
			click: 'onViewHistoryClick'
		}
    },
	init: function() {
		var me=this;
		me.getView().getForm().reset();
		var id=me.person.get('id');
		var personUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('person') );
		var studentIdAlias = me.sspConfig.get('studentIdAlias');			
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
    		me.person.populateFromGenericObject(r);
    		// load general student record
    		me.getView().loadRecord( me.person );
    		
    		// load additional values
    		me.getStudentIdField().setFieldLabel( studentIdAlias );
    		me.getNameField().setValue( me.person.getFullName() );
    		me.getBirthDateField().setValue( me.person.getFormattedBirthDate() );
		};
		
		// load the person record
		me.apiProperties.makeRequest({
			url: personUrl+id,
			method: 'GET',
			successFunc: successFunc 
		});
		
		return this.callParent(arguments);
    },
    
    onViewHistoryClick: function(button){
   	 this.appEventsController.getApplication().fireEvent("viewHistory");
    },
});