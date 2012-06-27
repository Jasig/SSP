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