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
Ext.define('Ssp.controller.tool.demo.DemoViewController', {
	extend: 'Deft.mvc.ViewController',
	mixins: [ 'Deft.mixin.Injectable' ],
	inject: {
		apiProperties: 'apiProperties'
	},
	control: {
		serverDateField: '#serverDate',
        serverTimestampField: '#serverTimestamp'
	},

	init: function() {
        var me = this;
		me.apiProperties.makeRequest({
			url: me.apiProperties.createUrl( me.apiProperties.getItemUrl('serverDateTime') ),
			method: 'GET',
			successFunc: me.renderServerDateTime,
			scope: me
		});
	},

	renderServerDateTime: function(r) {
        var me = this;
		var jsonData = Ext.decode(r.responseText);
		var date = Ext.Date.parse(jsonData.date, 'c');
		me.getServerDateField().setValue(date);
        var time = new Date(jsonData.timestamp);
        me.getServerTimestampField().setValue(time);
	}

});