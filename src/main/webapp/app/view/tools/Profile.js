Ext.define('Ssp.view.tools.Profile', {
	extend: 'Ext.form.Panel',
	alias : 'widget.Profile',
    title: 'Profile',
    
    width: '100%',
    height: '100%',
    bodyPadding: 5,

    // Fields will be arranged vertically, stretched to full width
    layout: 'anchor',
    defaults: {
        anchor: '100%'
    },

    // The fields
    defaultType: 'displayfield',
    items: [{
            xtype: 'fieldset',
            title: 'Student Profile',
            defaultType: 'textfield',
            defaults: {
                anchor: '100%'
            },
       items: 
       [{
	        xtype: 'displayfield',
	        fieldLabel: 'Student',
	        name: 'name'
	    }, {
	        fieldLabel: 'Tartan ID',
	        name: 'uniqueSchoolUserId'
	    }, {
	        xtype: 'displayfield',
	        fieldLabel: 'Date of Birth',
	        name: 'dateOfBirth',
	        allowBlank: false
	    }, {
	        fieldLabel: 'Home Phone',
	        name: 'homePhone'
	    }, {
	        fieldLabel: 'Cell Phone',
	        name: 'cellPhone'
	    }, {
	        fieldLabel: 'Address',
	        name: 'address'
	    }, {
	        fieldLabel: 'City',
	        name: 'city'
	    }, {
	        fieldLabel: 'State',
	        name: 'state'
	    }, {
	        fieldLabel: 'Zip Code',
	        name: 'zipCode'
	    }, {
	        fieldLabel: 'School Email',
	        name: 'emailPrimary'
	    }, {
	        fieldLabel: 'Alternate Email',
	        name: 'emailSecondary'
	    }, {
	        fieldLabel: 'Student Type',
	        name: 'studentType'
	    }, {
	        fieldLabel: 'SSP Program Status',
	        name: 'programStatus'
	    }, {
	        fieldLabel: 'Registration Status',
	        name: 'registrationStatus'
	    }, {
	        fieldLabel: 'Payment Status',
	        name: 'paymentStatus'
	    }, {
	        fieldLabel: 'CUM GPA',
	        name: 'cumGPA'
	    }, {
	        fieldLabel: 'Academic Programs',
	        name: 'academicPrograms'
	    }]
	    }],
	

	initComponent: function() {	
		this.superclass.initComponent.call(this, arguments);
	}
	
});