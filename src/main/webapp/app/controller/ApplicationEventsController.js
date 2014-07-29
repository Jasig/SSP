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
Ext.define('Ssp.controller.ApplicationEventsController', {
	extend: 'Ext.Base',
	config: {
		app: null
	},
		
	constructor: function(config){
		this.initConfig(config);
		return this.callParent(arguments);
	},
	
	setApplication: function(app){
		this.app = app;
	},	
	
	getApplication: function(){
		return this.app;
	},
	
	loadMaskOn: function(){
		Ext.ComponentQuery.query('sspview')[0].setLoading(true);
	},
	
	loadMaskOff: function(){
		Ext.ComponentQuery.query('sspview')[0].setLoading(false);
	},
	
	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	assignEvent: function( args ){
			this.getApplication().addListener(args.eventName, args.callBackFunc, args.scope);
			if(sspInDevelopMode)
				this.addObjectEvent(args);
	},
	
	addObjectEvent: function(args){
		var me=this;
		if(!me.events)
			me.events = {};
		if(args.scope.view && args.scope.view.id){
			var scopeId = args.scope.view.id;
			if(!this.events[scopeId]){
				this.events[scopeId] = [];
				args.scope.view.on("destroy", this.onObjectDestroyed, this);
			}
			this.events[scopeId].push({eventName:args.eventName, callBackFunc:args.callBackFunc, scope:args.scope, doNotDestroyAll: args.doNotDestroyAll});
		}
	},

	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	removeEvent: function( args ){
		if ( this.getApplication().hasListener( args.eventName ))
		{
			if(sspInDevelopMode)
				this.removeObjectEvent(args);
			
			this.getApplication().removeListener( args.eventName, args.callBackFunc, args.scope);
		}
	},
	
	removeObjectEvent: function(args){
		if(!args.scope.view || !args.scope.view.id)
		   return;
		
		var objEvents = this.events[args.scope.view.id];
		if(objEvents){
			for(var i = 0; i < objEvents.length; i++){
				if(objEvents[i].eventName === args.eventName){
					objEvents.splice(i,1);
					break;
				}
			}
		}
	},
	
	checkAllObjectsForEvents:function(){
		var objectCount = this.eventObjectCount();
		if(!this.eventObjectCounts){
			this.eventObjectCounts[objectCount];
		}else if(this.eventObjectCounts.length < 2){
			this.eventObjectCounts.push(objectCount);
		}else{
			var previousObjectCount = this.eventObjectCounts.pop();
			this.eventObjectCounts.push(objectCount);
			if(previousObjectCount < objectCount){
				this.alertObjectCountIncreasing(previousObjectCount, objectCount);
			}
		}
	},
	
	eventObjectCount: function(){
		var count = 0;
		for (var key in this.events) {
			if (this.events.hasOwnProperty(key)) {
			   count++;
			}
		}
		return count;
	},
	
	alertObjectCountIncreasing: function(previousObjectCount, currentObjectCount){
		var errorMessage = "Object Count Is Increasing. Prevous count:" + previousObjectCount + "Current Count:" + currentObjectCount;
		var eventsFound = false;
		for (var key in this.events) {
			if (this.events.hasOwnProperty(key)) {
			   if(this.events[key].length > 0 && this.events[key][0].doNotDestroyAll !== true){
					var msg = this.destroyObjectEvents(this.events[key], key);
					if(msg){
						eventsFound = true;
						errorMessage += msg;
					}
			   }
			}
		}
		if(eventsFound)
			Ext.MessageBox.alert("Objects Not Removed", errorMessage + " <br><b>Please notify developers.</b>");
	},

	
	onObjectDestroyed: function(view){
		var errorMessage = this.eventsDestroyed(this.events[view.id], view.id);
		delete this.events[view.id];
		if(errorMessage){
			Ext.MessageBox.alert("Uncleaned Events", errorMessage + "You may continue. <br><b>Please notify developers.</b>");
		}
	},
	
	eventsDestroyed: function(objEvents, objectId){
		var errorMessage= false;
		if(objEvents && objEvents.length > 0){
			errorMessage = "Object: " + objectId + " did not clean all events. <br> Events that needed to be cleaned:<br>";
			for(var i = 0; i < objEvents.length; i++){
				var args = objEvents[i];
				errorMessage += "&nbsp;&nbsp;&nbsp;&nbsp;" + args.eventName + "<br>" ;
			}
		}
		return errorMessage;
	}
});