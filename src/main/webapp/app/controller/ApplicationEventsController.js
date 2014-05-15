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
	
	/**
	 * @args
	 *   eventName - the name of an event to listen against
	 *   callBackFunc - the function to run when the event occurs
	 *   scope - the scope to run the function under
	 */
	assignEvent: function( args ){
			this.getApplication().addListener(args.eventName, args.callBackFunc, args.scope);
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
			this.getApplication().removeListener( args.eventName, args.callBackFunc, args.scope);
		}
	}
});