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
Ext.define('Ssp.view.tools.disabilityintake.DisabilityIntake', {
	extend: 'Ext.panel.Panel',
	alias : 'widget.disabilityintake',
    mixins: [ 'Deft.mixin.Injectable',
              'Deft.mixin.Controllable'],
    controller: 'Ssp.controller.tool.disabilityintake.DisabilityIntakeToolViewController',
    inject: {
    	authenticatedPerson: 'authenticatedPerson'
    },
	title: 'Disability Intake',	
	width: '100%',
	height: '100%',
	initComponent: function() {	
		var me=this;
		Ext.apply(me, 
				{		
		    		layout: 'fit',
		    		padding: 0,
		    		border: 0,
					dockedItems: [{
				        dock: 'top',
				        xtype: 'toolbar',
				        items: [{
				        			xtype: 'button', 
				        			itemId: 'saveButton', 
				        			text:'Save',
				        			hidden: !me.authenticatedPerson.hasAccess('DISABILITY_INTAKE_SAVE_BUTTON'),
				        	   },{
					        	     xtype: 'button', 
					        	     itemId: 'cancelButton', 
					        	     text:'Cancel',
					        	     hidden: !me.authenticatedPerson.hasAccess('DISABILITY_INTAKE_CANCEL_BUTTON'),
				        	   },{
				        	    	xtype: 'label',
				        	    	html: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE,
				        	    	itemId: 'saveSuccessMessage',
				        	    	style: Ssp.util.Constants.DATA_SAVE_SUCCESS_MESSAGE_STYLE,
				        	    	hidden: true
				        	    }]
					}],
				    
				    items: []
			    
		});
		
		return me.callParent(arguments);
	}
});