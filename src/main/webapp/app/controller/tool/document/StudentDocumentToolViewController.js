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
Ext.define('Ssp.controller.tool.document.StudentDocumentToolViewController', {
    extend: 'Deft.mvc.ViewController',	
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
    	apiProperties: 'apiProperties',
    	appEventsController: 'appEventsController',
    	formUtils: 'formRendererUtils',
    	person: 'currentPerson',
    	model: 'currentDocument',
    	documentsStore: 'documentsStore',
        confidentialityLevelsStore: 'confidentialityLevelsStore'
    },
    config: {
    	containerToLoadInto: 'tools',
    	formToDisplay: 'editdocument',
    	personDocumentUrl: ''
    },
    control: {
    	view: {
    		viewready: 'onViewReady'
    	},
    	
    	'addButton': {
			click: 'onAddClick'
		}
	},
    init: function() {
		var me = this;
		var personId = this.person.get('id');
		var successFunc = function(response,view){
	    	var r = Ext.decode(response.responseText);
	    	if (r.rows.length > 0)
	    	{
	    		me.documentsStore.loadData(r.rows);
	    	}
		};

    	this.confidentialityLevelsStore.load();

		this.personDocumentUrl = this.apiProperties.createUrl( this.apiProperties.getItemUrl('personDocument') );
		this.personDocumentUrl = this.personDocumentUrl.replace('{id}',personId);		

		this.apiProperties.makeRequest({
			url: this.personDocumentUrl,
			method: 'GET',
			successFunc: successFunc
		});
    	
    	var json = {"success":true,"results":0,"rows":[]};
    	var rows = [{
    		"id":"240e97c0-7fe5-11e1-b0c4-0800200c9a66",
    		"name":"My Document",
    		"note":"This is my document",
    		"confidentialityLevel":{"id":"afe3e3e6-87fa-11e1-91b2-0026b9e7ff4c","name":"EVERYONE"},
    		"createdBy":{"id":"58ba5ee3-734e-4ae9-b9c5-943774b4de41","firstName":"System","lastName":"Administrator"},
    		"modifiedBy":{"id":"58ba5ee3-734e-4ae9-b9c5-943774b4de41","firstName":"System","lastName":"Administrator"},
    		"createdDate":1331269200000
    	}];
    	json.rows = rows;

		this.documentsStore.loadData(json.rows);
		
		return this.callParent(arguments);
    },
 
    onViewReady: function(comp, obj){
    	this.appEventsController.assignEvent({eventName: 'editDocument', callBackFunc: this.editDocument, scope: this});
    	this.appEventsController.assignEvent({eventName: 'deleteDocument', callBackFunc: this.deleteConfirmation, scope: this});
    },    
 
    destroy: function() {
    	this.appEventsController.removeEvent({eventName: 'editDocument', callBackFunc: this.editDocument, scope: this});
    	this.appEventsController.removeEvent({eventName: 'deleteDocument', callBackFunc: this.deleteConfirmation, scope: this});

        return this.callParent( arguments );
    },    
    
    onAddClick: function(button){
    	var document = new Ssp.model.PersonDocument();
    	this.model.data = document.data;
    	this.loadEditor();
    },
    
    editDocument: function(){
    	this.loadEditor();
    },
 
    deleteConfirmation: function() {
        var message = 'You are about to delete a document. Would you like to continue?';
    	var model = this.model;
        if (model.get('id') != "") 
        {
           Ext.Msg.confirm({
   		     title:'Delete Document?',
   		     msg: message,
   		     buttons: Ext.Msg.YESNO,
   		     fn: this.deleteDocument,
   		     scope: this
   		   });
        }else{
     	   Ext.Msg.alert('SSP Error', 'Unable to delete document.'); 
        }
     },
     
     deleteDocument: function( btnId ){
     	var store = this.documentsStore;
     	var id = this.model.get('id');
     	if (btnId=="yes")
     	{
     		this.apiProperties.makeRequest({
      		   url: this.personDocumentUrl+"/"+id,
      		   method: 'DELETE',
      		   successFunc: function(response,responseText){
      			   store.remove( store.getById( id ) );
      		   }
      	    });   		
     	}
     },    
    
    loadEditor: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});    	
    }
});