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
var cc = 0;

Ext.define('Ssp.service.EarlyAlertService', {  
    extend: 'Ssp.service.AbstractService',          
    mixins: [ 'Deft.mixin.Injectable'],
    inject: {
        apiProperties: 'apiProperties',
        earlyAlertOutcomesStore: 'earlyAlertOutcomesStore',
        treeStore: 'earlyAlertsTreeStore',
        treeUtils: 'treeRendererUtils',
		currentEarlyAlertResponsesGridStore: 'currentEarlyAlertResponsesGridStore'
    },
    initComponent: function() {
        return this.callParent( arguments );
    },
    
    getBaseUrl: function( personId ){
        var me=this;
        var baseUrl = me.apiProperties.createUrl( me.apiProperties.getItemUrl('personEarlyAlert') );
        baseUrl = baseUrl.replace( '{personId}', personId );
        return baseUrl;
    },

    getAll: function( personId, callbacks ){
        var me=this;
        var success = function( response, view ){
            var r = Ext.decode(response.responseText);
            if (r.rows.length > 0)
            {
                
                me.populateEarlyAlerts( r.rows,personId );
                
            }
            if (callbacks != null)
            {
                callbacks.success( r, callbacks.scope );
            }   
        };

        var failure = function( response ){
            me.apiProperties.handleError( response );           
            if (callbacks != null)
            {
                callbacks.failure( response, callbacks.scope );
            }
        };
        
        // clear the early alerts
        me.treeStore.setRootNode({
            text: 'EarlyAlerts',
            leaf: false,
            expanded: false
        });
        
        me.apiProperties.makeRequest({
            url: me.getBaseUrl(personId),
            method: 'GET',
            successFunc: success,
            failureFunc: failure,
            scope: me
        });
    },

    getAllEarlyAlertResponses: function( personId, earlyAlertId, callbacks ){
        var me=this;
        var url = "";
        var node = me.cleanResponses( earlyAlertId );
        var success = function( response, view ){
            var r = Ext.decode(response.responseText);
            me.resetEarlyAlertResponsesStore();
            if (r.rows.length > 0)
            {
               
                me.populateEarlyAlertResponses( node, r.rows );
                
            }
            if (callbacks != null)
            {
                callbacks.success( r, callbacks.scope );
            }   
        };

        var failure = function( response ){
            me.apiProperties.handleError( response );           
            if (callbacks != null)
            {
                callbacks.failure( response, callbacks.scope );
            }
        };
        
        url = me.getBaseUrl(personId);
        if (earlyAlertId != null && earlyAlertId != "")
        {
            me.apiProperties.makeRequest({
                url: url + '/' + earlyAlertId + '/response',
                method: 'GET',
                successFunc: success,
                failureFunc: failure,
                scope: me
            });         
        }
    },  

    getAllEarlyAlertCount: function( personId, earlyAlertId, callbacks ){
        var me=this;
        var url = "";
        //var node = me.cleanResponses( earlyAlertId );
        var success = function( response, view ){
            var r = Ext.decode(response.responseText);
            if (callbacks != null)
            {
                callbacks.success( personId, earlyAlertId, r.rows.length, callbacks.scope );
            } 
        };

        var failure = function( response ){
            
            me.apiProperties.handleError( response );           
            if (callbacks != null)
            {
                callbacks.failure( response, callbacks.scope );
            }
        };
        url = me.getBaseUrl(personId);
		
        if (earlyAlertId != null && earlyAlertId != "")
        {
            me.apiProperties.makeRequest({
                url: url + '/' + earlyAlertId + '/response',
                method: 'GET',
                successFunc: success,
                failureFunc: failure,
                scope: me
            });       
        }
    },    
    
    populateEarlyAlerts: function( records, personId ){
        var me=this;
        
        Ext.Array.each( records, function(record, index){
             // count = me.getAllEarlyAlertCount(personId, record.id);
            //record.iconCls='earlyAlertTreeIcon';
            record.leaf=false;
            record.nodeType='early alert';
            record.gridDisplayDetails=record.courseName + " - " + record.courseTitle ; 
            record.noOfResponses = 0;
            record.expanded=false;
        });

        me.treeStore.getRootNode().appendChild(records);
    },

    resetEarlyAlertResponsesStore: function() {
        var me = this;
        me.currentEarlyAlertResponsesGridStore.removeAll();
    },
    
    populateEarlyAlertResponses: function( node, records ){
        var me=this;
        
		var dataArray = [];
        Ext.Array.each( records, function(record, index){
            record.leaf=true;
            record.nodeType='early alert response';
            record.gridDisplayDetails=me.earlyAlertOutcomesStore.getById(record.earlyAlertOutcomeId).get('name');
			dataArray.push(record);
        });
		
		me.currentEarlyAlertResponsesGridStore.loadData(dataArray);

        node.appendChild(records);
    },
    
    cleanResponses: function( earlyAlertId ){
        var me=this;
        node = me.treeStore.getNodeById( earlyAlertId );
        node.removeAll();
        return node;
    },
    
    save: function( personId, jsonData, callbacks ){
        var me=this;
        var id=jsonData.id;
        var url = me.getBaseUrl(personId);
        var success = function( response, view ){
            var r = Ext.decode(response.responseText);
            callbacks.success( r, callbacks.scope );
        };

        var failure = function( response ){
            me.apiProperties.handleError( response );           
            callbacks.failure( response, callbacks.scope );
        };
        
        // save
        if (id=="")
        {
            // create
            me.apiProperties.makeRequest({
                url: url,
                method: 'POST',
                jsonData: jsonData,
                successFunc: success,
                failureFunc: failure,
                scope: me
            });             
        }else{
            // update
            me.apiProperties.makeRequest({
                url: url+"/"+id,
                method: 'PUT',
                jsonData: jsonData,
                successFunc: success,
                failureFunc: failure,
                scope: me
            }); 
        }       
    }
});