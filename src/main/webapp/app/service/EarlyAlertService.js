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

    standardFailure: function(callbacks) {
        var me = this;
        return function(response) {
            me.apiProperties.handleError( response );
            if (callbacks)
            {
                callbacks.failure( response, callbacks.scope );
            }
        }
    },

    get: function( earlyAlertId, personId, callbacks ) {
        var me = this;
        var success = function( response ) {
            var r;
            if ( response.responseText ) {
                r = Ext.decode(response.responseText);
            }
            if ( callbacks ) {
                callbacks.success( r, callbacks.scope );
            }
        };
        var failure = me.standardFailure(callbacks);
        me.apiProperties.makeRequest({
            url: me.getBaseUrl(personId) + "/" + earlyAlertId,
            method: 'GET',
            successFunc: success,
            failureFunc: failure,
            scope: me
        });
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
		me.treeStore.getRootNode().removeAll();
        
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
        var sortingStore = Ext.create('Ext.data.Store', {
        	model: 'Ssp.model.tool.earlyalert.PersonEarlyAlertTree',
        	storeId: 'earlyAlertSortingStore',
        	sorters: [{
        		sorterFn: function(o1, o2) {
        			var getClosedDate = function(o) {
        					var closedDate = o.get('closedDate');
        					if (closedDate) {
        						return 2;
        					} else {
        						return 1;
        					}
        				},
        				rank1 = getClosedDate(o1),
        				rank2 = getClosedDate(o2);

        			if (rank1 === rank2) {
        				return 0;
        			}

        			return rank1 < rank2 ? -1 : 1;
        		}
        	}, {
        		property: "lastResponseDate",
        		direction: "DESC"
        	}, {
        		property: "createdDate",
        		direction: "DESC"
        	}]
        });

    	
    	sortingStore.loadData(records);
    	
    	sortingStore.sort();
    	var models = sortingStore.getRange();
    	var sortedRecords = []
        Ext.Array.each( models, function(record, index){
        	sortedRecords[index]=record.data
        	sortedRecords[index].leaf=false;
        	sortedRecords[index].nodeType='early alert';
        	sortedRecords[index].gridDisplayDetails=sortedRecords[index].courseName + " - " + sortedRecords[index].courseTitle ; 
        	sortedRecords[index].expanded=false;
        });

		me.treeStore.getRootNode().removeAll();
        me.treeStore.getRootNode().appendChild(sortedRecords);
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
			record.gridDisplayDetails='';
            if ( record.earlyAlertOutcomeId ) {
                var outcome = me.earlyAlertOutcomesStore.getById(record.earlyAlertOutcomeId);
                if ( outcome ) {
                    record.gridDisplayDetails = outcome.get('name');
                }
            } 	
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