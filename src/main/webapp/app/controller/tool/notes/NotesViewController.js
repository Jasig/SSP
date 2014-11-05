/*
 * Licensed to Apereo under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Apereo licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License.  You may obtain a
 * copy of the License at the following location:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
Ext.define('Ssp.controller.tool.notes.NotesViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'personNoteService',
        person: 'currentPerson',
        store: 'personNotesStore'
    },
	init: function() {
		var me=this;
		var id = me.person.get('id');

        me.store.removeAll();
		if(id != ""){
	    	me.loadNotes(id);
	    }
		
		return this.callParent(arguments);
    },
    
    loadNotes: function(id){
		var me = this;
		if(id != ""){
			me.service.getPersonNotes( id, {
				success: me.getTranscriptSuccess,
				failure: me.getTranscriptFailure,
				scope: me			
			});
		}
	},
	
    getTranscriptSuccess: function( records, scope ){
    	var me=scope;
        me.store.loadData(records);
		me.store.sort([
		    {
		        property : 'dateNoteTaken',
		        direction: 'DESC'
		    },
		    {
		        property : 'author',
		        direction: 'ASC'
		    }]);
        me.getView().setLoading( false );
    },
    
    getTranscriptFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});