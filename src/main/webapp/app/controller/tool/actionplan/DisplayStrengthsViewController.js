Ext.define('Ssp.controller.tool.actionplan.DisplayStrengthsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils',
    	model: 'currentPerson',
    	service: 'personService'
    },
    
    control: {  	
    	saveButton: {
    		selector: '#saveButton',
    		listeners: {
    			click: 'onSaveClick'
    		}
    	},
    	
    	strengthsField: {
    		selector: '#strengths',
    		listeners: {
    			change: 'onStrengthsChange'
    		}
    	},
    	
    	saveSuccessMessage: '#saveSuccessMessage'
	},
	
	init: function() {
		var me=this;
		me.getView().getForm().loadRecord( me.model );
		me.getSaveButton().disabled=true;  	
		me.getStrengthsField().setDisabled( !me.authenticatedPerson.hasAccess('ACTION_PLAN_STRENGTHS_FIELD') );
		return me.callParent(arguments);
    },
    
    onSaveClick: function(button) {
		var me=this;
		var form = me.getView().getForm();
		var jsonData;
		if (form.isValid())
		{
			form.updateRecord();
			jsonData = me.model.data;
			jsonData = me.model.setPropsNullForSave( me.model.data );
			console.log(jsonData);
			me.getView().setLoading('true');
			me.service.save( jsonData , {
				success: me.savePersonSuccess,
				failure: me.savePersonFailure,
				scope: me
			});	
		}else{
			Ext.Msg.alert('Unable to save strengths. Please correct the errors in the form.');
		}	
    },

    savePersonSuccess: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
		me.model.commit();
		me.setSaveButtonState();
		me.formUtils.displaySaveSuccessMessage( me.getSaveSuccessMessage() );
    },

    savePersonFailure: function( r, scope){
    	var me=scope;
    	me.getView().setLoading( false );
    },    
    
    onStrengthsChange: function(comp, oldValue, newValue, eOpts){
    	this.setSaveButtonState();
    },
    
    setSaveButtonState: function(){
    	this.getSaveButton().disabled = !this.getView().getForm().isDirty();
    }
});