Ext.define('Ssp.view.ProgramStatusChangeReasonWindow', {
	extend: 'Ext.window.Window',
	alias : 'widget.programstatuschangereasonwindow',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.ProgramStatusChangeReasonWindowViewController',
    inject: {
    	personLite: 'personLite',
    	store: 'programStatusChangeReasonsStore'
    },
	width: '100%',
	height: '100%',
	title: 'Please provide a reason the student will no longer be participating:',
    initComponent: function(){
    	var me=this;
    	Ext.applyIf(me,
    			   {
				    modal: true, 
		    		layout: 'anchor',
    				items: [{
                    	xtype: 'displayfield',
                    	fieldLabel: 'Student',
                    	value: me.personLite.get('displayFullName'),
                    	anchor: '95%'
                    },{
    			        xtype: 'combobox',
    			        itemId: 'programStatusChangeReasonCombo',
    			        name: 'programStatusChangeReasonId',
    			        fieldLabel: 'Reason',
    			        emptyText: 'Select One',
    			        store: me.store,
    			        valueField: 'id',
    			        displayField: 'name',
    			        mode: 'local',
    			        typeAhead: true,
    			        queryMode: 'local',
    			        allowBlank: false,
    			        forceSelection: true,
    			        anchor: '95%'
    		        }],
    	            dockedItems: [{
    		               xtype: 'toolbar',
    		               dock: 'bottom',
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
    	
    	return me.callParent(arguments);
    }
});