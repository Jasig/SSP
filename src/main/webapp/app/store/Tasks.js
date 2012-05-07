Ext.define('Ssp.store.Tasks', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.tool.actionplan.Task',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	currentPerson: 'currentPerson',
    	apiProperties: 'apiProperties'
    },    
	constructor: function(){
		var items = [{"id" : "23befc50-7f91-11e1-b0c4-0800200c9a66",
	        "createdDate" : "1332216000000",
	        "createdById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	        "modifiedDate" : "1332216000000",
	        "modifiedById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	        "objectStatus" : "ACTIVE",
	        "name" : "Family Services",
	        "description" : "FAMILY SERVICES:  Contact Pat Davis at Family Services Association 222-9481 for parenting education program.  Parenting 101 Classes are offered throughout the year.  Sliding fee scale will establish the fee and scholarships may be available.",
	        "dueDate" : "04/23/2012",
	        "reminderSentDate" : "1332216000000",
	        "completed" : "false",
	        "completedDate" : "1332216000000",
	        "challengeId" : "9D6E3B8F-AFB3-4D86-A527-9778035B94E1",
	        "deletableByStudent" : "true",
	        "closableByStudent" : "true",
	        "confidentialityLevel" : "EVERYONE",
	        "type" : "SSP"},
	       {"id" : "7ed6d720-7f91-11e1-b0c4-0800200c9a66",
	        "createdDate" : "1332216000000",
	        "createdById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	        "modifiedDate" : "1332216000000",
	        "modifiedById" : "91f46e39-cea8-422b-b215-00f6bcf5d280",
	        "objectStatus" : "ACTIVE",
	        "name" : "Montgomery County Child Support Enforcement Agency",
	        "description" : "Contact Montgomery County Child Support Enforcement Agency for enforcement of child support orders 225-4600 www.mcsea.org.",
	        "dueDate" : "04/23/2012",
	        "reminderSentDate" : "",
	        "completed" : "false",
	        "completedDate" : "1332216000000",
	        "challengeId" : "9D6E3B8F-AFB3-4D86-A527-9778035B94E1",
	        "deletableByStudent" : true,
	        "closableByStudent" : true,
	        "confidentialityLevel" : "DISABILITY",
	        "type" : "SSP"}
	      ];
		
		Ext.apply(this, { 
							proxy: this.apiProperties.getProxy('person/' + this.currentPerson.get('id') + '/task/' ),
						    autoLoad: false,
						    items: items
		});
		return this.callParent(arguments);
	},
});