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
Ext.define('Ssp.controller.tool.documents.DocumentsViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	authenticatedPerson: 'authenticatedPerson',
    	formUtils: 'formRendererUtils'
    },
    
	config: {
    	appEventsController: 'appEventsController',
    	containerToLoadInto: 'tools',
    	formToDisplay: 'uploaddocuments'
    },
    
    control: {
    	
    	
		addDocumentButton: {
			selector: '#addDocumentButton',
			listeners: {
				click: 'onAddDocumentClick'
			}
		},
		
	
	},
	
	init: function() {
		var me = this;
		
		// display loader
		//me.getView().setLoading( true );
		
		
		return me.callParent(arguments);
    },
    
   
    
    
    onAddDocumentClick: function(button) {
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {}); 
    	//this.appEventsController.getApplication().fireEvent('addDocument');
    },  
});