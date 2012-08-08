Ext.define('Ssp.controller.tool.studentintake.DemographicsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	citizenshipsStore: 'citizenshipsStore',
    	model: 'currentStudentIntake',
    	sspConfig: 'sspConfig'
    },
    config: {
    	displayEmploymentShift: 1
    },
    control: {
		'citizenship': {
			change: 'onCitizenshipChange'
		},
		
		primaryCaregiverCheckOn: '#primaryCaregiverCheckOn',
		primaryCaregiverCheckOff: '#primaryCaregiverCheckOff',		
		
		'childcareNeeded': {
			change: 'onChildcareNeededChange'
		},
		
		childCareNeededCheckOn: '#childCareNeededCheckOn',
		childCareNeededCheckOff: '#childCareNeededCheckOff',
		
		'isEmployed': {
			change: 'onIsEmployedChange'
		},
		
		employedCheckOn: '#employedCheckOn',
		employedCheckOff: '#employedCheckOff',		
		
		'countryOfCitizenship': {
			hide: 'onFieldHidden'
		},
		
		'childcareArrangement': {
			hide: 'onFieldHidden'
		},
		
		'placeOfEmployment': {
			hide: 'onFieldHidden'
		},
		
		'shift': {
			hide: 'onFieldHidden'
		},
		
		'wage': {
			hide: 'onFieldHidden'
		},
		
		'totalHoursWorkedPerWeek': {
			hide: 'onFieldHidden'
		}
	},

	init: function() {
		var me=this;
		var personDemographics = me.model.get('personDemographics');
		var citizenship = Ext.ComponentQuery.query('#citizenship')[0];
		var childcareNeeded = Ext.ComponentQuery.query('#childcareNeeded')[0];
		var isEmployed = Ext.ComponentQuery.query('#isEmployed')[0];
		var primaryCaregiver = me.model.get('personDemographics').get('primaryCaregiver');
		var childCareNeeded = me.model.get('personDemographics').get('childCareNeeded');
		var employed = me.model.get('personDemographics').get('employed');
		
		// Assign radio button values
		// Temp solution to assign a value to 
		// the No radio button 
		if ( personDemographics != null && personDemographics != undefined )
		{
			me.getPrimaryCaregiverCheckOn().setValue(primaryCaregiver);
			me.getPrimaryCaregiverCheckOff().setValue(!primaryCaregiver);

			me.getChildCareNeededCheckOn().setValue( childCareNeeded );
			me.getChildCareNeededCheckOff().setValue( !childCareNeeded );				

			me.getEmployedCheckOn().setValue( employed );
			me.getEmployedCheckOff().setValue( !employed );
		}
		
		me.displayStudentIntakeDemographicsEmploymentShift = me.sspConfig.get('displayStudentIntakeDemographicsEmploymentShift');
		
		me.showHideCountryOfCitizenship( citizenship.getValue() );
        me.showHideChildcareArrangement( childcareNeeded.getValue() );
        me.showHideEmploymentFields( isEmployed.getValue() );
        
		return me.callParent(arguments);
    },
    
    onCitizenshipChange: function(combo, newValue, oldValue, eOpts) {
    	this.showHideCountryOfCitizenship( newValue );
    },
    
    showHideCountryOfCitizenship: function( value ){
    	var field = Ext.ComponentQuery.query('#countryOfCitizenship')[0];
		var record = this['citizenshipsStore'].findRecord('name', 'International');
		if ( value==record.get( 'id' ) )
		{
			field.show();
		}else{
			field.hide();
		}    
    },

    onChildcareNeededChange: function(radiogroup, newValue, oldValue, eOpts) {
    	this.showHideChildcareArrangement( newValue );
    },
    
    showHideChildcareArrangement: function( value ){
    	var field = Ext.ComponentQuery.query('#childcareArrangement')[0];
    	if(value.childCareNeeded=="true")
    	{
    		field.show();
    	}else{
    		field.hide();
    	}
    },
 
    onIsEmployedChange: function(radiogroup, newValue, oldValue, eOpts) {
    	this.showHideEmploymentFields( newValue );
    },    
    
    showHideEmploymentFields: function( value ){
    	var placeOfEmployment = Ext.ComponentQuery.query('#placeOfEmployment')[0];
    	var shift = Ext.ComponentQuery.query('#shift')[0];
    	var wage = Ext.ComponentQuery.query('#wage')[0];
    	var totalHoursWorkedPerWeek = Ext.ComponentQuery.query('#totalHoursWorkedPerWeek')[0];
    	if(value.employed=="true")
    	{
    		placeOfEmployment.show();
    		if (this.displayStudentIntakeDemographicsEmploymentShift)
    		{
    		    shift.show();
    		}else{
    			shift.hide();
    		}
    		wage.show();
    		totalHoursWorkedPerWeek.show();
    	}else{
    		placeOfEmployment.hide();
    		shift.hide();
    		wage.hide();
    		totalHoursWorkedPerWeek.hide();
    	}
    },    

    onFieldHidden: function( comp, eOpts){
    	comp.setValue("");
    }
    
});