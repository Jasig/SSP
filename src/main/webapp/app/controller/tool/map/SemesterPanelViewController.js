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
Ext.define('Ssp.controller.tool.map.SemesterPanelViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
	inject:{
		currentMapPlan:'currentMapPlan'
	},
	
	control:{
		termNotesButton:{
			selector:"#termNotesButton",
			listeners: {
                click: 'onTermNotesButtonClick'
             }
		},
		deleteButton:{
			selector:"#deleteButton",
			listeners: {
                click: 'onDeleteButtonClick'
             }
		},		
		view: {
			afterlayout: {
				fn: 'onAfterLayout',
				single: true
			},
    	},
	},
	
	init: function() {
		var me=this;
		return me.callParent(arguments);
    },

	onAfterLayout: function(){
		var me = this;
		me.setTermNoteButton();
	},
	
	setTermNoteButton: function(){
		var me = this;
		var termNote = me.currentMapPlan.getTermNoteByTermCode(me.getView().itemId);
		var button = me.getTermNotesButton();
		if((termNote != undefined && termNote.data.contactNotes && termNote.data.contactNotes.length > 0) ||
			(termNote.data.studentNotes != undefined && termNote.data.studentNotes.length > 0) ){
			button.setIcon(Ssp.util.Constants.EDIT_TERM_NOTE_ICON_PATH);
			return;
		}
	          button.setIcon(Ssp.util.Constants.ADD_TERM_NOTE_ICON_PATH);
	},
	
	onTermNotesButtonClick: function() {
		var me = this;
		if(me.termNotesPopUp == null || me.termNotesPopUp.isDestroyed)
        	me.termNotesPopUp = Ext.create('Ssp.view.tools.map.TermNotes');
		
	    var termNote = me.currentMapPlan.getTermNoteByTermCode(me.getView().itemId);
	    me.termNotesPopUp.query('form')[0].getForm().loadRecord(termNote);
	   
		me.termNotesPopUp.query('[name=saveButton]')[0].addListener('click', me.onTermNotesSave, me, {single:true});
		
        me.termNotesPopUp.center();
        me.termNotesPopUp.show();
    },
    onDeleteButtonClick: function() {
		var me = this;
		var grid = me.getView().query('grid')[0];
		var record = grid.getView().getSelectionModel().getSelection()[0];
		if(!record)
		{
			 	Ext.Msg.alert('SSP Error', 'Please select an item.'); 
	    }
		else
		{
			me.getView().query('grid')[0].getView().store.remove(record);
		}
    },
	onTermNotesSave: function(button){
		var me = this;
		 var termNote = me.currentMapPlan.getTermNoteByTermCode(me.getView().itemId);
		me.termNotesPopUp.query('form')[0].getForm().updateRecord(termNote);
		me.setTermNoteButton();
		me.termNotesPopUp.close();
	},

	
	destroy: function(){
		var me=this;
		if(me.allTemplatesPopUp != null && !me.allTemplatesPopUp.isDestroyed)
		    me.allTemplatesPopUp.close();
		 return me.callParent( arguments );
	}
});