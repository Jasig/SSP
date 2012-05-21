Ext.define('Ssp.view.tools.StudentIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.studentintake',
	id: 'StudentIntake',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.studentintake.StudentIntakeToolViewController',
    inject: {
        store: 'studentsStore'
    },
	title: 'Student Intake',	
	width: '100%',
	height: '100%',   
	autoScroll: true,
	initComponent: function() {	
		Ext.apply(this, 
				{
		    		store: this.store,
		    		border: 0,
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
								border: 0,
						        items: [ { title: 'Personal',
						        	       autoScroll: true,
						        		   items: [{xtype: 'studentintakepersonal'}]
						        		},{
						            		title: 'Demographics',
						            		autoScroll: true,
						            		border: 0,
						            		items: [{xtype: 'studentintakedemographics'}]
						        		},{
						            		title: 'EduPlan',
						            		autoScroll: true,
						            		border: 0,
						            		items: [{xtype: 'studentintakeeducationplans'}]
						        		},{
						            		title: 'EduLevel',
						            		autoScroll: true,
						            		border: 0,
						            		items: [{xtype: 'studentintakeeducationlevels'}]
						        		},{
						            		title: 'EduGoal',
						            		autoScroll: true,
						            		border: 0,
						            		items: [{xtype: 'studentintakeeducationgoals'}]
						        		},{
						            		title: 'Funding',
						            		autoScroll: true,
						            		border: 0,
						            		items: [{xtype: 'studentintakefunding'}]
						        		},{
						            		title: 'Challenges',
						            		autoScroll: true,
						            		border: 0,
						            		items: [{xtype: 'studentintakechallenges'}]
						        		}]
						    })
					    
						]
			});
						
		return this.callParent(arguments);
	}

});