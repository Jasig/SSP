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
Ext.define('Ssp.controller.tool.earlyalert.EarlyAlertToolViewController', {
    extend: 'Deft.mvc.ViewController',  
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        appEventsController: 'appEventsController',
        confidentialityLevelsStore: 'confidentialityLevelsStore',
        campusesStore: 'campusesAllUnpagedStore',
        earlyAlertsStore: 'earlyAlertsStore',
        earlyAlertService: 'earlyAlertService',
        earlyAlertResponseService: 'earlyAlertResponseService',
        earlyAlert: 'currentEarlyAlert',
        earlyAlertResponse: 'currentEarlyAlertResponse',
        formUtils: 'formRendererUtils',
        outcomesStore: 'earlyAlertOutcomesAllUnpagedStore',
        outreachesStore: 'earlyAlertOutreachesAllUnpagedStore',
        reasonsStore: 'earlyAlertReasonsAllUnpagedStore',
        personLite: 'personLite',
        referralsStore: 'earlyAlertReferralsAllUnpagedStore',
        suggestionsStore: 'earlyAlertSuggestionsAllUnpagedStore',
        treeStore: 'earlyAlertsTreeStore'
    },
    config: {
        containerToLoadInto: 'tools',
        earlyAlertDetailsDisplay: 'earlyalertdetails',
        earlyAlertResponseDetailsDisplay: 'earlyalertresponsedetails'
    },
    control: {
        view: {
            viewready: 'onViewReady',
            itemexpand: 'onItemExpand',
            itemClick: 'onAlertClick'
        }
    },
    
    init: function(){
        return this.callParent(arguments);
    },

    onViewReady: function(comp, obj){
        var me=this;
        me.campusesStore.load({
            params:{status: "ALL", limit: "-1"}
        });
        
        me.confidentialityLevelsStore.load({
            params:{status: "ALL", limit: "-1"}
        });
        
        me.outcomesStore.load({
            params:{status: "ALL", limit: "-1"}
        });
        
        me.outreachesStore.load({
            params:{status: "ALL", limit: "-1"}
        });
        
        me.reasonsStore.load({
            params:{status: "ALL", limit: "-1"}
        });
        
        me.suggestionsStore.load({
            params:{status: "ALL", limit: "-1"}
        });
        
        me.referralsStore.load({
            params:{status: "ALL", limit: "-1"},
            callback: function(r,options,success) {
                 if(success == true) {
                         me.getEarlyAlerts(); 
                  }
                  else {
                      Ext.Msg.alert("Ssp Error","Failed to load referrals. See your system administrator for assitance.");
                  }
             }
        });

    },

    destroy: function() {
        return this.callParent( arguments );
    },    
    
    getEarlyAlerts: function(){
        var me=this;
        var pId = me.personLite.get('id');
        me.getView().setLoading(true);
        me.earlyAlertService.getAll( pId, 
            {success:me.getEarlyAlertsSuccess, 
             failure:me.getEarlyAlertsFailure, 
             scope: me});
    },
    
    getEarlyAlertsSuccess: function( r, scope){
        var me=scope;
        var personEarlyAlert;
        me.getView().setLoading(false);
        if ( me.earlyAlertsStore.getCount() > 0)
        {
            me.getView().getSelectionModel().select(0);
        }else{
            // if no record is available then set the selected early alert to null
            personEarlyAlert = new Ssp.model.tool.earlyalert.PersonEarlyAlert();
            me.earlyAlert.data = personEarlyAlert.data;
        }
		
    },

    getEarlyAlertsFailure: function( r, scope){
        var me=scope;
        me.getView().setLoading(false);
    },
	
    onItemExpand: function(nodeInt, obj){
        var me=this;
        var node = nodeInt;
        var nodeType = node.get('nodeType');
        var id = node.get('id' );
        var personId = me.personLite.get('id');
        if (node != null)
        {
            // use root here to prevent the expand from firing
            // when items are added to the root element in the tree
            if (nodeType == 'early alert' && id != "root" && id != "")
            {
                me.getView().setLoading(true);
                me.earlyAlertService.getAllEarlyAlertResponses(personId, id,
                {success:me.getEarlyAlertResponsesSuccess, 
                 failure:me.getEarlyAlertResponsesFailure, 
                 scope: me} 
                );
            }           
        }
    },
  
    getEarlyAlertResponsesSuccess: function( r, scope){
        var me=scope;
        // clear the current Early Alert Response
        var earlyAlertResponse = new Ssp.model.tool.earlyalert.EarlyAlertResponse();
        me.earlyAlertResponse.data = earlyAlertResponse.data;
        me.getView().setLoading(false);
    },

    getEarlyAlertResponsesFailure: function( r, scope){
        var me=scope;
        me.getView().setLoading(false);
    },    

    onAlertClick: function(nodeInt, obj){
        var me=this;
        var record = me.getView().getSelectionModel().getSelection()[0];
		var node = nodeInt;
       
		var id = record.get('id');
		
		var personId = me.personLite.get('id');

        if (record != null)
        {
            if (record.get('nodeType')=='early alert')
            {
                for (prop in me.earlyAlert.data)
                {
                    me.earlyAlert.data[prop] = record.data[prop];
                }


				/*me.earlyAlertService.getAllEarlyAlertResponses(personId, id,
                        {success:me.getEarlyAlertResponsesSuccess, 
                 failure:me.getEarlyAlertResponsesFailure, 
                 scope: me} );*/

                me.displayEarlyAlertDetails();
            }
			/*else{
                
                for (prop in me.earlyAlertResponse.data)
                {
                    me.earlyAlertResponse.data[prop] = record.data[prop];
                }
                
                me.displayEarlyAlertResponseDetails();              
            }  */ 
        }else{
            Ext.Msg.alert('Notification','Please select an item to view.');
        }
    },

    displayEarlyAlertDetails: function(button){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertDetailsDisplay(), true, {reloadEarlyAlert: false});
    },
    
    displayEarlyAlertResponseDetails: function(button){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertResponseDetailsDisplay(), true, {});
    }
});