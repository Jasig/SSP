Ext.define('Ssp.view.tools.profile.Person', {
	extend: 'Ext.form.Panel',
	alias : 'widget.profileperson',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfilePersonViewController',	
    width: '100%',
	height: '100%',
    initComponent: function() {	
		var me=this;
    	Ext.apply(me, 
				{
    		        border: 0,	
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 125
				    },
				    defaultType: 'displayfield',
				    items: [{
				            xtype: 'fieldset',
				            border: 0,
				            title: '',
				            defaultType: 'displayfield',
				            defaults: {
				                anchor: '100%'
				            },
				       items: 
				       [{
					        fieldLabel: 'Student',
					        name: 'name',
					        itemId: 'studentName'
					    }, {
					        fieldLabel: 'Student Id',
					        itemId: 'studentId',
					        name: 'schoolId'
					    }, {
					        fieldLabel: 'Birth Date',
					        name: 'birthDate',
					        itemId: 'birthDate'
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
					        fieldLabel: 'Academic Program',
					        name: 'academicPrograms'
					    }]
					    }],
				});
		
	     return me.callParent(arguments);
	}
	
});