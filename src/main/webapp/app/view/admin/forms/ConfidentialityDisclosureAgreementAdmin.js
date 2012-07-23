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
			        title: 'Confidentiality Disclosure Agreement Admin',
		    		autoScroll: true,
					width: '100%',
		    		height: '100%',
		    		bodyPadding: 5,
				    layout: 'anchor',
				    fieldDefaults: {
				        msgTarget: 'side',
				        labelAlign: 'right',
				        labelWidth: 125
				    },
				    defaultType: 'displayfield',
				       items: 
				       [{
					        fieldLabel: 'Name',
					        xtype: 'textfield',
					        disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
					        name: 'name',
					        anchor: '95%'
					    },{
					        fieldLabel: 'Description',
					        xtype: 'textfield',
					        disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
					        name: 'description',
					        anchor: '95%'
					    },{
		    		          xtype: 'htmleditor',
		    		          fieldLabel: 'Disclosure Agreement',
		    		          enableColors: false,
		    		          disabled: !me.authenticatedPerson.hasAccess('CONFIDENTIALITY_AGREEMENT_ADMIN_FIELDS'),
		    		          enableAlignments: false,
		    		          anchor: '95% 80%',
		    		          name: 'text'
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
     		               },{
		        	    	xtype: 'label',
		        	    	html: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE,
		        	    	itemId: 'saveSuccessMessage',
		        	    	style: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_STYLE,
		        	    	hidden: true
		        	    }]
     		           }]
				});
		
	     return me.callParent(arguments);
	}

});