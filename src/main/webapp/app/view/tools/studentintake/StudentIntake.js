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
		    		items: [ Ext.createWidget('tabpanel', {
						        width: '100%',
						        height: '100%',
						        activeTab: 0,
								border: 0,
						        items: [ { title: 'Personal',
						        		   autoScroll: true,
						        		   items: [{xtype: 'studentintakepersonal'}]
						        		},{
						            		title: 'Demographics',
						            		autoScroll: true,
						            		items: [{xtype: 'studentintakedemographics'}]
						        		},{
						            		title: 'EduPlan',
						            		autoScroll: true,
						            		items: [{xtype: 'studentintakeeducationplans'}]
						        		},{
						            		title: 'EduLevel',
						            		autoScroll: true,
						            		items: [{xtype: 'studentintakeeducationlevels'}]
						        		},{
						            		title: 'EduGoal',
						            		autoScroll: true,
						            		items: [{xtype: 'studentintakeeducationgoals'}]
						        		},{
						            		title: 'Funding',
						            		autoScroll: true,
						            		items: [{xtype: 'studentintakefunding'}]
						        		},{
						            		title: 'Challenges',
						            		autoScroll: true,
						            		items: [{xtype: 'studentintakechallenges'}]
						        		}]
						    })
					    
						],
						
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
					    }]

			});
						
		return this.callParent(arguments);
	}

});