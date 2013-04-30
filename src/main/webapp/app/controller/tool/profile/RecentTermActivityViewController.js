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
Ext.define('Ssp.controller.tool.profile.RecentTermActivityViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'transcriptService',
        personLite: 'personLite',
        store: 'termTranscriptsStore',
		termsStore: 'termsStore'
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');

        me.store.removeAll();
        if(personId != ""){
	    	me.getView().setLoading( true );
	    	if(me.termsStore.getTotalCount() <= 0){
				me.termsStore.addListener("load", me.termStoreLoaded, me, {single:true});
				me.termsStore.load();
			}else{
				me.termStoreLoaded();
			}
			
	    }
		return this.callParent(arguments);
    },
    
	termStoreLoaded: function(){
		var me = this;
		var personId = me.personLite.get('id');
		if(personId != ""){
			me.service.getTerm( personId, {
				success: me.getTranscriptSuccess,
				failure: me.getTranscriptFailure,
				scope: me			
			});
		}
	},
	
    getTranscriptSuccess: function( r, scope ){
    	var me=scope;

        var termTranscripts = [];
        Ext.Array.each(r, function(rawDatum) {
                var termTranscript = Ext.create('Ssp.model.TermTranscript', rawDatum);
				var termIndex = me.termsStore.find("code", termTranscript.get("termCode"));
				if(termIndex >= 0){
					var term = me.termsStore.getAt(termIndex);
					termTranscript.set("termStartDate", term.get("startDate"));
				}
                termTranscripts.push(termTranscript);
        });

        me.store.loadData(termTranscripts);
		me.store.sort([
		    {
		        property : 'termStartDate',
		        direction: 'DESC'
		    },
		    {
		        property : 'formattedCourse',
		        direction: 'ASC'
		    }]);
        me.getView().setLoading( false );
    },
    
    getTranscriptFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});