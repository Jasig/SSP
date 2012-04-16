Ext.define('Ssp.view.tools.Profile', {
	extend: 'Ext.form.Panel',
	alias : 'widget.profile',
	id: 'Profile',
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
            defaultType: 'displayfield',
            defaults: {
                anchor: '100%'
            },
       items: 
       [{
	        fieldLabel: 'Student',
	        name: 'name'
	    }, {
	        fieldLabel: 'Tartan ID',
	        name: 'schoolId'
	    }, {
	        fieldLabel: 'Date of Birth',
	        name: 'birthDate'
	    }, {
	        fieldLabel: 'Home Phone',
	        name: 'homePhone'
	    }, {
	        fieldLabel: 'Cell Phone',
	        name: 'cellPhone'
	    }, {
	        fieldLabel: 'Address',
	        name: 'addressLine1'
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
	        name: 'primaryEmailAddress'
	    }, {
	        fieldLabel: 'Alternate Email',
	        name: 'secondaryEmailAddress'
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
	    }]
	
});