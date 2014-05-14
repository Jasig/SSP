/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
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
					        allowBlank: false,
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
		    		          allowBlank: false,
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
     		                   formBind: true,
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