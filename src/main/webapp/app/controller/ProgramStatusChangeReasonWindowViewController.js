Ext.define('Ssp.controller.ProgramStatusChangeReasonWindowViewController', {
	extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	personLite: 'personLite',
    	programStatusChangeReasonsStore: 'programStatusChangeReasonsStore',
    	personProgramStatusService: 'personProgramStatusService'
    },
    control: {
		view: {
			show: 'onShow'
		},
		
		'saveButton': {
			click: 'onSaveClick'
		},
		
		'cancelButton': {
			click: 'onCancelClick'
		},
		
		programStatusChangeReasonCombo: '#programStatusChangeReasonCombo'
	},
	
	init: function() {	
    	var me=this;
		return me.callParent(arguments);
    },
    
    onShow: function(){
    	var me=this;
		me.getProgramStatusChangeReasonCombo().reset();
    	me.programStatusChangeReasonsStore.load({params:{start:0, limit:50}});
    },
    
    onSaveClick: function( button ){
	   	var me=this;
	   	var personId = me.personLite.get('id');
	   	var valid = me.getProgramStatusChangeReasonCombo().isValid();
	   	var reasonId = me.getProgramStatusChangeReasonCombo().value;
	   	if (valid && reasonId != "")
	   	{
		   	if (personId != "")
		   	{
		   		// TODO: Look-up Non-Participating Program Status Id
		   		personProgramStatus = new Ssp.model.PersonProgramStatus();
		   		personProgramStatus.set('programStatusId','b2d125c3-5056-a51a-8004-f1dbabde80c2');
		   		personProgramStatus.set('effectiveDate', new Date());
		   		personProgramStatus.set('programStatusChangeReasonId', reasonId );
			   	me.getView().setLoading( true );
		   		me.personProgramStatusService.save( 
		   				personId, 
		   				personProgramStatus.data, 
		   				{
		   			success: me.saveProgramStatusSuccess,
		               failure: me.saveProgramStatusFailure,
		               scope: me 
		        });
		   	}else{
		   		Ext.Msg.alert('SSP Error','Unable to determine student to set to No-Show status');
		   	}	   		
	   	}else{
	   		Ext.Msg.alert('SSP Error','Please correct the hilited errors in the form');
	   	}
    },
    
    onCancelClick: function( button ){
    	me.close();
    },
    
    close: function(){
    	this.getView().close();
    },
    
    saveProgramStatusSuccess: function( r, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    	me.close();
    },
    
    saveProgramStatusFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );    	
    }
});