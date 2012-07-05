/*
Copyright(c) 2012 Sinclair Community College
*/
Ext.define('Ssp.view.admin.AdminTreeMenu', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.admintreemenu',
	id: 'AdminTreeMenu',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.AdminViewController',
    inject: {
    	store: 'adminTreeMenusStore'
    },    
	initComponent: function() {	
		Ext.apply(this, 
				{
					store: this.store,
					singleExpand: true,
					fields: ['title','form','text'],	
				});
		
	     this.callParent(arguments);
	}	
}); 
Ext.define('Ssp.view.admin.AdminForms', {
	extend: 'Ext.container.Container',
	alias : 'widget.adminforms',
    id: 'AdminForms',
	width: '100%',
	height: '100%',
	layout: 'fit'
});
Ext.define('Ssp.view.admin.AdminMain', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.adminmain',
    id: 'AdminMain',
    title: 'Admin Main',
    height: '100%',
    width: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
				    layout: {
				    	type: 'hbox',
				    	align: 'stretch'
				    }
				});
		
	     this.callParent(arguments);
	}
});
Ext.define('Ssp.view.Search', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.search',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.SearchViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	apiProperties: 'apiProperties',
    	columnRendererUtils: 'columnRendererUtils',
    	person: 'currentPerson',
        store: 'studentsStore',
        programStatusesStore: 'programStatusesStore'
    },
    initComponent: function(){
    	Ext.apply(this,
    			   {
    				title: 'Students',
    	            collapsible: true,
    	            collapseDirection: 'left',
    	        	width: '100%',
    	        	height: '100%',
		    	    columns: [
		    	              { header: "Photo", dataIndex: 'photoUrl', renderer: this.columnRendererUtils.renderPhotoIcon, flex: 50 },		        
		    	              { text: 'Name', dataIndex: 'lastName', renderer: this.columnRendererUtils.renderStudentDetails, flex: 50},
		    	              ],
    	          
		    	    dockedItems: [{
		       			xtype: 'pagingtoolbar',
		       		    store: this.store,
		       			dock: 'bottom',
		       		    displayInfo: true,
		       		    pageSize: this.apiProperties.getPagingSize()
		       		},{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       			defaults: {
		       				labelWidth: 50
		       			},
		       		    items: [
		       		        {
		       		        	xtype: 'textfield',
		       		        	itemId: 'searchText'
		       		        },{
		       		        	xtype: 'button',
		       		        	tooltip: 'Find a student',
		       		        	itemId: 'searchButton',
					            width: 30,
					            height: 23,
					            cls: 'searchIcon'
		       		        },{
		       		        	xtype: 'tbspacer',
		       		        	flex: 1
		       		        },{
					            tooltip: 'Display with photo',
					            text: '',
					            width: 20,
					            height: 20,
					            cls: 'displayPhotoListIcon',
					            xtype: 'button',
					            itemId: 'displayPhotoButton'				        	
					        },{
					            tooltip: 'Display without photo',
					            text: '',
					            width: 20,
					            height: 20,
					            cls: 'displayListIcon',
					            xtype: 'button',
					            itemId: 'displayListButton'				        	
					        }
		       		    ]
		       		}/*,{
		       			xtype: 'toolbar',
		       			dock: 'top',
		       		    items: [
		       		        {
		    			        xtype: 'combobox',
		    			        itemId: 'caseloadStatusCombo',
		    			        name: 'programStatusId',
		    			        fieldLabel: 'Caseload Status',
		    			        emptyText: 'Select One',
		    			        store: this.programStatusesStore,
		    			        valueField: 'id',
		    			        displayField: 'name',
		    			        mode: 'local',
		    			        typeAhead: true,
		    			        queryMode: 'local',
		    			        allowBlank: true,
		    			        forceSelection: false,
		    			        labelWidth: 100
		    				},{
		       		        	xtype: 'tbspacer',
		       		        	flex: 1
		       		        }
		       		    ]
		       		    
		       		}*/]
		    	    });
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.Main', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.mainview',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    controller: 'Ssp.controller.MainViewController',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
		    			{
		    	    layout: {
		    	    	type: 'hbox',
		    	    	align: 'stretch'
		    	    },

		    	    dockedItems: {
		    	        xtype: 'toolbar',
		    	        items: [{
		    			            xtype: 'button',
		    			            text: 'Students',
		    			            itemId: 'studentViewNav',
		    			            action: 'displayStudentRecord'
		    			        }, {
		    			            xtype: 'button',
		    			            text: 'Admin',
		    			            itemId: 'adminViewNav',
		    			            action: 'displayAdmin'
		    			        },{
			       		        	xtype: 'tbspacer',
			       		        	flex: 1
			       		        },{
								    tooltip: 'Add Student',
								    text: '',
								    width: 25,
								    height: 25,
								    cls: 'addPersonIcon',
								    xtype: 'button',
								    itemId: 'addPersonButton'
								},{
								    tooltip: 'Edit Student',
								    text: '',
								    width: 25,
								    height: 25,
								    cls: 'editPersonIcon',
								    xtype: 'button',
								    itemId: 'editPersonButton'
								},{
								    tooltip: 'Delete Student',
								    text: '',
								    width: 25,
								    height: 25,
								    cls: 'deletePersonIcon',
								    xtype: 'button',
								    itemId: 'deletePersonButton'
								}]
		    	    }    		
    			});
    	
    	return me.callParent(arguments);
    }
});
Ext.define('Ssp.view.StudentRecord', {
	extend: 'Ext.panel.Panel',
    alias: 'widget.studentrecord',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.StudentRecordViewController',
    width: '100%',
    height: '100%',
    initComponent: function(){
    	Ext.apply(this,{
    		title: 'Student Record',
    	    collapsible: true,
    	    collapseDirection: 'left',
    		layout: {
    	    	type: 'hbox',
    	    	align: 'stretch'
    	    },
			
    	    items: [{xtype:'toolsmenu',flex:1},
			        {xtype: 'tools', flex:4}]		        
    	});
    	return this.callParent(arguments);
    }
});	
Ext.define('Ssp.view.person.CaseloadAssignment', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.caseloadassignment',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.CaseloadAssignmentViewController',
    inject: {
    	model: 'currentPerson'
    },
    width: '100%',
	height: '100%',   
	initComponent: function() {
		Ext.apply(this, 
				{
			        title: "Caseload Assignment",
		    		autoScroll: true,
		    	    defaults: {
		    	        bodyStyle: 'padding:15px'
		    	    },
		    	    layout: {
		    	        type: 'accordion',
		    	        titleCollapse: true,
		    	        animate: true,
		    	        activeOnTop: true
		    	    },		    		
		    		
		    		dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{xtype: 'button', 
				        	     itemId: 'saveButton', 
				        	     text:'Save'
				        	    },
				                 {
				            	   xtype: 'button',
				            	   itemId: 'cancelButton',
				            	   text: 'Cancel',
				                 },{ 
						        	xtype: 'tbspacer',
						        	flex: 1
						         },
				                 {
				            	   xtype: 'button',
				            	   itemId: 'printButton',
				            	   tooltip: 'Print Appointment Form',
				            	   width: 30,
						           height: 30,
						           cls: 'printIcon'
				                 },
				                 {
				            	   xtype: 'button',
				            	   itemId: 'emailButton',
				            	   tooltip: 'Email Appointment Form',
				            	   width: 30,
						           height: 30,
						           cls: 'emailIcon'
				                 }]
				    }],
				    
				    items: [ ]
			});
	
		return this.callParent(arguments);
	}

});
Ext.define('Ssp.view.person.EditPerson', {
	extend: 'Ext.form.Panel',
	alias: 'widget.editperson',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.EditPersonViewController',
	initComponent: function() {	
		Ext.apply(this, 
				{
					border: 0,	    
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 100
				    },
					items: [{
			            xtype: 'fieldset',
			            border: 0,
			            title: '',
			            defaultType: 'textfield',

			       items: [{
			        fieldLabel: 'First Name',
			        name: 'firstName',
			        itemId: 'firstName',
			        id: 'editPersonFirstName',
			        maxLength: 50,
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Middle Initial',
			        name: 'middleInitial',
			        itemId: 'middleName',
			        id: 'editPersonMiddleName',
			        maxLength: 1,
			        allowBlank:true,
			        width: 350
			    },{
			        fieldLabel: 'Last Name',
			        name: 'lastName',
			        itemId: 'lastName',
			        id: 'editPersonLastName',
			        maxLength: 50,
			        allowBlank:false,
			        width: 350
			    },{
			        fieldLabel: 'Student ID',
			        name: 'schoolId',
			        minLength: 7,
			        maxLength: 7,
			        itemId: 'studentId',
			        allowBlank:false,
			        width: 350
			    },{
			    	xtype: 'button',
			    	tooltip: 'Load record from external system',
			    	text: 'Retrieve from SIS',
			    	itemId: 'retrieveFromExternalButton'
			    },{
			        fieldLabel: 'Home Phone',
			        name: 'homePhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maskRe: /[\d\-]/,
			        regex: /^\d{3}-\d{3}-\d{4}$/,
			        regexText: 'Must be in the format xxx-xxx-xxxx',
			        maxLength: 12,
			        allowBlank:true,
			        itemId: 'homePhone',
			        width: 350
			    },{
			        fieldLabel: 'Work Phone',
			        name: 'workPhone',
			        emptyText: 'xxx-xxx-xxxx',
			        maskRe: /[\d\-]/,
			        regex: /^\d{3}-\d{3}-\d{4}$/,
			        regexText: 'Must be in the format xxx-xxx-xxxx',
			        maxLength: 12,
			        allowBlank:true,
			        itemId: 'workPhone',
			        width: 350
			    },{
			        fieldLabel: 'School Email',
			        name: 'primaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'primaryEmailAddress',
			        width: 350
			    },{
			        fieldLabel: 'Home Email',
			        name: 'secondaryEmailAddress',
			        vtype:'email',
			        maxLength: 100,
			        allowBlank:true,
			        itemId: 'secondaryEmailAddress',
			        width: 350
			    }]
			}]
		});
		
		return this.callParent(arguments);
	}
});
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
				        itemId: 'coachCombo',
				        fieldLabel: 'Assigned Coach',
				        emptyText: 'Select One',
				        store: this.coachesStore,
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
				        store: this.studentTypesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: false
					}]
			    }]
			});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.person.Appointment', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personappointment',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.AppointmentViewController',
    inject: {
    	person: 'currentPerson'
    },
	initComponent: function() {	
		var me=this;
		Ext.apply(this, 
				{
			    fieldDefaults: {
			        msgTarget: 'side',
			        labelAlign: 'right',
			        labelWidth: 200
			    },	
			    border: 0,
			    padding: 0,
				items: [{
			            xtype: 'fieldset',
			            border: 0,
			            title: '',
			            defaultType: 'textfield',
			            defaults: {
			                anchor: '100%'
			            },
			       items: [{
				    	xtype: 'datefield',
				    	fieldLabel: 'Appointment Date',
				    	itemId: 'appointmentDateField',
				        name: 'appointmentDate',
				        allowBlank: false
				    },{
				        xtype: 'timefield',
				        name: 'startTime',
				        itemId: 'startTimeField',
				        fieldLabel: 'Start Time',
				        increment: 30,
				        typeAhead: false,
				        allowBlank: false,
				        anchor: '100%'
				    },{
				        xtype: 'timefield',
				        name: 'endTime',
				        itemId: 'endTimeField',
				        fieldLabel: 'End Time',
				        typeAhead: false,
				        allowBlank: false,
				        increment: 30,
				        anchor: '100%'
				    },{
				        xtype: 'checkboxgroup',
				        fieldLabel: 'Send Student Intake Request',
				        columns: 1,
				        items: [
				            {boxLabel: '', name: 'sendStudentIntakeRequest'},
				        ]
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Last Student Intake Request Date',
				        name: 'lastStudentIntakeRequestDate',
				        value: ((me.person.getFormattedStudentIntakeRequestDate().length > 0) ? me.person.getFormattedStudentIntakeRequestDate() : 'No requests have been sent')
				    }]
			    }]
			});
		
		return me.callParent(arguments);
	}
});
Ext.define('Ssp.view.person.SpecialServiceGroups', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personspecialservicegroups',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.SpecialServiceGroupsViewController',
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{
				    bodyPadding: 5,
				    layout: 'anchor'
				});
		
		return me.callParent(arguments);
	}
});
Ext.define('Ssp.view.person.ReferralSources', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personreferralsources',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.ReferralSourcesViewController',
	width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(this, 
				{
				    bodyPadding: 5,
				    layout: 'anchor'
				});
		
		return me.callParent(arguments);
	}
});
Ext.define('Ssp.view.person.ServiceReasons', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personservicereasons',
	id: 'personservicereasons',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.ServiceReasonsViewController',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
		Ext.apply(this, 
				{
				    bodyPadding: 0,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.person.AnticipatedStartDate', {
	extend: 'Ext.form.Panel',
	alias: 'widget.personanticipatedstartdate',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.person.AnticipatedStartDateViewController',
    inject: {
    	anticipatedStartTermsStore: 'anticipatedStartTermsStore',
    	anticipatedStartYearsStore: 'anticipatedStartYearsStore'
    },
	initComponent: function() {	
		Ext.apply(this, 
				{
			border: 0,
			items: [{
		        xtype: 'checkboxgroup',
		        fieldLabel: 'Ability to Benefit',
		        columns: 1,
		        items: [
		            {boxLabel: '', name: 'abilityToBenefit'}
		        ]
		    },{
		        xtype: 'combobox',
		        name: 'anticipatedStartTerm',
		        fieldLabel: 'Anticipated Start Term',
		        emptyText: 'Select One',
		        store: this.anticipatedStartTermsStore,
		        valueField: 'name',
		        displayField: 'name',
		        mode: 'local',
		        typeAhead: true,
		        queryMode: 'local',
		        allowBlank: true
			},{
		        xtype: 'combobox',
		        name: 'anticipatedStartYear',
		        fieldLabel: 'Anticipated Start Year',
		        emptyText: 'Select One',
		        store: this.anticipatedStartYearsStore,
		        valueField: 'name',
		        displayField: 'name',
		        mode: 'local',
		        typeAhead: true,
		        queryMode: 'local',
		        allowBlank: true
			}]
		});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.component.MappedTextField', {
	extend: 'Ext.form.field.Text',
	alias : 'widget.mappedtextfield',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.component.MappedTextArea', {
	extend: 'Ext.form.field.TextArea',
	alias : 'widget.mappedtextarea',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.component.MappedCheckBox', {
	extend: 'Ext.form.field.Checkbox',
	alias : 'widget.mappedcheckbox',
	config: {
		mapId: null
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.component.MappedRadioButton', {
	extend: 'Ext.form.field.Radio',
	alias : 'widget.mappedradiobutton',
	config: {
		parentId: null,
		validationExpression: '[a-zA-Z]'
	},
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.ToolsMenu', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.toolsmenu',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.ToolsViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
        store: 'toolsStore'
    },
    initComponent: function(){
    	Ext.apply(this,
    			   {
		    		width: '100%',
		    		height: '100%',
    				store: this.store,

    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		    	    
    				columns:[{
    				           header: "Tools", 
    				           dataIndex: "name",
    				           sortable: false,
    				           menuDisabled: true,
    				           flex:1 },{
	    			    	        xtype:'actioncolumn',
	    			    	        width:18,
	    			    	        header: '',
	    			    	        items: [{
	    			    	            tooltip: 'Add Tool',
	    			    	            // icon: Ssp.util.Constants.ADD_TOOL_ICON_PATH,
	    			    	            getClass: this.columnRendererUtils.renderAddToolIcon,
	    			    	            handler: function(grid, rowIndex, colIndex) {
	    			    	            	var rec = grid.getStore().getAt(rowIndex);
	    			    	            	var panel = grid.up('panel');
	    			    	                //panel.toolId.data=rec.data.toolId;
	    			    	                panel.appEventsController.getApplication().fireEvent('addTool');
	    			    	                Ext.Msg.alert('Attention','This feature is not yet active');
	    			    	            },
	    			    	            scope: this
	    			    	        }]
	    		                }]
		    	    });
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.Tools', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.tools',
	id: 'Tools',
	width: '100%',
	height: '100%',
	layout: 'fit'
});
Ext.define('Ssp.view.tools.profile.Profile', {
	extend: 'Ext.form.Panel',
	alias : 'widget.profile',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileToolViewController',
    width: '100%',
	height: '100%',
    initComponent: function() {	
		var me=this;
    	Ext.apply(me, 
				{
		    	    layout: 'fit',
		            title: 'Profile',
		            padding: 0,
		            border: 0,
					items: [
						Ext.createWidget('tabpanel', {
						    width: '100%',
						    height: '100%',
						    activeTab: 0,
						    itemId: 'profileTabs',
						    items: [{ 
						    	      title: 'Personal',
						    	      autoScroll: true,
						    		  items: [{xtype: 'profileperson'}]
						    		},{ 
						    		  title: 'Special Service Groups',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profilespecialservicegroups'}]
						    		},{ 
						    		  title: 'Referral Sources',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profilereferralsources'}]
						    		},{ 
							    		  title: 'Services Provided History',
							    		  autoScroll: true,
							    		  items: [{xtype: 'profileservicesprovided'}]
							    	}]
						})
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Transition Student',
				            text: '',
				            width: 35,
				            height: 35,
				            cls: 'studentTransitionIcon',
				            xtype: 'button',
				            itemId: 'studentTransitionButton'
				        },{
					            tooltip: 'View Student History',
					            text: '',
					            width: 35,
					            height: 35,
					            cls: 'studentHistoryIcon',
					            xtype: 'button',
					            itemId: 'viewHistoryButton'
					        }]
				    }]
				});	     
    	
    	return this.callParent(arguments);
	}
});
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
Ext.define('Ssp.view.tools.profile.SpecialServiceGroups', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profilespecialservicegroups',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	store: 'profileSpecialServiceGroupsStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
			        hideHeaders: true,
			        store: this.store,
					autoScroll: true,
    		        columns: [
    		                { header: 'Group',  
    		                  dataIndex: 'name',
    		                  flex: 1,
    		                }],
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.profile.ReferralSources', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profilereferralsources',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	store: 'profileReferralSourcesStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
			        hideHeaders: true,
			        autoScroll: true,
		            store: this.store,
    		        columns: [
    		                { header: 'Source',  
    		                  dataIndex: 'name',
    		                  flex: 1,
    		                }],
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.profile.ServicesProvided', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.profileservicesprovided',
	width: '100%',
	height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
		            autoScroll: true,
    		        columns: [
    		                { header: 'Provided By',  
    		                  dataIndex: 'createdBy',
    		                  flex: .50,
    		                },{ header: 'Date Provided',  
    		                  dataIndex: 'createdDate',
    		                  flex: .50,
    		                }],
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.actionplan.ActionPlan', {
	extend: 'Ext.container.Container',
	alias : 'widget.actionplan',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.ActionPlanToolViewController',
    width: '100%',
	height: '100%',   
	layout: 'fit',
	initComponent: function() {	
		Ext.apply(this,{items: [{xtype: 'displayactionplan'}]});

		return this.callParent(arguments);
	}
		
});
Ext.define('Ssp.view.tools.actionplan.Tasks', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.tasks',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TasksViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentTask',
        store: 'tasksStore',
    },
    layout: 'auto',
	width: '100%',
    height: '100%',
    initComponent: function(){
    	
    	var sm = Ext.create('Ext.selection.CheckboxModel');
    	
    	Ext.apply(this,
    			{
    		        scroll: 'vertical',
    	    		store: this.store,    		
    	    		selModel: sm,
    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		
		    	    columns: [{
		    	        xtype:'actioncolumn',
		    	        width:65,
		    	        header: 'Action',
		    	        items: [{
		    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
		    	            tooltip: 'Edit Task',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	            	var panel = grid.up('panel');
		    	                panel.model.data=rec.data;
		    	            	panel.appEventsController.getApplication().fireEvent('editTask');
		    	            },
		    	            scope: this
		    	        },{
		    	            icon: Ssp.util.Constants.GRID_ITEM_CLOSE_ICON_PATH,
		    	            tooltip: 'Close Task',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	            	var panel = grid.up('panel');
		    	                panel.model.data=rec.data;
		    	            	panel.appEventsController.getApplication().fireEvent('closeTask');
		    	            },
		    	            scope: this
		    	        },{
		    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
		    	            tooltip: 'Delete Task',
		    	            handler: function(grid, rowIndex, colIndex) {
		    	            	var rec = grid.getStore().getAt(rowIndex);
		    	            	var panel = grid.up('panel');
		    	                panel.model.data=rec.data;
		    	            	panel.appEventsController.getApplication().fireEvent('deleteTask');
		    	            },
		    	            scope: this
		    	        }]
		    	    },{
		    	        text: 'Description',
		    	        flex: 1,
		    	        tdCls: 'task',
		    	        sortable: true,
		    	        dataIndex: 'name',
		    	        renderer: this.columnRendererUtils.renderTaskName
		    	    },{
		    	        header: 'Due Date',
		    	        width: 100,
		    	        dataIndex: 'dueDate',
		    	        renderer: this.columnRendererUtils.renderTaskDueDate
		    	    }]
    	

    			});
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.actionplan.AddTask', {
	extend: 'Ext.panel.Panel',
	alias: 'widget.addtask',
	mixins: [ 'Deft.mixin.Injectable'],
	inject: {
    	model: 'currentTask'
    },
	width: '100%',
    height: '100%',
	autoScroll: true,
    defaults: {
        anchor: '100%'
    },    
	
    initComponent: function() {
		Ext.apply(this,{
						title: 'Add Action Plan Tasks',
						items: [{ xtype: 'tasktree', flex:1 },
						        { xtype: 'addtaskform', flex:1 }]
		});
		
		return this.callParent(arguments);
	}
});

Ext.define('Ssp.view.tools.actionplan.AddTaskForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.addtaskform',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.AddTasksFormViewController',
    inject: {
        store: 'confidentialityLevelsStore'
    },
	width: '100%',
    height: '100%',
	autoScroll: true,
	padding: 0,
    fieldDefaults: {
        msgTarget: 'side',
        labelAlign: 'right',
        labelWidth: 150
    },    
	initComponent: function() {
		Ext.apply(this, 
				{
				    items: [{
				            xtype: 'fieldset',
				            title: 'Add Task',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '100%'
				            },
				       items: [{
					    	xtype: 'displayfield',
					        fieldLabel: 'Name',
					        name: 'name'
					    },{
				    	xtype: 'textarea',
				        fieldLabel: 'Description',
				        name: 'description',
				        maxLength: 1000,
				        allowBlank:false
				    },{
				        xtype: 'combobox',
				        itemId: 'confidentialityLevel',
				        name: 'confidentialityLevelId',
				        fieldLabel: 'Confidentiality Level',
				        emptyText: 'Select One',
				        store: this.store,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: false,
				        forceSelection: true
					},{
				    	xtype: 'datefield',
				    	fieldLabel: 'Target Date',
				        name: 'dueDate',
				        allowBlank:false    	
				    }]
				    }],
				    
				    dockedItems: [{
				        dock: 'bottom',
				        xtype: 'toolbar',
				        items: [{xtype: 'button', 
				        	     itemId: 'addButton', 
				        	     text:'Save', 
				        	     action: 'add' },
				        	     {
				            	   xtype: 'button',
				            	   itemId: 'closeButton',
				            	   text: 'Finished',
				            	   action: 'close'}]
				    }]
				});
		
		return this.callParent(arguments);
	}
});

Ext.define('Ssp.view.tools.actionplan.EditGoalForm', {
	extend: 'Ext.form.Panel',
	alias: 'widget.editgoalform',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.EditGoalFormViewController',
    inject: {
        store: 'confidentialityLevelsStore'
    },
	initComponent: function() {
        Ext.applyIf(this, {
        	title: 'Add Goal',
            fieldDefaults: {
                msgTarget: 'side',
                labelAlign: 'right',
                labelWidth: 150
            },            
        	items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },{
			        xtype: 'combobox',
			        itemId: 'confidentialityLevel',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: this.store,
			        valueField: 'id',
			        displayField: 'acronym',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true
				}],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.tools.actionplan.DisplayActionPlan', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.displayactionplan',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanViewController',
    inject: {
    	person: 'currentPerson'
    },
    width: '100%',
	height: '100%',
	padding: 0,
	initComponent: function() {	
		Ext.apply(this, 
				{
		    	    layout: {
		    	        type: 'accordion',
		    	        titleCollapse: true,
		    	        animate: true,
		    	        activeOnTop: true
		    	    },
		            title: 'Action Plan',
		            autoScroll: true,
		            padding: 0,
					items: [
						Ext.createWidget('tabpanel', {
						    width: '100%',
						    height: '100%',
						    activeTab: 0,
						    title: 'Tasks',
						    itemId: 'taskStatusTabs',
						    items: [{ 
						    	      title: 'Active',
						    		  autoScroll: true,
						    		  action: 'active',
						    		  items: [{xtype: 'tasks'}]
						    		},{ 
						    		  title: 'Complete',
						    		  autoScroll: true,
						    		  action: 'complete',
						    		  items: [{xtype: 'tasks'}]
						    		},{ 
						    		  title: 'All',
						    		  autoScroll: true,
						    		  action: 'all',
						    		  items: [{xtype: 'tasks'}]
						    		}],
						    	    
						    	    dockedItems: [{
								        dock: 'top',
								        xtype: 'toolbar',
								        items: [{
								            tooltip: 'Add a Task',
								            text: 'Add',
								            xtype: 'button',
								            itemId: 'addTaskButton'
								        }]
						    	    }]
						})
						,{xtype: 'displayactionplangoals', itemId: 'goalsPanel', flex: 1}
						,{xtype: 'displaystrengths', itemId: 'strengthsPanel'}
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
					            tooltip: 'Email Action Plan',
					            text: '',
					            width: 30,
					            height: 30,
					            cls: 'emailIcon',
					            xtype: 'button',
					            itemId: 'emailTasksButton'
					        },{
					            tooltip: 'Print Action Plan',
					            text: '',
					            width: 30,
					            height: 30,
					            cls: 'printIcon',
					            xtype: 'button',
					            itemId: 'printTasksButton'
					        },{ 
					        	xtype: 'tbspacer',
					        	flex: 1
					        },{
					            xtype: 'checkbox',
					            boxLabel: 'Display only tasks that I created',
					            itemId: 'filterTasksBySelfCheck'
					        }]
				    }]
				});
	
		return this.callParent(arguments);
	}
		
});
Ext.define('Ssp.view.tools.actionplan.DisplayActionPlanGoals', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displayactionplangoals',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayActionPlanGoalsViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentGoal',
        store: 'goalsStore'
    },
    width: '100%',
	height: '100%',   
    layout: 'anchor',
    itemId: 'goalsPanel',
    defaults: {
        anchor: '100%'
    },	
	initComponent: function() {	
		
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(this, {

				title: 'Goals',
				store: this.store,
				selModel: sm,
			    columns: [{
	    	        xtype:'actioncolumn',
	    	        width:65,
	    	        header: 'Action',
	    	        items: [{
	    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
	    	            tooltip: 'Edit Goal',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	                var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('editGoal');
	    	            },
	    	            scope: this
	    	        },{
	    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	    	            tooltip: 'Delete Goal',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('deleteGoal');
	    	            },
	    	            scope: this
	    	        }]
	    	    },{
	    	        header: 'Name',
	    	        flex: 1,
	    	        dataIndex: 'name',
	    	        renderer: this.columnRendererUtils.renderGoalName
	    	    },{
	    	        header: 'Confidentiality',
	    	        dataIndex: 'confidentialityLevel',
	    	        renderer: this.columnRendererUtils.renderConfidentialityLevelName
	    	    }],
	    	    
	    	    dockedItems: [{
			        dock: 'top',
			        xtype: 'toolbar',
			        items: [{
			            tooltip: 'Add a Goal',
			            text: 'Add',
			            xtype: 'button',
			            itemId: 'addGoalButton'
			        }]
	    	    }]
		});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.actionplan.DisplayStrengths', {
	extend: 'Ext.form.Panel',
	alias : 'widget.displaystrengths',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.DisplayStrengthsViewController',
    width: '100%',
	height: '100%',
	initComponent: function() {	
		Ext.applyIf(this,{
	        title: 'Strengths',
			items:[{
		        xtype:'form',
		        layout:'anchor',
		        items :[{
		            xtype: 'textarea',
		            anchor: '100%',
		            height: 50,
		            fieldLabel: 'Strengths',
		            itemId: 'strengths',
		            name: 'strengths'
		        }]
			}],
			
    	    dockedItems: [{
		        dock: 'top',
		        xtype: 'toolbar',
		        items: [{
		            tooltip: 'Save Strengths',
		            text: 'Save',
		            xtype: 'button',
		            itemId: 'saveButton'
		        }]
    	    }]
		
		});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.actionplan.TaskTree', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.tasktree',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.actionplan.TaskTreeViewController',
    inject: {
        store: 'treeStore'
    },
	height: 250,
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 dockedItems: [
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     	                      xtype: 'textfield',
     	                      fieldLabel: 'Search'
     	                     },
     	                      {
     	                    	  xtype: 'button',
     	                    	  text: 'GO',
     	                    	  action: 'search',
     	                    	  itemId: 'searchButton'
     	                      }]
     		           } ] 
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.studentintake.StudentIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentintake',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.StudentIntakeToolViewController',
    inject: {
        store: 'studentsStore'
    },
	title: 'Student Intake',	
	width: '100%',
	height: '100%',   
	initComponent: function() {	
		Ext.apply(this, 
				{
		    		store: this.store,
		    		layout: 'fit',
		    		padding: 0,
		    		border: 0,
		    		items: [],
						
			    		dockedItems: [{
					        dock: 'top',
					        xtype: 'toolbar',
					        items: [{xtype: 'button', itemId: 'saveButton', text:'Save', action: 'save' },
					                {xtype: 'button', itemId: 'cancelButton', text:'Cancel', action: 'reset' },
					                { 
					        	     xtype: 'tbspacer',
					        	     flex: 1
					               },{
					            	   xtype: 'button',
					            	   itemId: 'viewConfidentialityAgreementButton',
					            	   text: 'View Confidentiality Agreement',
					            	   action: 'viewConfidentialityAgreement'}]
					    }]

			});
						
		return this.callParent(arguments);
	}

});
Ext.define('Ssp.view.tools.studentintake.Challenges', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakechallenges',
	id : 'StudentIntakeChallenges',
    width: '100%',
    height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
		    	    autoScroll: true,
					border: 0,	
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});
Ext.define("Ssp.view.tools.studentintake.EducationGoals", {
	extend: "Ext.form.Panel",
	alias: 'widget.studentintakeeducationgoals',
	id : "StudentIntakeEducationGoals",   
    width: "100%",
    height: "100%", 
    initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
				    bodyPadding: 5,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 200
				    },
				    defaultType: "radiogroup",
				    items: [{
				            xtype: "fieldset",
				            border: 0,
				            title: "",
				            id: 'StudentIntakeEducationGoalsFieldSet',
				            defaultType: "textfield",
				            defaults: {
				                anchor: "100%"
				            },
				       items: [{
				            xtype: "radiogroup",
				            id: 'StudentIntakeEducationGoalsRadioGroup',
				            fieldLabel: "Education/Career Goal",
				            allowBlank: true,
				            columns: 1
				        }]
				    },{
			            xtype: "fieldset",
			            border: 0,
			            title: '',
			            defaultType: "textfield",
			            defaults: {
			                anchor: "100%"
			            },
			       items: [{
			            xtype: "radiogroup",
			            fieldLabel: "How sure are you about your major?",
			            columns: 1,
			            items: [
			                {boxLabel: "Very Unsure", name: "howSureAboutMajor", inputValue: "1"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "2"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "3"},
			                {boxLabel: "", name: "howSureAboutMajor", inputValue: "4"},
			                {boxLabel: "Very Sure", name: "howSureAboutMajor", inputValue: "5"}
			        		]
			        },{
				        fieldLabel: 'What is your planned occupation?',
				        name: 'plannedOccupation'
				    }]
				    
				    }]
				});
		
		return this.callParent(arguments);
	}	
});
Ext.define('Ssp.view.tools.studentintake.Demographics', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakedemographics',
	id : 'StudentIntakeDemographics',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.DemographicsViewController',
    inject: {
    	childCareArrangementsStore: 'childCareArrangementsStore',
    	citizenshipsStore: 'citizenshipsStore',
    	educationGoalsStore: 'educationGoalsStore',
    	educationLevelsStore: 'educationLevelsStore',
    	employmentShiftsStore: 'employmentShiftsStore',
    	ethnicitiesStore: 'ethnicitiesStore',
    	gendersStore: 'gendersStore',
    	maritalStatusesStore: 'maritalStatusesStore',
    	veteranStatusesStore: 'veteranStatusesStore'
    },    
	width: '100%',
    height: '100%',

	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
				    layout: 'anchor',
				    border: 0,
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 280
				    },
				    defaultType: 'displayfield',
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
				        name: 'maritalStatusId',
				        fieldLabel: 'Marital Status',
				        emptyText: 'Select One',
				        store: this.maritalStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        name: 'ethnicityId',
				        fieldLabel: 'Ethnicity',
				        emptyText: 'Select One',
				        store: this.ethnicitiesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        name: 'gender',
				        fieldLabel: 'Gender',
				        emptyText: 'Select One',
				        store: this.gendersStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'combobox',
				        itemId: 'citizenship',
				        name: 'citizenshipId',
				        fieldLabel: 'Citizenship',
				        emptyText: 'Select One',
				        store: this.citizenshipsStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        fieldLabel: 'Country of citizenship',
				        itemId: 'countryOfCitizenship',
				        name: 'countryOfCitizenship'
				    },{
				        xtype: 'combobox',
				        name: 'veteranStatusId',
				        fieldLabel: 'Veteran Status',
				        emptyText: 'Select One',
				        store: this.veteranStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: "radiogroup",
				        fieldLabel: "Are you a Primary Caregiver?",
				        columns: 1,
				        items: [
				            {boxLabel: "Yes", name: "primaryCaregiver", inputValue:"true"},
				            {boxLabel: "No", name: "primaryCaregiver", inputValue:"false"}]
				    },{
				        xtype: 'displayfield',
				        fieldLabel: 'If you have children, please indicate below'
				    },{
				        xtype: 'numberfield',
				        name: 'numberOfChildren',
				        fieldLabel: 'How many?',
				        value: 0,
				        minValue: 0,
				        maxValue: 50
				    },{
				        fieldLabel: 'Ages? Separate each age with a comma. (1,5,12)',
				        name: 'childAges'
				    },{
				        xtype: "radiogroup",
				        fieldLabel: "Childcare Needed?",
				        itemId: 'childcareNeeded',
				        columns: 1,
				        items: [
				            {boxLabel: "Yes", name: "childCareNeeded", inputValue:"true"},
				            {boxLabel: "No", name: "childCareNeeded", inputValue:"false"}]
				    },{
				        xtype: 'combobox',
				        itemId: 'childcareArrangement',
				        name: 'childCareArrangementId',
				        fieldLabel: 'If yes, what are your childcare arrangements?',
				        emptyText: 'Select One',
				        store: this.childCareArrangementsStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: "radiogroup",
				        itemId: 'isEmployed',
				        fieldLabel: "Are you employed?",
				        columns: 1,
				        items: [
				            {boxLabel: "Yes", name: "employed", inputValue:"true"},
				            {boxLabel: "No", name: "employed", inputValue:"false"}]
				    },{
				        fieldLabel: 'Place of employment',
				        itemId: 'placeOfEmployment',
				        name: 'placeOfEmployment'
				    },{
				        xtype: 'combobox',
				        name: 'shift',
				        itemId: 'shift',
				        fieldLabel: 'Shift',
				        emptyText: 'Select One',
				        store: this.employmentShiftsStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        fieldLabel: 'Wage',
				        itemId: 'wage',
				        name: 'wage'
				    },{
				        fieldLabel: 'Total hours worked weekly while attending school',
				        itemId: 'totalHoursWorkedPerWeek',
				        name: 'totalHoursWorkedPerWeek'
				    }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.studentintake.EducationLevels', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakeeducationlevels',
	id : 'StudentIntakeEducationLevels',
    width: '100%',
    height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
				    bodyPadding: 5,
				    border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.studentintake.EducationPlans', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakeeducationplans',
	id : 'StudentIntakeEducationPlans',   
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.EducationPlansViewController',
    inject: {
        studentStatusesStore: 'studentStatusesStore'
    },
	width: '100%',
    height: '100%',
	
    initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
					bodyPadding: 5,
					border: 0,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'left',
				        labelWidth: 225
				    },
				    defaultType: 'displayfield',
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
				        name: 'studentStatusId',
				        fieldLabel: 'Student Status',
				        emptyText: 'Select One',
				        store: this.studentStatusesStore,
				        valueField: 'id',
				        displayField: 'name',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true
					},{
				        xtype: 'checkboxgroup',
				        fieldLabel: 'Check all that you have completed',
				        columns: 1,
				        items: [
				            {boxLabel: 'New Student Orientation', name: 'newOrientationComplete'},
				            {boxLabel: 'Registered for Classes', name: 'registeredForClasses'}
				        ]
				    },{
				        xtype: "radiogroup",
				        fieldLabel: "Have your parents obtained a college degree?",
				        columns: 1,
				        itemId: 'parentsDegree',
				        items: [
				            {boxLabel: "Yes", name: "collegeDegreeForParents", inputValue:"true"},
				            {boxLabel: "No", name: "collegeDegreeForParents", inputValue:"false"}]
				    },{
				        xtype: "radiogroup",
				        fieldLabel: "Require special accommodations?",
				        columns: 1,
				        itemId: 'specialNeeds',
				        items: [
				            {boxLabel: "Yes", name: "specialNeeds", inputValue:"true"},
				            {boxLabel: "No", name: "specialNeeds", inputValue:"false"}]
				    },{
				        xtype: 'radiogroup',
				        fieldLabel: 'What grade did you typically earn at your highest level of education?',
				        columns: 1,
				        items: [
				            {boxLabel: 'A', name: 'gradeTypicallyEarned', inputValue: "A"},
				            {boxLabel: 'A-B', name: 'gradeTypicallyEarned', inputValue: "A-B"},
				            {boxLabel: 'B', name: 'gradeTypicallyEarned', inputValue: "B"},
				            {boxLabel: 'B-C', name: 'gradeTypicallyEarned', inputValue: "B-C"},
				            {boxLabel: 'C', name: 'gradeTypicallyEarned', inputValue: "C"},
				            {boxLabel: 'C-D', name: 'gradeTypicallyEarned', inputValue: "C-D"},
				            {boxLabel: 'D', name: 'gradeTypicallyEarned', inputValue: "D"},
				            {boxLabel: 'D-F', name: 'gradeTypicallyEarned', inputValue: "D-F"},
				            {boxLabel: 'F', name: 'gradeTypicallyEarned', inputValue: "F"}
				    		]
				        }]
				    }]
				});
		
		return this.callParent(arguments);
	}	
});
Ext.define('Ssp.view.tools.studentintake.Funding', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakefunding',
	id : 'StudentIntakeFunding',   
    width: '100%',
    height: '100%',
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
					border: 0,
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.studentintake.Personal', {
	extend: 'Ext.form.Panel',
	alias: 'widget.studentintakepersonal',
	id: 'StudentIntakePersonal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.PersonalViewController',
    inject: {
        statesStore: 'statesStore'
    },
	width: '100%',
    height: '100%',    
	initComponent: function() {
		Ext.apply(this, 
				{
					autoScroll: true,
    		        border: 0,	
				    bodyPadding: 5,				    
					layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 150
				    },
				    items: [{
				            xtype: 'fieldset',
				            border: 0,
				            title: '',
				            defaultType: 'textfield',
				            defaults: {
				                anchor: '100%'
				            },
				       items: [{
				    	xtype: 'displayfield',
				        fieldLabel: 'Intake Date',
				        name: 'studentIntakeCreatedDate'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Agreed to Confidentiality',
				        name: 'confidentialityAgreement'
				    },{
				    	xtype: 'displayfield',
				        fieldLabel: 'Date of Agreement',
				        name: 'confidentialityAgreementDate'
				    },{
				        fieldLabel: 'First Name',
				        name: 'firstName',
				        itemId: 'firstName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: 'Middle Initial',
				        name: 'middleInitial',
				        itemId: 'middleInitial',
				        maxLength: 1,
				        allowBlank:true
				    },{
				        fieldLabel: 'Last Name',
				        name: 'lastName',
				        itemId: 'lastName',
				        maxLength: 50,
				        allowBlank:false
				    },{
				        fieldLabel: 'Student ID',
				        name: 'schoolId',
				        minLength: 0,
				        maxLength: 7,
				        itemId: 'studentId',
				        allowBlank:false
				    },{
				    	xtype: 'datefield',
				    	fieldLabel: 'Birth Date',
				    	itemId: 'birthDate',
				    	altFormats: 'm/d/Y|m-d-Y',
				    	invalidText: '{0} is not a valid date - it must be in the format: 06/21/2012 or 06-21-2012',
				        name: 'birthDate',
				        allowBlank:false
				    },{
				        fieldLabel: 'Home Phone',
				        name: 'homePhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maskRe: /[\d\-]/,
				        regex: /^\d{3}-\d{3}-\d{4}$/,
				        regexText: 'Must be in the format xxx-xxx-xxxx',
				        maxLength: 12,
				        allowBlank:true,
				        itemId: 'homePhone' 
				    },{
				        fieldLabel: 'Work Phone',
				        name: 'workPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maskRe: /[\d\-]/,
				        regex: /^\d{3}-\d{3}-\d{4}$/,
				        regexText: 'Must be in the format xxx-xxx-xxxx',
				        maxLength: 12,
				        allowBlank:true,
				        itemId: 'workPhone'
				    },{
				        fieldLabel: 'Cell Phone',
				        name: 'cellPhone',
				        emptyText: 'xxx-xxx-xxxx',
				        maskRe: /[\d\-]/,
				        regex: /^\d{3}-\d{3}-\d{4}$/,
				        regexText: 'Must be in the format xxx-xxx-xxxx',
				        maxLength: 12,
				        allowBlank:true,
				        itemId: 'cellPhone'
				    },{
				        fieldLabel: 'Address',
				        name: 'addressLine1',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'address'
				    },{
				        fieldLabel: 'City',
				        name: 'city',
				        maxLength: 50,
				        allowBlank:true,
				        itemId: 'city'
				    },{
				        xtype: 'combobox',
				        name: 'state',
				        fieldLabel: 'State',
				        emptyText: 'Select a State',
				        store: this.statesStore,
				        valueField: 'code',
				        displayField: 'title',
				        mode: 'local',
				        typeAhead: true,
				        queryMode: 'local',
				        allowBlank: true,
				        forceSelection: true,
				        itemId: 'state'
					},{
				        fieldLabel: 'Zip Code',
				        name: 'zipCode',
				        maxLength: 10,
				        allowBlank:true,
				        itemId: 'zipCode'
				    },{
				        fieldLabel: 'Primary Email (School)',
				        name: 'primaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:false,
				        itemId: 'primaryEmailAddress'
				    },{
				        fieldLabel: 'Alternate Email',
				        name: 'secondaryEmailAddress',
				        vtype:'email',
				        maxLength: 100,
				        allowBlank:true,
				        itemId: 'secondaryEmailAddress'
				    }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});

Ext.define('Ssp.view.tools.journal.Journal', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.journal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.JournalToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentJournalEntry',
        store: 'journalEntriesStore'
    },
	width: '100%',
	height: '100%',
	initComponent: function() {	
    	var sm = Ext.create('Ext.selection.CheckboxModel');

		Ext.apply(this, 
				{
		            autoScroll: true,
		            title: 'Journal',
		            store: this.store,
	    		      columns: [{
					    	        xtype:'actioncolumn',
					    	        width:65,
					    	        header: 'Action',
					    	        items: [{
					    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
					    	            tooltip: 'Edit Task',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	                panel.appEventsController.getApplication().fireEvent('editJournalEntry');
					    	            },
					    	            scope: this
					    	        },{
					    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
					    	            tooltip: 'Delete Task',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	            	panel.appEventsController.getApplication().fireEvent('deleteJournalEntry');
					    	            },
					    	            scope: this
					    	        }]
				                },
	    		                { header: 'Date',  
		    		                  dataIndex: 'createdBy',
		    		                  flex: 1,
		    		                  renderer: this.columnRendererUtils.renderCreatedByDate
	    		                },
	    		                { header: 'Entered By',  
	    		                  dataIndex: 'createdBy',
	    		                  flex: 1,
	    		                  renderer: this.columnRendererUtils.renderCreatedBy
	    		                },
	      		                { header: 'Source',
	      		                  dataIndex: 'journalSource', 
	      		                  flex: 1,
	      		                  renderer: this.columnRendererUtils.renderJournalSourceName
	    		                },
	      		                { header: 'Confidentiality',
	      		                  dataIndex: 'confidentialityLevel', 
	      		                  flex: 1,
	      		                  renderer: this.columnRendererUtils.renderConfidentialityLevelName
	    		                }],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Add Journal Note',
				            text: 'Add',
				            xtype: 'button',
				            itemId: 'addButton'
				        }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.journal.EditJournal',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournal',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.EditJournalViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore',
        journalSourcesStore: 'journalSourcesStore',
        journalTracksStore: 'journalTracksStore',
        model: 'currentJournalEntry'
    },	
    initComponent: function() {
    	Ext.applyIf(this, {
        	title: ((this.model.get('id') == "") ? "Add Journal" : "Edit Journal"),
        	autoScroll: true,
        	defaults: {
            	labelWidth: 150,
            	padding: 5
            },
        	items: [
                {
			        xtype: 'combobox',
			        itemId: 'confidentialityLevelCombo',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: this.confidentialityLevelsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '100%'
				},{
			        xtype: 'combobox',
			        itemId: 'journalSourceCombo',
			        name: 'journalSourceId',
			        fieldLabel: 'Source',
			        emptyText: 'Select One',
			        store: this.journalSourcesStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '100%'
				},{
			        xtype: 'combobox',
			        itemId: 'journalTrackCombo',
			        name: 'journalTrackId',
			        fieldLabel: 'Journal Track',
			        emptyText: 'Select One',
			        store: this.journalTracksStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: true,
			        forceSelection: false,
			        anchor: '100%'
				},{
		        	xtype: 'label',
		        	text: 'Session Details (Critical Components)'
				},{
					xtype: 'tbspacer',
					flex: 1
				},{
		            tooltip: 'Add Journal Session Details',
		            text: 'Add/Edit Session Details',
		            xtype: 'button',
		            itemId: 'addSessionDetailsButton'
	    	    },
                { xtype: 'displayjournaldetails', autoScroll: true, anchor:'100% 50%' }
				,{
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    itemId: 'commentText',
                    anchor: '100%',
                    name: 'comment'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.tools.journal.DisplayDetails', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.displayjournaldetails',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.DisplayDetailsViewController',
    inject: {
        store: 'journalEntryDetailsStore'
    },
    layout:'fit',
	width: '100%',
	height: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    	    		store: this.store,
    	    		hideHeaders: true,
    	    		
    	    		features: [{
		    	        id: 'group',
		    	        ftype: 'grouping',
		    	        groupHeaderTpl: '{name}',
		    	        hideGroupedHeader: false,
		    	        enableGroupingMenu: false
		    	    }],
		    	    
		    	    columns: [{
		    	        text: '',
		    	        flex: 1,
		    	        sortable: false,
		    	        dataIndex: 'name'
		    	    }]
    			});
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.journal.TrackTree', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.journaltracktree',
	mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.journal.TrackTreeViewController',
    inject: {
        store: 'treeStore'
    },
	height: 200,
	width: '100%',
	
    initComponent: function(){
    	Ext.apply(this,
    			{
   		     singleExpand: false,
			 store: this.store,
			 useArrows: true,
			 rootVisible: false ,
			 hideCollapseTool: true,

			 dockedItems: [{
		               xtype: 'toolbar',
		               items: [{
     		                   text: 'Save Details',
     		                   xtype: 'button',
     		                   action: 'save',
     		                   itemId: 'saveButton'
     		               }, '-', {
     		                   text: 'Cancel',
     		                   xtype: 'button',
     		                   action: 'cancel',
     		                   itemId: 'cancelButton'
     		               }]
		           },{
 		               xtype: 'toolbar',
  		               dock: 'top',
  		               items: [{
  		                         xtype: 'label',
  		                         text: 'Select the details for this Journal Session'
  		                       }]  
  		            }]			 
			 
    });   	
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.earlyalert.EarlyAlert', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.earlyalert',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertToolViewController',
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
        store: 'earlyAlertsStore'
    },
	width: '100%',
	height: '100%',
	
	initComponent: function() {	
    	var sm = Ext.create('Ext.selection.CheckboxModel');
		
		Ext.apply(this, 
				{
		            autoScroll: true,
		            title: 'Early Alerts',

	    		      columns: [{
					    	        xtype:'actioncolumn',
					    	        width:65,
					    	        header: 'Action',
					    	        items: [{
					    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
					    	            tooltip: 'Edit Task',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	                panel.appEventsController.getApplication().fireEvent('editJournalEntry');
					    	            },
					    	            scope: this
					    	        },{
					    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
					    	            tooltip: 'Delete Task',
					    	            handler: function(grid, rowIndex, colIndex) {
					    	            	var rec = grid.getStore().getAt(rowIndex);
					    	            	var panel = grid.up('panel');
					    	                panel.model.data=rec.data;
					    	            	panel.appEventsController.getApplication().fireEvent('deleteJournalEntry');
					    	            },
					    	            scope: this
					    	        }]
				                },
	    		                { header: 'Name',  
	    		                  dataIndex: 'courseTitle',
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 },
	    		                { header: 'Description',
	    		                  dataIndex: 'description',
	    		                  field: {
	    		                      xtype: 'textfield'
	    		                  },
	    		                  flex: 50 }
	    		                  ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				            tooltip: 'Respond to the selected Early Alert',
				            text: 'Respond',
				            xtype: 'button',
				            itemId: 'respondButton'
				        }]
				    }]
				});
		
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.earlyalert.EarlyAlertResponse',{
	extend: 'Ext.form.Panel',
	alias : 'widget.earlyalertresponse',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.earlyalert.EarlyAlertResponseViewController',
    inject: {
    	earlyAlert: 'currentEarlyAlert',
        outcomesStore: 'earlyAlertOutcomesStore',
        outreachesStore: 'earlyAlertOutreachesStore',
        referralsStore: 'earlyAlertReferralsStore'
    },
    initComponent: function() {
		Ext.applyIf(this, {
			autoScroll: true,
        	title: 'Early Alert Response',
        	defaults:{
        		labelWidth: 200
        	},
            items: [
                {
                	xtype: 'displayfield',
                	fieldLabel: 'Early Alert Response',
                	value: this.earlyAlert.get('courseTitle'),
                	anchor: '95%'
                },{
			        xtype: 'combobox',
			        itemId: 'outcomeCombo',
			        name: 'earlyAlertOutcomeId',
			        fieldLabel: 'Outcome',
			        emptyText: 'Select One',
			        store: this.outcomesStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
					xtype: 'textfield',
					itemId: 'otherOutcomeDescriptionText',
					name: 'earlyAlertOutcomeOtherDescription',
					fieldLabel: 'Other Outcome Description',
					anchor: '95%'
				},{
		            xtype: 'multiselect',
		            name: 'earlyAlertOutreachIds',
		            fieldLabel: 'Outreach',
		            store: this.outreachesStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: false,
		            minSelections: 0,
		            anchor: '95%'
		        },{
		            xtype: 'multiselect',
		            name: 'earlyAlertReferralIds',
		            fieldLabel: 'Department and Service Referrals',
		            store: this.referralsStore,
		            displayField: 'name',
		            valueField: 'id',
		            allowBlank: true,
		            minSelections: 0,
		            anchor: '95%'
		        },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Comment',
                    anchor: '95%',
                    name: 'comment'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.tools.document.StudentDocuments', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.studentdocuments',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.document.StudentDocumentToolViewController',
    inject: {
    	appEventsController: 'appEventsController',
    	columnRendererUtils: 'columnRendererUtils',
    	model: 'currentDocument',
        store: 'documentsStore'
    },
	title: 'Student Documents',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
			border: 0,
			store: this.store,
			dockedItems: [{
		        dock: 'top',
		        xtype: 'toolbar',
		        items: [{
		        			xtype: 'button', 
		        			itemId: 'addButton', 
		        			text:'Add', 
		        			action: 'add'
		        	   },{
		        			xtype: 'button', 
		        			itemId: 'downloadButton', 
		        			text:'Download', 
		        			action: 'download'
		        	   }]
			}],
			
        	columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'createdDate',
                    text: 'Date Entered'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'name',
                    text: 'Name'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'note',
                    text: 'Note',
                    flex: 1
                },{
	    	        xtype:'actioncolumn',
	    	        width:65,
	    	        header: 'Action',
	    	        items: [{
	    	            icon: Ssp.util.Constants.GRID_ITEM_EDIT_ICON_PATH,
	    	            tooltip: 'Edit Task',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	                panel.appEventsController.getApplication().fireEvent('editDocument');
	    	            },
	    	            scope: this
	    	        },{
	    	            icon: Ssp.util.Constants.GRID_ITEM_DELETE_ICON_PATH,
	    	            tooltip: 'Delete Task',
	    	            handler: function(grid, rowIndex, colIndex) {
	    	            	var rec = grid.getStore().getAt(rowIndex);
	    	            	var panel = grid.up('panel');
	    	                panel.model.data=rec.data;
	    	            	panel.appEventsController.getApplication().fireEvent('deleteDocument');
	    	            },
	    	            scope: this
	    	        }]
                }],
                
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.document.EditDocument',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editdocument',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.document.EditDocumentViewController',
    initComponent: function() {
		Ext.applyIf(this, {
        	title: 'Edit Document',
            defaults: {
            	labelWidth: 150
            },
        	items: [
                {
			        xtype: 'combobox',
			        itemId: 'confidentialityLevelCombo',
			        name: 'confidentialityLevelId',
			        fieldLabel: 'Confidentiality Level',
			        emptyText: 'Select One',
			        store: this.confidentialityLevelsStore,
			        valueField: 'id',
			        displayField: 'name',
			        mode: 'local',
			        typeAhead: true,
			        queryMode: 'local',
			        allowBlank: false,
			        forceSelection: true,
			        anchor: '95%'
				},{
                    xtype: 'textfield',
                    fieldLabel: 'Name',
                    anchor: '100%',
                    name: 'name',
                    anchor: '95%',
                    allowBlank: false
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Note',
                    anchor: '100%',
                    name: 'note',
                    anchor: '95%'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.tools.sis.StudentInformationSystem', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentinformationsystem',
	title: 'Student Information System',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{		
					border: 0,
				    items: [ Ext.createWidget('tabpanel', {
				        width: '100%',
				        height: '100%',
				        activeTab: 0,
						border: 0,
				        items: [ { title: 'Registration',
				        	       autoScroll: true,
				        		   items: [{xtype: 'sisregistration'}]
				        		},{
				            		title: 'Transcript',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'sistranscript'}]
				        		},{
				            		title: 'Assessment',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'sisassessment'}]
				        		},{
				            		title: 'ACT',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'sisact'}]
				        		}]
				    })]
			    
		});
		
		return me.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.sis.Registration', {
	extend: 'Ext.form.Panel',
	alias: 'widget.sisregistration',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Academic Status',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Registration Status',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'Start Term',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    fieldLabel: 'CUM GPA',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.sis.Assessment', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.sisassessment',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Type'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Score'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Status'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Date'
                }
            ],
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.sis.Transcript', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.sistranscript',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Course'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Section'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Credit'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Title'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Grade'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Term'
                }
            ],
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.sis.Act', {
	extend: 'Ext.grid.Panel',
	alias: 'widget.sisact',
    width: '100%',
    height: '100%',
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Type'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Score'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Status'
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'string',
                    text: 'Date'
                }
            ],
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.disability.DisabilityServices', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.disabilityservices',
	title: 'Disability Services',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{		
					border: 0,
					dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				        			xtype: 'button', 
				        			itemId: 'saveButton', 
				        			text:'Save', 
				        			action: 'save'
				        	   }]
					}],
				    
				    items: [ Ext.createWidget('tabpanel', {
				        width: '100%',
				        height: '100%',
				        activeTab: 0,
						border: 0,
				        items: [ { title: 'General',
				        	       autoScroll: true,
				        		   items: [{xtype: 'disabilitygeneral'}]
				        		},{
				            		title: 'Agency Contacts',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'disabilityagencycontacts'}]
				        		},{
				            		title: 'Disability',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'disabilitycodes'}]
				        		},{
				            		title: 'Disposition',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'disabilitydisposition'}]
				        		},{
				            		title: 'Accommodations',
				            		autoScroll: true,
				            		border: 0,
				            		items: [{xtype: 'disabilityaccommodations'}]
				        		}]
				    })]
			    
		});
		
		return me.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.disability.General', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitygeneral',
    height: '100%',
    width: '100%',
    bodyPadding: 0,
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'combobox',
                    fieldLabel: 'ODS Status',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'If temporary eligibility, please explain',
                    anchor: '100%'
                },
                {
                    xtype: 'displayfield',
                    value: 'Display Field',
                    fieldLabel: 'ODS Registration Date',
                    anchor: '100%'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'ODS Counselor',
                    anchor: '100%'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Referred to ODS By',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }
});
Ext.define('Ssp.view.tools.disability.AgencyContacts', {
	extend: 'Ext.form.Panel',
	alias: 'widget.disabilityagencycontacts',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
        var me = this;

		Ext.apply(me, 
				{
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return me.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.disability.DisabilityCodes', {
	extend: 'Ext.form.Panel',
	alias: 'widget.disabilitycodes',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
        var me = this;

		Ext.apply(me, 
				{
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return me.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.disability.Disposition', {
    extend: 'Ext.form.Panel',
    alias: 'widget.disabilitydisposition',
    height: '100%',
    width: '100%',
    bodyPadding: 0,
    autoScroll: true,
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Release Signed',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Records Requested',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Referred for Screening LD/ADD',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Rights and Duties',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Eligibility Letter Sent',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Ineligibility Letter Sent',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'No Disability Documentation Received',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Inadequate Documentation',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Document states individual has no disability',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'HS reports no special ed placement/no report',
                    boxLabel: '',
                    anchor: '100%'
                },
                {
                    xtype: 'combobox',
                    fieldLabel: 'On Medication',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'If yes, list medications',
                    anchor: '100%'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Functional limitations, please explain',
                    anchor: '100%'
                }
            ]
        });

        me.callParent(arguments);
    }

});
Ext.define('Ssp.view.tools.disability.Accommodations', {
	extend: 'Ext.form.Panel',
	alias: 'widget.disabilityaccommodations',
    width: '100%',
    height: '100%',
    autoScroll: true,
	initComponent: function() {	
        var me = this;

		Ext.apply(me, 
				{
				    bodyPadding: 5,
				    layout: 'anchor',
				    defaults: {
				        anchor: '100%'
				    },
				    defaultType: 'checkbox'
				});
		
		return me.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.displacedworker.DisplacedWorker', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.displacedworker',
	title: 'Displaced Workers',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.tools.studentsuccess.StudentSuccess', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentsuccess',
	title: 'Student Success',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.admin.forms.AbstractReferenceAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.abstractreferenceadmin',
	title: 'Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.AbstractReferenceAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },
	height: '100%',
	width: '100%',
	autoScroll: true,

    initComponent: function(){
    	var cellEditor = Ext.create('Ext.grid.plugin.RowEditing',
		                             { clicksToEdit: 2 });
    	Ext.apply(this,
    			{
    		      plugins:cellEditor,
    		      selType: 'rowmodel',
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 50 
    		                 },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 50,
    		                  field: {
    		                      xtype: 'textfield'
    		                  }
    		                },
    		                { 
    		                  header: 'Status',
      		                  dataIndex: 'objectStatus' 
      		                }
    		           ],
    		        
    		           dockedItems: [
    		       		{
    		       			xtype: 'pagingtoolbar',
    		       		    dock: 'bottom',
    		       		    displayInfo: true,
    		       		    pageSize: this.apiProperties.getPagingSize()
    		       		},
    		              {
    		               xtype: 'toolbar',
    		               dock: 'top',
    		               items: [{
    		                   text: 'Add',
    		                   iconCls: 'icon-add',
    		                   xtype: 'button',
    		                   action: 'add',
    		                   itemId: 'addButton'
    		               }, '-', {
    		                   text: 'Delete',
    		                   iconCls: 'icon-delete',
    		                   xtype: 'button',
    		                   action: 'delete',
    		                   itemId: 'deleteButton'
    		               }]
    		           },{
    		               xtype: 'toolbar',
    		               dock: 'top',
    		               items: [{
    	                      xtype: 'label',
    	                       text: 'Double-click to edit an item.'
    	                     }]
    		           }]    	
    	});
    	
    	this.callParent(arguments);
    },
    
    reconfigure: function(store, columns) {
        var me = this,
            headerCt = me.headerCt;

        if (me.lockable) {
            me.reconfigureLockable(store, columns);
        } else {
            if (columns) {
                headerCt.suspendLayout = true;
                headerCt.removeAll();
                headerCt.add(columns);
            }
            if (store) {
                store = Ext.StoreManager.lookup(store);
                me.down('pagingtoolbar').bindStore(store);
                me.bindStore(store);        
            } else {
                me.getView().refresh();
            }
            if (columns) {
                headerCt.suspendLayout = false;
                me.forceComponentLayout();
            }
        }
        me.fireEvent('reconfigure', me);
    }

});
Ext.define('Ssp.view.admin.forms.ConfidentialityDisclosureAgreementAdmin', {
	extend: 'Ext.form.Panel',
	alias : 'widget.confidentialitydisclosureagreementadmin',
	id: 'ConfidentialityDisclosureAgreementAdmin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.ConfidentialityDisclosureAgreementAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },

	initComponent: function() {	
		Ext.apply(this, 
				{
		    		width: '100%',
		    		height: '100%',
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
				            title: 'Confidentiality Disclosure Agreement',
				            defaultType: 'displayfield',
				            defaults: {
				                anchor: '100%'
				            },
				       items: 
				       [{
					        fieldLabel: 'Name',
					        xtype: 'textfield',
					        name: 'name'
					    },{
					        fieldLabel: 'Description',
					        xtype: 'textfield',
					        name: 'description'
					    },{
		    		          xtype: 'htmleditor',
		    		          fieldLabel: 'Disclosure Agreement',
		    		          enableColors: false,
		    		          enableAlignments: false,
		    		          height: '100%',
		    		          width: '100%',
		    		          name: 'text'
		    		      }]
					    }],
					    
		           dockedItems: [
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Save',
     		                   xtype: 'button',
     		                   action: 'save',
     		                   itemId: 'saveButton'
     		               }]
     		           }]
				});
		
	     return this.callParent(arguments);
	}

});
Ext.define('Ssp.view.admin.forms.crg.ChallengeAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.challengeadmin',
	title: 'Challenge Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.ChallengeAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displaychallengesadmin', 
	                  	flex: 1
	                  },{
	                  	xtype: 'displaychallengecategoriesadmin', 
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.crg.ChallengeReferralAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.challengereferraladmin',
	title: 'Challenge Referral Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.ChallengeReferralAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displayreferralsadmin', 
	                  	flex: 1
	                  },{
		                  	xtype: 'displaychallengereferralsadmin', 
		                  	flex: 1
		                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.crg.AssociateChallengeCategoriesAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengecategoriesadmin',
	title: 'Challenge Category Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.AssociateChallengeCategoriesAdminViewController',
    inject: {
        store: 'treeStore'
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    		     singleExpand: true,
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
			        plugins: {
			            ptype: 'treeviewdragdrop',
			            dropGroup: 'gridtotree',
			            enableDrop: true
			        }
			     },
    			 dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            xtype: 'button',
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }] 
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.crg.AssociateChallengeReferralsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.displaychallengereferralsadmin',
	title: 'Challenge Referral Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.AssociateChallengeReferralsAdminViewController',
    inject: {
        store: 'treeStore'
    },
    viewConfig: {
        plugins: {
            ptype: 'treeviewdragdrop'
        }
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    		     autoScroll: true,
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
				        plugins: {
				            ptype: 'treeviewdragdrop',
				            dropGroup: 'gridtotree',
				            enableDrop: true
				        }
				 },    			 
    		     dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            xtype: 'button',
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }]
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.crg.DisplayChallengesAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaychallengesadmin',
	title: 'Challenges Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.DisplayChallengesAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        columnRendererUtils: 'columnRendererUtils'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: true
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 1 
    		                },
    		                { header: 'Show On Intake',  
      		                  dataIndex: 'showInStudentIntake',
      		                  renderer: me.columnRendererUtils.renderFriendlyBoolean,
      		                  flex: 1 
      		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: me.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
     		               dock: 'top',
     		               items: [{
     	                      xtype: 'label',
     	                       text: 'Associate items by dragging a Challenge onto a Category folder'
     	                     }]
     		           }]    	
    	});
    	
    	return me.callParent(arguments);
    },
    
    reconfigure: function(store, columns) {
        var me = this,
            headerCt = me.headerCt;

        if (me.lockable) {
            me.reconfigureLockable(store, columns);
        } else {
            if (columns) {
                headerCt.suspendLayout = true;
                headerCt.removeAll();
                headerCt.add(columns);
            }
            if (store) {
                store = Ext.StoreManager.lookup(store);
                me.down('pagingtoolbar').bindStore(store);
                me.bindStore(store);        
            } else {
                me.getView().refresh();
            }
            if (columns) {
                headerCt.suspendLayout = false;
                me.forceComponentLayout();
            }
        }
        me.fireEvent('reconfigure', me);
    }
});
Ext.define('Ssp.view.admin.forms.crg.DisplayReferralsAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displayreferralsadmin',
	title: 'Referrals Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.DisplayReferralsAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },
	height: '100%',
	width: '100%',

    initComponent: function(){
    	Ext.apply(this,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: true
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1 
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: this.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
      		              dock: 'top',
      		               items: [{
      		                         xtype: 'label',
      		                         text: 'Associate items by dragging a Referral onto a Challenge folder'
      		                       }]  
      		           }]    	
    	});
    	
    	return this.callParent(arguments);
    },
    
    reconfigure: function(store, columns) {
        var me = this,
            headerCt = me.headerCt;

        if (me.lockable) {
            me.reconfigureLockable(store, columns);
        } else {
            if (columns) {
                headerCt.suspendLayout = true;
                headerCt.removeAll();
                headerCt.add(columns);
            }
            if (store) {
                store = Ext.StoreManager.lookup(store);
                me.down('pagingtoolbar').bindStore(store);
                me.bindStore(store);        
            } else {
                me.getView().refresh();
            }
            if (columns) {
                headerCt.suspendLayout = false;
                me.forceComponentLayout();
            }
        }
        me.fireEvent('reconfigure', me);
    }
});
Ext.define('Ssp.view.admin.forms.crg.EditChallenge',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editchallenge',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditChallengeViewController',
    inject: {
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
	title: 'Edit Challenge',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Challenge Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },
                {
                    xtype: 'textfield',
                    fieldLabel: 'Tags',
                    anchor: '100%',
                    name: 'tags'
                },{
                    xtype: 'combobox',
                    name: 'defaultConfidentialityLevelId',
                    fieldLabel: 'Confidentiality Level',
                    emptyText: 'Select One',
                    store: this.confidentialityLevelsStore,
                    valueField: 'id',
                    displayField: 'acronym',
                    mode: 'local',
                    typeAhead: true,
                    queryMode: 'local',
                    allowBlank: false,
                    forceSelection: true
            	},{
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Description',
                    anchor: '100%',
                    name: 'selfHelpGuideDescription'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Self Help Guide Question',
                    anchor: '100%',
                    name: 'selfHelpGuideQuestion'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Student Intake',
                    anchor: '100%',
                    name: 'showInStudentIntake'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Self Help Search',
                    anchor: '100%',
                    name: 'showInSelfHelpSearch'
                }
            ],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.admin.forms.crg.EditReferral',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editreferral',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.crg.EditReferralViewController',
	title: 'Edit Referral',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Referral Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                },{
                    xtype: 'textareafield',
                    fieldLabel: 'Public Description',
                    anchor: '100%',
                    name: 'publicDescription'
                },
                {
                    xtype: 'checkboxfield',
                    fieldLabel: 'Show in Self Help Guide',
                    anchor: '100%',
                    name: 'showInSelfHelpGuide'
                }
            ],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.admin.forms.journal.JournalStepAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.journalstepadmin',
	title: 'Challenge Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.JournalStepAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displaystepsadmin', 
	                  	flex: 1
	                  },{
	                  	xtype: 'associatetrackstepsadmin', 
	                  	flex: 1
	                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.journal.JournalStepDetailAdmin', {
	extend: 'Ext.container.Container',
	alias : 'widget.journalstepdetailadmin',
	title: 'Journal Step Detail Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.JournalStepDetailAdminViewController',
	height: '100%',
	width: '100%',
	layout: {
        type: 'hbox',
        align: 'stretch'
    },
    initComponent: function(){
		Ext.apply(this,{
	          items: [
	                  {
	                  	xtype: 'displaydetailsadmin', 
	                  	flex: 1
	                  },{
		                  	xtype: 'associatestepdetailsadmin', 
		                  	flex: 1
		                  }
	                 ]});
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.journal.AssociateTrackStepsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.associatetrackstepsadmin',
	title: 'Track Steps Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.AssociateTrackStepsAdminViewController',
    inject: {
        store: 'treeStore'
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
			        plugins: {
			            ptype: 'treeviewdragdrop',
			            dropGroup: 'gridtotree',
			            enableDrop: true
			        }
			     },
    			 dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            xtype: 'button',
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }] 
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.journal.AssociateStepDetailsAdmin', {
	extend: 'Ext.tree.Panel',
	alias : 'widget.associatestepdetailsadmin',
	title: 'Step Details Associations',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.AssociateStepDetailsAdminViewController',
    inject: {
        store: 'treeStore'
    },
    viewConfig: {
        plugins: {
            ptype: 'treeviewdragdrop'
        }
    },
	height: '100%',
	width: '100%',
    initComponent: function(){
    	Ext.apply(this,
    			{
    		     autoScroll: true,
    			 store: this.store,
    			 useArrows: true,
    			 rootVisible: false,
    			 singleExpand: true,
			     viewConfig: {
				        plugins: {
				            ptype: 'treeviewdragdrop',
				            dropGroup: 'gridtotree',
				            enableDrop: true
				        }
				 },    			 
    		     dockedItems: [{
     				        dock: 'top',
     				        xtype: 'toolbar',
     				        items: [{
     				            tooltip: 'Delete selected association',
     				            text: 'Delete Associations',
     				            xtype: 'button',
     				            itemId: 'deleteAssociationButton'
     				        }]
     		    	    }]
     		       	
    	});
    	
    	return this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.journal.DisplayDetailsAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaydetailsadmin',
	title: 'Details Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.DisplayDetailsAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },
	height: '100%',
	width: '100%',

    initComponent: function(){
    	Ext.apply(this,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: true
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1 
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: this.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
     		               xtype: 'toolbar',
      		               dock: 'top',
      		               items: [{
      		                         xtype: 'label',
      		                         text: 'Associate items by dragging a Detail onto a Step folder'
      		                       }]  
      		            }]    	
    	});
    	
    	return this.callParent(arguments);
    },
    
    reconfigure: function(store, columns) {
        var me = this,
            headerCt = me.headerCt;

        if (me.lockable) {
            me.reconfigureLockable(store, columns);
        } else {
            if (columns) {
                headerCt.suspendLayout = true;
                headerCt.removeAll();
                headerCt.add(columns);
            }
            if (store) {
                store = Ext.StoreManager.lookup(store);
                me.down('pagingtoolbar').bindStore(store);
                me.bindStore(store);        
            } else {
                me.getView().refresh();
            }
            if (columns) {
                headerCt.suspendLayout = false;
                me.forceComponentLayout();
            }
        }
        me.fireEvent('reconfigure', me);
    }
});
Ext.define('Ssp.view.admin.forms.journal.DisplayStepsAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.displaystepsadmin',
	title: 'Steps Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.DisplayStepsAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },
    height: '100%',
	width: '100%',

    initComponent: function(){
    	Ext.apply(this,
    			{
		          viewConfig: {
		        	  plugins: {
		                  ptype: 'gridviewdragdrop',
		                  dragGroup: 'gridtotree',
		                  enableDrag: true
		        	  },
		          },
    		      autoScroll: true,
    		      selType: 'rowmodel',
    		      enableDragDrop: false,
    		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  field: {
    		                      xtype: 'textfield'
    		                  },
    		                  flex: 1 
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: this.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-' ,{
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           },{
    		               xtype: 'toolbar',
      		               dock: 'top',
      		               items: [{
      		                         xtype: 'label',
      		                         text: 'Associate items by dragging a Step onto a Track folder'
      		                       }]  
      		            }]    	
    	});
    	
    	return this.callParent(arguments);
    },
    
    reconfigure: function(store, columns) {
        var me = this,
            headerCt = me.headerCt;

        if (me.lockable) {
            me.reconfigureLockable(store, columns);
        } else {
            if (columns) {
                headerCt.suspendLayout = true;
                headerCt.removeAll();
                headerCt.add(columns);
            }
            if (store) {
                store = Ext.StoreManager.lookup(store);
                me.down('pagingtoolbar').bindStore(store);
                me.bindStore(store);        
            } else {
                me.getView().refresh();
            }
            if (columns) {
                headerCt.suspendLayout = false;
                me.forceComponentLayout();
            }
        }
        me.fireEvent('reconfigure', me);
    }
});
Ext.define('Ssp.view.admin.forms.journal.EditStep',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournalstep',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.EditStepViewController',
	title: 'Edit Step',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Step Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.admin.forms.journal.EditStepDetail',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editjournalstepdetail',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.journal.EditStepDetailViewController',
	title: 'Edit Detail',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Detail Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.admin.forms.campus.CampusAdmin', {
	extend: 'Ext.grid.Panel',
	alias : 'widget.campusadmin',
	title: 'Campus Admin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.CampusAdminViewController',
    inject: {
        apiProperties: 'apiProperties'
    },
    height: '100%',
	width: '100%',
	layout: 'fit',
    initComponent: function(){

    	Ext.apply(this,
    			{
    		      
    		      autoScroll: true,
     		      columns: [
    		                { header: 'Name',  
    		                  dataIndex: 'name',
    		                  flex: 50 },
    		                { header: 'Description',
    		                  dataIndex: 'description', 
    		                  flex: 50
    		                }
    		           ],
    		        
    		           dockedItems: [
     		       		{
     		       			xtype: 'pagingtoolbar',
     		       		    dock: 'bottom',
     		       		    displayInfo: true,
     		       		    pageSize: this.apiProperties.getPagingSize()
     		       		},
     		              {
     		               xtype: 'toolbar',
     		               items: [{
     		                   text: 'Add',
     		                   iconCls: 'icon-add',
     		                   xtype: 'button',
     		                   action: 'add',
     		                   itemId: 'addButton'
     		               }, '-', {
     		                   text: 'Edit',
     		                   iconCls: 'icon-edit',
     		                   xtype: 'button',
     		                   action: 'edit',
     		                   itemId: 'editButton'
     		               }, '-', {
     		                   text: 'Delete',
     		                   iconCls: 'icon-delete',
     		                   xtype: 'button',
     		                   action: 'delete',
     		                   itemId: 'deleteButton'
     		               }]
     		           }]  	
    	});

    	this.callParent(arguments);
    }
});
Ext.define('Ssp.view.admin.forms.campus.DefineCampus',{
	extend: 'Ext.panel.Panel',
	alias : 'widget.definecampus',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.DefineCampusViewController',
	title: 'Define a Campus',
	height: '100%',
	width: '100%',
	layout:'card',
	initComponent: function() {
        Ext.applyIf(this, {
        	activeItem: 0,
        	
        	dockedItems: [{
	               xtype: 'toolbar',
	               dock: 'bottom',
	               items: [{
	                   text: 'Prev',
	                   xtype: 'button',
	                   action: 'prev',
	                   itemId: 'prevButton'
	               }, '-', {
	                   text: 'Next',
	                   xtype: 'button',
	                   action: 'next',
	                   itemId: 'nextButton'
	               }, '-', {
	                   text: 'Finish',
	                   xtype: 'button',
	                   action: 'finish',
	                   itemId: 'finishButton'
	               }]
	           }],
        	
        	items: [{
        	    id: 'card-0',
        	    html: 'Step 1'
        	},{
        	    id: 'card-1',
        	    html: 'Step 2'
        	},{
        	    id: 'card-2',
        	    html: 'Step 3'
        	}]
        });
        return this.callParent(arguments);
	}
});
Ext.define('Ssp.view.admin.forms.campus.EditCampus',{
	extend: 'Ext.form.Panel',
	alias : 'widget.editcampus',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.campus.EditCampusViewController',
	title: 'Edit Campus',
	initComponent: function() {
        Ext.applyIf(this, {
            items: [
                {
                    xtype: 'textfield',
                    fieldLabel: 'Name',
                    anchor: '100%',
                    name: 'name'
                },
                {
                    xtype: 'textareafield',
                    fieldLabel: 'Description',
                    anchor: '100%',
                    name: 'description'
                }],
            
            dockedItems: [{
       		               xtype: 'toolbar',
       		               items: [{
		       		                   text: 'Save',
		       		                   xtype: 'button',
		       		                   action: 'save',
		       		                   itemId: 'saveButton'
		       		               }, '-', {
		       		                   text: 'Cancel',
		       		                   xtype: 'button',
		       		                   action: 'cancel',
		       		                   itemId: 'cancelButton'
		       		               }]
       		           }]
        });

        return this.callParent(arguments);
    }	
});
Ext.define('Ssp.view.ErrorWindow', {
	extend: 'Ext.window.Window',
	alias : 'widget.ssperrorwindow',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	columnRendererUtils: 'columnRendererUtils',
    	store: 'errorsStore'
    },
	width: '100%',
	height: '100%',
	title: 'Error! Please correct the errors listed below:',
    initComponent: function(){
    	var me=this;
    	Ext.apply(me,
    			   {
				    modal: true, 
		    		layout: 'fit',
    				items: [{
    			        xtype: 'grid',
    			        border: false,
    			        columns:[{ header: 'Error', 
		 				           dataIndex: 'label',
						           sortable: false,
						           menuDisabled: true,
						           flex:.25 
						         },{ header: 'Message', 
						           dataIndex: 'errorMessage',
						           renderer: me.columnRendererUtils.renderErrorMessage,
						           sortable: false,
						           menuDisabled: true,
						           flex:.75 
						         }],     
    			        store: me.store
    			    }],
    			    bbar: [
    			           { xtype: 'button', 
    			        	 text: 'OK', 
    			        	 itemId: 'closeButton', 
    			        	 handler: function() {
    			        		 me.close();
    			             }
    			           }
    			         ]
		    	    });
    	
    	return me.callParent(arguments);
    }
});
Ext.define('Ssp.model.Preferences', {
    extend: 'Ext.data.Model',
    fields: [{name:'SEARCH_GRID_VIEW_TYPE',type:'int', defaultValue:0}, // 0 display photo, 1 display grid
    		 {name:'ACTION_PLAN_ACTIVE_VIEW',type:'int',defaultValue:0} // 0 for Tasks, 1 for Goals
    		]
});
Ext.define('Ssp.model.FieldError', {
    extend: 'Ext.data.Model',
    fields: [{name:'label', type: 'string'},
             {name: 'errorMessage', type: 'string'}]
});
Ext.define('Ssp.model.util.TreeRequest', {
    extend: 'Ext.data.Model',
    fields: [{name: 'url', type: 'string'},
             {name: 'nodeType', type: 'string'},
             {name: 'isLeaf', type: 'boolean'},
             {name: 'nodeToAppendTo', defaultValue: null},
             {name: 'destroyBeforeAppend', type: 'boolean', defaultValue: false},
             {name: 'enableCheckedItems', type: 'boolean', defaultValue: true},
             {name: 'expanded', type:'boolean',defaultValue: false},
             {name: 'expandable', type:'boolean', defaultValue: true},
             {name: 'callbackFunc',type:'auto'},
             {name: 'callbackScope', type: 'auto'}]
});
Ext.define('Ssp.model.Configuration', {
    extend: 'Ext.data.Model',
    fields: [{name: 'syncStudentPersonalDataWithExternalSISData', 
    	      type: 'boolean', 
    	      defaultValue: false
    	     },
             {
    	      name: 'studentIdAlias', 
    	      type: 'string', 
    	      defaultValue: 'Tartan ID'
    	     },
    	     {name: 'studentIdMinValidationLength', 
    	      type: 'number', 
    	      defaultValue: 7
    	     },
       	     {name: 'studentIdMinValidationErrorText', 
       	      type: 'string', 
       	      defaultValue: 'The entered value is not long enough.'
       	     },
    	     {name: 'studentIdMaxValidationLength', 
       	      type: 'number', 
       	      defaultValue: 7
       	     },
       	     {name: 'studentIdMaxValidationErrorText', 
      	      type: 'string', 
      	      defaultValue: 'The entered value is too long.'
      	     },
    	     {name: 'studentIdAllowableCharacters', 
          	  type: 'string', 
          	  defaultValue: '0-9'
          	 },
    	     {name: 'studentIdValidationErrorText', 
             	  type: 'string', 
             	  defaultValue: 'Not a valid Student Id'
             },
    	     {
              name: 'displayStudentIntakeDemographicsEmploymentShift', 
              type: 'boolean', 
              defaultValue: '1'
             },
    	     {
              name: 'educationPlanParentsDegreeLabel', 
              type: 'string', 
              defaultValue: 'Have your parents obtained a college degree?'
             },
    	     {
              name: 'educationPlanSpecialNeedsLabel', 
              type: 'string', 
              defaultValue: 'Special needs or require special accomodation?'
             }],
             
     	constructor: function(){
     		var me=this;
     		me.callParent(arguments);
     		// apply student id validator for use in 
     		// form fields throughout the application
     		var minStudentIdLen = me.get('studentIdMinValidationLength');
    		var maxStudentIdLen = me.get('studentIdMaxValidationLength');
    		var allowableCharacters = me.get('studentIdAllowableCharacters');
    		// Example RegEx - /(^[1-9]{7,9})/
    		var regExString = '^([' + allowableCharacters + ']';
    		regExString = regExString + '{' + minStudentIdLen + ',';
    		regExString = regExString + maxStudentIdLen + '})';
    		var validStudentId = new RegExp( regExString );
            var studentIdValErrorText = 'You should only use the following character list for input: ' + allowableCharacters;
    		me.set('studentIdValidationErrorText',studentIdValErrorText);
    		me.set('studentIdMinValidationErrorText', 'Value should be at least ' + minStudentIdLen + ' characters & no more than ' + maxStudentIdLen + ' characters'); 	
            me.set('studentIdMaxValidationErrorText', 'Value should be at least ' + minStudentIdLen + ' characters & no more than ' + maxStudentIdLen + ' characters'); 

    		Ext.apply(Ext.form.field.VTypes, {
                //  vtype validation function
                studentIdValidator: function(val, field) {
                    return validStudentId.test(val);
                }
            });
            
    		return me;
    	}
});
Ext.define('Ssp.model.tool.studentintake.StudentIntakeForm', {
	extend: 'Ext.data.Model',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    }, 

	fields: [{name: 'person', 
		      convert: function(value, record) {
		            var person  = Ext.create('Ssp.model.Person',{});
		            person.populateFromGenericObject( value );		
		            return person;
		      	}
             },
              {name: 'personDemographics', 
   		      convert: function(value, record) {
		            var personDemographics = Ext.create('Ssp.model.tool.studentintake.PersonDemographics',{});
		            personDemographics.populateFromGenericObject( value );		
		            return personDemographics;
		      	}
             },
             {name: 'personEducationGoal', 
   		      convert: function(value, record) {
   		            var personEducationGoal = Ext.create('Ssp.model.tool.studentintake.PersonEducationGoal',{});
   		            personEducationGoal.populateFromGenericObject( value );  		
   		            return personEducationGoal;
   		      	}
             },
             {name: 'personEducationPlan', 
      		  convert: function(value, record) {
      		            var personEducationPlan = Ext.create('Ssp.model.tool.studentintake.PersonEducationPlan',{});
      		            personEducationPlan.populateFromGenericObject( value );
      		            return personEducationPlan;
      		    }
             },
             'personEducationLevels',
             'personFundingSources',
             'personChallenges',
             'referenceData'],

	autoLoad: false,
 	proxy: {
		type: 'rest',
		url: '/ssp/api/1/tool/studentIntake/',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});
Ext.define('Ssp.model.tool.journal.JournalEntryDetail', {
    extend: 'Ext.data.Model',
    fields: [{name:'group',type:'string'},
             {name:'id', type: 'string'},
             {name:'name', type: 'string'}]
});
Ext.define('Ssp.model.ApiUrl', {
    extend: 'Ext.data.Model',
    fields: [{name:'name',type:'string'},
             {name:'url',type:'string'}]
});
Ext.define('Ssp.mixin.ApiProperties', {	
	Extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiUrlStore: 'apiUrlStore' 
    },
    statics: {
    	getBaseAppUrl: function(){
    		var apiVersion = "1";
    	    var base = document.getElementsByTagName('base')[0];
    	    if (base && base.href && (base.href.length > 0)) {
    	        base = base.href;
    	    } else {
    	        base = document.URL;
    	    }
    	    return base.substr(0, base.indexOf("/", base.indexOf("//") + 2) + 1) + Ext.Loader.getPath('ContextName') + '/api/' + apiVersion + '/';
    	}
    },
    
	initComponent: function(){
		this.callParent(arguments);
	},
	
	getContext: function() {
		return Ssp.mixin.ApiProperties.getBaseAppUrl();
	},
	
	createUrl: function(value){
		return this.getContext() + value;
	},
	
	getPagingSize: function(){
		return 10;
	},
	
	getProxy: function(url){
		var proxyObj = {
			type: 'rest',
			url: this.createUrl(url),
			actionMethods: {
				create: "POST", 
				read: "GET", 
				update: "PUT", 
				destroy: "DELETE"
			},
			reader: {
				type: 'json',
				root: 'rows',
				totalProperty: 'results',
				successProperty: 'success',
				message: 'message'
			},
		    writer: {
		        type: 'json',
		        successProperty: 'success'
		    }
		};
		return proxyObj;
	},
	
	/*
	 * @args - {}
	 *    url - url of the request
	 *    method - 'PUT', 'POST', 'GET', 'DELETE'
	 *    jsonData - data to send
	 *    successFunc - success function
	 *    scope - scope
	 */
	makeRequest: function(args){
		Ext.Ajax.request({
			url: args.url,
			method: args.method,
			headers: { 'Content-Type': 'application/json' },
			jsonData: args.jsonData || '',
			success: args.successFunc,
			failure: this.handleError,
			scope: ((args.scope != null)? args.scope : this)
		},this);		
	},
	
	handleError: function(response) {
		var msg = 'Status Error: ' + response.status + ' - ' + response.statusText;
		Ext.Msg.alert('SSP Error', msg);								
	},
	
	/*
	 * Returns the base url of an item in the system.
	 * @itemName - the name of the item to locate.
	 * 	the returned item is returned by name from the apiUrlStore.
	 */
	getItemUrl: function( itemName ){
		var record = this.apiUrlStore.findRecord('name', itemName);
		var url = "";
		if (record != null)
			url = record.get('url');
		return url;
	}
});
Ext.define('Ssp.util.FormRendererUtils',{	
	extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        errorsStore: 'errorsStore'
    },
	config: {
		additionalFieldsKeySeparator: '_'
	},
	
	initComponent: function() {
		
    	// Create a custom validator for
		// mapped field types
		Ext.apply(Ext.form.field.VTypes, {
            //  vtype validation function
            mappedFieldValidator: function(val, field) {
            	var valid = true;
            	var exp = new RegExp(field.validationExpression);
            	var check = Ext.ComponentQuery.query('#'+field.parentId)[0];
            	if (check != null)
            	{
            		if (check.getValue()==true)
            		{
                    	valid = exp.test(val);
            		}
            	}
            	return valid;
            }
        });	

    	// Create a custom validator for
		// dates that are not required
		Ext.apply(Ext.form.field.VTypes, {
            //  vtype validation function
            forceDateValidator: function(val, field) {
            	var valid = true;
            	var exp = new RegExp(/\d{4}\/\d{2}\/\d{2} \d{2}:\d{2}:\d{2}/);
            	var check = field;
            	if (check != null)
            	{
            		if (check.getValue()==true)
            		{
                    	valid = exp.test(val);
            		}
            	}
            	return valid;
            }
        });		
		
		return this.callParent(arguments);
    },
    
    constructor: function(){
    	this.callParent(arguments);
    	return this;
    },
	
    cleanAll: function(view){
    	if (view.items)
    	{
        	if (view.items.length > 0)
        	{
        		view.removeAll(true);
        	}	
    	}
    },
    
    cleanItems: function(view){
    	var i = view.items.length;
    	while (view.items.length > 0)
    	{
    		var item = view.items.getAt(i);
    		if (item != undefined)
    			view.remove(item, true);
    		i=i-1;
    	}
    },	
	
    getProfileFormItems: function(){
		var cleaner = Ext.create( 'Ssp.util.TemplateDataUtil' );
		var applicationFormsStore =  Ext.getStore('ApplicationForms');
        return cleaner.prepareTemplateData(  applicationFormsStore );  	
    },    
    
    /**
     * Builds a dynamic group of radio buttons.
     * @args
     *  @formId 
     *  @radioGroupId
     *  @itemsArr
     *  @selectedItemId
     *  @idFieldName
     *  @selectedIdFieldName
     *  @required
     */
    createRadioButtonGroup: function( args ){
    	var me=this;
    	var formId = args.formId;
    	var radioGroupId = args.radioGroupId;
    	var radioGroupFieldSetId = args.radioGroupFieldSetId;
    	var selectedItemId = args.selectedItemId;
    	var additionalFieldsMap = args.additionalFieldsMap;
    	var itemsArr = args.itemsArr;
    	var idFieldName = args.idFieldName;
    	var selectedIdFieldName = args.selectedIdFieldName;
    	var selectedItem = args.selectedItemsArr[0];
    	var form = Ext.getCmp(formId);
    	var rbGroup = Ext.getCmp(radioGroupId);
		var items = itemsArr;
		var setSelectedItems = false;
		var additionalFieldArr = [];
		var fieldSet = Ext.ComponentQuery.query('#'+radioGroupFieldSetId)[0];
		// Define the radio buttons
		Ext.each(items,function(item,index){
			var itemId = item[idFieldName];
			var comp = {xtype:'mappedradiobutton'};
			comp.id = itemId;
			comp.boxLabel = item.name;
			comp.name = selectedIdFieldName;
			comp.inputValue = item[idFieldName];
			comp.listeners = {
				change: function(comp, oldValue, newValue, eOpts){
					me.appEventsController.getApplication().fireEvent('dynamicCompChange', comp);
				}	
			};

			// retrieve the additional field maps
			additionalFieldsArr = me.getMappedFields( itemId, additionalFieldsMap );		

			// populate the additional fields with selected values
			Ext.each(additionalFieldsArr,function(field,index){
				var names = field.name.split( me.additionalFieldsKeySeparator );
				if ( field.parentId == selectedItemId )
				{
					field.setValue( selectedItem[names[1]] );
				}else{
					field.setValue("");
				}
			},this);

			// hide items that are not selected
			me.hideEmptyFields( additionalFieldsArr );
			
			// add the items to the form
			fieldSet.add( additionalFieldsArr );			
			
			// selected radio button
			if (selectedItemId==item[idFieldName])
			{
				comp.checked = true;
			}

			// add radio button to the radiogroup
			rbGroup.add(comp);
			
		}, this);
    },
    
    /**
     * Provides the ability to build a form with a dynamic
     * set of elements. (checkboxes or radio)
     * 
     * Assumes an items array with: {id: id, name: name, description: description}
     * @args
     *    @mainComponentType = the main component type for the form ('checkbox', 'radio') 
     *    @radioGroupId = the radiobuttongroup id value / optional if building a checkbox based form
     *    @radioGroupFieldSetId = the fieldset in which the radiobutton group is located
     *    @selectedItemId - required value for the 'radio' select button id and optional for 'checkbox' mainComponentType  
     *    @formId = the form to add the items into.
     *    @fieldSetTitle - Provides a fieldset title for the fields
     *    @itemsArr = the array of items to add to the form
     *    @selectedItemsArr = the array of items to select in the form
     *    @idFieldName = the id field in the items array
     *    @selectedIdFieldName - the id field in the selectedItems array. 
     *                        This value can be the same name or a different name than the idFieldName.
     *    @additionalFieldsMap - a series of fields to provide for description related field items
     */
    createForm: function( args ){
    	var me=this;
    	var mainComponentType = args.mainComponentType;
    	var formId = args.formId;
    	var fieldSetTitle = args.fieldSetTitle || null;
    	var itemsArr = args.itemsArr;
    	var selectedItemsArr = args.selectedItemsArr || null;
    	var idFieldName = args.idFieldName;
    	var selectedIdFieldName = args.selectedIdFieldName;
    	var additionalFieldsMap = args.additionalFieldsMap || [];
    	var form = Ext.getCmp(formId);
		var selectedItems = selectedItemsArr;
		var selectedItem;
		var selectedId;
		var setSelectedItems = false;
		var otherId = "";
		var wrapper = {xtype: 'fieldset', padding: 0, border: 0, layout: { type: 'auto' },title: fieldSetTitle};
		var formFields = [];
		var selectedItems = selectedItemsArr || [];
		
		if ( mainComponentType == 'radio' )
		{
			this.createRadioButtonGroup(args);
		}else{
			Ext.each(itemsArr, function(item, index){
				var itemId = item[idFieldName];
				// create the items for the form
				var comp = {xtype: 'mappedcheckbox'};
				comp.id = itemId;
				comp.mapId = itemId;
				comp.boxLabel = item.name;
				comp.name = item.name;
				comp.inputValue = itemId;
				comp.listeners = {
					change: function(comp, oldValue, newValue, eOpts){
						me.appEventsController.getApplication().fireEvent('dynamicCompChange', comp);
					}	
				};

				// loop through additional fields map and add description type fields to the form
				var fieldsArr = [];
				fieldsArr.push( comp );
				
				additionalFieldsArr = me.getMappedFields( itemId, additionalFieldsMap );				
				
				// determine if the component is selected
				for (var s=0; s<selectedItems.length; s++)
				{
					selectedItem = selectedItems[s]
					selectedId = selectedItem[selectedIdFieldName];					
					if (selectedId==item[idFieldName])
					{
						comp.checked = true;					
						
						// populate the additional fields with selected values
						for(var z=0; z<additionalFieldsArr.length; z++)
						{
							var field = additionalFieldsArr[z];
							var names = field.name.split( me.additionalFieldsKeySeparator );
							if ( field.parentId == selectedId )
							{
								field.setValue( selectedItem[names[1]] );
							}else{
								field.setValue("");
							}
						}							

						break;
					}
				}
				
				this.hideEmptyFields(additionalFieldsArr);
				
		    	// add a fieldset if additional fields exist for this item
		    	if (additionalFieldsArr.length>0)
		    	{
			    	var fields = {xtype: 'fieldset', padding: 0, border: 0, layout: { type: 'auto' },title: ''};
			    	Ext.Array.insert(fieldsArr, 1, additionalFieldsArr);
			    	Ext.apply(fields, {items: fieldsArr});
		    	}
				
				// if a fieldset is not defined, then just return a checkbox
				if (fieldsArr.length > 1)
				{
					formFields.push(fields);
				}else{
					formFields.push( comp );
				}
				
			}, me);
			
			form.removeAll();
			Ext.apply(wrapper, {items: formFields});
			form.insert(form.items.length, wrapper);
		}
    },
    
    /**
     * Hides fields with no value set.
     */
    hideEmptyFields: function( arr ){
    	Ext.each(arr, function(item, index){
    		if (item.getValue()=="")
    		{
    			item.hide();
    			Ext.apply(item,{allowBlank:true});
    		}else{
    			item.show();
    			Ext.apply(item,{allowBlank:false});
    		}
    	});
    },
    
    /**
     * Loops over a collection of field maps and 
     * returns an array of fields
     */
    getMappedFields: function( itemId, maps ){
		var additionalFieldsArr = [];
		Ext.each(maps, function(map,index){
			if (itemId==map.parentId)
				additionalFieldsArr.push( this.getFieldFromMap( map ) );   		
    	},this);

    	return additionalFieldsArr;
    },
    
    /**
     * returns a field based on the parameters
     * in the map.
     * @param map object
     *  fieldType
     *  name
     *  parentId
     *  label
     *  labelWidth
     *  
     *  Example:
     *  {parentId: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
	 *   parentName: "other",
	 *   name: "otherDescription", 
	 *   label: "Please Explain", 
     *   fieldType: "textarea",
     *   labelWidth: defaultLabelWidth}
     * 
     * @returns a field created by it's xtype
     */
    getFieldFromMap: function( map ){
    	var field = Ext.createWidget( map.fieldType.toLowerCase() );
    	var valErrorText = 'Not a valid input.';
    	var validationExpression=field.validationExpression;
    	if (map.validationExpression != null)
    		validationExpression=map.validationExpression;
    	if (map.validationErrorMessage != null)
    		valErrorText = map.validationErrorMessage;

    	Ext.apply(field, {
    		parentId: map.parentId, 
    		name: map.parentId + this.additionalFieldsKeySeparator + map.name, 
    		fieldLabel: map.label, 
    		labelWidth: map.labelWidth,
    		anchor: '100%',
    		vtype: 'mappedFieldValidator',
    		vtypeText: valErrorText,
    		validationExpression: validationExpression
    	});    	
    	
    	// This field get's hidden when it's parent
    	// is not selected
    	field.on("hide", 
    		function(comp, eOpts){
    			comp.setValue("");
    	});
    	
    	return field;
    },
    
    /**
     * Determines if the string is using
     * the additionalFields model based on
     * a separator for the field.
     */
    isAdditionalFieldKey: function( key ){
    	var isKey = (key.indexOf( this.additionalFieldsKeySeparator ) != -1);
    	return isKey;
    },

    /**
     * @param obj - a values object from a form
     * @returns the value of the associated item in the mapped field
     */
    getMappedFieldValueFromFormValuesByIdKey: function( obj, id ){
    	var returnVal = "";
    	Ext.iterate(obj, function(key, value) {
			if ( this.isAdditionalFieldKey( key ) )
			{
				keys = key.split( this.additionalFieldsKeySeparator );
				if ( keys[0]==id )
				{
					returnVal = value;
				}
			} 
		},this);
    	return returnVal;
    },    
    
    /**
     * @param obj - a values object from a form
     * @returns array of additionalField objects with name/value pairs
     */
    getAdditionalFieldsArrayFromFormValues: function( obj ){
    	var fields = [];
    	Ext.iterate(obj, function(key, value) {
			if ( this.isAdditionalFieldKey( key ) )
			{
				keys = key.split( this.additionalFieldsKeySeparator );
				fields.push( {id: keys[0], name: keys[1], value: value} );
			} 
		},this);
    	return fields;
    },
    
    /**
     * @param obj - a values object from a form
     * @returns the object clean of any keys that the signature
     *          of an additional form field. ie. a description field
     *          for one of the items in the form.
     */
    dropAdditionalFieldsKeysFromFormValues: function( obj ){
    	Ext.iterate(obj, function(key, value) {
			if ( this.isAdditionalFieldKey( key ) )
			{
				delete obj[key];
			} 
		},this);
    	return obj;
    },
    
    /**
     * Method to create a json transfer object from
     * the selected values in a form.
     * This method is specifically for use with the
     * AdditionalFieldMappings related object type,
     * for dynamic check and radio objects.
     * @idKey = the supplied name of the key field in the transfer object
     * @formValues = an object containing key value pairs from the form
     * @personId = a related key value for the object
     * 
     * @returns = an array of transfer objects for the selected items in the form
     */
	createTransferObjectsFromSelectedValues: function(idKey, formValues, personId){
		var transferObjects = [];	
		var formUtils = this.formUtils;
		var additionalFieldArr = [];
		
		// Loop through all the values in the form
		// find the objects with an '_' character and save them to a new array
		additionalFieldsArr = this.getAdditionalFieldsArrayFromFormValues( formValues );

		// delete keys that match an additional fields signature, since they will be used to determine mapped description fields
		// and not actual selected items
		// compare the values in each of the keys against the selected items to create 
		// a series of personEducationLevel objects to save
		
		formValues = this.dropAdditionalFieldsKeysFromFormValues( formValues );
		
		// Loop through all the values in the form and create
		// transfer objects for them with an id field name matching
		// the supplied idKey. For each transfer object, loop over the
		// the additionalFields and match the id's to determine
		// additional fields that should be supplied as descriptions
		// against the mapped fields
		Ext.iterate(formValues, function(key, value) {
			var tObj = new Object();
			tObj[idKey]=value;
			tObj['personId'] = personId;
			Ext.Array.each( additionalFieldsArr, function(field, index){
				if (value==field.id)
					tObj[field.name]=field.value;
			}, this);
			transferObjects.push( tObj );
		});
		
		return transferObjects;
	},    
	
	/**
	 * Allows an additional field to be hidden until
	 * an item is selected that is associated with the 
	 * hidden field.
	 */
    onMapFieldHidden: function( comp, eOpts){
    	comp.setValue("");
    },
	
	/**
	 * @params
	 * @arrayToSort - the array to sort props on
	 * @fieldName - the field name to sort on
	 * 
	 * @returns - returns the sorted array
	 */
    alphaSortByField: function( arrayToSort, fieldName ){
    	return Ext.Array.sort(arrayToSort, function(a, b){
    		 var nameA=a[fieldName].toLowerCase(), nameB=b[fieldName].toLowerCase()
    		 if (nameA < nameB) //sort string ascending
    		  return -1
    		 if (nameA > nameB)
    		  return 1
    		 return 0 //default return value (no sorting)
    		});
    },
    
    /**
     * @params
     * @values - an object of name/value pairs
     * @modelType - the model type to set and return
     * 
     * @returns - a typed model object
     */
    getSelectedValuesAsTransferObject: function( values, modelType ){
		var selectedItems = [];
		for ( prop in values )
		{
			var obj = Ext.create(modelType,{id: values[prop]} );
			selectedItems.push( obj.data );
		}
		return selectedItems;
    },
 
    /**
     * @params
     * @values - an object of name/value pairs
     * 
     * @returns - an array of selected ids
     */
    getSelectedIdsAsArray: function( values ){
		var selectedIds = [];
		for ( prop in values )
		{
			selectedIds.push( {id: values[prop]} );
		}
		return selectedIds;
    },    
    
 	/**
	 * load a display into a container in the interface.
	 * @containerAlias = alias/xtype of the container into which to load the display.
	 * @compAlias = alias/xtype of the component that is loaded
	 * @removeExisting = boolean: true=remove existing items before load, 
	 *                            false=keep existing items
	 * @args = optional params space
	 */	
	loadDisplay: function( containerAlias, compAlias, removeExisting, args ) {	
		var comp = null;
		var store = null;
		var view = Ext.ComponentQuery.query(containerAlias.toLowerCase())[0];
		
		if (view.items.length > 0 && removeExisting==true)
			view.removeAll();

		// create the new widget
		comp =  Ext.widget(compAlias.toLowerCase(), args);	
		
		// add to the container
		view.add( comp );
		
		return comp;
	},

    /**
     * Compares a value against a record in a store, based on a provided
     * field for the comparison, as well as, criteria to find the record
     * in the store to use to compare against. If the values match
     * then the associated field will be hidden in the interface. Otherwise,
     * the field is shown.
     * @elementName - the field to hide
     * @compareValue - the value to compare against
     * @compareFieldName - the field in a record to compare against. For example: 'id'
     * @store - the store in which to find a value to compare against
     * @recordField - the name of the field in the store to find a record
     * @recordValue - the value of the field in the store to find a record against
     */
    showHideFieldByStoreValue: function( elementName, compareValue, compareFieldName, store, recordField, recordValue ){
		var queryValue = '#'+elementName;
    	var field = Ext.ComponentQuery.query(queryValue)[0];
		var record = store.findRecord(recordField, recordValue);
		if ( compareValue==record.get( compareFieldName ) )
		{
			field.show();
		}else{
			field.hide();
		}
    },
	
    /**
     * Method to allow a gridPanel to be reconfigured to display
     * a new set of columns or a new store of data.
     */
    reconfigureGridPanel: function(gridPanel, store, columns) {
    	var me = gridPanel,
            headerCt = me.headerCt;

        if (me.lockable) {
            me.reconfigureLockable(store, columns);
        } else {
            if (columns) {
                headerCt.suspendLayout = true;
                headerCt.removeAll();
                headerCt.add(columns);
            }
            if (store) {
                store = Ext.StoreManager.lookup(store);
                me.down('pagingtoolbar').bindStore(store);
                me.bindStore(store);        
            } else {
                me.getView().refresh();
            }
            if (columns) {
                headerCt.suspendLayout = false;
                me.forceComponentLayout();
            }
        }
        me.fireEvent('reconfigure', me);
    },
    
	/**
	 * Determines if one or more forms have invalid fields
	 * and ensures that the first invalid item is visible
	 * in the interface.
	 * 
	 * Returns a result object with an array of invalid fields
	 * and a valid flag to determine if the form is valid
	 * 
	 */
	validateForms: function( forms ){
		var me=this;
		var form;
		var result = {fields:[],valid:true};			
		Ext.Array.each(forms, function(form, index){
			var f;
			if (form.isValid()==false)
			{
				// collect all invalid fields
				// from the form
				var invalidFields = me.findInvalidFields( form );
				if (invalidFields.items.length>0)
				{
					Ext.Array.each(invalidFields.items,function(field,index){
						result.fields.push( me.cleanInvalidField( field ) );
					},me);
				}
				
				// find the first invalid field and display it
				if (result.valid==true)
				{
					f = form.findInvalid()[0];
					if (f) 
					{
						f.ensureVisible();
						// flag the form invalid
						result.valid=false;
					}
				}
			}
		});
		
		return result;	
	},
	
	/**
	 * Method to collect all of the invalid
	 * fields from a form.
	 */
	findInvalidFields: function(form)
	{
		return form.getFields().filterBy(function(field) {
	        var result=false;
			if (!field.validate())
	        {
				result = true;
	        }
			return result;
	    });
	},

	/**
	 * Clean a fields label of span tags used for
	 * outputing error asterisks on validatable fields
	 */
	cleanInvalidField: function( field )
	{
		return new Ssp.model.FieldError({
			label: field.fieldLabel.replace(Ssp.util.Constants.REQUIRED_ASTERISK_DISPLAY,"","gi"),
			errorMessage: field.activeErrors.join('. ')
		});
	},
	
	displayErrors: function( fields ){
		this.errorsStore.loadData( fields );
		Ext.create('Ssp.view.ErrorWindow', {
		    height: 300,
		    width: 500
		}).show();
	}
});


Ext.define('Ssp.util.ColumnRendererUtils',{
	extend: 'Ext.Component',

	initComponent: function() {
		return this.callParent( arguments );
    },

	renderFriendlyBoolean: function(val, metaData, record) {
		return ((val==true)?'Yes':'No');
	},    
    
	renderTaskName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('name').toUpperCase() + '</p>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},

	renderTaskDueDate: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + Ext.util.Format.date( record.get('dueDate') ,'m/d/Y') + '</p>';
		strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '<br/>' + record.getCreatedByPersonName().toUpperCase() + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},	
	
	renderGoalName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('name').toUpperCase() + '</p>';
		strHtml += '<p>' + record.get('description') + '</p>';
		strHtml += '</div>';
	    return strHtml;	
	},	
	
	renderConfidentialityLevelName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('confidentialityLevel').name.toUpperCase() + '</p>';
		strHtml += '</div>';
	    return strHtml;		
	},

	renderCreatedByDate: function(val, metaData, record) {
	    return Ext.util.Format.date( record.get('createdDate'),'m/d/Y');		
	},	

	renderCreatedBy: function(val, metaData, record) {
	    return record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase();		
	},	
	
	renderCreatedByDateAndName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('createdBy').firstName.toUpperCase() + ' ' + record.get('createdBy').lastName.toUpperCase() + '</p>';
        strHtml += '<p>' + Ext.util.Format.date( record.get('createdDate'),'m/d/Y') + '</p>';
        strHtml += '</div>';
	    return strHtml;		
	},
 
	renderJournalSourceName: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('journalSource').name.toUpperCase() + '</p>';
         strHtml += '</div>';
	    return strHtml;		
	},	
	
	renderPhotoIcon: function(val) {
	    return '<img src="' + val + '">';
	},
	
	renderStudentDetails: function(val, metaData, record) {
		var strHtml = '<div>';
        strHtml += '<p>' + record.getFullName() + '</p>';
        strHtml += '<p>' + record.get('schoolId') + '</p>';
        strHtml += '</div>';
	    return strHtml;
	},
	
	renderAddToolIcon: function(value,meta,record,rowIx,ColIx, store) {
	    return (record.get("active")==false)?
	                'addToolIcon':
	                'hideAddToolIcon';
	},

	renderErrorMessage: function(val, metaData, record) {
		var strHtml = '<div style="white-space:normal !important;">';
        strHtml += '<p>' + record.get('errorMessage') + '</p>';
		strHtml += '</div>';
	    return strHtml;
	},
	
	/**
	 * This method is used to return an object with id values
	 * an array format expected by the ExtJS multiSelect or itemSelect
	 * components.
	 * 
	 * Translates: 
	 * [{"id":"1"},{"id":"2"},{"id":"3"}]
	 * 
	 * Into:
	 * ["1","2","3"]
	 */
	getSelectedIdsForMultiSelect: function( arr ){
		var selectedIds = [];
		Ext.each(arr,function(item,index){
			selectedIds.push(item["id"]);
		});
		return selectedIds;
	}
});
Ext.define('Ssp.util.TreeRendererUtils',{	
	extend: 'Ext.Component',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	treeStore: 'treeStore'
    },

	initComponent: function() {
		return this.callParent(arguments);
    },
 
    /*
     * Find a child in the tree.
     * @id - the object id of the node to find
     */
    getNodeById: function( id ){
    	return this.treeStore.getNodeById( id );
    },    
    
    /*
     * Clears the treeStore instance, so new folders can be assigned.
     */
    clearRootCategories: function(){
    	// clear tree
    	this.treeStore.setRootNode({
	        text: 'root',
	        expanded: true,
	        children: []
	    });
    },
    
    /*
     * Appends children to the tree under a specified parentNode
     * @parentNode - the node at which to append
     * @children - the child nodes to append
     *    - Should be in the format of nodes as returned by the createNodes methods of this class. 
     */
    appendChildren: function(nodeToAppendTo, children) {
    	// Append to the root if no node is defined
    	if (nodeToAppendTo == null)
    	{
    		nodeToAppendTo = this.treeStore.getRootNode();
    	}else
    	{
    		// if not using the root
    		// then clean the node before append
    		nodeToAppendTo.removeAll();
    	}
    	
    	// only append if their are children
    	if (children.length > 0)
    	{
    		nodeToAppendTo.appendChild( children );
    	}
    },
    
    getNameFromNodeId: function( value ) {
    	var arr = value.split('_');
    	return arr[1];
    },
 
    getIdFromNodeId: function( value ) {
    	var arr = value.split('_');
    	return arr[0];
    },    
    
    /*
     * @records - Array of records in json format.
     * @isLeaf - Determines if the returned nodes array contains branch or leaf elements
     *             The default is to return branch elements.  
     */
    createNodes: function(records, isLeaf){
    	var nodes = [];
    	Ext.each(records, function(name, index) {
    		nodes.push({
    	    	        text: records[index].get('name'),
    	    	        id: records[index].get('id'),
    	    	        leaf: isLeaf || false
    	    	      });
    	});
    	return nodes;
    },
    
    /*
     * @records - Array of records in json format.
     * @isLeaf - Determines if the returned nodes array contains branch or leaf elements
     *             The default is to return branch elements.
     * @nodeType - An optional description for the node type. Used to identify the type of node for drag
     *             and drop functionality. For example with a nodeType set to 'challenge', the node will
     *             be created with an id such as 12345_challenge.
     * @expanded - whether or not a branch should load expanded   
     */
    createNodesFromJson: function(records, isLeaf, nodeType, enableCheckSelection, expanded, expandable){
    	var nodeIdentifier = "";
    	var enableCheckSelection = enableCheckSelection;
    	var nodes = [];
    	var nodeName = nodeType || "";
    	if (nodeName != "")
    		nodeIdentifier = '_' + nodeName;
    	Ext.each(records, function(name, index) {
    		var nodeData = {
        	        text: records[index].name,
        	        id: records[index].id + nodeIdentifier,
        	        leaf: isLeaf || false,
        	        expanded: expanded,
        	        expandable: expandable
        	      };
        	
        	if (enableCheckSelection && isLeaf==true)
        		nodeData['checked']=false;
        	
    		nodes.push( nodeData );
    	});
    	
    	return nodes;
    },   
 
    /*
     * Retrieves items to populate the tree store.
     * @args.url - The url for the request to get items
	 * @args.nodeType - An optional name to append to the id to determine the name of node
	 * @args.isLeaf - Boolean, whether or not the items are branch or leaf nodes
	 * @args.nodeToAppendTo = the rootNode to append the items
	 * @args.enableCheckedItems = boolean to determine if a checkbox is created for leaf items in the tree
     * @args.expanded - boolean to determine whether a branch should appear as expanded
     * @args.expandable - boolean to determine whether or not the branch can be expanded or collapsed
     * @args.
     */
    getItems: function( treeRequest ){
    	var me=this;
    	var destroyBeforeAppend = treeRequest.get('destroyBeforeAppend');
    	var url = treeRequest.get('url');
    	var isLeaf = treeRequest.get('isLeaf');
    	var enableCheckSelection = treeRequest.get('enableCheckedItems');
    	var nodeToAppendTo = treeRequest.get('nodeToAppendTo');
    	var nodeType = treeRequest.get('nodeType');
    	var expanded = treeRequest.get('expanded');
    	var expandable = treeRequest.get('expandable');
    	var callbackFunc = treeRequest.get('callbackFunc');
    	var callbackScope = treeRequest.get('callbackScope');
    	// retrieve items
		this.apiProperties.makeRequest({
			url: this.apiProperties.createUrl( url ),
			method: 'GET',
			jsonData: '',
			successFunc: function(response,view){
		    	var r = Ext.decode(response.responseText);
		    	var records = r.rows;
		    	var nodes = [];
		    	if (records.length > 0)
		    	{
		    		nodes = me.createNodesFromJson(records, isLeaf, nodeType, enableCheckSelection, expanded, expandable);
		    		me.appendChildren( nodeToAppendTo, nodes);
		    	}else{
		    		me.appendChildren( nodeToAppendTo, []);
		    	}
		    	
	    		if (callbackFunc != null && callbackFunc != "")
	    			callbackFunc( callbackScope );
			}
		});
    },    
    
});
Ext.define('Ssp.util.Constants',{
	extend: 'Ext.Component',
    statics: {
    	// EDUCATION GOALS
        EDUCATION_GOAL_OTHER_ID: '78b54da7-fb19-4092-bb44-f60485678d6b',
        EDUCATION_GOAL_MILITARY_ID: '6c466885-d3f8-44d1-a301-62d6fe2d3553',
        EDUCATION_GOAL_BACHELORS_DEGREE_ID: 'efeb5536-d634-4b79-80bc-1e1041dcd3ff',
        
        // EDUCATION LEVELS
        EDUCATION_LEVEL_NO_DIPLOMA_GED_ID: '5d967ba0-e086-4426-85d5-29bc86da9295',
        EDUCATION_LEVEL_GED_ID: '710add1c-7b53-4cbe-86cb-8d7c5837d68b',
        EDUCATION_LEVEL_HIGH_SCHOOL_GRADUATION_ID: 'f4780d23-fd8a-4758-b772-18606dca32f0',
        EDUCATION_LEVEL_SOME_COLLEGE_CREDITS_ID: 'c5111182-9e2f-4252-bb61-d2cfa9700af7',
        EDUCATION_LEVEL_OTHER_ID: '247165ae-3db4-4679-ac95-ca96488c3b27',
        
        // FUNDING SOURCES
        FUNDING_SOURCE_OTHER_ID: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
        
        // CHALLENGES
        CHALLENGE_OTHER_ID: '365e8c95-f356-4f1f-8d79-4771ae8b0291',
    
        // EARLY ALERT OUTCOME
        OTHER_EARLY_ALERT_OUTCOME_ID: '0a080114-3799-1bf5-8137-9a778e200004',
        
        GRID_ITEM_DELETE_ICON_PATH: '/ssp/images/delete-icon.png',
        GRID_ITEM_EDIT_ICON_PATH: '/ssp/images/edit-icon.jpg',
        GRID_ITEM_CLOSE_ICON_PATH: '/ssp/images/close-icon.jpg',
        
        REQUIRED_ASTERISK_DISPLAY: '<span style="color: rgb(255, 0, 0); padding-left: 2px;">*</span>'
    },

	initComponent: function() {
		return this.callParent( arguments );
    }
});
Ext.define('Ssp.store.JournalEntryDetails', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.journal.JournalEntryDetail',
	groupField: 'group'
});
Ext.define('Ssp.store.admin.AdminTreeMenus',{
	extend: 'Ext.data.TreeStore',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        columnRendererUtils: 'columnRendererUtils'
    },
	autoLoad: false,
    constructor: function(){
    	var items = {
    	    	text: 'Administrative Tools',
    	    	title: 'Administrative Tools',
    	    	form: '',
    	        expanded: true,
    	        children: [
    						{
    							text: 'Beta',
    							title: 'Beta',
    							form: '',
    							expanded: false,
    							children: [
    									{
    										text: 'Program Status Change Reasons',
    										title: 'Program Status Change Reasons',
    										store: 'programStatusChangeReasons',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },{
    										text: 'Referral Sources',
    										title: 'Referral Sources',
    										store: 'referralSources',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },{
    										text: 'Special Service Groups',
    										title: 'Special Service Groups',
    										store: 'specialServiceGroups',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    									},
    									{
    										text: 'Service Reasons',
    										title: 'Service Reasons',
    										store: 'serviceReasons',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },{
    										text: 'Student Types',
    										title: 'Student Types',
    										store: 'studentTypes',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true,
    										columns: [
    						    		                { header: 'Name',  
    						    		                  dataIndex: 'name',
    						    		                  required: true,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: .25 },
    						    		                { header: 'Description',
    						    		                  required: false,
    						    		                  dataIndex: 'description',
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 1},
    						      		                { header: 'Require Initial Appointment',
    						    		                  required: true,
    						      		                  dataIndex: 'requireInitialAppointment', 
    						      		                  flex: .25,
    						      		                  renderer: this.columnRendererUtils.renderFriendlyBoolean,
    						      		                  field: {
    						      		                      xtype: 'checkbox'
    						      		                  }
    						    		                }
    						    		           ]
    									},{
    								    	text: 'Campuses',
    										title: 'Campuses',
    										store: '',
    										form: 'CampusAdmin',
    										leaf: true
    									}
    							]
    						},{
    							text: 'Student Intake',
    							title: 'Student Intake',
    							form: '',
    							expanded: false,
    							children: [
    									{
    										text: 'Child Care Arrangements',
    										title: 'Child Care Arrangements',
    										store: 'childCareArrangements',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    									},
    									{
    										text: 'Citizenships',
    										title: 'Citizenships',
    										store: 'citizenships',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    },
    								    {
    								    	text: 'Confidentiality Disclosure Agreement',
    								    	title: 'Confidentiality Disclosure Agreement',
    								    	store: '',
    								        form: 'ConfidentialityDisclosureAgreementAdmin',
    										leaf: true
    								    },
    								    {
    								    	text: 'Education Goals',
    								    	title: 'Education Goals',
    								    	store: 'educationGoals',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Education Levels',
    								    	title: 'Education Levels',
    								    	store: 'educationLevels',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Ethnicities',
    								    	title: 'Ethnicities',
    								    	store: 'ethnicities',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Funding Sources',
    								    	title: 'Funding Sources',
    								    	store: 'fundingSources',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Marital Statuses',
    								    	title: 'Marital Statuses',
    								    	store: 'maritalStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Student Statuses',
    								    	title: 'Student Statuses',
    								    	store: 'studentStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    								    ,{
    								    	text: 'Veteran Statuses',
    								    	title: 'Veteran Statuses',
    								    	store: 'veteranStatuses',
    								        form: 'AbstractReferenceAdmin',
    										leaf: true
    								    }
    							]
    						},{
    							text: 'Counseling Reference Guide',
    							title: 'Counseling Reference Guide',
    							form: '',
    							expanded: false,
    							children: [{
											text: 'Categories',
											title: 'Categories',
											store: 'challengeCategories',
											form: 'AbstractReferenceAdmin',
											leaf: true
										},{
    										text: 'Challenges',
    										title: 'Challenges',
    										store: '',
    										form: 'ChallengeAdmin',
    										leaf: true
    									},{
    										text: 'Referrals',
    										title: 'Referrals',
    										store: '',
    										form: 'ChallengeReferralAdmin',
    										leaf: true
    									}]
    						},{
    							text: 'Security',
    							title: 'Security',
    							form: '',
    							expanded: false,
    							children: [{text: 'Confidentiality Levels',
    										title: 'Confidentiality Levels',
    										store: 'confidentialityLevels',
    										form: 'AbstractReferenceAdmin',
    										leaf: true,
    										columns: [{ header: 'Name',  
    						    		                  dataIndex: 'name',
    						    		                  required: true,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 50 },
    						    		                { header: 'Description',
    						    		                  dataIndex: 'description', 
    						    		                  flex: 50,
    						    		                  field: {
    						    		                      xtype: 'textfield'
    						    		                  },
    						    		                  flex: 50 },
    						      		                { header: 'Acronym',
    						      		                  dataIndex: 'acronym',
    						      		                  required: true,
    						      		                  flex: 50,
    						      		                  field: {
    						      		                      xtype: 'textfield'
    						      		                  }
    						    		                }]
    									}]
    						},{
    							text: 'Early Alert',
    							title: 'Early Alert',
    							form: '',
    							expanded: false,
    							children: [{
									text: 'Outcomes',
									title: 'Outcomes',
									store: 'earlyAlertOutcomes',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Outreaches',
									title: 'Outreaches',
									store: 'earlyAlertOutreaches',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Reasons',
									title: 'Reasons',
									store: 'earlyAlertReasons',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Referrals',
									title: 'Referrals',
									store: 'earlyAlertReferrals',
							        form: 'AbstractReferenceAdmin',
									leaf: true,
									columns: [
					    		                { header: 'Name',  
					    		                  dataIndex: 'name',
					    		                  required: true,
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: 50 },
					    		                { header: 'Description',
					    		                  required: false,
					    		                  dataIndex: 'description', 
					    		                  flex: 50,
					    		                  field: {
					    		                      xtype: 'textfield'
					    		                  },
					    		                  flex: 50 },
					      		                { header: 'Acronym',
					    		                  required: true,
					      		                  dataIndex: 'acronym', 
					      		                  flex: 50,
					      		                  field: {
					      		                      xtype: 'textfield'
					      		                  }
					    		                }
					    		           ]
								},{
									text: 'Suggestions',
									title: 'Suggestions',
									store: 'earlyAlertSuggestions',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								}]
    						},{
    							text: 'Journal',
    							title: 'Journal',
    							form: '',
    							expanded: false,
    							children: [{
									text: 'Sources',
									title: 'Sources',
									store: 'journalSources',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								},{
									text: 'Details',
									title: 'Details',
									store: '',
							        form: 'JournalStepDetailAdmin',
									leaf: true
								},{
									text: 'Steps',
									title: 'Steps',
									store: '',
							        form: 'JournalStepAdmin',
									leaf: true
								},{
									text: 'Tracks',
									title: 'Tracks',
									store: 'journalTracks',
							        form: 'AbstractReferenceAdmin',
									leaf: true
								}]
    						}
    	                   
    	        ]

    	    };
    	
    	Ext.apply(this,{
    		root: items,
    		folderSort: true,
    		sorters: [{
    		    property: 'text',
    		    direction: 'ASC'
    		}]
    	});
		return this.callParent(arguments);
    }


});
Ext.define('Ssp.controller.ApplicationEventsController', {
	extend: 'Ext.Base',
	config: {
		app: null
	},
	
	constructor: function(config){
		this.initConfig(config);
		return this.callParent(arguments);
	},
	
	setApplication: function(app){
		this.app = app;
	},	
	
	getApplication: function(){
		return this.app;
	},
	
	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	assignEvent: function( args ){
		if ( !this.getApplication().hasListener(args.eventName) )
		{
			this.getApplication().addListener(args.eventName, args.callBackFunc, args.scope);
		}
	},

	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	removeEvent: function( args ){
		if ( this.getApplication().hasListener( args.eventName ))
		{
			this.getApplication().removeListener( args.eventName, args.callBackFunc, args.scope );
		}
	}
});
Ext.define('Ssp.model.AbstractBase', {
    extend: 'Ext.data.Model',
    fields: [{name: 'id', type: 'string'},
             {name: 'createdBy',
              convert: function(value, record) {
		            var obj  = {id:value.id || '',
		                        firstName: value.firstName || '',
		                        lastName: value.lastName || ''};	
		            return obj;
		      }
             },
             {name: 'modifiedBy',
              convert: function(value, record) {
 		            var obj  = {id:value.id || '',
 		                        firstName: value.firstName || '',
 		                        lastName: value.lastName || ''};	
 		            return obj;
 		      }
             }
             ,{name: 'createdDate', type: 'date', dateFormat: 'time'}
             ,{name: 'objectStatus', type: 'string'}
             /*,
             {name: 'modifiedDate', type: 'date', dateFormat: 'time'},
             */],
    
	populateFromGenericObject: function( record ){
		if (record != null)
		{
			for (fieldName in this.data)
	    	{
				if (record[fieldName])
	    		{
	    			this.set(fieldName, record[fieldName] );
	    		}
	    	}
		}
    },
    
    getCreatedByPersonName: function(){
    	return this.get('createdBy').firstName + ' ' + this.get('createdBy').lastName;
    },

});

Ext.define('Ssp.model.Coach', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'firstName',type:'string'},
             {name:'middleInitial',type:'string'},
             {name:'lastName',type:'string'},
             {
                 name: 'fullName',
                 convert: function(value, record) {
                     return record.get('firstName') + ' '+ record.get('lastName');
                 }
             },
             {name:'department',type:'string', defaultValue:'Web Systems'},
             {name: 'workPhone', type:'string'},
             {name: 'primaryEmailAddress', type:'string'},
             {name: 'office', type:'string', defaultValue:'13023S'}]
});
Ext.define('Ssp.model.reference.Gender', {
    extend: 'Ext.data.Model',
    fields: ['code',
             'title']
});
Ext.define('Ssp.model.reference.State', {
    extend: 'Ext.data.Model',
    fields: ['code',
             'title']
});
Ext.define('Ssp.model.Tool', {
    extend: 'Ext.data.Model',
    fields: [{name:'name',type:'string'},
             {name:'toolType',type:'string'},
             {name:'active',type:'boolean'},
             {name:'group',type:'string'}]
});
Ext.define('Ssp.model.Person', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'photoUrl', type: 'string'},
             {name: 'schoolId', type: 'string'},
    		 {name: 'firstName', type: 'string'},
             {name: 'middleInitial', type: 'string'},
    		 {name: 'lastName', type: 'string'},
             {name: 'homePhone', type: 'string'},
    		 {name: 'cellPhone', type: 'string'},
             {name: 'workPhone', type: 'string'},
    		 {name: 'addressLine1', type: 'string'},
             {name: 'addressLine2', type: 'string'},
    		 {name: 'city', type: 'string'},
             {name: 'state', type: 'string'},
    		 {name: 'zipCode', type: 'string'},
             {name: 'primaryEmailAddress', type: 'string'},
    		 {name: 'secondaryEmailAddress', type: 'string'},
             {name: 'birthDate', type: 'date', dateFormat: 'time'},
    		 {name: 'username', type: 'string'},
             {name: 'userId', type: 'string'},
    		 {name: 'enabled', type: 'boolean'},
             {name: 'coachId', type: 'string'},
    		 {name: 'strengths', type: 'string'},
    		 {name: 'studentTypeId',type:'string'},
    		 {name: 'abilityToBenefit', type: 'boolean'},
    		 {name: 'anticipatedStartTerm', type: 'string'},
    		 {name: 'anticipatedStartYear', type: 'string'},
    		 {name: 'studentIntakeRequestDate', type: 'time'},
    		 {name: 'specialServiceGroups', type: 'auto'},
    		 {name: 'referralSources', type: 'auto'},
    		 {name: 'serviceReasons', type: 'auto'},
    		 {name:'permissions', type:'auto', defaultValue: null},
    		 {name:'confidentialityLevels', type:'auto', defaultValue: null}],
    
    		 //'programStatus',
    		 //'registrationStatus',
    		 //'paymentStatus',
    		 //'cumGPA',
    		 //'academicPrograms'
    		 
    		 
    getFullName: function(){ 
    	var firstName = this.get('firstName') || "";
    	var middleInitial = this.get('middleInitial') || "";
    	var lastName = this.get('lastName') || "";
    	return firstName + " " + middleInitial + " " + lastName;
    },
    
    getFormattedBirthDate: function(){
    	return Ext.util.Format.date( this.get('birthDate'),'m/d/Y');
    },
    
    getFormattedStudentIntakeRequestDate: function(){
    	return Ext.util.Format.date( this.get('studentIntakeRequestDate'),'m/d/Y');   	
    },

    /**
     * Determines if a user has access to a provided array of permissions.
     * 
     * @arguments
     *  arrPermissions - an array of permissions to test against the granted permissions for this user.
     *  
     *  return true if all of the permissionsToTest exist in the user's record
     */
    hasPermissions: function( arrPermissions ){
   	   return Ext.Array.every(arrPermissions,function(permission){
   		   return this.hasPermission( permission );
   	   },this);
    },
    
    /**
     * Determines if a user has access to the provided permission.
     * @arguments
     *  - permission - a permission
     *  to test against the granted permissions for this user.
     *  
     *  return true if the permission exists in the user's record
     */
    hasPermission: function( permission ){
   	 return Ext.Array.contains( this.get('permissions'), permission );
    }    
});
Ext.define('Ssp.model.PersonAppointment', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'startDate', type: 'date', dateFormat: 'time'},
             {name: 'endDate', type: 'date', dateFormat: 'time'}]
});
Ext.define('Ssp.model.Appointment', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'appointmentDate', type: 'date', dateFormat: 'time'},
             {name: 'startTime', type: 'date', dateFormat: 'time'},
             {name: 'endTime', type: 'date', dateFormat: 'time'}],

    getStartDate: function(){
		var me=this;
    	var startDate = new Date( me.get('appointmentDate') );
		startDate.setHours( me.get('startTime').getHours() );
		startDate.setMinutes( me.get('startTime').getMinutes() );
		return startDate;
    },
    
    getEndDate: function(){
    	var me=this;
    	var endDate = new Date( me.get('appointmentDate') );
		endDate.setHours( me.get('endTime').getHours() );
		endDate.setMinutes( me.get('endTime').getMinutes() );
		return endDate;    	
    }
});
Ext.define('Ssp.model.PersonGoal', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name: 'confidentialityLevel', type:'auto'}]
});
Ext.define('Ssp.model.PersonDocument', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'note',type:'string'},
             {name: 'confidentialityLevel',type: 'auto'}]
});
Ext.define('Ssp.model.tool.studentintake.PersonDemographics', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
             {name: 'coachId', type: 'string'},
             {name: 'maritalStatusId', type: 'string'},
             {name: 'citizenshipId', type: 'string'},
             {name: 'ethnicityId', type: 'string'},
             {name: 'veteranStatusId', type: 'string'},
             {name: 'abilityToBenefit', type: 'boolean'},
             {name: 'primaryCaregiver', type: 'boolean'},
             {name: 'childCareNeeded', type: 'boolean'},
             {name: 'employed', type: 'boolean'},
             {name: 'numberOfChildren', type: 'int'},
             {name: 'anticipatedStartTerm', type: 'string'},
             {name: 'anticipatedStartYear', type: 'int'}, 		 
             {name: 'countryOfResidence', type: 'string'},
             {name: 'paymentStatus', type: 'string'},
             {name: 'gender', type: 'string'},
             {name: 'countryOfCitizenship', type: 'string'},
             {name: 'childAges', type: 'string'},
             {name: 'placeOfEmployment', type: 'string'},
             {name: 'shift', type: 'string'},
             {name: 'wage', type: 'string'},
             {name: 'totalHoursWorkedPerWeek', type: 'int'},
             {name: 'local', type: 'string'},
             {name: 'childCareArrangementId', type: 'string'}]
});
Ext.define('Ssp.model.tool.studentintake.PersonEducationGoal', {
	extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
    		 {name: 'educationGoalId', type: 'string'},
    		 {name: 'description', type: 'string'},
    		 {name: 'plannedOccupation', type: 'string'},
    		 {name: 'howSureAboutMajor', type: 'int'}]
});
Ext.define('Ssp.model.tool.studentintake.PersonEducationPlan', {
	extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'personId', type: 'string'},
             {name: 'studentStatusId', type: 'string'},
             {name: 'newOrientationComplete', type: 'boolean'},
             {name: 'registeredForClasses', type: 'boolean'},
             {name: 'collegeDegreeForParents', type: 'boolean'},
             {name: 'specialNeeds', type: 'boolean'},
             {name: 'gradeTypicallyEarned', type: 'string'}]
});
Ext.define('Ssp.model.tool.actionplan.Task', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'description',type:'string'},
             {name:'dueDate', type:'date', dateFormat:'time'},
             {name:'reminderSentDate', type:'date', dateFormat:'time'},
             {name: 'confidentialityLevel',
                 convert: function(value, record) {
                	 var obj  = {id:'',name: ''}
                	 if (value != null)
                	 {
                		 obj.id  = value.id;
                		 obj.name = value.name;
                	 }	
   		            return obj;
                 }
   		      },
             {name:'deletable',type:'boolean'},
             //{name:'closableByStudent',type:'boolean'},
             {name:'completed',type:'boolean'},
             {name:'completedDate', type:'date', dateFormat:'time'},
             {name:'challengeId',type:'string'},
             {name:'type',type:'string'},
             {name:'personId',type:'string'}]
});
Ext.define('Ssp.model.tool.earlyalert.PersonEarlyAlert', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'courseName',type:'string'},
             {name:'courseTitle',type:'string'},
             {name:'emailCC',type:'string'},
             {name:'campusId',type:'string'},
             {name:'earlyAlertReasonIds',type:'auto'},
             {name:'earlyAlertReasonOtherDescription',type:'string'},
             {name:'earlyAlertSuggestionIds',type:'auto'},
             {name:'earlyAlertSuggestionOtherDescription',type:'string'},
             {name:'comment',type:'string'},
             {name:'closedDate',type:'time'},
             {name:'closedById',type:'string'}]
});
Ext.define('Ssp.model.tool.earlyalert.EarlyAlertResponse', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'name',type:'string'},
             {name:'earlyAlertId',type:'string'},
             {name:'earlyAlertOutcomeId',type:'string'},
             {name:'earlyAlertOutcomeOtherDescription',type:'string'},
             {name:'earlyAlertReferralIds',type:'auto'},
             {name:'earlyAlertOutreachIds',type:'auto'},
             {name:'comment',type:'string'}]
});
Ext.define('Ssp.model.tool.journal.JournalEntry', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'comment',type:'string'},
             {name: 'confidentialityLevel', type:'auto'},
			 {name:'journalSource', type:'auto'},
			 {name:'journalTrack', type:'auto'},
			 {name:'journalEntryDetails',type:'auto',defaultValue:[]}],
	
	getConfidentialityLevelId: function(){
		return this.get('confidentialityLevel').id;
	},
			 
	addJournalDetail: function( step, detail){
		var stepExists = false;
		Ext.Array.each(this.get("journalEntryDetails"),function(item,index){
			if (item.journalStep.id == step.id){
				// step exists. add the journal detail
				stepExists=true;
				item.journalStepDetail.push(detail);
			}
		});
		if (stepExists==false){
			this.addJournalStep( step, detail );
		}
	},
	
	removeJournalDetail: function( step, detail ){
		Ext.Array.each(this.get("journalEntryDetails"),function(item,index){
			if (item.journalStep.id == step.id){
				Ext.Array.each( item.journalStepDetail, function(innerItem, innerIndex){
					// remove the detail
					if ( innerItem.id == detail.id ){
						Ext.Array.remove(item.journalStepDetail,innerItem);
					}
				},this);
								
				// no details remain, so remove the step
				if (item.journalStepDetail.length<1)
				{
					this.removeJournalStep( step );
				}
			}
		},this);
	},
	
	addJournalStep: function( step, detail ){
		this.get("journalEntryDetails").push( {"journalStep":step, "journalStepDetail": [detail] } );
	},
	
	removeJournalStep: function( step ){
		var journalEntryDetails = this.get("journalEntryDetails");
		Ext.Array.each(journalEntryDetails,function(item,index){
			if (item.journalStep.id == step.id){
				Ext.Array.remove(journalEntryDetails,item);
			}
		});
	},
	
	removeAllJournalEntryDetails: function(){
		this.set('journalEntryDetails',[]);
	}
});
Ext.define('Ssp.model.reference.AbstractReference', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name: 'name', type: 'string'},
             {name: 'description', type: 'string'}]
});
Ext.define('Ssp.model.reference.Challenge', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name: 'tags', type:'string'},
             {name: 'showInStudentIntake', type: 'boolean'},
             {name: 'showInSelfHelpSearch', type: 'boolean'},
             {name: 'selfHelpGuideQuestion', type: 'string'},
             {name: 'selfHelpGuideDescription', type: 'string'},
             {name: 'defaultConfidentialityLevelId', type: 'string'},
             {name: 'referralCount', type: 'int'}]
});

Ext.define('Ssp.model.reference.ChallengeCategory', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [],
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + 'challengeCategory/'});
    }
});
Ext.define('Ssp.model.reference.ChallengeReferral', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name: 'publicDescription', type: 'string'},
             {name: 'showInSelfHelpGuide', type: 'boolean'}]
});
Ext.define('Ssp.model.reference.JournalTrack', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.JournalStep', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.JournalStepDetail', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.ConfidentialityLevel', {
    extend: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },
    
    fields: [{name:'acronym',type:'string',defaultValue:'DEFAULT'}] ,

	constructor: function(){
		Ext.apply(this.getProxy(), 
				{ 
			url: this.apiProperties.createUrl( this.apiProperties.getItemUrl('confidentialityLevel') )
			    }
		);
		return this.callParent(arguments);
	},

    proxy: {
		type: 'rest',
		url: '',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT", 
			destroy: "DELETE"
		},
		reader: {
			type: 'json',
			successProperty: 'success',
			message: 'message'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});
Ext.define('Ssp.model.reference.ConfidentialityDisclosureAgreement', {
    extend: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
    fields: [{name:'text',type:'string'}],

	constructor: function(){
		Ext.apply(this.getProxy(), 
				{ 
			url: this.apiProperties.createUrl( this.apiProperties.getItemUrl('confidentialityDisclosureAgreement') )
			    }
		);
		return this.callParent(arguments);
	}, 	
	
	proxy: {
		type: 'rest',
		url: '',
		actionMethods: {
			create: "POST", 
			read: "GET", 
			update: "PUT",
			destroy: "DELETE"
		},
		reader: {
			type: 'json'
		},
	    writer: {
	        type: 'json',
	        successProperty: 'success'
	    }
	}
});
Ext.define('Ssp.store.Coaches', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Coach',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		Ext.apply(this, {
							proxy: this.apiProperties.getProxy(this.apiProperties.getItemUrl('person')),
							autoLoad: false
						});
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.store.Goals', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonGoal',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personGoal') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
});
Ext.define('Ssp.store.JournalEntries', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.journal.JournalEntry',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	currentPerson: 'currentPerson',
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personJournalEntry') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
});
Ext.define('Ssp.store.EarlyAlerts', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.earlyalert.PersonEarlyAlert',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personEarlyAlert') ),
						  autoLoad: false });
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.store.Documents', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.PersonDocument',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personDocument') ),
						  autoLoad: false });
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.store.reference.AbstractReferences', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },

	constructor: function(){
		Ext.apply(this, { 
						    proxy: this.apiProperties.getProxy(''), 
							autoLoad: false,
							autoSync: false,
						    pageSize: this.apiProperties.getPagingSize(),
						    params : {
								page : 0,
								start : 0,
								limit : this.apiProperties.getPagingSize()
							}						
						}
		);
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.store.reference.AnticipatedStartTerms', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ name: "FA", description: "FA" },
           { name: "WI", description: "WI" },
           { name: "SP", description: "SP" },
           { name: "SU", description: "SU" }]
});
Ext.define('Ssp.store.reference.AnticipatedStartYears', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ name: "2010", description: "2010" },
           { name: "2011", description: "2011" },
           { name: "2012", description: "2012" },
           { name: "2013", description: "2013" },
           { name: "2014", description: "2014" },
           { name: "2015", description: "2015" }]
});
Ext.define('Ssp.store.reference.Challenges', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Challenge',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('challenge')});
    }
});
Ext.define('Ssp.store.reference.ChallengeCategories', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChallengeCategory',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('category')});
    }
});
Ext.define('Ssp.store.reference.ChallengeReferrals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChallengeReferral',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('challengeReferral')});
    }
});
Ext.define('Ssp.store.reference.ConfidentialityLevels', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ConfidentialityLevel',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('confidentialityLevel')});
    }
});
Ext.define('Ssp.store.reference.Genders', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.Gender',
	autoLoad: false
});
Ext.define('Ssp.store.reference.JournalStepDetails', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalStepDetail',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalStepDetail')});
    }
});
Ext.define('Ssp.store.reference.JournalSteps', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalStep',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalStep')});
    }
});
Ext.define('Ssp.store.reference.JournalTracks', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalTrack',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalTrack')});
    }
});
Ext.define('Ssp.store.reference.States', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.State',
	autoLoad: false
});
Ext.define('Ssp.store.Students', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Person',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        apiProperties: 'apiProperties'
    },
	constructor: function(){
		Ext.apply(this, {
							proxy: this.apiProperties.getProxy(this.apiProperties.getItemUrl('person')),
							autoLoad: false
						});
		return this.callParent(arguments);
	}
});
Ext.define('Ssp.store.Tools', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.Tool',
    autoLoad: false,
    constructor: function(){
		return this.callParent(arguments);
    },
    data: [{ group:'alpha', name: "Profile", toolType: "Profile", active: true },
           { group:'alpha', name: "Student Intake", toolType: "StudentIntake", active: true },
           { group:'alpha', name: "Action Plan", toolType: "ActionPlan", active: true },
           { group:'alpha', name: "Journal", toolType: "Journal", active: true },
           { group:'beta', name: "Early Alert", toolType: "EarlyAlert", active: true },
           { group:'beta', name: "SIS", toolType: "StudentInformationSystem", active: true },
           { group:'rc1', name: "Documents", toolType: "StudentDocuments", active: false },
           { group:'rc1', name: "Disability Services", toolType: "DisabilityServices", active: false },
           { group:'rc1', name: "Displaced Workers", toolType: "DisplacedWorker", active: false },
           { group:'rc1', name: "Student Success", toolType: "StudentSuccess", active: false },
           ],
           
    groupField: 'group'
});
Ext.define('Ssp.store.reference.YesNo', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.AbstractReference',
    constructor: function(){
    	var items= [{id: "Y", name: "Yes"},
    			    {id: "N", name: "No"}];
    	
    	Ext.apply(this,{
    		items: items 
    	});
    	Ext.apply(this, {autoLoad: false});
		this.callParent(arguments);
    }
});
Ext.define('Ssp.model.tool.actionplan.TaskGroup', {
    extend: 'Ssp.model.tool.actionplan.Task',
    fields: [{name:'group',type:'string'}]
});
Ext.define('Ssp.store.Tasks', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.actionplan.TaskGroup',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		Ext.apply(this, { proxy: this.apiProperties.getProxy( this.apiProperties.getItemUrl('personTaskGroup') ),
						  autoLoad: false });
		return this.callParent(arguments);
	},
	
	groupField: 'group'
});
Ext.define('Ssp.model.reference.EmploymentShift', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: ['code',
             'title']  
});
Ext.define('Ssp.store.reference.EmploymentShifts', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.reference.EmploymentShift',
	autoLoad: false
});
Ext.define('Ssp.model.reference.Campus', {
    extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'earlyAlertCoordinatorId', type: 'string'}]
});
Ext.define('Ssp.model.reference.CampusEarlyAlertRouting', {
    extend: 'Ssp.model.AbstractBase',
    fields: [{name:'earlyAlertReasonId',type:'string'},
             {name:'person',type:'auto'},
             {name:'groupName',type:'string'},
             {name:'groupEmail',type:'string'}]
});
Ext.define('Ssp.model.reference.ChildCareArrangement', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.Citizenship', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.EarlyAlertOutcome', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'sortOrder',type:'int'}]
});
Ext.define('Ssp.model.reference.EarlyAlertOutreach', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'sortOrder',type:'int'}]
});
Ext.define('Ssp.model.reference.EarlyAlertReason', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'sortOrder',type:'int'}]
});
Ext.define('Ssp.model.reference.EarlyAlertReferral', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'acronym',type:'string'},
             {name:'sortOrder',type:'int'}]
});
Ext.define('Ssp.model.reference.EarlyAlertSuggestion', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'sortOrder',type:'int'}]
});
Ext.define('Ssp.model.reference.Ethnicity', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.FundingSource', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.JournalSource', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.MaritalStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.ProgramStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.ProgramStatusChangeReason', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.ReferralSource', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.ServiceReason', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.SpecialServiceGroup', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.VeteranStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.model.reference.StudentType', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: [{name:'requireInitialAppointment',type:'boolean'}]
});
Ext.define('Ssp.model.reference.StudentStatus', {
	extend: 'Ssp.model.reference.AbstractReference',
    fields: []
});
Ext.define('Ssp.store.reference.Campuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Campus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('campus')});
    }
});
Ext.define('Ssp.store.reference.CampusEarlyAlertRoutings', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.CampusEarlyAlertRouting',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('campusEarlyAlertRouting')});
    }
});
Ext.define('Ssp.store.reference.ChildCareArrangements', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ChildCareArrangement',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('childCareArrangement')});
    }
});
Ext.define('Ssp.store.reference.Citizenships', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Citizenship',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('citizenship')});
    }
});
Ext.define('Ssp.store.reference.EarlyAlertOutcomes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertOutcome',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertOutcome')});
    }
});
Ext.define('Ssp.store.reference.EarlyAlertOutreaches', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertOutreach',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertOutreach')});
    }
});
Ext.define('Ssp.store.reference.EarlyAlertReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertReason')});
    }
});
Ext.define('Ssp.store.reference.EarlyAlertReferrals', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertReferral',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertReferral')});
    }
});
Ext.define('Ssp.store.reference.EarlyAlertSuggestions', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.EarlyAlertSuggestion',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('earlyAlertSuggestion')});
    }
});
Ext.define('Ssp.store.reference.Ethnicities', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.Ethnicity',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('ethnicity')});
    }
});
Ext.define('Ssp.store.reference.FundingSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.FundingSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('fundingSource')});
    }
});
Ext.define('Ssp.store.reference.JournalSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.JournalSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('journalSource')});
    }
});
Ext.define('Ssp.store.reference.MaritalStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.MaritalStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('maritalStatus')});
    }
});
Ext.define('Ssp.store.reference.ProgramStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ProgramStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('programStatus')});
    }
});
Ext.define('Ssp.store.reference.ProgramStatusChangeReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ProgramStatusChangeReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('programStatusChangeReason')});
    }
});
Ext.define('Ssp.store.reference.ReferralSources', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ReferralSource',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('referralSource')});   
    }
});
Ext.define('Ssp.store.reference.ServiceReasons', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.ServiceReason',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('serviceReason')});
    }
});
Ext.define('Ssp.store.reference.SpecialServiceGroups', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.SpecialServiceGroup',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('specialServiceGroup')});
    }
});
Ext.define('Ssp.store.reference.StudentStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.StudentStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('studentStatus')});
    }
});
Ext.define('Ssp.store.reference.StudentTypes', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.StudentType',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('studentType')});
    }
});
Ext.define('Ssp.store.reference.VeteranStatuses', {
    extend: 'Ssp.store.reference.AbstractReferences',
    model: 'Ssp.model.reference.VeteranStatus',
    constructor: function(){
    	this.callParent(arguments);
    	Ext.apply(this.getProxy(),{url: this.getProxy().url + this.apiProperties.getItemUrl('veteranStatus')});
    }
});


