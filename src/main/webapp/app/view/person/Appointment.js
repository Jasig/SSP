Ext.define('Ssp.view.person.Appointment', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personappointment',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	//coachesStore: 'coachesStore',
    	studentTypesStore: 'studentTypesStore'
    },
	initComponent: function() {	
		Ext.apply(this, 
				{
			    fieldDefaults: {
			        msgTarget: 'side',
			        labelAlign: 'right',
			        labelWidth: 200
			    },	
			    border: 0,
				items: [{
			            xtype: 'fieldset',
			            border: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			       items: [{
				        xtype: 'combobox',
				        name: 'coachId',
				        fieldLabel: 'Assigned Coach',
				        emptyText: 'Select One',
				        //store: this.coachesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				    	xtype: 'displayfield',
				        fieldLabel: 'Office',
				        name: 'coachOffice'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Phone',
				        name: 'coachPhone'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Department',
				        name: 'coachDepartment'
				    },{
				        xtype: 'combobox',
				        name: 'studentTypeId',
				        fieldLabel: 'Student Type',
				        emptyText: 'Select One',
				        store: this.studentTypesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				    	xtype: 'datefield',
				    	fieldLabel: 'Appointment Date',
				    	itemId: 'appointmentDate',
				        name: 'appointmentDate',
				        allowBlank:false
				    },{
				        xtype: 'timefield',
				        name: 'appointmentStartTime',
				        fieldLabel: 'Start Time',
				        minValue: '8:00 AM',
				        maxValue: '7:00 PM',
				        increment: 30,
				        anchor: '100%'
				    },{
				        xtype: 'timefield',
				        name: 'appointmentEndTime',
				        fieldLabel: 'End Time',
				        minValue: '8:00 AM',
				        maxValue: '7:00 PM',
				        increment: 30,
				        anchor: '100%'
				    },{
				        xtype: 'checkboxgroup',
				        fieldLabel: 'Send Student Intake Request',
				        columns: 1,
				        items: [
				            {boxLabel: '', name: 'sendStudentIntakeRequest'},
				        ]
				    }]
			    }]
			});
		
		return this.callParent(arguments);
	}
});