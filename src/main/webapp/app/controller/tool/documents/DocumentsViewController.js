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
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	store: 'studentDocumentsStore',
    	model: 'currentStudentDocument'

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
		editDocumentButton: {
				selector: '#editDocumentButton',
				listeners: {
					click: 'onEditDocumentClick'
			}
		},
		deleteDocumentButton: {
			selector: '#deleteDocumentButton',
			listeners: {
				click: 'onDeleteDocumentClick'
		}
		},
		downloadDocumentButton: {
			selector: '#downloadDocumentButton',
			listeners: {
				click: 'onDownloadDocumentClick'
		}
	}
		
	},
	
	init: function() {
		var me = this;
		me.formUtils.reconfigureGridPanel(me.getView(), me.store);
		me.store.load(me.person.get('id'));
		return me.callParent(arguments);
    },
    
    onDeleteDocumentClick: function(button) {
    	var me=this;
		var record = me.getView().getSelectionModel().getSelection()[0];
		var success = function()
		{
        	Ext.Msg.alert('Success', 'The Student Document has been deleted.');
        	me.init();
        };
        var failure = function()
        {
        	Ext.Msg.alert('Deleting document failed.  Please contact the system administrator.');
        };
        if(record)
        {
			var url = me.getBaseUrl(me.person.get('id'));
			me.apiProperties.makeRequest({
				url: url+'/'+ record.get('id'), 
				method: 'DELETE',
				jsonData: me.model.data,
				successFunc: success,
				failureFunc: failure,
				scope: me
			});	
        }
        else
        {
      	   Ext.Msg.alert('SSP Error', 'Please select an item to delete.'); 
        }
    },
    onDownloadDocumentClick: function(button) {
    	var me=this;
		var record = me.getView().getSelectionModel().getSelection()[0];
    	var url = me.getBaseUrl(me.person.get('id'));
    	url = url + '/'+record.get('id') + '/file';
    	window.open(url,'_self');

    },   
    onAddDocumentClick: function(button) {
    	var me=this;
		var model = new Ssp.model.tool.documents.StudentDocument();
		me.model.data = model.data;
		var comp = me.formUtils.loadDisplay(me.getContainerToLoadInto(), me.getFormToDisplay(), true, {}); 
    },
    onEditDocumentClick: function(button) {
    	var me=this;
		var record = me.getView().getSelectionModel().getSelection()[0];
        if (record) 
        {		
        	me.model.data=record.data;
        	var comp = me.formUtils.loadDisplay(me.getContainerToLoadInto(), me.getFormToDisplay(), true, {}); 
        }else{
     	   Ext.Msg.alert('SSP Error', 'Please select an item to edit.'); 
        }    
        },
    getBaseUrl: function(id){
		var me=this;
		var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('studentDocument') );
		baseUrl = baseUrl.replace('{id}', id);
		return baseUrl;
    }
});