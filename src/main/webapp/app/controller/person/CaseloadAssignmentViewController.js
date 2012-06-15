Ext.define('Ssp.controller.person.CaseloadAssignmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
        person: 'currentPerson',
        specialServiceGroupsStore: 'specialServiceGroupsStore',
        referralSourcesStore: 'referralSourcesStore',
        serviceReasonsStore: 'serviceReasonsStore',
        studentTypesStore: 'studentTypesStore'
    },
    control: {
    	'saveButton':{
    		click: 'onSaveClick'
    	},
    	
    	'cancelButton': {
    		click: 'onCancelClick'
    	}
    },
    
	init: function() {
		var me=this;
		var personal = Ext.ComponentQuery.query('.editperson')[0];
		
		var serviceReasonsSuccessFunc = function(records,operation,success){
			if (records.length > 0)
	    	{
	    		var items = [];
				Ext.Array.each(records,function(item,index){
	    			items.push(item.raw);
	    		});
				var serviceReasonsFormProps = {
	    				mainComponentType: 'checkbox',
	    				formId: 'personservicereasons', 
	                    fieldSetTitle: 'Select all that apply:',
	                    itemsArr: items, 
	                    selectedItemsArr: [], 
	                    idFieldName: 'id', 
	                    selectedIdFieldName: 'serviceReasonId',
	                    additionalFieldsMap: [] };
	    		
	    		me.formUtils.createForm( serviceReasonsFormProps );	    		
	    	}
		};		
		
		personal.loadRecord( me.person );
		me.specialServiceGroupsStore.load();
		me.referralSourcesStore.load();
		me.serviceReasonsStore.load({scope: me, callback: serviceReasonsSuccessFunc});
		me.studentTypesStore.load();
		
		return me.callParent(arguments);
    },
    
    onSaveClick: function(button){
		Ext.Msg.alert('Attention', 'This feature is not yet available.');
    },
    
    onCancelClick: function(button){
		this.loadStudentToolsView();
    },
    
    loadStudentToolsView: function(){
    	// Display the Student Record
    	var comp = this.formUtils.loadDisplay('studentrecord', 'toolsmenu', true, {flex:1});    	
    	comp = this.formUtils.loadDisplay('studentrecord', 'tools', false, {flex:4});
    }
});