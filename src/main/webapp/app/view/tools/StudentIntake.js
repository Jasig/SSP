Ext.define('Ssp.view.tools.StudentIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentintake',
	id: 'StudentIntake',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.StudentIntakeToolViewController',
    inject: {
        studentsStore: 'studentsStore'
    },
	title: 'Student Intake',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		Ext.apply(this, 
				{
		    		store: this.studentsStore,

				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{xtype: 'button', itemId: 'saveStudentIntakeButton', text:'Save', action: 'save' },
				                { 
				        	     xtype: 'tbspacer',
				        	     flex: 1
				               },{
				            	   xtype: 'button',
				            	   itemId: 'viewConfidentialityAgreementButton',
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
						
		return this.callParent(arguments);
	}

});