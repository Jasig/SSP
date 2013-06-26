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
Ext.define('Ssp.store.StudentActivities', {
    extend: 'Ext.data.Store',
    model: 'Ssp.model.StudentActivity',
   
	constructor: function(){
		return this.callParent(arguments);
	},

	getMostRecentActivitiesStore: function(maxiumum){
		if(!maxiumum)
			maxiumum = 20;
		var me = this;
		var mostRecentActivityStore = Ext.create('Ext.data.Store',{
			model: 'Ssp.model.StudentActivity',
		});
		mostRecentActivityStore.loadRecords(me.getRange(0, maxiumum));
		return mostRecentActivityStore;
	},
	
	getCoachLastActivity: function(coachId){
		var me = this;
		var indexLatestActivity = me.find('coachId', coachId);
		if(indexLatestActivity == -1)
			return null;
		return me.getAt(indexLatestActivity);
	}			
});