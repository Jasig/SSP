Ext.define('Ssp.controller.person.CoachViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	coachesStore: 'coachesStore',
    	person: 'currentPerson', 	
        studentTypesStore: 'studentTypesStore'
    },
    control: {
    	departmentField: '#departmentField',
    	phoneField: '#phoneField',
    	officeField: '#officeField',
    	emailAddressField: '#emailAddressField',

    	coachCombo: {
    		selector: '#coachCombo',
    		listeners: {
        		change: 'onCoachComboChange',
        		select: 'onCoachComboSelect'
    		} 
    	},
    	
    	studentTypeCombo: {
    		selector: '#studentTypeCombo',
    		listeners: {
        		select: 'onStudentTypeComboSelect'
    		}     		
    	}
    },
    
	init: function() {
		var me=this;

		me.studentTypesStore.load();
		me.coachesStore.load();
		
		me.getView().loadRecord( me.person );
		
		return this.callParent(arguments);
    },
    
	onCoachComboSelect: function(comp, records, eOpts){
		var me=this;
		var coach;
		if(records.length>0){
			coach=records[0];
			me.displayCoachDepartment( coach );
		}
	},
	
	onCoachComboChange: function(comp, newValue, oldValue, eOpts){
		var me=this;
		var coach = me.coachesStore.getById(newValue);
		if(coach != null){
			me.displayCoachDepartment( coach );
		}
	},
	
	displayCoachDepartment: function( coach ){
		var me=this;
		me.getDepartmentField().setValue( coach.get('department') );
		me.getPhoneField().setValue( coach.get('workPhone') );
		me.getEmailAddressField().setValue( coach.get('primaryEmailAddress') );
		me.getOfficeField().setValue( coach.get('office') );
	},

	onStudentTypeComboSelect: function(comp, records, eOpts){
		var me=this;
		var studentType, requireInitialAppointment;
		if(records.length>0){
			me.appEventsController.getApplication().fireEvent('studentTypeChange');
		}
	},
	
	onStudentTypeComboChange: function(comp, newValue, oldValue, eOpts){
		var me=this;
		var studentType, requireInitialAppointment;
		studentType = me.studentTypesStore.getById(newValue);
		if(studentType != null){
			me.appEventsController.getApplication().fireEvent('studentTypeChange');
		}
	}
});