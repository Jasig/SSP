Ext.define('Ssp.controller.person.CoachViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	person: 'currentPerson',
    	coachesStore: 'coachesStore',
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
			me.getDepartmentField().setValue( coach.get('department') );
			me.getPhoneField().setValue( coach.get('workPhone') );
			me.getEmailAddressField().setValue( coach.get('primaryEmailAddress') );
			me.getOfficeField().setValue( coach.get('office') );
		}
	},
	
	onCoachComboChange: function(comp, newValue, oldValue, eOpts){
		var me=this;
		var coach = me.coachesStore.getById(newValue);
		if(coach != null){
			me.getDepartmentField().setValue( coach.get('department') );
			me.getPhoneField().setValue( coach.get('workPhone') );
			me.getEmailAddressField().setValue( coach.get('primaryEmailAddress') );
			me.getOfficeField().setValue( coach.get('office') );
		}
	}
});