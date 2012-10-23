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
Ext.define('Ssp.view.tools.profile.Profile', {
	extend: 'Ext.form.Panel',
	alias : 'widget.profile',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.profile.ProfileToolViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
    width: '100%',
	height: '100%',
    initComponent: function() {	
		var me=this;
    	Ext.apply(me, 
				{
		    	    layout: 'fit',
		            title: 'Profile',
		            padding: 0,
		            border: 0,
					items: [
						Ext.createWidget('tabpanel', {
						    width: '100%',
						    height: '100%',
						    activeTab: 0,
						    itemId: 'profileTabs',
						    items: [{ 
						    	      title: 'Personal',
						    	      autoScroll: true,
						    		  items: [{xtype: 'profileperson'}]
						    		},{ 
						    		  title: 'Special Service Groups',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profilespecialservicegroups'}]
						    		},{ 
						    		  title: 'Referral Sources',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profilereferralsources'}]
						    		},{ 
						    		  title: 'Service Reasons',
						    		  autoScroll: true,
						    		  items: [{xtype: 'profileservicereasons'}]
						    		},{ 
						    		  title: 'Services Provided History',
						    		  hidden: true,
						    		  autoScroll: true,
						    		  items: [{xtype: 'profileservicesprovided'}]
							    	}]
						})
				    ],
				    
				    dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
					            tooltip: 'Print Student History',
					            text: '',
					            width: 32,
					            height: 32,
					            hidden: !me.authenticatedPerson.hasAccess('PRINT_HISTORY_BUTTON'),
					            cls: 'studentHistoryIcon',
					            xtype: 'button',
					            itemId: 'viewHistoryButton'
				        },{
			            	   xtype: 'button',
			            	   itemId: 'printConfidentialityAgreementButton',
			                   text: '',
			                   tooltip: 'Print Confidentiality Agreement',
			            	   cls: 'confidentialityAgreementIcon',
			            	   height: 32,
			            	   width: 32,
			            	   hidden: !me.authenticatedPerson.hasAccess('PROFILE_PRINT_CONFIDENTIALITY_AGREEMENT_BUTTON'),
			            }]
				    }]
				});	     
    	
    	return me.callParent(arguments);
	}
});