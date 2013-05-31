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
Ext.define('Ssp.controller.admin.shg.EditSelfHelpGuideChallengesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	formUtils: 'formRendererUtils',
    	parent: 'currentSelfHelpGuide',
    	store: 'selfHelpGuideQuestionsStore',
    	model: 'currentSelfHelpGuideQuestions'
    },
    config: {
    	formToDisplay: 'selfhelpguideadmin',
    	containerToLoadInto: 'adminforms'
    },
    control: {
    	'deleteChallengeButton': {
			click: 'deleteConfirmation'
		},
       'gridView': {
    	   refresh: 'onRefresh',
    	   drop: 'onDrop'
        }
    },
    
	init: function() {
		this.formUtils.reconfigureGridPanel( this.getView(), this.store);
		this.store.load({
			params: {selfReferenceGuideId:this.parent.data.id}
		});
		return this.callParent(arguments);
    },
    onRefresh: function(node, data, dropRec, dropPosition)
    {

    },
    onDrop:function(node, data, overModel, dropPosition, options)
	{   
    	var me=this;
		//Since the panels use two different models we need to remove the record from 
		//the store and recreate it so it conforms to the appropriate model
		var badRecord = me.store.findRecord('id', data.records[0].data.id);
        var newQuestionNumber = me.store.indexOf(badRecord) + 1;
        var newRecord = new Ssp.model.tool.shg.SelfHelpGuideQuestions();
        newRecord.data.critical = false;
        newRecord.data.mandatory = false;
        newRecord.data.name = badRecord.data.name;
        newRecord.data.challengeId = badRecord.data.id;
        newRecord.data.questionNumber = newQuestionNumber;
        newRecord.data.selfHelpGuideId = this.parent.data.id;
        if(badRecord)
        {
        	me.store.data.replace(this.store.data.getKey(badRecord),newRecord);
        	
        	for(var i = 0; i<this.store.data.items.length; i++)
        	{
        		me.store.data.items[i].data.questionNumber = i+1;
        	}
        }
        else
        {
        	me.store.add(newRecord);
        }
        me.getView().getStore().loadRecords(me.store.getRange());
        this.formUtils.reconfigureGridPanel( this.getView(), me.store);
     },
     deleteConfirmation: function( button ) {
    	 var me=this;
    	 var grid = button.up('grid');
		 var store = grid.getStore();
		 var selection = grid.getView().getSelectionModel().getSelection()[0];
		 var message;
		 if(!selection)
		 {
		 	Ext.Msg.alert('SSP Error', 'Please select an item.'); 
		 }
		 else
		 if ( selection.get('id') ) 
		 {
			   if ( !Ssp.util.Constants.isRestrictedAdminItemId( selection.get('id')  ) )
			   {
				    message = 'You are about to delete ' + selection.get('name') + '. Would you like to continue?';
			     	      	   
		         Ext.Msg.confirm({
		 		     title:'Delete?',
		 		     msg: message,
		 		     buttons: Ext.Msg.YESNO,
		 		     fn: me.deleteRecord,
		 		     scope: me
		 		   });
			   }else{
				   Ext.Msg.alert('WARNING', 'This item is related to core SSP functionality. Please see a developer to delete this item.'); 
			   }
		  }else{
		       store.remove(selection);
		       me.getView().getStore().loadRecords(me.store.getRange());
		       me.formUtils.reconfigureGridPanel( me.getView(), me.store);
		  }
     },	

	deleteRecord: function( btnId ){
		var me=this;
		var grid=me.getView();
		var store = grid.getStore();
		var selection = grid.getView().getSelectionModel().getSelection()[0];
		var id = selection.get('id');
		if (btnId=="yes")
		{
			me.apiProperties.makeRequest({
	 		url: store.getProxy().url+"/"+id,
	 		method: 'DELETE',
			successFunc: function(response,responseText){
	 		store.remove( store.getById( id ) );
	 		   }
	 	    });
		}
	},
	
	onCancelClick: function(button){
		this.displayMain();
	},
	
	displayMain: function(){
		var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
	}
});