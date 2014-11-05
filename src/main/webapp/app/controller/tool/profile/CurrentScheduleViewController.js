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
Ext.define('Ssp.controller.tool.profile.CurrentScheduleViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: ['Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        service: 'transcriptService',
        personLite: 'personLite',
        store: 'currentScheduleStore',
        termsStore: 'termsStore'
    },
    init: function(){
        var me = this;
        var personId = me.personLite.get('id');
        
        me.store.removeAll();
        if (personId != "") {
            me.getView().setLoading(true);
            
            if (me.termsStore.getTotalCount() <= 0) {
                me.termsStore.addListener("load", me.termStoreLoaded, me, {
                    single: true
                });
                me.termsStore.load();
            }
            else {
                me.termStoreLoaded();
            }
          
        }
        return this.callParent(arguments);
    },
    
    
    termStoreLoaded: function(){
        var me = this;
        var personId = me.personLite.get('id');
        if (personId != "") {
            me.service.getFull(personId, {
                success: me.getScheduleSuccess,
                failure: me.getScheduleFailure,
                scope: me
            });
        }
    },
    
    
    getScheduleSuccess: function(r, scope){
        var me = scope;
        
        var courseSchedules = [];
        var transcript = new Ssp.model.Transcript(r);
        var items = transcript.get('terms');
        if (items) {
            Ext.Array.each(items, function(item){
                var courseTranscript = Ext.create('Ssp.model.CourseTranscript', item);
				
                var termIndex = me.termsStore.getCurrentAndFutureTermsStore(true).findExact("code", courseTranscript.get("termCode"));
                if (termIndex >= 0) {
                    var term = me.termsStore.getCurrentAndFutureTermsStore(true).getAt(termIndex);
                    courseTranscript.set("termStartDate", term.get("startDate"));
                    
                    courseSchedules.push(courseTranscript);
                }
            });
        }
        
        if (courseSchedules.length > 0) {
        
            me.store.loadData(courseSchedules);
            
            me.store.sort([{
            
                property: 'termStartDate',
                
                direction: 'ASC'
            
            }, {
            
                property: 'formattedCourse',
                
                direction: 'ASC'
            
            }]);
            
        }
       
        me.getView().setLoading(false);
        
        
    },
    
    getScheduleFailure: function(response, scope){
        var me = scope;
        me.getView().setLoading(false);
    }
});
