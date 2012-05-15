Ext.define('Ssp.controller.tool.studentintake.DemographicsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	citizenshipsStore: 'citizenshipsStore',
    },
    control: {
		'citizenship': {
			change: 'onCitizenshipChange'
		},
		
		'childcareNeeded': {
			change: 'onChildcareNeededChange'
		},
		
		'isEmployed': {
			change: 'onIsEmployedChange'
		},
		
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
		var citizenship = Ext.ComponentQuery.query('#citizenship')[0];
		var childcareNeeded = Ext.ComponentQuery.query('#childcareNeeded')[0];
		var isEmployed = Ext.ComponentQuery.query('#isEmployed')[0];
		
		this.showHideCountryOfCitizenship( citizenship.getValue() );
        this.showHideChildcareArrangement( childcareNeeded.getValue() );
        this.showHideEmploymentFields( isEmployed.getValue() );
		
		return this.callParent(arguments);
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
    		shift.show();
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