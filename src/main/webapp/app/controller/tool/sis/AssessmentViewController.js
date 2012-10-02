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
Ext.define('Ssp.controller.tool.sis.AssessmentViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	service: 'assessmentService',
        personLite: 'personLite'
    },
	init: function() {
		var me=this;
		var personId = me.personLite.get('id');

    	// hide the loader
    	me.getView().setLoading( true );
    	
		me.service.getAll( personId, {
			success: me.getAssessmentSuccess,
			failure: me.getAssementFailure,
			scope: me			
		});
		
		return this.callParent(arguments);
    },
    
    getAssessmentSuccess: function( r, scope ){
    	var me=scope;
    	me.getView().setLoading( false );
    	
    },
    
    getAssementFailure: function( response, scope ){
    	var me=scope;
    	me.getView().setLoading( false );  	
    }
});