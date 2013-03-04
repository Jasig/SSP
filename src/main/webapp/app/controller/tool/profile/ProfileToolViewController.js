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
Ext.define('Ssp.controller.tool.profile.ProfileToolViewController', {
    extend: 'Deft.mvc.ViewController',
    mixins: [ 'Deft.mixin.Injectable' ],
    inject: {
    	apiProperties: 'apiProperties',
    	personLite: 'personLite',
		formUtils: 'formRendererUtils',
		appEventsController: 'appEventsController'
    },
    config: {
    	personViewHistoryUrl: '',
    	printConfidentialityAgreementUrl: ''
    },
    control: {
    	
    },
	init: function() {
		
		var me=this;
		var personId = me.personLite.get('id');
		
		me.appEventsController.assignEvent({
            eventName: 'viewCoachHistory',
            callBackFunc: me.onViewCoachHistory,
            scope: me
        });
		
		
		me.personViewHistoryUrl = me.apiProperties.getAPIContext() + me.apiProperties.getItemUrl('personViewHistory');
		
		me.personViewHistoryUrl = me.personViewHistoryUrl.replace('{id}',personId);
		
		me.printConfidentialityAgreementUrl = me.apiProperties.getContext() + me.apiProperties.getItemUrl('printConfidentialityDisclosureAgreement');
		return this.callParent(arguments);
    },
	
	destroy: function() {
        var me=this;
       // me.appEventsController.removeEvent({eventName: 'viewCoachHistory', callBackFunc: me.onViewCoachHistory, scope: me});
        
        return me.callParent( arguments );
    },
    
	
	
	onViewCoachHistory: function(){
      var me=this;
        me.apiProperties.getReporter().load({
            url:me.personViewHistoryUrl,
            params: ""
        });
    }
});