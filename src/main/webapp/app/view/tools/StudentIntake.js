Ext.require('Ext.tab.*');
Ext.define('Ssp.view.tools.StudentIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.StudentIntake',
	id: 'StudentIntake',
    title: 'Student Intake',

	store: Ext.getStore('Students'),
	
	width: '100%',
	height: '100%',   
    items: [],
	
	/* Example of a loader
	 * {   title: 'Personal', 
		   store: Ext.getStore('Students'),
		   loader: {
		        url:'templates/students.html',
		        contentType: 'html',
		        renderer: Ext.create( 'Ssp.util.Util' ).loaderXTemplateRenderer
		    },
		    listeners: {
	            activate: function(tab) {
	                tab.loader.load();
	            }
	        }
		}
	 * 
	 */	
	
	initComponent: function() {	
		this.items = [ Ext.createWidget('tabpanel', {
						        width: '100%',
						        height: '100%',
						        activeTab: 0,
						        items: [ { title: 'Personal',
						        		   items: [Ext.create('Ssp.view.tools.studentintake.Personal')]
						        		},{
						            		title: 'Demographics',
						            		items: [Ext.create('Ssp.view.tools.studentintake.Demographics')]
						        		},{
						            		title: 'EduPlan',
						            		items: [Ext.create('Ssp.view.tools.studentintake.EducationPlans')]
						        		},{
						            		title: 'EduLevel',
						            		items: [Ext.create('Ssp.view.tools.studentintake.EducationLevels')]
						        		},{
						            		title: 'EduGoal',
						            		items: [Ext.create('Ssp.view.tools.studentintake.EducationGoals')]
						        		},{
						            		title: 'Funding',
						            		items: [Ext.create('Ssp.view.tools.studentintake.Funding')]
						        		},{
						            		title: 'Challenges',
						            		items: [Ext.create('Ssp.view.tools.studentintake.Challenges')]
						        		}]
						    })
					    
						];
						
		this.superclass.initComponent.call(this, arguments);
	}

});