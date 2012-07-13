Ext.define('Ssp.view.admin.forms.ConfidentialityDisclosureAgreementAdmin', {
	extend: 'Ext.form.Panel',
	alias : 'widget.confidentialitydisclosureagreementadmin',
	id: 'ConfidentialityDisclosureAgreementAdmin',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.admin.ConfidentialityDisclosureAgreementAdminViewController',
    inject: {
        apiProperties: 'apiProperties',
        authenticatedPerson: 'authenticatedPerson'
    },
	initComponent: function() {
		var me=this;
		Ext.apply(me, 
				{
		    		autoScroll: true,
					width: '100%',
		    		height: '100%',
				    bodyPadding: 5,
				    layout: 'auto',
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 125
				    },
				    defaultType: 'displayfield',
				    items: [{
				            xtype: 'fieldset',
				            title: 'Confidentiality Disclosure Agreement',
				            border: 0,
				            defaultType: 'displayfield',
				            defaults: {
				                anchor: '100%'
				            },
				       items: 
				       [{
					        fieldLabel: 'Name',
					        xtype: 'textfield',
					        disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
					        name: 'name'
					    },{
					        fieldLabel: 'Description',
					        xtype: 'textfield',
					        disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
					        name: 'description'
					    },{
		    		          xtype: 'htmleditor',
		    		          fieldLabel: 'Disclosure Agreement',
		    		          enableColors: false,
		    		          disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
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
     		                   hidden: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_SAVE_BUTTON'),
     		                   action: 'save',
     		                   itemId: 'saveButton'
     		               }]
     		           }]
				});
		
	     return me.callParent(arguments);
	}

});