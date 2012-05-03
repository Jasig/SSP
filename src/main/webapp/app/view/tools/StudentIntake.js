Ext.define('Ssp.view.tools.StudentIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentintake',
	id: 'StudentIntake',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
        studentsStore: 'studentsStore'
    },
	title: 'Student Intake',	
	width: '100%',
	height: '100%',   
	
	initComponent: function() {	
		Ext.apply(this, 
				{
					autoScroll: true,
		    		store: this.studentsStore,

				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{xtype: 'button', id: 'saveStudentIntakeButton', text:'Save', action: 'save' },
				                { 
				        	     xtype: 'tbspacer',
				        	     flex: 1
				               },{
				            	   xtype: 'button',
				            	   id: 'viewConfidentialityAgreementButton',
				            	   text: 'View Confidentiality Agreement',
				            	   action: 'viewConfidentialityAgreement'}]
				    }],
				    
		    		items: [ Ext.createWidget('tabpanel', {
						        width: '100%',
						        height: '100%',
						        activeTab: 0,
						        items: [ { title: 'Personal',
						        			autoScroll: true,
						        		   items: [Ext.create('Ssp.view.tools.studentintake.Personal')]
						        		},{
						            		title: 'Demographics',
						            		autoScroll: true,
						            		items: [Ext.create('Ssp.view.tools.studentintake.Demographics')]
						        		},{
						            		title: 'EduPlan',
						            		autoScroll: true,
						            		items: [Ext.create('Ssp.view.tools.studentintake.EducationPlans')]
						        		},{
						            		title: 'EduLevel',
						            		autoScroll: true,
						            		items: [Ext.create('Ssp.view.tools.studentintake.EducationLevels')]
						        		},{
						            		title: 'EduGoal',
						            		autoScroll: true,
						            		items: [Ext.create('Ssp.view.tools.studentintake.EducationGoals')]
						        		},{
						            		title: 'Funding',
						            		autoScroll: true,
						            		items: [Ext.create('Ssp.view.tools.studentintake.Funding')]
						        		},{
						            		title: 'Challenges',
						            		autoScroll: true,
						            		items: [Ext.create('Ssp.view.tools.studentintake.Challenges')]
						        		}]
						    })
					    
						]
			});
						
		this.callParent(arguments);
	}

});