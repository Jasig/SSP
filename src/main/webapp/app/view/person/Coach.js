Ext.define('Ssp.view.person.Coach', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personcoach',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.CoachViewController',
    inject: {
    	coachesStore: 'coachesStore',
    	studentTypesStore: 'studentTypesStore'
    },
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
			    border: 0,
			    padding: 0,
			    fieldDefaults: {
			        msgTarget: 'side',
			        labelAlign: 'right',
			        labelWidth: 200
			    },	
				items: [{
			            xtype: 'fieldset',
			            border: 0,
			            padding: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			       items: [{
				        xtype: 'combobox',
				        name: 'coachId',
				        itemId: 'coachCombo',
				        fieldLabel: 'Assigned Coach',
				        emptyText: 'Select One',
				        store: me.coachesStore,
				        valueField: 'id',
				        displayField: 'fullName',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: false
					},{
				    	xtype: 'displayfield',
				        fieldLabel: 'Office',
				        itemId: 'officeField',
				        name: 'coachOffice'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Phone',
				        itemId: 'phoneField',
				        name: 'coachPhone'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Email',
				        itemId: 'emailAddressField',
				        name: 'coachEmailAddress'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Department',
				        itemId: 'departmentField',
				        name: 'coachDepartment'
				    },{
				        xtype: 'combobox',
				        name: 'studentTypeId',
				        itemId: 'studentTypeCombo',
				        id: 'studentTypeCombo',
				        fieldLabel: 'Student Type',
				        emptyText: 'Select One',
				        store: me.studentTypesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: false
					}]
			    }]
			});
		
		return me.callParent(arguments);
	}
});