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
        campusesStore: 'campusesStore',
        earlyAlertsStore: 'earlyAlertsStore',
        earlyAlertService: 'earlyAlertService',
        earlyAlertResponseService: 'earlyAlertResponseService',
        earlyAlert: 'currentEarlyAlert',
        earlyAlertResponse: 'currentEarlyAlertResponse',
        formUtils: 'formRendererUtils',
        outcomesStore: 'earlyAlertOutcomesStore',
        outreachesStore: 'earlyAlertOutreachesStore',
        reasonsStore: 'earlyAlertReasonsStore',
        personLite: 'personLite',
        referralsStore: 'earlyAlertReferralsStore',
        suggestionsStore: 'earlyAlertSuggestionsStore',
        treeStore: 'earlyAlertsTreeStore'
    },
    config: {
        containerToLoadInto: 'tools',
        formToDisplay: 'earlyalertresponse',
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
            params:{limit:50}
        });
        
        me.confidentialityLevelsStore.load({
            params:{limit:50}
        });
        
        me.outcomesStore.load({
            params:{limit:50}
        });
        
        me.outreachesStore.load({
            params:{limit:50}
        });
        
        me.reasonsStore.load({
            params:{limit:50}
        });
        
        me.suggestionsStore.load({
            params:{limit:50}
        });
        
        me.referralsStore.load({
            params:{limit:50},
            callback: function(r,options,success) {
                 if(success == true) {
                         me.getEarlyAlerts(); 
                  }
                  else {
                      Ext.Msg.alert("Ssp Error","Failed to load referrals. See your system administrator for assitance.");
                  }
             }
        });
        
        me.appEventsController.assignEvent({eventName: 'goToResponse', callBackFunc: me.onRespondClick, scope: me});
    },  

    destroy: function() {
        var me=this;
        //me.appEventsController.removeEvent({eventName: 'goToResponse', callBackFunc: me.onRespondClick, scope: me});
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
		var pId = me.personLite.get('id');
		
		me.treeStore.getRootNode().eachChild(function(record){
			var eaId = record.get('id');
            me.earlyAlertService.getAllEarlyAlertCount( pId, eaId, 
                {success:me.getEarlyAlertCountSuccess, 
                 failure:me.getEarlyAlertCountFailure, 
                 scope: me});
			}
	    );
    },

    getEarlyAlertsFailure: function( r, scope){
        var me=scope;
        me.getView().setLoading(false);
    },
	
	getEarlyAlertCountSuccess: function(personId, earlyAlertId, count, scope) {
		var me = scope;
		// update the tree node w/ the count
		var originalChild = me.treeStore.getRootNode().findChildBy(function(n) {
                return earlyAlertId === n.data.id;
            }, me, true);
		me.treeStore.suspendEvents();
		originalChild.set('noOfResponses',count);
        me.treeStore.resumeEvents();
		
	},

    getEarlyAlertCountFailure: function( r, scope){
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
    
    onRespondClick: function( button ){
        
        var me=this;
        
        var record = me.getView().getSelectionModel().getSelection()[0];
        if (record != null)
        {
            if (record.get('nodeType')=='early alert')
            {
                for (prop in me.earlyAlert.data)
                {
                    me.earlyAlert.data[prop] = record.data[prop];
                }
                
                me.loadEditor();
            }else{
                Ext.Msg.alert('Notification','Please select an Early Alert to send a response.');
            }   
        }else{
            Ext.Msg.alert('Notification','Please select an Early Alert to send a response.');
        }
    },
    
    onGoToResponse: function(){
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
    
    loadEditor: function(button){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getFormToDisplay(), true, {});
    },
    
    displayEarlyAlertDetails: function(button){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertDetailsDisplay(), true, {});
    },
    
    displayEarlyAlertResponseDetails: function(button){
        var comp = this.formUtils.loadDisplay(this.getContainerToLoadInto(), this.getEarlyAlertResponseDetailsDisplay(), true, {});
    }
});