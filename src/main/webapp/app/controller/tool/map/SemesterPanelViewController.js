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
	init: function() {
		var me=this;
	
		return me.callParent(arguments);
    },
	control:{
		termNotesButton:{
			selector:"#termNotesButton",
			listeners: {
                click: 'onTermNotesButtonClick'
             }
		}
	},
	
	onTermNotesButtonClick: function() {
		var me = this;
		if(me.termNotesPopUp == null || me.termNotesPopUp.isDestroyed)
        	me.termNotesPopUp = Ext.create('Ssp.view.tools.map.TermNotes');
	
		me.setFormValues(me.termNotesPopUp.query('form')[0], me.getView().query('form')[0]);
		me.termNotesPopUp.query('[name=saveButton]')[0].addListener('click', me.onTermNotesSave, me, {single:true});
		
        me.termNotesPopUp.center();
        me.termNotesPopUp.show();
    },

	onTermNotesSave: function(button){
		var me = this;
		me.setFormValues(me.getView().query('form')[0], button.findParentByType('form'));
		me.termNotesPopUp.close();
	},
	
	setFormValues: function(viewToSet, viewToGet){
		var form = viewToSet.getForm();
		var inputForm = viewToGet.getForm();
		form.findField("contactNotes").setValue(inputForm.findField("contactNotes").getValue());
		form.findField("studentNotes").setValue(inputForm.findField("studentNotes").getValue());
	},
	
	destroy: function(){
		var me=this;
		if(me.allTemplatesPopUp != null && !me.allTemplatesPopUp.isDestroyed)
		    me.allTemplatesPopUp.close();
		 return me.callParent( arguments );
	}
});